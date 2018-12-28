package cn.com.wz.parent.cms

class ArticleAuth {
	/**
	 * 表主键
	 */
	String id;
	/**
	 * 角色Id
	 */
	String roleId;
	/**
	 * 栏目类型Id
	 */
	String categoryId;
	/**
	 * 发布权限，0表示不具有发布权限，1表示具有发布权限
	 */
	int publishAuth;
	/**
	 * 审核权限，0表示没有审核权限，1表示有审核权限
	 */
	int auditAuth;
	/**
	 * 创建时间
	 */
	Date createTime;
	/**
	 * 创建人id
	 */
	String createrId;
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
		categoryId(blank:false)
		publishAuth(nullable:true)
		auditAuth(nullable:true)
		createTime(nullable:true)
		createrId(nullable:true)
		lastUpdateTime(nullable:true)
		lastUpdaterId(nullable:true)
	}
	static mapping = {
		table 'TBL_ARTICLEAUTH'
		// version is set to false, because this isn't available by default for legacy databases
		version false
		id generator:'uuid.hex', column:'ID'
   }
}
