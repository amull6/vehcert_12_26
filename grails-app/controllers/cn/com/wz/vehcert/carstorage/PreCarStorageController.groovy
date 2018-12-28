package cn.com.wz.vehcert.carstorage

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.system.user.User
import cn.com.wz.parent.base.BaseController
import parent.poi.event.ExcelReaderUtil
import parent.poi.event.IRowReader
import parent.poi.event.RowReader
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.AcroFields
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfImportedPage
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import grails.converters.JSON
import parent.poi.ExcelUtils

/**
 * @Description 公告信息生成controller
 * @create  2013-07-23 huxx
 */
class PreCarStorageController extends BaseController{
    def exportService
    def sqlService
    def preCarStorageService
    /**
     * @Description 进入公告信息生成页面
     * @return
     * @create 2013-07-23 huxx
     */
    def index() {
        render (view:'/cn/com/wz/vehcert/precarstorage/generateCartStorage',model: params)
    }

    /**
     * @Description 根据条件查询合格证信息
     * @Params searchFlag=searchByClxh根据车辆型号查询；searchFlag=searchByDpxh根据车辆底盘型号查询
     * @return
     */
    def search(){
        def cartStorage=null
        if ("searchByClxh".equals(params.searchFlag)){
           cartStorage=PreCarStorage.findByVeh_Clxh(params.veh_Clxh)
        }else if ("searchByDpxh".equals(params.searchFlag)){
            def cel={
                eq('veh_Dpxh',"${params.veh_Dpxh}")
                isNull('veh_Clxh')
            }
            def list=PreCarStorage.createCriteria().list(cel)
            if (list && list.size()>0){
                cartStorage=list.get(0)
            }
        }
        render (view:'/cn/com/wz/vehcert/precarstorage/generateCartStorage',model: [cartStorage:cartStorage])
    }
    /**
     * @Description 根据生成信息分解为基础公告信息
     * @Create 2013-07-24 huxx
     * @Update 2013-11-12 huxx 修改生成时，如果 总质量=额定载质量+整备质量+准乘人数*65有为空时，报错
     * @Update 2013-11-18 huxx 修改公式 总质量=额定载质量+整备质量+准乘人数*65    只适用于整车，不适用底盘
     * @Update 2013-11-22 huxx 修改底盘分解时，总质量=额定载质量+整备质量+准乘人数 分开分解
     * @Update 2013-11-26 huxx 修改车辆识别代号不参与分解
     * @Update 2014-03-03 huxx 对发动机型号、功率、排量、最大净功率进行特殊处理（四者是一对一的关系
     */
    def generateCarStorage(){
        session.removeAttribute("compFlag")
        def result=[]
        try{
            def noNeedRank=['veh_Clzzqymc','veh_Cpggh','veh_Ggsxrq','veh_Ggpc','veh_cpid','veh_Qyid','veh_Clfl','veh_Clmc',
                    'veh_Clxh','veh_Dpxh','veh_Dpid','veh_Bz','veh_Qyqtxx','veh_Pfbz','veh_y45pic','veh_zhpic','veh_fhpic','veh_pic1',
                    'veh_pic2','veh_other','createTime','createrId','updateTime','updaterId','veh_clsbdh']
            def noNeed=['veh_Fdjxh','veh_Pl','veh_Gl','veh_Zzl','veh_Edzzl','veh_Zbzl','veh_Jsszcrs','veh_Wkc','veh_qxhx','veh_Zj','veh_zdjgl']
            PreCarStorage pcs=new PreCarStorage(params)

            //对发动机型号、功率、排量、最大净功率进行特殊处理（四者是一对一的关系）
            def fdjxh=pcs.veh_Fdjxh?.split(";")
            def gl=pcs.veh_Gl?.split(";")
            def pl=pcs.veh_Pl?.split(";")
            def jgl=pcs.veh_zdjgl?.split(";")
            if (fdjxh && fdjxh?.size()==gl?.size() && gl?.size()==pl?.size() && pl?.size()==jgl?.size()){
                for(int i=0;i<fdjxh.size();i++){
                    def m=[:]
                    m.veh_Fdjxh=fdjxh[i]
                    m.veh_Gl= gl[i]?gl[i]:''
                    m.veh_Pl= pl[i]?pl[i]:''
                    m.veh_zdjgl=jgl[i]?jgl[i]:''
                    result.add(m)
                }
            }

            //判断车辆型号是否为空，如果为空就设为底盘型号
            if (!pcs.veh_Clxh){
                pcs.veh_Clxh=pcs.veh_Dpxh
            }

            //处理公式为：总质量=额定载质量+整备质量+准乘人数*65    只适用于整车，不适用底盘
            if (pcs.veh_Clztxx?.equals("QX")){
                def edzzl=pcs.veh_Edzzl?.split(",")
                def zbzl=pcs.veh_Zbzl?.split(",")
                def zcrs=pcs.veh_Jsszcrs?.split(",")
                def zzl=pcs.veh_Zzl?.split(",")
                if (edzzl && zbzl && zcrs && zzl){
                    def list=[]
                    def count=0
                    zzl?.each{zz->
                        edzzl?.each{ed->
                            zbzl?.each{zb->
                                zcrs?.each{zc->
                                    //处理准乘人数为2+3格式的情况
                                    def t1=zc?.split("\\+")
                                    int t2=0
                                    t1?.each{
                                        t2+=Integer.parseInt(it)
                                    }

                                    def left=Float.parseFloat(zz)
                                    def right=Float.parseFloat(ed)+Float.parseFloat(zb)+t2*65

                                    if(left ==right){
                                        def temp=[]
                                        result.each{
                                            def m=[:]
                                            it.entrySet()?.each{
                                                m.put(it.key,it.value)
                                            }
                                            temp.add(m)
                                        }
                                        addToList(temp,"veh_Zzl","${zz?zz:''}")
                                        addToList(temp,"veh_Edzzl","${ed?ed:''}")
                                        addToList(temp,"veh_Zbzl","${zb?zb:''}")
                                        addToList(temp,"veh_Jsszcrs","${zc?zc:''}")
                                        list.addAll(temp.collect())
                                        count+=1
                                    }
                                }
                            }

                        }
                    }
                    //如果没有一条符合符合公式就返回给空表
                    if (count){
                        result.clear()
                        result.addAll(list.collect())
                        list.clear()
                    }else{
                        render (view:'/cn/com/wz/vehcert/precarstorage/generateCartStorage',model: [cartStorage:params,dialogId:params.dialogId,returnMessage:'因没有满足公式【总质量=额定质量+整备质量+准乘人数*65】的记录，生成操作终止,请重新检查公告信息！'])
                        return
                    }
                }else{
                    render (view:'/cn/com/wz/vehcert/precarstorage/generateCartStorage',model: [cartStorage:params,dialogId:params.dialogId,returnMessage:'因没有满足公式【总质量=额定质量+整备质量+准乘人数*65】的记录，生成操作终止,请重新检查公告信息！'])
                    return
                }
            }else{
                def lst=["veh_Edzzl":pcs.veh_Edzzl,"veh_Zbzl":pcs.veh_Zbzl,"veh_Jsszcrs":pcs.veh_Jsszcrs,"veh_Zzl":pcs.veh_Zzl]
               lst.entrySet()?.each {p->
                   if (p.value){
                       def allList=[]
                       //分解属性并将分解后的属性与result中的值进行组合
                       def pArr=p.value?.toString()?.split(',')
                       pArr?.collect()?.each {pa->
                           //使用临时变量temp保存result的值 ,因为值是map不可以使用地址引用调用
                           def temp=[]
                           result.each{
                               def m=[:]
                               it.entrySet()?.each{
                                   m.put(it.key,it.value)
                               }
                               temp.add(m)
                           }
                           addToList(temp,p.key,"${pa?pa:''}")
                           allList.addAll(temp.collect())
                       }
                       result = allList
                   }
               }


//                addToList(result,"veh_Edzzl","${pcs.veh_Edzzl?pcs.veh_Edzzl:''}")
//                addToList(result,"veh_Zbzl","${pcs.veh_Zbzl?pcs.veh_Zbzl:''}")
//                addToList(result,"veh_Jsszcrs","${pcs.veh_Jsszcrs?pcs.veh_Jsszcrs:''}")
//                addToList(result,"veh_Zzl","${pcs.veh_Zzl?pcs.veh_Zzl:''}")
            }


            //处理公式为：外廓长=前悬+后悬+轴距
            def qxhx=pcs.veh_qxhx?.split(",")
            def zj=pcs.veh_Zj?.split(",")
            def wkc=pcs.veh_Wkc?.split(",")
            //只有当三个值都存在时才进行公式的计算  ,否则不参与组合计算
            if (qxhx && zj && wkc){
                def list1=[]
                def count=0
                wkc?.each{wk->
                    zj?.each{z->
                        //处理轴距为2+3格式的情况
                        def t1=z?.split('\\+')
                        Float t2=0
                        t1?.each{
                            t2+=Float.parseFloat(it)
                        }
                        qxhx?.each{qh->
                            def qhList=qh?.split("/")
                            if (qhList?.size()==2){
                                def left=Float.parseFloat(wk)
                                def right=Float.parseFloat(qhList[0])+Float.parseFloat(qhList[1])+t2
                                if (left ==right){

                                    def temp=[]
                                    result.each{
                                        def m=[:]
                                        it.entrySet()?.each{
                                            m.put(it.key,it.value)
                                        }
                                        temp.add(m)
                                    }
                                    addToList(temp,"veh_Wkc","${wk?wk:''}")
                                    addToList(temp,"veh_qxhx","${qh?qh:''}")
                                    addToList(temp,"veh_Zj","${z?z:''}")
                                    list1.addAll(temp.collect())
                                    count+=1
                                }
                            }

                        }
                    }
                }

                //如果没有一条符合符合公式就返回给空表
                if (count){
                    result.clear()
                    result.addAll(list1.collect())
                    list1.clear()
                }else{
                    render (view:'/cn/com/wz/vehcert/precarstorage/generateCartStorage',model: [cartStorage:params,dialogId:params.dialogId,returnMessage:'因没有满足公式【外廓长=前悬+后悬+轴距】的记录，生成操作终止,请重新检查公告信息！'])
                    return
                }

            }else{
                render (view:'/cn/com/wz/vehcert/precarstorage/generateCartStorage',model: [cartStorage:params,dialogId:params.dialogId,returnMessage:'因没有满足公式【外廓长=前悬+后悬+轴距】的记录，生成操作终止,请重新检查公告信息！'])
                return
            }

            pcs.properties?.each{p->
                //排除发动机型号、功率、排量
                if (noNeed.contains(p.key)){

                }else{
                    if (noNeedRank.contains(p.key)||!p.value){//如果属性不需分解或者属性值为空，只需将属性加入result的每一个元素中。
                        addToList(result,p.key,"${p.value?p.value:''}")
                    }else{
                        if (p.value){
                            def allList=[]
                            //分解属性并将分解后的属性与result中的值进行组合
                            def pArr=p.value?.toString()?.split(',')
                            pArr?.collect()?.each {pa->
                                //使用临时变量temp保存result的值 ,因为值是map不可以使用地址引用调用
                                def temp=[]
                                result.each{
                                    def m=[:]
                                    it.entrySet()?.each{
                                        m.put(it.key,it.value)
                                    }
                                    temp.add(m)
                                }
                                addToList(temp,p.key,"${pa?pa:''}")
                                allList.addAll(temp.collect())
                            }
                            result = allList
                        }

                    }
                }
            }
            export(result,params)

        }catch (Exception e){
            e.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
            result.clear()
        }
    }

