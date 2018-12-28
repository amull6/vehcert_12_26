package cn.com.wz.vehcert.zcinfo

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 18-1-23
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */
class GarbagePrintSet {
    String id
    String code
    String top   //上边距
    String left  //左边距
    String printerName  //打印机名称
    String location     //存放位置
    String max_garbage//打印的最大合格证号
    String max_garbage_upload//上传的最大合格证号
    String userid;//所属用户
    static constraints = {
        code(blank: true,nullable: true)
        top  (blank: true,nullable: true,matches: "[0-9.]+")
        left (blank: true,nullable: true,matches: "[0-9.]+")
        printerName  (blank: true,nullable: true)
        location (blank: true,nullable: true)
        max_garbage (blank: true,nullable: true)
        max_garbage_upload (blank: true,nullable: true)
        userid(blank: true,nullable: true)
    }
    static mapping = {
        table 'TBL_GARBAGE_PRINTSET'
        version false
        id generator:'uuid.hex', column:'id'
        top   (column:'topd',comment:'上边距')
        left   (column:'leftd',comment:'左边距')
        printerName  (comment:'打印机名称')
        location     (comment:'存放位置')
        max_garbage     (comment:'已打印的最大合格证,非超标车')
        max_garbage_upload     (comment:'垃圾站合格证上传最大值，非超标车')
        userid(comment:'所属用户')
    }
}
