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
    int printCount       //打印次数
    int limitPrintCount //限制打印次数
    String SAP_No         //SAP序列号
    String status        // 0、打印初次申请状态 1、申请增加打印次数 2、通过状态 3 拒绝状态 4打印完毕状态5、初始打印记录状态
    String applicant    //申请人
    String application_Time   //申请时间
    String auth_Time   //审核时间
    //合格证是否打印
    static constraints = {
        veh_Type  (blank: true,nullable: true)
        veh_Zchgzbh_0 (blank: true,nullable: true)
        veh_Clsbdh  (blank: true,nullable: true)
        printCount (blank: true,nullable: true)
        SAP_No  (blank: true,nullable: true)
        limitPrintCount  (blank: true,nullable: true)
        status (blank: true,nullable: true)
        applicant  (blank: true,nullable: true)
        application_Time (blank: true,nullable: true)
        auth_Time (blank: true,nullable: true)
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
        printCount     (comment:'打印次数')
        SAP_No  (comment:'SAP序列号')
        limitPrintCount (comment:'限制打印次数')
        status (comment:'0、打印初次申请状态 1、申请增加打印次数 2、审核完毕状态')
        applicant (comment:'申请人')
        application_Time (comment:'申请时间')
        auth_Time (comment:'审核时间')
        sort "veh_Zchgzbh_0": 'desc'
    }

}
