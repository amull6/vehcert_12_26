package cn.com.wz.vehcert.zcinfo
/**
 * @Description 农装合格证系统的合格证信息Domain , 与汽车、农用车合格证完全独立
 * @CreateTime 2014-06-27
 * @Author zhuwei
 * @update 2018-01-30 QJ 添加发动机型号字段
 * @update 2018-01-30 QJ 添加新车辆编号字段和环保代码是相同字符串，老车辆编号看做是时间和流水码
 */

class NZInfo {
    String id
    String veh_Cx          //车型
    String veh_Clbh       //车辆编号
    String veh_Dph        //底盘号
    String veh_Fdjh       //发动机号
    String veh_workshopno //生产车间编号
    String veh_Jcy        //检查员
    String veh_Ccrq       //出厂日期
    String veh_Zxbz       //执行标准
    String veh_Hgzbh     //合格证编号
    String processType = '0' //生成类型 0正常生成；1出口车，仅仅对中拖i、大拖车间的生产产生影响

    String createTime   //创建时间
    String createrId    //创建ID

    String updateTime   //最后更新时间
    String updaterId     //最后更新人的userName

    String upload = '0'   //0:未上传 1：上传成功 2：上传失败 ；3已撤销（仅车间使用）
    String isprint = '0' //合格证是否打印，0为未打印，1为已打印

    String uploader_Id //上传人
    String uploader_Time //上传时间

    String SAP_No      // SAP中的车辆唯一号
    String SAP_status = '0'  //根据调用SAP返回的结果，显示传送状态：0未同步、1已同步，同步失败也是未同步
    String transDate       //传输到SAP的时间
    String envirCode     //环保信息代码
    String veh_Jxdl     //机械大类
    String veh_Rllx     //燃料类型
    String veh_Jxxlb    //机械小类别
    String veh_Zycs     //机械产品的主要参数
    String veh_Pfjd     //排放阶段
    String car_storage_type     //公告类型 0表示小拖，1表示中拖，2，大拖 ，3，玉米收 4，稲麦机5，青贮机
    String veh_Fdjxh    //发动机型号
    String veh_new_clbh //车辆编号

    String veh_Scqymc   //生产企业名称
    String veh_Pp       //品牌
    String veh_Lx       //类型
    String veh_Gl       //功率
    String veh_Pfbz     //排放标准及阶段
    String veh_Jsys     //机身颜色
    String veh_Zxczxs   //转向操纵形式
    String veh_Zcrs     //准乘人数
    String veh_Lzs      //轮轴数
    String veh_Zj       //轴距
    String veh_Lts      //轮胎数
    String veh_Ltgg     //轮胎规格
    String veh_Qlj      //前轮距
    String veh_Hlj      //后轮距
    String veh_Lds      //履带数
    String veh_Ldgg     //履带规格
    String veh_Gj       //轨距
    String veh_Wkc      //外廓长
    String veh_Wkk      //外廓宽
    String veh_Wkg      //外扩高
    String veh_Bdqyl    //标定牵引力/割台宽度
    String veh_Zxsyzl   //最小使用质量/喂入量
    String veh_Zdyyzzl  //最大允许载质量/联合收割（获）机质量
    String veh_Scqydz   //生产企业地址
    String veh_Lxfs     //联系方式
    String veh_Fzrq     //发证日期
    String qr_name      //二维码名称
    String bar_name     //条形码名称
    String veh_Lhqxs    //离合器型式
    String veh_Qpzxs    //前配重型式
    String veh_Hpzxs    //后配重型式
    String veh_Jzxs     //机罩型式
    String veh_Jssxs    //驾驶室型式
    String veh_Bhxs     //板簧型式
    String veh_Ddlj     //订单轮距
    String is_Exp        //是否是英文版出口车


