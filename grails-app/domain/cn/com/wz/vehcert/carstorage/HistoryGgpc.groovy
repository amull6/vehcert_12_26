package cn.com.wz.vehcert.carstorage
/**
 * @Description 每个车型的历史公告批次
 * @create  2018-07-05
 */
class HistoryGgpc {
    String id
    String veh_Clxh //公告信息/一致性信息 ID
    String veh_Ggpc //历史公告批次
    String create_Time //创建时间
    String veh_Cph  //产品号

    static constraints = {
        veh_Clxh (blank: true,nullable: true)
        veh_Ggpc (blank: true,nullable: true)
        create_Time (blank: true,nullable: true)
        veh_Cph (blank: true,nullable: true)
    }

    static mapping = {
        table 'HISTORY_GGPC'
        version false
        id generator:'uuid.hex', column:'id'
        veh_Clxh (comment:'车辆型号')
        veh_Ggpc (comment:'公告批次')
        create_Time(comment:"创建时间")
        veh_Cph (comment:"产品号")
    }
}
