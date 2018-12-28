package dms

import cn.com.wz.parent.system.LogService
import cn.com.wz.parent.system.dictionary.DictionaryValue
import org.apache.axis.message.SOAPHeaderElement

import javax.servlet.ServletContext
import javax.xml.rpc.holders.BooleanHolder
import javax.xml.rpc.holders.StringHolder
import javax.xml.soap.SOAPElement

/**
 * Created with IntelliJ IDEA.
 * User: zhuwei
 * Date: 14-8-14
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
class DeleteCarStorage {
    LogService logService
    def ids=new ArrayList()
    def DeleteCarStorage(def list,def log){       // DeleteCarStorage
        ids= list
        logService = log
        try{
            WzAfficheSoap12Stub service=  new WzAfficheLocator().getWzAfficheSoap12(new URL("http://192.168.1.90:8088/WzAffiche.asmx"))      //正式地址
//            WzAfficheSoap12Stub service=  new WzAfficheLocator().getWzAfficheSoap12(new URL("http://192.168.1.38/CRMWebAffire/WzAffiche.asmx"))      //测试地址
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
//            List id= new ArrayList()

            def  responseMsg
            BooleanHolder flag=new BooleanHolder()
            StringHolder message = new StringHolder()

            if(ids.size()<1000){         //配置使用单条同步还是批量同步
                String[] id= new String[1]
                ids.each {p->
                    id[0]=p
                    service.delete(id,flag,message)
                    if(!flag.value){
                        println ">................................"
                    }
                    if((flag.value)&&(flag.value.toString()=='false')){
                        failListStr=failListStr+p+","
                        failListStr=+"原因"+message+";"
                        failNumb++
                    }
                }
                successNumb=ids.size()-failNumb
                responseMsg='删除成功'+successNumb+"条公告,删除失败"+failNumb+"条公告，同步失败信息"+ failListStr
            }else{
                String[] id= new String[ids?.size()]
                def count =0
                ids.each {p->
                    id[count]=p
                     count+=1
                }
                service.delete(id,flag,message)
                if(!flag.value){
                    println ">................................"
                }
                if((flag.value)&&(flag.value.toString()=='false')){
//                    failListStr=failListStr+p+","
                    responseMsg="删除"+ids?.size()+"条公告失败,失败原因"+message+";"
//                    responseMsg= failListStr
//                    failNumb++
                }else{
                    responseMsg='删除公告成功，共有'+ids?.size()+"条公告删除；"
                }
            }
            println(responseMsg)
            logService.insertLog('402885e93c8013fd013c801446d10035', "SynToDms",responseMsg, "systemLog")
        }catch(Exception e){
            println e.cause?e.cause:e
        }

    }
}