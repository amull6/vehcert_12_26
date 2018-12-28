
package cn.com.wz.parent.system.role

import cn.com.wz.parent.appmanage.AppRole
import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.dictionary.DictionaryValue

class RoleController extends BaseController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def logService
	def index() {
		render(view:'/cn/com/wz/parent/system/role/index')
	}

	def list() {
		render(view:'/cn/com/wz/parent/system/role/index')
	}
	
	def jsonListTree(){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def result = Role.list(params);
		
		render(contentType: "text/json"){
		array {
			for (m in result) {
				//中英文判断
				def textLabel=isEnglish()?m.roleNameE:m.roleNameC
				item(id:m.id,pid:m.parentId,text:textLabel)
			}}
	}
	}
	
	def jsonList(){
		params.max = params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') :0
		def result = [rows: Role.list(params), total: Role.count()]
		render result as JSON
	}
	
	/**
	* 根据条件查询数据
	* @return
	*/
   def search(){
	   params.max = params.limit ? params.int('limit') : 10
	   params.offset = params.start ? params.int('start') :0
	   def cel = {
		   if(params.roleNameC){
			   like("roleNameC", "%${params.roleNameC}%")
		   }
		   if(params.roleCode){
			   like("roleCode", "%${params.roleCode}%")
		   }
		   if(params.roleType?.id){
			  roleType{eq('id',params.roleType.id)}
		   }
	   }
	   def results = Role.createCriteria().list(params,cel)
	   def result = [rows: results, total: results.totalCount]
	   render result as JSON
   }

	def create() {
        def checkIds=grailsApplication.config.app.appCode
	  render (view:'/cn/com/wz/parent/system/role/create' ,model: [roleInstance: new Role(params),checkIds: checkIds])
	}

	def save() {

		//根据角色类型id从字典中获取角色类型
		def roleInstance=new Role(params)
		def roleType=DictionaryValue.get(params.roleType.id)
		roleInstance.setRoleTypeName(roleType?.toString())
		
		if (!roleInstance.save(flush: true)) {
			render(view: "/cn/com/wz/parent/system/role/create", model: [roleInstance: roleInstance,checkIds: params.checkIds])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.roleNameC])
		
		roleInstance.setDateCreated(new Date())
		roleInstance.setCreatedBy(getCurrentUser()?.getId())
		saveNotes(roleInstance.roleNameC,1)
        if (params.checkIds!="noChoose"&&params.checkIds!=null&&params.checkIds!='') {
            def codes=params.checkIds?.split(":_:")
            codes?.each{d->
                def appRole=new AppRole()
                appRole.roleId=roleInstance.id
                appRole.appId=DictionaryValue.findByCode(d).id
                appRole.save(flush: true)
            }
        }
		redirect(action: "show", id: roleInstance.id)
	}

	def show() {
		def roleInstance = Role.get(params.id)
		if (!roleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'role'), params.id])
			redirect(action: "list")
			return
		}

        def theSystem=''
        def apps=AppRole.findAllByRoleId(roleInstance.id)
        apps?.each{
            def sysName=DictionaryValue.get(it.appId).dicValueNameC
            theSystem+=sysName+'  '
        }

		render (view:"/cn/com/wz/parent/system/role/show",model: [role: roleInstance,theSystem:theSystem])
	}

	def edit() {
		def roleInstance = Role.get(params.id)
		if (!roleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'role'), params.id])
			redirect(action: "list")
			return
		}

        def checkIds=''
        def apps=AppRole.findAllByRoleId(roleInstance.id)
        apps?.each{
            def code=DictionaryValue.get(it.appId).code
            checkIds+=code+':_:'
        }
		
	   render (view:'/cn/com/wz/parent/system/role/edit',model:[roleInstance: roleInstance,checkIds:checkIds])

	}

	def update() {
		def roleInstance = Role.get(params.id)
		
		//根据角色类型id从字典中获取角色类型
		def roleType=DictionaryValue.get(params.roleType.id)
		roleInstance.setRoleTypeName(roleType?.toString())
		if (!roleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'role'), params.id])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (roleInstance.version > version) {
				roleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'role.label', default: 'Role')] as Object[],
						  "Another user has updated this Role while you were editing")
				render(view:"/cn/com/wz/parent/system/role/edit", model:[roleInstance: roleInstance])
				  return
			}
		}

		roleInstance.properties = params

		if (!roleInstance.save(flush: true)) {
			render(view:"/cn/com/wz/parent/system/role/edit", model:[roleInstance: roleInstance,checkIds:params.checkIds])
			return
		}
		roleInstance.setUpdateTime(new Date())
		roleInstance.setLastUpdateTime(new Date())
		roleInstance.setUpdatedBy(getCurrentUser()?.getId())

        def appRoles=AppRole.findAllByRoleId(roleInstance.id)
        appRoles?.each{
            it.delete(flush: true)
        }

        if (params.checkIds!="noChoose"&&params.checkIds!=null&&params.checkIds!='') {
            def codes=params.checkIds?.split(":_:")
            codes?.each{d->
                def appRole=new AppRole()
                appRole.roleId=roleInstance.id
                appRole.appId=DictionaryValue.findByCode(d).id
                appRole.save(flush: true)
            }
        }
		flash.message = message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.roleNameC])
		saveNotes(roleInstance.roleNameC,2)
		redirect(action:"show", id: roleInstance.id)
	}

	/**
	 * @Description 使用ajax删除员工记录
	 *@param params
	 *@return 操作信息
	 *@Auther huxx
	 *@createTime 2012-9-14 上午9:46:18
	 */
	def jsonDelete(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		def roleNames=''
		idsArray.each {id->
			def role =Role.get(id)
			roleNames+=role.roleNameC+'，'
			if (!role) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				return
			}
			try {
				role.delete(flush: true)
                def appRoles=AppRole.findAllByRoleId(id)
                appRoles?.each{
                    it.delete(flush: true)
                }
				//msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.deleted.message')
			}
		}
		if(flag){
			saveNotes(roleNames,0)
		}
		render msg
	}
	/**
	 *@Description 获取用户操作，写入操作日志
	 *@param role 角色的中文名；type 操作类型  0为删除1为添加2为更新
	 *@return
	 *@Auther liucj
	 *@createTime 2013-3-28 上午11:40
	 */
	def saveNotes(role,type){
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		//组装操作内容
		def operat=message(code:'role.dele.lable',args:["$role"])
		if(type==1){
			operat=message(code:'role.save.lable',args:["$role"])
		}
		if(type==2){
			operat=message(code:'role.update.lable',args:["$role"])
		}
		//写入日志
		logService.insertLog(user.id, "role", operat,'businessLog')
	}
}

