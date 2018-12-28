package cn.com.wz.vehcert.transferToSap

import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.FTPUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.system.LogService
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.carstorage.CarStorage
import cn.com.wz.vehcert.zcinfo.ZCInfo
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import grails.converters.JSON
import cn.com.wz.parent.FileUtil
import groovy.sql.Sql
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate
import parent.poi.ExcelUtils

/**
 * @Description 合格证调用SAP
 * @CreateTime 2014—05-24
 * @Author zhuwei
 */

class TransferToSapController extends BaseController {

    def synService
    LogService logService

    /**
     *@Description 合格证信息向SAP传送数据列表
     *@createTime 2014-05-24
     * *@Auther zhuwei
     */
    def transZcinfoToSapList() {
        render (view:'/cn/com/wz/vehcert/transferToSap/transZcinfoToSapList',model:[transFlag:params?.transFlag] );
    }

    /**
     *@Description 传送页面合格证信息查询
     *@createTime 2014-05-24
     **@Auther zhuwei
     * Update 合格证传输至车间只传输自己车间生产的合格证，营销公司传输所有产生的合格证信息
     * UpdateTime 2014-10-28 zhuwei
     */
    def jsonListSearch(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def organCode=[]
        if(loginUser.organs.size()>0){
            loginUser.organs.each{
                if(it.organCode){
                    organCode.add(it.organCode)
                }
            }
            params.organCode=organCode   //如果帐号没有管理任何组织，那么他就看到所有的传输列表数据
        }
        def result=synService.searchZcinfoByParams(params)
        render result
    }
    /**
     * @Description 为没有SAP号的合格证信息添加SAP序列号
     * @CreateTime 2018-04-13
     * @Author QJ
     */
    def zcinfoAddSapNo() {
        def params = params
        def id = params?.id
        def SAP_No = params?.SAP_No
        def msg
        def ZCInfoInstance = ZCInfo.get(id)
        def veh_zchgzbh = ZCInfoInstance.veh_Zchgzbh
        def veh_clsbdh  = ZCInfoInstance.veh_Clsbdh
        try {
            ZCInfoInstance.SAP_No = SAP_No
            ZCInfoInstance.save(flush: true)
            msg = 'SAP序列号更新成功'
        } catch (DataIntegrityViolationException e) {
            msg = 'SAP序列号更新失败'
        }
        def responseMsg = '合格证编号：' + veh_zchgzbh + '，VIN：' + veh_clsbdh +  '，操作结果：' + msg
        User loginUser = getCurrentUser()
        def userId = loginUser?.getId() //操作人
        logService.insertLog(userId, "addSapNoForZcinfo", responseMsg, "systemLog")
        render msg
    }
    /**
     * @Description 调用service将数据传输到SAP
     * @CreateTime 2014-05-26
     * @Author zhuwei
     */
    def transferData(){
        def params= params
        def opFlag=params?.opFlag
        def map
        try{
           map= synService.synZcinfo(params)
        } catch (Exception e){
            println e.cause?e.cause:e
        }

        def msg
        def type
        def msessageNo
         msg=map.msg
         type=map.type
        msessageNo=map.msessageNo
        if (map.type=='S'){
             //判断传输操作成功
            if(params?.ZOPIND=='1'){

                    if(opFlag=='0'){
                        msg='传输完成，'+ msg
                    }else{
                        msg='修改完成，'+ msg
                    }
            }else{   //opFlah=2&&ZOPIND=2
                msg='撤销完成，'+ msg
            }

        }else{
            //判断传输操作时报
            if(params?.ZOPIND=='1'){
                if(opFlag=='0'){
                    msg='传输失败，'+ msg
                }else{
                    msg='修改失败，'+ msg
                }
            }else{
                msg='撤销失败，失败原因'+ msg
            }

        }
        def result = [type:type,msg:msg]
        render result as JSON

    }

    /**
     *@Description 合格证信息向SAP传送数据列表
     *@createTime 2014-05-24
     * *@Auther zhuwei
     */
    def nzTransZcinfoToSapList() {
        render (view:'/cn/com/wz/vehcert/transferToSap/nz_transZcinfoToSapList',model:[transFlag:params?.transFlag] );
    }

