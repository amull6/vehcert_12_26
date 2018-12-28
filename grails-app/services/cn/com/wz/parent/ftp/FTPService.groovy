package cn.com.wz.parent.ftp

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply

/**
 * @Description:  FTP相关操作Service
 * @Create: 13-8-2 上午10:33   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class FTPService {
    def fileEncodeing=System.getProperty("file.encoding")
    /**
     * @Description 打开ftp连接
     * @param ip
     * @param port
     * @param userName
     * @param password
     * @return result.ftpClient 返回连接客户端 result.errorCode 返回错误代码
     * @Create 2013-08-02 huxx
     */
    def openServer(ip,port,userName,password){
        def result=[:]
        FTPClient ftpClient=new FTPClient()
        //打开ftp连接
        if (port){
            ftpClient.connect(ip,port)
        }else{
            ftpClient.connect(ip)
        }
        ftpClient.login(userName,password)

        ftpClient.setControlEncoding(fileEncodeing)

        // 检验是否连接成功
        int reply = ftpClient.getReplyCode();
         if (!FTPReply.isPositiveCompletion(reply)) {
             result.ftpClient=null
             result.errorCode=reply
             ftpClient.disconnect();
             println ">>>>>>>>>>>>>>>>>>>>>>>>>ftp连接${ip}:${port}失败！"
         }else{
             result.ftpClient=ftpClient
             println ">>>>>>>>>>>>>>>>>>>>>>>>>ftp连接${ip}:${port}成功！"
         }

        return result;
    }
    /**
     * @Description 单个文件的上传
     * @param ftpClient ftp客户端
     * @param input    要上传的文件流
     * @param remoteFileName 远程服务器上的文件名
     * @param path 远程服务器上的相对路径
     * @return  true 上传成功 false上传失败
     * @Create 2013-08-02 huxx
     */
    boolean upload(FTPClient ftpClient,InputStream input,String remoteFileName,String path){
        def result=true
        try{
            boolean change=ftpClient.changeWorkingDirectory(path)
            if (change){
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
                def flag=ftpClient.storeFile(newString(remoteFileName.getBytes(fileEncodeing),"iso-8859-1"), input)
                if (flag){
                    println ">>>>>>>>>>>>>>>>>>>>>>>>>${remoteFileName}文件上传成功！"
                }else{
                    result = false
                    println ">>>>>>>>>>>>>>>>>>>>>>>>>${remoteFileName}文件上传失败！"
                }
            }
        }catch(IOException ioe){
            result=false
            ioe.printStackTrace()
        }finally{
             ftpClient.logout()
             ftpClient.disconnect()
             input.close()
        }
        return result
    }
}
