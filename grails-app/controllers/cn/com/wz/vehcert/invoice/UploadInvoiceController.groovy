package cn.com.wz.vehcert.invoice

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.vehcert.zcinfo.ZCInfo
import grails.converters.JSON
import cn.com.wz.parent.base.BaseController
import invoice.InvoiceFeedBack
import invoice.InvoiceRowItem
import invoice.InvoiceResult

import java.util.zip.ZipInputStream

/**
 * @Description 票据上传控制器
 * @Create 2013-12-07 huxx
 */
class UploadInvoiceController extends BaseController{
     def invoiceService

    def index() {
        redirect(action: "list", params: params)
    }
    /**
     * @Description 进入票据上传管理页面
     * @return
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/invoice/index",model: [:])
    }
    /**
     * @Description 获取票据上传记录
     * @return
     * @Create 2013-12-07 huxx
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def cel={

        }
        def rows=UploadInvoice.createCriteria().list(params,cel)
        def result = [rows:rows,total:rows.totalCount]
        render result as JSON
    }
    /**
     * @Description 进入票据上传页面
     * @return
     * @Create 2013-12-07 huxx
     */
    def create() {
        render (view: "/cn/com/wz/vehcert/invoice/uploadInvoice",model: [:])
    }
    /**
     * @Description 保存票据上传信息
     * @return
     * @Create 2013-12-09 huxx
     */
    def save() {
        def msg="处理成功"
        UploadInvoice.withTransaction {
            def user=getCurrentUser()
            def nowTime=DateUtil.getCurrentTime()
            def uploadInvoiceInstance = new UploadInvoice()
            def flag=true
            def zipName=""
            def zipPath=""
            def resultCode=0
            def message=""
            def invoceList=[]
            def failedStr=""
            try{
                //处理上传文件路径
                def fileNames=params.file_uploadFileNames
                def str=fileNames.split (":_:")
                if(str?.length>=2){
                    //获取文件存储的相对路径(不包含文件名)
                    def relativePath=UploadUtil.getRelativePathByUploadType(str[0])
                    def names=str[1].split (":;:")
                    if(names.length==2){
                        def saveFileName=names[0]
                        String fileName=names[1]
                        String relativeFile=UploadUtil.getRelativeFile(servletContext,relativePath,saveFileName)
                        zipName=fileName
                        zipPath=relativeFile
                        def ext=UploadUtil.getExtName(fileName)
                        if (!"ZIP".equals(ext?.toString()?.toUpperCase())){
                            msg="请上传zip格式的文件！"
                            flag=false
                        }
                    }else{
                        msg="票据加密文件文件上传失败！"
                        flag=false

                    }
                }

                if (flag){  //调用国家关联系统，上传票据文件
                    def resultMessage=invoiceService.checkService(grailsApplication)

                    if(!resultMessage){
                        flag = false
                        msg="远程服务连接失败！"
                    }
                }

                if (flag){
                    def fis=new FileInputStream(new File(grailsApplication.config.upload.rootDir+zipPath))
                    ZipInputStream zipInputStream=new ZipInputStream(new BufferedInputStream(fis))

                    def result= invoiceService.uploadInvoices(grailsApplication,fis.bytes)
                    message = result.message
                    resultCode=result.resultCode
                    invoceList=result.resultList
                }

                if (flag){   //保存票据上传信息
                    uploadInvoiceInstance.fileName=zipName
                    uploadInvoiceInstance.filePath=zipPath
                    uploadInvoiceInstance.resultCode=resultCode
                    uploadInvoiceInstance.message=message
                    uploadInvoiceInstance.uploaderName=user?.userName
                    uploadInvoiceInstance.uploadTime=nowTime
                    if (!uploadInvoiceInstance.save(flush: true)) {
                        msg="保存票据上传信息失败！"
                        flag = false
                    }
                }

                if (flag){//保存返回的票据信息
                    invoceList?.each{invoice->
                        //获取数据库中是否已存在
                        def invoiceInfo=InvoiceInfo.findByInvNo(invoice.invNo)
                        if (invoiceInfo){//已上传的票据不允许再上传
                            failedStr+=invoice.invNo+","
                        }else{ //保存未上传的票据
                            invoiceInfo=new InvoiceInfo()
                            invoiceInfo.message=invoice.message
                            invoiceInfo.resultCode=invoice.resultCode
                            invoiceInfo.typeCode=invoice.typeCode
                            invoiceInfo.invNo=invoice.invNo
                            invoiceInfo.uploadInvoice=  uploadInvoiceInstance
                            if (!invoiceInfo.save()){
                                msg=invoiceInfo.errors
                            }else{   //保存返回的商品信息
                                invoice.itemList?.each{g->
                                    def goodsInfo=new GoodsInfo()
                                    goodsInfo.message=g.message
                                    goodsInfo.resultCode=g.resultCode
                                    goodsInfo.invoiceInfo=invoiceInfo
                                    goodsInfo.ggxh=g.GGXH
                                    goodsInfo.rowId=g.rowId
                                    goodsInfo.sl=g.SL
                                    goodsInfo.spmc=g.SPMC
                                    goodsInfo.updaterName=user.userName
                                    goodsInfo.updateTime=nowTime

                                    if (!goodsInfo.save()){
                                        msg="商品信息保存失败！"
                                        flag = false
                                    }
                                }
                            }
                        }

                    }
                }

            }catch (Exception e){
                msg="处理过程出现问题！${e.cause?e.cause:e}"
                flag = false
            }finally{
                if (flag){
                    if (failedStr){
                        msg="票据"+failedStr+"已上传，不允许重复上传！"
                    }
                }else{
                    //删除无效的文件
                    File file=new File(grailsApplication.config.upload.rootDir+zipPath)
                    if (file.exists()){
                        file.delete()
                    }
                }

                render ([flag:flag,msg:msg] as JSON)
            }
        }
    }
    /**
     * @Description 进入    票据管理页面
     * @return
     * @Create 2013-12-09 huxx
     */
    def toInvoice(){
        render (view: "/cn/com/wz/vehcert/invoice/invoices" ,model: [:])
    }

