package cn.com.wz.parent.system.dictionary

import grails.converters.JSON

import org.apache.commons.lang.time.DateUtils;
import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.DateUtil;
import cn.com.wz.parent.base.BaseController;
import cn.com.wz.parent.system.dictionary.DictionaryType;
/**
 *@Description 字典类型控制器
 *@Auther huxx
 *@createTime 2012-9-15 上午8:46:23
 */
class DictionaryTypeController  extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
         render(view:'/cn/com/wz/parent/system/dictionary/dictionaryType/index')
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
		def result = DictionaryType.list(params);
		
		
		render(contentType: "text/json") {
			array {
				for (m in result) {
					//处理中英文转换
					def textLable=isEnglish()?m.typeNameE:m.typeNameC
					item(id:m.id,pid:m.parentId,text:textLable)
				}
			}
		}
	}
	
	
	/**
	 *@Description 跳转到添加页面
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-17 下午4:45:53
	 */
    def create() {
		render (view:'/cn/com/wz/parent/system/dictionary/dictionaryType/create',model:[dictionaryType: new DictionaryType(params)])
    }

	/**
	 *@Description 保存添加信息 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-17 下午4:46:12
	 */
    def save() {
		def dictionaryType = new DictionaryType(params)
		
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		//添加创建时间和创建人
		dictionaryType.setCreaterId(user.getId())
		dictionaryType.setCreateTime(new Date());
		
        if (!dictionaryType.save(flush: true)) {
            render(view: "/cn/com/wz/parent/system/dictionary/dictionaryType/create", model: [dictionaryType: dictionaryType])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'dictionaryType.label', default: 'DictionaryType'), dictionaryType.id])
        redirect(action: "show", id: dictionaryType.id)
    }
	
	
	def show() {
		def dictionaryType = DictionaryType.get(params.id)
		if (!dictionaryType) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryType.label', default: 'DictionaryType'), params.id])
			redirect(action: "list")
			return
		}

	   render (view:'/cn/com/wz/parent/system/dictionary/dictionaryType/show',model:[dictionaryType: dictionaryType])
	}
	/**
	 *@Description ajax显示选中节点的详细信息
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-15 下午5:15:22
	 */
	def jsonShow(){
		def dictionaryType = DictionaryType.get(params.id)
		
		if (!dictionaryType) {
			dictionaryType.errors = message(code: 'default.not.found.message', args: [message(code: 'dictionaryType.label', default: 'DictionaryType'), params.id])
		}
		render(template:"/cn/com/wz/parent/system/dictionary/dictionaryType/show", model:[dictionaryType: dictionaryType])
	}

	/**
	 *@Description 跳转到修改页面
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-17 下午4:47:47
	 */
    def edit() {
        def dictionaryType = DictionaryType.get(params.id)
        if (!dictionaryType) {
			dictionaryType=new DictionaryType();
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryType.label', default: 'DictionaryType'), params.id])
			render (view:'/cn/com/wz/parent/system/dictionary/dictionaryType/edit',model:[dictionaryType: dictionaryType])
            return
        }

        render (view:'/cn/com/wz/parent/system/dictionary/dictionaryType/edit',model:[dictionaryType: dictionaryType])
    }

	/**
	 *@Description 保存修改信息
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-17 下午4:48:13
	 */
    def update() {
		def dictionaryType = DictionaryType.get(params.id)
		
		//获取当前登录用户
		def employee=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def currentDate=DateUtil.getCurrentTime();
		
		//添加最后修改时间和修改人
		dictionaryType.setLastUpdaterId(employee.getId())
		dictionaryType.setLastUpdateTime(currentDate)
				
        if (!dictionaryType) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryType.label', default: 'DictionaryType'), params.id])
            render(view: "/cn/com/wz/parent/system/dictionary/dictionaryType/edit", model: [dictionaryType: dictionaryType])
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (dictionaryType.version > version) {
                dictionaryType.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dictionaryType.label', default: 'DictionaryType')] as Object[],
                          "Another user has updated this DictionaryType while you were editing")
                render(view: "/cn/com/wz/parent/system/dictionary/dictionaryType/edit", model: [dictionaryType: dictionaryType])
                return
            }
        }

        dictionaryType.properties = params

        if (!dictionaryType.save(flush: true)) {
            render(view: "edit", model: [dictionaryType: dictionaryType])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'dictionaryType.label', default: 'DictionaryType'), dictionaryType.id])
        redirect(action: "show", id: dictionaryType.id)
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
		def dictionaryType = DictionaryType.get(params.id)
		if (!dictionaryType) {
			flag = false
		}
		try {
			dictionaryType.delete(flush: true)
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
