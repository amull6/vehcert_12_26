package cn.com.wz.vehcert.dealer

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.coc.CocCarStorage
import cn.com.wz.vehcert.coc.CocCarStoragePrint
import cn.com.wz.vehcert.zcinfo.ZCInfo
import cn.com.wz.vehcert.zcinfo.ZCInfoTemp
import com.cqcca.jni.QR
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.itextpdf.text.pdf.qrcode.EncodeHintType
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject
import parent.poi.ExcelUtils
import sun.misc.BASE64Decoder

import java.nio.file.FileSystems;

class CocPrintController extends BaseController {
    def exportService
    def sqlService
    def index() {}
    /**
     * @Description 经销商已经下载过的自己的合格证信息
     * @params
     * @author Xu
     */
    def ownLoadList(){
        render(view:'/cn/com/wz/vehcert/dealer/cocprint/ownloadlist')
    }

    /**
     * 车间本地数据库的合格证信息
     * @params
     * @author  Xu
     * @return
     */
    def cheJianList(){
        render(view:'/cn/com/wz/vehcert/dealer/cocprint/chejianlist')
    }

    /**
     * 经销商查询
     * @author  Xu
     * @return
     * @Update 2013-11-06 huxx 优化了查询sql效率
     */
    def jsonOwnLoadList(){
        def result=[:]
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        ////查询经销商所拥有的合格证信息
        StringBuffer sql_sbf = new StringBuffer("SELECT distinct veh_zchgzbh as veh_zchgzbh FROM WZH_ZCINFO_TEMP WHERE user_down ='"+loginUser.id+"'");
        def list =  sqlService.executeList(sql_sbf.toString())
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort='createTime'
        params.order="desc"
        if(list.size()>0){
            def cel = {
                or{
                    list.each {hgz->
                        eq("veh_Zchgzbh",hgz.veh_zchgzbh)
                    }
                }

                if(params.vehCode){
                    eq("veh_Zchgzbh","${params.vehCode}")
                }
                eq('veh_Type','0')
                eq('veh_Clztxx','QX')
            }

            def lst = ZCInfo.createCriteria().list(params,cel)
            result = [rows: lst, total: lst.totalCount]
        }else{
            result = [rows: [], total: 0]
        }

        render result as JSON
    }

