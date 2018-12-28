package cn.com.wz.parent.system.role

import cn.com.wz.parent.cms.ArticleCategory
import cn.com.wz.parent.system.Menu
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User


/**
 *@description 角色功能维护
 *@author LiuCJ
 *@createTime 2012-9-15
 */
class Role {
	/**
	 *与数据库库数据映射（定义数据库数据存储名称）
	 */
    static mapping = {
		table 'SYS_ROLE'
		id generator:'uuid.hex', column:'ID'
		roleCode column:'CODE'
		dateCreated column:'CREATION_DATE'
		lastUpdateTime column:'LAST_UPDATE_DATE'
	}
	/**
	 *序号
	 */
    String id
	/**
	 *角色英文名
	 */
    String roleNameE
	/*
	 *角色中文名
	 */
	String roleNameC
	/*
	 *显示顺序
	 */
	int showOrder
	/**
	 *角色编码
	 */
    String roleCode
	/*
	 *角色类型名称
	 */
	String roleTypeName
	/**
	 *创建时间
	 */
    Date dateCreated
	/**
	 *创建人
	 */
    String createdBy
	/**
	 *修改时间
	 */
	Date updateTime
	/**
	 *最新修改时间
	 */
    Date lastUpdateTime
	/**
	 *修改人
	 */
    String updatedBy
	/**
	 *角色描述
	 */
    String description
	static hasMany=[menus:Menu,users:User,articleCategorys:ArticleCategory]
	static belongsTo=[roleType : DictionaryValue]
	static constraints = {
		id(size:1..200,blank:false)
		roleNameE(size: 1..200, blank:true,nullable:true)
		roleNameC(size: 1..200, blank:false,unique:true)
		roleType()
		showOrder(inList:1..10)
		roleCode(blank:false,unique:true)
		dateCreated(nullable:true)
		updateTime(nullable:true)
		lastUpdateTime(nullable:true)
		updatedBy(size: 1..200,nullable:true)
		description(nullable:true)
		createdBy(size: 1..200, blank: false,nullable:true)
	}
	String toString() {
		return "${roleNameC}:${roleNameE}"
    }
}
