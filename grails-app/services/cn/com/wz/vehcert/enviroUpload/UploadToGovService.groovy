package cn.com.wz.vehcert.enviroUpload

import Enviro.WSXxgkVinLocator
import Enviro.WSXxgkVinSoap12Stub
import cn.com.wz.vehcert.environment.EnvirUpload

/**
 * @Description调用环保部提供的websService将环保请单数据上传到环保部
 * @CreateTime 2017-03-05 by zhuwei
 */

class UploadToGovService {
    /**
     *@Description传入企业编号和VIN上传密码，获取上传数据的验证码联通本次连接
     * @param manufid 企业ID
     * @param password VIN上传密码
     * @return返回获取的Key
     * */

     def enviroLogin(){
        def key
        def manufid = '14390443-7'
        def password = '3B85HNmjWQnXgEq5'
        try {
            WSXxgkVinSoap12Stub    service =  new WSXxgkVinLocator().getWSXxgkVinSoap12(new URL("http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx"))
//            调用成功的数据格式
//            <result><succeed>true</succeed><data>IXPPWKPHISCLKIYKFCFFYBSRUOHLCKGY</data></result>
//            调用失败数据格式
//            <result> <succeed>false</succeed>< data />密码错误</ data ></result>
            def keyStr= service.login(manufid,password)
            def succeed = keyStr.split('<succeed>')
            if(succeed.size() ==2){
               def succeed1 = succeed[1].split('</succeed>')
                if (succeed1.size()==2){
                      if (succeed1[0]=='true'){
                          println('获取验证码成功')
                          def keylist = succeed1[1].split('<data>')
                          if(keylist.size()==2){
                              def keylist1 =  keylist[1].split('</data>')
                              if (keylist1.size()==2){
                                  key =  keylist1[0]
                              }

                          }

                      }else{
                          println('获取验证码失败')
                          return '获取验证码失败，请检查！！!'
                      }
                }
            }
//            println(key)
        }catch(Exception e){
            println e.cause?e.cause:e
        }finally{
           return    key
        }
    }

    /**
     * @传入验证码登出本次连接
     * @CreateTime 2017-03-05 by zhuwei
     */
    def enviroLogout(def key){
        def logoutResult
        def logoutMes
        try {
            WSXxgkVinSoap12Stub    service =  new WSXxgkVinLocator().getWSXxgkVinSoap12(new URL("http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx"))
//            调用成功的数据格式
//           <result><succeed>true</succeed><data>成功登出</data></result>
//            调用失败数据格式
//            <result><succeed>false</succeed><data>错误信息</data></result>
            def keyStr= service.logout(key)
            def succeed = keyStr.split('<succeed>')
            if(succeed.size() ==2){
                def succeed1 = succeed[1].split('</succeed>')
                if (succeed1.size()==2){
                    logoutResult= succeed1[0]
                    if (succeed1[0]=='true'){
                        println('成功登出')
                    }else{
                        def logoutList = succeed1[1].split('<data>')
                        if(logoutList.size()==2){
                            def logoutList1 =  logoutList[1].split('</data>')
                            if (logoutList1.size()==2){
                                logoutMes =  logoutList[0]
                                println(logoutMes)
                            }

                        }
                        println('登出失败')
                    }
                }
            }
//            println(logoutResult)
        }catch(Exception e){
            println e.cause?e.cause:e
        }finally{
            return    logoutResult
        }
    }

    /**
     * @Description提交环保随车清单VIN信息
     * @CreateTime 2017-03-05 by zhuwei
     */
    def sendVinData(def key,def lst){
        def result
        def msg
        def map=[:]
        try {
            WSXxgkVinSoap12Stub    service =  new WSXxgkVinLocator().getWSXxgkVinSoap12(new URL("http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx"))
//        vindata数据格式，
//        <vindatas>
//        <vindata><vin>LZ0B9JA36H1000613</vin><xxgkh>CN ZC G5 Z2 0402000045 000002</xxgkh><sb>飞碟</sb><sccdz>山东省五莲县潮河镇驻地</sccdz><fdjh>A3016003911</fdjh><scdate>2017-01-11</scdate><ccdate>2017-01-11</ccdate><ccsy>自由加速烟度实验</ccsy><ccjl>合格</ccjl><gkwww>正在建设中</gkwww><fdjsb>云内</fdjsb><fdjsccdz>昆明市经济技术开发区经景路66号</fdjsccdz></vindata>
//		  <vindata><vin>LZ0B9JA38H1000614</vin><xxgkh>CN ZC G5 Z2 0402000046 000002</xxgkh><sb>飞碟</sb><sccdz>山东省五莲县潮河镇驻地</sccdz><fdjh>A3016004252</fdjh><scdate>2017-01-11</scdate><ccdate>2017-01-11</ccdate><ccsy>自由加速烟度实验</ccsy><ccjl>合格</ccjl><gkwww>正在建设中</gkwww><fdjsb>云内</fdjsb><fdjsccdz>昆明市经济技术开发区经景路66号</fdjsccdz></vindata>
//        </vindatas>
//        先将EnvirUpload数据对象转换成XML格式
            def  vindata = this.convertData(lst)
//        <result><succeed>true</succeed><data>操作成功!接受2条信息!</data></result>
//        处理环保随车请单的上传结果
            def sendDataResult= service.sendVinData(key,vindata)
            println(sendDataResult)
            def succeed = sendDataResult.split("<succeed>")
            if(succeed.size()==2){
                def  succeed1 =  succeed[1].split("</succeed>")
                if(succeed1.size()==2){
                    result = succeed1[0]
                    if (succeed1[0]=='true'){
                        println('报送成功')
                        //报送成功后，将报送成功的数据的上传状态做修改
                        lst.each{l->
                            def enviroInstance = EnvirUpload.findById(l.id)
                            enviroInstance.status= 1
                            enviroInstance.save(flush: true)
                        }
                    }else{
                        //报送失败后，将报送失败的数据的上传状态做修改
                        lst?.each{l->
                            def enviroInstance = EnvirUpload.findById(l.id)
                            enviroInstance.status= 2
                            enviroInstance.save(flush: true)
                        }
                    }

                    def msgList  = succeed1[1].split('<data>')
                    if(msgList.size()==2){
                        def msgList1 =  msgList[1].split('</data>')
                        if (msgList1.size()==2){
                            msg =  msgList1[0]
                            println(msg)
                        }
                    }
                }
            }
        }catch (Exception e){
            println e.cause?e.cause:e
        }finally{
            println(result)
            map.flag=result
            map.msg=msg
            return map
        }
    }

