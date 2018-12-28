package cn.com.wz.parent.system.role
import cn.com.wz.parent.base.BaseController;
import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
/**
 *@Description 用户角色维护控制器 
 *@Auther huxx
 *@createTime 2012-9-23 上午9:15:58
 *@update 2012-10-30 将employee修改user
 */
class RoleUserController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def logService
	/**
	 *@Description 跳转到用户角色维护页面 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-23 上午9:17:10
	 */
    def index() {
        render (view:'/cn/com/wz/parent/system/roleUser/index',model:[tabId:params.tabId])
    }

	/**
	 *@Description 跳转到用户角色添加页面 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-23 上午9:22:51
	 */
    def create() {
       render (view:'/cn/com/wz/parent/system/roleUser/index')
    }

	/**
	 *@Description 用于生成表格数据   opFlag=1表示获取指定组织结构下的全部人员信息；  opFlag=0表示获取不属于指定组织结构下的全部人员信息
	 *@param params.roleId 选中角色的id ;params.opFlag 0不等于，1等于
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-23 上午9:26:26
	 *@update huxx 2012-10-27 添加了一个用户可以输入多个角色的功能 
	 *@update huxx 2012-10-30 将用户修改为User
	 */
	def selectEmpByRole(){
		//设置分页信息
		params.max =params.limit ? params.int('limit') : 100
		params.offset = params.start ? params.int('start') :0

		def cel={}
		if('1'.equals(params.opFlag)){//获取指定角色下的用户信息
			 cel={
						if(params.userName){
							like("userName", "%${params.userName}%")
						}
						if(params.realName){
							userDetail{
								like("realName", "%${params.realName}%")
							}
						}
						userDetail{
							order ("realName",'asc')
						}
						eq('status',true)
						if(params.roleId){
							roles {
								eq('id',params.roleId)
							}
						}
						
					}
		}else if('0'.equals(params.opFlag)){//获取不属于指定角色的用户信息
			def r=Role.get(params.roleId)
			def ids=[]
			r?.users?.each {e->
				ids.add(e.id)
			}
			//处理id为空的情况。in的括号中不能没有内容那个
			if(ids.size()<1){
				ids.add('就不相信你存在')
			}
			cel={
				if(params.userName){
					like("userName", "%${params.userName}%")
				}
				if(params.realName){
					userDetail{
								like("realName", "%${params.realName}%")
							}
				}
				eq('status',true)
				//解决in中字符串不大于1000的问题
				def lst=StringUtil.splitIn(ids)
				and{
					lst?.each {id->
						not{
							'in'('id',id)
						}
					}
				}
			}
			
		}else{
			println  '操作参数错误！'
		}
		def rows=User.createCriteria().list(params,cel)
		//给user的realName和userTypeName赋值
		def all=[]
		rows?.each {u->
			def m=[:]
			m.id=u.id
			m.tempName=u.userDetail?.realName
			m.userName=u.userName
			m.organNames=''
			u.organs?.each {
				m.organNames+=it.organNameC+"、"
			}
			
			if(m.organNames){
				m.organNames=m.organNames.substring(0,m.organNames.lastIndexOf("、"))
			}
			m.postsNames=''
			u.posts?.each {
				m.postsNames+=it.postNameC+"、"
			}
			
			if(m.postsNames){
				m.postsNames=m.postsNames.substring(0,m.postsNames.lastIndexOf("、"))
			}
			m.jobLevel=u.userDetail?.jobLevel?.dicValueNameC
			m.userTypeName=u.userType?.dicValueNameC
			m.validStartTime=u.validStartTime
			m.validEndTime=u.validEndTime
			m.status=u.status
			
			all.add(m)
		}
		
		def result = [rows: all, total: rows.totalCount]
		render result as JSON
	}
	/**
	 *@Description 保存用户角色关系 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-23 上午9:28:08
	 */
    def save() {
		//获取要关联的记录的ids
		def userIds=params.userIds
		def userIdArr=userIds.split('_');
		
		//定义操作标识
		def flag=true
		//获取角色信息
		def role=Role.get(params.roleId)
		role.setLastUpdateTime(new Date())
		//将用户角色关系保存到数据库
		userIdArr.each {userId->
			try{
				def user=User.get(userId)
				user?.addToRoles(role)?.save(flash:true)//两种方式是等价的
			}catch(DataIntegrityViolationException e){
				flag=false
			}
		}
		
		//设置组织用户关系保存信息
		if(flag){
			saveNotes(params.roleId,params.userIds,1)
			render message(code: 'default.save.success.message',default:'Success')
		}else{
			render message(code: 'default.save.fail.message',default:'failed')
		}
    }

    

	/**
	 *@Description 使用ajax删除用户角色关系
	 *@param params.roleId 选中角色的id；params.deleteIds要删除的用户ids
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-23上午10:02:31
	 */
    def jsonDelete() {
		def msg = message(code: 'default.deleted.simple.message')
      
	   def arrayIds=params.deleteIds?.split('_')
	   def flag=true
	   def roler=Role.get(params.roleId)
	   for(userId in arrayIds){
		   			//获取用户及其与组织的关联（不能用get获取，那样获取不到所属的组织信息）
				   def user=User.createCriteria().list({eq('id',userId) roles{eq('id',params.roleId)}})
				   user=user?.get(0)
				try{
					roler.removeFromUsers(user)
				}catch(DataIntegrityViolationException e){
					flag=false
				}
	   }
   		   
	   if(!flag){
		   msg = message(code: 'default.not.simple.deleted.message')
	   }
	   if(flag){
		   saveNotes(params.roleId,params.deleteIds,0)
	   }
	   render msg
    }
	
	/**
	 *@Description 弹出对话框 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-10-29 上午8:03:18
	 */
	def dialog(){
		render (view:'/cn/com/wz/parent/system/roleUser/selectUser',model:params)	
	}
	/**
	 *@Description 获取用户操作，写入操作日志
	 *@param roleId 选角色的id；userIds要操作的用户ids；type 操作类型 1为添加 0为删除
	 *@return
	 *@Auther liucj
	 *@createTime 2013-3-28 上午11:40
	 */
	def saveNotes(roleId,userIds,type){
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def role=Role.get(roleId).roleNameC
		def	users=userIds.split('_')
		def userNames=''
			users.each{u->
				userNames+=User.get(u).userDetail?.realName+'_'+User.get(u).userName+","
			}
		//组装操作内容
		def operat=message(code:'roleUser.dele.lable',args:["$role","$userNames"])
		if(type){
			operat=message(code:'roleUser.save.lable',args:["$role","$userNames"])
		}
		//写入日志
		logService.insertLog(user.id, "role", operat,'businessLog')
	}
}
