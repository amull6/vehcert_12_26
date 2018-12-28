package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.system.user.User

class NzZcinfoReplaceService {

    def areaSelectNzInfoReplace(params,loginUser) {
        def sta = params.firstTime+" 00:00:00"
        def end = params.secondTime+" 23:59:59"
          def aaa=params
        def cel = {
                if(params.veh_Clbh){
                    like('veh_Clbh',"%${params.veh_Clbh}%")
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
                if (params.searchType=='0'){ //0表示经销商查看个人的申请记录 1表示查询全部满足条件的经销商的记录
                    eq('createrId',loginUser.id)
                    if(params.area_status){
                        eq('area_status',Integer.parseInt(params.area_status))  //销售商查询自己申请记录审核情况
                    }
                }else if(params.searchType=='1'){
                    if(params?.nzAreaManager){
                        if (params.organId) {    //判断区域经理的审核权限
                            inList("createrOrgan", params.organId)
                        }
                        //判定是否审核通过条件
                        if(params.area_status){
                            eq('area_status',Integer.parseInt(params.area_status))
                        }
                    }else{ //没有区域经理这个角色的，查看全部区域经理已经审核通过的 ,营销公司审核
                        if(params.auth_status){
                            eq('auth_status',Integer.parseInt(params.auth_status))  //营销公司为审核
                            eq('area_status',1)
                        }
                    }
                }else if(params.searchType=='2'){
                    if(params.auth_status){
                        eq('auth_status',Integer.parseInt(params.auth_status))  //更换打印列表
                    }
                    if(params?.isPrint){
                        like('isPrint',"%${params.isPrint}%")
                    }

                }

                order(params.sort,params.order)
                order("createTime","desc")
         }

        def results
        try{
            results = NZInfoReplace.createCriteria().list(params,cel)
        }catch(Exception e){
            e.printStackTrace()
            e.cause?.printStackTrace()
        }
        return results
    }

}
