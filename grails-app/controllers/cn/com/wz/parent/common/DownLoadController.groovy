package cn.com.wz.parent.common

import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.vehcert.zcinfo.ZCInfoReplace
import grails.converters.JSON
import org.apache.tools.zip.ZipEntry
import org.apache.tools.zip.ZipOutputStream

import javax.management.Attribute
import javax.print.Doc
import javax.print.DocFlavor
import javax.print.DocPrintJob
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.ServiceUI
import javax.print.SimpleDoc
import javax.print.attribute.AttributeSet
import javax.print.attribute.DocAttributeSet
import javax.print.attribute.HashDocAttributeSet
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.PrintRequestAttributeSet
import javax.print.attribute.Size2DSyntax
import javax.print.attribute.standard.Copies
import javax.print.attribute.standard.MediaPrintableArea
import javax.print.attribute.standard.NumberUp
import javax.print.attribute.standard.OrientationRequested

/**
 *@Description 下载公共控制器
 *@Auther liucj
 *@createTime 2013-04-10  上午8:26:50
 */
class DownLoadController extends BaseController {

    /**
	 * @Description 文件下载
	 * @return
	 * @param nameC附件名称不含后缀，type附件后缀，fileName附件名称含后缀，filePath附件绝对路径
	 * @Auther huxx
	 * @createTime 2012-11-09
	 */
	def downFile(){
//        def agentType=request.getHeader("User-Agent").toUpperCase()
		def nameNoType = params.nameC
		def type = params.type
		def name = params.fileName
		if(nameNoType&&type){
			name=nameNoType+'.'+type
		}
		def path = params.filePath
        def fileName=URLEncoder.encode(name,"UTF-8")

        response.reset();
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-disposition", "attachment;filename=${fileName}")
		response.contentType = "application/octet-stream"

        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)
        //获取文件绝对路径
		def filepath = rootDir+path
		def out = response.outputStream
		def inputStream = new FileInputStream(filepath)
		byte[] buffer = new byte[1024]
		int i = -1
		while ((i = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, i)
		}

        out.flush()
		out.close()
		inputStream.close()
		
	}

    /**
     * @Description 打包下载文件
     *  页面调用方式为   window.location.href="${createLink(controller:'downLoad',action:'downZip')}?fileName="+
     *                  encodeURI("整车合格证和底盘合格证.zip")+"&filePaths="+filePath+"&filePaths="+dpFilePath;
     * @params filePaths下载文件路径数组
     * fileName 下载后的文件名称
     * @create 2013-08-27 huxx
     */
    def downZip(){
        def filePaths=params.filePaths
        def fileName=URLEncoder.encode("${params.fileName}","UTF-8")

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-disposition", "attachment;filename=${fileName}")
        response.contentType = "application/octet-stream"


        //生成随机的唯一文件名
        String tempZipName = getUniqueFileName();
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(tempZipName));
        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)
        for(int i=0;i<filePaths.length;i++) {
            //获取文件绝对路径
            def realPath=rootDir+filePaths[i]
            def tempFile=new File(realPath)
            //如果文件存在就放入zip流中
            if (tempFile.exists()){
                FileInputStream fis = new FileInputStream(tempFile);
                zipOut.putNextEntry(new ZipEntry(tempFile.name));
                copyStream(fis,zipOut)
                zipOut.closeEntry();
                fis.close();
            }
        }
        zipOut.close();

        //将生成后的zip文件下载并删除
        File f=new File(tempZipName)
        InputStream input=new FileInputStream(f)
        def out=response.outputStream
        copyStream(input,out)
        input.close()
        f.delete()
        out.flush()
        out.close()

    }
    /**
     * @Description 流内容拷贝
     * @param input
     * @param output
     */
    def copyStream(InputStream input, OutputStream output){
        byte[] chunk = new byte[1024];
        int count;
        while ((count = input.read(chunk)) >=0 ) {
            output.write(chunk,0,count);
        }
    }
/**
 * @Description 直接打印服务器PDF文件
 * @return
 * @Auther QJ
 * @createTime 2018-09-07
 */
    def print(){
        def result=[:]
        def path = params.filePath
        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)
        //获取文件绝对路径
        def filepath = rootDir+path
        File pdfFile = new File(filepath)
        try{
            //构建打印请求属性集
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
//            MediaPrintableArea mp = new MediaPrintableArea(20f, 0f, 100f, 40f,Size2DSyntax.MM);// 5f, 5f, 100f, 40f
//            pras.add(mp);
            //设置打印格式，因为未确定文件类型，这里选择AUTOSENSE
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            //查找所有的可用打印服务
            PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, pras);
            //定位默认的打印服务
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            //显示打印对话框
//            PrintService service = ServiceUI.printDialog(null, 200, 200, printService,   defaultService, flavor, pras);
            if (defaultService!= null) {
                DocPrintJob job = defaultService.createPrintJob();  //创建打印任务
                FileInputStream fis = new FileInputStream(pdfFile); //构造待打印的文件流
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das); //建立打印文件格式
//                pras.add(OrientationRequested.LANDSCAPE);
                job.print(doc, pras); //进行文件的打印
                result.put("msg",'0');
                result.put("flag",'success')
                return  render (result as JSON)
            }
        }catch(Exception e){
            e.printStackTrace()
            e.cause?.printStackTrace()
            result.put("msg",'1');
            result.put("flag",'failed')
            return  render (result as JSON)
        }
    }
    /**
     * @Description 生成唯一的文件名称
     * @return
     */
    private static String  getUniqueFileName(){
        Random rnd = new Random();
        long curTime=System.currentTimeMillis();
        String fileName="down_"+curTime+"_"+rnd.nextInt()+".zip";
        return fileName;
    }

}
