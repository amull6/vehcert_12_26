package cn.com.wz.vehcert.environment
/**
 * @Description 重型柴油车/城市车辆环保信息业务表
 * @CreateTime 2016-12-21 by zhuwei
 */

class HeavyDieselInfo {
    String id         //保存对应合格证的id
    String en_xxgkbh  //信息公开号
//    车辆信息
    String en_clxh  //车辆型号
    String en_qcfl  //汽车分类
    String en_pfjd   //排放阶段
    String en_clsbff   //车辆识别方法和位置
    // 发动机信息
    String en_fdjxh    //发动机型号
    String en_zzs   //发动机制造商
    String en_xzmc  //发动机系族名称
    String en_sccdz  //发动机生产地址
    String en_cp         //发动机厂牌
    //检验信息
    String en_jcbz1  //    GB 17691-2005
    String en_jcbz2  //    GB 3847-2005
    String en_jcbz3  //     HJ 689-2014 城市车辆独有
    String en_jcbz4  //     HJ 437-2008
    String en_jcbz5  //      HJ 438-2008
    String en_jcbz6  //      GB 1495-2002
    String en_jcbz7  //      GB/T 27630-2011

    //污染控制技术信息
    String en_zzjgl  //最大净功率
    String en_zdjnj  //最大净扭矩
    String en_rlggxtxs  //燃料供给系统
    String en_pybxh   //喷油泵型号
    String en_pyqxh  //喷油器型号
    String en_zyqxh  //增压器型号
    String en_zlqxs   //中冷器形式
    String en_OBDxh  //OBD型号
    String en_EGRxh   //EGR型号
    String en_ECUxh  //ECU型号
    String en_pqhclxtxh  //排气后处理系统型号
    String en_tc      //封装/载体/涂层生产厂
    String en_tc1      //封装/载体/涂层生产厂
    String en_pqhclxtxs  //排气后处理系统形式
    String en_klqxh   //空滤器型号
    String en_jqxsqxh  //进气消声器型号
    String en_pqxsqxh  //排气消声器型号

    String en_clsbdh   //车辆识别代号
    String en_fdjh     //发动机号
    String en_zzrq     //制造日期
    String qr_name     //环保信息生成得二维防伪码图片名称
    String bar_name     //环保信息生成得二维码图车辆识别代号片名称

    /**
     /**
     * 上传状态 0未上传 1 已上传 2上传失败
     */
    int status=0 ;

    String type   //清单型号  ，0为重型柴油车 ，1为城市车辆
    String create_time //创建时间
    String creater_id //创建人id
    String update_time   //最后更新时间
    String updater_id     //最后更新人
    String en_bz          //备注


    static constraints = {

        en_xxgkbh (nullable: true)  //信息公开号，这个不能给设置上unqiue键
        //车辆信息
        en_clxh   (nullable: true)  //车辆型号
        en_qcfl   (nullable: true)  //汽车分类
        en_pfjd   (nullable: true)   //排放阶段
        en_clsbff (nullable: true)   //车辆识别方法和位置
        // 发动机信息
        en_fdjxh  (nullable: true)   //发动机型号
        en_zzs    (nullable: true) //发动机制造商
        en_xzmc   (nullable: true) //发动机系族名称
        en_sccdz  (nullable: true) //发动机生产地址
        en_cp     (nullable: true)    //发动机厂牌
        //检验信息
        en_jcbz1  (nullable: true) //    GB 17691-2005
        en_jcbz2  (nullable: true) //    GB 3847-2005
        en_jcbz3  (nullable: true) //     HJ 689-2014 城市车辆独有
        en_jcbz4  (nullable: true) //     HJ 437-2008
        en_jcbz5  (nullable: true) //      HJ 438-2008
        en_jcbz6  (nullable: true) //      GB 1495-2002
        en_jcbz7  (nullable: true) //      GB/T 27630-2011

        //污染控制技术信息
        en_zzjgl   (nullable: true) //最大净功率
        en_zdjnj   (nullable: true) //最大净扭矩
        en_rlggxtxs(nullable: true) //燃料供给系统
        en_pybxh   (nullable: true) //喷油泵型号
        en_pyqxh   (nullable: true)  //喷油器型号
        en_zyqxh   (nullable: true) //增压器型号
        en_zlqxs   (nullable: true) //中冷器形式
        en_OBDxh   (nullable: true) //OBD型号
        en_EGRxh   (nullable: true) //EGR型号
        en_ECUxh   (nullable: true) //ECU型号
        en_pqhclxtxh(nullable: true) //排气后处理系统型号
        en_tc      (nullable: true) //封装/载体/涂层生产厂
        en_tc1      (nullable: true) //封装/载体/涂层生产厂
        en_pqhclxtxs(nullable: true) //排气后处理系统形式
        en_klqxh    (nullable: true) //空滤器型号
        en_jqxsqxh  (nullable: true) //进气消声器型号
        en_pqxsqxh  (nullable: true) //排气消声器型号

        en_clsbdh   (nullable: true)//车辆识别代号
        en_fdjh     (nullable: true) //发动机号
        en_zzrq     (nullable: true) //制造日期
        qr_name     (nullable: true)//环保信息生成得二维防伪码图片名称
        bar_name     (nullable: true)//环保信息生成得二维码图车辆识别代号片名称

        status     (nullable:true)   //上传状态 0未上传 1 已上传 2上传失败

        type        (nullable: true) //清单型号  ，0为重型柴油车 ，1为城市车辆
        create_time  (nullable: true)//创建时间
        creater_id   (nullable: true)//创建人id
        update_time  (nullable: true)//最后更新时间
        updater_id   (nullable: true)  //最后更新人
        en_bz        (nullable: true)  //备注
    }

