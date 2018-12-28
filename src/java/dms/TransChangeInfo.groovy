package dms

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.LogService
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import org.apache.axis.message.SOAPHeaderElement

import javax.xml.rpc.holders.BooleanHolder
import javax.xml.rpc.holders.StringHolder
import javax.xml.soap.SOAPElement

/**
 * Created with IntelliJ IDEA.
 * @Description 将合格证更换记录传到CRM
 * User: zhuwei
 * Date: 15-12-11
 * Time: 上午8:22
 * To change this template use File | Settings | File Templates.
 */
class TransChangeInfo {
    LogService logService
    def ids=new ArrayList()
    def TransChangeInfo(def list,def log){
        ids= list
        logService = log
        def stratTime=DateUtil.getCurrentTime()
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

            int  failNumb=0   //失败记录数目
            int  successNumb=0 //成功记录数目

            def  responseMsg
            BooleanHolder flag=new BooleanHolder()
            StringHolder message = new StringHolder()

                list.each{l->
                    String  wz_unique=l.veh_Clsbdh  //旧VIN
                    String  wz_certificate_code=l.veh_Zchgzbh_0_R //更换后完整合格证编号
                    String  wz_vin=l.veh_Clsbdh_R   //更换后新VIN
                    String  fdjxh=l.veh_Fdjxh_R// 更换后发动机型号
                    String  wz_engineno=l.veh_Fdjh_R  //更换后发动机号
                    String  wym=l.veh_Clxh_R//更换后车辆型号
                    def time=l.createTime//变更记录申请的时间
                    def zcinfoReplaceId=l.id
                    service.updateVehicle(wz_unique,wz_certificate_code,wz_vin,fdjxh,wz_engineno,wym,flag,message)
                    def aaa=flag
                    def localFlagl=flag.value.toString().toBoolean()
                    def ccc=message
                    def localMessage=message.value.toString()
                    println(localFlagl)
                    println(localMessage)
                     //获取当前传输的变更记录
                    def zcinfoRelace=ZCInfoReplace.get(zcinfoReplaceId)
                    def detailMessage="旧VIN："+wz_unique+",新VIN："+wz_vin+",变更申请时间："+time+"同步"

//                    int transToCrm=0   //0表示要传给CRM，1禁止传给CRM  ，2传给CRM成功，3传给CRM失败
                    if(!localFlagl){   //失败

                        println ">................................失败"
                        detailMessage+="失败，原因："+localMessage
                        failNumb++
                        //如果返回消息是：无效车辆信息！，那么僵这个更换记录的transToCrm字段更新成1，不再向CRM传输
                        if(localMessage=='无效车辆信息！'){
//                            println('CRM'+localMessage)
                            //禁止传输
                            zcinfoRelace.transToCrm=1
                        }else{
                            zcinfoRelace.transToCrm=3
                        }
                        if(zcinfoRelace.save(flush: true)){
                            println("变更记录保存成功")
                        }else{
                            println("变更记录保存失败")
                        }

                    }else{ //成功
                        detailMessage+="成功"
                        zcinfoRelace.transToCrm=2
                        if(zcinfoRelace.save(flush: true)){
                            println("变更记录保存成功")
                        }else{
                            println("变更记录保存失败")
                        }
                    }
                    println(detailMessage)
                    logService.insertLog('402885e93c8013fd013c801446d10035', "SynToCrmDetail",detailMessage, "systemLog")
                }
                successNumb=ids.size()-failNumb
                responseMsg='同步到CRM数据'+ids.size()+'条，成功'+successNumb+"条,删除失败"+failNumb+"条。"
            println(responseMsg)
            logService.insertLog('402885e93c8013fd013c801446d10035', "SynToCrmTotal",responseMsg, "systemLog")
        }catch(Exception e){
            println e.cause?e.cause:e
        }

    }
}
