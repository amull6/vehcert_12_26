package cn.com.wz.vehcert.workshop

import cn.com.wz.parent.system.dictionary.DictionaryValue

/**
 * @Description 车间信息
 * @Author liuly
 * @createTime 2013-07-23
 */
class Workshop {
    /**
     * 表主键
     */
    String id
    /**
     *车间编号
     */
    String serialNum
    /**
     * 车间名称
     */
    String name
    /**
     * 备注
     */

    String note
    static constraints = {
          serialNum(nullable: false, blank: false,unique: true)
          name(nullable: false,blank: false)
           note(nullable:true)
    }
    static mapping = {
        table 'TBL_WORKSHOP'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'
        serialNum column: 'SERIAL_NUM'  (comment:'车间编号')
        name column: 'NAME'   (comment: '车间名称')
        note column: 'NOTE'    (comment: '备注')

    }
    /**
     * 返回字符串
     */
    String toString(){
        return "${name}"
    }
}
