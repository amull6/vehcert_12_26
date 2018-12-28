package cn.com.wz.vehcert.paper
/**
 *@Description  合格证纸张管理
 *@Auther liuly
 *@createTime 2013-07-29
 */
class Paper {
    String id
    /**
     * 经销商编号
     */
    String  dealerNum
    /**
    * 经销商名称
     */
    String   dealerName
    /**
     * 纸张编号
     */
    String   paperNum
    /**
     * 备注
     */
    String note
    /**
     * 状态   0代表发放 1代表未回收，2代表回收
     */
    int status=0
    /**
     * 0代表农用车整车合格证，1代表汽车整车合格证，2代表二类底盘合格证
     */
    int type=0
    /**
     * 回收时间
     */
    String    recoverTime
    /**
     * 发放时间
     */
    String provideTime
    /**
     * 领取人
     */
    String receiveName
    /**
     * 发放人id
     */
    String provideId
    /**
     * 确认时间
     */
    String confirmTime
    /**
     * 回收人id
     */
    String recoverId
    /**
     * 纸张数量
     */
    int nums
    /**
     * 编号范围
     */
    String numRand
    /**
     * 确认备注
     */
    String noteConfirm
    static constraints = {
        dealerNum(nullable: false)
        dealerName(nullable: false)
        paperNum(nullable: true)
        note(nullable: true)
        status(nullable:true)
        type(nullable:true)
        recoverTime(nullable: true)
        provideTime(nullable: true)
        receiveName(nullable: true)
        provideId (nullable: true)
        recoverId(nullable: true)
        nums(nullable: true)
        numRand(nullable: true)
        confirmTime(nullable: true)
        noteConfirm(nullable: true)
    }
    static mapping = {
        table 'TBL_PAPER'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'

        dealerNum   (comment: '经销商编号')
        dealerName  (comment: '经销商名称')
        paperNum    (comment: '纸张编号')
        note        (comment: '备注')
        status      (comment: '状态')
        type        (comment:'合格证类型')
        recoverTime (comment: '回收时间')
        provideTime (comment: '发放时间')
        confirmTime (comment:'确认时间')
        receiveName (comment: '领取人姓名')
        provideId   (comment: '发放人id')
        recoverId   (comment: '回收人id')
        nums        (comment: '纸张数量')
        numRand     (comment: '编号范围')
        noteConfirm (comment:'确认备注')
    }
}
