package cn.com.wz.parent.system.resourcemanage

/**
 * @Description:系统资源与角色管理domain     系统资源在字典中维护字典分类dt_sysResMan下的所有字典值为系统资源
 * @Create: 13-8-12 下午2:15   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class ResourceRole {
    String id
    String resourceId
    String roleId

    static  mapping = {
        table 'sys_resourceRole'
        id generator:'uuid.hex', column:'ID'
        resourceId (comment('资源id'))
        roleId  (comment('角色id'))
    }
}
