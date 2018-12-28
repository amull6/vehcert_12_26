package cn.com.wz.vehcert.dealercarstorage

import cn.com.wz.parent.system.dictionary.DictionaryValue

class DealerCar {
    String id;
    /**
     *公告型号
     */
    String dc_Ggxh
    /**
     *驾驶室
     */
    String dc_Jss
    /**
     * 公告批次
     */
    String dc_Ggpc
    /**
     * 车辆图片
     */
    String dc_Pic
    /**
     * 准乘人数
     */
    String dc_Jsszcrs
    /**
     * 发动机
     */
    String dc_Fdj
    /**
     * 公告轮胎
     */
    String dc_Gglt
    /**
     * 板簧片数
     */
    String dc_Bhps
    /**
     * 前后轮距
     */
    String dc_Qhlj
    /**
     * 轴距
     */
    String dc_Zj
    /**
     *整车尺寸
     */
    String dc_Zccc
    /**
     *货箱尺寸
     */
    String dc_Hxcc
    /**
     * 总质量
     */
    String dc_Zzl
    /**
     * 整备质量
     */
    String dc_Zbzl
    /**
     * 载质量
     */
    String dc_ZAzl
    /**
     * 扩展日期
     */
    String dc_Kzrq
    /**
     * 其他
     */
    String dc_Qt
    /**
     * 修改人
     */
    String updaterName
    /**
     * 修改时间
     */
    String updateTime
    /**
     * 类型
     */
    DictionaryValue kind
    static constraints = {
        dc_Ggxh (blank: true,nullable: true)
        dc_Jss   (blank: true,nullable: true)
        dc_Ggpc   (blank: true,nullable: true)
        dc_Pic     (blank: true,nullable: true)
        dc_Jsszcrs   (blank: true,nullable: true)
        dc_Fdj     (blank: true,nullable: true)
        dc_Gglt     (blank: true,nullable: true)
        dc_Bhps    (blank: true,nullable: true)
        dc_Qhlj     (blank: true,nullable: true)
        dc_Zj     (blank: true,nullable: true)
        dc_Zccc     (blank: true,nullable: true)
        dc_Hxcc    (blank: true,nullable: true)
        dc_Zzl     (blank: true,nullable: true)
        dc_Zbzl       (blank: true,nullable: true)
        dc_ZAzl     (blank: true,nullable: true)
        dc_Kzrq    (blank: true,nullable: true)
        dc_Qt      (blank: true,nullable: true)
        updaterName    (blank: true,nullable: true)
        updateTime     (blank: true,nullable: true)
    }
    static  mapping={
        table 'TBL_DEALER_CAR'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'
        dc_Ggxh (comment:'公告型号')
        dc_Jss     (comment:'驾驶室')
        dc_Ggpc     (comment:'公告批次')
        dc_Pic     (comment:'车辆照片')
        dc_Jsszcrs     (comment:'准乘人数')
        dc_Fdj     (comment:'发动机')
        dc_Gglt     (comment:'公告轮胎')
        dc_Bhps     (comment:'板簧片数')
        dc_Qhlj     (comment:'前后轮距')
        dc_Zj     (comment:'轴距')
        dc_Zccc     (comment:'整车尺寸')
        dc_Hxcc    (comment:'货箱尺寸')
        dc_Zzl     (comment:'总质量')
        dc_Zbzl       (comment:'整备质量')
        dc_ZAzl       (comment:'载质量')
        dc_Kzrq    (comment:'扩展日期')
        dc_Qt      (comment:'其他')
        updaterName      (comment:'修改人')
        updateTime      (comment:'修改日期')
    }
}
