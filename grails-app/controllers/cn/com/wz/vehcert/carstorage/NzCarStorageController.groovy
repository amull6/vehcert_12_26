package cn.com.wz.vehcert.carstorage

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User
import dms.SynAffiche
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.event.ExcelReaderUtil
import parent.poi.event.IRowReader
import parent.poi.event.RowReader
import grails.converters.JSON
import cn.com.wz.parent.base.BaseController
import groovy.sql.Sql
import org.springframework.core.io.support.PropertiesLoaderUtils
import parent.poi.ExcelUtils

import java.text.DecimalFormat;

/**
 * @Description 公告基础信息维护
 * @Create 2013-07-26 huxx                                                    c
 * @Update  huxx 2013-10-19 公告信息的更新时间以及创建时间使用数据库的默认当前时间，temp的inserttime也使用数据库的当前时间
 */
class NzCarStorageController extends BaseController {
    def dmsSynService
    def sqlToolService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def carStorageService
    def nzCarStorageService
        def exportService
        def dataSource
        def sqlService
        def index() {
            render (view:'/cn/com/wz/vehcert/carstorage/nzcarstorage/nzCarStorageList',model: params)
        }
        /**
         * @Description 根据条件查询出满足条件的农装公告记录
         * @return
         * @Create 2017-07-18 QJ
         */
        def nzSearch(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def result=nzCarStorageService.selectNzCarStorageByParams(params)
        render result as JSON
    }