    /**
     * @Description 获取已上传票据信息
     * @Create 2013-12-09 huxx
     */
    def invoiceJsonList(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def totalCount=0
        def lst=[]
        if ("0".equals(params.needSol)){
             totalCount=InvoiceInfo.executeQuery("select count(id) as totalCount from InvoiceInfo  i where not exists  (select id from GoodsInfo g where i.id=g.invoiceInfo.id and g.needSol='1') ${params.invNo?" and i.invNo='"+params.invNo+"'":''} ").get(0)
             lst=InvoiceInfo.findAll(" from InvoiceInfo  i where not exists (select id from GoodsInfo g where i.id=g.invoiceInfo.id and g.needSol='1')  ${params.invNo?" and i.invNo='"+params.invNo+"'":''} order by i.invNo desc",[],params)
        }else if ("2".equals(params.needSol)){
            totalCount=InvoiceInfo.executeQuery("select count(id) as totalCount from InvoiceInfo  i  ${params.invNo?" where i.invNo='"+params.invNo+"'":''} ").get(0)
             lst=InvoiceInfo.findAll(" from  InvoiceInfo i ${params.invNo?" where i.invNo='"+params.invNo+"'":''} order by i.invNo desc",[],params)
        }else{
             totalCount=InvoiceInfo.executeQuery("select count(id) as totalCount from InvoiceInfo  i where exists  (select id from GoodsInfo g where i.id=g.invoiceInfo.id and g.needSol='1') ${params.invNo?" and i.invNo='"+params.invNo+"'":''} ").get(0)
             lst=InvoiceInfo.findAll(" from InvoiceInfo  i where exists (select id from GoodsInfo g where i.id=g.invoiceInfo.id and g.needSol='1')  ${params.invNo?" and i.invNo='"+params.invNo+"'":''} order by i.invNo desc",[],params)
        }
        def list=[]
        lst?.each{
            def map=[:]
            map.id=it.id
            map.invNo=it.invNo
            map.typeCode=it.typeCode
            map.message=it.message
            map.resultCode=it.resultCode
            map.goodsInfos=it.goodsInfos
            list.add(map)
        }
        def result = [rows:list,total:totalCount]
        render result as JSON
    }

