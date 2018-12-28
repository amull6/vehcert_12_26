package cn.com.wz.vehcert.carstorage
/**
 * @Description 车辆公告基础信息
 * @create  2013-07-23 huxx
 * @update 2013-07-28 huxx 修改车辆型号不可为空；添加字段配置序列号
 */
class CarStorage {
    String id
    String veh_Clzzqymc //车辆制造企业名称
    String veh_Qyid     //企业ID
    String veh_Clfl     //车辆分类
    String veh_Clmc     //车辆名称
    String veh_Clpp     //车辆品牌
    String veh_Clxh     //车辆型号
    String veh_Csys     //车身颜色
    String veh_Dpxh     //底盘型号
    String veh_Dpid     //底盘ID
    String veh_Fdjxh    //发动机型号
    String veh_Rlzl     //燃料种类
    String veh_Pl       //排量
    String veh_Gl       //功率
    String veh_zdjgl    //最大净功率/转速
    String veh_Zxxs     //转向形式
    String veh_Qlj      //前轮距
    String veh_Hlj      //后轮距
    String veh_Lts      //轮胎数
    String veh_Ltgg     //轮胎规格
    String veh_Gbthps   //钢板弹簧片数
    String veh_Zj       //轴距
    String veh_Zh       //轴荷
    String veh_Zs       //轴数
    String veh_Wkc       //外廓长
    String veh_Wkk       //外廓宽
    String veh_Wkg       //外廓高
    String veh_Hxnbc     //货箱内部长
    String veh_Hxnbk     //货箱内部宽
    String veh_Hxnbg     //货箱内部高
    String veh_Zzl       //总质量
    String veh_Edzzl     //额定载质量
    String veh_Zbzl      //整备质量
    String veh_Zzllyxs   //载质量利用系数
    String veh_Zqyzzl   //准牵引总质量
    String veh_Edzk     //额定载客
    String veh_Bgcazzdyxzzl //半挂车按座
    String veh_Jsszcrs  //驾驶室准乘人数
    String veh_Yh       //油耗
    String veh_Yhlx     //油耗类型
    String veh_Hzdfs    //后制动方式
    String veh_Ggpc     //公告批次
    String veh_Cpggh    //产品公告号
    String veh_Ggsxrq   //公告生效日期
    String veh_Zgcs      //最高车速
    String veh_Bz       //备注
    String veh_Qybz     //企业标准
    String veh_Cpscdz   //产品生产地址
    String veh_Qyqtxx   //企业其它信息
    String veh_Pfbz     //排放标准
    String veh_Clztxx   //车辆状态信息
    String veh_Jss      //驾驶室
    String veh_Jsslx    //驾驶室类型

    String veh_cpid     //产品id
    String veh_jjlqj    //接近离去角
    String veh_qxhx     //前悬后悬
    String veh_dpqy     //底盘企业
    String veh_fgbsqy   //反光标示企业
    String veh_fgbssb   //反光标示商标
    String veh_fgbsxh   //反光标示型号
    String veh_y45pic   //右45度照片
    String veh_zhpic    //正后照片
    String veh_fhpic    //防护照片
    String veh_pic1     //照片1
    String veh_pic2     //照片2
    String veh_other    //其它
    String veh_VinFourBit //vin第四位
    String veh_pzxlh     //配置序列号
    String carStorageType     //公告类型 0表示汽车公告，1表示农用车公告
    String createTime //创建时间
    String createrId //创建人id
    String updateTime   //最后更新时间
    String updaterId     //最后更新人
    String car_storage_no   //公告唯一号

    String turnOff='0'  //停用标识   0：启用 1：停用
    String turnOffTime   //停用时间


