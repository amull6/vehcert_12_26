package cn.com.wz.vehcert.environment

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import grails.converters.JSON
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.jdbc.core.JdbcTemplate;


class EnvirSubmitController extends BaseController {

    def envirSubmitService
    def zcInfoService
    def index() {}
    def list()  {
        render(view: '/cn/com/wz/vehcert/environment/environmentSubmit/index')
    }
    /**
     *@Description 获取列表信息
     *@Auther Qj
     *@createTime 2016-2-6
     */
    def jsonList(){
        def aaa = params
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def result=envirSubmitService.selectByParams(params)
        render result as JSON
    }
    /**
     * @Description 将环保信息信息提交到服务器中
     * @Create 2016-08-05 Qj
     */
    def uploadToServer(){
        def msg=""
        try{
            //获取同步数据源
            def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
            def remoteUrl= dsp.getProperty("remoteUrl")
            def remoteUserName=dsp.getProperty("remoteUserName")
            def remotePassword= dsp.getProperty("remotePassword")
            def remoteDriverClassName=dsp.getProperty("remoteDriverClassName")
            if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
                return render("远程服务器信息配置不完整！")
            }
            def remoteDB=groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
            def leftDay=grailsApplication.config.client.leftDay
            //同步一致性证书
            def FailList=[]
            def map=[:]
            map.remoteDB=remoteDB
            map.leftDay=leftDay
            map.time1=params.time1
            map.time2=params.time2
            FailList=envirSubmitService.uploadEnvironment(map)
            if (FailList){
                msg="环保随车清单上传失败记录id：${FailList}"
            }else{
                msg='数据同步成功'
            }
        }catch(Exception e){
            e.cause?.printStackTrace()
            msg="上传终止！终止原因：${e.cause?e.cause:e}"
        }finally{
            render msg
        }
    }
    /**
     * @Description 将未提交环保信息全部提交到服务器中
     * @Create 2018-12-20 QJ
     */
    def uploadAllToServer(){
        def msg=""
        try{
            //获取同步数据源
            def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
            def remoteUrl= dsp.getProperty("remoteUrl")
            def remoteUserName=dsp.getProperty("remoteUserName")
            def remotePassword= dsp.getProperty("remotePassword")
            def remoteDriverClassName=dsp.getProperty("remoteDriverClassName")
            if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
                return render("远程服务器信息配置不完整！")
            }
            def remoteDB=groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
            def leftDay=grailsApplication.config.client.leftDay
            //同步一致性证书
            def FailList=[]
            def map=[:]
            map.remoteDB=remoteDB
            map.leftDay=leftDay
            FailList=envirSubmitService.uploadEnvironment(map)
            if (FailList){
                msg="环保随车清单上传失败记录id：${FailList}"
            }else{
                msg='数据同步成功'
            }
        }catch(Exception e){
            e.cause?.printStackTrace()
            msg="上传终止！终止原因：${e.cause?e.cause:e}"
        }finally{
            render msg
        }
    }
}
