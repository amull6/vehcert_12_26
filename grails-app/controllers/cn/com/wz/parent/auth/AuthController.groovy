package cn.com.wz.parent.auth

import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.dictionary.DictionaryValue
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

/**
 *@Description 角色对字典值（dictionaryValue)的功能权限控制器
 *@Auther liucj
 *@createTime 2013-7-7 下午4:00:26
 */
class AuthController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    /**
     * @Description 跳转到赋权页面
     * @author huxx
     * @createTime 2012-11-04
     */
	def index() {
		render (view:'/cn/com/wz/parent/auth/index',model: params)
	}
	/**
	 * @Description 点击角色后，刷新栏目列表，并对已赋权限赋值
	 * @author huxx
	 * @createTime 2012-11-04
	 */
	def dicValueList(){
		//获取所有的栏目信息，并组成树形结构
		def result=[:]
		//取三列的栏目信息
        def dicTypeCode=params.dicTypeCode.split('_')
		def cel={
			dictionaryType{
				inList('code',dicTypeCode)
			}
			order ('ordernum','asc')
		}
		def rows=DictionaryValue.createCriteria().list(cel)
		def lst=[]
		//获取指定角色下的栏目权限
		rows?.each {c->
			def m=[:]
			m.dicValueNameC=c.dicValueNameC
			m.id=c.id
            m.auths=0
            def auth=Auth.findByRoleIdAndDictionaryId(params.roleId,c.id)
			if(auth){
				 m.auths=auth.auths
			}
			lst.add(m)
		}
		result.rows=lst
		result.total=rows.size()
			
		render result as JSON
	}

   /**
	*@Description 保存角色对字典值的权限信息存储于Auth中
    *@param  dicTypeCode:字典值的字典类型的code,通过它可以获得想要赋权的所有字典值
    *@param  roleId：选定的角色Id，对这个角色进行赋权
    *@param  dictionaryIds：想要赋权的字典值的Id，页面被选中的字典值Id
	*@createTime 2013-7-7 下午4:26:33
	*/
   def save(){
	   def flag=true
	   //取得选中的角色对特定dicTypeCode的字典值的已有权限信息
       def dicTypeCode=params.dicTypeCode.split('_')
       def cel={
           dictionaryType{
               'in'('code',dicTypeCode)
           }
       }
       def dicValue=DictionaryValue.createCriteria().list(cel)
       def authCel={
           'in'('dictionaryId',dicValue.id)
           eq('roleId',params.roleId)
       }
	   def oldAuths=Auth.createCriteria().list(authCel)
	   //删除原有权限
       oldAuths.each{o->
		   o.delete(flush:true)
	   }
	   //重新保存权限//
	   def dictionaryIds = params.dictionaryIds.split('_')
	   if(dictionaryIds){
           dictionaryIds.each
			   {cid->
				   Auth h= new Auth()
				   h.setDictionaryId(cid)
				   h.setRoleId(params.roleId)
				   h.setAuths(1)
				   h.lastUpdateTime=new Date()
				   h.setLastUpdaterId(getCurrentUser()?.getId())
				  try{
					   h.save(flush:true)
					   }catch(DataIntegrityViolationException e){
						  flag=false
					 }
			}
       }else{
			   return
       }
	   //设置操作信息
	   if(flag){
		   render message(code: 'organUser.save.success.message',default:'Success')
	   }else{
		   render message(code: 'organUser.save.fail.message',default:'failed')
	   }
   }
}
