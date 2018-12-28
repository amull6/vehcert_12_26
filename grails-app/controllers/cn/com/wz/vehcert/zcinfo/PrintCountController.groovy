package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.zcinfo.DistributorPrintCount
import grails.converters.JSON
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
            distributorPrintCountInstance.limitPrintCount=a
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
}
