package cn.com.wz.vehcert.oil

import cn.com.wz.parent.system.dictionary.DictionaryValue
/**
 * @Description 油耗信息
 * @Author liuly
 * @createTime 2013-08-04
 */
class Oil {
    /**
     * id
     */
    String id
    /**
     * 车辆型号
     */
    String veh_Clxh
    /**
     * 车辆名称
     */
    String veh_Clmc
    /**
     * 油耗批次
     */
    String veh_Yh
    /**
     * 发动机型号
     */
    String veh_Fdjxh
    /**
     * 整车长*宽*高
     */
    String veh_CarH
    /**
     * 货箱长*宽*高
     */
    String veh_GoodH
    /**
     * 备注
     */
    String veh_Bz
    /**
     * 类型
     */
    DictionaryValue kind
    static constraints = {
        veh_Clxh(nullable: false)
        veh_Clmc(nullable: true)
        veh_Yh(nullable: true)
        veh_Fdjxh(nullable: true)
        veh_CarH(nullable:true)
        veh_GoodH(nullable:true)
        veh_Bz(nullable: true)
    }
    static mapping = {
        table 'TBL_OIL'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'

        veh_Clxh   (comment: '车辆型号')
        veh_Clmc  (comment: '车辆名称')
        veh_Yh    (comment: '油耗批次')
        veh_Fdjxh        (comment: '发动机型号')
        veh_CarH      (comment: '整车长*宽*高')
        veh_GoodH      (comment: '货箱长*宽*高')
        veh_Bz (comment: '备注')

    }
}
