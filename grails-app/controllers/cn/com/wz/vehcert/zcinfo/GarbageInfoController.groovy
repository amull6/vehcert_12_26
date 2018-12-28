package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.system.dictionary.DictionaryValue
import grails.converters.JSON
import groovy.sql.Sql
import org.springframework.core.io.support.PropertiesLoaderUtils
import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.ExcelUtils

class GarbageInfoController extends BaseController {

    def dataSource
    def sqlService
    def garbageInfoService
    def sqlToolService
    //    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    /**
     * @Description 进入合格证list页面
     * @Create 2014-07-10
     * @Author zhuwei
     */
    def index(){
        render(view: "/cn/com/wz/vehcert/zcinfo/nzzcinfo/index_NZ",model:[params:params])
    }
    /**
     * @Description 合格证上传道1.25列表，获取【未提交的合格证信息】
     * @author zhuwei
     * @return
     * @Create  2014-07-11
     * Update 合格证上传时，获取合格证生产车间编号等于当前用户所属组织编号的合格证
     * UpdateTime 2014-10-28 zhuwei
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def organCode=[]
        if(loginUser.organs.size()>0){
            loginUser.organs.each{
                if(it.organCode){
                    organCode.add(it.organCode)
                }
            }
            params.organCode=organCode
        }
        def lst=nzInfoService.selectGarbageInfoList(params)
        render ([rows: lst,total: lst.totalCount] as JSON )

    }
    /**
     *@Descriptions 车间 >> 合格证信息查询（主跳转）
     *@Auther zhuwei
     *@createTime  2014-07-11
     */
    def nzinfoBySearchIndex()
    {
        render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/search_NZ')
    }

    /**
     * @Description 获取合格证信息【查询页面使用】
     * @Author zhuwei
     * @return
     * @Create  2014-07-11
     * Update 合格证查询时，获取合格证生产车间编号等于当前用户所属组织编号的合格证
     * UpdateTime 2014-10-28 zhuwei
     */
    def jsonListByShop(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)

        if(loginUser.organs.size()>0){
            def organsCodeStr=new StringBuffer("(")
            def organCodeList=[]
            loginUser.organs.each{
                if(it.organCode){
                    organsCodeStr.append("'"+it.organCode+"',")       // ('202282','202343','202581')
                    organCodeList.add(it.organCode)
                }
            }
            organsCodeStr = organsCodeStr.substring(0,organsCodeStr.lastIndexOf(','))
            organsCodeStr=organsCodeStr+')'
            params.organsCodeStr=organsCodeStr
            params.organCodeList=organCodeList
         }

