package cn.com.wz.vehcert.coc

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.UploadUtil
import cn.com.wz.parent.base.BaseController
import cn.com.wz.parent.system.user.User
import parent.poi.event.ExcelReaderUtil
import parent.poi.event.IRowReader
import parent.poi.event.RowReader
import cn.com.wz.vehcert.carstorage.Temp
import cn.com.wz.vehcert.zcinfo.ZCInfo
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.pdf.*
import grails.converters.JSON
import parent.poi.ExcelUtils

/**
 *
 * @Update  huxx 2013-10-19 公告信息的更新时间以及创建时间使用数据库的默认当前时间，temp的inserttime也使用数据库的当前时间
 */
class CocCarStorageController extends BaseController{
    def dataSource;
    def exportService;
    def sqlService
    def cocCarStorageService
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    /**
     * 默认执行动作
     */
    def index() {
        render(view:'/cn/com/wz/vehcert/coc/carstorage/index')
    }
    /**
     *@Description 跳转到对象list页面
     *@param
     *@return
     *@Auther mike
     *@createTime
     */
    def list() {
        render(view:'/cn/com/wz/vehcert/coc/carstorage/index')
    }
    /**
     *@Description 模板下载
     *@Auther mike
     *@createTime 2013-07-26
     */
    def downloadTemplate(){
        try {


            def encodedFilename = URLEncoder.encode(("一致性证书模板"), "UTF-8")
            def filename = ""
            def userAgent = request.getHeader("User-Agent")
            if (userAgent =~ /MSIE [4-8]/) {
                // IE < 9 do not support RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename=\"${encodedFilename}.xls\""
            }
            else {
                // Use RFC 6266 (RFC 2231/RFC 5987)
                filename = "filename*=UTF-8''${encodedFilename}.xls"
            }
            response.setHeader("Content-disposition", "attachment;${filename}");
            response.contentType = grailsApplication.config.grails.mime.types['excel']
            def fos=response.outputStream
            String path=servletContext.getRealPath('/data/vehcert/');
            String filepath =path+"/coctemplate.xls";
            def inputStream = new FileInputStream(filepath)
            byte[] buffer = new byte[1024]
            int i = -1
            while ((i = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, i)
            }
            fos.flush()
            fos.close()
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;;
    }
    /**
     *@Description 转向批量创建页面
     *@Auther mike
     *@createTime 2013-07-26
     */
    def batch() {
        render(view:'/cn/com/wz/parent/common/import',model:[redirectUrl:"${createLink(controller: 'cocCarStorage',action: 'upload')}",tabId:params.tabId])
    }
    /**
     *@Description 数据导出成excel
     *@Auther mike
     *@createTime 2013-07-26
     */
    def exportexcel(){
        def datas =[]
        def lst=[]
        def content=[]
        try{
            if(params?.format && params.format != "html"){
                def encodedFilename = URLEncoder.encode(("一致性证书信息"), "UTF-8")
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
                def out=response.outputStream
                ExcelUtils excelOp=new ExcelUtils(grailsApplication.config.server.file.encode);
                Map labels =['coc_czsl':'车轴数量','coc_clsl':'车轮数量','coc_qdzwz':'驱动轴位置','coc_zj':'轴距','coc_qyzqzj':'牵引座前置距','coc_lj':'轮距','coc_cd':'长度','coc_zqdyqyzzjl':'最前端与牵引装置的距离'
                        ,'coc_zzqycd':'装载区域长度','coc_kd':'宽度','coc_gd':'高度','coc_tymj':'车辆在地面上的投影面积','coc_qx':'前悬','coc_hx':'后悬','coc_jjj':'接近角','coc_lqj':'离去角','coc_xsxdcsclzl':'行驶下带车身的车辆质量'
                        ,'coc_edzzl':'额定总质量','coc_zlzhfp':'该质量的轴荷分配','coc_zzllyxsh':'载质量利用系数','coc_czyxzdzl':'车轴允许的最大质量','coc_kssczwz':'可伸缩车轴的位置','coc_kczczwz':'可承载车轴位置','coc_cdzdyxzh':'车顶最大允许载荷'
                        ,'coc_qygsgc':'牵引杆式挂车','coc_bgc':'半挂车','coc_zjjgc':'中间轴挂车','coc_gczdzl':'挂车的最大质量','coc_zdzhzl':'最大组合质量','coc_jcdczdczfh':'接触点处的最大垂直负荷','coc_fdjzzsmc':'发动机制造商名称'
                        ,'coc_fdjxh':'发动机型号','coc_fdjgzyl':'发动机工作原理','coc_zjps':'直接喷射','coc_qgsl':'汽缸数量','coc_qgplxs':'气缸排列形式','coc_pl':'排量','coc_rlzl':'燃料种类','coc_zdjgl':'最大净功率','coc_dyfdjzs':'对应的发动机转速'
                        ,'coc_lhqxs':'离合器形式','coc_bsqzs':'变速器形式','coc_sb':'速比','coc_zcdb':'主传动比','coc_ltgg':'轮胎规格','coc_sfazkqxg':'是否安装有空气悬挂','coc_sfzykqxgzz':'是否装有空气悬挂的装置','coc_gbthps':'钢板弹簧的片数'
                        ,'coc_zxzlxs':'转向助力形式','coc_zdzzjysm':'制动装置简要说明','coc_zdxtgqgnyl':'制动系统供气管内压力','coc_csxs':'车身形式','coc_clys':'车辆颜色','coc_gnyxrj':'罐体内有效容积','coc_hxcd':'货箱长度','coc_hxkd':'货箱宽度'
                        ,'coc_hxgd':'货箱高度','coc_qzjzdljnl':'起重机的最大力矩能力','coc_cmsl':'车门数量','coc_cmgz':'车门构造','coc_zws':'座位数','coc_cccno':'CCC证书编号','coc_sybgno':'或实验报告编号','coc_zgcs':'最高车速','coc_sj':'声级'
                        ,'coc_pqpfw':'排气排放物','coc_co2':'CO2排放','coc_jgyqsybg':'结构要求的试验报告','coc_wxhwysjb':'危险货物运输的级别','coc_dwjgyqbh':'动物的结构要求的编号','coc_mxdwysjb':'某些动物运输的级别','coc_note':'备注','coc_zsno':'H证书编号'
                        ,'coc_clsccmc':'H车辆生产厂名称','coc_clzzg':'H车辆制造国','coc_cxxldhmc':'H车型系列代号名称','coc_dydhmc':'H单元代号名称','coc_cxdhmc':'H车型代号名称','coc_cxmc':'H车型名称','coc_clzwpp':'H车辆中文品牌','coc_cllb':'H车辆类别'
                        ,'coc_zzsmc':'H制造商的名称','coc_zzsdz':'H制造商的地址','coc_fdmpwz':'H法定名牌的位置','coc_clsbdh':'H车辆识别代号','coc_sbhdkwz':'H识别号的打刻位置','coc_fdjbh':'H发动机编号','coc_fdjbhdkwz':'H发动机编号的打刻位置','coc_ccczsh':'HCCC证书号'
                        ,'coc_clxh':'H车辆型号','coc_clmc':'车辆名称','coc_rq':'H日期','coc_new_energy':'是否新能源车型','coc_ddjgzdy':'电动机工作电压','coc_dldcxh':'动力电池型号','coc_dldceddy':'动力电池额定电压','coc_dldcedrl':'动力电池额定容量'
                        ,'coc_dldcsccmc':'动力电池生产厂名称','coc_abs':'是否带防抱死系统','coc_scqydz':'生产企业地址','coc_clzl':'车辆种类','coc_jccccno':'基础车辆一致性证书编号','coc_jcclxh':'基础车辆型号'
                        ,'coc_jccllb':'基础车辆类别','coc_zzjdcccno':'最终阶段车辆CCC证书编号','coc_zzjdqfrq':'最终阶段车辆CCC证书编号签发日期','coc_zdyxzzl':'最大允许总质量','coc_sjbzh':'CCC认证标准号及对应的实施阶段','coc_dzzs':'定置噪声','coc_fdjzs':'对应的发动机转速'
                        ,'coc_cwzs':'加速行驶车外噪音','coc_pfwbzh':'排气排放物标准号及对应的实施阶段','coc_ytrl':'实验用液体燃料','coc_ytrl_co':'液体燃料CO排放量','coc_ytrl_nox':'液体燃料NOX排放量','coc_ytrl_yd':'液体燃料烟度','coc_ytrl_hc ':'试验用液体燃料HC'
                        ,'coc_ytrl_hcnox':'试验用液体燃料HC+NOx','coc_ytrl_pn':'试验用液体燃料微粒物/PN','coc_qtrl':'实验用气体燃料','coc_qtrl_co':'气体燃料CO排放量'
                        ,'coc_qtrl_nmhc':'气体燃料NMHC排放量','coc_qtrl_ch4':'气体燃料CH4排放量','coc_qtrl_nox':'试验用气体燃料NOx','coc_qtrl_thc':'试验用气体燃料THC','coc_qtrl_pn':'试验用气体燃料PN','coc_co2pflbzh':'CO2排放量/燃料消耗量CCC认证引用的标准号'
                        ,'coc_co2_sq':'市区CO2排放量','coc_co2_sj':'市郊CO2排放量','coc_co2_zh':'综合CO2排放量','coc_rlxh_sq':'市区燃料消耗量'
                        ,'coc_rlxh_sj':'市郊燃料消耗量','coc_rlxh_zh':'综合燃料消耗量', 'coc_clzclx':'车辆注册类型', 'coc_yzxzsbh':'一致性证书编号']
                def cel = {
                    if(params.coc_clxh){
                        eq("coc_clxh","${params.coc_clxh}")
                    }
                    if(params.coc_clmc){
                        like("coc_clmc","%${params.coc_clmc}%")
                    }
                }
                datas = CocCarStorage.createCriteria().list(params,cel)
                lst=[]
                datas.each {
                    def m=[:]
                    it.properties.each {
                        if(it.value==''){
                            it.value='NULL'
                        }
                        m."${it.key}"=it.value
                    }
                    lst.add(m)
                }

                def m=[]
                m.add(labels)
                content.add(lst)

                excelOp.preWriteExcel(out,null,m,["一致性证书信息"],content)

                session.setAttribute('compFlag',"success")

                out.flush()
                out.close()
            }
        }catch(Exception e){
            e.cause?.printStackTrace()
            session.setAttribute('compFlag',"error")
        }finally{
            datas.clear()
            lst.clear()
            content.clear()
        }

    }

    /**
     *@Description 上传并解析
     *@Auther mike
     *@createTime 2013-07-26
     * @update 修改了上传一致性证书事获取到realPath错误
     * @uodateTime 2014-03-24
     * @Update 一致性证书导入添加一致性证书编号coc_yzxzsbh字段的导入
     * @UpdateTime 2015-03-02 zhuwei
     */
    def upload(String filePath) {
        //保存附件信息
        def realPath=""
        def str=filePath.split (":_:")
        if(str?.length>=2){
            //获取文件存储的相对路径(不包含文件名)
            def relativePath=UploadUtil.getRelativePathByUploadType(str[0])
            //将附件信息保存到数据库
            for(int i=1;i<str.length;i++){
                def names=str[i].split (":;:")
                if(names.length==3){
                    def saveFileName=names[0]
                    String fileName=names[1]
//                    String realPath=UploadUtil.getRelativeFile(servletContext, relativePath, saveFileName)
                    realPath= UploadUtil.getRootDir(servletContext)+names[2]

                    def count=0                 //实际存储成功的记录数
                    def failList=[]             //保存失败记录
                    def user=getCurrentUser()
                    def lst=[]
                    def totalSize=0
                    StringBuilder errorMessages=new StringBuilder()
                    List<String> labels=new ArrayList<String>();
                    try{
                        labels.add("coc_czsl");labels.add("coc_clsl");labels.add("coc_qdzwz");labels.add("coc_zj");labels.add("coc_qyzqzj");
                        labels.add("coc_lj");labels.add("coc_cd");labels.add("coc_zqdyqyzzjl");labels.add("coc_zzqycd");labels.add("coc_kd");
                        labels.add("coc_gd");labels.add("coc_tymj");labels.add("coc_qx");labels.add("coc_hx");labels.add("coc_jjj");labels.add("coc_lqj");
                        labels.add("coc_xsxdcsclzl");
                        labels.add("coc_edzzl");labels.add("coc_zlzhfp");labels.add("coc_zzllyxsh");labels.add("coc_czyxzdzl");labels.add("coc_kssczwz");
                        labels.add("coc_kczczwz");labels.add("coc_cdzdyxzh");labels.add("coc_qygsgc");labels.add("coc_bgc");labels.add("coc_zjjgc");
                        labels.add("coc_gczdzl");labels.add("coc_zdzhzl");
                        labels.add("coc_jcdczdczfh");labels.add("coc_fdjzzsmc");labels.add("coc_fdjxh");labels.add("coc_fdjgzyl");labels.add("coc_zjps");
                        labels.add("coc_qgsl");labels.add("coc_qgplxs");labels.add("coc_pl");labels.add("coc_rlzl");labels.add("coc_zdjgl");labels.add("coc_dyfdjzs");
                        labels.add("coc_lhqxs");labels.add("coc_bsqzs");labels.add("coc_sb");labels.add("coc_zcdb");labels.add("coc_ltgg");
                        labels.add("coc_sfazkqxg");labels.add("coc_sfzykqxgzz");labels.add("coc_gbthps");labels.add("coc_zxzlxs");
                        labels.add("coc_zdzzjysm");labels.add("coc_zdxtgqgnyl");labels.add("coc_csxs");labels.add("coc_clys");labels.add("coc_gnyxrj");
                        labels.add("coc_hxcd");labels.add("coc_hxkd");labels.add("coc_hxgd");labels.add("coc_qzjzdljnl");labels.add("coc_cmsl");
                        labels.add("coc_cmgz");labels.add("coc_zws");labels.add("coc_cccno");labels.add("coc_sybgno");labels.add("coc_zgcs");
                        labels.add("coc_sj");labels.add("coc_pqpfw");labels.add("coc_co2");labels.add("coc_jgyqsybg");labels.add("coc_wxhwysjb");
                        labels.add("coc_dwjgyqbh");labels.add("coc_mxdwysjb");labels.add("coc_note");labels.add("coc_zsno");labels.add("coc_clsccmc");
                        labels.add("coc_clzzg");labels.add("coc_cxxldhmc");labels.add("coc_dydhmc");labels.add("coc_cxdhmc");labels.add("coc_cxmc");
                        labels.add("coc_clzwpp");labels.add("coc_cllb");labels.add("coc_zzsmc");labels.add("coc_zzsdz");labels.add("coc_fdmpwz");
                        labels.add("coc_clsbdh");labels.add("coc_sbhdkwz");labels.add("coc_fdjbh");labels.add("coc_fdjbhdkwz");
                        labels.add("coc_ccczsh");labels.add("coc_clxh");labels.add("coc_clmc");labels.add("coc_rq");labels.add("coc_new_energy");
                        labels.add("coc_ddjgzdy");labels.add("coc_dldcxh");labels.add("coc_dldceddy");labels.add("coc_dldcedrl");labels.add("coc_dldcsccmc");labels.add("coc_abs");labels.add("coc_scqydz");labels.add("coc_clzl");
                        labels.add("coc_jccccno");labels.add("coc_jcclxh");labels.add("coc_jccllb");labels.add("coc_zzjdcccno");
                        labels.add("coc_zzjdqfrq");labels.add("coc_zdyxzzl");labels.add("coc_sjbzh");labels.add("coc_dzzs");
                        labels.add("coc_fdjzs");labels.add("coc_cwzs");labels.add("coc_pfwbzh");labels.add("coc_ytrl");
                        labels.add("coc_ytrl_co");labels.add("coc_ytrl_nox");labels.add("coc_ytrl_yd");labels.add("coc_ytrl_hc");
                        labels.add("coc_ytrl_hcnox");labels.add("coc_ytrl_pn");labels.add("coc_qtrl");labels.add("coc_qtrl_co");
                        labels.add("coc_qtrl_nmhc");labels.add("coc_qtrl_ch4");labels.add("coc_qtrl_nox");labels.add("coc_qtrl_thc");
                        labels.add("coc_qtrl_pn");labels.add("coc_co2pflbzh");labels.add("coc_co2_sq");labels.add("coc_co2_sj");
                        labels.add("coc_co2_zh");labels.add("coc_rlxh_sq");labels.add("coc_rlxh_sj");labels.add("coc_rlxh_zh");
                        labels.add("coc_clzclx");labels.add("coc_yzxzsbh");
                        def map=[:];
                        map.put('startRow',1) ;//从第二行开始
                        IRowReader reader = new RowReader(labels,map);
                        lst=ExcelReaderUtil.readExcel(reader, realPath);
                        totalSize=lst.size()
                        def time=DateUtil.getCurrentTime()
                        def m=cocCarStorageService.saveList(lst,user,time,request,grailsAttributes)
                        failList=m.failList
                        count = m.count

                        if (failList.size()!=0){
                            errorMessages.append(",\n存在导入失败的原因为:")
                        }
                        failList?.each{
                            errorMessages.append("【车辆型号=${it.veh_Clxh} 发动机编号=${it.coc_fdjbh}】 错误信息为："+it.errorMessages+"&*_*&")
                        }

                    } catch (Exception e){
                        e.printStackTrace()
                        totalSize = -1
                        count=0
                        errorMessages.append('数据插入时出现问题，数据全部回滚！请联系系统管理人员解决！')
                    }finally{
                        lst.clear();
                        labels.clear()
                        failList.clear()
                        def result=[realCount:count,count:totalSize,errorMessages:errorMessages]
                        render result as JSON
                    }

                }else{
                    def result=[realCount:0,count:0,errorMessages:"文件上传有问题"]
                    render result as JSON
                }

            }
        }else{
            def result=[realCount:0,count:0,errorMessages:"文件上传有问题"]
            render result as JSON
        }

    }
    /**
     *@Description ajax获取列表中的所有一致性证书
     *@Auther mike
     *@createTime
     * @Update 添加一致性证书编号的现实
     * @UpdateTime 2015-03-02 zhuwei
     */
    def jsonList(){
        params.max = params.limit ? params.int('limit') : 12
        params.offset = params.start ? params.int('start') : 0
        def dd=params
        def cel = {
            if(params.coc_clxh){
                eq("coc_clxh","${params.coc_clxh}")
            }
            if(params.coc_clmc){
                like("coc_clmc","%${params.coc_clmc}%")
            }
        }
        def results = CocCarStorage.createCriteria().list(params,cel)
        def rows=[];
        for(CocCarStorage tmp in results){
            def map=[:]
            map.id=tmp.id;
            map.coc_czsl=tmp.coc_czsl;
            map.coc_clsl=tmp.coc_clsl;
            map.coc_qdzwz=tmp.coc_qdzwz;
            map.coc_zj=tmp.coc_zj;
            map.coc_qyzqzj=tmp.coc_qyzqzj;
            map.coc_lj=tmp.coc_lj;
            map.coc_cd=tmp.coc_cd;
            map.coc_zqdyqyzzjl=tmp.coc_zqdyqyzzjl;
            map.coc_zzqycd=tmp.coc_zzqycd;
            map.coc_kd=tmp.coc_kd;
            map.coc_gd=tmp.coc_gd;
            map.coc_tymj=tmp.coc_tymj;
            map.coc_qx=tmp.coc_qx;
            map.coc_hx=tmp.coc_hx;
            map.coc_jjj=tmp.coc_jjj;
            map.coc_lqj=tmp.coc_lqj;
            map.coc_xsxdcsclzl=tmp.coc_xsxdcsclzl;
            map.coc_edzzl=tmp.coc_edzzl;
            map.coc_zlzhfp=tmp.coc_zlzhfp;
            map.coc_zzllyxsh=tmp.coc_zzllyxsh;
            map.coc_czyxzdzl=tmp.coc_czyxzdzl;
            map.coc_kssczwz=tmp.coc_kssczwz;
            map.coc_kczczwz=tmp.coc_kczczwz;
            if (tmp.coc_cdzdyxzh=='NULL'){
                map.coc_cdzdyxzh=''
            }else{
                map.coc_cdzdyxzh=tmp.coc_cdzdyxzh;
            }
            map.coc_qygsgc=tmp.coc_qygsgc;
            map.coc_bgc=tmp.coc_bgc;
            map.coc_zjjgc=tmp.coc_zjjgc;
            map.coc_gczdzl=tmp.coc_gczdzl;
            map.coc_zdzhzl=tmp.coc_zdzhzl;
            map.coc_jcdczdczfh=tmp.coc_jcdczdczfh;
            if (tmp.coc_fdjzzsmc=='NULL'){
                map.coc_fdjzzsmc=''
            }else{
                map.coc_fdjzzsmc=tmp.coc_fdjzzsmc;
            }
            map.coc_zjps=tmp.coc_zjps;
            map.coc_qgsl=tmp.coc_qgsl;
            map.coc_qgplxs=tmp.coc_qgplxs;
            map.coc_pl=tmp.coc_pl;
            map.coc_rlzl=tmp.coc_rlzl;
            map.coc_zdjgl=tmp.coc_zdjgl;
            map.coc_dyfdjzs=tmp.coc_dyfdjzs;
            map.coc_lhqxs=tmp.coc_lhqxs;
            map.coc_bsqzs=tmp.coc_bsqzs;
            map.coc_sb=tmp.coc_sb;
            map.coc_zcdb=tmp.coc_zcdb;
            map.coc_ltgg=tmp.coc_ltgg;
            map.coc_sfazkqxg=tmp.coc_sfazkqxg;
            map.coc_gbthps=tmp.coc_gbthps;
            map.coc_zxzlxs=tmp.coc_zxzlxs;
            map.coc_zdzzjysm=tmp.coc_zdzzjysm;
            map.coc_zdxtgqgnyl=tmp.coc_zdxtgqgnyl;
            map.coc_csxs=tmp.coc_csxs;
            if (tmp.coc_clys=='NULL'){
                map.coc_clys=''
            }else{
                map.coc_clys=tmp.coc_clys;
            }
            map.coc_gnyxrj=tmp.coc_gnyxrj;
            map.coc_hxcd=tmp.coc_hxcd;
            map.coc_hxkd=tmp.coc_hxkd;
            map.coc_hxgd=tmp.coc_hxgd;
            map.coc_qzjzdljnl=tmp.coc_qzjzdljnl;
            map.coc_cmsl=tmp.coc_cmsl;
            map.coc_cmgz=tmp.coc_cmgz;
            map.coc_zws=tmp.coc_zws;
            map.coc_cccno=tmp.coc_cccno;
            map.coc_sybgno=tmp.coc_sybgno;
            map.coc_zgcs=tmp.coc_zgcs;
            map.coc_sj=tmp.coc_sj;
            map.coc_pqpfw=tmp.coc_pqpfw;
            if (tmp.coc_co2=='NULL'){
                map.coc_co2='';
            }else{
                map.coc_co2=tmp.coc_co2;
            }
            map.coc_jgyqsybg=tmp.coc_jgyqsybg;
            map.coc_wxhwysjb=tmp.coc_wxhwysjb;
            map.coc_dwjgyqbh=tmp.coc_dwjgyqbh;
            map.coc_mxdwysjb=tmp.coc_mxdwysjb;
            if (tmp.coc_note=='NULL'){
                map.coc_note='';
            }else{
                map.coc_note=tmp.coc_note;
            }
            if (tmp.coc_zsno=='NULL'){
                map.coc_zsno='';
            }else{
                map.coc_zsno=tmp.coc_zsno;
            }
            map.coc_clsccmc=tmp.coc_clsccmc;
            map.coc_clzzg=tmp.coc_clzzg;
            map.coc_cxxldhmc=tmp.coc_cxxldhmc;
            map.coc_dydhmc=tmp.coc_dydhmc;
            map.coc_cxdhmc=tmp.coc_cxdhmc;
            map.coc_cxmc=tmp.coc_cxmc;
            map.coc_clzwpp=tmp.coc_clzwpp;
            map.coc_cllb=tmp.coc_cllb;
            map.coc_zzsmc=tmp.coc_zzsmc;
            map.coc_zzsdz=tmp.coc_zzsdz;
            map.coc_fdmpwz=tmp.coc_fdmpwz;
            if (tmp.coc_clsbdh=='NULL'){
                map.coc_clsbdh=''
            }else{
                map.coc_clsbdh=tmp.coc_clsbdh;
            }
            map.coc_sbhdkwz=tmp.coc_sbhdkwz;
            if (tmp.coc_fdjbh=='NULL'){

                map.coc_fdjbh='';
            }else{

                map.coc_fdjbh=tmp.coc_fdjbh;
            }
            map.coc_fdjbhdkwz=tmp.coc_fdjbhdkwz;
            map.coc_ccczsh=tmp.coc_ccczsh;
            map.coc_clxh=tmp.coc_clxh;
            map.coc_clmc=tmp.coc_clmc;
            map.coc_rq=tmp.coc_rq;
            map.coc_fdjxh=tmp.coc_fdjxh;
            map.coc_fdjgzyl=tmp.coc_fdjgzyl;
            map.coc_sfzykqxgzz=tmp.coc_sfzykqxgzz;
            map.coc_createTime=tmp.coc_createTime;
            map.coc_updateTime=tmp.coc_updateTime;
            map.coc_yzxzsbh=tmp.coc_yzxzsbh;
            map.coc_new_energy=tmp.coc_new_energy;
            map.coc_abs=tmp.coc_abs;
            map.coc_scqydz=tmp.coc_scqydz;
            map.coc_clzl=tmp.coc_clzl;
            map.coc_jccccno=tmp.coc_jccccno;
            map.coc_jcclxh=tmp.coc_jcclxh;
            map.coc_jccllb=tmp.coc_jccllb;
            map.coc_zzjdcccno=tmp.coc_zzjdcccno;
            map.coc_zzjdqfrq=tmp.coc_zzjdqfrq;
            map.coc_zdyxzzl=tmp.coc_zdyxzzl;

            map.coc_sjbzh=tmp.coc_sjbzh;
            map.coc_dzzs=tmp.coc_dzzs;
            map.coc_fdjzs=tmp.coc_fdjzs;
            map.coc_cwzs=tmp.coc_cwzs;
            map.coc_pfwbzh=tmp.coc_pfwbzh;
            map.coc_ytrl=tmp.coc_ytrl;
            map.coc_ytrl_co=tmp.coc_ytrl_co;
            map.coc_ytrl_nox=tmp.coc_ytrl_nox;
            map.coc_ytrl_yd=tmp.coc_ytrl_yd;
            map.coc_ytrl_hc=tmp.coc_ytrl_hc;

            map.coc_ytrl_hcnox=tmp.coc_ytrl_hcnox;
            map.coc_ytrl_pn=tmp.coc_ytrl_pn;
            map.coc_qtrl=tmp.coc_qtrl;
            map.coc_qtrl_co=tmp.coc_qtrl_co;
            map.coc_qtrl_nmhc=tmp.coc_qtrl_nmhc;
            map.coc_qtrl_ch4=tmp.coc_qtrl_ch4;
            map.coc_qtrl_nox=tmp.coc_qtrl_nox;
            map.coc_qtrl_thc=tmp.coc_qtrl_thc;
            map.coc_qtrl_pn=tmp.coc_qtrl_pn;
            map.coc_co2pflbzh=tmp.coc_co2pflbzh;

            map.coc_co2_sq=tmp.coc_co2_sq;
            map.coc_co2_sj=tmp.coc_co2_sj;
            map.coc_co2_zh=tmp.coc_co2_zh;
            map.coc_rlxh_sq=tmp.coc_rlxh_sq;
            map.coc_rlxh_sj=tmp.coc_rlxh_sj;
            map.coc_rlxh_zh=tmp.coc_rlxh_zh;
            map.coc_clzclx=tmp.coc_clzclx;

            map.coc_ddjgzdy=tmp.coc_ddjgzdy;
            map.coc_dldcxh=tmp.coc_dldcxh;
            map.coc_dldceddy=tmp.coc_dldceddy;
            map.coc_dldcedrl=tmp.coc_dldcedrl;
            map.coc_dldcsccmc=tmp.coc_dldcsccmc;

            map.put('creater',tmp.coc_creater?.userName)
            map.put('updater',tmp.coc_updater?.userName)
            rows.add(map)
        }
        def result = [rows: rows, total: results.totalCount]

        render result as JSON
    }
    /**
     *@Description 转到创建页面
     *@Auther mike
     *@createTime
     */
    def create() {
        CocCarStorage obj=new CocCarStorage(params)
        render(view:'/cn/com/wz/vehcert/coc/carstorage/create',model:[cocCarStorageInstance:obj])
    }
    /**
     *@Description 一致性证书基础信息保存
     *@Auther mike
     *@createTime
     */
    def save() {
        def cocCarStorageInstance = new CocCarStorage(params)
        User loginUser=getCurrentUser()
        cocCarStorageInstance.coc_creater=loginUser
        cocCarStorageInstance.coc_createTime=DateUtil.getCurrentTime()
        if (!cocCarStorageInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/coc/carstorage/create", model: [cocCarStorageInstance: cocCarStorageInstance])
            return
        }
        render(view:'/cn/com/wz/vehcert/coc/carstorage/show',model:[cocCarStorageInstance: cocCarStorageInstance])
    }

    def edit() {
        def cocCarStorageInstance = CocCarStorage.get(params.id)
        print  cocCarStorageInstance
        if (!cocCarStorageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'default.not.found.message', default: 'COCCarStorage'), params.id])
            redirect(action: "list")
            return
        }
        render(view:'/cn/com/wz/vehcert/coc/carstorage/edit',model:[cocCarStorageInstance:cocCarStorageInstance])
    }

    /**
     * @Description 手工编辑一致性证书基础信息时，先保存一条新的一致性证书信息，然后在将当前编辑的这条信息删除到TEMP表中
     * @return
     */
    def update() {
        def cocCarStorageInstance = CocCarStorage.get(params.cid)
        if (!cocCarStorageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.id])
            redirect(action: "index")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (cocCarStorageInstance.version > version) {
                cocCarStorageInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'coc.label', default: 'Meeting')] as Object[],
                        "Another user has updated this Meeting while you were editing")
                render(view: "/cn/com/wz/vehcert/coc/carstorage/edit", model: [cocCarStorageInstance: cocCarStorageInstance])
                return
            }
        }

        cocCarStorageInstance.properties = params
        User loginUser=getCurrentUser()
        cocCarStorageInstance.coc_updater=loginUser
        cocCarStorageInstance.coc_updateTime=DateUtil.getCurrentTime()
        if (!cocCarStorageInstance.save(flush: true)) {
            render(view: "/cn/com/wz/vehcert/coc/carstorage/edit", model: [cocCarStorageInstance: cocCarStorageInstance])
            return
        }
        Temp tmp=Temp.findByWzh_carstorageIDAndWzh_type(cocCarStorageInstance.id,2);
        if (!tmp){
            Temp t=new Temp()
            t.wzh_carstorageID=cocCarStorageInstance.id
            t.wzh_type=2
            t.insertTime=DateUtil.getCurrentTime()
            t.save(flush: true)
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'coc.label', default: 'COCCarStorage'),''])
        render(view:'/cn/com/wz/vehcert/coc/carstorage/show',model:[cocCarStorageInstance: cocCarStorageInstance])
    }

    def show() {
        def a=params
        def cocCarStorageInstance = CocCarStorage.get(params.id)
        if (!cocCarStorageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coc.label', default: 'COCCarStorage'), params.id])
            redirect(action: "list")
            return
        }
        render(view:'/cn/com/wz/vehcert/coc/carstorage/show',model:[cocCarStorageInstance: cocCarStorageInstance,params:params])
    }
    def jsonDelete(){
        def ids = params.deleteIds
        def idsArray = ids.split('_')
        def flag = true
        def msg = message(code: 'default.deleted.simple.message')
        idsArray.each {
            def cocCarStorageInstance = CocCarStorage.get(it)
            if (!cocCarStorageInstance) {
                return
            }
            try {
                cocCarStorageInstance.delete(flush: true);
                Temp tmp=Temp.findByWzh_carstorageIDAndWzh_type(it,2);
                if (!tmp){
                    Temp t=new Temp()
                    t.wzh_carstorageID=it
                    t.wzh_type=2
                    t.insertTime=DateUtil.getCurrentTime()
                    t.save(flush: true)
                }
            }catch (Exception e) {
                flag = false
            }
            if(!flag){
                msg = message(code: 'default.not.simple.deleted.message')
            }
        }
        render msg
    }
    /**
     * @Description 依据查询条件进行删除
     *@param params
     *@return 操作信息
     *@Auther liuly
     *@createTime 2013-08-14
     */
    def jsonDeleteSearch(){
        def message=""
        def count=0
        CocCarStorage.withTransaction {trans->
            try{
                def veh_Clxh=''
                def veh_Clmc=''
                if (params.veh_Clmc) {
                    veh_Clmc=params.veh_Clmc
                }
                if (params.veh_Clxh) {
                    veh_Clxh=params.veh_Clxh
                }

                ////查询经销商所拥有的合格证信息
                StringBuffer sql_sbfS = new StringBuffer("SELECT ID  FROM COC_CARSTORAGE ");
                if(params.veh_Clmc){
                    if(params.veh_Clxh){
                        sql_sbfS.append("  WHERE  coc_Clmc like '%"+veh_Clmc+"%' AND coc_Clxh = '"+veh_Clxh+"'");
                    }else{
                        sql_sbfS.append("  WHERE  coc_Clmc like '%"+veh_Clmc+"%'");
                    }
                }else{
                    if(params.veh_Clxh){
                        sql_sbfS.append("  WHERE  coc_Clxh = '"+veh_Clxh+"'");
                    }
                }
                def listS =  sqlService.executeList(sql_sbfS.toString())
                String curTime=DateUtil.getCurrentTime()
                listS.each {
                    Temp t=new Temp()
                    t.wzh_carstorageID=it.ID
                    t.wzh_type=2
                    t.insertTime=curTime
                    t.save()
                }

                if (listS.size()>0){
                    StringBuffer sql_sbf = new StringBuffer("DELETE FROM COC_CARSTORAGE  ");
                    if(params.veh_Clmc){
                        if(params.veh_Clxh){
                            sql_sbf.append("  WHERE  coc_Clmc like '%"+veh_Clmc+"%' AND coc_Clxh = '"+veh_Clxh+"'");
                        }else{
                            sql_sbf.append("  WHERE  coc_Clmc like '%"+veh_Clmc+"%'");
                        }
                    }else{
                        if(params.veh_Clxh){
                            sql_sbf.append("  WHERE  coc_Clxh = '"+veh_Clxh+"'");
                        }
                    }
                    def list =  sqlService.executeUpdate(sql_sbf.toString())
                    message=list+"条删除成功！"
                }else{
                    message = '0条删除成功！';
                }
            }catch (Exception e){
                trans.setRollbackOnly()
                message="删除失败！${e.message}"
            } finally{
                render message
            }

        }

    }
    /**
     *@Description 综合查询中的一致性证书查询
     *@param
     *@return
     *@Auther liuly
     *@createTime 2013-08-09
     *
     */
    def searchCo(){
        render(view:'/cn/com/wz/vehcert/coc/carstorage/cocCarstorageSearch')
    }

    /**
     *@Description 车间获得一致性证书 JSON
     *@Auther zouQ
     *@createTime   2013-08-10
     */
    def jsonListByWork(){
        params.max = params.limit ? params.int('limit') : 10
        params.offset = params.start ? params.int('start') : 0
        def cel = {
            if(params.clxh){
                like("coc_clxh","%${params.clxh}%")
            }
            if (params.clxh) {
                eq("coc_clxh","${params.clxh}")
            }
        }
        def results = CocCarStorage.createCriteria().list(params,cel)
        def result = [rows: results, total: results.totalCount]

        render result as JSON
    }
    /**
     * @Description 获取排列组合的结果
     * @return
     */
    def getCompResult(){
        render session.getAttribute('compFlag')
    }
}
