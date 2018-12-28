package cn.com.wz.parent

import java.text.SimpleDateFormat;


/**
 *@Description 处理时间的公用方法 
 *@Auther huxx
 *@createTime 2012-9-17 上午10:32:54
 */
class DateUtil {
	
	/**
	 *@Description 获取当前指定时间，并以dateFormat形式返回
	 *@param dateFormat 时间格式
	 *@return 返回dateFormat形式的时间字符串
	 *@Auther huxx
	 *@createTime 2012-9-17 上午10:34:18
	 */
	static String getCurrentTime(Date date,String dateFormat){
		return new SimpleDateFormat(dateFormat).format(date);
	}
	/**
	 *@Description  获取指定格式的当前时间
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-17 上午11:17:05
	 */
	static String getCurrentTime(String dateFormat){
		Calendar rightNow = Calendar.getInstance();
		Date date=rightNow.getTime()
		return new SimpleDateFormat(dateFormat).format(date);
	}
	/**
	 *@Description  获取默认格式(yyyy-MM-dd HH:mm:ss)的当前时间
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-17 上午11:17:05
	 */
	static String getCurrentTime(){
		Calendar rightNow = Calendar.getInstance();
		Date date=rightNow.getTime()
		return new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(date);
	}

    /**
     * @Description 获取指定格式的指定日期,默认格式为'yyyy-MM-dd HH:mm:ss'
     * @param date
     * @param dateFormat
     * @return
     */
    static String getFormatTime(Date date,String dateFormat){
        if (!dateFormat){
            dateFormat= 'yyyy-MM-dd HH:mm:ss'
        }
        return new SimpleDateFormat(dateFormat).format(date)
    }

}
