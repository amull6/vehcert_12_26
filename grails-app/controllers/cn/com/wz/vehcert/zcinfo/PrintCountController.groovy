package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.zcinfo.DistributorPrintCount
import grails.converters.JSON
import org.apache.axis.encoding.ser.ElementSerializer
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils
import parent.poi.event.ExcelReaderUtil
import parent.poi.event.IRowReader
import parent.poi.event.RowReader

/**
 *  经销商自助打印次数控制器
 * @param
 * @return
 * @create  2016-12-20 Qj
 */
class PrintCountController extends BaseController{
    def newDmsSynService
    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther Qj
     *@createTime 2016-12-21
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/zcinfo/dealer/printManage/list",model: params)
    }
    /**
     *@Description 跳转到经销商申请list页面
     *@param
     *@return
     *@Auther Qj
     *@createTime 2019-01-27
     */
    def applyList() {
        render (view: "/cn/com/wz/vehcert/zcinfo/dealer/applyPrint",model: params)
    }

    def index() {
        redirect(action: "list", params: params)
    }


    /**
     *@Description 获取列表信息
     *@Auther zhuwei
     *@createTime 2016-12-20
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        def dd=params
        def cel = {
            if(params.veh_Zchgzbh_0){
                eq("veh_Zchgzbh_0","${params.veh_Zchgzbh_0}")
            }
            //不显示经销商初始化申请
            ne("status","5")
            order("application_Time","asc")
        }
        def results = DistributorPrintCount.createCriteria().list(params,cel)
        def rows=[];
        for(DistributorPrintCount tmp in results){
            def map=[:]
            map.id=tmp.id;
            map.veh_Zchgzbh_0=tmp.veh_Zchgzbh_0;
            map.veh_Clsbdh=tmp.veh_Clsbdh;
            map.printCount=tmp.printCount;
            map.limitPrintCount=tmp.limitPrintCount;
            map.veh_Type=tmp.veh_Type;

            map.SAP_No=tmp.SAP_No;
            map.status=tmp.status;
            map.applicant=tmp.applicant;
            map.application_Time=tmp.application_Time;
            rows.add(map)
        }
        def result = [rows: rows, total: results.totalCount]

        render result as JSON
    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
    /**
     * @Description 查看详细信息
     * @CreateTime 2016-12-21 by zhuwei
     * @return
     */
    def show(){
        def distributorPrintCount=DistributorPrintCount.get(params.id)
        render (view:'/cn/com/wz/vehcert/zcinfo/dealer/printManage/show',model: [distributorPrintCount:distributorPrintCount])
    }
    /**
     * @Description 跳转到更新页面
     * @return
     * @create 2016-12-21  zhuwei
     */
    def edit() {
        def distributorPrintCount = DistributorPrintCount.get(params.id)
        if (!distributorPrintCount) {
            redirect(action: "list")
            return
        }
        render (view:'/cn/com/wz/vehcert/zcinfo/dealer/printManage/edit',model: [distributorPrintCount:distributorPrintCount])
    }
    /**
     * @DEscription 修改数据
     * @return
     */
    def update() {
        def params = params
        def distributorPrintCountInstance = DistributorPrintCount.get(params.id)
        if (params.version&&params.version<distributorPrintCountInstance.version){
            flash.message='记录已过期！'
            return render (view:'/cn/com/wz/vehcert/zcinfo/dealer/printManage/edit',model: [distributorPrintCount:distributorPrintCountInstance])
        }
        try{
            int a = Integer.parseInt(params.limitPrintCount.trim());
            int b = distributorPrintCountInstance.printCount
            if(a<b){
                flash.message='限制次数不能小于实际打印次数！'
                return render (view:'/cn/com/wz/vehcert/zcinfo/dealer/printManage/edit',model: [distributorPrintCount:distributorPrintCountInstance])
            }else{
                distributorPrintCountInstance.limitPrintCount=a
            }
        }catch(Exception e){
            flash.message='请输入正整数！'
            return render (view:'/cn/com/wz/vehcert/zcinfo/dealer/printManage/edit',model: [distributorPrintCount:distributorPrintCountInstance])
        }
        if (!distributorPrintCountInstance.save(flush: true)){
            return render (view:'/cn/com/wz/vehcert/zcinfo/dealer/printManage/edit',model: [distributorPrintCount:distributorPrintCountInstance])
        }else{
            flash.message="更新成功！"
            redirect(action: "show", id: distributorPrintCountInstance.id)
        }
    }
    /**
     * @Descriptions 经销商申请打印合格证通过增加打印次数一次
     * @Auther QJ
     * @createTime 2019-01-25
     * @Update
     */
    def gotoPass() {
        def distributorPrintCount = DistributorPrintCount.get(params.id)
        distributorPrintCount.limitPrintCount=distributorPrintCount.limitPrintCount+1
        def nowTime= DateUtil.getCurrentTime()
        distributorPrintCount.auth_Time=nowTime
        distributorPrintCount.status='2'
        if(distributorPrintCount.save(flush: true)){
            render "success"
        }else{
            render "fail"
        }
    }
    /**
     * @Descriptions 拒绝经销商申请的增加打印次数一次
     * @Auther QJ
     * @createTime 2019-01-25
     * @Update
     */
    def refuse() {
        def distributorPrintCount = DistributorPrintCount.get(params.id)
        distributorPrintCount.limitPrintCount=distributorPrintCount.printCount
        distributorPrintCount.status='3'
        def nowTime= DateUtil.getCurrentTime()
        distributorPrintCount.auth_Time=nowTime
        if(distributorPrintCount.save(flush: true)){
            render "success"
        }else{
            render "fail"
        }
    }
    /**
     *@Description 经销商获取列表信息
     *@Auther zhuwei
     *@createTime 2016-12-20
     */
    def zcinfoList(){
        def user = session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser = User.get(user.id)
        def newList = []
        def vinList = newDmsSynService.findVinOfDistributor(loginUser.userName)
        if (vinList.size()>0){
            for(m in vinList){
                newList.add(m.new_vin)
                List ZcinfoList = ZCInfo.findAllByVeh_Clsbdh(m.new_vin)
                ZcinfoList?.each { zcinfo ->
                    def distributorPrintCount = DistributorPrintCount.findByVeh_Zchgzbh_0(zcinfo.veh_Zchgzbh_0)
                    if (!distributorPrintCount) {
                        DistributorPrintCount newDistributorPrintCount = new DistributorPrintCount();
                        newDistributorPrintCount.veh_Zchgzbh_0 = zcinfo.veh_Zchgzbh_0
                        newDistributorPrintCount.veh_Clsbdh = zcinfo.veh_Clsbdh
                        newDistributorPrintCount.veh_Type = zcinfo.veh_Type
                        newDistributorPrintCount.printCount = 0
                        newDistributorPrintCount.limitPrintCount = 0
                        newDistributorPrintCount.SAP_No = zcinfo.SAP_No
                        newDistributorPrintCount.status = '5'
                        ///当前登录用户信息
                        newDistributorPrintCount.applicant = loginUser.userDetail.realName   ///申请人姓名
                        newDistributorPrintCount.save(flush: true)
                    }
                }
            }
        }
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        def dd=params
        def cel = {
            if(params.veh_Zchgzbh_0){
                eq("veh_Zchgzbh_0","${params.veh_Zchgzbh_0}")
            }
            eq("applicant",loginUser.userDetail.realName)
            //不显示经销商初始化申请
            order("application_Time","asc")
        }
        def results = DistributorPrintCount.createCriteria().list(params,cel)
        def rows=[];
        for(DistributorPrintCount tmp in results){
                def map=[:]
                map.id=tmp.id;
                map.veh_Zchgzbh_0=tmp.veh_Zchgzbh_0;
                map.veh_Clsbdh=tmp.veh_Clsbdh;
                map.printCount=tmp.printCount;
                map.limitPrintCount=tmp.limitPrintCount;
                map.veh_Type=tmp.veh_Type;

                map.SAP_No=tmp.SAP_No;
                map.status=tmp.status;
                map.applicant=tmp.applicant;
                map.application_Time=tmp.application_Time;
                rows.add(map)
        }
        def result = [rows: rows, total:results.totalCount]

        render result as JSON
    }
    /**
     * @Description 批量申请自助打印
     *@param params
     */
    def applyBatch(){
        def result = [:]
        def ids=params.applyIds.split("_")
        def message=""
        def count=0
        def successCount=0
        ///当前登录用户信息
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def nowTime= DateUtil.getCurrentTime()
        ids?.each{
            def distributorPrintCount= DistributorPrintCount.get(it)
            if(distributorPrintCount.status=='0'||distributorPrintCount.status=='1'){
                result.put("msg", '所选数据存在已申请自主打印合格证');
                result.put("flag", '0')
                return render(result as JSON)
            }else{
                if(distributorPrintCount.limitPrintCount==0){
                    distributorPrintCount.status=0
                    distributorPrintCount.applicant = loginUser.userDetail.realName   ///申请人姓名
                    distributorPrintCount.application_Time = nowTime
                }else{
                    distributorPrintCount.status=1
                    distributorPrintCount.applicant = loginUser.userDetail.realName   ///申请人姓名
                    distributorPrintCount.application_Time = nowTime
                }
            }
            if(distributorPrintCount.save(flush: true)){
                successCount+= 1
            }else{
                count += 1
                message += "【${distributorPrintCount.veh_Zchgzbh_0}申请失败，"
            }
        }
        if (!count){
            message="提交${successCount}条申请成功！"
        }
        render message
    }
}
