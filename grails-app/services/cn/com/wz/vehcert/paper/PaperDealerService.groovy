package cn.com.wz.vehcert.paper

import cn.com.wz.parent.system.user.User

class PaperDealerService {

    def save(params,user)  {
        //根据车间名称查询是否存在,存在更新，不存在创建
        PaperDealer paperDealer=null
        params.type=Integer.parseInt(params.type)
        paperDealer=new PaperDealer(params)
        def createTime=new Date().format("yyyy-MM-dd")
        paperDealer.createrId=user.id
        paperDealer.createTime=createTime.toString()
        def m=[:]
        if (paperDealer.save(flush: true)){
            m.result=true
        }else{
            m.result=false
        }
        m.instance=paperDealer
        return m
    }
    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-23
     */
    def list(params,user){
        print params
        def usercel=[]
        if(params.dealerName||params.dealerNum){
            def celUser={
                if(params.dealerNum){
                    like("userName","%${params.dealerNum}%")
                }
                if(params.dealerName){
                   userDetail{
                       like("realName","%${params.dealerName}%")
                   }
                }
            }
           usercel=User.createCriteria().list(celUser)
        }

        def cel={
            if(params.paperNum){
                like("paperNum","%${params.paperNum}%")
            }
            if(params.type&&params.type!='all'){
                eq('type',Integer.parseInt(params.type))
            }

            if (params.only){
                eq('createrId',user.id)
            }else{
                if(params.dealerName||params.dealerNum){
                    if (usercel.size()>0){
                        or{
                            usercel?.each{u->
                                eq('createrId',u.id)
                            }
                        }
                    } else{
                        eq('createrId','')
                    }
                }
            }
            order('paperNum')
            order('createrId')
        }
        def rows=PaperDealer.createCriteria().list(params,cel)

        def lst=[]

        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.paperNum=u.paperNum
            m.zcinfoNum=u.zcinfoNum
            m.vin =u.vin
            if(u.type==0){
                m.type='汽车整车'
            }else if(u.type==1){
                m.type='农用车整车'
            }else{
                m.type='二类底盘'
            }
            m. createTime=u.createTime
            def userP=User.get(u.createrId)
            if (userP){
                m.createrId=userP.userDetail.realName
                m.dealerNum= userP.userName
            }
            m. updateTime=u.updateTime
            def userU=User.get(u.updaterId)
            if (userU){
                m.updaterId=userU.userDetail.realName
            }
            lst.add(m)
        }
        def result = [rows:lst,total:rows.totalCount]
        return result
    }
}
