package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.Certificate
import cn.com.wz.vehcert.PrintResult
import cn.com.wz.vehcert.SysInfoService
import cn.com.wz.vehcert.SysInfoServiceLocator
import cn.com.wz.vehcert.carstorage.PreCarStorage
import cn.com.wz.vehcert.coc.CocCarStoragePrint
import grails.converters.JSON
import groovy.sql.Sql
import org.springframework.jdbc.core.JdbcTemplate

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class NzInfoService {
    def static final yearsCode=['2015':'F','2016':'G','2017':'H','2018':'J','2019':'K','2020':'L'
            ,'2021':'M','2022':'N','2023':'P','2024':'R','2025':'S','2026':'T','2027':'V','2028':'W','2029':'X','2030':'Y'
            ,'2031':'1','2032':'2','2033':'3','2034':'4','2035':'5','2036':'6','2037':'7','2038':'8','2039':'9','2040':'A'
            ,'2041':'B','2042':'C','2043':'D','2044':'E','2045':'F','2046':'G'
    ]
    def static final monthcode=['01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'X','11':'J','12':'P']
    def static final compareCode=['0':0,'1':1,'2':2,'3':3,'4':4,'5':5,'6':6,'7':7,'8':8,'9':9
            ,'A':1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'J':1,'K':2
            ,'L':3,'M':4,'N':5,'P':7,'R':9,'S':2,'T':3,'U':4,'V':5,'W':6
            ,'X':7,'Y':8,'Z':9,'Q':8  ]
    def static final powCode=[8,7,6,5,4,3,2,10,-1,9,8,7,6,5,4,3,2]
    def sqlService
    def dataSource
    def String checkInfo(NZInfo NZzcInfo,type){
        StringBuffer sb=new StringBuffer()
        def style=type
        if(type.toString()=='1'){     //山拖
            if(NZzcInfo.veh_Clbh==''||NZzcInfo.veh_Clbh==null){
                sb.append("车辆流水号不能为空;")
            }
            if (NZzcInfo.veh_Clbh.length()!=7){
                sb.append("车辆流水号与规则不符;")
             }
            if(NZzcInfo.veh_new_clbh==''||NZzcInfo.veh_new_clbh==null){
                sb.append("出厂编号不能为空;")
            }
//            else if (NZzcInfo.veh_new_clbh.length()!=17){
//                sb.append("出厂编号与规则不符;")
//            }
            if(NZzcInfo.veh_Cx==''||NZzcInfo.veh_Cx==null){
                sb.append("车型不能为空;")
            }

            if(NZzcInfo.veh_Fdjh==''||NZzcInfo.veh_Fdjh==null){
                sb.append("发动机号不能为空;")
            }
            if(NZzcInfo.envirCode==''||NZzcInfo.envirCode==null){
                sb.append("机械环保代码不能为空;")
            }else if (NZzcInfo.envirCode.length()!=17){
                sb.append("机械环保代码与规则不符;")
            }
            if(NZzcInfo.veh_Hgzbh==''||NZzcInfo.veh_Hgzbh==null){
                sb.append("合格证编号不能为空;")
            }
            if (NZzcInfo.veh_Hgzbh.length()!=18+NZzcInfo.veh_new_clbh.length()){
                sb.append("合格证编号与规则不符;")
            }
        }else if (type.toString()=='2'){  //农装
            if(NZzcInfo.veh_Clbh==''||NZzcInfo.veh_Clbh==null){
                sb.append("底盘号不能为空;")
            }
            if (NZzcInfo.veh_Clbh.length()!=7){
                sb.append("车辆流水号与规则不符;")
            }
            if(NZzcInfo.veh_new_clbh==''||NZzcInfo.veh_new_clbh==null){
                sb.append("出厂编号不能为空;")
            }
//            else if (NZzcInfo.veh_new_clbh.length()!=17){
//                sb.append("出厂编号与规则不符;")
//            }
            if(NZzcInfo.veh_Cx==''||NZzcInfo.veh_Cx==null){
                sb.append("车型不能为空;")
            }

            if(NZzcInfo.veh_Fdjh==''||NZzcInfo.veh_Fdjh==null){
                sb.append("发动机号不能为空;")
            }
            if(NZzcInfo.envirCode==''||NZzcInfo.envirCode==null){
                sb.append("机械环保代码不能为空;")
            }else if (NZzcInfo.envirCode.length()!=17){
                sb.append("机械环保代码与规则不符;")
            }
            if(NZzcInfo.veh_Hgzbh==''||NZzcInfo.veh_Hgzbh==null){
                sb.append("合格证编号不能为空;")
            }
            if (NZzcInfo.veh_Hgzbh.length()!=18+NZzcInfo.veh_new_clbh.length()){
                sb.append("合格证编号与规则不符;")
            }
        }else{          //高北
            if(NZzcInfo.veh_Clbh==''||NZzcInfo.veh_Clbh==null){
                sb.append("底盘号不能为空;")
            }
            if (NZzcInfo.veh_Clbh.length()!=7){
                sb.append("车辆流水号与规则不符;")
            }
            if(NZzcInfo.veh_new_clbh==''||NZzcInfo.veh_new_clbh==null){
                sb.append("出厂编号不能为空;")
            }
//            else if (NZzcInfo.veh_new_clbh.length()!=17){
//                sb.append("出厂编号与规则不符;")
//            }
            if(NZzcInfo.veh_Cx==''||NZzcInfo.veh_Cx==null){
                sb.append("车型不能为空;")
            }

            if(NZzcInfo.veh_Fdjh==''||NZzcInfo.veh_Fdjh==null){
                sb.append("发动机号不能为空;")
            }
            if(NZzcInfo.envirCode==''||NZzcInfo.envirCode==null){
                sb.append("机械环保代码不能为空;")
            }else if (NZzcInfo.envirCode.length()!=17){
                sb.append("机械环保代码与规则不符;")
            }
            if(NZzcInfo.veh_Hgzbh==''||NZzcInfo.veh_Hgzbh==null){
                sb.append("合格证编号不能为空;")
            }
            if (NZzcInfo.veh_Hgzbh.length()!=18+NZzcInfo.veh_new_clbh.length()){
                sb.append("合格证编号与规则不符;")
            }
        }
        return sb.toString()
    }
    /**
     * @Description 根据条件获取满足条件的合格证信息
     * @param params
     * @Create 2013-08-04 huxx
     * Update 合格证上传时，获取合格证生产车间编号等于当前用户所属组织编号的合格证
     * UpdateTime 2014-10-28 zhuwei
     */
    def selectNZInfoList(params){
        def sta = params.startTime+" 00:00:00"
        def end = params.endTime+" 23:59:59"
        def cel={
            if(params.upload){
                eq('upload',"${params.upload}")
            }
            if(params.startTime){
                ge('createTime',sta)
            }
            if(params.endTime){
                le('createTime',end)
            }
            if(params.veh_Clbh){
                eq('veh_Clbh',"${params.veh_Clbh}")

            }
            if (params.veh_Clbh1&&params.veh_Clbh2){
                between("veh_Clbh",params.veh_Clbh1,params.veh_Clbh2)
            }else{
                if (params.veh_Clbh1&&!params.veh_Clbh2){
                    ge("veh_Clbh",params.veh_Clbh1)
                }else if (!params.veh_Clbh1&&params.veh_Clbh2){
                    le("veh_Clbh",params.veh_Clbh2)
                }
            }
            if(params.organCode.size()>0){
                inList("veh_workshopno", params.organCode)
            }
            order ("upload", "asc")
            order ("veh_Clbh","asc")
        }
        def lst=NZInfo.createCriteria().list (params,cel)
        return lst
    }
    /**
     * @Description 计算农装环保代码
     * @Create 2017-07-20    QJ
     * @update 2018-02-22    环保代码加入车间识别标识
     */
    def calculateVIN(params,loginUser){

        String veh_Jxdl=params.veh_Jxdl?.trim()
        int count_Jxdl=veh_Jxdl.length()
        String veh_Rllx=params.veh_Rllx?.trim()
        int count_Rllx=veh_Rllx.length()
        String veh_Jxxlb=params.veh_Jxxlb?.trim()
        int count_Jxxlb=veh_Jxxlb.length()
        String veh_Zycs=params.veh_Zycs?.trim()
        int count_Zycs=veh_Zycs.length()
        String veh_Pfjd=params.veh_Pfjd?.trim()
        int count_Pfjd=veh_Pfjd.length()
        String veh_Clbh=params.veh_Clbh?.trim()
        String veh_Fzrq=params.veh_Fzrq?.trim()
        String veh_Qydm = params.veh_Qydm?.trim()
        def result=[:]//定义返回map  flag为0表示 解析不成功1 表示成功 msg 存储 解析不成功原因 vin 存储解析成功后的vin号
        StringBuilder vinpre=new StringBuilder(); //vin前8位
        StringBuilder vinnext=new StringBuilder();//vin8位之后
        if(veh_Qydm){
            vinpre.append(veh_Qydm);//前三位
        }else{
            result.put('flag','0');
            result.put('msg','企业代码不允许为空');
            return result as JSON
        }
        if (veh_Jxdl&&count_Jxdl==1)     {
            vinpre.append(veh_Jxdl);//第四位
        } else{
            result.put('flag','0');
            result.put('msg','机械大类别公告数据有误');
            return result as JSON
        }
        if (veh_Rllx&&count_Rllx==1)     {
            vinpre.append(veh_Rllx);//第五位
        } else{
            result.put('flag','0');
            result.put('msg','燃料类型公告数据有误');
            return result as JSON
        }
        if (veh_Jxxlb&&count_Jxxlb==1)     {
            vinpre.append(veh_Jxxlb);//第六位
        } else{
            result.put('flag','0');
            result.put('msg','机械小类别公告数据有误');
            return result as JSON
        }
        if (veh_Zycs&&count_Zycs==2)     {
            vinpre.append(veh_Zycs);//第七八位
        } else{
            result.put('flag','0');
            result.put('msg','机械产品主要参数公告数据有误');
            return result as JSON
        }
        ////////////////////////////////////////////////////////9位之后
        String waterCode=''
        if(veh_Clbh){
            String year = veh_Fzrq.substring(0,4)
            waterCode= veh_Clbh.substring(veh_Clbh.length()-4,veh_Clbh.length())
            String yearCode=yearsCode.get(year)  ;
            if (yearCode)     {
                vinnext.append(yearCode);//年份 第10位
            } else{
                result.put('flag','0');
                result.put('msg','发证日期中的年份不正确');
                return result as JSON
            }
        }else{
            result.put('flag','0');
            result.put('msg','车辆编号不存在');
            return result as JSON
        }
        if (veh_Pfjd&&count_Pfjd==1)     {
            vinnext.append(veh_Pfjd);//排放阶段 第11位
        } else{
            result.put('flag','0');
            result.put('msg','排放阶段公告数据有误');
            return result as JSON
        }
        String month = DateUtil.getCurrentTime('MM');
        String monthCode=monthcode.get(month);
        if (monthCode)     {
            vinnext.append(monthCode);//月份 第12位
        } else{
            result.put('flag','0');
            result.put('msg','合格证中月份不符合要求');
            return result as JSON
        }
        //人员 组织 车间 三者进行关联
        String symbolStr="select s.SYMBOL,d.CODE,s.START_NUM as startNum ,s.MAX_NUM as maxNum  from TBL_SYMBOL s,Tbl_Workshop w,SYS_ORGAN o,SYS_ORGAN_USERS ou,SYS_DICTIONARY_VALUE d where w.id=s.workshop_id and s.KIND_ID=d.DIC_VALUE_ID and w.serial_num=o.organ_code and o.id=ou.organization_id and ou.user_id='${loginUser.id}'"
        //取出
        def template=new JdbcTemplate(dataSource)
        List symbolList = template.queryForList(symbolStr)
        def symboList=[]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboList.add(symbol.get('SYMBOL'));
            }
        }
        if (symboList.size()>0){  //一个合格证打印用户只能关联一个车间
            vinnext.append(symboList.get(0));
        }else{
            result.put('flag','0');
            result.put('msg','合格证中打印人员未关联车间');
            return result as JSON
        }
        if (waterCode&&waterCode.length()!=4){
            result.put('flag','0');
            result.put('msg','合格证中流水号不符合要求');
            return result as JSON
        }
        int waterCode_int=0;
        try {
            waterCode_int=Integer.valueOf(waterCode)
        }catch (Exception e){
            result.put('flag','0');
            result.put('msg','合格证中流水号不符合要求');
            return result as JSON
        }
        vinnext.append(waterCode);//第14-17位

        //生成VIN中第9位 校验位
        String tmpEnvirCode= vinpre.toString()+'*'+ vinnext.toString();
        int tmpNum=0;
        if (tmpEnvirCode&&tmpEnvirCode.length()==17){
            for(int i=0;i<17;i++){
                String tmpChar=tmpEnvirCode.substring(i, i+1)
                if (i!=8){
                    def compareValue=compareCode.get(tmpChar)
                    if (compareValue!=null){
                        int value=compareValue*(powCode.get(i))
                        tmpNum+=value;
                    }else{
                        result.put('flag','0');
                        result.put('msg','环保信息代码生成出错');
                        return result as JSON
                    }
                }
            }
        }else{
            result.put('flag','0');
            result.put('msg','环保信息代码生成出错');
            return result as JSON
        }
        int valid=tmpNum%11;
        if (valid==10){
            vinpre.append('X');
        }else{
            vinpre.append(valid);
        }
        String envirCode=vinpre.toString()+vinnext.toString()
        result.put('flag','1');
        result.put('envirCode',envirCode);
        return result as JSON
    }


    /**
     * @Description 根据条件选择合格证信息 ，使用sql
     * @author zhuwei
     * @param params
     * @Create 2014-07-11
     * Update 合格证查询时，获取合格证生产车间编号等于当前用户所属组织编号的合格证
     * UpdateTime 2014-10-28 zhuwei
     */
    def selectNZinfoByShop(params){
        def need=false
        def sql=""
        if(params.veh_Clbh){
            sql+=" veh_Clbh = '${params.veh_Clbh}'"
            need=true
        }

        def endTime=''
        if (params.endTime){
            endTime= params.endTime +' 23:59:59'
            if (need){
                sql+=" and z.create_Time <= '${endTime}'"
            }else{
                sql+=" z.create_Time <= '${endTime}'"
                need=true
            }

        }
        def startTime=''
        if (params.startTime){
            startTime= params.startTime +' 00:00:00'
            if (need){
                sql+=" and z.create_Time >= '${startTime}'"
            }else{
                sql+=" z.create_Time >= '${startTime}'"
                need=true
            }

        }
        if (params.organsCodeStr!=''){
            if (need){
                sql+=" and z.veh_workshopno in ${params.organsCodeStr.toString()} "
            }else{
                sql+=" z.veh_workshopno in ${params.organsCodeStr.toString()} "
                need=true
            }

        }
        def sqls="select id,NZZCINFOID from WZH_NZ_ZCINFO z"
        if (sql){
            sqls+=" where  ${sql}  "
        }
        def lstId= sqlService.executeList(sqls)         //在1.25上id为空，在32.120的SQL上非空
        def cel={
            if(params.startTime){
                ge('createTime',startTime)
            }
            if(params.endTime){
                le('createTime',endTime)
            }
            if (params.veh_Clbh) {
                like("veh_Clbh","${params.veh_Clbh}")
            }
            if(params.upload){
                eq("upload","${params.upload}")
            }
            if (params.organCodeList.size()>0) {
                inList("veh_workshopno", params.organCodeList)
            }
            order ("veh_Clbh","asc")
            order ("upload", "asc")
        }
        def lst=NZInfo.createCriteria().list(params,cel)
        def results=[]
        lst?.each {l->
            def m=[:]
            l.properties.each {
                m."${it.key}"=it.value
            }
            m.id=l.id
            def id=''
            lstId.each { i->
                if (i.NZzcInfoId==l.id){        //如果SQL查到的id和动态查询超导的id相同，将id赋值给map中的idReal
                    id = i.id
                }
            }
            m.idReal=id   //因为在1.25上的ORCL数据库中id是空列，所以在1.25上部署的程序，idReal为null，er在32.120上SQL id列不为空，idReal则不为空
            results.add(m)
        }
        def result = [rows: results,total: lst.totalCount]
    }


    /**  同步数据
     * @update huxx 2013-08-16 添加了出现异常时数据回滚
     *
     * */

    def SynchronizationData(Sql sql_con,List<String> sql_options){
        def result=true
        Connection con = sql_con.useConnection;
        con.setAutoCommit(false)
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY)
        try{
            statement.clearBatch()
            sql_options.each{
                statement.addBatch(it.toString())
            }
            statement.executeBatch()
            con.commit()
        }catch(Exception e){
            //异常时数据全部会滚
            con.rollback()
            result=false
            e.printStackTrace();
        }finally{
            statement.close()
        }

        return result
    }



    /**
     * @Description 根据条件将合格证信息同步到服务器上
     * @param params
     * @CreateTime 2014-07-15
     * @Author zhuwei
     * @Update 中拖出口车打印的合格证上传不再NZ_print表中记录上传合格证的最大编号
     * @UpdateTime zhuwei 2015-01-19
     */
    def uploadNZInfo(params){
        def result=[]
        def remoteDB=params.remoteDB
        def cel={
            eq('upload','0')
            if (params.veh_Clbh1){
                ge("veh_Clbh",params.veh_Clbh1)
            }
            if (params.veh_Clbh2){
                le("veh_Clbh",params.veh_Clbh2)
            }
            eq('veh_workshopno',params.factoryCode)   //一个用户只关联到一个车间
        }
        def lst=NZInfo.createCriteria().list(cel)
        def sqlList=[]
        lst?.each{zc->
            //可以在这里先读取远程数据库判断记录是否已经存在 ,如果存在就将记录的id保存到result （暂时未做）
            StringBuffer sbf = new StringBuffer("insert into ");
            sbf.append("WZH_NZ_ZCINFO");  /**表名**/
            String col="(NZzcInfoId,"
            String value="values('${zc.id}',"
            zc.properties?.each{
                switch(it.key){
                    case "upload" :
                        col+="${it.key},"
                        value+="1,"          //这个地方与汽车合格证不同，不需要再次上传到国家，所以在1.25服务器上将上传状态置为1，
                                                // 在车间端从1.25向车间本地同步数据时，就可以把这个状态同步成1，就不会再次出现在未上传列表里面
                                                //以后若是农装合格证也需要上传的国家数据库，则需要将这个上传状态修改为1
                        break;
                    case "createTime" :
                        col+="create_time,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "createrId" :
                        col+="creater_id,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "updateTime" :
                        col+="update_time,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "updaterId" :
                        col+="updater_id,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "uploader_Time" :
                        col+="uploader_Time,"
                        value+="'${params.uploadTime}',"
                        break;
                    case "uploader_Id" :
                        col+="uploader_Id,"
                        value+="'${params.userName}',"
                        break;
                    case "veh_workshopno" :
                        col+="veh_workshopno,"
                        value+="'${params.factoryCode}',"
                        break;
                    case "transDate" :
                        col+="TRANS_DATE,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "processType" :
                        col+="process_Type,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    default:
                        col+="${it.key},"
                        value+="'${it.value?it.value:''}',"
                }

            }
            col = col.substring(0,col.lastIndexOf(','))
            value = value.substring(0,value.lastIndexOf(','))
            sbf.append(col+")")
            sbf.append(value+")")
            sqlList.add(sbf)
        }

        def uploadResult= SynchronizationData(remoteDB,sqlList)

        def createTime=DateUtil.getCurrentTime()
        //同步成功后，更新已提交的纪录
        if (uploadResult && "1".equals(params.changeStatus)){
            String updaterId=params.userName? params.userName:''
            if (lst?.size()>0){
                //同步合格证后，将updateTime更新为当前时间，并通过updateTime与当前时间比较，时差超过三天的删除
                String prix="update NZInfo set upload='1', updateTime='${createTime}' ,updaterId='${updaterId}',uploader_Id='${updaterId}',uploader_Time='${createTime}'"        //之所以更新updateTime而不是uploadTime是因为防止汽车合格证上传到国家是把 uploadTime上传到国家
                StringBuffer sb=new StringBuffer("${prix} where id in (")
                int count=0
                lst?.each{

                    if(count<999){
                        if (count==0){
                            sb.append("'${it.id}'")
                        }else{
                            sb.append(",'${it.id}'")
                        }

                    }else{
                        sb.append(")")
                        NZInfo.executeUpdate(sb.toString())
                        sb=new StringBuffer("${prix}  where id in ('${it.id}'")
                        count=count-999
                    }
                    count+=1
                }
                sb.append(")")
                NZInfo.executeUpdate(sb.toString())
            }
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>合格证信息更新成功"
        } else{
            result.addAll(lst.collect())
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>合格证信息更新失败"
        }

        //当上传的合格证信息不为空时删除配置保留天数以前已上传的合格证信息，当上传记录为空时不删除以前的合格证信息
        if(uploadResult&&lst && "1".equals(params.changeStatus)){
            def now=new Date()
            def old=now-Integer.parseInt(params.NzleftDay)
            def oldTime=DateUtil.getFormatTime(old,null)
            //删除当天以前的并且已上传的合格证信息，只保留当天提交的合格证信息【在这里上传后不允许修改，所以通过updateTime和当前时间减去保留时间比较，进行删除数据】
            def zcList= NZInfo.findAll("from NZInfo as z where z.upload='1' and substring(z.updateTime,0,11)<=:createTime",[createTime:oldTime?.toString()?.substring(0,11)])
            if (zcList?.size()>0){
                StringBuffer sb=new StringBuffer("delete from NZInfo  where id in (")
                int count=0
                zcList?.each{

                    if(count<999){
                        if (count==0){
                            sb.append("'${it.id}'")
                        }else{
                            sb.append(",'${it.id}'")
                        }

                    }else{
                        sb.append(")")
                        NZInfo.executeUpdate(sb.toString())
                        sb=new StringBuffer("delete from NZInfo  where id in ('${it.id}'")
                        count=count-999
                    }
                    count+=1
                }
                sb.append(")")
                NZInfo.executeUpdate(sb.toString())
            }

            //获取农用车、汽车整车、汽车底盘的上传记录的最大合格证编号
            //因为一个用户只关联到一个车间，所以打印的合格证查询出来也只能是一类合格证，而不是混合的合格证
//            def maxTractorMH=''
            def maxTractor=''   //包括小拖、中拖、大拖
            def maxTractorCB=''
            lst?.each{
                if(it.processType=='0'){ //仅计算常规生成的合格证的最大号
                    if(it.veh_Clbh.startsWith("3804")){ //仅计算超标车
                            if (maxTractorCB<it.veh_Clbh){
                                maxTractorCB=it.veh_Clbh
                            }
                        }else if(it.veh_Clbh.contains('TX')){    //正常生产的小拖车辆编号，
                                if (maxTractor<it.veh_Clbh) {
                                    maxTractor=it.veh_Clbh
                                }
                            }else if(it.veh_Clbh.contains('TZ')){//底盘合格证比较
                                if (maxTractor<it.veh_Clbh){           //小拖
                                    maxTractor=it.veh_Clbh
                                }
                            }else if(it.veh_Clbh.contains('TD')){
                                if (maxTractor<it.veh_Clbh){           //小拖
                                    maxTractor=it.veh_Clbh
                                }
                          }
                    }
            }


            def user=User.get(params.userId)
            def codeList=''
            user.organs.each{
                codeList=it.organCode
            }
            def NZprintSet=NZprintSet.findByCodeAndUserid("main",codeList)
            if (NZprintSet){
                NZprintSet.max_tractor_upload=maxTractor
                NZprintSet.max_CBtractor_upload=maxTractorCB
                NZprintSet.save()
            }else{
                NZprintSet=new NZprintSet()
                NZprintSet.code="main"
                NZprintSet.userid=codeList
                NZprintSet.max_tractor_upload=maxTractor
                NZprintSet.max_CBtractor_upload=maxTractorCB
                NZprintSet.save()
            }
        }

        return result
    }
    /**
     * @Decroption判断需要发动机号、合格证编号、超标车出场日期是不是与字典配置值一样
     */
    def returnConfig(checkItems){
        def check=checkItems
        def msg=false
        def cel = {
            inList("code", ['fdjh_config','hgzbh_config','CB_ccrq'])
        }
        //设置排序参数
        def results = DictionaryValue.createCriteria().list(cel)
        ArrayList List=[]
        if(results.size()>0){
            results.each{
                if(it.value1){
                    List.add(it.value1)
                }
            }
        }
        if(List.contains(checkItems)){
            msg=true
        }
        return msg
    }
}
