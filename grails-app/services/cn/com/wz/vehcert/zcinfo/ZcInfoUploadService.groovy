package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.carstorage.PreCarStorage
import org.apache.commons.lang.ArrayUtils
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.web.context.support.WebApplicationContextUtils
import vehcert.CertificateInfo
import vehcert.CertificateRequestService
import vehcert.CertificateRequestServiceLocator
import vehcert.CertificateRequestServiceSoap_PortType
import vehcert.NameValuePair
import vehcert.OperateResult

import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar

/**
 * @Description: 合格证信息上传到国家系统中i
 * @Create: 13-9-2 下午5:25   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class ZcInfoUploadService {
    def sqlService
    def zcInfoService
    /**
     * @description 合格证信息上传到国家系统
     * @param ids
     * @param loginUser 上传用户  ，自动上传时默认使用system的id
     * @param uploadType  上传类型，0自动上传；1经销商手动上传 ,2上传失败页面上传，3需要修改或撤销页面的上传，5上传管理人员手动上传
     * @return
     * @Update 2013-10-28 huxx 添加操作标识，opFlag的值可为”insert","overTime","update"分别表示上传、补传 、修改
     */
    def upload(ids,loginUser,uploadType,grailsApplication,String opFlag,String reason){
        //判断操作参数是否正确
        if (!["insert","overTime","update"].contains(opFlag)){
            def map=[:]
            map.msg="操作参数不正确！"
            map.flag=false
            return map
        }

        //设置上传人id，自动上传时使用system的id
        def uploaderId=""
        if (uploadType=='0'){
            def user=User.findByUserName('system')
            uploaderId=user?.id
        }else{
            uploaderId=loginUser?.id
        }

        def nowTime= DateUtil.getCurrentTime()

        String msg=""
        int succount=0
        int failcount=0
        boolean flag=true

        String failedzcinfo="" //上传失败的合格证编号

        def idArr=ids?.split("_")
        try{
            idArr.each {id->

                def zcinfoInstance=null
                if (uploadType=='0' || uploadType=='2'||uploadType=='5'){//自动上传
                   zcinfoInstance=ZCInfo.get(id)
                }else if (uploadType=='1'){ //经销商手动上传，传入的时下载合格证编号的id
                    ///获得经销商下载的合格证信息
                    def ZCInfoTempInstance = ZCInfoTemp.get(id)
                    ////根据经销商下载的合格证信息 查询当前服务器正式 合格证信息( 根据虚拟号编号 )
                    zcinfoInstance  = ZCInfo.findByWeb_virtualcode(ZCInfoTempInstance.web_virtualcode)
                }else if (uploadType=='3'){//需要撤销或修改页面的上传传入的是合格证的合格证编号
                    zcinfoInstance=ZCInfo.findByVeh_Zchgzbh(id)
                }

                if (!zcinfoInstance){
                    msg="合格证信息不存在！"
                    failcount+=1
                    return
                }

                def needNew=false
                def checkMessage=uploadCheckCarStorage(zcinfoInstance)
                if (checkMessage){
                    msg+="合格证编号：${zcinfoInstance.veh_Zchgzbh}"+checkMessage +"<br/>"
                    failcount+=1

                    if ("1".equals(zcinfoInstance.web_status)){
                        zcinfoInstance.delete(flush: true)
                        needNew=true
                    }

                    zcinfoInstance.upload_Failddm= checkMessage
                    zcinfoInstance.web_status='2'
                    zcinfoInstance.uploader_Id = uploaderId
                    zcinfoInstance.uploader_Time = nowTime

                    if (needNew){
                        def zcinfo1=new ZCInfo(zcinfoInstance.properties)
                        zcinfo1.save(flush:true)
                    }else{
                        zcinfoInstance.save(flush:true)
                    }
                    return
                }

                CertificateInfo certificateInfo = new CertificateInfo();

                def curTime=DateUtil.getCurrentTime("yyyy-MM-dd")
                //所有属性赋值
                certificateInfo.VERCODE = zcinfoInstance.vercode//校验码
                if(zcinfoInstance.veh_Rlzl=="电"){
                    certificateInfo.CDDBJ ="1"//纯电动标记
                }else{
                    certificateInfo.CDDBJ ="2"//纯电动标记
                }
                certificateInfo.CLLX=zcinfoInstance.veh_Clfl//车辆类型
                certificateInfo.CLSCDWMC =zcinfoInstance.veh_Clzzqymc//车辆生产单位名称
                certificateInfo.CLZTXX=zcinfoInstance.veh_Clztxx//车辆状态信息

                certificateInfo.CZRQ=convertRq(curTime)//操作日期
                certificateInfo.CREATETIME=convertRq(curTime)//创建日期
                certificateInfo.UPDATETIME=convertRq(curTime)//更新日期
                certificateInfo.FEEDBACK_TIME=convertRq(curTime)

                certificateInfo.DYWYM=zcinfoInstance.veh_Dywym//打印唯一码
                certificateInfo.QYID=zcinfoInstance.veh_Qyid//企业编号
                certificateInfo.YH=zcinfoInstance.veh_Yh//油耗
                certificateInfo.ZCHGZBH=zcinfoInstance.veh_Zchgzbh//整车合格证书编号
//                certificateInfo.ZXZS="1"//转向轴数
                certificateInfo.ZZBH=zcinfoInstance.veh_Zzbh//纸张编号
                certificateInfo.BGCAZZDYXZZL=zcinfoInstance.veh_Bgcazzdyxzzl//外挂车鞍座总质量
                certificateInfo.BZ=zcinfoInstance.veh_Bz//备注
                certificateInfo.CJH=zcinfoInstance.veh_Cjh//车架号
                certificateInfo.CLMC=zcinfoInstance.veh_Clmc//车辆名称
                certificateInfo.CLPP=zcinfoInstance.veh_Clpp//车辆品牌
                certificateInfo.CLSBDH=zcinfoInstance.veh_Clsbdh//车辆识别代码
                certificateInfo.CLXH=zcinfoInstance.veh_Clxh//车辆型号
                certificateInfo.CLZZQYMC=zcinfoInstance.veh_Clzzqymc//车辆制造企业名称
                certificateInfo.CLZZRQ=convertRq1(zcinfoInstance.veh_Clzzrq)//车辆制造日期
                certificateInfo.CPH=zcinfoInstance.veh_Cpggh//产品号
                certificateInfo.CPSCDZ=zcinfoInstance.veh_Cpscdz//产品生产地址
                certificateInfo.CSYS=zcinfoInstance.veh_Csys//车身颜色
                certificateInfo.DPHGZBH=zcinfoInstance.veh_Dphgzbh//底盘合格证书编号
                certificateInfo.DPID=zcinfoInstance.veh_Dpid//底盘ID
                certificateInfo.DPXH=zcinfoInstance.veh_Dpxh//底盘型号
                certificateInfo.EDZK=zcinfoInstance.veh_Edzk//额定载客
                certificateInfo.EDZZL=zcinfoInstance.veh_Edzzl//额定载质量
                certificateInfo.FDJH=zcinfoInstance.veh_Fdjh//发动机号
                certificateInfo.FDJXH=zcinfoInstance.veh_Fdjxh//发动机型号
                certificateInfo.FZRQ=convertRq1(zcinfoInstance.veh_Fzrq)//发证日期
                certificateInfo.GBTHPS=zcinfoInstance.veh_Gbthps//钢板弹簧片数
                certificateInfo.GGSXRQ=convertRq(zcinfoInstance.veh_Ggsxrq)//公告生效日期
                certificateInfo.GL=zcinfoInstance.veh_Gl//功率
                certificateInfo.HLJ=zcinfoInstance.veh_Hlj//后轮距
                certificateInfo.HXNBC=zcinfoInstance.veh_Hxnbc
                certificateInfo.HXNBG=zcinfoInstance.veh_Hxnbg
                certificateInfo.HXNBK=zcinfoInstance.veh_Hxnbk
                certificateInfo.HZDCZFS=zcinfoInstance.veh_Hzdczfs//后制动操作方式
                certificateInfo.JSSZCRS=zcinfoInstance.veh_Jsszcrs//驾驶室准乘人数
                certificateInfo.LTGG=zcinfoInstance.veh_Ltgg//轮胎规格
                certificateInfo.LTS=zcinfoInstance.veh_Lts//轮胎数
                certificateInfo.PC=zcinfoInstance.veh_Ggpc//批次
                certificateInfo.PFBZ=zcinfoInstance.veh_Pfbz//排放标准
                certificateInfo.PL=zcinfoInstance.veh_Pl//排量
                certificateInfo.QYBZ=zcinfoInstance.veh_Qybz//企业标准
                certificateInfo.QYQTXX=zcinfoInstance.veh_Qyqtxx//企业其他信息
                certificateInfo.QZDCZFS=zcinfoInstance.veh_Qzdczfs//前制动操作方式
                certificateInfo.QZDFS=zcinfoInstance.veh_Qzdfs//前制动方式
                certificateInfo.RLZL=zcinfoInstance.veh_Rlzl//燃料种类
                certificateInfo.WKC=zcinfoInstance.veh_Wkc
                certificateInfo.WKG =zcinfoInstance.veh_Wkg
                certificateInfo.WKK =zcinfoInstance.veh_Wkk
                certificateInfo.ZBZL=zcinfoInstance.veh_Zbzl//整备质量
                certificateInfo.ZGCS=zcinfoInstance.veh_Zgcs//最高车速
                certificateInfo.ZH=zcinfoInstance.veh_Zh//轴荷
                certificateInfo.ZJ=zcinfoInstance.veh_Zj//轴距
                certificateInfo.ZQYZZL=zcinfoInstance.veh_Zqyzzl//准牵引总质量
                certificateInfo.ZS=zcinfoInstance.veh_Zs//轴数
                certificateInfo.ZXXS=zcinfoInstance.veh_Zxxs//转向形式
                certificateInfo.ZZL=zcinfoInstance.veh_Zzl//总质量
                certificateInfo.ZZLLYXS=zcinfoInstance.veh_Zzllyxs//载质利用系数
                certificateInfo.WZHGZBH=zcinfoInstance.veh_Zchgzbh_0//完整合格证编号
                certificateInfo.PZXLH =zcinfoInstance.veh_pzxlh    //配置序列号
                certificateInfo.QLJ=zcinfoInstance.veh_Qlj //前轮距

                if (certificateInfo.QLJ && certificateInfo.QLJ.indexOf('/')>0){
                    certificateInfo.setZXZS('2')
                }else{
                    certificateInfo.setZXZS('1')
                }

                //赋值完成后添加到队列
                if (zcinfoInstance.veh_Type=="0"){ //汽车
                    CertificateRequestService client = new CertificateRequestServiceLocator();
                    String serverPath=grailsApplication.config.serverupload.carpath;
                    CertificateRequestServiceSoap_PortType service=client.getCertificateRequestServiceSoap(new URL(serverPath))
                    OperateResult  oResult=new OperateResult()
                    if ("insert".equals(opFlag)){
                        oResult=service.uploadInsert_Ent(certificateInfo)
                    }else if ("overTime".equals(opFlag)){
                        CertificateInfo[] d=new CertificateInfo[1]
                        d[0]=certificateInfo
                        oResult=service.uploadOverTime_Ent(d,reason)
                    }else if("update".equals(opFlag)){
                        CertificateInfo[] d=new CertificateInfo[1]
                        d[0]=certificateInfo
                        oResult=service.uploadUpdate_Ent(d,reason)
                    }

                    def errorMessage=GetResultMessage(oResult)
                    if (oResult.resultCode==0){ //上传成功

                        if (!"1".equals(zcinfoInstance.web_status)){
                            zcinfoInstance.delete(flush: true)
                            needNew=true
                        }

                        zcinfoInstance.web_status='1'
                        zcinfoInstance.upload_Failddm= String.valueOf(oResult.resultCode)
                        zcinfoInstance.uploader_Id = uploaderId
                        zcinfoInstance.uploader_Time = nowTime

                        succount=succount+1
                    }else{
                        if ("1".equals(zcinfoInstance.web_status)){
                            zcinfoInstance.delete(flush: true)
                            needNew=true
                        }
                        msg+=errorMessage
                        zcinfoInstance.upload_Failddm= String.valueOf(oResult.resultCode) +"   "+errorMessage
                        zcinfoInstance.web_status='2'
                        zcinfoInstance.uploader_Id = uploaderId
                        zcinfoInstance.uploader_Time = nowTime
                        failcount = failcount+1
                        if (failedzcinfo==""){
                            failedzcinfo =zcinfoInstance.veh_Zchgzbh
                        }else{
                            failedzcinfo=  failedzcinfo+","+zcinfoInstance.veh_Zchgzbh
                        }
                    }

                    if (needNew){
                        def zcinfoInstance1=new ZCInfo(zcinfoInstance.properties)
                        zcinfoInstance1.save(flush:true)
                    }else{
                        zcinfoInstance.save(flush:true)
                    }
                }else if (zcinfoInstance.veh_Type=="1"){ //农用车
                    CertificateRequestService client = new CertificateRequestServiceLocator();
                    String serverPath=grailsApplication.config.serverupload.argpath;
                    CertificateRequestServiceSoap_PortType service=client.getCertificateRequestServiceSoap(new URL(serverPath))
                    OperateResult  oResult=new OperateResult()
                    if ("insert".equals(opFlag)){
                        oResult=service.uploadInsert_Ent(certificateInfo)
                    }else if ("overTime".equals(opFlag)){
                        CertificateInfo[] d=new CertificateInfo[1]
                        d[0]=certificateInfo
                        oResult=service.uploadOverTime_Ent(d,"${reason}")
                    }else if("update".equals(opFlag)){
                        CertificateInfo[] d=new CertificateInfo[1]
                        d[0]=certificateInfo
                        oResult=service.uploadUpdate_Ent(d,"${reason}")
                    }

                    def errorMessage=GetResultMessage(oResult)
                    if (oResult.resultCode==0){ //上传成功
                        if (!"1".equals(zcinfoInstance.web_status)){
                            zcinfoInstance.delete(flush: true)
                            needNew=true
                        }
                        zcinfoInstance.web_status='1'
                        zcinfoInstance.uploader_Id = uploaderId
                        zcinfoInstance.uploader_Time = nowTime
                        zcinfoInstance.upload_Failddm= String.valueOf(oResult.resultCode)
                        succount=succount+1
                    }else{
                        msg+=errorMessage
                        if ("1".equals(zcinfoInstance.web_status)){
                            zcinfoInstance.delete(flush: true)
                            needNew=true
                        }
                        zcinfoInstance.upload_Failddm= String.valueOf(oResult.resultCode)+"   "+errorMessage
                        zcinfoInstance.uploader_Id = uploaderId
                        zcinfoInstance.uploader_Time = nowTime
                        zcinfoInstance.web_status='2'
                        failcount = failcount+1
                        if (failedzcinfo==""){
                            failedzcinfo =zcinfoInstance.veh_Zchgzbh
                        }else{
                            failedzcinfo=  failedzcinfo+","+zcinfoInstance.veh_Zchgzbh
                        }
                    }
                    if (needNew){
                        def zcinfoInstance1=new ZCInfo(zcinfoInstance.properties)
                        zcinfoInstance1.save(flush:true)

                    }else{
                        zcinfoInstance.save(flush:true)
                    }

                }
            }
        }catch(Exception e){
            msg = "数据保存异常${e.cause.message}"
            flag =false
            e.cause.printStackTrace()

        }

        def map=[:]
        map.msg=msg
        map.succount=succount
        map.failcount=failcount
        map.flag=flag
        map.failedzcinfo=failedzcinfo
        return map
    }
    /**
     * @Description 上传验证公告信息与公告库中的信息是否匹配
     * @param carStorage
     * @Create 2013-11-12 huxx
     * @UpdateTime 2015-03-30 zhuwei
     * @Update 添加发动机型号、排量、功率、最大净功率的校验
     * @Update 轮胎规格验证单独放在后面
     */
    def String uploadCheckCarStorage(ZCInfo zcinfo){
        StringBuffer sb=new StringBuffer()
        //获取分解前的公告信息
        PreCarStorage preCarStorage=new PreCarStorage()
        def dpxh
        if ("QX".equals(zcinfo.veh_Clztxx)){
            if(zcinfo.veh_Dpxh==''){
                dpxh=null
            }else{
                dpxh=zcinfo.veh_Dpxh
            }
            preCarStorage=PreCarStorage.findByVeh_ClxhAndVeh_Dpxh(zcinfo.veh_Clxh,dpxh)
        }else if ("DP".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(zcinfo.veh_Dpid,'DP')
        }else{
            sb.append("合格证车辆状态信息错误")
            return sb.toString()
        }

        //合格证编号与车辆类型的匹配验证
        if(zcinfo.veh_Clxh.startsWith("FD")){  //汽车的车辆型号以FD开头，合格证以WCB开头
            def hgzbh=zcinfo.veh_Zchgzbh
            if(!zcinfo.veh_Zchgzbh.startsWith("WCB")){
                sb.append("合格证编号与车辆类型不符")
            }
        }else{  //农用车的车辆型号
            if(!zcinfo.veh_Zchgzbh.startsWith("NB")){
                sb.append("合格证编号与车辆类型不符")
            }
        }

        //匹配条件验证
        if (!preCarStorage){
            sb.append("车辆型号与公告库不符！")
        }else{
            if(!preCarStorage.veh_Clmc?.equals(zcinfo.veh_Clmc)){
                sb.append("车辆名称与公告库不符！")
            }

            if (!preCarStorage.veh_Fdjxh){
                if (zcinfo.veh_Fdjxh){
                    sb.append("发动机型号与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Fdjxh.split(";")
                if (!f.collect().contains(zcinfo.veh_Fdjxh)){
                    sb.append("发动机型号与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Pl){
                if (zcinfo.veh_Pl){
                    sb.append("排量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Pl.split(";")
                if (!f.collect().contains(zcinfo.veh_Pl)){
                    sb.append("排量与公告库不符！")
                }
            }


            if (!preCarStorage.veh_Gl){
                if (zcinfo.veh_Gl){
                    sb.append("功率与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Gl.split(";")
                if (!f.collect().contains(zcinfo.veh_Gl)){
                    sb.append("功率与公告库不符！")
                }
            }
            //电动车校验去掉排量
            if(zcinfo.veh_Rlzl=='电'){
                //添加整车发动机型号、排量、功率、的对应关系的校验
                if("QX".equals(zcinfo.veh_Clztxx)){
                    def Fdjxh_Count=preCarStorage.veh_Fdjxh.split(";")
                    def Gl_count=preCarStorage.veh_Gl.split(";")
                    if (Fdjxh_Count.size()==Gl_count.size()){
                        def i=ArrayUtils.indexOf( Fdjxh_Count, zcinfo.veh_Fdjxh)  //查找合格证中的发动机型号在分解前发动机型号中的下标
                        println(Fdjxh_Count)
                        println(Gl_count)
                        if((Gl_count[i]).equals(zcinfo.veh_Gl)){

                        }else{
                            sb.append("分解前公告发动机型号、功率未一一对应！")
                        }

                    }else{
                        sb.append("分解前公告发动机型号、功率参数个数不一致！")
                    }
                }
            }else{
                //添加整车发动机型号、排量、功率、的对应关系的校验
                if("QX".equals(zcinfo.veh_Clztxx)){
                    def Fdjxh_Count=preCarStorage.veh_Fdjxh.split(";")
                    def Pl_Count=preCarStorage.veh_Pl.split(";")
                    def Gl_count=preCarStorage.veh_Gl.split(";")
                    if ((Fdjxh_Count.size()==Pl_Count.size())&&(Fdjxh_Count.size()==Gl_count.size())){
                        def i=ArrayUtils.indexOf( Fdjxh_Count, zcinfo.veh_Fdjxh)  //查找合格证中的发动机型号在分解前发动机型号中的下标
                        println(Fdjxh_Count)
                        println(Pl_Count)
                        println(Gl_count)
                        if((Pl_Count[i]).equals(zcinfo.veh_Pl)&&(Gl_count[i]).equals(zcinfo.veh_Gl)){

                        }else{
                            sb.append("分解前公告发动机型号、排量、功率未一一对应！")
                        }

                    }else{
                        sb.append("分解前公告发动机型号、排量、功率参数个数不一致！")
                    }
                }
            }

            if (!preCarStorage.veh_Rlzl?.equals(zcinfo.veh_Rlzl)){
                sb.append("燃料种类和公告库不符！")
            }

            if (!preCarStorage.veh_Qlj){
                if (zcinfo.veh_Qlj){
                    sb.append("前轮距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Qlj.split(",")
                if (!f.collect().contains(zcinfo.veh_Qlj)){
                    sb.append("前轮距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hlj){
                if (zcinfo.veh_Hlj){
                    sb.append("后轮距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hlj.split(",")
                if (!f.collect().contains(zcinfo.veh_Hlj)){
                    sb.append("后轮距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Lts){
                if (zcinfo.veh_Lts){
                    sb.append("轮胎数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Lts.split(",")
                if (!f.collect().contains(zcinfo.veh_Lts)){
                    sb.append("轮胎数与公告库不符！")
                }
            }
//           上传不校验轮胎规格
//            if (!preCarStorage.veh_Ltgg){
//                if (zcinfo.veh_Ltgg){
//                    sb.append("轮胎规格与公告库不符！")
//                }
//            }else{
//                def f=preCarStorage.veh_Ltgg.split(",")
//                if (!f.collect().contains(zcinfo.veh_Ltgg)){
//                    sb.append("轮胎规格与公告库不符！")
//                }
//            }
            if (!preCarStorage.veh_Gbthps){
                if (zcinfo.veh_Gbthps){
                    sb.append("钢板弹簧片数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Gbthps.split(",")
                if (!f.collect().contains(zcinfo.veh_Gbthps)){
                    sb.append("钢板弹簧片数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zj){
                if (zcinfo.veh_Zj){
                    sb.append("轴距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zj.split(",")
                if (!f.collect().contains(zcinfo.veh_Zj)){
                    sb.append("轴距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zs){
                if (zcinfo.veh_Zs){
                    sb.append("轴数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zs)){
                    sb.append("轴数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkc){
                if (zcinfo.veh_Wkc){
                    sb.append("外廓长与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkc.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkc)){
                    sb.append("外廓长与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkk){
                if (zcinfo.veh_Wkk){
                    sb.append("外廓宽与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkk.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkk)){
                    sb.append("外廓宽与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkg){
                if (zcinfo.veh_Wkg){
                    sb.append("外廓高与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkg.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkg)){
                    sb.append("外廓高与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbc){
                if (zcinfo.veh_Hxnbc){
                    sb.append("货箱内部长与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbc.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbc)){
                    sb.append("货箱内部长与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbk){
                if (zcinfo.veh_Hxnbk){
                    sb.append("货箱内部宽与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbk.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbk)){
                    sb.append("货箱内部宽与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbg){
                if (zcinfo.veh_Hxnbg){
                    sb.append("货箱内部高与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbg.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbg)){
                    sb.append("货箱内部高与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zzl){
                if (zcinfo.veh_Zzl){
                    sb.append("总质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Zzl)){
                    sb.append("总质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Edzzl){
                if (zcinfo.veh_Edzzl){
                    sb.append("额定载质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Edzzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Edzzl)){
                    sb.append("额定载质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zbzl){
                if (zcinfo.veh_Zbzl){
                    sb.append("整备质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zbzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Zbzl)){
                    sb.append("整备质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zzllyxs){
                if (zcinfo.veh_Zzllyxs){
                    sb.append("载质量利用系数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zzllyxs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zzllyxs)){
                    sb.append("载质量利用系数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Jsszcrs){
                if (zcinfo.veh_Jsszcrs){
                    sb.append("驾驶室准乘人数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Jsszcrs.split(",")
                if (!f.collect().contains(zcinfo.veh_Jsszcrs)){
                    sb.append("驾驶室准乘人数与公告库不符！")
                }
            }
            //上传不校验公告批次
//            if (!preCarStorage.veh_Ggpc){
//                if (zcinfo.veh_Ggpc){
//                    sb.append("公告批次与公告库不符！")
//                }
//            }else{
//                def f=preCarStorage.veh_Ggpc.split(",")
//                if (!f.collect().contains(zcinfo.veh_Ggpc)){
//                    sb.append("公告批次与公告库不符！")
//                }
//            }
            if (!preCarStorage.veh_Zgcs){
                if (zcinfo.veh_Zgcs){
                    sb.append("最高车速与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zgcs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zgcs)){
                    sb.append("最高车速与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Qybz){
                if (zcinfo.veh_Qybz){
                    sb.append("企业标准与公告库不符！")
                }
            }else{
                if(zcinfo.veh_Qybz){
                    if(!preCarStorage.veh_Qybz.trim()?.equals(zcinfo.veh_Qybz.trim())){
                        sb.append("企业标准与公告库不符！")
                    }
                }
            }
            if(zcinfo.veh_Rlzl=='电'){
                if(!preCarStorage.veh_Pfbz.equals(zcinfo.veh_Pfbz)){
                    sb.append("排放标准与公告库不符！")
                }

            }else{
                if(!preCarStorage.veh_Pfbz?.equals(zcinfo.veh_Pfbz)){
                    sb.append("排放标准与公告库不符！")
                }

            }

            if (zcinfo.veh_Clsbdh){
                if (!preCarStorage.veh_clsbdh){
                    if (zcinfo.veh_Clsbdh){
                        sb.append("车辆识别代号与公告库不符！")
                    }
                }else{
                    def f=preCarStorage.veh_clsbdh.split(",")
                    def clsbdh=zcinfo.veh_Clsbdh.substring(0,8)
                    def pipei=false
                    f.each{
                        def dh=it.substring(0,8)
                        if (clsbdh.equals(dh)){
                            pipei=true
                        }
                    }
                    if(!pipei){
                        sb.append("车辆识别代号与公告库不匹配！")
                    }
                }
            }else{
                sb.append("合格证的车辆识别代号不能为空！")
            }


        }

        return sb.toString()
    }
    /**
     * @description 合格证信息上传国家轮胎规格检验失败的合格证修改状态为上传失败
     * @return
     * @Update 2017-08-24
     */
    def checkFailedLtgg(ids,loginUser,uploadType){
        //设置上传人id，自动上传时使用system的id
        def uploaderId=""
        def checkMessage=""
        def result =[:]
        if (uploadType=='0'){
            def user=User.findByUserName('system')
            uploaderId=user?.id
        }else{
            uploaderId=loginUser?.id
        }

        def nowTime= DateUtil.getCurrentTime()

        String msg=""
        String failedzcinfo="" //上传失败的合格证编号

        def idList=ids?.split("_")
        try{
            idList.each {id->
                def zcinfoInstance=null
                if (uploadType=='0' || uploadType=='2'||uploadType=='5'){//自动上传
                    zcinfoInstance=ZCInfo.get(id)
                }else if (uploadType=='1'){ //经销商手动上传，传入的时下载合格证编号的id
                    ///获得经销商下载的合格证信息
                    def ZCInfoTempInstance = ZCInfoTemp.get(id)
                    ////根据经销商下载的合格证信息 查询当前服务器正式 合格证信息( 根据虚拟号编号 )
                    zcinfoInstance  = ZCInfo.findByWeb_virtualcode(ZCInfoTempInstance.web_virtualcode)
                }else if (uploadType=='3'){//需要撤销或修改页面的上传传入的是合格证的合格证编号
                    zcinfoInstance=ZCInfo.findByVeh_Zchgzbh(id)
                }

                if (!zcinfoInstance){
                    msg="合格证信息不存在！"
                    return
                }

                def needNew=false
                checkMessage="轮胎规格与车辆类型不符"

                if ("1".equals(zcinfoInstance.web_status)){
                    zcinfoInstance.delete(flush: true)
                    needNew=true
                }

                zcinfoInstance.upload_Failddm= checkMessage
                zcinfoInstance.web_status='2'
                zcinfoInstance.uploader_Id = uploaderId
                zcinfoInstance.uploader_Time = nowTime

                if (needNew){
                    def zcinfo1=new ZCInfo(zcinfoInstance.properties)
                    zcinfo1.save(flush:true)
                }else{
                    zcinfoInstance.save(flush:true)
                }
                return
            }
        }catch(Exception e){
            msg = "数据保存异常${e.cause.message}"
            e.cause.printStackTrace()

        }
    }

    /**
     * @description 合格证信息上传国家轮胎规格检验
     * @param ids
     * @param uploadType  上传类型，0自动上传；1经销商手动上传 ,2上传失败页面上传，3需要修改或撤销页面的上传
     * @return
     * @Update 2013-10-28 huxx 添加操作标识，opFlag的值可为”insert","overTime","update"分别表示上传、补传 、修改
     */
    def checkLtgg(ids,loginUser,uploadType){
        //判断操作参数是否正确
        //设置上传人id，自动上传时使用system的id
        def uploaderId=""
        def failIds=""
        def map=[:]
        def list=[]
        if (uploadType=='0'){
            def user=User.findByUserName('system')
            uploaderId=user?.id
        }else{
            uploaderId=loginUser?.id
        }

        def nowTime= DateUtil.getCurrentTime()

        String msg=""
        int succount=0
        int failcount=0
        boolean flag=true

        String failedzcinfo="" //上传失败的合格证编号

        def idArr=ids?.split("_")
        idArr.each {id->

            def zcinfoInstance=null
            if (uploadType=='0' || uploadType=='2'||uploadType=='5'){//自动上传
                zcinfoInstance=ZCInfo.get(id)
            }else if (uploadType=='1'){ //经销商手动上传，传入的时下载合格证编号的id
                ///获得经销商下载的合格证信息
                def ZCInfoTempInstance = ZCInfoTemp.get(id)
                ////根据经销商下载的合格证信息 查询当前服务器正式 合格证信息( 根据虚拟号编号 )
                zcinfoInstance  = ZCInfo.findByWeb_virtualcode(ZCInfoTempInstance.web_virtualcode)
            }else if (uploadType=='3'){//需要撤销或修改页面的上传传入的是合格证的合格证编号
                zcinfoInstance=ZCInfo.findByVeh_Zchgzbh(id)
            }

            if (!zcinfoInstance){
                msg="合格证信息不存在！"
                failcount+=1
                return
            }

            String fileId =zcInfoService.checkLtgg2(zcinfoInstance)

            if (fileId!=""){
                failcount+=1
                list.add(fileId)
            }
        }
        list?.each{
            failIds+= it+'_';
        }
        def result = [failIds: failIds, failcount: failcount]
        return result
    }
    /**
     * @Description 从国家系统中撤销合格证信息
     * @param ids
     * @param grailsApplication
     * @param reason
     * @return
     * @Create 2013-11-02 huxx
     */
    def delete(ids,grailsApplication,String reason){
        String msg=""
        def err=""

        def idArr=ids?.split("_")
        def String[] cars=new String[idArr?.size()]
        def String[] args=new String[idArr?.size()]
        try{
            int c=0
            int a=0

            idArr.each {id->
                def hgz=id.split(";")
                if (hgz && hgz.size()==2){
                    if (hgz[1]=='0'){
                        cars[c]=hgz[0]
                        c+=1
                    }else if (hgz[1]=='1'){
                        args[a]=hgz[0]
                        a+=1
                    }else{
                        if (err){
                            err+="_记录"+id+"信息有错误！"
                        }else{
                            err="记录"+id+"信息有错误！"
                        }
                    }
                }else{
                    if (err){
                        err+="_记录"+id+"信息有错误！"
                    }else{
                        err="记录"+id+"信息有错误！"
                    }

                }
            }

            //赋值完成后添加到队列
            if (cars && cars.size()>0 && cars[0]){ //汽车
                CertificateRequestService client = new CertificateRequestServiceLocator();
                String serverPath=grailsApplication.config.serverupload.carpath;
                CertificateRequestServiceSoap_PortType service=client.getCertificateRequestServiceSoap(new URL(serverPath))
                OperateResult  oResult=new OperateResult()
                oResult=service.uploadDelete_Ent(cars,reason)

                def errorMessage=GetResultMessage(oResult)
                if (oResult.resultCode==0){ //上传成功
                    msg="撤销成功！"
                }else{
                    msg=errorMessage
                }
            }
            if (args&& args.size()>0 && args[0]){ //农用车
                CertificateRequestService client = new CertificateRequestServiceLocator();
                String serverPath=grailsApplication.config.serverupload.argpath;
                CertificateRequestServiceSoap_PortType service=client.getCertificateRequestServiceSoap(new URL(serverPath))
                OperateResult  oResult=new OperateResult()
                oResult=service.uploadDelete_Ent(args,reason)

                def errorMessage=GetResultMessage(oResult)
                if (oResult.resultCode==0){ //上传成功
                    msg="撤销成功！"
                }else{
                    msg=errorMessage
                }
            }
        }catch(Exception e){
            msg = "数据处理异常"
            e.printStackTrace()

        }finally{
            if (err){
                msg+=":"+err
            }
            def map=[:]
            map.msg=msg
            return map
        }


    }

    /**
     * @Description 转换日期yyyy年mm月dd日
     * @author  Xu
     * @update 2013-11-04 huxx 修改方法可以转换类似于yyyy年m月d日的字符串为时间格式
     */
    def convertRq1 (String rq){
        if (rq){
            int index1=rq.indexOf("年")
            int index2=rq.indexOf("月")
            int index3=rq.indexOf("日")
            int year1=Integer.valueOf(rq.substring(0,index1))
            int month1 =Integer.valueOf(rq.substring(index1+1,index2))
            int day1 =Integer.valueOf(rq.substring(index2+1,index3))
            Calendar result=Calendar.getInstance()
            result.set(year1,month1-1,day1)
            return     result
        }else{
            return null
        }
    }

    /**
     * @Description 将yyyy-mm-dd格式的字符串转换成时间类型
     * @param rq
     * @return
     * @Create 2013-11-04 huxx
     */
    def convertRq (String rq){
        if (rq){
            rq = rq.substring(0,rq.length()>10?10:rq.length())
            int index1=rq.indexOf("-")
            int index2=rq.lastIndexOf("-")
            int index3=rq.indexOf(" ")
            int length=rq.length()
            def dd=rq.substring(index2+1,length)
            int year1=Integer.valueOf(rq.substring(0,index1))
            int month1 =Integer.valueOf(rq.substring(index1+1,index2))
            int day1 =Integer.valueOf(rq.substring(index2+1,length))
            Calendar result=Calendar.getInstance()
            result.set(year1,month1-1,day1)
            return     result
        }else{
            return null
        }
    }
    /**
     *
     * 解析操作结果
     *
     * @param oResult
     *         操作结果  {@link OperateResult }
     *
     * @return 解析结果字符串 {@link String }
     */
    static String GetResultMessage(OperateResult oResult)
    {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format("操作结果:%s\r\n", oResult.getResultCode()));

        for (NameValuePair nvp : oResult.getResultDetail())
        {
            sb.append(String.format("%s:%s\r\n", nvp.getName(), nvp.getValue()));
        }
        return sb.toString();
    }

    def getServletContext(){
        def webUtils = WebUtils.retrieveGrailsWebRequest()
        webUtils.getServletContext()
    }
    /**
     *@Descriptions 查询和导出的公用方法
     *@Auther liuly
     *@createTime
     * @Update 2013-10-21 huxx 添加获取未上传合格证的方法 以及添加了是否打印正式合格证的查询条件
     */
    def search(params){
        def cel = {
            if(params.veh_Zchgzbh){
                eq("veh_Zchgzbh","${params.veh_Zchgzbh}")
            }

            if(params.veh_Clsbdh){
                like('veh_Clsbdh',"%${params.veh_Clsbdh}%")
            }
            if(params.veh_Fzrq){
                eq('veh_Fzrq',params.veh_Fzrq)
            }
            if(params.uploader_Time){
                like('uploader_Time',"%${params.uploader_Time}%")
            }

            if ("0".equals(params.isUpload)){
                eq("web_status","0")//未上传
            }else if ("1".equals(params.isUpload)){
                eq("web_status","1")//上传成功
            }
            if ("hand".equals(params.searchType)){
                le("createTime",params.curTime)
            }

            if (params.isPrint){
                eq("web_isprint","${params.isPrint}")
            }

            // eq("web_status","3")
            //  }
        }
        def results = ZCInfo.createCriteria().list(params,cel)
        def lst=[]
        results.each {r->
            def m=[:]
            m.id=r.id
            m.veh_Zchgzbh=r.veh_Zchgzbh
            m.veh_Dphgzbh=r.veh_Dphgzbh
            m.veh_Fzrq=r.veh_Fzrq
            m.veh_Clzzqymc=r.veh_Clzzqymc
            m.veh_Qyid=r.veh_Qyid
            m.veh_Clfl=r.veh_Clfl
            m.veh_Clmc=r.veh_Clmc
            m.veh_Clpp=r.veh_Clpp
            m.veh_Clxh=r.veh_Clxh
            m.veh_Csys=r.veh_Csys
            m.veh_Dpxh=r.veh_Dpxh
            m.veh_Dpid=r.veh_Dpid
            m.veh_Clsbdh=r.veh_Clsbdh
            m.veh_Fdjh=r.veh_Fdjh
            m.veh_Fdjxh=r.veh_Fdjxh
            m.veh_Rlzl=r.veh_Rlzl
            m.veh_Pl=r.veh_Pl
            m.veh_Gl=r.veh_Gl
            m.veh_Zxxs=r.veh_Zxxs
            m.veh_Qlj=r.veh_Qlj
            m.veh_Hlj=r.veh_Hlj
            m.veh_Lts=r.veh_Lts
            m.veh_Ltgg=r.veh_Ltgg
            m.veh_Gbthps=r.veh_Gbthps
            m.veh_Zj=r.veh_Zj
            m.veh_Zh=r.veh_Zh
            m.veh_Zs=r.veh_Zs
            m.veh_Wkc=r.veh_Wkc
            m.veh_Wkk=r.veh_Wkk
            m.veh_Wkg=r.veh_Wkg
            m.veh_Hxnbc=r.veh_Hxnbc
            m.veh_Hxnbk=r.veh_Hxnbk
            m.veh_Hxnbg=r.veh_Hxnbg
            m.veh_Zzl=r.veh_Zzl
            m.veh_Edzzl=r.veh_Edzzl
            m.veh_Zbzl=r.veh_Zbzl
            m.veh_Zzllyxs=r.veh_Zzllyxs
            m.veh_Zqyzzl=r.veh_Zqyzzl
            m.veh_Edzk=r.veh_Edzk
            m.veh_Bgcazzdyxzzl=r.veh_Bgcazzdyxzzl
            m.veh_Jsszcrs=r.veh_Jsszcrs
            m.veh_Qzdfs=r.veh_Qzdfs
            m.veh_Hzdfs=r.veh_Hzdfs
            m.veh_Qzdczfs=r.veh_Qzdczfs
            m.veh_Cpggh=r.veh_Cpggh
            m.veh_Ggsxrq=r.veh_Ggsxrq
            m.veh_Zzbh=r.veh_Zzbh
            m.veh_Dywym=r.veh_Dywym
            m.veh_Zgcs=r.veh_Zgcs
            m.veh_Clzzrq=r.veh_Clzzrq
            m.veh_Bz=r.veh_Bz
            m.veh_Qybz=r.veh_Qybz
            m.veh_Cpscdz=r.veh_Cpscdz
            m.veh_Qyqtxx=r.veh_Qyqtxx
            m.veh_Pfbz=r.veh_Pfbz
            m.veh_Clztxx=r.veh_Clztxx
            m.veh_Jss=r.veh_Jss
            m.veh_Jsslx=r.veh_Jsslx
            m.veh_VinFourBit=r.veh_VinFourBit
            m.updateTime=r. updateTime

            def userC=User.get(r.updaterId)
            if (userC){
                m.updaterName=userC.userDetail.realName
            }else{
                m.updaterName=''
            }
            def userU=User.get(r.uploader_Id)
            if (userU){
                m.uploaderName=userU.userDetail.realName
            }else{
                m.uploaderName=''
            }

            m.uploader_Time=r.uploader_Time
            lst.add(m)
        }
        def result = [rows: lst, total: results.totalCount]
        return result
    }

    /**
     * @Description 已打印正式合格证信息上传 因uploadInfoByTempAuto方法会判断是汽车还是农用车，所以这里没有必要再分开汽车和农用车
     * @return
     * @create 2013-09-01 huxx
     * @update 2013-09-02 huxx 添加条件超过24小时的才自动上传
     */
    def autoUploadZcinfo(grailsApplication)
    {
        def map=[:]
        def SQL_NYC = grailsApplication.config.upload.sql
        def rows =  sqlService.executeList(SQL_NYC);

        if (rows){
            println ">>>>>>>>>>>>>>>>>>${DateUtil.getCurrentTime()}执行了定时任务,开始上传"
            String ids ="";
            rows?.each {c->
                ids+=c.id
                ids+="_"
            }
            if (ids){
                ids=ids.substring(0,ids.lastIndexOf("_"))
            }
            map=upload(ids,null,"0",grailsApplication,"insert","")
            map.msg= "成功提交${map.succount}条,失败提交${map.failcount},返回信息为：${map.msg}"
            println ">>>>>>>>>>>>>>>>>成功提交${map.succount}条,失败提交${map.failcount},返回信息为：${map.msg}"
        }else{
            map.msg="执行了定时任务，但是没有符合上传条件的数据"
            println ">>>>>>>>>>>>>>>>>>执行了定时任务，但是没有符合条件的数据"
        }
        return map

    }

    /**
     * @Description 根据条件查询
     */
    def selectNeedHgzs(){
        def sql=""

        def rows =  sqlService.executeList(sql);
    }

//    def search(){
//        //按合格证编号查询
//        System.out.println(GetQueryResultMessage(service.queryCertificateByWZHGZBH("合格证编号")));
//
//        //按日期查询
//        System.out.println(GetQueryResultMessage(service.queryCertificateByDate(1,"2009-1-1","2009-1-2",10,1)));
//
//    }

}
