package cn.com.wz.vehcert.invoice
/**
 * @Description 票据详细信息
 * @Create 2013-12-06 huxx
 */
class GoodsInfo {
    String id
    int resultCode      //反馈码 上传结果 0完全正确 1不正确
    String message         //反馈消息 成功时为空，失败时为失败原因
    String rowId           //明细ID
    String spmc             //商品名称
    String ggxh            // 规格型号
    int sl              //数量
    String hgzs            //合格证编号组成的字符串，用逗号分隔
    InvoiceInfo invoiceInfo     //所属票据
    String updateTime      //更新时间
    String updaterName      //更新人userName
    String needSol=1       //是否需要关联，0不需要，1需要
    String vins            //未关联的vin信息
    String fdjhs           //未关联的发动机号
    static constraints = {
        resultCode  (blank: true,nullable: true)
        message  (blank: true,nullable: true)
        rowId  (blank: true,nullable: true)
        spmc  (blank: true,nullable: true)
        ggxh  (blank: true,nullable: true)
        sl  (blank: true,nullable: true)
        hgzs  (blank: true,nullable: true)
        updateTime  (blank: true,nullable: true)
        updaterName  (blank: true,nullable: true)
        vins  (blank: true,nullable: true)
        fdjhs  (blank: true,nullable: true)

    }
    static mapping = {
        table 'tbl_goodsInfo'
        // version is set to false, because this isn't available by default for legacy databases
        version true
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'
        resultCode (comment:"返回码，0成功，1部分成功或完全不成功")
        message (comment:"反馈信息")
        rowId  (comment:"行明细")
        spmc  (comment:"商品名称")
        ggxh  (comment:"规格型号")
        sl  (comment:"数量")
        hgzs  (comment:"完整合格证编号，用逗号分隔")
        updateTime  (comment:"更新时间")
        updaterName  (comment:"更新人用户名")
        needSol     (comment:"需要关联，0不需要关联，1需要关联")
        sort "spmc": 'desc'
    }
}
