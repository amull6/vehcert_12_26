package cn.com.wz.vehcert.environment

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.zcinfo.ZCInfo
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.itextpdf.text.pdf.qrcode.EncodeHintType
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject

import java.nio.file.FileSystems

/**
 * @Description 环保信息证书生成控制器
 * @CreateTime 2016-12-21 by zhuwei
 */

class EnvirPrintController extends BaseController {

    def index() {}

    /**
     *@Description 跳转到车间自己得打印合格证列表
     * @CreteTime 2016-12-21  by zhuwei
     */
    def cheJianList(){
        render(view:'/cn/com/wz/vehcert/environment/envirodeal/chejianlist')
    }

    /**
     *车间本地查询
     * @return
     */
    def jsonChejianList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        params.sort='createTime'
        params.order="desc"
        def cel = {
            if(params.vehCode){
                eq("veh_Zchgzbh","${params.vehCode}")
            }
            eq('veh_Type','0')
            isNotNull('veh_Fdjh')  //发动机号不能为空
            isNotNull('veh_Clsbdh')  //车辆识别代号不能为空
            eq('veh_Clztxx','QX')
        }
        def results = ZCInfo.createCriteria().list(params,cel)
        def result = [rows: results, total: results.totalCount]
        render result as JSON
    }

    /**
     * @Description 查看环保信息是否已经打印
     * @ zcinfoID
     * @CreateTime by zhuwei 2016-12-21
     */
    def jsonSearchEnvPrn(){
        int status = 0
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def envPrnInstance = HeavyDieselInfo.get(zcinfoIDStr)
        if (!envPrnInstance) {
            status = 0
        }
        try {
            if (envPrnInstance.id!=null){
                if(envPrnInstance.status==1){ //上传成功
                    status =2
                }else{    //未上传或上传失败
                    status =1
                }

            }
        }catch (Exception e) {
            status = 0
        }
        render status
    }
    /**
     * @Description 查看轻柴环保信息是否已经打印
     * @ zcinfoID
     * @CreateTime by zhuwei 2016-12-21
     */
    def  jsonSearchLightDieselEnvPrn(){
        int status = 0
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def envPrnInstance = LightDieselInfo.get(zcinfoIDStr)
        if (!envPrnInstance) {
            status = 0
        }
        try {
            if (envPrnInstance.id!=null){
                if(envPrnInstance.status==1){ //上传成功
                    status =2
                }else{    //未上传或上传失败
                    status =1
                }

            }
        }catch (Exception e) {
            status = 0
        }
        render status
    }
    /**
     * @Description 查看重柴环保信息是否已经打印
     * @ zcinfoID
     * @CreateTime by zhuwei 2016-12-21
     */
    def  jsonSearchHeavyGasEnvPrn(){
        int status = 0
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def envPrnInstance = HeavyGasInfo.get(zcinfoIDStr)
        if (!envPrnInstance) {
            status = 0
        }
        try {
            if (envPrnInstance.id!=null){
                if(envPrnInstance.status==1){ //上传成功
                    status =2
                }else{    //未上传或上传失败
                    status =1
                }

            }
        }catch (Exception e) {
            status = 0
        }
        render status
    }
    /**
     * @Description 查看新能源汽车环保信息是否已经打印
     * @ zcinfoID
     * @CreateTime by zhuwei 2016-12-21
     */
    def  jsonSearchNewEnergyEnvPrn(){
        int status = 0
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def envPrnInstance = NewEnergyCarInfo.get(zcinfoIDStr)
        if (!envPrnInstance) {
            status = 0
        }
        try {
            if (envPrnInstance.id!=null){
                if(envPrnInstance.status==1){ //上传成功
                    status =2
                }else{    //未上传或上传失败
                    status =1
                }
            }
        }catch (Exception e) {
            status = 0
        }
        render status
    }
    /**
     * @Description 查看国六轻型汽油环保信息是否已经打印
     * @ zcinfoID
     * @CreateTime by zhuwei 2016-12-21
     */
    def  jsonSearchLightPetrolGb6EnvPrn(){
        int status = 0
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def envPrnInstance = LightPetrolGb6Info.get(zcinfoIDStr)
        if (!envPrnInstance) {
            status = 0
        }
        try {
            if (envPrnInstance.id!=null){
                if(envPrnInstance.status==1){ //上传成功
                    status =2
                }else{    //未上传或上传失败
                    status =1
                }

            }
        }catch (Exception e) {
            status = 0
        }
        render status
    }
    /**
     * @Description 根据chuan过来的参数判断到那个Domain类中去去环保清单库数据
     * @params
     * @return
     * @createTime 2016-12-21 15:40:17
     */
    def findEnvirModelList(){
        def aaa = params
        def en_type = params?.en_type
        def zcinfoID = params?.zcinfoID
        def zcinfoInstance = ZCInfo.get(zcinfoID)
        def en_clxh = zcinfoInstance?.veh_Clxh
        def en_fdjxh = zcinfoInstance?.veh_Fdjxh
        if(en_type =='HeavyDiesel'||en_type =='City'){  //重型柴油车/城市车辆
            render(view: '/cn/com/wz/vehcert/environment/envirodeal/heavyDieselList',model: [zcinfoID:params.zcinfoID,en_clxh:en_clxh,en_fdjxh:en_fdjxh,en_type:en_type]);
        }else if(en_type =='HeavyGas'){
            render(view: '/cn/com/wz/vehcert/environment/envirodeal/heavyGasList',model: [zcinfoID:params.zcinfoID,en_clxh:en_clxh,en_fdjxh:en_fdjxh,en_type:en_type]);
        }else if(en_type =='LightDual'||en_type =='LightPetrol'){
            render(view: '/cn/com/wz/vehcert/environment/envirodeal/lightDualList',model: [zcinfoID:params.zcinfoID,en_clxh:en_clxh,en_fdjxh:en_fdjxh,en_type:en_type]);
        }else if(en_type =='HeavyPetrol'){
            render(view: '/cn/com/wz/vehcert/environment/envirodeal/heavyPetrol',model: [zcinfoID:params.zcinfoID,en_clxh:en_clxh,en_fdjxh:en_fdjxh,en_type:en_type]);
        }else if (en_type =='LightDiesel'||en_type =='LightDiesel4'){
            render(view: '/cn/com/wz/vehcert/environment/envirodeal/lightDieselList',model: [zcinfoID:params.zcinfoID,en_clxh:en_clxh,en_fdjxh:en_fdjxh,en_type:en_type]);
        }else if (en_type =='NewEnergy'){
            render(view: '/cn/com/wz/vehcert/environment/envirodeal/newEnergyList',model: [zcinfoID:params.zcinfoID,en_clxh:en_clxh,en_fdjxh:en_fdjxh,en_type:en_type]);
        }else if (en_type =='LightPetrolGb6'){
            render(view: '/cn/com/wz/vehcert/environment/envirodeal/lightPetrolGb6List',model: [zcinfoID:params.zcinfoID,en_clxh:en_clxh,en_fdjxh:en_fdjxh,en_type:en_type]);
        }


    }


    /**
     * 使用插件Zxing生成生成二维码,传入车架号，传出生成图片url
     * @CreateTime 2016-12-19   zhuwei
     * @return
     */
    def generateQR(def vin){
        def now=new Date().getTime()
        String filePath =grailsApplication.config.ENVQR.location
        String fileName =vin+"_"+now+".png"
        println(fileName)
        JSONObject json = new JSONObject();
        String content ="http://xxgk.vecc.org.cn/vin/" +vin;// 内容
        int width = 115; // 图像宽度
        int height = 115; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def   bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        def qrPiUrl=filePath+"\\"+fileName
        return qrPiUrl
    }

    /**
     * 使用插件Zxing生成生成条形码
     * @CreateTime 2016-12-19  zhuwei
     * @return
     */
    def generateBar(def vin){
        def now=new Date().getTime()
        String filePath =grailsApplication.config.ENVBAR.location
        String fileName =vin+"_"+now+".png"
        println(fileName)
        JSONObject json = new JSONObject();
        String content =vin// 内容
//        String content = json.toString();// 内容
        int width = 225; // 图像宽度
        int height = 58; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        hints.put(EncodeHintType.ERROR_CORRECTION, true);
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def   bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.CODE_128, width, height, hints);// 生成矩阵
        def path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        def barPicUrl=filePath+"\\"+fileName
        return barPicUrl
    }
    /**
     * 使用插件Zxing生成生成二维码,传入车架号，传出生成图片url 与旧的相比尺寸不一样
     * @CreateTime 2016-12-19   QJ
     * @return
     */
    def newGenerateQR(def vin){
        def now=new Date().getTime()
        String filePath =grailsApplication.config.ENVQR.location
        String fileName =vin+"_"+now+".png"
        println(fileName)
        JSONObject json = new JSONObject();
        String content ="http://xxgk.vecc.org.cn/vin/" +vin;// 内容
        int width = 124; // 图像宽度
        int height = 124; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def   bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        def qrPiUrl=filePath+"\\"+fileName
        return qrPiUrl
    }

    /**
     * 使用插件Zxing生成生成条形码，传出生成图片url 与旧的相比尺寸不一样
     * @CreateTime 2016-12-19  zhuwei
     * @return
     */
    def newGenerateBar(def vin){
        def now=new Date().getTime()
        String filePath =grailsApplication.config.ENVBAR.location
        String fileName =vin+"_"+now+".png"
        println(fileName)
        JSONObject json = new JSONObject();
        String content =vin// 内容
//        String content = json.toString();// 内容
        int width = 304; // 图像宽度
        int height = 41; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        hints.put(EncodeHintType.ERROR_CORRECTION, true);
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        def   bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.CODE_128, width, height, hints);// 生成矩阵
        def path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        def barPicUrl=filePath+"\\"+fileName
        return barPicUrl
    }
    /**
     * @Description 将2017年01月01日的时间替换为2017-01-01的数据各省市
     * @CreateTime 2017-01-07 by zhuwei
     * @param date
     * @return
     */
    def convertDate(def date){
        date = date.replace('年','-')
        date = date.replace('月','-')
        date = date.replace('日','')
        return  date
    }

    /**
     * @Description 打印生成重型柴油车/城市车辆环保信息随车清单
     * @CreateTime 2016-12-22 by zhuwei  返回生成数据id/防伪二维码图片url/车辆识别代号条形码
     * @Update 2017-01-06 by zhuwei  在保存成功数据后，将数据级联保存到EnvirUpload表中
     *@Update 2017-01-10 by zhuwei  在保存到envirUpload数据时，增加了车辆型号、发动机型号、汽车分类字段
     */
    def jsonHeavyDieselUpdate(){
            def aa=params
            String id =""
            User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
            def flag=true
            def idStr=params.id//重型柴油车/城市车辆环保信息库id
            def zcinfoIDStr = params.zcinfoID///
            def usertype =params.usertype
            def QRpiUrl
            def BarPicUrl
            try{
                def heavyDiesel = HeavyDiesel.get(idStr)
                def zcinfoInstance= ZCInfo.get(zcinfoIDStr)
                HeavyDieselInfo heavyDieselPrint = HeavyDieselInfo.get(zcinfoInstance.id)
                if (!heavyDieselPrint){
                    def heavyDieselInfo = new HeavyDieselInfo()
                    heavyDieselInfo.setId(zcinfoInstance.id) //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联
                    heavyDieselInfo.en_xxgkbh         =heavyDiesel.en_xxgkbh
                    heavyDieselInfo.en_clxh           =heavyDiesel.en_clxh
                    heavyDieselInfo.en_qcfl           =heavyDiesel.en_qcfl
                    heavyDieselInfo.en_pfjd           =heavyDiesel.en_pfjd
                    heavyDieselInfo.en_clsbff         =heavyDiesel.en_clsbff
                    heavyDieselInfo.en_fdjxh          =heavyDiesel.en_fdjxh
                    heavyDieselInfo.en_zzs            =heavyDiesel.en_zzs
                    heavyDieselInfo.en_xzmc           =heavyDiesel.en_xzmc
                    heavyDieselInfo.en_sccdz          =heavyDiesel.en_sccdz
                    heavyDieselInfo.en_cp             =heavyDiesel.en_cp
                    heavyDieselInfo.en_jcbz1          =heavyDiesel.en_jcbz1
                    heavyDieselInfo.en_jcbz2          =heavyDiesel.en_jcbz2
                    heavyDieselInfo.en_jcbz3          =heavyDiesel.en_jcbz3
                    heavyDieselInfo.en_jcbz4          =heavyDiesel.en_jcbz4
                    heavyDieselInfo.en_jcbz5          =heavyDiesel.en_jcbz5
                    heavyDieselInfo.en_jcbz6          =heavyDiesel.en_jcbz6
                    heavyDieselInfo.en_jcbz7          =heavyDiesel.en_jcbz7
                    heavyDieselInfo.en_zzjgl          =heavyDiesel.en_zzjgl
                    heavyDieselInfo.en_zdjnj          =heavyDiesel.en_zdjnj
                    heavyDieselInfo.en_rlggxtxs       =heavyDiesel.en_rlggxtxs
                    heavyDieselInfo.en_pybxh          =heavyDiesel.en_pybxh
                    heavyDieselInfo.en_pyqxh          =heavyDiesel.en_pyqxh
                    heavyDieselInfo.en_zyqxh          =heavyDiesel.en_zyqxh
                    heavyDieselInfo.en_zlqxs          =heavyDiesel.en_zlqxs
                    heavyDieselInfo.en_OBDxh          =heavyDiesel.en_OBDxh
                    heavyDieselInfo.en_EGRxh          =heavyDiesel.en_EGRxh
                    heavyDieselInfo.en_ECUxh          =heavyDiesel.en_ECUxh
                    heavyDieselInfo.en_pqhclxtxh      =heavyDiesel.en_pqhclxtxh
                    heavyDieselInfo.en_tc             =heavyDiesel.en_tc
                    heavyDieselInfo.en_tc1             =heavyDiesel.en_tc1
                    heavyDieselInfo.en_pqhclxtxs      =heavyDiesel.en_pqhclxtxs
                    heavyDieselInfo.en_klqxh          =heavyDiesel.en_klqxh
                    heavyDieselInfo.en_jqxsqxh        =heavyDiesel.en_jqxsqxh
                    heavyDieselInfo.en_pqxsqxh        =heavyDiesel.en_pqxsqxh
                    heavyDieselInfo.type              =heavyDiesel.type
                    heavyDieselInfo.creater_id        =loginUser.userName
                    heavyDieselInfo.create_time       =DateUtil.getCurrentTime()

                    heavyDieselInfo.en_clsbdh   = zcinfoInstance?.veh_Clsbdh
                    heavyDieselInfo.en_fdjh     = zcinfoInstance?.veh_Fdjh
                    heavyDieselInfo.en_zzrq     = zcinfoInstance?.veh_Fzrq

//                    ############################生成二维防伪码START##############################
                    def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                    QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                    heavyDieselInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                    BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                    heavyDieselInfo.bar_name =  BarPicUrl

                    if(heavyDieselInfo.save(flush: true)){  //生成重型柴油车成功，将其数据保存到EnvirUpload表中
                        def envirUpload = new EnvirUpload()
                        envirUpload.setId(heavyDieselInfo.id)    //保存id，使用assign方式
                        envirUpload.en_clsbdh =   heavyDieselInfo?.en_clsbdh
                        envirUpload.en_xxgkbh =   heavyDieselInfo?.en_xxgkbh
                        envirUpload.en_fdjh   =   heavyDieselInfo?.en_fdjh
                        envirUpload.en_scrq   =   this.convertDate(heavyDieselInfo?.en_zzrq)         //生产日期 ,数据格式为2017-01-01
                        envirUpload.en_ccrq   =   this.convertDate(heavyDieselInfo?.en_zzrq)        //出厂日期
                        envirUpload.en_gfwz   =   DictionaryValue.findByCode('enviroWebSite')?.value1

                        envirUpload.en_fdjsb =   heavyDieselInfo?.en_cp
                        envirUpload.en_fdjsccdz =   heavyDieselInfo?.en_sccdz

//                       增加了车辆型号、发动机型号、车辆分类的参数的保存
                        envirUpload.en_clxh        =heavyDieselInfo?.en_clxh
                        envirUpload.en_fdjxh       =heavyDieselInfo?.en_fdjxh
                        envirUpload.en_qcfl        =heavyDieselInfo?.en_qcfl

//                      增加重型车出厂试验为 自由加速烟度 试验
                        envirUpload.en_ccsy        ='自由加速烟度'

                        envirUpload.creater_id        =loginUser.userName
                        envirUpload.create_time       =DateUtil.getCurrentTime()
                        if(heavyDieselInfo.type=='0'){  //重型柴油车
                            envirUpload.type='重型柴油车'
                        }else if(heavyDieselInfo.type=='1'){  //城市车辆
                            envirUpload.type='城市车辆'
                        }
                        envirUpload.save(flush: true)
                    }
                    id =  heavyDieselInfo.id
                } else{
                    id =  heavyDieselPrint.id
                }
            }catch(Exception e){
                e.cause?e.cause.printStackTrace():e.printStackTrace()
                flag=false
            }
            def result = [id: id, QRpiUrl: QRpiUrl,BarPicUrl:BarPicUrl]
            render result as JSON
    }
    /**
     * @Description 打印生成重型燃气车辆环保信息随车清单
     * @CreateTime 2016-12-22 by zhuwei  返回生成数据id/防伪二维码图片url/车辆识别代号条形码
     */
    def jsonHeavyGasUpdate(){
        def aa=params
        String id =""
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def flag=true
        def idStr=params.id//重型燃气车车辆环保信息库id
        def zcinfoIDStr = params.zcinfoID///
        def usertype =params.usertype
        def QRpiUrl
        def BarPicUrl
        try{
            def heavyGas = HeavyGas.get(idStr)
            def zcinfoInstance= ZCInfo.get(zcinfoIDStr)
            HeavyGasInfo heavyGasPrint = HeavyGasInfo.get(zcinfoInstance.id)
            if (!heavyGasPrint){
                def heavyGasInfo = new HeavyGasInfo()
                heavyGasInfo.setId(zcinfoInstance?.id)//使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联
                heavyGasInfo.en_xxgkbh         =heavyGas.en_xxgkbh
                heavyGasInfo.en_clxh           =heavyGas.en_clxh
                heavyGasInfo.en_qcfl           =heavyGas.en_qcfl
                heavyGasInfo.en_pfjd           =heavyGas.en_pfjd
                heavyGasInfo.en_cldsbffhwz     =heavyGas.en_cldsbffhwz
                heavyGasInfo.en_fdjxh          =heavyGas.en_fdjxh
                heavyGasInfo.en_zzsmc          =heavyGas.en_zzsmc
                heavyGasInfo.en_xzmc           =heavyGas.en_xzmc
                heavyGasInfo.en_sccdz          =heavyGas.en_sccdz
                heavyGasInfo.en_cp             =heavyGas.en_cp
                heavyGasInfo.en_jcbz1          =heavyGas.en_jcbz1
                heavyGasInfo.en_jcbz2          =heavyGas.en_jcbz2
                heavyGasInfo.en_jcbz3          =heavyGas.en_jcbz3
                heavyGasInfo.en_jcbz4          =heavyGas.en_jcbz4
                heavyGasInfo.en_jcbz5          =heavyGas.en_jcbz5
                heavyGasInfo.en_jcbz6          =heavyGas.en_jcbz6
                heavyGasInfo.en_jcbz7          =heavyGas.en_jcbz7
                heavyGasInfo.en_zdjgl          =heavyGas.en_zdjgl
                heavyGasInfo.en_zdjnj          =heavyGas.en_zdjnj
                heavyGasInfo.en_rlgjxtxs       =heavyGas.en_rlgjxtxs
                heavyGasInfo.en_ycgqxh         =heavyGas.en_ycgqxh
                heavyGasInfo.en_zfqhyltjqxh    =heavyGas.en_zfqhyltjqxh
                heavyGasInfo.en_hhzzxh         =heavyGas.en_hhzzxh
                heavyGasInfo.en_psqxh          =heavyGas.en_psqxh
                heavyGasInfo.en_egrxh          =heavyGas.en_egrxh
                heavyGasInfo.en_zyqxh          =heavyGas.en_zyqxh
                heavyGasInfo.en_zlqxs          =heavyGas.en_zlqxs
                heavyGasInfo.en_ecuxh          =heavyGas.en_ecuxh
                heavyGasInfo.en_obdxh          =heavyGas.en_obdxh
                heavyGasInfo.en_qzxwrwpfkzzzxh =heavyGas.en_qzxwrwpfkzzzxh
                heavyGasInfo.en_kqlqqxh        =heavyGas.en_kqlqqxh
                heavyGasInfo.en_jqxsqxh        =heavyGas.en_jqxsqxh
                heavyGasInfo.en_pqxsqxh        =heavyGas.en_pqxsqxh
                heavyGasInfo.en_pqhclxtxs      =heavyGas.en_pqhclxtxs
                heavyGasInfo.en_pqhclxtxh      =heavyGas.en_pqhclxtxh
                heavyGasInfo.en_tc             =heavyGas.en_tc
                heavyGasInfo.en_vin            =zcinfoInstance.veh_Clsbdh
                heavyGasInfo.creater_id        =loginUser.userName
                heavyGasInfo.create_time       =DateUtil.getCurrentTime()
                heavyGasInfo.en_fdjbh          =zcinfoInstance?.veh_Fdjh
                heavyGasInfo.en_zzrq           =zcinfoInstance?.veh_Fzrq

//                    ############################生成二维防伪码START##############################
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                heavyGasInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                heavyGasInfo.bar_name =  BarPicUrl

                if(heavyGasInfo.save(flush: true)){          //生成双燃料/轻型汽油车成功
                    def envirUpload = new EnvirUpload()
                    envirUpload.setId(heavyGasInfo?.id)    //保存id，使用assign方式
                    envirUpload.en_clsbdh =  heavyGasInfo?.en_vin
                    envirUpload.en_xxgkbh =  heavyGasInfo?.en_xxgkbh
                    envirUpload.en_scrq   =   this.convertDate(heavyGasInfo?.en_zzrq)         //生产日期 ,数据格式为2017-01-01
                    envirUpload.en_ccrq   =   this.convertDate(heavyGasInfo?.en_zzrq)        //出厂日期
                    envirUpload.en_gfwz   =   DictionaryValue.findByCode('enviroWebSite')?.value1
                    envirUpload.en_fdjh  =   heavyGasInfo?.en_fdjbh
                    envirUpload.en_fdjsb  =   heavyGasInfo?.en_cp              //轻型车可以不保存发动机商标和发动机生产厂地址
                    envirUpload.en_fdjsccdz =   heavyGasInfo?.en_sccdz

//                       增加了车辆型号、发动机型号、车辆分类的参数的保存
                    envirUpload.en_clxh        =heavyGasInfo?.en_clxh
                    envirUpload.en_fdjxh       =heavyGasInfo?.en_fdjxh
                    envirUpload.en_qcfl        =heavyGasInfo?.en_qcfl

//                      增加重型车出厂试验为 自由加速烟度 试验
                    envirUpload.en_ccsy        ='双怠速试验'

                    envirUpload.creater_id        =loginUser.userName
                    envirUpload.create_time       =DateUtil.getCurrentTime()

                    envirUpload.type='重型燃气'

                    envirUpload.save(flush: true)
                }
                id =  heavyGasInfo.id
            } else{
                id =  heavyGasPrint.id
            }
        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            flag=false
        }
        def result = [id: id, QRpiUrl: QRpiUrl,BarPicUrl:BarPicUrl]
        render result as JSON
    }
    /**
     * @Description 打印生成新能源汽车环保信息随车清单
     * @CreateTime 2016-12-22 by Qj  返回生成数据id/防伪二维码图片url/车辆识别代号条形码
     */
    def jsonNewEnergyUpdate(){
        def aa=params
        String id =""
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def flag=true
        def idStr=params.id//新能源汽车环保信息库id
        def zcinfoIDStr = params.zcinfoID///
        def usertype =params.usertype
        def QRpiUrl
        def BarPicUrl
        try{
            def newEnergy = NewEnergyCar.get(idStr)
            def zcinfoInstance= ZCInfo.get(zcinfoIDStr)
            NewEnergyCarInfo newEnergyPrint = NewEnergyCarInfo.get(zcinfoInstance.id)
            if (!newEnergyPrint){
                def newEnergyInfo = new NewEnergyCarInfo()
                newEnergyInfo.setId(zcinfoInstance?.id)//使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联
                newEnergyInfo.en_xxgkbh         =newEnergy.en_xxgkbh
                newEnergyInfo.en_clxh           =newEnergy.en_clxh
                newEnergyInfo.en_qcfl           =newEnergy.en_qcfl
                newEnergyInfo.en_cldsbffhwz     =newEnergy.en_cldsbffhwz
                newEnergyInfo.en_jcbz1          =newEnergy.en_jcbz1
                newEnergyInfo.en_ddjxh          =newEnergy.en_ddjxh
                newEnergyInfo.en_zckzqxh        =newEnergy.en_zckzqxh
                newEnergyInfo.en_cnzzxh         =newEnergy.en_cnzzxh
                newEnergyInfo.en_dcrl           =newEnergy.en_dcrl
                newEnergyInfo.en_vin            =zcinfoInstance.veh_Clsbdh
                newEnergyInfo.creater_id        =loginUser.userName
                newEnergyInfo.create_time       =DateUtil.getCurrentTime()
                newEnergyInfo.en_ddjbh          =zcinfoInstance?.veh_Fdjh
                newEnergyInfo.en_zzrq           =zcinfoInstance?.veh_Fzrq

//                    ############################生成二维防伪码START##############################
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                newEnergyInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                newEnergyInfo.bar_name =  BarPicUrl

                if(newEnergyInfo.save(flush: true)){          //生成双燃料/轻型汽油车成功
                    def envirUpload = new EnvirUpload()
                    envirUpload.setId(newEnergyInfo?.id)    //保存id，使用assign方式
                    envirUpload.en_clsbdh =  newEnergyInfo?.en_vin
                    envirUpload.en_xxgkbh =  newEnergyInfo?.en_xxgkbh
                    envirUpload.en_scrq   =   this.convertDate(newEnergyInfo?.en_zzrq)         //生产日期 ,数据格式为2017-01-01
                    envirUpload.en_ccrq   =   this.convertDate(newEnergyInfo?.en_zzrq)        //出厂日期
                    envirUpload.en_gfwz   =   DictionaryValue.findByCode('enviroWebSite')?.value1

//                    envirUpload.en_fdjsb =   lightDualInfo?.en_cp              //轻型车可以不保存发动机商标和发动机生产厂地址
//                    envirUpload.en_fdjsccdz =   lightDualInfo?.en_sccdz

//                       增加了车辆型号、发动机型号、车辆分类的参数的保存
                    envirUpload.en_fdjh        =newEnergyInfo?.en_ddjbh
                    envirUpload.en_clxh        =newEnergyInfo?.en_clxh
                    envirUpload.en_fdjxh       =newEnergyInfo?.en_ddjxh
                    envirUpload.en_qcfl        =newEnergyInfo?.en_qcfl

//                      增加重型车出厂试验为 自由加速烟度 试验
                    envirUpload.en_ccsy        ='加速行驶车外噪声实验'

                    envirUpload.creater_id        =loginUser.userName
                    envirUpload.create_time       =DateUtil.getCurrentTime()

                    envirUpload.type='新能源汽车'

                    envirUpload.save(flush: true)
                }
                id =  newEnergyInfo.id
            } else{
                id =  newEnergyPrint.id
            }
        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            flag=false
        }
        def result = [id: id, QRpiUrl: QRpiUrl,BarPicUrl:BarPicUrl]
        render result as JSON
    }
    /**
     * @Description 打印生成国六轻型汽油车辆环保信息随车清单
     * @CreateTime 2016-12-22 by zhuwei  返回生成数据id/防伪二维码图片url/车辆识别代号条形码
     */
    def jsonLightPetrolGb6Update(){
        def aa=params
        String id =""
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def flag=true
        def idStr=params.id//重型燃气车车辆环保信息库id
        def zcinfoIDStr = params.zcinfoID///
        def usertype =params.usertype
        def QRpiUrl
        def BarPicUrl
        try{
            def lightPetrolGb6 = LightPetrolGb6.get(idStr)
            def zcinfoInstance= ZCInfo.get(zcinfoIDStr)
            LightPetrolGb6Info lightPetrolGb6Print = LightPetrolGb6Info.get(zcinfoInstance.id)
            if (!lightPetrolGb6Print){
                def lightPetrolGb6Info = new LightPetrolGb6Info()
                lightPetrolGb6Info.setId(zcinfoInstance?.id)//使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联
                lightPetrolGb6Info.en_xxgkbh         =lightPetrolGb6.en_xxgkbh
                lightPetrolGb6Info.en_clxh           =lightPetrolGb6.en_clxh
                lightPetrolGb6Info.en_qcfl           =lightPetrolGb6.en_qcfl
                lightPetrolGb6Info.en_pfjd           =lightPetrolGb6.en_pfjd
                lightPetrolGb6Info.en_cldsbffhwz     =lightPetrolGb6.en_cldsbffhwz
                lightPetrolGb6Info.en_jcbz1          =lightPetrolGb6.en_jcbz1
                lightPetrolGb6Info.en_jcbz2          =lightPetrolGb6.en_jcbz2
                lightPetrolGb6Info.en_jcbz3          =lightPetrolGb6.en_jcbz3
                lightPetrolGb6Info.en_jcbz4          =lightPetrolGb6.en_jcbz4
                lightPetrolGb6Info.en_jcbz5          =lightPetrolGb6.en_jcbz5
                lightPetrolGb6Info.en_fdjxh          =lightPetrolGb6.en_fdjxh
                lightPetrolGb6Info.en_fdjscqy        =lightPetrolGb6.en_fdjscqy
                lightPetrolGb6Info.en_fdjcp          =lightPetrolGb6.en_fdjcp
                lightPetrolGb6Info.en_chzhqxh        =lightPetrolGb6.en_chzhqxh
                lightPetrolGb6Info.en_klbjqxh        =lightPetrolGb6.en_klbjqxh
                lightPetrolGb6Info.en_tc             =lightPetrolGb6.en_tc
                lightPetrolGb6Info.en_tc1            =lightPetrolGb6.en_tc1
                lightPetrolGb6Info.en_tgxh           =lightPetrolGb6.en_tgxh
                lightPetrolGb6Info.en_ycgqxh         =lightPetrolGb6.en_ycgqxh
                lightPetrolGb6Info.en_qzxpfkzzzxh    =lightPetrolGb6.en_qzxpfkzzzxh
                lightPetrolGb6Info.en_egrxh          =lightPetrolGb6.en_egrxh
                lightPetrolGb6Info.en_obdxh          =lightPetrolGb6.en_obdxh
                lightPetrolGb6Info.en_ecuxh          =lightPetrolGb6.en_ecuxh
                lightPetrolGb6Info.en_bsqxs          =lightPetrolGb6.en_bsqxs
                lightPetrolGb6Info.en_xyqxh          =lightPetrolGb6.en_xyqxh
                lightPetrolGb6Info.en_zyqxh          =lightPetrolGb6.en_zyqxh
                lightPetrolGb6Info.en_zlqxs          =lightPetrolGb6.en_zlqxs
                lightPetrolGb6Info.en_clsbdh         =zcinfoInstance.veh_Clsbdh
                lightPetrolGb6Info.creater_id        =loginUser.userName
                lightPetrolGb6Info.create_time       =DateUtil.getCurrentTime()
                lightPetrolGb6Info.en_fdjh          =zcinfoInstance?.veh_Fdjh
                lightPetrolGb6Info.en_zzrq           =zcinfoInstance?.veh_Fzrq
                lightPetrolGb6Info.en_jzzl           =Integer.toString(Integer.parseInt(zcinfoInstance?.veh_Zbzl)+100)

//                    ############################生成二维防伪码START##############################
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                QRpiUrl =this.newGenerateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightPetrolGb6Info.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                BarPicUrl =this.newGenerateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightPetrolGb6Info.bar_name =  BarPicUrl

                if(lightPetrolGb6Info.save(flush: true)){          //生成双燃料/轻型汽油车成功
                    def envirUpload = new EnvirUpload()
                    envirUpload.setId(lightPetrolGb6Info?.id)    //保存id，使用assign方式
                    envirUpload.en_clsbdh =  lightPetrolGb6Info?.en_clsbdh
                    envirUpload.en_xxgkbh =  lightPetrolGb6Info?.en_xxgkbh
                    envirUpload.en_scrq   =   this.convertDate(lightPetrolGb6Info?.en_zzrq)         //生产日期 ,数据格式为2017-01-01
                    envirUpload.en_ccrq   =   this.convertDate(lightPetrolGb6Info?.en_zzrq)        //出厂日期
                    envirUpload.en_gfwz   =   DictionaryValue.findByCode('enviroWebSite')?.value1
                    envirUpload.en_fdjh  =   lightPetrolGb6Info?.en_fdjh

//                       增加了车辆型号、发动机型号、车辆分类的参数的保存
                    envirUpload.en_clxh        =lightPetrolGb6Info?.en_clxh
                    envirUpload.en_fdjxh       =lightPetrolGb6Info?.en_fdjxh
                    envirUpload.en_qcfl        =lightPetrolGb6Info?.en_qcfl
                    envirUpload.en_fdjsb       =lightPetrolGb6Info?.en_fdjcp

//                      增加重型车出厂试验为 自由加速烟度 试验
                    envirUpload.en_ccsy        ='双怠速试验'

                    envirUpload.creater_id        =loginUser.userName
                    envirUpload.create_time       =DateUtil.getCurrentTime()

                    envirUpload.type='轻型汽油（国六）'

                    envirUpload.save(flush: true)
                }
                id =  lightPetrolGb6Info.id
            } else{
                id =  lightPetrolGb6Print.id
            }
        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            flag=false
        }
        def result = [id: id, QRpiUrl: QRpiUrl,BarPicUrl:BarPicUrl]
        render result as JSON
    }
    /**
     * @Description 跳转到重气环保信息随车清单
     */
    def heavyGasPrint(){
        def aaa=params
        try {
            def heavyGasInfo = HeavyGasInfo.get(params.heavyGasInfoId)  //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联  ,所以cocPrinID也是合格证信息的id
            if (!heavyGasInfo) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.cocPrnID])
                redirect(action: "findEnvirModelList" ,params: params)
                return
            }
            if(params.printOld){  //不替换打印
                //                    ############################生成二维防伪码START##############################
                def zcinfoInstance = ZCInfo.get(params.heavyGasPrintInfoId)
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                def QRpiUrl = this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                heavyGasInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                def BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                heavyGasInfo.bar_name =  BarPicUrl

                heavyGasInfo.save(flush: true)
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/heavyGasPrint',model:[heavyGasInfo: heavyGasInfo,params:params,BarPicUrl:BarPicUrl,QRpiUrl:QRpiUrl])
            }else{     //替换打印或者第一次打印
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/heavyGasPrint',model:[heavyGasInfo: heavyGasInfo,params:params,BarPicUrl:params.BarPicUrl,QRpiUrl:params?.QRpiUrl])
            }

        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            redirect(action: "findEnvirModelList",params: params)
        }

    }
    /**
     * @Description 跳转到重气环保信息随车清单
     */
    def newEnergyPrint(){
        def aaa=params
        try {
            def newEnergyInfo = NewEnergyCarInfo.get(params.newEnergyInfoId)  //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联  ,所以cocPrinID也是合格证信息的id
            if (!newEnergyInfo) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.cocPrnID])
                redirect(action: "findEnvirModelList" ,params: params)
                return
            }
            if(params.printOld){  //不替换打印
                //                    ############################生成二维防伪码START##############################
                def zcinfoInstance = ZCInfo.get(params.newEnergyPrintInfoId)
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                def QRpiUrl = this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                newEnergyInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                def BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                newEnergyInfo.bar_name =  BarPicUrl

                newEnergyInfo.save(flush: true)
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/newEnergyPrint',model:[newEnergyInfo: newEnergyInfo,params:params,BarPicUrl:BarPicUrl,QRpiUrl:QRpiUrl])
            }else{     //替换打印或者第一次打印
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/newEnergyPrint',model:[newEnergyInfo: newEnergyInfo,params:params,BarPicUrl:params.BarPicUrl,QRpiUrl:params?.QRpiUrl])
            }

        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            redirect(action: "findEnvirModelList",params: params)
        }

    }
    /**
     * @Description 跳转到轻型汽油（国六）信息随车清单
     */
    def lightPetrolGb6Print(){
        def aaa=params
        try {
            def lightPetrolGb6Info = LightPetrolGb6Info.get(params.lightPetrolGb6InfoId)  //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联  ,所以cocPrinID也是合格证信息的id
            if (!lightPetrolGb6Info) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.cocPrnID])
                redirect(action: "findEnvirModelList" ,params: params)
                return
            }
            if(params.printOld){  //不替换打印
                //                    ############################生成二维防伪码START##############################
                def zcinfoInstance = ZCInfo.get(params.lightPetrolGb6PrintInfoId)
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                def QRpiUrl = this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightPetrolGb6Info.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                def BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightPetrolGb6Info.bar_name =  BarPicUrl

                lightPetrolGb6Info.save(flush: true)
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/lightPetrolGb6Print',model:[lightPetrolGb6Info: lightPetrolGb6Info,params:params,BarPicUrl:BarPicUrl,QRpiUrl:QRpiUrl])
            }else{     //替换打印或者第一次打印
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/lightPetrolGb6Print',model:[lightPetrolGb6Info: lightPetrolGb6Info,params:params,BarPicUrl:params.BarPicUrl,QRpiUrl:params?.QRpiUrl])
            }

        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            redirect(action: "findEnvirModelList",params: params)
        }

    }
    /**
     * @Description 打印生成轻型柴油车国四/轻型柴油车国五信息随车清单
     * @CreateTime 2016-12-22 by zhuwei  返回生成数据id/防伪二维码图片url/车辆识别代号条形码
     */
    def jsonLightDieselUpdate(){
        def aa=params
        String id =""
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def flag=true
        def idStr=params.id//轻型柴油车国四/轻型柴油车国五信息库id
        def zcinfoIDStr = params.zcinfoID///
        def usertype =params.usertype
        def QRpiUrl
        def BarPicUrl
        try{
            def lightDiesel = LightDiesel.get(idStr)
            def zcinfoInstance= ZCInfo.get(zcinfoIDStr)
            LightDieselInfo lightDieselPrint = LightDieselInfo.get(zcinfoInstance.id)
            if (!lightDieselPrint){
                def lightDieselInfo = new LightDieselInfo()
                lightDieselInfo.setId(zcinfoInstance.id) //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联
                lightDieselInfo.en_xxgkbh         =lightDiesel.en_xxgkbh
                lightDieselInfo.en_clxh           =lightDiesel.en_clxh
                lightDieselInfo.en_qcfl           =lightDiesel.en_qcfl
                lightDieselInfo.en_pfjd           =lightDiesel.en_pfjd
                lightDieselInfo.en_cldsbffhwz     =lightDiesel.en_cldsbffhwz
                lightDieselInfo.en_jcbz1          =lightDiesel.en_jcbz1
                lightDieselInfo.en_jcbz2          =lightDiesel.en_jcbz2
                lightDieselInfo.en_jcbz3          =lightDiesel.en_jcbz3
                lightDieselInfo.en_jcbz4          =lightDiesel.en_jcbz4
                lightDieselInfo.en_fdjxh            =lightDiesel.en_fdjxh
                lightDieselInfo.en_hclzzxh          =lightDiesel.en_hclzzxh
                lightDieselInfo.en_tc             =lightDiesel.en_tc
                lightDieselInfo.en_pybxh          =lightDiesel.en_pybxh
                lightDieselInfo.en_zyqxh          =lightDiesel.en_zyqxh
                lightDieselInfo.en_pyqxh          =lightDiesel.en_pyqxh
                lightDieselInfo.en_ecuxh          =lightDiesel.en_ecuxh
                lightDieselInfo.en_obdxh          =lightDiesel.en_obdxh
                lightDieselInfo.en_egrxh          =lightDiesel.en_egrxh
                lightDieselInfo.en_zlqxh          =lightDiesel.en_zlqxh
                lightDieselInfo.en_bsxxs          =lightDiesel.en_bsxxs
                lightDieselInfo.en_xyqxh          =lightDiesel.en_xyqxh
                lightDieselInfo.en_iuprjcgn       =lightDiesel.en_iuprjcgn
                lightDieselInfo.type              =lightDiesel.type



                lightDieselInfo.creater_id        =loginUser.userName
                lightDieselInfo.create_time       =DateUtil.getCurrentTime()

                lightDieselInfo.en_vin   = zcinfoInstance?.veh_Clsbdh
                lightDieselInfo.en_zzrq     = zcinfoInstance?.veh_Fzrq

//                    ############################生成二维防伪码START##############################
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDieselInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDieselInfo.bar_name =  BarPicUrl
                if(lightDieselInfo.save(flush: true)){          //生成双燃料/轻型汽油车成功
                    def envirUpload = new EnvirUpload()
                    envirUpload.setId(lightDieselInfo?.id)    //保存id，使用assign方式
                    envirUpload.en_clsbdh =  lightDieselInfo?.en_vin
                    envirUpload.en_xxgkbh =  lightDieselInfo?.en_xxgkbh
                    envirUpload.en_scrq   =   this.convertDate(lightDieselInfo?.en_zzrq)         //生产日期 ,数据格式为2017-01-01
                    envirUpload.en_ccrq   =   this.convertDate(lightDieselInfo?.en_zzrq)        //出厂日期
                    envirUpload.en_gfwz   =   DictionaryValue.findByCode('enviroWebSite')?.value1

//                    envirUpload.en_fdjsb =   lightDualInfo?.en_cp              //轻型车可以不保存发动机商标和发动机生产厂地址
//                    envirUpload.en_fdjsccdz =   lightDualInfo?.en_sccdz

//                       增加了车辆型号、发动机型号、车辆分类的参数的保存
                    envirUpload.en_clxh        =lightDieselInfo?.en_clxh
                    envirUpload.en_fdjxh       =lightDieselInfo?.en_fdjxh
                    envirUpload.en_qcfl        =lightDieselInfo?.en_qcfl

//                      增加重型车出厂试验为 自由加速烟度 试验
                    envirUpload.en_ccsy        ='双怠速试验'

                    envirUpload.creater_id        =loginUser.userName
                    envirUpload.create_time       =DateUtil.getCurrentTime()
                    if(lightDieselInfo.type=='0'){  //轻型双燃料
                        envirUpload.type='轻型柴油国四'
                    }else if(lightDieselInfo.type=='1'){  //轻型汽油
                        envirUpload.type='轻型柴油国五'
                    }
                    envirUpload.save(flush: true)
                }
                id =  lightDieselInfo.id
            } else{
                id =  lightDieselPrint.id
            }
        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            flag=false
        }
        def result = [id: id, QRpiUrl: QRpiUrl,BarPicUrl:BarPicUrl]
        render result as JSON
    }
    /**
     * @Description 删除已经存在的重型柴油车/城市车辆的数据
     * @CreateTime 2016-12-22 by zhuwei
     * @Update by zhuwei 2017-01-06 在删除重型柴油车环保清单数据时，也级联删除环保清单上传表中的数据
     */
    def jsonDelHeavyDieselPr(){
        def msg = message(code: 'default.deleted.simple.message')
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def heavyDieselInfoPrint = HeavyDieselInfo.get(zcinfoIDStr)
        if (!heavyDieselInfoPrint) {
            return
        }
        try {
            if(!heavyDieselInfoPrint.delete(flush: true)){    //如果删除成功
                println('删除环保随车清单成功')
                def envirUpload = EnvirUpload.findByEn_clsbdh(heavyDieselInfoPrint?.en_clsbdh)
                envirUpload.delete(flush: true)
                println('删除环保随车清单上传数据成功')
            }
        }catch (Exception e) {
            flag = false
        }
        if(!flag){
            msg = message(code: 'default.not.simple.deleted.message')
        }
        render msg
    }
    /**
     * @Description 删除已经存在的轻型柴油车的数据
     * @CreateTime 2016-12-22 by zhuwei
     */
    def jsonDellightDieselPr(){
        def msg = message(code: 'default.deleted.simple.message')
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def lightDieselInfoPrint = LightDieselInfo.get(zcinfoIDStr)
        if (!lightDieselInfoPrint) {
            return
        }
        try {
            if(!lightDieselInfoPrint.delete(flush: true)){    //如果删除成功
                println('删除环保随车清单成功')
                def envirUpload = EnvirUpload.findByEn_clsbdh(lightDieselInfoPrint?.en_vin)
                envirUpload.delete(flush: true)
                println('删除环保随车清单上传数据成功')
            }
        }catch (Exception e) {
            flag = false
        }
        if(!flag){
            msg = message(code: 'default.not.simple.deleted.message')
        }
        render msg
    }
    /**
     * @Description 删除已经存在的重型燃气车数据
     * @CreateTime 2016-2-17 by Qj
     */
    def jsonDelHeavyGasPr(){
        def msg = message(code: 'default.deleted.simple.message')
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def heavyGasInfoPrint = HeavyGasInfo.get(zcinfoIDStr)
        if (!heavyGasInfoPrint) {
            return
        }
        try {
            if(!heavyGasInfoPrint.delete(flush: true)){    //如果删除成功
                println('删除环保随车清单成功')
                def envirUpload = EnvirUpload.findByEn_clsbdh(heavyGasInfoPrint?.en_vin)
                envirUpload.delete(flush: true)
                println('删除环保随车清单上传数据成功')
            }
        }catch (Exception e) {
            flag = false
        }
        if(!flag){
            msg = message(code: 'default.not.simple.deleted.message')
        }
        render msg
    }
    /**
     * @Description 删除已经存在的新能源汽车数据
     * @CreateTime 2016-2-17 by Qj
     */
    def jsonDelNewEnergyPr(){
        def msg = message(code: 'default.deleted.simple.message')
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def newEnergyInfoPrint = NewEnergyCarInfo.get(zcinfoIDStr)
        if (!newEnergyInfoPrint) {
            return
        }
        try {
            if(!newEnergyInfoPrint.delete(flush: true)){    //如果删除成功
                println('删除环保随车清单成功')
                def envirUpload = EnvirUpload.findByEn_clsbdh(newEnergyInfoPrint?.en_vin)
                envirUpload.delete(flush: true)
                println('删除环保随车清单上传数据成功')
            }
        }catch (Exception e) {
            flag = false
        }
        if(!flag){
            msg = message(code: 'default.not.simple.deleted.message')
        }
        render msg
    }

    /**
     * @Description 删除已经存在的国六轻型汽油车数据
     * @CreateTime 2016-2-17 by Qj
     */
    def jsonDelLightPetrolGb6Pr(){
        def msg = message(code: 'default.deleted.simple.message')
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def lightPetrolGb6InfoPrint = LightPetrolGb6Info.get(zcinfoIDStr)
        if (!lightPetrolGb6InfoPrint) {
            return
        }
        try {
            if(!lightPetrolGb6InfoPrint.delete(flush: true)){    //如果删除成功
                println('删除环保随车清单成功')
                def envirUpload = EnvirUpload.findByEn_clsbdh(lightPetrolGb6InfoPrint?.en_vin)
                envirUpload.delete(flush: true)
                println('删除环保随车清单上传数据成功')
            }
        }catch (Exception e) {
            flag = false
        }
        if(!flag){
            msg = message(code: 'default.not.simple.deleted.message')
        }
        render msg
    }

    /**
     * @Description 跳转到重型柴油车/城市车辆环保信息随车清单
     */
    def heavyDieselPrint(){
        def aaa=params
        try {
            def heavyDieselInfo = HeavyDieselInfo.get(params.heavyDieselInfoId)  //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联  ,所以cocPrinID也是合格证信息的id
            if (!heavyDieselInfo) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.cocPrnID])
                redirect(action: "findEnvirModelList" ,params: params)
                return
            }
            if(params.printOld){  //不替换打印
                //                    ############################生成二维防伪码START##############################
                def zcinfoInstance = ZCInfo.get(params.heavyDieselInfoId)
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                def QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                heavyDieselInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                def BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                heavyDieselInfo.bar_name =  BarPicUrl

                heavyDieselInfo.save(flush: true)
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/heavyDieselprint',model:[heavyDieselInfo: heavyDieselInfo,params:params,BarPicUrl:BarPicUrl,QRpiUrl:QRpiUrl])
            }else{     //替换打印或者第一次打印
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/heavyDieselprint',model:[heavyDieselInfo: heavyDieselInfo,params:params,BarPicUrl:params.BarPicUrl,QRpiUrl:params?.QRpiUrl])
            }

        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            redirect(action: "findEnvirModelList",params: params)
        }

    }
    /**
     * @Description 跳转到轻型柴油车环保信息随车清单
     */
    def lightDieselPrint(){
        def aaa=params
        try {
            def lightDieselInfo = LightDieselInfo.get(params.lightDieselInfoId)  //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联  ,所以cocPrinID也是合格证信息的id
            if (!lightDieselInfo) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.cocPrnID])
                redirect(action: "findEnvirModelList" ,params: params)
                return
            }
            if(params.printOld){  //不替换打印
                //                    ############################生成二维防伪码START##############################
                def zcinfoInstance = ZCInfo.get(params.lightDieselInfoId)
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                def QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDieselInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                def BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDieselInfo.bar_name =  BarPicUrl

                lightDieselInfo.save(flush: true)
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/lightDieselprint',model:[lightDieselInfo: lightDieselInfo,params:params,BarPicUrl:BarPicUrl,QRpiUrl:QRpiUrl])
            }else{     //替换打印或者第一次打印
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/lightDieselprint',model:[lightDieselInfo: lightDieselInfo,params:params,BarPicUrl:params.BarPicUrl,QRpiUrl:params?.QRpiUrl])
            }

        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            redirect(action: "findEnvirModelList",params: params)
        }

    }

    /**
     * @Description 打印生成双燃料/轻型汽油车车辆环保信息随车清单
     * @CreateTime 2016-12-22 by zhuwei  返回生成数据id/防伪二维码图片url/车辆识别代号条形码
     * @UPdate 在保存时，将双燃料环保清单数据级联保存到 envirUpload表中
     * @UpdateTime 2017-01-07 by zhuwei
     */
    def jsonLightDualUpdate(){
        def aa=params
        String id =""
        User loginUser=session.getAttribute(ConstantsUtil.LOGIN_USER)
        def flag=true
        def idStr=params.id//重型柴油车/城市车辆环保信息库id
        def zcinfoIDStr = params.zcinfoID///
        def usertype =params.usertype
        def QRpiUrl
        def BarPicUrl
        try{
            def lightDual = LightDual.get(idStr)
            def zcinfoInstance= ZCInfo.get(zcinfoIDStr)
            LightDualInfo lightDualInfoPrint = LightDualInfo.get(zcinfoInstance.id)
            if (!lightDualInfoPrint){
                def lightDualInfo = new LightDualInfo()
                lightDualInfo.setId(zcinfoInstance.id) //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联
                lightDualInfo.en_xxgkbh         =lightDual.en_xxgkbh
                lightDualInfo.en_clxh           =lightDual.en_clxh
                lightDualInfo.en_qcfl           =lightDual.en_qcfl
                lightDualInfo.en_pfjd           =lightDual.en_pfjd
                lightDualInfo.en_cldsbffhwz         =lightDual.en_cldsbffhwz

                lightDualInfo.en_jcbz1          =lightDual.en_jcbz1
                lightDualInfo.en_jcbz2          =lightDual.en_jcbz2
                lightDualInfo.en_jcbz3          =lightDual.en_jcbz3
                lightDualInfo.en_jcbz4          =lightDual.en_jcbz4

                lightDualInfo.en_fdjxh          =lightDual.en_fdjxh
                lightDualInfo.en_chzhqxh        =lightDual.en_chzhqxh
                lightDualInfo.en_tc             =lightDual.en_tc
                lightDualInfo.en_rlzfkzzzxh     =lightDual.en_rlzfkzzzxh
                lightDualInfo.en_ycgqxh         =lightDual.en_ycgqxh
                lightDualInfo.en_qzxpfkzzzxh    =lightDual.en_qzxpfkzzzxh
                lightDualInfo.en_egrxh          =lightDual.en_egrxh
                lightDualInfo.en_obdxh          =lightDual.en_obdxh
                lightDualInfo.en_iuprjcgn       =lightDual.en_iuprjcgn
                lightDualInfo.en_ecuxh          =lightDual.en_ecuxh
                lightDualInfo.en_rqhhqxh        =lightDual.en_rqhhqxh
                lightDualInfo.en_rqpsdyxh       = lightDual.en_rqhhqxh
                lightDualInfo.en_bsqxs          = lightDual.en_bsqxs
                lightDualInfo.en_xyqxh          = lightDual.en_xyqxh


                lightDualInfo.en_zyqxh          =lightDual.en_zyqxh
                lightDualInfo.en_zlqxs          =lightDual.en_zlqxs

                lightDualInfo.en_zyqxh          =lightDual.en_zyqxh
                lightDualInfo.en_zlqxs          =lightDual.en_zlqxs
                lightDualInfo.en_zyqxh          =lightDual.en_zyqxh
                lightDualInfo.en_zlqxs          =lightDual.en_zlqxs
                lightDualInfo.type              =lightDual.type


                lightDualInfo.creater_id        =loginUser.userName
                lightDualInfo.create_time       =DateUtil.getCurrentTime()

                lightDualInfo.en_clsbdh        = zcinfoInstance?.veh_Clsbdh
                lightDualInfo.en_zzrq          = zcinfoInstance?.veh_Fzrq
                lightDualInfo.en_fdjh          = zcinfoInstance?.veh_Fdjh//保存上发动机编号字段



//                    ############################生成二维防伪码START##############################
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDualInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDualInfo.bar_name =  BarPicUrl

                if(lightDualInfo.save(flush: true)){          //生成双燃料/轻型汽油车成功
                    def envirUpload = new EnvirUpload()
                    envirUpload.setId(lightDualInfo?.id)    //保存id，使用assign方式
                    envirUpload.en_clsbdh =   lightDualInfo?.en_clsbdh
                    envirUpload.en_xxgkbh =   lightDualInfo?.en_xxgkbh
                    envirUpload.en_fdjh   =   lightDualInfo?.en_fdjh
                    envirUpload.en_scrq   =   this.convertDate(lightDualInfo?.en_zzrq)         //生产日期 ,数据格式为2017-01-01
                    envirUpload.en_ccrq   =   this.convertDate(lightDualInfo?.en_zzrq)        //出厂日期
                    envirUpload.en_gfwz   =   DictionaryValue.findByCode('enviroWebSite')?.value1

//                    envirUpload.en_fdjsb =   lightDualInfo?.en_cp              //轻型车可以不保存发动机商标和发动机生产厂地址
//                    envirUpload.en_fdjsccdz =   lightDualInfo?.en_sccdz

//                       增加了车辆型号、发动机型号、车辆分类的参数的保存
                    envirUpload.en_clxh        =lightDualInfo?.en_clxh
                    envirUpload.en_fdjxh       =lightDualInfo?.en_fdjxh
                    envirUpload.en_qcfl        =lightDualInfo?.en_qcfl

//                      增加重型车出厂试验为 自由加速烟度 试验
                    envirUpload.en_ccsy        ='双怠速试验'

                    envirUpload.creater_id        =loginUser.userName
                    envirUpload.create_time       =DateUtil.getCurrentTime()
                    if(lightDualInfo.type=='0'){  //轻型双燃料
                        envirUpload.type='轻型双燃料'
                    }else if(lightDualInfo.type=='1'){  //轻型汽油
                        envirUpload.type='轻型汽油'
                    }
                    envirUpload.save(flush: true)
                }
                id =  lightDualInfo.id
            } else{
                id =  lightDualInfoPrint.id
            }
        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            flag=false
        }
        def result = [id: id, QRpiUrl: QRpiUrl,BarPicUrl:BarPicUrl]
        render result as JSON
    }

    /**
     * @Description 跳转到双燃料/轻型汽油车环保信息随车清单
     */
    def lightDualPrint(){
        def aaa=params
        try {
            def lightDualInfo = LightDualInfo.get(params.lightDualPrintInfoId)  //使用合格证证书的ID作为已打印的一致性证书的主键 以此作为关联  ,所以cocPrinID也是合格证信息的id
            if (!lightDualInfo) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.cocPrnID])
                redirect(action: "findEnvirModelList" ,params: params)
                return
            }
            if(params.printOld){  //不替换打印
                //                    ############################生成二维防伪码START##############################
                def zcinfoInstance = ZCInfo.get(params.lightDualPrintInfoId)
                def   veh_Clsbdh   = zcinfoInstance?.veh_Clsbdh
                def QRpiUrl =this.generateQR(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDualInfo.qr_name =  QRpiUrl

//                    ############################生成条形VIN码START##############################
                def BarPicUrl =this.generateBar(veh_Clsbdh)  //使用第三方插件生成二维码,f返回文件URL
                lightDualInfo.bar_name =  BarPicUrl

                lightDualInfo.save(flush: true)
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/lightDualPrint',model:[lightDualInfo: lightDualInfo,params:params,BarPicUrl:BarPicUrl,QRpiUrl:QRpiUrl])
            }else{     //替换打印或者第一次打印
                render(view:'/cn/com/wz/vehcert/environment/envirodeal/lightDualPrint',model:[lightDualInfo: lightDualInfo,params:params,BarPicUrl:params.BarPicUrl,QRpiUrl:params?.QRpiUrl])
            }

        }catch(Exception e){
            e.cause?e.cause.printStackTrace():e.printStackTrace()
            redirect(action: "findEnvirModelList",params: params)
        }

    }

    /**
     * @Description 删除已经存在的重型柴油车/城市车辆的数据
     * @CreateTime 2016-12-22 by zhuwei
     * @Update 在删除双燃料是，在上传表中级联删除envirUpload表中的数据
     * @UpdateTime 2017-01-07 by zhuwei
     */
    def jsonDelLightDualPr(){
        def msg = message(code: 'default.deleted.simple.message')
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def lightDualInfoInfoPrint = LightDualInfo.get(zcinfoIDStr)
        if (!lightDualInfoInfoPrint) {
            return
        }
        try {

            if(!lightDualInfoInfoPrint.delete(flush: true)){
                println('删除成功')
                def envirUpload = EnvirUpload.findByEn_clsbdh(lightDualInfoInfoPrint?.en_clsbdh)
                envirUpload.delete(flush: true)
                println('删除环保随车清单上传数据成功')

            }
        }catch (Exception e) {
            flag = false
        }
        if(!flag){
            msg = message(code: 'default.not.simple.deleted.message')
        }
        render msg
    }

    /**
     * @Description 删除已经存在的重型柴油车/城市车辆的数据
     * @CreateTime 2016-12-22 by zhuwei
     */
    def jsonSearchLightDualPrn(){
        int status = 0
        def flag=true
        def zcinfoIDStr = params.zcinfoID
        def envPrnInstance = LightDualInfo.get(zcinfoIDStr)
        if (!envPrnInstance) {
            status = 0
        }
        try {
            if (envPrnInstance.id!=null){
                if(envPrnInstance.status==1){ //上传成功
                    status =2
                }else{    //未上传或上传失败
                    status =1
                }

            }
        }catch (Exception e) {
            status = 0
        }
        render status
    }

}
