package cn.com.wz.parent.common
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User;
/**
 *@Description 弹出框公共处理类
 *@Auther huxx
 *@createTime 2013-04-03 下午3:26:50
 */
class DialogController extends BaseController {

    /**@Description 跳转到通用收信人弹出框
     * @author wangta0
     * @params tabId后台tab页面的id，flag验证操作标识，flag=“shortMessage” 表示需要进行手机号的验证
     * receiverIds表示已选择的执行人，多个执行人用下划线分割
     * @createTime 2012年11月19日上午10：30
     * @update huxx 2013-04-03
     */
    def receiver(){
        def receivers=[]
        def receiverInfos=params.receiverIds
        if(receiverInfos!=null&&receiverInfos.length()>0){
            def idsArray=receiverInfos?.split('_')
            idsArray?.each{o->
                def infos=o.split(";")
                def temp=[:]
                temp.receiverInfo=o
                temp.id=infos[0]
                temp.userName=infos[1]
                temp.realName=infos[2]
                temp.organNames=infos[3]
                temp.mPhone=infos[4]
                receivers.add(temp)
            }
        }
        render(view:'/cn/com/wz/parent/common/receiver',model:[receivers:receivers,canNotAddIds:params.canNotAddIds,msg:params.msg])
    }
}
