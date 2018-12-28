package cn.com.wz.parent.system.resourcemanage

import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.system.dictionary.DictionaryType
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.role.Role
import grails.converters.JSON
import cn.com.wz.parent.base.BaseController
import org.springframework.dao.DataIntegrityViolationException;
/**
 * @Description 系统资源与角色管理controller
 * @create 2013-08-12 huxx
 */
class ResourceRoleController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def dictionaryService

    def index() {
       render(view: '/cn/com/wz/parent/system/resourcerole/index2',model: [:])
    }

    /**
     * 构建完全系统资源树
     * @return
     * wangtao
     */
    def jsonResourceList(){
        //获取所有系统资源类字典项
        def resourceParent=DictionaryType.findByCode("dt_sysResMan")
        def resourceType=dictionaryService.getSubDictionaryType(resourceParent.id)
        render(contentType: "text/json") {
            array {
                for (m in resourceType) {
                    //status标示该节点是字典项还是字典值，只有字典值才会被获取并保存
                    def textLable = isEnglish()?m.typeNameE:m.typeNameC
                    if(m.parent){
                        menu(id: m.id,pid:m.parent?.id,text:textLable,status:'type',isExpand:0)
                    }else{
                        menu(id: m.id,pid:m.parent?.id,text:textLable,status:'type',expanded:true,isExpand:1)
                    }
                    //获取该字典项下所有字典值
                    def resourceValue=m.dictionaryValues
                    for (n in resourceValue){
                        def textValue = isEnglish()?n.dicValueNameE:n.dicValueNameC
                            menu(id: n.id,pid:m.id,text:textValue,status:'value',isExpand:0)

                    }
                }
            }
        }
    }


    /**
     * @Description 保存资源管理权限分配信息
     * @params
     * @return
     * @create wangtao 2013-11-20
     */
    def update(){
        def flag="保存成功！"
        def  resourceIds=[]
        if (params.resourceIds!=null&&params.resourceIds!=''){
            resourceIds=params.resourceIds?.split("_")
        }
        //删除角色原有资源
        def oldResource=ResourceRole.findAllByRoleId(params.roleId)
        oldResource?.each{
            it.delete(flush:true)
        }
        //保存角色新赋予的资源
        resourceIds?.each{
            def resourceRole=new ResourceRole(params)
            resourceRole.roleId=params.roleId
            resourceRole.resourceId=it
            if (!resourceRole.save(flust:true)){
                flag="保存失败"
            }
        }

        render flag
    }


    /**
     * @Description 根据角色id获取拥有此角色拥有的资源
     * @params
     * @create wangtao 2013-11-20
     */
    def selectOldResourceByRole (){
        //根据角色id获取角色下的所有资源信息
        def resource=ResourceRole.findAllByRoleId(params.roleId)
        //将菜单id组合成以'_'分隔的字符串
        def resourceIds=""
        resource.each {r->
            resourceIds+=r.resourceId+'_'
        }
        if(!"".equals(resourceIds)){
            resourceIds=resourceIds.substring(0, resourceIds.length()-1)
        }

        render resourceIds

    }


    /**
     * @Description 根据资源id获取拥有此资源的角色信息
     * @params appId 应用系统id； opFlag=0获取不具有此资源的角色信息，opFlag=1获取拥有此资源的角色信息
     * @create huxx 2013-08-13
     */
    def selectRolesByResource(){
        //设置分页信息
        params.max =params.limit ? params.int('limit') : 100
        params.offset = params.start ? params.int('start') :0

        //获取应用系统具有的角色的id
        def appRoleList=ResourceRole.findAllByResourceId(params.resourceId)
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
        render (view:'/cn/com/wz/parent/system/resourcerole/selectRole',model:params)
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
            def old=ResourceRole.findByResourceIdAndRoleId(params.resourceId,it)
            if (!old){
                def appRole=new ResourceRole(params)
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
        def resourceId=params.resourceId
        for(roleId in arrayIds){
            //获取appRole
            def appRole=ResourceRole.findByResourceIdAndRoleId(resourceId,roleId)
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
