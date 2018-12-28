package cn.com.wz.parent.upload

import cn.com.wz.parent.FileUtil
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import grails.converters.JSON;
import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.UploadUtil;
import cn.com.wz.parent.base.BaseController
/**
 *@Description uploadr的控制器
 *@Auther huxx
 *@createTime 2012-10-8 上午11:08:05
 */
class UploadrController extends BaseController{
    /**
     *@Description 文件上传action
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-10 下午1:34:36
     */
    def uploadDocs(){
        def prefix=params.prefix
        if(!prefix){
            prefix='public'
        }
        String fileName=request.getParameter("qqfile")
        //获取保存文件的相对路径（不包含文件名）
        def relativePath=UploadUtil.getRelativePath(ConstantsUtil.UPLOAD_FILE_PATH)
        return upload(fileName,relativePath,prefix)
    }

    /**
     *@Description 图片上传action
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-10 下午1:34:36
     */
    def uploadImages(){
        def prefix=params.prefix
        if(!prefix){
            prefix='public'
        }
        String fileName=request.getParameter("qqfile")
        //获取保存文件的相对路径（不包含文件名）
        def relativePath=UploadUtil.getRelativePath(ConstantsUtil.UPLOAD_IMAGE_PATH)
        return upload(fileName,relativePath,prefix)
    }

    /**
     *@Description 视频上传action
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-10 下午1:34:36
     */
    def uploadVideos(){
        def prefix=params.prefix
        if(!prefix){
            prefix='public'
        }
        String fileName=request.getParameter("qqfile")
        //获取保存文件的相对路径（不包含文件名）
        def relativePath=UploadUtil.getRelativePath(ConstantsUtil.UPLOAD_VIDEO_PATH)
        return upload(fileName,relativePath,prefix)
    }
    /**
     *@Description 文件上传
     *@param  fileName 上传文件的名称；relativePath文件上传的相对路径;prefix文件前缀，并作为一个文件夹名
     *@return   relativeFile  带文件名的相对路径：；：文件实际名称     saveFileName文件保存名称：；：文件实际名称 :;:带文件名的相对路径
     *@Auther huxx
     *@createTime 2012-10-16 下午1:01:47
     */
    def upload(String fileName,String relativePath,String prefix ){
        try {
            InputStream inputStream =null
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
                fileName=uploadedFile.originalFilename
                inputStream= uploadedFile.inputStream
            }else{
                inputStream= request.inputStream
            }
            //获取文件的保存名称
            def saveFileName=UploadUtil.getSaveFileName(prefix,fileName)

            //获取带有文件名的文件的绝对路径
            def relativeFile=UploadUtil.getRelativeFile(servletContext,relativePath,saveFileName)

            //获取文件存放根目录
            def rootDir=UploadUtil.getRootDir(servletContext)
            def saveFilePath=rootDir+relativeFile
            File uploadFile = new File(saveFilePath)
            try {
                uploadFile << inputStream
            } catch (Exception e) {
                throw new FileUploadException(e)
            }
            return render(text: [success:true,saveFileName:saveFileName+':;:'+fileName+":;:"+relativeFile] as JSON, contentType:'text/html')
        } catch (FileUploadException e) {
            return render(text: [success:false] as JSON, contentType:'text/html')
        }
    }
    /**
     *@Description 删除指定的文件
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-10 下午4:15:57
     */
    def deleteFile(){
        String relativeFile=params.relativeFile
        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)

        String realPath=rootDir+relativeFile
        File file=new File(realPath)
        if(file.exists()){
            try{
                file.delete()
                return render {[success:true]} as JSON
            }catch(IOException e){
                e.printStackTrace()
                return render {[success:false]} as JSON
            }

        }
    }
}