    /**
     * @Description 将key和value存入到list的每个map中
     * @param list
     * @param key
     * @param value
     * @return
     */
    def addToList(list,key,value){
        if(list){
            list.each{
                it.put(key,value)
            }
        }else{
            def m=[key:value]
            list.add(m)
        }
    }


    /**
     *@Description 从excel中导入用户信息
     *@param  filePath:用户信息文件路径；
     *config_column_map 存储了参数信息。sheet从第几个sheet中取，从0开始；startRow从第几行开始读取，从0开始；
     *columnMap保存了列与实体属性的对应关系，第一列的列号为0.
     *@return
     *@Auther lly
     *@createTime 2013-07-25
     * @update 2013-08-24 huxx 修改了导出方法，使用先导出为xml文件再写入excle的方式进行导出
     */
    def export(list,params){
        if(params?.format && params.format != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=CarStorage.${params.extension}")
//            List fields = ["veh_Clzzqymc","veh_Qyid", "veh_Clfl","VIN4","veh_Clmc","veh_Clpp","veh_Clxh","veh_Csys","veh_Dpxh","veh_Dpid","veh_cpid","veh_Ggpc","veh_qxhx","veh_Fdjxh","veh_Rlzl","veh_Pl","veh_Gl","veh_Zxxs","veh_Qlj","veh_Hlj","veh_Lts","veh_Ltgg","veh_Gbthps","veh_Zj","veh_Zh","veh_Zs","veh_Wkc","veh_Wkk","veh_Wkg","veh_Hxnbc","veh_Hxnbk","veh_Hxnbg","veh_Zzl","veh_Edzzl","veh_Zbzl","veh_Zzllyxs","veh_Zqyzzl","veh_Edzk","veh_Bgcazzdyxzzl","veh_Jsszcrs","veh_Hzdfs","veh_Cpggh","veh_Ggsxrq","veh_jjlqj","veh_dpqy","veh_Zgcs","veh_Yh","veh_Bz","veh_Qybz","veh_Cpscdz","veh_Qyqtxx","veh_Pfbz","veh_Clztxx","veh_Jss","veh_Jsslx","veh_fgbsqy","veh_fgbsxh","veh_fgbssb","veh_y45pic","veh_zhpic","veh_fhpic","veh_pic1","veh_pic2","veh_other",'veh_pzxlh','veh_zdjgl']
            Map labels =  ["veh_Clzzqymc":"车辆制造企业名称","veh_Qyid":"企业ID", "veh_Clfl":"车辆分类","VIN4":"VIN4","veh_Clmc":"车辆名称","veh_Clpp":"车辆品牌","veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_cpid":"产品ID","veh_Ggpc":"批次","veh_qxhx":"前悬后悬","veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类 ","veh_Pl":"排量","veh_Gl":"功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格","veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽","veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量 ","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车按座","veh_Jsszcrs":"驾驶室准乘人数","veh_Hzdfs":"后制动方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期 ","veh_jjlqj":"接近离去角","veh_dpqy":"底盘企业","veh_Zgcs":"最高车速","veh_Yh":"油耗","veh_Bz":"备注","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其他信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息","veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_fgbsqy":"反光标示企业","veh_fgbsxh":"反光标示型号","veh_fgbssb":"反光标示商标","veh_y45pic":"右45度照片","veh_zhpic":"正后照片","veh_fhpic":"防护照片","veh_pic1":"选择照片1","veh_pic2":"选择照片2 ","veh_other":"其它","veh_pzxlh":"配置序列号","veh_zdjgl":"最大净功率","carstorageType":"公告类型 0：汽车类型 1：农用车","veh_clsbdh":"车辆识别代号","veh_cph":"产品号"]
            def out=response.outputStream

            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            def content=[]
            content.add(list)
            excelOp.preWriteExcel(out,null,m,["分解前公告信息"],content)

            session.setAttribute('compFlag',"success")
            content.clear()
            out.flush()
            out.close()
        }
    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }

