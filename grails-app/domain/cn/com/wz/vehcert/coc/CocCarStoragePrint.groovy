package cn.com.wz.vehcert.coc

import cn.com.wz.parent.system.user.User
import org.springframework.orm.hibernate3.support.ClobStringType

/**
 *@Description  已打印的一致性证书基础信息
 *@Auther mike
 *@createTime 2013-07-24
 */
class CocCarStoragePrint {
    String id;
    /**
     * 一致性证书编号
     */
    String coc_yzxzsbh;
    /**
     * 车轴数量
     */
    String coc_czsl;
    /**
     * 车轮数量
     */
    String coc_clsl;
    /**
     * 驱动轴位置
     */
    String coc_qdzwz;
    /**
     * 轴距
     */
    String coc_zj;
    /**
     * 牵引座前置距
     */
    String coc_qyzqzj;
    /**
     * 轮距
     */
    String coc_lj;
    /**
     * 长度
     */
    String coc_cd;
    /**
     * 最前端与牵引装置的距离
     */
    String coc_zqdyqyzzjl;
    /**
     * 装载区域长度
     */
    String coc_zzqycd;
    /**
     * 宽度
     */
    String coc_kd;
    /**
     * 高度
     */
    String coc_gd;
    /**
     * 车辆在地面上的投影面积
     */
    String coc_tymj;
    /**
     * 前悬
     */
    String coc_qx;
    /**
     * 后悬
     */
    String coc_hx;
    /**
     * 接近角
     */
    String coc_jjj;
    /**
     * 离去角
     */
    String coc_lqj;
    /**
     * 行驶下带车身的车辆质量
     */
    String coc_xsxdcsclzl;
    /**
     * 额定总质量
     */
    String coc_edzzl;
    /**
     * 该质量的轴荷分配
     */
    String coc_zlzhfp;
    /**
     * 载质量利用系数
     */
    String coc_zzllyxsh;
    /**
     * 车轴允许的最大质量
     */
    String coc_czyxzdzl;
    /**
     * 可伸缩车轴的位置
     */
    String coc_kssczwz;
    /**
     * 可承载车轴位置
     */
    String coc_kczczwz;
    /**
     * 车顶最大允许载荷
     */
    String coc_cdzdyxzh;
    /**
     * 牵引杆式挂车
     */
    String coc_qygsgc;
    /**
     * 半挂车
     */
    String coc_bgc;
    /**
     * 中间轴挂车
     */
    String coc_zjjgc;
    /**
     * 挂车的最大质量
     */
    String coc_gczdzl;
    /**
     * 最大组合质量
     */
    String coc_zdzhzl;
    /**
     * 接触点处的最大垂直负荷
     */
    String coc_jcdczdczfh;
    /**
     * 发动机制造商名称
     */
    String coc_fdjzzsmc;
    /**
     * 直接喷射
     */
    String coc_zjps;
    /**
     * 汽缸数量
     */
    String coc_qgsl;
    /**
     * 气缸排列形式
     */
    String coc_qgplxs;
    /**
     * 排量
     */
    String coc_pl;
    /**
     * 燃料种类
     */
    String coc_rlzl;
    /**
     * 最大净功率
     */
    String coc_zdjgl;
    /**
     * 对应的发动机转速
     */
    String coc_dyfdjzs;
    /**
     * 离合器形式
     */
    String coc_lhqxs;
    /**
     * 变速器形式
     */
    String coc_bsqzs;
    /**
     * 速比
     */
    String coc_sb;
    /**
     * 主传动比
     */
    String coc_zcdb;
    /**
     * 轮胎规格
     */
    String coc_ltgg;
    /**
     * 是否安装有空气悬挂
     */
    String coc_sfazkqxg;
    /**
     * 钢板弹簧的片数
     */
    String coc_gbthps;
    /**
     * 转向助力形式
     */
    String coc_zxzlxs;
    /**
     * 制动装置简要说明
     */
    String coc_zdzzjysm;
    /**
     * 制动系统供气管内压力
     */
    String coc_zdxtgqgnyl;
    /**
     * 车身形式
     */
    String coc_csxs;
    /**
     * 车辆颜色
     */
    String coc_clys;
    /**
     * 罐体内有效容积
     */
    String coc_gnyxrj;
    /**
     * 货箱长度
     */
    String coc_hxcd;
    /**
     * 货箱宽度
     */
    String coc_hxkd;
    /**
     * 货箱高度
     */
    String coc_hxgd;
    /**
     * 起重机的最大力矩能力
     */
    String coc_qzjzdljnl;
    /**
     * 车门数量
     */
    String coc_cmsl;
    /**
     * 车门构造
     */
    String coc_cmgz;
    /**
     * 座位数
     */
    String coc_zws;
    /**
     * CCC证书编号
     */
    String coc_cccno;
    /**
     * 或实验报告编号
     */
    String coc_sybgno;
    /**
     * 最高车速
     */
    String coc_zgcs;
    /**
     * 声级
     */
    String coc_sj;
    /**
     * 排气排放物
     */
    String coc_pqpfw;
    /**
     * CO2排放
     */
    String coc_co2;
    /**
     * 结构要求的试验报告
     */
    String coc_jgyqsybg;
    /**
     * 危险货物运输的级别
     */
    String coc_wxhwysjb;
    /**
     * 动物的结构要求的编号
     */
    String coc_dwjgyqbh;
    /**
     * 某些动物运输的级别
     */
    String coc_mxdwysjb;
    /**
     * 备注
     */
    String coc_note;
    /**
     * 证书编号
     */
    String coc_zsno;
    /**
     * 车辆生产厂名称
     */
    String coc_clsccmc;
    /**
     * 车辆制造国
     */
    String coc_clzzg;
    /**
     * 车型系列代号名称
     */
    String coc_cxxldhmc;
    /**
     * 单元代号名称
     */
    String coc_dydhmc;
    /**
     * 车型代号名称
     */
    String coc_cxdhmc;
    /**
     * 车型名称
     */
    String coc_cxmc;
    /**
     * 车辆中文品牌
     */
    String coc_clzwpp;
    /**
     * 车辆类别
     */
    String coc_cllb;
    /**
     * 制造商的名称
     */
    String coc_zzsmc;
    /**
     * 制造商的地址
     */
    String coc_zzsdz;
    /**
     * 法定名牌的位置
     */
    String coc_fdmpwz;
    /**
     * 车辆识别代号
     */
    String coc_clsbdh;
    /**
     * 识别号的打刻位置
     */
    String coc_sbhdkwz;
    /**
     * 发动机编号
     */
    String coc_fdjbh;
    /**
     * 发动机编号的打刻位置
     */
    String coc_fdjbhdkwz;
    /**
     * CCC证书号
     */
    String coc_ccczsh;
    /**
     * 车辆型号
     */
    String coc_clxh;
    /**
     * 车辆名称
     */
    String coc_clmc;
    /**
     * 日期
     */
    String coc_rq;
    /**
     *发证日期
     */
    String coc_fzrq;
    /**
     * 发动机型号
     */
    String coc_fdjxh;
    /**
     * 发动机工作原理
     */
    String coc_fdjgzyl;
    /**
     * 是否装有空气悬挂的装置
     */
    String coc_sfzykqxgzz;
    /**
     * 创建人
     */
    String coc_creater;
    /**
     * 创建时间
     */
    String coc_createTime;
    //最后更新时间
    String coc_updateTime
    String coc_qr_code
    /**
     /**
     * 上传状态 0未上传 1 已上传 2上传失败
     */
    int status=0 ;
    /**
     *是否是新能源车
     */
    String coc_new_energy;
    /**
     *是否带防抱死系统
     */
    String coc_abs;
    /**
     *生产企业地址
     */
    String coc_scqydz;
    /**
     *车辆种类
     */
    String coc_clzl;
    /**
     *基础车辆一致性证书编号
     */
    String coc_jccccno;
    /**
     *基础车辆型号
     */
    String coc_jcclxh;
    /**
     *基础车辆类别
     */
    String coc_jccllb;
    /**
     *最终阶段车辆CCC证书编号
     */
    String coc_zzjdcccno;
    /**
     *最终阶段车辆CCC证书编号签发日期
     */
    String coc_zzjdqfrq;
    /**
     *最大允许总质量
     */
    String coc_zdyxzzl;
    /**
     *声级标准号及对应的实施阶段
     */
    String coc_sjbzh;
    /**
     *定置噪声
     */
    String coc_dzzs;
    /**
     *对应的发动机转速
     */
    String coc_fdjzs;
    /**
     *加速行驶车外噪声
     */
    String coc_cwzs;
    /**
     *排气排放物标准号及对应的实施阶段
     */
    String coc_pfwbzh;
    /**
     *实验用液体燃料
     */
    String coc_ytrl;
    /**
     *实验用液体燃料CO
     */
    String coc_ytrl_co;
    /**
     *实验用液体燃料NOX
     */
    String coc_ytrl_nox;
    /**
     *实验用液体燃料烟度
     */
    String coc_ytrl_yd;
    /**
     *实验用气体燃料
     */
    String coc_qtrl;
    /**
     *实验用气体燃料CO
     */
    String coc_qtrl_co;
    /**
     *实验用气体燃料NMHC
     */
    String coc_qtrl_nmhc;
    /**
     *实验用气体燃料CH4
     */
    String coc_qtrl_ch4;
    /**
     *CO2排放量标准号
     */
    String coc_co2pflbzh;
    /**
     *市区CO2排放量
     */
    String coc_co2_sq;
    /**
     *市郊CO2排放量
     */
    String coc_co2_sj;
    /**
     *综合CO2排放量
     */
    String coc_co2_zh;
    /**
     *市区燃料消耗量
     */
    String coc_rlxh_sq;
    /**
     *市郊燃料消耗量
     */
    String coc_rlxh_sj;
    /**
     *综合燃料消耗量
     */
    String coc_rlxh_zh;
    /**
     *实验用液体燃料HC
     */
    String coc_ytrl_hc;
    /**
     *实验用液体燃料HC+NOx
     */
    String coc_ytrl_hcnox;
    /**
     *实验用液体燃料pn
     */
    String coc_ytrl_pn;
    /**
     *实验用气体燃料nox
     */
    String coc_qtrl_nox;
    /**
     *实验用气体燃料THC
     */
    String coc_qtrl_thc;
    /**
     *实验用气体燃料pn
     */
    String coc_qtrl_pn;
    /**
     *车辆注册类型
     */
    String coc_clzclx;
    /**
     *电动机工作电压
     */
    String coc_ddjgzdy;
    /**
     *动力电池型号
     */
    String coc_dldcxh;
    /**
     *动力电池额定电压
     */
    String coc_dldceddy;
    /**
     *动力电池额定容量
     */
    String coc_dldcedrl;
    /**
     *动力电池生产厂名称
     */
    String coc_dldcsccmc;




