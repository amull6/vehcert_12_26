package cn.com.wz.parent.system

import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.system.dictionary.DictionaryType
import cn.com.wz.parent.system.dictionary.DictionaryValue;

/**
 *@Description 字典管理service类
 *@Auther huxx
 *@createTime 2013-3-1 下午1:14:28
 *@update huxx 2013-06-22 将字典值needSaveToApplicationCodes中记录的字典值存入application中，每个字典值对应一个Attribute("dic_dicCode",dicValue)
 */
class DictionaryService {
	/**
	 * 递归查询某个节点的所有子节点
	 * @param parentId
	 * @return
	 */
	 def getSubDictionaryType(parentId){
		def parent = DictionaryType.get(parentId)
		def children=parent?.children
		children?.each {
			children += getSubDictionaryType(it.id)
		}
		children
	}
    /**
     * 递归查询某个节点的所有子节点
     * @param code
     * @return
     */
    def getSubDictionaryTypeCode(String code){
//        def parent = DictionaryType.findByCode(code)
        def children=DictionaryType.createCriteria().list({
            parent{eq("code",code)}
            order("showOrder","asc")
        })
        children?.each {
            def result=getSubDictionaryType(it.code)
            if(result){
                children += getSubDictionaryType(it.code)
            }
        }
        children
    }

    /**
     * @Description 将needSaveToApplicationCodes中记录的字典值存入application中
     * @param servletContext
     * @return
     * @create huxx 2013-06-22
     */
    def initDicValueToApplication(servletContext){
        def dicValues=DictionaryValue.findByCode('needSaveToApplicationCodes')
        def initCodes=dicValues?.value1?.split(":_:")
        if (initCodes && initCodes.length>0){
            //解决in中字符串不大于1000的问题
            def lst=StringUtil.splitIn(initCodes)
            def cel={
                lst?.each {codes->
                    'in'('code',codes)
                }
            }
            def results = DictionaryValue.createCriteria().list(cel)
            results?.each{
                servletContext.setAttribute("dic_${it.code}",it)
            }
        }
    }

    /**
     * @Description 判断dicValue是否需要存入application中，如果需要就存入，不需要就不存入
     * @param servletContext
     * @return
     * @create huxx 2013-06-22
     */
    def initDicValueToApplication(servletContext,dicValue){
        def dicValues=DictionaryValue.findByCode('needSaveToApplicationCodes')
        def initCodes=dicValues?.value1?.toString()
        if (initCodes?.indexOf(dicValue?.code?.toString())){
            servletContext.setAttribute("dic_${dicValue.code}",dicValue)
        }

    }
    /**
     *@Description   通过字典项的code取字典值
     *@Auther liucj
     *@createTime 2013-7-13 16:25:31
     */
    def getDicValueByType(params){
        def cel={
            dictionaryType{
                eq('code',"${params.dicType}")
            }
            order('ordernum','asc')
        }

        def lst=DictionaryValue.createCriteria().list(params,cel)
        return  lst
    }
}