    /**
     *车间本地查询
     * @return
     * @update 底盘一致性证书打印
     */
    def jsonChejianList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort='createTime'
        params.order="desc"
        def cel = {
            if(params.vehCode){
                eq("veh_Zchgzbh","${params.vehCode}")
            }
            eq('veh_Type','0')
//            eq('veh_Clztxx','QX')
        }
        def results = ZCInfo.createCriteria().list(params,cel)
        def result = [rows: results, total: results.totalCount]
        render result as JSON
    }

    /**
     * @Description 跳转到经销商下载过的合格证信息相关的一致性信息列表
     * @params
     * @return
     * @createTime 2013-7-27 15:40:17
     * @author Xu
     * zcinfoID 合格证的ID
     * @Update 通过  params?.newCoC标记是不是新的打印模板
     * @UpdateTime 2015-02-27 zhuwei
     */
    def zcinfoOfList(){
        render(view: '/cn/com/wz/vehcert/dealer/cocprint/zcinfooflist',model: [zcinfoID:params.zcinfoID,usertype:params.usertype,newCoC:params?.newCoC]);
    }

    /**
     * @Description 跳转到一致性证书打印页面
     * @params
     * @return
     * @createTime 2013-7-29 9:50:32
     * @author Xu
     * zcinfoID 合格证的ID
     * @Update 通过  params?.newCoC标记是不是新的打印模板
     * @UpdateTime 2015-02-27 zhuwei
     * * @Update 2015-05-22 添加新版证书的二维码打印 zhuwei
     */
    def cocBookPrint(){
        CocCarStoragePrint.withTransaction {
            def aaa=params
            try {
                def cocPrnInstance = CocCarStoragePrint.get(params.cocPrnID)  //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联  ,所以cocPrinID也是合格证信息的id
                if (!cocPrnInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.cocPrnID])
                    redirect(action: "zcinfoOfList")
                    return
                }
                if(params?.newCoC=='1'){
                    if(params?.oldQRcode=='1'){  //新版一致性证书，不替换已打印一致性证书
                        def vin=ZCInfo.get(params.cocPrnID).veh_Clsbdh
                        def coc= cocPrnInstance.coc_yzxzsbh  //完全严格的讲，该处应该取一致性证书信息里面的一致性证书编号，而不是已打印一致性证书里面的编号
                        if(!cocPrnInstance.coc_qr_code){ //如果是没有二维码字符串，新生成
                            QR  qr=new QR()
                            def instr=coc+vin
                            println(instr)
//                        cocPrnInstance.coc_qr_code=  qr.GenQR(instr).substring(0,100)    //图片的base64编码
                            cocPrnInstance.coc_qr_code=  qr.GenQR(instr)    //图片的base64编码
                        }
//                    this.generateImage(QRstring,coc,vin)
                        def   QRpiUrl =this.generateImage(cocPrnInstance.coc_qr_code,cocPrnInstance.coc_yzxzsbh,vin)
                        render(view:'/cn/com/wz/vehcert/dealer/cocprint/newCocbookprint',model:[cocPrnInstance: cocPrnInstance,params:params,usertype:params.usertype,QRpiUrl:QRpiUrl])
                    }else{     //替换或者是第一次打印新版一致性证书
                        render(view:'/cn/com/wz/vehcert/dealer/cocprint/newCocbookprint',model:[cocPrnInstance: cocPrnInstance,params:params,usertype:params.usertype,QRpiUrl:params?.QRpiUrl])
                    }

                }else if(params?.newCoC=='2'){
                    if(params?.oldQRcode=='1'){  //新版一致性证书，不替换已打印一致性证书
                        def vin=ZCInfo.get(params.cocPrnID).veh_Clsbdh
                        def coc= cocPrnInstance.coc_yzxzsbh  //完全严格的讲，该处应该取一致性证书信息里面的一致性证书编号，而不是已打印一致性证书里面的编号
                        if(!cocPrnInstance.coc_qr_code){ //如果是没有二维码字符串，新生成
                            QR  qr=new QR()
                            def instr=coc+vin
                            println(instr)
//                        cocPrnInstance.coc_qr_code=  qr.GenQR(instr).substring(0,100)    //图片的base64编码
                            cocPrnInstance.coc_qr_code=  qr.GenQR(instr)    //图片的base64编码
                        }
//                    this.generateImage(QRstring,coc,vin)
                        def   QRpiUrl =this.generateImage(cocPrnInstance.coc_qr_code,cocPrnInstance.coc_yzxzsbh,vin)
                        render(view:'/cn/com/wz/vehcert/dealer/cocprint/thrCocbookprint',model:[cocPrnInstance: cocPrnInstance,params:params,usertype:params.usertype,QRpiUrl:QRpiUrl])
                    }else{     //替换或者是第一次打印新版一致性证书
                        render(view:'/cn/com/wz/vehcert/dealer/cocprint/thrCocbookprint',model:[cocPrnInstance: cocPrnInstance,params:params,usertype:params.usertype,QRpiUrl:params?.QRpiUrl])
                    }

                }else{
                    render(view:'/cn/com/wz/vehcert/dealer/cocprint/cocbookprint',model:[cocPrnInstance: cocPrnInstance,params:params,usertype:params.usertype])
                }
            }catch(Exception e){
                e.cause?e.cause.printStackTrace():e.printStackTrace()
                redirect(action: "zcinfoOfList")
            }

        }
    }

    /**
     * @Description 经销商ajax获取与合格证相关的一致性证书列表
     * @params
     * @return
     * @createTime 2013-7-27 15:50:02
     * @author Xu
     * @Update 2013-10-22 huxx 添加座位数的匹配条件
     * @Update 2013-11-04 修改查询条件中值为null的处理方式
     * @Update 专用车合格证货箱内部长、宽、高需要填写空白null，一致性也要填写null，    isNull 在sql server中无法使用所以使用or条件查询
     */
    def jsonZcinfoOfList(){

        def zcinfo =   ZCInfo.get(params.zcinfoID)
        //设置分页条件
        params.max =params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        //车辆型号、货箱长度、货箱宽度、货箱高度、发动机型号、外廓长、外廓宽、外廓高、排量 ,座位数,轮胎规格，载质量利用系数,轮距 .钢板弹簧片数.轴距
        def cel = {
            eq('coc_clxh',zcinfo.veh_Clxh)//车辆型号
            if (zcinfo.veh_Hxnbc){
                eq('coc_hxcd',zcinfo.veh_Hxnbc)//货箱长度
            }else{
//                isNull("coc_hxcd")
                or{
                    isNull("coc_hxcd")
                    eq('coc_hxcd','')
                }
            }

            if (zcinfo.veh_Hxnbk){
                eq('coc_hxkd',zcinfo.veh_Hxnbk)//货箱内部宽
            }else{
//                isNull("coc_hxkd")
                or{
                    isNull("coc_hxkd")
                    eq('coc_hxkd','')
                }
            }

            if (zcinfo.veh_Hxnbg){
                eq('coc_hxgd',zcinfo.veh_Hxnbg)//货箱内部搞
            }else{
//                isNull("coc_hxgd")
                or{
                    isNull("coc_hxgd")
                    eq('coc_hxgd','')
                }
            }

            if (zcinfo.veh_Fdjxh){
                eq('coc_fdjxh',zcinfo.veh_Fdjxh)//发动机型号
            }else{
                or{
                    isNull("coc_fdjxh")
                    eq('coc_fdjxh','')
                }
            }

            if (zcinfo.veh_Wkc){
                eq('coc_cd',zcinfo.veh_Wkc)//外廓长
            }else{
                or{
                    isNull("coc_cd")
                    eq('coc_cd','')
                }
            }

            if (zcinfo.veh_Wkk){
                eq('coc_kd',zcinfo.veh_Wkk)//外廓宽
            }else{
                or{
                    isNull("coc_kd")
                    eq('coc_kd','')
                }
            }

            if (zcinfo.veh_Wkg){
                eq('coc_gd',zcinfo.veh_Wkg)//外廓高
            }else{
                or{
                    isNull("coc_gd")
                    eq('coc_gd','')
                }
            }

            if(zcinfo.veh_Zj){  //轴距
                eq("coc_zj",zcinfo.veh_Zj)
            } else{
                or{
                    isNull("coc_zj")
                    eq('coc_zj','')
                }
            }

            if (zcinfo.veh_Gbthps){ //钢板弹簧片数
                eq("coc_gbthps",zcinfo.veh_Gbthps)
            }else{
                or{
                    isNull("coc_gbthps")
                    eq('coc_gbthps','')
                }
            }
            if (zcinfo.veh_Zzllyxs){ //载质量利用系数
                eq("coc_zzllyxsh",zcinfo.veh_Zzllyxs)
            }else{
                or{
                    isNull("coc_zzllyxsh")
                    eq('coc_zzllyxsh','')
                }
            }
            eq("coc_lj",zcinfo.veh_Qlj+"/"+zcinfo.veh_Hlj)    //轮距

            eq('coc_pl',zcinfo.veh_Pl)//排量
            if (zcinfo.veh_Jsszcrs){ //座位数
                eq("coc_zws",zcinfo.veh_Jsszcrs)
            }else{
                or{
                    isNull("coc_zws")
                    eq('coc_zws','')
                }
            }
            like("coc_ltgg","%${zcinfo.veh_Ltgg}%")
            order 'coc_ltgg','desc'
        }
        def results=CocCarStorage.createCriteria().list(params,cel)
        def result = [rows: results, total: results.totalCount]
        render result   as JSON
    }

    /**
     * @Description 经销商异步更新
     * @ zcinfoID
     * @author Xu
     * @Update 2013-10-28 huxx 修改一致性证书编号的生成规则 原规则为：’A048290’+车型系列代号名称+yyyymm(今天时间的年月)+Vin后六位+‘00’
     * @更改后的生成规则为：’A048290’+车型系列代号名称+yyyymm(今天时间的年月)+Vin后六位+CCC证书号中的后两位版本号
     * @Update 2015-05-22 添加新版证书的二维码打印 zhuwei
     * @Update 2015-06-30 新版一致性证书使用了合格证的‘车辆制造日期’，如果是老版证书还是取自一致性证书信息里面的 cocInstance.coc_rq   zhuwei
     */
    def jsonUpdate(){
        CocCarStoragePrint.withTransaction {
            def aa=params
            String id =""
            User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
            def flag=true
            def idStr=params.id
            def zcinfoIDStr = params.zcinfoID
            def usertype =params.usertype
            def QRpiUrl
            try{
                def cocInstance = CocCarStorage.get(idStr)
                def zcinfoInstance= ZCInfo.get(zcinfoIDStr)
                CocCarStoragePrint cocPrint = CocCarStoragePrint.get(zcinfoInstance.id)
                if (!cocPrint){
                    def cocCarStoragePrint = new CocCarStoragePrint()
                    cocCarStoragePrint.setId(zcinfoInstance.id) //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联
                    cocCarStoragePrint.coc_czsl         =cocInstance.coc_czsl
                    cocCarStoragePrint.coc_clsl         =cocInstance.coc_clsl
                    cocCarStoragePrint.coc_qdzwz        =cocInstance.coc_qdzwz
                    cocCarStoragePrint.coc_zj           =cocInstance.coc_zj
                    cocCarStoragePrint.coc_qyzqzj       =cocInstance.coc_qyzqzj
                    cocCarStoragePrint.coc_lj           =cocInstance.coc_lj
                    cocCarStoragePrint.coc_cd           =cocInstance.coc_cd
                    cocCarStoragePrint.coc_zqdyqyzzjl   =cocInstance.coc_zqdyqyzzjl
                    cocCarStoragePrint.coc_zzqycd       =cocInstance.coc_zzqycd
                    cocCarStoragePrint.coc_kd         =cocInstance.coc_kd
                    cocCarStoragePrint.coc_gd           =cocInstance.coc_gd
                    cocCarStoragePrint.coc_tymj         =cocInstance.coc_tymj
                    cocCarStoragePrint.coc_qx           =cocInstance.coc_qx
                    cocCarStoragePrint.coc_jjj          =cocInstance.coc_jjj
                    cocCarStoragePrint.coc_lqj          =cocInstance.coc_lqj
                    cocCarStoragePrint.coc_xsxdcsclzl   =cocInstance.coc_xsxdcsclzl
                    cocCarStoragePrint.coc_edzzl        =cocInstance.coc_edzzl
                    cocCarStoragePrint.coc_zlzhfp       =cocInstance.coc_zlzhfp
                    cocCarStoragePrint.coc_zzllyxsh     =cocInstance.coc_zzllyxsh
                    cocCarStoragePrint.coc_czyxzdzl     =cocInstance.coc_czyxzdzl
                    cocCarStoragePrint.coc_kssczwz      =cocInstance.coc_kssczwz
                    cocCarStoragePrint.coc_kczczwz      =cocInstance.coc_kczczwz
                    cocCarStoragePrint.coc_cdzdyxzh     =cocInstance.coc_cdzdyxzh
                    cocCarStoragePrint.coc_qygsgc       =cocInstance.coc_qygsgc
                    cocCarStoragePrint.coc_bgc          =cocInstance.coc_bgc
                    cocCarStoragePrint.coc_zjjgc        =cocInstance.coc_zjjgc
                    cocCarStoragePrint.coc_gczdzl       =cocInstance.coc_gczdzl
                    cocCarStoragePrint.coc_zdzhzl       =cocInstance.coc_zdzhzl
                    cocCarStoragePrint.coc_jcdczdczfh   =cocInstance.coc_jcdczdczfh
                    cocCarStoragePrint.coc_zjps         =cocInstance.coc_zjps
                    cocCarStoragePrint.coc_qgsl         =cocInstance.coc_qgsl
                    cocCarStoragePrint.coc_qgplxs       =cocInstance.coc_qgplxs
                    cocCarStoragePrint.coc_rlzl         =cocInstance.coc_rlzl
                    cocCarStoragePrint.coc_zdjgl        =cocInstance.coc_zdjgl
                    cocCarStoragePrint.coc_dyfdjzs      =cocInstance.coc_dyfdjzs
                    cocCarStoragePrint.coc_lhqxs        =cocInstance.coc_lhqxs
                    cocCarStoragePrint.coc_bsqzs        =cocInstance.coc_bsqzs
                    cocCarStoragePrint.coc_sb           =cocInstance.coc_sb
                    cocCarStoragePrint.coc_zcdb         =cocInstance.coc_zcdb
                    cocCarStoragePrint.coc_ltgg         =cocInstance.coc_ltgg
                    cocCarStoragePrint.coc_sfazkqxg     =cocInstance.coc_sfazkqxg
                    cocCarStoragePrint.coc_gbthps       =cocInstance.coc_gbthps
                    cocCarStoragePrint.coc_zxzlxs       =cocInstance.coc_zxzlxs
                    cocCarStoragePrint.coc_zdzzjysm     =cocInstance.coc_zdzzjysm
                    cocCarStoragePrint.coc_zdxtgqgnyl   =cocInstance.coc_zdxtgqgnyl
                    cocCarStoragePrint.coc_csxs         =cocInstance.coc_csxs
                    cocCarStoragePrint.coc_clys         =zcinfoInstance.veh_Csys   //车身颜色
                    cocCarStoragePrint.coc_gnyxrj       =cocInstance.coc_gnyxrj
                    cocCarStoragePrint.coc_hxcd         =cocInstance.coc_hxcd
                    cocCarStoragePrint.coc_hx           =cocInstance.coc_hx
                    cocCarStoragePrint.coc_hxkd         =cocInstance.coc_hxkd
                    cocCarStoragePrint.coc_hxgd         =cocInstance.coc_hxgd
                    cocCarStoragePrint.coc_qzjzdljnl    =cocInstance.coc_qzjzdljnl
                    cocCarStoragePrint.coc_cmsl         =cocInstance.coc_cmsl
                    cocCarStoragePrint.coc_cmgz         =cocInstance.coc_cmgz
                    cocCarStoragePrint.coc_zws          =cocInstance.coc_zws
                    cocCarStoragePrint.coc_cccno        =cocInstance.coc_cccno
                    cocCarStoragePrint.coc_sybgno       =cocInstance.coc_sybgno
                    cocCarStoragePrint.coc_zgcs         =cocInstance.coc_zgcs
                    cocCarStoragePrint.coc_sj           =cocInstance.coc_sj
                    cocCarStoragePrint.coc_pqpfw        =cocInstance.coc_pqpfw
                    cocCarStoragePrint.coc_co2          =cocInstance.coc_co2
                    cocCarStoragePrint.coc_jgyqsybg     =cocInstance.coc_jgyqsybg
                    cocCarStoragePrint.coc_wxhwysjb     =cocInstance.coc_wxhwysjb
                    cocCarStoragePrint.coc_dwjgyqbh     =cocInstance.coc_dwjgyqbh
                    cocCarStoragePrint.coc_mxdwysjb     =cocInstance.coc_mxdwysjb
                    cocCarStoragePrint.coc_note         =cocInstance.coc_note
                    cocCarStoragePrint.coc_zsno         =cocInstance.coc_zsno
                    cocCarStoragePrint.coc_clsccmc      =cocInstance.coc_clsccmc
                    cocCarStoragePrint.coc_clzzg        =cocInstance.coc_clzzg
                    cocCarStoragePrint.coc_cxxldhmc     =cocInstance.coc_cxxldhmc
                    cocCarStoragePrint.coc_dydhmc       =cocInstance.coc_dydhmc
                    cocCarStoragePrint.coc_cxdhmc       =cocInstance.coc_cxdhmc
                    cocCarStoragePrint.coc_cxmc         =cocInstance.coc_cxmc
                    cocCarStoragePrint.coc_clzwpp       =cocInstance.coc_clzwpp
                    cocCarStoragePrint.coc_cllb         =cocInstance.coc_cllb
                    cocCarStoragePrint.coc_zzsmc        =cocInstance.coc_zzsmc
                    cocCarStoragePrint.coc_zzsdz        =cocInstance.coc_zzsdz
                    cocCarStoragePrint.coc_fdjzzsmc     =cocInstance.coc_fdjzzsmc
                    cocCarStoragePrint.coc_fdmpwz       =cocInstance.coc_fdmpwz
                    cocCarStoragePrint.coc_clsbdh       =zcinfoInstance.veh_Clsbdh    //VIN
                    cocCarStoragePrint.coc_sbhdkwz      =cocInstance.coc_sbhdkwz
                    cocCarStoragePrint.coc_fdjbh        =zcinfoInstance.veh_Fdjh     //发动机号
                    cocCarStoragePrint.coc_fdjbhdkwz    =cocInstance.coc_fdjbhdkwz
                    cocCarStoragePrint.coc_ccczsh       =cocInstance.coc_ccczsh
                    cocCarStoragePrint.coc_clxh         =cocInstance.coc_clxh
                    cocCarStoragePrint.coc_clmc         =cocInstance.coc_clmc
//                cocCarStoragePrint.coc_rq           =cocInstance.coc_rq
                    cocCarStoragePrint.coc_pl           =cocInstance.coc_pl
                    cocCarStoragePrint.coc_fdjxh        =cocInstance.coc_fdjxh
                    cocCarStoragePrint.coc_fdjgzyl      =cocInstance.coc_fdjgzyl
                    cocCarStoragePrint.coc_sfzykqxgzz   =cocInstance.coc_sfzykqxgzz
                    cocCarStoragePrint.coc_creater      =loginUser.userName
                    cocCarStoragePrint.coc_createTime   =DateUtil.getCurrentTime()

                    cocCarStoragePrint.coc_new_energy   =cocInstance.coc_new_energy
                    cocCarStoragePrint.coc_abs          =cocInstance.coc_abs
                    cocCarStoragePrint.coc_scqydz       =cocInstance.coc_scqydz
                    cocCarStoragePrint.coc_clzl         =cocInstance.coc_clzl
                    cocCarStoragePrint.coc_jccccno      =cocInstance.coc_jccccno
                    cocCarStoragePrint.coc_jcclxh       =cocInstance.coc_jcclxh
                    cocCarStoragePrint.coc_jccllb       =cocInstance.coc_jccllb
                    cocCarStoragePrint.coc_zzjdcccno    =cocInstance.coc_zzjdcccno
                    cocCarStoragePrint.coc_zzjdqfrq     =cocInstance.coc_zzjdqfrq
                    cocCarStoragePrint.coc_zdyxzzl      =cocInstance.coc_zdyxzzl

                    cocCarStoragePrint.coc_sjbzh   =cocInstance.coc_sjbzh
                    cocCarStoragePrint.coc_dzzs          =cocInstance.coc_dzzs
                    cocCarStoragePrint.coc_fdjzs       =cocInstance.coc_fdjzs
                    cocCarStoragePrint.coc_cwzs         =cocInstance.coc_cwzs
                    cocCarStoragePrint.coc_pfwbzh      =cocInstance.coc_pfwbzh
                    cocCarStoragePrint.coc_ytrl       =cocInstance.coc_ytrl
                    cocCarStoragePrint.coc_ytrl_co       =cocInstance.coc_ytrl_co
                    cocCarStoragePrint.coc_ytrl_nox    =cocInstance.coc_ytrl_nox
                    cocCarStoragePrint.coc_ytrl_yd     =cocInstance.coc_ytrl_yd
                    cocCarStoragePrint.coc_ytrl_hc      =cocInstance.coc_ytrl_hc

                    cocCarStoragePrint.coc_ytrl_hcnox   =cocInstance.coc_ytrl_hcnox
                    cocCarStoragePrint.coc_ytrl_pn      =cocInstance.coc_ytrl_pn
                    cocCarStoragePrint.coc_qtrl      =cocInstance.coc_qtrl
                    cocCarStoragePrint.coc_qtrl_co      =cocInstance.coc_qtrl_co
                    cocCarStoragePrint.coc_qtrl_nmhc    =cocInstance.coc_qtrl_nmhc
                    cocCarStoragePrint.coc_qtrl_ch4      =cocInstance.coc_qtrl_ch4
                    cocCarStoragePrint.coc_qtrl_nox       =cocInstance.coc_qtrl_nox
                    cocCarStoragePrint.coc_qtrl_thc       =cocInstance.coc_qtrl_thc
                    cocCarStoragePrint.coc_qtrl_pn    =cocInstance.coc_qtrl_pn
                    cocCarStoragePrint.coc_co2pflbzh     =cocInstance.coc_co2pflbzh
                    cocCarStoragePrint.coc_co2_sq      =cocInstance.coc_co2_sq

                    cocCarStoragePrint.coc_co2_sj   =cocInstance.coc_co2_sj
                    cocCarStoragePrint.coc_co2_zh      =cocInstance.coc_co2_zh
                    cocCarStoragePrint.coc_rlxh_sq      =cocInstance.coc_rlxh_sq
                    cocCarStoragePrint.coc_rlxh_sj      =cocInstance.coc_rlxh_sj
                    cocCarStoragePrint.coc_rlxh_zh      =cocInstance.coc_rlxh_zh
                    cocCarStoragePrint.coc_clzclx      =cocInstance.coc_clzclx

                    cocCarStoragePrint.coc_ddjgzdy      =cocInstance.coc_ddjgzdy
                    cocCarStoragePrint.coc_dldcxh      =cocInstance.coc_dldcxh
                    cocCarStoragePrint.coc_dldceddy      =cocInstance.coc_dldceddy
                    cocCarStoragePrint.coc_dldcedrl      =cocInstance.coc_dldcedrl
                    cocCarStoragePrint.coc_dldcsccmc      =cocInstance.coc_dldcsccmc



                    //’A048290’+车型系列代号名称+合格证发证期日期的年月份+Vin后六位+CCC证书号中的后两位版本号
                    def v=cocInstance.coc_ccczsh
                    def version=v?.toString().substring(v?.length()-2,v?.length())
                    def year=zcinfoInstance.veh_Fzrq.substring(0,4)
                    def month=zcinfoInstance.veh_Fzrq.substring(5,7)
                    if(params?.newCoC){
//                   //如果是打印新版一致性证书，需要从合格证信息中取值
                        cocCarStoragePrint.coc_yzxzsbh=cocInstance?.coc_yzxzsbh  //新版一致性证书，加一致性证书编号
//                        cocCarStoragePrint.coc_rq =zcinfoInstance.veh_Clzzrq
//                        公告资源部宋华要求把coc_rq改为维护的日期，而不是从合格证上获取车辆制造日期
                        cocCarStoragePrint.coc_fzrq           =cocInstance.coc_rq
                        def coc=cocInstance?.coc_yzxzsbh    //一致性证书编号
                        def vin=zcinfoInstance.veh_Clsbdh    //合格证上的VIN信息
                        QR  qr=new QR()
                        def inStr=coc+vin
                        println(inStr)
                        def QRstring=  qr.GenQR(inStr)
//                    ########################验证二维码信息是否正确START############################
//                    def aaa=qr.DESEncrypt('A0482902AAA1046W1033001LZ0B9JB24F1090959')
//                                           A0482902AAA1046W1033001LZ0B9JB24F1090959
//                    println(aaa)
//                    println('/PE3bJhpiYrXZUPNRP4bgt8p2nNtTwe2moSHe8sSh7vofEOHm3oO2mtklZXjeT6j')
//                    def bbb=qr.DESDecrypt('/PE3bJhpiYrXZUPNRP4bgt8p2nNtTwe2moSHe8sSh7vofEOHm3oO2mtklZXjeT6j')  //通过微信将一致性证书二维码信息扫描出来的信息，将其解码查看解码后的信息是否正确
//                    ############################验证二维码是否正确END##############################
                        cocCarStoragePrint.coc_qr_code=QRstring
//                    QRpiUrl =this.generateQR(QRstring,coc,vin)  //使用第三方插件生成二维码，该方法已经弃用
                        QRpiUrl =this.generateImage(QRstring,coc,vin)
                    }else{
                        //如果是打印老版一致性证书，一致性证书还是自动生成
                        String yzxbh = 'A048290'+ cocInstance.coc_cxxldhmc+year+month+zcinfoInstance.veh_Clsbdh?.substring(zcinfoInstance.veh_Clsbdh?.length()-6)+"${version}"
                        cocCarStoragePrint.coc_yzxzsbh=yzxbh  //一致性证书编号
                        cocCarStoragePrint.coc_fzrq           =cocInstance.coc_rq
                    }

                    cocCarStoragePrint.coc_rq=zcinfoInstance.veh_Fzrq   //发证日期
                    if (Integer.valueOf(usertype)==1){ //经销商
                        cocCarStoragePrint.status=1 //此处为经销商用默认上传成功
                    }else{
                        cocCarStoragePrint.status=0 //车间的已打印的一致性信息为未上传
                    }
                    cocCarStoragePrint.save(flush: true)
                    id =  cocCarStoragePrint.id
                } else{
                    id =  cocPrint.id
                }
            }catch(Exception e){
                e.cause?e.cause.printStackTrace():e.printStackTrace()
                flag=false
            }
//        render id
            def result = [id: id, QRpiUrl: QRpiUrl]
            render result as JSON
        }
    }
    /**
     * 生成二维码
     * @param code 二维码字符串
     * @param coc一致性证书编号
     * @param vin合格证VIN信息
     * @return文件存放位置
     *  * @CreateTime 2015-05-20 zhuwei
     */

    def generateImage(imgStr,coc,vin){   //对字节数组字符串进行Base64解码并生成图片
        String path =grailsApplication.config.QR.location
        def now=new Date().getTime()
        String fileName =vin+"_"+coc+"_"+now+".png"
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成png图片
//            String imgFilePath = "D:\\QR\\1.png";//新生成的图片
            String imgFilePath =path+"\\"+fileName
            println(imgFilePath)
//            String fileName =vin+"_"+coc+"_"+now+".png"
//            def QRpiUrl=filePath+"\\"+fileName
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
//            out.write(imgStr);
            out.flush();
            out.close();
//            return true;
            return   imgFilePath
        }
        catch (Exception e)
        {
            return false;
        }
    }
    /**
     * 使用插件生成二维码 （已经弃用）
     * @param code 二维码字符串
     * @param coc一致性证书编号
     * @param vin合格证VIN信息
     * @CreateTime 2015-05-18   zhuwei
     * @return
     */
    def generateQR(code,coc,vin){
        def newcode=code.substring(0,20)
        def now=new Date().getTime()
//        String filePath = "D:\\QR";
        String filePath =grailsApplication.config.QR.location
//        String fileName = "zxing.png";
        String fileName =vin+"_"+coc+"_"+now+".png"
        println(fileName)
        JSONObject json = new JSONObject();
//        println('abc')
//        json.put('info',newcode);
//        println('def')
//        json.put("author", "shihy");
//        String content = code.toString() ;// 内容                                                    k
        String content = newcode.toString() ;// 内容
//        String content = json.toString();// 内容
        int width = 100; // 图像宽度
        int height = 100; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def   bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        def QRpiUrl=filePath+"\\"+fileName
        return QRpiUrl
    }

    /**
     * @Description 经销商异步清除已打印的一致性证书信息
     * @ zcinfoID
     * @author Xu
     */
    def jsonDelCocPr(){
        def msg = message(code: 'default.deleted.simple.message')
        CocCarStoragePrint.withTransaction {
            def flag=true
            def zcinfoIDStr = params.zcinfoID
            def cocCarStoragePrint = CocCarStoragePrint.get(zcinfoIDStr)
            if (!cocCarStoragePrint) {
                return
            }
            try {
                cocCarStoragePrint.delete(flush: true)
            }catch (Exception e) {
                flag = false
            }
            if(!flag){
                msg = message(code: 'default.not.simple.deleted.message')
            }
        }
        render msg
    }

    /**
     * @Description 经销商异步查询所选择的合格证信息 的一致性证书是否已经打印
     * @ zcinfoID
     * @author Xu
     */
    def jsonSearchCocPrn(){
        int status = 0
        def zcinfoIDStr = params.zcinfoID
        def cocPrnInstance = CocCarStoragePrint.get(zcinfoIDStr)
        if (!cocPrnInstance) {
            status = 0
        }
        try {
            if (cocPrnInstance.id!=null){
                if(cocPrnInstance.status==1){ //上传成功
                    status =2
                }else{    //未上传或上传失败
                    status =1
                }

            }
        }catch (Exception e) {
            status = 0
        }
        render status
    }
    /**
     * @Description 根据合格证编号查询所选择的合格证信息是否存在 要打印的的一致性证书是否已经打印
     * @ veh_Zchgzbh
     * @author QJ
     */
    def jsonSearchCocPrnByZchgzbh(){
        int cocstatus = 0
        def veh_Zchgzbh = params.veh_Zchgzbh
        def zcinfo = ZCInfo.findByVeh_Zchgzbh(veh_Zchgzbh);
        def zcinfoID
        if(zcinfo){
            zcinfoID = zcinfo.id
            def cocPrnInstance = CocCarStoragePrint.get(zcinfo.id)
            if (!cocPrnInstance) {
                cocstatus = 0
            }
            try {
                if (cocPrnInstance.id!=null){
                    if(cocPrnInstance.status==1){ //上传成功
                        cocstatus =2
                    }else{    //未上传或上传失败
                        cocstatus =1
                    }

                }
            }catch (Exception e) {
                cocstatus = 0
            }

        }else{
            cocstatus =3
            zcinfoID=''
        }
        def result=[:]
        result.put("zcinfoID",zcinfoID)
        result.put("cocstatus",cocstatus)
        return render (result as JSON)
    }
    /**
     *@Description 跳转到综合查询的已打印一致性证书查询
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-10
     */
    def searchPrint(){
        render(view: '/cn/com/wz/vehcert/dealer/cocprint/cocCarPrintSearch');
    }
    /**
     *@Description ajax获取列表中的所有已打印一致性证书
     *@Auther liuly
     *@createTime
     * @Update  添加按照时间条件查询已打印一致性证书的
     * @UpdateTime 2015-01-15 zhuwei
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        def cel = {
            if(params.coc_clxh){
                like("coc_clxh","%${params.coc_clxh}%")
            }
            if(params.coc_clmc){
                like("coc_clmc","%${params.coc_clmc}%")
            }
            if(params.coc_clsbdh){
                like("coc_clsbdh","%${params.coc_clsbdh}%")
            }
            if(params.createTimeStart){
                def time1=params.createTimeStart+' 00:00:00'
                ge('coc_createTime',time1)
            }
            if(params.createTimeEnd){
                def time2=params.createTimeEnd+' 23:59:59'
                le('coc_createTime',time2)
            }
        }
        def results = CocCarStoragePrint.createCriteria().list(params,cel)
        def result = [rows: results, total: results.totalCount]

        render result as JSON
    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
    /**
     *@Description 数据导出成excel
     *@Auther liuly
     *@createTime 2013-08-10
     * @Update 添加按照已打印一致性证书创建顺序查询条件
     * @updateTime 2015-01-15 zhuwei
     *
     */
    def exportexcel(){
        def datas =[]
        def lst=[]
        def content=[]
        try{
            if(params?.format && params.format != "html"){
                def encodedFilename = URLEncoder.encode(("一致性证书信息"), "UTF-8")
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
                def out=response.outputStream
                Map labels =['coc_yzxzsbh':'一致性证书编号','coc_czsl':'车轴数量','coc_clsl':'车轮数量','coc_qdzwz':'驱动轴位置','coc_zj':'轴距','coc_qyzqzj':'牵引座前置距','coc_lj':'轮距','coc_cd':'长度','coc_zqdyqyzzjl':'最前端与牵引装置的距离'
                        ,'coc_zzqycd':'装载区域长度','coc_kd':'宽度','coc_gd':'高度','coc_tymj':'车辆在地面上的投影面积','coc_qx':'前悬','coc_hx':'后悬','coc_jjj':'接近角','coc_lqj':'离去角','coc_xsxdcsclzl':'行驶下带车身的车辆质量'
                        ,'coc_edzzl':'额定总质量','coc_zlzhfp':'该质量的轴荷分配','coc_zzllyxsh':'载质量利用系数','coc_czyxzdzl':'车轴允许的最大质量','coc_kssczwz':'可伸缩车轴的位置','coc_kczczwz':'可承载车轴位置','coc_cdzdyxzh':'车顶最大允许载荷'
                        ,'coc_qygsgc':'牵引杆式挂车','coc_bgc':'半挂车','coc_zjjgc':'中间轴挂车','coc_gczdzl':'挂车的最大质量','coc_zdzhzl':'最大组合质量','coc_jcdczdczfh':'接触点处的最大垂直负荷','coc_fdjzzsmc':'发动机制造商名称'
                        ,'coc_fdjxh':'发动机型号','coc_fdjgzyl':'发动机工作原理','coc_zjps':'直接喷射','coc_qgsl':'汽缸数量','coc_qgplxs':'气缸排列形式','coc_pl':'排量','coc_rlzl':'燃料种类','coc_zdjgl':'最大净功率','coc_dyfdjzs':'对应的发动机转速'
                        ,'coc_lhqxs':'离合器形式','coc_bsqzs':'变速器形式','coc_sb':'速比','coc_zcdb':'主传动比','coc_ltgg':'轮胎规格','coc_sfazkqxg':'是否安装有空气悬挂','coc_sfzykqxgzz':'是否装有空气悬挂的装置','coc_gbthps':'钢板弹簧的片数'
                        ,'coc_zxzlxs':'转向助力形式','coc_zdzzjysm':'制动装置简要说明','coc_zdxtgqgnyl':'制动系统供气管内压力','coc_csxs':'车身形式','coc_clys':'车辆颜色','coc_gnyxrj':'罐体内有效容积','coc_hxcd':'货箱长度','coc_hxkd':'货箱宽度'
                        ,'coc_hxgd':'货箱高度','coc_qzjzdljnl':'起重机的最大力矩能力','coc_cmsl':'车门数量','coc_cmgz':'车门构造','coc_zws':'座位数','coc_cccno':'CCC证书编号','coc_sybgno':'或实验报告编号','coc_zgcs':'最高车速','coc_sj':'声级'
                        ,'coc_pqpfw':'排气排放物','coc_co2':'CO2排放','coc_jgyqsybg':'结构要求的试验报告','coc_wxhwysjb':'危险货物运输的级别','coc_dwjgyqbh':'动物的结构要求的编号','coc_mxdwysjb':'某些动物运输的级别','coc_note':'备注','coc_zsno':'H证书编号'
                        ,'coc_clsccmc':'H车辆生产厂名称','coc_clzzg':'H车辆制造国','coc_cxxldhmc':'H车型系列代号名称','coc_dydhmc':'H单元代号名称','coc_cxdhmc':'H车型代号名称','coc_cxmc':'H车型名称','coc_clzwpp':'H车辆中文品牌','coc_cllb':'H车辆类别'
                        ,'coc_zzsmc':'H制造商的名称','coc_zzsdz':'H制造商的地址','coc_fdmpwz':'H法定名牌的位置','coc_clsbdh':'H车辆识别代号','coc_sbhdkwz':'H识别号的打刻位置','coc_fdjbh':'H发动机编号','coc_fdjbhdkwz':'H发动机编号的打刻位置','coc_ccczsh':'HCCC证书号'
                        ,'coc_clxh':'H车辆型号','coc_clmc':'车辆名称','coc_rq':'H日期','coc_createTime':'创建时间']
                def cel = {
                    if(params.coc_clxh){
                        like("coc_clxh","%${params.coc_clxh}%")
                    }
                    if(params.coc_clmc){
                        like("coc_clmc","%${params.coc_clmc}%")
                    }
                    if(params.coc_clsbdh){
                        like("coc_clsbdh","%${params.coc_clsbdh}%")
                    }

                    if(params.createTimeStart){
                        def time1=params.createTimeStart+' 00:00:00'
                        ge('coc_createTime',time1)
                    }
                    if(params.createTimeEnd){
                        def time2=params.createTimeEnd+' 23:59:59'
                        le('coc_createTime',time2)
                    }

                }
                datas = CocCarStoragePrint.createCriteria().list(params,cel)
                datas.each {
                    def m=[:]
                    it.properties.each {
                        if(it.value==null){
                            it.value='NULL'
                        }
                        m."${it.key}"=it.value
                    }
                    lst.add(m)
                }

                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                def m=[]
                m.add(labels)
                content.add(lst)
                excelOp.preWriteExcel(out,null,m,["一致性证书信息"],content)
                session.setAttribute('compFlag',"success")
                out.flush()
                out.close()
            }
        }catch(Exception e){
            e.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
            lst.clear()
            content.clear()
        }
    }
}
