package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.login.LoginController
import cn.com.wz.parent.system.role.Role
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.carstorage.CarStorage
import grails.converters.JSON
import org.apache.commons.net.util.Base64;
/**
 * @Description 合格证开发免登录页面给CRM更换的合格证的页面
 * CreateTime 2017-07-19 by zhuwei
 */


class ZCInfoNoAuthController extends BaseController {
    def exportService
    def zcInfoReplaceService
    def zcinfoNoAuthService
    def newDmsSynService
    def index() {
        redirect(action:'zcinfoRoundNoAuth',params:params)
    }
    /**
     *@Description 合格证申请替换，免登录页面开发给CRM【没有登陆验证】
     *@param
     *@return
     *@Auther zhuwei
     *@createTime 2017-07-18
     *@Update 2017-08-09 by zhuwei 增加了用户的base64解码获取CRM的登陆账号
     * @Update 2017-08-10 by zhuwei 将用户的解密放在了保存数据时的action里面
     */
    def zcinfoRoundNoAuth(){
        if(params.username){
//            def username = new String(Base64.decodeBase64(params.username), "utf-8");
            def username = params.username
            println(params.username)
            render (view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoRoundNoAuth',model: [turnOff:params.turnOff,username:username])
        }else{
           println('没有CRM账号，不允许操作')
            redirect(controller:"LoginController" ,action:"doLogin")
        }
    }
    /**
     *@Descriptions 经销商 >>合格证更换申请【没有登陆验证】
     *@Auther liuly
     *@createTime  2013-08-11
     *@Update  与二次更换公用一个关联方法，通过 replaceType=1 标识来区分
     * UpdateTime 2014-12-01
     * UpdateTime 2017-07-18 by zhuwei   更换合格证时，不在限制只按照整车合格证关联车辆信息，直接在屏幕上显示是否是二次更换
     * UpdateTime 2017-07-18 by zhuwei   改为又有整车按照整车合格证关联车辆信息，没有整车按照底盘合格证关联车辆信息
     */
    def zcinfoRondByDpNoAuth(){
        def  veh_Clsbdh  = params.veh_Clsbdh;  ///底盘号信息
        def  replaceType=params?.replaceType
        def turnOff=params?.turnOff
        def username = params.username
        if (!veh_Clsbdh){
                render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoRoundNoAuth',model: [turnOff:turnOff,username: username])
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
                render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoRoundNoAuth',model: [zciInfoModel:zcin,zid:zcin.id,statusWrite:'nowrite',turnOff:turnOff,username: username])

        }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoRoundNoAuth',model: [turnOff:turnOff,username: username])
        }

    }
    /**
     *@Descriptions 经销商 >>合格证更换申请手动填写【没有登陆验证】
     *@createTime  2017-07-19  by zhuwei
     */
    def zcinfoRondWriteNoAuth()
    {
        def aaa=params
        render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoWriteNoAuth',model: [replaceType:params?.replaceType,turnOff:params?.turnOff,username: params?.username])
    }
    /**
     *@Descriptions 提取公告信息【没有登陆验证】
     *@createTime  2017-07-19  by zhuwei
     */
    def toNoticeNoAuth() {
        def aa=params
        ZCInfo obj=new ZCInfo(params)
        render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/noticeNoAuth',model:[zcinfoInstance:obj,veh_Need_Cer:params.veh_Need_Cer,tabId:params.tabId,turnOff:params.turnOff,supplement:params.supplement])
    }
    /**
     *@Descriptions 提取公告信息【没有登陆验证,手动填写页面，因页面时在查询出来的公告页面的js不一致，所以做了两个页面】
     *@createTime  2017-07-19  by zhuwei
     */
    def toNoticeWriteNoAuth() {
        ZCInfo obj=new ZCInfo(params)
        render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/noticeWriteNoAuth',model:[zcinfoInstance:obj,veh_Need_Cer:params.veh_Need_Cer,tabId:params.tabId,turnOff:params.turnOff])
    }
    /**
     *@Description ajax获取列表中的所有公告信息 【没有登陆验证】
     *@Auther mike
     *@createTime
     * @Update 2013-11-02 添加公告类型的选择条件
     * @update 214-08-09  添加公告唯一码的选择条件
     * @upDate 2015_04_07_ 添加更具公告停用状态标识turn_off筛选   zhuwei
     *@Update  2017_02_08 添加更换合格证时，如果时货箱尺寸为空，但是车辆状态信息为QX的数据时，这个整车数据可提取上
     *@Update  2017_02_08 添加更换合格证时，如果要点合格证，除了货箱尺寸全部为空外，还要添加车辆状态信息DP
     */
    def jsonNoticeNoAuth(){
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
     *@Descriptions 经销商 >>手动填写合格证，进行保存【没有权限】
     *@Auther zhuwei
     *@createTime  2013-08-12
     * @Update 2013-10-20 手动添加的合格证信息全部为整车合格证信息，添加车辆状态的值
     * @Update 2013-10-28 添加手动填写合格证时，判断填写的合格证是否存在
     * Update 2017-07-20 by zhuwei
     * @Update 2017-08-10 by zhuwei 方法传入CRM系统账号
     */
    def saveWriteNoAuth(){
        def  aaaa=params
        def carstorageId=params.carstorageId
        def username
        def backUsername
        if(params.username){
            username = new String(Base64.decodeBase64(params.username), "utf-8"); //将解密的用户名存到数据库
            backUsername = params.username//将加密的账号返回页面隐藏预

        }
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
            render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoWriteNoAuth',model:[returnMessage:"合格证信息已存在，请使用VIN进行关联更换",data:result as JSON,replaceType:params?.replaceType,turnOff:params?.turnOff,username:backUsername])
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
//            zciInfoModel.createrId= loginUser.userName
//            将传入的usernambase64解码保存到createId字段
            zciInfoModel.createrId=username
            zciInfoModel.createTime=DateUtil.getCurrentTime()
            /*zciInfoModel.veh_clzzqyxx=zciInfoC.veh_clzzqyxx*/
            zciInfoModel.veh_Bz=zciInfoC.veh_Bz
            zciInfoModel.veh_Clztxx="QX"
            zciInfoModel.web_status='1'
            zciInfoModel.web_isprint='1'


            if (zciInfoModel.save(flush: true)){ //保存成功
                if(params?.replaceType){ //是二次更换 {在免登录页面先不做二次更换}
                    render (view:'/cn/com/wz/vehcert/zcinfo/replaceTwoTime',model: [zciInfoModel:zciInfoModel,zid:zciInfoModel.id,statusWrite:'write',replaceType:params?.replaceType,turnOff:params?.turnOff,username:backUsername])
                }else{ //正常更换
                    render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoRoundNoAuth',model: [zciInfoModel:zciInfoModel,zid:zciInfoModel.id,statusWrite:'write',turnOff:params?.turnOff,username:backUsername])
                }

            }else{
                render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoWriteNoAuth',model: [zciInfoC:zciInfoC,replaceType:params?.replaceType,turnOff:params?.turnOff,username:backUsername])
            }
        }

    }

