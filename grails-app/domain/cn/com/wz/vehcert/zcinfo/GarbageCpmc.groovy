package cn.com.wz.vehcert.zcinfo

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 18-1-23
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
class GarbageCpmc {
    String id
    String cpmc   //车型

    static constraints = {
        cpmc  (blank: false,nullable: false)
    }
    static mapping = {
        table 'TBL_GARBAGE_CPMC'
        version false
        id generator:'uuid.hex', column:'id'
        cpmc   (column:'name',comment:'专用车垃圾站车型')
    }
}
