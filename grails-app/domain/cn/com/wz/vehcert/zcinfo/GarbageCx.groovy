package cn.com.wz.vehcert.zcinfo

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 18-1-23
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
class GarbageCx {
    String id
    String cx   //车型

    static constraints = {
        cx  (blank: false,nullable: false)
    }
    static mapping = {
        table 'TBL_GARBAGE_CX'
        version false
        id generator:'uuid.hex', column:'id'
        cx   (column:'name',comment:'专用车垃圾站车型')
    }
}
