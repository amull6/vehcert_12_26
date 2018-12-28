package cn.com.wz.parent.system.dictionary

import cn.com.wz.parent.base.BaseController;

/**
 * 字典表
 * @author haojia
 *
 */
class DictionaryController  extends BaseController{
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	/**
	 * 转向字典表主页面
	 * @return
	 */
    def index() { 
		//不明白为什么将这个转向页面的方法放到dicitonaryValue中的index中有问题
		render(view:'/cn/com/wz/parent/system/dictionary/dictionaryValue/index',model:params)
	}
}
