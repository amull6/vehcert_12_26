package cn.com.wz.parent.appmanage
/**
 * @Description 管理应用系统拥有的资源角色
 * @Author huxx
 * @createTime 2013-05-13
 */
class AppRole {
    String id
    String appId
    String roleId

    static constraints = {
        appId(nullable:false)
        roleId(nullable:false)
    }
    /**
     *与数据库库数据映射（定义数据库数据存储名称）
     */
    static mapping = {
        table 'sys_appRole'
        id generator:'uuid.hex', column:'ID'
    }
}
