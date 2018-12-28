package cn.com.wz.vehcert.workshop

import cn.com.wz.parent.DateUtil

/**
 * @Description 车间信息service
 * @create 2013-07-28 huxx
 */
class WorkshopService {

    /**
     * @Description  根据车辆型号和底盘型号查询是否存在,存在更新，不存在创建
     * @param params
     * @return
     * @create 2013-07-28 huxx
     */
    def save(params){
        //根据车间名称查询是否存在,存在更新，不存在创建
        Workshop workshop=null
        if (params.name){
            def cel={
                eq('name',"${params.name}")
            }
            def list=Workshop.createCriteria().list(cel)
            if (list && list.size()>0){
                workshop=list.get(0)
            }

        }
        if (workshop){
            workshop.properties=params
        }else{
            workshop=new Workshop(params)
        }

        def m=[:]
        if (workshop.save(flush: true)){
            m.result=true
        }else{
            m.result=false
        }
        m.instance=workshop
        return m
    }
}
