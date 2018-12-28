package cn.com.wz.vehcert.zcinfo
/**
 * @Description 颜色
 * @create  2013-08-15 mike
 */
class Color {
    String id
    String name   //颜色名称
    static constraints = {
        name  (blank: false,nullable: false)
    }
    static mapping = {
        table 'TBL_COLOR'
        version false
        id generator:'uuid.hex', column:'id'
        name   (column:'name',comment:'颜色名称')
    }
}
