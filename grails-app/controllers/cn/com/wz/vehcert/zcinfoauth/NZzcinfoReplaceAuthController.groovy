
package cn.com.wz.vehcert.zcinfoauth

import cn.com.wz.vehcert.zcinfo.NZInfo
import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.organization.Organization
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.system.user.UserDetail
import cn.com.wz.vehcert.zcinfo.NZInfoReplace
import grails.converters.JSON;
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils

class NZzcinfoReplaceAuthController extends BaseController {
    def nzZcinfoReplaceService

    def index() {
        redirect(action:'list',params:params)
    }
    /**
     *@Description 跳转到区域经理合格证更换申请list页面
     *@param
     *@return
     *@Auther zhuwei
     *@createTime 2014-11-17
     */
    def areaAuthList() {
        def aa=params.searchType
        render(view:'/cn/com/wz/vehcert/nzZcinfoAuth/areaAuthList',model: [searchType:params.searchType,msg:params?.msg])
    }
    /**
     * @Description 区域经理审核
     * @CreateTime 2014-11-17
     * @return
     */
    def jsonListByArea(){
        def aa=params
        ///当前登录用户信息
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def user=User.get(loginUser.id)
        def aaa=user?.roles
        def nzAreaManager=false
        user?.roles.each{
            if(it.roleCode=='nzAreaManager'){
                nzAreaManager=true
            }
        }
        def organId=[]
        user.organs.each{
            organId.add(it?.id)
        }
        params.organId=organId
        params.nzAreaManager=nzAreaManager

        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort='area_status'
        params.order="asc"

        def results=nzZcinfoReplaceService.areaSelectNzInfoReplace(params,loginUser)

        def lst=[]
        results.each { l->
            def realName= User.get(l.createrId)?.userDetail?.realName
            def m=[:]
            m.id =l.id
            m.createTime=l.createTime
            m.createName=l.createName
            m.veh_Clbh=l.veh_Clbh
            m.veh_Dph =l.veh_Dph
            m.veh_Cx = l.veh_Cx
            m.veh_Fdjh=l.veh_Fdjh
            m.veh_Ccrq=l.veh_Ccrq
            m.veh_Clbh_R =l.veh_Clbh_R
            m.veh_Dph_R=l.veh_Dph_R
            m.veh_Cx_R=l.veh_Cx_R
            m.veh_Ccrq_R=l.veh_Ccrq_R
            m.veh_Fdjh_R=l.veh_Fdjh_R
            m.salesManName=l.salesManName
            m.salesManPhone=l.salesManPhone
            m.area_status=l.area_status

            lst.add(m)
        }
        def result = [rows: lst, total: results.totalCount,searchType:params?.searchType]
        render result as JSON

    }
    /**
     * @Description 区域经理审核列表查看页面
     * @CreateTime 2014-11-18 zhuwei
     */
    def areaAuditView(){
        def params=params
        def nzZcinfoReAuInstance =NZInfoReplace.get(params.id)
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        if (!nzZcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'NZINfoReplace'), params.id])
            redirect(action: "areaAuthList",params:params)
            return
        }
        render(view:'/cn/com/wz/vehcert/nzZcinfoAuth/areaAuth',model:[nzZcinfoReAuInstance:nzZcinfoReAuInstance,nzZcinfoReplaceId:nzZcinfoReAuInstance?.id])
    }
    /**
     * @Description 农装区域经理什么
     * @return
     */
    def areAudit() {
        params.searchType=1 //区域经理审核searchType=1
        def  msg='审核成功'
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def nzZcinfoReAuInstance = NZInfoReplace.get(params.id)
        if (!nzZcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "areaAuthList",params:params)
            return
        }
        nzZcinfoReAuInstance.area_Remark=params.area_Remark
        if(params?.pass=='Y'){ //区域经理审核通过，将最终审核标记由默认3改为0，进入到营销公司审核页面
            nzZcinfoReAuInstance.area_status=1
            nzZcinfoReAuInstance.auth_status=0
        }else if(params?.pass=='N'){
            nzZcinfoReAuInstance.area_status=2
        }
        nzZcinfoReAuInstance.area_AuthId =User.findById(loginUser?.id).userName
        nzZcinfoReAuInstance.setArea_AuthTime(DateUtil.getCurrentTime('yyyy-MM-dd'))
        if( nzZcinfoReAuInstance.save(flush: true)){
            println('YES!')
        }else{
            msg='审核失败'
        }
          params.msg=msg

        flash.message = message(code: 'default.operate.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), nzZcinfoReAuInstance.veh_Clbh ])
        redirect(action: "areaAuthList", params:params)

    }
    /**
     *@Description 跳转到区域经理合格证更换申请list页面
     *@param
     *@return
     *@Auther zhuwei
     *@createTime 2014-11-17
     */
    def authList() {
        render(view:'/cn/com/wz/vehcert/nzZcinfoAuth/authList',model: [searchType:params.searchType,msg:params?.msg])
    }
    /**
     * @Description 农装合格证营销公司审核
     * @CreateTime 2014-11-18 zhuwei
     */
    def jsonListByCompany(){
        def aa=params
        ///当前登录用户信息
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)

        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort='auth_status'
        params.order="asc"

        def results=nzZcinfoReplaceService.areaSelectNzInfoReplace(params,loginUser)

        def lst=[]
        results.each { l->
            def realName= User.get(l.createrId)?.userDetail?.realName
            def m=[:]
            m.id =l.id
            m.createTime=l.createTime
            m.createName=l.createName
            m.veh_Clbh=l.veh_Clbh
            m.veh_Dph =l.veh_Dph
            m.veh_Cx = l.veh_Cx
            m.veh_Fdjh=l.veh_Fdjh
            m.veh_Ccrq=l.veh_Ccrq
            m.veh_Clbh_R =l.veh_Clbh_R
            m.veh_Dph_R=l.veh_Dph_R
            m.veh_Cx_R=l.veh_Cx_R
            m.veh_Ccrq_R=l.veh_Ccrq_R
            m.veh_Fdjh_R=l.veh_Fdjh_R
            m.salesManName=l.salesManName
            m.salesManPhone=l.salesManPhone
            m.auth_status=l.auth_status

            lst.add(m)
        }
        def result = [rows: lst, total: results.totalCount,searchType:params?.searchType]
        render result as JSON
    }
    /**
     * @Description 农装营销公司审核查看
     * @CreateTime 2014-11-18 zhuwei
     */
     def auditView(){
         def params=params
         def nzZcinfoReAuInstance =NZInfoReplace.get(params.id)
         User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
         if (!nzZcinfoReAuInstance) {
             flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'NZINfoReplace'), params.id])
             redirect(action: "authList",params:params)
             return
         }
         render(view:'/cn/com/wz/vehcert/nzZcinfoAuth/auth',model:[nzZcinfoReAuInstance:nzZcinfoReAuInstance,nzZcinfoReplaceId:nzZcinfoReAuInstance?.id,msg: params.msg])
     }
    /**
     * @Description 农装营销公司审核
     * @CreateTime 2014-11-18 zhuwei
     */
    def companyAudit(){
        def aa=params
        def  msg='审核成功'
        params.searchType=1 //农装营销公司审核searchType=1
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def nzZcinfoReAuInstance = NZInfoReplace.get(params.id)
        if (!nzZcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), params.id])
            redirect(action: "authList",params:params)
            return
        }

        if(params?.change_Field=='veh_Clbh'){
            def oldNzZcinfo=NZInfo.findByVeh_Clbh(params?.veh_Clbh_R)
            if(!oldNzZcinfo){
                nzZcinfoReAuInstance.veh_Clbh_R=params?.veh_Clbh_R
                nzZcinfoReAuInstance.veh_Dph_R=params?.veh_Dph_R
            }else{
                msg=params?.veh_Clbh_R+'对应的合格证已经存在，请更换车辆编号'
                params.msg=msg
                redirect(action: "auditView",params: params)
                return
            }

        }
        nzZcinfoReAuInstance.auth_Remark=params.auth_Remark

        if(params?.pass=='Y'){
            nzZcinfoReAuInstance.auth_status=1
        }else if(params?.pass=='N'){
            nzZcinfoReAuInstance.auth_status=2
        }

        nzZcinfoReAuInstance.authId =User.findById(loginUser?.id).userName
        nzZcinfoReAuInstance.setAuthTime(DateUtil.getCurrentTime('yyyy-MM-dd'))

        if (nzZcinfoReAuInstance.save(flush: true)){
            //最终审核通过，对原合格证的处理
            if(params?.pass=='Y'){
                if(nzZcinfoReAuInstance.change_Field=='def'){//正常更换，不换车辆编号时,只对原合格证更新
                    def newNzZcinfo=NZInfo.findByVeh_Clbh(nzZcinfoReAuInstance.veh_Clbh)   //找到更换前的合格证，对其进行更新
                    if(nzZcinfoReAuInstance?.veh_Cx_R!=nzZcinfoReAuInstance?.veh_Cx){
                        newNzZcinfo?.veh_Cx=nzZcinfoReAuInstance?.veh_Cx_R
                    }
                    if(nzZcinfoReAuInstance?.veh_Ccrq_R!=nzZcinfoReAuInstance?.veh_Ccrq){
                        newNzZcinfo?.veh_Ccrq=nzZcinfoReAuInstance?.veh_Ccrq_R
                    }
                    if(nzZcinfoReAuInstance?.veh_Fdjh_R!=nzZcinfoReAuInstance?.veh_Fdjh){
                        newNzZcinfo?.veh_Fdjh=nzZcinfoReAuInstance?.veh_Fdjh_R
                    }
                    newNzZcinfo?.veh_Hgzbh=''
                    if(!newNzZcinfo.save(flush: true)){
                        msg='新合格证保存失败'
                    }

                }else if(nzZcinfoReAuInstance.change_Field=='veh_Clbh'){//更换合格证编号，需要对更换前的合格证删除，新建更换后的合格证编号
                     def oldNzZcinfo=NZInfo.findByVeh_Clbh(nzZcinfoReAuInstance?.veh_Clbh)
                    if(oldNzZcinfo){
                      def aaaa=oldNzZcinfo.delete(flush:true)
                        if (!oldNzZcinfo.delete(flush:true)){ //删除旧合格证，保存新合格证 ,.delete删除成功返回null，不成功
                           def newNzZcinfo=new NZInfo()
                            newNzZcinfo.veh_Clbh=nzZcinfoReAuInstance?.veh_Clbh_R
                            newNzZcinfo.veh_Dph=nzZcinfoReAuInstance?.veh_Dph_R
                            newNzZcinfo.veh_Ccrq=nzZcinfoReAuInstance?.veh_Ccrq_R
                            newNzZcinfo.veh_Fdjh=nzZcinfoReAuInstance?.veh_Fdjh_R
                            newNzZcinfo.veh_Cx=nzZcinfoReAuInstance.veh_Cx_R
                            newNzZcinfo.veh_workshopno=nzZcinfoReAuInstance?.veh_workshopno
                            newNzZcinfo.veh_Jcy=nzZcinfoReAuInstance?.veh_Jcy
                            newNzZcinfo.veh_Zxbz=nzZcinfoReAuInstance?.veh_Zxbz
                            newNzZcinfo.SAP_No=nzZcinfoReAuInstance?.SAP_No
                            newNzZcinfo.SAP_status=nzZcinfoReAuInstance?.SAP_status
                            newNzZcinfo.transDate=nzZcinfoReAuInstance?.transDate

                            newNzZcinfo.upload='0'
                            newNzZcinfo.isprint='0'
                            newNzZcinfo.createTime=DateUtil.getCurrentTime()

                            newNzZcinfo.createrId=User.findById(loginUser?.id).userName
                              if(!newNzZcinfo.save(flash:true)){  //.save(flash:true)保存成功返回true，保存失败为false
                                  msg='新合格证保存失败'
                              }

                        }else{
                            msg='旧合格证删除失败'
                        }
                    }
                }
            }
        }else{
            msg='审核失败'
        }
         params.msg=msg
        flash.message = message(code: 'default.operate.message', args: [message(code: 'zcinfore.label', default: 'ZCInfoReplace'), nzZcinfoReAuInstance.veh_Clbh ])
        redirect(action: "authList", params:params)
    }
    /**
     * @Description 保存农装合格证更换信息记录
     * @CreateTime  2014-11-15
     * @return
     */
    def nzZcinfoReplaceSave(){
        def a=params
        def flag=true
        def msg=''
        //旧表
        def nzZciInfoModel
        def cel={
            eq('id',params.zid)
        }
        nzZciInfoModel=NZInfo.createCriteria().list(cel).get(0)
        //更换申请表
        def  nzZcinfoReplace=new NZInfoReplace()
        //新表
        if (!nzZciInfoModel){//没有这个id对应的合格证记录
            flag=false
        }else{       //有这个合格证对应的记录
            //判断合格证更换记录是否已存在
            //判断合格证更换记录是否已存在，只要是营销公司审核状态 veh_coc_status=0 【或者】 area_status=0就不能再次填写变更申请
            def cel1={
                eq('veh_Clbh',nzZciInfoModel.veh_Clbh)
                or{
                    eq("auth_status" ,0)
                    eq("area_status",0) //数据库以前的数据该字段的值为
                }
            }
            def zr=NZInfoReplace .createCriteria().list(cel1)
            if (zr && zr.size()>0){
                return render("编号为${nzZciInfoModel.veh_Clbh}的合格证已存于未审核的更换记录！")
            }

            nzZcinfoReplace .veh_Clbh= nzZciInfoModel.veh_Clbh
            nzZcinfoReplace. veh_Dph= nzZciInfoModel.veh_Dph
            nzZcinfoReplace.veh_Cx= nzZciInfoModel.veh_Cx
            nzZcinfoReplace. veh_Fdjh= nzZciInfoModel.veh_Fdjh
            nzZcinfoReplace. veh_workshopno= nzZciInfoModel.veh_workshopno
            nzZcinfoReplace. veh_Jcy= nzZciInfoModel.veh_Jcy
            nzZcinfoReplace. veh_Ccrq= nzZciInfoModel.veh_Ccrq
            nzZcinfoReplace. veh_Zxbz= nzZciInfoModel.veh_Zxbz
            nzZcinfoReplace. veh_Hgzbh= nzZciInfoModel.veh_Hgzbh
            nzZcinfoReplace. SAP_No= nzZciInfoModel.SAP_No
            nzZcinfoReplace. SAP_status= nzZciInfoModel.SAP_status
            nzZcinfoReplace. transDate= nzZciInfoModel.transDate


            if(params?.change_Field=='veh_Clbh'){  //更换车辆编号,不保存合格证编号和底盘号
                nzZcinfoReplace.change_Field=params?.change_Field
            }else{    //如果是正常更换，要保存更换后的车辆编号和底盘号
                nzZcinfoReplace. veh_Clbh_R = nzZciInfoModel.veh_Clbh
                nzZcinfoReplace. veh_Dph_R =nzZciInfoModel.veh_Dph
            }

            nzZcinfoReplace. veh_Fdjh_R=params?.veh_Fdjh_R
            nzZcinfoReplace. veh_Cx_R = params?.veh_Cx_R
            nzZcinfoReplace. veh_Ccrq_R=params?.veh_Ccrq_R
            nzZcinfoReplace. veh_Jcy_R=nzZciInfoModel.veh_Jcy
            nzZcinfoReplace. veh_Zxbz_R=nzZciInfoModel?.veh_Zxbz
            nzZcinfoReplace.createTime=DateUtil.getCurrentTime()
            User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
            nzZcinfoReplace.createrId=loginUser?.id              //保存申请的id
            def  organList=loginUser?.organs?.id
            nzZcinfoReplace.createrOrgan= organList[0]
            nzZcinfoReplace.createName= loginUser?.userDetail?.realName  //保存申请的的姓名，即经销商姓名
            nzZcinfoReplace.remark=params.remark
            nzZcinfoReplace.salesManName=params.salesManName
            nzZcinfoReplace.salesManPhone=params.salesManPhone

            if (nzZcinfoReplace.save(flush: true)){
                msg='提交成功'
            }else{
                msg='提交失败'
            }
        }
        if(flag==false){
            msg='合格证信息不存在，请重新输入车辆编号或手动填写！'
        }
        render msg
    }
    /**
     * @Description 农装合格证供应商查看自己申请的合格证
     * @CreateTime 2014-11-18 zhuwei
     */
    def nzZcinfoReplaceForSale(){
        def aaa=params
        render(view:'/cn/com/wz/vehcert/nzZcinfoAuth/list',model: [searchType:params.searchType])
    }
    /**
     * @Description 销售区域自己查看自己更换的合格证记录、
     * @CreateTime zhuwei 2014-11-18
     * @return
     */
    def jsonListBySales(){
        def aa=params
        ///当前登录用户信息
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)

        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort='auth_status'
        params.order="asc"

        def results=nzZcinfoReplaceService.areaSelectNzInfoReplace(params,loginUser)

        def lst=[]
        results.each { l->
            def realName= User.get(l.createrId)?.userDetail?.realName
            def m=[:]
            m.id =l.id
            m.createTime=l.createTime
            m.createName=l.createName
            m.veh_Clbh=l.veh_Clbh
            m.veh_Dph =l.veh_Dph
            m.veh_Cx = l.veh_Cx
            m.veh_Fdjh=l.veh_Fdjh
            m.veh_Ccrq=l.veh_Ccrq
            m.veh_Clbh_R =l.veh_Clbh_R
            m.veh_Dph_R=l.veh_Dph_R
            m.veh_Cx_R=l.veh_Cx_R
            m.veh_Ccrq_R=l.veh_Ccrq_R
            m.veh_Fdjh_R=l.veh_Fdjh_R
            m.salesManName=l.salesManName
            m.salesManPhone=l.salesManPhone
            m.area_status=l.area_status

            lst.add(m)
        }
        def result
        try{
            result = [rows: lst, total: results.totalCount,searchType:params?.searchType]
        }catch(Exception e){
            e.printStackTrace()
            e.cause?.printStackTrace()
        }

        render result as JSON
    }
    /**
     * @Description 供应商查看自己申请更换的合格证
     * @CreateTime 2014-11-18 zhuwei
     */
    def salesView(){
        def params=params
        def nzZcinfoReAuInstance =NZInfoReplace.get(params.id)
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        if (!nzZcinfoReAuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'zcinfore.label', default: 'NZINfoReplace'), params.id])
            redirect(action: "nzZcinfoReplaceForSale",params:params)
            return
        }
        render(view:'/cn/com/wz/vehcert/nzZcinfoAuth/viewForSales',model:[nzZcinfoReAuInstance:nzZcinfoReAuInstance])
    }
    /**
     * @Description跳转到合格证跟换打印列表
     * @CreateTime 2014-11-21 zhuwei
     */
    def changPrintList(){
        def aa=params
        render(view: '/cn/com/wz/vehcert/nzZcinfoAuth/changPrintList',model: [searchType:params?.searchType,isPrint:params?.isPrint,msg:params?.msg])
    }
    /**
     *
     */
    def jsonListForChangPrint(){
        def aa=params
        ///当前登录用户信息
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)

        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        params.sort='authTime'
        params.order="desc"

        def results=nzZcinfoReplaceService.areaSelectNzInfoReplace(params,loginUser)

        def lst=[]
        results.each { l->
            def realName= User.get(l.createrId)?.userDetail?.realName
            def m=[:]
            m.id =l.id
            m.createTime=l.createTime
            m.createName=l.createName
            m.veh_Clbh=l.veh_Clbh
            m.veh_Dph =l.veh_Dph
            m.veh_Cx = l.veh_Cx
            m.veh_Fdjh=l.veh_Fdjh
            m.veh_Ccrq=l.veh_Ccrq
            m.veh_Clbh_R =l.veh_Clbh_R
            m.veh_Dph_R=l.veh_Dph_R
            m.veh_Cx_R=l.veh_Cx_R
            m.veh_Ccrq_R=l.veh_Ccrq_R
            m.veh_Fdjh_R=l.veh_Fdjh_R
            m.salesManName=l.salesManName
            m.salesManPhone=l.salesManPhone
            m.auth_status=l.auth_status
            m.veh_Hgzbh_R=l.veh_Hgzbh_R
            m.veh_Hgzbh=l.veh_Hgzbh
            m.isPrint=l.isPrint

            lst.add(m)
        }
        def result = [rows: lst, total: results.totalCount,searchType:params?.searchType]
        render result as JSON
    }
    /**
     * @Description 跳转到更换后打印页面
     * @CreateTime 2014-11-21 zhuwei
     */
    def goToPrint(){
        def aa=params
        def replaceInstace=NZInfoReplace.findById(params?.id)     //更换记录

        def newNzZcinfo=NZInfo.findByVeh_Clbh(replaceInstace?.veh_Clbh_R) //更换后的新合格证编号
        if(newNzZcinfo){
            render (view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/changePrint',model:[newNzZcinfo:newNzZcinfo,replaceNzZcinfoId:replaceInstace?.id])
        }else{
            def msg='更换后的合格证不存在'
            params.msg=msg
            params.searchType=2
            params.isPrint=0
            redirect(action: "changPrintList",params: params)
        }

    }
    /**
     * Description 保存更换后的合格证的合格证编号，
     */
    def printSave(){
        def aa=params
        def isPrintSave
        flash.clear()
        def newNzZcinfo=NZInfo.get(params.newNzZcinfoId) //获取更换后的合格证记录
        newNzZcinfo.veh_Hgzbh=params?.veh_Hgzbh
           if(!newNzZcinfo.save(flush: true)){
               flash.message = '合格证保存失败'
               isPrintSave=false
           }else{
               isPrintSave=true
               flash.message = '合格证保存成功'
               def rePlaceZcinfo=NZInfoReplace.get(params?.replaceNzZcinfoId)
               if(rePlaceZcinfo){
//                   rePlaceZcinfo.isPrint='1' //更新更换记录
                   rePlaceZcinfo.veh_Hgzbh_R=params?.veh_Hgzbh
                   if(!rePlaceZcinfo.save(flush: true)){
                       flash.message = '合格证更换记录保存失败'
                   }
               }
           }
        render(view:'/cn/com/wz/vehcert/zcinfo/nzzcinfo/changePrint',model:[newNzZcinfo:newNzZcinfo,replaceNzZcinfoId:params.replaceNzZcinfoId,isPrintSave:isPrintSave])
    }
}
