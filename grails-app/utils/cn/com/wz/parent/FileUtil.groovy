package cn.com.wz.parent

import java.io.StringWriter;

import grails.converters.XML;
import groovy.util.XmlSlurper;
import groovy.xml.MarkupBuilder;

/**
 *@Description 对文件读写操作
 *@Auther huxx
 *@createTime 2013-1-4 下午2:44:20
 */
class FileUtil {

    /**
     *@Description 从xml文件中读取文件内容
     *@param
     *@return
     *@Auther huxx
     *@createTime 2013-1-4 下午2:48:31
     */
    static  readXML(String filePath){
        //也可以从文件解析
        def xml = new XmlSlurper().parse(new File(filePath))
        return xml
    }

    /**
     *@Description 用MarkupBuilder构建xml
     *@param
     *@return
     *@Auther huxx
     *@createTime 2013-1-4 下午2:53:00
     */
    static writeXMLDemo(){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.values() {
            3.times {
                def num = it
                xml.value(id:num,name:"my name is ${num}") {
                    child("this is child [${num}]")
                }
            }
        }
        //打印出看是否为xml
//        println writer//.toString()
        //也可以写到文件
        //new File("c:/my-value.xml").write(writer.toString())
    }

    // 复制文件
    static copyFile(File sourceFile, File targetFile) throws IOException {
        def result=true
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } catch (Exception e){
            result = false
        }finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
        return result
    }

}
