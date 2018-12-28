package cn.com.wz.vehcert.carstorage
/**
 * @Description 玉米收公告信息/一致性信息 临时表 desc：用来存储修改 删除后ID信息
 * @create  2017-08-10 QJ
 * 描述: 保证表中数据ID信息唯一性（公告、一致性信息）
 * @Update 2013-08-26 huxx 添加了插入时间,同步时只取同步时间小于插入时间的记录
 */
class HarvestTemp {
    String id
    String wzh_carstorageID  ///公告信息/一致性信息 ID
    int wzh_type   //// 1 公告信息
    String insertTime //插入时间
    String car_storage_no //公告的公告唯一号

    static constraints = {
        wzh_carstorageID (blank: true,nullable: true)
        wzh_type (blank: true,nullable: true)
        insertTime (blank: true,nullable: true)
        car_storage_no(blank: true,nullable: true)
    }

    static mapping = {
        table 'wzh_harvest_temp'
        version false
        id generator:'uuid.hex', column:'id'
        wzh_carstorageID (comment:'公告信息ID')
        wzh_type (comment:'类型 1 公告信息')
        insertTime(comment:"插入时间,同步时只取同步时间小于插入时间的记录")
        car_storage_no(comment:"删除公告时保存的公告唯一号")
    }
}
