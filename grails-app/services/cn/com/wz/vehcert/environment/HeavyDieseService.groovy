package cn.com.wz.vehcert.environment

import cn.com.wz.vehcert.MessageUtil

class HeavyDieseService {
    def sqlToolService
    def saveList(lst,user,time,request,grailsAttributes){
        def failList=[]
        def count=0
        def curCount=1
        lst?.each{ pct->
            //清空属性值的前后空格
            pct.entrySet()?.each{
                it.value=it.value?.toString()?.trim()
            }

            //将集合中float类型的值为null的转换为0
            //def floatList=['veh_Zs','veh_Wkc','veh_Wkk','veh_Wkg','veh_Hxnbc','veh_Hxnbk','veh_Hxnbg','veh_Zzl','veh_Edzzl','veh_Zbzl','veh_Zzllyxs','veh_Zgcs']
            //根据pct中值为carStorage的属性赋值，并处理字段为float类型的
            def heavyDiesel=new HeavyDiesel()
            pct.each {
//                  def  car_storage_no=Integer.parseInt(it.value)
                heavyDiesel."${it.key}"=  it.value?(it.value.toString().toUpperCase()=="NULL"?'':it.value):''  //将NULL的内容保存为空
            }
            heavyDiesel.creater_id=user?.userName
            heavyDiesel.create_time=time
            curCount+=1
            if(heavyDiesel.save()){
                count+=1
                println(count)
            }else{
                def errorMessages=""
                def num=1
                //错误信息处理
                heavyDiesel.errors.allErrors?.each{
                    if(it in org.springframework.validation.FieldError){
                        //将对象和属性国际化
                        it.arguments[1]="${MessageUtil.message(code: 'carstorage.label',request:request,grailsAttributes:grailsAttributes)}"
                        it.arguments[0]="${MessageUtil.message(code:"carstorage.${ it.arguments[0]}.label",request:request,grailsAttributes:grailsAttributes)}"
                        errorMessages+="${num}、"+MessageUtil.message(error: it,request:request,grailsAttributes:grailsAttributes)+";"
                    }
                }
                pct.errorMessages=errorMessages
                failList.add(pct)
            }
        }
        return [failList:failList,count:count]
    }
    /**
     * @Description 根据参数获取重型柴油车的环保信息数据
     * @CreateTime 2016-12-20 by zhuwei
     * @param params
     * @return
     */
    def selectByParams(params){
        def cel={
            if(params.en_clxh){
                like("en_clxh","%${params.en_clxh}%")
            }
            if (params.en_fdjxh) {
                like("en_fdjxh","%${params.en_fdjxh}%")
            }
            if (params.en_xxgkbh) {
                eq("en_xxgkbh","${params.en_xxgkbh}")
            }
            if (params.type){
                eq("type","${params.type}")
            }
            if (params.en_type){
                if(params.en_type=='HeavyDiesel') {
                    eq("type","0")
                } else if(params.en_type=='City'){
                    eq("type","1")
                }
            }

        }
        def lst=HeavyDiesel.createCriteria().list([max: params.max,offset: params.offset],cel)
        def list=[]
        lst?.each {
            def m=[:]

            it.properties.each {
                if(it.value==null){
                    it.value='NULL'
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
    def selectInfoByParams(params){
        def cel={
            if(params.en_clxh){
                like("en_clxh","%${params.en_clxh}%")
            }
            if (params.en_fdjxh) {
                like("en_fdjxh","%${params.en_fdjxh}%")
            }
            if (params.en_xxgkbh) {
                eq("en_xxgkbh","${params.en_xxgkbh}")
            }
            if (params.type){
                eq("type","${params.type}")
            }
        }
        def lst=HeavyDieselInfo.createCriteria().list([max: params.max,offset: params.offset],cel)
        def list=[]
        lst?.each {
            def m=[:]

            it.properties.each {
                if(it.value==null){
                    it.value='NULL'
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
     *@Description 查找已经存在的证书编号
     * @CreateTime 2016-12-02 zhuwei
     */
    def checkGkxxNo(lst){
        def existsNoList=[]
        def emptyCount=0

        lst.each {
            //从导入的Excle 中的数据中找到公告唯一号查找有没有已经存在的公告
            //找到信息公开编号为空的excle 数据，返回有多少条公告唯一号为空的信息提示
            if(it.en_xxgkbh=='NULL'||it.en_xxgkbh=='null'||!it.en_xxgkbh){
                emptyCount=emptyCount+1
            }else{
                def findHeavy_diesel=HeavyDiesel.findAllByEn_xxgkbh(it.en_xxgkbh?.toString()?.trim()) //因为信息公开编号唯一主键，所以查出来的数据只有一条
                if(findHeavy_diesel&&findHeavy_diesel.size()>0){
                    existsNoList.add(it.en_xxgkbh?.toString()?.trim())   //启用状态的公告保存到existsCarStorageNoList中去
                }
            }

        }
        return [emptyCount:emptyCount,existsNoList:existsNoList]

    }
    /**
     * @Description 使用SQL实现数据的导入
     * @param paramsMap
     * @param request
     * @param grailsAttributes
     * @return
     */
    def newSqlSaveList(paramsMap,request,grailsAttributes){
        try{
            double timeCount=0
            def lst=paramsMap.lst
            def remoteDB=paramsMap.remoteDB
            def carStorageMaxCount=paramsMap.carStorageMaxCount
            def time=paramsMap.time
            def user= paramsMap.user
            def existsNoList=paramsMap.existsNoList
            List<String> sql_deleteOptions = new ArrayList<String>();
            StringBuffer sb
            int count=0
            if(existsNoList.size()>0){
                existsNoList?.each{ existsCarStorageNo->

                            if(count<999){             //in 中的最大表达式数为 1000
                                if (count==0){
                                    sb=new StringBuffer("delete from wzh_heavy_diesel  where en_xxgkbh in (")
                                    sb.append("'${existsCarStorageNo?.toString()?.trim()}'")
                                }else{
                                    sb.append(",'${existsCarStorageNo?.toString()?.trim()}'")
                                }
                            }else{
                                sb.append(")")
                                sql_deleteOptions.add(sb);
                                println(sb)
                                def useTime =  sqlToolService.SynchronizationData (remoteDB,sql_deleteOptions,carStorageMaxCount);//满999条，将公告删除
                                timeCount+=useTime
                                sql_deleteOptions.clear()
                                sb=new StringBuffer("delete from wzh_heavy_diesel  where en_xxgkbh in ('${existsCarStorageNo?.toString()?.trim()}'")
                                count=count-999
                            }
                            count+=1
                }

                sb.append(")")
                sql_deleteOptions.add(sb);
                println(sb)
                def useTime =  sqlToolService.SynchronizationData (remoteDB,sql_deleteOptions,carStorageMaxCount);
                timeCount+=useTime
//                    println(timeCount)
                sql_deleteOptions.clear()
            }

            lst?.each{ pct->
                //清空属性值的前后空格
                pct.entrySet()?.each{
                    it.value=it.value?.toString()?.trim()
                }
            }

            if (lst?.size()>0){
                //插入Excle中有公告唯一号的公告信息
                List<String> sql_options = new ArrayList<String>();
                lst?.each{r->
                    //double类型去掉小数点，转化为String
                    StringBuffer sbf = new StringBuffer("insert into wzh_heavy_diesel ");
                    UUID uuid = UUID.randomUUID()
                    def id=uuid.toString().replace('-','')
                    StringBuffer title=new StringBuffer(" (ID,CREATER_ID,CREATE_TIME")
                    StringBuffer values=new StringBuffer(" values('${id}','${user.userName}','${time}'")
                    r.entrySet().each{v->
//                        def noNeed=["carStorageType","veh_VinFourBit"]
//                        if (!noNeed.contains(v.key) ){
                            title.append(",${v.key}")
                            values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
//                        }else if(v.key=='carStorageType'){
//                            title.append(",car_Storage_Type")
//                            values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
//                        }else if(v.key=='veh_VinFourBit'){
//                            title.append(",VEH_VIN_FOUR_BIT")
//                            values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
//                        }
                    }
                    sbf.append(title+")")
                    sbf.append(values+")")
                    sql_options.add(sbf);
                }
                if(sql_options.size()>0){
                    def insertUseTime =  sqlToolService.SynchronizationData (remoteDB,sql_options,carStorageMaxCount);
                    timeCount+=insertUseTime
                    sql_options.clear()
                }
            }
            return [time:time,timeCount:timeCount]
        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
        }

    }
}
