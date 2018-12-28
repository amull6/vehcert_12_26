package cn.com.wz.parent.system.dictionary

import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.Menu
import cn.com.wz.parent.system.role.Role
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

/**
 *@Description  字典值管理控制器
 *@Auther huxx
 *@createTime 2012-9-15 上午8:45:39
 */
class DictionaryValueController  extends BaseController{

    def dictionaryService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		render(view:'/cn/com/wz/parent/system/dictionary/dictionaryValue/index',model:params)
    }

    def list() {
//        params.max = Math.min(params.max ? params.int('max') : 10, 100)
//        [dictionaryValueInstanceList: DictionaryValue.list(params), dictionaryValueInstanceTotal: DictionaryValue.count()]
		render(view:'/cn/com/wz/parent/system/dictionary/dictionaryValue/index')
	}

    def getDicValueByType(){
            def result=[:]
            if(params.dicType){
                    result.rows=DictionaryValue.findAllByDictionaryType(DictionaryType.get(params.dicType),[max:params.max,offset:params.offset,sort:'ordernum',order:'asc'])
                    result.total=DictionaryValue.countByDictionaryType(DictionaryType.get(params.dicType))
            }else{
                    result.rows=[]
                    result.total=0
            }
            render result as JSON
    }
    /**
     * 根据条件查询数据
     * @return
     */
    def search(){
        params.max = params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
        def cel = {   
            if(params.dicValueNameC){   
                like("dicValueNameC", "%${params.dicValueNameC}%")   
            }
			if(params.dicValueNameE){   
                like("dicValueNameE", "%${params.dicValueNameE}%")   
            }
			if(params.dicTypeId){
				dictionaryType{
					eq("id",params.dicTypeId)
				}
			}
        }
		//设置排序参数
		params.sort='ordernum'
		params.order='asc'
        def results = DictionaryValue.createCriteria().list(params,cel)   
        def result = [rows: results, total: results.totalCount]
		render result as JSON 
    }
	
    def create() {
		render(view:'/cn/com/wz/parent/system/dictionary/dictionaryValue/create',model:[dictionaryValueInstance: new DictionaryValue(params)])
    }

    def save() {
        def dictionaryValueInstance = new DictionaryValue(params)
        if (!dictionaryValueInstance.save(flush: true)) {
            render(view: "/cn/com/wz/parent/system/dictionary/dictionaryValue/create", model: [dictionaryValueInstance: dictionaryValueInstance])
            return
        }

        //判断字典值是否需要保存到application中
        dictionaryService.initDicValueToApplication(servletContext,dictionaryValueInstance)

		flash.message = message(code: 'default.created.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), dictionaryValueInstance.id])
        redirect(action: "show", id: dictionaryValueInstance.id)
    }

    def show() {
        def dictionaryValueInstance = DictionaryValue.get(params.id)
        if (!dictionaryValueInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), params.id])
            redirect(action: "list")
            return
        }
		render(view:'/cn/com/wz/parent/system/dictionary/dictionaryValue/show',model:[dictionaryValueInstance: dictionaryValueInstance])
    }

    def edit() {
        def dictionaryValueInstance = DictionaryValue.get(params.id)
        if (!dictionaryValueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), params.id])
            redirect(action: "list")
            return
        }
		render(view:'/cn/com/wz/parent/system/dictionary/dictionaryValue/edit',model:[dictionaryValueInstance: dictionaryValueInstance])
    }

    def update() {
        def dictionaryValueInstance = DictionaryValue.get(params.id)
        if (!dictionaryValueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (dictionaryValueInstance.version > version) {
                dictionaryValueInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dictionaryValue.label', default: 'DictionaryValue')] as Object[],
                          "Another user has updated this DictionaryValue while you were editing")
                render(view: "edit", model: [dictionaryValueInstance: dictionaryValueInstance])
                return
            }
        }

        dictionaryValueInstance.properties = params

        if (!dictionaryValueInstance.save(flush: true)) {
            render(view: "edit", model: [dictionaryValueInstance: dictionaryValueInstance])
            return
        }
		//判断字典值是否需要保存到application中
        dictionaryService.initDicValueToApplication(servletContext,dictionaryValueInstance)

		flash.message = message(code: 'default.updated.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), dictionaryValueInstance.id])
        redirect(action: "show", id: dictionaryValueInstance.id)
    }

    def delete() {
        def dictionaryValueInstance = DictionaryValue.get(params.id)
        if (!dictionaryValueInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), params.id])
            redirect(action: "list")
            return
        }

        try {
            dictionaryValueInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	def jsonDelete(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		idsArray.each {
			def dictionaryValueInstance = DictionaryValue.get(it)
			if (!dictionaryValueInstance) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				return
			}
			try {
				dictionaryValueInstance.delete(flush: true)
				//msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.deleted.message')
			}
		}
		render msg
	}


    /**
     *@Description 获取指定类型下的字典值信息，并返回树结构数据
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-9-23 上午10:47:12
     */
    def selectDicValueByTypeCode(){
        render(contentType: "text/json") {
//            params.sort='ordernum'
//            params.order="asc"

            array {
                //获取角色类型
                def typeList=dictionaryService.getSubDictionaryTypeCode(params.code)
                for (t in typeList) {
                    //处理中英文转换
                    def textLable=isEnglish()?t.typeNameE:t.typeNameC
                    item(id:t.id,pid:null,text:textLable,isDir:true)

                    //获取角色类型下的角色信息
                    def dicValueList=DictionaryValue.createCriteria().list(params,{dictionaryType{eq('id',t.id)}})
                    for(r in dicValueList){
                        //处理中英文转换
                        textLable=isEnglish()?r.dicValueNameE:r.dicValueNameC
                        item(id:r.id,pid:t.id,text:textLable,isDir:false)
                    }
                }
            }
        }
    }


}
