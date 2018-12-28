package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User
import grails.converters.JSON

import java.text.SimpleDateFormat;


class GarbageConfigController extends BaseController {

    def index() {
        render(view: "/cn/com/wz/vehcert/zcinfo/garbageStationInfo/garbageStationConfig/Garbage_configList",model:[params:params])
    }
    /**
     * @Description从字典里取到农装生产车间取到的配置项字典：发动机号、合格证编号、超标车出场日期
     * @CreateTime   2014-10-30
     * @Author zhuwei
    * */

   def findConfigDicValue(){
       params.max = params.limit ? params.int('limit') : 10
       params.offset = params.start ? params.int('start') : 0
       def cel = {
           inList("code", ['mainMc_config','Zxbz_config','garbagehgzbh_config'])
       }
       //设置排序参数
       params.sort='code'
       params.order='asc'
       def results = DictionaryValue.createCriteria().list(params,cel)
       def result = [rows: results, total: results.totalCount]
       render result as JSON
    }
//    /**
//     * @Description 编辑
//     * @return
//     */
    def edit() {
        def dictionaryValueInstance = DictionaryValue.get(params.id)
        if (!dictionaryValueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dictionaryValue.label', default: 'DictionaryValue'), params.id])
            redirect(action: "list")
            return
        }
        render(view:'/cn/com/wz/parent/system/dictionary/dictionaryValue/edit',model:[dictionaryValueInstance: dictionaryValueInstance])
    }

    def update() {
        def flag='success'
        def msg="处理成功！"
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(DateUtil.getCurrentTime());
        try{

            def updates=params.updates?.split(":")
            if(updates && updates[0]){
                for(int i=0;i<updates.size();i++){
                    def items=updates[i].split("#")
                    def Dic=DictionaryValue.findByCode(items[0])
                    if(Dic){
                        Dic.lastUpdated=date
                        Dic.lastUpdatedBy=loginUser?.userName
                        if(items.size()>1){
                            Dic.value1=items[1]
                        }else{
                            Dic.value1=''
                        }

                        if(Dic.save(flash:true)){

                        }else{
                            throw new Throwable("更新失败！")
                        }
                    }
                }
            }


        }catch (Exception e){
            flag="failed"
            msg=e.cause?e.cause:e
        }finally{
            def result= [flag:flag,msg:msg]
            render result as JSON
        }
    }
 }
