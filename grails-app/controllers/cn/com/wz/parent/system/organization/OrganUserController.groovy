package cn.com.wz.parent.system.organization
import cn.com.wz.parent.base.BaseController;
import grails.converters.JSON

import org.springframework.dao.DataIntegrityViolationException

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.role.Role
import cn.com.wz.parent.system.user.User
/**
 *@Description 组织用户维护控制器 
 *@Auther huxx
 *@createTime 2012-9-19 上午10:29:58
 *@update 2012-10-30 将employee修改user
 */
class OrganUserController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def logService
	def organizationService
	/**
	 *@Description 跳转到组织结构关系页面 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-19 上午10:56:10
	 */
    def index() {
        render (view:'/cn/com/wz/parent/system/organUser/index')
    }

	/**
	 *@Description 跳转到组织用户关系添加页面 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-19 下午2:48:51
	 */
    def create() {
       render (view:'/cn/com/wz/parent/system/organUser/addOrganUser')
    }

	/**
	 *@Description 用于生成表格数据   获取的指定组织结构下的全部人员信息
	 *@param params.organId 选中组织结构的id ;params.opFlag 0获取不属于指定组织的用户信息，1获取指定组织下的用户信息
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-20 上午8:26:26
	 *@update 2012-11-17 huxx	修改了一个用户只能属于一个组织的问题
	 */
	def selectEmpByOrgan(){
		//设置分页信息
		params.max = params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		def rows=[]
		def role
		if(params.roleId){
			role=Role.get(params.roleId)
		}
		if('1'.equals(params.opFlag)){
			def cel={
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
						if(params.organId){
							organs{
								eq('id',params.organId)
							}
						}
						eq('status',true)
						//下边是公共类选择联系人的节点id
						if(params.nodeId){
							organs {
								eq('id',params.nodeId)
							}
						}
						
					}
			rows=User.createCriteria().list(params,cel)
			
		}else if('0'.equals(params.opFlag)){//获取不属于选中组织的用户
		
			//获取组织下的用户
			def organ=Organization.get(params.organId)
			def ids=[]
			organ?.users?.each {e->
				ids.add(e.id)
			}
			//处理id为空的情况。in的括号中不能没有内容那个
			if(ids.size()<1){
				ids.add('就不相信你存在')
			}
			//查询不输入当前组织的用户
			def cel1={
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
			rows=User.createCriteria().list(params,cel1)
		}else{
			println  '操作参数错误！'
		}
		//给user的realName和userTypeName赋值
		def all=[]
		rows?.each {u->
			def flag=true
			if(role!=null){
				role.users.each {
					if(it.userName==u.userName){
						flag=false
					}
				}
			}
			if(flag==true){
				def m=[:]
				m.id=u.id
				m.realName=u.userDetail?.realName
				m.mPhone=u.userDetail?.mPhone
				m.phoneCode=u.userDetail?.phoneCode
				m.phone=u.userDetail?.phone
				m.eMail=u.userDetail?.eMail
				m.birthDay=u.userDetail?.birthDay
				m.address=u.userDetail?.address
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
				m.receiverType="systemUser"
				all.add(m)
			}
			
		}
		
		def result = [rows: all, total: rows.totalCount]
		render result as JSON
	}	/**
	 *@Description 用于生成表格数据   获取的指定组织结构及其子孙组织下的全部人员信息
	 *@param params.nodeId 选中组织结构的id 
	 *@return
	 *@Auther huxx
	 *@createTime 2012-9-20 上午8:26:26
	 *@update 2012-11-17 huxx	修改了一个用户只能属于一个组织的问题
	 */
	def selectAllUserByOrgan(){
		//设置分页信息
		params.max = params.limit ? params.int('limit') : 10
		params.offset = params.start ? params.int('start') : 0
		def rows=[]
		def role
		if(params.roleId){
			role=Role.get(params.roleId)
		}
		def organIds=[]
			organIds.add(params.nodeId)
		def childOrgan=organizationService.getSubOrgan(params.nodeId)
		if(childOrgan){
			childOrgan?.each{
				organIds.add(it.id)
				}
			}
			def cel={
						if(params.userName){
							like("userName", "%${params.userName}%")
						}
						if(params.realName){
							userDetail{
								like("realName", "%${params.realName}%")
							}
						}
						if(params.post){
							posts{
								like('postNameC',"%${params.post}%")
							}
						}
						if(params.mPhone){
							userDetail{
								like("mPhone", "%${params.mPhone}%")
							}
						}
						if(params.phoneCode){
							userDetail{
								like("phoneCode", "%${params.phoneCode}%")
							}
							
						}
						userDetail{
							order ("realName",'asc')
						}
						if(params.nodeId){
							organs{
								inList("id",organIds)
							}
						}
						eq('status',true)
//						//下边是公共类选择联系人的节点id
//						if(params.nodeId){
//							organs {
//								eq('id',params.nodeId)
//							}
//						}
					}
			rows=User.createCriteria().list(params,cel)
		//给user的realName和userTypeName赋值
		def all=[]
		rows?.each {u->
			def flag=true
			if(role!=null){
				role.users.each {
					if(it.userName==u.userName){
						flag=false
					}
				}
			}
			if(flag==true){
				def m=[:]
				m.id=u.id
				m.realName=u.userDetail?.realName
				m.mPhone=u.userDetail?.mPhone
				m.phoneCode=u.userDetail?.phoneCode
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
				m.receiverType="systemUser"
				all.add(m)
			}
			
		}
		
		def result = [rows: all, total: rows.totalCount]
		render result as JSON
	}
	/**
	 *@Description 保存组织用户关系 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-19 下午3:28:08
	 */
    def save() {
		
		//获取要关联的记录的ids
		def userIds=params.userIds
		def userIdArr=userIds.split('_');
		
		//定义操作标识
		def flag=true
		//获取组织结构信息
		def organ=Organization.get(params.organId)
		//将组织用户关系保存到数据库
		userIdArr.each {userId->
			try{
				def user=User.get(userId)
				user?.addToOrgans(organ)?.save(flash:true)//user和organ存在的情况下两种方式是等价的
			}catch(DataIntegrityViolationException e){
				flag=false
			}
		}
		//设置组织用户关系保存信息
		if(flag){
			saveNotes(params.organId,params.userIds,1)
			render message(code: 'default.save.success.message',default:'Success')
		}else{
			render message(code: 'default.save.fail.message',default:'failed')
		}
    }

    

	/**
	 *@Description 使用ajax删除组织用户关系
	 *@param params.organId 选中组织结构的id；params.deleteIds要删除的用户ids
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-9-20 下午10:02:31
	 */
    def jsonDelete() {
		def msg = message(code: 'default.deleted.simple.message')
      
	   def arrayIds=params.deleteIds?.split('_')
	   def flag=true
	   def organ=Organization.get(params.organId)
	   for(userId in arrayIds){
			   		//获取用户及其与组织的关联（不能用get获取，那样获取不到所属的组织信息）
				   def user=User.createCriteria().list({eq('id',userId) organs{eq('id',params.organId)}})
				   user=user?.get(0)
				try{
					organ.removeFromUsers(user)
				}catch(DataIntegrityViolationException e){
						flag=false
				}
	   }
   		   
	   if(!flag){
		   msg = message(code: 'default.not.simple.deleted.message')
	   }
	   if(flag){
		   saveNotes(params.organId,params.deleteIds,0)
	   }
	   render msg
    }
	/**
	 *@Description 获取用户操作，写入操作日志
	 *@param organId 选中组织结构的id；userIds要操作的用户ids；type 操作类型 1为添加 0为删除
	 *@return 
	 *@Auther liucj
	 *@createTime 2013-3-28 上午11:40
	 */
	def saveNotes(organId,userIds,type){
		//获取当前登录用户
		def user=session.getAttribute(ConstantsUtil.LOGIN_USER)
		def organ=Organization.get(organId).organNameC
		def	users=userIds.split('_')
		def userNames=''
			users.each{u->
				userNames+=User.get(u).userDetail?.realName+'_'+User.get(u).userName+","
			}
		//组装操作内容
		def operat=message(code:'organUser.dele.lable',args:["$organ","$userNames"])
		if(type){
			operat=message(code:'organUser.save.lable',args:["$organ","$userNames"])
		}
		//写入日志
		logService.insertLog(user.id, "organization", operat,'businessLog')
	}
}
