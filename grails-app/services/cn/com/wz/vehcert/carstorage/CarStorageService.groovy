package cn.com.wz.vehcert.carstorage

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.system.user.UserDetail
import cn.com.wz.vehcert.MessageUtil
import cn.com.wz.vehcert.coc.CocCarStoragePrint

import java.text.DecimalFormat

/**
 * @Description 公告基础信息Service
 * @Create 2013-07-26 huxx
 */
class CarStorageService {
    def sqlToolService
    def logService

    def serviceMethod() {

    }
    /**
     * @Description 根据条件获取公告基础信息
     * @param params
     * @return result  result.totalCount表示记录总数，result.list表示取出的记录集合
     * @Create 2013-07-26 huxx
     * @Update 增加油耗类型和车辆状态信息查询条件 2015-06-09 zhuwei
     */
    def selectCarStorageByParams(params){
        def cel={
            if(params.veh_Clxh){
                eq('veh_Clxh',"${params.veh_Clxh}")
            }
            if (params.veh_Clmc){
                like('veh_Clmc',"%${params.veh_Clmc}%")
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

            if (params.veh_Fdjxh){
                eq("veh_Fdjxh","${params.veh_Fdjxh}")
            }
            if (params.veh_Ltgg){
                eq("veh_Ltgg","${params.veh_Ltgg}")
            }
            if (params.veh_Hxnbc){
                eq("veh_Hxnbc","${params.veh_Hxnbc}")
            }
            if (params.veh_Hxnbk){
                eq("veh_Hxnbk","${params.veh_Hxnbk}")
            }
            if (params.veh_Hxnbg){
                eq("veh_Hxnbg","${params.veh_Hxnbg}")
            }
            if (params.veh_Jsszcrs){
                eq("veh_Jsszcrs","${params.veh_Jsszcrs}")
            }
            if (params.veh_Gbthps){
                eq("veh_Gbthps","${params.veh_Gbthps}")
            }
            if (params.veh_Jsslx){
                eq("veh_Jsslx","${params.veh_Jsslx}")
            }
            if (params.veh_Zj){
                eq("veh_Zj","${params.veh_Zj}")
            }
            if (params.veh_Wkc){
                eq("veh_Wkc","${params.veh_Wkc}")
            }
            if (params.veh_Wkk){
                eq("veh_Wkk","${params.veh_Wkk}")
            }
            if (params.veh_Wkg){
                eq("veh_Wkg","${params.veh_Wkg}")
            }
            if (params.veh_Qlj){
                eq("veh_Qlj","${params.veh_Qlj}")
            }
            if (params.veh_Hlj){
                eq("veh_Hlj","${params.veh_Hlj}")
            }


            if(params.veh_Clztxx){
                eq("veh_Clztxx","${params.veh_Clztxx}")
            }
            if(params.veh_Yhlx=='1'){
                isNotNull('veh_Yhlx')  //有油耗类型
            }
            if(params.veh_Yhlx=='0'){
                isNull('veh_Yhlx')
            }
            order 'createTime','desc'
            order 'car_storage_no','desc'
        }
//        def lst1=CarStorage.createCriteria().list(cel)
        def lst
        try{
            lst=CarStorage.createCriteria().list([max: params.max,offset: params.offset],cel)
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
     *
     * @param lst
     * @param user
     * @param time
     * @param request
     * @param grailsAttributes
     * @Description SQL导入公告信息
     * @CreateTime 2014-07-25 zhuwei
     * @updateTime 2014-08-09 zhuwei
     * @update 公告唯一号修改为：四位年份+流水码
     * @return
     */
    def sqlSaveList(paramsMap,request,grailsAttributes){
        double timeCount=0
        def lst=paramsMap.lst
        def remoteDB=paramsMap.remoteDB
        def carStorageMaxCount=paramsMap.carStorageMaxCount
        def time=paramsMap.time
        def user= paramsMap.user
        def maxCarStorageNo_S= paramsMap.maxCarStorageNo_S
        def numb=maxCarStorageNo_S.substring(4)    //公告唯一号流水码
        def startChar=DateUtil.getCurrentTime('yyyy')  //获得当前年份
        lst?.each{ pct->
            //清空属性值的前后空格
            pct.entrySet()?.each{
                it.value=it.value?.toString()?.trim()
            }
        }
            def deleteCarstorageNo=[]
            def deleteCarstorage=[]

            lst.each {
                if(it.car_storage_no!='NULL'){
                    deleteCarstorageNo.add(it.car_storage_no?.toString()?.trim())  //获取存在公告唯一号的公告信息，先进行删除、后插入操作
                    println(it.car_storage_no?.toString()?.trim())
                    deleteCarstorage.add(it)
                }
            }
            //删除存在公告唯一号存在的公告记录
            if (deleteCarstorage?.size()>0){
                List<String> sql_options = new ArrayList<String>();
                StringBuffer sb=new StringBuffer("delete from wzh_carStorage  where car_storage_no in (")
                int count=0
                deleteCarstorageNo?.each{
                    if(count<999){              // 批处理中出现错误: ORA-01795: 列表中的最大表达式数为 1000
                        if (count==0){
                            sb.append("'${it?.toString()?.trim()}'")
                        }else{
                            sb.append(",'${it?.toString()?.trim()}'")
                        }

                    }else{
                        sb.append(")")
                        sql_options.add(sb);
//                        CarStorage.executeUpdate(sb.toString())
                        def useTime =  sqlToolService.SynchronizationData (remoteDB,sql_options,carStorageMaxCount);
                        timeCount+=useTime
//                        println(timeCount)
                        sql_options.clear()
                        sb=new StringBuffer("delete from wzh_carStorage  where car_storage_no in ('${it}'")
                        count=count-999
                    }
                    count+=1
                }
                sb.append(")")
//                println(sb)
                sql_options.add(sb);
//                CarStorage.executeUpdate(sb.toString())
                        def useTime =  sqlToolService.SynchronizationData (remoteDB,sql_options,carStorageMaxCount);
                        timeCount+=useTime
                        println(timeCount)
                        sql_options.clear()
                }

        if (lst?.size()>0){
            //插入Excle中有公告唯一号的公告信息
            List<String> sql_options = new ArrayList<String>();
            lst?.each{r->
                //double类型去掉小数点，转化为String
                DecimalFormat df = new DecimalFormat( "0");
                StringBuffer sbf = new StringBuffer("insert into wzh_carStorage ");
                UUID uuid = UUID.randomUUID()
                def id=uuid.toString().replace('-','')
                StringBuffer title=new StringBuffer(" (ID,CREATER_ID,CREATE_TIME")
                StringBuffer values=new StringBuffer(" values('${id}','${user.id}','${time}'")
                r.entrySet().each{v->
                    if(v.key=='car_storage_no'&&v.value.toString().toUpperCase()=="NULL"){  //如果公告唯一号是NULL，生成唯一号插入
                        if(maxCarStorageNo_S&&maxCarStorageNo_S.startsWith(startChar)){
                            numb=df.format(Double.parseDouble(numb)+1)   //上传生成的公告唯一码是当前年份，那么只对流水号+1操作
                        }else{                                                               //如果是新年份生成的生成的公告唯一号，流水码从1开始累加
                            numb='1'
                        }
                        maxCarStorageNo_S=startChar+numb
//                        print(maxCarStorageNo_S)
                        title.append(",${v.key}")
                        values.append(",'${maxCarStorageNo_S}'")
                    }else if(v.key=='car_storage_no'&&v.value.toString().toUpperCase()!="NULL"){   //已有的唯一号的，直接插入到数据库
                        title.append(",${v.key}")
                        values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
                    }
                    def noNeed=["car_storage_no","carStorageType","veh_VinFourBit"]
                    if (!noNeed.contains(v.key) ){
                        title.append(",${v.key}")
                        values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
                    }else if(v.key=='carStorageType'){
                        title.append(",car_Storage_Type")
                        values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
                    }else if(v.key=='veh_VinFourBit'){
                        title.append(",VEH_VIN_FOUR_BIT")
                        values.append(",'${ v.value?(v.value.toString().toUpperCase()=="NULL"?'':v.value):''}'")
                        }
                }
                sbf.append(title+")")
                sbf.append(values+")")
//                println(sbf)
                sql_options.add(sbf);
            }
            if(sql_options.size()>0){
                def useTime =  sqlToolService.SynchronizationData (remoteDB,sql_options,carStorageMaxCount);
                timeCount+=useTime
                println(timeCount)
                sql_options.clear()
            }
        }
        println(maxCarStorageNo_S)
        return [time:time,maxCarStorageNo_S:maxCarStorageNo_S,timeCount:timeCount]
    }
    /**
     *@Description 公告唯一使用人工维护后使用的新的导入方法 ，上面的方法sqlSaveList已经弃用
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
                  def  existsCarStorage=CarStorage.findByCar_storage_noAndTurnOff(existsCarStorageNo,'0')   //在导入service方法被调用前，已经对导入的Excle公告进行校验过，在这里再次检验公告为启用状态
                  if(existsCarStorage){//将Excle中已经存在的公告唯一号的公告先保存到TEMP表，但是不保存公告唯一号，在将这条公告删除
                      Temp t=new Temp()
                      t.wzh_carstorageID=existsCarStorage.id
                      t.wzh_type=1
                      t.insertTime=time
                      if(t.save(flush: true)){ //如果TEMP表中更新的记录保存成功，那么将这条已经存在的公告唯一号删除

                              if(count<999){             //in 中的最大表达式数为 1000
                                  if (count==0){
                                      sb=new StringBuffer("delete from wzh_carStorage  where car_storage_no in (")
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
                                  sb=new StringBuffer("delete from wzh_carStorage  where car_storage_no in ('${existsCarStorageNo?.toString()?.trim()}'")
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
                    StringBuffer sbf = new StringBuffer("insert into wzh_carStorage ");
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
                        }else if(v.key=='veh_VinFourBit'){
                            title.append(",VEH_VIN_FOUR_BIT")
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
                def findCarStorage=CarStorage.findAllByCar_storage_no(it.car_storage_no?.toString()?.trim()) //因为公告唯一号是唯一主键，所以查出来的数据只有一条
//                println(it.car_storage_no)
//                def bb=findCarStorage[0]
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
