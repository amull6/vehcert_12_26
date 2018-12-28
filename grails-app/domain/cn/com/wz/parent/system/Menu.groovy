package cn.com.wz.parent.system
import cn.com.wz.parent.system.role.Role;
/**
 * 
 * @author haojia
 * 系统菜单数据对象
 */
class Menu {
	//系统编号
	String id
	//菜单的后台名称
	String textE
	//菜单显示内容
	String textC
	//显示顺序
	int showOrder
	//以下三个字段组成菜单对应的URL地址
	String controllerName//对应controller的名称
	String actionName//对应action的名称，可以为空
	String urlPms//对应需要想Url传递的数据，可以为为空
	//节点css
	String classes
	//菜单是否启用状态
	boolean enabled = false
	//创建日期
	Date dateCreated
	//更新日期
	Date lastUpdated
	//父级菜单
	Menu parent
	static belongsTo = [Role]
	//子菜单
	static hasMany = [children:Menu,roles:Role]
	//定义映射规则
	static mapping = {
		table 'SYS_MENU'
		id generator:'uuid.hex'
		children cascade: "all-delete-orphan",sort:'showOrder', order: 'asc'
		urlPms column:'url_Params'
	}
    static constraints = {
		parent(nullable:true)
		textC(maxSize:60,blank:false)
		textE(maxSize:60,blank:true)
		urlPms(nullable:true,blank:true)
		enabled(blank:false)
		controllerName(nullable:true)
		actionName(nullable:true)
		classes(nullable:true)
		showOrder (inList:0..10,nullable:false)
    }
	String toString(){
		return "${textC}:${textE}"
	}
}
