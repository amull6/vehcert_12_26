package cn.com.wz.vehcert.zcinfo

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

/**
 * @Description 经销商自主打印Service
 * @Create 2018-09-17 mike
 */
class DealerPrintService {
   def forDealerPrint(filepath){
       def result=[:]
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
               return  result as JSON
           }
       }catch(Exception e){
           e.printStackTrace()
           e.cause?.printStackTrace()
           result.put("msg",'1');
           result.put("flag",'failed')
           return  result as JSON
       }

   }

}
