package cn.com.wz.parent.system

/**
 * 默认配置服务
 * @author huxx
 * @update huxx 2013-06-22 将preference全部存入application中，并且程序中取得时候从application中取，减少了对数据库的读取。
 * 对preference的存统一使用save方法，取preference时统一从application中取。
 *
 */
class PreferenceService {
    /**
     * @Description 保存preference的通用方法
     * @param u
     * @param params
     * @param servletContext
     * @return
     * @create huxx 2013-06-22
     */
    def save(u,params,servletContext){
        Preference preferenceInstance=Preference.findByUserAndCode(u,params.code)
        if(preferenceInstance){
            preferenceInstance.properties=params
            preferenceInstance.lastUpdateTime=new Date()
            if(!preferenceInstance.save(flush: true)){
                return false
            }
        }else{
            preferenceInstance=new Preference()
            preferenceInstance.properties=params
            preferenceInstance.createTime=new Date()
            preferenceInstance.lastUpdateTime=new Date()
            if (params.showOrder){
                preferenceInstance.showOrder=params.showOrder
            }else{
                preferenceInstance.showOrder=0
            }
            preferenceInstance.user=u
            if(!preferenceInstance.save(flush: true)){
                return false
            }
        }

        initPrefereceToApplication(servletContext,preferenceInstance)
        return true
    }

    /**
     * @Description 将用户个性化设置初始化到application中
     * @param serveltContext
     * @return
     * @Create huxx 2013-06-22
     */
    def initPreferenceToApplication(servletContext){
        //保存系统用户个性设置
        def lst =Preference.list()
        lst?.each{
            def map=[:]
            map.id=it.id
            map.code=it.code
            map.preName=it.preName
            map.preValue=it.preValue
            map.user=it.user
            servletContext.setAttribute("${it.code}_${it.user.id}",map)
        }
    }

    /**
     * Description 将单个的preference存入application中
     * @param servletContext
     * @param p
     * @return
     * @Create huxx 2013-06-22
     */
    def initPrefereceToApplication(servletContext,p){
        def map=[:]
        map.id=p.id
        map.code=p.code
        map.preName=p.preName
        map.preValue=p.preValue
        map.user=p.user
        servletContext.setAttribute("${p.code}_${p.user.id}",map)
    }



    def saveEmail(user,params,servletContext){
        Preference preferenceInstance=Preference.findByUserAndCode(user,"email")
        if(preferenceInstance){
            preferenceInstance.preValue=params.userName+params.address+":"+params.password
            preferenceInstance.lastUpdateTime=new Date()
            if(!preferenceInstance.save(flush: true)){
                return false
            }
        }else{
            preferenceInstance=new Preference()
            preferenceInstance.preName='邮箱设置'
            preferenceInstance.preValue=params.userName+params.address+":"+params.password
            preferenceInstance.code='email'
            preferenceInstance.preDesc='邮箱信息'
            preferenceInstance.createTime=new Date()
            preferenceInstance.lastUpdateTime=new Date()
            preferenceInstance.showOrder=3
            preferenceInstance.user=user
            if(!preferenceInstance.save(flush: true)){
                return false
            }
        }

        initPrefereceToApplication(servletContext,preferenceInstance)
        return true
    }
}
