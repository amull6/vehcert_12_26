package cn.com.wz.vehcert.zcinfo
/**
 * @Description 经销商 合格证下载记录表
 * @create  2013-08-19 zouQ
 */
class ZCInfoTemp {
    String id
    String veh_Type ='0'  //汽车和农用车标示   0：汽车 1：农用车
    String veh_Zchgzbh  //整车合格证编号
    String veh_Dphgzbh  //底盘合格证编号
    String veh_Fzrq     //发证日期
    String veh_Clzzqymc //车辆制造企业名称
    String veh_Qyid     //企业ID
    String veh_Clfl     //车辆分类
    String veh_Clmc     //车辆名称
    String veh_Clpp     //车辆品牌
    String veh_Clxh    //车辆型号
    String veh_Csys     //车身颜色
    String veh_Dpxh     //底盘型号
    String veh_Dpid     //底盘ID
    String veh_Clsbdh    //车辆识别代号
    String veh_Cjh         //车架号
    String veh_Fdjh        //发动机号

    String veh_Fdjxh    //发动机型号
    String veh_Rlzl     //燃料种类
    String veh_Pl       //排量
    String veh_Gl       //功率
    String veh_zdjgl    //最大净功率
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
    String veh_Bgcazzdyxzzl //半挂车鞍座最大允许总质量
    String veh_Jsszcrs  //驾驶室准乘人数
    String veh_Qzdfs       //前制动方式
    String veh_Hzdfs    //后制动方式
    String veh_Qzdczfs     //前制动操作方式
    String veh_Hzdczfs     //后制动操作方式
    String veh_Cpggh    //产品公告号
    String veh_Ggsxrq   //公告生效日期
    String veh_Zzbh        //纸张编号
    String veh_Dywym   //打印唯一码
    String veh_Zgcs      //最高车速
    String veh_Clzzrq   //车辆制造日期
    String veh_Bz       //备注
    String veh_Qybz     //企业标准
    String veh_Cpscdz   //产品生产地址
    String veh_Qyqtxx   //企业其它信息
    String veh_Pfbz     //排放标准
    String veh_Clztxx   //车辆状态信息
    String veh_Jss      //驾驶室
    String veh_Jsslx    //驾驶室类型
    String veh_Zchgzbh_0  //完整合格证编号
    String used
    String used2
    String upload //
    String veh_Yh  //油耗
    String veh_VinFourBit //vin第四位
    String veh_Ggpc        //公告批次
    String veh_pzxlh                  //配置序列号
    String veh_clzzqyxx //车辆制造企业信息
    String veh_workshopno                  //车间编号

    String createTime //创建时间
    String createrId //创建人id
    String updateTime   //最后更新时间
    String updaterId     //最后更新人
    String uploader_Id //上传人
    String uploader_Time //上传时间
    String upload_Failddm //上传失败返回代码
    String upload_Path  //合格证上传相对路径


    String web_status='0' //合格证状态 是否上传    0:未上传 1：上传成功 2：上传失败
    String web_isprint='0' //合格证是否打印
    String web_virtualcode//虚拟合格证编号
    String vercode//校验信息


    String user_down;   ////下载人ID信息

    String insertTime;   ////记录插入时间
    String realName//用户真实姓名
    String type     //类型（0，表示下载 ；1表示打印）


