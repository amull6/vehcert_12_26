package cn.com.wz.parent.system

import org.springframework.dao.DataIntegrityViolationException;

import parent.poi.ExcelUtils;

import cn.com.wz.parent.system.dictionary.DictionaryValue;
import cn.com.wz.parent.system.organization.Organization;
import cn.com.wz.parent.system.post.Post;
import cn.com.wz.parent.system.role.Role;
import cn.com.wz.parent.system.user.User;
import cn.com.wz.parent.system.user.UserDetail;
import grails.converters.JSON
import java.util.Map;

/**
 *@Description 用户管理逻辑处理类 
 *@Auther huxx
 *@createTime 2012-12-8 下午1:38:26
 */
class UserService {
	
	/**
	 *@Description 根据查询参数获取用户列表
	 *@param 
	 *@return 返回用户列表的json数据
	 *@Auther huxx
	 *@createTime 2012-12-8 下午1:40:57
	 */
	def jsonListByParams(params){
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
		}
		def rows=User.createCriteria().list(params,cel)
		def lst=[]
		//给user的realName和userTypeName赋值
		rows?.each {u->
			def m=[:]
			m.id=u.id
			m.userName=u.userName
			m.tempName=u.userDetail?.realName
			m.userTypeName=u.userType?.dicValueNameC
			m.validStartTime=u.validStartTime
			m.validEndTime=u.validEndTime
			m.lastLoginTime=u.lastLoginTime
			m.status=u.status
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
			lst.add(m)
		}
		def result = [rows:lst, total: rows.totalCount]
		
