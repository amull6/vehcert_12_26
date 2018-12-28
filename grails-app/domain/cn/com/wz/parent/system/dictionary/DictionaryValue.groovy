package cn.com.wz.parent.system.dictionary

import cn.com.wz.parent.system.organization.Organization
import cn.com.wz.parent.system.role.Role
import cn.com.wz.parent.system.user.User

class DictionaryValue {
    String id
    String dicValueNameC
    String dicValueNameE
	//字典编码
	String code
	//字典的值
	String value1
	String value2
	/**
	 * 备注信息
	 */
	String remark
    boolean isvalid
    Long ordernum
    Date lastUpdated
    String lastUpdatedBy='system'
    Date dateCreated
    String createdBy='system'
    // Relation
    DictionaryType dictionaryType
	
	static hasMany=[]
    static constraints = {
        id(size: 1..200, blank: false)
        dicValueNameC(size: 1..200,blank:false,nullable: false)
        dicValueNameE(size: 1..200)
		code(blank:false,unique:true)
		value1(nullable:true)
		value2(nullable:true)
        isvalid()
        ordernum(nullable: false, size: 0..10,blank:false)
        lastUpdated()
        lastUpdatedBy(size: 1..200, blank: false)
        dateCreated()
        createdBy(size: 1..200, blank: false)
		remark(nullable:true)
    }

    static mapping = {
        table 'SYS_DICTIONARY_VALUE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'DIC_VALUE_ID'
        dictionaryType column:'DIC_TYPE_ID'
        dateCreated column:'CREATION_DATE'
        lastUpdated column:'LAST_UPDATE_DATE'

        dictionaryType  sort:'showOrder',order:'asc'
        sort "ordernum":"asc"
    }

    String toString() {
        return "${dicValueNameC}" 
    }
	
}
