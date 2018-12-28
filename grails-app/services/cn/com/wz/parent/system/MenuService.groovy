package cn.com.wz.parent.system

import cn.com.wz.parent.system.Menu
import cn.com.wz.parent.system.role.Role;

/**
 * 系统菜单的服务类
 * @author haojia
 *
 */
class MenuService {

    def authService

	def initMenu(){
		//从配置文件中读取XML
		def xml = new XmlParser().parse("menu.xml")
	}
	/**
	 * 递归查询某个节点的所有子节点
	 * Groovy的强大语法，可以认真学习一下——郝佳
	 * @param parentId
	 * @return
	 */
	def getSubMenu(parentId){
		def parent = Menu.get(parentId)
		def children=parent?.children
		children?.each {
			children += getSubMenu(it.id)
		}
		children
	}
	
	
	/**
	 *@Description 根据用户名获取具有权限的菜单信息
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-28 上午11:02:05
	 */
	def getMenusByUserName(userName,params){
		//根据用户获取用户所属的角色信息
//		def cel={
//			users{
//				eq('userName',userName)
//			}
//		}
//		def roleList=Role.createCriteria().list(cel)
        def roleList=authService.selectRolesByParams(['userName':"${userName}",appCode: "${params.appCode}"])
		//获取角色id并组成数组
		def roleIds=[]
		roleList.each { role->
			roleIds.add(role.id)
		}
		//处理用户不属于任何角色的情况
		if(roleIds.size()<=0){
			roleIds.add('就不信你存在！')
		}
		//根据角色id信息获取菜单信息
		def menuCel={
			roles{
				inList('id',roleIds)
			}
			order (params.sort,params.order)
		}
		def menuList=Menu.createCriteria().listDistinct(menuCel)
	}
	/**
	 *@Description 获取用户有权限查看的根菜单信息
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-27 下午3:59:22
	 */
	def getRootMenu( userName,params){
		def menuList=[]
		//对system赋值全部
		if('system'.equals(userName)){
			menuList=Menu.findAllByParentIsNull(params)
		}else{
			menuList=getMenusByUserName(userName,params)
			
			menuList=checkRootList(menuList)
			menuList=sort(menuList)
		}
		return menuList
		
	}
	/**
	 *@Description 根据showorder进行排序
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-28 下午2:51:34
	 */
	def sort(lst){
		def result=[]
		lst?.each {m->
			if(result){
				//用于标识m是否已经加入result中
				def flag=0
				//遍历result将m插入比m.showOrder大的元素前面
				for(int i=0;i<result.size();i++){
					def r=result.get(i)
					if(r.showOrder>=m.showOrder){
						result.add(i, m)
						flag=1
						break
					}
				}
				//如果result中没有比m.showOrder大的就将m插入result的最后
				if(!flag){
					result.add(m)
				}
			}else{
				result.add(m)
			}
			
		}
		
		result
	}
	/**
	 *@Description 检查所有节点的根节点是否存在，如果不包含就加入，并将非根节点删除 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-27 下午4:07:53
	 */
	def checkRootList(menuList){
		def result=[]
		result.addAll(menuList.collect())
		menuList?.each {m->
			//元素是否有父节点
			if(m.parent){
				//将非根节点删除
				result.remove(m)
				def parent=getRootParent(m)
				//如果根节点不在结果集中，就将根节点加入结果集
				if(!result.collect().contains(parent)){
					result.addAll(parent)
				}
			}
		}
		result
	}
	/**
	 *@Description 递归获取child的根节点
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-11-28 上午9:13:05
	 */
	def getRootParent(child){
		def result
		//判断child是否有父节点
		if(child.parent){
			def parent=Menu.get(child.parent.id)
			if(parent.parent){
				result=getRootParent(parent)
			}else{
				result=parent
			}
		}
		result
	}
	
	/**
	 *@Description 获取指定节点下相应权限的子节点
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-11-28 上午11:06:23
	 */
	def getSubMenuByAuth(userName,params){
		
		def result=[]
		def parent=Menu.get(params.pid)
		if(parent){
			if('system'.equals(userName)){
				result=getSubMenu(parent.id)
			}else{
				//取得节点下的所有子节点
				def childrens=getSubMenu(parent.id)
				//获取用户所有具有权限的菜单
				def authChilds=getMenusByUserName(userName,params)
				//获取指定节点下的用户具有权限的子节点
				def childs=childrens?.collect().intersect(authChilds)
				//
				result=checkParentExist(childs)
				//将根节点删除
				result.remove(parent)
			}
		}
		result
	}
	
	/**
	 *@Description 检查所有节点的父节点是否存在，如果不包含就加入
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-11-27 下午4:07:53
	 */
	def checkParentExist(menuList){
		def result=[]
		result.addAll(menuList.collect())
		menuList?.each {m->
			//元素是否有父节点
			if(m.parent){
				def parents=getParentsNotInList(m,result)
				result.addAll(parents)
			}
		}
		result
	}
	/**
	 *@Description 递归获取child的不在lst中的父节点的集合
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-11-28 上午9:13:05
	 */
	def getParentsNotInList(child,lst){
		def result=[]
		def oldLst=lst.collect()
		//判断child是否有父节点
		if(child.parent){
			def parent=Menu.get(child.parent.id)
			//lst中不包含父节点的情况，将parent加入父节点，并且将判断parent的父节点的信息是否加入lst中
			if(!(lst.contains(parent))){
				//parent.children?.clear()
				result.add(parent)
				oldLst.addAll(result)
				result.addAll(getParentsNotInList(parent,oldLst))
			}
		}
		
		result
	}
	
}
