package cn.com.wz.vehcert.environment

/**
 * @Description 重型燃气车环保随车单信息表
 * @create  2016-12-20 Qj
 * @update
 */
class HeavyGasInfo {
    String id
    String en_xxgkbh  //信息公开号
    String en_clxh     //车辆型号
    String en_qcfl     //汽车分类
    String en_pfjd     //排放阶段
    String en_cldsbffhwz     //车辆的识别方法和位置
    String en_fdjxh     //发动机型号
    String en_fdjbh     //发动机编号
    String en_zzsmc     //制造商名称
    String en_xzmc     //系族名称
    String en_sccdz    //生产厂地址
    String en_cp       //厂牌
    String en_jcbz1   //GB 17691-2005
    String en_jcbz2   //HJ 437-2008
    String en_jcbz3   //HJ 438-2008
    String en_jcbz4   //GB 18285-2005
    String en_jcbz5   //GB 11340-2005
    String en_jcbz6   //GB 1495-2002
    String en_jcbz7   //GB/T 27630-2011
    String en_zdjgl   //最大净功率/转速
    String en_zdjnj      //最大净扭矩
    String en_rlgjxtxs     //燃料供给系统型式
    String en_ycgqxh   //氧传感器型号/生产厂
    String en_zfqhyltjqxh       //蒸发器或压力调节器型号/生产厂
    String en_hhzzxh       //混合装置型号/生产厂
    String en_psqxh       //喷射器型号/生产厂
    String en_egrxh       //EGR型号/生产厂
    String en_zyqxh       //增压器型号
    String en_zlqxs       //中冷器型式
    String en_ecuxh     //ECU型号/版本号/生产厂
    String en_obdxh     //OBD型号/生产厂
    String en_qzxwrwpfkzzzxh     //曲轴箱污染物排放控制装置型号/生产厂
    String en_kqlqqxh      //空气滤清器型号/生产厂
    String en_jqxsqxh     //进气消声器型号/生产厂
    String en_pqxsqxh     //排气消声器型号/生产厂
    String en_pqhclxtxs   //排气后处理系统型式/生产厂
    String en_pqhclxtxh   //排气后处理系统型号/生产厂
    String en_vin        //车辆识别代号
    String qr_name      //二维码文件名
    String bar_name    //条形码文件吗
    String create_time //创建时间
    String creater_id //创建人id
    String update_time   //最后更新时间
    String updater_id     //最后更新人
    /**
     /**
     * 上传状态 0未上传 1 已上传 2上传失败
     */
    int status=0 ;
    String en_zzrq;  //制造日期
    String en_tc        //封装/载体/涂层生产厂
    String en_bz          //备注


