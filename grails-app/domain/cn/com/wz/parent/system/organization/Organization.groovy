package cn.com.wz.parent.system.organization


import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User;

/**
 *@Description 组织结构domain域
 *@Auther huxx
 *@createTime 2012-9-17 下午5:14:11
 */
class Organization {

	String id;
	/**
	 * 组织结构中文名称
	 */
	String organNameC;
	/**
	 * 组织结构英文名称
	 */
	String organNameE;
	/**
	 * 组织结构编码
	 */
	String organCode;
	/**
	 * 组织结构显示顺序号
	 */
	int showOrder;
	
	/**
	 * 组织结构描述
	 */
	String description;
	
	static hasMany=[childs:Organization,users:User]
	
	static belongsTo=[parent:Organization,organType:DictionaryValue]//会自动在表中生成字段 parent_id,对应的属性为parentId
	//static fetchMode = [organs: 'lazy']//设置级联模式eager级联，lazy延迟
	//与字典值表建立单向关系
	//static hasOne=[organType:DictionaryValue]
	
	//字段约束条件
    static constraints = {
		
		id (size: 1..200, blank: false)
		organNameC (size: 1..200,blank:false)
		organNameE (size: 1..200,blank:false)
		organCode (size:1..200,unique:true,blank:false)
		showOrder (blank:false)
		description (nullable:true)
		parent ( size:0..4000)
		organType ()
    }
	
	//数据库映射关系
	static mapping={
		table 'SYS_organ'
		id generator:'uuid.hex', column:'id'
		employees (joinTable:[name:'tbl_organ_employee',key:'organId'])
	}
	
	/**
	 * 返回字符串
	 */
	String toString(){
		return "${organNameC}"
	}
}
