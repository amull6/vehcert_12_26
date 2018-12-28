package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.dictionary.DictionaryValue
import grails.converters.JSON
import org.springframework.jdbc.core.JdbcTemplate

class HarvestInfoService {
    def dataSource
    def static final monthcode=['01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'X','11':'J','12':'P']
    def static final yearsCode=['2015':'F','2016':'G','2017':'H','2018':'J','2019':'K','2020':'L'
            ,'2021':'M','2022':'N','2023':'P','2024':'R','2025':'S','2026':'T','2027':'V','2028':'W','2029':'X','2030':'Y'
            ,'2031':'1','2032':'2','2033':'3','2034':'4','2035':'5','2036':'6','2037':'7','2038':'8','2039':'9','2040':'A'
            ,'2041':'B','2042':'C','2043':'D','2044':'E','2045':'F','2046':'G'
    ]
    def static final compareCode=['0':0,'1':1,'2':2,'3':3,'4':4,'5':5,'6':6,'7':7,'8':8,'9':9
            ,'A':1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'J':1,'K':2
            ,'L':3,'M':4,'N':5,'P':7,'R':9,'S':2,'T':3,'U':4,'V':5,'W':6
            ,'X':7,'Y':8,'Z':9]
    def static final powCode=[8,7,6,5,4,3,2,10,-1,9,8,7,6,5,4,3,2]
    def sqlService
/**
 * @Descriptin合格证校验方法
 * @CreateTime 2017-05-10
  */

    def String checkInfo(HarvestInfo harvestInfoInstance,type){
        StringBuffer sb=new StringBuffer()
        def style=type
        if(type.toString()=='1'){     //正常生产的收获机
            if(harvestInfoInstance.veh_Clbh==''||harvestInfoInstance.veh_Clbh==null){
                sb.append("车辆编号不能为空;")
            }
            if (!harvestInfoInstance.veh_Clbh.startsWith("3")||harvestInfoInstance.veh_Clbh.length()!=11){
                sb.append("车辆编号与规则不符;")
            }
            if(!harvestInfoInstance.veh_Clbh.contains('D')){
                sb.append("车辆编号与规则不符;")
            }

            if(harvestInfoInstance.veh_Dph==''||harvestInfoInstance.veh_Dph==null){
                sb.append("底盘号不能为空;")
            }else if (harvestInfoInstance.veh_Dph.length()!=17){
                sb.append("底盘号与规则不符;")
            }
            if(!harvestInfoInstance.veh_Dph.contains('D')){
                sb.append("底盘号与规则不符;")
            }
            if(harvestInfoInstance.veh_new_clbh==''||harvestInfoInstance.veh_new_clbh==null){
                sb.append("新车辆编号不能为空;")
            }else if (harvestInfoInstance.veh_new_clbh.length()!=17){
                sb.append("新车辆编号与规则不符;")
            }
            if(!harvestInfoInstance.veh_new_clbh.contains('D')){
                sb.append("新车辆编号与规则不符;")
            }

            if(harvestInfoInstance.veh_Cx==''||harvestInfoInstance.veh_Cx==null){
                sb.append("车型不能为空;")
            }

            if(harvestInfoInstance.veh_Fdjh==''||harvestInfoInstance.veh_Fdjh==null){
                sb.append("发动机号不能为空;")
            }
            if(harvestInfoInstance.envirCode==''||harvestInfoInstance.envirCode==null){
                sb.append("机械环保代码不能为空;")
            }else if (harvestInfoInstance.envirCode.length()!=17){
                sb.append("机械环保代码与规则不符;")
            }
            if(!harvestInfoInstance.envirCode.contains('D')){
                sb.append("机械环保代码与规则不符;")
            }

        }else if (type.toString()=='2'){   //超标车
            if(harvestInfoInstance.veh_Clbh==''||harvestInfoInstance.veh_Clbh==null){
                sb.append("车辆编号不能为空;")
            }
            if (!harvestInfoInstance.veh_Clbh.startsWith("3")||harvestInfoInstance.veh_Clbh.length()!=12){
                sb.append("车辆编号与规则不符;")
            }
            if(!harvestInfoInstance.veh_Clbh.contains('E')){
                sb.append("车辆编号与规则不符;")
            }

            if(harvestInfoInstance.veh_Dph==''||harvestInfoInstance.veh_Dph==null){
                sb.append("底盘号不能为空;")
            }else if (harvestInfoInstance.veh_Dph.length()!=17){
                sb.append("底盘号与规则不符;")
            }
            if(!harvestInfoInstance.veh_Dph.contains('E')){
                sb.append("底盘号与规则不符;")
            }
            if(harvestInfoInstance.veh_new_clbh==''||harvestInfoInstance.veh_new_clbh==null){
                sb.append("新车辆编号不能为空;")
            }else if (harvestInfoInstance.veh_new_clbh.length()!=17){
                sb.append("新车辆编号与规则不符;")
            }
            if(!harvestInfoInstance.veh_new_clbh.contains('E')){
                sb.append("新车辆编号与规则不符;")
            }

            if(harvestInfoInstance.veh_Cx==''||harvestInfoInstance.veh_Cx==null){
                sb.append("车型不能为空;")
            }

            if(harvestInfoInstance.veh_Fdjh==''||harvestInfoInstance.veh_Fdjh==null){
                sb.append("发动机号不能为空;")
            }
            if(harvestInfoInstance.envirCode==''||harvestInfoInstance.envirCode==null){
                sb.append("机械环保代码不能为空;")
            }else if (harvestInfoInstance.envirCode.length()!=17){
                sb.append("机械环保代码与规则不符;")
            }
            if(!harvestInfoInstance.envirCode.contains('E')){
                sb.append("机械环保代码与规则不符;")
            }
        }
        return sb.toString()
    }

    /**
     * @Decroption判断需要发动机号、合格证编号、超标车出场日期是不是与字典配置值一样
     * @CreateTime 2017_05_10 by zhuwei
     */
    def returnConfig(checkItems){
        def check=checkItems
        def msg=false
        def cel = {
            inList("code", ['yms_config_fdjh','yms_config_hgzbh','dms_config_hgzbh','dms_config_fdjh'])
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
    /**
     * @Description 计算农装环保代码
     * @Create 2017-07-20    QJ
     */
    def calculateVIN(params,loginUser){

        String veh_Qydm='576'
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
        String carStorageType=params.carStorageType?.trim()
        String veh_Fzrq=params.veh_Fzrq?.trim()
        String type = params.type?.trim()

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
            String year = veh_Clbh.substring(1,3)
            waterCode= veh_Clbh.substring(veh_Clbh.length()-5,veh_Clbh.length())
            year='20'+year
            String yearCode=yearsCode.get(year)  ;
            if (yearCode)     {
                vinnext.append(yearCode);//年份 第10位
            } else{
                result.put('flag','0');
                result.put('msg','合格证中年份不符合要求');
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
            vinnext.append(monthCode);//月份 第1213位
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
        def symboMap=[:]
        if(symbolList!=null&&symbolList.size()!=0){
            for(def symbol in symbolList){
                symboMap.put(symbol.get('CODE')+'_'+symbol.get('SYMBOL'),symbol.get('SYMBOL'));
            }
        }
        if (symboMap.containsKey(type)){  //一个合格证打印用户只能关联一个车间
            vinnext.append(symboMap.get(type));
        }else{
            result.put('flag','0');
            result.put('msg','合格证中打印人员未关联车间');
            return result as JSON
        }
        if (waterCode&&waterCode.length()!=5){
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
        vinnext.append(waterCode.substring(1));//第14-17位

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
     * @Description 根据条件查询收获车间合格证数据
     * @CreateTime 2017-05-10 by zhuwei
     * @param params
     */
    def selectByParams(params){
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
//        if (params.organsCodeStr!=''){
//            if (need){
//                sql+=" and z.veh_workshopno in ${params.organsCodeStr.toString()} "
//            }else{
//                sql+=" z.veh_workshopno in ${params.organsCodeStr.toString()} "
//                need=true
//            }
//
//        }
        def sqls="select id, HavesterInfoId from WZH_HARVESTER_INFO z"
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
//            if (params.organCodeList.size()>0) {
//                inList("veh_workshopno", params.organCodeList)
//            }
            order ("createTime","desc")
//            order ("upload", "asc")
        }
        def lst=HarvestInfo.createCriteria().list(params,cel)
        def results=[]
        lst?.each {l->
            def m=[:]
            l.properties.each {
                m."${it.key}"=it.value
            }
            m.id=l.id
            def id=''
            lstId.each { i->
                if (i.HavesterInfoId==l.id){        //如果SQL查到的id和动态查询超导的id相同，将id赋值给map中的idReal
                    id = i.id
                }
            }
            m.idReal=id   //因为在1.25上的ORCL数据库中id是空列，所以在1.25上部署的程序，idReal为null，er在32.120上SQL id列不为空，idReal则不为空
            results.add(m)
        }
        def result = [rows: results,total: lst.totalCount]

    }
}
