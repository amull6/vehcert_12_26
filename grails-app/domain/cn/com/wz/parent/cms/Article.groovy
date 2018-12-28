package cn.com.wz.parent.cms

import org.springframework.orm.hibernate3.support.ClobStringType;

import cn.com.wz.parent.cms.Accessory
/**
 *@description 网页文章维护
 *@author LiuCJ
 *@createTime 2012-10-4 下午4：02
 */

class Article {
	static mapping = {
		table 'TBL_ARTICLE'
		// version is set to false, because this isn't available by default for legacy databases
		version false
		id generator:'uuid.hex', column:'ID'
		articleCategory column:'CATE_GORY_ID'
		contentC type: ClobStringType
		contentE type: ClobStringType
		description type: ClobStringType
   }
	String id;
	/**
	 * 文章中文标题
	 */
	String titleC;
	/**
	 * 文章英文标题
	 */
	String titleE;
	/**
	 * 文章中文内容，内容信息可能很长，需要用大字段存储
	 */
	String contentC;
	/**
	 * 文章英文内容，同contentC用大字段存储
	 */
	String contentE;
	/**
	 * 是否置顶，0不指定（默认值）；1置顶
	 */
	boolean isTop;
    /**
     * 推荐，0不推荐（默认值）；1推荐
     */
    int recommend=0;
	/**
	 * 审核通过页面才显示，0没审核；1通过审核；2未通过审核
	 */
	int isAudited=0;
    /**
     * 文章权重，数字越大显示顺序越靠前，默认为0；
     */
    int showOrder=0;
	/**
	 * 附件ids，多个附件id用_分隔
	 */
	String accessoryIds;
	/**
	 * 文章简介
	 */
	String description;
	/**
	 * 关键字，多个关键字用"、"分隔
	 */
	String keyWords;
	/**
	 * 显示图片（图片类新闻使用）
	 */
	String picName;
	/**
	 * 图片路径
	 */
	String picPath;
	/**
	 * 文章创建人id
	 */
	String createrId;
	/**
	 * 文章创建时间
	 */
	Date createTime;
	/**
	 * 最后修改人id
	 */
	String lastUpdaterId;
	/**
	 * 最后修改时间
	 */
	Date lastUpdateTime;
	/**
	 * 关联栏目类型
	 */
	ArticleCategory  articleCategory
	
	static hasMany=[accessorys:Accessory]
	static constraints = {
		id(size: 1..200, blank: false)
		titleC(size: 1..200,blank:false)
		titleE(size: 1..200,blank:true,nullable:true)
		isTop()
		isAudited()
        recommend()
		contentC(blank:false)
		contentE(blank:true,nullable:true)
		description(nullable: true)
        showOrder(nullable: true)
		keyWords(blank:true,nullable:true)
		picName(size: 1..200,nullable: true)
		picPath(size: 1..200,nullable: true)
		createrId(nullable: true)
		createTime(nullable: true)
		lastUpdaterId(nullable: true)
		lastUpdateTime(nullable: true)
		accessoryIds(nullable:true)
	}
	String toString() {
		return "${titleC}"
	}
}
