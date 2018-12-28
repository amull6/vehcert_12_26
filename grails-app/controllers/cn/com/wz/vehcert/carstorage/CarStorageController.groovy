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
class CarStorageController extends BaseController {
    def dmsSynService
    def sqlToolService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def carStorageService
    def exportService
    def dataSource
    def sqlService
    def index() {
        render (view:'/cn/com/wz/vehcert/carstorage/cartStorageList',model: params)
    }

    /**
     * @Description 根据条件查询出满足条件的记录
     * @return
     * @Create 2013-07-26 huxx
     */
    def search(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def result=carStorageService.selectCarStorageByParams(params)
        render result as JSON
    }

    /**
     * @Description 进入导入页面
     * @return
     * @Create 2013-07-29 huxx
     */
    def importPage(){
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'carStorage',action: 'imp')}",tabId:params.tabId])
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
            labels.add('veh_Clzzqymc');
            labels.add('veh_Qyid');
            labels.add('veh_Clfl');
            labels.add('veh_VinFourBit');
            labels.add('veh_Clmc');
            labels.add('veh_Clpp');
            labels.add('veh_Clxh');
            labels.add('veh_Csys');
            labels.add('veh_Dpxh');
            labels.add('veh_Dpid');
            labels.add('veh_cpid');
            labels.add('veh_Ggpc');
            labels.add('veh_qxhx');
            labels.add('veh_Fdjxh');
            labels.add('veh_Rlzl');
            labels.add('veh_Pl');
            labels.add('veh_Gl');
            labels.add('veh_Zxxs');
            labels.add('veh_Qlj');
            labels.add('veh_Hlj');
            labels.add('veh_Lts');
            labels.add('veh_Ltgg');
            labels.add('veh_Gbthps');
            labels.add('veh_Zj');
            labels.add('veh_Zh');
            labels.add('veh_Zs');
            labels.add('veh_Wkc');
            labels.add('veh_Wkk');
            labels.add('veh_Wkg');
            labels.add('veh_Hxnbc');
            labels.add('veh_Hxnbk');
            labels.add('veh_Hxnbg');
            labels.add('veh_Zzl');
            labels.add('veh_Edzzl');
            labels.add('veh_Zbzl');
            labels.add('veh_Zzllyxs');
            labels.add('veh_Zqyzzl');
            labels.add('veh_Edzk');
            labels.add('veh_Bgcazzdyxzzl');
            labels.add('veh_Jsszcrs');
            labels.add('veh_Hzdfs');
            labels.add('veh_Cpggh');
            labels.add('veh_Ggsxrq');
            labels.add('veh_jjlqj');
            labels.add('veh_dpqy');
            labels.add('veh_Zgcs');
            labels.add('veh_Yh');
            labels.add('veh_Yhlx');
            labels.add('veh_Bz');
            labels.add('veh_Qybz');
            labels.add('veh_Cpscdz');
            labels.add('veh_Qyqtxx');
            labels.add('veh_Pfbz');
            labels.add('veh_Clztxx');
            labels.add('veh_Jss');
            labels.add('veh_Jsslx');
            labels.add('veh_fgbsqy');
            labels.add('veh_fgbsxh');
            labels.add('veh_fgbssb');
            labels.add('veh_y45pic');
            labels.add('veh_zhpic');
            labels.add('veh_fhpic');
            labels.add('veh_pic1');
            labels.add('veh_pic2');
            labels.add('veh_other');
            labels.add('veh_pzxlh');
            labels.add('veh_zdjgl');
            labels.add('carStorageType');
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
                checkResult=carStorageService.checkCarStorageNo(lst)   //不再校验公告唯一号的唯一性
                emptyCount= checkResult.emptyCount
                turnOffCarStorage=checkResult.turnOffCarStorage
                paramsMap.existsCarStorageNoList=checkResult.existsCarStorageNoList
                if(emptyCount==0&&turnOffCarStorage.size()==0){ // 导入时不再校验公告唯一号是不是唯一的，只校验公告是不是空的、公告是不是已经停用
                    def m=carStorageService.newSqlSaveList(paramsMap,request,grailsAttributes)
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
     * @Description 跳转到新增页面
     * @return
     * @create 2013-07-28 huxx
     * @Update 2014-07-28 zhuwei
     * @update 新建公告添加公告唯一号
     */
    def toCreate(){
        def maxCarStorageNo_S=servletContext.getAttribute('dic_maxCarStorageNo').value1
        DecimalFormat df = new DecimalFormat( "0")
        maxCarStorageNo_S=df.format(Double.parseDouble(maxCarStorageNo_S)+1)
        render (view:'/cn/com/wz/vehcert/carstorage/create',model: [maxCarStorageNo:maxCarStorageNo_S])
    }

    /**
     * @Description 保存公告信息
     * @return
     * @create 2013-07-28 huxx
     * @Update 2014-07-28 zhuwei
     * @update 新建公告添加公告唯一号
     * @update 2015-04-08 保存时不再将唯一码保存到内存变量中
     */
    def save(){
        def carStorage=new CarStorage(params)
        carStorage.createrId=getCurrentUser()?.id
        carStorage.createTime=DateUtil.getCurrentTime()
        if (!carStorage.save(flash:true)){
            render (view:'/cn/com/wz/vehcert/carstorage/create',model: [carStorage:carStorage])
            return
        }else{
//            def writeInDic=carStorage.car_storage_no
//            def  maxCarStorageNoIns=DictionaryValue.findByCode('maxCarStorageNo')
//            println(writeInDic)
//            maxCarStorageNoIns.setValue1(writeInDic)
//            servletContext.setAttribute("dic_maxCarStorageNo",maxCarStorageNoIns)
            flash.message="保存成功！"
            redirect(action: "show", id: carStorage.id)
        }
    }

    /**
     * @Description 跳转到更新页面
     * @return
     * @create 2013-07-28 huxx
     * @Update 2015-05-26 更新时不再将公告的公告唯一号保存到Temp表zhuwei
     */
    def toUpdate(){
        def carStorage=CarStorage.get(params.id)
        render (view:'/cn/com/wz/vehcert/carstorage/edit',model: [carStorage:carStorage])
    }

    def update(){
        def carStorage=CarStorage.get(params.id)
        if (params.version&&params.version<carStorage.version){
            flash.message='记录已过期！'
            return render (view:'/cn/com/wz/vehcert/carstorage/edit',model: [carStorage:carStorage])
        }
        String curTime=DateUtil.getCurrentTime()
        carStorage.properties=params
        carStorage.updaterId=getCurrentUser()?.id
        carStorage.updateTime=curTime

        if (!carStorage.save(flush: true)){
            return render (view:'/cn/com/wz/vehcert/carstorage/edit',model: [carStorage:carStorage])
        }else{
            Temp t=new Temp()
            t.wzh_carstorageID=carStorage.id
            t.wzh_type=1
            t.insertTime=curTime
//            t.car_storage_no=carStorage.car_storage_no
            t.save(flush: true)
            flash.message="更新成功！"
            redirect(action: "show", id: carStorage.id)
        }
    }

    def show(){
        def carStorage=CarStorage.get(params.id)
        render (view:'/cn/com/wz/vehcert/carstorage/show',model: [carStorage:carStorage])
    }

    /**
     * @Description 使用ajax删除用户记录
     *@param params
     *@return 操作信息
     *@Auther huxx
     *@createTime 2012-9-14 上午9:46:18
     * @UpdateTime 删除公告的时候报公告唯一号保存到temp表 zhuwei
     */
    def jsonDelete(){
        def ids=params.deleteIds.split("_")
        def message=""
        def count=0
        ids?.each{
            def carStorage= CarStorage.get(it)
            def car_storage_no=carStorage.car_storage_no
           if(carStorage?.delete(flush: true)){
               count+=1
               message+="【${carStorage.veh_Clxh} ${carStorage.veh_Dpxh}】删除失败！_"
           }else{
               Temp t=new Temp()
               t.wzh_carstorageID=it
               t.wzh_type=1
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
     *@createTime 2014-4-7
     */
    def disableCarStorage(){
        def ids=params.deleteIds.split("_")
        def message=""
        def count=0
        int turnOff=0

        ids?.each{   //先查看勾选的记录中有没有已经停用的公告
            def carStorage= CarStorage.get(it)
            if(carStorage.turnOff=='1'){
                turnOff=turnOff+1
            }
        }
       if(turnOff>0){     //如果勾选记录中有公告处于停用公告，给予不能重复停用提示
           message="勾选记录中有已停用公告，不能重复停用！"
       }else{       //如果勾选的记录中没有停用的记录，做停用处理
           ids?.each{
               def carStorage= CarStorage.get(it)
               def car_storage_no=carStorage.car_storage_no
               //对停用公告做逻辑删除
               carStorage.setTurnOff('1') //停用标识   0：启用 1：停用
               carStorage.setTurnOffTime(DateUtil.getCurrentTime())
               if(!carStorage?.save(flush: true)){
                   count+=1
                   message+="【${carStorage.veh_Clxh} ${carStorage.veh_Dpxh}】停用失败！_"
               }else{
                   Temp t=new Temp()
                   t.wzh_carstorageID=it
                   t.wzh_type=1
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
     */
    def jsonDeleteSearch(){
        def veh_Clxh=''
        def veh_Clmc=''
        if (params.veh_Clmc) {
            veh_Clmc=params.veh_Clmc
        }
        if (params.veh_Clxh) {
            veh_Clxh=params.veh_Clxh
        }

        def message=""
        CarStorage.withTransaction {trans->
            try{
                ////查询经销商所拥有的合格证信息
                StringBuffer sql_sbfS = new StringBuffer("SELECT ID,car_storage_no FROM wzh_carStorage ");
                if(params.veh_Clmc){
                     if(params.veh_Clxh){
                         sql_sbfS.append("  WHERE  veh_Clmc like '%"+veh_Clmc+"%' AND veh_Clxh = '"+veh_Clxh+"'");
                     }else{
                         sql_sbfS.append("  WHERE  veh_Clmc like '%"+veh_Clmc+"%'");
                     }
                    if (params.carStorageType){
                        sql_sbfS.append("  and car_storage_type='${params.carStorageType}'")
                    }
                }else{
                    if(params.veh_Clxh){
                        sql_sbfS.append("  WHERE  veh_Clxh = '"+veh_Clxh+"'");
                        if (params.carStorageType){
                            sql_sbfS.append("  and car_storage_type='${params.carStorageType}'")
                        }
                    }else{
                        if (params.carStorageType){
                            sql_sbfS.append("  where car_storage_type='${params.carStorageType}'")
                        }
                    }
                }

                def listS =  sqlService.executeList(sql_sbfS.toString())

                if (listS){
                    StringBuffer sql_sbf = new StringBuffer("DELETE FROM wzh_carStorage ");
                    if(params.veh_Clmc){
                        if(params.veh_Clxh){
                            sql_sbf.append("  WHERE  veh_Clmc like '%"+veh_Clmc+"%' AND veh_Clxh = '"+veh_Clxh+"'");
                        }else{
                            sql_sbf.append("  WHERE  veh_Clmc like '%"+veh_Clmc+"%'");
                        }
                        if (params.carStorageType){
                            sql_sbfS.append("  and car_storage_type='${params.carStorageType}'")
                        }
                    }else{
                        if(params.veh_Clxh){
                            sql_sbf.append("  WHERE  veh_Clxh = '"+veh_Clxh+"'");
                            if (params.carStorageType){
                                sql_sbfS.append("  and car_storage_type='${params.carStorageType}'")
                            }
                        }else{
                            if (params.carStorageType){
                                sql_sbf.append("  where car_storage_type='${params.carStorageType}'")
                            }
                        }
                    }
                    def list =  sqlService.executeUpdate(sql_sbf.toString())

                    if (list>0){
                        message=list+"条删除成功！"

                        listS.each {
                            Temp t=new Temp()
                            t.wzh_carstorageID=it.ID
                            t.car_storage_no=it?.car_storage_no
                            t.wzh_type=1
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
     *@createTime 2014-04-04
     */
    def disableSearchCarStorage(){
        def aaa=params
        def veh_Clxh=''
        def veh_Clmc=''
        def  turnOff=''
        def aa=params
        def turn_off_time=DateUtil.getCurrentTime()
        if (params.veh_Clmc) {
            veh_Clmc=params.veh_Clmc
        }
        if (params.veh_Clxh) {
            veh_Clxh=params.veh_Clxh
        }
        if(params.turnOff){
            turnOff=params.turnOff
        }

        def message=""
        CarStorage.withTransaction {trans->
            try{
                ////查询经销商所拥有的合格证信息
                def need=false
                StringBuffer sql_sbfS = new StringBuffer("SELECT ID,car_storage_no,turn_Off FROM wzh_carStorage ");
                if(params.veh_Clmc){
                       sql_sbfS.append("  WHERE  veh_Clmc= '"+veh_Clmc+"' ");
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

                if(params.carStorageType){
                    if (need){
                        sql_sbfS.append("  and   CAR_STORAGE_TYPE ='${params.carStorageType}'");
                    }else{
                        sql_sbfS.append("  WHERE  CAR_STORAGE_TYPE='${params.carStorageType}'");
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

                if(params.veh_Fdjxh){
                    if (need){
                        sql_sbfS.append("  and   veh_Fdjxh ='${params.veh_Fdjxh}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Fdjxh='${params.veh_Fdjxh}'");
                        need=true
                    }
                }

                if(params.veh_Ltgg){
                    if (need){
                        sql_sbfS.append("  and   veh_Ltgg ='${params.veh_Ltgg}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Ltgg='${params.veh_Ltgg}'");
                        need=true
                    }
                }

                if(params.veh_Hxnbc){
                    if (need){
                        sql_sbfS.append("  and   veh_Hxnbc ='${params.veh_Hxnbc}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Hxnbc='${params.veh_Hxnbc}'");
                        need=true
                    }
                }

                if(params.veh_Hxnbk){
                    if (need){
                        sql_sbfS.append("  and   veh_Hxnbk ='${params.veh_Hxnbk}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Hxnbk='${params.veh_Hxnbk}'");
                        need=true
                    }
                }


                if(params.veh_Hxnbg){
                    if (need){
                        sql_sbfS.append("  and   veh_Hxnbg ='${params.veh_Hxnbg}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Hxnbg='${params.veh_Hxnbg}'");
                        need=true
                    }
                }

                if(params.veh_Jsszcrs){
                    if (need){
                        sql_sbfS.append("  and   veh_Jsszcrs ='${params.veh_Jsszcrs}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Jsszcrs='${params.veh_Jsszcrs}'");
                        need=true
                    }
                }

                if(params.veh_Gbthps){
                    if (need){
                        sql_sbfS.append("  and   veh_Gbthps ='${params.veh_Gbthps}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Gbthps='${params.veh_Gbthps}'");
                        need=true
                    }
                }

                if(params.veh_Jsslx){
                    if (need){
                        sql_sbfS.append("  and   veh_Jsslx ='${params.veh_Jsslx}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Jsslx='${params.veh_Jsslx}'");
                        need=true
                    }
                }

                if(params.veh_Zj){
                    if (need){
                        sql_sbfS.append("  and   veh_Zj ='${params.veh_Zj}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Zj='${params.veh_Zj}'");
                        need=true
                    }
                }

                if(params.veh_Wkc){
                    if (need){
                        sql_sbfS.append("  and   veh_Wkc ='${params.veh_Wkc}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Wkc='${params.veh_Wkc}'");
                        need=true
                    }
                }

                if(params.veh_Wkk){
                    if (need){
                        sql_sbfS.append("  and   veh_Wkk ='${params.veh_Wkk}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Wkk='${params.veh_Wkk}'");
                        need=true
                    }
                }

                if(params.veh_Wkg){
                    if (need){
                        sql_sbfS.append("  and   veh_Wkg ='${params.veh_Wkg}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Wkg='${params.veh_Wkg}'");
                        need=true
                    }
                }

                if(params.veh_Qlj){
                    if (need){
                        sql_sbfS.append("  and   veh_Qlj ='${params.veh_Qlj}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Qlj='${params.veh_Qlj}'");
                        need=true
                    }
                }

                if(params.veh_Hlj){
                    if (need){
                        sql_sbfS.append("  and   veh_Hlj ='${params.veh_Hlj}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Hlj='${params.veh_Hlj}'");
                        need=true
                    }
                }

                if(params.veh_Clztxx){    //车辆状态信息
                    if (need){
                        sql_sbfS.append("  and   veh_Clztxx ='${params.veh_Clztxx}'");
                    }else{
                        sql_sbfS.append("  WHERE  veh_Clztxx='${params.veh_Clztxx}'");
                        need=true
                    }
                }

                if(params.veh_Yhlx){    //油耗类型
                    if(params.veh_Yhlx=='1'){
                        if (need){
                            sql_sbfS.append("  and   veh_Yhlx is not null ");
                        }else{
                            sql_sbfS.append("  WHERE  veh_Yhlx is not null ");
                            need=true
                        }
                    }else if(params.veh_Yhlx=='0'){
                        if (need){
                            sql_sbfS.append("  and   veh_Yhlx is  null ");
                        }else{
                            sql_sbfS.append("  WHERE  veh_Yhlx is  null ");
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
                        StringBuffer sql_sbf = new StringBuffer("update  wzh_carStorage set turn_Off='1' , turn_off_time='"+turn_off_time+"'");
                        def jion=false
                        if(params.veh_Clmc){
                            sql_sbf.append("  WHERE  veh_Clmc ='"+veh_Clmc+"' ");
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
                        if(params.carStorageType){
                            if (jion){
                                sql_sbf.append("  and   CAR_STORAGE_TYPE ='${params.carStorageType}'");
                            }else{
                                sql_sbf.append("  WHERE  CAR_STORAGE_TYPE='${params.carStorageType}'");
                                jion=true
                            }
                        }
                        if(params.veh_Fdjxh){
                            if (jion){
                                sql_sbf.append("  and   veh_Fdjxh ='${params.veh_Fdjxh}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Fdjxh='${params.veh_Fdjxh}'");
                                jion=true
                            }
                        }

                        if(params.veh_Ltgg){
                            if (jion){
                                sql_sbf.append("  and   veh_Ltgg ='${params.veh_Ltgg}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Ltgg='${params.veh_Ltgg}'");
                                jion=true
                            }
                        }

                        if(params.veh_Hxnbc){
                            if (jion){
                                sql_sbf.append("  and   veh_Hxnbc ='${params.veh_Hxnbc}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Hxnbc='${params.veh_Hxnbc}'");
                                jion=true
                            }
                        }

                        if(params.veh_Hxnbk){
                            if (jion){
                                sql_sbf.append("  and   veh_Hxnbk ='${params.veh_Hxnbk}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Hxnbk='${params.veh_Hxnbk}'");
                                jion=true
                            }
                        }


                        if(params.veh_Hxnbg){
                            if (jion){
                                sql_sbf.append("  and   veh_Hxnbg ='${params.veh_Hxnbg}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Hxnbg='${params.veh_Hxnbg}'");
                                jion=true
                            }
                        }

                        if(params.veh_Jsszcrs){
                            if (jion){
                                sql_sbf.append("  and   veh_Jsszcrs ='${params.veh_Jsszcrs}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Jsszcrs='${params.veh_Jsszcrs}'");
                                jion=true
                            }
                        }

                        if(params.veh_Gbthps){
                            if (jion){
                                sql_sbf.append("  and   veh_Gbthps ='${params.veh_Gbthps}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Gbthps='${params.veh_Gbthps}'");
                                jion=true
                            }
                        }

                        if(params.veh_Jsslx){
                            if (jion){
                                sql_sbf.append("  and   veh_Jsslx ='${params.veh_Jsslx}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Jsslx='${params.veh_Jsslx}'");
                                jion=true
                            }
                        }

                        if(params.veh_Zj){
                            if (jion){
                                sql_sbf.append("  and   veh_Zj ='${params.veh_Zj}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Zj='${params.veh_Zj}'");
                                jion=true
                            }
                        }

                        if(params.veh_Wkc){
                            if (jion){
                                sql_sbf.append("  and   veh_Wkc ='${params.veh_Wkc}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Wkc='${params.veh_Wkc}'");
                                jion=true
                            }
                        }

                        if(params.veh_Wkk){
                            if (jion){
                                sql_sbf.append("  and   veh_Wkk ='${params.veh_Wkk}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Wkk='${params.veh_Wkk}'");
                                jion=true
                            }
                        }

                        if(params.veh_Wkg){
                            if (jion){
                                sql_sbf.append("  and   veh_Wkg ='${params.veh_Wkg}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Wkg='${params.veh_Wkg}'");
                                jion=true
                            }
                        }

                        if(params.veh_Qlj){
                            if (jion){
                                sql_sbf.append("  and   veh_Qlj ='${params.veh_Qlj}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Qlj='${params.veh_Qlj}'");
                                jion=true
                            }
                        }

                        if(params.veh_Hlj){
                            if (jion){
                                sql_sbf.append("  and   veh_Hlj ='${params.veh_Hlj}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Hlj='${params.veh_Hlj}'");
                                jion=true
                            }
                        }

                        if(params.veh_Clztxx){    //车辆状态信息
                            if (jion){
                                sql_sbf.append("  and   veh_Clztxx ='${params.veh_Clztxx}'");
                            }else{
                                sql_sbf.append("  WHERE  veh_Clztxx='${params.veh_Clztxx}'");
                                jion=true
                            }
                        }

                        if(params.veh_Yhlx){    //油耗类型
                            if(params.veh_Yhlx=='1'){
                                if (jion){
                                    sql_sbf.append("  and   veh_Yhlx is not null ");
                                }else{
                                    sql_sbf.append("  WHERE  veh_Yhlx is not null ");
                                    jion=true
                                }
                            }else if(params.veh_Yhlx=='0'){
                                if (jion){
                                    sql_sbf.append("  and   veh_Yhlx is  null ");
                                }else{
                                    sql_sbf.append("  WHERE  veh_Yhlx is  null ");
                                    jion=true
                                }
                            }

                        }

//                        println(sql_sbf)
                        def list =  sqlService.executeUpdate(sql_sbf.toString())

                        if (list>0){
                            message=list+"条停用成功！"

                            listS.each {
                                Temp t=new Temp()
                                t.wzh_carstorageID=it.ID
                                t.car_storage_no=it?.car_storage_no
                                t.wzh_type=1
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
     *@Description 公告信息查询页面导出
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-28 liuly
     *@UpdateBy zhuwei 2014_07_25
     *@导出公告内容添加公告唯一号
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
            //List fields = ["veh_Clzzqymc","veh_Qyid", "veh_Clfl","veh_Clmc","veh_Clpp","veh_Clxh","veh_Csys","veh_Dpxh","veh_Dpid","veh_cpid","veh_Ggpc","veh_qxhx","veh_Fdjxh","veh_Rlzl","veh_Pl","veh_Gl","veh_Zxxs","veh_Qlj","veh_Hlj","veh_Lts","veh_Ltgg","veh_Gbthps","veh_Zj","veh_Zh","veh_Zs","veh_Wkc","veh_Wkk","veh_Wkg","veh_Hxnbc","veh_Hxnbk","veh_Hxnbg","veh_Zzl","veh_Edzzl","veh_Zbzl","veh_Zzllyxs","veh_Zqyzzl","veh_Edzk","veh_Bgcazzdyxzzl","veh_Jsszcrs","veh_Hzdfs","veh_Cpggh","veh_Ggsxrq","veh_jjlqj","veh_dpqy","veh_Zgcs","veh_Yh","veh_Bz","veh_Qybz","veh_Cpscdz","veh_Qyqtxx","veh_Pfbz","veh_Clztxx","veh_Jss","veh_Jsslx","veh_fgbsqy","veh_fgbsxh","veh_fgbssb","veh_y45pic","veh_zhpic","veh_fhpic","veh_pic1","veh_pic2","veh_other"]
            Map labels =  ["veh_Clzzqymc":"车辆制造企业名称","veh_Qyid":"企业ID", "veh_Clfl":"车辆分类","veh_VinFourBit":"VIN4","veh_Clmc":"车辆名称",
                           "veh_Clpp":"车辆品牌","veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_cpid":"产品ID",
                           "veh_Ggpc":"批次","veh_qxhx":"前悬后悬","veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类 ","veh_Pl":"排量","veh_Gl":"功率",
                          "veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格","veh_Gbthps":"钢板弹簧片数",
                          "veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长",
                          "veh_Hxnbk":"货箱内部宽","veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量 ","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数",
                          "veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车按座","veh_Jsszcrs":"驾驶室准乘人数","veh_Hzdfs":"后制动方式","veh_Cpggh":"产品公告号",
                          "veh_Ggsxrq":"公告生效日期 ","veh_jjlqj":"接近离去角","veh_dpqy":"底盘企业","veh_Zgcs":"最高车速","veh_Yh":"油耗","veh_Yhlx":"油耗类型","veh_Bz":"备注",
                           "veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其他信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息","veh_Jss":"驾驶室",
                            "veh_Jsslx":"驾驶室类型","veh_fgbsqy":"反光标示企业","veh_fgbsxh":"反光标示型号","veh_fgbssb":"反光标示商标","veh_y45pic":"右45度照片","veh_zhpic":"正后照片",
                           "veh_fhpic":"防护照片","veh_pic1":"选择照片1","veh_pic2":"选择照片2 ","veh_other":"其它","veh_pzxlh":"配置序列号","veh_zdjgl":"最大净功率/转速",
                            "carStorageType":"公告类型（0表示汽车公告；1表示农用车公告）","car_storage_no":"公告唯一号","turnOff":"公告启用标识（0:启用；1：停用）","turnOffTime":"公告停用时间"]
            def upperCase = { domain, value ->
                return value.toUpperCase()
            }
            def out=response.outputStream
            def map=carStorageService.selectCarStorageByParams(params)

            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(map.rows)
            map.clear()
            excelOp.preWriteExcel(out,null,m,["分解后公告信息"],content)
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

        def lst=[]
        int i=1
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.num=i
            m.veh_Clzzqymc =u.veh_Clzzqymc
            m.veh_Qyid     =u.veh_Qyid
            m.veh_Clfl     =u.veh_Clfl
            m.veh_Clmc     =u.veh_Clmc
            m.veh_Clpp     =u.veh_Clpp
            m.veh_Clxh     =u.veh_Clxh
            m.veh_Csys     =u.veh_Csys
            m.veh_Dpxh     =u.veh_Dpxh
            m.veh_Dpid     =u.veh_Dpid
            m.veh_Fdjxh    =u.veh_Fdjxh
            m.veh_Rlzl     =u.veh_Rlzl
            m.veh_Pl       =u.veh_Pl
            m.veh_Gl       =u.veh_Gl
            m.veh_zdjgl    =u.veh_zdjgl
            m.veh_Zxxs     =u.veh_Zxxs
            m.veh_Qlj      =u.veh_Qlj
            m.veh_Hlj      =u.veh_Hlj
            m.veh_Lts      =u.veh_Lts
            m.veh_Ltgg     =u.veh_Ltgg
            m.veh_Gbthps   =u.veh_Gbthps
            m.veh_Zj       =u.veh_Zj
            m.veh_Zh       =u.veh_Zh
            m.veh_Zs       =u.veh_Zs
            m.veh_Wkc      =u.veh_Wkc
            m.veh_Wkk      =u.veh_Wkk
            m.veh_Wkg      =u.veh_Wkg
            m.veh_Hxnbc    =u.veh_Hxnbc
            m.veh_Hxnbk    =u.veh_Hxnbk
            m.veh_Hxnbg    =u.veh_Hxnbg
            m.veh_Zzl      =u.veh_Zzl
            m.veh_Edzzl    =u.veh_Edzzl
            m.veh_Zbzl     =u.veh_Zbzl
            m.veh_Zzllyxs  =u.veh_Zzllyxs
            m.veh_Zqyzzl   =u.veh_Zqyzzl
            m.veh_Edzk     =u.veh_Edzk
            m.veh_Bgcazzdyxzzl =u.veh_Bgcazzdyxzzl
            m.veh_Jsszcrs  =u.veh_Jsszcrs
            m.veh_Yh       =u.veh_Yh
            m.veh_Hzdfs    =u.veh_Hzdfs
            m.veh_Ggpc     =u.veh_Ggpc
            m.veh_Cpggh    =u.veh_Cpggh
            m.veh_Ggsxrq   =u.veh_Ggsxrq
            m.veh_Zgcs     =u.veh_Zgcs
            m.veh_Bz       =u.veh_Bz
            m.veh_Qybz     =u.veh_Qybz
            m.veh_Cpscdz   =u.veh_Cpscdz
            m.veh_Qyqtxx   =u.veh_Qyqtxx
            m.veh_Pfbz     =u.veh_Pfbz
            m.veh_Clztxx   =u.veh_Clztxx
            m.veh_Jss      =u.veh_Jss
            m.veh_Jsslx    =u.veh_Jsslx

            m.veh_cpid     =u.veh_cpid
            m.veh_jjlqj    =u.veh_jjlqj
            m.veh_qxhx     =u.veh_qxhx
            m.veh_dpqy     =u.veh_dpqy
            m.veh_fgbsqy   =u.veh_fgbsqy
            m.veh_fgbssb   =u.veh_fgbssb
            m.veh_fgbsxh   =u.veh_fgbsxh
            m.veh_y45pic   =u.veh_y45pic
            m.veh_zhpic    =u.veh_zhpic
            m.veh_fhpic    =u.veh_fhpic
            m.veh_pic1     =u.veh_pic1
            m.veh_pic2     =u.veh_pic2
            m.veh_other     =u.veh_other

            m.createTime   =u.createTime
            m.createrId    =u.createrId
            m.updateTime   =u.updateTime
            m.updaterId     =u.updaterId
            m.turnOff=u.turnOff
            m.turnOffTime=u.turnOffTime

            i++;
            lst.add(m)
        }
        def result = [rows:lst,total:rows.totalCount]
        render result as JSON
    }

    /**
     * @Description 公告信息下载  >> 初始化  当出现同步问题或者数据不正确时，可以执行此方法 初始化数据
     * @Create 2013-08-10 zouQ
     */
    def initialize(){
        //获取同步数据源(远程服务器)
        def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
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
