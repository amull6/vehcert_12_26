package cn.com.wz.vehcert.sap

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.LogService
import cn.com.wz.parent.system.user.User
import cn.com.wz.stvehcert.STInfo
import cn.com.wz.vehcert.carstorage.CarStorage
import cn.com.wz.vehcert.zcinfo.GarbageInfo
import cn.com.wz.vehcert.zcinfo.HarvestInfo
import cn.com.wz.vehcert.zcinfo.NZInfo
import cn.com.wz.vehcert.zcinfo.ZCInfo
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import com.sap.conn.jco.AbapException
import com.sap.conn.jco.JCoFunction
import com.sap.conn.jco.JCoParameterList
import com.sap.conn.jco.JCoStructure
import com.sap.conn.jco.JCoTable
import grails.converters.JSON
import newDms.NewTransChangeInfo
import sendDataToSap.SynDelivery

import java.text.SimpleDateFormat


/**
 * @Description 与sap进行数据同步的服务类
 * @Create: 14-5-22 下午4:42  huxx
 */
class SynService {
    def conManageService
    LogService logService

    /**
     * @Description 根据条件选择合格证信息进行传输
     * @param params
     * @CreateTime 2014-5-54
     * @Author zhuwei
     * Update 合格证传输至车间只传输自己车间生产的合格证，营销公司传输所有产生的合格证信息
     * UpdateTime 2014-10-28 zhuwei
     */
    def searchZcinfoByParams(params){
        def cel={
            if(params.organCode&&params.transFlag=='0'){  //给车间配置的传输菜单，opFlag参数为0；给营销公司配置的传输菜单opFlag参数为1,所以营销公司能够看全部的合格证传输信息
                                                           //对于system ，要么什么组织也不关联，要么关联上所有组织，这样system才能看全部信息，或者通过营销公司传输列表看
                inList("veh_workshopno", params.organCode)
            }
            if(params.veh_Zchgzbh){
                like("veh_Zchgzbh", "%${params.veh_Zchgzbh}%")
            }
            if(params.veh_Clsbdh){
                like("veh_Clsbdh", "%${params.veh_Clsbdh}%")
            }
            if(params.veh_Fdjh){
                like("veh_Fdjh", "%${params.veh_Fdjh}%")
            }
            if(params.SAP_No){
                like("SAP_No", "%${params.SAP_No}%")
            }

            if(params.createTime){
                def time= params.createTime+' 00:00:00'
                ge("createTime", time)
            }
            if(params.createTime1){
                def time1= params.createTime1+' 23:59:59'
                le("createTime", time1)
            }

            if(params.veh_Fzrq){
                ge("veh_Fzrq", params.veh_Fzrq)
            }
            if(params.veh_Fzrq1){
                le("veh_Fzrq", params.veh_Fzrq1)
            }
            if(params.SAP_status){
//                eq("SAP_status",params.SAP_status)   // params.SAP_status是什么类型值SAP_status也要是相应类型值
                eq("SAP_status", "${params.SAP_status}")          //   "${params.SAP_status}"是String类型，那么SAP_status也得是String类型                             s
            }
//            eq('web_isprint','1')//去掉限制正式打印的合格证
//            ne('veh_Clfl','二类底盘')
            ne("SAP_status","4")   //有整车合格证的二类底盘不显示
            ne('web_status','3')       //撤销的合格证不再在合格传输证列表中显示
            order ("SAP_No",'asc')
            order ("createTime",'desc')

        }
        def rows=ZCInfo.createCriteria().list(params,cel)
        def lst=[]
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.SAP_No=u.SAP_No
            m.veh_Zchgzbh=u.veh_Zchgzbh
            m.veh_Clsbdh=u.veh_Clsbdh
            m.veh_Fdjh=u.veh_Fdjh
            m.veh_Fzrq=u.veh_Fzrq
            m.SAP_status=u.SAP_status
            m.transDate=u.transDate
            m.createTime=u.createTime
            m.veh_Clfl=u.veh_Clfl
            lst.add(m)
        }
        def result = [rows:lst, total: rows.totalCount]
        return result as JSON
    }

    /**
     * @Description同步合格证信息到SAP
     * @Create 2014-05-22
     * @Author zhuwei
     */

    def synZcinfo(params){
        def type=''
        def msg=''
        List msessageNo=[]
        try
        {
            //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
//        def nowDate =dateFormat.format( new Date() )
            def nowDate=DateUtil.getCurrentTime('yyyy-MM-dd HH:mm:ss')
            def zcinfoInstace=ZCInfo.get(params.id)
            def date=zcinfoInstace?.veh_Clzzrq   //车辆制造日期2014年05月25日转换成 YYYYMMDD格式
            def BUDAT=date.substring(0,4) + date.substring(5,7) + date.substring(8,10)
            def ZSCNO='XX0030'//扫描站点,固定
            def SERNR         //SAP车辆唯一号
            if(params?.SAP_No){
                SERNR=params?.SAP_No
            }else{
                SERNR=zcinfoInstace?.SAP_No
            }
            def ZN_CER=zcinfoInstace?.veh_Zchgzbh  //为完整合格证编号
            def ZN_ENG=zcinfoInstace?.veh_Fdjh        //发动机号
            def ZN_VIN=zcinfoInstace?.veh_Clsbdh       //VIN
            def ZOPIND=params?.ZOPIND

            def conn=conManageService.Connect(params)
//            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE");  //获得RFC,汽车、农庄调用的RFC
            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE1");    //获得RFC,农用车用RFC
            if(function == null)
                throw new RuntimeException("ZPP_VEH_TRACE not found in SAP.");

            //设置传入参数 第一个参数为参数值；第二个参数为参数名称（FRC中的import中的参数名）
            //            JCoParameter impParams=function.getImport
            JCoParameterList impParams=function.getImportParameterList()   //多个输入参数的方法

            impParams.setValue("IM_CHECK","")
            JCoStructure data= impParams.getStructure("IM_DATA")
            data.setValue("BUDAT",BUDAT)
            data.setValue("ZSCNO",ZSCNO)
            data.setValue("SERNR",SERNR)
            data.setValue("ZN_CER",ZN_CER)
            data.setValue("ZN_ENG",ZN_ENG)
            data.setValue("ZN_VIN",ZN_VIN)
            data.setValue("ZOPIND",ZOPIND)

            //执行RFC
            function.execute(conn);

            //获取RFC中的单个返回值的处理方式
//            JCoParameterList expParams= function.getExportParameterList()
//            def type1=expParams.getValue("TYPE")

            //获取table中的数据
            JCoTable codes = function.getTableParameterList().getTable("RETURN");
            def NumRows= codes.getNumRows()
            // codes.setRow(0);
//            def aaa=codes.getString("TYPE")
            if(codes.getString("TYPE")=='S'){
                type='S'
                if(ZOPIND=='1'){
                    zcinfoInstace.setSAP_status('1')
                    zcinfoInstace.setSAP_No(SERNR)
                    zcinfoInstace.setTransDate(nowDate) //不管传输、修改还是撤销，都保存了操作的时间
                }else{
                    zcinfoInstace.setSAP_status('0')
                    zcinfoInstace.setSAP_No("")
                    zcinfoInstace.setTransDate(nowDate)
                }

                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }
            }else if (codes.getString("TYPE")=='E'){
                type='E'
//                zcinfoInstace.setSAP_No(SERNR)
                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }

            }

            msessageNo?.each {m->
                if(m=='001'){
                    msg+="操作已被锁定;"
                }else if(m=='002'){
                    msg+="操作已被锁定;"
                }else if(m=='003'){
                    msg+="无数据显示;"
                }else if(m=='004'){
                    msg+="请选择上传文件;"
                }else if(m=='005'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='006'){
                    msg+="输入的车辆唯一号找不到订单;"
                }else if(m=='007'){
                    msg+="扫描站点是必输项;"
                }else if(m=='008'){
                    msg+="车辆唯一号是必输项;"
                }else if(m=='009'){
                    msg+="过账日期是必输项;"
                }else if(m=='010'){
                    msg+="车辆不满足扫描记录的顺序和条件;"
                }else if(m=='011'){
                    msg+="您没有操作权限;"
                }else if(m=='012'){
                    msg+="合格证编号不能为空;"
                }else if(m=='013'){
                    msg+="发动机编号不能为空;"
                }else if(m=='014'){
                    msg+="VIN号不能为空;"
                }else if(m=='015'){
                    msg+="试车员不能为空;"
                }else if(m=='016'){
                    msg+="检验员不能为空;"
                }else if(m=='017'){
                    msg+="该车辆正在被其他用户扫描;"
                }else if(m=='018'){
                    msg+="生产入库处理成功;"
                }else if(m=='019'){
                    msg+="生产入库处理失败;"
                }else if(m=='021'){
                    msg+="工序确认执行成功;"
                }else if(m=='022'){
                    msg+="工序确认执行失败;"
                }else if(m=='023'){
                    msg+="提供的扫描时间不正确(YYYYMMDDHHMMSS);"
                }else if(m=='024'){
                    msg+="设备更新执行成功;"
                }else if(m=='025'){
                    msg+="设备更新执行失败;"
                }else if(m=='026'){
                    msg+="没有找到订单对用的生产线;"
                }else if(m=='027'){
                    msg+="操作标识不合法;"
                }else if(m=='028'){
                    msg+="配置表维护的时间不合法;"
                }else if(m=='029'){
                    msg+="关键件条码已存在;"
                }else if(m=='030'){
                    msg+="关键件条码已扫描;"
                }else if(m=='031'){
                    msg+="关键件条码已扫描;"
                }else if(m=='032'){
                    msg+="物料编码不存在;"
                }else if(m=='033'){
                    msg+="物料供应商对应关系不存;"
                }else if(m=='034'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='035'){
                    msg+="车辆物料跟条码物料不一致;"
                }else if(m=='036'){
                    msg+="过账日期格式不合法,应为YYYYMMDD;"
                }
            }
        }
        catch(Exception e){
//            map.put("isSuccess", false);
//            map.put("errorMsg", e.getMessage());
            type='E'
            msg="${e.cause?e.cause:e}"
            throw  e
        } finally{
            def result = [type:type, msessageNo:msessageNo,msg:msg]
            return result
        }
    }

    /**
     * @Description同步合格证变更信息到SAP
     * @Create 2018-03-31
     * @Author QJ
     */

    def synZcinfoReplace(zcInfoReplace){
        def msg=''
        List msessageNo=[]
        try
        {
            //        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
//        def nowDate =dateFormat.format( new Date() )
            def nowDate=DateUtil.getCurrentTime('yyyy-MM-dd HH:mm:ss')
            def date=zcInfoReplace?.veh_Clzzrq_R   //车辆制造日期2014年05月25日转换成 YYYYMMDD格式
            def BUDAT=date.substring(0,4) + date.substring(5,7) + date.substring(8,10)
            def ZSCNO='XX0030'//扫描站点,固定
            def SERNR         //SAP车辆唯一号
            SERNR=zcInfoReplace?.SAP_No
            def ZN_CER=zcInfoReplace?.veh_Zchgzbh_0_R  //为完整合格证编号
            def ZN_ENG=zcInfoReplace?.veh_Fdjh_R        //发动机号
            def ZN_VIN=zcInfoReplace?.veh_Clsbdh_R       //VIN
            def ZOPIND='1'
            msg= '更换后VIN：'+ ZN_VIN+',更换后合格证编号：'+ZN_CER+',SAP序列号：'+SERNR+',返回信息：'
            def tempMap =[:]
            def conn=conManageService.Connect(tempMap)
//            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE");  //获得RFC,汽车、农庄调用的RFC
            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE1");    //获得RFC,农用车用RFC
            if(function == null)
                throw new RuntimeException("ZPP_VEH_TRACE not found in SAP.");

            //设置传入参数 第一个参数为参数值；第二个参数为参数名称（FRC中的import中的参数名）
            //            JCoParameter impParams=function.getImport
            JCoParameterList impParams=function.getImportParameterList()   //多个输入参数的方法

            impParams.setValue("IM_CHECK","")
            JCoStructure data= impParams.getStructure("IM_DATA")
            data.setValue("BUDAT",BUDAT)
            data.setValue("ZSCNO",ZSCNO)
            data.setValue("SERNR",SERNR)
            data.setValue("ZN_CER",ZN_CER)
            data.setValue("ZN_ENG",ZN_ENG)
            data.setValue("ZN_VIN",ZN_VIN)
            data.setValue("ZOPIND",ZOPIND)

            //执行RFC
            function.execute(conn);

            //获取RFC中的单个返回值的处理方式
//            JCoParameterList expParams= function.getExportParameterList()
//            def type1=expParams.getValue("TYPE")

            //获取table中的数据
            JCoTable codes = function.getTableParameterList().getTable("RETURN");
            def NumRows= codes.getNumRows()
            // codes.setRow(0);
//            def aaa=codes.getString("TYPE")
            if(codes.getString("TYPE")=='S'){
                zcInfoReplace.setTransToSap(2)
                zcInfoReplace.setSapTime(nowDate) //保存了操作的时间
                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }
            }else if (codes.getString("TYPE")=='E'){
                zcInfoReplace.setTransToSap(1)
                zcInfoReplace.setSapTime(nowDate) //保存了操作的时间
                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }

            }

            msessageNo?.each {m->
                if(m=='001'){
                    msg+="操作已被锁定;"
                }else if(m=='002'){
                    msg+="操作已被锁定;"
                }else if(m=='003'){
                    msg+="无数据显示;"
                }else if(m=='004'){
                    msg+="请选择上传文件;"
                }else if(m=='005'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='006'){
                    msg+="输入的车辆唯一号找不到订单;"
                }else if(m=='007'){
                    msg+="扫描站点是必输项;"
                }else if(m=='008'){
                    msg+="车辆唯一号是必输项;"
                }else if(m=='009'){
                    msg+="过账日期是必输项;"
                }else if(m=='010'){
                    msg+="车辆不满足扫描记录的顺序和条件;"
                }else if(m=='011'){
                    msg+="您没有操作权限;"
                }else if(m=='012'){
                    msg+="合格证编号不能为空;"
                }else if(m=='013'){
                    msg+="发动机编号不能为空;"
                }else if(m=='014'){
                    msg+="VIN号不能为空;"
                }else if(m=='015'){
                    msg+="试车员不能为空;"
                }else if(m=='016'){
                    msg+="检验员不能为空;"
                }else if(m=='017'){
                    msg+="该车辆正在被其他用户扫描;"
                }else if(m=='018'){
                    msg+="生产入库处理成功;"
                }else if(m=='019'){
                    msg+="生产入库处理失败;"
                }else if(m=='021'){
                    msg+="工序确认执行成功;"
                }else if(m=='022'){
                    msg+="工序确认执行失败;"
                }else if(m=='023'){
                    msg+="提供的扫描时间不正确(YYYYMMDDHHMMSS);"
                }else if(m=='024'){
                    msg+="设备更新执行成功;"
                }else if(m=='025'){
                    msg+="设备更新执行失败;"
                }else if(m=='026'){
                    msg+="没有找到订单对用的生产线;"
                }else if(m=='027'){
                    msg+="操作标识不合法;"
                }else if(m=='028'){
                    msg+="配置表维护的时间不合法;"
                }else if(m=='029'){
                    msg+="关键件条码已存在;"
                }else if(m=='030'){
                    msg+="关键件条码已扫描;"
                }else if(m=='031'){
                    msg+="关键件条码已扫描;"
                }else if(m=='032'){
                    msg+="物料编码不存在;"
                }else if(m=='033'){
                    msg+="物料供应商对应关系不存;"
                }else if(m=='034'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='035'){
                    msg+="车辆物料跟条码物料不一致;"
                }else if(m=='036'){
                    msg+="过账日期格式不合法,应为YYYYMMDD;"
                }
            }
        }catch(Exception e){
            msg="${e.cause?e.cause:e}"
            throw  e
        } finally{
            logService.insertLog('402885e93c8013fd013c801446d10035', "SynToSapDetail",msg, "systemLog")
        }
    }

    /**
     * @Description 根据条件选择【农装合格证】信息进行传输到SAP
     * @param params
     * @CreateTime 2014-7-15
     * @Author zhuwei
     * Update 合格证传输至车间只传输自己车间生产的合格证，营销公司传输所有产生的合格证信息
     * UpdateTime 2014-10-28 zhuwei
     */
    def searchNZInfoByParams(params){
        def cel={
            if(params.organCode&&params.transFlag=='0'){     //给车间配置的传输菜单，opFlag参数为0；给营销公司配置的传输菜单opFlag参数为1,所以营销公司能够看全部的合格证传输信息
                                                           //对于system ，要么什么组织也不关联，要么关联上所有组织，这样system才能看全部信息，或者通过营销公司传输列表看
                inList("veh_workshopno", params.organCode)
            }
            if(params.veh_new_clbh){
                like("veh_new_clbh", "%${params.veh_new_clbh}%")
            }
            if(params.veh_Hgzbh){
                like("veh_Hgzbh", "%${params.veh_Hgzbh}%")
            }
            if(params.veh_Fdjh){
                like("veh_Fdjh", "%${params.veh_Fdjh}%")
            }
            if(params.SAP_No){
                like("SAP_No", "%${params.SAP_No}%")
            }

            if(params.veh_Ccrq){
                ge("veh_Ccrq", params.veh_Ccrq)
            }
            if(params.veh_Ccrq1){
                le("veh_Ccrq", params.veh_Ccrq1)
            }
            if(params.createTime){
                def time= params.createTime+' 00:00:00'
                ge("createTime", time)
            }
            if(params.createTime1){
                def time1= params.createTime1+' 23:59:59'
                le("createTime", time1)
            }
            if(params.SAP_status){
                eq("SAP_status","${params.SAP_status}")
            }
//            eq('isprint','1')        //去掉打印正式限制
            ne('upload','3')       //撤销的合格证不再在合格传输证列表中显示
            order ("SAP_No",'asc')
            order ("createTime",'desc')
        }
        def rows=NZInfo.createCriteria().list(params,cel)
        def lst=[]
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.SAP_No=u.SAP_No
            m.veh_Hgzbh=u.veh_Hgzbh
            m.veh_Dph=u.veh_Dph
            m.veh_new_clbh=u.veh_new_clbh
            m.veh_Fdjh=u.veh_Fdjh
            m.veh_Ccrq=u.veh_Ccrq
            m.SAP_status=u.SAP_status
            m.transDate=u.transDate
            m.createTime=u.createTime
            lst.add(m)
        }
        def result = [rows:lst, total: rows.totalCount]
        return result as JSON
    }

    /**
     * @Description 根据条件选择【农装合格证】信息进行传输到SAP
     * @param params
     * @CreateTime 2014-7-15
     * @Author zhuwei
     * Update 合格证传输至车间只传输自己车间生产的合格证，营销公司传输所有产生的合格证信息
     * UpdateTime 2014-10-28 zhuwei
     */
    def searchHarvestInfoByParams(params){
        def cel={
            if(params.veh_Clbh){
                like("veh_Clbh", "%${params.veh_Clbh}%")
            }
            if(params.veh_Hgzbh){
                like("veh_Hgzbh", "%${params.veh_Hgzbh}%")
            }
            if(params.SAP_No){
                like("SAP_No", "%${params.SAP_No}%")
            }
            if(params.createTime){
                def time= params.createTime+' 00:00:00'
                ge("createTime", time)
            }
            if(params.createTime1){
                def time1= params.createTime1+' 23:59:59'
                le("createTime", time1)
            }
            if(params.SAP_status){
                eq("SAP_status","${params.SAP_status}")
            }
//            eq('isprint','1')        //去掉打印正式限制
            ne('upload','3')       //撤销的合格证不再在合格传输证列表中显示
            order ("createTime",'desc')
            order ("SAP_No",'desc')

        }
        def rows=HarvestInfo.createCriteria().list(params,cel)
        def lst=[]
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.SAP_No=u.SAP_No
            m.veh_Hgzbh=u.veh_Hgzbh
            m.veh_Clbh=u.veh_Clbh
            m.veh_new_clbh=u.veh_new_clbh
            m.veh_Dph=u.veh_Dph
            m.veh_Fdjh=u.veh_Fdjh
            m.veh_Ccrq=u.veh_Ccrq
            m.SAP_status=u.SAP_status
            m.transDate=u.transDate
            m.createTime=u.createTime
            lst.add(m)
        }
        def result = [rows:lst, total: rows.totalCount]
        return result as JSON
    }

    /**
     * @Description 根据条件选择【垃圾站合格证】信息进行传输到SAP
     * @param params
     * @CreateTime 2014-7-15
     * @Author zhuwei
     * Update 合格证传输至车间只传输自己车间生产的合格证，营销公司传输所有产生的合格证信息
     * UpdateTime 2014-10-28 zhuwei
     */
    def searchGarbageInfoByParams(params){
        def cel={
            if(params.veh_Clbh){
                like("veh_Clbh", "%${params.veh_Clbh}%")
            }
            if(params.SAP_No){
                like("SAP_No", "%${params.SAP_No}%")
            }
            if(params.createTime){
                def time= params.createTime+' 00:00:00'
                ge("createTime", time)
            }
            if(params.createTime1){
                def time1= params.createTime1+' 23:59:59'
                le("createTime", time1)
            }
            if(params.SAP_status){
                eq("SAP_status","${params.SAP_status}")
            }
//            eq('isprint','1')        //去掉打印正式限制
            ne('upload','3')       //撤销的合格证不再在合格传输证列表中显示
            order ("createTime",'desc')
            order ("SAP_No",'desc')

        }
        def rows=GarbageInfo.createCriteria().list(params,cel)
        def lst=[]
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.SAP_No=u.SAP_No
            m.veh_Hgzbh=u.veh_Hgzbh
            m.veh_Clbh=u.veh_Clbh
            m.veh_Cpmc=u.veh_Cpmc
            m.veh_Ccrq=u.veh_Ccrq
            m.SAP_status=u.SAP_status
            m.transDate=u.transDate
            m.createTime=u.createTime
            lst.add(m)
        }
        def result = [rows:lst, total: rows.totalCount]
        return result as JSON
    }



    /**
     * @Description同步合格证信息到SAP
     * @Create 2014-05-22
     * @Author zhuwei
     * @update 2018-05-30 35位合格证号去掉信用码传后17位至SAP
     */

    def synZcinfo_NZ(params){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        def nowDate =dateFormat.format( new Date() )
        def type=''
        def msg=''
        List msessageNo=[]
        def NZInfoInstance=NZInfo.get(params.id)
        def date=NZInfoInstance?.veh_Ccrq   //车辆制造日期2014年05月25日转换成 YYYYMMDD格式
        def BUDAT=date.substring(0,4) + date.substring(5,7) + date.substring(8,10)
        def ZSCNO='XX0030'//扫描站点,固定
        def SERNR         //SAP车辆唯一号
        if(params?.SAP_No){
            SERNR=params?.SAP_No
        }else{
            SERNR=NZInfoInstance?.SAP_No
        }
        def ZN_CER=NZInfoInstance?.veh_new_clbh    //车辆编号即合格证后17位
        def ZN_ENG=NZInfoInstance?.veh_Fdjh        //发动机号
        def ZN_VIN=NZInfoInstance?.veh_new_clbh    //VIN即车辆编号
        def ZOPIND=params?.ZOPIND

        try
        {
            def conn=conManageService.Connect(params)
            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE");  //获得RFC
            if(function == null)
                throw new RuntimeException("ZPP_VEH_TRACE not found in SAP.");

            //设置传入参数 第一个参数为参数值；第二个参数为参数名称（FRC中的import中的参数名）
            //            JCoParameter impParams=function.getImport
            JCoParameterList impParams=function.getImportParameterList()   //多个输入参数的方法

            impParams.setValue("IM_CHECK","")
            JCoStructure data= impParams.getStructure("IM_DATA")
            data.setValue("BUDAT",BUDAT)
            data.setValue("ZSCNO",ZSCNO)
            data.setValue("SERNR",SERNR)
            data.setValue("ZN_CER",ZN_CER)
            data.setValue("ZN_ENG",ZN_ENG)
            data.setValue("ZN_VIN",ZN_VIN)
            data.setValue("ZOPIND",ZOPIND)

            //执行RFC
            function.execute(conn);

            //获取RFC中的单个返回值的处理方式
//            JCoParameterList expParams= function.getExportParameterList()
//            def type1=expParams.getValue("TYPE")

            //获取table中的数据
            JCoTable codes = function.getTableParameterList().getTable("RETURN");
            def NumRows= codes.getNumRows()
            // codes.setRow(0);
//            def aaa=codes.getString("TYPE")
            if(codes.getString("TYPE")=='S'){
                type='S'
                if(ZOPIND=='1'){
                    NZInfoInstance.setSAP_status('1')
                    NZInfoInstance.setSAP_No(SERNR)
                    NZInfoInstance.setTransDate(nowDate) //不管传输、修改还是撤销，都保存了操作的时间
                }else{
                    NZInfoInstance.setSAP_status('0')
                    NZInfoInstance.setSAP_No("")
                    NZInfoInstance.setTransDate(nowDate)
                }

                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }
            }else if (codes.getString("TYPE")=='E'){
                type='E'
//                NZInfoInstance.setSAP_No(SERNR)
                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }

            }

            msessageNo?.each {m->
                if(m=='001'){
                    msg+="操作已被锁定;"
                }else if(m=='002'){
                    msg+="操作已被锁定;"
                }else if(m=='003'){
                    msg+="无数据显示;"
                }else if(m=='004'){
                    msg+="请选择上传文件;"
                }else if(m=='005'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='006'){
                    msg+="输入的车辆唯一号找不到订单;"
                }else if(m=='007'){
                    msg+="扫描站点是必输项;"
                }else if(m=='008'){
                    msg+="车辆唯一号是必输项;"
                }else if(m=='009'){
                    msg+="过账日期是必输项;"
                }else if(m=='010'){
                    msg+="车辆不满足扫描记录的顺序和条件;"
                }else if(m=='011'){
                    msg+="您没有操作权限;"
                }else if(m=='012'){
                    msg+="合格证编号不能为空;"
                }else if(m=='013'){
                    msg+="发动机编号不能为空;"
                }else if(m=='014'){
                    msg+="VIN号不能为空;"
                }else if(m=='015'){
                    msg+="试车员不能为空;"
                }else if(m=='016'){
                    msg+="检验员不能为空;"
                }else if(m=='017'){
                    msg+="该车辆正在被其他用户扫描;"
                }else if(m=='018'){
                    msg+="生产入库处理成功;"
                }else if(m=='019'){
                    msg+="生产入库处理失败;"
                }else if(m=='021'){
                    msg+="工序确认执行成功;"
                }else if(m=='022'){
                    msg+="工序确认执行失败;"
                }else if(m=='023'){
                    msg+="提供的扫描时间不正确(YYYYMMDDHHMMSS);"
                }else if(m=='024'){
                    msg+="设备更新执行成功;"
                }else if(m=='025'){
                    msg+="设备更新执行失败;"
                }else if(m=='026'){
                    msg+="没有找到订单对用的生产线;"
                }else if(m=='027'){
                    msg+="操作标识不合法;"
                }else if(m=='028'){
                    msg+="配置表维护的时间不合法;"
                }else if(m=='029'){
                    msg+="关键件条码已存在;"
                }else if(m=='030'){
                    msg+="关键件条码已扫描;"
                }else if(m=='031'){
                    msg+="关键件条码已扫描;"
                }else if(m=='032'){
                    msg+="物料编码不存在;"
                }else if(m=='033'){
                    msg+="物料供应商对应关系不存;"
                }else if(m=='034'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='035'){
                    msg+="车辆物料跟条码物料不一致;"
                }else if(m=='036'){
                    msg+="过账日期格式不合法,应为YYYYMMDD;"
                }
            }
        }
        catch(Exception e){
//            map.put("isSuccess", false);
//            map.put("errorMsg", e.getMessage());
            type='E'
            msg="${e.cause?e.cause.message:e.message}"
        } finally{
            def result = [type:type, msessageNo:msessageNo,msg:msg]
            return result
        }
    }
    /**
     * @Description同步玉米收车间合格证证信息到SAP
     * @Create 2014-05-22
     * @Author zhuwei
     */

    def synZcinfo_Harvest(params){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        def nowDate =dateFormat.format( new Date() )
        def type=''
        def msg=''
        List msessageNo=[]
        def harvestInfoInstance=HarvestInfo.get(params.id)
        def date=harvestInfoInstance?.veh_Ccrq   //车辆制造日期2014年05月25日转换成 YYYYMMDD格式
        def BUDAT=date.substring(0,4) + date.substring(5,7) + date.substring(8,10)
        def ZSCNO='XX0030'//扫描站点,固定
        def SERNR         //SAP车辆唯一号
        if(params?.SAP_No){
            SERNR=params?.SAP_No
        }else{
            SERNR=harvestInfoInstance?.SAP_No
        }
        def ZN_CER=harvestInfoInstance?.veh_Hgzbh  //为完整合格证编号
        def ZN_ENG=harvestInfoInstance?.veh_Fdjh        //发动机号
        def ZN_VIN=harvestInfoInstance?.veh_new_clbh      //VIN
        def ZOPIND=params?.ZOPIND

        try
        {
            def conn=conManageService.Connect(params)
            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE");  //获得RFC
            if(function == null)
                throw new RuntimeException("ZPP_VEH_TRACE not found in SAP.");

            //设置传入参数 第一个参数为参数值；第二个参数为参数名称（FRC中的import中的参数名）
            //            JCoParameter impParams=function.getImport
            JCoParameterList impParams=function.getImportParameterList()   //多个输入参数的方法

            impParams.setValue("IM_CHECK","")
            JCoStructure data= impParams.getStructure("IM_DATA")
            data.setValue("BUDAT",BUDAT)
            data.setValue("ZSCNO",ZSCNO)
            data.setValue("SERNR",SERNR)
            data.setValue("ZN_CER",ZN_CER)
            data.setValue("ZN_ENG",ZN_ENG)
            data.setValue("ZN_VIN",ZN_VIN)
            data.setValue("ZOPIND",ZOPIND)

            //执行RFC
            function.execute(conn);

            //获取RFC中的单个返回值的处理方式
//            JCoParameterList expParams= function.getExportParameterList()
//            def type1=expParams.getValue("TYPE")

            //获取table中的数据
            JCoTable codes = function.getTableParameterList().getTable("RETURN");
            def NumRows= codes.getNumRows()
            // codes.setRow(0);
//            def aaa=codes.getString("TYPE")
            if(codes.getString("TYPE")=='S'){
                type='S'
                if(ZOPIND=='1'){
                    harvestInfoInstance.setSAP_status('1')
                    harvestInfoInstance.setSAP_No(SERNR)
                    harvestInfoInstance.setTransDate(nowDate) //不管传输、修改还是撤销，都保存了操作的时间
                }else{
                    harvestInfoInstance.setSAP_status('0')
                    harvestInfoInstance.setSAP_No("")
                    harvestInfoInstance.setTransDate(nowDate)
                }

                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }
            }else if (codes.getString("TYPE")=='E'){
                type='E'
//                harvestInfoInstance.setSAP_No(SERNR)
                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }

            }

            msessageNo?.each {m->
                if(m=='001'){
                    msg+="操作已被锁定;"
                }else if(m=='002'){
                    msg+="操作已被锁定;"
                }else if(m=='003'){
                    msg+="无数据显示;"
                }else if(m=='004'){
                    msg+="请选择上传文件;"
                }else if(m=='005'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='006'){
                    msg+="输入的车辆唯一号找不到订单;"
                }else if(m=='007'){
                    msg+="扫描站点是必输项;"
                }else if(m=='008'){
                    msg+="车辆唯一号是必输项;"
                }else if(m=='009'){
                    msg+="过账日期是必输项;"
                }else if(m=='010'){
                    msg+="车辆不满足扫描记录的顺序和条件;"
                }else if(m=='011'){
                    msg+="您没有操作权限;"
                }else if(m=='012'){
                    msg+="合格证编号不能为空;"
                }else if(m=='013'){
                    msg+="发动机编号不能为空;"
                }else if(m=='014'){
                    msg+="VIN号不能为空;"
                }else if(m=='015'){
                    msg+="试车员不能为空;"
                }else if(m=='016'){
                    msg+="检验员不能为空;"
                }else if(m=='017'){
                    msg+="该车辆正在被其他用户扫描;"
                }else if(m=='018'){
                    msg+="生产入库处理成功;"
                }else if(m=='019'){
                    msg+="生产入库处理失败;"
                }else if(m=='021'){
                    msg+="工序确认执行成功;"
                }else if(m=='022'){
                    msg+="工序确认执行失败;"
                }else if(m=='023'){
                    msg+="提供的扫描时间不正确(YYYYMMDDHHMMSS);"
                }else if(m=='024'){
                    msg+="设备更新执行成功;"
                }else if(m=='025'){
                    msg+="设备更新执行失败;"
                }else if(m=='026'){
                    msg+="没有找到订单对用的生产线;"
                }else if(m=='027'){
                    msg+="操作标识不合法;"
                }else if(m=='028'){
                    msg+="配置表维护的时间不合法;"
                }else if(m=='029'){
                    msg+="关键件条码已存在;"
                }else if(m=='030'){
                    msg+="关键件条码已扫描;"
                }else if(m=='031'){
                    msg+="关键件条码已扫描;"
                }else if(m=='032'){
                    msg+="物料编码不存在;"
                }else if(m=='033'){
                    msg+="物料供应商对应关系不存;"
                }else if(m=='034'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='035'){
                    msg+="车辆物料跟条码物料不一致;"
                }else if(m=='036'){
                    msg+="过账日期格式不合法,应为YYYYMMDD;"
                }
            }
        }
        catch(Exception e){
//            map.put("isSuccess", false);
//            map.put("errorMsg", e.getMessage());
            type='E'
            msg="${e.cause?e.cause.message:e.message}"
        } finally{
            def result = [type:type, msessageNo:msessageNo,msg:msg]
            return result
        }
    }
    def searchSTInfoByParams(params){
        def cel={
            if(params.organCode&&params.transFlag=='0'){     //给车间配置的传输菜单，opFlag参数为0；给营销公司配置的传输菜单opFlag参数为1,所以营销公司能够看全部的合格证传输信息
                //对于system ，要么什么组织也不关联，要么关联上所有组织，这样system才能看全部信息，或者通过营销公司传输列表看
                inList("veh_workshopno", params.organCode)
            }
            if(params.veh_Scbh_0){
                like("veh_Scbh_0", "%${params.veh_Scbh_0}%")
            }
            if(params.veh_Hgzbh){
                like("veh_Hgzbh", "%${params.veh_Hgzbh}%")
            }
            if(params.veh_Fdjh){
                like("veh_Fdjh", "%${params.veh_Fdjh}%")
            }
            if(params.SAP_No){
                like("SAP_No", "%${params.SAP_No}%")
            }

            if(params.veh_Scrq){
                ge("veh_Scrq", params.veh_Scrq)
            }
            if(params.veh_Scrq1){
                le("veh_Scrq", params.veh_Scrq1)
            }
            if(params.createTime){
                def time= params.createTime+' 00:00:00'
                ge("createTime", time)
            }
            if(params.createTime1){
                def time1= params.createTime1+' 23:59:59'
                le("createTime", time1)
            }
            if(params.SAP_status){
                eq("SAP_status","${params.SAP_status}")
            }
//            eq('isprint','1')        //去掉打印正式限制
            ne('upload','3')       //撤销的合格证不再在合格传输证列表中显示
//            order ("SAP_No",'desc')
            order ("createTime",'desc')
        }
        def rows=STInfo.createCriteria().list(params,cel)
        def lst=[]
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.SAP_No=u.SAP_No
            m.veh_Hgzbh=u.veh_Hgzbh
            m.veh_Scbh_0=u.veh_Scbh_0
            m.veh_Fdjh=u.veh_Fdjh
            m.veh_Scrq=u.veh_Scrq
            m.SAP_status=u.SAP_status
            m.transDate=u.transDate
            m.createTime=u.createTime
            lst.add(m)
        }
        def result = [rows:lst, total: rows.totalCount]
        return result as JSON
    }
    /**
     * @Description同步山拖合格证信息到SAP
     * @Create 2016-06-17
     * @Author zhuwei
     */
    def synZcinfo_ST(params){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        def nowDate =dateFormat.format( new Date() )
        def type=''
        def msg=''
        List msessageNo=[]
        def STInfoInstance=STInfo.get(params.id)
        def date=STInfoInstance?.veh_Scrq   //车辆制造日期2014年05月25日转换成 YYYYMMDD格式
        def BUDAT=date.substring(0,4) + date.substring(5,7) + date.substring(8,10)
        def ZSCNO='XX0030'//扫描站点,固定
        def SERNR         //SAP车辆唯一号
        if(params?.SAP_No){
            SERNR=params?.SAP_No
        }else{
            SERNR=STInfoInstance?.SAP_No
        }
        def ZN_CER=STInfoInstance?.veh_Hgzbh  //为完整合格证编号
        def ZN_ENG=STInfoInstance?.veh_Fdjh        //发动机号
        def ZN_VIN=STInfoInstance?.veh_new_clbh       //VIN
        def ZOPIND=params?.ZOPIND

        try
        {
            def conn=conManageService.Connect(params)
            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE");  //获得RFC
            if(function == null)
                throw new RuntimeException("ZPP_VEH_TRACE not found in SAP.");

            //设置传入参数 第一个参数为参数值；第二个参数为参数名称（FRC中的import中的参数名）
            //            JCoParameter impParams=function.getImport
            JCoParameterList impParams=function.getImportParameterList()   //多个输入参数的方法

            impParams.setValue("IM_CHECK","")
            JCoStructure data= impParams.getStructure("IM_DATA")
            data.setValue("BUDAT",BUDAT)
            data.setValue("ZSCNO",ZSCNO)
            data.setValue("SERNR",SERNR)
            data.setValue("ZN_CER",ZN_CER)
            data.setValue("ZN_ENG",ZN_ENG)
            data.setValue("ZN_VIN",ZN_VIN)
            data.setValue("ZOPIND",ZOPIND)

            //执行RFC
            function.execute(conn);

            //获取RFC中的单个返回值的处理方式
//            JCoParameterList expParams= function.getExportParameterList()
//            def type1=expParams.getValue("TYPE")

            //获取table中的数据
            JCoTable codes = function.getTableParameterList().getTable("RETURN");
            def NumRows= codes.getNumRows()
            // codes.setRow(0);
//            def aaa=codes.getString("TYPE")
            if(codes.getString("TYPE")=='S'){
                type='S'
                if(ZOPIND=='1'){
                    STInfoInstance.setSAP_status('1')
                    STInfoInstance.setSAP_No(SERNR)
                    STInfoInstance.setTransDate(nowDate) //不管传输、修改还是撤销，都保存了操作的时间
                }else{
                    STInfoInstance.setSAP_status('0')
                    STInfoInstance.setSAP_No("")
                    STInfoInstance.setTransDate(nowDate)
                }

                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }
            }else if (codes.getString("TYPE")=='E'){
                type='E'
//                STInfoInstance.setSAP_No(SERNR)
                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }

            }

            msessageNo?.each {m->
                if(m=='001'){
                    msg+="操作已被锁定;"
                }else if(m=='002'){
                    msg+="操作已被锁定;"
                }else if(m=='003'){
                    msg+="无数据显示;"
                }else if(m=='004'){
                    msg+="请选择上传文件;"
                }else if(m=='005'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='006'){
                    msg+="输入的车辆唯一号找不到订单;"
                }else if(m=='007'){
                    msg+="扫描站点是必输项;"
                }else if(m=='008'){
                    msg+="车辆唯一号是必输项;"
                }else if(m=='009'){
                    msg+="过账日期是必输项;"
                }else if(m=='010'){
                    msg+="车辆不满足扫描记录的顺序和条件;"
                }else if(m=='011'){
                    msg+="您没有操作权限;"
                }else if(m=='012'){
                    msg+="合格证编号不能为空;"
                }else if(m=='013'){
                    msg+="发动机编号不能为空;"
                }else if(m=='014'){
                    msg+="VIN号不能为空;"
                }else if(m=='015'){
                    msg+="试车员不能为空;"
                }else if(m=='016'){
                    msg+="检验员不能为空;"
                }else if(m=='017'){
                    msg+="该车辆正在被其他用户扫描;"
                }else if(m=='018'){
                    msg+="生产入库处理成功;"
                }else if(m=='019'){
                    msg+="生产入库处理失败;"
                }else if(m=='021'){
                    msg+="工序确认执行成功;"
                }else if(m=='022'){
                    msg+="工序确认执行失败;"
                }else if(m=='023'){
                    msg+="提供的扫描时间不正确(YYYYMMDDHHMMSS);"
                }else if(m=='024'){
                    msg+="设备更新执行成功;"
                }else if(m=='025'){
                    msg+="设备更新执行失败;"
                }else if(m=='026'){
                    msg+="没有找到订单对用的生产线;"
                }else if(m=='027'){
                    msg+="操作标识不合法;"
                }else if(m=='028'){
                    msg+="配置表维护的时间不合法;"
                }else if(m=='029'){
                    msg+="关键件条码已存在;"
                }else if(m=='030'){
                    msg+="关键件条码已扫描;"
                }else if(m=='031'){
                    msg+="关键件条码已扫描;"
                }else if(m=='032'){
                    msg+="物料编码不存在;"
                }else if(m=='033'){
                    msg+="物料供应商对应关系不存;"
                }else if(m=='034'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='035'){
                    msg+="车辆物料跟条码物料不一致;"
                }else if(m=='036'){
                    msg+="过账日期格式不合法,应为YYYYMMDD;"
                }
            }
        }
        catch(Exception e){
//            map.put("isSuccess", false);
//            map.put("errorMsg", e.getMessage());
            type='E'
            msg="${e.cause?e.cause.message:e.message}"
        } finally{
            def result = [type:type, msessageNo:msessageNo,msg:msg]
            return result
        }
    }
    /**
     * @Description同步专用车垃圾站信息到SAP
     * @Create 2014-05-22
     * @Author zhuwei
     */

    def synZcinfo_Garbage(params){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        def nowDate =dateFormat.format( new Date() )
        def type=''
        def msg=''
        List msessageNo=[]
        def garbageInfoInstance=GarbageInfo.get(params.id)
        def date=garbageInfoInstance?.veh_Ccrq   //车辆制造日期2014年05月25日转换成 YYYYMMDD格式
        def BUDAT=date.substring(0,4) + date.substring(5,7) + date.substring(8,10)
        def ZSCNO='XX0030'//扫描站点,固定
        def SERNR         //SAP车辆唯一号
        if(params?.SAP_No){
            SERNR=params?.SAP_No
        }else{
            SERNR=garbageInfoInstance?.SAP_No
        }
        def ZN_CER=garbageInfoInstance?.veh_Hgzbh  //为完整合格证编号
        def ZN_ENG=garbageInfoInstance?.veh_Clbh        //垃圾站没有发动机号传车架号
        def ZN_VIN=garbageInfoInstance?.veh_Clbh       //VIN
        def ZOPIND=params?.ZOPIND

        try
        {
            def conn=conManageService.Connect(params)
            JCoFunction function = conn.getRepository().getFunction("ZPP_VEH_TRACE");  //获得RFC
            if(function == null)
                throw new RuntimeException("ZPP_VEH_TRACE not found in SAP.");

            //设置传入参数 第一个参数为参数值；第二个参数为参数名称（FRC中的import中的参数名）
            //            JCoParameter impParams=function.getImport
            JCoParameterList impParams=function.getImportParameterList()   //多个输入参数的方法

            impParams.setValue("IM_CHECK","")
            JCoStructure data= impParams.getStructure("IM_DATA")
            data.setValue("BUDAT",BUDAT)
            data.setValue("ZSCNO",ZSCNO)
            data.setValue("SERNR",SERNR)
            data.setValue("ZN_CER",ZN_CER)
            data.setValue("ZN_ENG",ZN_ENG)
            data.setValue("ZN_VIN",ZN_VIN)
            data.setValue("ZOPIND",ZOPIND)

            //执行RFC
            function.execute(conn);

            //获取RFC中的单个返回值的处理方式
//            JCoParameterList expParams= function.getExportParameterList()
//            def type1=expParams.getValue("TYPE")

            //获取table中的数据
            JCoTable codes = function.getTableParameterList().getTable("RETURN");
            def NumRows= codes.getNumRows()
            // codes.setRow(0);
//            def aaa=codes.getString("TYPE")
            if(codes.getString("TYPE")=='S'){
                type='S'
                if(ZOPIND=='1'){
                    garbageInfoInstance.setSAP_status('1')
                    garbageInfoInstance.setSAP_No(SERNR)
                    garbageInfoInstance.setTransDate(nowDate) //不管传输、修改还是撤销，都保存了操作的时间
                }else{
                    garbageInfoInstance.setSAP_status('0')
                    garbageInfoInstance.setSAP_No("")
                    garbageInfoInstance.setTransDate(nowDate)
                }

                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }
            }else if (codes.getString("TYPE")=='E'){
                type='E'
//                harvestInfoInstance.setSAP_No(SERNR)
                for (int i = 0; i < codes.getNumRows(); i++)
                {
                    codes.setRow(i)
                    def NUMBER=codes.getString("NUMBER")
                    msessageNo.add(NUMBER)
                }

            }

            msessageNo?.each {m->
                if(m=='001'){
                    msg+="操作已被锁定;"
                }else if(m=='002'){
                    msg+="操作已被锁定;"
                }else if(m=='003'){
                    msg+="无数据显示;"
                }else if(m=='004'){
                    msg+="请选择上传文件;"
                }else if(m=='005'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='006'){
                    msg+="输入的车辆唯一号找不到订单;"
                }else if(m=='007'){
                    msg+="扫描站点是必输项;"
                }else if(m=='008'){
                    msg+="车辆唯一号是必输项;"
                }else if(m=='009'){
                    msg+="过账日期是必输项;"
                }else if(m=='010'){
                    msg+="车辆不满足扫描记录的顺序和条件;"
                }else if(m=='011'){
                    msg+="您没有操作权限;"
                }else if(m=='012'){
                    msg+="合格证编号不能为空;"
                }else if(m=='013'){
                    msg+="发动机编号不能为空;"
                }else if(m=='014'){
                    msg+="VIN号不能为空;"
                }else if(m=='015'){
                    msg+="试车员不能为空;"
                }else if(m=='016'){
                    msg+="检验员不能为空;"
                }else if(m=='017'){
                    msg+="该车辆正在被其他用户扫描;"
                }else if(m=='018'){
                    msg+="生产入库处理成功;"
                }else if(m=='019'){
                    msg+="生产入库处理失败;"
                }else if(m=='021'){
                    msg+="工序确认执行成功;"
                }else if(m=='022'){
                    msg+="工序确认执行失败;"
                }else if(m=='023'){
                    msg+="提供的扫描时间不正确(YYYYMMDDHHMMSS);"
                }else if(m=='024'){
                    msg+="设备更新执行成功;"
                }else if(m=='025'){
                    msg+="设备更新执行失败;"
                }else if(m=='026'){
                    msg+="没有找到订单对用的生产线;"
                }else if(m=='027'){
                    msg+="操作标识不合法;"
                }else if(m=='028'){
                    msg+="配置表维护的时间不合法;"
                }else if(m=='029'){
                    msg+="关键件条码已存在;"
                }else if(m=='030'){
                    msg+="关键件条码已扫描;"
                }else if(m=='031'){
                    msg+="关键件条码已扫描;"
                }else if(m=='032'){
                    msg+="物料编码不存在;"
                }else if(m=='033'){
                    msg+="物料供应商对应关系不存;"
                }else if(m=='034'){
                    msg+="车辆唯一号不存在;"
                }else if(m=='035'){
                    msg+="车辆物料跟条码物料不一致;"
                }else if(m=='036'){
                    msg+="过账日期格式不合法,应为YYYYMMDD;"
                }
            }
        }
        catch(Exception e){
//            map.put("isSuccess", false);
//            map.put("errorMsg", e.getMessage());
            type='E'
            msg="${e.cause?e.cause.message:e.message}"
        } finally{
            def result = [type:type, msessageNo:msessageNo,msg:msg]
            return result
        }
    }
    /**
     * @Description 查找要传已传CRM未传SAP的合格证变更记录
     * @CreateTime 2018-04-19
     * @Author Qj
     * @update QJ 2018-05-03底盘换整车也传SAP
     */
    def findChangRecordForSap(){
        List transfChangeDataList= new ArrayList()
        def cel= {
            eq('veh_coc_status', 1)        //审核状态为通过
            eq('transToCrm', 2)
            or{
                eq('transToSap',0)
                eq('transToSap',1)
            }
            isNotNull('veh_Zchgzbh_0_R')  //更换后的整车合格证编号不为空
            isNotNull('SAP_No')           //更换后的SAP序列号不能为空（新DMS打开）
            order("authTime","asc")
        }
        transfChangeDataList=ZCInfoReplace.createCriteria().list(cel)
        return transfChangeDataList
    }
    /**
     * @Description 查找要传已传CRM传SAP失败的合格证变更记录
     * @CreateTime 2018-05-15
     * @Author Qj
     * @update QJ 2018-05-03底盘换整车也传SAP
     */
    def findFailureChangRecordForSap(){
        List transfChangeDataList= new ArrayList()
        def cel= {
            eq('veh_coc_status', 1)        //审核状态为通过
            eq('transToCrm', 2)
            or{
                eq('transToSap',1)
            }
            isNotNull('veh_Zchgzbh_0_R')  //更换后的整车合格证编号不为空
            isNotNull('SAP_No')           //更换后的SAP序列号不能为空（新DMS打开）
            order("authTime","asc")
        }
        transfChangeDataList=ZCInfoReplace.createCriteria().list(cel)
        return transfChangeDataList
    }
    /**
     * @Description 查找要传给SAP的合格证变更记录（不管传CRM成功没成功）（只传SAP）
     * @CreateTime 2018-07-25
     * @Author zhuwei
     */
    def findAllReplaceForSap(){
        List transfChangeDataList= new ArrayList()
        def cel={
            eq('veh_coc_status',1)        //审核状态为通过
//              eq('veh_Clztxx_R','QX')
//              eq('veh_Clztxx_R','QX')       //传输变更记录只要整车换整车的记录
            or{
//                  int transToCrm=0   //0表示要传给CRM，1禁止传给CRM  ，2传给CRM成功，3传给CRM失败
                eq('transToSap',0)
                eq('transToSap',1) //将未传输的或者传输失败，注意不是禁止传输的，筛选出来
            }
            isNotNull('veh_Zchgzbh_0_R')  //更换后的整车合格证编号不为空
            isNotNull('SAP_No')           //更换后的SAP序列号不能为空（新DMS打开）
            order("authTime","asc")
        }
        transfChangeDataList=ZCInfoReplace.createCriteria().list(cel)
        return transfChangeDataList
    }
    /**
     * @Description 将合格证更换记录传给SAP和SAP中间表
     * @CreateTime 2018-07-25
     * @Author QJ
     */
    def replaceToSap(){
        def tranfList=this.findAllReplaceForSap()
        if(tranfList.size()>0){
            tranfList.each{zcInfoReplace->
                this.synZcinfoReplace(zcInfoReplace)
            }
            new SynDelivery(tranfList,logService)
        }
    }
}