    /**
     * @Description将vin环保随车清单数据转换成字符串格式
     * @CreateTime 2017-03-05 by zhuwei
     */
    def convertData(def enviroLst){
        String vindatas
        def vindata=new StringBuffer("")
        vindata.append("<vindatas>")
        enviroLst.each{enviroInstance->
            //<vindata>标签信息开始
            vindata.append("<vindata>")
            //VIN信息
            if(enviroInstance.en_clsbdh){
                vindata.append("<vin>"+"${enviroInstance.en_clsbdh}"+"</vin>")
            }
            //信息公开号
            if(enviroInstance.en_xxgkbh){
                vindata.append("<xxgkh>"+"${enviroInstance.en_xxgkbh}"+"</xxgkh>")
            }
            //商标
           if(enviroInstance.en_sb){
               vindata.append("<sb>"+"${enviroInstance.en_sb}"+"</sb>")
           }
            //生产厂地址
            if(enviroInstance.en_sccdz){
                vindata.append("<sccdz>"+"${enviroInstance.en_sccdz}"+"</sccdz>")
            }
            //发动机号
            if(enviroInstance.en_fdjh){
                vindata.append("<fdjh>"+"${enviroInstance.en_fdjh}"+"</fdjh>")
            }
            //生产日期
            if(enviroInstance.en_scrq){
                vindata.append("<scdate>"+"${enviroInstance.en_scrq}"+"</scdate>")
            }
            //出厂日期
            if(enviroInstance.en_ccrq){
                vindata.append("<ccdate>"+"${enviroInstance.en_ccrq}"+"</ccdate>")
            }
            //出厂试验
            if(enviroInstance.en_ccsy){
                vindata.append("<ccsy>"+"${enviroInstance.en_ccsy}"+"</ccsy>")
            }
            //出厂结论
            if(enviroInstance.en_ccjl){
                vindata.append("<ccjl>"+"${enviroInstance.en_ccjl}"+"</ccjl>")
            }
            //官方网站
            if(enviroInstance.en_gfwz){
                vindata.append("<gkwww>"+"${enviroInstance.en_gfwz}"+"</gkwww>")
            }
            //发动机商标
            if(enviroInstance.en_fdjsb){
                vindata.append("<fdjsb>"+"${enviroInstance.en_fdjsb}"+"</fdjsb>")
            }
            //发动机生产地址
            if(enviroInstance.en_fdjsccdz){
                vindata.append("<fdjsccdz>"+"${enviroInstance.en_fdjsccdz}"+"</fdjsccdz>")
            }
            vindata.append("</vindata>")
        }
        //<vindata>标签信息结束
        vindata.append("</vindatas>")
        vindatas = vindata.toString()
        return  vindatas
    }

    /**
     * @Description根据车架号删除已经上报的VIN数据
     * @CreateTime 2017-03-06 by zhuwei
     */
    def delData(def key,def vin){
        def result
        def msg
        def map=[:]
        try {
            WSXxgkVinSoap12Stub    service =  new WSXxgkVinLocator().getWSXxgkVinSoap12(new URL("http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx"))
//          <result><succeed>true</succeed><data>2</data></result>
            def delResult= service.delData(key,vin)
            println(delResult)
            def succeed = delResult.split("<succeed>")
            if(succeed.size()==2){
                def  succeed1 =  succeed[1].split("</succeed>")
                if(succeed1.size()==2){
                    result = succeed1[0]
                    if (succeed1[0]=='true'){
                        println('删除成功')
                        //报送成功后，将报送成功的数据的直接物理删除
                        def enviroInstance = EnvirUpload.findByEn_clsbdh(vin)
                        enviroInstance.status= 1
                        enviroInstance.delete(flush: true)
                        msg = "删除成功"
                    }else{
                        //报送失败后，将报送失败的数据的上传状态做修改
                        println("删除失败")
                        def msgList  = succeed1[1].split('<data>')
                        if(msgList.size()==2){
                            def msgList1 =  msgList[1].split('</data>')
                            if (msgList1.size()==2){
                                msg =  msgList1[0]
                                println(msg)
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            println e.cause?e.cause:e
        }finally{
            println(result)
            map.flag=result
            map.msg=msg
            return map
        }
    }
}