    //合格证是否打印
    static constraints = {
        veh_Type  (blank: true,nullable: true)
        veh_Clzzqymc (blank: true,nullable: true)
        veh_Zchgzbh  (blank: true,nullable: true)
        veh_Dphgzbh (blank: true,nullable: true)
        veh_Fzrq    (blank: true,nullable: true)
        veh_Qyid     (blank: true,nullable: true)
        veh_Clfl     (blank: true,nullable: true)
        veh_Clmc     (blank: true,nullable: true)
        veh_Clpp     (blank: true,nullable: true)
        veh_Clxh      (blank: true,nullable: true)
        veh_Csys     (blank: true,nullable: true)
        veh_Dpxh     (blank: true,nullable: true)
        veh_Dpid     (blank: true,nullable: true)
        veh_Clsbdh   (blank: true,nullable: true)
        veh_Cjh      (blank: true,nullable: true)
        veh_Fdjh     (blank: true,nullable: true)
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
        veh_Qzdfs       (blank: true,nullable: true)
        veh_Hzdfs    (blank: true,nullable: true)
        veh_Qzdczfs   (blank: true,nullable: true)
        veh_Hzdczfs   (blank: true,nullable: true)
        veh_Cpggh    (blank: true,nullable: true)
        veh_Ggsxrq   (blank: true,nullable: true)
        veh_Zzbh    (blank: true,nullable: true)
        veh_Dywym  (maxSize:510,blank: true,nullable: true)
        veh_Zgcs     (blank: true,nullable: true)
        veh_Clzzrq   (blank: true,nullable: true)
        veh_Bz       (blank: true,nullable: true)
        veh_Qybz     (blank: true,nullable: true)
        veh_Cpscdz   (blank: true,nullable: true)
        veh_Qyqtxx   (blank: true,nullable: true)
        veh_Pfbz     (blank: true,nullable: true)
        veh_Clztxx   (blank: true,nullable: true)
        veh_Jss      (blank: true,nullable: true)
        veh_Jsslx    (blank: true,nullable: true)
        veh_Zchgzbh_0(blank: true,nullable: true)
        used   (blank: true,nullable: true)
        used2   (blank: true,nullable: true)
        upload (blank: true,nullable: true)
        veh_Yh    (blank: true,nullable: true)
        veh_Ggpc    (blank: true,nullable: true)
        veh_pzxlh    (blank: true,nullable: true)
        veh_VinFourBit(blank:true,nullable:true)
        updateTime   (blank: true,nullable: true)
        updaterId    (blank: true,nullable: true)
         uploader_Id (blank: true,nullable: true)
         uploader_Time (blank: true,nullable: true)
         upload_Failddm (blank: true,nullable: true)
        upload_Path(blank: true,nullable: true)
        web_status    (blank: true,nullable: true)
        web_isprint    (blank: true,nullable: true)
        web_virtualcode    (blank: true,nullable: true)
        vercode    (maxSize:510,blank: true,nullable: true)
        veh_clzzqyxx    (blank: true,nullable: true)
        veh_workshopno    (blank: true,nullable: true)
        user_down    (blank: true,nullable: true)
        insertTime(blank: true,nullable: true)
        realName(blank: true,nullable: true)
        type(blank: true,nullable: true)
    }
//    static hasMany=[zcinforeplaces:ZCInfoReplace]
    static mapping = {
        table 'WZH_ZCINFO_TEMP'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'
        veh_Type   (comment:'汽车和农用车标示 0汽车 1农用车')
        veh_Zchgzbh   (comment:'整车合格证编号')
        veh_Dphgzbh  (comment:'底盘合格证编号')
        veh_Fzrq     (comment:'发证日期')
        veh_Clzzqymc (comment:'车辆制造企业名称')
        veh_Qyid     (comment:'企业ID')
        veh_Clfl     (comment:'车辆分类')
        veh_Clmc     (comment:'车辆名称')
        veh_Clpp     (comment:'车辆品牌')
        veh_Clxh    (comment:'车辆型号')
        veh_Csys     (comment:'车身颜色')
        veh_Dpxh     (comment:'底盘型号')
        veh_Dpid     (comment:'底盘ID')
        veh_Clsbdh   (comment:'车辆识别代号')
        veh_Cjh     (comment:'车架号')
        veh_Fdjh    (comment:'发动机号')
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
        veh_Bgcazzdyxzzl (comment:'半挂车鞍座最大允许总质量')
        veh_Jsszcrs  (comment:'驾驶室准乘人数')
        veh_Qzdfs       (comment:'前制动方式')
        veh_Hzdfs    (comment:'后制动方式')
        veh_Qzdczfs     (comment:'前制动操作方式')
        veh_Hzdczfs    (comment:'后制动操作方式')
        veh_Cpggh    (comment:'产品公告号')
        veh_Ggsxrq   (comment:'公告生效日期')
        veh_Zzbh     (comment:'纸张编号')
        veh_Dywym     (comment:'打印唯一码')
        veh_Zgcs     (comment:'最高车速')
        veh_Clzzrq  (comment:'车辆制造日期')
        veh_Bz       (comment:'备注')
        veh_Qybz     (comment:'企业标准')
        veh_Cpscdz   (comment:'产品生产地址')
        veh_Qyqtxx   (comment:'企业其它信息')
        veh_Pfbz     (comment:'排放标准')
        veh_Clztxx   (comment:'车辆状态信息')
        veh_Jss      (comment:'驾驶室')
        veh_Jsslx    (comment:'驾驶室类型')
        veh_Zchgzbh_0 (comment:'完整合格证编号')
        veh_Ggpc (comment:'公告批次')
        used   (comment:'标识')
        used2  (comment:'标识')
        upload (comment:'标识')
        veh_VinFourBit(comment:"vin第四位")
        veh_Yh(comment:"油耗")
        createTime   (comment:'创建时间')
        createrId    (comment:'创建人id')
        updateTime   (comment:'最后更新时间')
        updaterId     (comment:'最后更新人')
        veh_pzxlh     (comment:'配置序列号')
        uploader_Id (comment:'上传人')
        uploader_Time (comment:'上传时间')
        upload_Failddm (comment:'上传失败返回代码')
        upload_Path(comment:'合格证PDF上传相对路径')

        web_status    (comment:'合格证状态 是否上传 0 未上传 1 上传成功 2 上传失败 3 需要修改或撤销的合格证')
        web_isprint    (comment:'是否打印 0 未打印 1 已打印')
        web_virtualcode    (comment:'虚拟合格证编号')
        vercode    (column:'VerCode',comment:'校验信息')
        veh_clzzqyxx(comment:'车辆制造企业信息')
        veh_workshopno(comment:'车间编号')
        user_down(comment:'下载人ID信息')
        insertTime(comment:'记录插入时间')
        realName(comment:'创建人的真实姓名')
        type(comment:'类型（0，表示下载 ；1表示打印）')

        sort 'insertTime': "desc"
    }

}
