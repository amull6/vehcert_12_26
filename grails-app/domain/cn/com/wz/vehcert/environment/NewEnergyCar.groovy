package cn.com.wz.vehcert.environment
/**
 * @Description 环保信息清单重型柴油车&城市车辆
 * @CreateTime 2016-12-20 by zhuwei
 */

class NewEnergyCar {
    String id
    String en_xxgkbh   //信息公开号
    String en_clxh     //车辆型号
    String en_qcfl     //汽车分类
    String en_cldsbffhwz     //车辆的识别方法和位置
    String en_jcbz1    //GB 1495-2002
    String en_ddjxh    //电动机型号/生产厂
    String en_zckzqxh  //整车控制器型号/版本号/生产厂
    String en_cnzzxh   //储能装置型号/生产厂
    String en_dcrl     //电池容量/续航里程
    String create_time //创建时间
    String creater_id //创建人id
    String update_time   //最后更新时间
    String updater_id     //最后更新人



    static constraints = {
        en_xxgkbh (nullable: true,unique: true)  //信息公开号
        en_clxh (nullable: true)    //车辆型号
        en_qcfl (nullable: true)     //汽车分类
        en_cldsbffhwz (nullable: true)    //车辆的识别方法和位置
        en_jcbz1 (nullable: true)    //GB 18352.3-2013
        en_ddjxh (nullable: true)    //GB 18285-2005
        en_zckzqxh (nullable: true)  //GB 1495-2002
        en_cnzzxh (nullable: true)   //GB/T 27630-2011
        en_dcrl (nullable: true)     //发动机型号
        create_time (nullable: true) //创建时间
        creater_id (nullable: true)  //创建人id
        update_time (nullable: true) //最后更新时间
        updater_id (nullable: true)  //最后更新人
    }
    static mapping = {
        table 'WZH_NEW_ENERGY_CAR'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'
        en_xxgkbh (comment:'信息公开号')  //信息公开号
        en_clxh (comment:'车辆型号')    //车辆型号
        en_qcfl (comment:'汽车分类')     //汽车分类
        en_cldsbffhwz (comment:'车辆的识别方法和位置')    //车辆的识别方法和位置
        en_jcbz1 (comment:'GB 1495-2002')  //GB 18352.3-2013
        en_ddjxh (comment:'电动机型号/生产厂')  //GB 18285-2005
        en_zckzqxh (comment:'整车控制器型号/版本号/生产厂')  //GB 1495-2002
        en_cnzzxh (comment:'储能装置型号/生产厂')  //GB/T 27630-2011
        en_dcrl (comment:'电池容量/续航里程')  //发动机型号
        create_time (comment:'创建时间') //创建时间
        creater_id (comment:'创建人id') //创建人id
        update_time (comment:'最后更新时间')   //最后更新时间
        updater_id (comment:'最后更新人')    //最后更新人
    }
}
