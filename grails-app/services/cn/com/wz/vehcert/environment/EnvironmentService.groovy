package cn.com.wz.vehcert.environment

import cn.com.wz.parent.system.dictionary.DictionaryValue

class EnvironmentService {

    /**
     * @Description  环保信息
     * @param params
     * @return
     * @create 2013-08-05 liuly
     */
    def save(params){
        Environment en=null
        if (params.kind){
            def cel={
               eq('dicValueNameC',"${params.kind}")
            }
            def list=DictionaryValue.createCriteria().list(cel)
            if (list&&list.size()>0){
                params.kind=list.get(0)
                print params
                en=new Environment(params)
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
