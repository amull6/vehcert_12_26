package cn.com.wz.vehcert.zcinfo
/**
 * @Description 合格证替换申请信息
 * @create 2013-8-2 14:14:00 xu
 * @Update 2015-12-11 增加字段transToCrm表示是否同步到CRM       zhuwei
 * Update 2017-07-26 增加字段SAP_NO表示SAP唯一号 by zhuwei
 * Update 2018-02-01 增加字段formal 表示变换类型
 */
class ReplaceForSupplement {
    String id
    String change_Field = "def"    //更换申请变更字段    desc : "def"标识在默认下的类型 此类型不允许为空  当是日期变更时：“veh_Fzrq”
    int veh_Need_Cer = 0    //需要合格证类型 0：只要整车合格证 1：只要底盘合格证  2：整车和底盘合格证

    //原合格证信息
    String veh_Type   //汽车和农用车标示
    String veh_Xnhgzbh //虚拟合格证编号
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
    String veh_Hzdczfs    //后制动操作方式
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
    String vercode //二维码
    String veh_Yh  //油耗
    String veh_VinFourBit //vin第四位
    String veh_Ggpc        //公告批次
    String veh_createTime //创建时间
    String veh_createrId //创建人id
    String veh_pzxlh                  //配置序列号
    String upload_Path  //合格证上传相对路径
    String veh_clzzqyxx//车辆制造企业信息
    String SAP_No      // SAP中的车辆唯一号【更换时保存上SAP序列号】

    //更换后的合格证信息
    String veh_Type_R    //汽车和农用车标示
    String veh_Xnhgzbh_R //虚拟合格证编号
    String veh_Zchgzbh_R  //整车合格证编号
    String veh_Dphgzbh_R  //底盘合格证编号
    String veh_Fzrq_R     //发证日期
    String veh_Clzzqymc_R //车辆制造企业名称
    String veh_Qyid_R     //企业ID
    String veh_Clfl_R     //车辆分类
    String veh_Clmc_R     //车辆名称
    String veh_Clpp_R     //车辆品牌
    String veh_Clxh_R    //车辆型号
    String veh_Csys_R     //车身颜色
    String veh_Dpxh_R     //底盘型号
    String veh_Dpid_R     //底盘ID
    String veh_Clsbdh_R    //车辆识别代号
    String veh_Cjh_R         //车架号
    String veh_Fdjh_R        //发动机号

    String veh_Fdjxh_R    //发动机型号
    String veh_Rlzl_R     //燃料种类
    String veh_Pl_R       //排量
    String veh_Gl_R       //功率
    String veh_zdjgl_R    //最大净功率
    String veh_Zxxs_R     //转向形式
    String veh_Qlj_R      //前轮距
    String veh_Hlj_R      //后轮距
    String veh_Lts_R      //轮胎数
    String veh_Ltgg_R     //轮胎规格
    String veh_Gbthps_R   //钢板弹簧片数
    String veh_Zj_R       //轴距
    String veh_Zh_R       //轴荷
    String veh_Zs_R       //轴数
    String veh_Wkc_R       //外廓长
    String veh_Wkk_R       //外廓宽
    String veh_Wkg_R       //外廓高
    String veh_Hxnbc_R     //货箱内部长
    String veh_Hxnbk_R     //货箱内部宽
    String veh_Hxnbg_R     //货箱内部高
    String veh_Zzl_R       //总质量
    String veh_Edzzl_R     //额定载质量
    String veh_Zbzl_R      //整备质量
    String veh_Zzllyxs_R   //载质量利用系数
    String veh_Zqyzzl_R   //准牵引总质量
    String veh_Edzk_R     //额定载客
    String veh_Bgcazzdyxzzl_R //半挂车按座
    String veh_Jsszcrs_R  //驾驶室准乘人数
    String veh_Qzdfs_R       //前制动方式
    String veh_Hzdfs_R    //后制动方式
    String veh_Qzdczfs_R     //前制动操作方式
    String veh_Hzdczfs_R    //后制动操作方式
    String veh_Cpggh_R    //产品公告号
    String veh_Ggsxrq_R   //公告生效日期
    String veh_Zzbh_R        //纸张编号
    String veh_Dywym_R   //打印唯一码
    String veh_Zgcs_R      //最高车速
    String veh_Clzzrq_R   //车辆制造日期
    String veh_Bz_R       //备注
    String veh_Qybz_R     //企业标准
    String veh_Cpscdz_R   //产品生产地址
    String veh_Qyqtxx_R   //企业其它信息
    String veh_Pfbz_R     //排放标准
    String veh_Clztxx_R   //车辆状态信息
    String veh_Jss_R      //驾驶室
    String veh_Jsslx_R    //驾驶室类型
    String veh_Zchgzbh_0_R  //完整合格证编号
    String used_r
    String used2_r
    String upload_r //
    String vercode_r //二维码
    String veh_Yh_R  //油耗
    String veh_VinFourBit_R //vin第四位
    String veh_Ggpc_R       //公告批次
    String veh_pzxlh_R                   //配置序列号
    String upload_Path_R   //合格证上传相对路径
    String veh_clzzqyxx_R //车辆制造企业信息

