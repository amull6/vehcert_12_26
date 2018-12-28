package cn.com.wz.stvehcert

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.stvehcert.STInfo
import cn.com.wz.vehcert.zcinfo.ZCInfo
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.ExcelUtils

class STInfoController extends BaseController {
    def dataSource
    def sqlService
    def stInfoService
    def sqlToolService
    //这个地方不注释掉会用submit方式提交表单出现405错误
//    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        render (view: '/cn/com/wz/stvehcert/tractorModle/STInfo/list',model: params)
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
            if(params.modle){
                eq("modle","${params.modle}")
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
            if (params.car_storage_no){
                eq("car_storage_no",params.car_storage_no)
            }
            order 'createTime','desc'
        }
        def lst
        try{
            lst=TractorModle.createCriteria().list([max: params.max,offset: params.offset],cel)
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
        def result=stInfoService.calculateVIN(params,loginUser)
        render  result;
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
        def lst=stInfoService.selectSTInfoList(params)
        render ([rows: lst,total: lst.totalCount] as JSON )

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
        def beforeScbh=params.veh_Scbh
        def workShopNo
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        FrontSize=4
        BehindSize=5
        tractor_type=3
        //打印完成后，将车辆编号替换为流水号+1的流水号
        if(params.intI&&params.veh_Scbh){
            params.veh_Scbh=returnClbh(params.veh_Scbh,FrontSize,BehindSize,tractor_type)
        }
        //回复按钮的操作

        if(params.recover){
            def user=User.get(loginUser.id)
            def codeList=''
            user.organs.each{
                codeList=it.organCode
            }
            STprintSet STprintSet=STprintSet.findByCodeAndUserid('main',codeList)
            if(STprintSet){
                    if(STprintSet.max_tractor){
                        print STprintSet.max_tractor
                        params.veh_Scbh=returnClbh(STprintSet.max_tractor,FrontSize,BehindSize,tractor_type)
                    }
//
            }else{
                    return tractor_ST(params)
            }
        }
        if(params.veh_Scbh){ //params.veh_Hgzbh：打印完成后该值为传到该方法veh_Hgzbh的+1后的值；点击恢复后也为传到该方法veh_Hgzbh的+1后的值
            //递增递减就是传来的veh_Hgzbh本身的值，查看也是本身的值
            String  veh_Scbh=params.veh_Scbh;
            start=veh_Scbh.substring(0,FrontSize)   //取到开始字符串
            end=veh_Scbh.substring(FrontSize)       //取到流水号

            try {
                int end_int=Integer.valueOf(end)
                if(params.direction=='1'){// 向前
                    end_int++   //超标车不需要空位补零，超标车从30000开始
                    String end_str=''+end_int;   //目前值

                    if(type!='tractor_TC'){
                        for(int i=end_str.length();i<BehindSize;i++){
                            end_str="0"+end_str
                        }
                    }
                    veh_Scbh= start+  end_str;
                }else if (params.direction=='-1'){  //向后
                    end_int--
                    String end_str=''+end_int;   //目前值
                    if(type!='tractor_TC'){
                        for(int i=end_str.length();i<BehindSize;i++){
                            end_str="0"+end_str
                        }
                    }

                    veh_Scbh= start+  end_str;
                }

                def cel = {
                    eq("veh_Scbh",veh_Scbh)
                }
                def results = STInfo.createCriteria().list(cel)
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
                STprintSet STprintSet=STprintSet.findByCodeAndUserid('main',codeList)
//                def   printSet=null;
                if (!STprintSet){
                    STprintSet = new STprintSet()
                    STprintSet.code='main'
                    STprintSet.userid=codeList
                }
                if(results==null||results.size()==0){ //如果没有查到合格证信息
                    STInfo obj=null
                    if(params.intI){
                        def celBefore = {
                            eq("veh_Scbh",beforeScbh)
                        }
                        def zc = STInfo.createCriteria().list(celBefore).get(0)
                        obj=new STInfo()
                        obj.properties=zc.properties
                        obj.veh_Scbh=params.veh_Scbh
                        obj.veh_Cx=''
                        obj.veh_Zxbz='';
                        obj.veh_Zxbz='';
                        obj.veh_Jcy='';
                        obj.veh_Fdjh='';
                        obj.veh_Jgqbdm='';
                    }else{
                        obj=new STInfo();
                    }

                    boolean isValid=false;
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
                    if(params.direction||params.recover||(!params.intI)){  //递增减、恢复编号、查看该编号的合格证且其为保存的，将配置值带出来
                        //递增减、恢复查看流水号加减1后流水号是不是合法；由打印自动递增生成合格证编号不再在此处校验流水号，因为在保存时会自动校验
                        if (isValid){
                            if(end_int<=maxNum){ //未达最大值
                                if(end_int>=startNum){ //未达最大值
                                    obj.veh_Scbh=veh_Scbh
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
                        obj = STInfo.findByVeh_Scbh(params.veh_Scbh)
                        if (obj==null){
                            if(params.intI){
                                def celBefore = {
                                    eq("veh_Scbh",beforeScbh)
                                }
                                def zc = STInfo.createCriteria().list(celBefore).get(0)
                                obj=new STInfo()
                                obj.properties=zc.properties
                                obj.veh_Scbh=params.veh_Scbh;
                                obj.veh_Cx=''
                            }else{
                                obj=new STInfo();
                                obj.veh_Scbh=params.veh_Scbh;
                            }
                        }
                    }
//                    if(type=='tractor_TC'||type=='tractor_TX'){
//                        render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S',model:[NZInfoInstance:obj,symboMap:symboMap,STprintSet:STprintSet,type:type])
//                    }else{
                        if(processType=='0'){
                            render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:obj,symboMap:symboMap,STprintSet:STprintSet,type:type,processType:processType])
                        }else{
                            render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:obj,symboMap:symboMap,STprintSet:STprintSet,type:type,processType:processType])
                        }

//                    }
                    return
                }else if (results.size()==1){   //递增递减查看、或者按照车辆编号查看
                    STInfo obj=results.get(0);
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbol in symbolList){
                            symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //tractor_T0:T0 或者  tractor_T1:T1
                            symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_tractor_ T0  或者 start_tractor_ T1
                            symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_tractor_ T0  或者 max_tractor_ T1
                        }
                    }
                        if(processType=='0'){
                            render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:obj,symboMap:symboMap,STprintSet:STprintSet,type:type,processType:processType])
                        }else{
                            render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:obj,symboMap:symboMap,STprintSet:STprintSet,type:type,processType:processType])
                        }
