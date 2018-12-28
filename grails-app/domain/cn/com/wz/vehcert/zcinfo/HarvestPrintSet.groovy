package cn.com.wz.vehcert.zcinfo

class HarvestPrintSet {

    String id
    String code
    String top   //上边距
    String left  //左边距
    String printerName  //打印机名称
    String location     //存放位置
    String max_harvester//打印的最大合格证号
    String max_harvester_upload//上传的最大合格证号
    String max_CB_harvester
    String max_CB_harvester_upload
    String userid;//所属用户

    static constraints = {
        code(blank: true,nullable: true)
        top  (blank: true,nullable: true,matches: "[0-9.]+")
        left (blank: true,nullable: true,matches: "[0-9.]+")
        printerName  (blank: true,nullable: true)
        location (blank: true,nullable: true)
        max_harvester (blank: true,nullable: true)
        max_harvester_upload (blank: true,nullable: true)
        max_CB_harvester (blank: true,nullable: true)
        max_CB_harvester_upload (blank: true,nullable: true)
        userid(blank: true,nullable: true)
    }
    static mapping = {
        table 'TBL_HARVESTER_PRINTSET'
        version false
        id generator:'uuid.hex', column:'id'
        top   (column:'topd',comment:'上边距')
        left   (column:'leftd',comment:'左边距')
        printerName  (comment:'打印机名称')
        location     (comment:'存放位置')
        max_harvester (comment:'收获机打印的最大合格证')
        max_CB_harvester     (comment:'非正常生产最大合格证编号')
        max_harvester_upload     (comment:'收获机 上传最大值')
        max_CB_harvester_upload     (comment:'非正常生产上传最大值')
        userid(comment:'所属用户')
    }
}
