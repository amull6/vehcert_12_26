import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue

/**
 * @Description 合格证定时任务上传类
 */
class ZcinfoJob {
    def grailsApplication
    def dmsSynService
    static triggers={
//        cron name:'testTrigger',cronExpression:'0 0/15 * * * ?',startDelay:10l           //每分钟执行一次
//        cron name:'testTrigger',cronExpression:'0 0 22 * * ?',startDelay:10l         //每天晚上10点更新
//        cron name:'testTrigger',cronExpression:'0 15 13 * * ?',startDelay:10l         //上午8更新
//        cron name:'cronTrigger', cronExpression:'0 0 1/6 * * ?', startDelay: 10l   //每天凌晨1点开始，每6小时执行一次
//        cron name:'cronTrigger', cronExpression:'20 30 8 * * ?', startDelay: 10l
    }

    def execute(){
        try{
            dmsSynService.delete()   //定时任务的删除作业
            dmsSynService.syn()   //定时任务的同步作业
            dmsSynService.updateVehicle() //将当天更换的合格证信息传给CRM
        }catch (Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
        }
    }
}
