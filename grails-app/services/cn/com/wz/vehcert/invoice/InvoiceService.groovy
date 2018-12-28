package cn.com.wz.vehcert.invoice

import invoice.InvoiceFeedBack
import invoice.InvoicesServiceLocator

/**
 * @Description 票据管理模块服务类
 * @Create 2013-12-07 huxx
 */
class InvoiceService {
    def getClient(String flag,URL url){
        def result=null
        if (flag =="test"){
            invoice.test.TestInvoicesLocator client = new invoice.test.TestInvoicesLocator();
            result=client.getBasicHttpBinding_ITestInvoices(url)
        }else{
            InvoicesServiceLocator client = new InvoicesServiceLocator();
            result=client.getBasicHttpBinding_IInvoicesService(url)
        }

        return result
    }
    /**
     * @Description 测试服务是否正常
     * @param grailsApplication
     * @return
     * @Create 2013-12-09 huxx
     */
    def checkService(grailsApplication){
        String servicePath=grailsApplication.config.invoice.path;
        String result=getClient(grailsApplication.config.invoice.flag,new URL(servicePath)).helloWorld()
        return result
    }

    /**
     * @Descritpion : 上传票据加密文件
     * @param grailsApplication
     * @param file
     * @return
     * @Create 2013-12-09 huxx
     */
    def uploadInvoices(grailsApplication,byte[] file){
        String servicePath=grailsApplication.config.invoice.path;
        String userName=grailsApplication.config.invoice.userName;
        String key=grailsApplication.config.invoice.key
        String password=grailsApplication.config.invoice.password;
        InvoiceFeedBack result=getClient(grailsApplication.config.invoice.flag,new URL(servicePath)).uploadInvoices(userName,password,file,key)
        return result
    }
    /**
     * @Description 关联票据信息和合格证信息
     * @param grailsApplication
     * @param feedBack
     * @return
     * @Create 2013-12-10 huxx
     */
    def relateInvoices(grailsApplication,InvoiceFeedBack feedBack){
        String servicePath=grailsApplication.config.invoice.path;
        String userName=grailsApplication.config.invoice.userName;
        String key=grailsApplication.config.invoice.key
        String password=grailsApplication.config.invoice.password;
        InvoiceFeedBack result=getClient(grailsApplication.config.invoice.flag,new URL(servicePath)).relateInvoices(userName,password,feedBack,key)
        return result
    }

}
