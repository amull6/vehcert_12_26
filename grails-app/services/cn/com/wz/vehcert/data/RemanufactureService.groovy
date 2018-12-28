package cn.com.wz.vehcert.data

import cn.com.wz.parent.DateUtil
import cn.com.wz.vehcert.zcinfo.Remanufacture
import cn.com.wz.vehcert.zcinfo.ZCInfo
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import cn.com.wz.web.Result
import cn.com.wz.web.RemanufactureResult

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService

@WebService(name="Remanufacture",serviceName="Remanufacture",targetNamespace="http://www.oksonic.cn/xfire")
class RemanufactureService {
    def sqlService;
    def logService
    static expose=['xfire']
    @WebMethod
    @WebResult
    /**
     * @Description改制接口
     * CreateTime 2018-05-17 by QJ
     */
    RemanufactureResult changeSapNo(@WebParam String VIN ,@WebParam String sapNo,@WebParam String newSapNo)
    {
        RemanufactureResult remanufactureResult=new RemanufactureResult()
        def veh_clsbdh =VIN
        def SAP_No=sapNo
        def SAP_No_r=newSapNo
        if(veh_clsbdh.length() != 17){
            remanufactureResult.flag="E"
            remanufactureResult.message="VIN长度应为17位"
        }else{
            //按照VIN查找整车合格证信息,做了只有上传国家成功的才能查询
            def cel={
                    eq('veh_Clsbdh',"${veh_clsbdh}")
                    eq('SAP_No',"${SAP_No}")
            }
            def zcinfoList=ZCInfo.createCriteria().list(cel)
            if(zcinfoList.size()>0){
                try{
                    Remanufacture remanufacture =new Remanufacture()
                    remanufacture.VIN=veh_clsbdh
                    remanufacture.SAP_No=SAP_No
                    remanufacture.SAP_No_r =SAP_No_r
                    remanufacture.create_id ='remanufacture'
                    remanufacture.create_time=DateUtil.getCurrentTime()
                    if ( remanufacture.save(flush: true)){
                        zcinfoList.each {zcinfo->
                            zcinfo.SAP_No=SAP_No_r
                            def responseMsg  = 'VIN:'+veh_clsbdh+'，合格证号'+ zcinfo.veh_Zchgzbh+'，更换前SAP序列号：'+SAP_No+'，更换后SAP序列号：'+ SAP_No_r +'，变更结果：'
                            if (zcinfo.save(flush: true)) {
                                responseMsg= responseMsg+'成功'
                                logService.insertLog('remanufacture', "zcinfoChangeSapNo", responseMsg, "systemLog")
                            }else{
                                responseMsg= responseMsg+'失败'
                                logService.insertLog('remanufacture', "zcinfoChangeSapNo", responseMsg, "systemLog")
                            }

                        }
                        def cel1={
                            eq('SAP_No',"${SAP_No}")
                        }
                        def zcinfoReplaceList=ZCInfoReplace.createCriteria().list(cel)
                        if(zcinfoReplaceList.size()>0){
                            zcinfoReplaceList.each {zcinfoReplace->
                                zcinfoReplace.SAP_No=SAP_No_r
                                def responseMsgR  = '更换前SAP序列号：'+SAP_No+'，更换后SAP序列号：'+ SAP_No_r +'，变更结果：'
                                if (zcinfoReplace.save(flush: true)) {
                                    responseMsgR= responseMsgR+'成功'
                                    logService.insertLog('remanufacture', "zcinfoReplaceChangeSapNo", responseMsgR, "systemLog")
                                }else{
                                    responseMsgR= responseMsgR+'失败'
                                    logService.insertLog('remanufacture', "zcinfoReplaceChangeSapNo", responseMsgR, "systemLog")
                                }

                            }
                        }
                        remanufactureResult.flag="S"
                        remanufactureResult.message="SAP序列号更新成功"
                    }else{
                        remanufactureResult.flag="E"
                        remanufactureResult.message="SAP序列号更新失败，请稍后再试"
                    }
                }catch(Exception e){
                    e.printStackTrace()
                    e.cause?.printStackTrace()
                    remanufactureResult.flag="E"
                    remanufactureResult.message="SAP序列号更新失败，请稍后再试"
                }
            }else{
                remanufactureResult.flag="E"
                remanufactureResult.message="未找到该车辆信息，请稍后再试"
            }

        }
        def remanufactureMessage =  'VIN:'+veh_clsbdh+'，更换前SAP序列号：'+SAP_No+'，更换后SAP序列号：'+ SAP_No_r +'，变更结果：'+ remanufactureResult.message
        logService.insertLog('remanufacture', "Remanufacture", remanufactureMessage, "systemLog")
        return remanufactureResult;
    }
}

