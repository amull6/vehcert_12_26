package cn.com.wz.parent.cms

import grails.converters.JSON;

import org.apache.commons.lang.time.DateUtils;

/**
 *@Description 文章处理services类
 *@Auther huxx
 *@createTime 2012-12-17 下午2:50:35
 */
class ArticleService {

    def serviceMethod() {

    }
	/**
	 *@Description 首页获取最新通知公告 、新闻、企业文化、公司制度、员工文苑
	 *@param categoryCode=news获取新闻信息类文章；categoryCode=notes获取通知公告类文章；
	 *categoryCode=qywh 获取企业文化类文章；categoryCode=gszd获取公司制度类文章
	 *categoryCode=ygwy获取员工文苑类文章；categoryCode=itAndPro获取两化融合下推荐的没有图片的文章
	 *@param articleCategoryCode 栏目类型的code
     *@param hasPic 是否有图片
	 *@Auther huxx
	 *@createTime 2012-12-17 下午2:53:23
	 *@update 2013-02-20 huxx 添加了审核条件的限制
	 *@update 2013-03-04 liucj 添加了更多页面查询功能
     *@update 2013-07-05 liucj 添加获取两化融合的文章
	 */
	def getArticles(params){
		def cel={
            articleCategory{
                if(params.articleCategoryCode){
                    if(params.articleCategoryCode=='whxc'){
                        inList('categoryCode',['ftjl','gzgw'])
                    }else{
                        eq('categoryCode',"${params.articleCategoryCode}")
                    }
                }else{
                    if(params.categoryId){
                        eq('id',"${params.categoryId}")
                    }else if(params.categoryCode){
                        categoryType{
                            eq('code',"${params.categoryCode}")
                        }
                    }
                }
            }
            if(params.hasPic){
                isNotNull('picPath')
            }
            if(params.categoryCode=='itAndPro'){
                isNull('picPath')
                eq('recommend',1)
            }
			if(params.titleC){
				like("titleC","%${params.titleC}%")
			}
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
            order("showOrder","desc")
			order('createTime','desc')
		}
		
		
		def lst=Article.createCriteria().list(params,cel)
		return lst
	}
	/**
	 *@Description 点击弹出窗口来获取上次登录时间到现在的最新通知公告 、新闻、企业文化、公司制度、员工文苑
	 *@param categoryCode=news获取新闻信息类文章；categoryCode=notes获取通知公告类文章；
	 *categoryCode=qywh 获取企业文化类文章；categoryCode=gszd获取公司制度类文章
	 *categoryCode=ygwy获取员工文苑类文章
	 *@return
	 *@Auther liuly
	 *@createTime 2013-3-04
	 */
	def getNewTimeArticles(params){
		String newArticleDate=params.lastLoginTime
		def cel={
			articleCategory{
				categoryType{
					eq('code',"notes")
				}
			}
			if(params.titleC){
				like("titleC","%${params.titleC}%")
			}
			if(params.lastLoginTime){
				between('createTime', Date.parse("yyyy-MM-dd HH:mm:ss",newArticleDate),new Date())
			}
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
		return lst
	}

	/**
	 * @Description 获取新通知数量
	 * @param
	 * @return
	 * @Author liuly
	 * @createTime 2013-3-03
	 */
	Integer getNewArticlesNum(params){
		//获取上次登录时间
		String newArticleDate=params.lastLoginTime
		//提取上次登录时间和当前时间之间的通告
		def cel={
			articleCategory{
				categoryType{
					eq('code',"notes")
				}
			}
			if(params.titleC){
				like("titleC","%${params.titleC}%")
			}
			if(params.lastLoginTime){
				between('createTime', Date.parse("yyyy-MM-dd HH:mm:ss",newArticleDate),new Date())
			}
		
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
		def results = Article.createCriteria().count(cel)
		
	}

}
