package cn.com.wz.vehcert.zcinfo
/**
 * @Description 打印机设置
 * @create  2013-08-06 mike
 */
class PrintSet {
    String id
    String code
    String top   //上边距
    String left  //左边距
    String printerName  //打印机名称
    String location     //存放位置
    String maxQX_car;//汽车整车 -1表示未设置
    String maxQX_arg;//农用车整车  -1表示未设置
    String maxQX_car_upload;//汽车整车 -1表示未设置
    String maxQX_arg_upload;//农用车整车  -1表示未设置
    String userid;//所属用户
    static constraints = {
        code(blank: true,nullable: true)
        top  (blank: true,nullable: true,matches: "[0-9.]+")
        left (blank: true,nullable: true,matches: "[0-9.]+")
        printerName  (blank: true,nullable: true)
        location (blank: true,nullable: true)
        maxQX_car (blank: true,nullable: true)
        maxQX_arg (blank: true,nullable: true)
        maxQX_car_upload (blank: true,nullable: true)
        maxQX_arg_upload (blank: true,nullable: true)
        userid(blank: true,nullable: true)
    }
    static mapping = {
        table 'TBL_PRINTSET'
        version false
        id generator:'uuid.hex', column:'id'
        top   (column:'topd',comment:'上边距')
        left   (column:'leftd',comment:'左边距')
        printerName  (comment:'打印机名称')
        location     (comment:'存放位置')
        maxQX_car     (comment:'汽车整车')
        maxQX_arg     (comment:'农用车整车')
        maxQX_car_upload     (comment:'汽车整车 上传最大值')
        maxQX_arg_upload     (comment:'农用车整车 上传最大值')
        userid(comment:'所属用户')
    }
}
