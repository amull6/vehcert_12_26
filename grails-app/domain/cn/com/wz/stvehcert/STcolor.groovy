package cn.com.wz.stvehcert

class STcolor {

    String id
    String name   //颜色名称
    static constraints = {
        name  (blank: false,nullable: false)
    }
    static mapping = {
        table("WZ_TBL_STCOLOR")
        version false
        id generator:'uuid.hex', column:'id'
        name   (column:'name',comment:'颜色名称')
    }
}
