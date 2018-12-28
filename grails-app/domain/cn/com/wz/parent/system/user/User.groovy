package cn.com.wz.parent.system.user

import cn.com.wz.parent.system.Preference
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.organization.Organization
import cn.com.wz.parent.system.post.Post;
import cn.com.wz.parent.system.role.Role

/**
 *@Description 登录用户domain类
 *@Auther huxx
 *@createTime 2012-9-17 上午9:40:17
 */
class User {
	/**
	 * 表主键
	 */
	String id;
	/**
	 * 用户登录名
	 */
	String userName;
	/**
	 * 登录密码
	 */
	String password;
	/**
	 * 用户类型 0临时用户；1常规用户
	 */
	String userTypeName
	/**
	 * 账户有效开始时间
	 */
	String validStartTime
	/**
	 * 账户有效结束时间
	 */
	String validEndTime
	/**
	 * 账户状态 0未启用；1启用
	 */
	boolean status
	/**
	 * 上次登录时间
	 */
	String lastLoginTime;
	
	DictionaryValue userType
	
	//创建人信息
	Date createTime
	String createrId
	/**
	 *最新修改时间
	 */
	Date lastUpdateTime
	
	String  lastUpdaterId
	
	//static transients = ['userTypeName','realName']

    //注意所有与用户关联的类都会影响用户删除方法的执行，如果增加了关联关系，请联系王涛添加相应的删除代码。
	static hasOne=[userDetail:UserDetail]
	static belongsTo=[Organization,Role,Post]
	static hasMany=[organs:Organization,roles:Role,preferences:Preference,posts:Post]
    static constraints = {
		userName (blank:false,unique: true,minSize:1,maxSize:20)
		password (blank:false,minSize:6,maxSize:32)
		lastLoginTime(nullable:true)
		validStartTime(nullable:true)
		validEndTime(nullable:true)
		userTypeName(nullable:true)
		lastUpdateTime(nullable:true)
		lastUpdaterId(nullable:true)
		createTime(nullable:true)
		createrId(nullable:true)
    }
	//数据库映射关系
	static mapping = {
		table 'sys_user'
		// In case a sequence is needed, changed the identity generator for the following code:
		//id generator:'sequence', column:'DIC_TYPE_ID', params:[sequence:'COM_DICTIONARY_TYPE_sequence']
		id generator:'uuid.hex', column:'id'
		//children cascade: "save-update",sort:'showOrder', order: 'asc'
		userDetail(lazy: false)
		organs(lazy: false)
	}
}
