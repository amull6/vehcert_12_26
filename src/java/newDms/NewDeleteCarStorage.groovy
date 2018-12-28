package newDms

import cn.com.wz.parent.system.LogService
import dms.WzAfficheLocator
import dms.WzAfficheSoap12Stub
import org.apache.axis.message.SOAPHeaderElement

import javax.xml.rpc.holders.BooleanHolder
import javax.xml.rpc.holders.StringHolder
import javax.xml.soap.SOAPElement

/**
 * Created with IntelliJ IDEA.
 * User: zhuwei
 * Date: 2017-07-26
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
class NewDeleteCarStorage {
    LogService logService
    def ids=new ArrayList()
    def NewDeleteCarStorage(def list,def log){       // DeleteCarStorage
        ids= list
        logService = log
        try{
//            AfficheFromCRMSoap12Stub service=  new AfficheFromCRMLocator().getAfficheFromCRMSoap12(new URL("http://192.168.0.34:8089/AfficheFromCRM.asmx"))      //测试地址
            AfficheFromCRMSoap12Stub service=  new AfficheFromCRMLocator().getAfficheFromCRMSoap12(new URL("http://crmwebservice.wuzheng.com.cn:8089/AfficheFromCRM.asmx"))      //正试地址
//            ###############新放出的接口地址不用账号密码,所以该部分可以注释掉BEGIN############
//            service.username="wzcrm"
//            service.password="WZcrm365@2017"
            SOAPHeaderElement header = new SOAPHeaderElement("http://tempuri.org/","CrmSoapHeader")
            SOAPElement soap = null;
            soap = header.addChildElement("UserName");
            soap.addTextNode("wzcrm");
            soap = header.addChildElement("PassWord");
            soap.addTextNode("WZcrm365@2017");
            service.setHeader(header)
//            ###############新放出的接口地址不用账号密码,所以该部分可以注释掉END############

            String failListStr  //保存失败日志字符串
            int  failNumb=0   //失败记录数目
            int  successNumb=0 //成功记录数目

            def  responseMsg

            if(ids.size()<1000){         //配置使用单条同步还是批量同步
                String[] id= new String[1]
                Result result = new  Result()
                ids.each {p->
                    id[0]=p
//                  service.delete(id,flag,message)  //以前DMS/CRM提供的接口调用方法,已经注释掉
                    result =  service.delete(id)
                    def code =   result.code
                    def mes =   result.message
                   if(code =='E'){
                        failListStr=failListStr+p+","+"原因"+mes+";"
//                        failListStr=+"原因"+mes+";"
                        failNumb++
                    }
                }
                successNumb=ids.size()-failNumb
                responseMsg='删除成功'+successNumb+"条公告,删除失败"+failNumb+"条公告，同步失败信息"+ failListStr
            }else{
                String[] id= new String[ids?.size()]
                Result result = new  Result()

                def count =0
                ids.each {p->
                    id[count]=p
                     count+=1
                }
//                service.delete(id,flag,message)
                result =  service.delete(id)
                def code =   result.code
                def mes =   result.message
                if(code !='S'){
                    responseMsg="删除"+ids?.size()+"条公告失败,失败原因"+mes+";"
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