//                        render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_MH',model:[NZInfoInstance:obj,symboMap:symboMap,STprintSet:STprintSet,type:type])
                    return
                }
            } catch(Exception e){
                flash.message = '初始化车辆信息出错'
                    return tractor_ST(params)

            }
        }else{
            flash.message = '请输入合格证编号'
                return tractor_ST(params)
        }
    }
    /**
     *
     * @Description 取得山拖公告
     * @author QJ
     * @createTime 2017-07-20
     * @return
     */
    def toNotice() {
        def aa=params
        ZCInfo obj=new ZCInfo(params)
        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/nzNotice',model:[zcinfoInstance:obj,tabId:params.tabId])
    }
    /**
     * @description 合格证编号加减1处理
     * @Auther zhuwei
     * @createTime 2014-07-05
     * @param  veh_Clbh:底盘号；frontSize：合格证前面固定位数 ；BehindSize：后面流水码位数
     * @return
     */
    def returnClbh(def veh_Clbh,int FrontSize,int BehindSize,int type){
        def veh_Clbh1
        veh_Clbh1=Integer.parseInt(veh_Clbh.substring(FrontSize))+1
        def veh_ClbhS= veh_Clbh1.toString()     //流水号
//        def aa=FrontSize
        //不足六位的以0来补位
        def Z0 ='0'
        if(type.toString()!='2'){    //如果不是超标车,超标车不需要补0
            if(veh_ClbhS.length()<BehindSize){
                for(int i=1;i<(BehindSize-veh_ClbhS.length());i++){
                    Z0+='0'
                }
                Z0+=veh_ClbhS
            }
        }

        if(veh_ClbhS.length()<BehindSize){                  //正常生产的veh_ClbhS会小于BehindSize，超标车的BehindSize为0
            veh_Clbh=veh_Clbh.replace(veh_Clbh.substring(FrontSize),Z0)
        }else{
            veh_Clbh=veh_Clbh.replace(veh_Clbh.substring(FrontSize),veh_ClbhS)   //超标车的veh_ClbhS会小于BehindSize，或者流水号为5位 ，超标车直接替换流水码

        }
        return  veh_Clbh
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
        String modle=params.modle;
        String hqlStr="select ${field} from WZH_TRACTOR_MODLE c where c.modle='${modle}' group by ${field} order by count(*) desc";
        def template=new JdbcTemplate(dataSource)
        List suglist = template.queryForList(hqlStr)
        render(contentType: "text/json") {
            array {
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

        def hgz=STInfo.findByVeh_Scbh(params.veh_Scbh)
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
     *  @ author zhuwei
     *  @Update 更新导出因没有取到所属用户而产生的报错问题
     *  @UpdateTime zhuwei
     */
    def exportForST(){
        def rows =[]
        def lst=[]
        def content=[]
        def aaa=params
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

                Map labels = ["veh_Hgzbh":"合格证编号","veh_Scbh":"生产编号","veh_Cx":"型号","veh_Jgqbdm":"结构区别代号","veh_Fdjh":"发动机号","veh_Scrq":"生产日期"
                        ,"veh_Ys":"颜色","veh_Zxbz":"执行标准","createTime":"创建时间","createrId":"创建ID","updateTime":"最后更新时间"
                        ,"updaterId":"最后更新人","isprint":"是否已打印 0：未打印 1：已打印","veh_workshopno":"车间编号"
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

                def result=stInfoService.selectTSinfoByShop(params)
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
     * @Description 山拖合格证生成页面
     * @CreateTime 2016-06-15
     * @Author zhuwei
     */
    def tractor_ST(){
        flash.clear()
        StringBuilder   veh_Scbh=new StringBuilder('');
        veh_Scbh.append(DateUtil.getCurrentTime('yyMM'));
        def type=params.type    //取自菜单的配置参数，tractor_ST山拖
        def processType=params?.processType//processType=0表示正常打印，processType=1表示出口车
//        def productDh=type.split('_')
//        veh_Hgzbh.append(productDh[1])
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        STInfo obj=new STInfo()
        //人员 组织 车间 三者进行关联  ，s.SYMBO这个字段就是车间编码 ， d.CODE的值都是tractor
        //山拖以为合格证只是：YYMM+5位流水码 +类型码。所以在基础信息车间符号维护时仅仅通过流水码就能区分开来 ，只要车间编号一致就OK
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        //取出
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //tractor_T2:T2 或者  tractor_T3:T3
                symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_tractor_ T2  或者 start_tractor_ T3
                symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_tractor_ T2  或者 max_tractor_ T3
            }
        }
        boolean isValid=true;
        int startNum=0;
        int maxNum =0;
        if (symboMap.containsKey(type)){  //一个合格证打印用户只能关联一个车间
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
        def STprintSet = STprintSet.findByCodeAndUserid("main",codeList)
        if (!STprintSet){
            STprintSet = new STprintSet()
            STprintSet.code="main"
            STprintSet.userid=codeList
            STprintSet.save(flush: true)    //如果没有NZ_printSet数据，那么保存
        }
        if (isValid){
            String maxBH='';
            boolean formTop=false;
            if (STprintSet){
                //若最大值存在，则判断是否跟当前规则相同 若相同则   从最大值开始 若不同则从最小值开始。
                maxBH= STprintSet.max_tractor
                if (maxBH){      //如果printSet有已打印的合格证编号,先验证
                    if (maxBH.startsWith(veh_Scbh.toString())){  //先验证开始是不是3+年份+月份。7.31保存的31407，8月的veh_Hgzbh就是301408，随意每月开始都会从起始流水码开始
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
                veh_Scbh.append(startNum_Str);  //出厂编号赋上流水号
//                veh_Hgzbh.append(workShopNo)    //出厂编号赋上车间代号
                obj.properties = STInfo.findByVeh_Scbh(veh_Scbh)?.properties
                obj.veh_Scbh=veh_Scbh//底盘号与车辆编号是一致的
                //取到车间配置的发动机号和合格证编号
//                obj.veh_Fdjh=DictionaryValue.findByCode('fdjh_config')?.value1
//                obj.veh_Hgzbh=DictionaryValue.findByCode('hgzbh_config')?.value1
            } else{
                String  maxBH_= maxBH.substring(5); //取到流水号,从第位取
                int maxBH_INT=Integer.valueOf(maxBH_)  ;
                if(maxBH_INT<maxNum){ //未达最大值
                    String maxBH_Str=''+  (++maxBH_INT);   //目前值
                    //根据长度添加0
                    for(int i=maxBH_Str.length();i<5;i++){
                        maxBH_Str="0"+maxBH_Str
                    }
                    veh_Scbh.append(maxBH_Str);
//                    veh_Hgzbh.append(workShopNo)  //出厂编号赋上车间符号
                    obj.properties = STInfo.findByVeh_Scbh(veh_Scbh)?.properties
                    obj.veh_Scbh=veh_Scbh//底盘号与车辆编号是一致的
                    //取到车间配置的发动机号和合格证编号
//                    obj.veh_Fdjh=DictionaryValue.findByCode('fdjh_config')?.value1
//                    obj.veh_Hgzbh=DictionaryValue.findByCode('hgzbh_config')?.value1
                }else{ //已达最大值
                    flash.message = '<font color="red">合格证编号已达最大值</font>'
                }
            }
        }else{
            flash.message = '<font color="red">合格证编号生成错误</font>'
        }

        if (processType=='0'){
            render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:obj,symboMap:symboMap,STprintSet:STprintSet,type:type,processType:processType])
        }else if (processType=='1'){
            render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:new STInfo(),symboMap:symboMap,STprintSet:STprintSet,type:type,processType:processType])
        }
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
        String  veh_Hgzbh=params.veh_Hgzbh
        String  veh_Scbh=params.veh_Scbh
        def start
        def  end
        int  tractor_type
        int FrontSize

        FrontSize=4//中大拖
        start=veh_Scbh.substring(0,FrontSize)  //31410TZ或者31410TD
        end=veh_Scbh.substring(FrontSize)
        tractor_type=3


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
        def STprintSet = STprintSet.findByCodeAndUserid("main",workShop)
        if (!STprintSet){
            STprintSet = new STprintSet()
            STprintSet.code="main"
            STprintSet.userid=workShop
        }

        boolean isValid=false;
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
            def STInfoInstance = new STInfo(params)
                if(params?.processType=='0'){ //正常生产的中拖/大拖
                    render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                }else{
                    render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                }
            return
        }
        if (!boundaryCheck){
            def STInfoInstance = new STInfo(params)
            lsh='流水码超界'
                if(params?.processType=='0'){ //正常生产的中拖/大拖
                    render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                }else{
                    render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                }
            return
        }

        //保存农装合格证打印的车型
//        if (params.veh_Cx){
//            def cx=NzCx.findAllByCx(params.veh_Cx)
//            if (cx==null||cx.size()==0){   //此情况下保存
//                NzCx  newCx=new NzCx()
//                newCx.cx=params.veh_Cx
//                newCx.save()
//            }
//        }

        STInfo  STInfo=STInfo.findByVeh_Scbh(veh_Scbh)
        lsh=veh_Scbh.substring(FrontSize)      //根据前面的固定为去后面的流水号
        if (STInfo){ //合格证编号存在
            if (STInfo.upload=='1'){ //已上传
                flash.message = '<font color="red">合格证已上传,不允许修改</font>'
                    if(params?.processType=='0'){ //正常生产的中拖/大拖
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:STInfo,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }else{
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:STInfo,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }
                return

            }else{ //未上传
                //判断合格证信息是否正确完整

                def checkMessage=stInfoService.checkInfo(new STInfo(params))
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    STInfo.properties = params
                    STInfo.updaterId=loginUser.userName;
                    STInfo.updateTime=DateUtil.getCurrentTime()
                    STInfo.veh_Scbh_0=params?.veh_Scbh.toString()
                    if (!STInfo.save(flush: true)) {
                        flash.message = '<font color="red">合格证更新失败</font>'
                    }else{
                        flash.message = '合格证更新成功'
                    }
                }
                    if(params?.processType=='0'){
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:STInfo,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }else{
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:STInfo,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }
                return

            }
        }else{ //合格证编号不存在
            int   maxBH_INT=-1
            String maxBH
//            if(type!='tractor_TC'){
                maxBH=STprintSet?.max_tractor_upload
//            }else{
//                maxBH=STprintSet?.max_CBtractor_upload
//            }

            if(maxBH&&maxBH.startsWith(start)){
                //有上传的最大合格证号时,验证开始是不是3+年份+月份。如7.31保存的31407，8月的veh_Hgzbh就是301408，随意每月开始都会从起始流水码开始
                String maxBH_
//                if(type!='tractor_TC'){
                    maxBH_= maxBH.substring(FrontSize);//取到正常生产的合格证的流水号：7到12位，包含7和12位
//                }else{
//                    maxBH_= maxBH.substring(FrontSize)     //取到超标车的流水码
//                }

                maxBH_INT=Integer.valueOf(maxBH_) ;
            }
            if (maxBH_INT==-1||end_int>maxBH_INT){ //合格证跨月，upload合格证是7月的，现在当前声场的是8月的||当前的流水码大于上传的最大合格证流水号，可以保存
                def STInfoInstance = new STInfo(params)
                STInfoInstance.veh_Scbh_0=params?.veh_Scbh.toString()
                STInfoInstance.createrId=loginUser.userName
                STInfoInstance.createTime=DateUtil.getCurrentTime()
                STInfoInstance.veh_workshopno=workShop   //保存生产车间编号
                if(params?.processType=='1'){
                    STInfoInstance.processType=1
                }
                //判断合格证信息格式是否完整正确
                def checkMessage=stInfoService.checkInfo(STInfoInstance)
                if (checkMessage){
                    flash.message ="<font color='red'>${checkMessage}</font>"
                }else{
                    if (!STInfoInstance.save(flush: true)){
                        flash.message = '<font color="red">保存失败</font>'
                                           }else{
                        //合格证保存成功，需要暂时将流水号最大的合格证保存到农装printSet表中
                        flash.message = '合格证保存成功'
                        boolean needUpdate=true;
                        String max
//                        if(type!='tractor_TC'){
                            max=STprintSet?.max_tractor   //printSet里保存的非上传最大合格证号
//                        }else{
//                            max=STprintSet?.max_CBtractor
//                        }

                        println(max)
                        if (max&&max.startsWith(start)){
                            String max_str
                            max_str=max.substring(FrontSize);
                            int max_int=Integer.valueOf(max_str)
                            if (end_int<max_int){  //现在的流水号<printset里面的最大流水号 ,流水号不需要更新 ，这样最开始时没有保存流水号也会在执行到这时将起始流水号保存到printSet
                                needUpdate=false;
                            }
                        }
                        if (needUpdate){
//                            if(type!='tractor_TC'){
//                                if(params?.processType=='0'||type=='tractor_TX'){  //大、中拖中的0是正常打印的合格证，1为出口车||正常生产的小拖
                                    STprintSet.max_tractor=STInfoInstance.veh_Scbh   //printSet里保存的非上传最大合格证号，出口车不更新STprintSet表中的max_tractor值
//                                }
//                            }else{
//                                STprintSet.max_CBtractor=STInfoInstance.veh_Hgzbh
//                            }
                            STprintSet.code='main'
                            STprintSet.userid= workShop
                            if (!STprintSet.save(flush: true)){
                                flash.message = '<font color="red">流水号保存失败</font>'
                            }else{
                                println(STprintSet?.max_tractor)
                            }
                        }
                    }
                }
                lsh=STInfoInstance.veh_Hgzbh.substring(FrontSize)
                    if(params?.processType=='0'){ //正常生产的中拖/大拖
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }else{
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }
                return
            }else{ //不允许保存
                flash.message = '合格证编号小于已上传合格证流水码的最大值,不允许保存'
                def STInfoInstance = new STInfo(params)
                lsh=params.veh_Hgzbh.substring(FrontSize)
                    if(params?.processType=='0'){ //正常生产的中拖/大拖
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }else{
                        render(view:'/cn/com/wz/stvehcert/tractorModle/STInfo/tractor_ST1',model:[STInfoInstance:STInfoInstance,symboMap:symboMap,STprintSet:STprintSet,lsh:lsh,type: type,processType:params?.processType])
                    }
                return
            }
        }
    }
