package cn.com.wz.parent.system.user

import cn.com.wz.parent.system.dictionary.DictionaryValue;

/**
 *@Description 登录用户详细信息
 *@Auther huxx
 *@createTime 2012-10-24 下午4:38:05
 */
class UserDetail {
	/**
	 * 表主键
	 */
	String id;
	/**
	 * 用户真实姓名
	 */
	String realName
	/**
	 * 电子邮箱
	 */
	String eMail
	/**
	 * 移动电话
	 */
	String mPhone
	/**
	 * 手机缩号
	 */
	String phoneCode
	/**
	 * 联系电话
	 */
	String phone
	/**
	 * 出生日期
	 */
	String birthDay
	/**
	 * 家庭住址
	 */
	String address
	/**
	 * 职务级别
	 */
	DictionaryValue jobLevel
	
	static belongsTo=[user:User]
	
    static constraints = {
		realName(blank:false,nullable:true)
		eMail(nullable:true,email:true)
		mPhone(nullable:true)
		phoneCode(nullable:true)
		phone(nullable:true)
		birthDay(nullable:true)
		address(nullable:true)
		jobLevel(nullable:true)
    }
	//数据库映射关系
	static mapping = {
		table 'sys_userDetail'
		// In case a sequence is needed, changed the identity generator for the following code:
		//id generator:'sequence', column:'DIC_TYPE_ID', params:[sequence:'COM_DICTIONARY_TYPE_sequence']
		id generator:'uuid.hex', column:'id'
		//children cascade: "save-update",sort:'showOrder', order: 'asc'
		jobLevel column:'JOB_LEVEL_ID'
	}
	
	String toString(){
		return "${realName}"
	}
}
