package cn.com.wz.parent.cms
/**
 *@Description 文章栏目相关处理service类 
 *@Auther huxx
 *@createTime 2012-11-3 上午8:16:22
 */
class ArticleCategoryService {

    def serviceMethod() {

    }
	
	/**
	 *@Description 获取所有分类树，并根据节点级别缩进
	 *@param
	 *@return
	 *@Auther huxx
	 *@createTime 2012-10-30 下午4:53:08
	 */
	def getCategoryTree(){
		def resultList=[]
		//获取根目录节点
		//def rootList=ArticleCategory.findAllByParentIds('')
		def rootList=ArticleCategory.findAllByParentIsNull()
		//获取根节点下的子节点
		rootList?.each{a->
			resultList.add(a)
			def subCategory=getSubCategory(a.id,0)
			resultList.addAll(subCategory)
		}
		resultList
	}
	
	/**
	 *@Description 获取子节点信息
	 *@param parentId父节点的id，parentLevel父节点的级别
	 *@return
	 *@Auther huxx
	 *@createTime 2012-10-30 下午4:54:01
	 */
	def getSubCategory(String parentId,int parentLevel){
		def result=[]
		//根据节点级别设置名称缩进前缀
		parentLevel+=1
		def prefix=''
		for(int i=0;i<parentLevel;i++){
			prefix+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
		}
		
		//获取指定节点的子节点，并设置缩进前缀
		def children=ArticleCategory.get(parentId).children
		children?.each {c->
			ArticleCategory d=new ArticleCategory()
			d.id=c.id
			d.categoryNameC=prefix+c.categoryNameC
			result.add(d)
			result+=getSubCategory(c.id,parentLevel)
		}
		return result
	}
}
