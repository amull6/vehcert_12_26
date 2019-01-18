package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.FTPUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.carstorage.CarStorage
import grails.converters.JSON
import cn.com.wz.parent.FileUtil
import groovy.sql.Sql
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.ExcelUtils


class ZCInfoController extends BaseController {

    def exportService
    def dataSource;
    def sqlService
    def zcInfoService;
    def sqlToolService
    def newDmsSynService
    def toGenerateAgr() {
        //DONE  生成汽车合格证编号，存放至instance
        StringBuilder   veh_Zchgzbh=new StringBuilder('NB09');
        veh_Zchgzbh.append(DateUtil.getCurrentTime('yy'));
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        ZCInfo obj=new ZCInfo()
        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        //取出
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE'),symbol.get('SYMBOL'));
                symboMap.put('start_'+symbol.get('CODE'),symbol.get('STARTNUM'));
                symboMap.put('max_'+symbol.get('CODE'),symbol.get('MAXNUM'));
            }
        }
        boolean isValid=true;
        int startNum=0;
        int maxNum =0;
        if (symboMap.containsKey('farmcar')){
            veh_Zchgzbh.append(symboMap.get('farmcar'));
            startNum= symboMap.get('start_farmcar')
            maxNum= symboMap.get('max_farmcar')
            isValid=true;
        } else{
            isValid=false;
        }
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def printSet = PrintSet.findByCodeAndUserid("main",codeList)
        if (!printSet){
            printSet = new PrintSet()
            printSet.code="main"
            printSet.userid=codeList
        }

        if (isValid){
            String maxBH='';
            boolean formTop=false;
            if (printSet){
                //若 最大值存在 则判断是否跟当前规则相同 若相同则   从最大值开始 若不同则从最小值开始。
                maxBH= printSet.maxQX_arg
                if (maxBH){
                    if (maxBH.startsWith(veh_Zchgzbh.toString())){
                        formTop=false;
                    }else{ //从符号最小值开始
                        formTop=true;
                    }
                }else{//从符号最小值开始
                    formTop=true;
                }
            }else{
                //没有的话从初始值进行设置
                formTop=true;
            }
            if (formTop){
                //从起始值开始
                String startNum_Str=''+startNum;   //目前值
                //根据长度添加0
                for(int i=startNum_Str.length();i<6;i++){
                    startNum_Str="0"+startNum_Str
                }
                veh_Zchgzbh.append(startNum_Str);
                obj.properties = ZCInfo.findByVeh_Zchgzbh(veh_Zchgzbh)?.properties
                obj.veh_Zchgzbh=veh_Zchgzbh
            } else{
                String  maxBH_= maxBH.substring(8);
                int maxBH_INT=Integer.valueOf(maxBH_)     ;
                if(maxBH_INT<maxNum){ //未达最大值
                    String maxBH_Str=''+  (++maxBH_INT);   //目前值
                    //根据长度添加0
                    for(int i=maxBH_Str.length();i<6;i++){
                        maxBH_Str="0"+maxBH_Str
                    }
                    veh_Zchgzbh.append(maxBH_Str);
                    obj.properties = ZCInfo.findByVeh_Zchgzbh(veh_Zchgzbh)?.properties
                    obj.veh_Zchgzbh=veh_Zchgzbh
                }else{ //已达最大值
                    flash.message = '<font color="red">合格证编号已达最大值</font>'
                }
            }
        }else{
            flash.message = '<font color="red">合格证编号生成错误</font>'
        }
        if (params.processType=='0'){
            render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
        }else if (params.processType=='1'){
            render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:new ZCInfo(),symboMap:symboMap,printSet:printSet])
        }

    }
    /**
     * 管理部门操作农用车合格证更换页面
     * @author  Xu
     * @return
     */
    def managetoGenerateAgr() {
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def printSet = PrintSet.findByCodeAndUserid('main',codeList)
        if (!printSet){
            printSet = new PrintSet()
            printSet.userid=codeList
        }
        ZCInfo obj=new ZCInfo()
        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehmanage',model:[zcinfoInstance:obj,printSet:printSet,turnOff:params.turnOff])
    }

    /**
     * 公告资源部农用车合格证修改页面
     * @author  Xu
     * @return
     */
    def noticetoGenerateAgr() {
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def printSet = PrintSet.findByCodeAndUserid('main',codeList)
        if (!printSet){
            printSet = new PrintSet()
            printSet.userid=codeList
        }

        ZCInfo obj=new ZCInfo()
        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehnotice',model:[zcinfoInstance:obj,printSet:printSet])
    }



    def toGenerateCar() {
        //DONE  生成汽车合格证编号，存放至instance
        StringBuilder   veh_Zchgzbh=new StringBuilder('WCB0');
        veh_Zchgzbh.append(DateUtil.getCurrentTime('yy'));
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        ZCInfo obj=new ZCInfo()
        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        //取出
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE'),symbol.get('SYMBOL'));
                symboMap.put('start_'+symbol.get('CODE'),symbol.get('STARTNUM'));
                symboMap.put('max_'+symbol.get('CODE'),symbol.get('MAXNUM'));
            }
        }
        boolean isValid=true;
        String type='-1';//类别 0为整车 1为底盘 2为货车
        int startNum=0;
        int maxNum =0;
        if (symboMap.containsKey('carload')){
            veh_Zchgzbh.append(symboMap.get('carload'));
            startNum= symboMap.get('start_carload')
            maxNum= symboMap.get('max_carload')
            symboMap.put('check_carload',1);
            type='0'
            isValid=true;
        } else if (symboMap.containsKey('chassis')){
            veh_Zchgzbh.append(symboMap.get('chassis'));
            startNum= symboMap.get('start_chassis')
            maxNum= symboMap.get('max_chassis')
            symboMap.put('check_chassis',1);
            type='1'
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
        def printSet = PrintSet.findByCodeAndUserid("main",codeList)
        if (!printSet){
            printSet = new PrintSet()
            printSet.code="main"
            printSet.userid=codeList
        }
        if (isValid){
            String maxBH='';
            boolean formTop=false;
            if (printSet){
                //若 最大值存在 则判断是否跟当前规则相同 若相同则   从最大值开始 若不同则从最小值开始。
                maxBH= printSet.maxQX_car
                if (maxBH){
                    if (maxBH.startsWith(veh_Zchgzbh.toString())){
                        formTop=false;
                    }else{ //从符号最小值开始
                        formTop=true;
                    }
                }else{//从符号最小值开始
                    formTop=true;
                }
            }else{
                //没有的话从初始值进行设置
                formTop=true;
            }
            if (formTop){
                //从起始值开始
                String startNum_Str=''+startNum;   //目前值
                //根据长度添加0
                for(int i=startNum_Str.length();i<6;i++){
                    startNum_Str="0"+startNum_Str
                }
                veh_Zchgzbh.append(startNum_Str);
                obj.properties = ZCInfo.findByVeh_Zchgzbh(veh_Zchgzbh)?.properties
                obj.veh_Zchgzbh=veh_Zchgzbh
            } else{
                String  maxBH_= maxBH.substring(8);
                int maxBH_INT=Integer.valueOf(maxBH_)     ;
                if(maxBH_INT<maxNum){ //未达最大值
                    String maxBH_Str=''+  (++maxBH_INT);   //目前值
                    //根据长度添加0
                    for(int i=maxBH_Str.length();i<6;i++){
                        maxBH_Str="0"+maxBH_Str
                    }
                    veh_Zchgzbh.append(maxBH_Str);
                    obj.properties = ZCInfo.findByVeh_Zchgzbh(veh_Zchgzbh)?.properties
                    obj.veh_Zchgzbh=veh_Zchgzbh
                }else{ //已达最大值
                    flash.message = '<font color="red">合格证编号已达最大值</font>'
                }
            }
        }else{
            flash.message = '<font color="red">合格证编号生成错误</font>'
        }
        if (params.processType=='0'){
            render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
        }else if (params.processType=='1'){
            render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:new ZCInfo(),symboMap:symboMap,printSet:printSet])
        }

    }
    /**
     * 管理部门操作汽车合格证更换页面
     * @author  Xu
     * @return
     */
    def managetoGenerateCar() {
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def printSet = PrintSet.findByCodeAndUserid('main',codeList)
        if (!printSet){
            printSet = new PrintSet()
            printSet.userid=codeList
        }
        ZCInfo obj=new ZCInfo()
        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carmanage',model:[zcinfoInstance:obj,printSet:printSet,turnOff:params?.turnOff])
    }
    /**
     * @Description运输管理科办理运输途中保险打印PDF临时合格证页面
     * @CreateTime 2017-08-07 by zhuwei
     * @return
     */
    def transportPrint() {
        def veh_Type =params.veh_Type
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def printSet = PrintSet.findByCodeAndUserid('main',codeList)
        if (!printSet){
            printSet = new PrintSet()
            printSet.userid=codeList
        }
        ZCInfo obj=new ZCInfo()
        render(view:'/cn/com/wz/vehcert/zcinfo/generate/transportPrint',model:[zcinfoInstance:obj,printSet:printSet,kind:params?.kind,veh_Type:veh_Type])
    }

    /**
     * 公告资源部汽车合格证修改页面
     * @author  Xu
     * @return
     */
    def noticetoGenerateCar() {
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def printSet = PrintSet.findByCodeAndUserid('main',codeList)
        if (!printSet){
            printSet = new PrintSet()
            printSet.userid=codeList
        }
        ZCInfo obj=new ZCInfo()
        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carnotice',model:[zcinfoInstance:obj,printSet:printSet])
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
        String hqlStr="select ${field} from WZH_CARSTORAGE c where c.veh_Clxh='${veh_Clxh}' and c.turn_off = 0 group by ${field} order by count(*) desc";
        def template=new JdbcTemplate(dataSource)
        List suglist = template.queryForList(hqlStr)
        render(contentType: "text/json") {
            array {
                if(field=='veh_Hxnbc'){
                    pack(text:'无' ,value:'无')
                }
                if(field=='veh_Hxnbk'){
                    pack(text:'无' ,value:'无')
                }
                if(field=='veh_Hxnbg'){
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
     *
     * @Description 取得颜色
     * @author mike
     * @createTime 2013-08-02
     * @return
     */
    def jsonColors() {
        def colors=Color.findAll();
        render(contentType: "text/json") {
            array {
                for (m in colors) {
                    pack(text:m.name ,value:m.name)
                }
            }
        }
    }
    def toNotice() {
        def aa=params
        ZCInfo obj=new ZCInfo(params)
        render(view:'/cn/com/wz/vehcert/zcinfo/generate/notice',model:[zcinfoInstance:obj,veh_Need_Cer:params.veh_Need_Cer,tabId:params.tabId,turnOff:params.turnOff,supplement:params.turnOff,supplement:params.supplement])
    }
    /**
     *@Description ajax获取列表中的所有公告信息
     *@Auther mike
     *@createTime
     * @Update 2013-11-02 添加公告类型的选择条件
     * @update 214-08-09  添加公告唯一码的选择条件
     * @upDate 2015_04_07_ 添加更具公告停用状态标识turn_off筛选   zhuwei
     *@Update  2017_02_08 添加更换合格证时，如果时货箱尺寸为空，但是车辆状态信息为QX的数据时，这个整车数据可提取上
     *@Update  2017_02_08 添加更换合格证时，如果要点合格证，除了货箱尺寸全部为空外，还要添加车辆状态信息DP
     */
    def jsonNotice(){
        def orders=grailsApplication.config.notice.orders
        def orderList=orders?.toString()?.split(":_:")
        //判断是整车还是底盘，整车与底盘都选整车
        def cel = {
            if(params.veh_Clxh){
                eq("veh_Clxh","${params.veh_Clxh}")
            }
            if(params.veh_Hxnbc){
                if (params.veh_Hxnbc=='无'){
                    or{
                        isNull("veh_Hxnbc")
                        eq("veh_Hxnbc","")
                    }
                }else{
                    eq("veh_Hxnbc","${params.veh_Hxnbc}")
                }
            }
            if(params.veh_Hxnbk){
                if (params.veh_Hxnbk=='无'){
                    or{
                        isNull("veh_Hxnbk")
                        eq("veh_Hxnbk","")
                    }
                }else{
                    eq("veh_Hxnbk","${params.veh_Hxnbk}")
                }
            }
            if(params.veh_Hxnbg){
                if (params.veh_Hxnbg=='无'){
                    or{
                        isNull("veh_Hxnbg")
                        eq("veh_Hxnbg","")
                    }
                }else{
                    eq("veh_Hxnbg","${params.veh_Hxnbg}")
                }
            }
            if(params.veh_Fdjxh){
                eq("veh_Fdjxh","${params.veh_Fdjxh}")
            }
            if(params.veh_Jsslx){
                eq("veh_Jsslx","${params.veh_Jsslx}")
            }
            if(params.veh_Wkc){
                eq("veh_Wkc","${params.veh_Wkc}")
            }
            if(params.veh_Wkk){
                eq("veh_Wkk","${params.veh_Wkk}")
            }
            if(params.veh_Wkg){
                eq("veh_Wkg","${params.veh_Wkg}")
            }
            if(params.veh_Edzzl){
                eq("veh_Edzzl","${params.veh_Edzzl}")
            }
            if(params.veh_Qlj){
                eq("veh_Qlj","${params.veh_Qlj}")
            }
            if(params.veh_Hlj){
                eq("veh_Hlj","${params.veh_Hlj}")
            }
            if(params.veh_Gbthps){
                eq("veh_Gbthps","${params.veh_Gbthps}")
            }
            if(params.veh_Zbzl){
                eq("veh_Zbzl","${params.veh_Zbzl}")
            }
            if (params.veh_Ltgg){
                eq("veh_Ltgg",params.veh_Ltgg)
            }
            if (params.veh_Zj){
                eq("veh_Zj",params.veh_Zj)
            }
            if (params.veh_Yhlx==''||params.veh_Yhlx=='1'){
                and{
                    isNotNull("veh_Yhlx")
                    ne("veh_Yhlx",' ')
                }

            }else{
                or{
                    isNull("veh_Yhlx")
                    eq("veh_Yhlx",' ')
                }

            }
            if (params.veh_Jsszcrs){
                eq("veh_Jsszcrs",params.veh_Jsszcrs)
            }
            if (params.veh_Need_Cer){
                if (params.veh_Need_Cer=='0'||params.veh_Need_Cer=='2'){   //需要整车的，或者需要整车+底盘合格证
//                    isNotNull('veh_Hxnbc')
//                    isNotNull('veh_Hxnbk')
//                    isNotNull('veh_Hxnbk')
//                    更换合格证是如果要整车，或者整车+底盘的时候，公告提取时加了一个判断分支
                    or{
//                       货箱内部尺寸不为空，这样的数据肯定是整车
                        and{
                            isNotNull('veh_Hxnbc')
                            isNotNull('veh_Hxnbk')
                            isNotNull('veh_Hxnbk')
                        }
//                       货箱内部尺寸为空，但是这个公告数据是全系
                        and{
                            isNull('veh_Hxnbc')
                            isNull('veh_Hxnbk')
                            isNull('veh_Hxnbg')
                            eq("veh_Clztxx",'QX')

                        }
                    }


                }else{                 //只要底盘合格证
                    isNull('veh_Hxnbc')
                    isNull('veh_Hxnbk')
                    isNull('veh_Hxnbg')
                    //底盘公告选择时，添加车辆状态信息为DP
                    eq("veh_Clztxx",'DP')
                }
            }
            if (params.car_storage_no){
                eq("car_storage_no",params.car_storage_no)
            }
            if(params.turnOff=='0'){
                println('111111')
                eq("turnOff",'0')  //通过菜单配置传值turnOff，只提取启用状态的公告
            }

            orderList?.each{t->
                def o=t.split(",")
                if(o?.size()==2){
                    order "${o[0]}","${o[1]}"
                }else if (o?.size()==1){
                    order "${o[0]}"
                }
            }
        }
        def results = CarStorage.createCriteria().list(params,cel)
        def result = [rows: results, total: results.totalCount]
        render result as JSON
    }
    /**
     * @description 合格证编号加1处理
     * @Auther liuly
     * @createTime 2013-11-30
     * @return
     */
    def returnZchgzbh(def veh_Zchgzbh){
        def veh_Zchgzbh1=Integer.parseInt(veh_Zchgzbh.substring(8,14))+1
        def veh_ZchgzbhS= veh_Zchgzbh1.toString()
        //不足六位的以0来补位
        def Z0 ='0'
        if(veh_ZchgzbhS.length()<6){
            for(int i=1;i<(6-veh_ZchgzbhS.length());i++){
                Z0+='0'
            }
            Z0+=veh_ZchgzbhS
        }
        if(veh_ZchgzbhS.length()<6){
            veh_Zchgzbh=veh_Zchgzbh.replace(veh_Zchgzbh.substring(8,14),Z0)
        }else{
            veh_Zchgzbh=veh_Zchgzbh.replace(veh_Zchgzbh.substring(8,14),veh_ZchgzbhS)
        }
        return  veh_Zchgzbh
    }
    /**
     * @description 根据条件查询合格证
     * @Auther mike
     * @createTime 2013-08-03
     * @return
     * @Update 2013-11-25 liuly 打印完成后跳转到下一个编号的合格证
     */
    def search(){
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def beforeZch=params.veh_Zchgzbh
        //打印完成后跳转到下一个编号的合格证
        if(params.intI&&params.veh_Zchgzbh){
            params.veh_Zchgzbh=returnZchgzbh(params.veh_Zchgzbh)
        }
        //回复按钮的操作

        if(params.recover){
            def user=User.get(loginUser.id)
            def codeList=''
            user.organs.each{
                codeList=it.organCode
            }
            PrintSet printSet=PrintSet.findByCodeAndUserid('main',codeList)
            if(printSet){
                if(params.veh_Zchgzbh.indexOf('WC')>=0){
                    if(printSet.maxQX_car){
                        print printSet.maxQX_car
                        params.veh_Zchgzbh=returnZchgzbh(printSet.maxQX_car)
                    }
                }else{
                    if(printSet.maxQX_arg){
                        params.veh_Zchgzbh=returnZchgzbh(printSet.maxQX_arg)
                    }
                }
            }else{
                if(params.veh_Zchgzbh.indexOf('WC')>=0){

                    return toGenerateCar()
                }else{
                    return toGenerateAgr()
                }
            }
        }
        if(params.veh_Zchgzbh){
            String  veh_Zchgzbh=params.veh_Zchgzbh;
            String start=veh_Zchgzbh.substring(0,8)
            String end=veh_Zchgzbh.substring(8)
            String symbol=veh_Zchgzbh.substring(6,8)
            try {
                int end_int=Integer.valueOf(end)
                if(params.direction=='1'){// 向前
                    end_int++
                    String end_str=''+end_int;   //目前值
                    for(int i=end_str.length();i<6;i++){
                        end_str="0"+end_str
                    }
                    veh_Zchgzbh= start+  end_str;
                }else if (params.direction=='-1'){  //向后
                    end_int--
                    String end_str=''+end_int;   //目前值
                    for(int i=end_str.length();i<6;i++){
                        end_str="0"+end_str
                    }
                    veh_Zchgzbh= start+  end_str;
                }
                def cel = {
                    eq("veh_Zchgzbh",veh_Zchgzbh)
                }
                def results = ZCInfo.createCriteria().list(cel)
                //人员 组织 车间 三者进行关联
                String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
                def template=new JdbcTemplate(dataSource)
                List symbolList = template.queryForList(symbolStr)
                def symboMap=[:]
                //判定要修改的记录是否存在
                def printSetList = PrintSet.findAll()
                def   printSet=null;
                if (!printSetList||printSetList.size()==0){
                    printSet = new PrintSet()
                    printSet.userid=loginUser.id
                }else{
                    printSet=printSetList.get(0)
                }
                if(results==null||results.size()==0){
                    ZCInfo obj=null
                    if(params.intI){
                        def celBefore = {
                            eq("veh_Zchgzbh",beforeZch)
                        }
                        def zc = ZCInfo.createCriteria().list(celBefore).get(0)
                        obj=new ZCInfo()
                        obj.properties=zc.properties
                        obj.web_status=0
                        obj.veh_Zchgzbh=params.veh_Zchgzbh;
                        obj.veh_Fdjh=''
                        obj.veh_Clsbdh=''
                    }else{
                        obj=new ZCInfo();
                    }

                    boolean isValid=false;
                    int startNum=0;
                    int maxNUm =0;
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbolTmp in symbolList){
                            symboMap.put(symbolTmp.get('CODE'),symbolTmp.get('SYMBOL'));
                            if (symbol==symbolTmp.get('SYMBOL'))   {
                                symboMap.put('check_'+symbolTmp.get('CODE'),1);
                                isValid=true;
                                startNum= symbolTmp.get('STARTNUM')
                                maxNUm= symbolTmp.get('MAXNUM')
                            }
                        }
                    }
                    boolean haserror=false;
                    if(params.direction){
                        if (isValid){
                            if(end_int<=maxNUm){ //未达最大值
                                if(end_int>=startNum){ //未达最大值
                                    obj.veh_Zchgzbh=veh_Zchgzbh
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
                    if (haserror)   {
                        obj = ZCInfo.findByVeh_Zchgzbh(params.veh_Zchgzbh)
                        if (obj==null){
                            if(params.intI){
                                def celBefore = {
                                    eq("veh_Zchgzbh",beforeZch)
                                }
                                def zc = ZCInfo.createCriteria().list(celBefore).get(0)
                                obj=new ZCInfo()
                                obj.properties=zc.properties
                                obj.web_status=0
                                obj.veh_Zchgzbh=params.veh_Zchgzbh;
                                obj.veh_Fdjh=''
                                obj.veh_Clsbdh=''
                            }else{
                                obj=new ZCInfo();
                                obj.veh_Zchgzbh=params.veh_Zchgzbh;
                            }
                        }
                    }
                    if(params.kind=='0'){
                        if (params.processType=='0'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }else if (params.processType=='1'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }

                        return
                    }else{
                        if (params.processType=='0'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }else if (params.processType=='1'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }

                        return
                    }



                }else if (results.size()==1){
                    ZCInfo obj=results.get(0);
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbolTmp in symbolList){
                            symboMap.put(symbolTmp.get('CODE'),symbolTmp.get('SYMBOL'));
                            if (symbol==symbolTmp.get('SYMBOL'))   {
                                symboMap.put('check_'+symbolTmp.get('CODE'),1);
                            }
                        }
                    }
                    if(params.kind=='0'){
                        if (params.processType=='0'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }else if (params.processType=='1'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }

                        return
                    }else{
                        if (params.processType=='0'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }else if (params.processType=='1'){
                            render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:obj,symboMap:symboMap,printSet:printSet])
                        }

                        return
                    }

                }
            } catch(Exception e){
                flash.message = '初始化车辆信息出错'
                if (params.kind=='0'){//汽车
                    return toGenerateCar(params)
                }else{ //农用车
                    return toGenerateAgr(params)
                }
            }
        }else{
            flash.message = '请输入合格证编号'
            if (params.kind=='0'){//汽车
                return toGenerateCar(params)
            }else{ //农用车
                return toGenerateAgr(params)
            }
        }
    }

    /**
     * @description 管理员根据虚拟合格证编号条件查询合格证
     * @Auther Xu
     * @createTime 2013-8-10
     * @return
     * @Update 2013-11-03 huxx 当有多条相同合格证号的合格证信息时取第一条合格证信息
     */
    def managesearch(){
        flash.message=''
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        if(params.web_virtualcode||params.vehZchgzbhRe){
            try {
                def cel = {
                    if (params.vehZchgzbhRe){
                        eq("veh_Zchgzbh",params.vehZchgzbhRe)
                    }
                    if(params.web_virtualcode){
                        eq("web_virtualcode",params.web_virtualcode)
                    }

                    if(params.kind=='0' || params.kind=='2'){
                        eq("veh_Type",'0')
                    }else{
                        eq("veh_Type",'1')
                    }
                }
                def results = ZCInfo.createCriteria().list(cel)
                //判定要修改的记录是否存在
                def user=User.get(loginUser.id)
                def codeList=''
                user.organs.each{
                    codeList=it.organCode
                }
                def printSet = PrintSet.findByCodeAndUserid('main',codeList)
                if (!printSet){
                    printSet = new PrintSet()
                    printSet.userid=loginUser.id
                }
                if(results==null||results.size()==0){
                    flash.message = '未找到该编号的合格证'
                    ZCInfo obj=new ZCInfo()
                    obj.web_virtualcode=params.web_virtualcode
                    if(params.kind=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carmanage',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else if (params.kind=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehmanage',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else if (params.kind=='2'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carprint',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else if (params.kind=='3'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehprint',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else{
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carmanage',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }
                }else{
                    ZCInfo obj=results.get(0);
                    if(params.kind=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carmanage',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else if (params.kind=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehmanage',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else if (params.kind=='2'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carprint',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else if (params.kind=='3'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehprint',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else{
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carmanage',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }
                }
            } catch(Exception e){
                flash.message = '初始化车辆信息出错'
                if(params.kind=='0'){
                    return managetoGenerateCar()
                }else if (params.kind=='1'){
                    return managetoGenerateAgr()
                }else if (params.kind=='2'){
                    def map=[zcType:0]
                    return toZcInfoPrint(map)
                }else if (params.kind=='3'){
                    def map=[zcType:1]
                    return toZcInfoPrint(map)
                }else{
                    return managetoGenerateCar()
                }
            }
        }else{
            flash.message = '请输入虚拟合格证编号或者原合格证编号'
            if(params.kind=='0'){
                return managetoGenerateCar()
            }else if (params.kind=='1'){
                return managetoGenerateAgr()
            }else if (params.kind=='2'){
                def map=[zcType:0]
                return toZcInfoPrint(map)
            }else if (params.kind=='3'){
                def map=[zcType:1]
                return toZcInfoPrint(map)
            }else{
                return managetoGenerateCar()
            }
        }
    }

    /**
     * @description 运输管理部打印PDF合格证-》根据合格证编号查询数据
     * @Auther zhuwei
     * @当有多条相同合格证号的合格证信息时取第一条合格证信息（zcinfo表里veh_zchgzbh是唯一键）
     */
    def transportPrintSearch(){
        flash.message=''
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        try {
            def cel = {
                if (params.vehZchgzbhRe){
                    eq("veh_Zchgzbh",params.vehZchgzbhRe)
                }
//                    if(params.web_virtualcode){
//                        eq("web_virtualcode",params.web_virtualcode)
//                    }

                if(params.kind=='0'){
                    eq("veh_Type",'0')   //查询汽车数据
                }else if(params.kind=='1'){
                    eq("veh_Type",'1')   //查询农用车数据
                }else{
                    eq("veh_Type",'')
                }
            }
            def results = ZCInfo.createCriteria().list(cel)
            //判定要修改的记录是否存在
            def user=User.get(loginUser.id)
            def codeList=''
            user.organs.each{
                codeList=it.organCode
            }
            def printSet = PrintSet.findByCodeAndUserid('main',codeList)
            if (!printSet){
                printSet = new PrintSet()
                printSet.userid=loginUser.id
            }
            if(results==null||results.size()==0){
                flash.message = '未找到该编号的合格证'
                ZCInfo obj=new ZCInfo()
                obj.web_virtualcode=params.web_virtualcode
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/transportPrint',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe,kind:params?.kind,veh_Type:params?.veh_Type])
                return
            }else{
                ZCInfo obj=results.get(0);
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/transportPrint',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe,kind:params?.kind,veh_Type:params?.veh_Type])
                return
            }
        } catch(Exception e){
            flash.message = '初始化车辆信息出错'
            if(params.kind=='0'){
                return transportPrint()
            }
//                      如果需要打印农用车合格证，将下面的注释去掉就行了
//                else if (params.kind=='1'){
//                    return managetoGenerateAgr()
//                }
        }

    }

    /**
     * @description 公告资源部根据虚拟合格证编号条件查询合格证
     * @Auther Xu
     * @createTime 2013-8-10
     * @return
     */
    def noticesearch(){
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        if(params.web_virtualcode||params.vehZchgzbhRe){
            try {
                def cel = {
                    if(params.web_virtualcode){
                        eq("web_virtualcode",params.web_virtualcode)
                    }
                    if (params.vehZchgzbhRe){
                        eq("veh_Zchgzbh",params.vehZchgzbhRe)
                    }
                    if(params.kind=='0'){
                        eq("veh_Type",'0')
                    }else{
                        eq("veh_Type",'1')
                    }
                }
                def results = ZCInfo.createCriteria().list(cel)
                //判定要修改的记录是否存在
                def user=User.get(loginUser.id)
                def codeList=''
                user.organs.each{
                    codeList=it.organCode
                }
                def printSet = PrintSet.findByCodeAndUserid('main',codeList)
                if (!printSet){
                    printSet = new PrintSet()
                    printSet.userid=codeList
                }
                if(results==null||results.size()==0){
                    flash.message = '未找到该编号的合格证'
                    ZCInfo obj=new ZCInfo()
                    obj.web_virtualcode=params.web_virtualcode
                    if(params.kind=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carnotice',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else{
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehnotice',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }
                }else if (results.size()==1){
                    ZCInfo obj=results.get(0);
                    if(params.kind=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/carnotice',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }else{
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehnotice',model:[zcinfoInstance:obj,printSet:printSet,vehZchgzbhRe:params.vehZchgzbhRe])
                        return
                    }
                }
            } catch(Exception e){
                flash.message = '初始化车辆信息出错'
                if (params.kind=='0'){//汽车
                    return managetoGenerateCar()
                }else{ //农用车
                    return managetoGenerateAgr()
                }
            }
        }else{
            flash.message = '请输入虚拟合格证编号或者原合格证编号'
            if (params.kind=='0'){//汽车
                return managetoGenerateCar()
            }else{ //农用车
                return managetoGenerateAgr()
            }
        }
    }


    /**
     *@Description a VIN计算
     *@Auther mike
     *@createTime
     */
    def calculateVIN(){
        def result=zcInfoService.calculateVIN(params)
        render  result;
    }
    /**
     *@Description 打印合格证时回填数据
     *@Auther mike
     *@createTime
     *@Update 2013-11-05 huxx 打印时取页面传过来的合格证信息打印，然后将打印的合格证信息保存到数据库中
     *@Update 2013-02-16 huxx 添加了合格证信息与公告库中国信息的匹配
     */
    def print(){
        def result=[:]
        def msg=""
        def flag=''
        //判断合格证信息是否与公告库中的信息匹配
        def checkMessage=zcInfoService.checkCarStorage(new ZCInfo(params))
        if (checkMessage){
            msg = checkMessage
            flag='failed'
            result.put("msg",msg);
            result.put("flag",flag)
            return render (result as JSON)
        }

        ZCInfo zcInfo=ZCInfo.get(params.infoid)
        if (zcInfo){
            //打印时，打印页面上的参数
            zcInfo.properties=params
            def printMap=zcInfoService.printWCF(zcInfo.properties,grailsApplication)
            if (printMap.isSuccess=='0'){
                msg=printMap.msg
                flag='failed'
                result.put("msg","打印失败，错误原因:"+msg);
                result.put("flag",flag)
                return  render (result as JSON)
            } else{
                params.vercode=printMap.vercode
                params.veh_Zchgzbh_0=printMap.veh_wzhgzbh
                params.veh_Dywym=printMap.veh_Dywym
                params.upload_Path=printMap.msg
                params.web_virtualcode=printMap.veh_wzhgzbh
            }

            zcInfo.properties=params
            zcInfo.web_isprint=params.printType

            if (zcInfo.save(flush: true)){
                User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
                def workShop=''
                def organs=User.get(loginUser.id).organs
                organs.each {
                    workShop=it.organCode
                }

                PrintSet printSet=PrintSet.findByCodeAndUserid('main',zcInfo.veh_workshopno)
                if(!printSet){
                    printSet=new PrintSet()
                    printSet.code='main'
                    printSet.userid= workShop
                }
                String  veh_Zchgzbh=zcInfo.veh_Zchgzbh
                String start=veh_Zchgzbh.substring(0,8)
                String end=veh_Zchgzbh.substring(8)
                String symbol=veh_Zchgzbh.substring(6,8)
                String start06=veh_Zchgzbh.substring(0,6)//合格证号前6位
                def end_int=Integer.valueOf(end)
                if (zcInfo.veh_Zchgzbh.indexOf('WC')>=0){
                    boolean needUpdate=false;
                    String maxQX=printSet?.maxQX_car
                    if (maxQX&&maxQX.startsWith(start)){
                        String maxQX_str=maxQX.substring(8);
                        int maxQX_int=Integer.valueOf(maxQX_str)
                        if (end_int>maxQX_int){
                            needUpdate=true;
                        }
                    }else{
                        needUpdate=true;
                    }
                    if (needUpdate){
                        printSet.maxQX_car=zcInfo.veh_Zchgzbh
                        printSet.code='main'
                        printSet.userid= workShop
                        if (!printSet.save(flush: true)){
                            result.put("msg","流水号保存失败");
                            result.put("flag","failed")
                            return render (result as JSON)
                        }
                    }
                }else if (zcInfo.veh_Zchgzbh.indexOf('NB')>=0){
                    boolean needUpdate=false;
                    String maxQX=printSet?.maxQX_arg
                    if (maxQX&&maxQX.startsWith(start)){
                        String maxQX_str=maxQX.substring(8);
                        int maxQX_int=Integer.valueOf(maxQX_str)
                        if (end_int>maxQX_int){
                            needUpdate=true;
                        }
                    }else{
                        needUpdate=true;
                    }
                    if (needUpdate){
                        printSet.maxQX_arg=zcInfo.veh_Zchgzbh
                        printSet.userid= workShop
                        if (!printSet.save(flush: true)){
                            result.put("msg","流水号保存失败");
                            result.put("flag","failed")
                            return render (result as JSON)
                        }
                    }
                }
                result.put("msg","打印成功！完整合格证编号：${params.veh_Zchgzbh_0}");
                result.put("flag","successed")
                return render (result as JSON)
            }else{
                result.put('flag','failed')
                String errormsg=''
                zcInfo.errors.allErrors?.each{
                    if(it in org.springframework.validation.FieldError){
                        //将对象和属性国际化
                        it.arguments[1]="合格证"
                        it.arguments[0]="${message(code:"zcinfo.${ it.arguments[0]}.label")}"
                        errormsg+=message(error: it)+";"
                    }
                }
                result.put('msg',"打印失败，错误原因："+errormsg)
                return render (result as JSON)
            }

        }else{
            result.put('flag','failed')
            result.put("msg","打印失败，错误原因：合格证信息未找到")
            return render (result as JSON)
        }
    }
    /**
     *@Description 转到打印a4
     *@Auther mike
     *@createTime
     */
    def toprinta4(){
        ZCInfo zcInfo=null
        def result=[:]
        if (params.infoid&&(zcInfo=ZCInfo.get(params.infoid))!=null){
            render(view:'/cn/com/wz/vehcert/zcinfo/generate/print',model:[zcinfoInstance:zcInfo])
        }else{
            zcInfo=new ZCInfo()
            render(view:'/cn/com/wz/vehcert/zcinfo/generate/print',model:[zcinfoInstance:zcInfo])
        }
    }
    /**
     *@Description 打印合格证时回填数据  ，如果合格证已经上传到国家 打印时需要回填记录表里面的数据
     *@Auther Xu
     *@createTime
     */
    def printnotice(){
        ZCInfo zcInfo=null
        def result=[:]
        if (params.infoid&&(zcInfo=ZCInfo.get(params.infoid))!=null){
            zcInfo.setVeh_Zchgzbh_0(params.veh_Zchgzbh_0);
            zcInfo.setVeh_Dywym(params.veh_Dywym)
            zcInfo.vercode=params.veh_Jyw
            if (!zcInfo.save(flush: true)){
                result.put('flag','0')
            } else{
                ZCInfoReplace   zcInfoReplace
                //已经成功上传到国家
                if (zcInfo.web_status==1){
                    def cel1= {
                        zcInfo{
                            eq("id",zcInfo.id)
                        }
                        eq("veh_managestatus",0)//公告资源部未处理的信息
                    }
                    def replaceResult = ZCInfoReplace.createCriteria().list(cel1)
                    //如果有未处理的修改记录则 直接修改原纪录  如果没有则需要创建新修改记录
                    if (replaceResult.size()==0){
                        result.put('flag','0')
                    }else{
                        zcInfoReplace = replaceResult.get(0)
                        zcInfoReplace.setVeh_Zchgzbh_0_R(params.veh_Zchgzbh_0)
                        zcInfoReplace.setVeh_Dywym_R(params.veh_Dywym)
                        zcInfoReplace.setVercode_r(params.veh_Jyw)
                        if (!zcInfoReplace.save(flush: true)){
                            result.put('flag','0')
                        } else{
                            result.put('flag','1')
                        }
                    }
                } else{
                    result.put('flag','1')
                }
            }
            render result as JSON
        }else{
            result.put('flag','0')
            render result as JSON
        }
    }




    /**
     *@Description 修改生成的pdf文件名称
     *@Auther mike
     *@createTime
     */
    def changePdfName(){
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        ZCInfo zcInfo=null
        def result=[:]
        if (params.infoid&&(zcInfo=ZCInfo.get(params.infoid))!=null){
            zcInfo.setVeh_Zchgzbh_0(params.veh_Zchgzbh_0);
            zcInfo.setVeh_Dywym(params.veh_Dywym)
            zcInfo.vercode=params.veh_Jyw
            String xncode=params.xnVeh_Zchgzbh
            zcInfo.web_virtualcode=xncode;
            //打印虚拟合格证时将web_isprint改为0
            zcInfo.web_isprint='0'
            if (zcInfo.save(flush: true)){
                if (params.veh_Zchgzbh){
                    //判定要修改的记录是否存在
                    def printSetList = PrintSet.findAll()
                    def   printSet=null;
                    if (!printSetList||printSetList.size()==0){
                        printSet = new PrintSet()
                        printSet.userid=loginUser.id
                    }else{
                        printSet=printSetList.get(0)
                    }
                    if (printSet){
                        String location=printSet.location
                        File pFile=new File(location);
                        if(!pFile.exists()){
                            pFile.mkdirs();
                        }
                        String pdfname=grailsApplication.config.workshop.pdfname
                        def oldFilePath=location+"/"+pdfname
                        File file=new File(oldFilePath);
                        def newFilePath=grailsApplication.config.ftp.client.uploadDir+"/"+params.veh_Zchgzbh+'.pdf'
                        File newFile=new File(newFilePath);
                        if(newFile.exists()){
                            newFile.delete();
                        }

                        if (file.exists()){
                            def flag=FileUtil.copyFile(file,newFile)
                            if (flag){
                                result.put('flag','0')
                                render result as JSON;
                            }else{
                                result.put('flag','1')
                                render result as JSON;
                            }

                        }else{
                            result.put('flag','4')
                            render result as JSON;
                        }
                    }else{
                        result.put('flag','2')
                        render result as JSON;
                    }
                }else{
                    result.put('flag','1')
                    render result as JSON;
                }
            } else{
                String errormsg=''
                zcInfo.errors.allErrors?.each{
                    if(it in org.springframework.validation.FieldError){
                        //将对象和属性国际化
                        it.arguments[1]="合格证"
                        it.arguments[0]="${message(code:"zcinfo.${ it.arguments[0]}.label")}"
                        errormsg+=message(error: it)+";"
                    }
                }
                result.put('errormsg',errormsg)
                result.put('flag','3')
            }
            render result as JSON
        }else{
            result.put('flag','3')
            render result as JSON
        }
    }
    /**
     *@Description WCF 生成PDF
     *@Auther mike
     *@createTime
     */
    def printWCF(){
        def result=zcInfoService.printWCF(params,grailsApplication)
        render result as JSON
    }

    /**
     * @description 合格证保存
     * @Auther mike
     * @createTime 2013-08-03
     * @return
     * @Update 2013-11-13 huxx 添加了保存时合格证信息是否与分解前的公告库中的信息匹配的判断
     */
    def save() {
        String  veh_Zchgzbh=params.veh_Zchgzbh;
        String start=veh_Zchgzbh.substring(0,8)
        String end=veh_Zchgzbh.substring(8)
        String symbol=veh_Zchgzbh.substring(6,8)
        String start06=veh_Zchgzbh.substring(0,6)//合格证号前6位
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def workShop=''
        def organs=User.get(loginUser.id).organs
        organs.each {
            workShop=it.organCode
        }

        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,w.SERIAL_NUM as serialNum,w.name as name,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]

        //获取打印参数
        def printSet = PrintSet.findByCodeAndUserid("main",workShop)
        if (!printSet){
            printSet = new PrintSet()
            printSet.code="main"
            printSet.userid=workShop
        }

        boolean isValid=false;
        int startNum=0;
        int maxNUm =0;
        String serialNum='';
        String name='';
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbolTmp in symbolList){
                symboMap.put(symbolTmp.get('CODE'),symbolTmp.get('SYMBOL'));
                serialNum=symbolTmp.get('SERIALNUM')
                name = symbolTmp.get('NAME')
                if (symbol==symbolTmp.get('SYMBOL'))   {
                    symboMap.put('check_'+symbolTmp.get('CODE'),1);
                    isValid=true;
                    startNum= symbolTmp.get('STARTNUM')
                    maxNUm= symbolTmp.get('MAXNUM')
                }
            }
        }
        int end_int=0
        boolean boundaryCheck=true;
        try {
            end_int=Integer.valueOf(end)           ;
            if (isValid){
                if(end_int<=maxNUm){ //未达最大值
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
            def zcInfoInstance = new ZCInfo(params)
            if(params.kind=='0'){
                if (params.processType=='0'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }else if (params.processType=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }

                return
            }else{
                if (params.processType=='0'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }else if (params.processType=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }

                return
            }
        }
        if (!boundaryCheck){
            def zcInfoInstance = new ZCInfo(params)
            if(params.kind=='0'){
                if (params.processType=='0'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }else if (params.processType=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }

                return
            }else{
                if (params.processType=='0'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }else if (params.processType=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                }

                return
            }
        }
        //保存新颜色
        if (params.veh_Csys){
            def colors=Color.findAllByName(params.veh_Csys)
            if (colors==null||colors.size()==0){   //此情况下保存
                Color newColor=new Color();
                newColor.name=params.veh_Csys
                newColor.save()
            }
        }

        ZCInfo  zcInfo=ZCInfo.findByVeh_Zchgzbh(veh_Zchgzbh)
        if (zcInfo){ //合格证编号存在
            if (zcInfo.web_status=='1'){ //已上传
                flash.message = '<font color="red">合格证已上传,不允许修改</font>'
                if(params.kind=='0'){
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }

                    return
                }else{
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }

                    return
                }
            }else{ //未上传

                //判断合格证信息是否与公告库中的信息匹配
                def checkMessage=zcInfoService.checkCarStorage(new ZCInfo(params))
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    zcInfo.properties = params
                    zcInfo.updaterId=loginUser.userName;
                    zcInfo.updateTime=DateUtil.getCurrentTime()
                    if (!zcInfo.save(flush: true)) {
                        flash.message = '<font color="red">合格证更新失败</font>'
                    }else{
                        if (params.veh_Clztxx=='DP'){
                            flash.message = '合格证更新成功'
                        }else{
                            flash.message = '合格证更新成功'
                        }
                    }
                }

                if(params.kind=='0'){
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }

                    return
                }else{
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:zcInfo,symboMap:symboMap,printSet:printSet])
                    }

                    return
                }
            }
        }else{ //合格证编号不存在
            int   maxBH_INT=-1
            int   saveType=-1;
            if(params.kind=='0'){  //汽车
                saveType=0;
                String maxBH=printSet?.maxQX_car_upload
                if(maxBH&&maxBH.startsWith(start)){
                    String  maxBH_= maxBH.substring(8);
                    maxBH_INT=Integer.valueOf(maxBH_) ;
                }
            }else{ //农用车
                saveType=1;
                if (printSet?.maxQX_arg) {
                    String maxBH=printSet?.maxQX_arg_upload
                    if(maxBH&&maxBH.startsWith(start)){
                        String  maxBH_= maxBH.substring(8);
                        maxBH_INT=Integer.valueOf(maxBH_) ;
                    }
                }
            }
            if (maxBH_INT==-1||end_int>maxBH_INT){ //可以保存
                def zcInfoInstance = new ZCInfo(params)
                zcInfoInstance.createrId=loginUser.userName
                zcInfoInstance.createTime=DateUtil.getCurrentTime()
                zcInfoInstance.veh_workshopno=serialNum;
                String vircode=zcInfoInstance.veh_Zchgzbh_0
                zcInfoInstance.web_virtualcode=vircode;
                if(params.kind=='0'){
                    zcInfoInstance.veh_Type='0'
                }else{
                    zcInfoInstance.veh_Type='1'
                }
                //判断合格证信息是否与公告库中的信息匹配
                def checkMessage=zcInfoService.checkCarStorage(zcInfoInstance)
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    //有整车的二类底盘合格证SAP_status标记为4
                    def zcInfoLinkList=ZCInfo.findAllByVeh_Clsbdh(zcInfoInstance.veh_Clsbdh)
                    if (zcInfoLinkList.size()>0) {
                        if (zcInfoInstance.veh_Clztxx == 'DP') {
                            zcInfoInstance.SAP_status = '4'
                        }
                        zcInfoLinkList.each {zcInfoLink->
                            if (zcInfoLink.veh_Clztxx == 'DP') {
                                zcInfoLink.SAP_status = '4'
                                zcInfoLink.save(flush: true)
                            }
                        }

                    }
                    if (!zcInfoInstance.save(flush: true)){
                        flash.message = '<font color="red">保存失败</font>'
                    }else{
                        flash.message = '合格证保存成功'
                    }
                }


                if(params.kind=='0'){
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }

                    return
                }else{
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }

                    return
                }
            }else{ //不允许保存
                flash.message = '合格证编号小于已上传合格证流水码的最大值,不允许保存'
                def zcInfoInstance = new ZCInfo(params)
                if(params.kind=='0'){
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/car1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }
                    return
                }else{
                    if (params.processType=='0'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }else if (params.processType=='1'){
                        render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrveh1',model:[zcinfoInstance:zcInfoInstance,symboMap:symboMap,printSet:printSet])
                    }

                    return
                }
            }
        }
    }

    /**
     * @Description 验证合格证的公告信息是否符合公告库中的信息
     * @Create 2013-11-23 huxx
     */
    def check(){
        def result=[:]
        def msg="验证通过！"
        def flag='sucessed'
        //判断合格证信息是否与公告库中的信息匹配
        def checkMessage=zcInfoService.checkCarStorage(new ZCInfo(params))
        if (checkMessage){
            msg = checkMessage
            flag='failed'
            result.put("msg",msg);
            result.put("flag",flag)
        }else{
            result.put("msg",msg);
            result.put("flag",flag)
        }

        render (result as JSON)
    }
    /**
     * @Description 运输管理科验证合格证的公告信息是否符合公告库中的信息（去掉公告批次的检验）
     * @Create 2018-06-14 QJ
     */
    def transportCheck(){
        def result=[:]
        def msg="验证通过！"
        def flag='sucessed'
        //判断合格证信息是否与公告库中的信息匹配
        def checkMessage=zcInfoService.checkTransportCarStorage(new ZCInfo(params))
        if (checkMessage){
            msg = checkMessage
            flag='failed'
            result.put("msg",msg);
            result.put("flag",flag)
        }else{
            result.put("msg",msg);
            result.put("flag",flag)
        }

        render (result as JSON)
    }
    /**
     * @description 管理员操作合格证更换保存
     * @Auther Xu
     * @createTime 2013-8-10
     * @return
     * @update 2013-08-26 huxx 判断合格证编号是否重复条件,通过虚拟合格证编号来查询
     * @update 2013-09-05 huxx 添加调用后台pdf自动打印代码，并添加事务，当打印失败时保存的记录回滚
     * @Update 2013-11-13 huxx 添加合格证信息与分解前的公告信息匹配验证
     * @update 2013-11-18 huxx 修改为在打印PDF前验证公告信息是否匹配
     * @Update 2013-12-21 huxx 公告资源部和营销公司更换打印时默认是审核通过
     * @Update 2014-03-12 huxx 修改判定合格证更换记录是否已上传，添加了条件web_status=3的情况
     * @Update 2017-08-09 zhuwei 增加运输管理科打印PDF合格证时不在修改web_isprint字段，以免影响上传
     */
    def managesave() {
        def params =params
        def result=[:]
        def msg="保存成功！"
        def flag='sucessed'

        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        String curTime=DateUtil.getCurrentTime()

        //保存新颜色
        if (params.veh_Csys){
            def colors=Color.findAllByName(params.veh_Csys)
            if (colors==null||colors.size()==0){   //此情况下保存
                Color newColor=new Color();
                newColor.name=params.veh_Csys
                newColor.save(flush: true)
            }
        }

        //原合格证编号 判定原合格证编号是否存在，不存在系统默认已上传
        String oldHgzbh=params.veh_Zchgzbh_Re
        def oldHgz1=ZCInfo.findByVeh_Zchgzbh(oldHgzbh)
        def oldHgz=new ZCInfo(oldHgz1?.properties)   //防止合格证不换号时，新合格证号替换原合格证信息的情况

        //判定新输入合格证的合法性，1、新输入合格和原合格证编号相同合法 2、新合格证与原合格证不同时新合格证编号在zcinfo表中不存在合法
        String  veh_Zchgzbh=params.veh_Zchgzbh
        def hgz=ZCInfo.findByVeh_Zchgzbh(veh_Zchgzbh)
        if (veh_Zchgzbh==oldHgzbh){

        }else{
            if (hgz){
                msg="新合格证编号不合法！"
                flag='failed'
                result.put("msg",msg)
                result.put("flag",flag)
                return render (result as JSON)
            }
        }

        ZCInfo.withTransaction {trans->
            def oldFilePath=oldHgz?.upload_Path

            //保存完合格证信息后，打印PDF
            if(params.printType=='0'){
                def printMap=zcInfoService.printWCF(params,grailsApplication)
                if (printMap.isSuccess=='0'){
                    msg=printMap.msg
                    flag='failed'
                    result.put("msg",msg);
                    result.put("flag",flag)
                    return  render (result as JSON)
                } else{
                    params.vercode_r=printMap.vercode
                    params.vercode=printMap.vercode
                    params.veh_Zchgzbh_0=printMap.veh_wzhgzbh
                    params.veh_Dywym=printMap.veh_Dywym
                    params.upload_Path=printMap.msg
                    params.web_virtualcode=printMap.veh_wzhgzbh
                }
            }

            if (hgz){  //新合格证信息存在时（即合格证编号与原合格证编号相同时）
                hgz.properties = params
                //对于运输管理科打印的合格证，web_isprint这个打印类型（正式/PDF合格证）字段不在修改
                if(params.transPrint!='1'){
                    hgz.web_isprint=params.printType
                }

                hgz.updaterId=loginUser.userName;
                hgz.updateTime=curTime

                if (!hgz.save()) {
                    msg="新合格证信息更新失败！"
                    flag='failed'
                    result.put("msg",msg)
                    result.put("flag",flag)
                    return render (result as JSON)
                }else{
                    //保存成功后，保存更换记录
                    ZCInfoReplace zcInfoReplaceInstance = new ZCInfoReplace()

                    def zcInfoReplaceInstance1= perfectZcinforeplace(oldHgz,hgz,zcInfoReplaceInstance)

                    //插入修改记录
                    if (!oldHgz || oldHgz.web_status=='1' || oldHgz.web_status=='3'){ //已经上传到国家
                        zcInfoReplaceInstance1.veh_isupload=1
                    }else{  //未上传到国家或者上传国家失败
                        zcInfoReplaceInstance1.veh_isupload=0
                    }
                    zcInfoReplaceInstance1.veh_usertype=1
                    //公告资源部和营销公司更换打印时默认是审核通过
                    zcInfoReplaceInstance1.veh_coc_status=1
                    if (!zcInfoReplaceInstance1.save()){
                        trans.setRollbackOnly()
                        msg="新合格证更换记录更新失败！"
                        flag='failed'
                        result.put("msg",msg)
                        result.put("flag",flag)
                        return render (result as JSON)
                    }else{
                        msg="新合格证信息更新成功！"
                        flag='sucessed'
                        result.put("replaceId",zcInfoReplaceInstance.id)
                        result.put("msg",msg)
                        result.put("flag",flag)
                        result.put("fileName",hgz.veh_Zchgzbh)
                        result.put("filePath",hgz.upload_Path)
                    }
                }
            }else{  //新增合格证信息
                hgz = new ZCInfo(params)
                if (oldHgz){
                    hgz.web_status='0'
                }else{
                    hgz.web_status='1'
                }

                hgz.web_isprint=params.printType
                hgz.createrId=loginUser.userName
                hgz.createTime=curTime
                hgz.updaterId=loginUser.userName;
                hgz.updateTime=curTime

                if (!hgz.save()) {
                    trans.setRollbackOnly()
                    msg="新合格证信息保存失败！"
                    flag='failed'
                    result.put("msg",msg)
                    result.put("flag",flag)
                }else{
                    //插入修改记录此处默认新增合格证已经上传到国家
                    ZCInfoReplace zcInfoReplaceInstance = new ZCInfoReplace()

                    def zcInfoReplaceInstance1= perfectZcinforeplace(oldHgz,hgz,zcInfoReplaceInstance)
                    //处理原合格证号不存在时，原合格证号不显示的问题
                    zcInfoReplaceInstance1.veh_Zchgzbh= params.veh_Zchgzbh_Re
                    //新建非更换合格证信息不传CRM
                    if(params.veh_Zchgzbh_Re==''||params.veh_Zchgzbh_Re==null||params.veh_Zchgzbh_Re==hgz.veh_Zchgzbh){
                        zcInfoReplaceInstance1.transToCrm=1
                    }
                    //插入修改记录
                    if (!oldHgz || oldHgz.web_status=='1' || oldHgz.web_status=='3'){ //已经上传到国家
                        zcInfoReplaceInstance1.veh_isupload=1
                    }else{  //未上传到国家或者上传国家失败
                        zcInfoReplaceInstance1.veh_isupload=0
                    }
                    zcInfoReplaceInstance1.veh_usertype=1
                    //公告资源部和营销公司更换打印时默认是审核通过
                    zcInfoReplaceInstance1.veh_coc_status=1
                    if (!zcInfoReplaceInstance1.save()){
                        trans.setRollbackOnly()
                        msg="新合格证更换记录更新失败"
                        flag='failed'
                        result.put("msg",msg)
                        result.put("flag",flag)
                        return render (result as JSON)
                    }else{
                        msg="<div>新合格证信息保存成功！</div><div>完整合格证编号：${params.veh_Zchgzbh_0}</div>"
                        flag='sucessed'
                        result.put("replaceId",zcInfoReplaceInstance.id)
                        result.put("msg",msg)
                        result.put("flag",flag)
                        result.put("fileName",hgz.veh_Zchgzbh)
                        result.put("filePath",hgz.upload_Path)
                    }
                }

                //删除原合格证信息
                File f=new File(grailsApplication.config.upload.rootDir+oldFilePath)
                if (f.exists()){
                    f.delete()
                }
            }

        }

        render result as JSON
    }
    /**
     * @Description 打印失败后，将合格证更换记录删除
     * @return
     * @Creater 2013-09-05 huxx
     * @update 已废弃
     */
    def deleteZcinfoReplace(){
        def zcInfoReplace=ZCInfoReplace.get(params.id)
        def result=zcInfoReplace.delete(flush: true)
        if(result){
            render "false"
        }else{
            render "true"
        }

    }

    /**
     * @description 公告资源部农用车合格证修改
     * @Auther Xu
     * @createTime 2013-8-11
     * @return
     * @Update 已废弃
     */
    def noticesave() {
        String  veh_Zchgzbh=params.veh_Zchgzbh;
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        String curDate=DateUtil.getCurrentTime("yyyyMMdd")
        String dir="/zcinfopdf/${curDate}/"
        String uploadDir=UploadUtil.getRootDir(servletContext)
        File localFile=new File(uploadDir+dir)
        if (!localFile.isDirectory()){
            localFile.mkdirs()
        }
        //判断编号是否重复
        def cel = {
            eq("veh_Zchgzbh",veh_Zchgzbh)
            if (params.infoid){
                ne('id',params.infoid)
            }
        }
        def results = ZCInfo.createCriteria().list(cel)//相同编号的合格证列表
        //判定要修改的记录是否存在
        def user=User.get(loginUser.id)
        def codeList=''
        user.organs.each{
            codeList=it.organCode
        }
        def printSet = PrintSet.findByCodeAndUserid('main',loginUser.id)
        if (!printSet){
            printSet = new PrintSet()
            printSet.userid=codeList
        }
        if (results!=null&&results.size()!=0){
            flash.message = '存在合格证编号相同的合格证，请查证'
            def zcInfoInstance = new ZCInfo(params)
            if(params.kind=='0'){
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/carnotice',model:[zcinfoInstance:zcInfoInstance,printSet:printSet])
                return
            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehnotice',model:[zcinfoInstance:zcInfoInstance,printSet:printSet])
                return
            }
        }
        if (params.infoid){  //判断是否新建
            def zcInfoInstance = ZCInfo.get(params.infoid)
            def zcInfooldInstance = ZCInfo.get(params.infoid)
            if (!zcInfoInstance) {
                flash.message = '合格证不存在，请查证'
                def obj = new ZCInfo(params)
                if(params.kind=='0'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/carnotice',model:[zcinfoInstance:obj,printSet:printSet])
                    return
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehnotice',model:[zcinfoInstance:obj,printSet:printSet])
                    return
                }
            }

            zcInfoInstance.properties = params
            zcInfoInstance.updaterId=loginUser.userName;
            zcInfoInstance.updateTime=DateUtil.getCurrentTime()
            if (!zcInfoInstance.save(flush: true)) {
                flash.message = '更新失败'
            }else{
                ZCInfoReplace zcInfoReplaceInstance = new ZCInfoReplace()
                //插入修改记录
                if (zcInfoInstance.web_status=='1'){ //已经上传到国家
                    zcInfoReplaceInstance.veh_isupload=1
                    zcInfoReplaceInstance.veh_managestatus=0
                }else{  //未上传到国家或者上传国家失败
                    zcInfoReplaceInstance.veh_isupload=0
                    zcInfoReplaceInstance.veh_managestatus=1
                }
                def zcInfoReplaceInstance1= perfectZcinforeplace(zcInfooldInstance,zcInfoInstance,zcInfoReplaceInstance)
                if (!zcInfoReplaceInstance1.save(flush: true)){
                    flash.message = '更新失败'
                }else{
                    flash.message = '更新成功'
                }
            }
            if(params.kind=='0'){
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/carnotice',model:[zcinfoInstance:zcInfoInstance,printSet:printSet])
                return
            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehnotice',model:[zcinfoInstance:zcInfoInstance,printSet:printSet])
                return
            }
        }else{  //判断合格证编号是否合法
            def zcInfoInstance = new ZCInfo(params)
            flash.message = '请先查询后，再修改信息'
            if(params.kind=='0'){
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/carnoticee',model:[zcinfoInstance:zcInfoInstance,printSet:printSet])
                return
            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/generate/agrvehnotice',model:[zcinfoInstance:zcInfoInstance,printSet:printSet])
                return
            }
        }
    }
    /**
     *
     * @param zcInfooldInstance
     * @param zcInfoInstance
     * @param zcInfoReplace
     * @return
     * @Update 2013-11-03 huxx 添加了合格证类型的处理
     * @Update 2013-12-21 huxx 修改合格证更换记录的id不是原合格证的创建人id而是当前登录用户的id
     * Update 保存更换记录时将车间扫描的SAP序列号也保存到更换记录表中 by zhuwei 2017-07-26
     * @Update 2018-09-06 QJ 修改BUG——汽车更换打印保存更换信息时更换后没保存虚拟合格证、完整合格证
     */
    def   perfectZcinforeplace(ZCInfo zcInfooldInstance,ZCInfo zcInfoInstance,ZCInfoReplace zcInfoReplace)  {
        ///当前登录用户信息
        if(zcInfooldInstance!=null){
            zcInfoReplace.veh_Type=zcInfooldInstance.veh_Type
            zcInfoReplace.veh_Xnhgzbh=zcInfooldInstance.web_virtualcode //虚拟合格证编号
            zcInfoReplace.veh_Zchgzbh=zcInfooldInstance.veh_Zchgzbh  //整车合格证编号
            zcInfoReplace.veh_Dphgzbh=zcInfooldInstance.veh_Dphgzbh  //底盘合格证编号
            zcInfoReplace.veh_Fzrq=zcInfooldInstance.veh_Fzrq     //发证日期
            zcInfoReplace.veh_Clzzqymc=zcInfooldInstance.veh_Clzzqymc //车辆制造企业名称
            zcInfoReplace.veh_Qyid=zcInfooldInstance.veh_Qyid     //企业ID
            zcInfoReplace.veh_Clfl=zcInfooldInstance.veh_Clfl     //车辆分类
            zcInfoReplace.veh_Clmc=zcInfooldInstance.veh_Clmc     //车辆名称
            zcInfoReplace.veh_Clpp=zcInfooldInstance.veh_Clpp     //车辆品牌
            zcInfoReplace.veh_Clxh=zcInfooldInstance.veh_Clxh    //车辆型号
            zcInfoReplace.veh_Csys=zcInfooldInstance.veh_Csys     //车身颜色
            zcInfoReplace.veh_Dpxh=zcInfooldInstance.veh_Dpxh     //底盘型号
            zcInfoReplace.veh_Dpid =zcInfooldInstance.veh_Dpid    //底盘ID
            zcInfoReplace.veh_Clsbdh =zcInfooldInstance.veh_Clsbdh   //车辆识别代号
            zcInfoReplace.veh_Cjh =zcInfooldInstance.veh_Cjh        //车架号
            zcInfoReplace.veh_Fdjh =zcInfooldInstance.veh_Fdjh       //发动机号
            zcInfoReplace.veh_Fdjxh=zcInfooldInstance.veh_Fdjxh    //发动机型号
            zcInfoReplace.veh_Rlzl =zcInfooldInstance.veh_Rlzl    //燃料种类
            zcInfoReplace.veh_Pl =zcInfooldInstance.veh_Pl      //排量
            zcInfoReplace.veh_Gl =zcInfooldInstance.veh_Gl      //功率
            zcInfoReplace.veh_zdjgl =zcInfooldInstance.veh_zdjgl   //最大净功率
            zcInfoReplace.veh_Zxxs =zcInfooldInstance.veh_Zxxs    //转向形式
            zcInfoReplace.veh_Qlj =zcInfooldInstance.veh_Qlj     //前轮距
            zcInfoReplace.veh_Hlj =zcInfooldInstance.veh_Hlj     //后轮距
            zcInfoReplace.veh_Lts =zcInfooldInstance.veh_Lts     //轮胎数
            zcInfoReplace.veh_Ltgg =zcInfooldInstance.veh_Ltgg    //轮胎规格
            zcInfoReplace.veh_Gbthps =zcInfooldInstance.veh_Gbthps  //钢板弹簧片数
            zcInfoReplace.veh_Zj   =zcInfooldInstance.veh_Zj    //轴距
            zcInfoReplace.veh_Zh =zcInfooldInstance.veh_Zh      //轴荷
            zcInfoReplace.veh_Zs =zcInfooldInstance.veh_Zs      //轴数
            zcInfoReplace.veh_Wkc=zcInfooldInstance.veh_Wkc       //外廓长
            zcInfoReplace.veh_Wkk =zcInfooldInstance.veh_Wkk      //外廓宽
            zcInfoReplace.veh_Wkg =zcInfooldInstance.veh_Wkg      //外廓高
            zcInfoReplace.veh_Hxnbc =zcInfooldInstance.veh_Hxnbc    //货箱内部长
            zcInfoReplace.veh_Hxnbk=zcInfooldInstance.veh_Hxnbk     //货箱内部宽
            zcInfoReplace.veh_Hxnbg =zcInfooldInstance.veh_Hxnbg    //货箱内部高
            zcInfoReplace.veh_Zzl  =zcInfooldInstance.veh_Zzl     //总质量
            zcInfoReplace.veh_Edzzl=zcInfooldInstance.veh_Edzzl     //额定载质量
            zcInfoReplace.veh_Zbzl =zcInfooldInstance.veh_Zbzl     //整备质量
            zcInfoReplace.veh_Zzllyxs=zcInfooldInstance.veh_Zzllyxs   //载质量利用系数
            zcInfoReplace.veh_Zqyzzl =zcInfooldInstance.veh_Zqyzzl  //准牵引总质量
            zcInfoReplace.veh_Edzk   =zcInfooldInstance.veh_Edzk  //额定载客
            zcInfoReplace.veh_Bgcazzdyxzzl=zcInfooldInstance.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
            zcInfoReplace.veh_Jsszcrs =zcInfooldInstance.veh_Jsszcrs //驾驶室准乘人数
            zcInfoReplace.veh_Qzdfs =zcInfooldInstance.veh_Qzdfs      //前制动方式
            zcInfoReplace.veh_Hzdfs =zcInfooldInstance.veh_Hzdfs   //后制动方式
            zcInfoReplace.veh_Qzdczfs =zcInfooldInstance.veh_Qzdczfs    //前制动操作方式
            zcInfoReplace.veh_Cpggh =zcInfooldInstance.veh_Cpggh   //产品公告号
            zcInfoReplace.veh_Ggsxrq =zcInfooldInstance.veh_Ggsxrq  //公告生效日期
            zcInfoReplace.veh_Zzbh  =zcInfooldInstance.veh_Zzbh      //纸张编号
            zcInfoReplace.veh_Dywym =zcInfooldInstance.veh_Dywym  //打印唯一码
            zcInfoReplace.veh_Zgcs  =zcInfooldInstance.veh_Zgcs    //最高车速
            zcInfoReplace.veh_Clzzrq =zcInfooldInstance.veh_Clzzrq  //车辆制造日期
            zcInfoReplace.veh_Bz   =zcInfooldInstance.veh_Bz    //备注
            zcInfoReplace.veh_Qybz  =zcInfooldInstance.veh_Qybz   //企业标准
            zcInfoReplace.veh_Cpscdz =zcInfooldInstance.veh_Cpscdz  //产品生产地址
            zcInfoReplace.veh_Qyqtxx =zcInfooldInstance.veh_Qyqtxx  //企业其它信息
            zcInfoReplace.veh_Pfbz   =zcInfooldInstance.veh_Pfbz  //排放标准
            zcInfoReplace.veh_Clztxx =zcInfooldInstance.veh_Clztxx  //车辆状态信息
            zcInfoReplace.veh_Jss   =zcInfooldInstance.veh_Jss   //驾驶室
            zcInfoReplace.veh_Jsslx =zcInfooldInstance.veh_Jsslx   //驾驶室类型
            zcInfoReplace.veh_Zchgzbh_0 =zcInfooldInstance.veh_Zchgzbh_0 //完整合格证编号
            zcInfoReplace.used  =zcInfooldInstance.used
            zcInfoReplace.used2  =zcInfooldInstance.used2
            zcInfoReplace.upload =zcInfooldInstance.upload//
            zcInfoReplace.vercode =zcInfooldInstance.vercode//二维码
            zcInfoReplace.veh_Yh  =zcInfooldInstance.veh_Yh//油耗
            zcInfoReplace.veh_VinFourBit=zcInfooldInstance.veh_VinFourBit //vin第四位
            zcInfoReplace.veh_Ggpc  =zcInfooldInstance.veh_Ggpc      //公告批次
            zcInfoReplace.veh_createTime =zcInfooldInstance.createTime //创建时间
            zcInfoReplace.veh_createrId =zcInfooldInstance.createrId //创建人id
            zcInfoReplace.veh_pzxlh   =zcInfooldInstance.veh_pzxlh                //配置序列号
            zcInfoReplace.upload_Path   =zcInfooldInstance.upload_Path//合格证上传相对路径
            zcInfoReplace.veh_clzzqyxx =zcInfooldInstance.veh_clzzqyxx//车辆制造企业信息
            zcInfoReplace.SAP_No = zcInfooldInstance.SAP_No
        }
        //修改后的合格证信息
        zcInfoReplace.veh_Type_R=zcInfoInstance.veh_Type
        zcInfoReplace.veh_Zchgzbh_R =zcInfoInstance.veh_Zchgzbh   //整车合格证编号
        zcInfoReplace.veh_Dphgzbh_R =zcInfoInstance.veh_Dphgzbh  //底盘合格证编号
        zcInfoReplace.veh_Fzrq_R   =zcInfoInstance.veh_Fzrq   //发证日期
        zcInfoReplace.veh_Clzzqymc_R =zcInfoInstance.veh_Clzzqymc //车辆制造企业名称
        zcInfoReplace.veh_Qyid_R   =zcInfoInstance.veh_Qyid   //企业ID
        zcInfoReplace.veh_Clfl_R  =zcInfoInstance.veh_Clfl    //车辆分类
        zcInfoReplace.veh_Clmc_R  =zcInfoInstance.veh_Clmc    //车辆名称
        zcInfoReplace.veh_Clpp_R  =zcInfoInstance.veh_Clpp    //车辆品牌
        zcInfoReplace.veh_Clxh_R  =zcInfoInstance.veh_Clxh   //车辆型号
        zcInfoReplace.veh_Csys_R   =zcInfoInstance.veh_Csys   //车身颜色
        zcInfoReplace.veh_Dpxh_R  =zcInfoInstance.veh_Dpxh    //底盘型号
        zcInfoReplace.veh_Dpid_R  =zcInfoInstance.veh_Dpid    //底盘ID
        zcInfoReplace.veh_Clsbdh_R  =zcInfoInstance.veh_Clsbdh   //车辆识别代号
        zcInfoReplace.veh_Cjh_R   =zcInfoInstance.veh_Cjh       //车架号
        zcInfoReplace.veh_Fdjh_R =zcInfoInstance.veh_Fdjh        //发动机号

        zcInfoReplace.veh_Fdjxh_R =zcInfoInstance.veh_Fdjxh    //发动机型号
        zcInfoReplace.veh_Rlzl_R  =zcInfoInstance.veh_Rlzl    //燃料种类
        zcInfoReplace.veh_Pl_R    =zcInfoInstance.veh_Pl    //排量
        zcInfoReplace.veh_Gl_R  =zcInfoInstance.veh_Gl      //功率
        zcInfoReplace.veh_zdjgl_R  =zcInfoInstance.veh_zdjgl   //最大净功率
        zcInfoReplace.veh_Zxxs_R   =zcInfoInstance.veh_Zxxs   //转向形式
        zcInfoReplace.veh_Qlj_R   =zcInfoInstance.veh_Qlj    //前轮距
        zcInfoReplace.veh_Hlj_R  =zcInfoInstance.veh_Hlj     //后轮距
        zcInfoReplace.veh_Lts_R  =zcInfoInstance.veh_Lts     //轮胎数
        zcInfoReplace.veh_Ltgg_R   =zcInfoInstance.veh_Ltgg   //轮胎规格
        zcInfoReplace.veh_Gbthps_R  =zcInfoInstance.veh_Gbthps  //钢板弹簧片数
        zcInfoReplace.veh_Zj_R    =zcInfoInstance.veh_Zj    //轴距
        zcInfoReplace.veh_Zh_R    =zcInfoInstance.veh_Zh    //轴荷
        zcInfoReplace.veh_Zs_R   =zcInfoInstance.veh_Zs     //轴数
        zcInfoReplace.veh_Wkc_R   =zcInfoInstance.veh_Wkc     //外廓长
        zcInfoReplace.veh_Wkk_R  =zcInfoInstance.veh_Wkk      //外廓宽
        zcInfoReplace.veh_Wkg_R  =zcInfoInstance.veh_Wkg      //外廓高
        zcInfoReplace.veh_Hxnbc_R  =zcInfoInstance.veh_Hxnbc    //货箱内部长
        zcInfoReplace.veh_Hxnbk_R  =zcInfoInstance.veh_Hxnbk    //货箱内部宽
        zcInfoReplace.veh_Hxnbg_R   =zcInfoInstance.veh_Hxnbg   //货箱内部高
        zcInfoReplace.veh_Zzl_R    =zcInfoInstance.veh_Zzl    //总质量
        zcInfoReplace.veh_Edzzl_R  =zcInfoInstance.veh_Edzzl    //额定载质量
        zcInfoReplace.veh_Zbzl_R    =zcInfoInstance.veh_Zbzl   //整备质量
        zcInfoReplace.veh_Zzllyxs_R =zcInfoInstance.veh_Zzllyxs   //载质量利用系数
        zcInfoReplace.veh_Zqyzzl_R    =zcInfoInstance.veh_Zqyzzl//准牵引总质量
        zcInfoReplace.veh_Edzk_R    =zcInfoInstance.veh_Edzk  //额定载客
        zcInfoReplace.veh_Bgcazzdyxzzl_R =zcInfoInstance.veh_Bgcazzdyxzzl //半挂车按座
        zcInfoReplace.veh_Jsszcrs_R =zcInfoInstance.veh_Jsszcrs  //驾驶室准乘人数
        zcInfoReplace.veh_Qzdfs_R  =zcInfoInstance.veh_Qzdfs      //前制动方式
        zcInfoReplace.veh_Hzdfs_R   =zcInfoInstance.veh_Hzdfs  //后制动方式
        zcInfoReplace.veh_Qzdczfs_R  =zcInfoInstance.veh_Qzdczfs    //前制动操作方式
        zcInfoReplace.veh_Cpggh_R    =zcInfoInstance.veh_Cpggh //产品公告号
        zcInfoReplace.veh_Ggsxrq_R =zcInfoInstance.veh_Ggsxrq   //公告生效日期
        zcInfoReplace.veh_Zzbh_R    =zcInfoInstance.veh_Zzbh     //纸张编号
        zcInfoReplace.veh_Zgcs_R   =zcInfoInstance.veh_Zgcs     //最高车速
        zcInfoReplace.veh_Clzzrq_R   =zcInfoInstance.veh_Clzzrq  //车辆制造日期
        zcInfoReplace.veh_Bz_R     =zcInfoInstance.veh_Bz    //备注
        zcInfoReplace.veh_Qybz_R    =zcInfoInstance.veh_Qybz   //企业标准
        zcInfoReplace.veh_Cpscdz_R   =zcInfoInstance.veh_Cpscdz  //产品生产地址
        zcInfoReplace.veh_Qyqtxx_R   =zcInfoInstance.veh_Qyqtxx  //企业其它信息
        zcInfoReplace.veh_Pfbz_R     =zcInfoInstance.veh_Pfbz  //排放标准
        zcInfoReplace.veh_Clztxx_R   =zcInfoInstance.veh_Clztxx  //车辆状态信息
        zcInfoReplace.veh_Jss_R     =zcInfoInstance.veh_Jss   //驾驶室
        zcInfoReplace.veh_Jsslx_R    =zcInfoInstance.veh_Jsslx  //驾驶室类型
        zcInfoReplace.veh_Yh_R    =zcInfoInstance.veh_Yh//油耗
        zcInfoReplace.veh_VinFourBit_R   =zcInfoInstance.veh_VinFourBit//vin第四位
        zcInfoReplace.veh_Ggpc_R    =zcInfoInstance.veh_Ggpc     //公告批次
        zcInfoReplace.veh_pzxlh_R  =zcInfoInstance.veh_pzxlh                 //配置序列号
        zcInfoReplace.veh_clzzqyxx_R = zcInfoInstance.veh_clzzqyxx//车辆制造企业信息
        zcInfoReplace.SAP_No = zcInfoInstance.SAP_No
        zcInfoReplace.veh_usertype =1             //合格证修改记录作者类型  0：经销商修改、更换 1：公告资源部修改(只是针对已经上传到国家的合格证修改 才会有修改记录)
        zcInfoReplace.createTime = DateUtil.getCurrentTime() //申请时间
        zcInfoReplace.createrId =getCurrentUser()?.id //申请人id
        zcInfoReplace.veh_Dywym_R=zcInfoInstance.veh_Dywym
        zcInfoReplace.veh_Zchgzbh_0_R=zcInfoInstance.veh_Zchgzbh_0
        zcInfoReplace.veh_Xnhgzbh_R=zcInfoInstance.web_virtualcode
        return  zcInfoReplace
    }

    /**
     *@Descriptions 经销商 >> 合格证查询、打印 （主页面）
     *@Auther zouq
     *@createTime
     */
    def index_ZCI()
    {
        def  veh_Zchgzbh_0  = params.veh_Zchgzbh_0;  ///虚拟合格证编号信息
        if (!veh_Zchgzbh_0){
            render(view:'/cn/com/wz/vehcert/zcinfo/dealer/index')
            return
        }
        def  zciInfoModel =  ZCInfo.findByVeh_Zchgzbh_0(veh_Zchgzbh_0)
        render(view:'/cn/com/wz/vehcert/zcinfo/dealer/index',model: [zciInfoModel:zciInfoModel])
    }

    /**
     *@Descriptions 经销商 >> 合格证查询、打印 （下载函数）
     *@Auther zouq
     *@createTime
     *@update 增加打印临时表中type字段表示是下载还是在线打印
     */
    def download_ZCI()
    {
        def result=[:]
        def id = params.hidden_id;    ////要下载合格证ID信息
        def zcinfoModel = ZCInfo.get(id)
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def userName =loginUser.userName
        def organCodeList=[]
        if(loginUser.organs.size()>0){
            loginUser.organs.each{
                if(it.organCode){
                    organCodeList.add(it.organCode)
                }
            }
        }
        if(zcinfoModel.veh_Type=='0'&&!organCodeList.contains('qccp')){
            List vinObjectList = newDmsSynService.findVinOfDistributor(loginUser.userName)
            List vinList =[]
            if(vinObjectList.size()>0){
                vinObjectList.each{object->
                    if(object.new_loanislock==0||object.new_loanislock==6){
                        vinList.add(object.new_vin)
                    }
                }
            }
            if(!vinList.contains(zcinfoModel.veh_Clsbdh)){
                result.msg="6"
                result.flag="failed"
                return render (result as JSON)
            }
        }
        if (zcinfoModel){
            //判断公告是否扩展
            def carStorage=CarStorage.executeQuery("select max(veh_Ggpc) as veh_Ggpc from CarStorage where veh_Clxh=:veh_Clxh",[veh_Clxh: zcinfoModel.veh_Clxh])
            if (carStorage){
                if (!(carStorage.get(0)==zcinfoModel.veh_Ggpc)){
                    result.msg="2"
                    result.flag="failed"
                    return render (result as JSON)
                }
            }else{
                result.msg="2"
                result.flag="failed"
                return render (result as JSON)
            }
            //去掉发证日期超过两天限制此处应判断此合格证上使用的公告批次是否已经发生变化
//            //判断日期是否超过两日
//            def now=new Date()
//            def old=now-2
//            def oldTime=DateUtil.getFormatTime(old,"yyyy年MM月dd日")
//            if (zcinfoModel.veh_Fzrq<oldTime){
//                result.msg="3"
//                result.flag="failed"
//                return render (result as JSON)
//            }
            //判断文件是否存在  ,不存在就生成
            File file=new File(grailsApplication.config.upload.rootDir+zcinfoModel.upload_Path)
            if (!file.exists()){
                def printMap=zcInfoService.printWCF(zcinfoModel.properties,grailsApplication)
                if (printMap.isSuccess=='0'){
                    result.put("msg",printMap.msg);
                    result.put("flag",'failed')
                    return  render (result as JSON)
                } else{  //将返回的三个值保存到数据库中
                    zcinfoModel.vercode=printMap.vercode
                    zcinfoModel.veh_Zchgzbh_0=printMap.veh_wzhgzbh
                    zcinfoModel.veh_Dywym=printMap.veh_Dywym
                    zcinfoModel.upload_Path=printMap.msg
                    zcinfoModel.web_virtualcode=printMap.veh_wzhgzbh
                    if (!zcinfoModel.save(flush: true)){
                        println "错误信息如下："+ zcinfoModel.errors.allErrors.toListString()
                        result.put("msg",'4');
                        result.put("flag",'failed')
                        return  render (result as JSON)
                    }
                }
            }

            ZCInfoTemp zcinfoTemp = new ZCInfoTemp();
            zcinfoTemp.veh_Type =zcinfoModel.veh_Type//汽车和农用车标示   0：汽车 1：农用车
            zcinfoTemp.veh_Zchgzbh =zcinfoModel.veh_Zchgzbh  //整车合格证编号
            zcinfoTemp.veh_Dphgzbh =zcinfoModel.veh_Dphgzbh  //底盘合格证编号
            zcinfoTemp.veh_Fzrq =zcinfoModel.veh_Fzrq //发证日期
            zcinfoTemp.veh_Clzzqymc =zcinfoModel.veh_Clzzqymc //车辆制造企业名称
            zcinfoTemp.veh_Qyid =zcinfoModel.veh_Qyid //企业ID
            zcinfoTemp.veh_Clfl =zcinfoModel.veh_Clfl //车辆分类
            zcinfoTemp.veh_Clmc =zcinfoModel.veh_Clmc //车辆名称
            zcinfoTemp.veh_Clpp =zcinfoModel.veh_Clpp //车辆品牌
            zcinfoTemp.veh_Clxh =zcinfoModel.veh_Clxh//车辆型号
            zcinfoTemp.veh_Csys =zcinfoModel.veh_Csys //车身颜色
            zcinfoTemp.veh_Dpxh =zcinfoModel.veh_Dpxh //底盘型号
            zcinfoTemp.veh_Dpid =zcinfoModel.veh_Dpid //底盘ID
            zcinfoTemp.veh_Clsbdh =zcinfoModel.veh_Clsbdh//车辆识别代号
            zcinfoTemp.veh_Cjh =zcinfoModel.veh_Cjh //车架号
            zcinfoTemp.veh_Fdjh =zcinfoModel.veh_Fdjh//发动机号

            zcinfoTemp.veh_Fdjxh =zcinfoModel.veh_Fdjxh//发动机型号
            zcinfoTemp.veh_Rlzl =zcinfoModel.veh_Rlzl //燃料种类
            zcinfoTemp.veh_Pl =zcinfoModel.veh_Pl   //排量
            zcinfoTemp.veh_Gl =zcinfoModel.veh_Gl   //功率
            zcinfoTemp.veh_zdjgl =zcinfoModel.veh_zdjgl//最大净功率
            zcinfoTemp.veh_Zxxs =zcinfoModel.veh_Zxxs //转向形式
            zcinfoTemp.veh_Qlj =zcinfoModel.veh_Qlj  //前轮距
            zcinfoTemp.veh_Hlj =zcinfoModel.veh_Hlj  //后轮距
            zcinfoTemp.veh_Lts =zcinfoModel.veh_Lts  //轮胎数
            zcinfoTemp.veh_Ltgg =zcinfoModel.veh_Ltgg //轮胎规格
            zcinfoTemp.veh_Gbthps =zcinfoModel.veh_Gbthps   //钢板弹簧片数
            zcinfoTemp.veh_Zj =zcinfoModel.veh_Zj   //轴距
            zcinfoTemp.veh_Zh =zcinfoModel.veh_Zh   //轴荷
            zcinfoTemp.veh_Zs =zcinfoModel.veh_Zs  //轴数
            zcinfoTemp.veh_Wkc =zcinfoModel.veh_Wkc   //外廓长
            zcinfoTemp.veh_Wkk =zcinfoModel.veh_Wkk   //外廓宽
            zcinfoTemp.veh_Wkg =zcinfoModel.veh_Wkg   //外廓高
            zcinfoTemp.veh_Hxnbc =zcinfoModel.veh_Hxnbc //货箱内部长
            zcinfoTemp.veh_Hxnbk =zcinfoModel.veh_Hxnbk //货箱内部宽
            zcinfoTemp.veh_Hxnbg =zcinfoModel.veh_Hxnbg //货箱内部高
            zcinfoTemp.veh_Zzl =zcinfoModel.veh_Zzl   //总质量
            zcinfoTemp.veh_Edzzl =zcinfoModel.veh_Edzzl //额定载质量
            zcinfoTemp.veh_Zbzl =zcinfoModel.veh_Zbzl  //整备质量
            zcinfoTemp.veh_Zzllyxs =zcinfoModel.veh_Zzllyxs   //载质量利用系数
            zcinfoTemp.veh_Zqyzzl =zcinfoModel.veh_Zqyzzl   //准牵引总质量
            zcinfoTemp.veh_Edzk =zcinfoModel.veh_Edzk //额定载客
            zcinfoTemp.veh_Bgcazzdyxzzl =zcinfoModel.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
            zcinfoTemp.veh_Jsszcrs =zcinfoModel.veh_Jsszcrs  //驾驶室准乘人数
            zcinfoTemp.veh_Qzdfs =zcinfoModel.veh_Qzdfs   //前制动方式
            zcinfoTemp.veh_Hzdfs =zcinfoModel.veh_Hzdfs//后制动方式
            zcinfoTemp.veh_Qzdczfs =zcinfoModel.veh_Qzdczfs //前制动操作方式
            zcinfoTemp.veh_Hzdczfs =zcinfoModel.veh_Hzdczfs //后制动操作方式
            zcinfoTemp.veh_Cpggh =zcinfoModel.veh_Cpggh//产品公告号
            zcinfoTemp.veh_Ggsxrq =zcinfoModel.veh_Ggsxrq   //公告生效日期
            zcinfoTemp.veh_Zzbh =zcinfoModel.veh_Zzbh//纸张编号
            zcinfoTemp.veh_Dywym =zcinfoModel.veh_Dywym   //打印唯一码
            zcinfoTemp.veh_Zgcs =zcinfoModel.veh_Zgcs  //最高车速
            zcinfoTemp.veh_Clzzrq =zcinfoModel.veh_Clzzrq   //车辆制造日期
            zcinfoTemp.veh_Bz =zcinfoModel.veh_Bz   //备注
            zcinfoTemp.veh_Qybz =zcinfoModel.veh_Qybz //企业标准
            zcinfoTemp.veh_Cpscdz =zcinfoModel.veh_Cpscdz   //产品生产地址
            zcinfoTemp.veh_Qyqtxx =zcinfoModel.veh_Qyqtxx   //企业其它信息
            zcinfoTemp.veh_Pfbz =zcinfoModel.veh_Pfbz//排放标准
            zcinfoTemp.veh_Clztxx =zcinfoModel.veh_Clztxx   //车辆状态信息
            zcinfoTemp.veh_Jss =zcinfoModel.veh_Jss  //驾驶室
            zcinfoTemp.veh_Jsslx =zcinfoModel.veh_Jsslx//驾驶室类型
            zcinfoTemp.veh_Zchgzbh_0 =zcinfoModel.veh_Zchgzbh_0  //完整合格证编号
            zcinfoTemp.used =zcinfoModel.used
            zcinfoTemp.used2 =zcinfoModel.used2
            zcinfoTemp.upload =zcinfoModel.upload //
            zcinfoTemp.veh_Yh =zcinfoModel.veh_Yh  //油耗
            zcinfoTemp.veh_VinFourBit =zcinfoModel.veh_VinFourBit //vin第四位
            zcinfoTemp.veh_Ggpc =zcinfoModel.veh_Ggpc//公告批次
            zcinfoTemp.veh_pzxlh =zcinfoModel.veh_pzxlh  //配置序列号
            zcinfoTemp.veh_clzzqyxx =zcinfoModel.veh_clzzqyxx //车辆制造企业信息
            zcinfoTemp.veh_workshopno =zcinfoModel.veh_workshopno  //车间编号

            zcinfoTemp.createTime =zcinfoModel.createTime //创建时间
            zcinfoTemp.createrId =zcinfoModel.createrId //创建人的用户名称
            zcinfoTemp.realName=zcinfoModel.createrId//创建人的用户名称
            zcinfoTemp.updateTime =zcinfoModel.updateTime  //最后更新时间
            zcinfoTemp.updaterId =zcinfoModel.updaterId //最后更新人
            zcinfoTemp.uploader_Id =zcinfoModel.uploader_Id //上传人
            zcinfoTemp.uploader_Time =zcinfoModel.uploader_Time //上传时间
            zcinfoTemp.upload_Failddm =zcinfoModel.upload_Failddm //上传失败返回代码
            zcinfoTemp.upload_Path =zcinfoModel.upload_Path  //合格证上传相对路径


            zcinfoTemp.web_status =zcinfoModel.web_status //合格证状态 是否上传0:未上传 1：上传成功 2：上传失败
            zcinfoTemp.web_isprint =zcinfoModel.web_isprint //合格证是否打印
            zcinfoTemp.web_virtualcode =zcinfoModel.web_virtualcode//虚拟合格证编号
            zcinfoTemp.vercode =zcinfoModel.vercode//校验信息
            zcinfoTemp.user_down = loginUser.id
            ////完成持久化操作
            zcinfoTemp.insertTime = DateUtil.getCurrentTime('yyyy-MM-dd HH:mm:ss');    ////记录插入时间
            zcinfoTemp.type = params.type
            if(zcinfoTemp.save(flush: true)){
                result.flag="success"
                result.upload_path=zcinfoTemp.upload_Path
                return render (result as JSON)
            }else{
                result.msg="5"
                result.flag="failed"
                return render (result as JSON)
            }
        }else{
            result.msg="1"
            result.flag="failed"
            return render (result as JSON)
        }

    }

    /**
     * @Description 进入合格证list页面
     * @Create 2013-08-04 huxx
     */
    def index(){
        render(view: "/cn/com/wz/vehcert/zcinfo/index",model:[params:params])
    }
    /**
     * @Description 获取未提交的合格证信息
     * @return
     * @Create  2013-08-04 huxx
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
        def lst=zcInfoService.selectZCInfoList(params)
        render ([rows: lst,total: lst.totalCount] as JSON )

    }
    /**
     * @Description 获取车间生产的合格证信息，用于车间查询
     * @return
     * @Create  2013-11-28 liuly
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
        def result=zcInfoService.selectZcinfoByShop(params)
        render result as JSON

    }

    /**
     * @Description 将车间合格证信息提交到服务器中，包括生成的pdf、一致性证书、合格证信息
     * @Create 2013-08-05 huxx
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
            def leftDay=grailsApplication.config.client.leftDay
            //同步一致性证书
            def cocFailList=[]
            def map=[:]
            map.remoteDB=remoteDB
            map.leftDay=leftDay
            cocFailList=zcInfoService.uploadCoc(map)

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
            def cocFails=[]
            cocFails.addAll(cocFailList.collect())
            m.failList=cocFails
            m.remoteDB=remoteDB
            m.symboStr=symboStr
            m.leftDay=leftDay
            m.userId=loginUser?.id
            m.userName=loginUser?.userName
            m.changeStatus=grailsApplication.config.changeStatus
            m.veh_Zchgzbh1=params.veh_Zchgzbh1
            m.veh_Zchgzbh2=params.veh_Zchgzbh2
            zcFailList=zcInfoService.uploadZcInfo(m)
            remoteDB.close()

            //处理错误信息
            if ( cocFailList || zcFailList){
                msg="一致性证书上传失败记录id：${cocFailList},合格证上传失败记录id：${zcFailList}"
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
     *@Descriptions 经销商 >> 合格证日期变更
     *@Auther zouq
     *@createTime  2013-08-05
     */
    def zcinfoBychangeDateIndex()
    {
        if (!params.veh_Clsbdh){
            render(view:'/cn/com/wz/vehcert/zcinfo/dealer/updateZcinfoBydate')
            return
        }
        def cel={
            if(params.veh_Clsbdh){
                eq('veh_Clsbdh',params.veh_Clsbdh)
            }
            if (params.veh_Clztxx){
                eq('veh_Clztxx',params.veh_Clztxx)
            }
        }
        def  zciInfoModel =  ZCInfo.createCriteria().list(cel)
        def zcinfoResult=[]
        if (zciInfoModel.size()>0){
            zcinfoResult=zciInfoModel.get(0)
        }else{
            render(view:'/cn/com/wz/vehcert/zcinfo/dealer/updateZcinfoBydate')
            return
        }
        render(view:'/cn/com/wz/vehcert/zcinfo/dealer/updateZcinfoBydate',model: [zciInfoModel:zcinfoResult])
    }

    /**
     *@Descriptions 经销商 >> 合格证日期变更(更新日期操作函数)
     *@Auther zouq
     *@createTime  2013-08-05
     */
    def updateZcinfoByDate()
    {
        def result=[:]
        /// 只修改了 发证日期和车辆在制造日期
        def veh_Fzrq = params.veh_Fzrq
        def veh_Clzzrq = params.veh_Clzzrq
        def veh_Zzbh_re = params.veh_Zzbh_re
        if (!veh_Fzrq&&!veh_Clzzrq){
            result.flag='0'
            result.msg="必须填写发证日期！"
            return render (result as JSON)
        }
        def zcInfooldInstance = ZCInfo.get(params.hidden_id)     ///变更的合格证信息

        if (!zcInfooldInstance){
            result.flag='0'
            result.msg="修改的记录不存在，请重新查询！"
            return render (result as JSON)
        }

        // 提交锁
        if (params.version) {
            def version = params.version.toLong()
            if (zcInfooldInstance.version > version) {
                result.flag='0'
                result.msg="要修改的记录已更新，请重新查询！"
                return render (result as JSON)
            }
        }
        //对车辆发证日期和车辆制造日期赋值
        def oldFzrq=zcInfooldInstance.veh_Fzrq
        def oldClzzrq=zcInfooldInstance.veh_Clzzrq

        ///当前登录用户信息
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)

        zcInfooldInstance.veh_Fzrq = veh_Fzrq
        zcInfooldInstance.veh_Clzzrq =  veh_Clzzrq
        def printMap=zcInfoService.printWCF(zcInfooldInstance.properties,grailsApplication)
        if (printMap.isSuccess=='0'){
            result.put("msg",printMap.msg);
            result.put("flag",'0')
            return  render (result as JSON)
        } else{
            params.vercode_r=printMap.vercode
            params.veh_Zchgzbh_0=printMap.veh_wzhgzbh
            params.veh_Dywym=printMap.veh_Dywym
            params.upload_Path=printMap.msg
            params.web_virtualcode=printMap.veh_wzhgzbh
        }


        ///日期变更记录表(初始化)
        ZCInfoReplace zcInfoReplace = new ZCInfoReplace();

        //需要合格证类型 0：只要整车合格证 1：只要底盘合格证  2：整车和底盘合格证
        if (zcInfooldInstance.veh_Clztxx=="DP"){
            zcInfoReplace.veh_Need_Cer=1
        }else if (zcInfooldInstance.veh_Clztxx=="QX"){
            zcInfoReplace.veh_Need_Cer=0
        }
        //原合格证信息
        zcInfoReplace.veh_Type = zcInfooldInstance.veh_Type   //汽车和农用车标示
        zcInfoReplace.veh_Xnhgzbh = zcInfooldInstance.web_virtualcode //虚拟合格证编号
        zcInfoReplace.veh_Zchgzbh = zcInfooldInstance.veh_Zchgzbh  //整车合格证编号
        zcInfoReplace.veh_Dphgzbh = zcInfooldInstance.veh_Dphgzbh  //底盘合格证编号
        zcInfoReplace.veh_Fzrq = oldFzrq //发证日期
        zcInfoReplace.veh_Clzzqymc = zcInfooldInstance.veh_Clzzqymc //车辆制造企业名称
        zcInfoReplace.veh_Qyid = zcInfooldInstance.veh_Qyid //企业ID
        zcInfoReplace.veh_Clfl = zcInfooldInstance.veh_Clfl //车辆分类
        zcInfoReplace.veh_Clmc = zcInfooldInstance.veh_Clmc //车辆名称
        zcInfoReplace.veh_Clpp = zcInfooldInstance.veh_Clpp //车辆品牌
        zcInfoReplace.veh_Clxh = zcInfooldInstance.veh_Clxh//车辆型号
        zcInfoReplace.veh_Csys = zcInfooldInstance.veh_Csys //车身颜色
        zcInfoReplace.veh_Dpxh = zcInfooldInstance.veh_Dpxh //底盘型号
        zcInfoReplace.veh_Dpid = zcInfooldInstance.veh_Dpid //底盘ID
        zcInfoReplace.veh_Clsbdh = zcInfooldInstance.veh_Clsbdh//车辆识别代号
        zcInfoReplace.veh_Cjh = zcInfooldInstance.veh_Cjh //车架号
        zcInfoReplace.veh_Fdjh = zcInfooldInstance.veh_Fdjh//发动机号

        zcInfoReplace.veh_Fdjxh = zcInfooldInstance.veh_Fdjxh//发动机型号
        zcInfoReplace.veh_Rlzl = zcInfooldInstance.veh_Rlzl //燃料种类
        zcInfoReplace.veh_Pl = zcInfooldInstance.veh_Pl   //排量
        zcInfoReplace.veh_Gl = zcInfooldInstance.veh_Gl   //功率
        zcInfoReplace.veh_zdjgl = zcInfooldInstance.veh_zdjgl//最大净功率
        zcInfoReplace.veh_Zxxs = zcInfooldInstance.veh_Zxxs //转向形式
        zcInfoReplace.veh_Qlj = zcInfooldInstance.veh_Qlj  //前轮距
        zcInfoReplace.veh_Hlj = zcInfooldInstance.veh_Hlj  //后轮距
        zcInfoReplace.veh_Lts = zcInfooldInstance.veh_Lts  //轮胎数
        zcInfoReplace.veh_Ltgg = zcInfooldInstance.veh_Ltgg //轮胎规格
        zcInfoReplace.veh_Gbthps = zcInfooldInstance.veh_Gbthps   //钢板弹簧片数
        zcInfoReplace.veh_Zj = zcInfooldInstance.veh_Zj  //轴距
        zcInfoReplace.veh_Zh = zcInfooldInstance.veh_Zh   //轴荷
        zcInfoReplace.veh_Zs = zcInfooldInstance.veh_Zs   //轴数
        zcInfoReplace.veh_Wkc = zcInfooldInstance.veh_Wkc   //外廓长
        zcInfoReplace.veh_Wkk = zcInfooldInstance.veh_Wkk   //外廓宽
        zcInfoReplace.veh_Wkg = zcInfooldInstance.veh_Wkg   //外廓高
        zcInfoReplace.veh_Hxnbc = zcInfooldInstance.veh_Hxnbc //货箱内部长
        zcInfoReplace.veh_Hxnbk = zcInfooldInstance.veh_Hxnbk //货箱内部宽
        zcInfoReplace.veh_Hxnbg = zcInfooldInstance.veh_Hxnbg //货箱内部高
        zcInfoReplace.veh_Zzl = zcInfooldInstance.veh_Zzl   //总质量
        zcInfoReplace.veh_Edzzl = zcInfooldInstance.veh_Edzzl //额定载质量
        zcInfoReplace.veh_Zbzl = zcInfooldInstance.veh_Zbzl  //整备质量
        zcInfoReplace.veh_Zzllyxs = zcInfooldInstance.veh_Zzllyxs   //载质量利用系数
        zcInfoReplace.veh_Zqyzzl = zcInfooldInstance.veh_Zqyzzl   //准牵引总质量
        zcInfoReplace.veh_Edzk = zcInfooldInstance.veh_Edzk //额定载客
        zcInfoReplace.veh_Bgcazzdyxzzl = zcInfooldInstance.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
        zcInfoReplace.veh_Jsszcrs = zcInfooldInstance.veh_Jsszcrs  //驾驶室准乘人数
        zcInfoReplace.veh_Qzdfs = zcInfooldInstance.veh_Qzdfs   //前制动方式
        zcInfoReplace.veh_Hzdfs = zcInfooldInstance.veh_Hzdfs//后制动方式
        zcInfoReplace.veh_Qzdczfs = zcInfooldInstance.veh_Qzdczfs //前制动操作方式
        zcInfoReplace.veh_Hzdczfs = zcInfooldInstance.veh_Hzdczfs//后制动操作方式
        zcInfoReplace.veh_Cpggh = zcInfooldInstance.veh_Cpggh//产品公告号
        zcInfoReplace.veh_Ggsxrq = zcInfooldInstance.veh_Ggsxrq   //公告生效日期

        zcInfoReplace.veh_Dywym = zcInfooldInstance.veh_Dywym   //打印唯一码
        zcInfoReplace.veh_Zgcs = zcInfooldInstance.veh_Zgcs  //最高车速
        zcInfoReplace.veh_Clzzrq = oldClzzrq   //车辆制造日期
        zcInfoReplace.veh_Bz = zcInfooldInstance.veh_Bz   //备注
        zcInfoReplace.veh_Qybz = zcInfooldInstance.veh_Qybz //企业标准
        zcInfoReplace.veh_Cpscdz = zcInfooldInstance.veh_Cpscdz   //产品生产地址
        zcInfoReplace.veh_Qyqtxx = zcInfooldInstance. veh_Qyqtxx  //企业其它信息
        zcInfoReplace.veh_Pfbz = zcInfooldInstance.veh_Pfbz //排放标准
        zcInfoReplace.veh_Clztxx = zcInfooldInstance.veh_Clztxx   //车辆状态信息
        zcInfoReplace.veh_Jss = zcInfooldInstance.veh_Jss  //驾驶室
        zcInfoReplace.veh_Jsslx = zcInfooldInstance.veh_Jsslx//驾驶室类型
        zcInfoReplace.veh_Zchgzbh_0 = zcInfooldInstance.veh_Zchgzbh_0  //完整合格证编号
        zcInfoReplace.used  = zcInfooldInstance. used
        zcInfoReplace.used2 = zcInfooldInstance.used2
        zcInfoReplace.upload = zcInfooldInstance.upload //
        zcInfoReplace.vercode = zcInfooldInstance.vercode //二维码
        zcInfoReplace.veh_Yh = zcInfooldInstance.veh_Yh  //油耗
        zcInfoReplace.veh_VinFourBit = zcInfooldInstance.veh_VinFourBit //vin第四位
        zcInfoReplace.veh_Ggpc = zcInfooldInstance.veh_Ggpc//公告批次
        zcInfoReplace.veh_createTime = zcInfooldInstance.createTime //创建时间
        zcInfoReplace.veh_createrId = zcInfooldInstance.createrId //创建人id
        zcInfoReplace.veh_pzxlh = zcInfooldInstance.veh_pzxlh  //配置序列号
        zcInfoReplace.upload_Path = zcInfooldInstance.upload_Path  //合格证上传相对路径
        zcInfoReplace.veh_clzzqyxx = zcInfooldInstance.veh_clzzqyxx//车辆制造企业信息

        //更换后的合格证信息
        zcInfoReplace.veh_Type_R = zcInfooldInstance.veh_Type//汽车和农用车标示
        zcInfoReplace.veh_Xnhgzbh_R = params.web_virtualcode //虚拟合格证编号
        zcInfoReplace.veh_Zchgzbh_R = zcInfooldInstance.veh_Zchgzbh  //整车合格证编号
        zcInfoReplace.veh_Dphgzbh_R = zcInfooldInstance.veh_Dphgzbh  //底盘合格证编号
        zcInfoReplace.veh_Clzzqymc_R = zcInfooldInstance.veh_Clzzqymc //车辆制造企业名称
        zcInfoReplace.veh_Qyid_R = zcInfooldInstance.veh_Qyid //企业ID
        zcInfoReplace.veh_Clfl_R = zcInfooldInstance.veh_Clfl //车辆分类
        zcInfoReplace.veh_Clmc_R = zcInfooldInstance.veh_Clmc //车辆名称
        zcInfoReplace.veh_Clpp_R = zcInfooldInstance.veh_Clpp //车辆品牌
        zcInfoReplace.veh_Clxh_R = zcInfooldInstance.veh_Clxh//车辆型号
        zcInfoReplace.veh_Csys_R = zcInfooldInstance.veh_Csys //车身颜色
        zcInfoReplace.veh_Dpxh_R = zcInfooldInstance.veh_Dpxh //底盘型号
        zcInfoReplace.veh_Dpid_R = zcInfooldInstance.veh_Dpid //底盘ID
        zcInfoReplace.veh_Clsbdh_R = zcInfooldInstance.veh_Clsbdh//车辆识别代号
        zcInfoReplace.veh_Cjh_R = zcInfooldInstance.veh_Cjh //车架号
        zcInfoReplace.veh_Fdjh_R = zcInfooldInstance.veh_Fdjh//发动机号

        zcInfoReplace.veh_Fdjxh_R = zcInfooldInstance.veh_Fdjxh//发动机型号
        zcInfoReplace.veh_Rlzl_R = zcInfooldInstance.veh_Rlzl //燃料种类
        zcInfoReplace.veh_Pl_R = zcInfooldInstance.veh_Pl   //排量
        zcInfoReplace.veh_Gl_R = zcInfooldInstance.veh_Gl   //功率
        zcInfoReplace.veh_zdjgl_R = zcInfooldInstance.veh_zdjgl//最大净功率
        zcInfoReplace.veh_Zxxs_R = zcInfooldInstance.veh_Zxxs //转向形式
        zcInfoReplace.veh_Qlj_R = zcInfooldInstance.veh_Qlj  //前轮距
        zcInfoReplace.veh_Hlj_R = zcInfooldInstance.veh_Hlj  //后轮距
        zcInfoReplace.veh_Lts_R = zcInfooldInstance.veh_Lts  //轮胎数
        zcInfoReplace.veh_Ltgg_R = zcInfooldInstance.veh_Ltgg //轮胎规格
        zcInfoReplace.veh_Gbthps_R = zcInfooldInstance.veh_Gbthps   //钢板弹簧片数
        zcInfoReplace.veh_Zj_R  = zcInfooldInstance.veh_Zj  //轴距
        zcInfoReplace.veh_Zh_R  = zcInfooldInstance.veh_Zh  //轴荷
        zcInfoReplace. veh_Zs_R = zcInfooldInstance.veh_Zs   //轴数
        zcInfoReplace. veh_Wkc_R = zcInfooldInstance.veh_Wkc   //外廓长
        zcInfoReplace. veh_Wkk_R = zcInfooldInstance.veh_Wkk   //外廓宽
        zcInfoReplace. veh_Wkg_R = zcInfooldInstance.veh_Wkg   //外廓高
        zcInfoReplace. veh_Hxnbc_R = zcInfooldInstance.veh_Hxnbc //货箱内部长
        zcInfoReplace. veh_Hxnbk_R = zcInfooldInstance.veh_Hxnbk //货箱内部宽
        zcInfoReplace. veh_Hxnbg_R = zcInfooldInstance.veh_Hxnbg //货箱内部高
        zcInfoReplace. veh_Zzl_R  = zcInfooldInstance.veh_Zzl  //总质量
        zcInfoReplace. veh_Edzzl_R = zcInfooldInstance.veh_Edzzl //额定载质量
        zcInfoReplace. veh_Zbzl_R = zcInfooldInstance.veh_Zbzl  //整备质量
        zcInfoReplace. veh_Zzllyxs_R = zcInfooldInstance.veh_Zzllyxs   //载质量利用系数
        zcInfoReplace.veh_Zqyzzl_R = zcInfooldInstance.veh_Zqyzzl   //准牵引总质量
        zcInfoReplace.veh_Edzk_R = zcInfooldInstance.veh_Edzk //额定载客
        zcInfoReplace.veh_Bgcazzdyxzzl_R = zcInfooldInstance.veh_Bgcazzdyxzzl //半挂车按座
        zcInfoReplace.veh_Jsszcrs_R = zcInfooldInstance.veh_Jsszcrs  //驾驶室准乘人数
        zcInfoReplace.veh_Qzdfs_R = zcInfooldInstance.veh_Qzdfs   //前制动方式
        zcInfoReplace.veh_Hzdfs_R = zcInfooldInstance.veh_Hzdfs//后制动方式
        zcInfoReplace.veh_Qzdczfs_R = zcInfooldInstance.veh_Qzdczfs //前制动操作方式
        zcInfoReplace.veh_Hzdczfs_R = zcInfooldInstance.veh_Hzdczfs//后制动操作方式
        zcInfoReplace.veh_Cpggh_R = zcInfooldInstance.veh_Cpggh//产品公告号
        zcInfoReplace.veh_Ggsxrq_R = zcInfooldInstance.veh_Ggsxrq   //公告生效日期
        zcInfoReplace.veh_Dywym_R = params.veh_Dywym  //打印唯一码
        zcInfoReplace. veh_Zgcs_R = zcInfooldInstance.veh_Zgcs  //最高车速
        zcInfoReplace.veh_Bz_R = zcInfooldInstance.veh_Bz   //备注
        zcInfoReplace.veh_Qybz_R = zcInfooldInstance.veh_Qybz //企业标准
        zcInfoReplace.veh_Cpscdz_R = zcInfooldInstance.veh_Cpscdz   //产品生产地址
        zcInfoReplace.veh_Qyqtxx_R = zcInfooldInstance.veh_Qyqtxx   //企业其它信息
        zcInfoReplace.veh_Pfbz_R = zcInfooldInstance.veh_Pfbz //排放标准
        zcInfoReplace.veh_Clztxx_R = zcInfooldInstance.veh_Clztxx   //车辆状态信息
        zcInfoReplace.veh_Jss_R = zcInfooldInstance.veh_Jss  //驾驶室
        zcInfoReplace.veh_Jsslx_R = zcInfooldInstance.veh_Jsslx//驾驶室类型
        zcInfoReplace.veh_Zchgzbh_0_R =params.veh_Zchgzbh_0  //完整合格证编号
        zcInfoReplace.used_r = zcInfooldInstance.used
        zcInfoReplace.used2_r = zcInfooldInstance.used2
        zcInfoReplace.upload_r = zcInfooldInstance.upload //
        zcInfoReplace.vercode_r =  params.vercode_r //二维码
        zcInfoReplace.veh_Yh_R = zcInfooldInstance.veh_Yh  //油耗
        zcInfoReplace.veh_VinFourBit_R = zcInfooldInstance.veh_VinFourBit //vin第四位
        zcInfoReplace.veh_Ggpc_R = zcInfooldInstance.veh_Ggpc   //公告批次
        zcInfoReplace.veh_pzxlh_R = zcInfooldInstance.veh_pzxlh   //配置序列号
        zcInfoReplace.veh_clzzqyxx_R = zcInfooldInstance.veh_clzzqyxx //车辆制造企业信息

        zcInfoReplace.veh_usertype = 0   //合格证修改记录作者类型  0：经销商修改、更换 1：公告资源部修改(只是针对已经上传到国家的合格证修改 才会有修改记录)
        zcInfoReplace.veh_isupload =0  //原合格证信息是否已经上传到国家系统  0：未上传到国家 1：已上传到国家
        zcInfoReplace.veh_managestatus=0  //针对已经上传到国家的记录进行修改  由公告资源部确认处理情况  0：待处理 1：处理完毕
        zcInfoReplace.salesmanname="" //业务员姓名
        zcInfoReplace.salesmantel="" //业务员电话
        zcInfoReplace.authTime=""   //审核时间 （处理人）
        zcInfoReplace.authId="" //审核人   （处理时间）
        zcInfoReplace.remark=""  //申请备注
        zcInfoReplace.auth_Remark="" //审核备注


        zcInfoReplace.createTime=DateUtil.getCurrentTime() //申请时间
        zcInfoReplace.createrId=loginUser.id //申请人id
        zcInfoReplace.veh_Fzrq_R =  veh_Fzrq   ///变更后日期
        zcInfoReplace.change_Field = "veh_Fzrq"  ///变更字段 用来标识是否为 日期变更
        zcInfoReplace.veh_coc_status = 1    ///变更审核状态 日期变更默认为 审核通过
        zcInfoReplace.createName = loginUser.userDetail.realName   ///申请人姓名
        zcInfoReplace.veh_Zzbh_R = veh_Zzbh_re   ///纸张编号
        zcInfoReplace.veh_Clzzrq_R = veh_Clzzrq   //车辆制造日期
        /////PDF 下载路径
        zcInfoReplace.upload_Path_R = params.upload_Path     ///变更后新的下载PDF路径
        def result1 = 0;
        if(zcInfoReplace.save(flush: true)){
            result1 = 1;
        }

        if (result1>0){
            zcInfooldInstance.vercode=params.vercode_r
            zcInfooldInstance.veh_Zchgzbh_0=params.veh_Zchgzbh_0
            zcInfooldInstance.veh_Dywym=params.veh_Dywym
            zcInfooldInstance.upload_Path= params.upload_Path
            zcInfooldInstance.web_virtualcode=params.web_virtualcode

            if (!zcInfooldInstance.save(flush: true)) {
                result.flag='0'
                result.msg="数据更新失败！"
                return render (result as JSON)
            }else{
                result.flag='1'
                result.msg="数据更新成功！"
                return render (result as JSON)
            }
        }
    }

    /**
     *@Descriptions 车间 >> 合格证信息查询（主跳转）
     *@Auther zouq
     *@createTime  2013-08-05
     */
    def zcinfoBySearchIndex()
    {
        render(view:'/cn/com/wz/vehcert/zcinfo/search')
    }
    /**
     *@Description 合格证申请替换
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-11
     */
    def zcinfoRound(){
        def aa=params
        render (view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [turnOff:params.turnOff])
    }
    /**
     *@Description A4合格证更换正式合格证
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-11
     */
    def zcinfoRoundForPdf(){
        def aa=params
        def replaceType=params?.replaceType
        def turnOff=params?.turnOff
        def formal='1';
        render (view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [turnOff:turnOff,formal:formal,replaceType:replaceType])
    }

    /**
     * @Description 合格证二次更换
     * @CreateTime 2014-11-25 zhuwei
     */
    def replaceTwoTime(){
        def aaa=params
        def replaceType=params?.replaceType
        def turnOff=params?.turnOff
        render (view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime',model: [replaceType:replaceType,turnOff:turnOff])
    }
    /**
     *@Descriptions 经销商PDF变正式合格证更换申请，根据完整合格证编号关联出原合格证
     *@Auther QJ
     *@createTime  2018-03-08
     *@Update
     */
    def zcinfoByZchgzbh(){
        def  veh_Zchgzbh_0  = params.veh_Zchgzbh_0;  ///底盘号信息
        def  replaceType=params?.replaceType
        def turnOff=params?.turnOff
        def formal = params?.formal
        if (!veh_Zchgzbh_0){

            if(replaceType=='1'){  //二次更换页面
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [replaceType:replaceType,turnOff:turnOff,formal:formal])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime' ,model: [replaceType:replaceType,turnOff:turnOff])
                }
            }else{
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [replaceType:replaceType,turnOff:turnOff,formal:formal])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [turnOff:turnOff])
                }
            }
            return
        }
        def cel={
            if(params.veh_Zchgzbh_0){
                eq('veh_Zchgzbh_0',params.veh_Zchgzbh_0)
            }
        }
        def  zciInfo =  ZCInfo.createCriteria().list(cel);
        def zcin=new ZCInfo()
        def flag=true
        if(zciInfo) {
            zciInfo.each {z->
                if (z.veh_Clztxx=='QX'||z.veh_Clztxx=='DP'){      //改为有整车按照整车合格证关联车辆信息，没有整车按照底盘合格证关联车辆信息
                    zcin=z
                    flag = true
                }
            }
        } else{
            flag = false
        }
        if (flag){
            if(replaceType=='1'){  //二次更换页面
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',turnOff:turnOff,formal:formal,replaceType:replaceType])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',replaceType:replaceType,turnOff:turnOff ])
                }
            }else{
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',turnOff:turnOff,formal:formal,replaceType:replaceType])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',turnOff:turnOff])
                }
            }
        }else{
            if(replaceType=='1'){  //二次更换页面
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [replaceType:replaceType,turnOff:turnOff,formal:formal])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime' ,model: [replaceType:replaceType,turnOff:turnOff])
                }
            }else{
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [replaceType:replaceType,turnOff:turnOff,formal:formal])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [turnOff:turnOff])
                }
            }
        }
    }
    /**
     *@Descriptions 经销商 >>合格证更换申请h
     *@Auther liuly
     *@createTime  2013-08-11
     *@Update  与二次更换公用一个关联方法，通过 replaceType=1 标识来区分
     * UpdateTime 2014-12-01
     * UpdateTime 2017-07-18 by zhuwei   更换合格证时，不在限制只按照整车合格证关联车辆信息，
     * UpdateTime 2017-07-18 by zhuwei   改为又有整车按照整车合格证关联车辆信息，没有整车按照底盘合格证关联车辆信息
     */
    def zcinfoRondByDp(){
        def  veh_Clsbdh  = params.veh_Clsbdh;  ///底盘号信息
        def  replaceType=params?.replaceType
        def turnOff=params?.turnOff
        def formal = params?.formal
        if (!veh_Clsbdh){

            if(replaceType=='1'){  //二次更换页面
                render(view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime' ,model: [replaceType:replaceType,turnOff:turnOff])
            }else{
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [turnOff:turnOff,formal:formal])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [turnOff:turnOff])
                }
            }
            return
        }
        def cel={
            if(params.veh_Clsbdh){
                eq('veh_Clsbdh',params.veh_Clsbdh)
                //按照合格证排序,目的是循环最后一次有整车和底盘合格证得时候，循环到整车（实现有整车和底盘时按照整车关联车辆信息，没有整车时按照底盘关联车辆信息）
                order ("veh_Zchgzbh", "desc")
            }
        }
        def  zciInfo =  ZCInfo.createCriteria().list(cel);
        def zcin=new ZCInfo()
        def flag=true
        if(zciInfo) {
            zciInfo.each {z->
                if (z.veh_Clztxx=='QX'||z.veh_Clztxx=='DP'){      //改为有整车按照整车合格证关联车辆信息，没有整车按照底盘合格证关联车辆信息
                    zcin=z
                    flag = true
                }
            }
        } else{
            flag = false
        }
        if (flag){
            if(replaceType=='1'){  //二次更换页面
                render(view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',replaceType:replaceType,turnOff:turnOff ])
            }else{
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',turnOff:turnOff,formal:formal])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',turnOff:turnOff])
                }
            }

        }else{
            if(replaceType=='1'){  //二次更换页面
                render(view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime',model: [replaceType:replaceType,turnOff:turnOff])
            }else{
                if(formal=='1'){
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRoundForPdf',model: [turnOff:turnOff,formal:formal])
                }else{
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [turnOff:turnOff])
                }
            }
        }

    }
    /**
     *@Descriptions 经销商 >>合格证更换申请手动填写
     *@Auther liuly
     *@createTime  2013-08-11
     */
    def zcinfoRondWrite()
    {
        def aaa=params
        render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoWrite',model: [replaceType:params?.replaceType,turnOff:params?.turnOff])
    }
    /**
     *@Descriptions 经销商 >>手动填写合格证，进行保存
     *@Auther liuly
     *@createTime  2013-08-12
     * @Update 2013-10-20 手动添加的合格证信息全部为整车合格证信息，添加车辆状态的值
     * @Update 2013-10-28 添加手动填写合格证时，判断填写的合格证是否存在
     */
    def saveWrite(){
        def  aaaa=params
        def carstorageId=params.carstorageId
        def zciInfoC=CarStorage.get(carstorageId)
        //判断填写的合格证是否存在
        def zciInfoModel=ZCInfo.findByVeh_Zchgzbh(params.veh_Zchgzbh)
        def zcinfo=ZCInfo.findByVeh_Clsbdh(params.veh_Clsbdh)
        if (zciInfoModel || zcinfo) {  //存在
            def result=[:]
            zciInfoC?.properties.each{
                result.put("${it.key}","${it.value?it.value:''}")
            }
            result.id=params.carstorageId
            result.veh_Zchgzbh=params.veh_Zchgzbh
            result.veh_Clsbdh=params.veh_Clsbdh
            result.veh_Zzbh=params.veh_Zzbh
            result.veh_Fzrq=params.veh_Fzrq
            result.veh_Cjh= params.veh_Clsbdh
            result.veh_Csys=params.veh_Csys
            result.veh_Fdjxh=params.veh_Fdjxh
            result.veh_Fdjh=params.veh_Fdjh
            result.veh_Clzzrq=params.veh_Clzzrq
            //添加二次更换标识replaceType
            render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoWrite',model:[returnMessage:"合格证信息已存在，请使用VIN进行关联更换",data:result as JSON,replaceType:params?.replaceType,turnOff:params?.turnOff])
        }else{ //不存在

            zciInfoModel=new ZCInfo()
            zciInfoModel.veh_Type=zciInfoC.carStorageType
            zciInfoModel.veh_VinFourBit=zciInfoC.veh_VinFourBit
            zciInfoModel.veh_Clsbdh=params.veh_Clsbdh
            zciInfoModel.veh_Zzbh=params.veh_Zzbh
            zciInfoModel.veh_Zchgzbh=params.veh_Zchgzbh
            zciInfoModel.veh_Fzrq=params.veh_Fzrq
            zciInfoModel.veh_Clzzqymc=zciInfoC.veh_Clzzqymc
            zciInfoModel.veh_Clpp=zciInfoC.veh_Clpp
            zciInfoModel.veh_Clmc=zciInfoC.veh_Clmc
            zciInfoModel.veh_Clxh=zciInfoC.veh_Clxh
            zciInfoModel.veh_Cjh= params.veh_Clsbdh
            zciInfoModel.veh_Csys=params.veh_Csys
            zciInfoModel.veh_Dpxh=zciInfoC.veh_Dpxh

            /* zciInfoModel.veh_Dphgzbh=zciInfoC.veh_Dphgzbh*/
            zciInfoModel.veh_Fdjxh=params.veh_Fdjxh
            zciInfoModel.veh_Fdjh=params.veh_Fdjh
            zciInfoModel.veh_Rlzl=zciInfoC.veh_Rlzl
            zciInfoModel.veh_Pl=zciInfoC.veh_Pl
            zciInfoModel.veh_Gl=zciInfoC.veh_Gl
            zciInfoModel.veh_Lts=zciInfoC.veh_Lts
            zciInfoModel.veh_Pfbz=zciInfoC.veh_Pfbz

            zciInfoModel.veh_Yh=zciInfoC.veh_Yh
            zciInfoModel.veh_Wkc=zciInfoC.veh_Wkc
            zciInfoModel.veh_Wkk=zciInfoC.veh_Wkk
            zciInfoModel.veh_Wkg=zciInfoC.veh_Wkg
            zciInfoModel.veh_Hxnbc=zciInfoC.veh_Hxnbc
            zciInfoModel.veh_Hxnbk=zciInfoC.veh_Hxnbk
            zciInfoModel.veh_Hxnbg=zciInfoC.veh_Hxnbg
            zciInfoModel.veh_Ltgg=zciInfoC.veh_Ltgg
            zciInfoModel.veh_Gbthps=zciInfoC.veh_Gbthps

            zciInfoModel.veh_Qlj=zciInfoC.veh_Qlj
            zciInfoModel.veh_Hlj=zciInfoC.veh_Hlj
            zciInfoModel.veh_Zj=zciInfoC.veh_Zj
            zciInfoModel.veh_Zh=zciInfoC.veh_Zh
            zciInfoModel.veh_Zs=zciInfoC.veh_Zs
            zciInfoModel.veh_Zxxs=zciInfoC.veh_Zxxs
            zciInfoModel.veh_Zzl=zciInfoC.veh_Zzl
            zciInfoModel.veh_Zbzl=zciInfoC.veh_Zbzl
            zciInfoModel.veh_Edzzl=zciInfoC.veh_Edzzl

            zciInfoModel.veh_Zzllyxs=zciInfoC.veh_Zzllyxs
            zciInfoModel.veh_Zqyzzl=zciInfoC.veh_Zqyzzl
            zciInfoModel.veh_Bgcazzdyxzzl=zciInfoC.veh_Bgcazzdyxzzl
            zciInfoModel.veh_Jsszcrs=zciInfoC.veh_Jsszcrs
            zciInfoModel.veh_Edzk=zciInfoC.veh_Edzk
            zciInfoModel.veh_Zgcs=zciInfoC.veh_Zgcs

            zciInfoModel.veh_pzxlh=zciInfoC.veh_pzxlh

            zciInfoModel.veh_Clzzrq=params.veh_Clzzrq //车辆制造日期
            User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
            zciInfoModel.createrId= loginUser.userName
            zciInfoModel.createTime=DateUtil.getCurrentTime()
            /*zciInfoModel.veh_clzzqyxx=zciInfoC.veh_clzzqyxx*/
            zciInfoModel.veh_Bz=zciInfoC.veh_Bz
            zciInfoModel.veh_Clztxx="QX"
            zciInfoModel.web_status='1'
            zciInfoModel.web_isprint='1'


            if (zciInfoModel.save(flush: true)){ //保存成功
                if(params?.replaceType){ //是二次更换
                    render (view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime',model: [zciInfoModel:zciInfoModel,zid:zciInfoModel.id,statusWrite:'write',replaceType:params?.replaceType,turnOff:params?.turnOff])
                }else{ //正常更换
                    render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoRound',model: [zciInfoModel:zciInfoModel,zid:zciInfoModel.id,statusWrite:'write',turnOff:params?.turnOff])
                }

            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/zcinfoWrite',model: [zciInfoC:zciInfoC,replaceType:params?.replaceType,turnOff:params?.turnOff])
            }
        }

    }
    /**
     *@Description 综合查询的合格证信息查询页面(单条查询）
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-13 liuly
     */
    def showSingle(){
        def  zciInfoModel=ZCInfo.get(params.id)
        render (view:'/cn/com/wz/vehcert/zcinfo/singleZin',model:[zciInfoModel:zciInfoModel] );
    }

    def changeStatus(){
        def flag='success'
        def msg=""
        try{
            def zcinfo=ZCInfo.get(params.id)
            if (zcinfo){
                zcinfo.web_status='3'
                def temp="cancel:${zcinfo.veh_Zchgzbh}:${zcinfo.veh_Clsbdh}:${DateUtil.getCurrentTime()}"
                zcinfo.veh_Zchgzbh=temp
                zcinfo.veh_Clsbdh=temp
                zcinfo.updateTime=DateUtil.getCurrentTime()
                zcinfo.updaterId=getCurrentUser()?.userName

                if (zcinfo.save(flush: true)){
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
     *@Description 综合查询的合格证信息查询页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-13 liuly
     */
    def zcinfoSearchAll() {
        render (view:'/cn/com/wz/vehcert/zcinfo/zcinfoSearch' );
    }
    /**
     *@Description 综合查询的合格证信息查询
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-13 liuly
     */
    def jsonListSearch(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def result=zcInfoService.selectZcinfoByParams(params)
        render result as JSON
    }
    /**
     *@Description 综合查询的合格证信息查询的导出excel
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-13 liuly
     * @update zhuwei 综合查询的导出Excle修改了导出的字段
     */
    def export(){
        def content=[]
        try{
            if(params?.format && params.format != "html"){
                def encodedFilename = URLEncoder.encode(("合格证信息"), "UTF-8")
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
                //List fields = [ "veh_Zchgzbh","veh_Dphgzbh","veh_Fzrq","veh_Clzzqymc","veh_Qyid","veh_Clfl","veh_Clmc","veh_Clpp","veh_Clxh","veh_Csys","veh_Dpxh","veh_Dpid","veh_Clsbdh","veh_Cjh","veh_Fdjh","veh_Fdjxh","veh_Rlzl","veh_Pl","veh_Gl","veh_zdjgl","veh_Zxxs","veh_Qlj","veh_Hlj","veh_Lts","veh_Ltgg","veh_Gbthps","veh_Zj","veh_Zh","veh_Zs","veh_Wkc","veh_Wkk","veh_Wkg","veh_Hxnbc","veh_Hxnbk","veh_Hxnbg","veh_Zzl","veh_Edzzl","veh_Zbzl","veh_Zzllyxs","veh_Zqyzzl","veh_Edzk","veh_Bgcazzdyxzzl","veh_Jsszcrs","veh_Qzdfs","veh_Hzdfs","veh_Qzdczfs","veh_Hzdczfs","veh_Cpggh","veh_Ggsxrq","veh_Zzbh","veh_Dywym","veh_Zgcs","veh_Clzzrq","veh_Bz","veh_Qybz","veh_Cpscdz","veh_Qyqtxx","veh_Pfbz","veh_Clztxx","veh_Jss","veh_Jsslx","veh_VinFourBit","updateTime", "updaterId","uploader_Id","uploader_Time"]
//            Map labels =  [ "veh_Zchgzbh":"整车合格证编号","veh_Dphgzbh":"底盘合格证编号","veh_Fzrq":"发证日期","veh_Clzzqymc":"车辆制造企业名称","veh_Qyid":"企业ID","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称","veh_Clpp":"车辆品牌","veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Clsbdh":"车辆识别代号","veh_Cjh":"车架号", "veh_Fdjh":"发动机号","veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类","veh_Pl":"排量","veh_Gl":"功率","veh_zdjgl":"最大净功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格","veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽","veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车鞍座最大允许总质量","veh_Jsszcrs":"驾驶室准乘人数","veh_Qzdfs":"前制动方式","veh_Hzdfs":"后制动方式","veh_Qzdczfs":"前制动操作方式","veh_Hzdczfs":"后制动操作方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期","veh_Zzbh":"纸张编号","veh_Dywym":"打印唯一码","veh_Zgcs":"最高车速","veh_Clzzrq":"车辆制造日期","veh_Bz":"备注","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址", "veh_Qyqtxx":"企业其它信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息","veh_Jss":"驾驶室","veh_Jsslx ":"驾驶室类型", "veh_VinFourBit":"vin第四位","updateTime":"最后更新时间", "updaterId":"最后更新人","uploader_Id":"上传人","uploader_Time":"上传时间"]
                Map labels = ["idReal":"序号", "veh_Clxh":"车辆型号","veh_Clsbdh":"车辆识别代号","veh_Fdjh":"发动机号","veh_Fdjxh":"发动机型号","veh_Type":"汽车和农用车标示0：汽车1：农用车","veh_Zchgzbh":"整车合格证编号","veh_Dphgzbh":"底盘合格证编号","veh_Fzrq":"发证日期","veh_Clzzqymc":"车辆制造企业名称","veh_Qyid":"企业ID","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称"
                              ,"veh_Clpp":"车辆品牌","veh_Fdjh":"发动机号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Cjh":"车架号"
                              ,"veh_Rlzl":"燃料种类","veh_Pl":"排量","veh_Gl":"功率","veh_zdjgl":"最大净功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格"
                              ,"veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽"
                              ,"veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车鞍座最大允许总质量","veh_Jsszcrs":"驾驶室准乘人数"
                              ,"veh_Qzdfs":"前制动方式","veh_Hzdfs":"后制动方式","veh_Qzdczfs":"前制动操作方式","veh_Hzdczfs":"后制动操作方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期","veh_Zzbh":"纸张编号"
                              ,"veh_Dywym":"打印唯一码","veh_Zgcs":"最高车速","veh_Clzzrq":"车辆制造日期","veh_Bz":"备注","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其它信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息"
                              ,"veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_Zchgzbh_0":"完整合格证编号","used":"used","used2":"used2","upload":"上传路径","veh_Yh":"油耗","veh_VinFourBit":"vin第四位"
                              ,"veh_Ggpc":"公告批次","veh_pzxlh":"配置序列号","veh_clzzqyxx":"车辆制造企业信息","veh_workshopno":"车间编号","createTime":"创建时间","createrId":"创建人id","updateTime":"最后更新时间"
                              ,"updaterId":"最后更新人","uploader_Id":"上传人","uploader_Time":"上传时间","upload_Failddm":"上传失败返回代码","upload_Path":"合格证上传相对路径","web_status":"合格证状态 是否上传0:未上传1：上传成功2：上传失败"
                              ,"web_isprint":"合格证是否打印","web_virtualcode":"虚拟合格证编号","vercode":"校验信息"]
                def out=response.outputStream
                params.offset=0
                def map=zcInfoService.exportZcinfoByParams(params)
                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                def m=[]
                m.add(labels)
                content.add(map.rows)
                map.clear()
                excelOp.preWriteExcel(out,null,m,["合格证信息"],content)
                session.setAttribute('compFlag',"success")
                out.flush()
                out.close()
            }
        }catch(Exception e){
            e.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
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
     * 经销商 >> 合格证上传申请 跳转主页面
     * @author  zouQ
     * @time 2013年8月17日
     */
    def zcinfoUpload() {
        render(view:'/cn/com/wz/vehcert/zcinfo/dealer/zcinfoApplyUpload')
    }


    /**
     * json查询 合格证上传申请
     * @author  zouQ
     * @time 2013年8月17日
     * @Update 2013-09-02 huxx 修改数据从wzh_zcinfo_temp表中获取
     * @Update 2014-02-13 huxx 添加读取条件needUpload
     */
    def zcinfoUploadJson(){
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort='createTime'
        params.order="desc"
        def sta = params.firstTime+" 00:00:00"
        def end = params.secondTime+" 23:59:59"

        def template=new JdbcTemplate(dataSource)
        def countSql= """
                    select count(t.id) from wzh_zcinfo_temp t left join wzh_zcinfo zc on t.veh_zchgzbh=zc.veh_zchgzbh
                        where zc.web_status='0' and user_down='${loginUser.id}' and t.insert_time =(select max(insert_time) from wzh_zcinfo_temp t1 where t1.veh_zchgzbh =t.veh_zchgzbh)
            """
        if(params.firstTime){
            countSql+="and insert_Time>='${sta}'"
        }
        if (params.secondTime){
            countSql+= "and insert_Time<='${end}' "
        }
        if ('0'.equals(params.needUpload)){
            countSql+=" and (need_upload='0' or need_upload is null)"
        }


        int totalCount=template.queryForInt(countSql)

        def  sql="""
                select need_upload,id  ,insert_time ,veh_zchgzbh ,veh_fzrq ,veh_Clfl,veh_Clmc,veh_Clxh,veh_Csys,veh_dpXH,veh_dpid,veh_Clsbdh,veh_Cjh,veh_fdjh from
                  (select need_upload,id  ,insert_time ,veh_zchgzbh ,veh_fzrq ,veh_Clfl,veh_Clmc,veh_Clxh,veh_Csys,veh_dpXH,veh_dpid,veh_Clsbdh,veh_Cjh,veh_fdjh,rownum as rownum1 from
                        (select t.*,zc.need_upload from wzh_zcinfo_temp t  left join wzh_zcinfo zc on t.veh_zchgzbh=zc.veh_zchgzbh  where zc.web_status='0' and user_down='${loginUser.id}' and t.insert_time =(select max(insert_time) from wzh_zcinfo_temp t1 where t1.veh_zchgzbh =t.veh_zchgzbh))
                  where rownum<=${params.offset+params.max}  )
                where rownum1 >${params.offset}
           """
        if(params.firstTime){
            sql+="and insert_Time>='${sta}'"
        }
        if (params.secondTime){
            sql+= "and insert_Time<='${end}' "
        }
        if ('0'.equals(params.needUpload)){
            sql+=" and (need_upload='0' or need_upload is null)"
        }

        List lst = template.queryForList(sql)
        params.offset= params.offset+params.max
        def result = [rows: lst, total:totalCount]
        render result as JSON
    }


    /**
     *@Descriptions 经销商 >> 合格证更换查询  >> 查看 下载合格证 函数处理
     *@Auther zouq
     *@createTime
     * @update 2013-08-27 huxx 添加了更换底盘和整车时，如果全要就下载两个文件
     * @update 2013-10-20 hux 添加了合格证PDF文件是否存在的判定
     */
    def download_ZCI_by(){
        def result=[:]   //保存合格证的PDF路径
        def message="success"
        def hgz = params.hgz;    ////要下载合格证编号
        def veh_Need_Cer=params.veh_Need_Cer //0只要整车；1只要底盘；2 整车和底盘都要
        def zcinfo=ZCInfo.findByVeh_Zchgzbh(hgz)

        //判断PDF文件是否存在
        if (!zcinfo?.upload_Path){
            message = "0"
            result.put("message",message)
            return (render (result as JSON))
        }
        File file=new File(grailsApplication.config.upload.rootDir+zcinfo?.upload_Path)
        if (!file.exists()) {
            message = "0"
            result.put("message",message)
            return (render (result as JSON))
        }

        result.put("filePath",zcinfo.upload_Path)
        //如果是整车和底盘都要时，获取底盘合格证信息  因整车和底盘都要时页面显示的是整车合格证信息，因此只需要获取底盘合格证信息
        def dp=new ZCInfo()
        if (veh_Need_Cer=="2"){
            dp = ZCInfo.findByVeh_ClsbdhAndVeh_Clztxx("${zcinfo.veh_Clsbdh}","DP")
        }
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        if (zcinfo){
            ZCInfoTemp zcinfoTemp = new ZCInfoTemp();
            zcinfoTemp.properties=zcinfo.properties
//            zcinfoTemp.web_isprint =1 //合格证是否打印
            zcinfoTemp.user_down = loginUser.id
            zcinfoTemp.insertTime = DateUtil.getCurrentTime();   /////记录插入时间
            ////经销商真实姓名
            zcinfoTemp.realName = loginUser.userDetail.realName;

            if(zcinfoTemp.save(flush: true)){
                message = "success"
            }else{
                message = "2"
            }
        }
        else{
            message = "3"
        }

        //保存底盘合格证下载记录
        if (veh_Need_Cer=="2"){
            //判断PDF文件是否存在
            if (!dp?.upload_Path){
                message = "5"
                result.put("message",message)
                return (render (result as JSON))
            }
            File dpFile=new File(grailsApplication.config.upload.rootDir+dp?.upload_Path)
            if (!dpFile.exists()) {
                message = "5"
                result.put("message",message)
                return (render (result as JSON))
            }

            result.put("dpFilePath",dp.upload_Path)
            ZCInfoTemp zcinfoTemp = new ZCInfoTemp();
            zcinfoTemp.properties=dp.properties
//            zcinfoTemp.web_isprint =1 //合格证是否打印
            zcinfoTemp.user_down = loginUser.id
            ////完成持久化操作
            zcinfoTemp.insertTime = DateUtil.getCurrentTime();   /////记录插入时间
            ////经销商真实姓名
            zcinfoTemp.realName = loginUser.userDetail.realName;

            if(zcinfoTemp.save(flush: true)){
                message = "success"
            }else{
                message = "2"
            }
        }

        result.put("message",message)

        render (result as JSON)
    }
    ////车间合合格证打印 (合格证信息查询) 导出
    /**
     *  @ author zouq
     *  @Update 修改合格证车间导出因找不到当前用户而产生的报错问题
     *  @UpdateTime zhuwei 2015-01-03
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

                Map labels = ["idReal":"序号", "veh_Clxh":"车辆型号","veh_Clsbdh":"车辆识别代号","veh_Fdjh":"发动机号","veh_Fdjxh":"发动机型号","veh_Type":"汽车和农用车标示0：汽车1：农用车","veh_Zchgzbh":"整车合格证编号","veh_Dphgzbh":"底盘合格证编号","veh_Fzrq":"发证日期","veh_Clzzqymc":"车辆制造企业名称","veh_Qyid":"企业ID","veh_Clfl":"车辆分类","veh_Clmc":"车辆名称"
                              ,"veh_Clpp":"车辆品牌","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_Cjh":"车架号"
                              ,"veh_Rlzl":"燃料种类","veh_Pl":"排量","veh_Gl":"功率","veh_zdjgl":"最大净功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格"
                              ,"veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽"
                              ,"veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车鞍座最大允许总质量","veh_Jsszcrs":"驾驶室准乘人数"
                              ,"veh_Qzdfs":"前制动方式","veh_Hzdfs":"后制动方式","veh_Qzdczfs":"前制动操作方式","veh_Hzdczfs":"后制动操作方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期","veh_Zzbh":"纸张编号"
                              ,"veh_Dywym":"打印唯一码","veh_Zgcs":"最高车速","veh_Clzzrq":"车辆制造日期","veh_Bz":"备注","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其它信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息"
                              ,"veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_Zchgzbh_0":"完整合格证编号","used":"used","used2":"used2","upload":"上传路径","veh_Yh":"油耗","veh_VinFourBit":"vin第四位"
                              ,"veh_Ggpc":"公告批次","veh_pzxlh":"配置序列号","veh_clzzqyxx":"车辆制造企业信息","veh_workshopno":"车间编号","createTime":"创建时间","createrId":"创建人id","updateTime":"最后更新时间"
                              ,"updaterId":"最后更新人","uploader_Id":"上传人","uploader_Time":"上传时间","upload_Failddm":"上传失败返回代码","upload_Path":"合格证上传相对路径","web_status":"合格证状态 是否上传0:未上传1：上传成功2：上传失败"
                              ,"web_isprint":"合格证是否打印","web_virtualcode":"虚拟合格证编号","vercode":"校验信息"]
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
//            def organCode=''
//            loginUser.organs.each{
//                organCode=it.organCode
//            }
//            params.organCode=organCode
                def result=zcInfoService.selectZcinfoByShop(params)
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
     * @Description 进入合格证打印页面
     * @return
     * @Create 2013-09-11 huxx
     */
    def toZcInfoPrint(){
        if (params.zcType=='0'){
            render(view: "/cn/com/wz/vehcert/zcinfo/generate/carprint",model: params)
        }else if (params.zcType=='1'){
            render(view: "/cn/com/wz/vehcert/zcinfo/generate/agrvehprint",model: params)
        }
    }

    /**
     * @description 管理员操作合格证更换保存
     * @Auther Xu
     * @createTime 2013-8-10
     * @return
     * @update 2013-08-26 huxx 判断合格证编号是否重复条件,通过虚拟合格证编号来查询
     * @update 2013-09-05 huxx 添加调用后台pdf自动打印代码，并添加事务，当打印失败时保存的记录回滚
     */
    def saveForPrint() {
        def result=[:]
        def msg="保存成功！"
        def flag='sucessed'

        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        String curTime=DateUtil.getCurrentTime()

        def hgz=ZCInfo.findByVeh_Zchgzbh(params.veh_Zchgzbh)
        hgz.properties = params
        hgz.web_isprint=params.printType
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
            flag='sucessed'
            result.put("msg",msg)
            result.put("flag",flag)
            return render (result as JSON)
        }


        render result as JSON
    }
    /**
     * @description 合格证同步
     * @Auther liuly
     * @createTime 2013-8-10
     * @return
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
            def result=sqlToolService.synZcinfo(ora_con,sql_con,params,zcinfoMaxCount)
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
            sqlToolService.closeCon(sql_con,ora_con);
            render msg
        }
    }
    /**
     * @description 检验合格证是否既有整车又有底盘
     * @Auther QJ
     * @createTime 2018-6-14
     */
    def checkZcinfoCount() {
        def msg="保存成功！"
        def count='0'
        def veh_Zchgzbh_0 = params.veh_Zchgzbh_0
        def veh_Clsbdh = params.veh_Clsbdh
        List zcinfoList = ZCInfo.findAllByVeh_Clsbdh(veh_Clsbdh)
        ZCInfo zcinfo = new ZCInfo()
        if(zcinfoList.size()==2){
            zcinfoList.each{zcinfoR->
                if(zcinfoR.veh_Zchgzbh_0!=veh_Zchgzbh_0){
                    zcinfo=zcinfoR
                }
            }
            msg=zcinfo.id
            count='2'
        }else if(zcinfoList.size()==1){
            msg="该VIN只存在一个合格证，要想选择底盘和整车，请走普通更换流程"
            count='1'
        }else if(zcinfoList.size()==0){
            msg="请先选择要更换的车辆，或检查要更换的车辆VIN是否正确"
            count='0'
        }else{
            msg="该VIN对应合格证号超过2个，请联系产品管理部检查"
            count=zcinfoList.size()
        }
        def result = [count:count,msg:msg]
        render result as JSON
    }

    /**
     *
     * @Description 取得经销商所有的车辆
     * @author QJ
     * @createTime 2018-06-25
     * @return
     */
    def carsOfDistributor() {
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def organCodeList=[]
        def vinList=[]
        if(loginUser.organs.size()>0){
            loginUser.organs.each{
                if(it.organCode){
                    organCodeList.add(it.organCode)
                }
            }
        }
        if(organCodeList.contains('qccp')){
            render(contentType: "text/json") {
                pack(text:null,value:null)
            }
        }else{
            vinList = newDmsSynService.findVinOfDistributor(loginUser.userName)
            render(contentType: "text/json") {
                array {
                    for (m in vinList) {
                        pack(text:m.new_vin ,value:m.new_vin)
                    }
                }
            }
        }
    }
    /**
     *
     * @Description 取得经销商所有的已实销车辆
     * @author QJ
     * @createTime 2018-09-06
     * @return
     */
    def salecarsOfDistributor() {
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def organCodeList=[]
        def vinList=[]
        if(loginUser.organs.size()>0){
            loginUser.organs.each{
                if(it.organCode){
                    organCodeList.add(it.organCode)
                }
            }
        }
        if(organCodeList.contains('qccp')){
            render(contentType: "text/json") {
                pack(text:null,value:null)
            }
        }else{
            vinList = newDmsSynService.findVinOfDistributor(loginUser.userName)
            render(contentType: "text/json") {
                array {
                    for (m in vinList) {
                        if(m.new_loanislock==0||m.new_loanislock==6){
                            List ZcinfoList = ZCInfo.findAllByVeh_Clsbdh(m.new_vin)
                            ZcinfoList.each{zcinfo->
                                pack(text:zcinfo.veh_Zchgzbh_0 ,value:zcinfo.veh_Zchgzbh_0)
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     *
     * @Description 取得经销商所有的车辆的合格证编号
     * @author QJ
     * @createTime 2018-06-25
     * @return
     */
    def hgzbhOfDistributor() {
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        List vinList = newDmsSynService.findVinOfDistributor(loginUser.userName)
        render(contentType: "text/json") {
            array {y
                for (m in vinList) {
                    def zcinfoList =[]
                    zcinfoList = ZCInfo.findAllByVeh_Clsbdh(m.new_vin)
                    for(n in zcinfoList ){
                        if(n.veh_Zchgzbh_0!=null&&n.veh_Zchgzbh_0!=''){
                            pack(text:n.veh_Zchgzbh_0 ,value:n.veh_Zchgzbh_0)
                        }
                    }
                }
            }
        }
    }
    /**
     * @DEscription 查询时检查经销商是否具有调出该车的权限
     * @return
     */
    def checkLimit() {
        def result=[:]
        def  veh_Zchgzbh_0  = params.veh_Zchgzbh_0;  ///虚拟合格证编号信息
        if (!veh_Zchgzbh_0){
            render(view:'/cn/com/wz/vehcert/zcinfo/dealer/index')
            return
        }
        def  zcinfoModel =  ZCInfo.findByVeh_Zchgzbh_0(veh_Zchgzbh_0)
        if(zcinfoModel){
            def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
            def loginUser=User.get(user.id)
            def organCodeList=[]
            if(loginUser.organs.size()>0){
                loginUser.organs.each{
                    if(it.organCode){
                        organCodeList.add(it.organCode)
                    }
                }
            }
            if(zcinfoModel.veh_Type=='0'&&!organCodeList.contains('qccp')){
                List vinObjectList = newDmsSynService.findVinOfDistributor(loginUser.userName)
                List vinList =[]
                if(vinObjectList.size()>0){
                    vinObjectList.each{object->
                        if(object.new_loanislock==0||object.new_loanislock==6){
                            vinList.add(object.new_vin)
                        }
                    }
                }
                if(!vinList.contains(zcinfoModel.veh_Clsbdh)){
                    result.msg="您不具有查看该车合格证的权限"
                    result.flag="0"
                    return render (result as JSON)
                }else{
                    result.msg="允许打印"
                    result.flag="1"
                    return render (result as JSON)
                }
            }else{
                result.msg="允许打印"
                result.flag="1"
                return render (result as JSON)
            }
        }else{
            result.msg="不存在该合格证信息，请核实"
            result.flag="0"
            return render (result as JSON)
        }
    }
}