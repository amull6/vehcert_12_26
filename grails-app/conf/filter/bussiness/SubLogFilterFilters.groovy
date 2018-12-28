package filter.bussiness

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.system.LogService
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.core.io.support.PropertiesLoaderUtils

/**
 * @Description 添加业务日志
 * @create 2013-10-17 huxx
 * @添加日志步骤
 * 1、在字典中添加日志对象
 * 2、在过log.properties中添加相应的controller和action
 * 3、在i18n文件中添加以下几个信息 （注意区分大小写）
 *  wz.log.${controllerName}.${actionName}.lable
 *  wz.log.${controllerName}.lable
 *  wz.log.${controllerName}.operateObjectCode
 *  wz.log.${controllerName}.${actionName}.logType
 */
class SubLogFilterFilters {
    LogService logService
    def messageSource
    def filters = {
        def dsp=PropertiesLoaderUtils.loadAllProperties("log.properties")
        def controllers=dsp.getProperty("controllers")
        def actions=dsp.getProperty("actions")
        businessLogFilters(controller: "${controllers}", action: "${actions}") {
            before = {

            }
            after = { Map model ->
                def actionLable="wz.log.${controllerName}.${actionName}.label"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.${controllerName}.label"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())

                //操作对象code
                def operateObjectCode="wz.log.${controllerName}.operateObjectCode"
                def ooc= messageSource.getMessage("${operateObjectCode}", [].toArray(), LocaleContextHolder.getLocale())

                //日志类型
                def logType="wz.log.${controllerName}.logType"
                def type= messageSource.getMessage("${logType}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, ooc, msgAction+msgController+"/"+params,type)
            }
            afterView = { Exception e ->

            }
        }
    }
}
