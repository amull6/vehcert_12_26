package cn.com.wz.parent.system

import cn.com.wz.parent.system.dictionary.DictionaryValue
import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.Menu;
import cn.com.wz.parent.system.role.Role;

import grails.converters.JSON
/**
 * Menu控制器
 * @author haojia
 *
 */
class MenuController extends BaseController{
	//注入Menu服务类
	def menuService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	/**
	 * 默认执行动作
	 */
    def index() {
        render(view:'/cn/com/wz/parent/system/menu/index',model:params)
    }
	/**
	 * 返回Menu对象列表
	 * @return
	 */
    def list() {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [menuInstanceList: Menu.list(params), menuInstanceTotal: Menu.count()]
		render(view:'/cn/com/wz/parent/system/menu/index')
    }
	/**
	* 返回JSON形式的Menu对象列表
	* @return
	*/
	def jsonList(){
		render list(sort:'showOrder',order:'asc') as JSON
	}
	/**
	 * @Description 获取具有权限的所有菜单的根菜单
	 * @return
	 * @update huxx 2012-09-25 添加了根据用户所属角色读取菜单信息（未完成对菜单的去重和排序）
	 * @update huxx 2012-11-12 完成对多个角色具有相同权限导致菜单重复的问题
     * @update huxx 2013-05-13 添加了应用系统的权限控制
	 */
	def jsonList4Tree(){
		//设置菜单排序信息
		params.sort='showOrder'
		params.order='asc'
		//获取当前用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)

        params.appCode=session.getAttribute('appCode')
		//获取用户有权限的菜单信息
		def menuList=menuService.getRootMenu(user?.userName,params)
		render(contentType: "text/json") {
		    array {
		       menuList?.each {m->
					def url=""
					if(m.controllerName){
						if(m.actionName){
							url=createLink(controller:m.controllerName,action:m.actionName)
						}else{
							url=createLink(controller:m.controllerName)
						}
					}
					if(m.urlPms){
						url+="?"+m.urlPms
					}
					def textLable = isEnglish()?m.textE:m.textC
					if(m.parent){
						menu(id: m.id,pid:m.parent.id,text:textLable,href:url,classes:m.classes?:'',isExpand:0)
					}else{
						menu(id: m.id,pid:null,text:textLable,href:url,classes:m.classes?:'',expanded:true,isExpand:1)
					}
		        }
		    }
		}
	}
	
	/**
	 * @Description 根据权限获取子菜单
	 * @return
	 * @update huxx 2012-11-27 加入了根据权限获取子菜单
	 * @update huxx 2013-02-26 添加了url参数
	 */
	def jsonSubMenu(){
		//设置菜单排序信息
		params.sort='showOrder'
		params.order='asc'
		//获取当前用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)

        params.appCode=session.getAttribute("appCode")
		def results = menuService.getSubMenuByAuth(user.userName,params)
		render(contentType: "text/json") {
			array {
				for (m in results) {
					def url=""
					if(m.controllerName){
						if(m.actionName){
							url=createLink(controller:m.controllerName,action:m.actionName)
						}else{
							url=createLink(controller:m.controllerName)
						}
					}
					if(m.urlPms){
						url+="?"+m.urlPms
					}
					def textLable = isEnglish()?m.textE:m.textC
					if(m.parent){
						menu(id: m.id,pid:m.parent?.id,text:textLable,href:url,classes:m.classes?:'',isExpand:0)
					}else{
						menu(id: m.id,pid:m.parent?.id,text:textLable,href:url,classes:m.classes?:'',expanded:true,isExpand:1)
					}
				}
			}
		}
	}
	/**
	 * 构建完全菜单树
	 * @return
	 */
	def jsonAllList(){
		def results = Menu.list(sort:'showOrder',order:'asc')
		render(contentType: "text/json") {
			array {
				for (m in results) {
					def textLable = isEnglish()?m.textE:m.textC
					if(m.parent){
						menu(id: m.id,pid:m.parent?.id,text:textLable,classes:m.classes?:'',isExpand:0)
					}else{
						menu(id: m.id,pid:m.parent?.id,text:textLable,classes:m.classes?:'',expanded:true,isExpand:1)
					}
				}
			}
		}
	}
	/**
	 *@Description 获取用户所有具有权限的菜单，用户生成菜单树 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2013-3-2 上午11:41:42
	 */
	def getAllMenusByAuth(){
		//设置菜单排序信息
		params.sort='showOrder'
		params.order='asc'
		
		//获取当前用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		if(!user){
			redirect(controller:'base',action:'login')
		}