    static constraints = {
        veh_Clzzqymc (blank: true,nullable: true)
        veh_Qyid     (blank: true,nullable: true)
        veh_Clfl     (blank: true,nullable: true)
        veh_Clmc     (blank: true,nullable: true)
        veh_Clpp     (blank: true,nullable: true)
        veh_Clxh     (blank: false,nullable: false)
        veh_Csys     (blank: true,nullable: true)
        veh_Dpxh     (blank: true,nullable: true)
        veh_Dpid     (blank: true,nullable: true)
        veh_Fdjxh    (blank: true,nullable: true)
        veh_Rlzl     (blank: true,nullable: true)
        veh_Pl       (blank: true,nullable: true)
        veh_Gl       (blank: true,nullable: true)
        veh_zdjgl    (blank: true,nullable: true)
        veh_Zxxs     (blank: true,nullable: true)
        veh_Qlj      (blank: true,nullable: true)
        veh_Hlj      (blank: true,nullable: true)
        veh_Lts      (blank: true,nullable: true)
        veh_Ltgg     (blank: true,nullable: true)
        veh_Gbthps   (blank: true,nullable: true)
        veh_Zj       (blank: true,nullable: true)
        veh_Zh       (blank: true,nullable: true)
        veh_Zs       (blank: true,nullable: true)
        veh_Wkc      (blank: true,nullable: true)
        veh_Wkk      (blank: true,nullable: true)
        veh_Wkg      (blank: true,nullable: true)
        veh_Hxnbc    (blank: true,nullable: true)
        veh_Hxnbk    (blank: true,nullable: true)
        veh_Hxnbg    (blank: true,nullable: true)
        veh_Zzl      (blank: true,nullable: true)
        veh_Edzzl    (blank: true,nullable: true)
        veh_Zbzl     (blank: true,nullable: true)
        veh_Zzllyxs  (blank: true,nullable: true)
        veh_Zqyzzl   (blank: true,nullable: true)
        veh_Edzk     (blank: true,nullable: true)
        veh_Bgcazzdyxzzl (blank: true,nullable: true)
        veh_Jsszcrs  (blank: true,nullable: true)
        veh_Yh       (blank: true,nullable: true)
        veh_Yhlx     (blank: true,nullable: true)
        veh_Hzdfs    (blank: true,nullable: true)
        veh_Ggpc     (blank: true,nullable: true)
        veh_Cpggh    (blank: true,nullable: true)
        veh_Ggsxrq   (blank: true,nullable: true)
        veh_Zgcs     (blank: true,nullable: true)
        veh_Bz       (blank: true,nullable: true)
        veh_Qybz     (blank: true,nullable: true)
        veh_Cpscdz   (blank: true,nullable: true)
        veh_Qyqtxx   (blank: true,nullable: true)
        veh_Pfbz     (blank: true,nullable: true)
        veh_Clztxx   (blank: true,nullable: true)
        veh_Jss      (blank: true,nullable: true)
        veh_Jsslx    (blank: true,nullable: true)
        veh_cpid     (blank: true,nullable: true)


        veh_jjlqj    (blank: true,nullable: true)
        veh_qxhx     (blank: true,nullable: true)
        veh_dpqy     (blank: true,nullable: true)
        veh_fgbsqy   (blank: true,nullable: true)
        veh_fgbssb   (blank: true,nullable: true)
        veh_fgbsxh   (blank: true,nullable: true)
        veh_y45pic   (blank: true,nullable: true)
        veh_zhpic    (blank: true,nullable: true)
        veh_fhpic    (blank: true,nullable: true)
        veh_pic1     (blank: true,nullable: true)
        veh_pic2     (blank: true,nullable: true)
        veh_other     (blank: true,nullable: true)
        veh_VinFourBit(blank:false,nullable:false)
        veh_pzxlh     (blank: true,nullable: true)

        carStorageType   (blank: false,nullable: false)
        updateTime   (blank: true,nullable: true)
        updaterId    (blank: true,nullable: true)

        createTime (blank: true,nullable: true)

        car_storage_no (blank: true,nullable: true)
        turnOff (blank: true,nullable: true)
        turnOffTime(blank: true,nullable: true)

    }

