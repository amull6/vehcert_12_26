package cn.com.wz.vehcert.environment

/**
 * @Description 国六轻型汽油随车单信息表导入表
 * @create  2016-12-20 Qj
 * @update
 */
class LightPetrolGb6 {
    String id
    String en_xxgkbh   //信息公开号
    String en_clxh     //车辆型号
    String en_qcfl     //汽车分类
    String en_pfjd     //排放阶段
    String en_cldsbffhwz     //车辆的识别方法和位置
    String en_jcbz1   //GB 18352.6-2016
    String en_jcbz2   //GB 18285-2005
    String en_jcbz3   //GB 1495-2002
    String en_jcbz4   //27630 预留
    String en_jcbz5   //8702  预留
    String en_fdjxh   //发动机型号
    String en_fdjscqy //发动机生产企业
    String en_fdjcp   //厂牌
    String en_chzhqxh //催化转化器型号
    String en_klbjqxh //颗粒捕集器型号
    String en_tc     //涂层/载体/封装生产厂
    String en_tc1     //涂层/载体/封装生产厂1
    String en_tgxh   //碳罐型号
    String en_ycgqxh       //氧传感器型号
    String en_qzxpfkzzzxh      //曲轴箱排放控制装置型号
    String en_egrxh       //EGR型号
    String en_obdxh       //OBD型号
    String en_ecuxh       //ECU型号
    String en_bsqxs   //变速器型式
    String en_xyqxh   //消音器型号
    String en_zyqxh   //增压器型号
    String en_zlqxs   //中冷器型式
    String create_time //创建时间
    String creater_id //创建人id
    String update_time   //最后更新时间
    String updater_id     //最后更新人

    static constraints = {
        en_xxgkbh (nullable: true,unique: true)  //信息公开号
        en_clxh (nullable: true)    //车辆型号
        en_qcfl (nullable: true)     //汽车分类
        en_pfjd (nullable: true)    //排放阶段
        en_cldsbffhwz (nullable: true)    //车辆的识别方法和位置
        en_jcbz1 (nullable: true)   //GB 18352.3-2013
        en_jcbz2 (nullable: true)  //GB 18285-2005
        en_jcbz3 (nullable: true)  //GB 1495-2002
        en_jcbz4 (nullable: true)  //GB/T 27630-2011
        en_fdjxh (nullable: true)  //发动机型号
        en_fdjscqy (nullable: true)  //发动机生产企业
        en_fdjcp (nullable: true)  //厂牌
        en_chzhqxh (nullable: true)//催化转化器型号
        en_tc (nullable: true)    //涂层/载体/封装生产厂
        en_tc1 (nullable: true)    //涂层/载体/封装生产厂1
        en_klbjqxh (nullable: true)  //颗粒捕集器型号
        en_ycgqxh (nullable: true)      //氧传感器型号
        en_qzxpfkzzzxh (nullable: true)    //曲轴箱排放控制装置型号
        en_egrxh (nullable: true)      //EGR型号
        en_obdxh (nullable: true)      //OBD型号
        en_tgxh (nullable: true)      //碳罐型号
        en_ecuxh (nullable: true)       //ECU型号
        en_bsqxs (nullable: true)   //变速器型式
        en_xyqxh (nullable: true)  //消音器型号
        en_zyqxh (nullable: true) //增压器型号
        en_zlqxs (nullable: true)   //中冷器型式
        create_time (nullable: true)//创建时间
        creater_id (nullable: true)//创建人id
        update_time (nullable: true)   //最后更新时间
        updater_id (nullable: true)    //最后更新人
    }
    static mapping = {
        table 'WZH_LIGHT_PETROL_GB6'
        version false
        id generator:'uuid.hex', column:'ID'
        en_xxgkbh (comment:'信息公开号')  //信息公开号
        en_clxh (comment:'车辆型号')    //车辆型号
        en_qcfl (comment:'汽车分类')     //汽车分类
        en_pfjd (comment:'排放阶段')   //排放阶段
        en_cldsbffhwz (comment:'车辆的识别方法和位置')    //车辆的识别方法和位置
        en_jcbz1 (comment:'GB 18352.3-2013')  //GB 18352.3-2013
        en_jcbz2 (comment:'GB 18285-2005')  //GB 18285-2005
        en_jcbz3 (comment:'GB 1495-2002')  //GB 1495-2002
        en_jcbz4 (comment:'GB/T 27630-2011')  //GB/T 27630-2011
        en_fdjxh (comment:'发动机型号')  //发动机型号
        en_fdjscqy (comment:'发动机生产企业')  //发动机生产企业
        en_fdjcp (comment:'厂牌')  //厂牌
        en_chzhqxh (comment:'催化转化器型号') //催化转化器型号
        en_tc (comment:'涂层/载体/封装生产厂')    //涂层/载体/封装生产厂
        en_tc1 (comment:'涂层/载体/封装生产厂1')    //涂层/载体/封装生产厂1
        en_klbjqxh (comment:'颗粒捕集器型号')  //颗粒捕集器型号
        en_ycgqxh (comment:'氧传感器型号')      //氧传感器型号
        en_qzxpfkzzzxh (comment:'曲轴箱排放控制装置型号')    //曲轴箱排放控制装置型号
        en_egrxh (comment:'EGR型号')      //EGR型号
        en_obdxh (comment:'OBD型号')      //OBD型号
        en_tgxh (comment:'碳罐型号')      //碳罐型号
        en_ecuxh (comment:'ECU型号')       //ECU型号
        en_bsqxs (comment:'变速器型式')   //变速器型式
        en_xyqxh (comment:'消音器型号')  //消音器型号
        en_zyqxh (comment:'增压器型号') //增压器型号
        en_zlqxs (comment:'中冷器型式')   //中冷器型式
        create_time (comment:'创建时间') //创建时间
        creater_id (comment:'创建人id') //创建人id
        update_time (comment:'最后更新时间')   //最后更新时间
        updater_id (comment:'最后更新人')    //最后更新人
    }
}