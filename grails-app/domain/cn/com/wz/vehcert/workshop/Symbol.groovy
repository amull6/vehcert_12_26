package cn.com.wz.vehcert.workshop

import cn.com.wz.parent.system.dictionary.DictionaryValue

class Symbol {
    /**
     * 表主键
     */
    String id
    /**
     * 对应符号
     */
    String symbol
    /**
     * 符号备注
     */
    String symbolNote
    /**
     * 车间编号的起始值
     */
    int startNum
    /**
     * 车间编号的最大值
     */
    int maxNum
    static belongsTo=[kind:DictionaryValue,workshop:Workshop]

    static constraints = {
        symbol(nullable: true)
        symbolNote(nullable: true)
        maxNum(nullable: true)
        startNum(nullable:true)
    }
    static mapping = {
        table 'TBL_SYMBOL'
        // version is set to false, because this isn't available by default for legacy databases
        version false
        id generator:'uuid.hex', column:'ID'
        symbol column: 'SYMBOL' (comment: '对应符号')
        symbolNote column: 'SYMBOL_NOTE' (comment: '符号备注')
        startNum column: 'START_NUM'   (comment:'车间编号起始值')
        maxNum column: 'MAX_NUM'      (comment:'车间编号最大值')
    }
}
