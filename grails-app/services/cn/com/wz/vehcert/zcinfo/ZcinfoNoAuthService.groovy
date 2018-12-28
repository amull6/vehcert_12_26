package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.system.user.User

class ZcinfoNoAuthService {
    /**
     * @Description 免登录页面查询经销商更换数据service
     * @CreateTime 201
     */
    def selectZcInfoReplaceNoAuth(params,loginUser){
        def  aaa = params
        def sta = params.firstTime+" 00:00:00"
        def end = params.secondTime+" 23:59:59"

        def sta_1 = params.firstTime1+" 00:00:00"
        def end_1 = params.secondTime1+" 23:59:59"
        //添加区域和经销商的查询条件
        def distributor=params.distributor?.id
        def userR=params.user?.id
        def user=[]
        if (params.searchType=='1'){
            if (userR==''){
                def celU={
                    organs{
                        if(params.distributor){
                            eq('id',distributor)
                        }
                    }
                }
                user=User.createCriteria().list(celU)
            }else{
                def celU={
                    organs{
                        if(params.distributor){
                            eq('id',distributor)
                        }
                    }
                    userDetail{
                        if(params.user){
                            eq('id',userR)
                        }
                    }
                }
                user=User.createCriteria().list(celU)
            }
        }
        int type=0
        if (params.hidden_type){
            type = Integer.parseInt(params.hidden_type)   ///标识查询类型  1 普通变更类型    2 日期变更类型
        }

        def cel = {
            eq("veh_usertype",0)    //经销商修改数据
            if(params.veh_Dphgzbh){
                like('veh_Dphgzbh',"%${params.veh_Dphgzbh}%")
            }
            if(params.veh_Zchgzbh){
                like('veh_Zchgzbh',"%${params.veh_Zchgzbh}%")
            }
            if(params.veh_Clsbdh){
                like('veh_Clsbdh',"%${params.veh_Clsbdh}%")
            }
            if(params.veh_Fdjh){
                like('veh_Fdjh',"%${params.veh_Fdjh}%")
            }
            if(params.firstTime){
                ge('createTime',sta)
            }
            if(params.secondTime){
                le('createTime',end)
            }
            if(params.firstTime1){
                ge('authTime',sta_1)
            }
            if(params.secondTime1){
                le('authTime',end_1)
            }

            if (params.veh_Zchgzbh_rq){
                like('veh_Zchgzbh',"%${params.veh_Zchgzbh_rq}%")
            }
            if (params.veh_Xnhgzbh_rq){
                like('veh_Xnhgzbh',"%${params.veh_Xnhgzbh_rq}%")
            }
            if (params.veh_Clxh_rq){
                like('veh_Clxh',"%${params.veh_Clxh_rq}%")
            }
            if (params.veh_Fdjxh_rq){
                like('veh_Fdjxh',"%${params.veh_Fdjxh_rq}%")
            }

            if(params.startTime1){
                ge('veh_Fzrq',params.startTime1)
            }
            if(params.endTime1){
                le('veh_Fzrq',params.endTime1)
            }
            if(params.startTime2){
                ge('veh_Fzrq_R',params.startTime2)
            }
            if(params.endTime2){
                le('veh_Fzrq_R',params.endTime2)
            }
            if (params.searchType=='0'){ //0表示经销商查看个人的申请记录 1表示查询全部满足条件的经销商的记录
                if(!params?.systemRole){
                    eq('createrId',params?.userId)  //searchType=0为经销上查看，如果当前用户没有系统管理员角色，只查看自己申请的更换，
                    // 如果有系统管理员帐号查看所有更换记录
                }

            }else{
                if(params.distributor&&distributor){
                    if (user.size()>0){
                        or{
                            user?.each{u->
                                eq('createrId',u.id)
                            }
                        }
                    } else{
                        eq('createrId','')
                    }
                }
            }

            //判定是否审核通过条件
            if(params.veh_coc_status){
                eq('veh_coc_status',Integer.parseInt(params.veh_coc_status))
            }else{  //全部的，查询veh_coc_statu=0、1、2、3的
                inList("veh_coc_status", [0,1,2,3])
            }
            ///标识不是日期变更的字段
            if (type==1){
                ne('change_Field',"veh_Fzrq")
            }
            if (type==2){
                eq('change_Field',"veh_Fzrq")
            }
            order(params.sort,params.order)
            order("createTime","desc")
        }

        def results
        try{
            results = ZCInfoReplace.createCriteria().list(params,cel)
        }catch(Exception e){
            e.printStackTrace()
            e.cause?.printStackTrace()
        }
        return results
    }
}
