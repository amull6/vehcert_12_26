package cn.com.wz.vehcert.paper
 import cn.com.wz.parent.system.user.User
class PaperService {

    /**
     * @Description  合格证纸张发放
     * @param params
     * @return
     * @create 2013-07-28 huxx
     */
    def save(params,user){
        //根据车间名称查询是否存在,存在更新，不存在创建

        if (params.provideId){
            def cel={
                userDetail{
                    eq('realName',"${params.provideId}")
                }

            }
            def list=User.createCriteria().list(cel)
            if (list&&list.size()>0){
                params.provideId=list.get(0).id
            }

        }
      def nums=Integer.parseInt(params.nums)
      params.nums=nums
        params.type=Integer.parseInt(params.type)
        Paper paper=new Paper(params)
        def provideTime=new Date().format("yyyy-MM-dd")
        paper.provideId=user.id
        paper.provideTime=provideTime.toString()
        def m=[:]
        if (paper.save(flush: true)){
            m.result=true
        }else{
            m.result=false
        }
        m.instance=paper
        return m
    }
    def saveReco(params)  {
        //根据车间名称查询是否存在,存在更新，不存在创建

        if (params.provideId){
            def cel={
                userDetail{
                    eq('realName',"${params.recoverId}")
                }

            }
            def list=User.createCriteria().list(cel)
            if (list&&list.size()>0){
                params.recoverId=list.get(0).id
            }

        }

        Paper paper=new Paper(params)
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
