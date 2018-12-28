package sendDataToSap

import cn.com.wz.parent.system.LogService
import org.apache.axis.message.SOAPHeaderElement

import javax.xml.soap.SOAPElement

/**
 * Created with IntelliJ IDEA. 合格证调用SAP发布的webservice接口，将车辆更换信息传到SAP
 * User: QJ
 * Date: 18-07-24
 * Time: 上午8:26
 */
class SynDelivery {
    LogService logService

    def SynDelivery(def list, def log) {
        logService = log
        def responseMsg=''
        try {
            def zcinfoReplaceList = list
            SI_DMS_ZSDI034_OUTBindingStub service = new SI_DMS_ZSDI034_OUTServiceLocator().getHTTP_Port(new URL(
                    "http://sappidev.wuzheng.com.cn:50000/dir/wsdl?p=sa/4bacefefa3f03600afd626976b5055f2"))
            service.username = "xp.lei"
            service.password = "handhand"
            SOAPHeaderElement header = new SOAPHeaderElement("http://www.wuzheng.com.cn/DMS/ZSDI034/Sender", "SI_DMS_ZSDI034_OUT")
            SOAPElement soap = null;
            soap = header.addChildElement("UserName");
            soap.addTextNode("xp.lei");
            soap = header.addChildElement("PassWord");
            soap.addTextNode("handhand");
            service.setHeader(header)
            int failNumb = 0   //失败记录数目
            int successNumb = 0 //成功记录数目
            zcinfoReplaceList.each { zcinfoReplace ->
                def detailMessage =''
                if(zcinfoReplace.transToSap==2){
                    ZSDI034 item = new ZSDI034()
                    item.i_ZCLXH = zcinfoReplace.veh_Clxh_R
                    item.i_ZEQUNR = zcinfoReplace.SAP_No
                    item.i_ZFDJH = zcinfoReplace.veh_Fdjh_R
                    item.i_ZFDJXH = zcinfoReplace.veh_Fdjxh_R
                    item.i_ZHGZBH = zcinfoReplace.veh_Zchgzbh_R
                    item.i_ZVIN = zcinfoReplace.veh_Clsbdh_R
                    item.i_ZVIN_OLD = zcinfoReplace.veh_Clsbdh
                    item.i_ZTIME = zcinfoReplace.authTime
                    ZSDI034Response response = service.SI_DMS_ZSDI034_OUT(item)
                    def type = response.e_TYPE
                    def test = response.e_TEXT
                    if (type == 'S') {
                        detailMessage = "旧VIN：" + zcinfoReplace.veh_Clsbdh + ",新VIN：" + zcinfoReplace.veh_Clsbdh_R + ",变更申请时间：" + zcinfoReplace.authTime + ",是否变更成功：成功" + ",SAP返回信息：" + test
                        successNumb++
                    } else if (type == 'E') {
                        detailMessage = "旧VIN：" + zcinfoReplace.veh_Clsbdh + ",新VIN：" + zcinfoReplace.veh_Clsbdh_R + ",变更申请时间：" + zcinfoReplace.authTime + ",是否变更成功：失败" + ",SAP返回信息：" + test
                        failNumb++
                    }
                }else{
                        detailMessage = "旧VIN：" + zcinfoReplace.veh_Clsbdh + ",新VIN：" + zcinfoReplace.veh_Clsbdh_R + ",变更申请时间：" + zcinfoReplace.authTime + ",是否变更成功：失败" + ",原因：车辆变更信息还未同步到SAP成功"
                        failNumb++
                }
                println(detailMessage)
                logService.insertLog('402885e93c8013fd013c801446d10035', "SynToSapDetailWebService", detailMessage, "systemLog")
        }
             responseMsg = '同步到SAP中间表数据' + list.size() + '条，成功' + successNumb + "条,失败" + failNumb + "条。"
        } catch (Exception e) {
             responseMsg = '同步异常：'+"${e.cause ? e.cause : e}"
            throw e
        } finally {
            println(responseMsg)
            logService.insertLog('402885e93c8013fd013c801446d10035', "SynToSapWebService", responseMsg, "systemLog")
        }
    }
}
