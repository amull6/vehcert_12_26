package cn.com.wz.parent
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply

/**
 * @Description:  FTP相关操作的公共类
 * @Create: 13-8-2 上午10:33   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
class FTPUtil {
    //ftp服务端的字符集编码
    String fileEncoding="GBK"
    def ftpClient=new FTPClient()

    def FTPUtil(String serverFileEncoding){
        fileEncoding=serverFileEncoding

    }


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
        //打开ftp连接
        if (port){
            ftpClient.connect(ip,port)
        }else{
            ftpClient.connect(ip)
        }
        ftpClient.login(userName,password)

        ftpClient.setControlEncoding(fileEncoding)

        // 检验是否连接成功
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            println ">>>>>>>>>>>>>>>>>>>>>>>>>ftp连接${ip}:${port}失败！"
            return false
        }else{
            println ">>>>>>>>>>>>>>>>>>>>>>>>>ftp连接${ip}:${port}成功！"
            return true
        }

    }
    /**
     * @Description 单个文件的上传
     * @param ftpClient ftp客户端
     * @param file    要上传的文件
     * @param remoteFileName 远程服务器上的文件名
     * @param path 远程服务器上的相对路径
     * @param deleteFile=true 删除文件成功后删除本地文件，deleteFile=false删除成功后不删除本地文件
     * @return  true 上传成功 false上传失败
     * @Create 2013-08-02 huxx
     */
    boolean upload(File file,String remoteFileName,String path,boolean deleteFile){
        FileInputStream input=new FileInputStream(file)
        def result=upload(input,remoteFileName,path)
        if (result){
            if (deleteFile){
                def resultFlag=file.delete()
                if (resultFlag){
                    println ">>>>>>>>>>>>>>>>>>>>>本地文件pom.xml删除成功"
                }else{
                    println ">>>>>>>>>>>>>>>>>>>>>本地文件pom.xml删除失败"
                }
            }
        }
        return result
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
    boolean upload(InputStream input,String remoteFileName,String path){
        def result=true
        try{
            //处理含有中文的路径
            path=new String(path.getBytes(fileEncoding),"iso-8859-1")
            //当只有最后一级目录不存在时可以创建，如果有多级目录不存在，将不会创建成功
            ftpClient.makeDirectory(path)
            boolean change=ftpClient.changeWorkingDirectory(path)
            if (change){
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
                def flag=ftpClient.storeFile(new String(remoteFileName.getBytes(fileEncoding),"iso-8859-1"), input)
                if (flag){
                    println ">>>>>>>>>>>>>>>>>>>>>>>>>${remoteFileName}文件上传成功！"
                }else{
                    result = false
                    println ">>>>>>>>>>>>>>>>>>>>>>>>>${remoteFileName}文件上传失败！"
                }
            }else{
                result = false
                println ">>>>>>>>>>>>>>>>>>>>>>>>>路径有问题${remoteFileName}文件上传失败！"
            }
        }catch(IOException ioe){
            result=false
            ioe.printStackTrace()
        }finally{
            input.close()
        }
        return result
    }

    /**
     * @Description 上传文件夹下的所有文件，
     * @param file 如果file时文件夹就上传文件夹下的所有文件，如果是文件就上传单个文件
     *  path 上传到ftp的路径
     *  getSubFiles=true表示读取子目录的文件并上传，getSubFiles=false表示不读取子目录中的文件
     *  useSubDir=true表示在path路径下创建相应的子文件夹并将子文件放入相应的子文件夹下，useSubDir=false表示全部放到path路径下
     *  deleteFile=true 删除文件成功后删除本地文件，deleteFile=false删除成功后不删除本地文件
     * @return failList上传失败的文件名列表
     * @Create 2013-08-03 huxx
     */
    def uploadMutlFiles(File file,String path,boolean getSubFiles,boolean useSubDir,boolean deleteFile){
        def failList=[]
        if (file.isDirectory()){
            //获取ftp中文件存放路径
            def subPath=path
            if (useSubDir){
                subPath+=file.name+"/"
            }

            def lst=file.listFiles()
            lst.each {
                def resultList=uploadMutlFiles(it,subPath,getSubFiles,useSubDir,deleteFile)
                failList.addAll(resultList.collect())
            }
        }else{
            def result=upload(file,file.name,path,deleteFile)
            if (!result){
                failList.add(file.name)
            }
        }

        return failList
    }

    /**
     * @Description 注销用户登录并关闭ftp连接g
     * @Params
     * @return
     * @create 2013-08-03 huxx
     */
    def disConnect(){
        if (ftpClient.isConnected()){
            ftpClient.logout()
            ftpClient.disconnect()
        }
    }
}
