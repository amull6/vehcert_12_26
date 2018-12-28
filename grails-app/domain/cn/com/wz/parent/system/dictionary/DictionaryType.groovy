package cn.com.wz.parent.system.dictionary

/**
 * @Description 字典类型domain类
 *@Auther huxx
 *@createTime 2012-9-15 上午8:49:13
 */
class DictionaryType {
	
	//表主键
	String id
	/**
	 * 字典项中文名称
	 */
	String typeNameC;
	/**
	 * 英文名称
	 */
	String typeNameE;
	
	/*
	 * 字典类型编码
	 */
	String code
	
	/**
	 * 显示顺序
	 */
	int showOrder;
	/**
	 * 创建人id
	 */
	String createrId;
	/**
	 * 类型创建时间
	 */
	Date createTime;
	/**
	 * 类型最后修改时间
	 */
	String lastUpdateTime;
	/**
	 * 最后修改人id
	 */
	 String lastUpdaterId;

	//Relation
	static hasMany=[dictionaryValues:DictionaryValue,children:DictionaryType]
	static belongsTo=[parent:DictionaryType]
	//约束条件
	static constraints = {
	
		id (size: 1..32)
		typeNameC (size: 0..200,blank:false,nullable: false)
		typeNameE (size: 0..200,nullable: true)
		code (blank:false,unique:true)
		showOrder (inList:0..10,blank:false)
		parent ()
		
		createTime (nullable:true)
		createrId (nullable:true)
		lastUpdateTime (nullable:true)
		lastUpdaterId (nullable:true)

	}
	
	//数据库映射关系
	static mapping = {
		table 'sys_dictionaryType'
		id generator:'uuid.hex', column:'id'
        children sort: 'showOrder',order: "asc"
        dictionaryValues sort:"ordernum",order:"asc"
        sort "showOrder"
	}
	
	String toString() {
		return "${typeNameC}:${typeNameE}"
	}
}