    /**
     * @Description 获取商品信息
     * @Create 2013-12-10 huxx
     */
    def getGoodsInfo(){
        def cel={
            invoiceInfo{
                eq("id",params.invoiceId)
            }
        }
        def lst=GoodsInfo.createCriteria().list(params,cel)
        def result= [rows:lst,total:lst.totalCount]
        render result as JSON
    }
    /**
     * @Description 批量系统关联票据
     * @Create 2013-12-12 huxx
     * @Update 2013-12-26 huxx 修改匹配条件为计划号数字部分+配置流水号
     */
    def relateInvoicesForMany(){
        def resultFlag=true
        def msg=""
        def flag='success'
        try{
            def invNoList=params.invNos.toString().split("_")
            //根据票据号获取相应的商品信息
            def ptUserName=grailsApplication.config.PTUserName
            def ptPassword=grailsApplication.config.PTPassword
            def ptDriverName=grailsApplication.config.PTDriverClassName
            def ptUrl=grailsApplication.config.PTUrl
            if (!ptUserName || !ptPassword || !ptDriverName || !ptUrl){
                flag = 'failed'
                return render ([msg:"服务器信息配置不完整！",flag:flag] as JSON)
            }
            def db=groovy.sql.Sql.newInstance("${ptUrl}","${ptUserName}","${ptPassword}","${ptDriverName}")

            //处理返回结果信息
            invoice.InvoiceFeedBack ifb=new InvoiceFeedBack()
            InvoiceResult[] irs=new InvoiceResult[invNoList.size()]
            def rCount=0
            invNoList.each{
                def lst=db.rows("exec sp_getVINbyFapiaohao '${it.toString()}' ")
                //获取完整合格证编号
                def invoice=InvoiceInfo.findByInvNo(it)
                if (invoice?.goodsInfos?.size()>0){
                    InvoiceRowItem[] items=new InvoiceRowItem[invoice?.goodsInfos?.size()]
                    int i=0
                    invoice?.goodsInfos?.each{goods->
                        InvoiceRowItem item=new InvoiceRowItem()
                        item.setRowId(goods.rowId)
                        String[] hgzList=new String[lst?.size()]
                        def count=0
                        String vins=""
                        String fdjhs=""

                        lst?.each{
                            String jihuahao=goods.spmc?.substring(0, goods.spmc?.indexOf(" ")>0?goods.spmc?.indexOf(" "):0)
                            if(it.计划号?.startsWith(jihuahao) && goods.ggxh?.equals(it.配置流水号)){
                                if(vins){
                                    vins+=","+it.VIN
                                }else{
                                    vins=it.VIN
                                }
                                if(fdjhs){
                                    fdjhs+=","+it.FDJH
                                }else{
                                    fdjhs=it.FDJH
                                }

                                //根据Vin获取完整合格证编号
                                def hgz=ZCInfo.findByVeh_ClsbdhAndVeh_Clztxx(it.VIN,"QX")
                                if (hgz){
                                    hgzList[count]=hgz.veh_Zchgzbh_0
                                    count+=1
                                }else{
                                    resultFlag=false
                                }
                                return
                            }
                        }
                        item.setHgzList(hgzList)
                        items[i]=item
                        i+=1

                        //将关联的合格证信息保存到系统中
                        def g=GoodsInfo.get(goods.id)
                        g.vins=vins
                        g.fdjhs=fdjhs
                        g.save()
                    }
                    //处理票据信息
                    InvoiceResult ir=new InvoiceResult()
                    ir.invNo=invoice.invNo
                    ir.typeCode=invoice.typeCode
                    ir.setItemList(items)

                    irs[rCount]=ir
                    rCount+=1
                }else{
                    resultFlag=false
                }
            }
            ifb.setResultList(irs)
            //调用webservice接口，关联票据和合格证信息
            def result=invoiceService.relateInvoices(grailsApplication,ifb)

            //将返回信息保存
            if (result.resultCode==0){
                if (resultFlag){
                    msg="关联成功！"
                }else{
                    msg="部分关联成功！"
                }
            }else{
                msg="关联失败！"
            }

            result.resultList?.each{invoice->
                //获取数据库中是否已存在
                def invoiceInfo=InvoiceInfo.findByInvNo(invoice.invNo)
                if (invoiceInfo){
                    invoiceInfo.message=invoice.message
                    invoiceInfo.resultCode=invoice.resultCode
                    invoiceInfo.typeCode=invoice.typeCode
                    invoiceInfo.invNo=invoice.invNo
                    if (!invoiceInfo.save()){
                        flash.message=invoiceInfo.errors
                    }else{   //保存返回的商品信息
                        invoice.itemList?.each{g->
                            def goodsInfo=GoodsInfo.findByRowIdAndInvoiceInfo(g.rowId,invoiceInfo)
                            goodsInfo.message=g.message
                            goodsInfo.resultCode=g.resultCode
                            def hgzs=""
                            g.hgzList?.each{
                                hgzs+=it+","
                            }
                            if (hgzs){
                                hgzs=hgzs.substring(0,hgzs.lastIndexOf(","))
                            }
                            goodsInfo.hgzs=hgzs

                            if (g.resultCode==0&&invoice.resultCode==0){
                                def count=g.hgzList?.length
                                if (count ==goodsInfo.sl){
                                    goodsInfo.needSol=0
                                }else{
                                    goodsInfo.needSol=1
                                }
                            }else{
                                goodsInfo.needSol=1
                            }
                            if (!goodsInfo.save()){
                                msg="商品信息保存失败！"
                                flag = false
                            }
                        }
                    }
                }

            }

        }catch(Exception e){
            flag = 'failed'
            msg="程序异常，异常原因：${e.cause?e.cause:e}"
        }finally{
            render ([msg:msg,flag:flag] as JSON)
        }
    }
    /**
     * @Description 进入商品关联的合格证信息页面
     * @Create 2013-12-11 huxx
     */
    def showDetail(){
        def goods=GoodsInfo.get(params.id)
       render (view: "/cn/com/wz/vehcert/invoice/zcinfos",model: [goods:goods])
    }
    /**
     * @Description 获取合格证信息
     * @Create 2013-12-11 huxx
     */
    def zcinfoJsonList(){
        def lst=[]
        def totalCount=0
       def goods=GoodsInfo.get(params.id)
       def hgzs=goods.hgzs
       if (hgzs){
          def hgzList=hgzs.split(",")
          def cel={
              "in"("veh_Zchgzbh_0",hgzList)
          }
          def list=ZCInfo.createCriteria().list(cel)
           lst = list
           totalCount=list?.size()
       }

        render ([rows: lst,total:totalCount] as JSON  )
    }

