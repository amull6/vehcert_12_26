package cn.com.wz.vehcert.invoice

import org.springframework.orm.hibernate3.support.ClobStringType

/**
 * @Description 票据上传记录       上传时用户先将票据加密文件打成zip包，再进行上传所以一次票据上传记录就对应一个zip压缩文件
 * @Create: 13-12-7 上午9:06  huxx
 */
class UploadInvoice {
    String id
    String filePath
    String fileName
    int resultCode
    String message
    String uploaderName
    String uploadTime

    static constraints = {
        filePath  (blank: false,nullable: false)
        fileName  (blank: false,nullable: false)
        resultCode  (blank: true,nullable: true)
        message  (blank: true,nullable: true)
        uploaderName  (blank: false,nullable: false)
        uploadTime  (blank: false,nullable: false)

    }
    static mapping = {
        table 'tbl_uploadInfo'
        // version is set to false, because this isn't available by default for legacy databases
        version true
        // In case a sequence is needed, changed the identity generator for the following code:
        //id generator:'sequence', column:'DIC_VALUE_ID', params:[sequence:'COM_DICTIONARY_VALUE_sequence']
        id generator:'uuid.hex', column:'id'
        resultCode (comment:"返回码，0成功，1部分成功或完全不成功")
        message (comment:"反馈信息")
        uploaderName (comment:"上传人用户名")
        uploadTime (comment:"上传时间")


        sort "uploadTime": 'desc'

        message type: ClobStringType
    }
}
