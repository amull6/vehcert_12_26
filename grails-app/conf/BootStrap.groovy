import grails.converters.JSON
class BootStrap {
    def preferenceService
    def dictionaryService
    def init = { servletContext ->
//		def appCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
//		initMenu()
//		initLinkTool()
//		initDictionary()
//		
        //时间格式转换
        JSON.registerObjectMarshaller(Date) {
            return it?.format("yyyy-MM-dd HH:mm:ss")
        }

        initDefaultSysConfig(servletContext)

    }
    def destroy = {

    }

    /**
     * @Description 加载系统默认配置
     * @create huxx 2013-06-12
     */
    def initDefaultSysConfig(servletContext){
        println '================================>初始化部分字典信息开始'
        //将字典中needSaveToApplicationCodes记录的字典值存入application中
        dictionaryService.initDicValueToApplication(servletContext);
        println '================================>初始化部分字典信息完成'

    }
}
