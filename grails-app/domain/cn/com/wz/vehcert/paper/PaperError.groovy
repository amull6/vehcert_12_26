package cn.com.wz.vehcert.paper
 //打印错误纸张
class PaperError {
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
     * 合格证编号
     */
    String veh_Hgzbh
    /**
     * 纸张数量
     */
    int num=0
    /**
     * 0代表农用车整车合格证，1代表汽车整车合格证，2代表二类底盘合格证
     */
    int type=0
    /**
     * 备注
     */
    String note
    /**
     * 回收人id
     */
    String createrId
    /**
     *回收时间
     */
    String createTime

    static constraints = {
        dealerNum(nullable: true)
        dealerName(nullable: true)
        paperNum(nullable: true)
        note(nullable: true)
        type(nullable:true)
        createTime(nullable: true)
        createrId(nullable: true)
        veh_Hgzbh(nullable: true)
    }
    static mapping = {
        table 'TBL_PAPER_ERROR'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'

        dealerNum   (comment: '经销商编号')
        dealerName  (comment: '经销商名称')
        paperNum    (comment: '纸张编号')
        note        (comment: '备注')
        num         (comment: '纸张数量')
        type        (comment:'合格证类型')
        createTime (comment: '记录时间')
        createrId   (comment: '记录人id')
        veh_Hgzbh(comment:'合格证编号')
    }
}
