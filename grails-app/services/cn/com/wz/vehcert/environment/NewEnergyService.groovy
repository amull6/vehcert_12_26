package cn.com.wz.vehcert.environment

/**
 *
 * @param
 * @return
 */
class NewEnergyService {
    def sqlToolService
    /**
     *@Description 查找已经存在的证书编号
     * @CreateTime 2015-04-02 zhuwei
     * @Update 将导入的公告的状态校验，只允许导入启用状态的公告 zhuwei 2015-06-04
     */
    def checkCarStorageNo(lst){
        def existsNoList=[]
        def emptyCount=0

        lst.each {
            //从导入的Excle 中的数据中找到公告唯一号查找有没有已经存在的公告
            //找到信息公开编号为空的excle 数据，返回有多少条公开信息号为空的信息提示
            if(it.en_xxgkbh=='NULL'||it.en_xxgkbh=='null'||!it.en_xxgkbh){
                emptyCount=emptyCount+1
            }else{
                def findNew_Energy=NewEnergyCar.findAllByEn_xxgkbh(it.en_xxgkbh?.toString()?.trim())
                if(findNew_Energy&&findNew_Energy.size()>0){
                    existsNoList.add(it.en_xxgkbh?.toString()?.trim())
                }
            }

        }
        return [emptyCount:emptyCount,existsNoList:existsNoList]

    }
    /**
     * @Description 根据参数获取重型燃气车的环保信息数据
 * @CreateTime 2016-12-21 by Qj
     * @param params
     * @return
     */
    def selectByParams(params){
        def cel={
            if(params.en_clxh){
                like("en_clxh","%${params.en_clxh}%")
            }
            if (params.en_ddjxh) {
                like("en_ddjxh","%${params.en_ddjxh}%")
            }
            if (params.en_xxgkbh) {
                eq("en_xxgkbh","${params.en_xxgkbh}")
            }
        }
        def lst=NewEnergyCar.createCriteria().list([max: params.max,offset: params.offset],cel)
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
                            sb=new StringBuffer("delete from WZH_NEW_ENERGY_CAR  where en_xxgkbh in (")
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
                        sb=new StringBuffer("delete from WZH_NEW_ENERGY_CAR  where en_xxgkbh in ('${existsCarStorageNo?.toString()?.trim()}'")
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
                    StringBuffer sbf = new StringBuffer("insert into WZH_NEW_ENERGY_CAR ");
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
