package cn.com.wz.vehcert.printpw

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.Preference
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException;


class PrintPwController extends BaseController {

    /**
     * @Descritpion 设置打印密码页面
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def print(){
        def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def preference=Preference.findByUser(u)
        if(preference){
            render(view:"/cn/com/wz/vehcert/printpw/printNew",model:[preference:preference,flag:1])
        }else{
            render(view:"/cn/com/wz/vehcert/printpw/printNew",model:[flag:0])
        }
    }
    /**
     * @Descritpion 设置打印密码
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def savePrint(){
        def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def user=User.get(u.id)
        def preference=Preference.findByUser(user)
        print params
        def isOr='success'
        if (preference){
            print preference.preValue
            print  params.oldPwd.encodeAsPassword()
            if (preference.preValue!=params.oldPwd.encodeAsPassword()){
                isOr ='noExit'
            }else if (user.password==params.preValue.encodeAsPassword()){
                 isOr ='old'
            }else{
                preference.preValue=params.preValue.encodeAsPassword()
                preference.lastUpdateTime=new Date()
                if(!preference.save(flush: true)){
                    isOr='error'
                }
            }
        }else{
            if (user.password==params.preValue.encodeAsPassword()){
                isOr ='old'
            }else{
                preference=new Preference()
                preference.preName='二级密码'
                preference.preValue=params.preValue.encodeAsPassword()
                preference.code='password'
                preference.showOrder=5
                preference.user=user
                preference.createTime=new Date()
                preference.lastUpdateTime=new Date()
                preference.preDesc='打印密码'
                if(!preference.save(flush: true)){
                    isOr='error'
                }
            }

        }
        render isOr
    }
    /**
     * @Descritpion 管理员进行密码重置
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def resetPassword(){
        def ids = params.ids
        def idsArray = ids.split('_')
        def flag = true
        idsArray.each {id->
            def preference =Preference.get(id)
            if (!preference) {
                return
            }
            try {
                preference.preValue="666666".encodeAsPassword()
                preference.save(flush: true)
            }catch (DataIntegrityViolationException e) {
                flag = false
            }
        }
        render flag
    }
    /**
     * @Descritpion 管理员查看所有业务员的二级密码
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def pwdList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def cel={
           user{
               userDetail{
                   if(params.tf_realName){
                       like("realName","%${params.tf_realName}%")
                   }
               }
               if(params.tf_userName){
                   like("userName","%${params.tf_userName}%")
               }
           }
        }
        def preference=Preference.createCriteria().list(params,cel)
         def lst=[]
        preference.each{p->
            def m=[:]
            m.id=p.id
            m.userName=p.user.userName
            m.realName=p.user.userDetail.realName
            m.preName=p.preName
            lst.add(m)
        }
        def result = [rows:lst,total:preference.totalCount]
        render result as JSON
    }
    /**
     * @Descritpion 管理员查看所有业务员的二级密码
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def index(){

      render(view:"/cn/com/wz/vehcert/printpw/printList")

    }
    /**
     * @Descritpion 设置打印密码页面
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def isSet(){
        def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def preference=Preference.findByUser(u)
        def flag=true
        if(preference){
             flag = true
        }else{
            flag = false
        }
        render flag
    }
    /**
     * @Descritpion 设置打印密码页面
     * @params
     * @return
     * @create liuly 2013-09-02
     */
     def setPrint(){
         render(view:"/cn/com/wz/vehcert/printpw/setPrint",model: [tabName:params.tabName,type:params.type])
     }
    /**
     * @Descritpion 查看输入的密码是否正确
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def printRight(){
        def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def preference=Preference.findByUser(u)
        def flag='success'
        if (preference){
            if(preference.preValue==params.preValue.encodeAsPassword()){
                session.setAttribute("printTime",new Date())
                flag ='success'
            }else{
                flag = 'notRight'
            }
        }else{
            flag = 'notSet'
        }

        render flag
    }
    /**
     * @Descritpion 查看输入的密码是否正确
     * @params
     * @return
     * @create liuly 2013-09-02
     */
    def timeOrPrint(){
        def u=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def flag=true
        def preference=Preference.findByUser(u)
        if(preference){
            //获取上次输入打印密码的时间
            def printTime=session.getAttribute('printTime')
            print printTime
            if (printTime){
                //当前时间
                def nowTime=new Date()
                def difM=0
                //字典值里面的打印有效时间段
                DictionaryValue time=DictionaryValue.findByCode('printTime')
                //上次输入时间和当前时间的小时差值
                long live=nowTime.getTime()-printTime.getTime()
                long hour=live/(60*60*1000)
                long min =live/(60*1000)-hour*60
                long sec =live/1000-hour*60*60-min*60
                print min
                if (min>Long.parseLong(time.value1)){
                    flag=false
                }
            }else{
                flag = false
            }
        }else{
            flag = false
        }
        render flag
    }
}