    static constraints = {
        en_xxgkbh (nullable: true) //信息公开号
        en_clxh  (nullable: true)   //车辆型号
        en_qcfl (nullable: true)    //汽车分类
        en_pfjd (nullable: true)     //排放阶段
        en_cldsbffhwz (nullable: true)    //车辆的识别方法和位置
        en_fdjxh (nullable: true)    //发动机型号
        en_fdjbh (nullable: true)    //发动机编号
        en_zzsmc (nullable: true)    //制造商名称
        en_xzmc  (nullable: true)     //系族名称
        en_sccdz (nullable: true)   //生产厂地址
        en_cp (nullable: true)       //厂牌
        en_jcbz1 (nullable: true)   //GB 17691-2005
        en_jcbz2 (nullable: true)  //HJ 437-2008
        en_jcbz3 (nullable: true)   //HJ 438-2008
        en_jcbz4 (nullable: true)  //GB 18285-2005
        en_jcbz5 (nullable: true) //GB 11340-2005
        en_jcbz6 (nullable: true)  //GB 1495-2002
        en_jcbz7 (nullable: true)  //GB/T 27630-2011
        en_zdjgl (nullable: true)   //最大净功率/转速
        en_zdjnj (nullable: true)     //最大净扭矩
        en_rlgjxtxs (nullable: true)    //燃料供给系统型式
        en_ycgqxh (nullable: true)  //氧传感器型号/生产厂
        en_zfqhyltjqxh (nullable: true)      //蒸发器或压力调节器型号/生产厂
        en_hhzzxh (nullable: true)      //混合装置型号/生产厂
        en_psqxh (nullable: true)      //喷射器型号/生产厂
        en_egrxh (nullable: true)       //EGR型号/生产厂
        en_zyqxh (nullable: true)      //增压器型号
        en_zlqxs  (nullable: true)     //中冷器型式
        en_ecuxh (nullable: true)    //ECU型号/版本号/生产厂
        en_obdxh (nullable: true)     //OBD型号/生产厂
        en_qzxwrwpfkzzzxh (nullable: true)   //曲轴箱污染物排放控制装置型号
        en_kqlqqxh (nullable: true)      //空气滤清器型号
        en_jqxsqxh (nullable: true)     //进气消声器型号
        en_pqxsqxh (nullable: true)    //排气消声器型号
        en_pqhclxtxs (nullable: true)
        en_pqhclxtxh (nullable: true)
        en_vin   (nullable: true)
        qr_name  (nullable:true)
        bar_name (nullable:true)
        creater_id    (nullable: true)
        update_time   (nullable: true)
        updater_id    (nullable: true)
        create_time   (nullable: true)
        status     (nullable:true)
        en_zzrq    (nullable:true)
        en_tc       (nullable: true) //封装/载体/涂层生产厂
        en_bz        (nullable: true)  //备注
    }
    static mapping = {
        table 'WZH_HEAVY_GAS_INFO'
        id generator:'assigned', column:'ID'
        en_xxgkbh (comment:'信息公开号') //信息公开号
        en_clxh (comment:'车辆型号')    //车辆型号
        en_qcfl   (comment:'汽车分类')  //汽车分类
        en_pfjd (comment:'排放阶段')    //排放阶段
        en_cldsbffhwz (comment:'车辆的识别方法和位置')    //车辆的识别方法和位置
        en_fdjxh (comment:'发动机型号')     //发动机型号
        en_fdjbh (comment:'发动机编号')     //发动机编号
        en_xzmc (comment:'系族名称')    //系族名称
        en_sccdz (comment:'生产厂地址')    //生产厂地址
        en_cp  (comment:'厂牌')     //厂牌
        en_jcbz1 (comment:'GB 17691-2005')  //GB 17691-2005
        en_jcbz2 (comment:'HJ 437-2008')   //HJ 437-2008
        en_jcbz3 (comment:'HJ 438-2008')  //HJ 438-2008
        en_jcbz4 (comment:'GB 18285-2005')   //GB 18285-2005
        en_jcbz5 (comment:'GB 11340-2005')  //GB 11340-2005
        en_jcbz6 (comment:'GB 1495-2002')  //GB 1495-2002
        en_jcbz7 (comment:'GB/T 27630-2011')  //GB/T 27630-2011
        en_zdjgl (comment:'最大净功率/转速')   //最大净功率/转速
        en_zdjnj (comment:'燃料供给系统型式')      //最大净扭矩
        en_rlgjxtxs (comment:'燃料供给系统型式')     //燃料供给系统型式
        en_ycgqxh (comment:'氧传感器型号/生产厂')  //氧传感器型号/生产厂
        en_zfqhyltjqxh (comment:'蒸发器或压力调节器型号/生产厂')      //蒸发器或压力调节器型号/生产厂
        en_hhzzxh (comment:'混合装置型号/生产厂')      //混合装置型号/生产厂
        en_psqxh  (comment:'喷射器型号/生产厂')     //喷射器型号/生产厂
        en_egrxh (comment:'EGR型号/生产厂')      //EGR型号/生产厂
        en_zyqxh  (comment:'增压器型号')      //增压器型号
        en_zlqxs  (comment:'中冷器型式')      //中冷器型式
        en_ecuxh  (comment:'ECU型号/版本号/生产厂')    //ECU型号/版本号/生产厂
        en_obdxh  (comment:'OBD型号/生产厂')   //OBD型号/生产厂
        en_qzxwrwpfkzzzxh (comment:'曲轴箱污染物排放控制装置型号/生产厂')    //曲轴箱污染物排放控制装置型号/生产厂
        en_kqlqqxh (comment:'空气滤清器型号/生产厂')     //空气滤清器型号/生产厂
        en_jqxsqxh  (comment:'进气消声器型号/生产厂')   //进气消声器型号/生产厂
        en_pqxsqxh (comment:'排气消声器型号/生产厂')    //排气消声器型号/生产厂
        en_pqhclxtxs (comment:'排气后处理系统型式/生产厂')  //排气后处理系统型式/生产厂
        en_pqhclxtxh (comment:'排气后处理系统型号/生产厂')  //排气后处理系统型号/生产厂
        en_vin (comment:'车辆识别代号')  //车辆识别代号
        qr_name  (comment:'二维码文件名')
        bar_name (comment:'条形码文件名')
        create_time   (comment:'创建时间')
        creater_id    (comment:'创建人id')
        update_time   (comment:'最后更新时间')
        updater_id     (comment:'最后更新人')
        status     (comment:'状态 ：0未上传 1 已上传 2上传失败')
        en_zzrq    (comment:'制造日期')
        en_tc       (comment:'封装/载体/涂层生产厂') //封装/载体/涂层生产厂
        en_bz        (comment:'备注')       //备注
    }
}
