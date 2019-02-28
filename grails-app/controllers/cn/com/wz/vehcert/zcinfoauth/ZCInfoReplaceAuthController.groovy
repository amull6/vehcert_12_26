package cn.com.wz.vehcert.zcinfoauth

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.LogService
import cn.com.wz.parent.system.organization.Organization
import cn.com.wz.parent.system.role.Role
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.system.user.UserDetail
import cn.com.wz.vehcert.carstorage.CarStorage
import cn.com.wz.vehcert.zcinfo.PrintSet
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import cn.com.wz.vehcert.zcinfo.ZCInfo
import cn.com.wz.vehcert.zcinfo.ReplaceForSupplement
import grails.converters.JSON
import newDms.NewTransChangeInfo;
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.ExcelUtils

class ZCInfoReplaceAuthController extends BaseController {
    def exportService
    def zcInfoService
    def zcInfoReplaceService
    def dataSource;
    def newDmsSynService
    def dmsSynService
    LogService logService
    def SynService

    def index() {
        redirect(action: 'list', params: params)
    }

    /**
     * @Description 跳转到合格证更换申请list页面
     * @param
     * @return
     * @Auther Xu
     * @createTime 2013-8-2 14:44:51
     */
    def list() {
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/list')
    }
    /**
     * @Description 跳转到PDF合格证更换申请list页面
     * @param
     * @return
     * @Auther QJ
     * @createTime 2018-7-2 14:44:51
     */
    def pdfList() {
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/pdfList')
    }
    /**
     * @Description 跳转到区域经理合格证更换申请list页面
     * @param
     * @return
     * @Auther zhuwei
     * @createTime 2014-11-17
     */
    def areaAuthList() {
        def aa = params
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/areaAuthList', model: [searchType: params.searchType, msg: params?.msg])
    }
    /**
     * @Description 二次更换，区域经理审核//产品管理部审核
     * @CreateTime 2014-11-26
     * @ZCInfoUpload
     */
    def jsonListByArea() {
        ///当前登录用户信息
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def user = User.get(loginUser.id)
        def aaa = user?.roles
        def areaManager = false
        user?.roles.each {
            if (it.roleCode == 'areaManager') {    //判断是否有区域经理角色
                areaManager = true
            }
        }
        def organId = []
        user.organs.each {
            organId.add(it?.id)
        }
        params.organId = organId
        params.areaManager = areaManager

        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort = 'area_status'
        params.order = "asc"

        def results = zcInfoReplaceService.areaManagerAuthList(params, loginUser)

        def lst = []
        results.each { l ->
            def realName = User.get(l.createrId)?.userDetail?.realName
            def m = [:]
            m.id = l.id
            m.veh_Zchgzbh_0 = l.veh_Zchgzbh_0
            m.veh_Zchgzbh_0_R = l.veh_Zchgzbh_0_R
            m.veh_createTime = l.veh_createTime
            m.createrName = realName
            m.veh_Zchgzbh = l.veh_Zchgzbh
            m.veh_Zzbh = l.veh_Zzbh
            m.veh_Clsbdh = l.veh_Clsbdh
            m.veh_Cpggh = l.veh_Cpggh
            m.veh_Fdjh = l.veh_Fdjh
            m.veh_Fdjxh = l.veh_Fdjxh
            m.veh_Zchgzbh_R = l.veh_Zchgzbh_R
            m.veh_Clsbdh_R = l.veh_Clsbdh_R
            m.veh_Cpggh_R = l.veh_Cpggh_R
            m.veh_Fdjh_R = l.veh_Fdjh_R
            m.salesmanname = l.salesmanname
            m.salesmantel = l.salesmantel
            m.createTime = l.createTime
            m.veh_coc_status = l.veh_coc_status
            m.veh_Clxh = l.veh_Clxh
            m.veh_Clxh_R = l.veh_Clxh_R
            m.veh_Clmc = l.veh_Clmc
            m.veh_Clmc_R = l.veh_Clmc_R
            m.veh_Fdjxh_R = l.veh_Fdjxh_R
            m.veh_Zzbh = l.veh_Zzbh
            m.veh_Zzbh_R = l.veh_Zzbh_R
            m.veh_Fzrq = l.veh_Fzrq
            m.veh_Fzrq_R = l.veh_Fzrq_R
            m.area_status = l.area_status
            m.remark = l.remark
            m.authTime = l.authTime
            lst.add(m)

        }
        def result = [rows: lst, total: results.totalCount, searchType: params?.searchType, msg: params?.msg]
        render result as JSON

    }

