package newDms

import cn.com.wz.parent.system.LogService
import cn.com.wz.parent.system.dictionary.DictionaryValue
import dms.WzAfficheLocator
import dms.WzAfficheSoap12Stub
import org.apache.axis.message.SOAPHeaderElement

import javax.servlet.ServletContext
import javax.xml.rpc.holders.BooleanHolder
import javax.xml.rpc.holders.StringHolder
import javax.xml.soap.SOAPElement

/**
 * Created by kuail_000 on 2014/7/29 0029.
 * @UPdate 2015-04-15 公告记录传输到DMS是屏蔽掉turnOff和turnOffTime字段 zhuwei
 */
class NewSynAffiche implements Runnable {
    LogService logService
    ServletContext servletContext
    def  startTime
    def carstoreList=new ArrayList<newDms.AfficheModel>()
    def NewSynAffiche(def list,def service, def servlet,def time){       // SynAffiche的构造函数
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
            def failThreadCount=servletContext.getAttribute('failThreadCount')   //记录同步失败超过10条公告的线程数量
            def synThreadCount= servletContext.getAttribute('synThreadCount')    //同步需要的线程数量
            //   正式地址
//            AfficheFromCRMSoap12Stub service=  new AfficheFromCRMLocator().getAfficheFromCRMSoap12(new URL("http://192.168.0.34:8089/AfficheFromCRM.asmx"))      //测试地址
            AfficheFromCRMSoap12Stub service=  new AfficheFromCRMLocator().getAfficheFromCRMSoap12(new URL("http://crmwebservice.wuzheng.com.cn:8089/AfficheFromCRM.asmx"))      //正试地址
//              ###############DMS/CRM接口地址不在需要用账号、密码链接BEGIN##############
//            service.username="wzcrm"
//            service.password="WZcrm365@2017"

            SOAPHeaderElement header = new SOAPHeaderElement("http://tempuri.org/","CrmSoapHeader")
            SOAPElement soap = null;
            soap = header.addChildElement("UserName");
            soap.addTextNode("wzcrm");
            soap = header.addChildElement("PassWord");
            soap.addTextNode("WZcrm365@2017");
            service.setHeader(header)
//              ###############DMS/CRM接口地址不在需要用账号、密码链接BEGIN##############

            def failList=[]  //保存同步失败的公告唯一号
            def failListStr  //保存失败日志字符串
            int  failNumb=0   //失败记录数目
            int  successNumb=0 //成功记录数目
            def  responseMsg

            newDms.AfficheModel[] list=new newDms.AfficheModel[10]
            def count=0
            def all=0
            def totalCount=carstoreList?.size()
            carstoreList?.each{cs->
                newDms.AfficheModel sa=new newDms.AfficheModel()
                cs.properties.each{p->
                    if(p.key!='turnOff'&&p.key!='turnOffTime'){
                        switch (p.key){
                            case  "car_storage_no" :      //公告唯一码
                                sa.new_wym = p.value
                                break
                            case "veh_Clzzqymc" :         //车辆制造企业名称
                                sa.new_clzzqymc = p.value
                                break
                            case "veh_Qyid" :            //企业ID
                                sa.new_qyid   = p.value
                                break
                            case "veh_Clfl" :             //车辆分类
                                sa.new_clfl   = p.value
                                break
                            case "veh_Clmc" :            //车辆名称
                                sa.new_clmc   =p.value
                                break
                            case "veh_Clpp" :            //车辆品牌
                                sa.new_clpp   =p.value
                                break
                            case "veh_Clxh" :            //车辆型号
                                sa.new_clxh   =p.value
                                break
                            case "veh_Csys" :         //车身颜色
                                sa.new_csys   =p.value
                                break
                            case "veh_Dpxh" :           //底盘型号
                                sa.new_dpxh   =p.value
                                break
                            case  "veh_Dpid" :          //底盘ID
                                sa.new_iddpid = p.value
                                break
                            case "veh_Fdjxh" :            //发动机型号
                                sa.new_fdjxh   =p.value
                                break
                            case "veh_Rlzl" :             //燃料种类
                                sa.new_rlzl   =p.value
                                break
                            case "veh_Pl" :             //排量
                                sa.new_pl   =p.value
                                break
                            case  "veh_Gl" :           //功率
                                sa.new_gl   =p.value
                                break
                            case "veh_zdjgl" :                   //最大净功率
                                sa.new_zdjgl   =p.value
                                break
                            case "veh_Zxxs" :           //转向形式
                                sa.new_zxxs   =p.value
                                break
                            case "veh_Qlj" :        //前轮距
                                sa.new_qlj   =p.value
                                break
                            case "veh_Hlj" :          //后轮距
                                sa.new_hlj   =p.value
                                break
                            case "veh_Lts" :          //轮胎数
                                sa.new_lts   =p.value
                                break
                            case "veh_Ltgg" :      //轮胎规格
                                sa.new_ltgg   =p.value
                                break
                            case  "veh_Gbthps" :       //钢板弹簧片数
                                sa.new_gbthps   =p.value
                                break
                            case "veh_Zj" :           //轴距z
                                sa.new_zj   =p.value
                                break
                            case "veh_Zh" :     //轴荷?
                                sa.new_zh   =p.value
                                break
                            case "veh_Zs" :      //轴数
                                sa.new_zs   =p.value
                                break
                            case "veh_Wkc" :  //外廓长
                                sa.new_wkc   =p.value
                                break
                            case "veh_Wkk" :      //外廓宽
                                sa.new_wkk   =p.value
                                break
                            case "veh_Wkg" :    //外廓高
                                sa.new_wkg   =p.value
                                break
                            case  "veh_Hxnbc" :   //货箱内部长
                                sa.new_hxnbc   =p.value
                                break
                            case "veh_Hxnbk" :       //货箱内部宽
                                sa.new_hxnbk   =p.value
                                break
                            case "veh_Hxnbg" :      //货箱内部高
                                sa.new_hxnbg   =p.value
                                break
                            case "veh_Zzl" :      //总质量
                                sa.new_zzl   =p.value
                                break
                            case "veh_Edzzl" :     //额定载质量
                                sa.new_edzzl   =p.value
                                break
                            case "veh_Zbzl" :      //整备质量
                                sa.new_zbzl   =p.value
                                break
                            case "veh_Zzllyxs" :      //载质量利用系数
                                sa.new_zzllyxs   =p.value
                                break
                            case  "veh_Edzk" :     //额定载客 e
                                sa.new_edzk   =p.value
                                break
                            case "veh_Bgcazzdyxzzl" : //半挂车鞍座
                                sa.new_bgcazzdyxzzl   =p.value
                                break
                            case "veh_Jsszcrs" :    //驾驶室准乘人数
                                sa.new_jsszcrs   =p.value
                                break
                            case "veh_Yh" :     //油耗
                                sa.new_yh   =p.value
                                break
                            case "veh_Yhlx" :     //油耗类型
                                sa.new_yhlx   =p.value
                                break
                            case "veh_Hzdfs" :   //后制动方式
                                sa.new_hzdfs   =p.value
                                break
                            case "veh_Ggpc" :   //公告批次
                                sa.new_ggpc   =p.value
                                break
                            case  "veh_Cpggh" :   //产品公告号
                                sa.new_cpggh   =p.value
                                break
                            case "veh_Ggsxrq" :  //公告生效日期
                                sa.new_ggsxrq   =p.value
                                break
                            case "veh_Zgcs" :      //最高车速
                                sa.new_zgcs   =p.value
                                break
                            case "veh_Bz" :    //备注
                                sa.new_bz   =p.value
                                break
                            case "veh_Qybz" :   //企业标准
                                sa.new_qybz   =p.value
                                break
                            case "veh_Cpscdz" :   //产品生产地址
                                sa.new_cpscdz   =p.value
                                break
                            case "veh_Qyqtxx" :       //企业其他信息
                                sa.new_qyqtxx   =p.value
                                break
                            case  "veh_Pfbz" :       //排放标准
                                sa.new_pfbz   =p.value
                                break
                            case  "veh_Clztxx" :    //车辆状态信息
                                sa.new_clztxx = p.value
                                break
                            case "veh_Jss" :    //驾驶室
                                sa.new_jss   =p.value
                                break
                            case "veh_Jsslx" :   //驾驶室类型
                                sa.new_jsslx   =p.value
                                break
                            case "veh_cpid" :   //产品id
                                sa.new_cpid   =p.value
                                break
                            case "veh_jjlqj" :       //接近离去角
                                sa.new_jjlqj   =p.value
                                break
                            case  "veh_qxhx" :       //前悬后悬
                                sa.new_qxhx   =p.value
                                break
                            case  "veh_dpqy" :    //底盘企业
                                sa.new_dpqy = p.value
                                break
                            case "veh_fgbsqy" :    //反光标识企业
                                sa.new_fgbsqy   =p.value
                                break
                            case "veh_fgbssb" :   //反光标识商标
                                sa.new_fgbssb   =p.value
                                break
                            case "veh_fgbsxh" :   //反光标识型号
                                sa.new_fgbsxh   =p.value
                                break
                            case "veh_VinFourBit" :       //VIN第四位
                                sa.new_vinvinfourbit   =p.value
                                break
                            case  "veh_pzxlh" :       //配置序列号
                                sa.new_pzxlh   =p.value
                                break
//                            default:
//                                sa."${p.key.toString().replace("veh_","new_")}" = p.value
                        }
                    }
                                     }
                list[count]=sa
                count+=1
                all+=1
                if(count==10 || all==totalCount){
                    Result result = new  Result()
                    result = service.syncAffiche(list)
                    def code =   result.code
                    def mes =   result.message
                    if(code=='E'){
                        def aaa = list
                        list.each {p->
                            if(p?.new_wym){ //只有在list的元素部位空时才进入执行
                                println('打印公告唯一码'+p?.new_wym)
                                failList.add(p?.new_wym)
                                failListStr = failListStr+p?.new_wym+","
                                failListStr =failListStr+p?.new_wym+","
                                failNumb++
                            }

                        }
                        failListStr=failListStr+"原因"+mes+";"
                    }
                    count=0
                    list=new newDms.AfficheModel[10]
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