    /**
     * @Description 合格证更换保存{免登录方法}
     * @author  zhuwei
     * @Update 将汽车合格证的更换申请合并在该方法中
     * @UpdateTime 2017-08-09将更换的数据的username保存成CRM传过来的用户名
     * @updateTime 2018-04-25 更换信息中将底盘的信息保存到更换附加表ReplaceForSupplement
     */
    def zcinfoReplaceSaveNoAuth() {
        def flag = true
        def username
        if(params.username){
            username = new String(Base64.decodeBase64(params.username), "utf-8");
        }
        def msg = ''
        //旧表
        def zciInfoModel
        def cel = {
            eq('id', params.zid)  //关联VIN对应的整车合格证的id
//           eq('veh_Clztxx','QX')   //不在限定之验证整车合格证信息
        }
        zciInfoModel = ZCInfo.createCriteria().list(cel)?.get(0)      //关联VIN对应的整车合格证
        if(zciInfoModel.veh_Type=='0'){
            List vinObjectList = newDmsSynService.findVinOfDistributor(username)
            List vinList =[]
            if(vinObjectList==null||vinObjectList==''){
                return render("请检查登录经销商账户登录名是否为经销商编号")
            }else{
                if(vinObjectList.size()>0){
                    vinObjectList.each{object->
                        vinList.add(object.new_vin)
                    }
                }
                if(!vinList.contains(zciInfoModel.veh_Clsbdh)){
                    return render("您没有权限更换VIN为${zciInfoModel.veh_Clsbdh}的合格证！")
                }
            }
        }
        //更换申请表
        def zcinfoReplace = new ZCInfoReplace()
        if (params?.formal == '1') {
            if (!zciInfoModel) {
                flag = false
            } else {
                def cel1 = {
                    eq('veh_Zchgzbh', zciInfoModel.veh_Zchgzbh)
                    or {
                        eq("veh_coc_status", 0)
                        eq("area_status", 0) //数据库以前的数据该字段的值为
                        eq("product_auth_status", 0)
                    }
                }
                def zr = ZCInfoReplace.createCriteria().list(cel1)
//             def zr=ZCInfoReplace.findAllByVeh_ZchgzbhAndVeh_coc_status(zciInfoModel.veh_Zchgzbh,'0')
                if (zr && zr.size() > 0) {
                    return render("合格证${zciInfoModel.veh_Zchgzbh}已存在未审核的更换记录！")

                }
                if (params?.replaceType == '1') { //replaceType=='1' 是二次更换标识，正常的其他更换该标识为0
                    zcinfoReplace.replaceType = 1
                    zcinfoReplace.area_status = 0      //area_status默认初始值为3，只有在二次更换申请时将其改写为0啊
                    //保存申请者的组织id【取第一个组织id保存】，经销商帐号只能关联到个区域组织
                    User loginUser=User.findByUserName(username)
                    def organList = loginUser?.organs?.id
                    zcinfoReplace.createrOrgan = organList[0]
                    zcinfoReplace.veh_coc_status = 3 //营销公司审核审核状态不使用默认值0，而是将其置3，在区域经理审核时再将其重置为0
                }
                zcinfoReplace.veh_Type = zciInfoModel.veh_Type
                zcinfoReplace.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                zcinfoReplace.veh_Zchgzbh = zciInfoModel.veh_Zchgzbh
                zcinfoReplace.veh_Dphgzbh = zciInfoModel.veh_Dphgzbh
                zcinfoReplace.veh_Fzrq = zciInfoModel.veh_Fzrq
                zcinfoReplace.veh_Clzzqymc = zciInfoModel.veh_Clzzqymc
                zcinfoReplace.veh_Qyid = zciInfoModel.veh_Qyid
                zcinfoReplace.veh_Clfl = zciInfoModel.veh_Clfl
                zcinfoReplace.veh_Clmc = zciInfoModel.veh_Clmc
                zcinfoReplace.veh_Clpp = zciInfoModel.veh_Clpp
                zcinfoReplace.veh_Clxh = zciInfoModel.veh_Clxh
                zcinfoReplace.veh_Csys = zciInfoModel.veh_Csys
                zcinfoReplace.veh_Dpxh = zciInfoModel.veh_Dpxh
                zcinfoReplace.veh_Dpid = zciInfoModel.veh_Dpid
                zcinfoReplace.veh_Clsbdh = zciInfoModel.veh_Clsbdh    //车辆识别代号
                zcinfoReplace.veh_Cjh = zciInfoModel.veh_Cjh
                zcinfoReplace.veh_Fdjh = zciInfoModel.veh_Fdjh        //发动机号

                zcinfoReplace.veh_Fdjxh = zciInfoModel.veh_Fdjxh     //发动机型号
                zcinfoReplace.veh_Rlzl = zciInfoModel.veh_Rlzl     //燃料种类
                zcinfoReplace.veh_Pl = zciInfoModel.veh_Pl       //排量
                zcinfoReplace.veh_Gl = zciInfoModel.veh_Gl       //功率
                zcinfoReplace.veh_zdjgl = zciInfoModel.veh_zdjgl    //最大净功率
                zcinfoReplace.veh_Zxxs = zciInfoModel.veh_Zxxs     //转向形式
                zcinfoReplace.veh_Qlj = zciInfoModel.veh_Qlj      //前轮距
                zcinfoReplace.veh_Hlj = zciInfoModel.veh_Hlj      //后轮距
                zcinfoReplace.veh_Lts = zciInfoModel.veh_Lts      //轮胎数
                zcinfoReplace.veh_Ltgg = zciInfoModel.veh_Ltgg    //轮胎规格
                zcinfoReplace.veh_Gbthps = zciInfoModel.veh_Gbthps   //钢板弹簧片数
                zcinfoReplace.veh_Zj = zciInfoModel.veh_Zj      //轴距
                zcinfoReplace.veh_Zh = zciInfoModel.veh_Zh       //轴荷
                zcinfoReplace.veh_Zs = zciInfoModel.veh_Zs       //轴数
                zcinfoReplace.veh_Wkc = zciInfoModel.veh_Wkc      //外廓长
                zcinfoReplace.veh_Wkk = zciInfoModel.veh_Wkk       //外廓宽
                zcinfoReplace.veh_Wkg = zciInfoModel.veh_Wkg       //外廓高
                zcinfoReplace.veh_Hxnbc = zciInfoModel.veh_Hxnbc     //货箱内部长
                zcinfoReplace.veh_Hxnbk = zciInfoModel.veh_Hxnbk     //货箱内部宽
                zcinfoReplace.veh_Hxnbg = zciInfoModel.veh_Hxnbg     //货箱内部高
                zcinfoReplace.veh_Zzl = zciInfoModel.veh_Zzl       //总质量
                zcinfoReplace.veh_Edzzl = zciInfoModel.veh_Edzzl     //额定载质量
                zcinfoReplace.veh_Zbzl = zciInfoModel.veh_Zbzl      //整备质量
                zcinfoReplace.veh_Zzllyxs = zciInfoModel.veh_Zzllyxs   //载质量利用系数
                zcinfoReplace.veh_Zqyzzl = zciInfoModel.veh_Zqyzzl   //准牵引总质量
                zcinfoReplace.veh_Edzk = zciInfoModel.veh_Edzk     //额定载客
                zcinfoReplace.veh_Bgcazzdyxzzl = zciInfoModel.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
                zcinfoReplace.veh_Jsszcrs = zciInfoModel.veh_Jsszcrs  //驾驶室准乘人数
                zcinfoReplace.veh_Qzdfs = zciInfoModel.veh_Qzdfs       //前制动方式
                zcinfoReplace.veh_Hzdfs = zciInfoModel.veh_Hzdfs    //后制动方式
                zcinfoReplace.veh_Qzdczfs = zciInfoModel.veh_Qzdczfs     //前制动操作方式
                zcinfoReplace.veh_Cpggh = zciInfoModel.veh_Cpggh    //产品公告号
                zcinfoReplace.veh_Ggsxrq = zciInfoModel.veh_Ggsxrq   //公告生效日期
                zcinfoReplace.veh_Zzbh = params.veh_Zzbh       //纸张编号
                zcinfoReplace.veh_Dywym = zciInfoModel.veh_Dywym   //打印唯一码
                zcinfoReplace.veh_Zgcs = zciInfoModel.veh_Zgcs     //最高车速
                zcinfoReplace.veh_Clzzrq = zciInfoModel.veh_Clzzrq   //车辆制造日期
                zcinfoReplace.veh_Bz = zciInfoModel.veh_Bz       //备注
                zcinfoReplace.veh_Qybz = zciInfoModel.veh_Qybz     //企业标准
                zcinfoReplace.veh_Cpscdz = zciInfoModel.veh_Cpscdz   //产品生产地址
                zcinfoReplace.veh_Qyqtxx = zciInfoModel.veh_Qyqtxx   //企业其它信息
                zcinfoReplace.veh_Pfbz = zciInfoModel.veh_Pfbz     //排放标准
                zcinfoReplace.veh_Clztxx = zciInfoModel.veh_Clztxx   //车辆状态信息
                zcinfoReplace.veh_Jss = zciInfoModel.veh_Jss     //驾驶室
                zcinfoReplace.veh_Jsslx = zciInfoModel.veh_Jsslx   //驾驶室类型
                zcinfoReplace.veh_Zchgzbh_0 = zciInfoModel.veh_Zchgzbh_0   //完整合格证编号
                zcinfoReplace.used = zciInfoModel.used
                zcinfoReplace.used2 = zciInfoModel.used2
                zcinfoReplace.upload = zciInfoModel.upload  //
                // zcinfoReplace. vercode  =zciInfoModel.vercode //二维码
                zcinfoReplace.veh_Yh = zciInfoModel.veh_Yh   //油耗
                zcinfoReplace.veh_VinFourBit = zciInfoModel.veh_VinFourBit  //vin第四位
                zcinfoReplace.veh_Ggpc = zciInfoModel.veh_Ggpc         //公告批次
                zcinfoReplace.veh_createTime = zciInfoModel.createTime  //创建时间
                zcinfoReplace.veh_createrId = zciInfoModel.createrId  //创建人id
                zcinfoReplace.veh_pzxlh = zciInfoModel.veh_pzxlh                   //配置序列号
                zcinfoReplace.upload_Path = zciInfoModel.upload_Path   //合格证上传相对路径
                zcinfoReplace.veh_clzzqyxx = zciInfoModel.veh_clzzqyxx //车辆制造企业信息
                zcinfoReplace.SAP_No = zciInfoModel.SAP_No //SAP序列号，更换记录里增加SAP序列号

                zcinfoReplace.veh_VinFourBit_R = zciInfoModel.veh_VinFourBit
                zcinfoReplace.veh_Type_R = zciInfoModel.veh_Type
                zcinfoReplace.veh_Cpggh_R = zciInfoModel.veh_Cpggh
                zcinfoReplace.veh_zdjgl_R = zciInfoModel.veh_zdjgl
                zcinfoReplace.veh_Clztxx_R = zciInfoModel.veh_Clztxx   //车辆状态信息
                zcinfoReplace.veh_Qyqtxx_R = zciInfoModel.veh_Qyqtxx   //企业其它信息
                zcinfoReplace.veh_Dpid_R = zciInfoModel.veh_Dpid
                zcinfoReplace.veh_Jsslx_R = zciInfoModel.veh_Jsslx
                zcinfoReplace.veh_pzxlh_R = zciInfoModel.veh_pzxlh
                zcinfoReplace.veh_Ggsxrq_R = zciInfoModel.veh_Ggsxrq
                zcinfoReplace.veh_Cpscdz_R = zciInfoModel.veh_Cpscdz
                zcinfoReplace.veh_Qybz_R = zciInfoModel.veh_Qybz
                zcinfoReplace.veh_Hzdfs_R = zciInfoModel.veh_Hzdfs
                zcinfoReplace.veh_Qyid_R = zciInfoModel.veh_Qyid
                zcinfoReplace.veh_Xnhgzbh_R = zciInfoModel.web_virtualcode
                zcinfoReplace.veh_Clzzqymc_R = zciInfoModel.veh_Clzzqymc
                zcinfoReplace.veh_Clpp_R = zciInfoModel.veh_Clpp
                zcinfoReplace.veh_Clmc_R = zciInfoModel.veh_Clmc
                zcinfoReplace.veh_Clxh_R = zciInfoModel.veh_Clxh
                zcinfoReplace.veh_Csys_R = zciInfoModel.veh_Csys  //车身颜色取修改后的车身颜色
                zcinfoReplace.veh_Clfl_R = zciInfoModel.veh_Clfl
                zcinfoReplace.veh_Dpxh_R = zciInfoModel.veh_Dpxh
                zcinfoReplace.veh_Fdjxh_R = zciInfoModel.veh_Fdjxh
                zcinfoReplace.veh_Zgcs_R = zciInfoModel.veh_Zgcs
                zcinfoReplace.veh_Fdjh_R = zciInfoModel.veh_Fdjh
                zcinfoReplace.veh_Rlzl_R = zciInfoModel.veh_Rlzl
                zcinfoReplace.veh_Pl_R = zciInfoModel.veh_Pl
                zcinfoReplace.veh_Gl_R = zciInfoModel.veh_Gl
                zcinfoReplace.veh_Lts_R = zciInfoModel.veh_Lts
                zcinfoReplace.veh_Pfbz_R = zciInfoModel.veh_Pfbz

                zcinfoReplace.veh_Yh_R = zciInfoModel.veh_Yh
                zcinfoReplace.veh_Wkc_R = zciInfoModel.veh_Wkc
                zcinfoReplace.veh_Wkk_R = zciInfoModel.veh_Wkk
                zcinfoReplace.veh_Wkg_R = zciInfoModel.veh_Wkg
                zcinfoReplace.veh_Hxnbc_R = zciInfoModel.veh_Hxnbc
                zcinfoReplace.veh_Hxnbk_R = zciInfoModel.veh_Hxnbk
                zcinfoReplace.veh_Hxnbg_R = zciInfoModel.veh_Hxnbg
                zcinfoReplace.veh_Ltgg_R = zciInfoModel.veh_Ltgg
                zcinfoReplace.veh_Gbthps_R = zciInfoModel.veh_Gbthps

                zcinfoReplace.veh_Qlj_R = zciInfoModel.veh_Qlj
                zcinfoReplace.veh_Hlj_R = zciInfoModel.veh_Hlj
                zcinfoReplace.veh_Zj_R = zciInfoModel.veh_Zj
                zcinfoReplace.veh_Zh_R = zciInfoModel.veh_Zh
                zcinfoReplace.veh_Zs_R = zciInfoModel.veh_Zs
                zcinfoReplace.veh_Zxxs_R = zciInfoModel.veh_Zxxs
                zcinfoReplace.veh_Zzl_R = zciInfoModel.veh_Zzl
                zcinfoReplace.veh_Zbzl_R = zciInfoModel.veh_Zbzl
                zcinfoReplace.veh_Edzzl_R = zciInfoModel.veh_Edzzl
                zcinfoReplace.veh_Ggpc_R = zciInfoModel.veh_Ggpc
                zcinfoReplace.veh_Zzllyxs_R = zciInfoModel.veh_Zzllyxs
                zcinfoReplace.veh_Zqyzzl_R = zciInfoModel.veh_Zqyzzl
                zcinfoReplace.veh_Bgcazzdyxzzl_R = zciInfoModel.veh_Bgcazzdyxzzl
                zcinfoReplace.veh_Jsszcrs_R = zciInfoModel.veh_Jsszcrs
                zcinfoReplace.veh_Edzk_R = zciInfoModel.veh_Edzk
                zcinfoReplace.veh_Zgcs_R = zciInfoModel.veh_Zgcs
                zcinfoReplace.veh_Bz_R = zciInfoModel.veh_Bz
                if (zcinfoReplace.veh_Clztxx_R == 'QX') {
                    zcinfoReplace.veh_Need_Cer = 0
                } else {
                    zcinfoReplace.veh_Need_Cer = 1
                }
                zcinfoReplace.remark = params.remark
                zcinfoReplace.salesmanname = params.salesmanname
                zcinfoReplace.salesmantel = params.salesmantel
                User loginUser=User.findByUserName(username)
                zcinfoReplace.createrId = loginUser.id
                zcinfoReplace.createTime = DateUtil.getCurrentTime()
                if (zcinfoReplace.save(flush: true)) {
                    msg = '提交成功'
                } else {
                    msg = '提交失败'
                }
            }
            if (flag == false) {
                msg = '要更换的合格证不存在，请重新选择！'
            }
            render msg
        } else {
            //新表
            def carstorageId = params.newId
            def carStorage = CarStorage.get(carstorageId)
            if (params.veh_Need_Cer == '2') {
                def replaceForSupplement = new ReplaceForSupplement()
                def dpCarstorageId = params.newDpId
                def dpCarstorage = CarStorage.get(dpCarstorageId)
                if (!zciInfoModel || !carStorage || !dpCarstorage) {
                    flag = false
                } else {
                    //判断合格证更换记录是否已存在，只要是营销公司审核状态 veh_coc_status=0 【或者】 area_status=0就不能再次填写变更申请
                    def cel1 = {
                        eq('veh_Zchgzbh', zciInfoModel.veh_Zchgzbh)
                        or {
                            eq("veh_coc_status", 0)
                            eq("area_status", 0) //数据库以前的数据该字段的值为
                            eq("product_auth_status", 0)
                        }
                    }
                    def zr = ZCInfoReplace.createCriteria().list(cel1)
//             def zr=ZCInfoReplace.findAllByVeh_ZchgzbhAndVeh_coc_status(zciInfoModel.veh_Zchgzbh,'0')
                    if (zr && zr.size() > 0) {
                        return render("合格证${zciInfoModel.veh_Zchgzbh}已存在未审核的更换记录！")

                    }
                    if (params?.replaceType == '1') { //replaceType=='1' 是二次更换标识，正常的其他更换该标识为0
                        zcinfoReplace.replaceType = 1
                        replaceForSupplement.replaceType = 1
                        zcinfoReplace.area_status = 0      //area_status默认初始值为3，只有在二次更换申请时将其改写为0啊
                        replaceForSupplement.area_status = 0
                        //保存申请者的组织id【取第一个组织id保存】，经销商帐号只能关联到个区域组织
                        User loginUser=User.findByUserName(username)
                        def organList = loginUser?.organs?.id
                        zcinfoReplace.createrOrgan = organList[0]
                        replaceForSupplement.createrOrgan = organList[0]
                        zcinfoReplace.veh_coc_status = 3 //营销公司审核审核状态不使用默认值0，而是将其置3，在区域经理审核时再将其重置为0
                        replaceForSupplement.veh_coc_status = 3
                    }
                    zcinfoReplace.veh_Type = zciInfoModel.veh_Type
                    zcinfoReplace.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                    zcinfoReplace.veh_Zchgzbh = zciInfoModel.veh_Zchgzbh
                    zcinfoReplace.veh_Dphgzbh = zciInfoModel.veh_Dphgzbh
                    zcinfoReplace.veh_Fzrq = zciInfoModel.veh_Fzrq
                    zcinfoReplace.veh_Clzzqymc = zciInfoModel.veh_Clzzqymc
                    zcinfoReplace.veh_Qyid = zciInfoModel.veh_Qyid
                    zcinfoReplace.veh_Clfl = zciInfoModel.veh_Clfl
                    zcinfoReplace.veh_Clmc = zciInfoModel.veh_Clmc
                    zcinfoReplace.veh_Clpp = zciInfoModel.veh_Clpp
                    zcinfoReplace.veh_Clxh = zciInfoModel.veh_Clxh
                    zcinfoReplace.veh_Csys = zciInfoModel.veh_Csys
                    zcinfoReplace.veh_Dpxh = zciInfoModel.veh_Dpxh
                    zcinfoReplace.veh_Dpid = zciInfoModel.veh_Dpid
                    zcinfoReplace.veh_Clsbdh = params.veh_Clsbdh    //车辆识别代号
                    zcinfoReplace.veh_Cjh = params.veh_Clsbdh
                    zcinfoReplace.veh_Fdjh = zciInfoModel.veh_Fdjh        //发动机号

                    zcinfoReplace.veh_Fdjxh = zciInfoModel.veh_Fdjxh     //发动机型号
                    zcinfoReplace.veh_Rlzl = zciInfoModel.veh_Rlzl     //燃料种类
                    zcinfoReplace.veh_Pl = zciInfoModel.veh_Pl       //排量
                    zcinfoReplace.veh_Gl = zciInfoModel.veh_Gl       //功率
                    zcinfoReplace.veh_zdjgl = zciInfoModel.veh_zdjgl    //最大净功率
                    zcinfoReplace.veh_Zxxs = zciInfoModel.veh_Zxxs     //转向形式
                    zcinfoReplace.veh_Qlj = zciInfoModel.veh_Qlj      //前轮距
                    zcinfoReplace.veh_Hlj = zciInfoModel.veh_Hlj      //后轮距
                    zcinfoReplace.veh_Lts = zciInfoModel.veh_Lts      //轮胎数
                    zcinfoReplace.veh_Ltgg = zciInfoModel.veh_Ltgg    //轮胎规格
                    zcinfoReplace.veh_Gbthps = zciInfoModel.veh_Gbthps   //钢板弹簧片数
                    zcinfoReplace.veh_Zj = zciInfoModel.veh_Zj      //轴距
                    zcinfoReplace.veh_Zh = zciInfoModel.veh_Zh       //轴荷
                    zcinfoReplace.veh_Zs = zciInfoModel.veh_Zs       //轴数
                    zcinfoReplace.veh_Wkc = zciInfoModel.veh_Wkc      //外廓长
                    zcinfoReplace.veh_Wkk = zciInfoModel.veh_Wkk       //外廓宽
                    zcinfoReplace.veh_Wkg = zciInfoModel.veh_Wkg       //外廓高
                    zcinfoReplace.veh_Hxnbc = zciInfoModel.veh_Hxnbc     //货箱内部长
                    zcinfoReplace.veh_Hxnbk = zciInfoModel.veh_Hxnbk     //货箱内部宽
                    zcinfoReplace.veh_Hxnbg = zciInfoModel.veh_Hxnbg     //货箱内部高
                    zcinfoReplace.veh_Zzl = zciInfoModel.veh_Zzl       //总质量
                    zcinfoReplace.veh_Edzzl = zciInfoModel.veh_Edzzl     //额定载质量
                    zcinfoReplace.veh_Zbzl = zciInfoModel.veh_Zbzl      //整备质量
                    zcinfoReplace.veh_Zzllyxs = zciInfoModel.veh_Zzllyxs   //载质量利用系数
                    zcinfoReplace.veh_Zqyzzl = zciInfoModel.veh_Zqyzzl   //准牵引总质量
                    zcinfoReplace.veh_Edzk = zciInfoModel.veh_Edzk     //额定载客
                    zcinfoReplace.veh_Bgcazzdyxzzl = zciInfoModel.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
                    zcinfoReplace.veh_Jsszcrs = zciInfoModel.veh_Jsszcrs  //驾驶室准乘人数
                    zcinfoReplace.veh_Qzdfs = zciInfoModel.veh_Qzdfs       //前制动方式
                    zcinfoReplace.veh_Hzdfs = zciInfoModel.veh_Hzdfs    //后制动方式
                    zcinfoReplace.veh_Qzdczfs = zciInfoModel.veh_Qzdczfs     //前制动操作方式
                    zcinfoReplace.veh_Cpggh = zciInfoModel.veh_Cpggh    //产品公告号
                    zcinfoReplace.veh_Ggsxrq = zciInfoModel.veh_Ggsxrq   //公告生效日期
                    zcinfoReplace.veh_Zzbh = params.veh_Zzbh       //纸张编号
                    zcinfoReplace.veh_Dywym = zciInfoModel.veh_Dywym   //打印唯一码
                    zcinfoReplace.veh_Zgcs = zciInfoModel.veh_Zgcs     //最高车速
                    zcinfoReplace.veh_Clzzrq = zciInfoModel.veh_Clzzrq   //车辆制造日期
                    zcinfoReplace.veh_Bz = zciInfoModel.veh_Bz       //备注
                    zcinfoReplace.veh_Qybz = zciInfoModel.veh_Qybz     //企业标准
                    zcinfoReplace.veh_Cpscdz = zciInfoModel.veh_Cpscdz   //产品生产地址
                    zcinfoReplace.veh_Qyqtxx = zciInfoModel.veh_Qyqtxx   //企业其它信息
                    zcinfoReplace.veh_Pfbz = zciInfoModel.veh_Pfbz     //排放标准
                    zcinfoReplace.veh_Clztxx = zciInfoModel.veh_Clztxx   //车辆状态信息
                    zcinfoReplace.veh_Jss = zciInfoModel.veh_Jss     //驾驶室
                    zcinfoReplace.veh_Jsslx = zciInfoModel.veh_Jsslx   //驾驶室类型
                    zcinfoReplace.veh_Zchgzbh_0 = zciInfoModel.veh_Zchgzbh_0   //完整合格证编号
                    zcinfoReplace.used = zciInfoModel.used
                    zcinfoReplace.used2 = zciInfoModel.used2
                    zcinfoReplace.upload = zciInfoModel.upload  //
                    // zcinfoReplace. vercode  =zciInfoModel.vercode //二维码
                    zcinfoReplace.veh_Yh = zciInfoModel.veh_Yh   //油耗
                    zcinfoReplace.veh_VinFourBit = zciInfoModel.veh_VinFourBit  //vin第四位
                    zcinfoReplace.veh_Ggpc = zciInfoModel.veh_Ggpc         //公告批次
                    zcinfoReplace.veh_createTime = zciInfoModel.createTime  //创建时间
                    zcinfoReplace.veh_createrId = zciInfoModel.createrId  //创建人id
                    zcinfoReplace.veh_pzxlh = zciInfoModel.veh_pzxlh                   //配置序列号
                    zcinfoReplace.upload_Path = zciInfoModel.upload_Path   //合格证上传相对路径
                    zcinfoReplace.veh_clzzqyxx = zciInfoModel.veh_clzzqyxx //车辆制造企业信息
                    zcinfoReplace.SAP_No = zciInfoModel.SAP_No //SAP序列号，更换记录里增加SAP序列号

                    zcinfoReplace.veh_VinFourBit_R = carStorage.veh_VinFourBit
                    zcinfoReplace.veh_Type_R = carStorage.carStorageType
                    zcinfoReplace.veh_Cpggh_R = carStorage.veh_Cpggh
                    zcinfoReplace.veh_zdjgl_R = carStorage.veh_zdjgl
                    if (params.veh_Need_Cer == '0' || params.veh_Need_Cer == '2') {
                        zcinfoReplace.veh_Clztxx_R = 'QX'
                    } else {
                        zcinfoReplace.veh_Clztxx_R = 'DP'
                    }

                    zcinfoReplace.veh_Qyqtxx_R = carStorage.veh_Qyqtxx   //企业其它信息
                    zcinfoReplace.veh_Dpid_R = carStorage.veh_Dpid
                    zcinfoReplace.veh_Jsslx_R = carStorage.veh_Jsslx
                    zcinfoReplace.veh_pzxlh_R = carStorage.veh_pzxlh
                    zcinfoReplace.veh_Ggsxrq_R = carStorage.veh_Ggsxrq
                    zcinfoReplace.veh_Cpscdz_R = carStorage.veh_Cpscdz
                    zcinfoReplace.veh_Qybz_R = carStorage.veh_Qybz
                    zcinfoReplace.veh_Hzdfs_R = carStorage.veh_Hzdfs
                    zcinfoReplace.veh_Qyid_R = carStorage.veh_Qyid
                    zcinfoReplace.veh_Xnhgzbh_R = zciInfoModel.web_virtualcode
                    zcinfoReplace.veh_Clzzqymc_R = carStorage.veh_Clzzqymc
                    zcinfoReplace.veh_Clpp_R = carStorage.veh_Clpp
                    zcinfoReplace.veh_Clmc_R = carStorage.veh_Clmc
                    zcinfoReplace.veh_Clxh_R = carStorage.veh_Clxh
                    zcinfoReplace.veh_Csys_R = params.veh_Csys_R  //车身颜色取修改后的车身颜色
                    zcinfoReplace.veh_Clfl_R = carStorage.veh_Clfl
                    zcinfoReplace.veh_Dpxh_R = carStorage.veh_Dpxh
                    zcinfoReplace.veh_Fdjxh_R = carStorage.veh_Fdjxh
                    zcinfoReplace.veh_Zgcs_R = carStorage.veh_Zgcs
                    zcinfoReplace.veh_Fdjh_R = params.veh_Fdjh
                    zcinfoReplace.veh_Rlzl_R = carStorage.veh_Rlzl
                    zcinfoReplace.veh_Pl_R = carStorage.veh_Pl
                    zcinfoReplace.veh_Gl_R = carStorage.veh_Gl
                    zcinfoReplace.veh_Lts_R = carStorage.veh_Lts
                    zcinfoReplace.veh_Pfbz_R = carStorage.veh_Pfbz

                    zcinfoReplace.veh_Yh_R = carStorage.veh_Yh
                    zcinfoReplace.veh_Wkc_R = carStorage.veh_Wkc
                    zcinfoReplace.veh_Wkk_R = carStorage.veh_Wkk
                    zcinfoReplace.veh_Wkg_R = carStorage.veh_Wkg
                    zcinfoReplace.veh_Hxnbc_R = carStorage.veh_Hxnbc
                    zcinfoReplace.veh_Hxnbk_R = carStorage.veh_Hxnbk
                    zcinfoReplace.veh_Hxnbg_R = carStorage.veh_Hxnbg
                    zcinfoReplace.veh_Ltgg_R = carStorage.veh_Ltgg
                    zcinfoReplace.veh_Gbthps_R = carStorage.veh_Gbthps

                    zcinfoReplace.veh_Qlj_R = carStorage.veh_Qlj
                    zcinfoReplace.veh_Hlj_R = carStorage.veh_Hlj
                    zcinfoReplace.veh_Zj_R = carStorage.veh_Zj
                    zcinfoReplace.veh_Zh_R = carStorage.veh_Zh
                    zcinfoReplace.veh_Zs_R = carStorage.veh_Zs
                    zcinfoReplace.veh_Zxxs_R = carStorage.veh_Zxxs
                    zcinfoReplace.veh_Zzl_R = carStorage.veh_Zzl
                    zcinfoReplace.veh_Zbzl_R = carStorage.veh_Zbzl
                    zcinfoReplace.veh_Edzzl_R = carStorage.veh_Edzzl
                    zcinfoReplace.veh_Ggpc_R = carStorage.veh_Ggpc
                    zcinfoReplace.veh_Zzllyxs_R = carStorage.veh_Zzllyxs
                    zcinfoReplace.veh_Zqyzzl_R = carStorage.veh_Zqyzzl
                    zcinfoReplace.veh_Bgcazzdyxzzl_R = carStorage.veh_Bgcazzdyxzzl
                    zcinfoReplace.veh_Jsszcrs_R = carStorage.veh_Jsszcrs
                    zcinfoReplace.veh_Edzk_R = carStorage.veh_Edzk
                    zcinfoReplace.veh_Zgcs_R = carStorage.veh_Zgcs
                    zcinfoReplace.veh_Bz_R = carStorage.veh_Bz

                    zcinfoReplace.veh_Need_Cer = Integer.parseInt(params.veh_Need_Cer)
                    zcinfoReplace.remark = params.remark
                    zcinfoReplace.salesmanname = params.salesmanname
                    zcinfoReplace.salesmantel = params.salesmantel
                    User loginUser=User.findByUserName(username)
                    zcinfoReplace.createrId = loginUser.id
                    zcinfoReplace.createTime = DateUtil.getCurrentTime()

                    //整车和底盘都要的合格证向更换附加表中写入底盘公告数据
                    replaceForSupplement.veh_Type = zciInfoModel.veh_Type
                    replaceForSupplement.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                    replaceForSupplement.veh_Zchgzbh = zciInfoModel.veh_Zchgzbh
                    replaceForSupplement.veh_Dphgzbh = zciInfoModel.veh_Dphgzbh
                    replaceForSupplement.veh_Fzrq = zciInfoModel.veh_Fzrq
                    replaceForSupplement.veh_Clzzqymc = zciInfoModel.veh_Clzzqymc
                    replaceForSupplement.veh_Qyid = zciInfoModel.veh_Qyid
                    replaceForSupplement.veh_Clfl = zciInfoModel.veh_Clfl
                    replaceForSupplement.veh_Clmc = zciInfoModel.veh_Clmc
                    replaceForSupplement.veh_Clpp = zciInfoModel.veh_Clpp
                    replaceForSupplement.veh_Clxh = zciInfoModel.veh_Clxh
                    replaceForSupplement.veh_Csys = zciInfoModel.veh_Csys
                    replaceForSupplement.veh_Dpxh = zciInfoModel.veh_Dpxh
                    replaceForSupplement.veh_Dpid = zciInfoModel.veh_Dpid
                    replaceForSupplement.veh_Clsbdh = params.veh_Clsbdh    //车辆识别代号
                    replaceForSupplement.veh_Cjh = params.veh_Clsbdh
                    replaceForSupplement.veh_Fdjh = zciInfoModel.veh_Fdjh        //发动机号

                    replaceForSupplement.veh_Fdjxh = zciInfoModel.veh_Fdjxh     //发动机型号
                    replaceForSupplement.veh_Rlzl = zciInfoModel.veh_Rlzl     //燃料种类
                    replaceForSupplement.veh_Pl = zciInfoModel.veh_Pl       //排量
                    replaceForSupplement.veh_Gl = zciInfoModel.veh_Gl       //功率
                    replaceForSupplement.veh_zdjgl = zciInfoModel.veh_zdjgl    //最大净功率
                    replaceForSupplement.veh_Zxxs = zciInfoModel.veh_Zxxs     //转向形式
                    replaceForSupplement.veh_Qlj = zciInfoModel.veh_Qlj      //前轮距
                    replaceForSupplement.veh_Hlj = zciInfoModel.veh_Hlj      //后轮距
                    replaceForSupplement.veh_Lts = zciInfoModel.veh_Lts      //轮胎数
                    replaceForSupplement.veh_Ltgg = zciInfoModel.veh_Ltgg    //轮胎规格
                    replaceForSupplement.veh_Gbthps = zciInfoModel.veh_Gbthps   //钢板弹簧片数
                    replaceForSupplement.veh_Zj = zciInfoModel.veh_Zj      //轴距
                    replaceForSupplement.veh_Zh = zciInfoModel.veh_Zh       //轴荷
                    replaceForSupplement.veh_Zs = zciInfoModel.veh_Zs       //轴数
                    replaceForSupplement.veh_Wkc = zciInfoModel.veh_Wkc      //外廓长
                    replaceForSupplement.veh_Wkk = zciInfoModel.veh_Wkk       //外廓宽
                    replaceForSupplement.veh_Wkg = zciInfoModel.veh_Wkg       //外廓高
                    replaceForSupplement.veh_Hxnbc = zciInfoModel.veh_Hxnbc     //货箱内部长
                    replaceForSupplement.veh_Hxnbk = zciInfoModel.veh_Hxnbk     //货箱内部宽
                    replaceForSupplement.veh_Hxnbg = zciInfoModel.veh_Hxnbg     //货箱内部高
                    replaceForSupplement.veh_Zzl = zciInfoModel.veh_Zzl       //总质量
                    replaceForSupplement.veh_Edzzl = zciInfoModel.veh_Edzzl     //额定载质量
                    replaceForSupplement.veh_Zbzl = zciInfoModel.veh_Zbzl      //整备质量
                    replaceForSupplement.veh_Zzllyxs = zciInfoModel.veh_Zzllyxs   //载质量利用系数
                    replaceForSupplement.veh_Zqyzzl = zciInfoModel.veh_Zqyzzl   //准牵引总质量
                    replaceForSupplement.veh_Edzk = zciInfoModel.veh_Edzk     //额定载客
                    replaceForSupplement.veh_Bgcazzdyxzzl = zciInfoModel.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
                    replaceForSupplement.veh_Jsszcrs = zciInfoModel.veh_Jsszcrs  //驾驶室准乘人数
                    replaceForSupplement.veh_Qzdfs = zciInfoModel.veh_Qzdfs       //前制动方式
                    replaceForSupplement.veh_Hzdfs = zciInfoModel.veh_Hzdfs    //后制动方式
                    replaceForSupplement.veh_Qzdczfs = zciInfoModel.veh_Qzdczfs     //前制动操作方式
                    replaceForSupplement.veh_Cpggh = zciInfoModel.veh_Cpggh    //产品公告号
                    replaceForSupplement.veh_Ggsxrq = zciInfoModel.veh_Ggsxrq   //公告生效日期
                    replaceForSupplement.veh_Zzbh = params.veh_Zzbh       //纸张编号
                    replaceForSupplement.veh_Dywym = zciInfoModel.veh_Dywym   //打印唯一码
                    replaceForSupplement.veh_Zgcs = zciInfoModel.veh_Zgcs     //最高车速
                    replaceForSupplement.veh_Clzzrq = zciInfoModel.veh_Clzzrq   //车辆制造日期
                    replaceForSupplement.veh_Bz = zciInfoModel.veh_Bz       //备注
                    replaceForSupplement.veh_Qybz = zciInfoModel.veh_Qybz     //企业标准
                    replaceForSupplement.veh_Cpscdz = zciInfoModel.veh_Cpscdz   //产品生产地址
                    replaceForSupplement.veh_Qyqtxx = zciInfoModel.veh_Qyqtxx   //企业其它信息
                    replaceForSupplement.veh_Pfbz = zciInfoModel.veh_Pfbz     //排放标准
                    replaceForSupplement.veh_Clztxx = zciInfoModel.veh_Clztxx   //车辆状态信息
                    replaceForSupplement.veh_Jss = zciInfoModel.veh_Jss     //驾驶室
                    replaceForSupplement.veh_Jsslx = zciInfoModel.veh_Jsslx   //驾驶室类型
                    replaceForSupplement.veh_Zchgzbh_0 = zciInfoModel.veh_Zchgzbh_0   //完整合格证编号
                    replaceForSupplement.used = zciInfoModel.used
                    replaceForSupplement.used2 = zciInfoModel.used2
                    replaceForSupplement.upload = zciInfoModel.upload  //
                    // replaceForSupplement. vercode  =zciInfoModel.vercode //二维码
                    replaceForSupplement.veh_Yh = zciInfoModel.veh_Yh   //油耗
                    replaceForSupplement.veh_VinFourBit = zciInfoModel.veh_VinFourBit  //vin第四位
                    replaceForSupplement.veh_Ggpc = zciInfoModel.veh_Ggpc         //公告批次
                    replaceForSupplement.veh_createTime = zciInfoModel.createTime  //创建时间
                    replaceForSupplement.veh_createrId = zciInfoModel.createrId  //创建人id
                    replaceForSupplement.veh_pzxlh = zciInfoModel.veh_pzxlh                   //配置序列号
                    replaceForSupplement.upload_Path = zciInfoModel.upload_Path   //合格证上传相对路径
                    replaceForSupplement.veh_clzzqyxx = zciInfoModel.veh_clzzqyxx //车辆制造企业信息
                    replaceForSupplement.SAP_No = zciInfoModel.SAP_No //SAP序列号，更换记录里增加SAP序列号

                    replaceForSupplement.veh_VinFourBit_R = dpCarstorage.veh_VinFourBit
                    replaceForSupplement.veh_Type_R = dpCarstorage.carStorageType
                    replaceForSupplement.veh_Cpggh_R = dpCarstorage.veh_Cpggh
                    replaceForSupplement.veh_zdjgl_R = dpCarstorage.veh_zdjgl
                    replaceForSupplement.veh_Clztxx_R = 'DP'
                    replaceForSupplement.veh_Qyqtxx_R = dpCarstorage.veh_Qyqtxx   //企业其它信息
                    replaceForSupplement.veh_Dpid_R = dpCarstorage.veh_Dpid
                    replaceForSupplement.veh_Jsslx_R = dpCarstorage.veh_Jsslx
                    replaceForSupplement.veh_pzxlh_R = dpCarstorage.veh_pzxlh
                    replaceForSupplement.veh_Ggsxrq_R = dpCarstorage.veh_Ggsxrq
                    replaceForSupplement.veh_Cpscdz_R = dpCarstorage.veh_Cpscdz
                    replaceForSupplement.veh_Qybz_R = dpCarstorage.veh_Qybz
                    replaceForSupplement.veh_Hzdfs_R = dpCarstorage.veh_Hzdfs
                    replaceForSupplement.veh_Qyid_R = dpCarstorage.veh_Qyid
                    replaceForSupplement.veh_Xnhgzbh_R = zciInfoModel.web_virtualcode
                    replaceForSupplement.veh_Clzzqymc_R = dpCarstorage.veh_Clzzqymc
                    replaceForSupplement.veh_Clpp_R = dpCarstorage.veh_Clpp
                    replaceForSupplement.veh_Clmc_R = dpCarstorage.veh_Clmc
                    replaceForSupplement.veh_Clxh_R = dpCarstorage.veh_Clxh
                    replaceForSupplement.veh_Csys_R = params.veh_Csys_R  //车身颜色取修改后的车身颜色
                    replaceForSupplement.veh_Clfl_R = dpCarstorage.veh_Clfl
                    replaceForSupplement.veh_Dpxh_R = dpCarstorage.veh_Dpxh
                    replaceForSupplement.veh_Fdjxh_R = dpCarstorage.veh_Fdjxh
                    replaceForSupplement.veh_Zgcs_R = dpCarstorage.veh_Zgcs
                    replaceForSupplement.veh_Fdjh_R = params.veh_Fdjh
                    replaceForSupplement.veh_Rlzl_R = dpCarstorage.veh_Rlzl
                    replaceForSupplement.veh_Pl_R = dpCarstorage.veh_Pl
                    replaceForSupplement.veh_Gl_R = dpCarstorage.veh_Gl
                    replaceForSupplement.veh_Lts_R = dpCarstorage.veh_Lts
                    replaceForSupplement.veh_Pfbz_R = dpCarstorage.veh_Pfbz

                    replaceForSupplement.veh_Yh_R = dpCarstorage.veh_Yh
                    replaceForSupplement.veh_Wkc_R = dpCarstorage.veh_Wkc
                    replaceForSupplement.veh_Wkk_R = dpCarstorage.veh_Wkk
                    replaceForSupplement.veh_Wkg_R = dpCarstorage.veh_Wkg
                    replaceForSupplement.veh_Hxnbc_R = dpCarstorage.veh_Hxnbc
                    replaceForSupplement.veh_Hxnbk_R = dpCarstorage.veh_Hxnbk
                    replaceForSupplement.veh_Hxnbg_R = dpCarstorage.veh_Hxnbg
                    replaceForSupplement.veh_Ltgg_R = dpCarstorage.veh_Ltgg
                    replaceForSupplement.veh_Gbthps_R = dpCarstorage.veh_Gbthps

                    replaceForSupplement.veh_Qlj_R = dpCarstorage.veh_Qlj
                    replaceForSupplement.veh_Hlj_R = dpCarstorage.veh_Hlj
                    replaceForSupplement.veh_Zj_R = dpCarstorage.veh_Zj
                    replaceForSupplement.veh_Zh_R = dpCarstorage.veh_Zh
                    replaceForSupplement.veh_Zs_R = dpCarstorage.veh_Zs
                    replaceForSupplement.veh_Zxxs_R = dpCarstorage.veh_Zxxs
                    replaceForSupplement.veh_Zzl_R = dpCarstorage.veh_Zzl
                    replaceForSupplement.veh_Zbzl_R = dpCarstorage.veh_Zbzl
                    replaceForSupplement.veh_Edzzl_R = dpCarstorage.veh_Edzzl
                    replaceForSupplement.veh_Ggpc_R = dpCarstorage.veh_Ggpc
                    replaceForSupplement.veh_Zzllyxs_R = dpCarstorage.veh_Zzllyxs
                    replaceForSupplement.veh_Zqyzzl_R = dpCarstorage.veh_Zqyzzl
                    replaceForSupplement.veh_Bgcazzdyxzzl_R = dpCarstorage.veh_Bgcazzdyxzzl
                    replaceForSupplement.veh_Jsszcrs_R = dpCarstorage.veh_Jsszcrs
                    replaceForSupplement.veh_Edzk_R = dpCarstorage.veh_Edzk
                    replaceForSupplement.veh_Zgcs_R = dpCarstorage.veh_Zgcs
                    replaceForSupplement.veh_Bz_R = dpCarstorage.veh_Bz

                    replaceForSupplement.veh_Need_Cer = Integer.parseInt(params.veh_Need_Cer)
                    replaceForSupplement.remark = params.remark
                    replaceForSupplement.salesmanname = params.salesmanname
                    replaceForSupplement.salesmantel = params.salesmantel
                    replaceForSupplement.createrId = loginUser.id
                    replaceForSupplement.createTime = DateUtil.getCurrentTime()
                    if (zcinfoReplace.save(flush: true)) {
                        replaceForSupplement.zcinfoReplaceId = zcinfoReplace.id
                        if (replaceForSupplement.save(flush: true)) {
                            msg = '提交成功'
                        } else {
                            msg = '提交失败'
                        }
                    } else {
                        msg = '提交失败'
                    }
                }
                if (flag == false) {
                    msg = '公告信息不存在，请重新选择！'
                }
                render msg
            } else {
                if (!zciInfoModel || !carStorage) {
                    flag = false
                } else {
                    //判断合格证更换记录是否已存在，只要是营销公司审核状态 veh_coc_status=0 【或者】 area_status=0就不能再次填写变更申请
                    def cel1 = {
                        eq('veh_Zchgzbh', zciInfoModel.veh_Zchgzbh)
                        or {
                            eq("veh_coc_status", 0)
                            eq("area_status", 0) //数据库以前的数据该字段的值为
                            eq("product_auth_status", 0)
                        }
                    }
                    def zr = ZCInfoReplace.createCriteria().list(cel1)
//             def zr=ZCInfoReplace.findAllByVeh_ZchgzbhAndVeh_coc_status(zciInfoModel.veh_Zchgzbh,'0')
                    if (zr && zr.size() > 0) {
                        return render("合格证${zciInfoModel.veh_Zchgzbh}已存在未审核的更换记录！")

                    }
                    if (params?.replaceType == '1') { //replaceType=='1' 是二次更换标识，正常的其他更换该标识为0
                        zcinfoReplace.replaceType = 1
                        zcinfoReplace.area_status = 0      //area_status默认初始值为3，只有在二次更换申请时将其改写为0啊
                        //保存申请者的组织id【取第一个组织id保存】，经销商帐号只能关联到个区域组织
                        User loginUser=User.findByUserName(username)
                        def organList = loginUser?.organs?.id
                        zcinfoReplace.createrOrgan = organList[0]
                        zcinfoReplace.veh_coc_status = 3 //营销公司审核审核状态不使用默认值0，而是将其置3，在区域经理审核时再将其重置为0
                    }
                    zcinfoReplace.veh_Type = zciInfoModel.veh_Type
                    zcinfoReplace.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                    zcinfoReplace.veh_Zchgzbh = zciInfoModel.veh_Zchgzbh
                    zcinfoReplace.veh_Dphgzbh = zciInfoModel.veh_Dphgzbh
                    zcinfoReplace.veh_Fzrq = zciInfoModel.veh_Fzrq
                    zcinfoReplace.veh_Clzzqymc = zciInfoModel.veh_Clzzqymc
                    zcinfoReplace.veh_Qyid = zciInfoModel.veh_Qyid
                    zcinfoReplace.veh_Clfl = zciInfoModel.veh_Clfl
                    zcinfoReplace.veh_Clmc = zciInfoModel.veh_Clmc
                    zcinfoReplace.veh_Clpp = zciInfoModel.veh_Clpp
                    zcinfoReplace.veh_Clxh = zciInfoModel.veh_Clxh
                    zcinfoReplace.veh_Csys = zciInfoModel.veh_Csys
                    zcinfoReplace.veh_Dpxh = zciInfoModel.veh_Dpxh
                    zcinfoReplace.veh_Dpid = zciInfoModel.veh_Dpid
                    zcinfoReplace.veh_Clsbdh = params.veh_Clsbdh    //车辆识别代号
                    zcinfoReplace.veh_Cjh = params.veh_Clsbdh
                    zcinfoReplace.veh_Fdjh = zciInfoModel.veh_Fdjh        //发动机号

                    zcinfoReplace.veh_Fdjxh = zciInfoModel.veh_Fdjxh     //发动机型号
                    zcinfoReplace.veh_Rlzl = zciInfoModel.veh_Rlzl     //燃料种类
                    zcinfoReplace.veh_Pl = zciInfoModel.veh_Pl       //排量
                    zcinfoReplace.veh_Gl = zciInfoModel.veh_Gl       //功率
                    zcinfoReplace.veh_zdjgl = zciInfoModel.veh_zdjgl    //最大净功率
                    zcinfoReplace.veh_Zxxs = zciInfoModel.veh_Zxxs     //转向形式
                    zcinfoReplace.veh_Qlj = zciInfoModel.veh_Qlj      //前轮距
                    zcinfoReplace.veh_Hlj = zciInfoModel.veh_Hlj      //后轮距
                    zcinfoReplace.veh_Lts = zciInfoModel.veh_Lts      //轮胎数
                    zcinfoReplace.veh_Ltgg = zciInfoModel.veh_Ltgg    //轮胎规格
                    zcinfoReplace.veh_Gbthps = zciInfoModel.veh_Gbthps   //钢板弹簧片数
                    zcinfoReplace.veh_Zj = zciInfoModel.veh_Zj      //轴距
                    zcinfoReplace.veh_Zh = zciInfoModel.veh_Zh       //轴荷
                    zcinfoReplace.veh_Zs = zciInfoModel.veh_Zs       //轴数
                    zcinfoReplace.veh_Wkc = zciInfoModel.veh_Wkc      //外廓长
                    zcinfoReplace.veh_Wkk = zciInfoModel.veh_Wkk       //外廓宽
                    zcinfoReplace.veh_Wkg = zciInfoModel.veh_Wkg       //外廓高
                    zcinfoReplace.veh_Hxnbc = zciInfoModel.veh_Hxnbc     //货箱内部长
                    zcinfoReplace.veh_Hxnbk = zciInfoModel.veh_Hxnbk     //货箱内部宽
                    zcinfoReplace.veh_Hxnbg = zciInfoModel.veh_Hxnbg     //货箱内部高
                    zcinfoReplace.veh_Zzl = zciInfoModel.veh_Zzl       //总质量
                    zcinfoReplace.veh_Edzzl = zciInfoModel.veh_Edzzl     //额定载质量
                    zcinfoReplace.veh_Zbzl = zciInfoModel.veh_Zbzl      //整备质量
                    zcinfoReplace.veh_Zzllyxs = zciInfoModel.veh_Zzllyxs   //载质量利用系数
                    zcinfoReplace.veh_Zqyzzl = zciInfoModel.veh_Zqyzzl   //准牵引总质量
                    zcinfoReplace.veh_Edzk = zciInfoModel.veh_Edzk     //额定载客
                    zcinfoReplace.veh_Bgcazzdyxzzl = zciInfoModel.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
                    zcinfoReplace.veh_Jsszcrs = zciInfoModel.veh_Jsszcrs  //驾驶室准乘人数
                    zcinfoReplace.veh_Qzdfs = zciInfoModel.veh_Qzdfs       //前制动方式
                    zcinfoReplace.veh_Hzdfs = zciInfoModel.veh_Hzdfs    //后制动方式
                    zcinfoReplace.veh_Qzdczfs = zciInfoModel.veh_Qzdczfs     //前制动操作方式
                    zcinfoReplace.veh_Cpggh = zciInfoModel.veh_Cpggh    //产品公告号
                    zcinfoReplace.veh_Ggsxrq = zciInfoModel.veh_Ggsxrq   //公告生效日期
                    zcinfoReplace.veh_Zzbh = params.veh_Zzbh       //纸张编号
                    zcinfoReplace.veh_Dywym = zciInfoModel.veh_Dywym   //打印唯一码
                    zcinfoReplace.veh_Zgcs = zciInfoModel.veh_Zgcs     //最高车速
                    zcinfoReplace.veh_Clzzrq = zciInfoModel.veh_Clzzrq   //车辆制造日期
                    zcinfoReplace.veh_Bz = zciInfoModel.veh_Bz       //备注
                    zcinfoReplace.veh_Qybz = zciInfoModel.veh_Qybz     //企业标准
                    zcinfoReplace.veh_Cpscdz = zciInfoModel.veh_Cpscdz   //产品生产地址
                    zcinfoReplace.veh_Qyqtxx = zciInfoModel.veh_Qyqtxx   //企业其它信息
                    zcinfoReplace.veh_Pfbz = zciInfoModel.veh_Pfbz     //排放标准
                    zcinfoReplace.veh_Clztxx = zciInfoModel.veh_Clztxx   //车辆状态信息
                    zcinfoReplace.veh_Jss = zciInfoModel.veh_Jss     //驾驶室
                    zcinfoReplace.veh_Jsslx = zciInfoModel.veh_Jsslx   //驾驶室类型
                    zcinfoReplace.veh_Zchgzbh_0 = zciInfoModel.veh_Zchgzbh_0   //完整合格证编号
                    zcinfoReplace.used = zciInfoModel.used
                    zcinfoReplace.used2 = zciInfoModel.used2
                    zcinfoReplace.upload = zciInfoModel.upload  //
                    // zcinfoReplace. vercode  =zciInfoModel.vercode //二维码
                    zcinfoReplace.veh_Yh = zciInfoModel.veh_Yh   //油耗
                    zcinfoReplace.veh_VinFourBit = zciInfoModel.veh_VinFourBit  //vin第四位
                    zcinfoReplace.veh_Ggpc = zciInfoModel.veh_Ggpc         //公告批次
                    zcinfoReplace.veh_createTime = zciInfoModel.createTime  //创建时间
                    zcinfoReplace.veh_createrId = zciInfoModel.createrId  //创建人id
                    zcinfoReplace.veh_pzxlh = zciInfoModel.veh_pzxlh                   //配置序列号
                    zcinfoReplace.upload_Path = zciInfoModel.upload_Path   //合格证上传相对路径
                    zcinfoReplace.veh_clzzqyxx = zciInfoModel.veh_clzzqyxx //车辆制造企业信息
                    zcinfoReplace.SAP_No = zciInfoModel.SAP_No //SAP序列号，更换记录里增加SAP序列号

                    zcinfoReplace.veh_VinFourBit_R = carStorage.veh_VinFourBit
                    zcinfoReplace.veh_Type_R = carStorage.carStorageType
                    zcinfoReplace.veh_Cpggh_R = carStorage.veh_Cpggh
                    zcinfoReplace.veh_zdjgl_R = carStorage.veh_zdjgl
                    if (params.veh_Need_Cer == '0' || params.veh_Need_Cer == '2') {
                        zcinfoReplace.veh_Clztxx_R = 'QX'
                    } else {
                        zcinfoReplace.veh_Clztxx_R = 'DP'
                    }

                    zcinfoReplace.veh_Qyqtxx_R = carStorage.veh_Qyqtxx   //企业其它信息
                    zcinfoReplace.veh_Dpid_R = carStorage.veh_Dpid
                    zcinfoReplace.veh_Jsslx_R = carStorage.veh_Jsslx
                    zcinfoReplace.veh_pzxlh_R = carStorage.veh_pzxlh
                    zcinfoReplace.veh_Ggsxrq_R = carStorage.veh_Ggsxrq
                    zcinfoReplace.veh_Cpscdz_R = carStorage.veh_Cpscdz
                    zcinfoReplace.veh_Qybz_R = carStorage.veh_Qybz
                    zcinfoReplace.veh_Hzdfs_R = carStorage.veh_Hzdfs
                    zcinfoReplace.veh_Qyid_R = carStorage.veh_Qyid
                    zcinfoReplace.veh_Xnhgzbh_R = zciInfoModel.web_virtualcode
                    zcinfoReplace.veh_Clzzqymc_R = carStorage.veh_Clzzqymc
                    zcinfoReplace.veh_Clpp_R = carStorage.veh_Clpp
                    zcinfoReplace.veh_Clmc_R = carStorage.veh_Clmc
                    zcinfoReplace.veh_Clxh_R = carStorage.veh_Clxh
                    zcinfoReplace.veh_Csys_R = params.veh_Csys_R  //车身颜色取修改后的车身颜色
                    zcinfoReplace.veh_Clfl_R = carStorage.veh_Clfl
                    zcinfoReplace.veh_Dpxh_R = carStorage.veh_Dpxh
                    zcinfoReplace.veh_Fdjxh_R = carStorage.veh_Fdjxh
                    zcinfoReplace.veh_Zgcs_R = carStorage.veh_Zgcs
                    zcinfoReplace.veh_Fdjh_R = params.veh_Fdjh
                    zcinfoReplace.veh_Rlzl_R = carStorage.veh_Rlzl
                    zcinfoReplace.veh_Pl_R = carStorage.veh_Pl
                    zcinfoReplace.veh_Gl_R = carStorage.veh_Gl
                    zcinfoReplace.veh_Lts_R = carStorage.veh_Lts
                    zcinfoReplace.veh_Pfbz_R = carStorage.veh_Pfbz

                    zcinfoReplace.veh_Yh_R = carStorage.veh_Yh
                    zcinfoReplace.veh_Wkc_R = carStorage.veh_Wkc
                    zcinfoReplace.veh_Wkk_R = carStorage.veh_Wkk
                    zcinfoReplace.veh_Wkg_R = carStorage.veh_Wkg
                    zcinfoReplace.veh_Hxnbc_R = carStorage.veh_Hxnbc
                    zcinfoReplace.veh_Hxnbk_R = carStorage.veh_Hxnbk
                    zcinfoReplace.veh_Hxnbg_R = carStorage.veh_Hxnbg
                    zcinfoReplace.veh_Ltgg_R = carStorage.veh_Ltgg
                    zcinfoReplace.veh_Gbthps_R = carStorage.veh_Gbthps

                    zcinfoReplace.veh_Qlj_R = carStorage.veh_Qlj
                    zcinfoReplace.veh_Hlj_R = carStorage.veh_Hlj
                    zcinfoReplace.veh_Zj_R = carStorage.veh_Zj
                    zcinfoReplace.veh_Zh_R = carStorage.veh_Zh
                    zcinfoReplace.veh_Zs_R = carStorage.veh_Zs
                    zcinfoReplace.veh_Zxxs_R = carStorage.veh_Zxxs
                    zcinfoReplace.veh_Zzl_R = carStorage.veh_Zzl
                    zcinfoReplace.veh_Zbzl_R = carStorage.veh_Zbzl
                    zcinfoReplace.veh_Edzzl_R = carStorage.veh_Edzzl
                    zcinfoReplace.veh_Ggpc_R = carStorage.veh_Ggpc
                    zcinfoReplace.veh_Zzllyxs_R = carStorage.veh_Zzllyxs
                    zcinfoReplace.veh_Zqyzzl_R = carStorage.veh_Zqyzzl
                    zcinfoReplace.veh_Bgcazzdyxzzl_R = carStorage.veh_Bgcazzdyxzzl
                    zcinfoReplace.veh_Jsszcrs_R = carStorage.veh_Jsszcrs
                    zcinfoReplace.veh_Edzk_R = carStorage.veh_Edzk
                    zcinfoReplace.veh_Zgcs_R = carStorage.veh_Zgcs
                    zcinfoReplace.veh_Bz_R = carStorage.veh_Bz

                    zcinfoReplace.veh_Need_Cer = Integer.parseInt(params.veh_Need_Cer)
                    zcinfoReplace.remark = params.remark
                    zcinfoReplace.salesmanname = params.salesmanname
                    zcinfoReplace.salesmantel = params.salesmantel
                    User loginUser=User.findByUserName(username)
                    zcinfoReplace.createrId = loginUser.id
                    zcinfoReplace.createTime = DateUtil.getCurrentTime()
                    if (zcinfoReplace.save(flush: true)) {
                        msg = '提交成功'
                    } else {
                        msg = '提交失败'
                    }
                }
                if (flag == false) {
                    msg = '公告信息不存在，请重新选择！'
                }
                render msg

            }
        }
    }

