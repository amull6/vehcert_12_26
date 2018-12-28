package cn.com.wz.parent

import org.springframework.web.context.support.WebApplicationContextUtils

import java.util.Date;
import javax.servlet.ServletContext

import org.apache.commons.lang.math.RandomUtils;

/**
 *@Description 文件上传相关公共方法 
 *@Auther huxx
 *@createTime 2012-9-27 下午1:39:51
 */
class UploadUtil {

	/**
	 *@Description 根据传入的文件名生成新的文件名  文件名形式如下：前缀-年月日时分秒-随机数 .ext
	 *@param 文件名称带扩展名 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-27 下午1:50:55
	 */
	static String getSaveFileName(String prefix,String fileName){
		String fileExt=getExtName(fileName);
		def newFileName=prefix+'-'+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-" +RandomUtils.nextInt()+'.'+fileExt;
		return newFileName;
	}
	/**
	 *@Description 获取文件名扩展名
	 *@param 文件名称带扩展名 
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-27 下午1:50:55
	 */
	static String getExtName(String fileName){
		def ext=''
		if(fileName){
			ext=fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return ext;
	}

	/**
	 *@Description 获取保存文件的相对路径
	 *uploadPath文件上传路径，如果不存在就使用默认上传路径UPLOAD_FILE_PATH；
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-27 下午1:57:30
	 */
	static String getRelativePath(String uploadPath){
		def path=''
		//判断是否上传到默认文件夹下
		if(uploadPath){
			path=uploadPath
		}else{
			path=ConstantsUtil.UPLOAD_FILE_PATH
		}
		
		return path
	}
	/**
	 *@Description 根据相对路径和保存文件名获取文件相对保存路径（判断文件夹是否存在（保存时使用）,不存在创建）
	 *@param 
	 *@return 带有文件名的文件相对路径
	 *@Auther huxx
	 *@createTime 2012-10-18 上午8:36:47
     *@Update 2013-11-22 huxx 文件按时间分文件夹存放
	 */
	static String getRelativeFile(servletContext,String relativePath,String saveFileName){
		def relativeFile=''
		def prefix=getPrefix(saveFileName)
		def relativeFilePath=relativePath+prefix+'/' +DateUtil.getCurrentTime("yyyyMMdd")+"/"

        //获取文件存放根目录
        def rootDir=getRootDir(servletContext)
        def realPath=rootDir+relativeFilePath
		File f=new File(realPath)
		//如果文件夹不存在就创建
		if(!f.exists()){
			f.mkdirs()
		}
		relativeFile=relativeFilePath+saveFileName
		return relativeFile
	}
	/**
	 *@Description 根据相对路径和保存文件名获取文件相对保存路径（未判断文件夹是否存在）
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-10-18 上午8:36:47
     *@Update 2013-11-22 huxx 文件按时间分文件夹存放
	 */
	static String getRelativeFile(String relativePath,String saveFileName){
		def relativeFile=''
		def prefix=getPrefix(saveFileName)
		def relativeFilePath=relativePath+prefix+'/' +DateUtil.getCurrentTime("yyyyMMdd")+"/"
		relativeFile=relativeFilePath+saveFileName
		return relativeFile
	}
	/**
	 *@Description 获取文件前缀
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-10-18 上午8:57:07
	 */
	static String getPrefix(String saveFileName){
		def prefix=''
		if(saveFileName){
			if(!(saveFileName.indexOf('-')==-1)){
                prefix=saveFileName.substring(0,saveFileName.indexOf('-'))
            }
		}
		return prefix
	}
	
	/**
	 * @Description 根据传入的上传类型，获取上传文件的路径（不包含文件名）
	 * @param uploadType 文件上传类型 doc、image、video
	 * @return
	 * @createTime 2012-10-13
	 * @author huxx
	 */
	static String getRelativePathByUploadType(String uploadType){
		def relativePath=''
		if("doc".equals(uploadType)){
			relativePath=ConstantsUtil.UPLOAD_FILE_PATH
		}else if("image".equals(uploadType)){
			relativePath=ConstantsUtil.UPLOAD_IMAGE_PATH
		}else if("video".equals(uploadType)){
			relativePath=ConstantsUtil.UPLOAD_VIDEO_PATH
		}
		
		return relativePath
	}
	
	/**
	 *@Description 删除指定的文件
	 *@param relativeFile带有文件名的文件先对路径
	 *@return
	 *@Auther huxx
	 *@createTime 2012-10-10 下午4:15:57
     *@update huxx 2013-05-25 修改了删除时，从指定的目录中删除文件
	 */
	static void deleteFile(servletContext,String relativeFile){
        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)
        String realPath=rootDir+relativeFile
		File file=new File(realPath)
		if(file.exists()){
			try{
				file.delete()
			}catch(IOException e){
				e.printStackTrace()
			}
		}
	}
	/**
	 *@Description 根据从页面上传来的上传文件字符串（type:_:saveFileName0:;:fileName0:_:saveFileName1:;:fileName1:_:），
	 *生成传到页面的生成上传文件显示的字符串(fileName0:;:saveFileName0:;:savePath0:_:fileName1:;:saveFileName1:;:savePath1:_:)
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-10-16 上午11:37:24
	 */
	static String getFileNamesByUpFileNames(String upSaveFileNames){
		def arr=upSaveFileNames?.split(":_:")
		String type=''
		if(arr){
			type==arr[0]
		}
		
		//获取文件存储的相对路径(不包含文件名)
		def relativePath=getRelativePathByUploadType(type)
		def fileNameStr=upSaveFileNames.replaceFirst(type+':_:', '')?.split(':_:')
		def fileNames=""
		fileNameStr?.each {fName->
			if(fName.length()>0){
				def names=fName.split(":;:")
				if(names.length==2){
					fileNames+=names[1]+':;:'+names[0]+':;:'+getRelativeFile(relativePath,names[0])+':_:'
				}
				
			}
		}
		return fileNames
	}
	/**
	 *@Description 根据上传文件的信息获取文件的原名
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-10-16 上午11:50:39
	 */
	static String getFileNameByUpFileNames(String saveFileNames){
		def names=saveFileNames.split (":;:")
		if(names.length==2){
			return names[1]
		}else{
			return ''
		}
	}
	/**
	 *@Description 根据上传文件的字符串删除上传文件 
	 *@param  upSaveFileNames根据从页面上传来的上传文件字符串（type:_:saveFileName0:;:fileName0:_:saveFileName1:;:fileName1:_:）
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-10-16 下午12:30:23
	 */
	static int deleteFileByUpFileNames(ServletContext servletContext,String upSaveFileNames){
		String type=upSaveFileNames?.split(":_:")[0]
		//获取文件存储的相对路径(不包含文件名)
		def relativePath=getRelativePathByUploadType(type)
		def fileNameStr=upSaveFileNames.replaceFirst(type+':_:', '')?.split(':_:')
		int result=0
		fileNameStr?.each {fName->
			if(fName.length()>0){
				def names=fName.split(":;:")
				if(names.length==2){
					def relativeFile=getRelativeFile(relativePath,names[0])
					String filePath=servletContext.getRealPath(relativeFile)
					this.deleteFile(filePath)
					result++
				}
				
			}
		}
		return result
	}

    /**
     *@Description 获取上传文件的根文件夹
     * @param relativePath
     * @create huxx 2013-05-25
     */
    static String getRootDir(servletContext){
        def appCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
        def rootDir=appCtx.grailsApplication.config.upload.rootDir
        return rootDir
    }
}