    /**
     * @Description 跳转到产品管理部合格证更换申请list页面
     * @param
     * @return
     * @Auther zhuwei
     * @createTime 2014-11-17
     */
    def productAuthList() {
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/productAuthList', model: [searchType: params.searchType, msg: params?.msg])
    }
    /**
     * @Description 二次更换，区域经理审核//产品管理部审核
     * @CreateTime 2014-11-26
     * @return
     */
    def jsonListByProduct() {
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort = 'product_auth_status'
        params.order = "asc"
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def results = zcInfoReplaceService.areaManagerAuthList(params, loginUser)

        def lst = []
        results.each { l ->
            def realName = User.get(l.createrId)?.userDetail?.realName
            def m = [:]
            m.id = l.id
            m.veh_Zchgzbh_0 = l.veh_Zchgzbh_0
            m.veh_Zchgzbh_0_R = l.veh_Zchgzbh_0_R
            m.veh_createTime = l.veh_createTime
            m.createrName = realName
            m.veh_Zchgzbh = l.veh_Zchgzbh
            m.veh_Zzbh = l.veh_Zzbh
            m.veh_Clsbdh = l.veh_Clsbdh
            m.veh_Cpggh = l.veh_Cpggh
            m.veh_Fdjh = l.veh_Fdjh
            m.veh_Fdjxh = l.veh_Fdjxh
            m.veh_Zchgzbh_R = l.veh_Zchgzbh_R
            m.veh_Clsbdh_R = l.veh_Clsbdh_R
            m.veh_Cpggh_R = l.veh_Cpggh_R
            m.veh_Fdjh_R = l.veh_Fdjh_R
            m.salesmanname = l.salesmanname
            m.salesmantel = l.salesmantel
            m.createTime = l.createTime
            m.veh_coc_status = l.veh_coc_status
            m.veh_Clxh = l.veh_Clxh
            m.veh_Clxh_R = l.veh_Clxh_R
            m.veh_Clmc = l.veh_Clmc
            m.veh_Clmc_R = l.veh_Clmc_R
            m.veh_Fdjxh_R = l.veh_Fdjxh_R
            m.veh_Zzbh = l.veh_Zzbh
            m.veh_Zzbh_R = l.veh_Zzbh_R
            m.veh_Fzrq = l.veh_Fzrq
            m.veh_Fzrq_R = l.veh_Fzrq_R
            m.product_auth_status = l.product_auth_status
            m.remark = l.remark
            m.authTime = l.authTime
            lst.add(m)

        }
        def result = [rows: lst, total: results.totalCount, searchType: params?.searchType, msg: params?.msg]
        render result as JSON

    }
    /**
     * @Description 跳转到区域经理审核页面
     * @CreateTime 2014-11-26 zhuwei
     */
    def productAuthView() {
        def params = params
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        def replaceForSupplement = ReplaceForSupplement.findByZcinfoReplaceId(params.id)
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        if (!zcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "productAuthList", params: params)
            return
        }
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/productAuth', model: [zcinfoReAuInstance: zcinfoReAuInstance, zcinfoReAuInstanceold: zcinfoReAuInstance, replaceForSupplement: replaceForSupplement])
    }

    /**
     * 合格证更换申请审核不通过
     * @return
     * @author Xu
     * update Qj修改产品部不通过总标记变为不通过z
     */
    def productAudit() {
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        if (!zcinfoReAuInstance) {
            def msg = '未找到相应更换换记录'
            params.msg = msg
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "productAuthList", params: params)
            return
        }
        zcinfoReAuInstance.product_auth_Remark = params.product_auth_Remark
        if (params?.pass == 'Y') {
            zcinfoReAuInstance.product_auth_status = 1
            zcinfoReAuInstance.veh_coc_status = 0 //区域经理审核通过，将最终审核标记由3置为 0
        } else if (params?.pass == 'N') {
            zcinfoReAuInstance.product_auth_status = 2       //区域经理审核不通过 ,veh_coc_status仍然为3
            zcinfoReAuInstance.veh_coc_status = 2
        }
        zcinfoReAuInstance.product_auth_Id = getCurrentUser()?.getId()
        zcinfoReAuInstance.setProduct_authTime(DateUtil.getCurrentTime('yyyy-MM-dd'))

        def msg = '审核成功'
        if (!zcinfoReAuInstance.save(flush: true)) {
            msg = '审核失败'
        }
        params.msg = msg
        flash.message = message(code: 'default.operate.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), zcinfoReAuInstance.veh_Xnhgzbh])
        redirect(action: "productAuthList", params: params)

    }
    /**
     * @Description 二次更换区域经理、产品管理部导出方法
     * @return
     * @Auther Xu
     */
    def rePlaceTwoTimesExport() {
        def rows = []
        def lst = []
        def content = []
        try {
            if (params?.format && params.format != "html") {
                def encodedFilename = URLEncoder.encode(("合格证二次更换审核"), "UTF-8")
                def filename = ""
                def userAgent = request.getHeader("User-Agent")
                if (userAgent =~ /MSIE [4-8]/) {
                    // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename=\"${encodedFilename}." + params.extension + "\""
                } else {
                    // Use RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename*=UTF-8''${encodedFilename}." + params.extension
                }
                response.setHeader("Content-disposition", "attachment;${filename}");
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                Map labels = ["createTime"   : "申请日期", "createrId": "经销商", "veh_Zchgzbh": "旧合格证编号", "veh_Clsbdh": "旧车架号", "veh_Clxh": "旧公告号", "veh_Fdjh": "旧发动机号", "veh_Fdjxh": "旧发动机型号",
                              "veh_Zchgzbh_R": "新合格证编号", "veh_Clsbdh_R": "新车架号", "veh_Clxh_R": "新公告号", "veh_Fdjh_R": "新发动机号", "veh_Fdjxh_R": "新发动机型号", "salesmanname": "业务员姓名", "salesmantel": "业务员电话", "createTime": "变更日期"]
                def out = response.outputStream
                params.sort = 'createTime'
                params.order = "desc"
                User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
                rows = zcInfoReplaceService.areaManagerAuthList(params, loginUser)
                rows.each { r ->
                    def m = [:]
                    m.veh_Zchgzbh = r.veh_Zchgzbh
                    m.veh_Clsbdh = r.veh_Clsbdh
                    m.veh_Clxh = r.veh_Clxh
                    m.veh_Fdjh = r.veh_Fdjh
                    m.veh_Fdjxh = r.veh_Fdjxh
                    m.veh_Zchgzbh_R = r.veh_Zchgzbh_R
                    m.veh_Clsbdh_R = r.veh_Clsbdh_R
                    m.veh_Clxh_R = r.veh_Clxh_R
                    m.veh_coc_status = r.veh_coc_status
                    m.veh_Fdjh_R = r.veh_Fdjh_R
                    m.veh_Fdjxh_R = r.veh_Fdjxh_R
                    m.salesmanname = r.salesmanname
                    m.salesmantel = r.salesmantel
                    m.createTime = r.createTime
                    m.veh_createTime = r.veh_createTime
                    def userC = User.get(r.createrId)
                    if (userC) {
                        m.createrId = userC.userDetail.realName
                    } else {
                        m.createrId = ''
                    }
                    m.veh_Zzbh = r.veh_Zzbh
                    m.remark = r.remark

                    lst.add(m)
                }
                ExcelUtils excelOp = new ExcelUtils(grailsApplication.config.server.file.encode);
                def m = []
                m.add(labels)
                content.add(lst)
                excelOp.preWriteExcel(out, null, m, ["合格证审核"], content)
                session.setAttribute('compFlag', "success")
                out.flush()
                out.close()
            }
        } catch (Exception e) {
            e.printStackTrace()
            session.setAttribute('compFlag', "error")
        } finally {
            rows.clear()
            lst.clear()
            content.clear()
        }
    }
    /**
     * @Description 跳转到合格证更换申请list页面 （五征用）
     * @param
     * @return
     * @Auther xu
     * @createTime
     */
    def chaxunlist() {
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/chaxunlist')
    }

    /**
     * @Description 跳转到需要修改（撤销）的合格证信息查询 （公告资源部用）
     * @param
     * @return
     * @Auther Xu
     * @createTime
     */
    def amendbacklist() {
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/amendbacklist')
    }

    /**
     * 合格证更换申请审核页面
     * @author Xu
     * @return
     */
    def auditView() {
        def params = params
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        if (!zcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "list", params: params)
            return
        }
        //判定要修改的记录是否存在
        def printSet = PrintSet.findByUserid(loginUser.id)
        if (!printSet) {
            printSet = new PrintSet()
        }
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/genghuanshenhe', model: [zcinfoReAuInstance: zcinfoReAuInstance, printSet: printSet, zcinfoReAuInstanceold: zcinfoReAuInstance])
    }
    /**
     * @Description 跳转到区域经理审核页面
     * @CreateTime 2014-11-26 zhuwei
     */
    def areaAuthView() {
        def params = params
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        def replaceForSupplement = ReplaceForSupplement.findByZcinfoReplaceId(params.id)
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        if (!zcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "areaAuthList", params: params)
            return
        }
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/areaAuth', model: [zcinfoReAuInstance: zcinfoReAuInstance, zcinfoReAuInstanceold: zcinfoReAuInstance, replaceForSupplement: replaceForSupplement])
    }
    /**
     * 合格证更换申请审核不通过
     * @return
     * @author Xu
     * update Qj修改产品部不通过总标记变为不通过z
     */
    def areaAudit() {
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        if (!zcinfoReAuInstance) {
            def msg = '未找到相应更换换记录'
            params.msg = msg
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "areaAuthList", params: params)
            return
        }
        zcinfoReAuInstance.area_Remark = params.area_Remark
        if (params?.pass == 'Y') {
            zcinfoReAuInstance.area_status = 1
            zcinfoReAuInstance.product_auth_status = 0 //区域经理审核通过，将产品管理部审核标记由3置为 0
        } else if (params?.pass == 'N') {
            zcinfoReAuInstance.area_status = 2       //区域经理审核不通过
            zcinfoReAuInstance.veh_coc_status = 2
        }
        zcinfoReAuInstance.area_AuthId = getCurrentUser()?.getId()
        zcinfoReAuInstance.setArea_AuthTime(DateUtil.getCurrentTime('yyyy-MM-dd'))

        def msg = '审核成功'
        if (!zcinfoReAuInstance.save(flush: true)) {
            msg = '审核失败'
        }
        params.msg = msg
        flash.message = message(code: 'default.operate.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), zcinfoReAuInstance.veh_Xnhgzbh])
        redirect(action: "areaAuthList", params: params)

    }
    /**
     * 合格证申请详情查看
     * @author Xu
     * @return
     */
    def genghuanShow() {
        def params = params
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        if (!zcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "chaxunlist", params: params)
            return
        }
        render(view: '/cn/com/wz/vehcert/zcinfoauth/zcinforeplaceauth/genghuanshow', model: [zcinfoReAuInstance: zcinfoReAuInstance, type: params.type])
    }
    /**
     * 合格证更换申请审核不通过
     * @return
     * @author Xu
     */
    def auditNoPass() {
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        if (!zcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "list", params: params)
            return
        }
        zcinfoReAuInstance.auth_Remark = params.authRemark
        zcinfoReAuInstance.veh_coc_status = 2  //不通过
        zcinfoReAuInstance.authId = getCurrentUser()?.getId()
        zcinfoReAuInstance.setAuthTime(DateUtil.getCurrentTime('yyyy-MM-dd'))
        zcinfoReAuInstance.save(flush: true)

        flash.message = message(code: 'default.operate.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), zcinfoReAuInstance.veh_Xnhgzbh])
        redirect(action: "list", params: params)

    }
    /**
     * @Description 全项点击的时候重新选择数据
     * @return
     * @Create 2013-08-26 huxx
     */
    def jsonChangeData() {
        def map = [:]
        if (params.type == 'QX') {
            ZCInfoReplace zcinforeplace = ZCInfoReplace.get(params.id)
            map.put("veh_Zchgzbh", zcinforeplace?.veh_Zchgzbh_R)
            map.put("veh_Fdjxh", zcinforeplace?.veh_Fdjxh_R)  //发动机型号
            map.put("veh_zdjgl", zcinforeplace?.veh_zdjgl_R)  //净功率
            map.put("veh_Clfl", zcinforeplace?.veh_Clfl_R)    //车辆类型
            map.put("veh_Clxh", zcinforeplace?.veh_Clxh_R) //车辆型号
            map.put("veh_Zj", zcinforeplace?.veh_Zj_R)  //轴距
            map.put("veh_Pl", zcinforeplace?.veh_Pl_R)  //排量
            map.put("veh_Gl", zcinforeplace?.veh_Gl_R)   //功率
            map.put("veh_Zzl", zcinforeplace?.veh_Zzl_R)     //总质量
            map.put("veh_Clmc", zcinforeplace?.veh_Clmc_R)  //车辆名称
            map.put("veh_Edzzl", zcinforeplace?.veh_Edzzl_R) //额定载质量
            map.put("veh_Pfbz", zcinforeplace?.veh_Pfbz_R)  //排放标准
            map.put("veh_Hxnbc", zcinforeplace?.veh_Hxnbc_R) //货厢内部尺寸
            map.put("veh_Hxnbk", zcinforeplace?.veh_Hxnbk_R)
            map.put("veh_Hxnbg", zcinforeplace?.veh_Hxnbg_R)
            map.put("veh_Zxxs", zcinforeplace?.veh_Zxxs_R)   //转向形式
            map.put("veh_Zbzl", zcinforeplace?.veh_Zbzl_R)   //整备质量
            map.put("veh_Zh", zcinforeplace?.veh_Zh_R)     //轴荷
            map.put("veh_Wkc", zcinforeplace?.veh_Wkc_R)      //外廓尺寸
            map.put("veh_Wkk", zcinforeplace?.veh_Wkk_R)
            map.put("veh_Wkg", zcinforeplace?.veh_Wkg_R)
            map.put("veh_Zqyzzl", zcinforeplace?.veh_Zqyzzl_R)    //准牵引总质量
            map.put("veh_Edzk", zcinforeplace?.veh_Edzk_R)      //额定载客
            map.put("veh_Zzllyxs", zcinforeplace?.veh_Zzllyxs_R)     //载质量利用系数
            map.put("veh_Qyqtxx", zcinforeplace?.veh_Qyqtxx_R)       //车辆制造企业其他信息
            map.put("veh_Dpid", zcinforeplace?.veh_Dpid_R)      //底盘ID
            map.put("veh_Dpxh", zcinforeplace?.veh_Dpxh_R)     //底盘型号
            map.put("veh_Jsslx", zcinforeplace?.veh_Jsslx_R)   //驾驶室类型
            map.put("veh_Bgcazzdyxzzl", zcinforeplace?.veh_Bgcazzdyxzzl_R)       //半挂车鞍座最大允许总质量
            map.put("veh_clzzqyxx", zcinforeplace?.veh_clzzqyxx_R)      //车辆制造企业信息
            map.put("veh_Yh", zcinforeplace?.veh_Yh_R)     //油耗
            map.put("veh_Zs", zcinforeplace?.veh_Zs_R)     //轴数
            map.put("veh_Ggpc", zcinforeplace?.veh_Ggpc_R)   //公告批次
            map.put("veh_Cpggh", zcinforeplace?.veh_Cpggh_R)       //产品公告号
            map.put("veh_Ggsxrq", zcinforeplace?.veh_Ggsxrq_R)             //公告生效日期
            map.put("veh_Gbthps", zcinforeplace?.veh_Gbthps_R)            //钢板弹簧片数
            map.put("veh_Zj", zcinforeplace?.veh_Zj_R)
            map.put("veh_Clpp", zcinforeplace?.veh_Clpp_R)      //车辆品牌
            map.put("veh_Rlzl", zcinforeplace?.veh_Rlzl_R)      //燃料种类
            map.put("veh_Zgcs", zcinforeplace?.veh_Zgcs_R)   //最高车速

            map.put("veh_Lts", zcinforeplace?.veh_Lts_R)        //轮胎数
            map.put("veh_Ltgg", zcinforeplace?.veh_Ltgg_R)        //轮胎规格
            map.put("veh_Qlj", zcinforeplace?.veh_Qlj_R)       //前轮距
            map.put("veh_Hlj", zcinforeplace?.veh_Hlj_R)       //后轮距
            map.put("veh_Jsszcrs", zcinforeplace?.veh_Jsszcrs_R)   //驾驶室准乘人数

            map.put("veh_Clzzqymc", zcinforeplace?.veh_Clzzqymc_R)  //车辆制造企业名称
            map.put("veh_Cpscdz", zcinforeplace?.veh_Cpscdz_R)  //产品生产地
            map.put("veh_Qybz", zcinforeplace?.veh_Qybz_R)    //企业备注
            map.put("veh_Bz", zcinforeplace?.veh_Bz_R)        //备注
            map.put("veh_Qyid", zcinforeplace?.veh_Qyid_R)   //企业ID
            map.put("veh_pzxlh", zcinforeplace?.veh_pzxlh_R)  //配置序列号
            map.put("veh_Hzdfs", zcinforeplace?.veh_Hzdfs_R)
            map.put("veh_Jss", zcinforeplace?.veh_Jss_R)                  //驾驶室
            map.put("veh_VinFourBit", zcinforeplace?.veh_VinFourBit_R)    //vin第四位

        } else if (params.type == 'DP') {
//            def hgz = ZCInfo.findByVeh_ClsbdhAndVeh_Clztxx("${params.vin}", "DP")
//            if (hgz) {
//                map.put("veh_Zchgzbh", hgz?.veh_Zchgzbh)
//                map.put("veh_Fdjxh", hgz?.veh_Fdjxh)
//                map.put("veh_zdjgl", hgz?.veh_zdjgl)
//                map.put("veh_Clfl", hgz?.veh_Clfl)
//                map.put("veh_Clxh", hgz?.veh_Clxh) //车辆型号
//                map.put("veh_Zj", hgz?.veh_Zj)  //轴距
//                map.put("veh_Pl", hgz?.veh_Pl)  //排量
//                map.put("veh_Gl", hgz?.veh_Gl)   //功率
//                map.put("veh_Zzl", hgz?.veh_Zzl)     //总质量
//                map.put("veh_Clmc", hgz?.veh_Clmc)  //车辆名称
//                map.put("veh_Edzzl", hgz?.veh_Edzzl) //额定载质量
//                map.put("veh_Pfbz", hgz?.veh_Pfbz)  //排放标准
//                map.put("veh_Hxnbc", hgz?.veh_Hxnbc) //货厢内部尺寸
//                map.put("veh_Hxnbk", hgz?.veh_Hxnbk)
//                map.put("veh_Hxnbg", hgz?.veh_Hxnbg)
//                map.put("veh_Zxxs", hgz?.veh_Zxxs)   //转向形式
//                map.put("veh_Zbzl", hgz?.veh_Zbzl)   //整备质量
//                map.put("veh_Zh", hgz?.veh_Zh)     //轴荷
//                map.put("veh_Wkc", hgz?.veh_Wkc)      //外廓尺寸
//                map.put("veh_Wkk", hgz?.veh_Wkk)
//                map.put("veh_Wkg", hgz?.veh_Wkg)
//                map.put("veh_Zqyzzl", hgz?.veh_Zqyzzl)    //准牵引总质量
//                map.put("veh_Edzk", hgz?.veh_Edzk)      //额定载客
//                map.put("veh_Zzllyxs", hgz?.veh_Zzllyxs)     //载质量利用系数
//                map.put("veh_Qyqtxx", hgz?.veh_Qyqtxx)       //车辆制造企业其他信息
//                map.put("veh_Dpid", hgz?.veh_Dpid)      //底盘ID
//                map.put("veh_Dpxh", hgz?.veh_Dpxh)     //底盘型号
//                map.put("veh_Jsslx", hgz?.veh_Jsslx)   //驾驶室类型
//                map.put("veh_Bgcazzdyxzzl", hgz?.veh_Bgcazzdyxzzl)       //半挂车鞍座最大允许总质量
//                map.put("veh_clzzqyxx", hgz?.veh_clzzqyxx)      //车辆制造企业信息
//                map.put("veh_Yh", hgz?.veh_Yh)     //油耗
//                map.put("veh_Zs", hgz?.veh_Zs)     //轴数
//                map.put("veh_Ggpc", hgz?.veh_Ggpc)   //公告批次
//                map.put("veh_Cpggh", hgz?.veh_Cpggh)       //产品公告号
//                map.put("veh_Ggsxrq", hgz?.veh_Ggsxrq)             //公告生效日期
//                map.put("veh_Gbthps", hgz?.veh_Gbthps)            //钢板弹簧片数
//                map.put("veh_Zj", hgz?.veh_Zj)
//                map.put("veh_Clpp", hgz?.veh_Clpp)
//                map.put("veh_Rlzl", hgz?.veh_Rlzl)
//                map.put("veh_Zgcs", hgz?.veh_Zgcs)
//                map.put("veh_Lts", hgz?.veh_Lts)
//
//                map.put("veh_Ltgg", hgz?.veh_Ltgg)
//                map.put("veh_Qlj", hgz?.veh_Qlj)
//                map.put("veh_Hlj", hgz?.veh_Hlj)
//                map.put("veh_Jsszcrs", hgz?.veh_Jsszcrs)
//
//                map.put("veh_Clzzqymc", hgz?.veh_Clzzqymc)  //车辆制造企业名称
//                map.put("veh_Cpscdz", hgz?.veh_Cpscdz)  //产品生产地
//                map.put("veh_Qybz", hgz?.veh_Qybz)    //企业备注
//                map.put("veh_Bz", hgz?.veh_Bz)        //备注
//                map.put("veh_Qyid", hgz?.veh_Qyid)   //企业ID
//                map.put("veh_pzxlh", hgz?.veh_pzxlh)  //配置序列号
//                map.put("veh_Hzdfs", hgz?.veh_Hzdfs)
//                map.put("veh_Jss", hgz?.veh_Jss)                  //驾驶室
//                map.put("veh_VinFourBit", hgz?.veh_VinFourBit)    //vin第四位
//            } else {
                def replaceDp = ReplaceForSupplement.findByZcinfoReplaceId("${params.id}")
                map.put("veh_Zchgzbh", replaceDp?.veh_Zchgzbh_R)
                map.put("veh_Fdjxh", replaceDp?.veh_Fdjxh_R)  //发动机型号
                map.put("veh_zdjgl", replaceDp?.veh_zdjgl_R)  //净功率
                map.put("veh_Clfl", replaceDp?.veh_Clfl_R)    //车辆类型
                map.put("veh_Clxh", replaceDp?.veh_Clxh_R) //车辆型号
                map.put("veh_Zj", replaceDp?.veh_Zj_R)  //轴距
                map.put("veh_Pl", replaceDp?.veh_Pl_R)  //排量
                map.put("veh_Gl", replaceDp?.veh_Gl_R)   //功率
                map.put("veh_Zzl", replaceDp?.veh_Zzl_R)     //总质量
                map.put("veh_Clmc", replaceDp?.veh_Clmc_R)  //车辆名称
                map.put("veh_Edzzl", replaceDp?.veh_Edzzl_R) //额定载质量
                map.put("veh_Pfbz", replaceDp?.veh_Pfbz_R)  //排放标准
                map.put("veh_Hxnbc", replaceDp?.veh_Hxnbc_R) //货厢内部尺寸
                map.put("veh_Hxnbk", replaceDp?.veh_Hxnbk_R)
                map.put("veh_Hxnbg", replaceDp?.veh_Hxnbg_R)
                map.put("veh_Zxxs", replaceDp?.veh_Zxxs_R)   //转向形式
                map.put("veh_Zbzl", replaceDp?.veh_Zbzl_R)   //整备质量
                map.put("veh_Zh", replaceDp?.veh_Zh_R)     //轴荷
                map.put("veh_Wkc", replaceDp?.veh_Wkc_R)      //外廓尺寸
                map.put("veh_Wkk", replaceDp?.veh_Wkk_R)
                map.put("veh_Wkg", replaceDp?.veh_Wkg_R)
                map.put("veh_Zqyzzl", replaceDp?.veh_Zqyzzl_R)    //准牵引总质量
                map.put("veh_Edzk", replaceDp?.veh_Edzk_R)      //额定载客
                map.put("veh_Zzllyxs", replaceDp?.veh_Zzllyxs_R)     //载质量利用系数
                map.put("veh_Qyqtxx", replaceDp?.veh_Qyqtxx_R)       //车辆制造企业其他信息
                map.put("veh_Dpid", replaceDp?.veh_Dpid_R)      //底盘ID
                map.put("veh_Dpxh", replaceDp?.veh_Dpxh_R)     //底盘型号
                map.put("veh_Jsslx", replaceDp?.veh_Jsslx_R)   //驾驶室类型
                map.put("veh_Bgcazzdyxzzl", replaceDp?.veh_Bgcazzdyxzzl_R)       //半挂车鞍座最大允许总质量
                map.put("veh_clzzqyxx", replaceDp?.veh_clzzqyxx_R)      //车辆制造企业信息
                map.put("veh_Yh", replaceDp?.veh_Yh_R)     //油耗
                map.put("veh_Zs", replaceDp?.veh_Zs_R)     //轴数
                map.put("veh_Ggpc", replaceDp?.veh_Ggpc_R)   //公告批次
                map.put("veh_Cpggh", replaceDp?.veh_Cpggh_R)       //产品公告号
                map.put("veh_Ggsxrq", replaceDp?.veh_Ggsxrq_R)             //公告生效日期
                map.put("veh_Gbthps", replaceDp?.veh_Gbthps_R)            //钢板弹簧片数
                map.put("veh_Zj", replaceDp?.veh_Zj_R)
                map.put("veh_Clpp", replaceDp?.veh_Clpp_R)      //车辆品牌
                map.put("veh_Rlzl", replaceDp?.veh_Rlzl_R)      //燃料种类
                map.put("veh_Zgcs", replaceDp?.veh_Zgcs_R)   //最高车速

                map.put("veh_Lts", replaceDp?.veh_Lts_R)        //轮胎数
                map.put("veh_Ltgg", replaceDp?.veh_Ltgg_R)        //轮胎规格
                map.put("veh_Qlj", replaceDp?.veh_Qlj_R)       //前轮距
                map.put("veh_Hlj", replaceDp?.veh_Hlj_R)       //后轮距
                map.put("veh_Jsszcrs", replaceDp?.veh_Jsszcrs_R)   //驾驶室准乘人数

                map.put("veh_Clzzqymc", replaceDp?.veh_Clzzqymc_R)  //车辆制造企业名称
                map.put("veh_Cpscdz", replaceDp?.veh_Cpscdz_R)  //产品生产地
                map.put("veh_Qybz", replaceDp?.veh_Qybz_R)    //企业备注
                map.put("veh_Bz", replaceDp?.veh_Bz_R)        //备注
                map.put("veh_Qyid", replaceDp?.veh_Qyid_R)   //企业ID
                map.put("veh_pzxlh", replaceDp?.veh_pzxlh_R)  //配置序列号
                map.put("veh_Hzdfs", replaceDp?.veh_Hzdfs_R)
                map.put("veh_Jss", replaceDp?.veh_Jss_R)                  //驾驶室
                map.put("veh_VinFourBit", replaceDp?.veh_VinFourBit_R)    //vin第四位
//            }
        }
        def result = map as JSON
        render result

    }

    /**
     * 异步合格证更换申请查询
     * @return
     * @author Xu
     */
    def jsonList() {
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort = 'createTime'
        params.order = "desc"
        //判断是否根据片区进行查询
        def distributor = ''
        if (params.distributor) {
            distributor = params.distributor.id
        }
        //经销商名称
        def userR = ''
        if (params.user) {
            userR = params.user.id
        }

        def user = []
        def celU = {
            organs {
                if (params.distributor) {
                    eq('id', distributor)
                }
            }
            userDetail {
                if (params.user) {
                    eq('id', userR)
                }
            }
        }
        user = User.createCriteria().list(celU)

        def cel = {
            if (params.distributor && !distributor == '') {
                if (user.size() >= 1) {
                    user.each { u ->
                        eq('createrId', u.id)
                    }
                } else {
                    eq('createrId', ' ')
                }
            }
            if (params.veh_Cjh) {
                like("veh_Cjh", "%${params.veh_Cjh}%")
            }
            if (params.veh_Fdjh) {
                like("veh_Fdjh", "%${params.veh_Fdjh}%")
            }
            if (params.firstTime) {
                ge('createTime', params.firstTime)
            }
            if (params.secondTime) {
                le('createTime', params.secondTime)
            }
            eq('veh_coc_status', 0)  //未审核
            eq('veh_usertype', 0)   //只查询经销商的更换申请
        }
        def results = ZCInfoReplace.createCriteria().list(params, cel)
        def lst = []
        results.each { l ->
            def m = [:]
            m.id = l.id
            m.veh_createTime = l.veh_createTime
            m.createrId = User.get(l.createrId)?.userDetail?.realName
            m.veh_Zchgzbh = l.veh_Zchgzbh
            m.veh_Clsbdh = l.veh_Clsbdh
            m.veh_Cpggh = l.veh_Cpggh
            m.veh_Fdjh = l.veh_Fdjh
            m.veh_Zchgzbh_R = l.veh_Zchgzbh_R
            m.veh_Clsbdh_R = l.veh_Clsbdh_R
            m.veh_Cpggh_R = l.veh_Cpggh_R
            m.veh_Fdjh_R = l.veh_Fdjh_R
            m.salesmanname = l.salesmanname
            m.salesmantel = l.salesmantel
            m.createTime = l.createTime
            m.veh_coc_status = l.veh_coc_status
            m.veh_Clxh = l.veh_Clxh
            m.veh_Clxh_R = l.veh_Clxh_R
            lst.add(m)
        }
        def result = [rows: lst, total: results.totalCount]
        render result as JSON
    }

    /**
     * @Description 审核导出功能
     * @return
     * @Auther Xu
     */
    def shenheexport() {
        def rows = []
        def lst = []
        def content = []
        try {
            if (params?.format && params.format != "html") {
                def encodedFilename = URLEncoder.encode(("合格证更换审核"), "UTF-8")
                def filename = ""
                def userAgent = request.getHeader("User-Agent")
                if (userAgent =~ /MSIE [4-8]/) {
                    // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename=\"${encodedFilename}." + params.extension + "\""
                } else {
                    // Use RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename*=UTF-8''${encodedFilename}." + params.extension
                }
                response.setHeader("Content-disposition", "attachment;${filename}");
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                Map labels = ["createTime"   : "申请日期", "createrId": "经销商", "veh_Zchgzbh": "旧合格证编号", "veh_Clsbdh": "旧车架号", "veh_Clxh": "旧公告号", "veh_Fdjh": "旧发动机号", "veh_Fdjxh": "旧发动机型号",
                              "veh_Zchgzbh_R": "新合格证编号", "veh_Clsbdh_R": "新车架号", "veh_Clxh_R": "新公告号", "veh_Fdjh_R": "新发动机号", "veh_Fdjxh_R": "新发动机型号", "salesmanname": "业务员姓名", "salesmantel": "业务员电话", "createTime": "变更日期"]
                def out = response.outputStream
                params.sort = 'createTime'
                params.order = "desc"
                User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
                rows = zcInfoReplaceService.selectZcInfoReplace(params, loginUser)
                rows.each { r ->
                    def m = [:]
                    m.veh_Zchgzbh = r.veh_Zchgzbh
                    m.veh_Clsbdh = r.veh_Clsbdh
                    m.veh_Clxh = r.veh_Clxh
                    m.veh_Fdjh = r.veh_Fdjh
                    m.veh_Fdjxh = r.veh_Fdjxh
                    m.veh_Zchgzbh_R = r.veh_Zchgzbh_R
                    m.veh_Clsbdh_R = r.veh_Clsbdh_R
                    m.veh_Clxh_R = r.veh_Clxh_R
                    m.veh_coc_status = r.veh_coc_status
                    m.veh_Fdjh_R = r.veh_Fdjh_R
                    m.veh_Fdjxh_R = r.veh_Fdjxh_R
                    m.salesmanname = r.salesmanname
                    m.salesmantel = r.salesmantel
                    m.createTime = r.createTime
                    m.veh_createTime = r.veh_createTime
                    def userC = User.get(r.createrId)
                    if (userC) {
                        m.createrId = userC.userDetail.realName
                    } else {
                        m.createrId = ''
                    }
                    m.veh_Zzbh = r.veh_Zzbh
                    m.remark = r.remark

                    lst.add(m)
                }
                ExcelUtils excelOp = new ExcelUtils(grailsApplication.config.server.file.encode);
                def m = []
                m.add(labels)
                content.add(lst)
                excelOp.preWriteExcel(out, null, m, ["合格证审核"], content)
                session.setAttribute('compFlag', "success")
                out.flush()
                out.close()
            }
        } catch (Exception e) {
            e.printStackTrace()
            session.setAttribute('compFlag', "error")
        } finally {
            rows.clear()
            lst.clear()
            content.clear()
        }
    }

    /**
     * 合格证更换申请查询 需要修改（撤销）的合格证信息异步查询
     * @return
     * @author Xu
     */
    def jsonAmendbackList() {
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort = 'createTime'
        params.order = "desc"
        List codeList =[]
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def userId = loginUser.id
        User user  = User.findById(userId)
        def roles = user.roles
        roles.each{role->
            def roleCode = role.roleCode
            codeList.add(roleCode)
        }
        def flag
        if(codeList!=null&&codeList.size()!=0){
            if(codeList.contains('carUpload')&&codeList.contains('farmVehicleUpload')){
                flag=0
            }else{
                if(codeList.contains('carUpload')){
                    flag=1
                }else if(codeList.contains('farmVehicleUpload')){
                    flag=2
                }else{
                    flag=0
                }
            }
        }else{
            flag=0
        }
        def cel = {
            if(flag==1){
                eq("veh_Type","0")
            }else if(flag==2){
                eq("veh_Type","1")
            }
            //判断是否换号
            if ("1".equals(params.changeType)) {
                sqlRestriction("veh_Zchgzbh<>veh_Zchgzbh_R")
            } else if ("0".equals(params.changeType)) {
                sqlRestriction("veh_Zchgzbh=veh_Zchgzbh_R")
            }
            if (params.veh_Clsbdh) {
                like('veh_Clsbdh', "%${params.veh_Clsbdh}%")
            }
            if (params.veh_Fdjh) {
                like('veh_Fdjh', "%${params.veh_Fdjh}%")
            }
            ne('change_Field', 'veh_Fzrq')
            if (params.veh_managestatus) {
                eq('veh_managestatus', Integer.valueOf(params.veh_managestatus))
            }
            or {
                and {
                    eq('veh_usertype', 1) //公告资源部
                    eq('veh_isupload', 1)
                }
                and {
                    eq('veh_usertype', 0)  //经销商
                    eq('veh_isupload', 1)  //已经上传国家
                    eq('veh_coc_status', 1)  //审核通过
                }
            }

        }
        def results = ZCInfoReplace.createCriteria().list(params, cel)
        def result = [rows: results, total: results.totalCount]
        render result as JSON
    }

    /**
     * @Description 验证合格证的公告信息是否符合公告库中的信息
     * @Create 2013-11-23 huxx
     */
    def check() {
        def result = [:]
        def msg = "验证通过！"
        def flag = 'sucessed'
        //判断合格证信息是否与公告库中的信息匹配
        def zcinfoReAuInstance = new ZCInfoReplace(params)
        def tempZcinfo = new ZCInfo()
        tempZcinfo = perfectZcinfo(tempZcinfo, zcinfoReAuInstance, params.printType)
        def checkMessage = zcInfoService.checkCarStorage(tempZcinfo)
        if (checkMessage) {
            msg = checkMessage
            flag = 'failed'
            result.put("msg", msg)
            result.put("flag", flag)
        } else {
            result.put("msg", msg)
            result.put("flag", flag)

        }

        render(result as JSON)
    }
    /**
     * @Description 验证合格证的轮胎规格是否符合公告库中的信息
     * @Create 2017-8-21 huxx
     */
    def checkLtgg() {
        def result = [:]
        def msg = "验证通过！"
        def flag = 'sucessed'
        //判断合格证信息是否与公告库中的信息匹配
        def zcinfoReAuInstance = new ZCInfoReplace(params)
        def tempZcinfo = new ZCInfo()
        tempZcinfo = perfectZcinfo(tempZcinfo, zcinfoReAuInstance, params.printType)
        def checkMessage = zcInfoService.checkLtgg(tempZcinfo)
        if (checkMessage) {
            msg = checkMessage
            flag = 'failed'
            result.put("msg", msg);
            result.put("flag", flag)

        } else {
            result.put("msg", msg)
            result.put("flag", flag)
        }

        render(result as JSON)
    }
    /**
     * @Description 验证合格证的公告批次是否符合公告库中的信息
     * @Create 2017-8-21 huxx
     */
    def checkGgpc() {
        def result = [:]
        def msg = "验证通过！"
        def flag = 'sucessed'
        def checkMessage = zcInfoReplaceService.checkGgpc(params)
        if (checkMessage) {
            msg = checkMessage
            flag = 'failed'
            result.put("msg", msg);
            result.put("flag", flag)

        } else {
            result.put("msg", msg)
            result.put("flag", flag)
        }

        render(result as JSON)
    }
    /**
     * @Description 审核成功调用的方法ajax调用
     * @param
     * @return
     * @Auther Xu
     * @update 2013-09-06 huxx 添加调用PDF打印的代码，并添加事务,先打印pdf再保存，
     * @Update 2013-10-20 huxx 修改判定veh_isupload的条件为在保存时，从旧合格证中判断变更记录是否需要在【需要修改（撤销）的合格证信息】中显示，
     * 原合格证已上传的显示veh_isUpload=1，原合格证未上传的显示veh_isUpload=0
     * @Update 2013-11-11 huxx 当整车和底盘都要时，打印底盘时保存审核时间
     * @Update 2013-11-13 huxx 添加合格证信息与分解前的公告信息匹配验证
     * @update 2013-11-18 huxx 修改为在打印PDF前验证公告信息是否匹配
     * @Update 2014-03-12 huxx 修改判定合格证更换记录是否已上传，添加了条件web_status=3的情况
     */
    def update() {
        def result = [:]
        def msg = '保存成功！'
        def flag = 'sucessed'
        def dpPass = 0
        def qxPass = 0
        def pass = 0
        def dd = params.zcinforeid
        //判定要审核的记录是否存在  (防止底盘信息覆盖整车信息)
        def t = ZCInfoReplace.get(params.zcinforeid)
        def zcinfoReAuInstance = new ZCInfoReplace()
        zcinfoReAuInstance.properties = t.properties
        zcinfoReAuInstance.id = t.id

        /*判断合格证编号是否合法
         *处理逻辑：根据原整车合格证的车辆识别代码获取原合格证的底盘合格证，
         * 1、如果新输入的整车合格证号是原整车合格证号或zcinfo表中不存在的新合格证编号为合法的合格证编号。
         * 2、如果输入的是底盘合格编号是原底盘合格证号或zcinfo表中不存在的新合格证编号为合法的合格证编号。
         */
        //查询新的合格证信息是否存在
        def newZcinfo = ZCInfo.findByVeh_Zchgzbh("${params.veh_Zchgzbh_R}")
        def oldDP = ZCInfo.findByVeh_ClsbdhAndVeh_Clztxx(params.veh_Clsbdh, "DP")
        //判定当前合格证编号是原整车合格证编号或原底盘合格证编号
        if (oldDP?.veh_Zchgzbh == params.veh_Zchgzbh_R && params.veh_Clztxx_R == "DP") {

        } else if (params.veh_Zchgzbh_R == params.veh_Zchgzbh && params.veh_Clztxx_R == "QX") {

        } else {
            if (newZcinfo) {  //如果不是 原整车合格证编号和原底盘合格证编号，数据库中存在的合格证编号是违法合格证编号
                msg = '合格证编号已使用,请查证'
                flag = 'failed'
                result.put("msg", msg);
                result.put("flag", flag)
                render result as JSON
                return
            }
        }

        if (!zcinfoReAuInstance) {
            msg = '审核记录不存在，请核查!'
            flag = 'failed'
            result.put("msg", msg);
            result.put("flag", flag)
            render result as JSON
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (zcinfoReAuInstance.version > version) {
                msg = '当您正在审核的时候,其他人已经修改了该审核记录!'
                flag = 'failed'
                result.put("msg", msg);
                result.put("flag", flag)
                render result as JSON
            }
        }
        User loginUser = getCurrentUser()
        def userId = loginUser?.getId() //审核人
        def userName = loginUser?.userName//审核人登录名
        def curTime = DateUtil.getCurrentTime()

        zcinfoReAuInstance.properties = params

        //使用原来的整车审核状态和底盘审核状态  （因为使用的ajax申请，页面的状态信息没有更改，当第二次提交时会将状态信息覆盖）
        zcinfoReAuInstance.veh_QX_status = t.veh_QX_status
        zcinfoReAuInstance.veh_DP_status = t.veh_DP_status

        zcinfoReAuInstance.veh_Clzzrq_R = zcinfoReAuInstance.veh_Fzrq_R
        zcinfoReAuInstance.authId = userId //审核人
        zcinfoReAuInstance.setAuthTime(curTime)  //审核时间

        def web_virtualcode = params.web_virtualcode
        //对保存修改操作添加事物处理
        ZCInfo.withTransaction { trans ->
            def zcinfoInstance = ZCInfo.findByVeh_Zchgzbh("${params.veh_Zchgzbh}")
            //这里应该删除的只是当前打印的合格证的文件
            def oldFilePath = ""
            if (zcinfoInstance) {
                oldFilePath = zcinfoInstance.upload_Path
            }

            //保存完合格证信息后，打印PDF
            if (params.printType == '0') {
                def tempZcinfo = new ZCInfo()
                tempZcinfo = perfectZcinfo(tempZcinfo, zcinfoReAuInstance, params.printType)
                def printMap = zcInfoService.printWCF(tempZcinfo.properties, grailsApplication)
                if (printMap.isSuccess == '0') {
                    msg = printMap.msg
                    flag = 'failed'
                    result.put("msg", msg);
                    result.put("flag", flag)
                    return render(result as JSON)
                } else {
                    zcinfoReAuInstance.vercode_r = printMap.vercode
                    zcinfoReAuInstance.veh_Zchgzbh_0_R = printMap.veh_wzhgzbh
                    zcinfoReAuInstance.veh_Dywym_R = printMap.veh_Dywym
                    zcinfoReAuInstance.upload_Path_R = printMap.msg
                    web_virtualcode = printMap.veh_wzhgzbh
                }
            }

            //标识是否是新的记录，默认不是一条新纪录
            def newFlag = false

            if (!newZcinfo) {//新合格证不存在
                newFlag = true
                if (!zcinfoInstance || zcinfoInstance.web_status == '1' || zcinfoInstance.web_status == '3') {
                    zcinfoReAuInstance.veh_isupload = 1
                }
                zcinfoReAuInstance.veh_usertype = 0
                zcinfoReAuInstance.veh_managestatus = 0
                //申请审核成功后需要向合格证信息里面插入数据
                newZcinfo = new ZCInfo()
                newZcinfo.web_virtualcode = web_virtualcode
                newZcinfo.createrId = userName
                newZcinfo.createTime = curTime
                newZcinfo.web_status = '0'
                //信息完善
                newZcinfo = perfectZcinfo(newZcinfo, zcinfoReAuInstance, params.printType)

                if (!newZcinfo.save(flush: true)) {
                    msg = '合格证信息保存失败!'
                    flag = 'failed'
                    result.put("msg", msg);
                    result.put("flag", flag)
                    return render(result as JSON)

                }
            } else {//新合格证信息存在（未换号） ，审核成功后修改原合格证信息
                newFlag = false

                if (newZcinfo.veh_Clztxx == "QX") {
                    if (!zcinfoInstance || zcinfoInstance.web_status == '1' || zcinfoInstance.web_status == '3') {
//已上传国家成功
                        zcinfoReAuInstance.veh_isupload = 1
                    } else {
                        zcinfoReAuInstance.veh_isupload = 0//没有成功上传到国家或者未上传
                    }
                } else {
                    if (!oldDP || oldDP.web_status == '1' || oldDP.web_status == '3') {//已上传国家成功
                        zcinfoReAuInstance.veh_isupload = 1
                    } else {
                        zcinfoReAuInstance.veh_isupload = 0//没有成功上传到国家或者未上传
                    }
                }

                zcinfoReAuInstance.veh_usertype = 0
                zcinfoReAuInstance.veh_managestatus = 0

                //信息完善
                newZcinfo = perfectZcinfo(newZcinfo, zcinfoReAuInstance, params.printType)
                newZcinfo.web_virtualcode = web_virtualcode
                newZcinfo.updateTime = curTime
                newZcinfo.updaterId = userName

                if (!newZcinfo.save(flush: true)) {
                    trans.setRollbackOnly()
                    msg = '合格证信息保存失败!'
                    flag = 'failed'
                    result.put("msg", msg);
                    result.put("flag", flag)
                    return render(result as JSON)
                }
            }

            //当只要整车或只要底盘时直接审核通过，如果整车和底盘都要时，只有整车和底盘都打印后才算审核通过
            if (zcinfoReAuInstance.getVeh_Need_Cer() == 0 || zcinfoReAuInstance.getVeh_Need_Cer() == 1) {   //只要整车或只要底盘
                zcinfoReAuInstance.veh_coc_status = 1  //审核通过
                pass = 1
            } else if (zcinfoReAuInstance.getVeh_Need_Cer() == 2) {   //整车或底盘都要
                if (zcinfoReAuInstance.veh_Clztxx_R == 'QX') {
                    zcinfoReAuInstance.veh_QX_status = 1
                    qxPass = 1
                    if (zcinfoReAuInstance.veh_DP_status == 1) {
                        zcinfoReAuInstance.veh_coc_status = 1  //审核通过
                        pass = 1
                    }
                } else {
                    zcinfoReAuInstance.veh_DP_status = 1
                    dpPass = 1
                    if (zcinfoReAuInstance.veh_QX_status == 1) {
                        zcinfoReAuInstance.veh_coc_status = 1  //审核通过
                        pass = 1
                    }
                }
            }

            //当整车和底盘都要时，当打印底盘时不更新更换记录
            if (zcinfoReAuInstance.getVeh_Need_Cer() != 2 || (zcinfoReAuInstance.getVeh_Need_Cer() == 2 && zcinfoReAuInstance.veh_Clztxx_R == "QX")) {
                t.properties = zcinfoReAuInstance.properties
                if (!t.save(flush: true)) {
                    trans.setRollbackOnly()
                    msg = "审核记录信息保存失败!"
                    flag = 'failed'
                    result.put("msg", msg);
                    result.put("flag", flag)
                    return render(result as JSON)
                }
            } else { //当整车和底盘都要时，打印底盘时仅保存底盘的状态即可
                t.veh_coc_status = 1
                t.authTime = curTime
                if (!t.save(flush: true)) {
                    trans.setRollbackOnly()
                    msg = "审核记录信息保存失败!"
                    flag = 'failed'
                    result.put("msg", msg);
                    result.put("flag", flag)
                    return render(result as JSON)

                }
            }

            //因新合格证在前面已生成，判断新路径和原路径是否相同，不相同就删除原文件
            if (oldFilePath && !(oldFilePath.equals(newZcinfo?.upload_Path))) {
                File f = new File(grailsApplication.config.upload.rootDir + oldFilePath)
                if (f.exists()) {
                    f.delete()
                }
            }

            //处理返回结果
            if (zcinfoReAuInstance.getVeh_Need_Cer() == 2) {
                def opFlag = true
                //删除旧合格证信息
                if (newFlag) {
                    if (zcinfoReAuInstance.veh_Clztxx_R == "DP") {  //整车和底盘都要，打底盘时
                        if (oldDP) {
                            File f = new File(grailsApplication.config.upload.rootDir + oldDP.upload_Path)
                            if (f.exists()) {
                                f.delete()
                            }

                            if (oldDP.delete(flush: true)) {
                                opFlag = false
                            }
                        }


                    } else if (zcinfoReAuInstance.veh_Clztxx_R == "QX") { //整车和底盘都要，打整车时
                        def old = ZCInfo.findByVeh_Zchgzbh("${zcinfoReAuInstance.veh_Zchgzbh}")
                        if (old) {
                            File f = new File(grailsApplication.config.upload.rootDir + old.upload_Path)
                            if (f.exists()) {
                                f.delete()
                            }

                            if (old.delete(flush: true)) {
                                opFlag = false
                            }
                        }

                    }
                }

                if (!opFlag) {
                    trans.setRollbackOnly()

                    msg = "旧合格证信息删除失败!"
                    flag = 'failed'
                    result.put("msg", msg);
                    result.put("flag", flag)
                    return render(result as JSON)
                }

                if (zcinfoReAuInstance.veh_coc_status == 1) {
                    msg = "审核成功!<div>完整合格证编号为：${zcinfoReAuInstance.veh_Zchgzbh_0_R}</div>"
                } else if (zcinfoReAuInstance.veh_Clztxx_R == "QX") {
                    msg = "<div>整车合格证打印成功！</div><div>完整合格证编号为：${zcinfoReAuInstance.veh_Zchgzbh_0_R}</div><div><font color='red'>请打印底盘合格证信息！</font></div>"
                } else if (zcinfoReAuInstance.veh_Clztxx_R == "DP") {
                    msg = "<div>底盘合格证打印成功！</div><div>完整合格证编号为：${zcinfoReAuInstance.veh_Zchgzbh_0_R}</div><div><font color='red'>请打印整车合格证信息！</font></div>"
                }

                flag = 'sucessed'
                result.put("msg", msg);
                result.put("flag", flag)
                result.put("pass", pass)
                result.put("dpPass", dpPass)
                result.put("qxPass", qxPass)
                render result as JSON
                return
            } else {
                def opFlag = true
                //删除旧合格证信息
                if (newFlag) {
                    if (zcinfoReAuInstance.veh_Need_Cer == 1) {  //只要底盘
                        if (oldDP) {
                            File f = new File(grailsApplication.config.upload.rootDir + oldDP.upload_Path)
                            if (f.exists()) {
                                f.delete()
                            }

                            if (oldDP.delete(flush: true)) {
                                opFlag = false
                            }
                        }

                    } else if (zcinfoReAuInstance.veh_Need_Cer == 0) { //只要整车 （有点问题应该把整车和底盘合格证都删掉，不然底盘还会存在，再换成老底盘会报合格证号已存在）
                        def old = ZCInfo.findByVeh_Zchgzbh("${zcinfoReAuInstance.veh_Zchgzbh}")
                        if (old) {
                            File f = new File(grailsApplication.config.upload.rootDir + old.upload_Path)
                            if (f.exists()) {
                                f.delete()
                            }

                            if (old.delete(flush: true)) {
                                opFlag = false
                            }
                        }
                    }
                }

                if (!opFlag) {
                    trans.setRollbackOnly()

                    msg = "旧合格证信息删除失败!"
                    flag = 'failed'
                    result.put("msg", msg);
                    result.put("flag", flag)
                    return render(result as JSON)
                }

                msg = "审核成功!<div>完整合格证编号为：${zcinfoReAuInstance.veh_Zchgzbh_0_R}</div>"
                flag = 'sucessed'
                result.put("msg", msg);
                result.put("flag", flag)
                result.put("pass", pass)
                result.put("dpPass", dpPass)
                result.put("qxPass", qxPass)
                render result as JSON
                return
            }
        }

    }

    /**
     * 合格证信息完善
     * @param zcinfo
     * @param zcInfoReplace
     * @author Xu
     * @return
     * @update 更换合格证后将SAP_no写会新合格证
     */
    def perfectZcinfo(ZCInfo zcinfo, ZCInfoReplace zcInfoReplace, String printFlag) {
        if (printFlag == '0') {//PDF打印
            zcinfo.web_isprint = '0'    //
        } else {//直接打印到正式合格证上
            zcinfo.web_isprint = '1'    //
        }
        zcinfo.veh_Type = zcInfoReplace.veh_Type_R  //汽车和农用车标示   0：汽车 1：农用车
        zcinfo.veh_Zchgzbh = zcInfoReplace.veh_Zchgzbh_R  //整车合格证编号
        zcinfo.veh_Dphgzbh = zcInfoReplace.veh_Dphgzbh_R  //底盘合格证编号
        zcinfo.veh_Fzrq = zcInfoReplace.veh_Fzrq_R     //发证日期
        zcinfo.veh_Clzzqymc = zcInfoReplace.veh_Clzzqymc_R //车辆制造企业名称
        zcinfo.veh_Qyid = zcInfoReplace.veh_Qyid_R     //企业ID
        zcinfo.veh_Clfl = zcInfoReplace.veh_Clfl_R     //车辆分类
        zcinfo.veh_Clmc = zcInfoReplace.veh_Clmc_R     //车辆名称
        zcinfo.veh_Clpp = zcInfoReplace.veh_Clpp_R     //车辆品牌
        zcinfo.veh_Clxh = zcInfoReplace.veh_Clxh_R    //车辆型号
        zcinfo.veh_Csys = zcInfoReplace.veh_Csys_R     //车身颜色
        zcinfo.veh_Dpxh = zcInfoReplace.veh_Dpxh_R     //底盘型号
        zcinfo.veh_Dpid = zcInfoReplace.veh_Dpid_R    //底盘ID
        zcinfo.veh_Clsbdh = zcInfoReplace.veh_Clsbdh_R    //车辆识别代号
        zcinfo.veh_Cjh = zcInfoReplace.veh_Cjh_R        //车架号
        zcinfo.veh_Fdjh = zcInfoReplace.veh_Fdjh_R        //发动机号

        zcinfo.veh_Fdjxh = zcInfoReplace.veh_Fdjxh_R   //发动机型号
        zcinfo.veh_Rlzl = zcInfoReplace.veh_Rlzl_R    //燃料种类
        zcinfo.veh_Pl = zcInfoReplace.veh_Pl_R      //排量
        zcinfo.veh_Gl = zcInfoReplace.veh_Gl_R       //功率
        zcinfo.veh_zdjgl = zcInfoReplace.veh_zdjgl_R    //最大净功率
        zcinfo.veh_Zxxs = zcInfoReplace.veh_Zxxs_R    //转向形式
        zcinfo.veh_Qlj = zcInfoReplace.veh_Qlj_R     //前轮距
        zcinfo.veh_Hlj = zcInfoReplace.veh_Hlj_R     //后轮距
        zcinfo.veh_Lts = zcInfoReplace.veh_Lts_R     //轮胎数
        zcinfo.veh_Ltgg = zcInfoReplace.veh_Ltgg_R    //轮胎规格
        zcinfo.veh_Gbthps = zcInfoReplace.veh_Gbthps_R   //钢板弹簧片数
        zcinfo.veh_Zj = zcInfoReplace.veh_Zj_R     //轴距
        zcinfo.veh_Zh = zcInfoReplace.veh_Zh_R     //轴荷
        zcinfo.veh_Zs = zcInfoReplace.veh_Zs_R      //轴数
        zcinfo.veh_Wkc = zcInfoReplace.veh_Wkc_R      //外廓长
        zcinfo.veh_Wkk = zcInfoReplace.veh_Wkk_R     //外廓宽
        zcinfo.veh_Wkg = zcInfoReplace.veh_Wkg_R     //外廓高
        zcinfo.veh_Hxnbc = zcInfoReplace.veh_Hxnbc_R    //货箱内部长
        zcinfo.veh_Hxnbk = zcInfoReplace.veh_Hxnbk_R    //货箱内部宽
        zcinfo.veh_Hxnbg = zcInfoReplace.veh_Hxnbg_R    //货箱内部高
        zcinfo.veh_Zzl = zcInfoReplace.veh_Zzl_R    //总质量
        zcinfo.veh_Edzzl = zcInfoReplace.veh_Edzzl_R     //额定载质量
        zcinfo.veh_Zbzl = zcInfoReplace.veh_Zbzl_R     //整备质量
        zcinfo.veh_Zzllyxs = zcInfoReplace.veh_Zzllyxs_R   //载质量利用系数
        zcinfo.veh_Zqyzzl = zcInfoReplace.veh_Zqyzzl_R  //准牵引总质量
        zcinfo.veh_Edzk = zcInfoReplace.veh_Edzk_R    //额定载客
        zcinfo.veh_Bgcazzdyxzzl = zcInfoReplace.veh_Bgcazzdyxzzl_R //半挂车鞍座最大允许总质量
        zcinfo.veh_Jsszcrs = zcInfoReplace.veh_Jsszcrs_R //驾驶室准乘人数
        zcinfo.veh_Qzdfs = zcInfoReplace.veh_Qzdfs_R     //前制动方式
        zcinfo.veh_Hzdfs = zcInfoReplace.veh_Hzdfs_R  //后制动方式
        zcinfo.veh_Qzdczfs = zcInfoReplace.veh_Qzdczfs_R    //前制动操作方式
        zcinfo.veh_Cpggh = zcInfoReplace.veh_Cpggh_R  //产品公告号
        zcinfo.veh_Ggsxrq = zcInfoReplace.veh_Ggsxrq_R //公告生效日期
        zcinfo.veh_Zzbh = zcInfoReplace.veh_Zzbh_R    //纸张编号
        zcinfo.veh_Dywym = zcInfoReplace.veh_Dywym_R //打印唯一码
        zcinfo.veh_Zgcs = zcInfoReplace.veh_Zgcs_R    //最高车速
        zcinfo.veh_Clzzrq = zcInfoReplace.veh_Clzzrq_R   //车辆制造日期
        zcinfo.veh_Bz = zcInfoReplace.veh_Bz_R    //备注
        zcinfo.veh_Qybz = zcInfoReplace.veh_Qybz_R    //企业标准
        zcinfo.veh_Cpscdz = zcInfoReplace.veh_Cpscdz_R   //产品生产地址
        zcinfo.veh_Qyqtxx = zcInfoReplace.veh_Qyqtxx_R   //企业其它信息
        zcinfo.veh_Pfbz = zcInfoReplace.veh_Pfbz_R   //排放标准
        zcinfo.veh_Clztxx = zcInfoReplace.veh_Clztxx_R   //车辆状态信息
        zcinfo.veh_Jss = zcInfoReplace.veh_Jss_R  //驾驶室
        zcinfo.veh_Jsslx = zcInfoReplace.veh_Jsslx_R  //驾驶室类型
        zcinfo.veh_Zchgzbh_0 = zcInfoReplace.veh_Zchgzbh_0_R //完整合格证编号
        zcinfo.vercode = zcInfoReplace.vercode_r
        zcinfo.used = zcInfoReplace.used_r
        zcinfo.used2 = zcInfoReplace.used2_r
        zcinfo.upload = zcInfoReplace.upload_r//
        zcinfo.veh_Yh = zcInfoReplace.veh_Yh_R//油耗
        zcinfo.veh_VinFourBit = zcInfoReplace.veh_VinFourBit_R//vin第四位
        zcinfo.veh_Ggpc = zcInfoReplace.veh_Ggpc_R  //公告批次
        zcinfo.veh_pzxlh = zcInfoReplace.veh_pzxlh_R              //配置序列号
        zcinfo.veh_clzzqyxx = zcInfoReplace.veh_clzzqyxx_R//车辆制造企业信息
        zcinfo.upload_Path = zcInfoReplace.upload_Path_R //pdf上传路径
        zcinfo.SAP_No = zcInfoReplace.SAP_No
        return zcinfo
    }

    /**
     * @Descriptions 经销商 >> 合格证更换申请查询（主页面 跳转）
     * @Auther zouq
     * @createTime 2013-08-05
     */
    def index_zcinfoReplace() {
        render(view: '/cn/com/wz/vehcert/zcinfo/dealer/search/list', model: [searchType: params.searchType])
    }

    /**
     * @Descriptions 经销商 >> 合格证更换申请查询 数据源(经销商)
     * @Auther zouq
     * @createTime 2013-08-05
     * @update 2013-09-01 将经销商和审核使用的更换查询页面使用同一个方法，添加了区域的查询条件
     * @update 2018-03-15 添加区域列
     */
    def jsonListByDealer() {
        def  params=params
        ///当前登录用户信息
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def userRole = User.get(loginUser?.id)?.roles
        def systemRole = false
        userRole.each {
            def roleCode = Role.get(it.id).roleCode
            if (roleCode == '001') {
                systemRole = true
            }
        }
        params.systemRole = systemRole
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort = 'authTime'
        params.order = "desc"

        def results = zcInfoReplaceService.selectZcInfoReplace(params, loginUser)

        def lst = []
        results.each { l ->
            def realName = User.get(l.createrId)?.userDetail?.realName
            def allOrganNameC = new StringBuilder();
            def distributorList = Organization.createCriteria().list({
                parent { eq('organCode', 'distributor') }
            }).organNameC
            User.get(l.createrId)?.organs.each {
                if (distributorList.contains(it.organNameC)) {
                    allOrganNameC.append(it.organNameC + '/')
                }
            }
            if (allOrganNameC.length() > 0) {
                allOrganNameC = allOrganNameC.substring(0, allOrganNameC.length() - 1)
            } else {
                allOrganNameC.append('其它')
            }
            def m = [:]
            m.id = l.id
            m.allOrganNameC = allOrganNameC
            m.veh_Zchgzbh_0 = l.veh_Zchgzbh_0
            m.veh_Zchgzbh_0_R = l.veh_Zchgzbh_0_R
            m.veh_createTime = l.veh_createTime
            m.createrName = realName
            m.veh_Zchgzbh = l.veh_Zchgzbh
            m.veh_Zzbh = l.veh_Zzbh
            m.veh_Clsbdh = l.veh_Clsbdh
            m.veh_Cpggh = l.veh_Cpggh
            m.veh_Fdjh = l.veh_Fdjh
            m.veh_Fdjxh = l.veh_Fdjxh
            m.veh_Zchgzbh_R = l.veh_Zchgzbh_R
            m.veh_Clsbdh_R = l.veh_Clsbdh_R
            m.veh_Cpggh_R = l.veh_Cpggh_R
            m.veh_Fdjh_R = l.veh_Fdjh_R
            m.veh_Fdjxh_R = l.veh_Fdjxh_R
            m.salesmanname = l.salesmanname
            m.salesmantel = l.salesmantel
            m.createTime = l.createTime
            m.veh_coc_status = l.veh_coc_status
            m.veh_Clxh = l.veh_Clxh
            m.veh_Clxh_R = l.veh_Clxh_R
            m.veh_Clmc = l.veh_Clmc
            m.veh_Clmc_R = l.veh_Clmc_R
            m.veh_Fdjxh = l.veh_Fdjxh
            m.veh_Fdjxh_R = l.veh_Fdjxh_R
            m.veh_Zzbh = l.veh_Zzbh
            m.veh_Zzbh_R = l.veh_Zzbh_R
            m.veh_Fzrq = l.veh_Fzrq
            m.veh_Fzrq_R = l.veh_Fzrq_R
            m.remark = l.remark
            m.authTime = l.authTime
            m.replaceType = l.replaceType
            m.area_status = l.area_status
            m.product_auth_status = l.product_auth_status
            m.SAP_No = l.SAP_No
            m.transToCrm = l.transToCrm
            m.transToSap = l.transToSap

            lst.add(m)
        }
        def result = [rows: lst, total: results.totalCount]
        render result as JSON
    }

    /**
     * @Description 处理完成控制器
     * @author Xu
     * 2013-8-11
     * @Update 2014-03-02 huxx 完成操作时，不修改authTime和authorId
     */
    def tofinish() {
        ZCInfoReplace.withTransaction {
            def flag = true
            def id = params.id
            try {
                User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
                def zcinfoInstance = ZCInfoReplace.get(id)
                zcinfoInstance.setVeh_managestatus(1) //处理完成
//                    zcinfoInstance.authTime =DateUtil.getCurrentTime()
//                    zcinfoInstance.authorId = loginUser.id
                zcinfoInstance.save(flush: true)
            } catch (DataIntegrityViolationException e) {
                flag = false
            }

            if (flag) {
                render message(code: 'organUser.save.success.message', default: 'Success')
            } else {
                render message(code: 'organUser.save.fail.message', default: 'failed')
            }
        }
    }

    /**
     * @Description 合格证更换保存
     * @author liuly
     * 2013-8-11
     * @Update 将汽车合格证的更换申请合并在该方法中
     * @UpdateTime 2014-11-25 zhuwei
     * Update 1、将只验证整车合格证编号改为验证【整车或者底盘合格证】   by zhuwei 2017-07-26
     * Update 2、保存更换记录时将车间扫描的SAP序列号也保存到更换记录表中 by zhuwei 2017-07-26
     */
    def zcinfoReplaceSave() {
        def flag = true
        def aaaa = params
        def msg = ''
        //旧表
        def zciInfoModel
        def cel = {
            eq('id', params.zid)  //关联VIN对应的合格证的id
        }
        zciInfoModel = ZCInfo.createCriteria().list(cel)?.get(0)
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
        if(zciInfoModel.veh_Type=='0'&&!organCodeList.contains('qccp')){
            List vinObjectList = newDmsSynService.findVinOfDistributor(loginUser.userName)
            List vinList =[]
            if(vinObjectList.size()>0){
                vinObjectList.each{object->
                    vinList.add(object.new_vin)
                }
            }
            if(!vinList.contains(zciInfoModel.veh_Clsbdh)){
                return render("您没有权限更换VIN为${zciInfoModel.veh_Clsbdh}的合格证！")
            }
        }
        //更换申请表
        def zcinfoReplace = new ZCInfoReplace()
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
                    def organList = loginUser?.organs?.id
                    zcinfoReplace.createrOrgan = organList[0]
                    replaceForSupplement.createrOrgan = organList[0]
                    zcinfoReplace.veh_coc_status = 3 //营销公司审核审核状态不使用默认值0，而是将其置3，在区域经理审核时再将其重置为0
                    replaceForSupplement.veh_coc_status = 3
                }
                zcinfoReplace.veh_Type = zciInfoModel.veh_Type
                zcinfoReplace.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                zcinfoReplace.veh_isupload = Integer.parseInt(zciInfoModel.web_status)
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
                    def organList = loginUser?.organs?.id
                    zcinfoReplace.createrOrgan = organList[0]
                    zcinfoReplace.veh_coc_status = 3 //营销公司审核审核状态不使用默认值0，而是将其置3，在区域经理审核时再将其重置为0
                }
                zcinfoReplace.veh_Type = zciInfoModel.veh_Type
                zcinfoReplace.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                zcinfoReplace.veh_isupload = Integer.parseInt(zciInfoModel.web_status)
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
    /**
     * @Description A4合格证更换正式合格证保存
     * @author QJ
     * 2013-8-11
     */
    def zcinfoPdfReplaceSave() {
        def flag = true
        def aaaa = params
        def msg = ''
        //旧表
        def zciInfoModel
        def cel = {
            eq('id', params.zid)  //关联VIN对应的合格证的id
        }
        zciInfoModel = ZCInfo.createCriteria().list(cel)?.get(0)
        User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
        if(zciInfoModel.veh_Type=='0'){
            List vinObjectList = newDmsSynService.findVinOfDistributor(loginUser.userName)
            List vinList =[]
            if(vinObjectList.size()>0){
                vinObjectList.each{object->
                    vinList.add(object.new_vin)
                }
            }
            if(!vinList.contains(zciInfoModel.veh_Clsbdh)){
                return render("您没有权限更换VIN为${zciInfoModel.veh_Clsbdh}的合格证！")
            }
        }
        //更换申请表
        def zcinfoReplace = new ZCInfoReplace()
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
            if (params.isAll == '0') {
                if (params?.replaceType == '1') { //replaceType=='1' 是二次更换标识，正常的其他更换该标识为0
                    zcinfoReplace.replaceType = 1
                    zcinfoReplace.area_status = 0      //area_status默认初始值为3，只有在二次更换申请时将其改写为0啊
                    //保存申请者的组织id【取第一个组织id保存】，经销商帐号只能关联到个区域组织
                    def organList = loginUser?.organs?.id
                    zcinfoReplace.createrOrgan = organList[0]
                    zcinfoReplace.veh_coc_status = 3 //营销公司审核审核状态不使用默认值0，而是将其置3，在区域经理审核时再将其重置为0
                }
                zcinfoReplace.veh_Type = zciInfoModel.veh_Type
                zcinfoReplace.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                zcinfoReplace.veh_isupload = Integer.parseInt(zciInfoModel.web_status)
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
                if(params.formal){
                    zcinfoReplace.formal = params.formal
                }
                zcinfoReplace.createrId = loginUser.id
                zcinfoReplace.createTime = DateUtil.getCurrentTime()
                if (zcinfoReplace.save(flush: true)) {
                    msg = '提交成功'
                } else {
                    msg = '提交失败'
                }
            } else if (params.isAll == '1') {
                def replaceForSupplement = new ReplaceForSupplement()
                def zciInfoModelOther
                def celOther = {
                    eq('id', params.otherid)  //关联VIN对应的合格证的id
                }
                zciInfoModelOther = ZCInfo.createCriteria().list(celOther)?.get(0)
                if (params?.replaceType == '1') { //replaceType=='1' 是二次更换标识，正常的其他更换该标识为0
                    zcinfoReplace.replaceType = 1
                    replaceForSupplement.replaceType = 1
                    zcinfoReplace.area_status = 0      //area_status默认初始值为3，只有在二次更换申请时将其改写为0啊
                    replaceForSupplement.area_status = 0
                    //保存申请者的组织id【取第一个组织id保存】，经销商帐号只能关联到个区域组织
                    def organList = loginUser?.organs?.id
                    zcinfoReplace.createrOrgan = organList[0]
                    replaceForSupplement.createrOrgan = organList[0]
                    zcinfoReplace.veh_coc_status = 3 //营销公司审核审核状态不使用默认值0，而是将其置3，在区域经理审核时再将其重置为0
                    replaceForSupplement.veh_coc_status = 3
                }
                zcinfoReplace.veh_Type = zciInfoModel.veh_Type
                zcinfoReplace.veh_Xnhgzbh = zciInfoModel.web_virtualcode
                zcinfoReplace.veh_isupload = Integer.parseInt(zciInfoModel.web_status)
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
                zcinfoReplace.veh_Need_Cer = 2
                zcinfoReplace.remark = params.remark
                zcinfoReplace.salesmanname = params.salesmanname
                zcinfoReplace.salesmantel = params.salesmantel
                if(params.formal){
                    zcinfoReplace.formal = params.formal
                    replaceForSupplement.formal = params.formal
                }
                zcinfoReplace.createrId = loginUser.id
                zcinfoReplace.createTime = DateUtil.getCurrentTime()

                //整车和底盘都要的合格证向更换附加表中写入底盘公告数据
                replaceForSupplement.veh_Type = zciInfoModelOther.veh_Type
                replaceForSupplement.veh_Xnhgzbh = zciInfoModelOther.web_virtualcode
                replaceForSupplement.veh_Zchgzbh = zciInfoModelOther.veh_Zchgzbh
                replaceForSupplement.veh_Dphgzbh = zciInfoModelOther.veh_Dphgzbh
                replaceForSupplement.veh_Fzrq = zciInfoModelOther.veh_Fzrq
                replaceForSupplement.veh_Clzzqymc = zciInfoModelOther.veh_Clzzqymc
                replaceForSupplement.veh_Qyid = zciInfoModelOther.veh_Qyid
                replaceForSupplement.veh_Clfl = zciInfoModelOther.veh_Clfl
                replaceForSupplement.veh_Clmc = zciInfoModelOther.veh_Clmc
                replaceForSupplement.veh_Clpp = zciInfoModelOther.veh_Clpp
                replaceForSupplement.veh_Clxh = zciInfoModelOther.veh_Clxh
                replaceForSupplement.veh_Csys = zciInfoModelOther.veh_Csys
                replaceForSupplement.veh_Dpxh = zciInfoModelOther.veh_Dpxh
                replaceForSupplement.veh_Dpid = zciInfoModelOther.veh_Dpid
                replaceForSupplement.veh_Clsbdh = zciInfoModelOther.veh_Clsbdh    //车辆识别代号
                replaceForSupplement.veh_Cjh = zciInfoModelOther.veh_Clsbdh
                replaceForSupplement.veh_Fdjh = zciInfoModelOther.veh_Fdjh        //发动机号

                replaceForSupplement.veh_Fdjxh = zciInfoModelOther.veh_Fdjxh     //发动机型号
                replaceForSupplement.veh_Rlzl = zciInfoModelOther.veh_Rlzl     //燃料种类
                replaceForSupplement.veh_Pl = zciInfoModelOther.veh_Pl       //排量
                replaceForSupplement.veh_Gl = zciInfoModelOther.veh_Gl       //功率
                replaceForSupplement.veh_zdjgl = zciInfoModelOther.veh_zdjgl    //最大净功率
                replaceForSupplement.veh_Zxxs = zciInfoModelOther.veh_Zxxs     //转向形式
                replaceForSupplement.veh_Qlj = zciInfoModelOther.veh_Qlj      //前轮距
                replaceForSupplement.veh_Hlj = zciInfoModelOther.veh_Hlj      //后轮距
                replaceForSupplement.veh_Lts = zciInfoModelOther.veh_Lts      //轮胎数
                replaceForSupplement.veh_Ltgg = zciInfoModelOther.veh_Ltgg    //轮胎规格
                replaceForSupplement.veh_Gbthps = zciInfoModelOther.veh_Gbthps   //钢板弹簧片数
                replaceForSupplement.veh_Zj = zciInfoModelOther.veh_Zj      //轴距
                replaceForSupplement.veh_Zh = zciInfoModelOther.veh_Zh       //轴荷
                replaceForSupplement.veh_Zs = zciInfoModelOther.veh_Zs       //轴数
                replaceForSupplement.veh_Wkc = zciInfoModelOther.veh_Wkc      //外廓长
                replaceForSupplement.veh_Wkk = zciInfoModelOther.veh_Wkk       //外廓宽
                replaceForSupplement.veh_Wkg = zciInfoModelOther.veh_Wkg       //外廓高
                replaceForSupplement.veh_Hxnbc = zciInfoModelOther.veh_Hxnbc     //货箱内部长
                replaceForSupplement.veh_Hxnbk = zciInfoModelOther.veh_Hxnbk     //货箱内部宽
                replaceForSupplement.veh_Hxnbg = zciInfoModelOther.veh_Hxnbg     //货箱内部高
                replaceForSupplement.veh_Zzl = zciInfoModelOther.veh_Zzl       //总质量
                replaceForSupplement.veh_Edzzl = zciInfoModelOther.veh_Edzzl     //额定载质量
                replaceForSupplement.veh_Zbzl = zciInfoModelOther.veh_Zbzl      //整备质量
                replaceForSupplement.veh_Zzllyxs = zciInfoModelOther.veh_Zzllyxs   //载质量利用系数
                replaceForSupplement.veh_Zqyzzl = zciInfoModelOther.veh_Zqyzzl   //准牵引总质量
                replaceForSupplement.veh_Edzk = zciInfoModelOther.veh_Edzk     //额定载客
                replaceForSupplement.veh_Bgcazzdyxzzl = zciInfoModelOther.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
                replaceForSupplement.veh_Jsszcrs = zciInfoModelOther.veh_Jsszcrs  //驾驶室准乘人数
                replaceForSupplement.veh_Qzdfs = zciInfoModelOther.veh_Qzdfs       //前制动方式
                replaceForSupplement.veh_Hzdfs = zciInfoModelOther.veh_Hzdfs    //后制动方式
                replaceForSupplement.veh_Qzdczfs = zciInfoModelOther.veh_Qzdczfs     //前制动操作方式
                replaceForSupplement.veh_Cpggh = zciInfoModelOther.veh_Cpggh    //产品公告号
                replaceForSupplement.veh_Ggsxrq = zciInfoModelOther.veh_Ggsxrq   //公告生效日期
                replaceForSupplement.veh_Zzbh = params.veh_Zzbh       //纸张编号
                replaceForSupplement.veh_Dywym = zciInfoModelOther.veh_Dywym   //打印唯一码
                replaceForSupplement.veh_Zgcs = zciInfoModelOther.veh_Zgcs     //最高车速
                replaceForSupplement.veh_Clzzrq = zciInfoModelOther.veh_Clzzrq   //车辆制造日期
                replaceForSupplement.veh_Bz = zciInfoModelOther.veh_Bz       //备注
                replaceForSupplement.veh_Qybz = zciInfoModelOther.veh_Qybz     //企业标准
                replaceForSupplement.veh_Cpscdz = zciInfoModelOther.veh_Cpscdz   //产品生产地址
                replaceForSupplement.veh_Qyqtxx = zciInfoModelOther.veh_Qyqtxx   //企业其它信息
                replaceForSupplement.veh_Pfbz = zciInfoModelOther.veh_Pfbz     //排放标准
                replaceForSupplement.veh_Clztxx = zciInfoModelOther.veh_Clztxx   //车辆状态信息
                replaceForSupplement.veh_Jss = zciInfoModelOther.veh_Jss     //驾驶室
                replaceForSupplement.veh_Jsslx = zciInfoModelOther.veh_Jsslx   //驾驶室类型
                replaceForSupplement.veh_Zchgzbh_0 = zciInfoModelOther.veh_Zchgzbh_0   //完整合格证编号
                replaceForSupplement.used = zciInfoModelOther.used
                replaceForSupplement.used2 = zciInfoModelOther.used2
                replaceForSupplement.upload = zciInfoModelOther.upload  //
                // replaceForSupplement. vercode  =zciInfoModelOther.vercode //二维码
                replaceForSupplement.veh_Yh = zciInfoModelOther.veh_Yh   //油耗
                replaceForSupplement.veh_VinFourBit = zciInfoModelOther.veh_VinFourBit  //vin第四位
                replaceForSupplement.veh_Ggpc = zciInfoModelOther.veh_Ggpc         //公告批次
                replaceForSupplement.veh_createTime = zciInfoModelOther.createTime  //创建时间
                replaceForSupplement.veh_createrId = zciInfoModelOther.createrId  //创建人id
                replaceForSupplement.veh_pzxlh = zciInfoModelOther.veh_pzxlh                   //配置序列号
                replaceForSupplement.upload_Path = zciInfoModelOther.upload_Path   //合格证上传相对路径
                replaceForSupplement.veh_clzzqyxx = zciInfoModelOther.veh_clzzqyxx //车辆制造企业信息
                replaceForSupplement.SAP_No = zciInfoModelOther.SAP_No //SAP序列号，更换记录里增加SAP序列号

                replaceForSupplement.veh_VinFourBit_R = zciInfoModelOther.veh_VinFourBit
                replaceForSupplement.veh_Type_R = zciInfoModelOther.veh_Type
                replaceForSupplement.veh_Cpggh_R = zciInfoModelOther.veh_Cpggh
                replaceForSupplement.veh_zdjgl_R = zciInfoModelOther.veh_zdjgl
                replaceForSupplement.veh_Clztxx_R = zciInfoModelOther.veh_Clztxx
                replaceForSupplement.veh_Qyqtxx_R = zciInfoModelOther.veh_Qyqtxx   //企业其它信息
                replaceForSupplement.veh_Dpid_R = zciInfoModelOther.veh_Dpid
                replaceForSupplement.veh_Jsslx_R = zciInfoModelOther.veh_Jsslx
                replaceForSupplement.veh_pzxlh_R = zciInfoModelOther.veh_pzxlh
                replaceForSupplement.veh_Ggsxrq_R = zciInfoModelOther.veh_Ggsxrq
                replaceForSupplement.veh_Cpscdz_R = zciInfoModelOther.veh_Cpscdz
                replaceForSupplement.veh_Qybz_R = zciInfoModelOther.veh_Qybz
                replaceForSupplement.veh_Hzdfs_R = zciInfoModelOther.veh_Hzdfs
                replaceForSupplement.veh_Qyid_R = zciInfoModelOther.veh_Qyid
                replaceForSupplement.veh_Xnhgzbh_R = zciInfoModelOther.web_virtualcode
                replaceForSupplement.veh_Clzzqymc_R = zciInfoModelOther.veh_Clzzqymc
                replaceForSupplement.veh_Clpp_R = zciInfoModelOther.veh_Clpp
                replaceForSupplement.veh_Clmc_R = zciInfoModelOther.veh_Clmc
                replaceForSupplement.veh_Clxh_R = zciInfoModelOther.veh_Clxh
                replaceForSupplement.veh_Csys_R = zciInfoModelOther.veh_Csys
                replaceForSupplement.veh_Clfl_R = zciInfoModelOther.veh_Clfl
                replaceForSupplement.veh_Dpxh_R = zciInfoModelOther.veh_Dpxh
                replaceForSupplement.veh_Fdjxh_R = zciInfoModelOther.veh_Fdjxh
                replaceForSupplement.veh_Zgcs_R = zciInfoModelOther.veh_Zgcs
                replaceForSupplement.veh_Fdjh_R = zciInfoModelOther.veh_Fdjh
                replaceForSupplement.veh_Rlzl_R = zciInfoModelOther.veh_Rlzl
                replaceForSupplement.veh_Pl_R = zciInfoModelOther.veh_Pl
                replaceForSupplement.veh_Gl_R = zciInfoModelOther.veh_Gl
                replaceForSupplement.veh_Lts_R = zciInfoModelOther.veh_Lts
                replaceForSupplement.veh_Pfbz_R = zciInfoModelOther.veh_Pfbz

                replaceForSupplement.veh_Yh_R = zciInfoModelOther.veh_Yh
                replaceForSupplement.veh_Wkc_R = zciInfoModelOther.veh_Wkc
                replaceForSupplement.veh_Wkk_R = zciInfoModelOther.veh_Wkk
                replaceForSupplement.veh_Wkg_R = zciInfoModelOther.veh_Wkg
                replaceForSupplement.veh_Hxnbc_R = zciInfoModelOther.veh_Hxnbc
                replaceForSupplement.veh_Hxnbk_R = zciInfoModelOther.veh_Hxnbk
                replaceForSupplement.veh_Hxnbg_R = zciInfoModelOther.veh_Hxnbg
                replaceForSupplement.veh_Ltgg_R = zciInfoModelOther.veh_Ltgg
                replaceForSupplement.veh_Gbthps_R = zciInfoModelOther.veh_Gbthps

                replaceForSupplement.veh_Qlj_R = zciInfoModelOther.veh_Qlj
                replaceForSupplement.veh_Hlj_R = zciInfoModelOther.veh_Hlj
                replaceForSupplement.veh_Zj_R = zciInfoModelOther.veh_Zj
                replaceForSupplement.veh_Zh_R = zciInfoModelOther.veh_Zh
                replaceForSupplement.veh_Zs_R = zciInfoModelOther.veh_Zs
                replaceForSupplement.veh_Zxxs_R = zciInfoModelOther.veh_Zxxs
                replaceForSupplement.veh_Zzl_R = zciInfoModelOther.veh_Zzl
                replaceForSupplement.veh_Zbzl_R = zciInfoModelOther.veh_Zbzl
                replaceForSupplement.veh_Edzzl_R = zciInfoModelOther.veh_Edzzl
                replaceForSupplement.veh_Ggpc_R = zciInfoModelOther.veh_Ggpc
                replaceForSupplement.veh_Zzllyxs_R = zciInfoModelOther.veh_Zzllyxs
                replaceForSupplement.veh_Zqyzzl_R = zciInfoModelOther.veh_Zqyzzl
                replaceForSupplement.veh_Bgcazzdyxzzl_R = zciInfoModelOther.veh_Bgcazzdyxzzl
                replaceForSupplement.veh_Jsszcrs_R = zciInfoModelOther.veh_Jsszcrs
                replaceForSupplement.veh_Edzk_R = zciInfoModelOther.veh_Edzk
                replaceForSupplement.veh_Zgcs_R = zciInfoModelOther.veh_Zgcs
                replaceForSupplement.veh_Bz_R = zciInfoModelOther.veh_Bz

                replaceForSupplement.veh_Need_Cer = 2
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
        }
        if (flag == false) {
            msg = '要更换的合格证不存在，请重新选择！'
        }
        render msg
    }
    /**
     * @Descriptions 经销商 >> 更换合格证打印（主页面 跳转）
     * @Auther zouq
     * @createTime 2013-08-05
     */
    def index_downloadPrint() {
        render(view: '/cn/com/wz/vehcert/zcinfo/dealer/downloadPrint')
    }
    /**
     *
     * @Description 寻找公告信息和合格证信息中建议输入
     * @author mike
     * @createTime 2013-06-29
     * @return
     */
    def jsonReplaceSuggestion() {
        String field = params.field;
        String jxsId = params.jxsId;
        String distributorId = params.distributorId;
        def user = []
        def celU = {
            organs {
                if (params.distributorId) {
                    eq('id', distributorId)
                }
            }
            userDetail {
                if (params.jxsId) {
                    eq('id', jxsId)
                }
            }
        }
        user = User.createCriteria().list(celU)
        List suglist = [];
        if (user.size() > 0) {
            user?.each { u ->
                String hqlStr = "select ${field} from WZH_ZCINFO_REPLACE c where c.creater_id='${u.id}' and c.veh_coc_status=0 group by ${field} order by count(*) desc";
                def template = new JdbcTemplate(dataSource)
                List templist = template.queryForList(hqlStr)
                suglist.addAll(templist)
            }
        }
        render(contentType: "text/json") {
            array {
                if (field == 'veh_Clsbdh') {
                    pack(text: '无', value: '无')
                }
                if (field == 'veh_Fdjh') {
                    pack(text: '无', value: '无')
                }
                if (field == 'veh_zchgzbh_0') {
                    pack(text: '无', value: '无')
                }
                if (field == 'veh_Clxh') {
                    pack(text: '无', value: '无')
                }
                if (field == 'veh_Clsbdh_R') {
                    pack(text: '无', value: '无')
                }
                if (field == 'veh_Fdjh_R') {
                    pack(text: '无', value: '无')
                }
                if (field == 'veh_zchgzbh_0_R') {
                    pack(text: '无', value: '无')
                }
                if (field == 'veh_Clxh_R') {
                    pack(text: '无', value: '无')
                }
                for (m in suglist) {
                    String value = m.get(field.toUpperCase())
                    if (value) {
                        pack(text: value, value: value)
                    }
                }
            }
        }
    }

    /**
     * @Descriptions 经销商 >> 合格证变更申请查询 合格证编辑操作函数
     * @Auther zouq
     * @createTime 2013-08-16
     */
    def zinfoReplaceEdit() {
        def zcinfoReplaceID = params.id;  ///合格证变更表ID信息
        if (!zcinfoReplaceID) {
            render(view: '/cn/com/wz/vehcert/zcinfo/dealer/zcinfoReplaceUpdate', model: [searchType: params.searchType])
            return
        }
        def zcinfoReplace = ZCInfoReplace.get(zcinfoReplaceID);
        def replaceForSupplement = ReplaceForSupplement.findByZcinfoReplaceId(params.id)
        def flag = true
        if (zcinfoReplace) {
            render(view: '/cn/com/wz/vehcert/zcinfo/dealer/zcinfoReplaceUpdate', model: [zcinfoReplace: zcinfoReplace,replaceForSupplement:replaceForSupplement,zid: zcinfoReplace.id, statusWrite: 'nowrite', searchType: params.searchType])
        } else {
            render(view: '/cn/com/wz/vehcert/zcinfo/dealer/zcinfoReplaceUpdate', model: [searchType: params.searchType])
        }
    }
    /**
     * @Descriptions 经销商 >> 合格证变更申请查询 合格证删除
     * @Auther zouq
     * @createTime 2013-08-16
     * @Update
     */
    def deleteSingle() {
        def zcInfoReplace = ZCInfoReplace.get(params.id)
        if (zcInfoReplace.veh_coc_status == 1) {
            //如果最终更换打印处已经审核通过，那么不允许经销商在合格证更换申请查询出删除更换记录
            render "更换申请已经通过，不允许删除更换记录"
        } else {
            def result = zcInfoReplace.delete(flush: true)
            if (result) {
                render "fail"
            } else {
                render "success"
            }
        }

    }
    /**
     * @Descriptions 经销商 >> 合格证变更申请查询 变更合格证更新操作函数
     * @Auther zouq
     * @createTime 2013-08-16
     * @UPDATETIME 2018-04-24 更新选择底盘合格证要选择底盘公告，更新原先不能更改已经选好的公告问题
     * @UPDATETIME 2018-04-24 修改公告批次没有更新上的问题
     */
    def zinfoReplaceUpdate() {
        ////变更合格证ID信息
        def zid = params.zid
        ////新修改合格证ID信息
        def newid = params.newId
        if (!zid) {
            render "保存失败,查看数据格式后重试!"
            return
        }
        def zcinfoReplace = ZCInfoReplace.get(zid)

        if (!zcinfoReplace) {
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

        def carstorageId = params.newId
        if (carstorageId) {
            def zciInfoC = CarStorage.get(carstorageId)
            if(params.veh_Need_Cer == '2'){
                def supplement = ReplaceForSupplement.findByZcinfoReplaceId(zid)
                ReplaceForSupplement replaceForSupplement = new ReplaceForSupplement()
                if(supplement){
                    replaceForSupplement = supplement
                }
                def dpCarstorageId = params.newDpId
                def dpCarstorage = CarStorage.get(dpCarstorageId)
                zcinfoReplace.veh_VinFourBit_R = zciInfoC.veh_VinFourBit
                zcinfoReplace.veh_Cpggh_R = zciInfoC.veh_Cpggh
                zcinfoReplace.veh_zdjgl_R = zciInfoC.veh_zdjgl
                zcinfoReplace.veh_Clztxx_R = zciInfoC.veh_Clztxx
                zcinfoReplace.veh_Qyqtxx_R = zciInfoC.veh_Qyqtxx   //企业其它信息
                zcinfoReplace.veh_Dpid_R = zciInfoC.veh_Dpid
                zcinfoReplace.veh_Jsslx_R = zciInfoC.veh_Jsslx
                zcinfoReplace.veh_pzxlh_R = zciInfoC.veh_pzxlh
                zcinfoReplace.veh_Ggsxrq_R = zciInfoC.veh_Ggsxrq
                zcinfoReplace.veh_Cpscdz_R = zciInfoC.veh_Cpscdz
                zcinfoReplace.veh_Qybz_R = zciInfoC.veh_Qybz
                zcinfoReplace.veh_Hzdfs_R = zciInfoC.veh_Hzdfs
                zcinfoReplace.veh_Qyid_R = zciInfoC.veh_Qyid
//        zcinfoReplace.veh_Xnhgzbh_R=zciInfoC.web_virtualcode
                zcinfoReplace.veh_Clzzqymc_R = zciInfoC.veh_Clzzqymc
                zcinfoReplace.veh_Clpp_R = zciInfoC.veh_Clpp
                zcinfoReplace.veh_Clmc_R = zciInfoC.veh_Clmc
                zcinfoReplace.veh_Clxh_R = zciInfoC.veh_Clxh
                zcinfoReplace.veh_Ggpc_R = zciInfoC.veh_Ggpc

//            2013年8月24日 去掉车身颜色跟随公告信息 车身颜色进行修改
                zcinfoReplace.veh_Clfl_R = zciInfoC.veh_Clfl
                zcinfoReplace.veh_Dpxh_R = zciInfoC.veh_Dpxh
                zcinfoReplace.veh_Fdjxh_R = zciInfoC.veh_Fdjxh
                zcinfoReplace.veh_Rlzl_R = zciInfoC.veh_Rlzl
                zcinfoReplace.veh_Pl_R = zciInfoC.veh_Pl
                zcinfoReplace.veh_Gl_R = zciInfoC.veh_Gl
                zcinfoReplace.veh_Lts_R = zciInfoC.veh_Lts
                zcinfoReplace.veh_Pfbz_R = zciInfoC.veh_Pfbz

                zcinfoReplace.veh_Yh_R = zciInfoC.veh_Yh
                zcinfoReplace.veh_Wkc_R = zciInfoC.veh_Wkc
                zcinfoReplace.veh_Wkk_R = zciInfoC.veh_Wkk
                zcinfoReplace.veh_Wkg_R = zciInfoC.veh_Wkg
                zcinfoReplace.veh_Hxnbc_R = zciInfoC.veh_Hxnbc
                zcinfoReplace.veh_Hxnbk_R = zciInfoC.veh_Hxnbk
                zcinfoReplace.veh_Hxnbg_R = zciInfoC.veh_Hxnbg
                zcinfoReplace.veh_Ltgg_R = zciInfoC.veh_Ltgg
                zcinfoReplace.veh_Gbthps_R = zciInfoC.veh_Gbthps

                zcinfoReplace.veh_Qlj_R = zciInfoC.veh_Qlj
                zcinfoReplace.veh_Hlj_R = zciInfoC.veh_Hlj
                zcinfoReplace.veh_Zj_R = zciInfoC.veh_Zj
                zcinfoReplace.veh_Zh_R = zciInfoC.veh_Zh
                zcinfoReplace.veh_Zs_R = zciInfoC.veh_Zs
                zcinfoReplace.veh_Zxxs_R = zciInfoC.veh_Zxxs
                zcinfoReplace.veh_Zzl_R = zciInfoC.veh_Zzl
                zcinfoReplace.veh_Zbzl_R = zciInfoC.veh_Zbzl
                zcinfoReplace.veh_Edzzl_R = zciInfoC.veh_Edzzl

                zcinfoReplace.veh_Zzllyxs_R = zciInfoC.veh_Zzllyxs
                zcinfoReplace.veh_Zqyzzl_R = zciInfoC.veh_Zqyzzl
                zcinfoReplace.veh_Bgcazzdyxzzl_R = zciInfoC.veh_Bgcazzdyxzzl
                zcinfoReplace.veh_Jsszcrs_R = zciInfoC.veh_Jsszcrs
                zcinfoReplace.veh_Edzk_R = zciInfoC.veh_Edzk
                zcinfoReplace.veh_Zgcs_R = zciInfoC.veh_Zgcs
                zcinfoReplace.veh_Bz_R = zciInfoC.veh_Bz

                zcinfoReplace.veh_Csys_R = params.veh_Csys_R
                zcinfoReplace.veh_Need_Cer = Integer.parseInt(params.veh_Need_Cer)
                zcinfoReplace.veh_Zzbh = params.veh_Zzbh
                zcinfoReplace.veh_Fdjh_R = params.veh_Fdjh
                zcinfoReplace.remark = params.remark
                zcinfoReplace.salesmanname = params.salesmanname
                zcinfoReplace.salesmantel = params.salesmantel
                if(dpCarstorage) {
                    replaceForSupplement.veh_VinFourBit_R = dpCarstorage.veh_VinFourBit
                    replaceForSupplement.veh_Cpggh_R = dpCarstorage.veh_Cpggh
                    replaceForSupplement.veh_zdjgl_R = dpCarstorage.veh_zdjgl
                    replaceForSupplement.veh_Clztxx_R = dpCarstorage.veh_Clztxx
                    replaceForSupplement.veh_Qyqtxx_R = dpCarstorage.veh_Qyqtxx   //企业其它信息
                    replaceForSupplement.veh_Dpid_R = dpCarstorage.veh_Dpid
                    replaceForSupplement.veh_Jsslx_R = dpCarstorage.veh_Jsslx
                    replaceForSupplement.veh_pzxlh_R = dpCarstorage.veh_pzxlh
                    replaceForSupplement.veh_Ggsxrq_R = dpCarstorage.veh_Ggsxrq
                    replaceForSupplement.veh_Cpscdz_R = dpCarstorage.veh_Cpscdz
                    replaceForSupplement.veh_Qybz_R = dpCarstorage.veh_Qybz
                    replaceForSupplement.veh_Hzdfs_R = dpCarstorage.veh_Hzdfs
                    replaceForSupplement.veh_Qyid_R = dpCarstorage.veh_Qyid
//        replaceForSupplement.veh_Xnhgzbh_R=dpCarstorage.web_virtualcode
                    replaceForSupplement.veh_Clzzqymc_R = dpCarstorage.veh_Clzzqymc
                    replaceForSupplement.veh_Clpp_R = dpCarstorage.veh_Clpp
                    replaceForSupplement.veh_Clmc_R = dpCarstorage.veh_Clmc
                    replaceForSupplement.veh_Clxh_R = dpCarstorage.veh_Clxh

//            2013年8月24日 去掉车身颜色跟随公告信息 车身颜色进行修改
                    replaceForSupplement.veh_Clfl_R = dpCarstorage.veh_Clfl
                    replaceForSupplement.veh_Dpxh_R = dpCarstorage.veh_Dpxh
                    replaceForSupplement.veh_Fdjxh_R = dpCarstorage.veh_Fdjxh
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
                    replaceForSupplement.veh_Ggpc_R = dpCarstorage.veh_Ggpc
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
                    replaceForSupplement.veh_Zzllyxs_R = dpCarstorage.veh_Zzllyxs
                    replaceForSupplement.veh_Zqyzzl_R = dpCarstorage.veh_Zqyzzl
                    replaceForSupplement.veh_Bgcazzdyxzzl_R = dpCarstorage.veh_Bgcazzdyxzzl
                    replaceForSupplement.veh_Jsszcrs_R = dpCarstorage.veh_Jsszcrs
                    replaceForSupplement.veh_Edzk_R = dpCarstorage.veh_Edzk
                    replaceForSupplement.veh_Zgcs_R = dpCarstorage.veh_Zgcs
                    replaceForSupplement.veh_Bz_R = dpCarstorage.veh_Bz

                    replaceForSupplement.veh_Csys_R = params.veh_Csys_R_Dp
                    replaceForSupplement.veh_Need_Cer = Integer.parseInt(params.veh_Need_Cer)
                    replaceForSupplement.veh_Zzbh = params.veh_Zzbh
                    replaceForSupplement.veh_Fdjh_R = params.veh_Fdjh_Dp
                    replaceForSupplement.remark = params.remark
                    replaceForSupplement.salesmanname = params.salesmanname
                    replaceForSupplement.salesmantel = params.salesmantel
                    User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
                    replaceForSupplement.createrId = loginUser.id
                    replaceForSupplement.createTime = DateUtil.getCurrentTime()
                }else{
                    render "请选择底盘公告!"
                    return
                }
                if (zcinfoReplace.save(flush: true)) {
                    replaceForSupplement.zcinfoReplaceId = zcinfoReplace.id
                    if (replaceForSupplement.save(flush: true)) {
                        render "更新已成功!"
                    } else {
                        render "更新失败!"
                    }
                } else {
                    render "更新失败!"
                }
            }else {
                zcinfoReplace.veh_VinFourBit_R = zciInfoC.veh_VinFourBit
                zcinfoReplace.veh_Cpggh_R = zciInfoC.veh_Cpggh
                zcinfoReplace.veh_zdjgl_R = zciInfoC.veh_zdjgl
                zcinfoReplace.veh_Clztxx_R = zciInfoC.veh_Clztxx
                zcinfoReplace.veh_Qyqtxx_R = zciInfoC.veh_Qyqtxx   //企业其它信息
                zcinfoReplace.veh_Dpid_R = zciInfoC.veh_Dpid
                zcinfoReplace.veh_Jsslx_R = zciInfoC.veh_Jsslx
                zcinfoReplace.veh_pzxlh_R = zciInfoC.veh_pzxlh
                zcinfoReplace.veh_Ggsxrq_R = zciInfoC.veh_Ggsxrq
                zcinfoReplace.veh_Cpscdz_R = zciInfoC.veh_Cpscdz
                zcinfoReplace.veh_Qybz_R = zciInfoC.veh_Qybz
                zcinfoReplace.veh_Hzdfs_R = zciInfoC.veh_Hzdfs
                zcinfoReplace.veh_Qyid_R = zciInfoC.veh_Qyid
//        zcinfoReplace.veh_Xnhgzbh_R=zciInfoC.web_virtualcode
                zcinfoReplace.veh_Clzzqymc_R = zciInfoC.veh_Clzzqymc
                zcinfoReplace.veh_Clpp_R = zciInfoC.veh_Clpp
                zcinfoReplace.veh_Clmc_R = zciInfoC.veh_Clmc
                zcinfoReplace.veh_Clxh_R = zciInfoC.veh_Clxh

//            2013年8月24日 去掉车身颜色跟随公告信息 车身颜色进行修改
                zcinfoReplace.veh_Clfl_R = zciInfoC.veh_Clfl
                zcinfoReplace.veh_Dpxh_R = zciInfoC.veh_Dpxh
                zcinfoReplace.veh_Fdjxh_R = zciInfoC.veh_Fdjxh
                zcinfoReplace.veh_Rlzl_R = zciInfoC.veh_Rlzl
                zcinfoReplace.veh_Pl_R = zciInfoC.veh_Pl
                zcinfoReplace.veh_Gl_R = zciInfoC.veh_Gl
                zcinfoReplace.veh_Lts_R = zciInfoC.veh_Lts
                zcinfoReplace.veh_Pfbz_R = zciInfoC.veh_Pfbz

                zcinfoReplace.veh_Yh_R = zciInfoC.veh_Yh
                zcinfoReplace.veh_Wkc_R = zciInfoC.veh_Wkc
                zcinfoReplace.veh_Wkk_R = zciInfoC.veh_Wkk
                zcinfoReplace.veh_Wkg_R = zciInfoC.veh_Wkg
                zcinfoReplace.veh_Hxnbc_R = zciInfoC.veh_Hxnbc
                zcinfoReplace.veh_Hxnbk_R = zciInfoC.veh_Hxnbk
                zcinfoReplace.veh_Hxnbg_R = zciInfoC.veh_Hxnbg
                zcinfoReplace.veh_Ltgg_R = zciInfoC.veh_Ltgg
                zcinfoReplace.veh_Gbthps_R = zciInfoC.veh_Gbthps

                zcinfoReplace.veh_Qlj_R = zciInfoC.veh_Qlj
                zcinfoReplace.veh_Hlj_R = zciInfoC.veh_Hlj
                zcinfoReplace.veh_Zj_R = zciInfoC.veh_Zj
                zcinfoReplace.veh_Zh_R = zciInfoC.veh_Zh
                zcinfoReplace.veh_Zs_R = zciInfoC.veh_Zs
                zcinfoReplace.veh_Zxxs_R = zciInfoC.veh_Zxxs
                zcinfoReplace.veh_Zzl_R = zciInfoC.veh_Zzl
                zcinfoReplace.veh_Zbzl_R = zciInfoC.veh_Zbzl
                zcinfoReplace.veh_Edzzl_R = zciInfoC.veh_Edzzl

                zcinfoReplace.veh_Zzllyxs_R = zciInfoC.veh_Zzllyxs
                zcinfoReplace.veh_Zqyzzl_R = zciInfoC.veh_Zqyzzl
                zcinfoReplace.veh_Bgcazzdyxzzl_R = zciInfoC.veh_Bgcazzdyxzzl
                zcinfoReplace.veh_Jsszcrs_R = zciInfoC.veh_Jsszcrs
                zcinfoReplace.veh_Edzk_R = zciInfoC.veh_Edzk
                zcinfoReplace.veh_Zgcs_R = zciInfoC.veh_Zgcs
                zcinfoReplace.veh_Bz_R = zciInfoC.veh_Bz

                zcinfoReplace.veh_Csys_R = params.veh_Csys_R
                zcinfoReplace.veh_Need_Cer = Integer.parseInt(params.veh_Need_Cer)
                zcinfoReplace.veh_Zzbh = params.veh_Zzbh
                zcinfoReplace.veh_Fdjh_R = params.veh_Fdjh
                zcinfoReplace.remark = params.remark
                zcinfoReplace.salesmanname = params.salesmanname
                zcinfoReplace.salesmantel = params.salesmantel
                if (!zcinfoReplace.save(flush: true)) {
                    render "保存失败,查看数据格式后重试!"
                    return
                } else {
                    render "更新已成功!"
                }
            }
        }else{
            render "请选择整车公告!"
            return
        }
    }

    /**
     * 变更合格证 详细函数
     * @author zouQ
     * @return
     */
    def zcinfoReplaceView() {
        def zcinfoReAuInstance = ZCInfoReplace.get(params.id)
        def replaceForSupplement = ReplaceForSupplement.findByZcinfoReplaceId(params.id)
        if (!zcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "list", params: params)
            return
        }
        render(view: '/cn/com/wz/vehcert/zcinfo/dealer/zcinfoReplaceView', model: [zcinfoReAuInstance: zcinfoReAuInstance, replaceForSupplement:replaceForSupplement,searchType: params.searchType])
    }
    /**
     * @Description 修改没有SAP号的变更信息
     * @CreateTime 2018-04-13
     * @Author QJ
     */
    def zcinfoReplaceAddSapNo() {
        def params = params
        def id = params?.id
        def SAP_No = params?.SAP_No
        def msg
        def replaceInfoInstance = ZCInfoReplace.get(id)
        def veh_coc_status = replaceInfoInstance.veh_coc_status
        def vin = replaceInfoInstance.veh_Clsbdh
        def vin_r = replaceInfoInstance.veh_Clsbdh_R
        try {
            replaceInfoInstance.SAP_No = SAP_No
            replaceInfoInstance.save(flush: true)
            msg = 'SAP序列号更新成功'
        } catch (DataIntegrityViolationException e) {
            msg = 'SAP序列号更新失败'
        }
        def responseMsg = '变更前VIN：' + vin + '，变更后VIN：' + vin_r + '，审核状态：' + veh_coc_status + '，操作结果：' + msg
        User loginUser = getCurrentUser()
        def userId = loginUser?.getId() //操作人
        logService.insertLog(userId, "addSapNo", responseMsg, "systemLog")
        render msg

    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult() {
        render session.getAttribute('compFlag')
    }
    /**
     *   经销商 （合格证更换申请）基础变更 导出
     * @author zouq
     */
    def export_jc() {
        def rows = []
        def lst = []
        def content = []
        try {
            User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
            if (params?.format && params.format != "html") {
                def encodedFilename = URLEncoder.encode(("合格证更换申请基础变更（经销商）"), "UTF-8")
                def filename = ""
                def userAgent = request.getHeader("User-Agent")
                if (userAgent =~ /MSIE [4-8]/) {
                    // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename=\"${encodedFilename}." + params.extension + "\""
                } else {
                    // Use RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename*=UTF-8''${encodedFilename}." + params.extension
                }
                response.setHeader("Content-disposition", "attachment;${filename}");
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                /* List fields = [ "veh_coc_status","createTime","veh_Zzbh","dealer","veh_Zchgzbh","veh_Clsbdh","veh_Clxh","veh_Fdjh","veh_Fdjxh",
                    "veh_Zchgzbh_R","veh_Clsbdh_R","veh_Clxh_R","veh_Fdjh_R","veh_Fdjxh_R","salesmanname","salesmantel","changetime","remark"]*/

                Map labels = ["veh_coc_status": "审核状态 0未审核 1审核通过 2审核未通过", "createTime": "申请日期", "veh_Zzbh": "纸张编号", "dealer": "经销商",
                              "veh_Zchgzbh"   : "旧合格证编号", "veh_Zchgzbh_0": "旧完整合格证编号", "veh_Clsbdh": "旧车架号", "veh_Clxh": "旧公告号", "veh_Fdjh": "旧发动机号", "veh_Fdjxh": "旧发动机型号", "veh_Zchgzbh_R": "新合格证编号", "veh_Zchgzbh_0_R": "新完整合格证编号", "veh_Clsbdh_R": "新车架号", "veh_Clxh_R": "新公告号", "veh_Fdjh_R": "新发动机号", "veh_Fdjxh_R": "新发动机型号", "salesmanname": "业务员姓名"
                              , "salesmantel" : "业务员电话", "changetime": "审核日期", "remark": "申请备注"]
                //导出的Excle中增加新、旧完整合格证编号
                def upperCase = { domain, value ->
                    return value.toUpperCase()
                }

                def out = response.outputStream
                params.sort = 'createTime'
                params.order = "desc"

                rows = zcInfoReplaceService.selectZcInfoReplace(params, loginUser)
                rows.each { r ->
                    def m = [:]
                    m.veh_Zchgzbh = r.veh_Zchgzbh
                    m.veh_Zchgzbh_0 = r.veh_Zchgzbh_0       //导出的Excle中添加旧整车合格证编号
                    m.veh_Clsbdh = r.veh_Clsbdh
                    m.veh_Clxh = r.veh_Clxh
                    m.veh_Fdjh = r.veh_Fdjh
                    m.veh_Fdjxh = r.veh_Fdjxh
                    m.veh_Zchgzbh_R = r.veh_Zchgzbh_R
                    m.veh_Zchgzbh_0_R = r.veh_Zchgzbh_0_R //导出的Excle中添加新完整合格证编号
                    m.veh_Clsbdh_R = r.veh_Clsbdh_R
                    m.veh_Clxh_R = r.veh_Clxh_R
                    m.veh_coc_status = r.veh_coc_status
                    m.veh_Fdjh_R = r.veh_Fdjh_R
                    m.veh_Fdjxh_R = r.veh_Fdjxh_R
                    m.salesmanname = r.salesmanname
                    m.salesmantel = r.salesmantel
                    m.changetime = r.authTime
                    m.createTime = r.createTime
                    def userC = User.get(r.createrId)
                    if (userC) {
                        m.dealer = userC.userDetail.realName
                    } else {
                        m.dealer = ''
                    }
                    m.veh_Zzbh = r.veh_Zzbh
                    m.remark = r.remark

                    lst.add(m)
                }
                ExcelUtils excelOp = new ExcelUtils(grailsApplication.config.server.file.encode);
                def m = []
                m.add(labels)
                content.add(lst)
                excelOp.preWriteExcel(out, null, m, ["合格证更换申请基础变更（经销商）"], content)
                session.setAttribute('compFlag', "success")
                out.flush()
                out.close()
            }
        } catch (Exception e) {
            e.printStackTrace()
            session.setAttribute('compFlag', "error")
        } finally {
            rows.clear()
            lst.clear()
            content.clear()
        }
    }

    /**
     *   经销商 （合格证更换申请）日期变更 导出
     * @author zouq
     */
    def export_rq() {
        def rows = []
        def lst = []
        def content = []
        try {
            User loginUser = session.getAttribute(ConstantsUtil.LOGIN_USER)
            if (params?.format && params.format != "html") {
                def encodedFilename = URLEncoder.encode(("合格证更换申请日期变更（经销商）"), "UTF-8")
                def filename = ""
                def userAgent = request.getHeader("User-Agent")
                if (userAgent =~ /MSIE [4-8]/) {
                    // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename=\"${encodedFilename}." + params.extension + "\""
                } else {
                    // Use RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename*=UTF-8''${encodedFilename}." + params.extension
                }
                response.setHeader("Content-disposition", "attachment;${filename}");
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                // List fields = [ "veh_Zchgzbh","veh_Xnhgzbh","veh_Clxh","veh_Clmc","veh_Fdjxh","veh_Zzbh_R","veh_Fzrq","veh_Fzrq_R","createName","createTime"]

                Map labels = ["veh_coc_status": "审核状态 0未审核 1审核通过 2审核未通过", "veh_Zchgzbh": "整车合格证编号", "veh_Zchgzbh_0": "完整合格证编号", "veh_Xnhgzbh": "虚拟合格证编号", "veh_Clxh": "车辆型号", "veh_Clmc": "车辆名称", "veh_Fdjxh": "发动机型号",
                              "veh_Zzbh_R"    : "纸张编号", "veh_Fzrq": "变更前日期", "veh_Fzrq_R": "变更后日期", "createName": "变更人", "createTime": "变更时间"]
                //导出的Excle中新加完整合格证编号

                def out = response.outputStream
                params.sort = 'createTime'
                params.order = "desc"
                rows = zcInfoReplaceService.selectZcInfoReplace(params, loginUser)
                rows.each {
                    def m = [:]
                    it.properties.each {
                        m."${it.key}" = it.value
                    }
                    lst.add(m)
                }
                ExcelUtils excelOp = new ExcelUtils(grailsApplication.config.server.file.encode);
                def m = []
                m.add(labels)
                content.add(lst)
                excelOp.preWriteExcel(out, null, m, ["合格证更换申请日期变更（经销商）"], content)
                session.setAttribute('compFlag', "success")
                out.flush()
                out.close()

            }
        } catch (Exception e) {
            e.printStackTrace()
            session.setAttribute('compFlag', "error")
        } finally {
            rows.clear()
            lst.clear()
            content.clear()
        }
    }
    /**
     * @Description 根据id来寻找区域下的经销商
     * @author liuly
     * 2013-8-28
     */
    def findUserDetail() {
        def cel = {
            user {
                organs {
                    eq('id', params.distributor)
                }
            }
        }
        def userDetail = UserDetail.createCriteria().list(cel)
        def result = [rows: userDetail, total: userDetail.size()]
        render result as JSON
    }
    /**
     * @Description 根据id来寻找区域下的经销商
     * @author liuly
     * 2013-8-28
     */
    def findDistributor() {
        def cel = {

            parent {
                eq('organCode', 'distributor')
            }

        }
        def organ = Organization.createCriteria().list(cel)
        def lst = []
        organ.each { o ->
            def m = [:]
            m.id = o.id
            m.organNameC = o.organNameC
            lst.add(m)
        }
        render lst as JSON
    }

    /**
     * @Description 导出全部信息
     * @author liuly
     * 2013-9-6
     */

    def exportReplace() {
        def rows = []
        def lst = []
        def content = []
        try {
            if (params?.format && params.format != "html") {
                def encodedFilename = URLEncoder.encode(("需要修改（撤销）的合格证信息"), "UTF-8")
                def filename = ""
                def userAgent = request.getHeader("User-Agent")
                if (userAgent =~ /MSIE [4-8]/) {
                    // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename=\"${encodedFilename}." + params.extension + "\""
                } else {
                    // Use RFC 6266 (RFC 2231/RFC 5987)
                    filename = "filename*=UTF-8''${encodedFilename}." + params.extension
                }
                response.setHeader("Content-disposition", "attachment;${filename}");
                response.contentType = grailsApplication.config.grails.mime.types[params.format]
                Map labels = ["veh_Zchgzbh"  : "合格证编号", "veh_Dphgzbh": "底盘合格证编号", "veh_Fzrq": "发证日期", "veh_Clzzqymc": "汽车制造企业名称", "veh_Qyid": "企业ID", "veh_Clfl": "车辆分类",
                              "veh_Clmc"     : "车辆名称", "veh_Clpp": "车辆品牌", "veh_Clxh": "车辆型号", "veh_Csys": "车身颜色", "veh_Dpxh": "底盘型号", "veh_Dpid": "底盘ID", "veh_Clsbdh": "车辆识别代号", "veh_Cjh": "车架号",
                              "veh_Fdjh"     : "发动机号", "veh_Fdjxh": "发动机型号", "veh_Rlzl": "燃料种类", "veh_Pl": "排量", "veh_Gl": "功率", "veh_zdjgl": "最大净功率", "veh_Zxxs": "转向形式", "veh_Qlj": "前轮距", "veh_Hlj": "后轮距", "veh_Lts": "轮胎呢数",
                              "veh_Ltgg"     : "轮胎规格", "veh_Gbthps": "钢板弹簧片数", "veh_Zj": "轴距", "veh_Zh": "轴荷", "veh_Zs": "轴数", "veh_Wkc": "外廓长", "veh_Wkk": "外廓宽", "veh_Wkg": "外廓高", "veh_Hxnbc": "货箱内部长", "veh_Hxnbk": "货箱内部宽", "veh_Hxnbg": "货箱内部高",
                              "veh_Zzl"      : "总质量", "veh_Edzzl": "额定载质量", "veh_Zbzl": "整备质量", "veh_Zzllyxs": "载质量利用系数", "veh_Zqyzzl": "准牵引总质量", "veh_Edzk": "额定载客", "veh_Bgcazzdyxzzl": "半挂车鞍座最大允许总质量", "veh_Jsszcrs": "驾驶室准乘人数", "veh_Qzdfs": "前制动方式", "veh_Hzdfs": "后制动方式",
                              "veh_Qzdczfs"  : "前制动操作方式", "veh_Hzdczfs": "后制动操作方式", "veh_Cpggh": "产品公告号", "veh_Ggsxrq": "公告生效日期", "veh_Zzbh": "纸张编号", "veh_Dywym": "打印唯一码", "veh_Zgcs": "最高车速", "veh_Clzzrq": "车辆制造日期",
                              "veh_Qybz"     : "企业标准", "veh_Cpscdz": "产品生产地址", "veh_Qyqtxx": "企业其他信息", "veh_Pfbz": "排放标准", "veh_Clztxx": "车辆状态信息", "veh_Jss": "驾驶室", "veh_Jsslx": "驾驶室类型", "veh_Zchgzbh_0": "完整合格证编号", "used": "标识", "used2": "标识"
                              , "upload"     : "标识", "vercode": "二维码", "veh_Yh": "油耗", "veh_VinFourBit": "VIN第四位", "veh_Ggpc": "公告批次", "veh_pzxlh": "配置序列号", "veh_Zchgzbh_R": "新合格证编号", "veh_Dphgzbh_R": "新底盘合格证编号", "veh_Fzrq_R": "新发证日期", "veh_Clzzqymc_R": "新汽车制造企业名称",
                              "veh_Qyid_R"   : "新企业ID", "veh_Clfl_R": "新车辆分类", "veh_Clmc_R": "新车辆名称", "veh_Clpp_R": "新车辆品牌", "veh_Clxh_R": "新车辆型号", "veh_Csys_R": "新车辆颜色", "veh_Dpxh_R": "新底盘型号", "veh_Dpid_R": "新底盘ID", "veh_Clsbdh_R": "新车辆识别代号", "veh_Cjh_R": "新车架号", "veh_Fdjh_R": "新发动机号",
                              "veh_Fdjxh_R"  : "新发动机型号", "veh_Rlzl_R": "新燃料种类", "veh_Pl_R": "新排量", "veh_Gl_R": "新功率", "veh_zdjgl_R": "新最大净功率", "veh_Zxxs_R": "新转向形式", "veh_Qlj_R": "新前轮距", "veh_Hlj_R": "新后轮距", "veh_Lts_R": "新轮胎数", "veh_Ltgg_R": "新轮胎规格", "veh_Gbthps_R": "新弹簧片数",
                              "veh_Zj_R"     : "新轴距", "veh_Zh_R": "新轴荷", "veh_Zs_R": "新轴数", "veh_Wkc_R": "新外廓长", "veh_Wkk_R": "新外廓宽", "veh_Wkg_R": "新外廓高", "veh_Hxnbc_R": "新货箱长", "veh_Hxnbk_R": "新货箱宽", "veh_Hxnbg_R": "新货箱高", "veh_Zzl_R": "新总质量",
                              "veh_Edzzl_R"  : "新额定载质量", "veh_Zbzl_R": "新整备质量", "veh_Zzllyxs_R": "新载质量利用系数", "veh_Zqyzzl_R": "新准牵引总质量", "veh_Edzk_R": "新额定载客", "veh_Bgcazzdyxzzl_R": "新半挂车鞍座最大允许总质量", "veh_Jsszcrs_R": "新驾驶室准乘人数", "veh_Qzdfs_R": "新前制动方式", "veh_Hzdfs_R": "新后制动方式",
                              "veh_Qzdczfs_R": "新前制动操作方式", "veh_Hzdczfs_R": "新后制动操作方式", "veh_Cpggh_R": "新产品公告号", "veh_Ggsxrq_R": "新公告生效日期", "veh_Zzbh_R": "新纸张编号", "veh_Dywym_R": "新打印唯一码", "veh_Zgcs_R": "新最高车速", "veh_Clzzrq_R": "新车辆制造日期", "veh_Bz_R": "新备注",
                              "veh_Qybz_R"   : "新企业标准", "veh_Cpscdz_R": "新产品生产地址", "veh_Qyqtxx_R": "新企业其他信息", "veh_Pfbz_R": "新排放标准", "veh_Clztxx_R": "新车辆状态信息", "veh_Jss_R": "新驾驶室", "veh_Jsslx_R": "新驾驶室类型", "veh_Zchgzbh_0_R": "新完整合格证编号", "used_r": "新标识", "used2_r": "新标识",
                              "upload_r"     : "新标识", "vercode_r": "新二维码", "veh_Yh_R": "新油耗", "veh_VinFourBit_R": "新VIN第四位", "veh_Ggpc_R": "新公告批次", "veh_pzxlh_R": "新配置序列号"]

                def out = response.outputStream
                def cel = {
                    //判断是否换号
                    if ("1".equals(params.changeType)) {
                        sqlRestriction("veh_Zchgzbh<>veh_Zchgzbh_R")
                    } else if ("0".equals(params.changeType)) {
                        sqlRestriction("veh_Zchgzbh=veh_Zchgzbh_R")
                    }
                    if (params.veh_Clsbdh) {
                        like('veh_Clsbdh', "%${params.veh_Clsbdh}%")
                    }
                    if (params.veh_Fdjh) {
                        like('veh_Fdjh', "%${params.veh_Fdjh}%")
                    }
                    ne('change_Field', 'veh_Fzrq')
                    if (params.veh_managestatus) {
                        eq('veh_managestatus', Integer.valueOf(params.veh_managestatus))
                    }
                    or {
                        and {
                            eq('veh_usertype', 1) //公告资源部
                            eq('veh_isupload', 1)
                        }
                        and {
                            eq('veh_usertype', 0)  //经销商
                            eq('veh_isupload', 1)  //已经上传国家
                            eq('veh_coc_status', 1)  //审核通过
                        }
                    }

                }
                rows = ZCInfoReplace.createCriteria().list(params, cel)
                rows.each { r ->
                    def m = [:]
                    if (r.veh_Need_Cer == 0) {
                        m.veh_Need_Cer = '整车'
                    } else if (r.veh_Need_Cer == 1) {
                        m.veh_Need_Cer = '底盘'
                    } else {
                        m.veh_Need_Cer = '底盘和整车'
                    }
                    m.veh_Zchgzbh = r.veh_Zchgzbh//整车合格证编号
                    m.veh_Dphgzbh = r.veh_Dphgzbh//底盘合格证编号
                    m.veh_Fzrq = r.veh_Fzrq//发证日期
                    m.veh_Clzzqymc = r.veh_Clzzqymc //车辆制造企业名称
                    m.veh_Qyid = r.veh_Qyid//企业ID
                    m.veh_Clfl = r.veh_Clfl//车辆分类
                    m.veh_Clmc = r.veh_Clmc//车辆名称
                    m.veh_Clpp = r.veh_Clpp//车辆品牌
                    m.veh_Clxh = r.veh_Clxh//车辆型号
                    m.veh_Csys = r.veh_Csys//车身颜色
                    m.veh_Dpxh = r.veh_Dpxh//底盘型号
                    m.veh_Dpid = r.veh_Dpid//底盘ID
                    m.veh_Clsbdh = r.veh_Clsbdh//车辆识别代号
                    m.veh_Cjh = r.veh_Cjh//车架号
                    m.veh_Fdjh = r.veh_Fdjh//发动机号

                    m.veh_Fdjxh = r.veh_Fdjxh//发动机型号
                    m.veh_Rlzl = r.veh_Rlzl//燃料种类
                    m.veh_Pl = r.veh_Pl//排量
                    m.veh_Gl = r.veh_Gl//功率
                    m.veh_zdjgl = r.veh_zdjgl//最大净功率
                    m.veh_Zxxs = r.veh_Zxxs//转向形式
                    m.veh_Qlj = r.veh_Qlj//前轮距
                    m.veh_Hlj = r.veh_Hlj//后轮距
                    m.veh_Lts = r.veh_Lts//轮胎数
                    m.veh_Ltgg = r.veh_Ltgg//轮胎规格
                    m.veh_Gbthps = r.veh_Gbthps//钢板弹簧片数
                    m.veh_Zj = r.veh_Zj//轴距
                    m.veh_Zh = r.veh_Zh//轴荷
                    m.veh_Zs = r.veh_Zs//轴数
                    m.veh_Wkc = r.veh_Wkc//外廓长
                    m.veh_Wkk = r.veh_Wkk//外廓宽
                    m.veh_Wkg = r.veh_Wkg//外廓高
                    m.veh_Hxnbc = r.veh_Hxnbc//货箱内部长
                    m.veh_Hxnbk = r.veh_Hxnbk//货箱内部宽
                    m.veh_Hxnbg = r.veh_Hxnbg//货箱内部高
                    m.veh_Zzl = r.veh_Zzl//总质量
                    m.veh_Edzzl = r.veh_Edzzl//额定载质量
                    m.veh_Zbzl = r.veh_Zbzl//整备质量
                    m.veh_Zzllyxs = r.veh_Zzllyxs//载质量利用系数
                    m.veh_Zqyzzl = r.veh_Zqyzzl//准牵引总质量
                    m.veh_Edzk = r.veh_Edzk//额定载客
                    m.veh_Bgcazzdyxzzl = r.veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
                    m.veh_Jsszcrs = r.veh_Jsszcrs//驾驶室准乘人数
                    m.veh_Qzdfs = r.veh_Qzdfs//前制动方式
                    m.veh_Hzdfs = r.veh_Hzdfs//后制动方式
                    m.veh_Qzdczfs = r.veh_Qzdczfs//前制动操作方式
                    m.veh_Hzdczfs = r.veh_Hzdczfs//后制动操作方式
                    m.veh_Cpggh = r.veh_Cpggh//产品公告号
                    m.veh_Ggsxrq = r.veh_Ggsxrq//公告生效日期
                    m.veh_Zzbh = r.veh_Zzbh//纸张编号
                    m.veh_Dywym = r.veh_Dywym//打印唯一码
                    m.veh_Zgcs = r.veh_Zgcs//最高车速
                    m.veh_Clzzrq = r.veh_Clzzrq//车辆制造日期
                    m.veh_Bz = r.veh_Bz//备注
                    m.veh_Qybz = r.veh_Qybz//企业标准
                    m.veh_Cpscdz = r.veh_Cpscdz//产品生产地址
                    m.veh_Qyqtxx = r.veh_Qyqtxx//企业其它信息
                    m.veh_Pfbz = r.veh_Pfbz//排放标准
                    m.veh_Clztxx = r.veh_Clztxx//车辆状态信息
                    m.veh_Jss = r.veh_Jss//驾驶室
                    m.veh_Jsslx = r.veh_Jsslx//驾驶室类型
                    m.veh_Zchgzbh_0 = r.veh_Zchgzbh_0//完整合格证编号
                    m.used = r.used//used
                    m.used2 = r.used2//used2
                    m.upload = r.upload //上传路径
                    m.vercode = r.vercode//二维码
                    m.veh_Yh = r.veh_Yh//油耗
                    m.veh_VinFourBit = r.veh_VinFourBit //vin第四位
                    m.veh_Ggpc = r.veh_Ggpc//公告批次
                    m.veh_createTime = r.veh_createTime //创建时间
                    m.veh_createrId = r.veh_createrId
                    m.veh_pzxlh = r.veh_pzxlh//配置序列号
                    m.upload_Path = r.upload_Path//合格证上传相对路径
                    m.veh_clzzqyxx = r.veh_clzzqyxx//车辆制造企业信息


                    m.veh_Type_R = r.veh_Type_R//汽车和农用车标示
                    m.veh_Xnhgzbh_R = r.veh_Xnhgzbh_R//虚拟合格证编号
                    m.veh_Zchgzbh_R = r.veh_Zchgzbh_R//整车合格证编号
                    m.veh_Dphgzbh_R = r.veh_Dphgzbh_R//底盘合格证编号
                    m.veh_Fzrq_R = r.veh_Fzrq_R//发证日期
                    m.veh_Clzzqymc_R = r.veh_Clzzqymc_R //车辆制造企业名称
                    m.veh_Qyid_R = r.veh_Qyid_R//企业ID
                    m.veh_Clfl_R = r.veh_Clfl_R//车辆分类
                    m.veh_Clmc_R = r.veh_Clmc_R//车辆名称
                    m.veh_Clpp_R = r.veh_Clpp_R//车辆品牌
                    m.veh_Clxh_R = r.veh_Clxh_R//车辆型号
                    m.veh_Csys_R = r.veh_Csys_R//车身颜色
                    m.veh_Dpxh_R = r.veh_Dpxh_R//底盘型号
                    m.veh_Dpid_R = r.veh_Dpid_R//底盘ID
                    m.veh_Clsbdh_R = r.veh_Clsbdh_R//车辆识别代号
                    m.veh_Cjh_R = r.veh_Cjh_R//车架号
                    m.veh_Fdjh_R = r.veh_Fdjh_R//发动机号

                    m.veh_Fdjxh_R = r.veh_Fdjxh_R//发动机型号
                    m.veh_Rlzl_R = r.veh_Rlzl_R//燃料种类
                    m.veh_Pl_R = r.veh_Pl_R//排量
                    m.veh_Gl_R = r.veh_Gl_R//功率
                    m.veh_zdjgl_R = r.veh_zdjgl_R//最大净功率
                    m.veh_Zxxs_R = r.veh_Zxxs_R//转向形式
                    m.veh_Qlj_R = r.veh_Qlj_R//前轮距
                    m.veh_Hlj_R = r.veh_Hlj_R//后轮距
                    m.veh_Lts_R = r.veh_Lts_R//轮胎数
                    m.veh_Ltgg_R = r.veh_Ltgg_R//轮胎规格
                    m.veh_Gbthps_R = r.veh_Gbthps_R//钢板弹簧片数
                    m.veh_Zj_R = r.veh_Zj_R//轴距
                    m.veh_Zh_R = r.veh_Zh_R//轴荷
                    m.veh_Zs_R = r.veh_Zs_R//轴数
                    m.veh_Wkc_R = r.veh_Wkc_R//外廓长
                    m.veh_Wkk_R = r.veh_Wkk_R//外廓宽
                    m.veh_Wkg_R = r.veh_Wkg_R//外廓高
                    m.veh_Hxnbc_R = r.veh_Hxnbc_R//货箱内部长
                    m.veh_Hxnbk_R = r.veh_Hxnbk_R//货箱内部宽
                    m.veh_Hxnbg_R = r.veh_Hxnbg_R//货箱内部高
                    m.veh_Zzl_R = r.veh_Zzl_R//总质量
                    m.veh_Edzzl_R = r.veh_Edzzl_R//额定载质量
                    m.veh_Zbzl_R = r.veh_Zbzl_R//整备质量
                    m.veh_Zzllyxs_R = r.veh_Zzllyxs_R//载质量利用系数
                    m.veh_Zqyzzl_R = r.veh_Zqyzzl_R//准牵引总质量
                    m.veh_Edzk_R = r.veh_Edzk_R//额定载客
                    m.veh_Bgcazzdyxzzl_R = r.veh_Bgcazzdyxzzl_R //半挂车按座
                    m.veh_Jsszcrs_R = r.veh_Jsszcrs_R//驾驶室准乘人数
                    m.veh_Qzdfs_R = r.veh_Qzdfs_R//前制动方式
                    m.veh_Hzdfs_R = r.veh_Hzdfs_R//后制动方式
                    m.veh_Qzdczfs_R = r.veh_Qzdczfs_R//前制动操作方式
                    m.veh_Hzdczfs_R = r.veh_Hzdczfs_R//后制动操作方式
                    m.veh_Cpggh_R = r.veh_Cpggh_R//产品公告号
                    m.veh_Ggsxrq_R = r.veh_Ggsxrq_R//公告生效日期
                    m.veh_Zzbh_R = r.veh_Zzbh_R//纸张编号
                    m.veh_Dywym_R = r.veh_Dywym_R//打印唯一码
                    m.veh_Zgcs_R = r.veh_Zgcs_R//最高车速
                    m.veh_Clzzrq_R = r.veh_Clzzrq_R//车辆制造日期
                    m.veh_Bz_R = r.veh_Bz_R//备注
                    m.veh_Qybz_R = r.veh_Qybz_R//企业标准
                    m.veh_Cpscdz_R = r.veh_Cpscdz_R//产品生产地址
                    m.veh_Qyqtxx_R = r.veh_Qyqtxx_R//企业其它信息
                    m.veh_Pfbz_R = r.veh_Pfbz_R//排放标准
                    m.veh_Clztxx_R = r.veh_Clztxx_R//车辆状态信息
                    m.veh_Jss_R = r.veh_Jss_R//驾驶室
                    m.veh_Jsslx_R = r.veh_Jsslx_R//驾驶室类型
                    m.veh_Zchgzbh_0_R = r.veh_Zchgzbh_0_R//完整合格证编号
                    m.used_r = r.used_r
                    m.used2_r = r.used2_r
                    m.upload_r = r.upload_r //
                    m.vercode_r = r.vercode_r //二维码
                    m.veh_Yh_R = r.veh_Yh_R//油耗
                    m.veh_VinFourBit_R = r.veh_VinFourBit_R //vin第四位
                    m.veh_Ggpc_R = r.veh_Ggpc_R//公告批次
                    m.veh_pzxlh_R = r.veh_pzxlh_R//配置序列号
                    m.upload_Path_R = r.upload_Path_R//合格证上传相对路径
                    m.veh_clzzqyxx_R = r.veh_clzzqyxx_R//车辆制造企业信息
                    if (r.veh_usertype == 0) {
                        m.veh_usertype = '经销商修改、更换'
                    } else {
                        m.veh_usertype = '公告资源部修改'  //合格证修改记录作者类型
                    }
                    if (r.veh_isupload == 0) {
                        m.veh_isupload = '未上传到国家'  //原合格证信息是否已经上传到国家系统
                    } else {
                        m.veh_isupload = '已上传到国家'
                    }
                    if (r.veh_managestatus == 0) {
                        m.veh_managestatus = '待处理'
                    } else {
                        m.veh_managestatus = '处理完毕'//针对已经上传到国家的记录进行修改  由公告资源部确认处理情况
                    }
                    if (r.veh_coc_status == 0) {
                        m.veh_coc_status = '待审核'
                    } else if (r.veh_coc_status == 1) {
                        m.veh_coc_status = '审核通过'//审核状态
                    } else {
                        m.veh_coc_status = '审核失败'
                    }

                    if (r.statusReco == 0) {
                        m.statusReco = '未回收'
                    } else {
                        m.statusReco = '已回收'
                    }
                    lst.add(m)
                }
                ExcelUtils excelOp = new ExcelUtils(grailsApplication.config.server.file.encode);
                def m = []
                m.add(labels)
                content.add(lst)
                excelOp.preWriteExcel(out, null, m, ["合格证上传成功"], content)
                session.setAttribute('compFlag', "success")
                out.flush()
                out.close()
            }
        } catch (Exception e) {
            e.printStackTrace()
            session.setAttribute('compFlag', "error")
        } finally {
            rows.clear()
            lst.clear()
            content.clear()
        }
    }
    /**
     * @Description 手动将合格证变更的信息推送到CRM/SAP
     * @param
     * @return
     * @Auther QJ
     * @createTime 2018-03-30
     */
    def transToCrm() {
        newDmsSynService.newUpdateVehicle() //手动推送当天更换的合格证信息传给CRM
        def msg = '已将变更车辆信息同步至CRM，详情请看与CRM同步日志'
        render msg
    }
    /**
     * @Description 手动将合格证变更的信息推送到SAP
     * @param
     * @return
     * @Auther QJ
     * @createTime 2018-03-30
     */
    def transToSap() {
        def tranfList=SynService.findChangRecordForSap()
        if(tranfList.size()>0){
            tranfList.each{zcInfoReplace->
                if (zcInfoReplace.transToCrm==2)   {
                    SynService.synZcinfoReplace(zcInfoReplace)
                }
            }

        }
        def msg = '已将变更车辆信息同步至SAP，详情请看与SAP同步日志'
        render msg
    }
    /**
     * @Description 手动将推送SAP失败的合格证变更的信息再次推送到SAP
     * @param
     * @return
     * @Auther QJ
     * @createTime 2018-05-15
     */
    def transToSapFailure() {
        def tranfList=SynService.findFailureChangRecordForSap()
        if(tranfList.size()>0){
            tranfList.each{zcInfoReplace->
                if (zcInfoReplace.transToCrm==2)   {
                    SynService.synZcinfoReplace(zcInfoReplace)
                }
            }

        }
        def msg = '已将变更车辆信息同步至SAP，详情请看与SAP同步日志'
        render msg
    }
    /**
     * @Description 手动将选择的合格证变更的信息推送到CRM/SAP
     * @param
     * @return
     * @Auther QJ
     * @createTime 2018-04-14
     */
    def transToCrmOfSelect() {
        def ids = params.transIds.split("_")
        def replaceInfoList = [];
        User loginUser = getCurrentUser()
        def userId = loginUser?.getId() //操作人
        def transMsg
        ids?.each {
            def replaceInfo = ZCInfoReplace.get(it)
            def vin = replaceInfo.veh_Clsbdh
            def vin_r = replaceInfo.veh_Clsbdh_R
            def SAP_no = replaceInfo.SAP_No
            transMsg = '变更前VIN：' + vin + '，变更后VIN：' + vin_r + '，SAP序列号：' + SAP_no + '，尝试选择性手工推送结果：'
            if (replaceInfo.veh_coc_status == 1) {
                if (replaceInfo.veh_Clztxx == 'QX' && replaceInfo.veh_Clztxx_R == 'QX') {
                    if (replaceInfo.veh_Zchgzbh_0_R != null && replaceInfo.veh_Zchgzbh_0_R != '') {
                        if (replaceInfo.SAP_No != null && replaceInfo.SAP_No != '') {
                            transMsg = transMsg + '允许传输CRM/SAP数据'
                            logService.insertLog(userId, "transToCrmOfSelect", transMsg, "systemLog")
                            replaceInfoList.add(replaceInfo)
                        } else {
                            transMsg = transMsg + '无SAP序列号'
                            logService.insertLog(userId, "transToCrmOfSelect", transMsg, "systemLog")
                        }
                    } else {
                        transMsg = transMsg + '非正式合格证，不传CRM,SAP'
                        logService.insertLog(userId, "transToCrmOfSelect", transMsg, "systemLog")
                    }

                } else {
                    transMsg = transMsg + '变更前后车辆状态信息非整车'
                    logService.insertLog(userId, "transToCrmOfSelect", transMsg, "systemLog")
                }
            } else {
                transMsg = transMsg + '变更记录处于非通过状态' + replaceInfo.veh_coc_status
                logService.insertLog(userId, "transToCrmOfSelect", transMsg, "systemLog")
            }
        }
        if (replaceInfoList.size() > 0) {
            new NewTransChangeInfo(replaceInfoList, logService)
        }
        if (replaceInfoList.size() > 0) {
            replaceInfoList.each { zcInfoReplace ->
                if (zcInfoReplace.transToCrm == 2) {
                    SynService.synZcinfoReplace(zcInfoReplace)
                }
            }

        }
        def msg = '已将选中变更车辆信息同步至CRM/SAP，详情请看与CRM/SAP同步日志'
        render msg
    }
}