//    def save() {
//        def aaa=params
//        def STInfoInstance = new STInfo(params)
//        if (!STInfoInstance.save(flush: true)) {
//            render(view: "create", model: [STInfoInstance: STInfoInstance])
//            return
//        }
//
//        flash.message = message(code: 'default.created.message', args: [message(code: 'STInfo.label', default: 'STInfo'), STInfoInstance.id])
//        redirect(action: "show", id: STInfoInstance.id)
//    }

    def show() {
        def STInfoInstance = STInfo.get(params.id)
        if (!STInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'STInfo.label', default: 'STInfo'), params.id])
            redirect(action: "list")
            return
        }

        [STInfoInstance: STInfoInstance]
    }

    def edit() {
        def STInfoInstance = STInfo.get(params.id)
        if (!STInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'STInfo.label', default: 'STInfo'), params.id])
            redirect(action: "list")
            return
        }

        [STInfoInstance: STInfoInstance]
    }

    def update() {
        def STInfoInstance = STInfo.get(params.id)
        if (!STInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'STInfo.label', default: 'STInfo'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (STInfoInstance.version > version) {
                STInfoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'STInfo.label', default: 'STInfo')] as Object[],
                        "Another user has updated this STInfo while you were editing")
                render(view: "edit", model: [STInfoInstance: STInfoInstance])
                return
            }
        }

        STInfoInstance.properties = params

        if (!STInfoInstance.save(flush: true)) {
            render(view: "edit", model: [STInfoInstance: STInfoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'STInfo.label', default: 'STInfo'), STInfoInstance.id])
        redirect(action: "show", id: STInfoInstance.id)
    }

    def delete() {
        def STInfoInstance = STInfo.get(params.id)
        if (!STInfoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'STInfo.label', default: 'STInfo'), params.id])
            redirect(action: "list")
            return
        }

        try {
            STInfoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'STInfo.label', default: 'STInfo'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'STInfo.label', default: 'STInfo'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
