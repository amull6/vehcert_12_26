package cn.com.wz.parent.system.post
import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.system.organization.Organization
import cn.com.wz.parent.system.user.User;
/**
 * @Description 岗位 domain域
 * @author Administrator xinbh
 * @createTime 2012-9-19 下午16：10
 */
class Post {
	String id
	/**
	 * 岗位中文名称
	 */
	String postNameC
	/**
	 * 岗位类型名称
	 */
	/**
	 * 岗位编码
	 */
	String code
	/**
	 * 岗位显示顺序
	 */
	int showOrder
	/**
	 * 岗位描述
	 */
	String remark
	/**
	 * 岗位创建人
	 */
	String createrld
	/**
	 * 岗位创建时间
	 */
	Date createTime
	/**
	 * 岗位最后更新人
	 */
	String lastUpdaterld
	/**
	 * 岗位最后更新时间
	 */
	Date lastUpdateTime
	static belongsTo=[postType:DictionaryValue]
	static hasMany=[users:User]
	static constraints = {
		id(size: 1..200, blank: false)
		postNameC(blank:false,unique:true)
		showOrder(nullable:true)
		createrld(nullable:true)
		createTime(nullable:true)
		lastUpdaterld(nullable:true)
		lastUpdateTime(nullable:true)
		code(blank:false,unique:true)
		postType(nullable:false)
		remark(nullable:true)
    }
	/**
	 * 数据库的映射关系
	 */
	static mapping={
		table 'SYS_post'
		id generator:'uuid.hex', column:'id'
		postType column:'POST_TYPE_ID'
	}
	String toString(){
		return "${postNameC}"
	}
	}