    int veh_usertype = 0   //合格证修改记录作者类型  0：经销商修改、更换 1：公告资源部修改(只是针对已经上传到国家的合格证修改 才会有修改记录)
    int veh_isupload = 0          //原合格证信息是否已经上传到国家系统  0：未上传到国家 1：已上传到国家
    int veh_managestatus = 0      //针对已经上传到国家的记录进行修改  由公告资源部确认处理情况  0：待处理 1：处理完毕
    int veh_coc_status = 0    //审核状态 0：待审核 1审核通过 2审核失败
    int veh_QX_status = 0  //整车审核状态
    int veh_DP_status = 0  //底盘审核状态
    String createTime //申请时间
    String createrId //申请人id
    String salesmanname //业务员姓名
    String salesmantel //业务员电话
    String authTime   //审核时间 （处理人）
    String authId     //审核人   （处理时间）
    String remark  //申请备注
    String auth_Remark //审核备注
    String createName   //申请人姓名
    int statusReco = 0//0为未回收 1为已回收

    //新加二次更换的字段
    String area_AuthId //区域经理审核ID
    int area_status = 3  //区域经理审核，0为审核  1审核通过  2审核不通过，初始值为3，只有在二次更换时才将该值置为0
    String area_AuthTime     //区域经理审核时间
    String product_auth_Id   //产品管理部审核id
    String product_authTime  //产品管理部审核时间
    int product_auth_status = 3  // 产品管理部审核标记  0未审核 1审核通过 2审核未通过
    String product_auth_Remark  //产品管理部审核备注
    String area_Remark      //区域经理备注
    String createrOrgan //申请人所属区域，需要区域经理审批
    String replaceType = '0' //审批类型，0为正常更换  1二次更换特殊审批
    String formal = '0'  //0表示选公告换配置更换  1表示A4合格证换正式合格证

