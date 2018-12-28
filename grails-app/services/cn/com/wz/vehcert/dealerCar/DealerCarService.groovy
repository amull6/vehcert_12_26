package cn.com.wz.vehcert.dealerCar

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue
import  cn.com.wz.vehcert.dealercarstorage.DealerCar
import cn.com.wz.parent.system.user.User

class DealerCarService {

    /**
     * @Description  环保信息
     * @param params
     * @return
     * @create 2013-08-05 liuly
     */
    def save(params,updaterName){
        def parmas
        DealerCar en=null

            def celK={
                eq('dicValueNameC',"${params.kind}")
            }
            def listK=DictionaryValue.createCriteria().list(celK)
            print listK
            if (listK&&listK.size()>0){
                params.kind=listK.get(0)
                en=new DealerCar(params)
                en.updateTime=DateUtil.getCurrentTime()
                en.updaterName=updaterName
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
