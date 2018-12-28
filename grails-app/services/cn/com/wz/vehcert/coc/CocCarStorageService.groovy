package cn.com.wz.vehcert.coc

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.MessageUtil
import cn.com.wz.vehcert.carstorage.CarStorage

/**
 * @Description 一致性证书Service
 * @Create 2013-08-11 mike
 */
class CocCarStorageService {
    /**
     * @Description 保存一致性信息
     * @Create mike 2013-08-11
     */
    def saveList(lst,user,time,request,grailsAttributes){
        def failList=[]
        def count=0
        int i=1;
        lst?.each{ pct->
            //清空属性值的前后空格
            pct.entrySet()?.each{
                it.value=it.value?.toString()?.trim()
            }
            //根据pct中值为carStorage的属性赋值，并处理字段为float类型的
            def cocCarStorage=new CocCarStorage()
            pct.each {
                cocCarStorage."${it.key}"=  it.value?(it.value.toString().toUpperCase()=="NULL"?'':it.value):''
            }
            cocCarStorage.coc_creater=user
            cocCarStorage.coc_createTime=time
            if(cocCarStorage.save()){
                count+=1
            }else{
                def errorMessages=""
                def num=1
                //错误信息处理
                cocCarStorage.errors.allErrors?.each{
                    if(it in org.springframework.validation.FieldError){
                        //将对象和属性国际化
                        it.arguments[1]="${MessageUtil.message(code: 'coc.label',request:request,grailsAttributes:grailsAttributes)}"
                        it.arguments[0]="${MessageUtil.message(code:"coc.${ it.arguments[0]}.label",request:request,grailsAttributes:grailsAttributes)}"
                        errorMessages+="${num}、"+MessageUtil.message(error: it,request:request,grailsAttributes:grailsAttributes)+";"
                    }

                }
                pct.errorMessages=errorMessages
                failList.add(pct)
            }
        }
        return [failList:failList,count:count]
    }
}
