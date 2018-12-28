package cn.com.wz.vehcert.environment
import cn.com.wz.parent.base.BaseController
import groovy.sql.Sql
import org.springframework.core.io.support.PropertiesLoaderUtils;

class SynchroController extends BaseController {
    def sqlToolService
    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther Qj
     *@createTime 2016-12-20
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/environment/environmentDownload/heavyDieselDownloadList",model: params)
    }

    /**
     * @Description 环保信息同步（公告信息、 一致性信息）
     * @Create 2017-01-10 Qj
     */
    def synchroTable()
    {
        long  statrTime = System.currentTimeMillis()
        //获取同步数据源(远程服务器)
        def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
        def remoteUrl= dsp.getProperty("remoteUrl")
        def remoteUserName=dsp.getProperty("remoteUserName")
        def remotePassword= dsp.getProperty("remotePassword")
        def remoteDriverClassName=dsp.getProperty("remoteDriverClassName")

        def url = dsp.getProperty("url")
        def userName = dsp.getProperty("userName")
        def password = dsp.getProperty("password")
        def driverClassName = dsp.getProperty("driverClassName")

        if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
            return render("远程服务器信息配置不完整！")
        }
        def msg=""

        ////Oracle数据连接
        Sql ora_con = groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
        ////Sql数据库连接
        Sql sql_con  = groovy.sql.Sql.newInstance("${url}","${userName}","${password}","${driverClassName}");

        try{
            def  totalTime=0
            //同步重柴环保信息
            def heavyDieselResult=sqlToolService.synHeavyDiesel(ora_con,sql_con)
            def lightDualResult=sqlToolService.synLightDual(ora_con,sql_con)
            def lightDieselResult=sqlToolService.synLightDiesel(ora_con,sql_con)
            def heavyGasResult=sqlToolService.synHeavyGas(ora_con,sql_con)
            totalTime=(System.currentTimeMillis()-statrTime)/1000
            msg="重型柴油/城市汽车环保信息同步条数：${heavyDieselResult.totalCount} 双燃料/轻型汽油车辆环保信息同步条数：${lightDualResult.totalCount} 轻型柴油国四/国五环保信息同步条数：${lightDieselResult.totalCount} 重型燃气环保信息同步条数：${heavyGasResult.totalCount} 耗时: "+totalTime+"S</br>"+"</br>结果: <font color='green'>同步成功!</font>"
//
        }catch (Exception e){
            e.cause?.printStackTrace();
            msg="<font color='red'>同步失败,检查网络相关信息后重试!【${e.cause?e.cause:e}】</font>"
        } finally{
            sqlToolService.closeCon(sql_con,ora_con);
            render msg
        }
    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }


}