		return result as JSON
	}

    /**
     *@Description 根据查询参数获取用户列表
     *@param
     *@return 返回满足条件用户列表
     *@Auther huxx
     *@createTime 2013-06-19
     */
    def listByParams(params){
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
        }
        def rows=User.createCriteria().list(params,cel)
        def lst=[]
        //给user的realName和userTypeName赋值
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.userName=u.userName
            m.realName=u.userDetail?.realName
            m.userTypeName=u.userType?.dicValueNameC
            m.validStartTime=u.validStartTime
            m.validEndTime=u.validEndTime
            m.lastLoginTime=u.lastLoginTime
            m.status=u.status
            m.organNames=''
            m.mPhone=u.userDetail?.mPhone
            u.organs?.each {
                m.organNames+=it.organNameC+"、"
            }

            if(m.organNames){
                m.organNames=m.organNames.substring(0,m.organNames.lastIndexOf("、"))
            }
            m.postNames=''
            u.posts?.each {
                m.postNames+=it.postNameC+"、"
            }

            if(m.postNames){
                m.postNames=m.postNames.substring(0,m.postNames.lastIndexOf("、"))
            }
            m.jobLevel=u.userDetail?.jobLevel?.dicValueNameC
            lst.add(m)
        }
        return lst
    }
	/**
	 * @description 获得给定组织的所有子组织
	 * @author 王涛
	 */
	def getSubOrgan(parentId){
		def parent = Organization.get(parentId)
		def allChilds=[]
		allChilds=parent?.childs
		allChilds?.each {m->
			allChilds +=getSubOrgan(m.id)
		}
		return allChilds
	}
	/**
	 *@Description 保存新建信息
	 *@param 
	 *@return 保存成功返回true，保存失败返回false
	 *@Auther huxx
	 *@createTime 2012-12-8 下午1:48:12
	 */
	def save(User user,organsIds,postsIds,rolesIds){
		if(user.save(flush: true)){
			if(organsIds.length()>0){
				def idsArray = organsIds?.split('_')
				idsArray?.each{id->
							def organ=Organization.get(id)
							if(user?.addToOrgans(organ)?.save(flash:true)){
							}else{return false}
					}
			}
			if(postsIds.length()>0){
				def idsArray = postsIds?.split('_')
				idsArray?.each{id->
							def post=Post.get(id)
							if(user?.addToPosts(post)?.save(flash:true)){
							}else{return false}
					}
			}
			if(rolesIds.length()>0){
				def idsArray = rolesIds?.split('_')
				idsArray?.each{id->
							def role=Role.get(id)
							if(user?.addToRoles(role)?.save(flash:true)){
							}else{return false}
					}
			}
			return true
		}else{
			return false
		}
	}
	/**
	 *@Description 保存更新信息 
	 *@param userInstance用户实例；params页面传入的参数
	 *@return 返回1乐观锁锁定；返回2保存失败；返回3保存成功
	 *@Auther huxx
	 *@createTime 2012-12-8 下午1:55:30
	 */
	def update(User userInstance, params){
		//乐观锁判断
		if (params.version) {
			def version = params.version.toLong()
			if (userInstance.version > version) {
				return 1
			}
		}

		//给user赋上新的值
		userInstance.properties = params
		//给userDetail赋上新值
		if(userInstance.userDetail){
			userInstance.userDetail.properties=params
		}else{
			userInstance.userDetail=new UserDetail(params)
		}
		def needDeleteOrgans=[]
		needDeleteOrgans=userInstance?.organs
		needDeleteOrgans?.each{m->
			def organ=Organization.get(m.id)
			if(!userInstance?.removeFromOrgans(organ)){return 2}
			
			}
		
		def needDeletePosts=[]
		needDeletePosts=userInstance?.posts
		needDeletePosts?.each{m->
			def post=Post.get(m.id)
			if(!userInstance?.removeFromPosts(post)){return 2}
			
			}
		
		def needDeleteRoles=[]
		needDeleteRoles=userInstance?.roles
		needDeleteRoles?.each{m->
			def role=Role.get(m.id)
			if(!userInstance?.removeFromRoles(role)){return 2}
			
			}
		
		if (userInstance.save(flush: true)) {
			def organsIds=params.organsIds
			if(organsIds.length()>0){
				
					def idsArray = organsIds?.split('_')
					idsArray?.each{id->
								def organ=Organization.get(id)
								def user=User.get(userInstance.id)
								if(!user?.addToOrgans(organ)?.save(flash:true)){
									return 2
								}
						}
			}
			def postsIds=params.postsIds
			if(postsIds.length()>0){
				
					def idsArray = postsIds?.split('_')
					idsArray?.each{id->
								def post=Post.get(id)
								def user=User.get(userInstance.id)
								if(!user?.addToPosts(post)?.save(flash:true)){
									return 2
								}
						}
			}
			def rolesIds=params.rolesIds
			if(rolesIds.length()>0){
				
					def idsArray = rolesIds?.split('_')
					idsArray?.each{id->
								def role=Role.get(id)
								def user=User.get(userInstance.id)
								if(!user?.addToRoles(role)?.save(flash:true)){
									return 2
								}
						}
			}
			return 3
		}else{
			return 2
		}
	}
	/**
	 *@Description 批量删除数据，并返回删除信息
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-12-8 下午2:10:38
	 */
	def delete(User user){
			if('system'.equals(user.userName)){
				return  false
			}else{
                //取消与组织结构的关联
                def organs=user?.organs
                if(organs){
                    for(Organization o in organs){
                        def organ= Organization.get(o.id)
                        if(!user?.removeFromOrgans(organ)){return false}
                    }
                }
                //取消与角色的关联
                def roles=user?.roles
                if (roles){
                    for(Role r in roles){
                        def role = Role.get(r.id)
                        if (!user?.removeFromRoles(role)){return false}
                    }
                }
                //取消与岗位的关联
                def posts=user?.posts
                if (posts){
                    for(Post p in posts){
                        def post=Post.get(p.id)
                        if(!user?.removeFromPosts(post)){return false}
                    }
                }
                //删除用户
                if (!user.delete(flush: true)) {
                    return true
                }  else{
                    return false
                }

            }

	}
	/**
	 *@Description 批量重置用户密码 
	 *@param 
	 *@return 
	 *@Auther huxx
	 *@createTime 2012-12-8 下午2:19:45
	 */
	def resetPassword(params){
		def ids = params.ids
		def idsArray = ids.split('_')
		def flag = true
		def msg ='user.resetPassword.success.message'
		def errorMsg=''
		idsArray.each {id->
			def user =User.get(id)
			if (!user) {
				return
			}
			try {
				user.password="888888".encodeAsPassword()
				user.save(flush: true)
			}catch (DataIntegrityViolationException e) {
				flag = false
			}
			if(!flag){
				msg = 'user.resetPassword.failed.message'
			}
		}
		return msg
	}
    /**
     * @Description  用户信息
     * @param params
     * @return
     * @create 2013-08-29 liuly
     */
    def saveImport(params){
        User user=new User()
        def jobLevel=new DictionaryValue()
        def organ=new Organization()
        def post=new Post()
        //查询岗位级别
        def cel={
            eq('dicValueNameC',"${params.jobLevel}")
        }
        def listD=DictionaryValue.createCriteria().list(cel)
        if (listD&&listD.size()>0){
            jobLevel=listD.get(0)
        }
        //查询组织
        def celO={
            eq('organCode',params.code)
        }
        def listO=Organization.createCriteria().list(celO)
        if (listO&&listO.size()>0){
            organ=listO.get(0)
        }
        //查询岗位
        def celP={
            eq('postNameC',params.postNameC)
        }
        def listP=Post.createCriteria().list(celP)
        if (listP&&listP.size()>0){
            post=listP.get(0)
        }
        def m=[:]
        if (organ&&post&&jobLevel){
            user.status=1
            user.createTime=new Date()
            //设置用户详细信息
            def userDetailInstance= new UserDetail()
            userDetailInstance.realName=params.realName

            userDetailInstance.jobLevel=jobLevel
            user.userDetail=userDetailInstance
            user.userName=params.userName

            user.userType=DictionaryValue.findByCode("regularUser")
            user.password='888888'.encodeAsPassword()
            //保存用户组织关系
            user?.addToOrgans(organ)?.save(flash:true)

            //保存用户角色关系
            def role=Role.findByRoleCode("005")
            user?.addToRoles(role)?.save(flash:true)
            //保存岗位信息
            user?.addToPosts(post)?.save(flash:true)
        }
        if (user.save(flush: true)){
            m.result=true
        }else{
            m.result=false
        }
        m.instance=user
        return m
    }
}