    int transToCrm = 0   //0表示要传给CRM，1禁止传给CRM  ，2传给CRM成功，3传给CRM失败
    String zcinfoReplaceId
    static constraints = {
        veh_Need_Cer()
        veh_Type(blank: true, nullable: true)
        veh_Xnhgzbh(blank: true, nullable: true)
        change_Field(blank: true, nullable: true)
        veh_Clzzqymc(blank: true, nullable: true)
        veh_Zchgzbh(blank: true, nullable: true)
        veh_Dphgzbh(blank: true, nullable: true)
        veh_Fzrq(blank: true, nullable: true)
        veh_Qyid(blank: true, nullable: true)
        veh_Clfl(blank: true, nullable: true)
        veh_Clmc(blank: true, nullable: true)
        veh_Clpp(blank: true, nullable: true)
        veh_Clxh(blank: true, nullable: true)
        veh_Csys(blank: true, nullable: true)
        veh_Dpxh(blank: true, nullable: true)
        veh_Dpid(blank: true, nullable: true)
        veh_Clsbdh(blank: true, nullable: true)
        veh_Cjh(blank: true, nullable: true)
        veh_Fdjh(blank: true, nullable: true)
        veh_Fdjxh(blank: true, nullable: true)
        veh_Rlzl(blank: true, nullable: true)
        veh_Pl(blank: true, nullable: true)
        veh_Gl(blank: true, nullable: true)
        veh_zdjgl(blank: true, nullable: true)
        veh_Zxxs(blank: true, nullable: true)
        veh_Qlj(blank: true, nullable: true)
        veh_Hlj(blank: true, nullable: true)
        veh_Lts(blank: true, nullable: true)
        veh_Ltgg(blank: true, nullable: true)
        veh_Gbthps(blank: true, nullable: true)
        veh_Zj(blank: true, nullable: true)
        veh_Zh(blank: true, nullable: true)
        veh_Zs(blank: true, nullable: true)
        veh_Wkc(blank: true, nullable: true)
        veh_Wkk(blank: true, nullable: true)
        veh_Wkg(blank: true, nullable: true)
        veh_Hxnbc(blank: true, nullable: true)
        veh_Hxnbk(blank: true, nullable: true)
        veh_Hxnbg(blank: true, nullable: true)
        veh_Zzl(blank: true, nullable: true)
        veh_Edzzl(blank: true, nullable: true)
        veh_Zbzl(blank: true, nullable: true)
        veh_Zzllyxs(blank: true, nullable: true)
        veh_Zqyzzl(blank: true, nullable: true)
        veh_Edzk(blank: true, nullable: true)
        veh_Bgcazzdyxzzl(blank: true, nullable: true)
        veh_Jsszcrs(blank: true, nullable: true)
        veh_Qzdfs(blank: true, nullable: true)
        veh_Hzdfs(blank: true, nullable: true)
        veh_Qzdczfs(blank: true, nullable: true)
        veh_Hzdczfs(blank: true, nullable: true)
        veh_Cpggh(blank: true, nullable: true)
        veh_Ggsxrq(blank: true, nullable: true)
        veh_Zzbh(blank: true, nullable: true)
        veh_Dywym(maxSize: 510, blank: true, nullable: true)
        veh_Zgcs(blank: true, nullable: true)
        veh_Clzzrq(blank: true, nullable: true)
        veh_Bz(blank: true, nullable: true)
        veh_Qybz(blank: true, nullable: true)
        veh_Cpscdz(blank: true, nullable: true)
        veh_Qyqtxx(blank: true, nullable: true)
        veh_Pfbz(blank: true, nullable: true)
        veh_Clztxx(blank: true, nullable: true)
        veh_Jss(blank: true, nullable: true)
        veh_Jsslx(blank: true, nullable: true)
        veh_Zchgzbh_0(blank: true, nullable: true)
        used(blank: true, nullable: true)
        used2(blank: true, nullable: true)
        upload(blank: true, nullable: true)
        vercode(maxSize: 510, blank: true, nullable: true)
        veh_Yh(blank: true, nullable: true)
        veh_Ggpc(blank: true, nullable: true)
        veh_VinFourBit(blank: true, nullable: true)
        veh_pzxlh(blank: true, nullable: true)
        veh_createTime(blank: true, nullable: true)
        veh_createrId(blank: true, nullable: true)
        upload_Path(blank: true, nullable: true)
        veh_clzzqyxx(blank: true, nullable: true)

        veh_Type_R(blank: true, nullable: true)
        veh_Xnhgzbh_R(blank: true, nullable: true)
        veh_Clzzqymc_R(blank: true, nullable: true)
        veh_Zchgzbh_R(blank: true, nullable: true)
        veh_Dphgzbh_R(blank: true, nullable: true)
        veh_Fzrq_R(blank: true, nullable: true)
        veh_Qyid_R(blank: true, nullable: true)
        veh_Clfl_R(blank: true, nullable: true)
        veh_Clmc_R(blank: true, nullable: true)
        veh_Clpp_R(blank: true, nullable: true)
        veh_Clxh_R(blank: true, nullable: true)
        veh_Csys_R(blank: true, nullable: true)
        veh_Dpxh_R(blank: true, nullable: true)
        veh_Dpid_R(blank: true, nullable: true)
        veh_Clsbdh_R(blank: true, nullable: true)
        veh_Cjh_R(blank: true, nullable: true)
        veh_Fdjh_R(blank: true, nullable: true)
        veh_Fdjxh_R(blank: true, nullable: true)
        veh_Rlzl_R(blank: true, nullable: true)
        veh_Pl_R(blank: true, nullable: true)
        veh_Gl_R(blank: true, nullable: true)
        veh_zdjgl_R(blank: true, nullable: true)
        veh_Zxxs_R(blank: true, nullable: true)
        veh_Qlj_R(blank: true, nullable: true)
        veh_Hlj_R(blank: true, nullable: true)
        veh_Lts_R(blank: true, nullable: true)
        veh_Ltgg_R(blank: true, nullable: true)
        veh_Gbthps_R(blank: true, nullable: true)
        veh_Zj_R(blank: true, nullable: true)
        veh_Zh_R(blank: true, nullable: true)
        veh_Zs_R(blank: true, nullable: true)
        veh_Wkc_R(blank: true, nullable: true)
        veh_Wkk_R(blank: true, nullable: true)
        veh_Wkg_R(blank: true, nullable: true)
        veh_Hxnbc_R(blank: true, nullable: true)
        veh_Hxnbk_R(blank: true, nullable: true)
        veh_Hxnbg_R(blank: true, nullable: true)
        veh_Zzl_R(blank: true, nullable: true)
        veh_Edzzl_R(blank: true, nullable: true)
        veh_Zbzl_R(blank: true, nullable: true)
        veh_Zzllyxs_R(blank: true, nullable: true)
        veh_Zqyzzl_R(blank: true, nullable: true)
        veh_Edzk_R(blank: true, nullable: true)
        veh_Bgcazzdyxzzl_R(blank: true, nullable: true)
        veh_Jsszcrs_R(blank: true, nullable: true)
        veh_Qzdfs_R(blank: true, nullable: true)
        veh_Hzdfs_R(blank: true, nullable: true)
        veh_Qzdczfs_R(blank: true, nullable: true)
        veh_Hzdczfs_R(blank: true, nullable: true)
        veh_Cpggh_R(blank: true, nullable: true)
        veh_Ggsxrq_R(blank: true, nullable: true)
        veh_Zzbh_R(blank: true, nullable: true)
        veh_Dywym_R(maxSize: 510, blank: true, nullable: true)
        veh_Zgcs_R(blank: true, nullable: true)
        veh_Clzzrq_R(blank: true, nullable: true)
        veh_Bz_R(blank: true, nullable: true)
        veh_Qybz_R(blank: true, nullable: true)
        veh_Cpscdz_R(blank: true, nullable: true)
        veh_Qyqtxx_R(blank: true, nullable: true)
        veh_Pfbz_R(blank: true, nullable: true)
        veh_Clztxx_R(blank: true, nullable: true)
        veh_Jss_R(blank: true, nullable: true)
        veh_Jsslx_R(blank: true, nullable: true)
        veh_Zchgzbh_0_R(blank: true, nullable: true)
        used_r(blank: true, nullable: true)
        used2_r(blank: true, nullable: true)
        upload_r(blank: true, nullable: true)
        vercode_r(maxSize: 510, blank: true, nullable: true)
        veh_Yh_R(blank: true, nullable: true)
        veh_Ggpc_R(blank: true, nullable: true)
        veh_VinFourBit_R(blank: true, nullable: true)
        veh_pzxlh_R(blank: true, nullable: true)
        upload_Path_R(blank: true, nullable: true)
        veh_clzzqyxx_R(blank: true, nullable: true)
        veh_usertype()
        veh_isupload()
        veh_managestatus()
        veh_coc_status()
        veh_QX_status(blank: true, nullable: true)
        veh_DP_status(blank: true, nullable: true)
        salesmanname(blank: true, nullable: true)
        salesmantel(blank: true, nullable: true)
        authTime(blank: true, nullable: true)
        authId(blank: true, nullable: true)
        remark(blank: true, nullable: true)
        auth_Remark(blank: true, nullable: true)
        createName(blank: true, nullable: true)
        statusReco()

        area_AuthId(blank: true, nullable: true)
        area_status(blank: true, nullable: true)
        area_AuthTime(blank: true, nullable: true)
        area_Remark(blank: true, nullable: true)
        product_auth_Id(blank: true, nullable: true)
        product_authTime(blank: true, nullable: true)
        product_auth_status(blank: true, nullable: true)
        product_auth_Remark(blank: true, nullable: true)
        createrOrgan(blank: true, nullable: true)
        replaceType(blank: true, nullable: true)

        transToCrm(blank: true, nullable: true)

        SAP_No(blank: true, nullable: true)
        formal(blank: true, nullable: true)
        zcinfoReplaceId(blank: true, nullable: true)
    }

