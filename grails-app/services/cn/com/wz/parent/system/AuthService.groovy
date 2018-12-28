package cn.com.wz.parent.system

import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.appmanage.AppRole
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.role.Role

/**
 * @Description 权限处理service
 * @Author huxx
 * @CreateTime 2013-05-13
 */
class AuthService {

    /**
     * @Description 根据条件获取角色信息
     * @param params.userId用户id,params.userName用户登录名，params.appCode应用系统编码
     * @create huxx 2013-05-13
     */
    def selectRolesByParams(Map params){
        //根据应用系统的编码获取应用系统的id
        def appRole=DictionaryValue.findByCode("${params.appCode}")
        //根据appId获取应用系统中拥有的角色id
        def allAppRoleList=AppRole.findAllByAppId(appRole?.id)
        def allRoleIds=[]
        allAppRoleList?.each {
            allRoleIds.add(it.roleId)
        }

        //根据应用系统拥有的角色id和用户id获取用户在此系统中拥有的角色信息
        if (allRoleIds.size()<1){
            allRoleIds.add('就不相信你存在')
        }
        //解决in中字符串不大于1000的问题
        def lst=StringUtil.splitIn(allRoleIds)

        def cel={
            users{
                if(params.userId){
                    eq('id',params.userId)
                }
                if(params.userName){
                    eq('userName',params.userName)
                }

            }
            lst?.each {
                'in'('id',it)
            }
        }

        def result=Role.createCriteria().list(cel)
        return result
    }
}
