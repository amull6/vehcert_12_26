package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.organization.Organization
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.carstorage.HarvestCarStorage
import cn.com.wz.vehcert.carstorage.NzCarStorage
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import cn.com.wz.parent.base.BaseController
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.ExcelUtils;
/**
 * @Description 收获机械合格证生成控制器
 * @CreateTime 2017-05-04 by zhuwei
 */
class HarvestInfoController extends BaseController {

    def dataSource
    def sqlService
    def harvestInfoService
    def sqlToolService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [harvestInfoInstanceList: HarvestInfo.list(params), harvestInfoInstanceTotal: HarvestInfo.count()]
    }
    /**
     *
     * @Description 取得玉米收公告
     * @author QJ
     * @createTime 2017-07-20
     * @return
     */
    def toNotice() {
        def aa=params
        ZCInfo obj=new ZCInfo(params)
        render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvestNotice',model:[zcinfoInstance:obj,tabId:params.tabId,turnOff:params.turnOff,carStorageType:params.car_storage_type])
    }
    /**
     *@Description 获取列表中的所有公告信息
     *@Auther QJ
     *@createTime  2017-07_20
     * @upDate
     *
     */
    def jsonNotice(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0

        def cel = {
            if(params.veh_Clxh){
                eq("veh_Clxh","${params.veh_Clxh}")
            }
            if(params.veh_Fdjxh){
                eq("veh_Fdjxh","${params.veh_Fdjxh}")
            }
            if(params.veh_Jxdl){
                eq("veh_Jxdl","${params.veh_Jxdl}")
            }

            if (params.veh_Rllx){
                eq("veh_Rllx",params.veh_Rllx)
            }
            if (params.veh_Jxxlb){
                eq("veh_Jxxlb",params.veh_Jxxlb)
            }
            if (params.veh_Zycs){
                eq("veh_Zycs",params.veh_Zycs)
            }
            if (params.veh_Pfjd){
                eq("veh_Pfjd",params.veh_Pfjd)
            }
            if (params.car_storage_type){
                eq("car_storage_type",params.car_storage_type)
            }
            if (params.car_storage_no){
                eq("car_storage_no",params.car_storage_no)
            }
            if(params.turnOff=='0'){
                println('111111')
                eq("turnOff",'0')  //通过菜单配置传值turnOff，只提取启用状态的公告
            }
            order 'createTime','desc'
        }
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
            list.add(m)
        }
        def result=[total:lst.totalCount,rows:list]
        render result as JSON
    }
    /**
     *@Description 环保代码计算
     *@Auther QJ
     *@createTime 2017-07-20
     */
    def calculateVIN(){
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def result=harvestInfoService.calculateVIN(params,loginUser)
        render  result;
    }
    /**
     *
     * @Description 寻找公告信息和合格证信息中建议输入
     * @author mike
     * @createTime 2013-06-29
     * @return
     */
    def jsonInfoSuggestion() {
        String field=params.field;
        String veh_Clxh=params.veh_Clxh;
        String hqlStr="select ${field} from WZH_HARVEST_CARSTORAGE c where c.veh_Clxh='${veh_Clxh}' group by ${field} order by count(*) desc";
        def template=new JdbcTemplate(dataSource)
        List suglist = template.queryForList(hqlStr)
        render(contentType: "text/json") {
            array {
                if(field=='veh_Hxnbc'){
                    pack(text:'无' ,value:'无')
                }
                for (m in suglist) {
                    String value=m.get(field.toUpperCase())
                    if(value){
                        pack(text:value ,value:value)
                    }
                }
            }
        }
    }
    /**
     * @Description 玉米收合格证生成页面
     * @CreateTime 2017-05-04
     * @Author zhuwei
     * @Update by zhuwei 玉米收合格证一年一清空流水码
     */
    def harvest(){
        flash.clear()
        def type=params.type//菜单传进来的配置的参数
        def productDh=type.split('_')
        StringBuilder   veh_Clbh
        StringBuilder   veh_start
        def symbolChar =    productDh[1]
        def lsh
        veh_start = new StringBuilder('3')
        veh_start.append(DateUtil.getCurrentTime('yy'))
        veh_Clbh=new StringBuilder('3');
        veh_Clbh.append(DateUtil.getCurrentTime('yyMM'));
        veh_Clbh.append(productDh[1])

        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        HarvestInfo obj=new HarvestInfo()
        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID " +
                         "and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}' and s.SYMBOL = '${symbolChar}' "
        println(symbolStr)
        //取出
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //harvester_D:YZ 或者  harvester_E:DX
                symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_harvester_D  或者 start_harvester_E
                symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      // max_harvester_D  或者 max_harvester_E
            }
        }
        boolean isValid=true;
        int startNum=0;
        int maxNum =0;
        if (symboMap.containsKey(type)){  //正常生产的收获机
//            startNum= symboMap.start_tractor_T0
            startNum= symboMap.get('start_'+type)
            maxNum= symboMap.get('max_'+type)
            symboMap.put('check_'+type,1);
            isValid=true;
        }else{
            isValid=false;
        }
        //判定要修改的记录是否存在
        def logUser=User.get(loginUser.id)
        def organ = Organization.findByOrganNameE(type)
        HarvestPrintSet harvestPrintSet
        if(logUser&&organ){
           def authStr  = "select * from sys_organ_users where user_id = '${loginUser.id}' and organization_id = '${organ.id}' "
            def template1=new JdbcTemplate(dataSource)
            List authList = template.queryForList(authStr)
            if(authList!=null&&authList.size()!=0){
                isValid=true;
                 harvestPrintSet = HarvestPrintSet.findByCodeAndUserid("main",organ?.organCode)
                if (!harvestPrintSet){
                    harvestPrintSet = new HarvestPrintSet()
                    harvestPrintSet.code="main"
                    harvestPrintSet.userid=organ?.organCode
                    harvestPrintSet.save(flush: true)    //如果没有NZ_printSet数据，那么保存
                }
            }
        }else{
            isValid=false;
        }


        if (isValid){
            String maxBH='';
            boolean formTop=false;
            if (harvestPrintSet){
                //若最大值存在，则判断是否跟当前规则相同 若相同则   从最大值开始 若不同则从最小值开始。
                if(type=='harvester_D'||type=='harvester_E'){
                    maxBH= harvestPrintSet?.max_harvester
                }

                if (maxBH){      //如果printSet有已打印的合格证编号,先验证
                    if (maxBH.startsWith(veh_start.toString())){  //先验证开始是不是3+年份,一年一清流水码
                        formTop=false;
                    }else{ //从符号最小值开始
                        formTop=true;
                    }
                }else{//没有有已打印的合格证编号，从符号最小值开始
                    formTop=true;
                }
            }else{
                //没有的话从初始值进行设置
                formTop=true;
            }
            if (formTop){
                //从起始值开始
                String startNum_Str=''+startNum;   //范围起始值
                //根据长度添加0
                for(int i=startNum_Str.length();i<5;i++){
                    startNum_Str="0"+startNum_Str
                }
                veh_Clbh.append(startNum_Str);//赋上流水号
//                veh_Clbh.append(workShopNo);//赋上车间代号
                obj.properties = HarvestInfo.findByVeh_Clbh(veh_Clbh)?.properties
                obj.veh_Clbh=veh_Clbh
                //取到车间配置的发动机号和合格证编号
                if(type=='harvester_D'){
                    obj.veh_Fdjh=DictionaryValue.findByCode('yms_config_fdjh')?.value1
                }else if(type=='harvester_E'){
                    obj.veh_Fdjh=DictionaryValue.findByCode('qzj_config_fdjh')?.value1
                }
            }else{
                String  maxBH_
                if(type=='harvester_D'||type=='harvester_E'){
                    maxBH_= maxBH.substring(6,11); //取到流水号，从第6位取到11位取5位
                }

                int maxBH_INT=Integer.valueOf(maxBH_)  ;
                if(maxBH_INT<maxNum){ //未达最大值
                    String maxBH_Str=''+  (++maxBH_INT);   //目前值
                    //根据长度添加0
                    if(type=='harvester_D'||type=='harvester_E'){     //
                        for(int i=maxBH_Str.length();i<5;i++){
                            maxBH_Str="0"+maxBH_Str
                        }
                    }
                    veh_Clbh.append(maxBH_Str);
                    obj.properties = HarvestInfo.findByVeh_Clbh(veh_Clbh)?.properties
                    obj.veh_Clbh=veh_Clbh
                    //取到车间配置的发动机号和合格证编号
                    if(type=='harvester_D'){
                        obj.veh_Fdjh=DictionaryValue.findByCode('yms_config_fdjh')?.value1
                    }else if(type=='harvester_E'){
                        obj.veh_Fdjh=DictionaryValue.findByCode('dms_config_fdjh')?.value1
                    }

                }else{ //已达最大值
                    flash.message = '<font color="red">原车辆编号已达最大值</font>'
                }
            }
        }else{
            flash.message = '<font color="red">原车辆编号生成错误</font>'
        }
        if(type=='harvester_D'||type=='harvester_E'){
            lsh= veh_Clbh.substring(6)      //获得流水号
        }
        if(type=='harvester_D'){
            render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:obj,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type:type])
        }else if(type=='harvester_E'){
            render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:obj,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type:type])
        }

    }

    /**
     * @description 合格证保存
     * @Auther zhuwei
     * @createTime 2017-05-05
     * @return
     */
    def save() {
        flash.clear()
        def params= params
        def lsh
        def type=params.type

        def productDh=type.split('_')
        def symbolChar =    productDh[1]

        def  processType=params?.processType
        String  veh_Clbh=params.veh_Clbh
        StringBuilder   veh_start
        veh_start = new StringBuilder('3')
        veh_start.append(DateUtil.getCurrentTime('yy'))
        def start
        def  end
        int  tractor_type
        int FrontSize
//        int BackSize
        if(type=='harvester_D'){
            FrontSize=6
            start=veh_Clbh.substring(0,FrontSize)   //取到 31705YZ/31705DX
            end=veh_Clbh.substring(FrontSize)  //取到流水码
            tractor_type=1
        }else if(type=='harvester_E'){
            FrontSize=6
            start=veh_Clbh.substring(0,FrontSize)   //取到 31705YZ/31705DX
            end=veh_Clbh.substring(FrontSize)  //取到流水码
            tractor_type=2
        }
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)


        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID " +
                "and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}' and s.SYMBOL = '${symbolChar}' "
        println(symbolStr)
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]


        //判定要修改的记录是否存在
        boolean isValid=false;
        def logUser=User.get(loginUser.id)
        def organ = Organization.findByOrganNameE(type)
        HarvestPrintSet harvestPrintSet
        if(logUser&&organ){
            def authStr  = "select * from sys_organ_users where user_id = '${loginUser.id}' and organization_id = '${organ.id}' "
            def template1=new JdbcTemplate(dataSource)
            List authList = template.queryForList(authStr)
            if(authList!=null&&authList.size()!=0){
                isValid=true;
                harvestPrintSet = HarvestPrintSet.findByCodeAndUserid("main",organ?.organCode)
                if (!harvestPrintSet){
                    harvestPrintSet = new HarvestPrintSet()
                    harvestPrintSet.code="main"
                    harvestPrintSet.userid=organ?.organCode
                    harvestPrintSet.save(flush: true)    //如果没有NZ_printSet数据，那么保存
                }
            }
        }else{
            isValid=false;
        }

        int startNum=0;
        int maxNum =0;
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //tractor_T0:T0 或者  tractor_T1:T1
                symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_tractor_ T0  或者 start_tractor_ T1
                symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_tractor_ T0  或者 max_tractor_ T1
            }
        }

        if (symboMap.containsKey(type)){  //正常生产的小拖
            startNum= symboMap.get('start_'+type)
            maxNum= symboMap.get('max_'+type)
            symboMap.put('check_'+type,1);
            isValid=true;
        }else{
            isValid=false;
        }

        int end_int=0
        boolean boundaryCheck=true;
        try {
            end_int=Integer.valueOf(end);   //要保存合格证的流水号
            if (isValid){
                if(end_int<=maxNum){ //未达最大值
                    if(end_int>=startNum){ //未达最大值
                        boundaryCheck=true;
                    }else{ //已达最大值
                        flash.message = '<font color="red">编号已达最小值</font>'
                        boundaryCheck=false;
                    }
                }else{ //已达最大值
                    flash.message = '<font color="red">编号已达最大值</font>'
                    boundaryCheck=false;
                }
            }else{
                flash.message = '<font color="red">编号错误</font>'
                boundaryCheck=false;
            }
        } catch(Exception e){
            flash.message = '<font color="red">功能错误</font>'
            def HarvestInfoInstance = new HarvestInfo(params)
            if(type=='harvester_D'){
                render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:HarvestInfoInstance,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
            }else if(type=='harvester_E'){
                render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:HarvestInfoInstance,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
            }
            return
        }
        if (!boundaryCheck){
            def HarvestInfoInstance = new HarvestInfo(params)
            lsh='流水码超界'
            if(type=='harvester_D'){
                render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:HarvestInfoInstance,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
            }else if(type=='harvester_E'){
                render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:HarvestInfoInstance,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
            }
            return
        }

        //保存农装合格证打印的车型
        if (params.veh_Cx){
            def cx=HarvestCx.findAllByCx(params.veh_Cx)
            if (cx==null||cx.size()==0){   //此情况下保存
                HarvestCx  newCx=new HarvestCx()
                newCx.cx=params.veh_Cx
                newCx.save()
            }
        }

        HarvestInfo  HarvestInfo=HarvestInfo.findByVeh_Clbh(veh_Clbh)
        lsh=veh_Clbh.substring(FrontSize)      //根据前面的固定为去后面的流水号
        if (HarvestInfo){ //合格证编号存在
            if (HarvestInfo.upload=='1'){ //已上传
                flash.message = '<font color="red">合格证已上传,不允许修改</font>'
                if(type=='harvester_D'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:HarvestInfo,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }else if(type=='harvester_E'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:HarvestInfo,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }
                return

            }else{ //未上传
                //判断合格证信息是否正确完整

                def checkMessage=harvestInfoService.checkInfo(new HarvestInfo(params),tractor_type)
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    HarvestInfo.properties = params
                    HarvestInfo.updaterId=loginUser.userName;
                    HarvestInfo.updateTime=DateUtil.getCurrentTime()
                    if (!HarvestInfo.save(flush: true)) {
                        flash.message = '<font color="red">合格证更新失败</font>'
                    }else{
                        flash.message = '合格证更新成功'
                    }
                }
                if(type=='harvester_D'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:HarvestInfo,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }else if(type=='harvester_E'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:HarvestInfo,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }
                return

            }
        }else{ //合格证编号不存在
            int   maxBH_INT=-1
            String maxBH
            if(type=='harvester_D'||type=='harvester_E'){
                maxBH=harvestPrintSet?.max_harvester_upload
            }

            if(maxBH&&maxBH.startsWith(veh_start)){//流水码一年只清一次
                //有上传的最大合格证号时,验证开始是不是3+年份
                String maxBH_
                if(type=='harvester_D'||type=='harvester_E'){
                    maxBH_= maxBH.substring(FrontSize,12);//取到正常生产的合格证的流水号：7到12位，包含7和12位
                }
                maxBH_INT=Integer.valueOf(maxBH_) ;
            }
            if (maxBH_INT==-1||end_int>maxBH_INT){ //合格证跨年，upload合格证是16年的，现在当前生成的是17年的||当前的流水码大于上传的最大合格证流水号，可以保存
                def HarvestInfoInstance = new HarvestInfo(params)
                HarvestInfoInstance.createrId=loginUser.userName
                HarvestInfoInstance.createTime=DateUtil.getCurrentTime()
                HarvestInfoInstance.veh_workshopno=organ?.organCode   //保存生产车间编号
                if(params?.processType=='1'){
                    HarvestInfoInstance.processType=1
                }
                //判断合格证信息格式是否完整正确
                def checkMessage=harvestInfoService.checkInfo(HarvestInfoInstance,tractor_type)
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    if (!HarvestInfoInstance.save(flush: true)){
                        flash.message = '<font color="red">保存失败</font>'
                        }else{
                        //合格证保存成功，需要暂时将流水号最大的合格证保存到农装printSet表中
                        flash.message = '合格证保存成功'
                        boolean needUpdate=true;
                        String max
                        if(type=='harvester_D'||type=='harvester_E'){
                            max=harvestPrintSet?.max_harvester   //printSet里保存的非上传最大合格证号
                        }

                        println(max)
                        if (max&&max.startsWith(veh_start.toString())){
                            String max_str
                            max_str=max.substring(FrontSize);
                            int max_int=Integer.valueOf(max_str)
                            if (end_int<max_int){  //现在的流水号<printset里面的最大流水号 ,流水号不需要更新 ，这样最开始时没有保存流水号也会在执行到这时将起始流水号保存到printSet
                                needUpdate=false;
                            }
                        }
                        if (needUpdate){
                            if(type=='harvester_D'||type=='harvester_E'){
                                harvestPrintSet.max_harvester=HarvestInfoInstance.veh_Clbh   //printSet里保存的非上传最大合格证号，出口车不更新NzprintSet表中的max_tractor值
                            }

                            harvestPrintSet.code='main'
                            harvestPrintSet.userid= organ?.organCode
                            if (!harvestPrintSet.save(flush: true)){
                                flash.message = '<font color="red">流水号保存失败</font>'
                            }else{
                                println(harvestPrintSet?.max_harvester)
                            }
                        }
                    }
                }
                lsh=HarvestInfoInstance.veh_Clbh.substring(FrontSize)
                if(type=='harvester_D'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:HarvestInfoInstance,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }else if(type=='harvester_E'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:HarvestInfoInstance,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }
                return
            }else{ //不允许保存
                flash.message = '合格证编号小于已上传合格证流水码的最大值,不允许保存'
                def HarvestInfoInstance = new HarvestInfo(params)
                lsh=params.veh_Clbh.substring(FrontSize)
                if(type=='harvester_D'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:HarvestInfoInstance,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }else if(type=='harvester_E'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:HarvestInfo,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                }
                return
            }
        }
    }
    /**
     * @description 合格证编号加减1处理
     * @Auther zhuwei
     * @createTime 2014-07-05
     * @param  veh_Clbh:底盘号；frontSize：合格证前面固定位数 ；BehindSize：后面流水码位数
     * @return
     */
    def returnClbh(def veh_Clbh,int FrontSize,int BehindSize){
        def veh_Clbh1
        veh_Clbh1=Integer.parseInt(veh_Clbh.substring(FrontSize))+1
        def veh_ClbhS= veh_Clbh1.toString()     //流水号
        //不足六位的以0来补位
        def Z0 ='0'
            if(veh_ClbhS.length()<BehindSize){
                for(int i=1;i<(BehindSize-veh_ClbhS.length());i++){
                    Z0+='0'
                }
                Z0+=veh_ClbhS
            }


        if(veh_ClbhS.length()<BehindSize){                  //正常生产的veh_ClbhS会小于BehindSize，超标车的BehindSize为0
            veh_Clbh=veh_Clbh.replace(veh_Clbh.substring(FrontSize),Z0)
        }else{
            veh_Clbh=veh_Clbh.replace(veh_Clbh.substring(FrontSize),veh_ClbhS)   //超标车的veh_ClbhS会小于BehindSize，或者流水号为5位 ，超标车直接替换流水码

        }
        return  veh_Clbh
    }

    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }

    /**
     * @description 根据条件查询合格证
     * @Auther zhuwei
     * @createTime 2014-07-05
     */
    def search(){
        flash.clear()
        def lsh
        def params= params
        def type=params.type
        def processType=params?.processType
        def start
        def  end
        int  tractor_type
        int FrontSize
        int BehindSize   //int默认是0
        def beforeClbh=params.veh_Clbh
        def productDh=type.split('_')
        def symbolChar =    productDh[1]

        def workShopNo
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        if(type=='harvester_D'||type=='harvester_E'){
            FrontSize=6
            BehindSize=5
        }
        //打印完成后，将车辆编号替换为流水号+1的流水号
        if(params.intI&&params.veh_Clbh){
            params.veh_Clbh=returnClbh(params.veh_Clbh,FrontSize,BehindSize)
        }
        //恢复编号按钮的操作
        if(params.recover){
            def user=User.get(loginUser.id)
            def codeList=''
            def organ = Organization.findByOrganNameE(type)
            HarvestPrintSet harvestPrintSet=HarvestPrintSet.findByCodeAndUserid('main',organ?.organCode)
            if(harvestPrintSet){
                    if(harvestPrintSet.max_harvester){
                        print (harvestPrintSet.max_harvester)
                        params.veh_Clbh=returnClbh(harvestPrintSet.max_harvester,FrontSize,BehindSize)
                    }

            }else{
                if(type=='harvester_D'||type=='harvester_E'){
                    return harvest()
                }
            }
        }
        if(params.veh_Clbh){ //params.veh_Clbh：打印完成后该值为传到该方法veh_Clbh的+1后的值；点击恢复后也为传到该方法veh_Clbh的+1后的值
            //递增递减就是传来的veh_clbh本身的值，查看也是本身的值
            String  veh_Clbh=params.veh_Clbh;
            start=veh_Clbh.substring(0,FrontSize)   //取到开始字符串
            end=veh_Clbh.substring(FrontSize)       //取到流水号

            try {
                int end_int=Integer.valueOf(end)
                if(params.direction=='1'){// 向前
                    end_int++   //超标车不需要空位补零，超标车从30000开始
                    String end_str=''+end_int;   //目前值

                    if(type=='harvester_D'||type=='harvester_E'){
                        for(int i=end_str.length();i<BehindSize;i++){
                            end_str="0"+end_str
                        }
                    }
                    veh_Clbh= start+  end_str;
                }else if (params.direction=='-1'){  //向后
                    end_int--
                    String end_str=''+end_int;   //目前值
                    if(type=='harvester_D'||type=='harvester_E'){
                        for(int i=end_str.length();i<BehindSize;i++){
                            end_str="0"+end_str
                        }
                    }

                    veh_Clbh= start+  end_str;
                }

                def cel = {
                    eq("veh_Clbh",veh_Clbh)
                }
                def results = HarvestInfo.createCriteria().list(cel)
                //人员 组织 车间 三者进行关联
                //人员 组织 车间 三者进行关联
                String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID " +
                        "and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}' and s.SYMBOL = '${symbolChar}' "
                println(symbolStr)
                def template=new JdbcTemplate(dataSource)
                List symbolList = template.queryForList(symbolStr)
                def symboMap=[:]
                //判定要修改的记录是否存在
                boolean isValid=false;
                //判定要修改的记录是否存在
                def logUser=User.get(loginUser.id)
                def organ = Organization.findByOrganNameE(type)
                HarvestPrintSet harvestPrintSet
                if(logUser&&organ){
                    def authStr  = "select * from sys_organ_users where user_id = '${loginUser.id}' and organization_id = '${organ.id}' "
                    def template1=new JdbcTemplate(dataSource)
                    List authList = template.queryForList(authStr)
                    if(authList!=null&&authList.size()!=0){
                        isValid=true;
                        harvestPrintSet = HarvestPrintSet.findByCodeAndUserid("main",organ?.organCode)
                        if (!harvestPrintSet){
                            harvestPrintSet = new HarvestPrintSet()
                            harvestPrintSet.code="main"
                            harvestPrintSet.userid=organ?.organCode
                            harvestPrintSet.save(flush: true)    //如果没有NZ_printSet数据，那么保存
                        }
                    }
                }else{
                    isValid=false;
                }
                if(results==null||results.size()==0){ //如果没有查到合格证信息
                    HarvestInfo obj=null
                    if(params.intI){
                        def celBefore = {
                            eq("veh_Clbh",beforeClbh)
                        }
                        def zc = HarvestInfo.createCriteria().list(celBefore).get(0)
                        obj=new HarvestInfo()
                        obj.properties=zc.properties
                        obj.veh_Clbh=params.veh_Clbh
                        obj.veh_Cx=''
                        //取到车间配置的发动机号和合格证编号
                        if(type=='harvester_D'){
                            obj.veh_Fdjh=DictionaryValue.findByCode('yms_config_fdjh')?.value1
                        }else if(type=='harvester_E'){
                            obj.veh_Fdjh=DictionaryValue.findByCode('dms_config_fdjh')?.value1
                        }
                        //如果是超标车，取到超标车的出厂日期
//                        if(type=='tractor_TC'){
//                            obj.veh_Ccrq=DictionaryValue.findByCode('CB_ccrq')?.value1
//                        }
                    }else{
                        obj=new HarvestInfo();
                    }

                    int startNum=0;
                    int maxNum =0;
                    //###################################################################### start
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbol in symbolList){
                            symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //tractor_T0:T0 或者  tractor_T1:T1
                            symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_tractor_ T0  或者 start_tractor_ T1
                            symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_tractor_ T0  或者 max_tractor_ T1
                        }
                    }

                    if (symboMap.containsKey(type)){  //正常生产的小拖
//                        startNum= symboMap.start_tractor_T0
                        startNum= symboMap.get('start_'+type)
                        maxNum= symboMap.get('max_'+type)
                        symboMap.put('check_'+type,1);
                        isValid=true;
                    }else{
                        isValid=false;
                    }

                    boolean haserror=false;
                    if(params.direction||params.recover||params.intI){  //递增减、恢复编号、查看该编号的合格证且其为保存的，将配置值带出来
                        //递增减、恢复查看流水号加减1后流水号是不是合法；由打印自动递增生成合格证编号不再在此处校验流水号，因为在保存时会自动校验
                        if (isValid){
                            if(end_int<=maxNum){ //未达最大值
                                if(end_int>=startNum){ //未达最大值
                                    obj.veh_Clbh=veh_Clbh
                                    obj.veh_Fdjh=DictionaryValue.findByCode('fdjh_config')?.value1
                                    if(type=='harvester_D'){
                                        obj.veh_Fdjh=DictionaryValue.findByCode('yms_config_fdjh')?.value1
                                    }else if(type=='harvester_E'){
                                        obj.veh_Fdjh=DictionaryValue.findByCode('dms_config_fdjh')?.value1
                                    }
                                }else{ //已达最大值
                                    haserror=true;
                                    flash.message = '<font color="red">编号已达最小值</font>'
                                }
                            }else{ //已达最大值
                                haserror=true;
                                flash.message = '<font color="red">编号已达最大值</font>'
                            }
                        }else{
                            haserror=true;
                            flash.message = '<font color="red">编号生成错误</font>'
                        }
                    }else{
                        haserror=true;
                        flash.message = '<font color="red">未找到该编号的合格证</font>'
                    }
                    if (haserror){
                        obj = HarvestInfo.findByVeh_Clbh(params.veh_Clbh)
                        if (obj==null){
                            if(params.intI){
                                def celBefore = {
                                    eq("veh_Clbh",beforeClbh)
                                }
                                def zc = HarvestInfo.createCriteria().list(celBefore).get(0)
                                obj=new HarvestInfo()
                                obj.properties=zc.properties
                                obj.veh_Clbh=params.veh_Clbh
                                obj.veh_Cx=''
                            }else{
                                obj=new HarvestInfo();
                                obj.veh_Clbh=params.veh_Clbh;
                            }
                        }
                    }
                    if(type=='harvester_D'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:obj,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                    }else if(type=='harvester_E'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:obj,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                    }
                    return
                }else if (results.size()==1){   //递增递减查看、或者按照车辆编号查看
                    HarvestInfo obj=results.get(0);
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbol in symbolList){
                            symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //tractor_T0:T0 或者  tractor_T1:T1
                            symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_tractor_ T0  或者 start_tractor_ T1
                            symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_tractor_ T0  或者 max_tractor_ T1
                        }
                    }
                    if(type=='harvester_D'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_yms',model:[HarvestInfoInstance:obj,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                    }else if(type=='harvester_E'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/harvest_qzj',model:[HarvestInfoInstance:obj,symboMap:symboMap,harvestPrintSet:harvestPrintSet,lsh:lsh,type: type])
                    }
                    return
                }
            } catch(Exception e){
                flash.message = '初始化车辆信息出错'
                if(type=='harvester_D'||type=='harvester_E'){
                    return harvest()
                }

            }
        }else{
            flash.message = '请输入合格证编号'
            if(type=='harvester_D'||type=='harvester_E'){
                return harvest()
            }
        }
    }

    /**
     *
     * @Description 取得车型
     * @author zhuwei
     * @createTime 2014-08-18
     * @return
     */
    def jsonCx() {
        def cx=HarvestCx.findAll();
        render(contentType: "text/json") {
            array {
                for (m in cx) {
                    pack(text:m.cx ,value:m.cx)
                }
            }
        }
    }

    /**
     * @Description 打印时将打印状态修改
     * @CreateTime 2017-05-10
     * @Author zhuwei
     * @return
     */
    def print() {
        def aa=params
        def result=[:]
        def msg="保存成功！"
        def flag='successed'
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        String curTime=DateUtil.getCurrentTime()

        def hgz=HarvestInfo.findByVeh_Clbh(params.veh_Clbh)
        hgz.properties= params
        hgz.isprint=params.isprint   //保存打印标记
        hgz.updaterId=loginUser.userName;
        hgz.updateTime=curTime


        if (!hgz.save()) {
            msg="合格证信息更新失败！"
            flag='failed'
            result.put("msg",msg)
            result.put("flag",flag)
            return render (result as JSON)
        }else{
            msg="合格证信息更新成功！"
            flag='successed'
            result.put("msg",msg)
            result.put("flag",flag)
//            def rePlaceZcinfo=NZInfoReplace.get(params?.replaceNzZcinfoId)
//            if(rePlaceZcinfo){
//                rePlaceZcinfo.isPrint='1' //更新更换记录
//                if(!rePlaceZcinfo.save(flush: true)){
//                    flash.message = '合格证更换记录保存失败'
//                }
//            }
            return render (result as JSON)
        }
        render result
    }

    /**
     *@Descriptions 车间 >> 合格证信息查询（主跳转）
     *@Auther zhuwei
     *@createTime  2014-07-11
     */
    def searchIndex()
    {
        render(view:'/cn/com/wz/vehcert/zcinfo/harvestinfo/search_index')
    }
    /**
     * @Description 获取合格证信息【查询页面使用】
     * @Author zhuwei
     * @return
     * @Create  2017-05-10
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def result=harvestInfoService.selectByParams(params)
        render result as JSON

    }

    /**
     *  @ author zhuwei
     *  @Update 更新导出所有数据
     *  @UpdateTime zhuwei
     */
    def export_search(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
            if(params?.format && params.format != "html"){
                def encodedFilename = URLEncoder.encode(("合格证信息（车间）"), "UTF-8")
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

                Map labels = ["veh_Clbh":"车辆编号","veh_Cx":"车型","veh_Fdjh":"发动机号","veh_Dph":"底盘号" ,"veh_Hgzbh":"合格证编号"
                        ,"veh_Ccrq":"出厂日期","createTime":"创建时间","createrId":"创建ID","updateTime":"最后更新时间"
                        ,"updaterId":"最后更新人","isprint":"是否已打印 0：未打印 1：已打印",
                        "SAP_No":"SAP唯一号","SAP_status":"SAP传输状态 0：为传输 1：已传输","transDate":"传输时间"]
                def out=response.outputStream
                def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
                def loginUser=User.get(user.id)


                def result=harvestInfoService.selectByParams(params)
                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                def m=[]
                m.add(labels)
                content.add(result.rows)
                excelOp.preWriteExcel(out,null,m,["合格证信息（车间）"],content)
                session.setAttribute('compFlag',"success")
                out.flush()
                out.close()
            }
        }catch(Exception e){
            e.cause.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
            rows.clear()
            lst.clear()
            content.clear()
        }
    }
}
