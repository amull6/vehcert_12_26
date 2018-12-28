package cn.com.wz.vehcert.dms

import cn.com.wz.parent.DateUtil
import getDataFromCrm.GetDataFromCRMLocator
import getDataFromCrm.GetDataFromCRMSoap12Stub
import newDms.NewDeleteCarStorage
import newDms.NewSynAffiche
import newDms.NewTransChangeInfo

import javax.xml.soap.SOAPElement
import org.apache.axis.message.SOAPHeaderElement

/**
 * @Description 合格证系统与DMS/CRM系统的数据同步
 * @Create 2017-07-26 zhuwei
 */
class NewDmsSynService {
    def logService
    def servletContext
    def dmsSynService
    def synService
    /**
     * @Description 将公告信息同步的DMS【二期】
     * @Create 2017-07-26 zhuwei
     * @TODO 1、增量方式获取公告信息
     * @TODO 2、判定是单线程还是多线程 大于10000用多线程 最多四个线程
     * @TODO 3、添加日志 同步开始时间、同步总数、失败失败总数 失败记录唯一号
     */
    def newSyn(){

        def startTime=DateUtil.getCurrentTime()
        servletContext.setAttribute('synThreadCount',0)  //同步需要的线程数量
        servletContext.setAttribute('failThreadCount',0)  //执行失败的线程数量
        servletContext.setAttribute('runThreadCount',0)  //执行正在执行的线程数

        def result=dmsSynService.findCarStorageFroDms()
        def carStorageList=result?.lst
        def totalCount=result?.count

        if(totalCount>0&&totalCount<10000){

            servletContext.setAttribute('synThreadCount',1)
            def list=carStorageList?.subList(0,totalCount)
            Thread thread=new Thread(new NewSynAffiche(list,logService,servletContext,startTime));
            thread.start()
        }else if(totalCount>10000){
            servletContext.setAttribute('synThreadCount',4)
            double p =  Math.ceil(totalCount/4)
            int perNumber = (int) p;
            def list1=carStorageList?.subList(0,perNumber)
            def list2=carStorageList?.subList(perNumber,2*perNumber)
            def list3=carStorageList?.subList(2*perNumber,3*perNumber)
            def list4=carStorageList?.subList(3*perNumber,totalCount)

            Thread thread1=new Thread(new NewSynAffiche(list1,logService,servletContext,startTime));
            thread1.start()
            Thread thread2=new Thread(new NewSynAffiche(list2,logService,servletContext,startTime));
            thread2.start()
            Thread thread3=new Thread(new NewSynAffiche(list3,logService,servletContext,startTime));
            thread3.start()
            Thread thread4=new Thread(new NewSynAffiche(list4,logService,servletContext,startTime));
            thread4.start()
        }
    }
    /**
     * @Description 同步删除公告
     * @CreateTime 2017_07_26
     * @Author zhuwei
     */
    def newDelete(){
        //沿用了初期查找删除公告的放大
        def deleteCarStorageNos=dmsSynService.findDeleteCarStorageFroDms()
        if(deleteCarStorageNos.size()>0){
            new NewDeleteCarStorage(deleteCarStorageNos,logService)
        }
    }
    /**
     * @Description 将合格证更换记录传给CRM【新接口地址】
     * @CreateTime 2017-07-27
     * @Author zhuwei
     * @Update 传输到CRM变更信息成功的同时传到SAP
     * @UpdateTime 2018-03-31
     */
    def newUpdateVehicle(){
        def tranfList=dmsSynService.findChangRecordForCRM()
        if(tranfList.size()>0){
            new NewTransChangeInfo(tranfList,logService)
        }
        if(tranfList.size()>0){
            tranfList.each{zcInfoReplace->
                if (zcInfoReplace.transToCrm==2)   {
                    synService.synZcinfoReplace(zcInfoReplace)
                }
            }

        }
    }
    /**
     * @Description 根据经销商代码查询CRM系统里面经销商下面的车辆VIN
     ** @CreateTime 2018-06-22
     * @Author QJ
     */
    def findVinOfDistributor(def distributorCode){
        try {
//            GetDataFromCRMSoap12Stub service=  new GetDataFromCRMLocator().getGetDataFromCRMSoap12(new URL("http://crmwebservice.wuzheng.com.cn:8089/GetDataFromCRM.asmx"))      //正式地址
            GetDataFromCRMSoap12Stub service=  new GetDataFromCRMLocator().getGetDataFromCRMSoap12(new URL("http://192.168.0.34:8089/GetDataFromCRM.asmx"))      //测试地址
//            ################新接口已经不用使用账号和密码登陆了，所以以下部分注释掉BEGIN ############
//            service.username="wzcrm"
//            service.password="WZcrm365@2017"
            SOAPHeaderElement header = new SOAPHeaderElement("http://tempuri.org/","CrmSoapHeader")
            SOAPElement soap = null;
            soap = header.addChildElement("UserName");
            soap.addTextNode("wzcrm");
            soap = header.addChildElement("PassWord");
            soap.addTextNode("WZcrm365@2017");
            service.setHeader(header)
            def vinList = service.getDealerVehicleInfo(distributorCode)
            return vinList
//            println(logoutResult)
        }catch(Exception e){
            println e.cause?e.cause:e
        }
    }
}