    static mapping = {
        table 'WZH_HEAVY_DIESEL_INFO'
        id generator:'assigned', column:'ID'
        en_xxgkbh (comment:'信息公开号')  //信息公开号
        //车辆信息
        en_clxh   (comment:'车辆型号')  //车辆型号
        en_qcfl   (comment:'汽车分类')  //汽车分类
        en_pfjd   (comment:'排放阶段')   //排放阶段
        en_clsbff (comment:'车辆识别方法和位置')   //车辆识别方法和位置
        //发动机信息
        en_fdjxh  (comment:'发动机型号')   //发动机型号
        en_zzs    (comment:'发动机制造商') //发动机制造商
        en_xzmc   (comment:'发动机系族名称') //发动机系族名称
        en_sccdz  (comment:'发动机生产地址') //发动机生产地址
        en_cp     (comment:'发动机厂牌')   //发动机厂牌
        //标准
        en_jcbz1  (comment:'GB 17691-2005') //    GB 17691-2005
        en_jcbz2  (comment:'GB 3847-2005') //    GB 3847-2005
        en_jcbz3  (comment:'HJ 689-2014 城市车辆独有') //     HJ 689-2014 城市车辆独有
        en_jcbz4  (comment:'HJ 437-2008') //     HJ 437-2008
        en_jcbz5  (comment:'HJ 438-2008') //      HJ 438-2008
        en_jcbz6  (comment:'GB 1495-2002') //      GB 1495-2002
        en_jcbz7  (comment:'GB/T 27630-2011') //      GB/T 27630-2011

        //污染控制技术信息
        en_zzjgl  (comment:'最大净功率') //最大净功率
        en_zdjnj  (comment:'最大净扭矩') //最大净扭矩
        en_rlggxtxs(comment:'燃料供给系统') //燃料供给系统
        en_pybxh   (comment:'喷油泵型号') //喷油泵型号
        en_pyqxh   (comment:'喷油器型号')  //喷油器型号
        en_zyqxh   (comment:'增压器型号') //增压器型号
        en_zlqxs   (comment:'中冷器形式') //中冷器形式
        en_OBDxh   (comment:'OBD型号') //OBD型号
        en_EGRxh   (comment:'EGR型号') //EGR型号
        en_ECUxh   (comment:'ECU型号') //ECU型号
        en_pqhclxtxh  (comment:'排气后处理系统型号') //排气后处理系统型号
        en_tc      (comment:'封装/载体/涂层生产厂') //封装/载体/涂层生产厂
        en_tc1      (comment:'封装/载体/涂层生产厂') //封装/载体/涂层生产厂
        en_pqhclxtxs  (comment:'排气后处理系统形式') //排气后处理系统形式
        en_klqxh      (comment:'空滤器型号') //空滤器型号
        en_jqxsqxh    (comment:'进气消声器型号') //进气消声器型号
        en_pqxsqxh    (comment:'排气消声器型号') //排气消声器型号

        en_clsbdh   (comment:'车辆识别代号')//车辆识别代号
        en_fdjh     (comment:'发动机号') //发动机号
        en_zzrq     (comment:'车辆生产（进口）日期') //制造日期
        qr_name     (comment:'环保信息生成得二维防伪码图片名称')//环保信息生成得二维防伪码图片名称
        bar_name    (comment:'环保信息生成得二维码图车辆识别代号片名称')//环保信息生成得二维码图车辆识别代号片名称

        status     (comment:'上传状态 0未上传 1 已上传 2上传失败')   //上传状态 0未上传 1 已上传 2上传失败

        type          (comment:'清单型号 ，0为重型柴油车 ，1为城市车辆') //清单型号 ，0为重型柴油车 ，1为城市车辆
        create_time  (comment:'创建时间')//创建时间
        creater_id   (comment:'创建人id')//创建人id
        update_time  (comment:'最后更新时间')//最后更新时间
        updater_id   (comment:'最后更新人')  //最后更新人
        en_bz        (comment:'备注')       //备注
    }
}
