package cn.com.wz.vehcert.carstorage
/**
 * @Description 玉米收获机械公告基础信息
 * @create  2013-07-23 huxx
 * @update 2013-07-28 huxx 修改车辆型号不可为空；添加字段配置序列号
 */
class HarvestCarStorage {
    String id
    String veh_Jxdl     //机械大类
    String veh_Rllx     //燃料类型
    String veh_Jxxlb    //机械小类别
    String veh_Zycs     //机械产品的主要参数
    String veh_Pfjd     //排放阶段
    String veh_Clxh     //车辆型号
    String createTime //创建时间
    String createrId //创建人id
    String updateTime   //最后更新时间
    String updaterId     //最后更新人
    String car_storage_no   //公告唯一号
    String car_storage_type   //公告类型 0表示收获机，1表示稻麦机
    String veh_Fdjxh     //发动机型号

    String turnOff='0'  //停用标识   0：启用 1：停用
    String turnOffTime   //停用时间


    static constraints = {
        veh_Jxdl     (blank: true,nullable: true)
        veh_Rllx     (blank: true,nullable: true)
        veh_Jxxlb    (blank: true,nullable: true)
        veh_Zycs     (blank: true,nullable: true)
        veh_Pfjd     (blank: true,nullable: true)
        veh_Clxh     (blank: false,nullable: false)

        updateTime   (blank: true,nullable: true)
        updaterId    (blank: true,nullable: true)

        createTime (blank: true,nullable: true)

        car_storage_no (blank: true,nullable: true)
        turnOff (blank: true,nullable: true)
        turnOffTime(blank: true,nullable: true)
        car_storage_type   (blank: false,nullable: false)
        veh_Fdjxh(blank: true, nullable: true)

    }

    static mapping = {
        table 'wzh_Harvest_carStorage'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'

        veh_Jxdl     (comment:'机械大类')
        veh_Rllx     (comment:'燃料类型')
        veh_Jxxlb    (comment:'机械小类别')
        veh_Zycs     (comment:'机械产品的主要参数')
        veh_Pfjd     (comment:'排放阶段')
        veh_Clxh     (comment:'车辆型号')

        createTime   (comment:'创建时间')
        createrId    (comment:'创建人id')
        updateTime   (comment:'最后更新时间')
        updaterId     (comment:'最后更新人')
        car_storage_no (comment:'公告唯一号')
        turnOff (comment:'停用表示，0表示启用；1表示停用')
        turnOffTime(comment:'公告停用时间')
        car_storage_type (comment:"公告类型 0表示玉米收，1表示稻麦机")
        veh_Fdjxh(comment: '发动机型号')
        sort 'veh_Clxh': 'desc'
    }
}
