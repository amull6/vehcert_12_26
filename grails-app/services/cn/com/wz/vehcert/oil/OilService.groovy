package cn.com.wz.vehcert.oil

import cn.com.wz.parent.system.dictionary.DictionaryValue

class OilService {
    /**
     * @Description  环保信息
     * @param params
     * @return
     * @create 2013-08-05 liuly
     */
    def save(params){
    Oil en=null
    if (params.kind){
        def cel={
            eq('dicValueNameC',"${params.kind}")
        }
        def list=DictionaryValue.createCriteria().list(cel)
        if (list&&list.size()>0){
            params.kind=list.get(0)
            print params
            en=new Oil(params)
        }

    }
    def m=[:]
    if (en.save(flush: true)){
        m.result=true
    }else{
        m.result=false
    }
    m.instance=en
    return m
}
}
