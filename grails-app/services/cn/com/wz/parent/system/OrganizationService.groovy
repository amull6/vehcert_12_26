package cn.com.wz.parent.system

import cn.com.wz.parent.system.organization.Organization;

class OrganizationService {

	/**
	 * @description 接受一个组织结构的id，返回字符串类型的该组织的父子关系，如：信息技术部-应用系统科
	 * @author 王涛
     * @update huxx 2013-06-06 取消了顶级组织的显示
	 */
	def getParentOrganNameC(organId){
		def organ = Organization.get(organId)
		def parentNameC=''
		def parent=organ.parent
		if(parent){
            if(parent.parent){
                parentNameC=getParentOrganNameC(parent.id)+parent.organNameC+"-"+parentNameC
            }else{
                //对顶级组织的处理
                //parentNameC=parent.organNameC+"-"+parentNameC
            }
		}
		if(parentNameC==null||parentNameC==''){
			parentNameC=''
		}
		return parentNameC
	}
	/**
	 * @param organId:节点的id
	 * @description 获取节点下的所有子孙节点
	 */
	def getSubOrgan(organId){
		def parent = Organization.get(organId)
		def children=parent?.childs
		children?.each {
			children += getSubOrgan(it.id)
		}
		children
	}
	def getParentOrganIds(organId){
		def organ = Organization.get(organId)
		def parentIds=''
		def parent=organ.parent
		if(parent){
			if(parent.parent){
				parentIds=getParentOrganIds(parent.id)+parent.id+"-"+parentIds
			}else{
				
					parentIds=getParentOrganIds(parent.id)+parent.id+"-"+parentIds
				
			}
		}
		if(parentIds==null||parentIds==''){
			parentIds=''
		}
		return parentIds
	}
}
