package cn.com.wz.parent

import cn.com.wz.parent.system.resourcemanage.ResourceRole
import cn.com.wz.parent.system.role.Role
import cn.com.wz.parent.system.user.User

/**
 * @Description: 处理系统资源权限的相关标签库
 * @Create: 13-8-12 下午4:19   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class ResourceAuthTagLib {
    static namespace="c"
    def resourceAuth={attrs,body->
        //获取当前登录用户具有的角色
        def userId=session."${ConstantsUtil.LOGIN_USER}".id
        def cel={
            users{
                eq('id',userId)
            }
        }
        def roleList=Role.createCriteria().list (cel)
        def roleIds=roleList?.id
        if (roleIds||roleIds.size()<1){
            roleIds.add('就不相信你存在')
        }

        //根据资源code获取资源角色记录
        def resourceCode=attrs.resourceCode
        def result=ResourceRole.findAll('from ResourceRole as rr ,DictionaryValue d where d.id=rr.resourceId and d.code=:resourceCode',[resourceCode:resourceCode])
        def flag=false

        //查看当前用户是否有权限使用当前资源
        result?.each{
            if(roleIds.contains(it[0].roleId)){
               flag = true
            }
        }

        if (flag){
            out<< body()
        }else{
            out<< ''
        }
    }
}
