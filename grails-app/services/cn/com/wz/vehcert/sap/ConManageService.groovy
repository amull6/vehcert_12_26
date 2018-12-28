package cn.com.wz.vehcert.sap

import com.sap.conn.jco.JCoDestination
import com.sap.conn.jco.JCoDestinationManager
import com.sap.conn.jco.ext.DestinationDataProvider


/**
 * @Description 对sap的连接进行管理
 * @Create: 2014-5-22 下午4:29  huxx
 */
class ConManageService {
    def grailsApplication
    static String ABAP_AS = "ABAP_AS_WITHOUT_POOL";
    static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
    static String ABAP_MS = "ABAP_MS_WITHOUT_POOL";
    /**
     * @Description 直接连接sap
     * @param params params.client 客户端号、 params.userId登陆用户id、params.password登陆密码、params.language访问语言、params.server sap服务器地址ip、params.sn系统标识号
     * @Result 返回连接对象
     * @Create 2014-05-22 huxx
     */
    public  JCoDestination Connect(params){
        params.client=grailsApplication.config.sap.client
        params.userId=grailsApplication.config.sap.userId
        params.password=grailsApplication.config.sap.password
        params.language=grailsApplication.config.sap.language
        params.server=grailsApplication.config.sap.server
        params.sn=grailsApplication.config.sap.sn
        createDataFile(params)
        JCoDestination destination = null;
//        try {
//            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
//        } catch (Exception e) {
//           throw e
//        }
//        return destination;


        //使用subConfig文件的sap连接配置，多次连接尝试
        try {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
            def connectTry1=destination.getRepository();   // 第一次连接尝试
        } catch (Exception e) {
            try {
                params.server=grailsApplication.config.sap.server1
                createDataFile(params)
                destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
                def connectTry2=destination.getRepository() ; //第二次连接尝试
            }catch (Exception e1) {
                try{
                    params.server=grailsApplication.config.sap.server2
                    createDataFile(params)
                    destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
                    def connectTry3=destination.getRepository() ; //第三次连接尝试
                }catch (Exception e2){
                    params.server=grailsApplication.config.sap.server3
                    createDataFile(params)
                    destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
                    def connectTry4=destination.getRepository() ; //第四次连接尝试
                }
            }
        }
        return destination;

    }

    def  createDataFile(params) {
//        params.client=grailsApplication.config.sap.client
//        params.userId=grailsApplication.config.sap.userId
//        params.password=grailsApplication.config.sap.password
//        params.language=grailsApplication.config.sap.language
//        params.server=grailsApplication.config.sap.server
//        params.sn=grailsApplication.config.sap.sn

        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,params.server);//SAP 服务器地址
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,params.sn );//系统编号
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,params.client );//SAP集团
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,params.userId );//SAP用户名
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,params.password);//
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,params.language );//登陆语言
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,"10" );//最大连接数
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,"30" );//最大连接

        def name=ABAP_AS_POOLED
        def suffix="jcoDestination"
        // TODO Auto-generated method stub
        File cfg = new File(name+"."+suffix);
            try {
                FileOutputStream fos = new FileOutputStream(cfg, false);
                connectProperties.store(fos, "SAP Connect For RFC!");
                fos.close();
            } catch (Exception e) {
                throw new RuntimeException("Unable to create the destination file:"+cfg.getName());
            }

    }






}
