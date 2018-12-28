package cn.com.wz.vehcert.carstorage
import cn.com.wz.parent.DateUtil

/**
 * @Description 分解前公告信息service
 * @create 2013-07-28 huxx
 */
class PreCarStorageService {

    /**
     * @Description  根据车辆型号和底盘型号查询是否存在,存在更新，不存在创建
     * @param params
     * @return
     * @create 2013-07-28 huxx
     * @Update 2013-11-12 修改图片处理有问题
     * @Update 2018-07-03 修改分解前公告批次的同时将分解后公告批次改过来
     */
    def save(params){
        //根据车辆型号和底盘型号查询是否存在,存在更新，不存在创建
        PreCarStorage preCarStorage=null
        if (params.veh_Clxh&&params.veh_Clxh!='NULL'){
            if (params.veh_Dpxh){
                def cel={
                    eq('veh_Clxh',"${params.veh_Clxh}")
                    eq('veh_Dpxh',"${params.veh_Dpxh}")
                }
                def list=PreCarStorage.createCriteria().list(cel)
                if (list && list.size()>0){
                    preCarStorage=list.get(0)
                }
            }else{
                def cel={
                    eq('veh_Clxh',"${params.veh_Clxh}")
                    isNull('veh_Dpxh')
                }
                def list=PreCarStorage.createCriteria().list(cel)
                if (list && list.size()>0){
                    preCarStorage=list.get(0)
                }
            }

        }else{
            def cel={
                eq('veh_Dpxh',"${params.veh_Dpxh}")
                isNull('veh_Clxh')
            }
            def list=PreCarStorage.createCriteria().list(cel)
            if (list && list.size()>0){
                preCarStorage=list.get(0)
            }
        }

        if (preCarStorage){
            //批量更新公告批次
            if(preCarStorage.veh_Ggpc!=params.veh_Ggpc){
                if(params.veh_Clztxx=='QX'){
                    def carstorageList = CarStorage.findAllByVeh_Clxh(params.veh_Clxh)
                    if(carstorageList.size()>0){
                        if(params.veh_Ggpc!=carstorageList.get(0).veh_Ggpc){
                            carstorageList.each {carstorage->
                                carstorage.veh_Ggpc=params.veh_Ggpc
                                carstorage.veh_Cpggh=params.veh_Cpggh
                                carstorage.veh_Ggsxrq=params.veh_Ggsxrq
                                carstorage.save(flush: true)
                            }
                        }
                    }
                }else if(params.veh_Clztxx=='DP'){
                    def carstorageList = CarStorage.findAllByVeh_ClxhAndVeh_Clztxx(params.veh_Dpxh,'DP')
                    if(carstorageList.size()>0){
                        if(params.veh_Ggpc!=carstorageList.get(0).veh_Ggpc){
                            carstorageList.each {carstorage->
                                carstorage.veh_Ggpc=params.veh_Ggpc
                                carstorage.veh_Cpggh=params.veh_Cpggh
                                carstorage.veh_Ggsxrq=params.veh_Ggsxrq
                                carstorage.save(flush: true)
                            }
                        }
                    }
                }
            }
            preCarStorage.properties=params
        }else{
            preCarStorage=new PreCarStorage(params)
        }
        def historyGgpcList
        if(params.veh_Clxh){
             historyGgpcList =  HistoryGgpc.findAllByVeh_Clxh(params.veh_Clxh)
        }else{
             historyGgpcList =  HistoryGgpc.findAllByVeh_Clxh(params.veh_Dpxh)
        }
        def needChangeFlag=1
        historyGgpcList.each{ historyGgpc->
            if(historyGgpc.veh_Ggpc==params.veh_Ggpc){
                needChangeFlag=0
            }
        }
        if(needChangeFlag==1){
            def historyGgpc = new HistoryGgpc()
            String curTime=DateUtil.getCurrentTime()
            historyGgpc.create_Time = curTime
            historyGgpc.veh_Ggpc = params.veh_Ggpc
            if(params.veh_Clxh){
                historyGgpc.veh_Clxh = params.veh_Clxh
            }else{
                historyGgpc.veh_Clxh = params.veh_Dpxh
            }
            historyGgpc.save(flush: true)
        }
        preCarStorage.properties.each {
            if('NULL'.equals(it.value?.toUpperCase())){
                preCarStorage."${it.key}"=''
            }
        }

        //处理图片路径
        if (params.veh_y45pic&&!params.veh_y45pic.contains('upload')){
            preCarStorage.veh_y45pic=(params.veh_y45pic && !"NULL".equals(params.veh_y45pic.toUpperCase()))?'/upload/images/carstorage/'+params.veh_y45pic+'.jpg':''   //右45度照片
        }
         if (params.veh_zhpic&&!params.veh_zhpic.contains('upload')){
             preCarStorage.veh_zhpic=(params.veh_zhpic && ! "NULL".equals(params.veh_zhpic.toUpperCase()))?'/upload/images/carstorage/'+params.veh_zhpic+'.jpg':''     //正后照片
         }
         if (params.veh_fhpic&&!params.veh_fhpic.contains('upload')) {
             preCarStorage.veh_fhpic=(params.veh_fhpic && ! "NULL".equals(params.veh_fhpic.toUpperCase()))?'/upload/images/carstorage/'+params.veh_fhpic+'.jpg':''     //防护照片
         }
         if (params.veh_pic1&&!params.veh_pic1.contains('upload')){
             preCarStorage.veh_pic1=(params.veh_pic1 && ! "NULL".equals(params.veh_pic1.toUpperCase()))?'/upload/images/carstorage/'+params.veh_pic1+'.jpg':''      //照片1
         }
         if (params.veh_pic2&&!params.veh_pic2.contains('upload')){
             preCarStorage.veh_pic2=(params.veh_pic2 && ! "NULL".equals(params.veh_pic2.toUpperCase()))?'/upload/images/carstorage/'+params.veh_pic2+'.jpg':''      //照片2
         }
        preCarStorage.createrId=params.userId
        preCarStorage.createTime= DateUtil.getCurrentTime()

        def m=[:]
        if (preCarStorage.save(flush: true)){
            m.result=true
        }else{
            m.result=false
        }
        m.instance=preCarStorage
        return m
    }
}