    /**
     * @Description收获车间合格证信息向SAP传送数据
     * @CreateTime 2017-05-11 by zhuwei
     */
    def shTaransHarvestinfoToSapList(){
        render (view:'/cn/com/wz/vehcert/transferToSap/sh_transHarvestinfoToSapList',model:[transFlag:params?.transFlag] );
    }
    /**
     *@Description 专用车垃圾站向SAP传送数据列表
     *@createTime 2014-05-24
     * *@Auther zhuwei
     */
    def garbageTransInfoToSapList() {
        render (view:'/cn/com/wz/vehcert/transferToSap/garbage_transInfoToSapList',model:[transFlag:params?.transFlag] );
    }
    /**
     *@Description 传送页面合格证信息查询
     *@createTime 2014-05-24
     **@Auther zhuwei
     * Update 合格证传输至车间只传输自己车间生产的合格证，营销公司传输所有产生的合格证信息
     * UpdateTime 2014-10-28 zhuwei
     */
    def jsonListSearch_NZ(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def organCode=[]
        if(loginUser.organs.size()>0){
            loginUser.organs.each{
                if(it.organCode){
                    organCode.add(it.organCode)
                }
            }
            params.organCode=organCode
        }
        def result=synService.searchNZInfoByParams(params)
        render result
    }


    /**
     *@Description 传送页面收获车间合格证信息查询
     *@createTime 2017-05-11
     **@Auther zhuwei
     */
    def jsonListSearch_harvest(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def result=synService.searchHarvestInfoByParams(params)
        render result
    }

    /**
     *@Description 传送页面收获车间合格证信息查询
     *@createTime 2017-05-11
     **@Auther zhuwei
     */
    def jsonListSearch_garbage(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def loginUser=User.get(user.id)
        def result=synService.searchGarbageInfoByParams(params)
        render result
    }


    /**
     * @Description 调用service将农装数据传输到SAP
     * @CreateTime 2014-07-15
     * @Author zhuwei
     */
    def transferData_NZ(){
        def params= params
        def opFlag=params?.opFlag
        def map= synService.synZcinfo_NZ(params)
        def msg
        def type
        def msessageNo
        msg=map.msg
        type=map.type
        msessageNo=map.msessageNo
        if (map.type=='S'){
            //判断传输操作成功
            if(params?.ZOPIND=='1'){

                if(opFlag=='0'){
                    msg='传输完成，'+ msg
                }else{
                    msg='修改完成，'+ msg
                }
            }else{   //opFlah=2&&ZOPIND=2
                msg='撤销完成，'+ msg
            }

        }else{
            //判断传输操作时报
            if(params?.ZOPIND=='1'){
                if(opFlag=='0'){
                    msg='传输失败，'+ msg
                }else{
                    msg='修改失败，'+ msg
                }
            }else{
                msg='撤销失败，失败原因'+ msg
            }

        }
        def result = [type:type,msg:msg]
        render result as JSON

    }

    /**
     * @Description 调用service将收获车间数据传输到SAP
     * @CreateTime 2017-05-11
     * @Author zhuwei
     */
    def transferData_Harvest(){
        def params= params
        def opFlag=params?.opFlag
        def map= synService.synZcinfo_Harvest(params)
        def msg
        def type
        def msessageNo
        msg=map.msg
        type=map.type
        msessageNo=map.msessageNo
        if (map.type=='S'){
            //判断传输操作成功
            if(params?.ZOPIND=='1'){

                if(opFlag=='0'){
                    msg='传输完成，'+ msg
                }else{
                    msg='修改完成，'+ msg
                }
            }else{   //opFlah=2&&ZOPIND=2
                msg='撤销完成，'+ msg
            }

        }else{
            //判断传输操作时报
            if(params?.ZOPIND=='1'){
                if(opFlag=='0'){
                    msg='传输失败，'+ msg
                }else{
                    msg='修改失败，'+ msg
                }
            }else{
                msg='撤销失败，失败原因'+ msg
            }

        }
        def result = [type:type,msg:msg]
        render result as JSON

    }
    /**
     * @Description 调用service将收获车间数据传输到SAP
     * @CreateTime 2017-05-11
     * @Author zhuwei
     */
    def transferData_Garbage(){
        def params= params
        def opFlag=params?.opFlag
        def map= synService.synZcinfo_Garbage(params)
        def msg
        def type
        def msessageNo
        msg=map.msg
        type=map.type
        msessageNo=map.msessageNo
        if (map.type=='S'){
            //判断传输操作成功
            if(params?.ZOPIND=='1'){

                if(opFlag=='0'){
                    msg='传输完成，'+ msg
                }else{
                    msg='修改完成，'+ msg
                }
            }else{   //opFlah=2&&ZOPIND=2
                msg='撤销完成，'+ msg
            }

        }else{
            //判断传输操作时报
            if(params?.ZOPIND=='1'){
                if(opFlag=='0'){
                    msg='传输失败，'+ msg
                }else{
                    msg='修改失败，'+ msg
                }
            }else{
                msg='撤销失败，失败原因'+ msg
            }

        }
        def result = [type:type,msg:msg]
        render result as JSON

    }

}
