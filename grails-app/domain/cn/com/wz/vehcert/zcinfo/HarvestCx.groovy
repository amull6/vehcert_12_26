package cn.com.wz.vehcert.zcinfo

class HarvestCx {
    String id
    String cx   //车型

    static constraints = {
        cx  (blank: false,nullable: false)
    }
    static mapping = {
        table 'TBL_HARVEST_CX'
        version false
        id generator:'uuid.hex', column:'id'
        cx   (column:'name',comment:'收获机车型')
    }
}
