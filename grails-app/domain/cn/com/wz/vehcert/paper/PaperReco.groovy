package cn.com.wz.vehcert.paper

class PaperReco {
    String id
    /**
     * 经销商编号
     */
    String  dealerId
    /**
     * 更换申请的id
     */
    String replaceId
    /**
     * 经销商名称
     */
    String   dealerName
    /**
     * 0代表农用车整车合格证，1代表汽车整车合格证，2代表二类底盘合格证
     */
    int type=0
    /**
     * 纸张编号
     */
    String   paperNum
    /**
     * 备注
     */
    String note
    /**
     * 回收人id
     */
    String recoverId
    /**
     *回收时间
     */
    String recoverTime

    static constraints = {
        dealerId(nullable: true)
        dealerName(nullable: true)
        paperNum(nullable: true)
        note(nullable: true)
        type(nullable:true)
        recoverTime(nullable: true)
        recoverId(nullable: true)
        replaceId(nullable: true)
    }
    static mapping = {
        table 'TBL_PAPER_REVERT'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'

        dealerId   (comment: '经销商编号')
        dealerName  (comment: '经销商名称')
        paperNum    (comment: '纸张编号')
        note        (comment: '备注')
        recoverTime (comment: '回收时间')
        type        (comment:'合格证类型')
        recoverId   (comment: '回收人id')
        replaceId(comment:'更换申请的Id')
    }
}