    static constraints = {
        veh_Cx(blank: true, nullable: true)
        veh_Clbh(blank: true, nullable: true)
        veh_Fdjh(blank: true, nullable: true)
        veh_workshopno(nullable: true, blank: true)
        veh_Jcy(blank: true, nullable: true)
        veh_Ccrq(blank: true, nullable: true)
        veh_Zxbz(blank: true, nullable: true)
        veh_Hgzbh(blank: true, nullable: true)
        processType(blank: true, nullable: true)

        createTime(blank: true, nullable: true)
        createrId(blank: true, nullable: true)

        updateTime(blank: true, nullable: true)
        updaterId(blank: true, nullable: true)

        uploader_Id(blank: true, nullable: true)
        uploader_Time(blank: true, nullable: true)

        upload(blank: true, nullable: true)
        isprint(blank: true, nullable: true)

        SAP_No(blank: true, nullable: true)
        SAP_status(blank: true, nullable: true)
        transDate(blank: true, nullable: true)
        envirCode(blank: true, nullable: true)
        veh_Jxdl(blank: true, nullable: true)
        veh_Rllx(blank: true, nullable: true)
        veh_Jxxlb(blank: true, nullable: true)
        veh_Zycs(blank: true, nullable: true)
        veh_Pfjd(blank: true, nullable: true)
        car_storage_type(blank: false, nullable: false)
        veh_Fdjxh(blank: true, nullable: true)
        veh_new_clbh(blank: true, nullable: true)

        veh_Scqymc(blank: true, nullable: true)   //生产企业名称
        veh_Pp(blank: true, nullable: true)       //品牌
        veh_Lx(blank: true, nullable: true)       //类型
        veh_Gl(blank: true, nullable: true)       //功率
        veh_Pfbz(blank: true, nullable: true)     //排放标准及阶段
        veh_Jsys(blank: true, nullable: true)     //机身颜色
        veh_Zxczxs(blank: true, nullable: true)   //转向操纵形式
        veh_Zcrs(blank: true, nullable: true)     //准乘人数
        veh_Lzs(blank: true, nullable: true)      //轮轴数
        veh_Zj(blank: true, nullable: true)       //轴距
        veh_Lts(blank: true, nullable: true)      //轮胎数
        veh_Ltgg(blank: true, nullable: true)     //轮胎规格
        veh_Qlj(blank: true, nullable: true)      //前轮距
        veh_Hlj(blank: true, nullable: true)      //后轮距
        veh_Lds(blank: true, nullable: true)      //履带数
        veh_Ldgg(blank: true, nullable: true)     //履带规格
        veh_Gj(blank: true, nullable: true)       //轨距
        veh_Wkc(blank: true, nullable: true)      //外廓长
        veh_Wkk(blank: true, nullable: true)      //外廓宽
        veh_Wkg(blank: true, nullable: true)      //外扩高
        veh_Bdqyl(blank: true, nullable: true)    //标定牵引力
        veh_Zxsyzl(blank: true, nullable: true)   //最小使用质量
        veh_Zdyyzzl(blank: true, nullable: true)  //最大允许载质量
        veh_Scqydz(blank: true, nullable: true)   //生产企业地址
        veh_Lxfs(blank: true, nullable: true)     //联系方式
        veh_Fzrq(blank: true, nullable: true)
        qr_name(blank: true, nullable: true)
        bar_name(blank: true, nullable: true)
        veh_Lhqxs(blank: true, nullable: true)   //离合器型式
        veh_Qpzxs(blank: true, nullable: true)  //前配重型式
        veh_Hpzxs(blank: true, nullable: true)  //后配重型式
        veh_Jzxs(blank: true, nullable: true)   //机罩型式
        veh_Ddlj(blank: true, nullable: true)   //订单轮距
        veh_Jssxs(blank: true, nullable: true)   //驾驶室型式
        veh_Bhxs(blank: true, nullable: true)    //板簧型式
        veh_Dph(blank: true, nullable: true)     //底盘号
        is_Exp(blank: true, nullable: true)       //是否是英文版出口车

    }
    static mapping = {
        table 'WZH_NZ_ZCINFO'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator: 'uuid.hex', column: 'NZzcInfoId'
        veh_Cx(comment: '车型')
        veh_Clbh(comment: '底盘编号/机架号')
        veh_Fdjh(comment: '发动机号')
        veh_workshopno(comment: '检查员，先加上该字段，以备后用')
        veh_Jcy(comment: '检查员，先加上该字段，以备后用')
        veh_Ccrq(comment: '出厂日期，先加上该字段，以备后用')
        veh_Zxbz(comment: '执行标准，先加上该字段，以备后用')
        veh_Hgzbh(comment: '合格证编号，先加上该字段，以备后用')
        processType(comment: '生成类型 0正常生成；1出口车，仅仅对中拖i、大拖车间的生产产生影响')

        createTime(comment: '创建时间')
        createrId(comment: '创建ID')

        updateTime(comment: '更新时间')
        updaterId(comment: '更新ID')

        upload(comment: '0默认为为上传，1为已上传')
        isprint(comment: '合格证是否打印，0为未打印，1为已打印')

        uploader_Id(comment: '上传人')
        uploader_Time(comment: '上传时间')

        SAP_No(comment: 'SAP中的车辆唯一号 ')
        SAP_status(comment: '根据调用SAP返回的结果，显示传送状态：0未同步、1已同步，同步失败也是未同步 ')
        transDate(comment: '传输到SAP的时间 ')
        envirCode(comment: '环保信息代码 ')
        veh_Jxdl(comment: '机械大类')
        veh_Rllx(comment: '燃料类型')
        veh_Jxxlb(comment: '机械小类别')
        veh_Zycs(comment: '机械产品的主要参数')
        veh_Pfjd(comment: '排放阶段')
        car_storage_type(comment: "公告类型0表示小拖，1表示中拖，2，大拖 ，3，玉米收 4，稲麦机5，青贮机")
        veh_Fdjxh(comment: '发动机型号')
        veh_new_clbh(comment: '新车辆编号')

        veh_Scqymc(comment: '生产企业名称')   //生产企业名称
        veh_Pp(comment: '品牌')       //品牌
        veh_Lx(comment: '类型')       //类型
        veh_Gl(comment: '功率')       //功率
        veh_Pfbz(comment: '排放标准及阶段')     //排放标准及阶段
        veh_Jsys(comment: '机身颜色')     //机身颜色
        veh_Zxczxs(comment: '转向操纵形式')   //转向操纵形式
        veh_Zcrs(comment: '准乘人数')     //准乘人数
        veh_Lzs(comment: '轮轴数')      //轮轴数
        veh_Zj(comment: '轴距')         //准乘人数
        veh_Lts(comment: '轮胎数')      //轮轴数
        veh_Ltgg(comment: '轮胎规格')     //轮胎规格
        veh_Qlj(comment: '前轮距')      //前轮距
        veh_Hlj(comment: '后轮距')      //后轮距
        veh_Lds(comment: '履带数')      //履带数
        veh_Ldgg(comment: '履带规格')     //履带规格
        veh_Gj(comment: '轨距')       //轨距
        veh_Wkc(comment: '外廓长')      //外廓长
        veh_Wkk(comment: '外廓宽')      //外廓宽
        veh_Wkg(comment: '外扩高')      //外扩高
        veh_Bdqyl(comment: '标定牵引力/割台宽度')  //标定牵引力/割台宽度
        veh_Zxsyzl(comment: '最小使用质量/喂入量')   //最小使用质量/喂入量
        veh_Zdyyzzl(comment: '最大允许载质量/联合收割（获）机质量')  //最大允许载质量/联合收割（获）机质量
        veh_Scqydz(comment: '生产企业地址')   //生产企业地址
        veh_Lxfs(comment: '联系方式')     //联系方式
        veh_Fzrq(comment: '发证日期')
        qr_name(comment: '条形码名称')
        bar_name(comment: '二维码名称')
        veh_Lhqxs(comment: '离合器型式')   //离合器型式
        veh_Qpzxs(comment: '前配重型式')  //前配重型式
        veh_Hpzxs(comment: '后配重型式')  //后配重型式
        veh_Jzxs(comment: '机罩型式')   //机罩型式
        veh_Jssxs(comment: '驾驶室型式')  //驾驶室型式
        veh_Bhxs(comment: '板簧型式')   //板簧型式
        veh_Ddlj(comment: '订单轮距')   //订单轮距
        veh_Dph(comment: '底盘号')      //底盘号
        is_Exp(comment: '是否是英文版出口车') //是否是英文版出口车

        sort 'upload': 'asc'
        sort "veh_Clbh": 'desc'
    }
}
