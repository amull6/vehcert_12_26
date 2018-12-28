package cn.com.wz.vehcert.carstorage

import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.MessageUtil

/**
 * @Description 公告基础信息Service
 * @Create 2013-07-26 huxx
 */
class HarvestCarStorageService {
    def sqlToolService
    def logService

    def serviceMethod() {

    }
    /**
     * @Description 根据条件获取农装公告基础信息
     * @param params
     * @return result  result.totalCount表示记录总数，result.list表示取出的记录集合
     * @Create 2017-07-19 QJ
     * @Update 增加油耗类型和车辆状态信息查询条件 2015-06-09 zhuwei
     */
    def selectHarvestCarStorageByParams(params){
        def cel={
            if(params.veh_Clxh){
                eq('veh_Clxh',"${params.veh_Clxh}")
            }
            if (params.veh_Jxxlb){
                eq('veh_Jxxlb',"${params.veh_Jxxlb}")
            }
            if (params.veh_Fdjxh){
                eq('veh_Fdjxh',"${params.veh_Fdjxh}")
            }
            if (params.carStorageType){
               eq("carStorageType","${params.carStorageType}")
            }
            if (params.turnOff){
                eq("turnOff","${params.turnOff}")
            }
            if (params.car_storage_no1&&params.car_storage_no2){
                between("car_storage_no",params.car_storage_no1,params.car_storage_no2)
            }else{
                if (params.car_storage_no1&&!params.car_storage_no2){
                    ge("car_storage_no",params.car_storage_no1)
                }else if (!params.car_storage_no1&&params.car_storage_no2){
                    le("car_storage_no",params.car_storage_no2)
                }
            }
            order 'createTime','desc'
        }
//        def lst1=CarStorage.createCriteria().list(cel)
        def lst
        try{
            lst=HarvestCarStorage.createCriteria().list([max: params.max,offset: params.offset],cel)
        }catch(Exception e){
            println e.message
        }


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
            def u=User.get(it.createrId)
            m.createrName=u?.userDetail?.realName
            def uU=User.get(it.updaterId)
            if (uU){
                m.updaterName=uU?.userDetail?.realName
            }
            list.add(m)
        }
        def result=[total:lst.totalCount,rows:list]
        return result
    }

    /**
     * @Description 保存公告信息
     * 因为不是使用save（flush:true）立即保存到数据库，为了使数据保持完整性，这里需要使用事务，当一条导入不成功时就将数据全部回滚
     * @param lst 要保存到数据库中的数据对象列表
     * @param user当前登录用户对象
     * @param time 当前时间
     * @return  failList为保存失败的记录列表；count保存成功的记录数
     * @Create huxx 2013-08-11
     */
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
            def carStorage=new CarStorage()
            pct.each {
//                  def  car_storage_no=Integer.parseInt(it.value)
                    carStorage."${it.key}"=  it.value?(it.value.toString().toUpperCase()=="NULL"?'':it.value):''  //将NULL的内容保存为空
            }
            carStorage.createrId=user?.id
            carStorage.createTime=time
            carStorage.car_storage_no=curCount
            curCount+=1
            if(carStorage.save()){
                count+=1
                println(count)
            }else{
                def errorMessages=""
                def num=1
                //错误信息处理
                carStorage.errors.allErrors?.each{
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
     *@Description 农装公告唯一使用人工维护后使用的新的导入方法 ，上面的方法sqlSaveList已经弃用
     * @CreateTime 2015-04-01 zhuwei
     */
    def newSqlSaveList(paramsMap,request,grailsAttributes){
        try{
            double timeCount=0
            def lst=paramsMap.lst
            def remoteDB=paramsMap.remoteDB
            def carStorageMaxCount=paramsMap.carStorageMaxCount
            def time=paramsMap.time
            def user= paramsMap.user
            def existsCarStorageNoList=paramsMap.existsCarStorageNoList
            List<String> sql_deleteOptions = new ArrayList<String>();
            StringBuffer sb
            int count=0
            if(existsCarStorageNoList.size()>0){
                existsCarStorageNoList?.each{ existsCarStorageNo->
                    //根据公告唯一号查询启用状态的公告。公告唯一号为唯一主键，所以查出来的信息一定是一条
                  def  existsCarStorage=NzCarStorage.findByCar_storage_noAndTurnOff(existsCarStorageNo,'0')   //在导入service方法被调用前，已经对导入的Excle公告进行校验过，在这里再次检验公告为启用状态
                  if(existsCarStorage){//将Excle中已经存在的公告唯一号的公告先保存到TEMP表，但是不保存公告唯一号，在将这条公告删除
                      NzTemp t=new NzTemp()
                      t.wzh_carstorageID=existsCarStorage.id
                      t.wzh_type=1
                      t.insertTime=time
                      if(t.save(flush: true)){ //如果TEMP表中更新的记录保存成功，那么将这条已经存在的公告唯一号删除

                              if(count<999){             //in 中的最大表达式数为 1000
                                  if (count==0){
                                      sb=new StringBuffer("delete from wzh_Harvest_carStorage  where car_storage_no in (")
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
                                  sb=new StringBuffer("delete from wzh_Harvest_carStorage  where car_storage_no in ('${existsCarStorageNo?.toString()?.trim()}'")
                                  count=count-999
                              }
                              count+=1
                      }
                  }
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
                    StringBuffer sbf = new StringBuffer("insert into wzh_Harvest_carStorage ");
                    UUID uuid = UUID.randomUUID()
                    def id=uuid.toString().replace('-','')
                    StringBuffer title=new StringBuffer(" (ID,CREATER_ID,CREATE_TIME,turn_off")
                    StringBuffer values=new StringBuffer(" values('${id}','${user.id}','${time}','0'")
                    r.entrySet().each{v->
                        def noNeed=["carStorageType","veh_VinFourBit"]
                        if (!noNeed.contains(v.key) ){
                            title.append(",${v.key}")
                            values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
                        }else if(v.key=='carStorageType'){
                            title.append(",car_Storage_Type")
                            values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
                        }
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
    /**
     *@Description 校验公告唯一号是不是唯一的方法
     * @CreateTime 2015-04-02 zhuwei
     * @Update 将导入的公告的状态校验，只允许导入启用状态的公告 zhuwei 2015-06-04
     */
    def checkCarStorageNo(lst){
        def existsCarStorageNoList=[]
        def turnOffCarStorage=[]
        def emptyCount=0

        lst.each {
            //从导入的Excle 中的数据中找到公告唯一号查找有没有已经存在的公告
            //找到公告唯一号为空的excle 数据，返回有多少条公告唯一号为空的信息提示
            if(it.car_storage_no=='NULL'||it.car_storage_no=='null'||!it.car_storage_no){
                emptyCount=emptyCount+1
            }else{
                def findCarStorage=HarvestCarStorage.findAllByCar_storage_no(it.car_storage_no?.toString()?.trim()) //因为公告唯一号是唯一主键，所以查出来的数据只有一条
//                println(it.car_storage_no)
//                def bb=findCarStorage[0]            23
//                def aa=findCarStorage
//                def cc=bb.turnOff

                if(findCarStorage&&findCarStorage.size()>0){
                     if(findCarStorage[0].turnOff=='0'){
                         existsCarStorageNoList.add(it.car_storage_no?.toString()?.trim())   //启用状态的公告保存到existsCarStorageNoList中去
                     }else{
                         turnOffCarStorage.add(it.car_storage_no?.toString()?.trim())    //停用状态的公告不能在导入
                     }
                }
            }

        }
        return [emptyCount:emptyCount,existsCarStorageNoList:existsCarStorageNoList,turnOffCarStorage:turnOffCarStorage]
    }
}
