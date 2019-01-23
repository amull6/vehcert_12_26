package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.vehcert.carstorage.CarStorage
import cn.com.wz.vehcert.carstorage.NzCarStorage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.itextpdf.text.pdf.qrcode.EncodeHintType
import grails.converters.JSON
import groovy.sql.Sql
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.dao.DataIntegrityViolationException
import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.ExcelUtils

import java.nio.file.FileSystems

class NZInfoController extends BaseController {

    def dataSource
    def sqlService
    def nzInfoService
    def sqlToolService
    def static final yearsCode = ['2015'  : 'F', '2016': 'G', '2017': 'H', '2018': 'J', '2019': 'K', '2020': 'L'
                                  , '2021': 'M', '2022': 'N', '2023': 'P', '2024': 'R', '2025': 'S', '2026': 'T', '2027': 'V', '2028': 'W', '2029': 'X', '2030': 'Y'
                                  , '2031': '1', '2032': '2', '2033': '3', '2034': '4', '2035': '5', '2036': '6', '2037': '7', '2038': '8', '2039': '9', '2040': 'A'
                                  , '2041': 'B', '2042': 'C', '2043': 'D', '2044': 'E', '2045': 'F', '2046': 'G'
    ]
    def static final monthcode=['01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'X','11':'J','12':'P']
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
        def lst=nzInfoService.selectNZInfoList(params)
        render ([rows: lst,total: lst.totalCount] as JSON )

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
            if(params.veh_Lhqxs){
                eq("veh_Lhqxs","${params.veh_Lhqxs}")
            }

            if (params.veh_Qpzxs){
                eq("veh_Qpzxs",params.veh_Qpzxs)
            }
            if (params.veh_Hpzxs){
                eq("veh_Hpzxs",params.veh_Hpzxs)
            }
            if (params.veh_Jzxs){
                eq("veh_Jzxs",params.veh_Jzxs)
            }
            if (params.veh_Jssxs){
                eq("veh_Jssxs",params.veh_Jssxs)
            }
            if (params.veh_Bhxs){
                eq("veh_Bhxs",params.veh_Bhxs)
            }
            if (params.veh_Ddlj){
                eq("veh_Ddlj",params.veh_Ddlj)
            }
            if (params.veh_Ltgg){
                eq("veh_Ltgg",params.veh_Ltgg)
            }
            if (params.car_storage_type){
                eq("car_storage_type",params.car_storage_type)
            }
            if (params.car_storage_no){
                eq("car_storage_no",params.car_storage_no)
            }
            eq("turnOff",'0')  //通过菜单配置传值turnOff，只提取启用状态的公告
            if(params.isExp){
                eq("isExp",params.isExp)
            }
            order 'createTime','desc'
        }
        def lst
        try{
            lst=NzCarStorage.createCriteria().list([max: params.max,offset: params.offset],cel)
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
     *
     * @Description 寻找公告信息和合格证信息中建议输入
     * @author mike
     * @createTime 2013-06-29
     * @return
     */
    def jsonInfoSuggestion() {
        String field=params.field;
        String veh_Clxh=params.veh_Clxh;
        String hqlStr="select ${field} from WZH_NZCARSTORAGE c where c.veh_Clxh='${veh_Clxh}' group by ${field} order by count(*) desc";
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
     *@Description 环保代码计算
     *@Auther QJ
     *@createTime 2017-07-20
     */
    def calculateVIN(){
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def result=nzInfoService.calculateVIN(params,loginUser)
        render  result;
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
     * @Description 新版农装合格证2018
     * @CreateTime 2018-06-28
     * @Author QJ
     */
    def tractor_S(){
        flash.clear()
        StringBuilder veh_Clbh
        def lsh
        veh_Clbh = new StringBuilder();
        def year = DateUtil.getCurrentTime('yyyy');
        String yearCode=yearsCode.get(year);
        veh_Clbh.append(yearCode);
        def month = DateUtil.getCurrentTime('MM');
        String monthcode=monthcode.get(month);
        veh_Clbh.append(monthcode)
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        NZInfo obj=new NZInfo()
        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        //取出
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap=[:]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //tractor_T0:T0 或者  tractor_T1:T1
                symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_tractor_ T0  或者 start_tractor_ T1
                symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_tractor_ T0  或者 max_tractor_ T1
            }
        }
        boolean isValid=true;
        String type='';//类别 0为山拖大拖 1山拖中拖 2为日照中拖 3为日照小拖 4 日照收获机 5青贮机
        String car_storage_type='1'
        int startNum=0;
        int maxNum =0;
        if (symboMap.containsKey('tractor_A')){
            veh_Clbh.append(symboMap.get('tractor_A'));
            startNum= symboMap.get('start_tractor_A')
            maxNum= symboMap.get('max_tractor_A')
            symboMap.put('check_tractor_A',1);
            type='tractor_A'
            isValid=true;
        } else if (symboMap.containsKey('tractor_B')){
            veh_Clbh.append(symboMap.get('tractor_B'));
            startNum= symboMap.get('start_tractor_B')
            maxNum= symboMap.get('max_tractor_B')
            symboMap.put('check_tractor_B',1);
            type='tractor_B'
            isValid=true;
        }else if (symboMap.containsKey('tractor_C')){
            veh_Clbh.append(symboMap.get('tractor_C'));
            startNum= symboMap.get('start_tractor_C')
            maxNum= symboMap.get('max_tractor_C')
            symboMap.put('check_tractor_C',1);
            type='tractor_C'
            isValid=true;
        }else if (symboMap.containsKey('harvest_D')){
            veh_Clbh.append(symboMap.get('harvest_D'));
            startNum= symboMap.get('start_harvest_D')
            maxNum= symboMap.get('max_harvest_D')
            symboMap.put('check_harvest_D',1);
            type='harvest_D'
            car_storage_type='3'
            isValid=true;
        }else if (symboMap.containsKey('rice_D')){
            veh_Clbh.append(symboMap.get('rice_D'));
            startNum= symboMap.get('start_rice_D')
            maxNum= symboMap.get('max_rice_D')
            symboMap.put('check_rice_D',1);
            type='rice_D'
            car_storage_type='4'
            isValid=true;
        }else if (symboMap.containsKey('silage_E')){
            veh_Clbh.append(symboMap.get('silage_E'));
            startNum= symboMap.get('start_silage_E')
            maxNum= symboMap.get('max_silage_E')
            symboMap.put('check_silage_E',1);
            type='silage_E'
            car_storage_type='5'
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
        def NZprintSet = NZprintSet.findByCodeAndUserid("main",codeList)
        if (!NZprintSet){
            NZprintSet = new NZprintSet()
            NZprintSet.code="main"
            NZprintSet.userid=codeList
            NZprintSet.save(flush: true)    //如果没有NZ_printSet数据，那么保存
        }
        if (isValid){
            String maxBH='';
            boolean formTop=false;
            if (NZprintSet){
                //若最大值存在，则判断是否跟当前规则相同 若相同则   从最大值开始 若不同则从最小值开始。
                maxBH= NZprintSet.max_tractor
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
                for(int i=startNum_Str.length();i<4;i++){
                    startNum_Str="0"+startNum_Str
                }
                veh_Clbh.append(startNum_Str);//赋上流水号
//                obj.properties = ZCInfo.findByVeh_Zchgzbh(veh_Clbh)?.properties
                obj.veh_Clbh=veh_Clbh
            }else{
                String  maxBH_
                maxBH_= maxBH.substring(3,7); //取到流水号，从第6位取到11位取5位
                int maxBH_INT=Integer.valueOf(maxBH_)  ;
                if(maxBH_INT<maxNum){ //未达最大值
                    String maxBH_Str=''+  (++maxBH_INT);   //目前值
                    //根据长度添加0
                    for(int i=maxBH_Str.length();i<4;i++){
                        maxBH_Str="0"+maxBH_Str
                    }
                    veh_Clbh.append(maxBH_Str);
//                    obj.properties = NZInfo.findByVeh_Clbh(veh_Clbh)?.properties
                    obj.veh_Clbh=veh_Clbh
                }else{ //已达最大值
                    flash.message = '<font color="red">合格证编号已达最大值</font>'
                }
            }
            lsh= veh_Clbh.substring(3)
        }else{
            flash.message = '<font color="red">合格证编号生成错误</font>'
        }
        render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S',model:[NZInfoInstance:obj,symboMap:symboMap,NZprintSet:NZprintSet,lsh:lsh,type:type,car_storage_type:car_storage_type])  //NZInfoInstance:obj,symboMap:symboMap,printSet:printSet
    }

    /**
     * @description 合格证保存
     * @Auther zhuwei
     * @createTime 2014-07-03
     * @return
     * @update
     */
    def save() {
        flash.clear()
        def params = params
        def lsh
        def type = params.type
        def processType = params?.processType
        String veh_Clbh = params.veh_Clbh //车辆流水码
        def start
        def end
        // tractor_type区分不同工厂 不同编号
        int tractor_type
        int FrontSize
//        int BackSize
        if (type == 'tractor_A' || type == 'tractor_B') {
            FrontSize = 3
            start = veh_Clbh.substring(0, FrontSize)
            end = veh_Clbh.substring(FrontSize)
            tractor_type = 1

        } else if (type == 'tractor_C'|| type == 'harvest_D' || type == 'rice_D') { //五征格式
            FrontSize = 3
            start = veh_Clbh.substring(0, FrontSize)
            end = veh_Clbh.substring(FrontSize)
            tractor_type = 2

        } else if (type == 'silage_E') {   //其他格式
            FrontSize = 3
            start = veh_Clbh.substring(0, FrontSize)
            end = veh_Clbh.substring(FrontSize)
            tractor_type = 3
        }
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def workShop = ''
        def organs = User.get(loginUser.id).organs
        organs.each {
            workShop = it.organCode     //车间端打印合格证的帐号只能管理一个车间组织
        }

        //人员 组织 车间 三者进行关联
        String symbolStr = "select s.SYMBOL,d.CODE,w.SERIAL_NUM as serialNum,w.name as name,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        def template = new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap = [:]

        //获取打印参数
        def NZprintSet = NZprintSet.findByCodeAndUserid("main", workShop)
        if (!NZprintSet) {
            NZprintSet = new NZprintSet()
            NZprintSet.code = "main"
            NZprintSet.userid = workShop
        }

        boolean isValid = false;
        int startNum = 0;
        int maxNum = 0;
        if (symbolList != null && symbolList.size() != 0) {
            for (def symbol in symbolList) {
                symboMap.put(symbol.get('CODE') + '_' + symbol.get('SYMBOL'), symbol.get('SYMBOL'));
                //tractor_T0:T0 或者  tractor_T1:T1
                symboMap.put('start_' + symbol.get('CODE') + '_' + symbol.get('SYMBOL'), symbol.get('STARTNUM'));
                //start_tractor_ T0  或者 start_tractor_ T1
                symboMap.put('max_' + symbol.get('CODE') + '_' + symbol.get('SYMBOL'), symbol.get('MAXNUM'));
                //   max_tractor_ T0  或者 max_tractor_ T1
            }
        }
        if (symboMap.containsKey(type)) {
            startNum = symboMap.get('start_' + type)
            maxNum = symboMap.get('max_' + type)
            symboMap.put('check_' + type, 1);
            isValid = true;
        } else {
            isValid = false;
        }
        int end_int = 0
        boolean boundaryCheck = true;
        try {
            end_int = Integer.valueOf(end);   //要保存合格证的流水号
            if (isValid) {
                if (end_int <= maxNum) { //未达最大值
                    if (end_int >= startNum) { //未达最大值
                        boundaryCheck = true;
                    } else { //已达最大值
                        flash.message = '<font color="red">编号已达最小值</font>'
                        boundaryCheck = false;
                    }
                } else { //已达最大值
                    flash.message = '<font color="red">编号已达最大值</font>'
                    boundaryCheck = false;
                }
            } else {
                flash.message = '<font color="red">编号错误</font>'
                boundaryCheck = false;
            }
        } catch (Exception e) {
            flash.message = '<font color="red">功能错误</font>'
            def NZInfoInstance = new NZInfo(params)
            render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
            return
        }
        if (!boundaryCheck) {
            def NZInfoInstance = new NZInfo(params)
            lsh = '流水码超界'
            render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
            return
        }

        //保存农装合格证打印的颜色
        if (params.veh_Jsys) {
            def ys = Color.findAllByName(params.veh_Jsys)
            if (ys == null || ys.size() == 0) {   //此情况下保存
                Color color = new Color()
                color.name = params.veh_Jsys
                color.save()
            }
        }
        //保存农装合格证打印的车型
        if (params.veh_Clxh) {
            def cx = NzCx.findAllByCx(params.veh_Clxh)
            if (cx == null || cx.size() == 0) {   //此情况下保存
                NzCx newCx = new NzCx()
                newCx.cx = params.veh_Clxh
                newCx.save()
            }
        }
        NZInfo NZInfo = NZInfo.findByVeh_Clbh(veh_Clbh)
        lsh = veh_Clbh.substring(FrontSize)      //根据前面的固定为去后面的流水号
        if (NZInfo) { //合格证编号存在
            if (NZInfo.upload == '1') { //已上传
                flash.message = '<font color="red">合格证已上传,不允许修改</font>'
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfo, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return
            } else { //未上传
                //判断合格证信息是否正确完整
                // tractor_type区分不同工厂 不同编号
                def checkMessage = nzInfoService.checkInfo(new NZInfo(params), tractor_type)
                if (checkMessage) {
                    flash.message = "<font color='red'>${checkMessage}</font>"
                } else {
                    NZInfo.properties = params
                    def qrpiUrl = this.generateQr(params)  //使用第三方插件生成二维码,f返回文件URL
                    NZInfo.qr_name=qrpiUrl.replaceAll('\\\\','/')
                    NZInfo.updaterId = loginUser.userName;
                    NZInfo.updateTime = DateUtil.getCurrentTime()
                    if (!NZInfo.save(flush: true)) {
                        flash.message = '<font color="red">合格证更新失败</font>'
                    } else {
                        flash.message = '合格证更新成功'
                    }
                }
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfo, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return

            }
        } else { //合格证编号不存在
            int maxBH_INT = -1
            String maxBH
            maxBH = NZprintSet?.max_tractor_upload
            if (maxBH) {
                if (maxBH && maxBH.startsWith(start)) {
                    //有上传的最大合格证号时,验证开始是不是3+年份+月份。如7.31保存的31407，8月的veh_Clbh就是301408，随意每月开始都会从起始流水码开始
                    String maxBH_
                    maxBH_ = maxBH.substring(FrontSize, 7);//取到正常生产的合格证的流水号：5到7位，包含5和8位
                    maxBH_INT = Integer.valueOf(maxBH_);
                }
            }
            if (maxBH_INT == -1 || end_int > maxBH_INT) { //合格证跨月，upload合格证是7月的，现在当前声场的是8月的||当前的流水码大于上传的最大合格证流水号，可以保存
                def NZInfoInstance = new NZInfo(params)
                def qrpiUrl = this.generateQr(params)  //使用第三方插件生成二维码,f返回文件URL
                NZInfoInstance.qr_name=qrpiUrl.replaceAll('\\\\','/')
                NZInfoInstance.createrId = loginUser.userName
                NZInfoInstance.createTime = DateUtil.getCurrentTime()
                NZInfoInstance.veh_workshopno = workShop   //保存生产车间编号
                //判断合格证信息格式是否完整正确
                def checkMessage = nzInfoService.checkInfo(NZInfoInstance, tractor_type)
                if (checkMessage) {
                    flash.message = "<font color='red'>${checkMessage}</font>"
                } else {
                    if (!NZInfoInstance.save(flush: true)) {
                        flash.message = '<font color="red">保存失败</font>'
                    } else {
                        //合格证保存成功，需要暂时将流水号最大的合格证保存到农装printSet表中
                        flash.message = '合格证保存成功'
                        boolean needUpdate = true;
                        String max
                        max = NZprintSet?.max_tractor   //printSet里保存的非上传最大合格证号
                        println(max)
                        if(max){
                            if (max && max.startsWith(start)) {
                                String max_str
                                max_str = max.substring(FrontSize);
                                int max_int = Integer.valueOf(max_str)
                                if (end_int < max_int) {
                                    //现在的流水号<printset里面的最大流水号 ,流水号不需要更新 ，这样最开始时没有保存流水号也会在执行到这时将起始流水号保存到printSet
                                    needUpdate = false;
                                }
                            }
                        }
                        if (needUpdate) {
                            NZprintSet.max_tractor = NZInfoInstance.veh_Clbh
                            //printSet里保存的非上传最大合格证号，出口车不更新NzprintSet表中的max_tractor值
                            NZprintSet.code = 'main'
                            NZprintSet.userid = workShop
                            if (!NZprintSet.save(flush: true)) {
                                flash.message = '<font color="red">流水号保存失败</font>'
                            } else {
                                println(NZprintSet?.max_tractor)
                            }
                        }
                    }
                }
                lsh = NZInfoInstance.veh_Clbh.substring(FrontSize)
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return
            } else { //不允许保存
                flash.message = '合格证编号小于已上传合格证流水码的最大值,不允许保存'
                def NZInfoInstance = new NZInfo(params)
                lsh = params.veh_Clbh.substring(FrontSize)
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return
            }
        }
    }
    /**
     * @description 出口车合格证保存
     * @Auther zhuwei
     * @createTime 2018-12-19
     * @return
     * @update
     */
    def exSave() {
        flash.clear()
        def params = params
        def lsh
        def type = params.type
        def processType = params?.processType
        String veh_Clbh = params.veh_Clbh //车辆流水码
        def start
        def end
        int tractor_type
        int FrontSize
//        int BackSize
        if (type == 'tractor_A' || type == 'tractor_B') {
            FrontSize = 3
            start = veh_Clbh.substring(0, FrontSize)
            end = veh_Clbh.substring(FrontSize)
            tractor_type = 1

        } else if (type == 'tractor_C'|| type == 'harvest_D' || type == 'rice_D') { //五征格式
            FrontSize = 3
            start = veh_Clbh.substring(0, FrontSize)
            end = veh_Clbh.substring(FrontSize)
            tractor_type = 2

        } else if (type == 'silage_E') {   //其他格式
            FrontSize = 3
            start = veh_Clbh.substring(0, FrontSize)
            end = veh_Clbh.substring(FrontSize)
            tractor_type = 3
        }
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def workShop = ''
        def organs = User.get(loginUser.id).organs
        organs.each {
            workShop = it.organCode     //车间端打印合格证的帐号只能管理一个车间组织
        }

        //人员 组织 车间 三者进行关联
        String symbolStr = "select s.SYMBOL,d.CODE,w.SERIAL_NUM as serialNum,w.name as name,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        def template = new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboMap = [:]

        //获取打印参数
        def NZprintSet = NZprintSet.findByCodeAndUserid("main", workShop)
        if (!NZprintSet) {
            NZprintSet = new NZprintSet()
            NZprintSet.code = "main"
            NZprintSet.userid = workShop
        }

        boolean isValid = false;
        int startNum = 0;
        int maxNum = 0;
        if (symbolList != null && symbolList.size() != 0) {
            for (def symbol in symbolList) {
                symboMap.put(symbol.get('CODE') + '_' + symbol.get('SYMBOL'), symbol.get('SYMBOL'));
                //tractor_T0:T0 或者  tractor_T1:T1
                symboMap.put('start_' + symbol.get('CODE') + '_' + symbol.get('SYMBOL'), symbol.get('STARTNUM'));
                //start_tractor_ T0  或者 start_tractor_ T1
                symboMap.put('max_' + symbol.get('CODE') + '_' + symbol.get('SYMBOL'), symbol.get('MAXNUM'));
                //   max_tractor_ T0  或者 max_tractor_ T1
            }
        }
        if (symboMap.containsKey(type)) {
            startNum = symboMap.get('start_' + type)
            maxNum = symboMap.get('max_' + type)
            symboMap.put('check_' + type, 1);
            isValid = true;
        } else {
            isValid = false;
        }
        int end_int = 0
        boolean boundaryCheck = true;
        try {
            end_int = Integer.valueOf(end);   //要保存合格证的流水号
            if (isValid) {
                if (end_int <= maxNum) { //未达最大值
                    if (end_int >= startNum) { //未达最大值
                        boundaryCheck = true;
                    } else { //已达最大值
                        flash.message = '<font color="red">编号已达最小值</font>'
                        boundaryCheck = false;
                    }
                } else { //已达最大值
                    flash.message = '<font color="red">编号已达最大值</font>'
                    boundaryCheck = false;
                }
            } else {
                flash.message = '<font color="red">编号错误</font>'
                boundaryCheck = false;
            }
        } catch (Exception e) {
            flash.message = '<font color="red">功能错误</font>'
            def NZInfoInstance = new NZInfo(params)
            render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
            return
        }
        if (!boundaryCheck) {
            def NZInfoInstance = new NZInfo(params)
            lsh = '流水码超界'
            render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
            return
        }

        //保存农装合格证打印的颜色
        if (params.veh_Jsys) {
            def ys = Color.findAllByName(params.veh_Jsys)
            if (ys == null || ys.size() == 0) {   //此情况下保存
                Color color = new Color()
                color.name = params.veh_Jsys
                color.save()
            }
        }
        //保存农装合格证打印的车型
        if (params.veh_Clxh) {
            def cx = NzCx.findAllByCx(params.veh_Clxh)
            if (cx == null || cx.size() == 0) {   //此情况下保存
                NzCx newCx = new NzCx()
                newCx.cx = params.veh_Clxh
                newCx.save()
            }
        }
        NZInfo NZInfo = NZInfo.findByVeh_Clbh(veh_Clbh)
        lsh = veh_Clbh.substring(FrontSize)      //根据前面的固定为去后面的流水号
        if (NZInfo) { //合格证编号存在
            if (NZInfo.upload == '1') { //已上传
                flash.message = '<font color="red">合格证已上传,不允许修改</font>'
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfo, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return
            } else { //未上传
                //判断合格证信息是否正确完整
                def checkMessage = nzInfoService.checkInfo(new NZInfo(params), tractor_type)
                if (checkMessage) {
                    flash.message = "<font color='red'>${checkMessage}</font>"
                } else {
                    NZInfo.properties = params
                    def qrpiUrl = this.generateQr(params)  //使用第三方插件生成二维码,f返回文件URL
                    NZInfo.qr_name=qrpiUrl.replaceAll('\\\\','/')
                    NZInfo.updaterId = loginUser.userName;
                    NZInfo.updateTime = DateUtil.getCurrentTime()
                    if (!NZInfo.save(flush: true)) {
                        flash.message = '<font color="red">合格证更新失败</font>'
                    } else {
                        flash.message = '合格证更新成功'
                    }
                }
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfo, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return

            }
        } else { //合格证编号不存在
            int maxBH_INT = -1
            String maxBH
            maxBH = NZprintSet?.max_tractor_upload
            if (maxBH) {
                if (maxBH && maxBH.startsWith(start)) {
                    //有上传的最大合格证号时,验证开始是不是3+年份+月份。如7.31保存的31407，8月的veh_Clbh就是301408，随意每月开始都会从起始流水码开始
                    String maxBH_
                    maxBH_ = maxBH.substring(FrontSize, 7);//取到正常生产的合格证的流水号：5到7位，包含5和8位
                    maxBH_INT = Integer.valueOf(maxBH_);
                }
            }
            if (maxBH_INT == -1 || end_int > maxBH_INT) { //合格证跨月，upload合格证是7月的，现在当前声场的是8月的||当前的流水码大于上传的最大合格证流水号，可以保存
                def NZInfoInstance = new NZInfo(params)
                def qrpiUrl = this.generateQr(params)  //使用第三方插件生成二维码,f返回文件URL
                NZInfoInstance.qr_name=qrpiUrl.replaceAll('\\\\','/')
                NZInfoInstance.createrId = loginUser.userName
                NZInfoInstance.createTime = DateUtil.getCurrentTime()
                NZInfoInstance.veh_workshopno = workShop   //保存生产车间编号
                //判断合格证信息格式是否完整正确
                def checkMessage = nzInfoService.checkInfo(NZInfoInstance, tractor_type)
                if (checkMessage) {
                    flash.message = "<font color='red'>${checkMessage}</font>"
                } else {
                    if (!NZInfoInstance.save(flush: true)) {
                        flash.message = '<font color="red">保存失败</font>'
                    } else {
                        //合格证保存成功，需要暂时将流水号最大的合格证保存到农装printSet表中
                        flash.message = '合格证保存成功'
                        boolean needUpdate = true;
                        String max
                        max = NZprintSet?.max_tractor   //printSet里保存的非上传最大合格证号
                        println(max)
                        if(max){
                            if (max && max.startsWith(start)) {
                                String max_str
                                max_str = max.substring(FrontSize);
                                int max_int = Integer.valueOf(max_str)
                                if (end_int < max_int) {
                                    //现在的流水号<printset里面的最大流水号 ,流水号不需要更新 ，这样最开始时没有保存流水号也会在执行到这时将起始流水号保存到printSet
                                    needUpdate = false;
                                }
                            }
                        }
                        if (needUpdate) {
                            NZprintSet.max_tractor = NZInfoInstance.veh_Clbh
                            //printSet里保存的非上传最大合格证号，出口车不更新NzprintSet表中的max_tractor值
                            NZprintSet.code = 'main'
                            NZprintSet.userid = workShop
                            if (!NZprintSet.save(flush: true)) {
                                flash.message = '<font color="red">流水号保存失败</font>'
                            } else {
                                println(NZprintSet?.max_tractor)
                            }
                        }
                    }
                }
                lsh = NZInfoInstance.veh_Clbh.substring(FrontSize)
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return
            } else { //不允许保存
                flash.message = '合格证编号小于已上传合格证流水码的最大值,不允许保存'
                def NZInfoInstance = new NZInfo(params)
                lsh = params.veh_Clbh.substring(FrontSize)
                render(view: '/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S', model: [NZInfoInstance: NZInfoInstance, symboMap: symboMap, NZprintSet: NZprintSet, lsh: lsh, type: type])
                return
            }
        }
    }
    /**
     * 使用插件Zxing生成生成二维码,传入车架号，传出生成图片url
     * @CreateTime 2016-12-19   zhuwei
     * @return
     */
    def generateQr(def params){
        def now=new Date().getTime()
        String filePath =grailsApplication.config.NZQR.location
        String fileName =params.veh_Clbh+"_"+now+".png"
        println(fileName)
        JSONObject json = new JSONObject();
        if(params.type==''){

        }else{

        }
        String content ="合格证编号:" +params.veh_Hgzbh+";发证日期:"+params.veh_Fzrq+";生产企业名称:"+params.veh_Scqymc+";品牌:"+params.veh_Pp+";类型:"+params.veh_Lx+";型号名称:"+params.veh_Cx+";发动机型号:"+params.veh_Fdjxh+";发动机号码:"+params.veh_Fdjh+";功率/kW:"+params.veh_Gl+";排放标准号及排放阶段:"+params.veh_Pfbz+";出厂编号:"+params.veh_new_clbh+";底盘号:"+params.veh_Dph;// 内容
        int width = 160; // 图像宽度
        int height = 160; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
//        def  bitMatrix  = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def  bitMatrix  = new MultiFormatWriter().encode(new String(content.getBytes("UTF-8"),"ISO-8859-1"),
                BarcodeFormat.QR_CODE, width, height);
        bitMatrix = deleteWhite(bitMatrix);
        def path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        def qrpiUrl=filePath+"\\"+fileName
        return qrpiUrl
    }
    /**
     * 去掉二维码白边
     * @CreateTime 2016-12-19   zhuwei
     * @return
     */
    def deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
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
     * @description 根据条件查询合格证
     * @Auther zhuwei
     * @createTime 2014-07-05
     * @update  农装合格证2018 2018-05-30
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
        def workShopNo
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        FrontSize=3          //正常小拖
        BehindSize=4
        tractor_type=1
        //打印完成后，将车辆编号替换为流水号+1的流水号
        if(params.intI&&params.veh_Clbh){
            params.veh_Clbh=returnClbh(params.veh_Clbh,FrontSize,BehindSize,tractor_type)
        }
        //回复按钮的操作

        if(params.recover){
            def user=User.get(loginUser.id)
            def codeList=''
            user.organs.each{
                codeList=it.organCode
            }
            NZprintSet NZprintSet = NZprintSet.findByCodeAndUserid('main', codeList)
            if (NZprintSet) {
                if (NZprintSet.max_tractor) {
                    print NZprintSet.max_tractor
                    params.veh_Clbh = returnClbh(NZprintSet.max_tractor, FrontSize, BehindSize, tractor_type)
                }
            } else {
                    return tractor_S(params)
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
                    end_int++
                    String end_str=''+end_int;   //目前值
                    for(int i=end_str.length();i<BehindSize;i++){
                        end_str="0"+end_str
                    }
                    veh_Clbh= start+  end_str;
                }else if (params.direction=='-1'){  //向后
                    end_int--
                    String end_str=''+end_int;   //目前值
                    for(int i=end_str.length();i<BehindSize;i++){
                        end_str="0"+end_str
                    }
                    veh_Clbh= start+  end_str;
                }

                def cel = {
                    eq("veh_Clbh",veh_Clbh)
                }
                def results = NZInfo.createCriteria().list(cel)
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
                NZprintSet NZprintSet=NZprintSet.findByCodeAndUserid('main',codeList)
//                def   printSet=null;
                if (!NZprintSet){
                    NZprintSet = new NZprintSet()
                    NZprintSet.code='main'
                    NZprintSet.userid=codeList
                }
                if(results==null||results.size()==0){ //如果没有查到合格证信息
                    NZInfo obj= null
                    if(params.intI){
                        def celBefore = {
                            eq("veh_Clbh",beforeClbh)
                        }
                        def zc = NZInfo.createCriteria().list(celBefore).get(0)
                        obj=new NZInfo()
                        obj.properties=zc.properties
                        obj.veh_Clbh=params.veh_Clbh
                    }else{
                        obj=new NZInfo();
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

                    if (symboMap.containsKey(type)){
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
                        obj = NZInfo.findByVeh_Clbh(params.veh_Clbh)
                        if (obj==null){
                            if(params.intI){
                                def celBefore = {
                                    eq("veh_Clbh",beforeClbh)
                                }
                                def zc = NZInfo.createCriteria().list(celBefore).get(0)
                                obj=new NZInfo()
                                obj.properties=zc.properties
                                obj.veh_Clbh=params.veh_Clbh
                            }else{
                                obj=new NZInfo();
                                obj.veh_Clbh=params.veh_Clbh;
                            }
                        }
                    }
                    render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S',model:[NZInfoInstance:obj,symboMap:symboMap,NZprintSet:NZprintSet,type:type])
                    return
                }else if (results.size()==1){   //递增递减查看、或者按照车辆编号查看
                    NZInfo obj=results.get(0);
                    if(symbolList!=null&&symbolList.size()!=0){
                        for(def symbol in symbolList){
                            symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));        //tractor_T0:T0 或者  tractor_T1:T1
                            symboMap.put('start_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('STARTNUM')); //start_tractor_ T0  或者 start_tractor_ T1
                            symboMap.put('max_'+symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('MAXNUM'));      //   max_tractor_ T0  或者 max_tractor_ T1
                        }
                    }
                    render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_S',model:[NZInfoInstance:obj,symboMap:symboMap,NZprintSet:NZprintSet,type:type])
                    return
                }
            } catch(Exception e){
                flash.message = '初始化车辆信息出错'
                return tractor_S(params)
            }
        }else{
            flash.message = '请输入合格证编号'
            return tractor_S(params)
        }
    }

    /**
     * @Description 中大拖合格证生成页面
     * @CreateTime 2014-07-07
     * @Author zhuwei
     */
    def tractor_MH(){
        flash.clear()
        StringBuilder   veh_Clbh=new StringBuilder('3');
        veh_Clbh.append(DateUtil.getCurrentTime('yyMM'));
        def type=params.type    //取自菜单的配置参数，tractor_TZ中拖，tractor_TD大拖
        def carStorageType=params?.carStorageType    //取自菜单的配置参数，tractor_TZ中拖，tractor_TD大拖
        def processType=params?.processType//processType=0表示正常打印，processType=1表示出口车
        def productDh=type.split('_')
        veh_Clbh.append(productDh[1])
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        NZInfo obj=new NZInfo()
        //人员 组织 车间 三者进行关联
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
        def NZprintSet = NZprintSet.findByCodeAndUserid("main",codeList)
        if (!NZprintSet){
            NZprintSet = new NZprintSet()
            NZprintSet.code="main"
            NZprintSet.userid=codeList
            NZprintSet.save(flush: true)    //如果没有NZ_printSet数据，那么保存
        }
        if (isValid){
            String maxBH='';
            boolean formTop=false;
            if (NZprintSet){
                //若最大值存在，则判断是否跟当前规则相同 若相同则   从最大值开始 若不同则从最小值开始。
                maxBH= NZprintSet.max_tractor
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
                veh_Clbh.append(startNum_Str);  //出厂编号赋上流水号
//                veh_Clbh.append(workShopNo)    //出厂编号赋上车间代号
                obj.properties = ZCInfo.findByVeh_Zchgzbh(veh_Clbh)?.properties
                obj.veh_Clbh=veh_Clbh
                obj.veh_Dph=veh_Clbh//底盘号与车辆编号是一致的
                //取到车间配置的发动机号和合格证编号
                obj.veh_Fdjh=DictionaryValue.findByCode('fdjh_config')?.value1
                obj.veh_Hgzbh=DictionaryValue.findByCode('hgzbh_config')?.value1
            } else{
                String  maxBH_= maxBH.substring(6,11); //取到流水号,从第7位取，取到11位，截取流水码
                int maxBH_INT=Integer.valueOf(maxBH_)  ;
                if(maxBH_INT<maxNum){ //未达最大值
                    String maxBH_Str=''+  (++maxBH_INT);   //目前值
                    //根据长度添加0
                    for(int i=maxBH_Str.length();i<5;i++){
                        maxBH_Str="0"+maxBH_Str
                    }
                    veh_Clbh.append(maxBH_Str);
//                    veh_Clbh.append(workShopNo)  //出厂编号赋上车间符号
                    obj.properties = NZInfo.findByVeh_Clbh(veh_Clbh)?.properties
                    obj.veh_Clbh=veh_Clbh
                    obj.veh_Dph=veh_Clbh//底盘号与车辆编号是一致的
                    //取到车间配置的发动机号和合格证编号
                    obj.veh_Fdjh=DictionaryValue.findByCode('fdjh_config')?.value1
                    obj.veh_Hgzbh=DictionaryValue.findByCode('hgzbh_config')?.value1
                }else{ //已达最大值
                    flash.message = '<font color="red">合格证编号已达最大值</font>'
                }
            }
        }else{
            flash.message = '<font color="red">合格证编号生成错误</font>'
        }

        if (processType=='0'){
            render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_MH',model:[NZInfoInstance:obj,symboMap:symboMap,NZprintSet:NZprintSet,type:type,processType:processType,carStorageType:carStorageType])
        }else if (processType=='1'){
            render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/tractor_MH1',model:[NZInfoInstance:new NZInfo(),symboMap:symboMap,NZprintSet:NZprintSet,type:type,processType:processType,carStorageType:carStorageType])
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
            zcFailList=nzInfoService.uploadNZInfo(m)
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
            def nzinfo=NZInfo.get(params.id)
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

                Map labels = ["veh_Hgzbh":"合格证编号","veh_Clbh":"车辆编号","veh_Fdjh":"发动机号","envirCode":"环保信息代码","veh_Dph":"底盘号","veh_workshopno":"生产车间编号","veh_Jcy":"检查员"
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

        def hgz=NZInfo.findByVeh_Clbh(params.veh_Clbh)
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
            def rePlaceZcinfo=NZInfoReplace.get(params?.replaceNzZcinfoId)
            if(rePlaceZcinfo){
                rePlaceZcinfo.isPrint='1' //更新更换记录
                if(!rePlaceZcinfo.save(flush: true)){
                    flash.message = '合格证更换记录保存失败'
                }
            }
            return render (result as JSON)
        }
        render result
    }
    /**
     *
     * @Description 取得农装公告
     * @author QJ
     * @createTime 2017-07-20
     * @return
     */
    def toNotice() {
        def aa=params
        ZCInfo obj=new ZCInfo(params)
        render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/nzNotice',model:[zcinfoInstance:obj,tabId:params.tabId,turnOff:params.turnOff,carStorageType:params.car_storage_type])
    }

    /**
     *
     * @Description 取得车型
     * @author zhuwei
     * @createTime 2014-08-18
     * @return
     */
    def jsonCx() {
        def cx=NzCx.findAll();
        render(contentType: "text/json") {
            array {
                for (m in cx) {
                    pack(text:m.cx ,value:m.cx)
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
        def  nzzciInfo=NZInfo.createCriteria().list(cel);
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
        def NZzcinfModel=NZInfo.findByVeh_Clbh(params.veh_Clbh)
        def nzZcinfo=NZInfo.findByVeh_Dph(params.veh_Dph)
        if (NZzcinfModel ||nzZcinfo) {  //存在
            render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/nzZcinfoWrite',model:[returnMessage:"合格证信息已存在，请使用车辆编号进行关联更换",opFlag:params?.opFlag])
        }else{ //不存在

            NZzcinfModel=new NZInfo()
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
