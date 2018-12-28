package cn.com.wz.parent.cms

import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User

/**
 *@Description   文件管理控制器
 *@Auther LiuCJ
 *@createTime 2012-10-05 上午8:05:39
 *@update 修改大文本框  .withTransaction {} 12月4日下午3: 50
 */
class ArticleController  extends BaseController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def  articleService
	/**
	 * @Description 生成文章视图
	 * @
	 */
	def index() {
		def a =params.currentCategoryId
		render(view:'/cn/com/wz/parent/cms/article/index',model:params)
	}

	def list() {
		render(view:'/cn/com/wz/parent/cms/article/index',model:params)
	}
	/**
	 * @Description 置顶控制器
	 * @
	 */
	def top(){
		Article.withTransaction {
			def flag=true
			def idsStr=params.ids
			def ids=idsStr?.split("_")
			ids.each {id->
				
				try{
					def articleInstance= Article.get(id)
					articleInstance.setIsTop(true)
					articleInstance.save(flush:true)
				}catch(DataIntegrityViolationException e){
					flag=false
				}
			}
		
			if(flag){
				render message(code: 'organUser.save.success.message',default:'Success')
			}else{
				render message(code: 'organUser.save.fail.message',default:'failed')
			}
		}
	}
	/**
	 * @Description 取消置顶控制器
	 * @
	 */
	def untop(){
		Article.withTransaction {
			def flag=true
			def idsStr=params.ids
			def ids=idsStr?.split("_")
			ids.each {id->
				
				try{
					def articleInstance= Article.get(id)
					articleInstance.setIsTop(false)
					articleInstance.save(flush:true)
				}catch(DataIntegrityViolationException e){
					flag=false
				}
			}
		
			if(flag){
				render message(code: 'organUser.save.success.message',default:'Success')
				
			}else{
				render message(code: 'organUser.save.fail.message',default:'failed')
			}
		}
	}

    /**
     * @Description 推荐控制器
     * @
     */
    def recommend(){
        Article.withTransaction {
            def flag=true
            def idsStr=params.ids
            def ids=idsStr?.split("_")
            ids.each {id->

                try{
                    def articleInstance= Article.get(id)
                    articleInstance.setRecommend(1)
                    articleInstance.save(flush:true)
                }catch(DataIntegrityViolationException e){
                    flag=false
                }
            }

            if(flag){
                render message(code: 'organUser.save.success.message',default:'Success')

            }else{
                render message(code: 'organUser.save.fail.message',default:'failed')
            }
        }
    }

    /**
     * @Description 取消推荐控制器
     * @
     */
    def unrecommend(){
        Article.withTransaction {
            def flag=true
            def idsStr=params.ids
            def ids=idsStr?.split("_")
            ids.each {id->

                try{
                    def articleInstance= Article.get(id)
                    articleInstance.setRecommend(0)
                    articleInstance.save(flush:true)
                }catch(DataIntegrityViolationException e){
                    flag=false
                }
            }

            if(flag){
                render message(code: 'organUser.save.success.message',default:'Success')

            }else{
                render message(code: 'organUser.save.fail.message',default:'failed')
            }
        }
    }

	/**
	 * @Description 审核控制器
	 * @
	 */
	def audit(){
		Article.withTransaction {
			def flag=true
			def idsStr=params.ids
			def ids=idsStr?.split("_")
			ids.each {id->
				
				try{
					def articleInstance= Article.get(id)
					articleInstance.setIsAudited(1)
					articleInstance.save(flush:true)
				}catch(DataIntegrityViolationException e){
					flag=false
				}
			}
		
			if(flag){
				render message(code: 'organUser.save.success.message',default:'Success')
			}else{
				render message(code: 'organUser.save.fail.message',default:'failed')
			}
		}
	}
	/**
	 * @Description 取消审核控制器
	 * @
	 */
	def unaudit(){
		Article.withTransaction {
			def flag=true
			def idsStr=params.ids
			def ids=idsStr?.split("_")
			ids.each {id->
				
				try{
					def articleInstance= Article.get(id)
					articleInstance.setIsAudited(2)
					articleInstance.save(flush:true)
				}catch(DataIntegrityViolationException e){
					flag=false
				}
			}
		
			if(flag){
				render message(code: 'organUser.save.success.message',default:'Success')
				
			}else{
				render message(code: 'organUser.save.fail.message',default:'failed')
			}
		}
	}

	/**
	 * 根据栏目类型获取对应文章数据
	 * @return
	 */
	def getArticleByType(){
		params.max = params.limit ? params.int('limit') : 10
		params.offset =params.start ? params.int('start') :0

		def result=[:]
		if(params.articleCategory){
				 def cel={
					 articleCategory{
						 eq('id',"${params.articleCategory}")
					 }
					 if(params.selectOwn){
						 eq('createrId',getCurrentUser()?.getId())
					 }
					 order("isTop","desc")
                     order("showOrder","desc")
					 order("createTime","desc")
				 }
				
				def list=Article.createCriteria().list(params,cel)
				result.rows=list
				result.total=list.totalCount
				
		}else{
				result.rows=[]
				result.total=0
		}
		render result as JSON
	}
	/**
	 * 根据条件查询数据
	 * @return
	 */
	def search(){
		params.max =params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		def cel = {
			if(params.titleC){
				like("titleC", "%${params.titleC}%")
			}
			if(params.keyWords){
				like("keyWords", "%${params.keyWords}%")
			}
			if(params.categoryId){
				eq("articleCategory",ArticleCategory.get(params.categoryId))
			}
			order("isTop","desc")
			order("createTime","desc")
		}
		//设置排序参数
		def results = Article.createCriteria().list(params,cel)
		def result = [rows: results, total: results.totalCount]
		render result as JSON
	}
	
	def create() {
		render(view:'/cn/com/wz/parent/cms/article/create',model:[articleInstance: new Article(params),selectOwn:params.selectOwn])
	}

	def save() {
		Article.withTransaction {
            def articleInstance = new Article(params)
            articleInstance.contentC=params.content
            articleInstance.description=params.descrip
            //保存图片信息
            String picNameStr=params.picture_uploadFileNames

            def picStr=picNameStr.split(':_:')

            if(picStr?.length>=2){
                //获取图片存储的相对路径(不包含文件名)
//                def picRelativePath=UploadUtil.getRelativePathByUploadType(picStr[0])
                def picNames=picStr[1].split(":;:")
                if(picNames.length==3){
                    articleInstance.setPicName(picNames[1])
                    def relativeFile=picNames[2]
                    articleInstance.setPicPath(relativeFile)
                }
            }

            if (!articleInstance.save(flush: true)) {
                //获取附件信息
                def fileNames=params.accessorys_uploadFileNames
                //获取上传图片信息
                def picNames=params.picture_uploadFileNames

                render(view: "/cn/com/wz/cms/article/create", model: [articleInstance: articleInstance,fileNames:fileNames,picNames:picNames,selectOwn:params.selectOwn])
                return
            }
            //保存附件信息
            String fileNames=params.accessorys_uploadFileNames
            def str=fileNames.split (":_:")
            if(str?.length>=2){

                //将附件信息保存到数据库
                for(int i=1;i<str.length;i++){
                    def names=str[i].split (":;:")
                    if(names.length==3){
                        def saveFileName=names[0]
                        String fileName=names[1]
                        String relativeFile=names[2]
                        Accessory t=new Accessory();
                        t.setFileName(fileName)
                        t.setFilePath(relativeFile)
                        t.setFileType(UploadUtil.getExtName(fileName))
                        t.upTime=new Date()
                        t.article=articleInstance
                        t.save(flush:true)
                    }else{
                        println '在文章管理中保存附件时文件上传有问题'
                    }

                }
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.titleC])
            articleInstance.setCreateTime(new Date())
            articleInstance.setCreaterId(getCurrentUser()?.getId())
            Map result=[id: articleInstance.id,selectOwn:params.selectOwn]
            render result as JSON
        }
	}
	/**
	 * @Description 前台通知及新闻的内容展示 
	 * @param 
	 * @return 
	 * @update 2013-02-19 添加了对组织部门的处理
     * @update 2013-07-12 添加了两化：大事记和卷首语
	 */
	def frontShow(){
		def articleInstance = Article.get(params.id)
        if(params.categoryCode=='dsj'||params.categoryCode=='jsy'){
            articleInstance =  Article.findByArticleCategory(ArticleCategory.findByCategoryCode(params.categoryCode))
        }
		def categoryCode=articleInstance?.articleCategory?.categoryCode
		def picPath=articleInstance?.picPath
		def Path=articleInstance?.accessorys?.filePath
		def filePath=''
		if(Path){
			filePath=Path[0]
		}
		if (!articleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
			redirect(action: "list")
			 return
		}
	  def createrId=articleInstance.createrId
	  def user=User.get(createrId)
	  def organNameC=""
	  user?.organs?.each {
		  organNameC+=it.organNameC+"、"
	  }

	  if(organNameC){
		  organNameC=organNameC.substring(0,organNameC.lastIndexOf("、"))
	  }


	   if('notes'.equals(categoryCode) || 'gszd'.equals(categoryCode)){//弹出框显示公司公告和公司制度
		   render(view:'/cn/com/wz/parent/cms/article/notice_show',model:[articleInstance: articleInstance,organNameC:organNameC])
		   
	   }
	   else if('videoNews'.equals(categoryCode)){
		   render(view:'/cn/com/wz/parent/cms/article/videoNews_show',model:[articleInstance: articleInstance,organNameC:organNameC,picPath:picPath,filePath:filePath])
	   }else{ //弹出框显示公司新闻
	   		render(view:'/cn/com/wz/parent/cms/article/news_show',model:[articleInstance: articleInstance,organNameC:organNameC])
	   }

	}
	
	def frontShow2(){
        def articleInstance = Article.get(params.id)
        def map=[:]
        def category=articleInstance.articleCategory
        map.articleCategoryCode=category?.categoryCode
        map.max = 6
        map.offset = 0
        def relativeArticles=articleService.getArticles(map)
        render (view:'/cn/com/wz/parent/cms/article/itAproArticle',model:[article:articleInstance,relativeArticles:relativeArticles])
    }
	
	def show() {
		def a=params
		def articleInstance = Article.get(params.id)
		if (!articleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
			redirect(action: "list")
			return
		}
		render(view:'/cn/com/wz/parent/cms/article/show',model:[articleInstance: articleInstance,selectOwn:params.selectOwn])
	}

	def edit() {
		def params=params
		def articleInstance = Article.get(params.id)
		if (!articleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
			redirect(action: "list",params:[selectOwn:params.selectOwn])
			return
		}
		
		//获取上传图片信息
		def picName=articleInstance.picName
		def picPath=articleInstance.picPath
		def picNames=''
        if(picPath){
            picNames=picPath?.substring(picPath?.lastIndexOf("/")+1)+":;:"+picName+':;:'+picPath+":_:"
        }

        //获取附件信息
        def accessorys=Accessory.findAllByArticle(articleInstance)
        def fileNames=''
        accessorys?.each {a->
            fileNames+=a.filePath?.substring(a.filePath?.lastIndexOf("/")+1)+":;:"+a.fileName+':;:'+a.filePath+":_:"
        }
        render(view:'/cn/com/wz/parent/cms/article/edit',model:[articleInstance:articleInstance,fileNames:fileNames,picNames:picNames,selectOwn:params.selectOwn])
    }

	def update() {
		Article.withTransaction {
            def articleInstance = Article.get(params.id)
            if (!articleInstance) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])
                redirect(action: "list")
                return
            }

            if (params.version) {
                def version = params.version.toLong()
                if (articleInstance.version > version) {
                    articleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                              [message(code: 'article.label', default: 'Article')] as Object[],
                              "Another user has updated this Article while you were editing")
                    render(view: "edit", model: [articleInstance: articleInstance])
                    return
                }
            }

            articleInstance.properties = params
            articleInstance.isAudited=0

            if (!articleInstance.save(flush: true)) {

                //获取附件信息
                def fileNames=params.accessorys_uploadFileNames
                //获取上传图片信息
                def picNames=params.picture_uploadFileNames

                render(view: "/cn/com/wz/cms/article/edit", model: [articleInstance: articleInstance,fileNames:fileNames,picNames:picNames])
                return
            }

            //删除已保存的图片信息
            articleInstance.setPicName("")
            articleInstance.setPicPath("")
            //更新图片信息
            String picNameStr=params.picture_uploadFileNames
            def picStr=picNameStr.split(":_:")

            if(picStr?.length>=2){
                //获取图片存储的相对路径(不包含文件名)
                def names=picStr[1].split (':;:')
                if(names.length==3){
                    articleInstance.setPicName(names[1])
                    articleInstance.setPicPath(names[2])
                }
            }
            articleInstance.save(flush: true)

            //获取附件信息
            def accessorys=Accessory.findAllByArticle(articleInstance)
            //删除原有附件信息
            accessorys?.each {a->
                a.delete(flush:true)
            }
            //重新保存附件信息
            String fileNames=params.accessorys_uploadFileNames
            def str=fileNames.split (":_:")
            //获取文件存储的相对路径(不包含文件名)
            def relativePath=UploadUtil.getRelativePathByUploadType(str[0])
            //将附件信息保存到数据库
            for(int i=1;i<str.length;i++){
                def names=str[i].split (":;:")
                if(names.length==3){
                    def saveFileName=names[0]
                    String fileName=names[1]
                    String relativeFile=names[2]
                    Accessory t=new Accessory();
                    t.setFileName(fileName)
                    t.setFilePath(relativeFile)
                    t.setFileType(UploadUtil.getExtName(fileName))
                    t.upTime=new Date()
                    t.article=articleInstance
                    t.save(flush:true)
                }else{
                    println '在文章管理中，更新数据附件时报错'
                }

            }

            articleInstance.setLastUpdateTime(new Date())
            articleInstance.setLastUpdaterId(getCurrentUser()?.getId())
            flash.message = message(code: 'default.updated.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.titleC])
            redirect(action: "show", params:[id: articleInstance.id,selectOwn:params.selectOwn])
        }
	}
	
	def jsonDelete(){
		def ids = params.deleteIds
		def idsArray = ids.split('_')
		def flag = true
		def msg = message(code: 'default.deleted.simple.message')
		idsArray.each {
			def articleInstance = Article.get(it)
			if (!articleInstance) {
				//msg << message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), it])
				return
			}
			try {
				//删除图片文件
				if(articleInstance.picPath){
					UploadUtil.deleteFile(servletContext,articleInstance.picPath)
				}
				//###########################删除附件信息#########################################
					//获取附件信息
					def accessorys=Accessory.findAllByArticle(articleInstance)
					//删除原有附件信息
					accessorys?.each {a->
						//删除文件
						String realPath=servletContext.getRealPath(a.filePath)
						UploadUtil.deleteFile(servletContext,a.filePath)
						a.delete(flush:true)
					}
				//###########################删除附件信息END########################################
				
				articleInstance.delete(flush: true)
				//msg << message(code: 'default.deleted.message', args: [message(code: 'article.label', default: 'Article'), it])
			}catch (DataIntegrityViolationException e) {
				//msg << message(code: 'default.not.deleted.message', args: [message(code: 'article.label', default: 'Article'), it])
				flag = false
			}
			if(!flag){
				msg = message(code: 'default.not.simple.deleted.message')
			}
		}
		render msg
	}
	/**
	 *@Description 获取通知通告
	 *@param  categoryCode=news获取新闻信息类文章；categoryCode=notes获取通知公告类文章；
	 *categoryCode=qywh 获取企业文化类文章；categoryCode=gszd获取公司制度类文章
	 *categoryCode=ygwy获取员工文苑类文章；categoryCode=itAndPro获取两化融合下推荐的没有图片的文章
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-12-17 下午2:27:22
	 */
	def getArticles(){
		params.max =params.maxSize
		params.offset =0
		def lst=articleService.getArticles(params)
        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)
		render(contentType: "text/json") {
			array {
			   lst?.each {a->
                    //获取文件绝对路径
                    def filepath = rootDir+a.picPath
					article(id: a.id,createTime:a.createTime,title:a.titleC,descrip:a.description,picPath:filepath,content:a.contentC,articleCategory:a.articleCategory.categoryNameC,articleCategoryCode:a.articleCategory.categoryCode)
				}
			}
		}
	}
	

	/**
	 *@Description 跳转到更多通知页面
	 *@param categoryCode=news获取新闻信息类文章；categoryCode=notes获取通知公告类文章；
	 *categoryCode=qywh 获取企业文化类文章；categoryCode=gszd获取公司制度类文章
	 *categoryCode=ygwy获取员工文苑类文章
	 *@return
	 *@Auther huxx
	 *@createTime 2012-12-24 上午9:04:01
	 */
	def toMoreArticles(){
		params.max = params.max ? params.int('max') :10
		params.offset = params.offset ? params.int('offset') : 0
		
		def lst=articleService.getArticles(params)
		render (view:'/cn/com/wz/parent/cms/article/more',model:[categoryCode:params.categoryCode,lst:lst])
	}
	/**
	 *@Description 跳转到更多上次登录时间到现在的通知页面
	 *@param categoryCode=news获取新闻信息类文章；categoryCode=notes获取通知公告类文章；
	 *categoryCode=qywh 获取企业文化类文章；categoryCode=gszd获取公司制度类文章
	 *categoryCode=ygwy获取员工文苑类文章
	 *@return
	 *@Auther liuly
	 *@createTime 2013-3-04
	 */
	def getNewTimeArticles(){
		params.max = params.max ? params.int('max') :10
		params.offset = params.offset ? params.int('offset') : 0
		
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		params.lastLoginTime=user.lastLoginTime
		params.categoryCode="notes'"
		def lst=articleService.getNewTimeArticles(params)
		render (view:'/cn/com/wz/parent/cms/article/more_newArticle',model:[categoryCode:params.categoryCode,lst:lst])
	}
	/**
	 * @Description 获取新通知数量
	 * @param
	 * @return
	 * @Author liuly
	 * @createTime 2013-3-03
	 */
	def getNewArticlesNum(){
		int resultArticle=0
		int resultNotes=0
//		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def userMessage=User.get(user?.id)
		def resultUser=0
		//判断当前用户
		if(userMessage.userDetail.realName==null||userMessage.userDetail.eMail==null||userMessage.userDetail.mPhone==null){
				resultUser=1
		}
//		//判断协同状态
//		def resultAffair=affairService.getAffairsNum(user?.id)
		Map result=[resultUser:resultUser]
		
		render result as JSON
	}
	
	/**
	 * @Description 获取文章新建预览功能
	 * @return
	 * @Author liuCJ
	 * @createTime 2013-5-11 下午18:50
	 */
	def view() {
		Article.withTransaction {
		def articleInstance = new Article(params)
			articleInstance.contentC=params.content
		if (!articleInstance.save(flush: true)) {
			//获取附件信息
			def fileNames=UploadUtil.getFileNamesByUpFileNames(params.accessorys_uploadFileNames)
			//获取上传图片信息
			def picNames=UploadUtil.getFileNamesByUpFileNames(params.picture_uploadFileNames)
			
			render(view: "/cn/com/wz/parent/cms/article/create", model: [articleInstance: articleInstance,fileNames:fileNames,picNames:picNames,selectOwn:params.selectOwn])
			return
		}
		articleInstance.setCreateTime(new Date())
		articleInstance.setCreaterId(getCurrentUser()?.getId())
		Map result=[id: articleInstance.id]
		render result as JSON
	}
	}

    /**
     * @Description 获取两化融合模块home页面轮显的文章
     * @return
     * @Author liuCJ
     * @createTime 2013-7-5 下午14:25
     */
    def getItAndPro(){
        params.max =params.maxSize
        params.offset =0
        def cel={
            articleCategory{
                    categoryType{
                        eq('code',"${params.categoryCode}")
                    }
                }
            eq('recommend',1)
            isNotNull('picPath')
            or{
                articleCategory{
                    eq('needAuth',false)
                }
                and{
                    articleCategory{
                        eq('needAuth',true)
                    }
                    eq('isAudited',1)
                }
            }
            order('isTop', 'desc')
            order('createTime','desc')
        }
        def lst=Article.createCriteria().list(params,cel)
        //获取文件存放根目录
        def rootDir=UploadUtil.getRootDir(servletContext)
        render(contentType: "text/json") {
            array {
                lst?.each {a->
                    //获取文件绝对路径
                    def filepath = rootDir+a.picPath
                    article(id: a.id,createTime:a.createTime,title:a.titleC,picPath:filepath)
                }
            }
        }
    }
}