        params.appCode=session.getAttribute('appCode')
		//获取用户具有权限的菜单
		def menus=menuService.getMenusByUserName(user.userName, params)
		//将菜单中的所有节点的父节点加入
		menus=menuService.checkParentExist(menus)
		menus=menuService.sort(menus)
		render(contentType: "text/json") {
			array {
				for (m in menus) {
					def url=""
					if(m.controllerName){
						if(m.actionName){
							url=createLink(controller:m.controllerName,action:m.actionName)
						}else{
							url=createLink(controller:m.controllerName)
						}
					}
					if(m.urlPms){
						url+="?"+m.urlPms
					}
					def textLable = isEnglish()?m.textE:m.textC
					if(m.parent){
						menu(id: m.id,pid:m.parent?.id,text:textLable,href:url,classes:m.classes?:'',isExpand:0)
					}else{
						menu(id: m.id,pid:m.parent?.id,text:textLable,href:url,classes:m.classes?:'',expanded:true,isExpand:1)
					}
				}
			}
		}
	}
	
	/**
	* 转向创建Menu对象页面
	* @return
	*/
    def create() {
        render(view:'/cn/com/wz/parent/system/menu/create',model:[menuInstance: new Menu(params)])
    }
	
	def jsonCreate(){
		def menuInstance = new Menu(params)
		render(template:"/cn/com/wz/parent/system/menu/create", model:[menuInstance: menuInstance])
	}
	/**
	* 保存Menu对象
	* @return
	*/
    def save() {
        def menuInstance = new Menu(params)
        if (!menuInstance.save(flush: true)) {
            render(view: "/cn/com/wz/parent/system/menu/create", model: [menuInstance: menuInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'menu.label', default: 'Menu'), menuInstance.id])
        redirect(action: "show", id: menuInstance.id)
    }
	/**
	* 保存Menu对象,返回JOSN
	* @return
	*/
	def jsonSave(){
		def menuInstance = new Menu(params)
		if (!menuInstance.save(flush: true)) {
			render(template:"/cn/com/wz/parent/system/menu/create", model:[menuInstance: menuInstance])
		}else{
			flash.message = message(code: 'default.created.message', args: [message(code: 'menu.label', default: 'Menu'), menuInstance.id])
			render(template:"/cn/com/wz/parent/system/menu/show", model:[menuInstance: menuInstance])
		}
	}
	/**
	 * Menu对象信息信息显示界面
	 * @return
	 */
    def show() {
        def menuInstance = Menu.get(params.id)
        if (!menuInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
            redirect(action: "list")
            return
        }
		render(view:"/cn/com/wz/parent/system/menu/show", model:[menuInstance: menuInstance])
    }
	def jsonShow(){
		def menuInstance = Menu.get(params.id)
		if (!menuInstance) {
			menuInstance.errors = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
		}
		render(template:"/cn/com/wz/parent/system/menu/show", model:[menuInstance: menuInstance])
	}
	/**
	* 转向Menu对象编辑界面
	* @return
	*/
    def edit() {
        def menuInstance = Menu.get(params.id)
        if (!menuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
            redirect(action: "list")
            return
        }
        render(view:"/cn/com/wz/parent/system/menu/edit", model:[menuInstance: menuInstance])
    }
	def jsonEdit(){
		def menuInstance = Menu.get(params.id)
		if (!menuInstance) {
			render message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
		}else{
			render(template:"/cn/com/wz/parent/system/menu/edit", model:[menuInstance: menuInstance])
		}
	}
	/**
	* 更新Menu对象
	* @return
	*/
    def update() {
		
        def menuInstance = Menu.get(params.id)
        if (!menuInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (menuInstance.version > version) {
                menuInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'menu.label', default: 'Menu')] as Object[],
                          "Another user has updated this Menu while you were editing")
                render(view: "edit", model: [menuInstance: menuInstance])
                return
            }
        }

		def ddd=params
        menuInstance.properties = params

        if (!menuInstance.save(flush: true)) {
            render(view: "edit", model: [menuInstance: menuInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'menu.label', default: 'Menu'), menuInstance.id])
        redirect(action: "show", id: menuInstance.id)
    }
	def jsonUpdate(){
		def menuInstance = Menu.get(params.id)
		if (!menuInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
			redirect(action: "list")
		}

		if (params.version) {
			def version = params.version.toLong()
			if (menuInstance.version > version) {
				menuInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'menu.label', default: 'Menu')] as Object[],
						  "Another user has updated this Menu while you were editing")
				render(view: "edit", model: [menuInstance: menuInstance])
			}
		}

		menuInstance.properties = params

		if (!menuInstance.save(flush: true)) {
			render(template: "/cn/com/wz/parent/system/menu/edit", model: [menuInstance: menuInstance])
		}else{
			flash.message = message(code: 'default.updated.message', args: [message(code: 'menu.label', default: 'Menu'), menuInstance.id])
			render(template:"/cn/com/wz/parent/system/menu/show", model:[menuInstance: menuInstance])
		}
	}
	/**
	* 删除Menu对象
	* @return
	*/
    def delete() {
        def menuInstance = Menu.get(params.id)
        if (!menuInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
            redirect(action: "list")
            return
        }

        try {
            menuInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	def jsonDelete(){
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		def menuInstance = Menu.get(params.id)
		if (!menuInstance) {
			flag = false
		}
		try {
			menuInstance.delete(flush: true)
		}
		catch (DataIntegrityViolationException e) {
			flag = false
		}
		if(!flag){
			msg = message(code: 'default.not.simple.deleted.message')
		}
		render msg
	}
}
