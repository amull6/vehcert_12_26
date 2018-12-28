package cn.com.wz.parent.system.post
import cn.com.wz.parent.UploadUtil
import parent.poi.ExcelUtils;
import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException
import java.lang.StringBuilder
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.ConstantsUtil

/**
 * Description 岗位类的控制器
 * @author Administrator xinbh
 * @createTime 2012-9-19 下午16：30
 */
class PostController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def logService
    def postService
     def index(){
     redirect(action: "list", params: params)
    }

    def list() { 
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
       // [postInstanceList: Post.list(params), postInstanceTotal: Post.count()]
		render(view:'/cn/com/wz/parent/system/post/list',model:params)
	}

	/**
	 * 根据条件查询数据
	 *@Auther xinbh
	 *@createTime 2012-9-21
	 */
	def search(){
		def dd=params
		params.max = params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		params.sort='showOrder'
		params.order='ASC'
		def cel = {
			
			if(params.postNameC){
				like("postNameC", "%${params.postNameC}%")
			}
			if(params.postCode){
				like("code", "%${params.postCode}%")
			}
			if(params.postType?.id){
				postType{
					eq("id",params.postType.id)
				}
			}
		}
		def results = Post.createCriteria().list(params,cel)
		def rows=[]
		results?.each{p->
			def m=[:]
			m.put('id', p.id)
			m.put('postNameC',p.postNameC)
			m.put('code', p.code)
			m.put('showOrder', p.showOrder)
			m.put('remark', p.remark)
			m.put('createTime', p.createTime)
			m.put('lastUpdateTime', p.lastUpdateTime)
			def postTypeName=p.postType?.dicValueNameC
			m.put('postTypeName',postTypeName)
			rows.add(m)
			
		}
		def result = [rows: rows, total: results.totalCount]
		render result as JSON
	}
     def create() {
		 def postInstance=new Post()
		 
		render(view:'/cn/com/wz/parent/system/post/create',model:[postInstance:postInstance])
    }

    def save() {
        def postInstance = new Post(params)
		postInstance.setCreateTime(new Date())
		postInstance.setCreaterld(getCurrentUser()?.getId())
        if (!postInstance.save(flush: true)) {
            render(view: "/cn/com/wz/parent/system/post/create", model: [postInstance: postInstance])
            return
        }
		saveNotes(postInstance.postNameC,1)
		flash.message = message(code: 'default.created.message', args: [message(code: 'post.label', default: 'Post'), postInstance.postNameC])
        redirect(action: "show", params:[id: postInstance.id])
    }

    def show() {
        def postInstance = Post.get(params.id)
        if (!postInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), params.id])
            redirect(action: "list")
            return
        }
		render(view:'/cn/com/wz/parent/system/post/show',model:[postInstance: postInstance])
    }

    def edit() {
        def postInstance = Post.get(params.id)
        if (!postInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), params.id])
            redirect(action: "list")
            return
        }
		render(view:'/cn/com/wz/parent/system/post/edit',model:[postInstance: postInstance])
		
       // [postInstance: postInstance]
    }

    def update() {
        def postInstance = Post.get	(params.id)
        if (!postInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (postInstance.version > version) {
                postInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'post.label', default: 'Post')] as Object[],
                          "Another user has updated this Post while you were editing")
                render(view: "/cn/com/wz/parent/system/post/edit", model: [postInstance: postInstance,organ:params.organ1])
                return
            }
        }

        postInstance.properties = params
		postInstance.setLastUpdateTime(new Date())
		postInstance.setLastUpdaterld(getCurrentUser()?.getId())

        if (!postInstance.save(flush: true)) {
            render(view: "/cn/com/wz/parent/system/post/edit", model: [postInstance: postInstance])
            return
        }
		saveNotes(postInstance.postNameC,2)
		flash.message = message(code: 'default.updated.message', args: [message(code: 'post.label', default: 'Post'), postInstance.postNameC])
        redirect(action: "show", params:[id: postInstance.id])
    }

	
	def getPostJson(){
		params.sort='showOrder'
		params.order='ASC'
		def results = [:]
		def rows = Post.list(params);
		results.total = rows.size()
		results.rows = rows
		render results as JSON
		}
	
	
	def jsonDelete(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		def post=''
		idsArray.each {
			def postInstance = Post.get(it)
			post+=postInstance.postNameC+'，'
			if (!postInstance) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				return
			}
			try {
				postInstance.delete(flush: true)
				//msg << message(code: 'default.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.deleted.message')
			}
		}
		saveNotes(post,0)
		render msg
	}
	
	/**
	 *@Description 生成左侧的组织结构树
	 *@Auther xinbh
	 *@createTime 2012-9-21
	 */
	def jsonListTree(){
		params.sort='showOrder'
		params.order='ASC'
		def result = Post.list(params);
		
		render(contentType: "text/json") {
			array {
				for (m in result) {
					item(id:m.id,pid:m.organId,text:m.postNameC)
				}
			}
		}
	}
	/**
	 *@Description ajax显示选中节点的详细信息
	 *@Auther xinbh
	 *@createTime 2012-9-23
	 */
	def jsonShow(){
		def post = Post.get(params.id)
		
		if (!post) {
			post.errors = message(code: 'default.not.found.message', args: [message(code: 'post.label', default: 'Post'), params.id])
		}
		render(template:"/cn/com/wz/parent/system/post/show", model:[post: post])
	}
	/**
	 *@Description 获取用户操作，写入操作日志
	 *@param post 岗位的中文名；type 操作类型  0为删除1为添加2为更新
	 *@return
	 *@Auther liucj
	 *@createTime 2013-3-28 上午11:40
	 */
	def saveNotes(post,type){
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		//组装操作内容
		def operat=message(code:'post.dele.lable',args:["$post"])
		if(type==1){
			operat=message(code:'post.save.lable',args:["$post"])
		}
		if(type==2){
			operat=message(code:'post.update.lable',args:["$post"])
		}
		//写入日志
		logService.insertLog(user.id, "post", operat,'businessLog')
	}
	/**
	 *@Description 从excel中导入用户信息
	 *@param  filePath:用户信息文件路径；
	 *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow从第几行开始读取，从0开始；
	 *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
	 *@Auther lly
	 *@createTime 2013-4-02
     * @update 修改了获取realPath错误；修改了导入数据不能保存问题，通过调用service方法保存，返回导入成功和失败信息
     * @updateTime 2014-05-17 by zhuwei
	 */
	def impPost(String filePath){
		def realPath=""
		def filePaths=filePath.split(':_:')
		if(filePaths?.length>=2){
			//获取图片存储的相对路径(不包含文件名)
			def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
			def fileNames=filePaths[1].split(":;:")
			if(fileNames.length==3){
				realPath=UploadUtil.getRelativeFile(servletContext, picRelativePath, fileNames[0])
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
			}
		}
		ExcelUtils excelOp=new ExcelUtils();
		Map config_column_map=[sheet:0,
							startRow:2,
							columnMap:[
								'1':'postNameC',      //从0列是指从A列开始取值；从1列是指从B列开始取值
								'2':'code',
								'3':'postTypeCode'
				
								]
						   ]
		def lst=excelOp.readExcel(realPath, config_column_map)
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def totalCount=lst?.size()  //读取记录总数
        def count=0                 //实际存储成功的记录数
        def returnData=""           //返回的第一个存储成功的记录
        def failList=[]             //保存失败记录

        lst?.each{ p->
            //清空属性值的前后空格
            p.entrySet()?.each{
                it.value=it.value?.toString()?.trim()
            }
            try{
                def m=postService.save(p,user)


                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (p.postNameC){
                            returnData+= p.postNameC
                        } else{
                            returnData+= "noPostNameC"
                        }
                        returnData+="_"
                        if (p.code){
                            returnData+= p.code
                        } else{
                            returnData+= "noCode"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'post.label')}"
                            it.arguments[0]="${message(code:"post.${ it.arguments[0]}.label")}"
                            errorMessages+="${num}、"+message(error: it)+";"
                        }

                    }
                    p.errorMessages=errorMessages
                    failList.add(p)
                }

            }catch(Exception e){
                p.errorMessages=e.message
                failList.add(p)
            }
        }
        StringBuilder errorMessages=new StringBuilder()
        failList?.each{
            errorMessages.append("【postNameC=${it.postNameC} code=${it.code}】 错误信息为："+it.errorMessages+"&*_*&")
        }


        def result=[realCount:count,count:totalCount,errorMessages:errorMessages,returnData:returnData]
        render result as JSON
	}
	/**
	 *@Description 跳转到导入文件页面
	 *@param
	 *@return
	 *@Auther liuly
	 *@createTime 2013-04-02
	 */
	def importPage(){
		//传入参数来跳转不同的action
		def numPost="imPost"
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'Post',action: 'impPost')}",tabId:params.tabId])
//		render(view:'/cn/com/wz/parent/common/import',model:[flag:numPost])
	}
}