    /**
     *@Descriptions 经销商 >> 合格证更换申请查询（主页面 跳转）
     *@Auther zouq
     *@createTime  2017-07-24
     */
    def salesListNoAuth()
    {
        render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/salesListNoAuth',model: [searchType:'0',username: params?.username])
    }
    /**
     *@Descriptions 经销商 >> 合格证更换申请查询 数据源(经销商)
     * @Auther zhuwei
     * @createTime  2017-07-24
     */
    def jsonListByDealerNoAuth(){

        ///当前登录用户信息
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def userRole=User.get(loginUser?.id)?.roles
        def systemRole=false
        userRole.each {
            def roleCode=Role.get(it.id).roleCode
            if(roleCode=='001'){
                systemRole=true
            }
        }
        //将CRM传过来的用户名转换为用户ID
        def username
        def userId
        if(params.username){
            username = new String(Base64.decodeBase64(params.username), "utf-8"); //将解密的用户名存到数据库
            userId = User.findByUserName(username)?.id
        }
        params.systemRole=systemRole
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort='authTime'
        params.order="desc"
        params.userId= userId

        def results=zcinfoNoAuthService.selectZcInfoReplaceNoAuth(params,loginUser)

        def lst=[]
        results.each { l->
            def realName= User.get(l.createrId)?.userDetail?.realName
            def m=[:]
            m.id =l.id
            m.veh_Zchgzbh_0=l.veh_Zchgzbh_0
            m.veh_Zchgzbh_0_R=l.veh_Zchgzbh_0_R
            m.veh_createTime=l.veh_createTime
            m.createrName =realName
            m.veh_Zchgzbh = l.veh_Zchgzbh
            m.veh_Zzbh=l.veh_Zzbh
            m.veh_Clsbdh=l.veh_Clsbdh
            m.veh_Cpggh =l.veh_Cpggh
            m.veh_Fdjh=l.veh_Fdjh
            m.veh_Fdjxh=l.veh_Fdjxh
            m.veh_Zchgzbh_R=l.veh_Zchgzbh_R
            m.veh_Clsbdh_R=l.veh_Clsbdh_R
            m.veh_Cpggh_R=l.veh_Cpggh_R
            m.veh_Fdjh_R=l.veh_Fdjh_R
            m.veh_Fdjxh_R=l.veh_Fdjxh_R
            m.salesmanname=l.salesmanname
            m.salesmantel=l.salesmantel
            m.createTime=l.createTime
            m.veh_coc_status=l.veh_coc_status
            m.veh_Clxh=l.veh_Clxh
            m.veh_Clxh_R=l.veh_Clxh_R
            m.veh_Clmc=l.veh_Clmc
            m.veh_Clmc_R=l.veh_Clmc_R
            m.veh_Fdjxh=l.veh_Fdjxh
            m.veh_Fdjxh_R=l.veh_Fdjxh_R
            m.veh_Zzbh=l.veh_Zzbh
            m.veh_Zzbh_R=l.veh_Zzbh_R
            m.veh_Fzrq=l.veh_Fzrq
            m.veh_Fzrq_R=l.veh_Fzrq_R
            m.remark=l.remark
            m.authTime=l.authTime
            m.replaceType=l.replaceType
            m.area_status=l.area_status
            m.product_auth_status=l.product_auth_status

            lst.add(m)
        }
        def result = [rows: lst, total: results?.totalCount]
        render result as JSON
    }

