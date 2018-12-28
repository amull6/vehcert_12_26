package cn.com.wz.stvehcert

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import grails.converters.JSON;


class StTransferToSApController extends BaseController {
    def   synService
    /**
     *@Description 山拖合格证信息向SAP传送数据列表
     *@createTime 2016-06-17
     * *@Auther zhuwei
     */
    def stTransZcinfoToSapList() {
        def transFlag = params?.transFlag
        render (view:'/cn/com/wz/stvehcert/tractorModle/stTransferToSap/st_transZcinfoToSapList',model:[transFlag:transFlag] );
    }


    /**
     *@Description 传送页面合格证信息查询
     *@createTime 2016-06-17
     **@Auther zhuwei
     * Update 合格证传输至车间只传输自己车间生产的合格证，营销公司传输所有产生的合格证信息
     * UpdateTime 2016-06-17 zhuwei
     */
    def jsonListSearch_ST(){
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
        def result=synService.searchSTInfoByParams(params)
        render result
    }

    /**
     * @Description 调用service将农装数据传输到SAP
     * @CreateTime 2016-06-17
     * @Author zhuwei
     */
    def transferData_ST(){
        def params= params
        def opFlag=params?.opFlag
        def map= synService.synZcinfo_ST(params)
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
