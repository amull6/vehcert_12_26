package cn.com.wz.vehcert.zcinfo
/**
 * @Description 农装车型
 * @create  2014-08-18 zhuwei
 * @Description 用来保存农装的车型
 */

class NzCx {
    String id
    String cx   //车型

    static constraints = {
        cx  (blank: false,nullable: false)
    }
    static mapping = {
        table 'TBL_NZCX'
        version false
        id generator:'uuid.hex', column:'id'
        cx   (column:'name',comment:'农装车型')
    }
}
