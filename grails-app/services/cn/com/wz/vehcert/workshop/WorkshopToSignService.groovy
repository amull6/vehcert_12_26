package cn.com.wz.vehcert.workshop

import cn.com.wz.parent.system.dictionary.DictionaryValue

/**
 * @Description 车间符号信息service
 * @create 2013-07-28 huxx
 */
class WorkshopToSignService {
    /**
     * @Description  根据车辆型号和底盘型号查询是否存在,存在更新，不存在创建
     * @param params
     * @return
     * @create 2013-07-28 huxx
     */
    def save(params){
        print params
        //根据车间号，不存在创建
        Workshop ws=null
        Symbol sl=null
        DictionaryValue d=null
        if (params.serialNum&&params.name){
            def cel={
                eq("serialNum","${params.serialNum}")
                eq("name","${params.name}")
            }
            def list=Workshop.createCriteria().list(cel)
            print list
            if(list && list.size()>0){
                d=DictionaryValue.findByDicValueNameC(params.kind)
                print d
            if (d){
                ws=list.get(0)
             }
           }

            def cel1={
                workshop{
                    eq("serialNum","${params.serialNum}")
                    eq("name","${params.name}")
                }
                kind{
                    eq('dicValueNameC',"${params.kind}")
                }
            }
            def listSy=Symbol.createCriteria().list(cel1)
            if(listSy && listSy.size()>0){
                  sl = listSy.get(0)
            }
        }
        def m=[:]
        if (sl){
            sl.symbol=params.symbol
            sl.symbolNote=params.symbolNote
            sl.workshop=ws
            sl.kind=d
            sl.startNum=Integer.parseInt(params.startNum)
            sl.maxNum=Integer.parseInt(params.maxNum)
            if (sl.save(flush: true)){
                m.result=true
            }else{
                m.result=false
            }
        }else{
            if (ws){
                def symbol=new Symbol()
                symbol.symbol=params.symbol
                symbol.symbolNote=params.symbolNote
                symbol.workshop=ws
                symbol.kind=d
                symbol.startNum=Integer.parseInt(params.startNum)
                symbol.maxNum=Integer.parseInt(params.maxNum)
                if (symbol.save(flush: true)){
                    m.result=true
                }else{
                    m.result=false
                }
            }else{
                m.result=false
            }
        }

        m.instance=ws
        return m
    }
}
