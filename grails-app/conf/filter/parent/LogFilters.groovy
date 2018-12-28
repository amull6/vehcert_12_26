package filter.parent

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.system.LogService
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @Description 系统日志过滤器
 */
class LogFilters {
    LogService logService
    def messageSource
    def filters = {
        //##################################文章管理日志#################################################################
        articleFilter(controller:'article', action:'(save|update|jsonDelete|top|untop|audit|unaudit)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.article."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.article."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "articleLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }

        articleAuthFilter(controller:'articleAuth', action:'(save)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.articleAuth."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.articleAuth."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "articleAuthLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }

        articleCategoryFilter(controller:'articleCategory', action:'(save|update|jsonDelete)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.articleCategory."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.articleCategory."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "articleCategoryLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }

        //##################################文章管理日志END#################################################################

        //##################################用户及权限管理日志#################################################################
        userFilter(controller:'user', action:'(updateChangeMessage)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.user."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.user."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "user", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }

        authFilter(controller:'auth', action:'(save)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.auth."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.auth."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "authLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }
        //##################################用户及权限管理日志END#################################################################

        //##################################文件下载管理日志#################################################################
        downLoadFilter(controller:'downLoad', action:'(downFile|downZip)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.downLoad."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.downLoad."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "downLoadLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }
        //##################################文件下载管理日志END#################################################################

        //##################################菜单管理日志#################################################################
        menuFilter(controller:'menu', action:'(save|update|jsonDelete)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.menu."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.menu."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "menuLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }
        //##################################菜单管理日志END#################################################################

        //##################################字典管理日志#################################################################
        dictionaryTypeFilter(controller:'dictionaryType', action:'(save|update|jsonDelete)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.dictionaryType."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.dictionaryType."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "dictionaryTypeLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }

        dictionaryValueFilter(controller:'dictionaryValue', action:'(save|update|jsonDelete)') {
            before = {
            }
            after = { Map model ->
                def actionLable="wz.log.dictionaryValue."+actionName+".lable"
                def msgAction=messageSource.getMessage("${actionLable}", [].toArray(), LocaleContextHolder.getLocale())
                def controllerLable="wz.log.dictionaryValue."+controllerName+".lable"
                def msgController=messageSource.getMessage("${controllerLable}", [].toArray(), LocaleContextHolder.getLocale())
                logService.insertLog(session.getAttribute(ConstantsUtil.LOGIN_USER).id, "dictionaryValueLog", msgAction+msgController+"/"+params,'businessLog')
            }
            afterView = { Exception e ->

            }
        }
        //##################################字典管理日志END#################################################################
    }
}
