package cn.com.wz.vehcert.print

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.coc.CocCarStorage
import cn.com.wz.vehcert.workshop.Workshop
import cn.com.wz.vehcert.zcinfo.PrintSet
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils

/**
 *@Description 打印机设置
 *@Auther mike
 *@createTime 2013-08-06
 */
class PrintSetController extends BaseController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    /**
     *@Description 跳转到详情页面
     *@Auther mike
     *@createTime 2013-08-06
     */
    def show() {
        def printSet = PrintSet.get(params.id)
        if (!printSet){
            printSet = new PrintSet()
        }
        render (view:'/cn/com/wz/vehcert/printset/show', model: [printSetInstance: printSet])
    }

    /**
     *@Description 进入编辑页面
     *@Auther mike
     *@createTime 2013-08-06
     */
    def edit() {
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def printSet = PrintSet.findByUserid(loginUser.id)
        if (!printSet){
            printSet = new PrintSet()
            printSet.userid=loginUser.id
            printSet.save(flush:true)
        }
        render (view:'/cn/com/wz/vehcert/printset/edit',model:  [printSetInstance: printSet])
    }
    /**
     *@Description 进入编辑页面  车间
     *@Auther mike
     *@createTime 2013-08-06
     */
    def editWorkShop() {
        def printSetList = PrintSet.findAll()
        def   printSet=null;
        if (!printSetList||printSetList.size()==0){
            printSet = new PrintSet()
            printSet.userid=loginUser.id
        }else{
            printSet=printSetList.get(0)
        }
        render (view:'/cn/com/wz/vehcert/printset/edit',model:  [printSetInstance: printSet])
    }
    /**
     *@Description 保存更新信息
     *@Auther mike
     *@createTime 2013-08-06
     */
    def update() {
        //判定要修改的记录是否存在
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def printSet
        if (params.id){
            printSet= PrintSet.get(params.id)
        }else{
            printSet=PrintSet.findByUserid(loginUser.id)
        }

        if (!printSet) {
            printSet=new PrintSet(params);
            flash.message = '设置失败！'
            render(view: "/cn/com/wz/vehcert/printset/edit", model: [printSetInstance: printSet])
            return
        }
        printSet.properties = params
        if (!printSet.save(flush: true)) {
            flash.message = '设置失败！'
            render(view: "/cn/com/wz/vehcert/printset/edit", model: [printSetInstance: printSet])
            return
        }else{
            flash.message = '设置成功！'
            render(view:'/cn/com/wz/vehcert/printset/show',model:[printSetInstance: printSet])
        }
    }
}
