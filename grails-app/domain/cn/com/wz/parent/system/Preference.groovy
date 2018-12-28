package cn.com.wz.parent.system

import java.util.Date;

import cn.com.wz.parent.system.user.User;
/**
 *@Description  用户个性属性表
 *@Auther huxx
 *@createTime 2012-11-12 上午11:53:57
 */
class Preference {
	//编号
	String id
	/**
	 * 个性属性名
	 */
	String preName
	/**
	 * 个性属性值
	 */
	String preValue
	/**
	 * 个性属性编码
	 */
	String code
	/**
	 * 显示顺序
	 */
	int showOrder
	/**
	 * 个性属性描述
	 */
	String preDesc
	//所属用户
	static belongsTo = [user:User]
	//建日期
	Date createTime
	//更新日期
	Date lastUpdateTime
	
    static constraints = {
		id(size: 1..200, blank: false)
		preName(size: 0..4000)
		preValue(size: 0..4000)
		code(nullable:false)
		showOrder (inList:0..9)
		preDesc(size:0..4000)
		user(nullable:false)
		lastUpdateTime(nullable:true)
    }
	
	static mapping = {
		table 'SYS_PREFERENCE'
		// In case a sequence is needed, changed the identity generator for the following code:
		//id generator:'sequence', column:'DIC_TYPE_ID', params:[sequence:'COM_DICTIONARY_TYPE_sequence']
		id generator:'uuid.hex', column:'PREFERENCE_ID'
	}
	
	String toString(){
		return "${preValue}"
	}
}