    /**
     * @Description 将数据库中存的关联提交到国家系统中
     * @Create 2013-12-10 huxx
     */
    def relateInvoicesByHand(){
        def resultFlag=true
        def msg=""
        def flag='success'
        try{
            def invNoList=params.invNos.toString().split("_")
            //处理返回结果信息
            invoice.InvoiceFeedBack ifb=new InvoiceFeedBack()
            InvoiceResult[] irs=new InvoiceResult[invNoList.size()]
            def rCount=0
            invNoList.each{
                //获取完整合格证编号
                def invoice=InvoiceInfo.findByInvNo(it)
                if (invoice?.goodsInfos?.size()>0){
                    InvoiceRowItem[] items=new InvoiceRowItem[invoice?.goodsInfos?.size()]
                    int i=0
                    invoice?.goodsInfos?.each{goods->
                        InvoiceRowItem item=new InvoiceRowItem()
                        item.setRowId(goods.rowId)
                        item.setSPMC(goods.spmc)
                        item.setSL(goods.sl)
                        item.setGGXH(goods.ggxh)
                        def hgzs=goods.hgzs
                        if (hgzs){
                            def hgzLst=hgzs.toString().split(",")
                            String[] hgzList=new String[hgzLst?.size()]
                            def count=0
                            hgzLst?.each{
                                    //根据Vin获取完整合格证编号
                                    def hgz=ZCInfo.findByVeh_Zchgzbh_0(it)
                                    if (hgz){
                                        hgzList[count]=hgz.veh_Zchgzbh_0
                                        count+=1
                                    }else{
                                        resultFlag=false
                                    }
                            }
                            item.setHgzList(hgzList)
                        }

                        items[i]=item
                        i+=1

                    }
                    //处理票据信息
                    InvoiceResult ir=new InvoiceResult()
                    ir.invNo=invoice.invNo
                    ir.typeCode=invoice.typeCode
                    ir.setItemList(items)

                    irs[rCount]=ir
                    rCount+=1
                }else{
                    resultFlag=false
                }
            }
            ifb.setResultList(irs)
            //调用webservice接口，关联票据和合格证信息
            def result=invoiceService.relateInvoices(grailsApplication,ifb)

            //将返回信息保存
            if (result.resultCode==0){
                if (resultFlag){
                    msg="关联成功！"
                }else {
                    msg="部分关联成功！"
                }
            }else{
               msg="关联失败！"
            }


            result.resultList?.each{invoice->
                //获取数据库中是否已存在
                def invoiceInfo=InvoiceInfo.findByInvNo(invoice.invNo)
                if (invoiceInfo){
                    invoiceInfo.message=invoice.message
                    invoiceInfo.resultCode=invoice.resultCode
                    if (!invoiceInfo.save()){
                        flash.message=invoiceInfo.errors
                    }else{   //保存返回的商品信息
                        invoice.itemList?.each{g->
                            def goodsInfo=GoodsInfo.findByRowIdAndInvoiceInfo(g.rowId,invoiceInfo)
                            goodsInfo.message=g.message
                            goodsInfo.resultCode=g.resultCode
                            if (g.resultCode==0&&invoice.resultCode==0){
                                def count=g.hgzList?.length
                                if (count ==goodsInfo.sl){
                                    goodsInfo.needSol=0
                                }else{
                                    goodsInfo.needSol=1
                                }
                            }else{
                                goodsInfo.needSol=1
                            }
                            if (!goodsInfo.save()){
                                msg="商品信息保存失败！"
                                flag = false
                            }
                        }
                    }
                }

            }
//            }else{
//                msg=result.message
//                msg+="详细错误信息:"
//                result.resultList?.each{invoice->
//                    //获取数据库中是否已存在
//                    def invoiceInfo=InvoiceInfo.findByInvNo(invoice.invNo)
//                    if (invoiceInfo){
//                            msg+="<br/>票据号【${invoiceInfo.getInvNo()}】 ${invoiceInfo.message} 商品信息如下："
//                            invoice.itemList?.each{g->
//                               msg+=g.message?(g.message+";"):""
//                            }
//
//                    }
//
//                }
//            }

        }catch(Exception e){
            flag = 'failed'
            msg="程序异常，异常原因：${e.cause?e.cause:e}"
        }finally{
            render ([msg:msg,flag:flag] as JSON)
        }
    }