    /**
     * 变更合格证 详细函数
     * @author zouQ
     * @returnI
     */
    def zcInfoReplaceViewNoAuth() {
        def aaa = params
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        if (!zcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "salesListNoAuth",params:params)         //没有找到就跳到列表
            return
        }
        render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoReplaceViewNoAuth',model:[zcinfoReAuInstance:zcinfoReAuInstance,searchType:params.searchType,username:params?.username])
    }
    /**
     *@Descriptions 经销商 >> 合格证变更申请查询 合格证编辑操作函数
     *@Auther zouq
     *@createTime  2013-08-16
     */
    def zinfoReplaceEditNoAuth()
    {
        def  zcinfoReplaceID  = params.id;  ///合格证变更表ID信息
        if (!zcinfoReplaceID){
            render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoReplaceUpdateNoAuth',model: [searchType:params.searchType,username: params?.username])
            return
        }
        def  zcinfoReplace =  ZCInfoReplace.get(zcinfoReplaceID);
        def flag=true
        if(zcinfoReplace) {
            render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoReplaceUpdateNoAuth',model: [zcinfoReplace:zcinfoReplace,zid:zcinfoReplace.id,statusWrite:'nowrite',searchType:params.searchType,username: params?.username])
        } else{
            render(view:'/cn/com/wz/vehcert/zcinfo/replaceNoAuth/zcinfoReplaceUpdateNoAuth',model: [searchType:params.searchType,username: params?.username])
        }
    }
    /**
     *@Descriptions 经销商 >> 合格证变更申请查询 合格证删除
     *@Auther zhuwei
     *@createTime  2017-07-25
     * @Update
     */
    def deleteSingleNoAuth(){
        def zcInfoReplace=ZCInfoReplace.get(params.id)
        if(zcInfoReplace.veh_coc_status==1){
            //如果最终更换打印处已经审核通过，那么不允许经销商在合格证更换申请查询出删除更换记录
            render "更换申请已经通过，不允许删除更换记录"
        }else{
            def result=zcInfoReplace.delete(flush: true)
            if(result){
                render "fail"
            }else{
                render "success"
            }
        }

    }
    /**
     *@Descriptions 经销商 >> 合格证变更申请查询 变更合格证更新操作函数
     *@Auther zouq
     *@createTime  2013-08-16
     */
    def zinfoReplaceUpdateNoAuth()
    {
        ////变更合格证ID信息
        def zid = params.zid
        ////新修改合格证ID信息
        def newid = params.newId
        if (!zid){
            render "保存失败,查看数据格式后重试!"
            return
        }
        def zcinfoReplace = ZCInfoReplace.get(zid)

        if (!zcinfoReplace){
            render "保存失败,查看数据格式后重试!"
            return
        }
        // 提交锁
        if (params.version) {
            def version = params.version.toLong()
            if (zcinfoReplace.version > version) {
                render "保存失败,查看数据格式后重试!"
                return
            }
        }

        def carstorageId=params.newId

        if (carstorageId){
            def zciInfoC=CarStorage.get(carstorageId)
            zcinfoReplace. veh_VinFourBit_R =zciInfoC.veh_VinFourBit
            zcinfoReplace. veh_Cpggh_R=zciInfoC.veh_Cpggh
            zcinfoReplace. veh_zdjgl_R=zciInfoC.veh_zdjgl
            zcinfoReplace. veh_Clztxx_R=zciInfoC.veh_Clztxx
            zcinfoReplace. veh_Qyqtxx_R=zciInfoC.veh_Qyqtxx   //企业其它信息
            zcinfoReplace. veh_Dpid_R = zciInfoC.veh_Dpid
            zcinfoReplace. veh_Jsslx_R =zciInfoC.veh_Jsslx
            zcinfoReplace. veh_pzxlh_R =zciInfoC.veh_pzxlh
            zcinfoReplace. veh_Ggsxrq_R=zciInfoC.veh_Ggsxrq
            zcinfoReplace. veh_Cpscdz_R=zciInfoC.veh_Cpscdz
            zcinfoReplace. veh_Qybz_R=zciInfoC.veh_Qybz
            zcinfoReplace. veh_Hzdfs_R=zciInfoC.veh_Hzdfs
            zcinfoReplace. veh_Qyid_R= zciInfoC.veh_Qyid
//        zcinfoReplace.veh_Xnhgzbh_R=zciInfoC.web_virtualcode
            zcinfoReplace.veh_Clzzqymc_R=zciInfoC.veh_Clzzqymc
            zcinfoReplace.veh_Clpp_R=zciInfoC.veh_Clpp
            zcinfoReplace.veh_Clmc_R=zciInfoC.veh_Clmc
            zcinfoReplace.veh_Clxh_R=zciInfoC.veh_Clxh

//            2013年8月24日 去掉车身颜色跟随公告信息 车身颜色进行修改
            zcinfoReplace.veh_Clfl_R=zciInfoC.veh_Clfl
            zcinfoReplace.veh_Dpxh_R=zciInfoC.veh_Dpxh
            zcinfoReplace.veh_Fdjxh_R=zciInfoC.veh_Fdjxh
            zcinfoReplace.veh_Rlzl_R=zciInfoC.veh_Rlzl
            zcinfoReplace.veh_Pl_R=zciInfoC.veh_Pl
            zcinfoReplace.veh_Gl_R=zciInfoC.veh_Gl
            zcinfoReplace.veh_Lts_R=zciInfoC.veh_Lts
            zcinfoReplace.veh_Pfbz_R=zciInfoC.veh_Pfbz

            zcinfoReplace.veh_Yh_R=zciInfoC.veh_Yh
            zcinfoReplace.veh_Wkc_R=zciInfoC.veh_Wkc
            zcinfoReplace.veh_Wkk_R=zciInfoC.veh_Wkk
            zcinfoReplace.veh_Wkg_R=zciInfoC.veh_Wkg
            zcinfoReplace.veh_Hxnbc_R=zciInfoC.veh_Hxnbc
            zcinfoReplace.veh_Hxnbk_R=zciInfoC.veh_Hxnbk
            zcinfoReplace.veh_Hxnbg_R=zciInfoC.veh_Hxnbg
            zcinfoReplace.veh_Ltgg_R=zciInfoC.veh_Ltgg
            zcinfoReplace.veh_Gbthps_R=zciInfoC.veh_Gbthps

            zcinfoReplace.veh_Qlj_R=zciInfoC.veh_Qlj
            zcinfoReplace.veh_Hlj_R=zciInfoC.veh_Hlj
            zcinfoReplace.veh_Zj_R=zciInfoC.veh_Zj
            zcinfoReplace.veh_Zh_R=zciInfoC.veh_Zh
            zcinfoReplace.veh_Zs_R=zciInfoC.veh_Zs
            zcinfoReplace.veh_Zxxs_R=zciInfoC.veh_Zxxs
            zcinfoReplace.veh_Zzl_R=zciInfoC.veh_Zzl
            zcinfoReplace.veh_Zbzl_R=zciInfoC.veh_Zbzl
            zcinfoReplace.veh_Edzzl_R=zciInfoC.veh_Edzzl

            zcinfoReplace.veh_Zzllyxs_R=zciInfoC.veh_Zzllyxs
            zcinfoReplace.veh_Zqyzzl_R=zciInfoC.veh_Zqyzzl
            zcinfoReplace.veh_Bgcazzdyxzzl_R=zciInfoC.veh_Bgcazzdyxzzl
            zcinfoReplace.veh_Jsszcrs_R=zciInfoC.veh_Jsszcrs
            zcinfoReplace.veh_Edzk_R=zciInfoC.veh_Edzk
            zcinfoReplace.veh_Zgcs_R=zciInfoC.veh_Zgcs
            zcinfoReplace.veh_Bz_R=zciInfoC.veh_Bz
        }

        zcinfoReplace.veh_Csys_R=params.veh_Csys_R
        zcinfoReplace.veh_Need_Cer=Integer.parseInt(params.veh_Need_Cer)
        zcinfoReplace.veh_Zzbh=params.veh_Zzbh
        zcinfoReplace.veh_Fdjh_R=params.veh_Fdjh
        zcinfoReplace.remark=params.remark
        zcinfoReplace.salesmanname=params.salesmanname
        zcinfoReplace.salesmantel=params.salesmantel
//        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
//        createId保存的是CRM传过来的用户名的id
        def username
        def userId
        if(params.username){
            username = new String(Base64.decodeBase64(params.username), "utf-8"); //将解密的用户名存到数据库
            userId = User.findByUserName(username)?.id
        }
        zcinfoReplace.createrId= userId
        zcinfoReplace.createTime=DateUtil.getCurrentTime()

        if (!zcinfoReplace.save(flush: true)) {
            render "保存失败,查看数据格式后重试!"
            return
        }else{
            render "更新已成功!"
        }
    }
///**
// *
// * @Description 取得经销商所有的车辆
// * @author QJ
// * @createTime 2018-06-25
// * @return
// */
//    def carsOfDistributorNoAuth(params) {
//         def name
//        if(params.username){
//            name = new String(Base64.decodeBase64(params.username), "utf-8");
//        }
//        List vinList=[]
//        if(name.length()==6){
//             vinList = newDmsSynService.findVinOfDistributor(name)
//        }
//        render(contentType: "text/json") {
//            array {
//                for (m in vinList) {
//                    pack(text:m.new_vin ,value:m.new_vin)
//                }
//            }
//        }
//    }
}
