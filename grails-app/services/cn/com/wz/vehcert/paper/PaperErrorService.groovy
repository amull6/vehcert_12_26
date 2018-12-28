package cn.com.wz.vehcert.paper

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.system.user.User

class PaperErrorService {

    def save(params,user)  {
        //根据车间名称查询是否存在,存在更新，不存在创建
        PaperError paper=null
        if (params.dealerNum){
            def cel={
                eq('userName',"${params.dealerNum}")

            }
            def list=User.createCriteria().list(cel)
            if (list&&list.size()>0){
                params.num=Integer.parseInt(params.num)
                params.type=Integer.parseInt(params.type)
                paper=new PaperError(params)
                def createTime=new Date().format("yyyy-MM-dd")
                paper.createrId=user.id
                paper.createTime=createTime.toString()
            }

        }
        def m=[:]
        if (paper.save(flush: true)){
            m.result=true
        }else{
            m.result=false
        }
        m.instance=paper
        return m
    }
}
