package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.carstorage.PreCarStorage
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate

/**
 * @Description:
 * @Create: 13-9-6 上午9:03   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class ZcInfoReplaceService {
    def dataSource;
    /**
     * @Description 根据查询条件获取更换记录
     * @param params
     * @Create 2013-09-06 huxx
     * @Update 将特殊更换的合格证更新记录信息也在【合格证更换申请查询】菜单中显示已，这个菜单有供应商和更换审核菜单中使用
     * UpdateTime 2015-01-13 zhuwei
     * @Update 汽车产品管理部在审核界面只能看到汽车的信息，农用车产品管理部的只能看到农用车
     * UpdateTime 2018-03-14 qj
     *
     */
    def selectZcInfoReplace(params,loginUser){
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
            //营销公司产品管理部划分农用车和汽车，农用车产品管理部只能审核农用车数据，汽车产品管理部只能审核汽车数据
            String findCodeStr="Select a.ORGAN_CODE as organCode,a.ID from SYS_ORGAN a ,SYS_ORGAN_USERS b where b.ORGANIZATION_ID =a.ID  and  b.USER_ID = '${loginUser.id}'"
            def template=new JdbcTemplate(dataSource)
            List codeList = [];
            List organList = template.queryForList(findCodeStr)
            if(organList!=null&&organList.size()!=0){
                for(def organ in organList){
                    def a =organ.get('ORGANCODE')
                   codeList.add(a);
                }
            }
            def flag
            if(codeList!=null&&codeList.size()!=0){
                if(codeList.contains('qccp')&&codeList.contains('nyccp')){
                    flag=0
                }else{
                    if(codeList.contains('qccp')){
                        flag=1
                    }else if(codeList.contains('nyccp')){
                        flag=2
                    }else{
                        flag=0
                    }
                }
            }else{
                flag=0
            }
            if( flag==1){
                eq('veh_Type_R','0')
            }else if(flag==2){
                eq('veh_Type_R','1')
            }
            eq("veh_usertype",0)
            if(params.veh_Dphgzbh){
                like('veh_Dphgzbh',"%${params.veh_Dphgzbh}%")
            }
            if(params.veh_Zchgzbh){
                like('veh_Zchgzbh',"%${params.veh_Zchgzbh}%")
            }
            if(params.formal){
                eq('formal',params.formal)
            }
            if(params.veh_Clsbdh){
                like('veh_Clsbdh',"%${params.veh_Clsbdh}%")
            }
            if(params.veh_Zchgzbh_0_R){
                like('veh_Zchgzbh_0_R',"%${params.veh_Zchgzbh_0_R}%")
            }
            if(params.veh_Clsbdh_R){
                like('veh_Clsbdh_R',"%${params.veh_Clsbdh_R}%")
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
                    eq('createrId',loginUser.id)  //searchType=0为经销上查看，如果当前用户没有系统管理员角色，只查看自己申请的更换，
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
            order("createTime","asc")
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
    /**
     * @Description 二次更换区域经理审核列表方法，包括区域经理，产品管理部列表的查询方法
     * @createTime 2014-11-26 zhuwei
     * @Update 汽车产品管理部在审核界面只能看到汽车的信息，农用车产品管理部的只能看到农用车
     * UpdateTime 2018-03-14 qj
     */
    def areaManagerAuthList(params,loginUser) {
        def sta = params.firstTime+" 00:00:00"
        def end = params.secondTime+" 23:59:59"
        def aaa=params
        def cel = {
            //营销公司产品管理部划分农用车和汽车，农用车产品管理部只能审核农用车数据，汽车产品管理部只能审核汽车数据
            String findCodeStr="Select a.ORGAN_CODE as organCode,a.ID from SYS_ORGAN a ,SYS_ORGAN_USERS b where b.ORGANIZATION_ID =a.ID  and  b.USER_ID = '${loginUser.id}'"
            def template=new JdbcTemplate(dataSource)
            List codeList = [];
            List organList = template.queryForList(findCodeStr)
            if(organList!=null&&organList.size()!=0){
                for(def organ in organList){
                    def a =organ.get('ORGANCODE')
                    codeList.add(a);
                }
            }
            def flag
            if(codeList!=null&&codeList.size()!=0){
                if(codeList.contains('qccp')&&codeList.contains('nyccp')){
                    flag=0
                }else{
                    if(codeList.contains('qccp')){
                        flag=1
                    }else if(codeList.contains('nyccp')){
                        flag=2
                    }else{
                        flag=0
                    }
                }
            }else{
                flag=0
            }
            if( flag==1){
                eq('veh_Type_R','0')
            }else if(flag==2){
                eq('veh_Type_R','1')
            }
            if(params.firstTime){
                ge('createTime',sta)
            }
            if(params.secondTime){
                le('createTime',end)
            }
            if(params.veh_Clsbdh){
                like('veh_Clsbdh',"%${params.veh_Clsbdh}%")
            }
            if(params.veh_Fdjh){
                like('veh_Fdjh',"%${params.veh_Fdjh}%")
            }

            if (params.searchType=='0'){ //0表示经销商查看个人的申请记录
                eq('createrId',loginUser.id)
                if(params.auth_status){
                    eq('auth_status',Integer.parseInt(params.auth_status))  //销售商查询自己申请记录审核情况，用营销公司最终打印合格证的状态筛选
                }
            }else if(params.searchType=='1'){  //1区域经理查看自己区域的申请记录
                if(params?.areaManager){
                    if (params.organId.size()>0) {  //   如果不是区域经理看到区域审核列表，那么area_status参数为空，searchType参数为1，查看多有区域审核信息
                        inList("createrOrgan", params.organId)
                    }
                }
                if(params.area_status){
                    if(params.area_status.toString().equals('All')){  //因为在product_auth_status默认值为3，只有区域经理审核通过后该值被置为0
                        inList("area_status", [0,1,2])
                    }else{
                        eq('area_status',Integer.parseInt(params.area_status))
                    }

                }
                eq('replaceType','1')

            }else if(params.searchType=='2'){ //营销公司 产品管理部审核，查看通过区域经理审核的二次更换记录
                if(params.product_auth_status){
                    if(params.product_auth_status.toString().equals('All')){  //因为在product_auth_status默认值为3，只有区域经理审核通过后该值被置为0
                        inList("product_auth_status", [0,1,2])
                    }else{
                        eq('product_auth_status',Integer.parseInt(params.product_auth_status))
                    }

                }
                eq('replaceType','1')     //查询所有二次更换的合格证信息
            }
             if(params?.veh_coc_status){    //最终在二次更换审核状态，用于
                 eq('veh_coc_status',Integer.parseInt(params.veh_coc_status))
             }
            order(params.sort,params.order)
            order("createTime","asc")
        }

        def results = ZCInfoReplace.createCriteria().list(params,cel)
        return results
    }
    /**
     * @Description 审核验证PDF合格证更换申请时公告批次与公告库中的信息是否匹配
     * @param carStorage
     * @Create 2017-8-24huxx
     */
    def  checkGgpc(params){
        StringBuffer sb=new StringBuffer()
        PreCarStorage preCarStorage=new PreCarStorage()
        if(params.isAll=='1'){
            def zciInfoModelOther
            def celOther = {
                eq('id', params.otherid)
            }
            zciInfoModelOther = ZCInfo.createCriteria().list(celOther)?.get(0)
            def otherGgpc =zciInfoModelOther.veh_Ggpc
            PreCarStorage otherPreCarStorge = new PreCarStorage()
            if("QX".equals(params.veh_Clztxx)){
                def dpxh
                if(params.veh_Dpxh==''){
                    dpxh=null
                }else{
                    dpxh=params.veh_Dpxh
                }
                preCarStorage=PreCarStorage.findByVeh_ClxhAndVeh_Dpxh(params.veh_Clxh,dpxh)
                otherPreCarStorge=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(zciInfoModelOther.veh_Dpid,'DP')
                if (!preCarStorage){
                    sb.append("整车合格证车辆型号与公告库不符！")
                }else{
                    if (!preCarStorage.veh_Ggpc){
                        if (params.veh_Ggpc){
                            sb.append("整车合格证公告批次与公告库不符!是否继续？")
                        }
                    }else{
                        if (!preCarStorage.veh_Ggpc?.equals(params.veh_Ggpc)){
                            sb.append("该整车合格证公告批次已经过期，请重新选择公告申请")
                        }
                    }
                }
                if (!otherPreCarStorge){
                    sb.append("底盘合格证车辆型号与公告库不符！")
                }else{
                    if (!otherPreCarStorge.veh_Ggpc){
                        if (otherGgpc){
                            sb.append("底盘合格证公告批次与公告库不符!是否继续？")
                        }
                    }else{
                        if (!otherPreCarStorge.veh_Ggpc?.equals(otherGgpc)){
                            sb.append("该底盘合格证公告批次已经过期，请重新选择公告申请")
                        }
                    }
                }
            }else if ("DP".equals(params.veh_Clztxx)){
                preCarStorage=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(params.veh_Dpid,'DP')
                def dpxh
                if(zciInfoModelOther.veh_Dpxh==''){
                    dpxh=null
                }else{
                    dpxh=zciInfoModelOther.veh_Dpxh
                }
                otherPreCarStorge=PreCarStorage.findByVeh_ClxhAndVeh_Dpxh(zcinfo.veh_Clxh,dpxh)
                if (!preCarStorage){
                    sb.append("底盘合格证车辆型号与公告库不符！")
                }else{
                    if (!preCarStorage.veh_Ggpc){
                        if (params.veh_Ggpc){
                            sb.append("底盘合格证公告批次与公告库不符!是否继续？")
                        }
                    }else{
                        if (!preCarStorage.veh_Ggpc?.equals(params.veh_Ggpc)){
                            sb.append("该底盘合格证公告批次已经过期，请重新选择公告申请")
                        }
                    }
                }
                if (!otherPreCarStorge){
                    sb.append("整车合格证车辆型号与公告库不符！")
                }else{
                    if (!otherPreCarStorge.veh_Ggpc){
                        if (otherGgpc){
                            sb.append("整车合格证公告批次与公告库不符!是否继续？")
                        }
                    }else{
                        if (!otherPreCarStorge.veh_Ggpc?.equals(otherGgpc)){
                            sb.append("该整车合格证公告批次已经过期，请重新选择公告申请")
                        }
                    }
                }
            }else{
                sb.append("合格证车辆状态信息错误")
            }

        }else{
            //获取分解前的公告信息
            if ("QX".equals(params.veh_Clztxx)){
                def dpxh
                if(params.veh_Dpxh==''){
                    dpxh=null
                }else{
                    dpxh=params.veh_Dpxh
                }
                preCarStorage=PreCarStorage.findByVeh_ClxhAndVeh_Dpxh(params.veh_Clxh,dpxh)
            }else if ("DP".equals(params.veh_Clztxx)){
                preCarStorage=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(params.veh_Dpid,'DP')
            }else{
                sb.append("合格证车辆状态信息错误")
                return sb.toString()
            }
            if (!preCarStorage){
                sb.append("车辆型号与公告库不符！")
            }else{
                if (!preCarStorage.veh_Ggpc){
                    if (params.veh_Ggpc){
                        sb.append("公告批次与公告库不符!是否继续？")
                    }
                }else{
                    if (!preCarStorage.veh_Ggpc?.equals(params.veh_Ggpc)){
                        sb.append("该车辆信息的公告批次已经过期，请重新选择公告申请")
                    }
                }
            }
        }
        return sb.toString()
    }
}
