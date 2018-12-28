package cn.com.wz.vehcert.zcinfo

class HarvestInfo {
    String id
    String veh_Cx          //车型
    String veh_Clbh       //车辆编号
    String veh_Fdjh       //发动机号
    String veh_Dph        //底盘号
    String veh_workshopno //生产车间编号
    String veh_Jcy        //检查员
    String veh_Ccrq       //出厂日期
    String veh_Zxbz       //执行标准
    String veh_Hgzbh     //合格证编号
    String processType='0' //生成类型 0正常生成；1出口车，仅仅对中拖i、大拖车间的生产产生影响

    String createTime   //创建时间
    String createrId    //创建ID

    String updateTime   //最后更新时间
    String updaterId     //最后更新人的userName

    String upload='0'   //0:未上传 1：上传成功 2：上传失败 ；3已撤销（仅车间使用）
    String isprint='0' //合格证是否打印，0为未打印，1为已打印

    String uploader_Id //上传人
    String uploader_Time //上传时间

    String SAP_No      // SAP中的车辆唯一号
    String SAP_status='0'  //根据调用SAP返回的结果，显示传送状态：0未同步、1已同步，同步失败也是未同步
    String transDate       //传输到SAP的时间
    String envirCode     //环保信息代码
    String veh_Jxdl     //机械大类
    String veh_Rllx     //燃料类型
    String veh_Jxxlb    //机械小类别
    String veh_Zycs     //机械产品的主要参数
    String veh_Pfjd     //排放阶段
    String car_storage_type     //公告类型
    String veh_Fdjxh    //发动机型号
    String veh_new_clbh //车辆编号

    static constraints = {
        veh_Cx (blank: true,nullable: true)
        veh_Clbh (blank: true,nullable: true)
        veh_Fdjh (blank: true,nullable: true)
        veh_Dph (blank: true,nullable: true)
        veh_workshopno(nullable: true,blank: true)
        veh_Jcy (blank: true,nullable: true)
        veh_Ccrq (blank: true,nullable: true)
        veh_Zxbz (blank: true,nullable: true)
        veh_Hgzbh (blank: true,nullable: true)
        processType(blank: true,nullable: true)

        createTime  (blank: true,nullable: true)
        createrId    (blank: true,nullable: true)

        updateTime   (blank: true,nullable: true)
        updaterId    (blank: true,nullable: true)

        uploader_Id (blank: true,nullable: true)
        uploader_Time (blank: true,nullable: true)

        upload  (blank: true,nullable: true)
        isprint  (blank: true,nullable: true)

        SAP_No       (blank: true,nullable: true)
        SAP_status  (blank: true,nullable: true)
        transDate   (blank: true,nullable: true)
        envirCode   (blank: true,nullable: true)
        veh_Jxdl     (blank: true,nullable: true)
        veh_Rllx     (blank: true,nullable: true)
        veh_Jxxlb    (blank: true,nullable: true)
        veh_Zycs     (blank: true,nullable: true)
        veh_Pfjd     (blank: true,nullable: true)
        car_storage_type   (blank: false,nullable: false)
        veh_Fdjxh(blank: true, nullable: true)
        veh_new_clbh(blank: true, nullable: true)
    }

   static  mapping = {
       table 'WZH_HARVESTER_INFO'
       // version is set to false, because this isn't available by default for legacy databases
       version false
       // In case a sequence is needed, changed the identity generator for the following code:
       //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
       id generator:'uuid.hex', column:'HavesterInfoId'
       veh_Cx   (comment:'车型')
       veh_Clbh   (comment:'车辆编号')
       veh_Fdjh   (comment:'发动机号')
       veh_Dph   (comment:'底盘号')
       veh_workshopno  (comment:'检查员，先加上该字段，以备后用')
       veh_Jcy   (comment:'检查员，先加上该字段，以备后用')
       veh_Ccrq   (comment:'出厂日期，先加上该字段，以备后用')
       veh_Zxbz   (comment:'执行标准，先加上该字段，以备后用')
       veh_Hgzbh   (comment:'合格证编号，先加上该字段，以备后用')
       processType  (comment:'生成类型 0正常生成；1出口车，仅仅对中拖i、大拖车间的生产产生影响')

       createTime  (comment:'创建时间')
       createrId    (comment:'创建ID')

       updateTime   (comment:'更新时间')
       updaterId    (comment:'更新ID')

       upload  (comment:'0默认为为上传，1为已上传')
       isprint  (comment:'合格证是否打印，0为未打印，1为已打印')

       uploader_Id (comment:'上传人')
       uploader_Time (comment:'上传时间')

       SAP_No     (comment:'SAP中的车辆唯一号 ')
       SAP_status (comment:'根据调用SAP返回的结果，显示传送状态：0未同步、1已同步，同步失败也是未同步 ')
       transDate (comment:'传输到SAP的时间 ')
       envirCode (comment:'环保信息代码 ')
       veh_Jxdl     (comment:'机械大类')
       veh_Rllx     (comment:'燃料类型')
       veh_Jxxlb    (comment:'机械小类别')
       veh_Zycs     (comment:'机械产品的主要参数')
       veh_Pfjd     (comment:'排放阶段')
       car_storage_type (comment:"公告类型 0表示玉米收，1表示稻麦机")
       veh_Fdjxh(comment: '发动机型号')
       veh_new_clbh(comment: '新车辆编号')

       sort 'upload':'asc'
       sort "veh_Clbh": 'desc'
   }
}
