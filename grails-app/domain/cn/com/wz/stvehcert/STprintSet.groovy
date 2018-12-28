package cn.com.wz.stvehcert


class STprintSet {

    String id
    String code
    String top   //上边距
    String left  //左边距
    String printerName  //打印机名称
    String location     //存放位置
    String max_tractor//打印的最大合格证号
    String max_tractor_upload//上传的最大合格证号
    String max_CBtractor //打印的小拖超标车的最大合格证
    String max_CBtractor_upload   //上传的小拖超标车的最大合格证
    String userid;//所属用户
    static constraints = {
        code(blank: true,nullable: true)
        top  (blank: true,nullable: true,matches: "[0-9.]+")
        left (blank: true,nullable: true,matches: "[0-9.]+")
        printerName  (blank: true,nullable: true)
        location (blank: true,nullable: true)
        max_tractor (blank: true,nullable: true)
        max_tractor_upload (blank: true,nullable: true)
        max_CBtractor(blank: true,nullable: true)
        max_CBtractor_upload(blank: true,nullable: true)
        userid(blank: true,nullable: true)
    }
    static mapping = {
        table 'TBL_ST_PRINTSET'
        version false
        id generator:'uuid.hex', column:'id'
        top   (column:'topd',comment:'上边距')
        left   (column:'leftd',comment:'左边距')
        printerName  (comment:'打印机名称')
        location     (comment:'存放位置')
        max_tractor     (comment:'已打印的最大合格证,非超标车')
        max_tractor_upload     (comment:'农装合格证上传最大值，非超标车')
        max_CBtractor     (comment:'打印的小拖超标车的最大合格证')
        max_CBtractor_upload     (comment:'上传的小拖超标车的最大合格证')
        userid(comment:'所属用户')
    }
}
