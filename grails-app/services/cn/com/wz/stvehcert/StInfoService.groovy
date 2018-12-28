package cn.com.wz.stvehcert

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.system.user.User
import grails.converters.JSON
import org.springframework.jdbc.core.JdbcTemplate

class StInfoService {
    def dataSource
    def static final yearsCode=['2015':'F','2016':'G','2017':'H','2018':'J','2019':'K','2020':'L'
            ,'2021':'M','2022':'N','2023':'P','2024':'R','2025':'S','2026':'T','2027':'V','2028':'W','2029':'X','2030':'Y'
            ,'2031':'1','2032':'2','2033':'3','2034':'4','2035':'5','2036':'6','2037':'7','2038':'8','2039':'9','2040':'A'
            ,'2041':'B','2042':'C','2043':'D','2044':'E','2045':'F','2046':'G'
    ]
    def static final monthcode=['01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'X','11':'J','12':'P']
    def static final compareCode=['0':0,'1':1,'2':2,'3':3,'4':4,'5':5,'6':6,'7':7,'8':8,'9':9
            ,'A':1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'J':1,'K':2
            ,'L':3,'M':4,'N':5,'P':7,'R':9,'S':2,'T':3,'U':4,'V':5,'W':6
            ,'X':7,'Y':8,'Z':9]
    def static final powCode=[8,7,6,5,4,3,2,10,-1,9,8,7,6,5,4,3,2]
    def sqlService
    def String checkInfo(STInfo STzcInfo){
        StringBuffer sb=new StringBuffer()
            if(STzcInfo.veh_Scbh==''||STzcInfo.veh_Scbh==null){
                sb.append("生产编号不能为空;")
            }
            if (STzcInfo.veh_Scbh.length()!=9){
                sb.append("生产编号与规则不符;")
            }
            if(STzcInfo.envirCode==''||STzcInfo.envirCode==null){
                sb.append("环保代码不能为空;")
            }
            if(STzcInfo.veh_Hgzbh==''||STzcInfo.veh_Hgzbh==null){
                sb.append("合格证编号不能为空;")
            }
            if(STzcInfo.veh_Cx==''||STzcInfo.veh_Cx==null){
                sb.append("车型不能为空;")
            }
            if(STzcInfo.veh_Fdjh==''||STzcInfo.veh_Fdjh==null){
                sb.append("发动机号不能为空;")
            }
        return sb.toString()
    }
    /**
     * Description 山拖合格证查询方法
     * @CreateTime2016-06-16 by zhuwei
     * @param params
     */
    def selectSTInfoList(params){
            def sta = params.startTime+" 00:00:00"
            def end = params.endTime+" 23:59:59"
            def cel={
                if(params.upload){
                    eq('upload',"${params.upload}")
                }
                if(params.isprint){
                    eq('isprint',"${params.isprint}")

                }
                if(params.startTime){
                    ge('createTime',sta)
                }
                if(params.endTime){
                    le('createTime',end)
                }
                if(params.veh_Hgzbh){
                    like('veh_Hgzbh',"%${params.veh_Hgzbh}%")

                }
                if(params.veh_Fdjh){
                    like('veh_Fdjh',"%${params.veh_Fdjh}%")

                }
                if(params.veh_Cx){
                    eq('veh_Cx',"${params.veh_Cx}")

                }
                if (params.veh_Hgzbh1&&params.veh_Hgzbh2){
                    between("veh_Hgzbh",params.veh_Hgzbh1,params.veh_Hgzbh2)
                }else{
                    if (params.veh_Hgzbh1&&!params.veh_Hgzbh2){
                        ge("veh_Hgzbh",params.veh_Hgzbh1)
                    }else if (!params.veh_Hgzbh1&&params.veh_Hgzbh2){
                        le("veh_Hgzbh",params.veh_Hgzbh2)
                    }
                }

                if(params.organCode.size()>0){
                    inList("veh_workshopno", params.organCode)
                }
                order ("veh_Hgzbh","desc")
            }
            def lst=STInfo.createCriteria().list (params,cel)
            return lst
        }
    /**
     * @Description 计算农装环保代码
     * @Create 2017-07-20    QJ
     */
    def calculateVIN(params,loginUser){

        String veh_Qydm='H36'
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
        String type = params.type?.trim()
        int count_modle_type=params.modle_type?.length()
        String modle_type=params.modle_type?.trim()
        def result=[:]//定义返回map  flag为0表示 解析不成功1 表示成功 msg 存储 解析不成功原因 vin 存储解析成功后的vin号
        StringBuilder vinpre=new StringBuilder(); //vin前8位
        StringBuilder vinnext=new StringBuilder();//vin8位之后
        if(modle_type&&count_modle_type==1){
            if(modle_type.equals('1')){
                veh_Qydm ='576'
            }
        }else{
            result.put('flag','0');
            result.put('msg','公告类型不允许为空');
            return result as JSON
        }
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
            String year = veh_Clbh.substring(0,2)
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
     * @Description 根据条件选择合格证信息 ，使用sql
     * @author zhuwei
     * @param params
     * @Create 2016-06-16
     */
    def selectTSinfoByShop(params){
        def cel={
            if(params.startTime){
                def startTime= params.startTime +' 00:00:00'
                ge('createTime',startTime)
            }
            if(params.endTime){
                def endTime= params.endTime +' 23:59:59'
                le('createTime',endTime)
            }
            if (params.veh_Hgzbh) {
                like("veh_Hgzbh","%${params.veh_Hgzbh}%")
            }
            if(params.upload){
                eq("upload","${params.upload}")
            }
            if (params.organCodeList.size()>0) {
                inList("veh_workshopno", params.organCodeList)
            }
            if(params.veh_Fdjh){
                like('veh_Fdjh',"%${params.veh_Fdjh}%")

            }
            if(params.isprint){
                eq('isprint',"${params.isprint}")

            }

            order ("veh_Hgzbh","desc")
        }
        def lst=STInfo.createCriteria().list(params,cel)
        def results=[]
        lst?.each {l->
            def m=[:]
            l.properties.each {
                m."${it.key}"=it.value
            }
            results.add(m)
        }

        def result = [rows: results,total: lst.totalCount]
    }

}
