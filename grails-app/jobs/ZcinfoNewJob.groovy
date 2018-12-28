/**
 * Created with IntelliJ IDEA.
 * Desciption 合格证与DMS/CRM新接口执行JOB【新接口地址】
 * User: 朱伟
 * Date: 17-7-26
 * Time: 上午9:55
 * To change this template use File | Settings | File Templates.
 */
class ZcinfoNewJob {
    def grailsApplication
    def newDmsSynService
    static triggers={
  //      cron name:'testTrigger',cronExpression:'0 0/3 * * * ?',startDelay:10l           //每分钟执行一次
//          cron name:'testTrigger',cronExpression:'0 0 22 * * ?',startDelay:10l         //每天晚上10点更新
//        cron name:'testTrigger',cronExpression:'0 15 13 * * ?',startDelay:10l         //上午8更新
//        cron name:'cronTrigger', cronExpression:'0 0 1/6 * * ?', startDelay: 10l   //每天凌晨1点开始，每6小时执行一次
//        cron name:'cronTrigger', cronExpression:'20 30 8 * * ?', startDelay: 10l
    }

    def execute(){
        try{
            println('执行job了！！！！')
            newDmsSynService.newSyn()   //定时任务的同步作业
            newDmsSynService.newDelete()   //定时任务的删除作业  /
            newDmsSynService.newUpdateVehicle() //将当天更换的合格证信息传给CRM
        }catch (Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
        }
    }
}
