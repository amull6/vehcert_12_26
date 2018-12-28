package cn.com.wz.parent.cms
import cn.com.wz.parent.base.BaseController;
import org.hibernate.FetchMode;
import org.springframework.dao.DataIntegrityViolationException
import cn.com.wz.parent.cms.ArticleCategory;
import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.system.role.Role
import grails.converters.JSON
/**
 *@Description 栏目类型角色管理     控制器
 *@Auther LiuCJ
 *@createTime 2012-10-18 下午2:10:48
 */
class ArticleAuthController extends BaseController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def articleCategoryService
	
	def index() {
		render (view:'/cn/com/wz/parent/cms/articleAuth/index')
	}
	/**
	 * @Description 点击角色后，刷新栏目列表，并对已赋权限赋值
	 * @author huxx
	 * @createTime 2012-11-04
	 */
	def toCategoryList(){
		//获取所有的栏目信息，并组成树形结构
		def result=[:]
		def rows=articleCategoryService.getCategoryTree()
		//获取指定角色下的栏目权限
		rows?.each {ac->
			def auth=ArticleAuth.findByRoleIdAndCategoryId(params.roleId,ac.id)
			ac.publishAuth=0
			ac.auditAuth=0
			if(auth){
				 ac.publishAuth=auth.publishAuth
				 ac.auditAuth=auth.auditAuth
			}
		}
		result.rows=rows
		result.total=ArticleCategory.count()
			
		render result as JSON
	}

   /**
	*@Description 保存角色对菜单权限信息存储于ArticleAuth中
	*@param
	*@return
	*@Auther 
	*@createTime 2012-11-6 下午3:46:33
	*/
   def save(){
	   def flag=true
	   //取得选中的角色对栏目已有的权限信息
	   def auths=ArticleAuth.findAllByRoleId(params.roleId)
	   //删除原有权限
	   if(auths){
		   auths.each{a->
			   a.delete(flush:true)
		   }
	   }
	   //重新保存权限//
	   //先保存Publish权限
	   def articleCategoryIds = params.publishAuth.split('_')
	   if(articleCategoryIds){
	   	   articleCategoryIds.each
	   		{articleCategoryId->
				   ArticleAuth a= new ArticleAuth()
				   a.setCategoryId(articleCategoryId)
				   a.setRoleId(params.roleId)
				   a.setPublishAuth(1)
				   a.lastUpdateTime=new Date()
				   a.setLastUpdaterId(getCurrentUser()?.getId())
				   
				  try{
					   a.save(flush:true)	   
				  	 }catch(DataIntegrityViolationException e){
				  		flag=false
					 }
			}
	   	 }
	   //再保存Audit权限
	   def categoryIds = params.auditAuth.split('_')
	   	if(categoryIds){
	   	   categoryIds.each
			  {aId->
				  //保存auth权限时，如果roleid和categoryId的记录存在就更新记录，如果不存在就创建记录
				  def auth=ArticleAuth.findByRoleIdAndCategoryId(params.roleId,aId)
				  if(auth){
					    auth.setAuditAuth(1)
						auth.lastUpdateTime=new Date()
						auth.setLastUpdaterId(getCurrentUser()?.getId())
						try{
							auth.save(flush:true)
							}catch(DataIntegrityViolationException e){
							   flag=false
						}
				  }else{
					  ArticleAuth a= new ArticleAuth()
					  a.setCategoryId(aId)
					  a.setRoleId(params.roleId)
					  a.setAuditAuth(1)
					  a.lastUpdateTime=new Date()
					  a.setLastUpdaterId(getCurrentUser()?.getId())
					 try{
						 a.save(flush:true)
						 }catch(DataIntegrityViolationException e){
							flag=false
					 }
				   }
	   	   		}
		   }
	   //设置操作信息
	   if(flag){
		   render message(code: 'organUser.save.success.message',default:'Success')
	   }else{
		   render message(code: 'organUser.save.fail.message',default:'failed')
	   }
   }

}