    /**
     * @Description 进入公告信息页面
     * @return
     */
    def importPage(){
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'preCarStorage',action: 'imp')}",tabId:params.tabId])
    }

    /**
     * @Description 批量导入生成前的公告信息
     * @param filePath
     * @update 修改了上传一致性证书事获取到realPath错误
     * @updateTime 2014-03-05 by zhuwei
     * @return
     */
    def imp(String filePath){
        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取图片存储的相对路径(不包含文件名)
            def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==3){
//                realPath=UploadUtil.getRelativeFile(servletContext, picRelativePath, fileNames[0])
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
            }
        }
        /**
         * 全部以String的方式读取
         */
        List<String> labels=new ArrayList<String>();
        labels.add('veh_Clzzqymc');
        labels.add('veh_Qyid');
        labels.add('veh_Clfl');
        labels.add('veh_Clmc');
        labels.add('veh_Clpp');
        labels.add('veh_Clxh');
        labels.add('veh_Csys');
        labels.add('veh_Dpxh');
        labels.add('veh_Dpid');
        labels.add('veh_cpid');
        labels.add('veh_Ggpc');
        labels.add('veh_qxhx');
        labels.add('veh_Fdjxh');
        labels.add('veh_Rlzl');
        labels.add('veh_Pl');
        labels.add('veh_Gl');
        labels.add('veh_Zxxs');
        labels.add('veh_Qlj');
        labels.add('veh_Hlj');
        labels.add('veh_Lts');
        labels.add('veh_Ltgg');
        labels.add('veh_Gbthps');
        labels.add('veh_Zj');
        labels.add('veh_Zh');
        labels.add('veh_Zs');
        labels.add('veh_Wkc');
        labels.add('veh_Wkk');
        labels.add('veh_Wkg');
        labels.add('veh_Hxnbc');
        labels.add('veh_Hxnbk');
        labels.add('veh_Hxnbg');
        labels.add('veh_Zzl');
        labels.add('veh_Edzzl');
        labels.add('veh_Zbzl');
        labels.add('veh_Zzllyxs');
        labels.add('veh_Zqyzzl');
        labels.add('veh_Edzk');
        labels.add('veh_Bgcazzdyxzzl');
        labels.add('veh_Jsszcrs');
        labels.add('veh_Hzdfs');
        labels.add('veh_Cpggh');
        labels.add('veh_Ggsxrq');
        labels.add('veh_jjlqj');
        labels.add('veh_dpqy');
        labels.add('veh_Zgcs');
        labels.add('veh_Yh');
        labels.add('veh_Bz');
        labels.add('veh_Qybz');
        labels.add('veh_Cpscdz');
        labels.add('veh_Qyqtxx');
        labels.add('veh_Pfbz');
        labels.add('veh_Clztxx');
        labels.add('veh_Jss');
        labels.add('veh_Jsslx');
        labels.add('veh_fgbsqy');
        labels.add('veh_fgbsxh');
        labels.add('veh_fgbssb');
        labels.add('veh_y45pic');
        labels.add('veh_zhpic');
        labels.add('veh_fhpic');
        labels.add('veh_pic1');
        labels.add('veh_pic2');
        labels.add('veh_other');
        labels.add('veh_clsbdh');
        labels.add('veh_cph');
        labels.add('veh_zdjgl');
        def map=[:];
        map.put('startRow',1) ;//从第二行开始
        IRowReader reader = new RowReader(labels,map);
        def lst=[]
        try {
            lst=ExcelReaderUtil.readExcel(reader, realPath);
        } catch (Exception e) {
            e.cause.printStackTrace();
        }
        def totalCount=lst?.size()  //读取记录总数
        def count=0                 //实际存储成功的记录数
        def returnData=""           //返回的第一个存储成功的记录
        def failList=[]             //保存失败记录

        lst?.each{ pct->
            //清空属性值的前后空格
            pct.entrySet()?.each{
                it.value=it.value?.toString()?.trim()
            }
            try{
                pct.userId=getCurrentUser()?.id
                def m=preCarStorageService.save(pct)

                if(m.result){
                    count+=1
                    //记录第一个保存成功的记录
                    if (count==1){
                        if (pct.veh_Clxh){
                            returnData+= pct.veh_Clxh
                        } else{
                            returnData+= "noClxh"
                        }
                        returnData+="_"
                        if (pct.veh_Dpxh){
                            returnData+= pct.veh_Dpxh
                        } else{
                            returnData+= "noDpxh"
                        }
                    }
                }else{
                    def errorMessages=""
                    def num=1
                    //错误信息处理
                    m.instance.errors.allErrors?.each{
                        if(it in org.springframework.validation.FieldError){
                            //将对象和属性国际化
                            it.arguments[1]="${message(code: 'carstorage.label')}"
                            it.arguments[0]="${message(code:"carstorage.${ it.arguments[0]}.label")}"
                            errorMessages+="${num}、"+message(error: it)+";"
                        }

                    }
                    pct.errorMessages=errorMessages
                    failList.add(pct)
                }
            }catch(Exception e){
                totalCount=-1
                e.cause.printStackTrace()
                pct.errorMessages=e.cause.message
                failList.add(pct)
            }
        }
        StringBuilder errorMessages=new StringBuilder()
        failList?.each{
            errorMessages.append("【clxh=${it.veh_Clxh} dpxh=${it.veh_Dpxh}】 错误信息为："+it.errorMessages+"&*_*&")
        }


        def result=[realCount:count,count:totalCount,errorMessages:errorMessages,returnData:returnData]

        render result as JSON
    }
    /**
     * @Description 保存生成前的公告信息
     * @return
     * @create 2013-07-28 huxx
     */
    def save(){
        def m=[:]
        try{
            params.userId=getCurrentUser()?.id
            m=preCarStorageService.save(params)
        }catch(Exception e){
            e.printStackTrace()
        }

        if (m.result){
            flash.message = '保存成功！'
        }else{
            flash.message='保存失败！'
        }

        render (view:'/cn/com/wz/vehcert/precarstorage/generateCartStorage',model: [cartStorage: m.instance])
    }

    /**
     *@Description 公告信息查询页面
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-28 liuly
     */
    def listSearch(){
        render (view:'/cn/com/wz/vehcert/precarstorage/preCartStorageSearch',model: params);
    }
    /**
     *@Description 公告信息查询页面导出
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-07-28 liuly
     */
    def exportSearch(){
        def rows =[]
        def lst=[]
        def content=[]
        try{
        if(params?.format && params.format != "html"){
            def encodedFilename = URLEncoder.encode(("分解前公告信息"), "UTF-8")
            def filename = ""
            def userAgent = request.getHeader("User-Agent")
            if (userAgent =~ /MSIE [4-8]/) {
                // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename=\"${encodedFilename}."+params.extension+"\""
            }
            else {
                // Use RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename*=UTF-8''${encodedFilename}."+params.extension
            }
            response.setHeader("Content-disposition", "attachment;${filename}");
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
           // List fields = ["veh_Clzzqymc","veh_Qyid", "veh_Clfl","veh_Clmc","veh_Clpp","veh_Clxh","veh_Csys","veh_Dpxh","veh_Dpid","veh_cpid","veh_Ggpc","veh_qxhx","veh_Fdjxh","veh_Rlzl","veh_Pl","veh_Gl","veh_Zxxs","veh_Qlj","veh_Hlj","veh_Lts","veh_Ltgg","veh_Gbthps","veh_Zj","veh_Zh","veh_Zs","veh_Wkc","veh_Wkk","veh_Wkg","veh_Hxnbc","veh_Hxnbk","veh_Hxnbg","veh_Zzl","veh_Edzzl","veh_Zbzl","veh_Zzllyxs","veh_Zqyzzl","veh_Edzk","veh_Bgcazzdyxzzl","veh_Jsszcrs","veh_Hzdfs","veh_Cpggh","veh_Ggsxrq","veh_jjlqj","veh_dpqy","veh_Zgcs","veh_Yh","veh_Bz","veh_Qybz","veh_Cpscdz","veh_Qyqtxx","veh_Pfbz","veh_Clztxx","veh_Jss","veh_Jsslx","veh_fgbsqy","veh_fgbsxh","veh_fgbssb","veh_y45pic","veh_zhpic","veh_fhpic","veh_pic1","veh_pic2","veh_other"]
            Map labels =  ["veh_Clzzqymc":"车辆制造企业名称","veh_Qyid":"企业ID", "veh_Clfl":"车辆分类","veh_Clmc":"车辆名称","veh_Clpp":"车辆品牌","veh_Clxh":"车辆型号","veh_Csys":"车身颜色","veh_Dpxh":"底盘型号","veh_Dpid":"底盘ID","veh_cpid":"产品ID","veh_Ggpc":"批次","veh_qxhx":"前悬后悬","veh_Fdjxh":"发动机型号","veh_Rlzl":"燃料种类 ","veh_Pl":"排量","veh_Gl":"功率","veh_Zxxs":"转向形式","veh_Qlj":"前轮距","veh_Hlj":"后轮距","veh_Lts":"轮胎数","veh_Ltgg":"轮胎规格","veh_Gbthps":"钢板弹簧片数","veh_Zj":"轴距","veh_Zh":"轴荷","veh_Zs":"轴数","veh_Wkc":"外廓长","veh_Wkk":"外廓宽","veh_Wkg":"外廓高","veh_Hxnbc":"货箱内部长","veh_Hxnbk":"货箱内部宽","veh_Hxnbg":"货箱内部高","veh_Zzl":"总质量 ","veh_Edzzl":"额定载质量","veh_Zbzl":"整备质量","veh_Zzllyxs":"载质量利用系数","veh_Zqyzzl":"准牵引总质量","veh_Edzk":"额定载客","veh_Bgcazzdyxzzl":"半挂车按座","veh_Jsszcrs":"驾驶室准乘人数","veh_Hzdfs":"后制动方式","veh_Cpggh":"产品公告号","veh_Ggsxrq":"公告生效日期 ","veh_jjlqj":"接近离去角","veh_dpqy":"底盘企业","veh_Zgcs":"最高车速","veh_Yh":"油耗","veh_Bz":"备注","veh_Qybz":"企业标准","veh_Cpscdz":"产品生产地址","veh_Qyqtxx":"企业其他信息","veh_Pfbz":"排放标准","veh_Clztxx":"车辆状态信息","veh_Jss":"驾驶室","veh_Jsslx":"驾驶室类型","veh_fgbsqy":"反光标示企业","veh_fgbsxh":"反光标示型号","veh_fgbssb":"反光标示商标","veh_y45pic":"右45度照片","veh_zhpic":"正后照片","veh_fhpic":"防护照片","veh_pic1":"选择照片1","veh_pic2":"选择照片2 ","veh_other":"其它","veh_clsbdh":"车辆识别代号","veh_cph":"产品号","veh_zdjgl":"最大净功率"]
            def upperCase = { domain, value ->
                return value
            }
           /* Map formatters = [veh_Clzzqymc: upperCase]
            Map parameters = [title: "公告信息表", "column.widths": [0.3, 0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.2,0.1,0.3,0.3,0.1,0.3,0.3,0.1,0.2,0.2,0.1,0.3,0.3,0.3,0.2,0.1,0.2,0.2,0.3,0.3,0.3,0.2,0.1,0.2,0.2,0.2,0.2,0.1,0.2,0.1,0.2,0.3,0.2,0.1,0.3,0.1,0.2,0.3,0.2,0.3,0.2,0.2,0.2,0.2,0.2,0.3,0.2,0.1,0.2,0.2,0.2,0.2,0.2,0.5],"pdf.encoding":"UniGB-UCS2-H", "font.family": "STSong-Light"	]*/
            def out=response.outputStream
            def cel={    ///查询条件（车辆名称、车辆型号）
                if(params.clmc){
                      like("veh_Clmc","%${params.clmc}%")
                }
                if (params.clxh) {
                    eq("veh_Clxh","${params.clxh}")

                }
                if (params.veh_Dpxh) {
                    eq("veh_Dpxh","${params.veh_Dpxh}")

                }
            }
            rows=PreCarStorage.createCriteria().list(params,cel)

            rows.each {
                def m=[:]
                it.properties.each {
                    if (it.key=='veh_y45pic'||it.key=='veh_zhpic'||it.key=='veh_fhpic'||it.key=='veh_pic1'||it.key=='veh_pic2'){
                        if (it.value==null){
                            it.value='NULL'
                            m."${it.key}"=it.value
                        }else{
                            int first=it.value?.lastIndexOf('/')
                            int last=it.value?.indexOf('.')
                            m."${it.key}"=it.value?.substring(first+1,last)
                        }
                    } else{
                        if(it.value==null){
                            it.value='NULL'
                        }
                        m."${it.key}"=it.value
                    }
                }
                lst.add(m)
            }
            ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
            def m=[]
            m.add(labels)
            content.add(lst)
            excelOp.preWriteExcel(out,null,m,["分解前公告信息"],content)

            session.setAttribute('compFlag',"success")
            out.flush()
            out.close()
        }
        }catch(Exception e){
            e.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
            rows.clear()
            lst.clear()
            content.clear()
        }
    }

    /**
     *@Description 公告信息查询页面导出
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-05 liuly
     */
    def showSingle(){
        def  preCarStorage=PreCarStorage.get(params.id)
        render (view:'/cn/com/wz/vehcert/precarstorage/singlePre',model:[preCarStorage:preCarStorage] );
    }
   def exportpdf(){
    try {
        def cel={    ///查询条件（车辆名称、车辆型号）
            if(params.veh_Clmc){
                like("veh_Clmc","%${params.veh_Clmc}%")
            }
            if (params.veh_Clxh) {
                like("veh_Clxh","%${params.veh_Clxh}%")

            }
        }
        def datas=PreCarStorage.createCriteria().list(cel)

        def encodedFilename = URLEncoder.encode(("一致性证书信息"), "UTF-8")
        def filename = ""
        def userAgent = request.getHeader("User-Agent")
        if (userAgent =~ /MSIE [4-8]/) {
            // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
            filename = "filename=\"${encodedFilename}.pdf\""
        }
        else {
            // Use RFC 6266 (RFC 2231/RFC 5987)
            filename = "filename*=UTF-8''${encodedFilename}.pdf"
        }
        response.setHeader("Content-disposition", "attachment;${filename}");
        response.contentType = grailsApplication.config.grails.mime.types[params.format]
        Map formatters = [:]
        Map parameters = [title: "公告信息","pdf.encoding":"UniGB-UCS2-H", "font.family": "STSong-Light"]
        def fos=response.outputStream
        String path=servletContext.getRealPath('/data/vehcert/');
        String TemplatePDF =path+"/preCar.pdf";
        ByteArrayOutputStream[]  baos = new ByteArrayOutputStream[datas.size()];//用于存储每页生成PDF流
        /** 向PDF模板中插入数据 */
        for (int item = 0; item < datas.size(); item++) {
            PreCarStorage data=datas.get(item)
            baos[item] = new ByteArrayOutputStream();
            PdfReader reader = new PdfReader(TemplatePDF);
            PdfStamper stamp = new PdfStamper(reader, baos[item]);
            AcroFields form = stamp.getAcroFields();

            int i = 1;
            for (Iterator it = form.getFields().keySet().iterator(); it.hasNext(); i++) {
                String name = (String) it.next();
                form.setField(''+name.trim(), data.getProperty(name.trim()));
            }
            stamp.setFormFlattening(true); // 千万不漏了这句啊, */
            stamp.close();
        }
        Document doc = new Document();
        PdfCopy pdfCopy = new PdfCopy(doc, fos);
        doc.open();
        PdfImportedPage impPage = null;
        /**取出之前保存的每页内容*/
        for (int i = 0; i < datas.size(); i++) {
            impPage = pdfCopy.getImportedPage(new PdfReader(baos[i]
                    .toByteArray()), 1);
            pdfCopy.addPage(impPage);
            impPage = pdfCopy.getImportedPage(new PdfReader(baos[i]
                    .toByteArray()), 2);
            pdfCopy.addPage(impPage);
        }
        doc.close();//当文件拷贝  记得关闭doc
        fos.flush()
        fos.close()
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (DocumentException e) {
        e.printStackTrace();
    }
    return null;
   }
    /**
     * @Description 公告信息列表函数
     * @Create 2013-08-11 liuly
     */
    def jsonListByDownload(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0

        def cel={    ///查询条件（车辆名称、车辆型号）
            if(params.clmc){
                like("veh_Clmc","%${params.clmc}%")
            }
            if(params.veh_Dpxh){
                eq("veh_Dpxh","${params.veh_Dpxh}")
            }
            if (params.clxh) {
                eq("veh_Clxh","${params.clxh}")
            }
        }
        def rows=PreCarStorage.createCriteria().list(params,cel)
        def lst=[]
        int i=1
        rows?.each {u->
            def m=[:]
            m.id=u.id
            m.num=i
            m.veh_Clzzqymc =u.veh_Clzzqymc
            m.veh_Qyid     =u.veh_Qyid
            m.veh_Clfl     =u.veh_Clfl
            m.veh_Clmc     =u.veh_Clmc
            m.veh_Clpp     =u.veh_Clpp
            m.veh_Clxh     =u.veh_Clxh
            m.veh_Csys     =u.veh_Csys
            m.veh_Dpxh     =u.veh_Dpxh
            m.veh_Dpid     =u.veh_Dpid
            m.veh_Fdjxh    =u.veh_Fdjxh
            m.veh_Rlzl     =u.veh_Rlzl
            m.veh_Pl       =u.veh_Pl
            m.veh_Gl       =u.veh_Gl
            m.veh_zdjgl    =u.veh_zdjgl
            m.veh_Zxxs     =u.veh_Zxxs
            m.veh_Qlj      =u.veh_Qlj
            m.veh_Hlj      =u.veh_Hlj
            m.veh_Lts      =u.veh_Lts
            m.veh_Ltgg     =u.veh_Ltgg
            m.veh_Gbthps   =u.veh_Gbthps
            m.veh_Zj       =u.veh_Zj
            m.veh_Zh       =u.veh_Zh
            m.veh_Zs       =u.veh_Zs
            m.veh_Wkc      =u.veh_Wkc
            m.veh_Wkk      =u.veh_Wkk
            m.veh_Wkg      =u.veh_Wkg
            m.veh_Hxnbc    =u.veh_Hxnbc
            m.veh_Hxnbk    =u.veh_Hxnbk
            m.veh_Hxnbg    =u.veh_Hxnbg
            m.veh_Zzl      =u.veh_Zzl
            m.veh_Edzzl    =u.veh_Edzzl
            m.veh_Zbzl     =u.veh_Zbzl
            m.veh_Zzllyxs  =u.veh_Zzllyxs
            m.veh_Zqyzzl   =u.veh_Zqyzzl
            m.veh_Edzk     =u.veh_Edzk
            m.veh_Bgcazzdyxzzl =u.veh_Bgcazzdyxzzl
            m.veh_Jsszcrs  =u.veh_Jsszcrs
            m.veh_Yh       =u.veh_Yh
            m.veh_Hzdfs    =u.veh_Hzdfs
            m.veh_Ggpc     =u.veh_Ggpc
            m.veh_Cpggh    =u.veh_Cpggh
            m.veh_Ggsxrq   =u.veh_Ggsxrq
            m.veh_Zgcs     =u.veh_Zgcs
            m.veh_Bz       =u.veh_Bz
            m.veh_Qybz     =u.veh_Qybz
            m.veh_Cpscdz   =u.veh_Cpscdz
            m.veh_Qyqtxx   =u.veh_Qyqtxx
            m.veh_Pfbz     =u.veh_Pfbz
            m.veh_Clztxx   =u.veh_Clztxx
            m.veh_Jss      =u.veh_Jss
            m.veh_Jsslx    =u.veh_Jsslx

            m.veh_cpid     =u.veh_cpid
            m.veh_jjlqj    =u.veh_jjlqj
            m.veh_qxhx     =u.veh_qxhx
            m.veh_dpqy     =u.veh_dpqy
            m.veh_fgbsqy   =u.veh_fgbsqy
            m.veh_fgbssb   =u.veh_fgbssb
            m.veh_fgbsxh   =u.veh_fgbsxh
            m.veh_y45pic   =u.veh_y45pic
            m.veh_zhpic    =u.veh_zhpic
            m.veh_fhpic    =u.veh_fhpic
            m.veh_pic1     =u.veh_pic1
            m.veh_pic2     =u.veh_pic2
            m.veh_other     =u.veh_other
            m.veh_clsbdh    =u.veh_clsbdh
            m.veh_cph       =u.veh_cph

            m.createTime   =u.createTime
            def createrId=User.get(u.createrId)
            if (createrId){
                m.createrId    =createrId.userDetail.realName
            } else{
                m.createrId=''
            }

            m.updateTime   =u.updateTime
            def updaterId=User.get(u.updaterId)
            if (updaterId){
                m.updaterId =updaterId.userDetail.realName
            } else{
                m.updaterId =''
            }


            i++;
            lst.add(m)
        }
        def result = [rows:lst,total:rows.totalCount]
        render result as JSON
    }
    /**
     * @Description 依据查询条件进行删除
     *@param params
     *@return 操作信息
     *@Auther liuly
     *@createTime 2013-08-14
     * @Update 2013-11-02 huxx 添加了删除条件公告类型
     */
    def jsonDeleteSearch(){
        def veh_Clxh=''
        def veh_Clmc=''
        def sql="DELETE FROM tbl_precarstorage "
        def needWhere=true
        if (params.clmc) {
            if (needWhere){
               sql+= " where veh_clmc like '%"+params.clmc +"%'"
                needWhere=false
            }else{
                sql+= " and veh_clmc like '%"+params.clmc +"%'"
            }
        }
        if (params.clxh) {
            if (needWhere){
                sql+= "  where  veh_clxh = '"+params.clxh +"'"
                needWhere=false
            } else{
                sql+= "  and  veh_clxh = '"+params.clxh +"'"
            }
        }

        def message=""
        PreCarStorage.withTransaction {trans->
            try{
                StringBuffer sql_sbf = new StringBuffer(sql);

                def list =  sqlService.executeUpdate(sql_sbf.toString())

                if (list>0){
                    message=list+"条删除成功！"

                }else{
                    message = '删除失败！'
                }

            }catch (Exception e){
                trans.setRollbackOnly()
                message = "删除失败！${e.message}"
            }finally{
                render message
            }
        }
    }
    /**
     * @Description 依据查询条件进行删除
     *@param params
     *@return 操作信息
     *@Auther liuly
     *@createTime 2013-11-12
     */
    def preSearch(){
        render (view:'/cn/com/wz/vehcert/precarstorage/preSearch',model: params)
    }
}
