package cn.com.wz.vehcert.zcinfo

class NZInfoReplace {
    //变更前合格证信息
    String id
    String change_Field="def"    //更换申请变更字段， 此类型不允许为空    desc : "def"标识在默认下的类型,是指车型、出厂日期、发动机号变更
                                          //  当是车辆编号变更：“veh_Clbh”

    String veh_Cx          //车型
    String veh_Clbh       //车辆编号
    String veh_Fdjh       //发动机号
    String veh_Dph        //底盘号
    String veh_workshopno //生产车间编号
    String veh_Jcy        //检查员
    String veh_Ccrq       //出厂日期
    String veh_Zxbz       //执行标准
    String veh_Hgzbh     //合格证编号

    String SAP_No      // SAP中的车辆唯一号
    String SAP_status='0'  //根据调用SAP返回的结果，显示传送状态：0未同步、1已同步，同步失败也是未同步
    String transDate       //传输到SAP的时间

    //变更后合格证信息
    String veh_Cx_R          //车型
    String veh_Clbh_R       //车辆编号
    String veh_Fdjh_R       //发动机号
    String veh_Dph_R        //底盘号
    String veh_Jcy_R        //检查员
    String veh_Ccrq_R       //出厂日期
    String veh_Zxbz_R       //执行标准
    String veh_Hgzbh_R     //合格证编号

    int area_status=0     //区域经理审核 0：待审核 1审核通过 2审核不通过，默认值为0
    int auth_status=3    //审核状态 0：待审核 1审核通过 2审核不通过 ，默认值为3
    String createTime //申请时间
    String createrId //申请人id
    String createName   //申请人姓名
    String createrOrgan //申请人所属区域，需要区域经理审批
    String remark  //申请备注
    String salesManName //业务员姓名
    String salesManPhone //业务员电话

    String authTime   //审核时间 （处理人）
    String area_AuthTime //区域经理审核时间

    String isPrint='0'  //更换后的合格证是否打印

    String authId     //审核人   （处理时间）
    String area_AuthId //区域经理审核ID
    String area_Remark      //区域经理备注
    String auth_Remark //审核备注

    static constraints = {
         change_Field (blank: true,nullable: true)

         veh_Cx (blank: true,nullable: true)
         veh_Clbh  (blank: true,nullable: true)
         veh_Fdjh (blank: true,nullable: true)
         veh_Dph  (blank: true,nullable: true)
         veh_workshopno (blank: true,nullable: true)
         veh_Jcy     (blank: true,nullable: true)
         veh_Ccrq  (blank: true,nullable: true)
         veh_Zxbz   (blank: true,nullable: true)
         veh_Hgzbh    (blank: true,nullable: true)

         SAP_No     (blank: true,nullable: true)
         SAP_status (blank: true,nullable: true)
         transDate   (blank: true,nullable: true)

        //变更后合格证信息
         veh_Cx_R   (blank: true,nullable: true)
         veh_Clbh_R (blank: true,nullable: true)
         veh_Fdjh_R   (blank: true,nullable: true)
         veh_Dph_R    (blank: true,nullable: true)
         veh_Jcy_R     (blank: true,nullable: true)
         veh_Ccrq_R   (blank: true,nullable: true)
         veh_Zxbz_R   (blank: true,nullable: true)
         veh_Hgzbh_R  (blank: true,nullable: true)

         area_status(blank: true,nullable: true)
         auth_status (blank: true,nullable: true)
         createTime (blank: true,nullable: true)
         createrId (blank: true,nullable: true)
        createrOrgan(blank: true,nullable: true)
         createName   (blank: true,nullable: true)
         remark (blank: true,nullable: true)
        salesManName(blank: true,nullable: true)
        salesManPhone (blank: true,nullable: true)

         authTime   (blank: true,nullable: true)
        area_AuthTime(blank: true,nullable: true)
        area_AuthId(blank: true,nullable: true)
         authId     (blank: true,nullable: true)
        area_Remark  (blank: true,nullable: true)
         auth_Remark (blank: true,nullable: true)

        isPrint(blank: true,nullable: true)
    }

    static mapping = {
        table 'WZH_NZZCINFO_REPLACE'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'
        change_Field (comment:'变更类型')

        veh_Cx (comment:'原车型')
        veh_Clbh  (comment:'原车辆编号')
        veh_Fdjh  (comment:'原车发动机号')
        veh_Dph   (comment:'原底盘号')
        veh_workshopno  (comment:'生产车间编号')
        veh_Jcy      (comment:'检查员')
        veh_Ccrq   (comment:'原出厂日期')
        veh_Zxbz    (comment:'原执行标准')
        veh_Hgzbh     (comment:'合格证编号')

        SAP_No      (comment:'SAP唯一号')
        SAP_status  (comment:'SAP传输状态')
        transDate    (comment:'传输时间')

        //变更后合格证信息
        veh_Cx_R    (comment:'更换后车型')
        veh_Clbh_R  (comment:'更换后车辆编号')
        veh_Fdjh_R   (comment:'更换后发动机号号')
        veh_Dph_R    (comment:'更换后底盘号号')
        veh_Jcy_R    (comment:'更换后检查员')
        veh_Ccrq_R  (comment:'更换后出厂日期')
        veh_Zxbz_R   (comment:'更换后执行标准')
        veh_Hgzbh_R  (comment:'更换后合格证编号')


        createTime (comment:'申请时间')
        createrId (comment:'申请人Id')
        createName (comment:'申请经销商所属组织id')
        createrOrgan(comment:'创建更换记录人的组织id')
        remark (comment:'申请备注')
        salesManName(comment:'业务员姓名')
        salesManPhone (comment:'业务员电话')

        authTime  (comment:'审核时间')
        authId     (comment:'审核人ID')
        area_AuthTime (comment:'区域经理审核时间')
        area_AuthId(comment:'区域经理审核ID')
        auth_Remark (comment:'审核备注')
        area_Remark  (comment:'区域经理审核备注备注')
        area_status (comment:'区域经理审核 0：待审核 1审核通过 2审核不通过')
        auth_status (comment:'审核状态 0：待审核 1审核通过 2审核失败')

        isPrint(comment:'更换后的合格证是否已经打印，0未打印 1已打印')
    }
}
