package cn.com.wz.vehcert.environment

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import grails.converters.JSON
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.dao.DataIntegrityViolationException
import parent.poi.ExcelUtils
import parent.poi.event.ExcelReaderUtil
import parent.poi.event.IRowReader
import parent.poi.event.RowReader

/**
 *  重型燃气车控制器
 * @param
 * @return
 * @create  2016-12-20 Qj
 */
class LightPetrolGb6Controller extends BaseController{

    def lightPetrolGb6Service
    /**
     *@Description 跳转到list页面
     *@param
     *@return
     *@Auther Qj
     *@createTime 2016-12-21
     */
    def list() {
        render (view: "/cn/com/wz/vehcert/environment/lightPetrolGb6/list",model: params)
    }

    def index() {
        redirect(action: "list", params: params)
    }


    /**
     *@Description 获取列表信息
     *@Auther zhuwei
     *@createTime 2016-12-20
     */
    def jsonList(){
        def aaa = params
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') :0
        def result=lightPetrolGb6Service.selectByParams(params)
        render result as JSON
    }
    /**
     * @Description 进入导入页面
     * @return
     * @Create 2013-07-29 huxx
     */
    def importPage(){
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'lightPetrolGb6',action: 'imp')}",tabId:params.tabId])
    }
    /**
     * @Description 导入轻型汽油国六环保信息
     * @param filePath
     * @Create 2013-07-29 QJ
     */
    def imp(String filePath){

        def realPath=""
        def filePaths=filePath.split(':_:')
        if(filePaths?.length>=2){
            //获取文件的相对路径(不包含文件名)
            def picRelativePath=UploadUtil.getRelativePathByUploadType(filePaths[0])
            def fileNames=filePaths[1].split(":;:")
            if(fileNames.length==3){
                realPath= UploadUtil.getRootDir(servletContext)+fileNames[2]
            }
        }

        List<String> labels=new ArrayList<String>();
        def lst=[]
        def totalCount=0  //读取记录总数
        def count=0                 //实际存储成功的记录数
        def failList=[]             //保存失败记录
        def checkResult
        StringBuilder errorMessages=new StringBuilder()
        def timeCount=0   //记录导入使用的时间
        def emptyCount     //记录导入公告中公告唯一码为空的个数
        def existsCarStorageNoList  //记录重复的公告唯一码的数列
        def turnOffCarStorage   //导入公告中已经停用的公告数组

        try{
            //获得服务器信息，这里获取程序本地的数据库，而非远程数据库
            def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
            def remoteUrl= dsp.getProperty("url")
            def remoteUserName=dsp.getProperty("userName")
            def remotePassword= dsp.getProperty("password")
            def remoteDriverClassName=dsp.getProperty("driverClassName")
            if (!remoteUrl || !remoteUserName || !remotePassword || !remoteDriverClassName){
                return render("服务器信息配置不完整！")
            }
            def remoteDB=groovy.sql.Sql.newInstance("${remoteUrl}","${remoteUserName}","${remotePassword}","${remoteDriverClassName}")
            def carStorageMaxCount=Integer.parseInt(grailsApplication.config.client.impCarStorage.maxCount?grailsApplication.config.client.impCarStorage.maxCount:'0')
            //获取Excle内容信息
            labels.add('en_xxgkbh');
            labels.add('en_clxh');
            labels.add('en_qcfl');
            labels.add('en_pfjd');
            labels.add('en_cldsbffhwz');
            labels.add('en_jcbz1');
            labels.add('en_jcbz2');
            labels.add('en_jcbz3');
            labels.add('en_jcbz4');
            labels.add('en_jcbz5');

            labels.add('en_fdjxh');
            labels.add('en_fdjscqy');
            labels.add('en_fdjcp');
            labels.add('en_chzhqxh');
            labels.add('en_tc');
            labels.add('en_klbjqxh');
            labels.add('en_tc1');

            labels.add('en_tgxh');
            labels.add('en_ycgqxh');
            labels.add('en_qzxpfkzzzxh');
            labels.add('en_egrxh');
            labels.add('en_obdxh');
            labels.add('en_ecuxh');
            labels.add('en_bsqxs');
            labels.add('en_xyqxh');
            labels.add('en_zyqxh');
            labels.add('en_zlqxs');
            labels.add('en_bz');

            def map=[:];
            map.put('startRow',1) ;//从第二行开始
            IRowReader reader = new RowReader(labels,map);
            lst=ExcelReaderUtil.readExcel(reader, realPath);

            totalCount=lst?.size()  //读取记录总数
            def user=getCurrentUser()
            def time=DateUtil.getCurrentTime()
            def paramsMap=[:]
            paramsMap.lst=lst
            paramsMap.remoteDB=remoteDB
            paramsMap.carStorageMaxCount=carStorageMaxCount
            paramsMap.time=time
            paramsMap.user=user

            if(totalCount>0){
//                //对list里面的的公告唯一码进行校验，如果没有查到相同公告唯一码的公告，就做公告导入处理
//                    def m=heavyDieseService.saveList(lst,user,time,request,grailsAttributes)
//                    timeCount=m.timeCount
//                    count = lst.size()
                //对list存在的信息公开编号进行查询
                println('在这里面进行查询已经存在的信息公开编号')
                checkResult=lightPetrolGb6Service.checkCarStorageNo(lst)   //不再校验公告唯一号的唯一性
                emptyCount= checkResult.emptyCount
//                turnOffCarStorage=checkResult.turnOffCarStorage
                paramsMap.existsNoList=checkResult.existsNoList
                if(emptyCount==0){ // 如果没有信息公开编号为0的数据库
                    def m=lightPetrolGb6Service.newSqlSaveList(paramsMap,request,grailsAttributes)
                    timeCount=m.timeCount
                    count = lst.size()
                }else{
                    errorMessages.append("导入信息中有${emptyCount}条信息公开号为空，")
                }
            }

        }catch(Exception e){
            totalCount = -1
            count = 0
            errorMessages.append("数据插入时出现问题！数据全部回滚！错误原因：${e.cause}")
            e.cause?e.cause.printStackTrace():e.printStackTrace()
        }finally{
            lst.clear()
            labels.clear()
            failList.clear()
            def result=[realCount:count,count:totalCount,errorMessages:errorMessages,timeCount:timeCount]
            render result as JSON
        }

    }
    /**
     *@Description 国六轻型汽油环保信息导出
     *@param
     *@return
     *@Auther ZHUWEI
     *@UpdateBy QJ 2016_12_22
     */
    def exportSearch(){
        def rows =[]
        def content=[]
        try{
            if(params?.format && params.format != "html"){
                def encodedFilename = URLEncoder.encode(("国六轻型汽油环保信息导出"), "UTF-8")
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

                Map labels =  ["en_xxgkbh":"信息公开号",
                        "en_clxh":"车辆型号",
                        "en_qcfl":"汽车分类",
                        "en_pfjd":"排放阶段",
                        "en_clsbffhwz":"车辆识别方法和位置",
                        "en_jcbz1":"GB 18352.6-2016检测机构",
                        "en_jcbz2":"GB 18285-2005检测机构",
                        "en_jcbz3":"GB 1495-2002检测机构",
                        "en_jcbz4":"27630 预留检测机构",
                        "en_jcbz5":"8702  预留检测机构",
                        "en_fdjxh":"发动机型号",
                        "en_fdjscqy":"发动机生产企业",
                        "en_fdjcp":"厂牌",
                        "en_chzhqxh":"催化转化器型号/生产企业",
                        "en_tc":"涂层/载体/封装生产企业",
                        "en_klbjqxh ":"颗粒捕集器型号/生产企业",
                        "en_tc1":"涂层/载体/封装生产企业",
                        "en_tgxh":"炭罐型号/生产企业",
                        "en_ycgqxh":"氧传感器型号/生产企业",
                        "en_qzxpfkzzzxh":"曲轴箱排放控制装置型号/生产企业",
                        "en_egrxh":"EGR型号/生产企业",
                        "en_obdxh":"OBD系统供应商",
                        "en_ecuxh":"ECU型号/生产企业",
                        "en_bsqxs":"变速器型式/档位数",
                        "en_xyqxh":"消音器型号/生产企业",
                        "en_zyqxh":"增压器型号/生产企业",
                        "en_zlqxs":"中冷器型式",
                        "en_bz":"备注 "
                ]
                def upperCase = { domain, value ->
                    return value.toUpperCase()
                }
                def out=response.outputStream
                def map=lightPetrolGb6Service.selectByParams(params)

                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                def m=[]
                m.add(labels)
                content.add(map.rows)
                map.clear()
                excelOp.preWriteExcel(out,null,m,["重型燃气车环保信息导出"],content)
                session.setAttribute('compFlag',"success")
                out.flush()
                out.close()

            }
        }catch(Exception e){
            e.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
            rows.clear()
            content.clear()
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
     * @Description 查看详细信息
     * @CreateTime 2016-12-21 by zhuwei
     * @return
     */
    def show(){
        def lightPetrolGb6=LightPetrolGb6.get(params.id)
        render (view:'/cn/com/wz/vehcert/environment/lightPetrolGb6/show',model: [lightPetrolGb6:lightPetrolGb6])
    }
    /**
     * @Description 跳转到更新页面
     * @return
     * @create 2016-12-21  zhuwei
     */
    def edit() {
        def lightPetrolGb6 = LightPetrolGb6.get(params.id)
        if (!lightPetrolGb6) {
            redirect(action: "list")
            return
        }
        render (view:'/cn/com/wz/vehcert/environment/lightPetrolGb6/edit',model: [lightPetrolGb6:lightPetrolGb6])
    }
    /**
     * @DEscription 修改数据
     * @return
     */
    def update() {
        def lightPetrolGb6Instance = LightPetrolGb6.get(params.id)
        if (params.version&&params.version<lightPetrolGb6Instance.version){
            flash.message='记录已过期！'
            return render (view:'/cn/com/wz/vehcert/environment/lightPetrolGb6/edit',model: [lightPetrolGb6:lightPetrolGb6Instance])
        }
        String curTime=DateUtil.getCurrentTime()
        lightPetrolGb6Instance.properties=params
        lightPetrolGb6Instance.updater_id= User.get(getCurrentUser()?.id)?.userName
        lightPetrolGb6Instance.update_time=curTime

        if (!lightPetrolGb6Instance.save(flush: true)){
            return render (view:'/cn/com/wz/vehcert/environment/lightPetrolGb6/edit',model: [lightPetrolGb6:lightPetrolGb6Instance])
        }else{
            flash.message="更新成功！"
            redirect(action: "show", id: lightPetrolGb6Instance.id)
        }
    }



    /**
     *@Description 删除
     *@paramGas
     *@return
     *@Auther zhuwei
     *@createTime 2016-12-21
     *
     */
    def jsonDelete(){
        def ids = params.deleteIds
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.deleted.simple.message')
        idsArray.each {
            def lightPetrolGb6 = LightPetrolGb6.get(it)
            if (!lightPetrolGb6) {
                return
            }
            try {
                lightPetrolGb6.delete(flush: true)
            }catch (DataIntegrityViolationException e) {
                flag = false
            }
            if(!flag){
                msg = message(code: 'default.not.simple.deleted.message')
            }
        }
        render msg
    }

}
