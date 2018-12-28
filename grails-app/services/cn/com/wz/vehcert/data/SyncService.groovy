package cn.com.wz.vehcert.data


import cn.com.wz.vehcert.zcinfo.ZCInfo
import cn.com.wz.web.Result

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="Sync",serviceName="Sync",targetNamespace="http://www.oksonic.cn/xfire")
class SyncService {
    def sqlService;
    def logService
    static expose=['xfire']
    @WebMethod
    @WebResult
    /**
     * @Description维修网站调用该接口获得车辆参数信息
     * CreateTime 2016-04-13 by zhuwei
     */
    Result syncInfo(@WebParam String VIN)
//     String syncInfo(@WebParam String VIN)
    {
        def result=new Result()
        result.flag="S"
        result.message="查询成功"

        def veh_clsbdh =VIN
           if(veh_clsbdh.length() != 17){
                result.flag="E"
                result.message="VIN长度应为17位"
           }else{
               //按照VIN查找整车合格证信息,做了只有上传国家成功的才能查询
               def zcinfoList = ZCInfo.findAllByVeh_ClsbdhAndVeh_ClztxxAndWeb_status(veh_clsbdh,'QX','1')
               if(zcinfoList.size()>0){
                   def zcinfoInstance = zcinfoList[0]
                   //将车辆信息赋值给Result
                   result.body_color=zcinfoInstance?.veh_Csys//车身颜色
                   result.car_name = zcinfoInstance?.veh_Clmc //车辆名称
                   result.carvin   = zcinfoInstance?.veh_Clsbdh
                   result.car_type  =zcinfoInstance?.veh_Dpxh//地盘型号
                   result.displacement = zcinfoInstance?.veh_Pl
                   result.engine_num =zcinfoInstance?.veh_Fdjh
                   result.engine_type=zcinfoInstance?.veh_Fdjxh
                   result.fuel=zcinfoInstance?.veh_Rlzl
                   result.manufacture=zcinfoInstance?.veh_Clzzqymc
                   result.power=zcinfoInstance?.veh_Gl
                   result.tires_num=zcinfoInstance?.veh_Lts
                   result.vehicle_brand =zcinfoInstance?.veh_Clpp
                   result.vehicle_manufacturing_date=zcinfoInstance?.veh_Clzzrq
                   result.vehicle_model=zcinfoInstance?.veh_Clxh
                   result.wheel_base=zcinfoInstance?.veh_Zj
                   result.tire_size=zcinfoInstance?.veh_Ltgg

               }else{
                   result.flag="E"
                   result.message="未找到相关车辆信息"
               }

           }
        return result;

    }
}