        def result=nzInfoService.selectNZinfoByShop(params)
        render result as JSON

    }


    /**
     * @Description 小拖合格证生成页面，包括正常生产的小拖和超标车
     * @CreateTime 2014-06-28
     * @Author zhuwei
     */
    def garbage(){
        flash.clear()
        def type=params.type
        def productDh=type.split('_')
        def workShopNo=params.workShopNoveh_Clbh1.toString()
        StringBuilder   veh_Clbh
        def lsh
        if(type=='garbage_1'){       //正常生产的小拖
               veh_Clbh=new StringBuilder('4');
               veh_Clbh.append(DateUtil.getCurrentTime('yyMM'));
        }
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        GarbageInfo obj=new GarbageInfo()
        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        //取出
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //garbage_T0:T0 或者  garbage_T1:T1
                symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_garbage_ T0  或者 start_garbage_ T1
                symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_garbage_ T0  或者 max_garbage_ T1
            }
        }
        boolean isValid=true;
        int startNum=0;
        int maxNum =0;
        if (symboMap.containsKey(type)){  //正常生产的垃圾站
//            startNum= symboMap.start_garbage_T0
            startNum= symboMap.get('start_'+type)
            maxNum= symboMap.get('max_'+type)
            symboMap.put('check_'+type,1);
            isValid=true;
        }else{
            isValid=false;
        }
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def GarbagePrintSet = GarbagePrintSet.findByCodeAndUserid("main",codeList)
        if (!GarbagePrintSet){
            GarbagePrintSet = new GarbagePrintSet()
            GarbagePrintSet.code="main"
            GarbagePrintSet.userid=codeList
            GarbagePrintSet.save(flush: true)    //如果没有NZ_printSet数据，那么保存
        }
        if (isValid){
            String maxBH='';
            boolean formTop=false;
            if (GarbagePrintSet){
                //若最大值存在，则判断是否跟当前规则相同 若相同则   从最大值开始 若不同则从最小值开始。
                if(type=='garbage_1'){
                    maxBH= GarbagePrintSet.max_garbage
                }

                if (maxBH){      //如果printSet有已打印的合格证编号,先验证
                    if (maxBH.startsWith(veh_Clbh.toString())){  //先验证开始是不是3+年份+月份。7.31保存的31407，8月的veh_Clbh就是301408，随意每月开始都会从起始流水码开始
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
                obj.properties = GarbageInfo.findByVeh_Clbh(veh_Clbh)?.properties
                veh_Clbh.append(productDh[1]);
                obj.veh_Clbh=veh_Clbh
                obj.veh_Cx =DictionaryValue.findByCode('mainCx_config')?.value1
                obj.veh_Cpmc =DictionaryValue.findByCode('mainMc_config')?.value1
                obj.veh_Zxbz =DictionaryValue.findByCode('Zxbz_config')?.value1
                //取到车间配置的发动机号和合格证编号
                obj.veh_Hgzbh=DictionaryValue.findByCode('garbagehgzbh_config')?.value1
            }else{
                String  maxBH_
                maxBH_= maxBH.substring(5,10); //取到流水号，从第7位取到12位取5位
                int maxBH_INT=Integer.valueOf(maxBH_)  ;
                if(maxBH_INT<maxNum){ //未达最大值
                String maxBH_Str=''+  (++maxBH_INT);   //目前值
                for(int i=maxBH_Str.length();i<5;i++){
                    maxBH_Str="0"+maxBH_Str
                 }
                veh_Clbh.append(maxBH_Str);
                obj.properties = GarbageInfo.findByVeh_Clbh(veh_Clbh)?.properties
                veh_Clbh.append(productDh[1]);
                obj.veh_Clbh=veh_Clbh
                obj.veh_Cx =''
                obj.veh_Cpmc =DictionaryValue.findByCode('mainMc_config')?.value1
                obj.veh_Zxbz =DictionaryValue.findByCode('Zxbz_config')?.value1
                //取到车间配置的发动机号和合格证编号
                obj.veh_Hgzbh=DictionaryValue.findByCode('garbagehgzbh_config')?.value1
                }else{ //已达最大值
                    flash.message = '<font color="red">合格证编号已达最大值</font>'
                }
            }
        }else{
            flash.message = '<font color="red">合格证编号生成错误</font>'
        }
            lsh= veh_Clbh.substring(5,10);      //获得流水号

        render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:obj,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,lsh:lsh,type:type])
    }

    /**
     * @description 合格证保存
     * @Auther zhuwei
     * @createTime 2014-07-03
     * @return
     * @update 中拖、大拖的出口车不更新打印设置表中的已打印最大号
     */
    def save() {
        flash.clear()
        def params= params
        def lsh
        def type=params.type
        def  processType=params?.processType
        String  veh_Clbh=params.veh_Clbh
        def start
        def  end
        int  garbage_type
        int FrontSize
        int middleSize
        int behindSize
//        int BackSize
        if(type=='garbage_1'){
            FrontSize=5
            middleSize=5
            start=veh_Clbh.substring(0,FrontSize)   //取到 31410TX
            end=veh_Clbh.substring(FrontSize,FrontSize+middleSize)  //取到流水码
            garbage_type=1

        }
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def workShop=''
        def organs=User.get(loginUser.id).organs
        organs.each {
            workShop=it.organCode     //车间端打印合格证的帐号只能管理一个车间组织
        }

        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,w.SERIAL_NUM as serialNum,w.name as name,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]


        //获取打印参数
        def GarbagePrintSet = GarbagePrintSet.findByCodeAndUserid("main",workShop)
        if (!GarbagePrintSet){
            GarbagePrintSet = new GarbagePrintSet()
            GarbagePrintSet.code="main"
            GarbagePrintSet.userid=workShop
        }

        boolean isValid=false;
        int startNum=0;
        int maxNum =0;
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //garbage_T0:T0 或者  garbage_T1:T1
                symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_garbage_ T0  或者 start_garbage_ T1
                symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_garbage_ T0  或者 max_garbage_ T1
            }
        }

        if (symboMap.containsKey(type)){
//            veh_Zchgzbh.append(symboMap.get('garbage_T0'));
            startNum= symboMap.get('start_'+type)
//            startNum= symboMap.start_garbage_T0
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
            def GarbageInfoInstance = new GarbageInfo(params)
            if(type=='garbage_1'){
                render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:GarbageInfoInstance,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,lsh:lsh,type: type])
            }
                return
        }
        if (!boundaryCheck){
            def GarbageInfoInstance = new GarbageInfo(params)
            lsh='流水码超界'
            if(type=='garbage_1'){
                render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:GarbageInfoInstance,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,lsh:lsh,type: type])
            }
                return
        }

        //保存垃圾站合格证打印的车型
        if (params.veh_Cx){
            def cx=GarbageCx.findAllByCx(params.veh_Cx)
            if (cx==null||cx.size()==0){   //此情况下保存
                GarbageCx  garbageCx=new GarbageCx()
                garbageCx.cx=params.veh_Cx
                garbageCx.save()
            }
        }
        //保存垃圾站合格证打印的产品名称
        if (params.veh_Cpmc){
            def cpmc=GarbageCpmc.findAllByCpmc(params.veh_Cpmc)
            if (cpmc==null||cpmc.size()==0){   //此情况下保存
                GarbageCpmc  garbageCpmc=new GarbageCpmc()
                garbageCpmc.cpmc=params.veh_Cpmc
                garbageCpmc.save()
            }
        }
        GarbageInfo  GarbageInfo=GarbageInfo.findByVeh_Clbh(veh_Clbh)
        lsh=veh_Clbh.substring(FrontSize,10)      //根据前面的固定为去后面的流水号
        if (GarbageInfo){ //合格证编号存在
            if (GarbageInfo.upload=='1'){ //已上传
                flash.message = '<font color="red">合格证已上传,不允许修改</font>'
                       if(type=='garbage_1'){
                           render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:GarbageInfo,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,lsh:lsh,type: type])
                       }
                return

            }else{ //未上传
                //判断合格证信息是否正确完整

                def checkMessage=garbageInfoService.checkInfo(new GarbageInfo(params),garbage_type)
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    GarbageInfo.properties = params
                    GarbageInfo.updaterId=loginUser.userName;
                    GarbageInfo.updateTime=DateUtil.getCurrentTime()
                    if (!GarbageInfo.save(flush: true)) {
                        flash.message = '<font color="red">合格证更新失败</font>'
                    }else{
                            flash.message = '合格证更新成功'
                        }
                }
                if(type=='garbage_1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:GarbageInfo,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,lsh:lsh,type: type])
                }
                return

            }
        }else{ //合格证编号不存在
            int   maxBH_INT=-1
            String maxBH
                maxBH=GarbagePrintSet?.max_garbage_upload
                if(maxBH&&maxBH.startsWith(start)){
                  //有上传的最大合格证号时,验证开始是不是3+年份+月份。如7.31保存的31407，8月的veh_Clbh就是301408，随意每月开始都会从起始流水码开始
                    String maxBH_
                    maxBH_= maxBH.substring(FrontSize,10);//取到正常生产的合格证的流水号：5到10位
                    maxBH_INT=Integer.valueOf(maxBH_) ;
            }
            if (maxBH_INT==-1||end_int>maxBH_INT){ //合格证跨月，upload合格证是7月的，现在当前声场的是8月的||当前的流水码大于上传的最大合格证流水号，可以保存
                 def GarbageInfoInstance = new GarbageInfo(params)
                GarbageInfoInstance.createrId=loginUser.userName
                GarbageInfoInstance.createTime=DateUtil.getCurrentTime()
                GarbageInfoInstance.veh_workshopno=workShop   //保存生产车间编号
                if(params?.processType=='1'){
                    GarbageInfoInstance.processType=1
                }
                //判断合格证信息格式是否完整正确
                def checkMessage=garbageInfoService.checkInfo(GarbageInfoInstance,garbage_type)
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    if (!GarbageInfoInstance.save(flush: true)){
                        flash.message = '<font color="red">保存失败</font>'
5                    }else{
                          //合格证保存成功，需要暂时将流水号最大的合格证保存到农装printSet表中
                        flash.message = '合格证保存成功'
                            boolean needUpdate=true;
                            String max
                            max=GarbagePrintSet?.max_garbage   //printSet里保存的非上传最大合格证号

                            println(max)
                            if (max&&max.startsWith(start)){
                                String max_str
                                    max_str=max.substring(FrontSize,10);
                                int max_int=Integer.valueOf(max_str)
                                if (end_int<max_int){  //现在的流水号<printset里面的最大流水号 ,流水号不需要更新 ，这样最开始时没有保存流水号也会在执行到这时将起始流水号保存到printSet
                                    needUpdate=false;
                                }
                            }
                            if (needUpdate){
                                    if(params?.processType=='0'||type=='garbage_1'){  //大、中拖中的0是正常打印的合格证，1为出口车||正常生产的小拖
                                        GarbagePrintSet.max_garbage=GarbageInfoInstance.veh_Clbh   //printSet里保存的非上传最大合格证号，出口车不更新NzprintSet表中的max_garbage值
                                    }
                                GarbagePrintSet.code='main'
                                GarbagePrintSet.userid= workShop
                                if (!GarbagePrintSet.save(flush: true)){
                                    flash.message = '<font color="red">流水号保存失败</font>'
                                }else{
                                    println(GarbagePrintSet?.max_garbage)
                                }
                            }
                    }
                }
                lsh=GarbageInfoInstance.veh_Clbh.substring(FrontSize,10)
                if(type=='garbage_1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:GarbageInfoInstance,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,lsh:lsh,type: type])
                }
                    return
            }else{ //不允许保存
                flash.message = '合格证编号小于已上传合格证流水码的最大值,不允许保存'
                def GarbageInfoInstance = new GarbageInfo(params)
                lsh=params.veh_Clbh.substring(FrontSize,10)
                if(type=='garbage_1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:GarbageInfoInstance,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,lsh:lsh,type: type])
                }
                    return
            }
        }
    }
    /**
     * @description 合格证编号加减1处理
     * @Auther zhuwei
     * @createTime 2014-07-05
     * @param  veh_Clbh:底盘号；frontSize：合格证前面固定位数 ；MiddleSize：后面流水码位数
     * @return
     */
    def returnClbh(def veh_Clbh,int FrontSize,int MiddleSize,int BehindSize,int type){
        def veh_Clbh1
            veh_Clbh1=Integer.parseInt(veh_Clbh.substring(FrontSize,FrontSize+MiddleSize))+1
        def veh_ClbhS= veh_Clbh1.toString()     //流水号
//        def aa=FrontSize
        //不足wu位的以0来补位
        def Z0 ='0'
        if(type.toString()!='2'){    //如果不是超标车,超标车不需要补0
            if(veh_ClbhS.length()<MiddleSize){
                for(int i=1;i<(MiddleSize-veh_ClbhS.length());i++){
                    Z0+='0'
                }
                Z0+=veh_ClbhS
            }
        }

        if(veh_ClbhS.length()<MiddleSize){
            veh_Clbh=veh_Clbh.replace(veh_Clbh.substring(FrontSize,FrontSize+MiddleSize),Z0)
        }else{
                veh_Clbh=veh_Clbh.replace(veh_Clbh.substring(FrontSize,FrontSize+MiddleSize),veh_ClbhS)

        }
        return  veh_Clbh
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
        def end
        int garbage_type
        int FrontSize
        int MiddleSize
        int BehindSize   //int默认是0
        def beforeClbh=params.veh_Clbh
        def workShopNo
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        if(type=='garbage_1'){
            FrontSize=5          //垃圾站
            MiddleSize=5
            BehindSize=1
            garbage_type=1
        }
        //打印完成后，将车辆编号替换为流水号+1的流水号
        if(params.intI&&params.veh_Clbh){
            params.veh_Clbh=returnClbh(params.veh_Clbh,FrontSize,MiddleSize,BehindSize,garbage_type)
        }
        //回复按钮的操作

        if(params.recover){
            def user=User.get(loginUser.id)
            def codeList=''
            user.organs.each{
                codeList=it.organCode
            }
            GarbagePrintSet GarbagePrintSet=GarbagePrintSet.findByCodeAndUserid('main',codeList)
            if(GarbagePrintSet){
                if(params.veh_Clbh.startsWith('4')){
                    if(GarbagePrintSet.max_garbage){
                        print GarbagePrintSet.max_garbage
                        params.veh_Clbh=returnClbh(GarbagePrintSet.max_garbage,FrontSize,MiddleSize,BehindSize,garbage_type)
                    }
                }
            }else{
                   if (type=='garbage_1'){
                       return garbage(params)
                   }
            }
        }
        if(params.veh_Clbh){ //params.veh_Clbh：打印完成后该值为传到该方法veh_Clbh的+1后的值；点击恢复后也为传到该方法veh_Clbh的+1后的值
                             //递增递减就是传来的veh_clbh本身的值，查看也是本身的值
            String  veh_Clbh=params.veh_Clbh;
            start=veh_Clbh.substring(0,FrontSize)   //取到开始字符串
            end=veh_Clbh.substring(FrontSize,FrontSize+MiddleSize)       //取到流水号
            workShopNo=veh_Clbh.substring(FrontSize+MiddleSize)

            try {
                int end_int=Integer.valueOf(end)
                if(params.direction=='1'){// 向前
                    end_int++   //超标车不需要空位补零，超标车从30000开始
                    String end_str=''+end_int;   //目前值
                    for(int i=end_str.length();i<MiddleSize;i++){
                        end_str="0"+end_str
                    }
                    veh_Clbh= start+  end_str+workShopNo;
                }else if (params.direction=='-1'){  //向后
                    end_int--
                    String end_str=''+end_int;   //目前值
                    for(int i=end_str.length();i<MiddleSize;i++){
                        end_str="0"+end_str
                    }
                    veh_Clbh= start+  end_str+workShopNo;
                }

                def cel = {
                    eq("veh_Clbh",veh_Clbh)
                }
                def results = GarbageInfo.createCriteria().list(cel)
                //人员 组织 车间 三者进行关联
                String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
                def template=new JdbcTemplate(dataSource)
                List symbolList = template.queryForList(symbolStr)
                def symboMap=[:]
                //判定要修改的记录是否存在
                def user=User.get(loginUser.id)
                def codeList=''
                user.organs.each{
                    codeList=it.organCode
                }
                GarbagePrintSet GarbagePrintSet=GarbagePrintSet.findByCodeAndUserid('main',codeList)
//                def   printSet=null;
                if (!GarbagePrintSet){
                    GarbagePrintSet = new GarbagePrintSet()
                    GarbagePrintSet.code='main'
                    GarbagePrintSet.userid=codeList
                }
                if(results==null||results.size()==0){ //如果没有查到合格证信息
                    GarbageInfo obj=null
                    if(params.intI){
                        def celBefore = {
                            eq("veh_Clbh",beforeClbh)
                        }
                        def zc = GarbageInfo.createCriteria().list(celBefore).get(0)
                        obj=new GarbageInfo()
                        obj.properties=zc.properties
                        obj.veh_Clbh=params.veh_Clbh
                        obj.veh_Cx =''
                        obj.veh_Cpmc =DictionaryValue.findByCode('mainMc_config')?.value1
                        obj.veh_Zxbz =DictionaryValue.findByCode('Zxbz_config')?.value1
                        //取到车间配置的发动机号和合格证编号
                        obj.veh_Hgzbh=DictionaryValue.findByCode('garbagehgzbh_config')?.value1
                        //如果是超标车，取到超标车的出厂日期
                    }else{
                        obj=new GarbageInfo();
                    }

                    boolean isValid=false;
                    int startNum=0;
                    int maxNum =0;
                    //###################################################################### start
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbol in symbolList){
                            symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //garbage_T0:T0 或者  garbage_T1:T1
                            symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_garbage_ T0  或者 start_garbage_ T1
                            symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_garbage_ T0  或者 max_garbage_ T1
                        }
                    }

                    if (symboMap.containsKey(type)){  //正常生产的小拖
//                        startNum= symboMap.start_garbage_T0
                        startNum= symboMap.get('start_'+type)
                        maxNum= symboMap.get('max_'+type)
                        symboMap.put('check_'+type,1);
                        isValid=true;
                    }else{
                        isValid=false;
                    }

                    boolean haserror=false;
                    if(params.direction||params.recover||(!params.intI)){  //递增减、恢复编号、查看该编号的合格证且其为保存的，将配置值带出来
                    //递增减、恢复查看流水号加减1后流水号是不是合法；由打印自动递增生成合格证编号不再在此处校验流水号，因为在保存时会自动校验
                        if (isValid){
                            if(end_int<=maxNum){ //未达最大值
                                if(end_int>=startNum){ //未达最大值
                                    obj.veh_Clbh=veh_Clbh
                                    obj.veh_Cx =''
                                    obj.veh_Cpmc =DictionaryValue.findByCode('mainMc_config')?.value1
                                    obj.veh_Zxbz =DictionaryValue.findByCode('Zxbz_config')?.value1
                                    //取到车间配置的发动机号和合格证编号
                                    obj.veh_Hgzbh=DictionaryValue.findByCode('garbagehgzbh_config')?.value1
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
                    }
                    if (haserror){
                        obj = GarbageInfo.findByVeh_Clbh(params.veh_Clbh)
                        if (obj==null){
                            if(params.intI){
                                def celBefore = {
                                    eq("veh_Clbh",beforeClbh)
                                }
                                def zc = GarbageInfo.createCriteria().list(celBefore).get(0)
                                obj=new GarbageInfo()
                                obj.properties=zc.properties
                                obj.veh_Clbh=params.veh_Clbh
                                obj.veh_Cx =''
                                //取到车间配置的发动机号和合格证编号
                            }else{
                                obj=new GarbageInfo();
                                obj.veh_Clbh=params.veh_Clbh;
                                obj.veh_Cpmc =DictionaryValue.findByCode('mainMc_config')?.value1
                                obj.veh_Zxbz =DictionaryValue.findByCode('Zxbz_config')?.value1
                                //取到车间配置的发动机号和合格证编号
                                obj.veh_Hgzbh=DictionaryValue.findByCode('garbagehgzbh_config')?.value1
                            }
                        }
                    }
                    render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:obj,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,type:type])
                    return
                }else if (results.size()==1){   //递增递减查看、或者按照车辆编号查看
                    GarbageInfo obj=results.get(0);
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbol in symbolList){
                            symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //garbage_T0:T0 或者  garbage_T1:T1
                            symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_garbage_ T0  或者 start_garbage_ T1
                            symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_garbage_ T0  或者 max_garbage_ T1
                        }
                    }
                    render(view:'/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStation',model:[GarbageInfoInstance:obj,symboMap:symboMap,GarbagePrintSet:GarbagePrintSet,type:type])
                    return
                }
            } catch(Exception e){
                flash.message = '初始化车辆信息出错'
                    return garbage(params)

            }
        }else{
            flash.message = '请输入合格证编号'
                return garbage(params)
        }
    }



    /**
     * @Description 农装合格证信息从车间同步到总服务器
     * @CreateTime 2014-07-05
     * @Author zhuwei
     */
    def uploadToServer(){
        def msg=""
        try{
            //获取同步数据源
            def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
            def remoteUrl= dsp.getProperty("remoteUrl")
            def remoteUserName=dsp.getProperty("remoteUserName")
            def remotePassword= dsp.getProperty("remotePassword")
            def remoteDriverClassName=dsp.getProperty("remoteDriverClassName")
            if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
                return render("远程服务器信息配置不完整！")
            }
            def remoteDB=groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
            def NzleftDay=grailsApplication.config.client.NzLeftDay
            //同步一致性证书
            def map=[:]
            map.remoteDB=remoteDB
            map.NzleftDay=NzleftDay

            //同步合格证信息
            def zcFailList=[]
            User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
            //人员 组织 车间 三者进行关联
            String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
            //取出
            def template=new JdbcTemplate(dataSource)
            List symbolList = template.queryForList(symbolStr)
            def symboStr=""
            if(symbolList!=null&&symbolList.size()!=0){
                for(def symbol in symbolList){
                    symboStr=symbol.get('SYMBOL')
                }
            }
            def u=User.get(loginUser.id)
            def m=[:]
            def organCodeList=[]
            u.organs.each{o->
                organCodeList.add(o.organCode)
            }
            m.organCodeList=organCodeList
            m.factoryCode=u.organs?.organCode?.first()
            m.remoteDB=remoteDB
            m.symboStr=symboStr
            m.NzleftDay=NzleftDay
            m.userId=loginUser?.id
            m.userName=loginUser?.userName
            m.uploadTime=DateUtil.getCurrentTime()
            m.changeStatus=grailsApplication.config.changeStatus    //表示需不需要回滚数据
            m.veh_Clbh1=params.veh_Clbh1
            m.veh_Clbh2=params.veh_Clbh2
            zcFailList=nzInfoService.uploadGarbageInfo(m)
            remoteDB.close()

            //处理错误信息
            if (zcFailList){
                msg="合格证上传失败记录id：${zcFailList}"
            }else{
                msg='数据同步成功'
            }
        }catch(Exception e){
            e.cause?.printStackTrace()
            msg="上传终止！终止原因：${e.cause?e.cause:e}"
        }finally{
            render msg
        }
    }
    /**
     * @Description 农装合格证上传菜单里面撤销方法
     * @CreateTime 2014-07-16
     * @Author zhuwei
     * @return
     */
    def changeNZStatus(){
        def flag='success'
        def msg=""
        try{
            def nzinfo=GarbageInfo.get(params.id)
            if (nzinfo){
                nzinfo.upload='3'
                def temp="cancel:${nzinfo.veh_Hgzbh}:${nzinfo.veh_Clbh}:${DateUtil.getCurrentTime()}"
                nzinfo.veh_Hgzbh=temp
                nzinfo.veh_Clbh=temp
                nzinfo.updateTime=DateUtil.getCurrentTime()
                nzinfo.updaterId=getCurrentUser()?.userName

                if (nzinfo.save(flush: true)){
                    msg="合格证信息撤销成功！"
                }else{
                    flag = "failed"
                    msg="合格证信息撤销失败!"
                }
            } else{
                msg='合格证信息不存在！'
                flag = 'failed'
            }
        }catch (Exception e){
            flag="failed"
            msg="处理过程出现异常"
        }finally{
            render ([msg:msg,flag:flag] as JSON)
        }

    }

    /**
     * @description 合格证同步
     * @Auther liuly
     * @createTime 2013-8-10
     * @return
     */
    def synchroTable_NZ(){
        def pa=params
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
            //获取每次同步的最大数，当不设置时默认一次同步全部的
            def zcinfoMaxCount=1
            /*
            *同步的逻辑：
            * 1、公告信息和一致性证书信息分开同步
            * 2、同步时首先从客户端数据库中tbl_lastSynTime取出最后更新时间，根据最后最后更新时间从服务器的wzh_temp表中取出在服务端已修改或删除的记录id，然后从车间客户端删除这些记录
            * 3、根据最后更新时间从服务端从取出创建时间或修改时间大于最后更新时间的记录，同步到客户端
             */
            def  totalTime=0
            //同步分解前的公告信息
            def result=sqlToolService.synZcinfo_NZ(ora_con,sql_con,params,zcinfoMaxCount)
            if(result.totalCount>=1){
                msg="<font color='green'>同步成功!</font>"
            }else{
                msg="<font color='red'>同步失败!</font>"
            }
            totalTime=(System.currentTimeMillis()-statrTime)/1000

        }catch (Exception e){
            e.cause?.printStackTrace();
            msg="<font color='red'>同步失败,检查网络相关信息后重试!【${e.cause?e.cause:e}】</font>"
        } finally{
            render msg
        }
    }
    /**
     *  @ author zhuwei
     *  @Update 更新导出因没有取到所属用户而产生的报错问题
     *  @UpdateTime zhuwei
     */
    def export_search_NZ(){
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

                Map labels = ["veh_Hgzbh":"合格证编号","veh_Clbh":"车辆编号","veh_Fdjh":"发动机号","veh_Dph":"底盘号","veh_workshopno":"生产车间编号","veh_Jcy":"检查员"
                        ,"veh_Ccrq":"出厂日期","veh_Zxbz":"执行标准","createTime":"创建时间","createrId":"创建ID","updateTime":"最后更新时间"
                        ,"updaterId":"最后更新人","upload":"上传状态 0：未上传 1：上传成功 2：上传失败 3：已撤销","isprint":"是否已打印 0：未打印 1：已打印","uploader_Id":"上传人ID","uploader_Time":"上传时间"
                        ,"veh_Ggpc":"公告批次","veh_pzxlh":"配置序列号","veh_clzzqyxx":"车辆制造企业信息","veh_workshopno":"车间编号","createTime":"创建时间","createrId":"创建人id","updateTime":"最后更新时间"
                        ,"SAP_No":"SAP唯一号","SAP_status":"SAP传输状态 0：为传输 1：已传输","transDate":"传输时间"]
                def out=response.outputStream
                def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
                def loginUser=User.get(user.id)

                if(loginUser.organs.size()>0){
                    def organsCodeStr=new StringBuffer("(")
                    def organCodeList=[]
                    loginUser.organs.each{
                        if(it.organCode){
                            organsCodeStr.append("'"+it.organCode+"',")       // ('202282','202343','202581')
                            organCodeList.add(it.organCode)
                        }
                    }
                    organsCodeStr = organsCodeStr.substring(0,organsCodeStr.lastIndexOf(','))
                    organsCodeStr=organsCodeStr+')'
                    params.organsCodeStr=organsCodeStr
                    params.organCodeList=organCodeList
                }

//                def organCode=''
//                loginUser.organs.each{
//                    organCode=it.organCode
//                }
//                params.organCode=organCode
                def result=nzInfoService.selectNZinfoByShop(params)
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

    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
    /**
     * @Description 打印时将打印状态修改
     * @CreateTime 2014-07-23
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

        def hgz=GarbageInfo.findByVeh_Clbh(params.veh_Clbh)
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
            return render (result as JSON)
        }
        render result
    }

    /**
     *
     * @Description 取得车型
     * @author zhuwei
     * @createTime 2014-08-18
     * @return
     */
    def jsonCx() {
        def cx=GarbageCx.findAll();
        render(contentType: "text/json") {
            array {
                for (m in cx) {
                    pack(text:m.cx ,value:m.cx)
                }
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
    def jsonCpmc() {
        def cpmc=GarbageCpmc.findAll();
        render(contentType: "text/json") {
            array {
                for (m in cpmc) {
                    pack(text:m.cpmc ,value:m.cpmc)
                }
            }
        }
    }
    /**
     * @Description 农装合格证常规变更【车型、出厂日期、发动机号】的变更
     * CreateTime 2014-11-13 zhuwei
     * @return
     */
    def nzInfoFormalChange(){
        def opFlag=params?.opFlag
        render (view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/formalReplace',model: [opFlag:params?.opFlag])
    }
    /**
     * @Description   农装合格证非常规变更【底盘号和车辆编号不一致时】
     * @CreateTime 2014-11-13
     */
     def nzInfoUNFormalChange(){
         def opFlag=params?.opFlag
         render (view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/unFormalChange',model: [opFlag:params?.opFlag])
     }
    /**
     *@Descriptions 经销商 >>合格证更换申请 ，通过车辆编号关联合格证信息
     *@Auther zhuwei 正常更换和车辆编号、底盘号更换使用同一个方法
     *@createTime  2014-11-11
     */
    def fingNZzcinfoByClbh(){
        def aaaa=params
        def  veh_Clbh  = params.veh_Clbh;  ///底盘号信息
        def opFlag=params?.opFlag
        if (!veh_Clbh){
            if(opFlag=='0'){
                render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/formalReplace',model: [opFlag:params?.opFlag ])
            }else if(opFlag=='1'){
                render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/unFormalChange',model: [opFlag:params?.opFlag ])
            }
            return
        }
        def cel={
            if(params.veh_Clbh){
                eq('veh_Clbh',params.veh_Clbh)
            }
        }
        def  nzzciInfo=GarbageInfo.createCriteria().list(cel);
        def flag=true
        if (nzzciInfo.size()==0){
            flag = false
        }
        if (flag){
            if(opFlag=='0'){
                render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/formalReplace',model: [NZzcinfModel:nzzciInfo[0],zid:nzzciInfo[0].id,statusWrite:'nowrite',opFlag:opFlag ])
            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/unFormalChange',model: [NZzcinfModel:nzzciInfo[0],zid:nzzciInfo[0].id,statusWrite:'nowrite',opFlag:opFlag])
            }
        }else{
            if(opFlag=='0'){
                render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/formalReplace',model: [opFlag:params?.opFlag ])
            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/unFormalChange',model: [opFlag:params?.opFlag ])
            }
        }

    }

    /**
     * @Description  农装合格证更换中tiaozhuan到手动填写页面
     * @CreateTime 2014-11-15 zhuwei
     */
    def nzZcinfoRondWrite(){
        def aa =params
        def opFlag=params?.opFlag
            render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/nzZcinfoWrite',model: [opFlag:params?.opFlag])
    }
    /**
     * @Description 农装合格证手动填写保存
     * CreateTime 2014-11-15 zhuwei
     */
    def saveWrite(){
        def  a=params
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        //判断填写的合格证是否存在
        def NZzcinfModel=GarbageInfo.findByVeh_Clbh(params.veh_Clbh)
        def nzZcinfo=GarbageInfo.findByVeh_Dph(params.veh_Dph)
        if (NZzcinfModel ||nzZcinfo) {  //存在
            render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/nzZcinfoWrite',model:[returnMessage:"合格证信息已存在，请使用车辆编号进行关联更换",opFlag:params?.opFlag])
        }else{ //不存在

            NZzcinfModel=new GarbageInfo()
            NZzcinfModel.veh_Fdjh=params?.veh_Fdjh
            NZzcinfModel.veh_Cx=params?.veh_Cx
            NZzcinfModel.veh_Clbh=params?.veh_Clbh
            NZzcinfModel.veh_Dph=params?.veh_Dph
            NZzcinfModel.veh_Hgzbh=params?.veh_Hgzbh
            NZzcinfModel.veh_Ccrq=params?.veh_Ccrq

            NZzcinfModel.createrId=loginUser.userName
            NZzcinfModel.createTime=DateUtil.getCurrentTime()
            NZzcinfModel.veh_workshopno=loginUser.userName   //更换时手动填写的合格证信息的生产车间保存创建人的用户名

            if (NZzcinfModel.save(flush: true)){
                if(params?.opFlag=='0'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/formalReplace',model: [NZzcinfModel:NZzcinfModel,zid:NZzcinfModel.id,statusWrite:'write',opFlag:params?.opFlag])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/NZReplace/unFormalChange',model: [NZzcinfModel:NZzcinfModel,zid:NZzcinfModel.id,statusWrite:'write',opFlag:params?.opFlag])
                }

            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/nzZcinfoWrite',model: [opFlag:params?.opFalg])
            }
        }
    }
}
