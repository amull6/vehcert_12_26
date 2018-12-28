package cn.com.wz.parent.auth
/**
 * @description 角色对字典值（dictionaryValue)的功能权限
 * @author liucj
 * @createTime 2013.7.7 下午4.08
 */
class Auth {
	/**
	 * 表主键
	 */
	String id;
	/**
	 * 角色Id
	 */
	String roleId;
	/**
	 * 字典值ID
	 */
	String dictionaryId;
	/**
	 * 权限，0表示不具有权限，1表示具有权限
	 */
	int auths;
	/**
	 * 最后修改时间
	 */
	Date lastUpdateTime;
	/**
	 * 最后修改人id
	 */
	String lastUpdaterId;

	static constraints = {
		roleId(blank:false)
        dictionaryId(blank:false)
        auths(nullable:true)
		lastUpdateTime(nullable:true)
		lastUpdaterId(nullable:true)
	}
	static mapping = {
		table 'TBL_AUTH'
		// version is set to false, because this isn't available by default for legacy databases
		version false
		id generator:'uuid.hex', column:'ID'
   }
}