    /**
     * @Description 进入导入页面
     * @return
     * @Create 2013-07-29 huxx
     */
    def importPage(){
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'nzCarStorage',action: 'imp')}",tabId:params.tabId])
    }
    /**
     * @Description 公告信息导入
     * @param filePath
     * @return
     * @Create 2013-07-29 huxx
     * @Update 2014-07-25 zhuwei
     * @update 改用SQL导入公告
     */
    def imp(String filePath){

        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取文件的相对路径(不包含文件名)
            def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==3){
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
            }
        }

        List<String> labels=new ArrayList<String>();
        def lst=[]
        def totalCount=0  //读取记录总数
        def count=0                 //实际存储成功的记录数
        def failList=[]             //保存失败记录
        def checkResult
        StringBuilder errorMessages=new StringBuilder()
        def timeCount=0   //记录导入使用的时间
        def emptyCount     //记录导入公告中公告唯一码为空的个数
        def existsCarStorageNoList  //记录重复的公告唯一码的数列
        def turnOffCarStorage   //导入公告中已经停用的公告数组

        try{
            //获得服务器信息，这里获取程序本地的数据库，而非远程数据库
            def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
            def remoteUrl= dsp.getProperty("url")
            def remoteUserName=dsp.getProperty("userName")
            def remotePassword= dsp.getProperty("password")
            def remoteDriverClassName=dsp.getProperty("driverClassName")
            if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
                return render("服务器信息配置不完整！")
            }
            def remoteDB=groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
            def carStorageMaxCount=Integer.parseInt(grailsApplication.config.client.impCarStorage.maxCount?grailsApplication.config.client.impCarStorage.maxCount:'0')
            //获取Excle内容信息
            labels.add('veh_Clxh');
            labels.add('veh_Fdjxh');
            labels.add('veh_Jxdl');
            labels.add('veh_Rllx');
            labels.add('veh_Jxxlb');
            labels.add('veh_Zycs');
            labels.add('veh_Pfjd');

            labels.add('veh_Lx');
            labels.add('veh_Gl');
            labels.add('veh_Pfbz');
            labels.add('veh_Jsys');
            labels.add('veh_Zxczxs');
            labels.add('veh_Zcrs');
            labels.add('veh_Lzs');

            labels.add('veh_Zj');
            labels.add('veh_Lts');
            labels.add('veh_Ltgg');
            labels.add('veh_Qlj');
            labels.add('veh_Hlj');
            labels.add('veh_Lds');
            labels.add('veh_Ldgg');
            labels.add('veh_Gj');
            labels.add('veh_Wkc');

            labels.add('veh_Wkk');
            labels.add('veh_Wkg');
            labels.add('veh_Bdqyl');
            labels.add('veh_Zxsyzl');
            labels.add('veh_Zdyyzzl');

            labels.add('veh_Lhqxs');
            labels.add('veh_Qpzxs');
            labels.add('veh_Hpzxs');

            labels.add('veh_Jzxs');
            labels.add('veh_Ddlj');
            labels.add('veh_Jssxs');
            labels.add('veh_Bhxs');
            labels.add('veh_Scqymc');
            labels.add('veh_Pp');
            labels.add('veh_Zxbz');
            labels.add('veh_Scqydz');
            labels.add('veh_Lxfs');
            labels.add('is_Exp');
            labels.add('car_storage_type');
            labels.add('car_storage_no');
            def map=[:];
            map.put('startRow',1) ;//从第二行开始
            IRowReader reader = new RowReader(labels,map);
            lst=ExcelReaderUtil.readExcel(reader, realPath);

            totalCount=lst?.size()  //读取记录总数
            def user=getCurrentUser()
            def time=DateUtil.getCurrentTime()
            def paramsMap=[:]
            paramsMap.lst=lst
            paramsMap.remoteDB=remoteDB
            paramsMap.carStorageMaxCount=carStorageMaxCount
            paramsMap.time=time
            paramsMap.user=user
            if(totalCount>0){
              //对list里面的的公告唯一码进行校验，如果没有查到相同公告唯一码的公告，就做公告导入处理
                println('在这里面进行公告唯一号校验')
                checkResult=nzCarStorageService.checkCarStorageNo(lst)   //不再校验公告唯一号的唯一性
                emptyCount= checkResult.emptyCount
                turnOffCarStorage=checkResult.turnOffCarStorage
                paramsMap.existsCarStorageNoList=checkResult.existsCarStorageNoList
                if(emptyCount==0&&turnOffCarStorage.size()==0){ // 导入时不再校验公告唯一号是不是唯一的，只校验公告是不是空的、公告是不是已经停用
                    def m=nzCarStorageService.newSqlSaveList(paramsMap,request,grailsAttributes)
                    timeCount=m.timeCount
                    count = lst.size()
                }else{
                    if(turnOffCarStorage.size()>0){
                        errorMessages.append("公告库中已经存在公告唯一号为${turnOffCarStorage}的停用公告，")
                    }
                    if(emptyCount>0){
                        errorMessages.append("导入公告中有${emptyCount}条公告唯一号为空，")
                    }
                }
            }

        }catch(Exception e){
            totalCount = -1
            count = 0
            errorMessages.append("数据插入时出现问题！数据全部回滚！错误原因：${e.cause}")
            e.cause?e.cause.printStackTrace():e.printStackTrace()
        }finally{
            lst.clear()
            labels.clear()
            failList.clear()
            def result=[realCount:count,count:totalCount,errorMessages:errorMessages,timeCount:timeCount]
            render result as JSON
        }

    }

    /**
     * @Description 跳转到新增农装公告页面
     * @return
     * @create 2017-07-19 QJ
     * @update
     */
    def toCreate(){
        render (view:'/cn/com/wz/vehcert/carstorage/nzcarstorage/nzCreate')
    }

    /**
     * @Description 保存公告信息
     * @return
     * @create 2017-07-28 QJ
     * @update
     */
    def save(){
        def nzCarStorage=new NzCarStorage(params)
        nzCarStorage.createrId=getCurrentUser()?.id
        nzCarStorage.createTime=DateUtil.getCurrentTime()
        if (!nzCarStorage.save(flash:true)){
            render (view:'/cn/com/wz/vehcert/carstorage/nzcarstorage/nzCreate',model: [nzCarStorage:nzCarStorage])
            return
        }else{
//            def writeInDic=carStorage.car_storage_no
//            def  maxCarStorageNoIns=DictionaryValue.findByCode('maxCarStorageNo')
//            println(writeInDic)
//            maxCarStorageNoIns.setValue1(writeInDic)
//            servletContext.setAttribute("dic_maxCarStorageNo",maxCarStorageNoIns)
            flash.message="保存成功！"
            redirect(action: "show", id: nzCarStorage.id)
        }
    }

    /**
     * @Description 跳转到更新页面
     * @return
     * @create 2013-07-28 huxx
     * @Update 2017-7-20QJ农装
     */
    def toUpdate(){
        def nzCarStorage=NzCarStorage.get(params.id)
        render (view:'/cn/com/wz/vehcert/carstorage/nzcarstorage/nzEdit',model: [nzCarStorage: nzCarStorage])
    }

    def update(){
        def nzCarStorage=NzCarStorage.get(params.id)
        if (params.version&&params.version<nzCarStorage.version){
            flash.message='记录已过期！'
            return render (view:'/cn/com/wz/vehcert/carstorage/nzcarstorage/nzEdit',model: [nzCarStorage:nzCarStorage])
        }
        String curTime=DateUtil.getCurrentTime()
        nzCarStorage.properties=params
        nzCarStorage.updaterId=getCurrentUser()?.id
        nzCarStorage.updateTime=curTime

        if (!nzCarStorage.save(flush: true)){
            return render (view:'/cn/com/wz/vehcert/carstorage/nzcarstorage/nzEdit',model: [nzCarStorage:nzCarStorage])
        }else{
            NzTemp t=new NzTemp()
            t.wzh_carstorageID=nzCarStorage.id
            t.wzh_type=0
            t.insertTime=curTime
//            t.car_storage_no=carStorage.car_storage_no
            t.save(flush: true)
            flash.message="更新成功！"
            redirect(action: "show", id: nzCarStorage.id)
        }
    }

    def show(){
        def nzCarStorage=NzCarStorage.get(params.id)
        render (view:'/cn/com/wz/vehcert/carstorage/nzcarstorage/nzShow',model: [nzCarStorage:nzCarStorage])
    }

    /**
     * @Description 使用ajax删除农装记录
     *@param params
     *@return 操作信息
     *@Auther huxx
     *@createTime 2012-9-14 上午9:46:18
     * @UpdateTime QJ农装记录n
     */
    def jsonDelete(){
        def ids=params.deleteIds.split("_")
        def message=""
        def count=0
        ids?.each{
            def nzCarStorage= NzCarStorage.get(it)
            def car_storage_no=nzCarStorage.car_storage_no
           if(nzCarStorage?.delete(flush: true)){
               count+=1
               message+="【${nzCarStorage.veh_Clxh}】删除失败！_"
           }else{
               NzTemp t=new NzTemp()
               t.wzh_carstorageID=it
               t.wzh_type=0
               t.insertTime=DateUtil.getCurrentTime()
               t.car_storage_no=car_storage_no
               t.save()
           }
        }
        if (!count){
            message="删除成功！"
        }
        render message
    }
    /**
     * @Description 逻辑删除，停用公告
     *@param params
     *@return 操作信息
     *@Auther zhuwei
     *@createTime 2017-7-20   QJ　停用农庄公告
     */
    def disableCarStorage(){
        def ids=params.deleteIds.split("_")
        def message=""
        def count=0
        int turnOff=0

        ids?.each{   //先查看勾选的记录中有没有已经停用的公告
            def nzCarStorage = NzCarStorage.get(it)
            if(nzCarStorage.turnOff=='1'){
                turnOff=turnOff+1
            }
        }
       if(turnOff>0){     //如果勾选记录中有公告处于停用公告，给予不能重复停用提示
           message="勾选记录中有已停用公告，不能重复停用！"
       }else{       //如果勾选的记录中没有停用的记录，做停用处理
           ids?.each{
               def nzCarStorage= NzCarStorage.get(it)
               def car_storage_no=nzCarStorage.car_storage_no
               //对停用公告做逻辑删除
               nzCarStorage.setTurnOff('1') //停用标识   0：启用 1：停用
               nzCarStorage.setTurnOffTime(DateUtil.getCurrentTime())
               if(!nzCarStorage?.save(flush: true)){
                   count+=1
                   message+="【${nzCarStorage.veh_Clxh}】停用失败！_"
               }else{
                   NzTemp t=new NzTemp()
                   t.wzh_carstorageID=it
                   t.wzh_type=0
                   t.insertTime=DateUtil.getCurrentTime()
                   t.car_storage_no=car_storage_no
                   t.save()
               }
           }
           if (!count){
               message="停用成功！"
           }
       }

        render message
    }
    /**
     * @Description 依据查询条件进行删除
     *@param params
     *@return 操作信息
     *@Auther liuly
     *@createTime 2013-08-14
     * @Update 2013-11-02 huxx 添加了删除条件公告类型
     * @update 2014_08_11 zhuwei 删除时在temp表写入公告唯一号
     * @update 2017_07_20 QJ 条件删除农装公告
     */
    def jsonDeleteSearch(){
        def veh_Clxh=''
        def veh_Jxxlb=''
        if (params.veh_Jxxlb) {
            veh_Jxxlb=params.veh_Jxxlb
        }
        if (params.veh_Clxh) {
            veh_Clxh=params.veh_Clxh
        }

        def message=""
        NzCarStorage.withTransaction {trans->
            try{
                ////查询经销商所拥有的合格证信息
                StringBuffer sql_sbfS = new StringBuffer("SELECT ID,car_storage_no FROM wzh_NZcarStorage ");
                if(params.veh_Jxxlb){
                     if(params.veh_Clxh){
                         sql_sbfS.append("  WHERE  veh_Jxxlb like '%"+veh_Jxxlb+"%' AND veh_Clxh = '"+veh_Clxh+"'");
                     }else{
                         sql_sbfS.append("  WHERE  veh_Jxxlb like '%"+veh_Jxxlb+"%'");
                     }
                    if (params.car_storage_type){
                        sql_sbfS.append("  and car_storage_type='${params.car_storage_type}'")
                    }
                }else{
                    if(params.veh_Clxh){
                        sql_sbfS.append("  WHERE  veh_Clxh = '"+veh_Clxh+"'");
                        if (params.car_storage_type){
                            sql_sbfS.append("  and car_storage_type='${params.car_storage_type}'")
                        }
                    }else{
                        if (params.car_storage_type){
                            sql_sbfS.append("  where car_storage_type='${params.car_storage_type}'")
                        }
                    }
                }

                def listS =  sqlService.executeList(sql_sbfS.toString())

                if (listS){
                    StringBuffer sql_sbf = new StringBuffer("DELETE FROM wzh_NZcarStorage ");
                    if(params.veh_Jxxlb){
                        if(params.veh_Clxh){
                            sql_sbf.append("  WHERE  veh_Jxxlb like '%"+veh_Jxxlb+"%' AND veh_Clxh = '"+veh_Clxh+"'");
                        }else{
                            sql_sbf.append("  WHERE  veh_Jxxlb like '%"+veh_Jxxlb+"%'");
                        }
                        if (params.car_storage_type){
                            sql_sbfS.append("  and car_storage_type='${params.car_storage_type}'")
                        }
                    }else{
                        if(params.veh_Clxh){
                            sql_sbf.append("  WHERE  veh_Clxh = '"+veh_Clxh+"'");
                            if (params.car_storage_type){
                                sql_sbfS.append("  and car_storage_type='${params.car_storage_type}'")
                            }
                        }else{
                            if (params.car_storage_type){
                                sql_sbf.append("  where car_storage_type='${params.car_storage_type}'")
                            }
                        }
                    }
                    def list =  sqlService.executeUpdate(sql_sbf.toString())

                    if (list>0){
                        message=list+"条删除成功！"

                        listS.each {
                            NzTemp t=new NzTemp()
                            t.wzh_carstorageID=it.ID
                            t.car_storage_no=it?.car_storage_no
                            t.wzh_type=0
                            t.insertTime=DateUtil.getCurrentTime()
                            t.save()
                        }
                    }else{
                        message = '删除失败！'
                    }
                }else{
                    message = "0条记录删除成功！"
                }

            }catch (Exception e){
                trans.setRollbackOnly()
                message = "删除失败！${e.message}"
            }finally{
                render message
            }
        }


    }
    /**
     * @Description 依据查询条件进行公告停用，使用逻辑删除
     *@param params
     *@return 操作信息
     *@Auther zhuwei
     *@createTime QJ 2017-07-20 删除农装
     */
    def disableSearchCarStorage(){
        def aaa=params
        def veh_Clxh=''
        def veh_Jxxlb=''
        def  turnOff=''
        def aa=params
        def turn_off_time=DateUtil.getCurrentTime()
        if (params.veh_Jxxlb) {
            veh_Jxxlb=params.veh_Jxxlb
        }
        if (params.veh_Clxh) {
            veh_Clxh=params.veh_Clxh
        }
        if(params.turnOff){
            turnOff=params.turnOff
        }

        def message=""
        NzCarStorage.withTransaction {trans->
            try{
                ////查询经销商所拥有的合格证信息
                def need=false
                StringBuffer sql_sbfS = new StringBuffer("SELECT ID,car_storage_no,turn_Off FROM wzh_NZcarStorage ");
                if(params.veh_Jxxlb){
                       sql_sbfS.append("  WHERE  veh_Jxxlb= '"+veh_Jxxlb+"' ");
                       need=true
                }

                if(params.veh_Clxh){
                    if (need){
                        sql_sbfS.append("  and   veh_Clxh = '"+veh_Clxh+"'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Clxh= '"+veh_Clxh+"'");
                        need=true
                    }
                }

                if(params.turnOff){
                    if (need){
                        sql_sbfS.append("  and   turn_Off = '"+turnOff+"'");
                    }else{
                        sql_sbfS.append("  WHERE  turn_Off= '"+turnOff+"'");
                        need=true
                    }
                }

                if(params.car_storage_type){
                    if (need){
                        sql_sbfS.append("  and   CAR_STORAGE_TYPE ='${params.car_storage_type}'");
                    }else{
                        sql_sbfS.append("  WHERE  CAR_STORAGE_TYPE='${params.car_storage_type}'");
                        need=true
                    }
                }

                if(params.car_storage_no1&&params.car_storage_no2){
                    if (need){
                        sql_sbfS.append("  and   car_storage_no> = '${params.car_storage_no1}' and  car_storage_no<= '${params.car_storage_no2}' ");
                    }else{
                        sql_sbfS.append("  WHERE car_storage_no> = '${params.car_storage_no1}' and  car_storage_no<= '${params.car_storage_no2}' ");
                        need=true
                    }
                }else{
                    if (params.car_storage_no1&&!params.car_storage_no2){
                        if (need){
                            sql_sbfS.append("  and   car_storage_no> = '${params.car_storage_no1}' ");
                        }else{
                            sql_sbfS.append("  WHERE car_storage_no> = '${params.car_storage_no1}' ");
                            need=true
                        }
                    }else if (!params.car_storage_no1&&params.car_storage_no2){
                        if (need){
                            sql_sbfS.append("  and car_storage_no<= '${params.car_storage_no2}' ");
                        }else{
                            sql_sbfS.append("  WHERE   car_storage_no<= '${params.car_storage_no2}' ");
                            need=true
                        }
                    }
                }

//                println(sql_sbfS)

                def listS =  sqlService.executeList(sql_sbfS.toString())
                int turnOffCount=0
                if (listS){        //查找已经停用公告的记录数量
                    listS.each {
//                        println(it[2])
                        if( it[2]=='1'){
                            turnOffCount=turnOffCount+1
                        }
                    }
                }
//                println(turnOffCount)
                if(turnOffCount>0){ //如果有已停用的公告，给予不能重复停用提示
                    message="筛选记录中有已停用公告，不能重复停用！"
                }else{     //如果没有有已停用的公告，进行update公告数据库
                    if (listS){
                        StringBuffer sql_sbf = new StringBuffer("update  wzh_NZcarStorage set turn_Off='1' , turn_off_time='"+turn_off_time+"'");
                        def jion=false
                        if(params.veh_Jxxlb){
                            sql_sbf.append("  WHERE  veh_Jxxlb ='"+veh_Jxxlb+"' ");
                            jion=true
                        }

                        if(params.turnOff){
                            if (jion){
                                sql_sbf.append("  and   turn_Off = '"+turnOff+"'");
                            }else{
                                sql_sbf.append("  WHERE  turn_Off= '"+turnOff+"'");
                                jion=true
                            }
                        }

                        if(params.veh_Clxh){
                            if (jion){
                                sql_sbf.append("  and   veh_Clxh = '"+veh_Clxh+"'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Clxh= '"+veh_Clxh+"'");
                                jion=true
                            }
                        }

                        if(params.car_storage_no1&&params.car_storage_no2){
                            if (jion){
                                sql_sbf.append("  and   car_storage_no> = '${params.car_storage_no1}' and  car_storage_no<= '${params.car_storage_no2}' ");
                            }else{
                                sql_sbf.append("  WHERE car_storage_no> = '${params.car_storage_no1}' and  car_storage_no<= '${params.car_storage_no2}' ");
                                jion=true
                            }
                        }else{
                            if (params.car_storage_no1&&!params.car_storage_no2){
                                if (jion){
                                    sql_sbf.append("  and   car_storage_no> = '${params.car_storage_no1}' ");
                                }else{
                                    sql_sbf.append("  WHERE car_storage_no> = '${params.car_storage_no1}' ");
                                    jion=true
                                }
                            }else if (!params.car_storage_no1&&params.car_storage_no2){
                                if (jion){
                                    sql_sbf.append("  and car_storage_no<= '${params.car_storage_no2}' ");
                                }else{
                                    sql_sbf.append("  WHERE   car_storage_no<= '${params.car_storage_no2}' ");
                                    jion=true
                                }
                            }
                        }
                        if(params.car_storage_type){
                            if (jion){
                                sql_sbf.append("  and   CAR_STORAGE_TYPE ='${params.car_storage_type}'");
                            }else{
                                sql_sbf.append("  WHERE  CAR_STORAGE_TYPE='${params.car_storage_type}'");
                                jion=true
                            }
                        }

//                        println(sql_sbf)
                        def list =  sqlService.executeUpdate(sql_sbf.toString())

                        if (list>0){
                            message=list+"条停用成功！"

                            listS.each {
                                NzTemp t=new NzTemp()
                                t.wzh_carstorageID=it.ID
                                t.car_storage_no=it?.car_storage_no
                                t.wzh_type=0
                                t.insertTime=DateUtil.getCurrentTime()
                                t.save()
                            }
                        }else{
                            message = '停用失败！'
                        }
                    }else{
                        message = "0条记录停用成功！"
                    }
                }


            }catch (Exception e){
                trans.setRollbackOnly()
                message = "停用失败！${e.message}"
            }finally{
                render message
            }
        }


    }
    /**
     *@Description 农装公告信息查询页面导出
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-28 liuly
     *@UpdateBy zhuwei 2014_07_25
     *@UpdateBy QJ 2014_07_19
     *@导出公告内容添加公告唯一号导出农装公告
     */
    def exportSearch(){
        def rows =[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("分解后公告信息"), "UTF-8")
            def filename = ""
            def userAgent = request.getHeader("User-Agent")
            if (userAgent =~ /MSIE [4-8]/) {
                // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename=\"${encodedFilename}."+params.extension+"\""
            }
            else {
                // Use RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename*=UTF-8''${encodedFilename}."+params.extension
            }
            response.setHeader("Content-disposition", "attachment;${filename}");
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            Map labels = ["veh_Clxh"        : "型号名称", "veh_Fdjxh": "发动机型号", "veh_Jxdl": "机械大类", "veh_Rllx": "燃料类型", "veh_Jxxlb": "机械小类别", "veh_Zycs": "主要参数", "veh_Pfjd": "排放阶段",
                          "veh_Lx"          : "类型",
                          "veh_Gl"          : "功率",
                          "veh_Pfbz"        : "排放标准及阶段",
                          "veh_Jsys"        : "机身颜色",
                          "veh_Zxczxs"      : "转向操纵形式",
                          "veh_Zcrs"        : "准乘人数",
                          "veh_Lzs"         : "轮轴数",
                          "veh_Zj"          : "轴距",
                          "veh_Lts"         : "轮胎数",

                          "veh_Ltgg"        : "轮胎规格",
                          "veh_Qlj"         : "前轮距",
                          "veh_Hlj"         : "后轮距",
                          "veh_Lds"         : "履带数",
                          "veh_Ldgg"        : "履带规格",
                          "veh_Gj"          : "轨距",
                          "veh_Wkc"         : "外廓长",

                          "veh_Wkk"         : "外廓宽",
                          "veh_Wkg"         : "外廓高",
                          "veh_Bdqyl"       : "标定牵引力/割台宽度",
                          "veh_Zxsyzl"      : "最小使用质量/喂入量",
                          "veh_Zdyyzzl"     : "最大允许载质量/联合收割（获）机质量",
                          "veh_Lhqxs"       : "离合器型式",
                          "veh_Qpzxs"       : "前配重型式",
                          "veh_Hpzxs"       : "后配重型式",
                          "veh_Jzxs"        : "机罩型式",
                          "veh_Ddlj"        : "订单轮距",
                          "veh_Jssxs"       : "驾驶室型式",
                          "veh_Bhxs"        : "板簧型式",
                          "veh_Scqymc"      : "生产企业名称",
                          "veh_Pp"          : "品牌",
                          "veh_Zxbz"        : "执行标准",
                          "veh_Scqydz"      : "生产企业地址",
                          "veh_Lxfs"        : "联系方式",
                          "is_Exp"        : "是否是英文版出口车",
                          "car_storage_type": "公告类型（0表示小拖；1、中拖；2、大拖；3、超标车）", "car_storage_no": "公告唯一号", "turnOff": "公告启用标识（0:启用；1：停用）", "turnOffTime": "公告停用时间"]
            def upperCase = { domain, value ->
                return value.toUpperCase()
            }
            def out=response.outputStream
            def map=nzCarStorageService.selectNzCarStorageByParams(params)

            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(map.rows)
            map.clear()
            excelOp.preWriteExcel(out,null,m,["公告信息"],content)
            session.setAttribute('compFlag',"success")
            out.flush()
            out.close()

        }
    }catch(Exception e){
        e.printStackTrace()
        session.setAttribute('compFlag',"error")
    }finally{
        rows.clear()
        content.clear()
    }
   }


    /**
     * @Description 同步数据 （公告信息、 一致性信息）
     * @Create 2013-07-25 zouQ
     */
    def synchroTable()
    {
        long  statrTime = System.currentTimeMillis()
        //获取同步数据源(远程服务器)
        def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
        def remoteUrl= dsp.getProperty("remoteUrl")
        def remoteUserName=dsp.getProperty("remoteUserName")
        def remotePassword= dsp.getProperty("remotePassword")
        def remoteDriverClassName=dsp.getProperty("remoteDriverClassName")

        def url = dsp.getProperty("url")
        def userName = dsp.getProperty("userName")
        def password = dsp.getProperty("password")
        def driverClassName = dsp.getProperty("driverClassName")

        if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
            return render("远程服务器信息配置不完整！")
        }
        def msg=""

        ////Oracle数据连接
        Sql ora_con = groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
        ////Sql数据库连接
        Sql sql_con  = groovy.sql.Sql.newInstance("${url}","${userName}","${password}","${driverClassName}");

        try{
            def lastSynTime=sqlToolService.getLastSynTime(sql_con)
            //获取每次同步的最大数，当不设置时默认一次同步全部的
            def carMaxCount=Integer.parseInt(grailsApplication.config.client.carStorageSyn.maxCount?grailsApplication.config.client.carStorageSyn.maxCount:0)
            def cocMaxCount=Integer.parseInt(grailsApplication.config.client.cocSyn.maxCount?grailsApplication.config.client.cocSyn.maxCount:0)
            /*
            *同步的逻辑：
            * 1、公告信息和一致性证书信息分开同步
            * 2、同步时首先从客户端数据库中tbl_lastSynTime取出最后更新时间，根据最后最后更新时间从服务器的wzh_temp表中取出在服务端已修改或删除的记录id，然后从车间客户端删除这些记录
            * 3、根据最后更新时间从服务端从取出创建时间或修改时间大于最后更新时间的记录，同步到客户端
             */
            def  totalTime=0
            //同步分解前的公告信息
            def result=sqlToolService.synPreCarStorage(ora_con,sql_con,carMaxCount)
            if ("all".equals(params.synType)) {
                def result_1 = sqlToolService.GetServeData(ora_con,sql_con,lastSynTime?.carStorageLastSynTime,lastSynTime?.carStorageLastDeleteTime,carMaxCount).split(",");       ///公告信息
                def result_2 = sqlToolService.GetServeDataByConsistency(ora_con,sql_con,lastSynTime?.cocLastSynTime,lastSynTime?.cocLastDeleteTime,cocMaxCount).split(",");   	///一致性信息

                totalTime=(System.currentTimeMillis()-statrTime)/1000
                msg="分解前公告同步条数：${result.totalCount} <br/>公告信息同步条数: "+result_1[0]+"<br/>一致性信息同步条数: "+result_2[0]+"</br>耗时: "+totalTime+"S</br>"+"</br>结果: <font color='green'>同步成功!</font>"
            }else if ("agr".equals(params.synType)){
//                def result_1 = sqlToolService.GetServeDataByType(ora_con,sql_con,carMaxCount,"1").split(",");       ///公告信息
                def result_1 = sqlToolService.GetServeDataByType(ora_con,sql_con,lastSynTime?.carStorageLastSynTime,lastSynTime?.carStorageLastDeleteTime,carMaxCount,"1").split(",");       ///公告信息

                totalTime=(System.currentTimeMillis()-statrTime)/1000
                msg="分解前公告同步条数：${result.totalCount} <br/>公告信息同步条数: "+result_1[0]+"耗时: "+totalTime+"S</br>"+"</br>结果: <font color='green'>同步成功!</font>"
            }else{
                msg='同步的公告类型不正确！'
            }

        }catch (Exception e){
            e.cause?.printStackTrace();
            msg="<font color='red'>同步失败,检查网络相关信息后重试!【${e.cause?e.cause:e}】</font>"
        } finally{
            sqlToolService.closeCon(sql_con,ora_con);
            render msg
        }
    }



    /**
     * @Description 公告信息下载列表函数
     * @Create 2013-07-25 zouQ
     */
    def listDown(){
        render (view:'/cn/com/wz/vehcert/carstorage/preCartStorageDownloadList',model: params);
    }

    /**
     * @Description 公告信息列表函数
     * @Create 2013-07-25 zouQ
     */
    def jsonListByDownload(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def infoType = params.infoType


        def cel={    ///查询条件（车辆名称、车辆型号）
            if(params.clmc){
                like("veh_Clmc","%${params.clmc}%")
            }
            if(params.dpxh){
                eq("veh_Dpxh","${params.dpxh}")
            }
            if (params.clxh) {
                eq("veh_Clxh","${params.clxh}")
            }
        }
        def rows=CarStorage.createCriteria().list(params,cel)

        def lst = []
        int i = 1
        rows?.each { u ->
            def m = [:]
            m.id = u.id
            m.num = i
            m.veh_Clzzqymc = u.veh_Clzzqymc
            m.veh_Qyid = u.veh_Qyid
            m.veh_Clfl = u.veh_Clfl
            m.veh_Clmc = u.veh_Clmc
            m.veh_Clpp = u.veh_Clpp
            m.veh_Clxh = u.veh_Clxh
            m.veh_Csys = u.veh_Csys
            m.veh_Dpxh = u.veh_Dpxh
            m.veh_Dpid = u.veh_Dpid
            m.veh_Fdjxh = u.veh_Fdjxh
            m.veh_Rlzl = u.veh_Rlzl
            m.veh_Pl = u.veh_Pl
            m.veh_Gl = u.veh_Gl
            m.veh_zdjgl = u.veh_zdjgl
            m.veh_Zxxs = u.veh_Zxxs
            m.veh_Qlj = u.veh_Qlj
            m.veh_Hlj = u.veh_Hlj
            m.veh_Lts = u.veh_Lts
            m.veh_Ltgg = u.veh_Ltgg
            m.veh_Gbthps = u.veh_Gbthps
            m.veh_Zj = u.veh_Zj
            m.veh_Zh = u.veh_Zh
            m.veh_Zs = u.veh_Zs
            m.veh_Wkc = u.veh_Wkc
            m.veh_Wkk = u.veh_Wkk
            m.veh_Wkg = u.veh_Wkg
            m.veh_Hxnbc = u.veh_Hxnbc
            m.veh_Hxnbk = u.veh_Hxnbk
            m.veh_Hxnbg = u.veh_Hxnbg
            m.veh_Zzl = u.veh_Zzl
            m.veh_Edzzl = u.veh_Edzzl
            m.veh_Zbzl = u.veh_Zbzl
            m.veh_Zzllyxs = u.veh_Zzllyxs
            m.veh_Zqyzzl = u.veh_Zqyzzl
            m.veh_Edzk = u.veh_Edzk
            m.veh_Bgcazzdyxzzl = u.veh_Bgcazzdyxzzl
            m.veh_Jsszcrs = u.veh_Jsszcrs
            m.veh_Yh = u.veh_Yh
            m.veh_Hzdfs = u.veh_Hzdfs
            m.veh_Ggpc = u.veh_Ggpc
            m.veh_Cpggh = u.veh_Cpggh
            m.veh_Ggsxrq = u.veh_Ggsxrq
            m.veh_Zgcs = u.veh_Zgcs
            m.veh_Bz = u.veh_Bz
            m.veh_Qybz = u.veh_Qybz
            m.veh_Cpscdz = u.veh_Cpscdz
            m.veh_Qyqtxx = u.veh_Qyqtxx
            m.veh_Pfbz = u.veh_Pfbz
            m.veh_Clztxx = u.veh_Clztxx
            m.veh_Jss = u.veh_Jss
            m.veh_Jsslx = u.veh_Jsslx

            m.veh_cpid = u.veh_cpid
            m.veh_jjlqj = u.veh_jjlqj
            m.veh_qxhx = u.veh_qxhx
            m.veh_dpqy = u.veh_dpqy
            m.veh_fgbsqy = u.veh_fgbsqy
            m.veh_fgbssb = u.veh_fgbssb
            m.veh_fgbsxh = u.veh_fgbsxh
            m.veh_y45pic = u.veh_y45pic
            m.veh_zhpic = u.veh_zhpic
            m.veh_fhpic = u.veh_fhpic
            m.veh_pic1 = u.veh_pic1
            m.veh_pic2 = u.veh_pic2
            m.veh_other = u.veh_other

            m.createTime = u.createTime
            m.createrId = u.createrId
            m.updateTime = u.updateTime
            m.updaterId = u.updaterId
            m.turnOff = u.turnOff
            m.turnOffTime = u.turnOffTime

            i++;
            lst.add(m)
        }
        def result = [rows: lst, total: rows.totalCount]
        render result as JSON
    }

    /**
     * @Description 公告信息下载  >> 初始化  当出现同步问题或者数据不正确时，可以执行此方法 初始化数据
     * @Create 2013-08-10 zouQ
     */
    def initialize() {
        //获取同步数据源(远程服务器)
        def dsp = PropertiesLoaderUtils.loadAllProperties("db.properties")
        def url = dsp.getProperty("url")
        def userName = dsp.getProperty("userName")
        def password = dsp.getProperty("password")
        def driverClassName = dsp.getProperty("driverClassName")
        ////Sql数据库连接
        Sql sql_con  = groovy.sql.Sql.newInstance("${url}","${userName}","${password}","${driverClassName}");
        sqlToolService.initialize(sql_con);
        render "初始化数据成功!"
        //sqlToolService.CreateByTable(sql_con);
    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }




    def dd(){
        double free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
        double total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
        double max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
        println "读取数据开始时间："+System.currentTimeMillis()+"   空闲内存：${free}  总占用内存：${total}  实际可用内存：${max - total + free}"
        def carstoreList= CarStorage.list()
        def startTime=System.currentTimeMillis()

        def totalCount=carstoreList?.size()


        def list1=carstoreList?.subList(0,20000)
        def list2=carstoreList?.subList(20001,40000)
        def list3=carstoreList?.subList(40001,60000)
        def list4=carstoreList?.subList(60001,80000)
        def list5=carstoreList?.subList(80001,totalCount)

        Thread thread1=new Thread(new SynAffiche(list1));
        thread1.start()
        Thread thread2=new Thread(new SynAffiche(list2));
        thread2.start()
        Thread thread3=new Thread(new SynAffiche(list3));
        thread3.start()
        Thread thread4=new Thread(new SynAffiche(list4));
        thread4.start()
        Thread thread5=new Thread(new SynAffiche(list5));
        thread5.start()
//        dmsSynService.syn(carstoreList)

        free = (Runtime.getRuntime().freeMemory()) / (1024.0 * 1024);
        total = (Runtime.getRuntime().totalMemory()) / (1024.0 * 1024);
        max = (Runtime.getRuntime().maxMemory()) / (1024.0 * 1024);
        println "同步条数：${totalCount}  同步结束时间："+System.currentTimeMillis()+"   空闲内存：${free}  总占用内存：${total}  实际可用内存：${max - total + free}"
        println "总用时：" +  (System.currentTimeMillis()-startTime)
    }
}
