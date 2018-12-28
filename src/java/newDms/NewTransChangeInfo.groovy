package newDms

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.LogService
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import dms.WzAfficheLocator
import dms.WzAfficheSoap12Stub
import org.apache.axis.message.SOAPHeaderElement

import javax.xml.rpc.holders.BooleanHolder
import javax.xml.rpc.holders.StringHolder
import javax.xml.soap.SOAPElement

/**
 * Created with IntelliJ IDEA.
 * @Description 将合格证更换记录传到CRM
 * User: zhuwei
 * Date: 2017-07-27
 * Time: 上午8:22
 * To change this template use File | Settings | File Templates.
 */
class NewTransChangeInfo {
    LogService logService
    def ids=new ArrayList()
    def NewTransChangeInfo(def list,def log){
        ids= list
        logService = log
        def stratTime=DateUtil.getCurrentTime()
        try{
//              AfficheFromCRMSoap12Stub service=  new AfficheFromCRMLocator().getAfficheFromCRMSoap12(new URL("http://192.168.0.34:8089/AfficheFromCRM.asmx"))      //测试地址
            AfficheFromCRMSoap12Stub service=  new AfficheFromCRMLocator().getAfficheFromCRMSoap12(new URL("http://crmwebservice.wuzheng.com.cn:8089/AfficheFromCRM.asmx"))      //正试地址
//            ################新接口已经不用使用账号和密码登陆了，所以以下部分注释掉BEGIN ############
//            service.username="wzcrm"
//            service.password="WZcrm365@2017"
            SOAPHeaderElement header = new SOAPHeaderElement("http://tempuri.org/","CrmSoapHeader")
            SOAPElement soap = null;
            soap = header.addChildElement("UserName");
            soap.addTextNode("wzcrm");
            soap = header.addChildElement("PassWord");
            soap.addTextNode("WZcrm365@2017");
            service.setHeader(header)
//            ################新接口已经不用使用账号和密码登陆了，所以以下部分注释掉END ############
            int  failNumb=0   //失败记录数目
            int  successNumb=0 //成功记录数目

            def  responseMsg
                list.each{l->
                    String  wz_unique           =l.veh_Clsbdh      //旧VIN
                    String  wz_certificate_code =l.veh_Zchgzbh_0_R //更换后完整合格证编号
                    String  wz_vin              =l.veh_Clsbdh_R    //更换后新VIN
                    String  fdjxh               =l.veh_Fdjxh_R     // 更换后发动机型号
                    String  wz_engineno         =l.veh_Fdjh_R      //更换后发动机号
                    String  wym                 =l.veh_Clxh_R      //更换后车辆型号
                    String  sapcode             =l.SAP_No          //SAP序列号
                    def time=l.createTime//变更记录申请的时间
                    def zcinfoReplaceId=l.id
                    Result result = new Result()
                    result = service.updateVehicle(wz_unique,wz_certificate_code,wz_vin,fdjxh,wz_engineno,wym,sapcode)
                    def code = result.code
                    def mes  = result.message
                     //获取当前传输的变更记录
                    def zcinfoRelace=ZCInfoReplace.get(zcinfoReplaceId)
                    def detailMessage="旧VIN："+wz_unique+",新VIN："+wz_vin+",变更申请时间："+time+"同步"
//                    int transToCrm=0   //0表示要传给CRM，1禁止传给CRM  ，2传给CRM成功，3传给CRM失败
                    if(code != 'S'){   //失败

                        println ">................................失败"
                        detailMessage = detailMessage + "失败，原因："+mes
                        failNumb++
                        //如果返回消息是：无效车辆信息！，那么僵这个更换记录的transToCrm字段更新成1，不再向CRM传输
                        //Update 2017-08-08 无效车辆信息也继续向CRM传输数据
//                        if(mes=='无效车辆信息！'){
//                            //禁止传输
//                            zcinfoRelace.transToCrm=1
//                        }else{
                            zcinfoRelace.transToCrm=3     //无效库存信息
//                        }
                        if(zcinfoRelace.save(flush: true)){
                            println("变更记录保存成功")
                        }else{
                            println("变更记录保存失败")
                        }

                    }else{ //成功
                        detailMessage=detailMessage+"成功"
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
