package cn.com.wz.vehcert.environment



class EnvirUplaodService {

    /**
     * @Description 根据参数获取重型柴油车的环保信息数据
     * @CreateTime 2016-12-20 by zhuwei
     * @param params
     * @return
     */
    def selectByParams(params){

        def cel={
            if(params.time1){
                def time1 = params.time1 +' 00:00:00'
                ge("create_time","${time1.toString()}")
            }
            if (params.time2) {
                def time2 = params.time2 +' 23:59:59'
                le("create_time","${time2.toString()}")
            }
//            上传状态 0未上传 1 已上传 2上传失败
                if (params.status) {
                 int status =Integer.parseInt(params.status)
                eq("status",status)
            }
            if(params.en_clsbdh){
                eq("en_clsbdh","${params.en_clsbdh}")

            }
            if (params.type){
                eq("type","${params.type}")
            }
        }
        def lst=EnvirUpload.createCriteria().list([max: params.max,offset: params.offset],cel)
        def list=[]
        lst?.each {
            def m=[:]

            it.properties.each {
                if(it.value==null){
                    it.value=''
                }
                m."${it.key}"=it.value
            }
            m.id="${it.id}"
            list.add(m)
        }
        def result=[total:lst.totalCount,rows:list]
        return result
    }

    /**
     * @Description 根据参数获取重型柴油车的环保信息数据
     * @CreateTime 2016-12-20 by zhuwei
     * @param params
     * @return
     */
    def selectByCondition(params){

        def cel={
            if(params.time1){
                def time1 = params.time1 +' 00:00:00'
                ge("create_time","${time1.toString()}")
            }
            if (params.time2) {
                def time2 = params.time2 +' 23:59:59'
                le("create_time","${time2.toString()}")
            }
//            上传状态 0未上传 1 已上传 2上传失败
            if (params.status) {
                int status =Integer.parseInt(params.status)
                eq("status",status)
            }
            if(params.en_clsbdh){
                eq("en_clsbdh","${params.en_clsbdh}")

            }
            if (params.type){
                eq("type","${params.type}")
            }

        }
        def lst=EnvirUpload.createCriteria().list([max: params.max,offset: params.offset],cel)
        return lst
    }
}
