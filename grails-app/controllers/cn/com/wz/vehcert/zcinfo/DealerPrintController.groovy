package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import grails.converters.JSON

import javax.print.Doc
import javax.print.DocFlavor
import javax.print.DocPrintJob
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.SimpleDoc
import javax.print.attribute.DocAttributeSet
import javax.print.attribute.HashDocAttributeSet
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.PrintRequestAttributeSet
import javax.servlet.ServletContextEvent

/**
 *  打印次数管理控制器
 * @param
 * @return
 * @create  2016-12-20 Qj
 */
class DealerPrintController extends BaseController{
    def dealerPrintService
    /**
     *@Description 跳转到经销商打印页面
     *@Auther QJ
     *@createTime 2018-09-26
     */
    def forDealerPrint(){
        def path = '../pdf'+params.path
        def id = params.hidden_id;    ////要更新的合格证ID信息
        def zcinfoModel = ZCInfo.get(id)
        DistributorPrintCount distributorPrintCount = DistributorPrintCount.findByVeh_Zchgzbh_0(zcinfoModel.veh_Zchgzbh_0)
        if(distributorPrintCount){
            distributorPrintCount.printCount=distributorPrintCount.printCount+1
            distributorPrintCount.save(flush: true)
        }
        render (view: "/cn/com/wz/vehcert/zcinfo/dealer/dealerPrint",model: [path:path])
    }
    /**
     *@Description 文件拷贝
     *@Auther QJ
     *@createTime 20178-09-20
     */
    def fileCopy(){
        def result =[:]
        def path = params.path
        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)
        //获取文件绝对路径
        def oldFilepath = rootDir+path
        String rootPath = request.getSession().getServletContext().getRealPath("")
        def newFilepath = rootPath+'/pdf'+path
        int index = newFilepath.lastIndexOf("/");
        def filePath = newFilepath.substring(0,index)
        File file = new File(filePath)
        if(!file .exists()){
            (new File(filePath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
        }
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldFilepath);
            File newFile = new File(newFilepath);
            if(newFile.exists()){
                result.msg="文件已存在"
                result.flag="1"
                return render (result as JSON)
            }else {
                if (oldfile.exists()) { //文件存在时
                    InputStream inStream = new FileInputStream(oldFilepath); //读入原文件
                    FileOutputStream fs = new FileOutputStream(newFilepath);
                    byte[] buffer = new byte[1444];
                    while ( (byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread; //字节数 文件大小
                        System.out.println(bytesum);
                        fs.write(buffer,0, byteread);
                    }
                    inStream.close();
                    fs.close();
                    result.msg="复制单个文件操作成功"
                    result.flag="1"
                    return render (result as JSON)
                }
            }
        }catch (Exception e) {
            result.msg="打印过程（拷贝文件）出现问题，请联系系统管理员"
            result.flag="0"
            return render (result as JSON)
            e.printStackTrace();
        }
    }

    /**
     * @DEscription 经销商申请增加一次自主打印次数
     * @Auther QJ
     */
    def applyCount() {
        def result=[:]
        def id = params.hidden_id;    ////要更新的合格证ID信息
        def zcinfoModel = ZCInfo.get(id)
        def nowTime= DateUtil.getCurrentTime()
        DistributorPrintCount distributorPrintCount = DistributorPrintCount.findByVeh_Zchgzbh_0(zcinfoModel.veh_Zchgzbh_0)
        if(distributorPrintCount){
            if(distributorPrintCount.status == '0'||distributorPrintCount.status == '1'){
                result.put("msg", '已存在该打印申请');
                result.put("flag", '4')
                return  render (result as JSON)
            }else{
                if(distributorPrintCount.limitPrintCount==0&&distributorPrintCount.printCount==0){
                    distributorPrintCount.status = '0'
                    distributorPrintCount.application_Time = nowTime
                }else{
                    distributorPrintCount.status = '1'
                    distributorPrintCount.application_Time = nowTime
                }
            }
            if (distributorPrintCount.save(flush: true)) {
                result.put("msg", '申请成功');
                result.put("flag", '2')
                return  render (result as JSON)
            }else{
                result.put("msg", '申请失败');
                result.put("flag", '3')
                return render(result as JSON)
            }
        }else{
            DistributorPrintCount newDistributorPrintCount = new DistributorPrintCount();
            newDistributorPrintCount.veh_Zchgzbh_0 = zcinfoModel.veh_Zchgzbh_0
            newDistributorPrintCount.veh_Clsbdh = zcinfoModel.veh_Clsbdh
            newDistributorPrintCount.veh_Type = zcinfoModel.veh_Type
            newDistributorPrintCount.printCount = 0
            newDistributorPrintCount.limitPrintCount = 0
            newDistributorPrintCount.SAP_No = zcinfoModel.SAP_No
            newDistributorPrintCount.status = '0'
            ///当前登录用户信息
            User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
            newDistributorPrintCount.applicant = loginUser.userDetail.realName   ///申请人姓名
            newDistributorPrintCount.application_Time = nowTime
            if (newDistributorPrintCount.save(flush: true)) {
                result.put("msg", '申请成功');
                result.put("flag", '1')
                return  render (result as JSON)
            }else{
                result.put("msg", '申请失败');
                result.put("flag", '0')
                return render(result as JSON)
            }
        }

    }
}
