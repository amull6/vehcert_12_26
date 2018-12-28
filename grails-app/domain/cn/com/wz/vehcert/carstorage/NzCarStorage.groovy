package cn.com.wz.vehcert.carstorage
/**
 * @Description 农装公告基础信息
 * @create 2013-07-23 huxx
 * @update 2013-07-28 huxx 修改车辆型号不可为空；添加字段配置序列号
 * @update 2018-01-30 QJ 添加发动机型号字段
 */
class NzCarStorage {
    String id
    String veh_Jxdl     //机械大类
    String veh_Rllx     //燃料类型
    String veh_Jxxlb    //机械小类别
    String veh_Zycs     //机械产品的主要参数
    String veh_Pfjd     //排放阶段
    String veh_Scqymc   //生产企业名称
    String veh_Pp       //品牌
    String veh_Lx       //类型
    String veh_Clxh     //车辆型号
    String veh_Fdjxh     //发动机型号
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
    String veh_Zxbz     //执行标准
    String veh_Scqydz   //生产企业地址
    String veh_Lxfs     //联系方式
    String veh_Lhqxs    //离合器型式
    String veh_Qpzxs    //前配重型式
    String veh_Hpzxs    //后配重型式
    String veh_Jzxs     //机罩型式
    String veh_Jssxs    //驾驶室型式
    String veh_Bhxs     //板簧型式
    String veh_Ddlj     //订单轮距

    String car_storage_type     //公告类型 0表示小拖，1表示中拖，2，大拖 ，3，玉米收 4，稲麦机5，青贮机
    String is_Exp= '0'         //是否是英文版出口车
    String createTime //创建时间
    String createrId //创建人id
    String updateTime   //最后更新时间
    String updaterId     //最后更新人
    String car_storage_no   //公告唯一号
    String turnOff = '0'  //停用标识   0：启用 1：停用
    String turnOffTime   //停用时间


    static constraints = {
        veh_Jxdl(blank: true, nullable: true)
        veh_Rllx(blank: true, nullable: true)
        veh_Jxxlb(blank: true, nullable: true)
        veh_Zycs(blank: true, nullable: true)
        veh_Pfjd(blank: true, nullable: true)
        veh_Clxh(blank: false, nullable: false)
        car_storage_type(blank: false, nullable: false)
        updateTime(blank: true, nullable: true)
        updaterId(blank: true, nullable: true)
        createTime(blank: true, nullable: true)
        car_storage_no(blank: true, nullable: true)
        turnOff(blank: true, nullable: true)
        turnOffTime(blank: true, nullable: true)
        veh_Fdjxh(blank: true, nullable: true)

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
        veh_Zxbz(blank: true, nullable: true)    //执行标准
        veh_Scqydz(blank: true, nullable: true)   //生产企业地址
        veh_Lxfs(blank: true, nullable: true)     //联系方式
        veh_Lhqxs(blank: true, nullable: true)   //离合器型式
        veh_Qpzxs(blank: true, nullable: true)  //前配重型式
        veh_Hpzxs(blank: true, nullable: true)  //后配重型式
        veh_Jzxs(blank: true, nullable: true)   //机罩型式
        veh_Ddlj(blank: true, nullable: true)  //订单轮距
        veh_Jssxs(blank: true, nullable: true)   //驾驶室型式
        veh_Bhxs(blank: true, nullable: true)    //板簧型式
        is_Exp(blank: true, nullable: true)    //是否是英文版出口车

    }

    static mapping = {
        table 'wzh_NZcarStorage'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator: 'uuid.hex', column: 'id'

        veh_Jxdl(comment: '机械大类')
        veh_Rllx(comment: '燃料类型')
        veh_Jxxlb(comment: '机械小类别')
        veh_Zycs(comment: '机械产品的主要参数')
        veh_Pfjd(comment: '排放阶段')
        veh_Clxh(comment: '车辆型号')
        car_storage_type(comment: "公告类型 0表示小拖，1表示中拖，2，大拖 ，3，超标车")
        createTime(comment: '创建时间')
        createrId(comment: '创建人id')
        updateTime(comment: '最后更新时间')
        updaterId(comment: '最后更新人')
        car_storage_no(comment: '公告唯一号')
        turnOff(comment: '停用表示，0表示启用；1表示停用')
        turnOffTime(comment: '公告停用时间')
        veh_Fdjxh(comment: '发动机型号')

        veh_Scqymc(comment: '生产企业名称')   //生产企业名称
        veh_Pp(comment: '品牌')       //品牌
        veh_Lx(comment: '类型')       //类型
        veh_Gl(comment: '功率')       //功率
        veh_Pfbz(comment: '排放标准及阶段')     //排放标准及阶段
        veh_Jsys(comment: '机身颜色')     //机身颜色
        veh_Zxczxs(comment: '转向操纵形式')   //转向操纵形式
        veh_Zcrs(comment: '准乘人数')     //准乘人数
        veh_Lzs(comment: '轮轴数')      //轮轴数
        veh_Zj(comment: '轴距')         //轴距
        veh_Lts(comment: '轮胎数')      //轮胎数
        veh_Ltgg(comment: '轮胎规格')     //轮胎规格
        veh_Qlj(comment: '前轮距')      //前轮距
        veh_Hlj(comment: '后轮距')      //后轮距
        veh_Lds(comment: '履带数')      //履带数
        veh_Ldgg(comment: '履带规格')     //履带规格
        veh_Gj(comment: '轨距')       //轨距
        veh_Wkc(comment: '外廓长')      //外廓长
        veh_Wkk(comment: '外廓宽')      //外廓宽
        veh_Wkg(comment: '外扩高')      //外扩高
        veh_Bdqyl(comment: '标定牵引力/割台宽度')    //标定牵引力
        veh_Zxsyzl(comment: '最小使用质量/喂入量')   //最小使用质量
        veh_Zdyyzzl(comment: '最大允许载质量/联合收割（获）机质量')  //最大允许载质量
        veh_Zxbz(comment: '执行标准')    //执行标准
        veh_Scqydz(comment: '生产企业地址')   //生产企业地址
        veh_Lxfs(comment: '联系方式')     //联系方式
        veh_Lhqxs(comment: '离合器型式')   //离合器型式
        veh_Qpzxs(comment: '前配重型式')  //前配重型式
        veh_Hpzxs(comment: '后配重型式')  //后配重型式
        veh_Jzxs(comment: '机罩型式')   //机罩型式
        veh_Jssxs(comment: '驾驶室型式')  //驾驶室型式
        veh_Bhxs(comment: '板簧型式')   //板簧型式
        veh_Ddlj(comment: '订单轮距')   //订单轮距
        is_Exp(comment: '是否是英文版出口车')   //是否是英文版出口车
        sort 'veh_Clxh': 'desc'
    }
}
