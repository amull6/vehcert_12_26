package cn.com.wz.parent.cms

/**
 *@description 文章包含的附件维护
 *@author LiuCJ
 *@createTime 2012-10-4 下午4：02
 *@update 更新员工活动的附件也保存在此domin类下    2013-5-28 下午2：50
 */

class Accessory {
	
	
	static mapping = {
		table 'TBL_ACCESSORYS'
		// version is set to false, because this isn't available by default for legacy databases
		version false
		id generator:'uuid.hex', column:'ID'
		upTime column:'UP_TIME'
   }
	String id;
	/**
	 * 文件相对路径（包含文件名称）
	 */
	String filePath;
	/**
	 * 文件名称，
	 */
	String fileName;
	/**
	 * 文件类型，保存文件名的后缀
	 */
	String fileType;
	/**
	 * 文件上传时间
	 */
	Date upTime;
	Article article
	static constraints = {
		id(size:1..200,blank:false)
		filePath(nullable:true)
		fileName(nullable:true)
		fileType(nullable:true)
		upTime(nullable:true)
		article(nullable:true)
	}
	
	
	String toString() {
		return "${fileName}"
	}
}
