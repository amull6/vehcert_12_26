package cn.com.wz.parent.cms

import cn.com.wz.parent.base.BaseController
import org.springframework.dao.DataIntegrityViolationException
import cn.com.wz.parent.system.dictionary.DictionaryType
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.cms.ArticleCategory
import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.system.role.Role
/**
 *@Description 网站文本管理
 *@Auther liucj
 *@createTime 2012-9-28 上午8:25:04
 */
class ArticleCategoryController extends BaseController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		render(view:'/cn/com/wz/parent/cms/articleCategory/index',model:params)
	}

	
	
	/**
	 *@Description 获取栏目信息（并以showOrder降序排列)
	 *@param categoryType分类栏目类型，
	 *null:取全部的栏目信息；
	 *news 取新闻类的栏目
	 *notes 取通知公告类的栏目
	 *@return
	 *@Auther 
	 *@createTime 2012-9-28
	 *@update huxx 2012-10-14 添加了根据分类获取栏目信息
	 *@update huxx 2012-12-26 修改了没有栏目类型时，信息显示不正确的问题。
	 */
	def jsonListTree(){
		String categoryType=params.categoryType
		params.sort='showOrder'
		params.order='asc'
		def categoryList=[]
		//获取当前用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		
		//对system赋值全部
		if('system'.equals(user?.userName)){
			def categoryCel={
					if(categoryType){
						categoryType{
							eq('code',categoryType)
						}
					}
					[sort:'showOrder',order:'asc']
				}
			categoryList=ArticleCategory.createCriteria().list(categoryCel)
			categoryList.each{category->
				category.publishAuth=1
				category.auditAuth=1
			}
		}else{
				//根据用户获取用户所属的角色信息
				def cel={
					users{
						eq('id',user.id)
					}
				}
				def roleList=Role.createCriteria().list(cel)
				//获取角色id并组成数组
				def roleIds=[]
				roleList.each { role->
					roleIds.add(role.id)
				}
				//根据角色id信息获取其有审核或发布权限的栏目信息
				def authCel={
					inList("roleId",roleIds)
				}
				def authList=ArticleAuth.createCriteria().list(authCel)
				
				def categoryIds=[]
				authList?.each {
					categoryIds.add(it.categoryId)
				}
				if(categoryType){
					def dr=DictionaryValue.findByCode("${categoryType}")
					//获取用户可以查看的栏目信息   条件：categoryType符合    且    id为categoryIds中
					def categoryCel={
						eq('categoryType',dr)
						inList('id',categoryIds)
						order('showOrder','asc')
					}
					categoryList=ArticleCategory.createCriteria().list(categoryCel)
				}else{
					//获取用户可以查看的栏目信息   条件：categoryType符合    且    id为categoryIds中
					def categoryCel={
						inList('id',categoryIds)
						order('showOrder','asc')
					}
					categoryList=ArticleCategory.createCriteria().list(categoryCel)
				}
				
				//设置当前用户对栏目有那些权限
				categoryList?.each {category->
					def publishAuth=0
					def auditAuth=0
					for(int i=0;i<authList?.size();i++){
						def auth=authList.get(i)
						if(category.id==auth.categoryId){
							if(1.equals(auth.publishAuth)){
								publishAuth=1
							}
							if(1.equals(auth.auditAuth)){
								auditAuth=1
							}
							
							if(1.equals(auth.publishAuth)&&1.equals(auth.auditAuth)){
								publishAuth=1
								auditAuth=1
								break
							}
						}
					}
					category.publishAuth=publishAuth
					category.auditAuth=auditAuth
				}
		}
		
		render(contentType: "text/json") {
			array {
				for (m in categoryList) {
					//中英文判断
					def textLabel=isEnglish()?m.categoryNameE:m.categoryNameC
					item(id:m.id,pid:m.parentId,publishAuth:m.publishAuth,auditAuth:m.auditAuth,text:textLabel)
				}
			}
		}
	}
	
	/**
	 *@Description 获取所有栏目分类信息
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2013-3-2 下午1:47:55
	 */
	def jsonAllListTree(){
		params.sort='showOrder'
		params.order='asc'
		def categoryList=[]
		def categoryCel={
				order('showOrder','asc')
		}
		categoryList=ArticleCategory.createCriteria().list(categoryCel)
		
		render(contentType: "text/json") {
			array {
				for (m in categoryList) {
					//中英文判断
					def textLabel=isEnglish()?m.categoryNameE:m.categoryNameC
					item(id:m.id,pid:m.parentId,text:textLabel)
				}
			}
		}
	}
	def list() {
			render(view:'/cn/com/wz/parent/cms/articleCategory/index')
	}


	/**
	 *@Description 跳转到添加页面
	 *@param
	 *@return
	 *@Auther 
	 *@createTime 2012-9-28
	 */
	def create() {
	   render (view:'/cn/com/wz/parent/cms/articleCategory/create',model:[articleCategoryInstance: new ArticleCategory(params)])
	}

	/**
	 *@Description 保存添加信息
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-28
	 */
	def save() {
		//根据栏目类型id从字典中获取栏目类型
		def articleCategoryInstance = new ArticleCategory(params)
		def categoryType=DictionaryValue.get(params.categoryType.id)
		articleCategoryInstance.setCategoryTypeName(categoryType?.toString())
		//定义父类的Id
		def parent=ArticleCategory.get(params.parent.id)
		def parentIds=''
		def parentNames=''
		if(parent){
			if(parent.parentNames){
				parentNames=parent.parentNames+'_'+parent.categoryNameC
			}else{
			    parentNames=parent.categoryNameC
			}
			if(parent.parentIds){
				parentIds=parent.parentIds+'_'+parent.id
			}else{
				parentIds=parent.id
			}
		}
		articleCategoryInstance.setParentIds(parentIds?.toString())
		articleCategoryInstance.setParentNames(parentNames?.toString())
		
		if (!articleCategoryInstance.save(flush: true)) {
			render(view: "/cn/com/wz/parent/cms/articleCategory/create", model: [articleCategoryInstance: articleCategoryInstance])
			return
		}
		articleCategoryInstance.setCreateTime(new Date())
		articleCategoryInstance.setCreaterId(getCurrentUser()?.getId())
		flash.message = message(code: 'default.created.message', args: [message(code: 'articleCategory.label', default: 'ArticleCategory'), articleCategoryInstance.categoryNameC])
		redirect(action: "show", id: articleCategoryInstance.id)
	}
	
	/**
	 *@Description 详细信息展示页面
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-28
	 */
	def show() {
		def articleCategoryInstance = ArticleCategory.get(params.id)
		if (!articleCategoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'articleCategory.label', default: 'ArticleCategory'), params.id])
			redirect(action: "list")
			return
		}

	   render (view:'/cn/com/wz/parent/cms/articleCategory/show',model: [articleCategoryInstance: articleCategoryInstance])
	}
	
	/**
	 *@Description ajax显示选中节点的详细信息
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-28
	 */
	def jsonShow(){
		def articleCategoryInstance = ArticleCategory.get(params.id)
		if (!articleCategoryInstance) {
			articleCategoryInstance.errors = message(code: 'default.not.found.message', args: [message(code: 'articleCategory.label', default: 'ArticleCategory'), params.id])
		}
		render(template:"/cn/com/wz/parent/cms/articleCategory/show", model:[articleCategoryInstance: articleCategoryInstance])
	}


	/**
	 *@Description 跳转到编辑页面
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-28
	 */
	def edit() {
		def articleCategoryInstance = ArticleCategory.get(params.id)
		if (!articleCategoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'articleCategory.label', default: 'ArticleCategory'), params.id])
			redirect(action: "list")
			return
		}

		render (view:'/cn/com/wz/parent/cms/articleCategory/edit',model: [articleCategoryInstance: articleCategoryInstance])
	}

	/**
	 *@Description 保存修改信息
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-28
	 */
	def update() {
		def articleCategoryInstance = ArticleCategory.get(params.id)
		if (!articleCategoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'articleCategory.label', default: 'ArticleCategory'), params.id])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (articleCategoryInstance.version > version) {
				articleCategoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'articleCategory.label', default: 'ArticleCategory')] as Object[],
						  "Another user has updated this ArticleCategory while you were editing")
				render(view: "edit", model: [articleCategoryInstance: articleCategoryInstance])
				return
			}
		}

		articleCategoryInstance.properties = params

		if (!articleCategoryInstance.save(flush: true)) {
			render(view: "/cn/com/wz/parent/cms/articleCategory/edit", model: [articleCategoryInstance: articleCategoryInstance])
			return
		}
		//定义父类的Id
		def parent=ArticleCategory.get(params.parent.id)
		def parentIds=''
		def parentNames=''
		if(parent){
			if(parent.parentNames){
				parentNames=parent.parentNames+'_'+parent.categoryNameC
			}else{
				parentNames=parent.categoryNameC
			}
			if(parent.parentIds){
				parentIds=parent.parentIds+'_'+parent.id
			}else{
				parentIds=parent.id
			}
		}
		articleCategoryInstance.setParentIds(parentIds?.toString())
		articleCategoryInstance.setParentNames(parentNames?.toString())

		articleCategoryInstance.setLastUpdateTime(new Date())
		articleCategoryInstance.setLastUpdaterId(getCurrentUser()?.getId())
		flash.message = message(code: 'default.updated.message', args: [message(code: 'articleCategory.label', default: 'ArticleCategory'), articleCategoryInstance.categoryNameC])
		redirect(action: "show", id: articleCategoryInstance.id)
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
		def articleCategoryInstance = ArticleCategory.get(params.id)
		if (!articleCategoryInstance) {
			flag = false
		}
		try {
			articleCategoryInstance.delete(flush: true)
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
