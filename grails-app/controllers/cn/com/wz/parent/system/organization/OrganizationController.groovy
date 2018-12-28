package cn.com.wz.parent.system.organization
import cn.com.wz.parent.base.BaseController;
import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.base.BaseController

/**
 *@Description 组织结构管理控制器 
 *@Auther huxx
 *@createTime 2012-9-18 上午10:04:04
 */
class OrganizationController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def logService
    def index() {
        render(view:'/cn/com/wz/parent/system/organization/index',model:params)
    }

	/**
	 *@Description 生成左侧的字典项树
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-17 下午4:45:17
	 */
	def jsonListTree(){
		params.sort='showOrder'
		params.order="asc"
		def result = Organization.list(params);
		
		render(contentType: "text/json") {
			array {
				for (m in result) {
					//中英文判断
					def textLabel=isEnglish()?m.organNameE:m.organNameC
                    if(m.parent){
                        item(id:m.id,pid:m.parentId,text:textLabel,organCode:m.organCode,expanded:false,isExpand:0)
                    }else{
                        item(id:m.id,pid:m.parentId,text:textLabel,organCode:m.organCode,expanded:true,isExpand:1)
                    }

				}
			}
		}
	}
	
	
    def list() {
       params.max =params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		params.sort='showOrder'
		params.order="asc"
        render (view:'/cn/com/wz/parent/system/organization/list',model: [organizationInstanceList: Organization.list(params), organizationInstanceTotal: Organization.count()])
    }

	/**
	 *@Description 跳转到添加页面 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-18 下午3:02:40
	 */
    def create() {
       render (view:'/cn/com/wz/parent/system/organization/create',model:[organizationInstance: new Organization(params)])
    }

	/**
	 *@Description 保存添加信息 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-18 下午3:04:35
	 */
    def save() {
        def organizationInstance = new Organization(params)
        if (!organizationInstance.save(flush: true)) {
            render(view: "/cn/com/wz/parent/system/organization/create", model: [organizationInstance: organizationInstance])
            return
        }
		saveNotes(organizationInstance.organNameC,1)
		flash.message = message(code: 'default.created.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])
        redirect(action: "show", id: organizationInstance.id)
    }

	/**
	 *@Description 详细信息展示页面
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-18 下午3:05:07
	 */
    def show() {
        def organizationInstance = Organization.get(params.id)
        if (!organizationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])
            redirect(action: "list")
            return
        }

       render (view:'/cn/com/wz/parent/system/organization/show',model: [organizationInstance: organizationInstance])
    }
	
	/**
	 *@Description ajax显示选中节点的详细信息
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-15 下午5:15:22
	 */
	def jsonShow(){
		def organizationInstance = Organization.get(params.id)
		if (!organizationInstance) {
			organizationInstance.errors = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])
		}
		render(template:"/cn/com/wz/parent/system/organization/show", model:[organizationInstance: organizationInstance])
	}


	/**
	 *@Description 跳转到编辑页面 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-18 下午3:17:57
	 */
    def edit() {
        def organizationInstance = Organization.get(params.id)
        if (!organizationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/parent/system/organization/edit',model: [organizationInstance: organizationInstance])
    }

	/**
	 *@Description 保存修改信息 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-18 下午3:18:20
	 */
    def update() {
        def organizationInstance = Organization.get(params.id)
        if (!organizationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'organization.label', default: 'Organization'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (organizationInstance.version > version) {
                organizationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'organization.label', default: 'Organization')] as Object[],
                          "Another user has updated this Organization while you were editing")
                render(view: "edit", model: [organizationInstance: organizationInstance])
                return
            }
        }

        organizationInstance.properties = params

        if (!organizationInstance.save(flush: true)) {
            render(view: "/cn/com/wz/parent/system/organization/edit", model: [organizationInstance: organizationInstance])
            return
        }
		saveNotes(organizationInstance.organNameC,2)
		flash.message = message(code: 'default.updated.message', args: [message(code: 'organization.label', default: 'Organization'), organizationInstance.id])
        redirect(action: "show", id: organizationInstance.id)
    }

    /**
	 * @Description ajax删除数据
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-15 下午5:24:48
	 */
	def jsonDelete(){
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		def organizationInstance = Organization.get(params.id)
		def organ=organizationInstance.organNameC
		if (!organizationInstance) {
			flag = false
		}
		try {
			organizationInstance.delete(flush: true)
		}
		catch (DataIntegrityViolationException e) {
			flag = false
		}
		if(!flag){
			msg = message(code: 'default.not.simple.deleted.message')
		}
		saveNotes(organ,0)
		render msg
	}
	/**
	 *@Description 获取用户操作，写入操作日志
	 *@param organ 组织的中文名；type 操作类型  0为删除1为添加2为更新
	 *@return
	 *@Auther liucj
	 *@createTime 2013-3-28 上午11:40
	 */
	def saveNotes(organ,type){
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		//组装操作内容
		def operat=message(code:'organization.dele.lable',args:["$organ"])
		if(type==1){
			operat=message(code:'organization.save.lable',args:["$organ"])
		}
		if(type==2){
			operat=message(code:'organization.update.lable',args:["$organ"])
		}
		//写入日志
		logService.insertLog(user.id, "organization", operat,'businessLog')
	}
}
