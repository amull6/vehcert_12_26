package cn.com.wz.vehcert.zcinfo
/**
 * @Description 改制记录表
 * @create  2018-05-17 QJ
 * @Description 用来保存改制的车辆信息
 */

class Remanufacture {
    String id
    String VIN   //车辆识别代号
    String SAP_No //改制前序列号
    String SAP_No_r //改制后序列号
    String create_time //创建时间
    String create_id   //创建人

    static constraints = {
        VIN           (blank: false,nullable:false)
        SAP_No        (blank: false,nullable: true)
        SAP_No_r      (blank: false,nullable: true)
        create_time   (blank: true,nullable: true)
        create_id     (blank: true,nullable: true)
    }
    static mapping = {
        table 'REMANFACTURE'
        version false
        id generator:'uuid.hex', column:'id'
        VIN    (comment:'车辆识别代号')
        SAP_No   (comment:'改制前序列号')
        SAP_No_r     (comment:'改制后序列号')
        create_time     (comment:'创建时间')
        create_id     (comment:'创建人')
    }
}