    static constraints = {
        coc_yzxzsbh(blank: false,nullable: true)
        coc_czsl(nullable:true)
        coc_clsl(nullable:true)
        coc_qdzwz(nullable:true)
        coc_zj(nullable:true)
        coc_qyzqzj(nullable:true)
        coc_lj(nullable:true)
        coc_cd(nullable:true)
        coc_zqdyqyzzjl(nullable:true)
        coc_zzqycd(nullable:true)
        coc_kd(nullable:true)
        coc_tymj(nullable:true)
        coc_qx(nullable:true)
        coc_gd(nullable:true)
        coc_hx(nullable:true)
        coc_jjj(nullable:true)
        coc_lqj(nullable:true)
        coc_xsxdcsclzl(nullable:true)
        coc_edzzl(nullable:true)
        coc_zlzhfp(nullable:true)
        coc_zzllyxsh(nullable:true)
        coc_czyxzdzl(nullable:true)
        coc_kssczwz(nullable:true)
        coc_kczczwz(nullable:true)
        coc_cdzdyxzh(nullable:true)
        coc_qygsgc(nullable:true)
        coc_bgc(nullable:true)
        coc_zjjgc(nullable:true)
        coc_gczdzl(nullable:true)
        coc_zdzhzl(nullable:true)
        coc_jcdczdczfh(nullable:true)
        coc_fdjzzsmc(nullable:true)
        coc_zjps(nullable:true)
        coc_qgsl(nullable:true)
        coc_qgplxs(nullable:true)
        coc_pl(nullable:true)
        coc_rlzl(nullable:true)
        coc_zdjgl(nullable:true)
        coc_dyfdjzs(nullable:true)
        coc_lhqxs(nullable:true)
        coc_bsqzs(nullable:true)
        coc_sb(nullable:true)
        coc_zcdb(nullable:true)
        coc_ltgg(nullable:true)
        coc_sfazkqxg(nullable:true)
        coc_gbthps(nullable:true)
        coc_zxzlxs(nullable:true)
        coc_zdzzjysm(nullable:true)
        coc_zdxtgqgnyl(nullable:true)
        coc_csxs(nullable:true)
        coc_clys(nullable:true)
        coc_gnyxrj(nullable:true)
        coc_hxcd(nullable:true)
        coc_hxkd(nullable:true)
        coc_hxgd(nullable:true)
        coc_qzjzdljnl(nullable:true)
        coc_cmsl(nullable:true)
        coc_cmgz(nullable:true)
        coc_zws(nullable:true)
        coc_cccno(nullable:true)
        coc_sybgno(nullable:true)
        coc_zgcs(nullable:true)
        coc_sj(nullable:true)
        coc_pqpfw(nullable:true)
        coc_co2(nullable:true)
        coc_jgyqsybg(nullable:true)
        coc_wxhwysjb(nullable:true)
        coc_dwjgyqbh(nullable:true)
        coc_mxdwysjb(nullable:true)
        coc_note(nullable:true)
        coc_zsno(nullable:true)
        coc_clsccmc(nullable:true)
        coc_clzzg(nullable:true)
        coc_cxxldhmc(nullable:true)
        coc_dydhmc(nullable:true)
        coc_cxdhmc(nullable:true)
        coc_cxmc(nullable:true)
        coc_clzwpp(nullable:true)
        coc_cllb(nullable:true)
        coc_zzsmc(nullable:true)
        coc_zzsdz(nullable:true)
        coc_fdmpwz(nullable:true)
        coc_clsbdh(nullable:true)
        coc_sbhdkwz(nullable:true)
        coc_fdjbh(nullable:true)
        coc_fdjbhdkwz(nullable:true)
        coc_ccczsh(nullable:true)
        coc_clxh(nullable:true)
        coc_clmc(nullable:true)
        coc_rq(nullable:true)
        coc_fzrq(nullable: true);
        coc_fdjxh(nullable:true)
        coc_fdjgzyl(nullable:true)
        coc_sfzykqxgzz(nullable:true)
        coc_creater(nullable:true)
        coc_createTime(nullable:true)
        coc_updateTime (nullable:true)
        coc_qr_code (nullable:true)
        status()
        coc_new_energy(nullable:true)
        coc_abs(nullable:true)
        coc_clzl(nullable:true)
        coc_scqydz(nullable:true)
        coc_jccccno(nullable:true)
        coc_jcclxh(nullable:true)
        coc_jccllb(nullable:true)
        coc_zzjdcccno(nullable:true)
        coc_zzjdqfrq(nullable:true)
        coc_zdyxzzl(nullable:true)

        coc_sjbzh (nullable:true)
        coc_dzzs (nullable:true)
        coc_fdjzs (nullable:true)
        coc_cwzs (nullable:true)
        coc_pfwbzh (nullable:true)
        coc_ytrl (nullable:true)
        coc_ytrl_co (nullable:true)
        coc_ytrl_nox (nullable:true)
        coc_ytrl_yd (nullable:true)
        coc_qtrl (nullable:true)
        coc_qtrl_co (nullable:true)
        coc_qtrl_nmhc (nullable:true)
        coc_qtrl_ch4 (nullable:true)

        coc_co2pflbzh (nullable:true)
        coc_co2_sq (nullable:true)
        coc_co2_sj (nullable:true)
        coc_co2_zh (nullable:true)
        coc_rlxh_sq (nullable:true)
        coc_rlxh_sj (nullable:true)
        coc_rlxh_zh (nullable:true)

        coc_ytrl_hc (nullable:true)
        coc_ytrl_hcnox (nullable:true)
        coc_ytrl_pn (nullable:true)
        coc_qtrl_nox (nullable:true)
        coc_qtrl_thc (nullable:true)
        coc_qtrl_pn (nullable:true)
        coc_clzclx (nullable:true)

        coc_ddjgzdy (nullable:true)
        coc_dldcxh (nullable:true)
        coc_dldceddy (nullable:true)
        coc_dldcedrl (nullable:true)
        coc_dldcsccmc (nullable:true)
    }
    //数据库映射关系
    static mapping={
        table 'COC_INFO'
        id generator:'assigned', column:'id'
        coc_qr_code type: ClobStringType   //将内部消息内容修改为大字段类型
        coc_yzxzsbh(comment:'一致性证书编号')
        coc_czsl (comment:'车轴数量')
        coc_clsl (comment:'车轮数量')
        coc_qdzwz (comment:'驱动轴位置')
        coc_zj (comment:'轴距')
        coc_qyzqzj (comment:'牵引座前置距')
        coc_lj (comment:'轮距')
        coc_cd (comment:'长度')
        coc_zqdyqyzzjl (comment:'最前端与牵引装置的距离')
        coc_zzqycd (comment:'装载区域长度')
        coc_kd (comment:'宽度')
        coc_gd (comment:'高度')
        coc_tymj (comment:'车辆在地面上的投影面积')
        coc_qx (comment:'前悬')
        coc_jjj (comment:'接近角')
        coc_lqj (comment:'离去角')
        coc_xsxdcsclzl (comment:'行驶下带车身的车辆质量')
        coc_edzzl (comment:'额定总质量')
        coc_zlzhfp (comment:'该质量的轴荷分配')
        coc_zzllyxsh (comment:'载质量利用系数')
        coc_czyxzdzl (comment:'车轴允许的最大质量')
        coc_kssczwz (comment:'可伸缩车轴的位置')
        coc_kczczwz (comment:'可承载车轴位置')
        coc_cdzdyxzh (comment:'车顶最大允许载荷')
        coc_qygsgc (comment:'牵引杆式挂车')
        coc_bgc (comment:'半挂车')
        coc_zjjgc (comment:'中间轴挂车')
        coc_gczdzl (comment:'挂车的最大质量')
        coc_zdzhzl (comment:'最大组合质量')
        coc_jcdczdczfh (comment:'接触点处的最大垂直负荷')
        coc_zjps (comment:'直接喷射')
        coc_qgsl (comment:'气缸数量')
        coc_qgplxs (comment:'气缸排列形式')
        coc_rlzl (comment:'燃料种类')
        coc_zdjgl (comment:'最大净功率')
        coc_dyfdjzs (comment:'对应的发动机转速')
        coc_lhqxs (comment:'离合器形式')
        coc_bsqzs (comment:'变速器形式')
        coc_sb (comment:'速比')
        coc_zcdb (comment:'主传动比')
        coc_ltgg (comment:'轮胎规格')
        coc_sfazkqxg (comment:'是否安装有空气悬挂')
        coc_gbthps (comment:'钢板弹簧的片数')
        coc_zxzlxs (comment:'转向助力形式')
        coc_zdzzjysm (comment:'制动装置简要说明')
        coc_zdxtgqgnyl (comment:'制动系统供气管内压力')
        coc_csxs (comment:'车身形式')
        coc_clys (comment:'车辆颜色')
        coc_gnyxrj (comment:'罐体内有效容积')
        coc_hxcd (comment:'货箱长度')
        coc_hx (comment:'后悬')
        coc_hxkd (comment:'货箱宽度')
        coc_hxgd (comment:'货箱高度')
        coc_qzjzdljnl (comment:'起重机的最大力矩能力')
        coc_cmsl (comment:'车门数量')
        coc_cmgz (comment:'车门构造')
        coc_zws (comment:'座位数')
        coc_cccno (comment:'CCC证书编号')
        coc_sybgno (comment:'或实验报告编号')
        coc_zgcs (comment:'最高车速')
        coc_sj (comment:'声级')
        coc_pqpfw (comment:'排气排放物')
        coc_co2 (comment:'CO2排放')
        coc_jgyqsybg (comment:'结构要求的试验报告')
        coc_wxhwysjb (comment:'危险货物运输的级别')
        coc_dwjgyqbh (comment:'动物的结构要求的编号')
        coc_mxdwysjb (comment:'某些动物运输的级别')
        coc_note (comment:'备注')
        coc_zsno (comment:'证书编号')
        coc_clsccmc (comment:'车辆生产厂名称')
        coc_clzzg (comment:'车辆制造国')
        coc_cxxldhmc (comment:'车型系列代号名称')
        coc_dydhmc (comment:'单元代号名称')
        coc_cxdhmc (comment:'车型代号名称')
        coc_cxmc (comment:'车型名称')
        coc_clzwpp (comment:'车辆中文品牌')
        coc_cllb (comment:'车辆类别')
        coc_zzsmc (comment:'制造商的名称')
        coc_zzsdz (comment:'制造商的地址')
        coc_fdjzzsmc (comment:'发动机制造商名称')
        coc_fdmpwz (comment:'法定名牌的位置')
        coc_clsbdh (comment:'车辆识别代号')
        coc_sbhdkwz (comment:'识别号的打刻位置')
        coc_fdjbh (comment:'发动机编号')
        coc_fdjbhdkwz (comment:'发动机编号的打刻位置')
        coc_ccczsh (comment:'CCC证书号')
        coc_clxh (comment:'车辆型号')
        coc_clmc (comment:'车辆名称')
        coc_rq (comment:'日期')
        coc_fzrq(comment:'发证日期')
        coc_pl (comment:'排量')
        coc_fdjxh (comment:'发动机型号')
        coc_fdjgzyl (comment:'发动机工作原理')
        coc_sfzykqxgzz (comment:'是否装有空气悬挂的装置')
        coc_creater (comment:'创建人')
        coc_createTime (comment:'创建时间')
        coc_updateTime(comment:"最后更新时间")
        coc_qr_code(comment:"二维码字符串")
        status(comment:'上传状态')

        coc_new_energy (comment:'是否带防抱死系统')
        coc_abs (comment:'是否带防抱死系统')
        coc_scqydz (comment:'生产企业地址')
        coc_clzl (comment:'车辆种类')
        coc_jccccno (comment:'基础车辆一致性证书编号')
        coc_jcclxh (comment:'基础车辆型号')
        coc_jccllb (comment:'基础车辆类别')
        coc_zzjdcccno (comment:'最终阶段车辆CCC证书编号')
        coc_zzjdqfrq (comment:'最终阶段车辆CCC证书编号签发日期')
        coc_zdyxzzl (comment:'最大允许总质量')

        coc_sjbzh (comment:'声级标准号及对应的实施阶段')
        coc_dzzs (comment:'定置噪声')
        coc_fdjzs (comment:'对应的发动机转速')
        coc_cwzs (comment:'加速行驶车外噪声')
        coc_pfwbzh (comment:'排气排放物标准号及对应的实施阶段')
        coc_ytrl (comment:'实验用液体燃料')
        coc_ytrl_co (comment:'实验用液体燃料CO')
        coc_ytrl_nox (comment:'实验用液体燃料NOX')
        coc_ytrl_yd (comment:'实验用液体燃料烟度')
        coc_qtrl (comment:'实验用气体燃料')
        coc_qtrl_co (comment:'实验用气体燃料CO')
        coc_qtrl_nmhc (comment:'实验用气体燃料NMHC')
        coc_qtrl_ch4 (comment:'实验用气体燃料CH4')

        coc_co2pflbzh (comment:'CO2排放量标准号')
        coc_co2_sq (comment:'市区CO2排放量')
        coc_co2_sj (comment:'市郊CO2排放量')
        coc_co2_zh (comment:'综合CO2排放量')
        coc_rlxh_sq (comment:'市区燃料消耗量')
        coc_rlxh_sj (comment:'市郊燃料消耗量')
        coc_rlxh_zh (comment:'综合燃料消耗量')

        coc_ytrl_hc (comment:'试验用液体燃料HC')
        coc_ytrl_hcnox (comment:'试验用液体燃料HC+NOx')
        coc_ytrl_pn (comment:'试验用液体燃料微粒物/PN')
        coc_qtrl_nox (comment:'试验用气体燃料NOx')
        coc_qtrl_thc (comment:'试验用气体燃料THC')
        coc_qtrl_pn (comment:'试验用气体燃料PN')
        coc_clzclx (comment:'车辆注册类型')

        coc_ddjgzdy (comment:'电动机工作电压')
        coc_dldcxh (comment:'动力电池型号')
        coc_dldceddy (comment:'动力电池额定电压')
        coc_dldcedrl (comment:'动力电池额定容量')
        coc_dldcsccmc (comment:'动力电池生产厂名称')


        sort "coc_createTime": 'desc'
    }
}
