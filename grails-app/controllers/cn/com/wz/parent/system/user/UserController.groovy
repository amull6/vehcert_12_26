package cn.com.wz.parent.system.user

import cn.com.wz.parent.DateUtil;
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController;
import parent.poi.ExcelUtils;
import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil;
import cn.com.wz.parent.PasswordCodec;
import cn.com.wz.parent.base.BaseController;
import grails.converters.JSON
import cn.com.wz.parent.system.organization.Organization;
import cn.com.wz.parent.system.post.Post;
import cn.com.wz.parent.system.role.Role;
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.system.user.UserDetail
import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.insidenote.InsideNote
import java.text.SimpleDateFormat
import cn.com.wz.parent.system.dictionary.DictionaryValue;
/**
 *@Description 用户管理模块
 *@Auther huxx
 *@createTime 2012-10-25 下午4:27:51
 */
class UserController extends BaseController{

    //定义一些方法的提交方式
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    //定义服务类
    def userService
    def exportService
    def logService

    def index() {
        redirect(action: "list", params: params)
    }

    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-25 下午4:28:09
     */
    def list() {
        render (view:'/cn/com/wz/parent/system/user/index', model:[tabId:params.tabId])
    }

    /**
     *@Description 用于生成表格数据   获取满足条件的用户信息列表
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-9-20 上午8:26:26
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        //调用service中的方法，获取数据
        def result=userService.jsonListByParams(params)
        render result
    }

    /**
     *@Description 跳转到用户新建页面
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-25 下午4:40:16
     */
    def create() {
		def userInstance=new User(params)
		userInstance.status=true
        render (view:'/cn/com/wz/parent/system/user/create', model:[user: userInstance,userDetail:new UserDetail(params)])
    }

    /**
     * @Description 跳转到岗位页面并当已有岗位信息时，将之传给岗位页面
     * @author wangtao
     * @createTime 2012-12-17
     */
    def getPosts(){
        def posts=[]
        def postsIds=params.postsIds
        if(postsIds!=null&&postsIds!=''){
            def idsArray=postsIds?.split('_')
            idsArray?.each{i->
                posts+=Post.get(i)
            }
        }
        render (view:'/cn/com/wz/parent/system/user/getPosts', model:[tabId:params.tabId,posts:posts])
    }
    /**
     * @Description 跳转到岗位页面并当已有岗位信息时，将之传给岗位页面
     * @author wangtao
     * @createTime 2012-12-17
     */
    def getOrgans(){
        def organs=[]
        def organsIds=params.organsIds
        if(organsIds!=null&&organsIds!=''){
            def idsArray=organsIds?.split('_')
            idsArray?.each{i->
                organs+=Organization.get(i)
            }
        }
        render (view:'/cn/com/wz/parent/system/user/getOrgans', model:[tabId:params.tabId,organs:organs])
    }
    def getRoles(){
        def roles=[]
        def rolesIds=params.rolesIds
        if(rolesIds!=null&&rolesIds!=''){
            def idsArray=rolesIds?.split('_')
            idsArray?.each{i->
                roles+=Role.get(i)
            }
        }
        render (view:'/cn/com/wz/parent/system/user/getRoles', model:[tabId:params.tabId,roles:roles])
    }
    /**
     * @Description 岗位页面取数函数，取出所有操作人员所属组织的所有岗位
     * @author wangtao
     * @createTime 2012-12-24
     */