    static mapping = {
        table 'REPLACE_FOR_SUPPLEMENT'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator: 'uuid.hex', column: 'id'
        zcInfo column: 'zcinfo_id'
        veh_Need_Cer(comment: '所需合格证的类型，0：只要整车合格证 1：只要底盘合格证  2：整车和底盘合格证')
        veh_Type(comment: '汽车和农用车标示')
        veh_Xnhgzbh(comment: '虚拟合格证编号')
        change_Field(comment: '更换申请变更字段')
        veh_Zchgzbh(comment: '整车合格证编号')
        veh_Dphgzbh(comment: '底盘合格证编号')
        veh_Fzrq(comment: '发证日期')
        veh_Clzzqymc(comment: '车辆制造企业名称')
        veh_Qyid(comment: '企业ID')
        veh_Clfl(comment: '车辆分类')
        veh_Clmc(comment: '车辆名称')
        veh_Clpp(comment: '车辆品牌')
        veh_Clxh(comment: '车辆型号')
        veh_Csys(comment: '车身颜色')
        veh_Dpxh(comment: '底盘型号')
        veh_Dpid(comment: '底盘ID')
        veh_Clsbdh(comment: '车辆识别代号')
        veh_Cjh(comment: '车架号')
        veh_Fdjh(comment: '发动机号')
        veh_Fdjxh(comment: '发动机型号')
        veh_Rlzl(comment: '燃料种类')
        veh_Pl(comment: '排量')
        veh_Gl(comment: '功率')
        veh_zdjgl(comment: '最大净功率')
        veh_Zxxs(comment: '转向形式')
        veh_Qlj(comment: '前轮距')
        veh_Hlj(comment: '后轮距')
        veh_Lts(comment: '轮胎数')
        veh_Ltgg(comment: '轮胎规格')
        veh_Gbthps(comment: '钢板弹簧片数')
        veh_Zj(comment: '轴距')
        veh_Zh(comment: '轴荷')
        veh_Zs(comment: '轴数')
        veh_Wkc(comment: '外廓长')
        veh_Wkk(comment: '外廓宽')
        veh_Wkg(comment: '外廓高')
        veh_Hxnbc(comment: '货箱内部长')
        veh_Hxnbk(comment: '货箱内部宽')
        veh_Hxnbg(comment: '货箱内部高')
        veh_Zzl(comment: '总质量')
        veh_Edzzl(comment: '额定载质量')
        veh_Zbzl(comment: '整备质量')
        veh_Zzllyxs(comment: '载质量利用系数')
        veh_Zqyzzl(comment: '准牵引总质量')
        veh_Edzk(comment: '额定载客')
        veh_Bgcazzdyxzzl(comment: '半挂车鞍座最大允许总质量')
        veh_Jsszcrs(comment: '驾驶室准乘人数')
        veh_Qzdfs(comment: '前制动方式')
        veh_Hzdfs(comment: '后制动方式')
        veh_Qzdczfs(comment: '前制动操作方式')
        veh_Hzdczfs(comment: '后制动操作方式')
        veh_Cpggh(comment: '产品公告号')
        veh_Ggsxrq(comment: '公告生效日期')
        veh_Zzbh(comment: '纸张编号')
        veh_Dywym(comment: '打印唯一码')
        veh_Zgcs(comment: '最高车速')
        veh_Clzzrq(comment: '车辆制造日期')
        veh_Bz(comment: '备注')
        veh_Qybz(comment: '企业标准')
        veh_Cpscdz(comment: '产品生产地址')
        veh_Qyqtxx(comment: '企业其它信息')
        veh_Pfbz(comment: '排放标准')
        veh_Clztxx(comment: '车辆状态信息')
        veh_Jss(comment: '驾驶室')
        veh_Jsslx(comment: '驾驶室类型')
        veh_Zchgzbh_0(comment: '完整合格证编号')
        used(comment: '标识')
        used2(comment: '标识')
        upload(comment: '标识')
        vercode(comment: '二维码')
        veh_Yh(comment: '油耗')
        veh_Ggpc(comment: '公告批次')
        veh_VinFourBit(comment: "vin第四位")
        veh_pzxlh(comment: "配置序列号")
        veh_createTime(comment: "原创建人")
        veh_createrId(comment: "原创建时间")
        upload_Path(comment: "上传路径")
        veh_clzzqyxx(comment: "车辆制造企业信息")

        veh_Type_R(comment: '汽车和农用车标示')
        veh_Xnhgzbh_R(comment: '虚拟合格证编号')
        veh_Zchgzbh_R(comment: '整车合格证编号')
        veh_Dphgzbh_R(comment: '底盘合格证编号')
        veh_Fzrq_R(comment: '发证日期')
        veh_Clzzqymc_R(comment: '车辆制造企业名称')
        veh_Qyid_R(comment: '企业ID')
        veh_Clfl_R(comment: '车辆分类')
        veh_Clmc_R(comment: '车辆名称')
        veh_Clpp_R(comment: '车辆品牌')
        veh_Clxh_R(comment: '车辆型号')
        veh_Csys_R(comment: '车身颜色')
        veh_Dpxh_R(comment: '底盘型号')
        veh_Dpid_R(comment: '底盘ID')
        veh_Clsbdh_R(comment: '车辆识别代号')
        veh_Cjh_R(comment: '车架号')
        veh_Fdjh_R(comment: '发动机号')
        veh_Fdjxh_R(comment: '发动机型号')
        veh_Rlzl_R(comment: '燃料种类')
        veh_Pl_R(comment: '排量')
        veh_Gl_R(comment: '功率')
        veh_zdjgl_R(comment: '最大净功率')
        veh_Zxxs_R(comment: '转向形式')
        veh_Qlj_R(comment: '前轮距')
        veh_Hlj_R(comment: '后轮距')
        veh_Lts_R(comment: '轮胎数')
        veh_Ltgg_R(comment: '轮胎规格')
        veh_Gbthps_R(comment: '钢板弹簧片数')
        veh_Zj_R(comment: '轴距')
        veh_Zh_R(comment: '轴荷')
        veh_Zs_R(comment: '轴数')
        veh_Wkc_R(comment: '外廓长')
        veh_Wkk_R(comment: '外廓宽')
        veh_Wkg_R(comment: '外廓高')
        veh_Hxnbc_R(comment: '货箱内部长')
        veh_Hxnbk_R(comment: '货箱内部宽')
        veh_Hxnbg_R(comment: '货箱内部高')
        veh_Zzl_R(comment: '总质量')
        veh_Edzzl_R(comment: '额定载质量')
        veh_Zbzl_R(comment: '整备质量')
        veh_Zzllyxs_R(comment: '载质量利用系数')
        veh_Zqyzzl_R(comment: '准牵引总质量')
        veh_Edzk_R(comment: '额定载客')
        veh_Bgcazzdyxzzl_R(comment: '半挂车鞍座最大允许总质量')
        veh_Jsszcrs_R(comment: '驾驶室准乘人数')
        veh_Qzdfs_R(comment: '前制动方式')
        veh_Hzdfs_R(comment: '后制动方式')
        veh_Qzdczfs_R(comment: '前制动操作方式')
        veh_Hzdczfs_R(comment: '后制动操作方式')
        veh_Cpggh_R(comment: '产品公告号')
        veh_Ggsxrq_R(comment: '公告生效日期')
        veh_Zzbh_R(comment: '纸张编号')
        veh_Dywym_R(comment: '打印唯一码')
        veh_Zgcs_R(comment: '最高车速')
        veh_Clzzrq_R(comment: '车辆制造日期')
        veh_Bz_R(comment: '备注')
        veh_Qybz_R(comment: '企业标准')
        veh_Cpscdz_R(comment: '产品生产地址')
        veh_Qyqtxx_R(comment: '企业其它信息')
        veh_Pfbz_R(comment: '排放标准')
        veh_Clztxx_R(comment: '车辆状态信息')
        veh_Jss_R(comment: '驾驶室')
        veh_Jsslx_R(comment: '驾驶室类型')
        veh_Zchgzbh_0_R(comment: '完整合格证编号')
        used_r(comment: '标识')
        used2_r(comment: '标识')
        upload_r(comment: '标识')
        vercode_r(comment: '二维码')
        veh_VinFourBit_R(comment: "vin第四位")
        veh_Yh_R(comment: '油耗')
        veh_Ggpc_R(comment: '公告批次')

        veh_pzxlh_R(comment: '配置序列号')
        upload_Path_R(comment: '合格证上传相对路径')
        veh_clzzqyxx_R(comment: '车辆制造企业信息')

        veh_usertype(comment: '合格证修改记录作者类型  0：经销商修改、更换 1：公告资源部修改(只是针对已经上传到国家的合格证修改 才会有修改记录)')
        //合格证修改记录作者类型  0：经销商修改、更换 1：公告资源部修改(只是针对已经上传到国家的合格证修改 才会有修改记录)
        veh_isupload(comment: '原合格证信息是否已经上传到国家系统  0：未上传到国家 1：已上传到国家')       //原合格证信息是否已经上传到国家系统  0：未上传到国家 1：已上传到国家
        veh_managestatus(comment: '针对已经上传到国家的记录进行修改  由公告资源部确认处理情况  0：待处理 1：处理完毕')     //
        veh_coc_status(comment: "审核状态，0：待审核，1：审核成功 2：审核不通过")
        veh_QX_status(comment: "整车审核状态，0：待审核，1：审核成功")
        veh_DP_status(comment: "底盘审核状态，0：待审核，1：审核成功")
        createTime(comment: '变更时间')
        createrId(comment: '变更人id')

        salesmanname(comment: '业务员姓名')
        salesmantel(comment: '业务员电话')
        authTime(comment: '审核时间')
        authId(comment: '审核人')
        remark(comment: '申请备注')
        auth_Remark(comment: '审核备注')
        createName(comment: '申请人姓名')
        statusReco(comment: '回收状态：0未回收，1已回收')

        //新加汽车合格证二次审核内容
        area_status(comment: '区域经理审核标记， 0未审核 1审核通过 2审核不通过')
        area_AuthId(comment: '区域经理审核ID')
        area_AuthTime(comment: '区域经理审核时间')
        area_Remark(comment: '区域经理审核备注')
        product_auth_status(comment: '产品管理部审核状态，0为审核 1审核通过 2 审核未通过')
        product_auth_Remark(comment: '产品管理部审核标记')
        product_authTime(comment: '产品管理部审核时间')
        product_auth_Id(comment: '产品管理部审核人')
        createrOrgan(comment: '保存申请更换人的组织ID')
        replaceType(comment: '更换类型标记：0正常更换 1二次更换')

        transToCrm(comment: '0表示要传给CRM，1禁止传给CRM  ，2传给CRM成功，3传给CRM失败')

        SAP_No(comment: 'SAP序列号')
        formal(comment: '0表示选公告换配置更换  1表示A4合格证换正式合格证')
        zcinfoReplaceId(comment: '对应合格证变更表信息的ID')
        sort 'createTime': "desc"
    }
}