    /**
     * @Description 添加完整合格证编号
     * @Create 2013-12-13 huxx
     */
    def addHgzs(){
        def msg=""
        def flag="success"
        try {
            def hgzs=params.hgzs
            if (hgzs){
                def goods=GoodsInfo.get(params.id)
                if (goods){
                    //验证合格证编号是否存在
                    def lst=hgzs.split(",")
                    def str=""
                    String hasHgzs=""
                    String hgz0s=""
                    lst?.each{
                        def hgz=ZCInfo.findByVeh_ClsbdhAndVeh_Clztxx(it,"QX")
                        if (hgz ){
                            if (hgz.veh_Zchgzbh_0){
                                if(goods.hgzs?.indexOf(hgz.veh_Zchgzbh_0)>=0){
                                    flag = "failed"
                                    hasHgzs+=it+","
                                }else{
                                    if (goods.hgzs){
                                        goods.hgzs+=","+hgz.veh_Zchgzbh_0
                                    }else{
                                        goods.hgzs=hgz.veh_Zchgzbh_0
                                    }
                                    if (goods.vins){
                                        goods.vins+=","+it
                                    }else{
                                        goods.vins=it
                                    }

                                    if (goods.fdjhs){
                                        goods.fdjhs+=","+hgz.veh_Fdjh
                                    }else{
                                        goods.fdjhs=hgz.veh_Fdjh
                                    }

                                }
                            }else{
                               hgz0s+=hgz0s?",":''+it
                            }

                        }else{
                            if (str){
                                str+=","+it
                            } else{
                                str+=it
                            }
                        }

                    }

                    if (hasHgzs){
                        flag = "failed"
                        msg="${hasHgzs}已关联"
                    }
                    if (hgz0s){
                        flag = "failed"
                        msg+=(msg?',':'')+"${str} 完整合格证编号不存在，请确认是否已打印上传！"
                    }
                    if (str){
                        flag = "failed"
                        msg+=(msg?',':'')+"${str} 未找到对应的合格证信息，请确认填写的VIN是否正确！"
                    }

                }else{
                    flag = "failed"
                    msg="商品信息不存在！"
                }
            }else{
                flag = "failed"
                msg="完整合格证编号不能为空！"
            }
        }catch (Exception e){
            msg="处理错误！错误原因：${e.cause?e.cause:e}"
            flag = "failed"
        }
        if (!msg){
            msg="保存成功！"
        }
        render ([flag:flag,msg:msg] as JSON)

    }
    /**
     * @Description 删除合格证关联
     * @Create 2013-12-13 huxx
     */
    def deleteHgzs(){
        def msg="删除成功！"
        def flag="success"
        def ids=params.deleteIds
        if (ids){
            def goods=GoodsInfo.get(params.id)
            if (goods){
                def hgzs=goods.hgzs
                def vins=goods.vins
                def fdjhs=goods.fdjhs
                def lst=ids.split("_")

                lst?.each{
                    def hgzlist=ZCInfo.findAllByVeh_Zchgzbh_0(it)

                    hgzs=hgzs?.replace(it,"")
                    hgzlist?.each{ hgz->
                       vins=vins?.replace(hgz.veh_Clsbdh,"")
                       fdjhs=fdjhs?.replace(hgz.veh_Fdjh,"")
                    }
                }

                if (hgzs){
                    while(hgzs.indexOf(",,")>=0){
                        hgzs= hgzs.replace(",,",",")
                    }
                    def index=hgzs.lastIndexOf(",")
                    if (!hgzs ||index<hgzs.length()-1){
                        if (index==0){
                             hgzs = hgzs.replace(",","")
                        }
                        goods.hgzs=hgzs
                    }else{
                       goods.hgzs=hgzs.substring(0,index)
                    }
                }else{
                    goods.hgzs=hgzs
                }

                if (vins){
                    while(vins.indexOf(",,")>=0){
                        vins= vins.replace(",,",",")
                    }
                    def index=vins.lastIndexOf(",")
                    if (!vins ||index<vins.length()-1){
                        if (index==0){
                            vins = vins.replace(",","")
                        }
                        goods.vins=vins
                    }else{
                        goods.vins=vins.substring(0,index)
                    }
                }else{
                    goods.vins=vins
                }

                if (fdjhs){
                    while(fdjhs.indexOf(",,")>=0){
                        fdjhs= fdjhs.replace(",,",",")
                    }
                    def index=fdjhs.lastIndexOf(",")
                    if (!fdjhs ||index<fdjhs.length()-1){
                        if (index==0){
                            fdjhs = fdjhs.replace(",","")
                        }
                        goods.fdjhs=fdjhs
                    }else{
                        goods.fdjhs=fdjhs.substring(0,index)
                    }
                }else{
                    goods.fdjhs=fdjhs
                }

            } else{
                flag = "failed"
                msg="商品信息不存在！"
            }
        }else{
            flag = "failed"
            msg="请选择删除记录！"
        }
        render ([flag:flag,msg:msg] as JSON)
    }
}