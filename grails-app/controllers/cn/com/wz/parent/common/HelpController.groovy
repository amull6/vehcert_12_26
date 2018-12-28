package cn.com.wz.parent.common
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.dictionary.DictionaryType
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User
import grails.converters.JSON;
/**
 *@Description 弹出用户指导页面
 *@Auther liucj
 *@createTime 2013-04-07 下午6:26:50
 */
class HelpController extends BaseController {

    def index(){
        render(view:'/cn/com/wz/parent/common/help',model:[params:params])
    }

    /**
     *@Description   通过字典项的code取字典值
     *@Auther liucj
     *@createTime 2013-7-13 16:25:31
     */
    def getDicValueByType(){
        if(params.articleCategoryCode){
            params.dicType=params.articleCategoryCode
        }
        params.max =params.maxSize
        params.offset =0
        def cel={
            dictionaryType{
                eq('code',"${params.dicType}")
            }
            order('ordernum','asc')
        }

        def lst=DictionaryValue.createCriteria().list(params,cel)
        render(contentType: "text/json") {
            array {
                lst?.each {a->
                    article(name:a.dicValueNameC,code:a.code)
                }
            }
        }
    }
}
