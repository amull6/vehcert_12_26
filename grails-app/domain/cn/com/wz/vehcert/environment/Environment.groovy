package cn.com.wz.vehcert.environment

import cn.com.wz.parent.system.dictionary.DictionaryValue

/**
 * @Description 环保信息
 * @Author liuly
 * @createTime 2013-08-04
 */
class Environment {
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
     * 车辆类别
     */
    String veh_Clfl
    /**
     * 发动机型号
     */
    String veh_Fdjxh
    /**
     * 公告批次
     */
    String veh_Ggpx
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
        veh_Clfl(nullable: true)
        veh_Fdjxh(nullable: true)
        veh_Ggpx(nullable:true)
        veh_Bz(nullable: true)
    }
    static mapping = {
        table 'TBL_ENVIRONMENT'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'

        veh_Clxh   (comment: '车辆型号')
        veh_Clmc  (comment: '车辆名称')
        veh_Clfl    (comment: '车辆类别')
        veh_Fdjxh        (comment: '发动机型号')
        veh_Ggpx      (comment: '公告批次')
        veh_Bz (comment: '备注')

    }
}