    static mapping = {
        table 'wzh_carStorage'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'

        veh_Clzzqymc (comment:'车辆制造企业名称')
        veh_Qyid     (comment:'企业ID')
        veh_Clfl     (comment:'车辆分类')
        veh_Clmc     (comment:'车辆名称')
        veh_Clpp     (comment:'车辆品牌')
        veh_Clxh     (comment:'车辆型号')
        veh_Csys     (comment:'车身颜色')
        veh_Dpxh     (comment:'底盘型号')
        veh_Dpid     (comment:'底盘ID')
        veh_Fdjxh    (comment:'发动机型号')
        veh_Rlzl     (comment:'燃料种类')
        veh_Pl       (comment:'排量')
        veh_Gl       (comment:'功率')
        veh_zdjgl    (comment:'最大净功率')
        veh_Zxxs     (comment:'转向形式')
        veh_Qlj      (comment:'前轮距')
        veh_Hlj      (comment:'后轮距')
        veh_Lts      (comment:'轮胎数')
        veh_Ltgg     (comment:'轮胎规格')
        veh_Gbthps   (comment:'钢板弹簧片数')
        veh_Zj       (comment:'轴距')
        veh_Zh       (comment:'轴荷')
        veh_Zs       (comment:'轴数')
        veh_Wkc      (comment:'外廓长')
        veh_Wkk      (comment:'外廓宽')
        veh_Wkg      (comment:'外廓高')
        veh_Hxnbc    (comment:'货箱内部长')
        veh_Hxnbk    (comment:'货箱内部宽')
        veh_Hxnbg    (comment:'货箱内部高')
        veh_Zzl      (comment:'总质量')
        veh_Edzzl    (comment:'额定载质量')
        veh_Zbzl     (comment:'整备质量')
        veh_Zzllyxs  (comment:'载质量利用系数')
        veh_Zqyzzl   (comment:'准牵引总质量')
        veh_Edzk     (comment:'额定载客')
        veh_Bgcazzdyxzzl (comment:'半挂车按座')
        veh_Jsszcrs  (comment:'驾驶室准乘人数')
        veh_Yh       (comment:'油耗')
        veh_Yhlx      (comment:'油耗类型')
        veh_Hzdfs    (comment:'后制动方式')
        veh_Ggpc     (comment:'公告批次')
        veh_Cpggh    (comment:'产品公告号')
        veh_Ggsxrq   (comment:'公告生效日期')
        veh_Zgcs     (comment:'最高车速')
        veh_Bz       (comment:'备注')
        veh_Qybz     (comment:'企业标准')
        veh_Cpscdz   (comment:'产品生产地址')
        veh_Qyqtxx   (comment:'企业其它信息')
        veh_Pfbz     (comment:'排放标准')
        veh_Clztxx   (comment:'车辆状态信息')
        veh_Jss      (comment:'驾驶室')
        veh_Jsslx    (comment:'驾驶室类型')

        veh_cpid     (comment:'产品id')
        veh_jjlqj    (comment:'接近离去角')
        veh_qxhx     (comment:'前悬后悬')
        veh_dpqy     (comment:'底盘企业')
        veh_fgbsqy   (comment:'反光标示企业')
        veh_fgbssb   (comment:'反光标示商标')
        veh_fgbsxh   (comment:'反光标示型号')
        veh_y45pic   (comment:'右45度照片')
        veh_zhpic    (comment:'正后照片')
        veh_fhpic    (comment:'防护照片')
        veh_pic1     (comment:'照片1')
        veh_pic2     (comment:'照片2')
        veh_other     (comment:'其它')
        veh_VinFourBit(comment:"vin第四位")
        veh_pzxlh     (comment:"配置序列号")

        carStorageType (comment("公告类型，0表示汽车公告，1表示农用车公告"))
        createTime   (comment:'创建时间')
        createrId    (comment:'创建人id')
        updateTime   (comment:'最后更新时间')
        updaterId     (comment:'最后更新人')
        car_storage_no (comment:'公告唯一号')
        turnOff (comment:'停用表示，0表示启用；1表示停用')
        turnOffTime(comment:'公告停用时间')
        sort 'veh_Clxh': 'desc'
    }
}
