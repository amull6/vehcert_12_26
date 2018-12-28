package cn.com.wz.stvehcert
/**
 * @Desciption  山拖车辆信息表
 * @CreateTime 2016-06-13 by zhuwei
 */

class TractorModle {
    String id;
    String modle;//车型编码
    String modleName//车型名称
    String createid     //创建人
    String createTime   //创建时间
    String lastUpdateId   //最后更新人
    String lastUpdateTime  //最后更新时间
    String veh_Jxdl     //机械大类
    String veh_Rllx     //燃料类型
    String veh_Jxxlb    //机械小类别
    String veh_Zycs     //机械产品的主要参数
    String veh_Pfjd     //排放阶段
    String car_storage_no   //公告唯一号
    String veh_Fdjxh     //发动机型号
    String modle_type     //类型 0山拖公告 1农装公告


    static constraints = {
        modle(nullable: false,blank: false)
        modleName(nullable: true)
        createid (nullable: true)
        createTime  (nullable: true)
        lastUpdateId   (nullable: true)
        lastUpdateTime  (nullable: true)
        veh_Jxdl     (blank: true,nullable: true)
        veh_Rllx     (blank: true,nullable: true)
        veh_Jxxlb    (blank: true,nullable: true)
        veh_Zycs     (blank: true,nullable: true)
        veh_Pfjd     (blank: true,nullable: true)
        car_storage_no (blank: true,nullable: true)
        veh_Fdjxh(blank: true, nullable: true)
        modle_type(blank: true, nullable: true)
    }
    static mapping = {
        table 'WZH_TRACTOR_MODLE'
        version false
        id generator:'uuid.hex', column:'id'
        modle comment("车型编码")
        modleName comment("车型名称")
        createid comment('创建人')
        createTime comment('创建时间')
        lastUpdateId comment('最后更新人')
        lastUpdateTime comment('最后更新时间')
        veh_Jxdl     (comment:'机械大类')
        veh_Rllx     (comment:'燃料类型')
        veh_Jxxlb    (comment:'机械小类别')
        veh_Zycs     (comment:'机械产品的主要参数')
        veh_Pfjd     (comment:'排放阶段')
        car_storage_no (comment:'公告唯一号')
        veh_Fdjxh(comment: '发动机型号')
        modle_type(comment: '类型 0山拖公告 1农装公告')
    }
}
