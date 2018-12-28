package cn.com.wz.parent.system.role

import cn.com.wz.parent.appmanage.AppRole
import cn.com.wz.parent.base.BaseController;
import org.hibernate.FetchMode;
import org.springframework.dao.DataIntegrityViolationException
import cn.com.wz.parent.base.BaseController;
import cn.com.wz.parent.system.Menu;
import cn.com.wz.parent.system.dictionary.DictionaryValue;
/**
 *@Description 菜单角色管理控制器 
 *@Auther huxx
 *@createTime 2012-9-23 上午10:25:48
 */
class MenuRoleController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        render (view:'/cn/com/wz/parent/system/menuRole/index')
    }

	/**
	 *@Description 按角色类型获取所有角色信息
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-23 上午10:47:12
	 */
   def selectRoleByType(){
       //获取当前登录系统的appId
       def appCode=grailsApplication.config.app.appCode
       def appRole=DictionaryValue.findByCode(appCode)
       //根据appId获取应用系统中拥有的角色id
       def allAppRoleList=AppRole.findAllByAppId(appRole?.id)
       def allRoleIds=[]
       allAppRoleList?.each {
           allRoleIds.add(it.roleId)
       }
	   render(contentType: "text/json") {
		   params.sort='showOrder'
		   params.order="asc"
		   
		   array {
			   //获取角色类型
			   def roleTypeList=DictionaryValue.createCriteria().list([sort:'ordernum',oder:asc],{dictionaryType {eq('code','roleType')}})
			  
			   for (t in roleTypeList) {
				   //处理中英文转换
				   def textLable=isEnglish()?t.dicValueNameE:t.dicValueNameC
				   item(id:t.id,pid:null,text:textLable,type:'roleType')
				   
				   //获取角色类型下的角色信息
				   def roleList=Role.createCriteria().list(params,{roleType{eq('id',t.id)}
                      inList('id',allRoleIds)
                   })
				   for(r in roleList){
					   //处理中英文转换
					   textLable=isEnglish()?r.roleNameE:r.roleNameC
					   item(id:r.id,pid:t.id,text:textLable,type:'role')
				   }
			   }
		   }
	   }
   }
   
   /**
    *@Description 保存角色菜单信息
    *@param 
    *@return 
    *@Auther huxx
    *@createTime 2012-9-23 下午2:46:33
    */
   def save(){
	   def flag=true
	   //取得角色信息
	   def role=Role.get(params.roleId)
	   
	   //################删除原来的角色菜单关联信息####################
	   //根据角色id获取角色下的所有菜单信息
	   def cel={
		   roles{
			   eq('id',params.roleId)
		   }
	   }
	   def menus=Menu.createCriteria().list(cel)
	   menus.each {menu->
		   try{
			  role.removeFromMenus(menu)
		   }catch(DataIntegrityViolationException e){
				   flag=false
		   }
	   }
	   //################删除原来的角色菜单关联信息END####################
	   
	   //###############保存新的角色菜单管理信息#############
	   params.menuIds?.split("_").each {menuId->
		   def menu=Menu.get(menuId)
		   menu?.addToRoles(role)
		   
	   }
	   try{
		   role.save(flash:true)
	   }catch(DataIntegrityViolationException e){
		   flag=false
	   }
	  
	   //设置操作信息
	   if(flag){
		   render message(code: 'default.save.success.message',default:'Success')
	   }else{
		   render message(code: 'default.save.fail.message',default:'failed')
	   }
   }
   
   /**
    *@Description 获取角色下已保存的菜单信息
    *@param roleId角色id
    *@return 以"_"分隔的角色roleId下的菜单id字符串
    *@Auther huxx
    *@createTime 2012-9-24 上午8:52:03
    */
   def selectOldMenusByRole(){
	   //根据角色id获取角色下的所有菜单信息
	   def cel={
		   roles{
			   eq('id',params.roleId)
		   }
	   }
	   def menus=Menu.createCriteria().list(cel)
	   //将菜单id组合成以'_'分隔的字符串
	   def menuIds=""
	   menus.each {menu->
		   menuIds+=menu.id+'_'
	   }
	   if(!"".equals(menuIds)){
		   menuIds=menuIds.substring(0, menuIds.length()-1)
	   }
	  
	   render menuIds
   }


}
