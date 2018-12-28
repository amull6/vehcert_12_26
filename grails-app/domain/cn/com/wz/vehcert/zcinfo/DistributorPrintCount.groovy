package cn.com.wz.vehcert.zcinfo
/**
 * @Description 经销商自主打印合格证次数记录表
 * @create  2018-09-06 QJ
 */
class DistributorPrintCount {
    String id
    String veh_Type ='0'  //汽车和农用车标示   0：汽车 1：农用车
    String veh_Zchgzbh_0  //完整合格证编号
    String veh_Clsbdh     //车辆识别代号
    int printCount     //打印次数
    int limitPrintCount //限制打印次数

    //合格证是否打印
    static constraints = {
        veh_Type  (blank: true,nullable: true)
        veh_Zchgzbh_0 (blank: true,nullable: true)
        veh_Clsbdh  (blank: true,nullable: true)
        printCount (blank: true,nullable: true)
        limitPrintCount    (blank: true,nullable: true)
    }
    static mapping = {
        table 'DISTRIBUTOR_PRINT_COUNT'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'zcInfoId'
        veh_Type   (comment:'汽车和农用车标示 0汽车 1农用车')
        veh_Zchgzbh_0   (comment:'整车合格证编号')
        veh_Clsbdh  (comment:'底盘合格证编号')
        printCount     (comment:'发证日期')
        limitPrintCount (comment:'车辆制造企业名称')

        sort "veh_Zchgzbh_0": 'desc'
    }

}
