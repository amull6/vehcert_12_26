package cn.com.wz.vehcert.invoice

/**
 * @Description   车辆票据信息
 * @Create: 13-12-7 上午10:42  huxx
 */
class InvoiceInfo {
    String  id
    int resultCode
    String  message
    String typeCode
    String invNo
    UploadInvoice uploadInvoice
    static hasMany = [goodsInfos:GoodsInfo]

    static constraints = {
        resultCode  (blank: true,nullable: true)
        message  (blank: true,nullable: true)
        typeCode  (blank: true,nullable: true)
        invNo  (blank: false,nullable: false)

    }
    static mapping = {
        table 'tbl_invoiceInfo'
        // version is set to false, because this isn't available by default for legacy databases
        version true
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'
        resultCode (comment:"返回码，0成功，1部分成功或完全不成功")
        message (comment:"反馈信息")
        typeCode  (comment:"票据类别代码")
        invNo  (comment:"票据号")


        sort "invNo": 'desc'
    }
}
