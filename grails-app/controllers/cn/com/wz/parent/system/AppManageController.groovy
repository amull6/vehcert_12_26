package cn.com.wz.parent.system

import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.appmanage.AppRole
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.role.Role
import cn.com.wz.parent.system.user.User
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException;
/**
 * @Desecription 管理不同应用程序的信息
 * @Author huxx
 * @createTime 2013-05-13
 */
class AppManageController extends BaseController {

    def index() {}

    /**
     * @Description 跳转到应用系统的角色信息控制页面
     * @return
     * @create huxx 2013-05-13
     */
    def toAppRoleManage(){
        render(view:'/cn/com/wz/parent/system/appRole/index',model: params)
    }
    /**
     * @Description 根据应用id获取应用拥有的角色信息
     * @params appId 应用系统id； opFlag=0获取应用系统不具有的角色信息，opFlag=1获取应用系统拥有的角色信息
     * @create huxx 2013-05-13
     */
    def selectRolesByApp(){
        //设置分页信息
        params.max =params.limit ? params.int('limit') : 100
        params.offset = params.start ? params.int('start') :0

        //获取应用系统具有的角色的id
        def appRoleList=AppRole.findAllByAppId(params.appId)
        def roleIds=[]
        appRoleList?.each {
            roleIds.add(it.roleId)
        }
        if (roleIds.size()<1){
            roleIds.add('就不相信你存在')
        }
        //解决in中字符串不大于1000的问题
        def lst=StringUtil.splitIn(roleIds)

        //根据角色id获取角色信息
        def cel={}
        if ("1".equals(params.opFlag)){
            cel = {
				if(params.roleNameC){
					like("roleNameC","%${params.roleNameC}%")
				}
				if(params.roleCode){
					like("roleCode","%${params.roleCode}%")
				}
                lst?.each {id->
                        'in'('id',id)
                }
                order('showOrder','asc')
            }
        }else if ("0".equals(params.opFlag)){
            cel = {
				if(params.roleNameC){
					like("roleNameC","%${params.roleNameC}%")
				}
				if(params.roleCode){
					like("roleCode","%${params.roleCode}%")
				}
                lst?.each {id->
                    not{
                        'in'('id',id)
                    }
                }
                order('showOrder','asc')
            }
        }

        def roleList=Role.createCriteria().list(params,cel)

        def result = [rows: roleList, total: roleList.totalCount]
        render result as JSON
    }

    /**
     *@Description 弹出对话框
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-29 上午8:03:18
     */
    def dialog(){
        render (view:'/cn/com/wz/parent/system/appRole/selectRole',model:params)
    }

    /**
     * @Description 保存应用系统的角色信息
     * @params
     * @return
     * @create huxx 2013-05-13
     */
    def save(){
        def flag=true
       def roleIds=params.roleIds?.split("_")
        roleIds?.each{
            //判断是否已存在
            def old=AppRole.findByAppIdAndRoleId(params.appId,it)
            if (!old){
                def appRole=new AppRole(params)
                appRole.roleId=it
                try{
                    appRole.save(flust:true)
                }catch (DataIntegrityViolationException e){
                    flag = false
                }
            }
        }

        render flag
    }

    /**
     *@Description 使用ajax删除用户角色关系
     *@param params.roleId 选中角色的id；params.deleteIds要删除的用户ids
     *@return
     *@Auther huxx
     *@createTime 2012-9-23上午10:02:31
     */
    def jsonDelete() {
        def msg = message(code: 'default.deleted.simple.message')

        def arrayIds=params.deleteIds?.split('_')
        def flag=true
        def appId=params.appId
        for(roleId in arrayIds){
            //获取appRole
            def appRole=AppRole.findByAppIdAndRoleId(appId,roleId)
            //删除应用系统与角色的关联
            try{
                appRole.delete(flush: true)
            }catch(DataIntegrityViolationException e){
                flag=false
            }
        }

        if(!flag){
            msg = message(code: 'default.not.simple.deleted.message')
        }

        render msg
    }
}
