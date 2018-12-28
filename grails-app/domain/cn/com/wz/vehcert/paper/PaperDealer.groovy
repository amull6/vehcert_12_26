package cn.com.wz.vehcert.paper
/**
 *@Description  经销商合格证纸张管理
 *@Auther liuly
 *@createTime 2013-11-05
 */
class PaperDealer {

    String id
    /**
     * 经销商纸张编号
     */
    String  paperNum
    /**
     * 合格证编号
     */
    String   zcinfoNum
    /**
     * VIN
     */
    String   vin
    /**
     * 0代表农用车整车合格证，1代表汽车整车合格证，2代表二类底盘合格证
     */
    int type=0
    /**
     * 创建人id
     */
    String    createrId
    /**
     * 创建时间
     */
    String createTime
    /**
     * 更新人id
     */
    String    updaterId
    /**
     * 更新时间
     */
    String updateTime
    static constraints = {
        paperNum(nullable: false)
        zcinfoNum(nullable: true)
        vin(nullable:true)
        type(nullable:true)
        createrId(nullable: false)
        createTime(nullable: false)
        updaterId(nullable: true)
        updateTime (nullable: true)
    }
    static mapping = {
        table 'TBL_PAPER_DEALER'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'

        paperNum    (comment: '纸张编号')
        zcinfoNum        (comment: '合格证编号')
        vin      (comment: 'VIN')
        type        (comment:'合格证类型')
        createrId (comment: '创建人id')
        createTime (comment: '创建人id')
        updaterId (comment:'更新人id')
        updateTime (comment: '更新时间')
    }
}

