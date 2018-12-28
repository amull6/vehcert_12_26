package cn.com.wz.parent.cms

import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.cms.Article;
import cn.com.wz.parent.system.role.Role;
/**
 *@description 网站文本管理
 *@author LiuCJ
 *@createTime 2012-9-27下午5：10
 */
class ArticleCategory {
	
	String id;
	/**
	 * 分类中文名称
	 */
	String categoryNameC;
	/**
	 * 分类英文名
	 */
	String categoryNameE;
	/**
	 * 分类编码，业务调用中，可能会用编码做标识
	 */
	String categoryCode;
	/**
	 * 所有祖先分类的id，用_分隔
	 */
	String parentIds;
	/**
	 * 所有祖先分类的name名称，用_分隔
	 */
	String parentNames;
	/**
	 * 显示顺序
	 */
	int showOrder;
	/**
	 * 分类节点应用的样式
	 */
	String style;
	/**
	 * 是否删除，0表示未被删除；1表示已被删除
	 */
	int isDeleted=0;
	/**
	 * 文章是否需要审核，0不需要审核；1需要审核
	 */
	boolean needAuth;
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
	/**
	 * 分章栏目的类型：0一般文章栏目；1图片文章栏目；2产品栏目
	 */
	String categoryTypeName;
	/**
	 * 分类描述
	 */
	String description;
	DictionaryValue categoryType
	ArticleCategory parent
	
	/**
	 * 设置发布权限和审核权限，在对应的表中不进行保存
	 */
	int publishAuth
	int auditAuth
	static hasMany=[roles:Role,articles:Article,children:ArticleCategory]
	static belongsTo=[Role]
    static constraints = {
		id (size: 1..200, blank: false)
		categoryNameC (size: 1..200,unique:true,blank:false)
		categoryNameE (size: 1..200,blank:true,nullable:true)
		categoryCode (size:1..200,nullable:true)
		showOrder (blank:false)
		description (nullable:true)
		categoryType(nullable:true)
		//parentId(nullable:true)
		parentIds(nullable:true)
		parentNames(nullable:true)
		style(nullable:true)
		isDeleted(nullable:true)
		needAuth(nullable:true)
		createTime(nullable:true)
		createrId(nullable:true)
		lastUpdateTime(nullable:true)
		lastUpdaterId(nullable:true)
		parent (nullable:true)
		publishAuth(nullable:true)
		auditAuth(nullable:true)
    }
	
	/**
	 *与数据库库数据映射（定义数据库数据存储名称）
	 */
	static mapping = {
		table 'TBL_ARTICLECATEGORY'
		// version is set to false, because this isn't available by default for legacy databases
		version false
		id generator:'uuid.hex', column:'ID'
		categoryCode column:'category_Code'
		//parentId column:'PARENT_ID'
        children cascade: "all-delete-orphan",sort:'showOrder', order: 'asc'
		createTime column:'CREATION_DATE'
		lastUpdateTime column:'LAST_UPDATE_DATE'
	}
	
	String toString() {
		return "${categoryNameC}"}
}
