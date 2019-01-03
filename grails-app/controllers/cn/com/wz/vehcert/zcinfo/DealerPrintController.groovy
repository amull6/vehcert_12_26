package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
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
}
