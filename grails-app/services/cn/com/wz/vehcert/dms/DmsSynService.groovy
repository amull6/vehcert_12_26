package cn.com.wz.vehcert.dms

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.vehcert.carstorage.CarStorage
import cn.com.wz.vehcert.carstorage.Temp
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import dms.AfficheModel
import dms.DeleteCarStorage
import dms.SynAffiche
import dms.TransChangeInfo
import dms.WzAfficheLocator
import dms.WzAfficheSoap12Stub
import dms.WzAfficheSoap_PortType
import org.apache.axis.client.Call
import org.apache.axis.message.SOAPHeaderElement

import javax.xml.namespace.QName
import javax.xml.rpc.holders.BooleanHolder
import javax.xml.rpc.holders.StringHolder
import javax.xml.soap.SOAPElement

/**
 * @Description 合格证系统与DMS系统的数据同步
 * @Create 2014-07-25 huxx
 */
class DmsSynService {
    def logService
    def servletContext
    /**
     * @Description 将公告信息同步的DMS
     * @Create 2014-07-25 huxx
     *
     * @TODO 1、增量方式获取公告信息
     * @TODO 2、判定是单线程还是多线程 大于10000用多线程 最多四个线程
     * @TODO 3、添加日志 同步开始时间、同步总数、失败失败总数 失败记录唯一号
     */
    def syn(){

        def startTime=DateUtil.getCurrentTime()
        servletContext.setAttribute('synThreadCount',0)  //同步需要的线程数量
        servletContext.setAttribute('failThreadCount',0)  //执行失败的线程数量
        servletContext.setAttribute('runThreadCount',0)  //执行正在执行的线程数

        def result=this.findCarStorageFroDms()
        def carStorageList=result?.lst
        def totalCount=result?.count

        if(totalCount>0&&totalCount<10000){

            servletContext.setAttribute('synThreadCount',1)
            def list=carStorageList?.subList(0,totalCount)
            Thread thread=new Thread(new SynAffiche(list,logService,servletContext,startTime));
            thread.start()
        }else if(totalCount>10000){
            servletContext.setAttribute('synThreadCount',4)
            double p =  Math.ceil(totalCount/4)
            int perNumber = (int) p;
            def list1=carStorageList?.subList(0,perNumber)
            def list2=carStorageList?.subList(perNumber,2*perNumber)
            def list3=carStorageList?.subList(2*perNumber,3*perNumber)
            def list4=carStorageList?.subList(3*perNumber,totalCount)

            Thread thread1=new Thread(new SynAffiche(list1,logService,servletContext,startTime));
            thread1.start()
            Thread thread2=new Thread(new SynAffiche(list2,logService,servletContext,startTime));
            thread2.start()
            Thread thread3=new Thread(new SynAffiche(list3,logService,servletContext,startTime));
            thread3.start()
            Thread thread4=new Thread(new SynAffiche(list4,logService,servletContext,startTime));
            thread4.start()
        }
    }

    /**
     * @Description 同步删除公告
     * @CreateTime 2014_08_14
     * @Author zhuwei
     */
    def delete(){
        def deleteCarStorageNos=this.findDeleteCarStorageFroDms()
        if(deleteCarStorageNos.size()>0){
            new DeleteCarStorage(deleteCarStorageNos,logService)
        }
    }
    /**
     * @Description 将合格证更换记录传给CRM   】
     * @CreateTime 2015-12-11
     * @Author zhuwei
     */
    def updateVehicle(){
        def tranfList=this.findChangRecordForCRM()
        if(tranfList.size()>0){
            new TransChangeInfo(tranfList,logService)
        }

    }

    /**
     * @Description查找创建时间大于上次同步时间的公告传给定时作业
     * @CreateTime 2014_08_08
     * @Author zhuwei
     * @Update 将修改后的公告也通过同步接口传递给DMS
     */
       def findCarStorageFroDms(){
           def count
           def lastDmsSynTime=DictionaryValue.findByCode('lastDmsSynTime').value1
           def cel={
               or{
                   ge('createTime',lastDmsSynTime)
                   ge('updateTime',lastDmsSynTime) //将修改后的公告也通过同步接口传递给DMS
               }
           }
           def lst=CarStorage.createCriteria().list(cel)
           def result = [lst: lst,count: lst.size()]
           return result
       }
        /**
         * @Description查找插入时间大于上次同步时间的删除公告传给定时作业
         * @CreateTime 2014_08_13
         * @Author zhuwei
         * @Update 查找删除的公告时筛选掉删除的一致性证书  wzh_type=‘1’      2015-04-15 zhuwei
         * @Update 只查找TEMP表中WZH_TYPE=1，  insertTime> lastDmsSynTime,  car_storage_no不为空的公告
         */
        def findDeleteCarStorageFroDms(){
            List car_storage_noS= new ArrayList()
            def lastDmsSynTime=DictionaryValue.findByCode('lastDmsSynTime').value1
            def cel={
                   ge('insertTime',lastDmsSynTime)
                   eq('wzh_type',1)        //根据  wzh_type字段值查找删除的公告，不查找删除的一致性证书
                   isNotNull('car_storage_no')
            }
            def lst=Temp.createCriteria().list(cel)
            lst.each {p->
                car_storage_noS.add(p.car_storage_no)
            }
            return car_storage_noS
        }
    /**
     * @Description 查找要传给CRM的合格证变更记录
     * @Description 只传审核通过的有整车合格证编号的并且能往CRM传输的合格证更换记录
     * @CreateTime 2015-12-11
     * @Author zhuwei
     * @Update 2015-12-11 不再参考上次同步时间作为下次同步依据。对于同步返回标示为：false，的并且返回消息为：无效车辆信息！
     * 【即合格证里面有数据但是CRM里面没有的数据的变更记录不再再次传给CRM，防止不停传输】
     * @Update 2018-05-02 取消只传输整车换整车的车辆信息变更，底盘换整车，底盘换底盘的也传CRM,SAP
     */
      def findChangRecordForCRM(){
          List transfChangeDataList= new ArrayList()
          def cel={
              eq('veh_coc_status',1)        //审核状态为通过
//              eq('veh_Clztxx_R','QX')
//              eq('veh_Clztxx_R','QX')       //传输变更记录只要整车换整车的记录
              or{
//                  int transToCrm=0   //0表示要传给CRM，1禁止传给CRM  ，2传给CRM成功，3传给CRM失败
                  eq('transToCrm',0)
                  eq('transToCrm',3) //将未传输的或者传输失败，注意不是禁止传输的，筛选出来
              }
              isNotNull('veh_Zchgzbh_0_R')  //更换后的整车合格证编号不为空
              isNotNull('SAP_No')           //更换后的SAP序列号不能为空（新DMS打开）
              eq('formal','0')
              order("authTime","asc")
          }
          transfChangeDataList=ZCInfoReplace.createCriteria().list(cel)
          return transfChangeDataList
      }




}