    /**
     *@Description 保存新建信息 （user信息和userDetail信息）
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-25 下午4:41:01
     */
    def save() {
        def userInstance = new User(params)
        //设置用户默认密码
        userInstance.password='888888'.encodeAsPassword()
        //设置用户详细信息
        def userDetailInstance= new UserDetail(params)
        userInstance.userDetail=userDetailInstance

        userInstance.setCreaterId(getCurrentUser()?.getId())
        userInstance.setCreateTime(new Date())
        def flag=userService.save(userInstance,params.organsIds,params.postsIds,params.rolesIds)

        if (!flag) {
            render(view: "/cn/com/wz/parent/system/user/create", model: [user: userInstance,userDetail:userDetailInstance])
            return
        }else{
            saveNotes(userInstance.userDetail.realName+'_'+userInstance.userName,1)
            flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.userName])
            redirect(action: "show", id: userInstance.id)
        }

    }

    /**
     *@Description 跳转到详情页面
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-25 下午4:50:32
     */
    def show() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        render (view:'/cn/com/wz/parent/system/user/show', model: [user: userInstance])
    }

    /**
     *@Description 进入编辑页面
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-25 下午4:52:19
     */
    def edit() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }
        def organs=[]
        def organsIds=""
        organs=userInstance?.organs
        organs?.each{organ->
            organsIds+=organ.id+"_"
        }
        def posts=[]
        def postsIds=""
        posts=userInstance?.posts
        posts?.each{post->
            postsIds+=post.id+"_"
        }
        def roles=[]
        def rolesIds=""
        roles=userInstance?.roles
        roles?.each{role->
            rolesIds+=role.id+"_"
        }
        render (view:'/cn/com/wz/parent/system/user/edit',model:  [user: userInstance,organsIds:organsIds,postsIds:postsIds,rolesIds:rolesIds])
    }

    /**
     *@Description 保存更新信息
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-10-25 下午4:53:18
     */
    def update() {
        //判定要修改的记录是否存在
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        userInstance.setLastUpdaterId(getCurrentUser()?.getId())
        userInstance.setLastUpdateTime(new Date())
        //调用services中的更新方法
        def flag=userService.update(userInstance, params)

        if (flag==1) {
            userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                    [message(code: 'user.label', default: 'User')] as Object[],
                    "Another user has updated this User while you were editing")
            render(view: "/cn/com/wz/parent/system/user/edit", model: [user: userInstance])
            return
        }else if(flag==2){
            render(view: "/cn/com/wz/parent/system/user/edit", model: [user: userInstance])
            return
        }else{
            saveNotes(userInstance.userDetail.realName+'_'+userInstance.userName,2)
            flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.userName])
            redirect(action: "show", id: userInstance.id)
        }
    }

    /**
     * @Description 使用ajax删除用户记录
     *@param params
     *@return 操作信息
     *@Auther huxx
     *@createTime 2012-9-14 上午9:46:18
     */
    def jsonDelete(){
        def msg='删除成功！'
        def fails=''
        def ids=params.deleteIds?.split("_")
        ids?.each{u->
            def user=User.get(u)
            if (!user){
                return
            }
            def userName=user?.userDetail?.realName +'_'+user?.userName
            def flag=userService.delete(user)
            if (flag){
                saveNotes(userName,0)
            }else{
                fails+=userName+'、'
            }
        }
        if(fails!=''&&fails!=null){
            fails=fails.substring(0,fails.lastIndexOf("、"))
            msg=fails+'删除失败！'
        }
        render msg
    }
    /**
     *@Description 重置密码
     *@param
     *@return
     *@Auther huxx
     *@createTime 2012-11-5 下午4:03:44
     */
    def resetPassword(){
        def msg=userService.resetPassword(params)
        render message(code:"${msg}")
    }
    /**
     *@Description 从excel中导入用户信息
     *@param  filePath:用户信息文件路径；
     *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow从第几行开始读取，从0开始；
     *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
     *@return
     *@Auther huxx
     *@createTime 2013-2-27 下午2:50:28
     */
    def impUsers(String filePath){
        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取图片存储的相对路径(不包含文件名)
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==3){
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
            }
        }
        ExcelUtils excelOp=new ExcelUtils();
        Map config_column_map=[sheet:0,
                startRow:2,
                columnMap:[
                        '0':'organCode',
                        '1':'code',
                        '2':'postNameC',
                        '3':'jobLevel',
                        '4':'realName',
                        '5':'userName'
                ]
        ]
        def lst=excelOp.readExcel(realPath, config_column_map)
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
                def m=userService.saveImport(p)


                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (p.realName){
                            returnData+= p.realName
                        } else{
                            returnData+= "norealName"
                        }
                        returnData+="_"
                        if (p.userName){
                            returnData+= p.userName
                        } else{
                            returnData+= "nouserName"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'user.label')}"
                            it.arguments[0]="${message(code:"user.${ it.arguments[0]}.label")}"
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
            errorMessages.append("【realName=${it.realName} userName=${it.userName}】 错误信息为："+it.errorMessages+"&*_*&")
        }


        def result=[realCount:count,count:totalCount,errorMessages:errorMessages,returnData:returnData]
        render result as JSON

    }
    /**
     *@Description 跳转到导入文件页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-03-10
     */
    def importPage(){
        //传入参数来跳转不同的action
        render(view:'/cn/com/wz/parent/common/import',model:[tabId:params.tabId,url:"${createLink(controller:'User',action:'impUsers')}"])
    }
    /**
     * @Description 个人信息的维护
     *@param
     *@return
     *@Auther lly
     *@createTime 2013-03-04
     */
    def changeMessage(){
        def userInstance=session.getAttribute(ConstantsUtil.LOGIN_USER)
        params.userId=userInstance?.id
        print(params.userId)
        userInstance=User.get(params.userId)
        render(view:"/cn/com/wz/parent/system/user/changeMessage",model: [user: userInstance])
    }
	/**
	 *@Description 保存更新个人信息维护后的相关内容
	 *@param
	 *@return
	 *@Auther liuly
	 *@createTime 2013-3-04
	 */
	def updateChangeMessage() {
		//判定要修改的记录是否存在
		def userInstance = User.get(params.id)
		userInstance.setLastUpdaterId(getCurrentUser()?.getId())
		userInstance.setLastUpdateTime(new Date())
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
			redirect(action: "list")
			return
		}

		//调用services中的更新方法
	   userInstance.properties = params
		//给userDetail赋上新值
		if(userInstance.userDetail){
			userInstance.userDetail.properties=params
		}else{
			userInstance.userDetail=new UserDetail(params)
		}

		if (userInstance.save(flush:true)) {
			flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.userName])
			render(view: "/cn/com/wz/parent/system/user/changeMessage", model: [user: userInstance])
		}else{
			flash.message = message(code: 'default.updatFail.message', default:"Failed to update!")
			render (view:'/cn/com/wz/parent/system/user/changeMessage', model: [user: userInstance])
		}
	}
    /**
     *@Description 导出功能
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-03-19
     */
    def export(){
        if(params?.format && params.format != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=user.${params.extension}")
            List fields = ["num","organNameC", "organCode","postNameC","dicValueNameC","realName","userName"]
            Map labels =  ["num":"序号","organNameC":"部门名称", "organCode":"部门编码","postNameC":"岗位","dicValueNameC":"员工级别","realName":"员工姓名","userName":"登录名"]
            def upperCase = { domain, value ->
                return value.toUpperCase()
            }
            Map formatters = [organNameC: upperCase]
            Map parameters = [title: "员工信息表", "column.widths": [0.3, 0.3, 0.3,0.3,0.3,0.4],"pdf.encoding":"UniGB-UCS2-H", "font.family": "STSong-Light"	]
            def out=response.outputStream
			def cel={
				if(params.organsIds.length()>0){
					def idsArray = params.organsIds?.split('_')
					organs{
						inList("id",idsArray)
					}
						
				}
				if(params.postsIds.length()>0){
					def idsArray = params.postsIds?.split('_')
					posts{
						inList("id",idsArray)
					}
				}
				if(params.jobLevel){
					userDetail{
						jobLevel{
							eq("id",params.jobLevel)
						}
					}
				}
				if(params.createTime){
					if(params.createTimeTo){
						def createTime=params.createTime
						def createTimeTo=params.createTimeTo
						between("createTime",java.sql.Date.valueOf(createTime),java.sql.Date.valueOf(createTimeTo))
					}
				}
			}
			def user=User.createCriteria().list (cel)
			def lst=[]
			int i=1
			user.each {u->
				def m=[:]
				m.put("num",i)
                def organNames =''
                u.organs?.each {
                    organNames+=it.organNameC+"、"
                }

                if(organNames){
                    organNames=organNames.substring(0,organNames.lastIndexOf("、"))
                }
                m.put("organNameC", organNames)
                def organCodes =''
                u.organs?.each {
                    organCodes+=it.organCode+"、"
                }

                if(organCodes){
                    organCodes=organCodes.substring(0,organCodes.lastIndexOf("、"))
                }
				m.put("organCode", organCodes)
                def postNameCs =''
                u.posts?.each {
                    postNameCs+=it.postNameC+"、"
                }

                if(postNameCs){
                    postNameCs=postNameCs.substring(0,postNameCs.lastIndexOf("、"))
                }
				m.put("postNameC", postNameCs)
				m.put("dicValueNameC",u.userDetail.jobLevel.dicValueNameC)
				m.put("realName", u.userDetail.realName)
				m.put("userName",u.userName)
				lst.add(m)
				i++
			}
            exportService.export(params.format, out,lst, fields, labels, formatters, parameters)
            out.flush()
            out.close()
        }
    }
	    /**
     *@Description 导出部分数据页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-04-19
     */
	def exportPage(){
		render(view:"/cn/com/wz/parent/system/user/exportUser",model: [extension:params.extension,format:params.format])
	}
    /**
     *@Description 获取用户操作，写入操作日志
     *@param users 用户的中文名；type 操作类型  0为删除1为添加2为更新
     *@return
     *@Auther liucj
     *@createTime 2013-3-28 上午11:40
     */
    def saveNotes(users,type){
        //获取当前登录用户
        def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
        //组装操作内容
        def operat=message(code:'user.dele.lable',args:["$users"])
        if(type==1){
            operat=message(code:'user.save.lable',args:["$users"])
        }
        if(type==2){
            operat=message(code:'user.update.lable',args:["$users"])
        }
        //写入日志
        logService.insertLog(user.id, "user", operat,'businessLog')
    }

    /**
     *@Description 获取满足条件的用户list
     *@param params.partPageFlag=1表示分页，params.partPageFlag=0表示不分页；
     *@return 获取满足条件的用户json数据
     *@Auther huxx
     *@createTime 2012-9-20 上午8:26:26
     */
    def getJsonUserList(){
        String realNames=params.realName
        if (realNames){
            def lst=realNames.split("、")
            params.realName=lst[lst.size()-1]
        }
        if ("1"==params.partPageFlag){
            params.max = params.limit ? params.int('limit') : 10
            params.offset = params.start ? params.int('start') :0
        }

        //调用service中的方法，获取数据
        def lst=userService.listByParams(params)
        def result=[:]
        result.put("valueField", "realName");
        result.put("data", lst);
        render result as JSON
    }
	/**
	 *@Description 跳转到高级查询
	 *@Auther liuly
	 *@createTime 2013-06-24
	 */
	def sysUser(){
		render(view:'/cn/com/wz/parent/system/user/sysUser',model:[groupId:params.groupId])
	}
}

