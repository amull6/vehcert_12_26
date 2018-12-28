package dms

import cn.com.wz.parent.system.LogService
import cn.com.wz.parent.system.dictionary.DictionaryValue
import org.apache.axis.message.SOAPHeaderElement

import javax.servlet.ServletContext
import javax.xml.rpc.holders.BooleanHolder
import javax.xml.rpc.holders.StringHolder
import javax.xml.soap.SOAPElement

/**
 * Created by kuail_000 on 2014/7/29 0029.
 * @UPdate 2015-04-15 公告记录传输到DMS是屏蔽掉turnOff和turnOffTime字段 zhuwei
 */
class SynAffiche implements Runnable {
    LogService logService
    ServletContext servletContext
    def  startTime
    def carstoreList=new ArrayList<AfficheModel>()
    def SynAffiche(def list,def service, def servlet,def time){       // SynAffiche的构造函数
        carstoreList= list
        logService = service
        servletContext=servlet
        startTime=time

    }
    void run() {
        try{

            def runThreadCount= servletContext.getAttribute('runThreadCount')    //正在运行的线程数
            servletContext.setAttribute('runThreadCount',++runThreadCount)
            def a= servletContext.getAttribute('runThreadCount')
            println(a)
            def failThreadCount=servletContext.getAttribute('failThreadCount')   //记录同步失败超过10条公告的线程数量
            def synThreadCount= servletContext.getAttribute('synThreadCount')    //同步需要的线程数量
            //   正式地址
            WzAfficheSoap12Stub service=  new WzAfficheLocator().getWzAfficheSoap12(new URL("http://192.168.1.90:8088/WzAffiche.asmx"))

//            WzAfficheSoap12Stub service=  new WzAfficheLocator().getWzAfficheSoap12(new URL("http://192.168.1.38/CRMWebAffire/WzAffiche.asmx"))  //测试地址
            service.username="webservice_pdmuser"
            service.password="Wz123"
            SOAPHeaderElement header = new SOAPHeaderElement("http://tempuri.org/","CRMSoapHeader")
            SOAPElement soap = null;
            soap = header.addChildElement("UserName");
            soap.addTextNode("webservice_pdmuser");
            soap = header.addChildElement("PassWord");
            soap.addTextNode("Wz123");
            service.setHeader(header)

            def failList=[]  //保存同步失败的公告唯一号
            def failListStr  //保存失败日志字符串
            int  failNumb=0   //失败记录数目
            int  successNumb=0 //成功记录数目
            def  responseMsg

            AfficheModel[] list=new AfficheModel[10]
            def count=0
            def all=0
            BooleanHolder flag=new BooleanHolder()
            StringHolder message = new StringHolder()
            def totalCount=carstoreList?.size()
            carstoreList?.each{cs->
                AfficheModel sa=new AfficheModel()
                cs.properties.each{p->
//                    println(p.key)
                    if(p.key!='turnOff'&&p.key!='turnOffTime'){
//                        println(p.key)
                        switch (p.key){
                            case "carStorageType" :
                                break
                            case "createTime" :
                                break
                            case "createrId" :
                                break
                            case "updateTime" :
                                break
                            case "updaterId" :
                                break
                            case "id" :
                                break
                            case "veh_y45pic" :
                                break
                            case "veh_zhpic" :
                                break
                            case "veh_fhpic" :
                                break
                            case "veh_pic1" :
                                break
                            case "veh_pic2" :
                                break
                            case  "veh_other" :
                                break
                            case  "veh_VinFourBit" :
                                sa.wz_vinVinFourBit = p.value
                                break
                            case  "veh_Dpid" :
                                sa.wz_IDDpid = p.value
                                break
                            case  "car_storage_no" :
                                sa.wz_wym = p.value
                                break
                            default:
                                sa."${p.key.toString().replace("veh_","wz_")}" = p.value
                        }
                    }

                }
                list[count]=sa
                count+=1
                all+=1
                if(count==10 || all==totalCount){
                    service.syncAffiche(list,flag,message)
                    if(flag.value.toString()=='false'){
                        list.each {p->
                            failList.add(p.wz_wym)
                            failListStr+=p.wz_wym+","
                            failNumb++
                        }
                        failListStr=+"原因"+message+";"
                    }
                    count=0
                    list=new AfficheModel[10]
                }
            }
            if(failNumb>=10){  //如果一个线程中同步失败记录数目超过10条，认为该线程同步失败，同步失败线程数+1
                servletContext.setAttribute('failThreadCount',failThreadCount++)
            }
            successNumb=carstoreList.size()-failNumb
            responseMsg='同步成功'+successNumb+"记录,失败"+failNumb+"条记录，同步失败信息"+ failListStr
            logService.insertLog('402885e93c8013fd013c801446d10035', "SynToDms",responseMsg, "systemLog")
            String sql="update DictionaryValue set value1='${startTime}' where code='lastDmsSynTime'"
            if(synThreadCount==1&&failThreadCount==0){            //单线程同步成功,保存同步时间
                println('单线程执行成功')
                DictionaryValue.executeUpdate(sql.toString())
            }else if(synThreadCount==1&&failThreadCount>0){     //单线程同步失败
                println('单线程执行失败')
            }else if(synThreadCount==4&&failThreadCount==0&&runThreadCount==4){     //多线程同步成功
                println('多线程执行成功')
                DictionaryValue.executeUpdate(sql.toString())
            }else if(synThreadCount==4&&failThreadCount>0){      //多线程同步失败
                println('多线程执行失败')
            }
        }catch(Exception e){
            println e.cause?e.cause:e
        }

    }
}
