package cn.com.wz.vehcert.zcinfo

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.Certificate
import cn.com.wz.vehcert.PrintResult
import cn.com.wz.vehcert.SysInfoService
import cn.com.wz.vehcert.SysInfoServiceLocator
import cn.com.wz.vehcert.carstorage.HistoryGgpc
import cn.com.wz.vehcert.carstorage.PreCarStorage
import cn.com.wz.vehcert.coc.CocCarStoragePrint
import grails.converters.JSON
import groovy.sql.Sql
import org.apache.commons.lang.ArrayUtils

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

/**
 * @Description 合格证信息Service
 * @Create 2013-08-04 mike
 */
class ZcInfoService {
    def static final yearsCode=['2001':'1','2002':'2','2003':'3','2004':'4','2005':'5','2006':'6','2007':'7','2008':'8','2009':'9','2010':'A'
                                ,'2011':'B','2012':'C','2013':'D','2014':'E','2015':'F','2016':'G','2017':'H','2018':'J','2019':'K','2020':'L'
                                ,'2021':'M','2022':'N','2023':'P','2024':'R','2025':'S','2026':'T','2027':'V','2028':'W','2029':'X','2030':'Y'
                                ,'2031':'1','2032':'2','2033':'3','2034':'4','2035':'5','2036':'6','2037':'7','2038':'8','2039':'9','2040':'A']
    def static final compareCode=['0':0,'1':1,'2':2,'3':3,'4':4,'5':5,'6':6,'7':7,'8':8,'9':9
                                  ,'A':1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'J':1,'K':2
                                  ,'L':3,'M':4,'N':5,'P':7,'R':9,'S':2,'T':3,'U':4,'V':5,'W':6
                                  ,'X':7,'Y':8,'Z':9]
    def static final powCode=[8,7,6,5,4,3,2,10,-1,9,8,7,6,5,4,3,2]

    def sqlService
    /**
     * @Description 计算VIN
     * @Create 2013-08-04
     * @UpdateVIN编制规则vinpre总质量由veh_Zzl<1800添加1变为veh_Zzl<1700添加1
     * @Update 新能源汽车VIN生成 2017-09-07
     */
    def calculateVIN(params){
        String kind=params.kind
        String veh_VinFourBit=params.veh_VinFourBit?.trim()
        String veh_Zzl=params.veh_Zzl?.trim()
        String veh_Jsslx=params.veh_Jsslx?.trim()
        String veh_Rlzl=params.veh_Rlzl?.trim()
        String veh_Pl=params.veh_Pl?.trim()
        String veh_Zs=params.veh_Zs?.trim()
        String veh_Zj=params.veh_Zj?.replaceAll(' ','')//轴距由表达式 清除所有空格
        String veh_Zchgzbh=params.veh_Zchgzbh?.trim()
        String veh_Cpscdz=params.veh_Cpscdz?.trim()
        //农用车使用
        String veh_Clfl=params.veh_Clfl?.trim()
        String veh_Gl=params.veh_Gl?.trim()
        String veh_zdjgl=params.veh_zdjgl?.trim()
        String veh_other=params.veh_other?.trim()
        def rl =''
        def dczl=''
        def dcrl=''
        def result=[:]//定义返回map  flag为0表示 解析不成功1 表示成功 msg 存储 解析不成功原因 vin 存储解析成功后的vin号
        if (veh_Rlzl) {
            if (veh_Rlzl == '电') {
                if (veh_other) {
                    String vinFlag = veh_other.substring(veh_other.indexOf("[")+1, veh_other.indexOf("]"))
                    def other = vinFlag?.split("/")
                    if (other.size() == 3) {
                        rl = other[0] ? other[0] : ''//燃料
                        dczl = other[1] ? other[1] : ''//电池种类
                        dcrl = other[2] ? other[2] : ''//电池容量
                        for (int i = 0; i < rl.length(); i++){
                            if (!Character.isDigit(rl.charAt(i))){
                                result.put('flag', '0');
                                result.put('msg', '请检查新能源汽车公告其它信息里的电池标志');
                                return result as JSON
                            }
                        }
                        for (int i = 0; i < dczl.length(); i++){
                            if (!Character.isDigit(dczl.charAt(i))){
                                result.put('flag', '0');
                                result.put('msg', '请检查新能源汽车公告其它信息里的电池标志');
                                return result as JSON
                            }
                        }
                        for (int i = 0; i < dcrl.length(); i++){
                            if (!Character.isDigit(dcrl.charAt(i))){
                                result.put('flag', '0');
                                result.put('msg', '请检查新能源汽车公告其它信息里的电池标志');
                                return result as JSON
                            }
                        }

                    } else {
                        result.put('flag', '0');
                        result.put('msg', '请检查新能源汽车公告电池信息');
                        return result as JSON
                    }
                } else {
                    result.put('flag', '0');
                    result.put('msg', '新能源汽车其它信息不允许为空');
                    return result as JSON
                }

            }
        } else {
            result.put('flag', '0');
            result.put('msg', '燃料种类不允许为空');
            return result as JSON
        }
        StringBuilder vinpre=new StringBuilder(); //vin前9位
        StringBuilder vinnext=new StringBuilder();//vin9位之后
        if (kind=='0'){ //汽车VIN计算
            vinpre.append('LZ0');
            if(veh_VinFourBit&&veh_VinFourBit.length()==1){
                vinpre.append(veh_VinFourBit);//VIN第四位
            }else{
                result.put('flag','0');
                result.put('msg','vin第四位不符合要求');
                return result as JSON
            }
            if (veh_Rlzl) {
                if (veh_Rlzl == '电') {
                    double rl_double = Double.valueOf(rl);
                    if (rl_double == 0) {
                        if (veh_zdjgl) {
                            try {
                                double veh_zdjgl_double = Double.valueOf(veh_zdjgl);
                                if (veh_zdjgl_double < 35) {
                                    vinpre.append('A');
                                } else if (veh_zdjgl_double < 60) {
                                    vinpre.append('B');
                                } else if (veh_zdjgl_double < 100) {
                                    vinpre.append('D');
                                } else {
                                    vinpre.append('E');
                                }
                            } catch (Exception e) {
                                result.put('flag', '0');
                                result.put('msg', '总质量不符合要求');
                                return result as JSON
                            }
                        } else {
                            result.put('flag', '0');
                            result.put('msg', '电机峰值不允许为空');
                            return result as JSON
                        }
                    } else if (rl_double == 1) {
                        if (veh_zdjgl) {
                            try {
                                double veh_zdjgl_double = Double.valueOf(veh_zdjgl);
                                if (veh_zdjgl_double < 35) {
                                    vinpre.append('V');
                                } else if (veh_zdjgl_double < 60) {
                                    vinpre.append('W');
                                } else if (veh_zdjgl_double < 100) {
                                    vinpre.append('X');
                                } else {
                                    vinpre.append('Z');
                                }
                            } catch (Exception e) {
                                result.put('flag', '0');
                                result.put('msg', '总质量不符合要求');
                                return result as JSON
                            }
                        } else {
                            result.put('flag', '0');
                            result.put('msg', '电机峰值不允许为空');
                            return result as JSON
                        }
                    } else {
                        vinpre.append('N');
                    }
                } else {
                    if (veh_Zzl) {
                        try {
                            double veh_Zzl_double = Double.valueOf(veh_Zzl);
                            if (veh_Zzl_double <= 1700) {
                                vinpre.append('1');
                            } else if (veh_Zzl_double <= 2500) {
                                vinpre.append('0');
                            } else if (veh_Zzl_double <= 3500) {
                                vinpre.append('4');
                            } else if (veh_Zzl_double <= 4500) {
                                vinpre.append('9');
                            } else if (veh_Zzl_double <= 7500) {
                                vinpre.append('C');
                            } else if (veh_Zzl_double <= 9500) {
                                vinpre.append('D');
                            } else if (veh_Zzl_double <= 12000) {
                                vinpre.append('E');
                            } else {
                                vinpre.append('F');
                            }
                        } catch (Exception e) {
                            result.put('flag', '0');
                            result.put('msg', '总质量不符合要求');
                            return result as JSON
                        }
                    } else {
                        result.put('flag', '0');
                        result.put('msg', '总质量不允许为空');
                        return result as JSON
                    }
                }
            } else {
                result.put('flag', '0');
                result.put('msg', '燃料种类不允许为空');
                return result as JSON
            }
            if (veh_Jsslx && veh_Jsslx.length() == 1) {
                vinpre.append(veh_Jsslx);//驾驶室类型
            } else {
                result.put('flag', '0');
                result.put('msg', '驾驶室类型不符合要求');
                return result as JSON
            }
            if(veh_Rlzl){
                if (veh_Rlzl=='柴油'){
                    if(veh_Pl){
                        try{
                            double veh_Pl_double=Double.valueOf(veh_Pl) ;
                            if (veh_Pl_double<2000){
                                vinpre.append('A');
                            } else if (veh_Pl_double<3000){
                                vinpre.append('B');
                            } else if (veh_Pl_double<5000){
                                vinpre.append('D');
                            } else{
                                vinpre.append('E');
                            }
                        }catch (Exception e){
                            result.put('flag','0');
                            result.put('msg','排量不符合要求');
                            return result as JSON
                        }
                    }else{
                        result.put('flag','0');
                        result.put('msg','排量不允许为空');
                        return result as JSON
                    }
                }else if (veh_Rlzl=='汽油'){
                    if(veh_Pl){
                        try{
                            double veh_Pl_double=Double.valueOf(veh_Pl) ;
                            if (veh_Pl_double<1000){
                                vinpre.append('V');
                            } else if (veh_Pl_double<=1600){
                                vinpre.append('W');
                            } else if (veh_Pl_double<3000){
                                vinpre.append('X');
                            } else{
                                vinpre.append('Z');
                            }
                        }catch (Exception e){
                            result.put('flag','0');
                            result.put('msg','排量不符合要求');
                            return result as JSON
                        }
                    }else{
                        result.put('flag','0');
                        result.put('msg','排量不允许为空');
                        return result as JSON
                    }
//                }else if (veh_Rlzl.contains('NG')){//将天然气细化为：天然气：NG；压缩天然气：CNG；液化天然气：LNG ，VIN第七位为  C
                    //因为以前做公告的天然气新能源汽车是用字母表示，但不符合合格证规范，正常的是使用汉字组合
                    //组合形式有：天然气、汽油/天然气，以后还有可能还有的组合：压缩天然气、液化天然气
                }else if (veh_Rlzl.contains('天然气')){//将天然气细化为：天然气：NG；压缩天然气：CNG；液化天然气：LNG ，VIN第七位为  C
                    //
                    vinpre.append('C');
//                }else if (veh_Rlzl=='电动车'){         //以前公告资源部要求填写电动车
                } else if (veh_Rlzl == '电') {            //现在改成了电
                    if (dczl) {
                        double dczl_double = Double.valueOf(dczl);
                        if (dczl_double == 0) {
                            try {
                                double dcrl_double = Double.valueOf(dcrl);
                                if (dcrl_double < 100) {
                                    vinpre.append('A');
                                } else if (dcrl_double < 150) {
                                    vinpre.append('B');
                                } else if (dcrl_double < 200) {
                                    vinpre.append('C');
                                } else {
                                    vinpre.append('D');
                                }
                            } catch (Exception e) {
                                result.put('flag', '0');
                                result.put('msg', '电池容量不符合要求');
                                return result as JSON
                            }

                        } else if (dczl_double == 1) {
                            try {
                                double dcrl_double = Double.valueOf(dcrl);
                                if (dcrl_double < 100) {
                                    vinpre.append('E');
                                } else if (dcrl_double <= 150) {
                                    vinpre.append('F');
                                } else if (dcrl_double < 200) {
                                    vinpre.append('G');
                                } else {
                                    vinpre.append('H');
                                }
                            } catch (Exception e) {
                                result.put('flag', '0');
                                result.put('msg', '电池容量不符合要求');
                                return result as JSON
                            }

                        } else {
                            vinpre.append('K');
                        }
                    } else {
                        result.put('flag', '0');
                        result.put('msg', '  电池种类信息不允许为空');
                        return result as JSON
                    }
                }else{//液化石油气LPG，第七位为L
                    vinpre.append('L');
                }
            }else{
                result.put('flag','0');
                result.put('msg','燃料种类不允许为空');
                return result as JSON
            }
            if(veh_Zs){
                if (veh_Zs=='2'){
                    if(veh_Zj){
                        try{
                            double veh_Zj_double=0.0;
                            if (veh_Zj.contains("+")){
                                String[] cals=veh_Zj.split("\\+")
                                for(int i=0;i<cals.length;i++){
                                    double tmp=Double.valueOf(cals[i]) ;
                                    veh_Zj_double+= tmp
                                }
                            }else{
                                veh_Zj_double=Double.valueOf(veh_Zj) ;
                            }
                            if (veh_Zj_double<=2200){
                                vinpre.append('1');
                            } else if (veh_Zj_double<=3000){
                                vinpre.append('2');
                            } else if (veh_Zj_double<=3800){
                                vinpre.append('3');
                            } else if (veh_Zj_double<=5000){
                                vinpre.append('4');
                            }else{
                                vinpre.append('5');
                            }
                        }catch (Exception e){
                            result.put('flag','0');
                            result.put('msg','轴距不符合要求');
                            return result as JSON
                        }
                    }else{
                        result.put('flag','0');
                        result.put('msg','轴距不允许为空');
                        return result as JSON
                    }
                }else if (veh_Zs=='3'){
                    if(veh_Zj){
                        try{
                            double veh_Zj_double=0.0;
                            if (veh_Zj.contains('+')){
                                String[] cals=veh_Zj.split('\\+')
                                for(int i=0;i<cals.length;i++){
                                    double tmp=Double.valueOf(cals[i]) ;
                                    veh_Zj_double+= tmp
                                }
                            }else{
                                veh_Zj_double=Double.valueOf(veh_Zj) ;
                            }
                            if (veh_Zj_double<=4000){
                                vinpre.append('6');
                            } else if (veh_Zj_double<=5000){
                                vinpre.append('7');
                            } else{
                                vinpre.append('8');
                            }
                        }catch (Exception e){
                            result.put('flag','0');
                            result.put('msg','轴距不符合要求');
                            return result as JSON
                        }
                    }else{
                        result.put('flag','0');
                        result.put('msg','轴距不允许为空');
                        return result as JSON
                    }
                } else{
                    result.put('flag','0');
                    result.put('msg','轴数不符合要求');
                    return result as JSON
                }
            }else{
                result.put('flag','0');
                result.put('msg','轴数不允许为空');
                return result as JSON
            }
            ////////////////////////////////////////////////////////10位之后
            String waterCode=''
            if(veh_Zchgzbh&&veh_Zchgzbh.length()==14){
                String year=   veh_Zchgzbh.substring(4,6)
                waterCode= veh_Zchgzbh.substring(8,14)
                year='20'+year
                String yearCode=yearsCode.get(year)  ;
                if (yearCode)     {
                    vinnext.append(yearCode);//年份
                } else{
                    result.put('flag','0');
                    result.put('msg','合格证中年份不符合要求');
                    return result as JSON
                }
            }else{
                result.put('flag','0');
                result.put('msg','合格证编号不符合要求');
                return result as JSON
            }

            if(veh_Cpscdz=='杭州市余杭区五常荆长路号'){
                vinnext.append('0');//驾驶室类型
            }else if (veh_Cpscdz=='浙江省三门县健跳镇大塘'){
                vinnext.append('2');//驾驶室类型
            } else{
                vinnext.append('1');//驾驶室类型
            }
            if (waterCode&&waterCode.length()==6){
                vinnext.append(waterCode);//驾驶室类型
            }else{
                result.put('flag','0');
                result.put('msg','合格证中流水号不符合要求');
                return result as JSON
            }
            //生成VIN中第9位 校验位
            String tmpVIN= vinpre.toString()+'*'+ vinnext.toString();
            int tmpNum=0;
            if (tmpVIN&&tmpVIN.length()==17){
                for(int i=0;i<17;i++){
                    String tmpChar=tmpVIN.substring(i, i+1)
                    if (i!=8){
                        def compareValue=compareCode.get(tmpChar)
                        if (compareValue!=null){
                            tmpNum+=(compareValue*(powCode.get(i)));
                        }else{
                            result.put('flag','0');
                            result.put('msg','VIN生成出错');
                            return result as JSON
                        }
                    }
                }
            }else{
                result.put('flag','0');
                result.put('msg','VIN生成出错');
                return result as JSON
            }
            int valid=tmpNum%11;
            if (valid==10){
                vinpre.append('X');
            }else{
                vinpre.append(valid);
            }
            String vin=vinpre.toString()+vinnext.toString()
            result.put('flag','1');
            result.put('vin',vin);
            return result as JSON
        }else{ //农用车VIN计算
            vinpre.append('L5Z');
            if(veh_Clfl){
                if (veh_Clfl=='三轮汽车'){
                    vinpre.append('1');//车辆类型
                }else if (veh_Clfl=='低速货车'){
                    vinpre.append('2');//车辆类型
                }else{
                    vinpre.append('3');//车辆类型
                }
            }else{
                result.put('flag','0');
                result.put('msg','车辆类型不允许为空');
                return result as JSON
            }
            if(veh_Jsslx&&veh_Jsslx.length()==1){
                vinpre.append(veh_Jsslx);//驾驶室类型
            }else{
                result.put('flag','0');
                result.put('msg','车身类型不符合要求');
                return result as JSON
            }
            if (veh_Zzl) {
                try {
                    double veh_Zzl_double = Double.valueOf(veh_Zzl);
                    if (veh_Zzl_double <= 1500) {
                        vinpre.append('1');
                    } else if (veh_Zzl_double <= 2000) {
                        vinpre.append('2');
                    } else if (veh_Zzl_double <= 3000) {
                        vinpre.append('3');
                    } else if (veh_Zzl_double <= 4000) {
                        vinpre.append('4');
                    } else if (veh_Zzl_double <= 5000) {
                        vinpre.append('5');
                    } else if (veh_Zzl_double <= 6000) {
                        vinpre.append('6');
                    } else if (veh_Zzl_double <= 7000) {
                        vinpre.append('7');
                    } else if (veh_Zzl_double <= 8000) {
                        vinpre.append('8');
                    } else if (veh_Zzl_double <= 9000) {
                        vinpre.append('9');
                    } else if (veh_Zzl_double <= 10000) {
                        vinpre.append('A');
                    } else if (veh_Zzl_double <= 11000) {
                        vinpre.append('B');
                    } else {
                        vinpre.append('C');
                    }
                }catch (Exception e){
                    result.put('flag','0');
                    result.put('msg','总质量不符合要求');
                    return result as JSON
                }
            }else{
                result.put('flag','0');
                result.put('msg','总质量不允许为空');
                return result as JSON
            }
            if(veh_Zj){
                try{
                    double veh_Zj_double=0.0;
                    if (veh_Zj.contains('+')){
                        String[] cals=veh_Zj.split('+')
                        for(int i=0;i<cals.length;i++){
                            double tmp=Double.valueOf(cals[i]) ;
                            veh_Zj_double+= tmp
                        }
                    }else{
                        veh_Zj_double=Double.valueOf(veh_Zj) ;
                    }
                    if (veh_Zj_double<=2350){
                        vinpre.append('2');
                    } else if (veh_Zj_double<=2500){
                        vinpre.append('3');
                    } else if (veh_Zj_double<=2650){
                        vinpre.append('4');
                    } else if (veh_Zj_double<=2800){
                        vinpre.append('5');
                    }else if (veh_Zj_double<=2950){
                        vinpre.append('6');
                    }else if (veh_Zj_double<=3100){
                        vinpre.append('7');
                    }else if (veh_Zj_double<=3250){
                        vinpre.append('8');
                    }else if (veh_Zj_double<=3400){
                        vinpre.append('9');
                    }else if (veh_Zj_double<=3600){
                        vinpre.append('A');
                    }else if (veh_Zj_double<=3800){
                        vinpre.append('B');
                    }else{
                        vinpre.append('C');
                    }
                }catch (Exception e){
                    result.put('flag','0');
                    result.put('msg','轴距不符合要求');
                    return result as JSON
                }
            }else{
                result.put('flag','0');
                result.put('msg','轴距不允许为空');
                return result as JSON
            }
            if(veh_Rlzl){
                if (veh_Rlzl=='柴油'){
                    if(veh_Gl){
                        try{
                            double veh_Gl_double=Double.valueOf(veh_Gl) ;
                            if (veh_Gl_double<=10){
                                vinpre.append('A');
                            } else if (veh_Gl_double<=14){
                                vinpre.append('B');
                            } else if (veh_Gl_double<=17){
                                vinpre.append('C');
                            }else if (veh_Gl_double<=20){
                                vinpre.append('D');
                            }else if (veh_Gl_double<=30){
                                vinpre.append('E');
                            }else if (veh_Gl_double<=40){
                                vinpre.append('F');
                            }else if (veh_Gl_double<=50){
                                vinpre.append('G');
                            }else if (veh_Gl_double<=60){
                                vinpre.append('H');
                            }else if (veh_Gl_double<=70){
                                vinpre.append('J');
                            }else if (veh_Gl_double<=90){
                                vinpre.append('K');
                            }else if (veh_Gl_double<=100){
                                vinpre.append('L');
                            } else{
                                vinpre.append('M');
                            }
                        }catch (Exception e){
                            result.put('flag','0');
                            result.put('msg','功率不符合要求');
                            return result as JSON
                        }
                    }else{
                        result.put('flag','0');
                        result.put('msg','功率不允许为空');
                        return result as JSON
                    }
                }else{
                    result.put('flag','0');
                    result.put('msg','燃料种类不符合要求');
                    return result as JSON
                }
            }else{
                result.put('flag','0');
                result.put('msg','燃料种类不允许为空');
                return result as JSON
            }
            ////////////////////////////////////////////////////////10位之后
            String waterCode=''
            String symbol=''
            if(veh_Zchgzbh&&veh_Zchgzbh.length()==14){
                String year=   veh_Zchgzbh.substring(4,6)
                waterCode= veh_Zchgzbh.substring(8,14)
                symbol= veh_Zchgzbh.substring(6,8)
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
                result.put('msg','合格证编号不符合要求');
                return result as JSON
            }
            vinnext.append('1');//第11位
            if (waterCode&&waterCode.length()!=6){
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
            if (symbol=='A1'){
                if (waterCode_int<=100000){
                    vinnext.append('1');//第12位
                }else{
                    vinnext.append('4');//第12位
                }
            }else if (symbol=='A2'){
                if (waterCode_int<=100000){
                    vinnext.append('2');//第12位
                }else{
                    vinnext.append('5');//第12位
                }
            }else if (symbol=='A3'){
                vinnext.append('3');//第12位
            }else if (symbol=='B1'){
                vinnext.append('6');//第12位
            }else if (symbol=='C1'){
                vinnext.append('7');//第12位
            }else{
                vinnext.append('8');//第12位
            }
            vinnext.append(waterCode.substring(1));//第13-17位

            //生成VIN中第9位 校验位
            String tmpVIN= vinpre.toString()+'*'+ vinnext.toString();
            int tmpNum=0;
            if (tmpVIN&&tmpVIN.length()==17){
                for(int i=0;i<17;i++){
                    String tmpChar=tmpVIN.substring(i, i+1)
                    if (i!=8){
                        def compareValue=compareCode.get(tmpChar)
                        if (compareValue!=null){
                            int value=compareValue*(powCode.get(i))
                            tmpNum+=value;
                        }else{
                            result.put('flag','0');
                            result.put('msg','VIN生成出错');
                            return result as JSON
                        }
                    }
                }
            }else{
                result.put('flag','0');
                result.put('msg','VIN生成出错');
                return result as JSON
            }
            int valid=tmpNum%11;
            if (valid==10){
                vinpre.append('X');
            }else{
                vinpre.append(valid);
            }
            String vin=vinpre.toString()+vinnext.toString()
            result.put('flag','1');
            result.put('vin',vin);
            return result as JSON
        }
    }

    /**
     * @Description 根据条件获取满足条件的合格证信息
     * @param params
     * @Create 2013-08-04 huxx
     */
    def selectZCInfoList(params){
        def sta = params.startTime+" 00:00:00"
        def end = params.endTime+" 23:59:59"
        def cel={
            if(params.web_status){
                eq('web_status',"${params.web_status}")
            }
            if(params.startTime){
                ge('createTime',sta)
            }
            if(params.endTime){
                le('createTime',end)
            }
            if(params.veh_Zchgzbh){
                eq('veh_Zchgzbh',"${params.veh_Zchgzbh}")

            }
            if(params.veh_Clsbdh){
                eq('veh_Clsbdh',"${params.veh_Clsbdh}")

            }
            if (params.veh_Zchgzbh1&&params.veh_Zchgzbh2){
                between("veh_Zchgzbh",params.veh_Zchgzbh1,params.veh_Zchgzbh2)
            }else{
                if (params.veh_Zchgzbh1&&!params.veh_Zchgzbh2){
                    ge("veh_Zchgzbh",params.veh_Zchgzbh1)
                }else if (!params.veh_Zchgzbh1&&params.veh_Zchgzbh2){
                    le("veh_Zchgzbh",params.veh_Zchgzbh2)
                }
            }
            if(params.organCode.size()>0){
//                eq('veh_workshopno',params.organCode)
                inList("veh_workshopno", params.organCode)
            }
            order ("web_status", "asc")
            order ("veh_Zchgzbh","asc")
        }
        def lst=ZCInfo.createCriteria().list (params,cel)
        return lst
    }

    /**
     * @Description 根据条件将已打印的一致性证书同步到服务器上
     * @param params
     * @return 返回同步失败的记录id集合
     * @Create 2013-08-05 huxx
     */
    def uploadCoc(params){
        def result=[]
        def remoteDB=params.remoteDB
        def cel={
            eq('status',0)
        }
        def lst=CocCarStoragePrint.createCriteria().list(cel)

        def sqlList=[]
        lst?.each{coc->
            //可以在这里先读取远程数据库判断记录是否已经存在 ,如果存在就将记录的id保存到result （暂时未做）
            StringBuffer sbf = new StringBuffer("insert into ");
            sbf.append("COC_INFO");  /**表名**/
            String col="(id,version,"
            String value="values('${coc.id}',1,"
            coc.properties?.each{
                switch(it.key){
                    case 'coc_createTime' :
                        col+="coc_create_time,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "coc_updateTime" :
                        col+="coc_update_time,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "coc_createrId" :
                        col+="coc_creater_id,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "coc_updaterId" :
                        col+="coc_updater_id,"
                        value+="'${it.value?it.value:''}',"
                        break;
                //coc_qr_code 大字段处理,因为是大字段不再数据库间同步了
                    case "coc_qr_code" :
//                        OracleParameter pa1 = new OracleParameter(":content", OracleDbType.Varchar2);
//                        col+="coc_qr_code,"
//                        value+="'${it.value?it.value:''}',"
                        break;
                    case "coc_creater" :
                        break;
                    case "coc_updater" :
                        break;
                    case "status" :
                        col+="${it.key},"
                        value+="0,"
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
        if (uploadResult){
            if (lst?.size()>0){
                StringBuffer sb=new StringBuffer("update CocCarStoragePrint set status=1, coc_updateTime='${createTime}' where id in (")
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
                        CocCarStoragePrint.executeUpdate(sb.toString())
                        sb=new StringBuffer("update CocCarStoragePrint set status=1, coc_updateTime='${createTime}'  where id in ('${it.id}'")
                        count=count-999
                    }
                    count+=1
                }
                sb.append(")")
                CocCarStoragePrint.executeUpdate(sb.toString())
            }
        } else{
            result.addAll(lst?.id?.collect())
        }
        def now=new Date()
        def old=now-Integer.parseInt(params.leftDay)

        def oldTime=DateUtil.getFormatTime(old,null)
        //删除当天以前的并且已上传的一致性证书，只保留配置天数内提交的一致性证书信息
        def cocList= CocCarStoragePrint.findAll("from CocCarStoragePrint as z where z.status=1 and  substring(z.coc_updateTime,0,11)<=:createTime",[createTime:oldTime?.toString()?.substring(0,11)])
        if (cocList?.size()>0){
            StringBuffer sb=new StringBuffer("delete from CocCarStoragePrint  where id in (")
            int count=0
            cocList?.each{
                if(count<999){
                    if (count==0){
                        sb.append("'${it.id}'")
                    }else{
                        sb.append(",'${it.id}'")
                    }

                }else{
                    sb.append(")")
                    CocCarStoragePrint.executeUpdate(sb.toString())
                    sb=new StringBuffer("delete from CocCarStoragePrint  where id in ('${it.id}'")
                    count=count-999
                }
                count+=1
            }
            sb.append(")")
            CocCarStoragePrint.executeUpdate(sb.toString())
        }


        return result
    }
    /**
     * @Description 根据条件将合格证信息同步到服务器上
     * @param params
     * @Create 2013-08-05 huxx
     */
    def uploadZcInfo(params){
        def result=[]
        def failList=params.failList
        def remoteDB=params.remoteDB
        if (!failList){
            failList.add("就不信你存在")
        }
        def cel={
            eq('web_status','0')
            //解决in中字符串不大于1000的问题
            def lst=StringUtil.splitIn(failList)
            and{
                lst?.each {id->
                    not{
                        'in'('id',id)
                    }
                }
            }
            if (params.veh_Zchgzbh1){
                ge("veh_Zchgzbh",params.veh_Zchgzbh1)
            }
            if (params.veh_Zchgzbh2){
                le("veh_Zchgzbh",params.veh_Zchgzbh2)
            }
            eq('veh_workshopno',params.factoryCode)
        }
        def lst=ZCInfo.createCriteria().list(cel)
        def sqlList=[]
        lst?.each{zc->
            //可以在这里先读取远程数据库判断记录是否已经存在 ,如果存在就将记录的id保存到result （暂时未做）
            StringBuffer sbf = new StringBuffer("insert into ");
            sbf.append("WZH_ZCINFO");  /**表名**/
            String col="(zcinfoid,"
            String value="values('${zc.id}',"
            zc.properties?.each{
                switch(it.key){
                    case "web_status" :
                        col+="${it.key},"
                        value+="0,"
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
                    case "veh_VinFourBit" :
                        col+="veh_vin_four_bit,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "zcinforeplaces":
                        break;
                    case "veh_workshopno" :
                        col+="veh_workshopno,"
                        value+="'${params.factoryCode}',"
                        break;
                    case "upload_Path" :
                        col+="upload_Path,"
                        value+="'',"
                        break;
                    case "processType" :
                        col+="process_type,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "needUpload" :
                        col+="need_upload,"
                        value+="'${it.value?it.value:''}',"
                        break;
                    case "transDate" :
                        col+="TRANS_DATE,"
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
                String prix="update ZCInfo set web_status='1', updateTime='${createTime}' ,updaterId='${updaterId}'"
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
                        ZCInfo.executeUpdate(sb.toString())
                        sb=new StringBuffer("${prix}  where id in ('${it.id}'")
                        count=count-999
                    }
                    count+=1
                }
                sb.append(")")
                ZCInfo.executeUpdate(sb.toString())
            }
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>合格证信息更新成功"
        } else{
            result.addAll(lst.collect())
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>合格证信息更新失败"
        }

        //当上传的合格证信息不为空时删除当天以前已上传的合格证信息，当上传记录为空时不删除以前的合格证信息
        if(uploadResult&&lst && "1".equals(params.changeStatus)){
            def now=new Date()
            def old=now-Integer.parseInt(params.leftDay)
            def oldTime=DateUtil.getFormatTime(old,null)
            //删除当天以前的并且已上传的合格证信息，只保留当天提交的合格证信息
            def zcList= ZCInfo.findAll("from ZCInfo as z where z.web_status='1' and substring(z.updateTime,0,11)<=:createTime",[createTime:oldTime?.toString()?.substring(0,11)])
            if (zcList?.size()>0){
                StringBuffer sb=new StringBuffer("delete from ZCInfo  where id in (")
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
                        ZCInfo.executeUpdate(sb.toString())
                        sb=new StringBuffer("delete from ZCInfo  where id in ('${it.id}'")
                        count=count-999
                    }
                    count+=1
                }
                sb.append(")")
                ZCInfo.executeUpdate(sb.toString())
            }

            //获取农用车、汽车整车、汽车底盘的上传记录的最大合格证编号
            def nyMaxNum=''
            def zcMaxNum=''
            def dpMaxNum=''
            lst?.each{
                if(it.processType=='0'){ //仅计算常规生成的合格证的最大号
                    if(it.veh_Type=='1'){//农用车合格证号比较
                        if (nyMaxNum<it.veh_Zchgzbh){
                            nyMaxNum=it.veh_Zchgzbh
                        }
                    }else{
                        if (it.veh_Clztxx?.toUpperCase()=='QX'){    //整车合格证比较
                            if (zcMaxNum<it.veh_Zchgzbh) {
                                zcMaxNum=it.veh_Zchgzbh
                            }
                        }else if (it.veh_Clztxx?.toUpperCase()=='DP'){//底盘合格证比较
                            if (dpMaxNum<it.veh_Zchgzbh){
                                dpMaxNum=it.veh_Zchgzbh
                            }
                        }
                    }
                }
            }

            //获取汽车的最大流水号
            def qcMaxNum=''
            if (zcMaxNum){
                if (dpMaxNum){
                    def zc=zcMaxNum[4..5]+zcMaxNum[8..13]
                    def dp=dpMaxNum[4..5]+dpMaxNum[8..13]
                    if (zc<dp){
                        qcMaxNum=zcMaxNum[0..3]+dp[0..1]+"${params.symboStr}"+dp[2..7]
                    }else{
                        qcMaxNum=zcMaxNum[0..3]+zc[0..1]+"${params.symboStr}"+zc[2..7]
                    }
                }else{
                    qcMaxNum=zcMaxNum
                }
            }else{
                if (dpMaxNum){
                    qcMaxNum=dpMaxNum[0..5]+"${params.symboStr}"+dpMaxNum[6..13]
                }else{

                }
            }
            def user=User.get(params.userId)
            def codeList=''
            user.organs.each{
                codeList=it.organCode
            }
            def printset=PrintSet.findByCodeAndUserid("main",codeList)
            if (printset){
                printset.maxQX_car_upload=qcMaxNum
                printset.maxQX_arg_upload=nyMaxNum
                printset.save()
            }else{
                printset=new PrintSet()
                printset.code="main"
                printset.userid=codeList
                printset.maxQX_car_upload=qcMaxNum
                printset.maxQX_arg_upload=nyMaxNum
                printset.save()
            }
        }


        return result
    }

    /**  同步数据
     * @update huxx 2013-08-16 添加了出现异常时数据回滚
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
     *@Descriptions 合格证日期变更 同时插入变更记录表中  （tbl_zcinfo   tbl_zcinfoReplace）
     * @Auther zouq
     * @createTime  2013-08-05
     */
    def changeZcinfoUpdate(def fz_date,def zcinf_ID,User loginUser)      ///变更合格证、同时插入到变更记录表（tbl_zcinfoReplace）
    {
        def sql =
                "INSERT INTO WZH_ZCINFO_REPLACE" +
                        "  (id,AUTH_ID,AUTH_REMARK,AUTH_TIME,CHANGE_TIME,CHANGE_FIELD,CREATE_TIME,CREATER_ID,REMARK,SALESMANNAME,SALESMANTEL,UPLOAD,UPLOAD_R,USED,USED2,USED2_R,USED_R,VEH_BGCAZZDYXZZL," +
                        "   VEH_BGCAZZDYXZZL_R,VEH_BZ,VEH_BZ_R,VEH_CJH,VEH_CJH_R,VEH_CLFL,VEH_CLFL_R,VEH_CLMC,VEH_CLMC_R,VEH_CLPP,VEH_CLPP_R,VEH_CLSBDH,VEH_CLSBDH_R,VEH_CLXH,VEH_CLXH_R,VEH_CLZTXX,VEH_CLZTXX_R,VEH_CLZZQYMC,VEH_CLZZQYMC_R" +
                        "   ,VEH_CLZZRQ,VEH_CLZZRQ_R,VEH_CPGGH,VEH_CPGGH_R,VEH_CPSCDZ,VEH_CPSCDZ_R,VEH_CSYS,VEH_CSYS_R,VEH_DPHGZBH,VEH_DPHGZBH_R,VEH_DPID,VEH_DPID_R,VEH_DPXH,VEH_DPXH_R,VEH_DYWYM,VEH_DYWYM_R," +
                        "   VEH_EDZK,VEH_EDZK_R,VEH_EDZZL,VEH_EDZZL_R,VEH_FDJH,VEH_FDJH_R,VEH_FDJXH,VEH_FDJXH_R,VEH_FZRQ,VEH_FZRQ_R,VEH_GBTHPS,VEH_GBTHPS_R,VEH_GGPC,VEH_GGSXRQ,VEH_GGSXRQ_R,VEH_GL,VEH_GL_R,VEH_HLJ,VEH_HLJ_R," +
                        "   VEH_HXNBC,VEH_HXNBC_R,VEH_HXNBG,VEH_HXNBG_R,VEH_HXNBK,VEH_HXNBK_R,VEH_HZDFS,VEH_HZDFS_R,VEH_JSS,VEH_JSS_R,VEH_JSSLX,VEH_JSSLX_R,VEH_JSSZCRS,VEH_JSSZCRS_R,VEH_LTGG,VEH_LTGG_R,VEH_LTS,VEH_LTS_R," +
                        "   VEH_NEED_CER,VEH_PFBZ,VEH_PFBZ_R,VEH_PL,VEH_PL_R,VEH_QLJ,VEH_QLJ_R,VEH_QYBZ,VEH_QYBZ_R,VEH_QYID,VEH_QYID_R,VEH_QYQTXX,VEH_QYQTXX_R,VEH_QZDCZFS,VEH_QZDCZFS_R,VEH_QZDFS,VEH_QZDFS_R," +
                        "   VEH_RLZL,VEH_RLZL_R,VEH_TYPE,VEH_TYPE_R,VEH_VIN_FOUR_BIT,VEH_VIN_FOUR_BIT_R,VEH_WKC,VEH_WKC_R,VEH_WKG,VEH_WKG_R,VEH_WKK,VEH_WKK_R,VEH_XNHGZBH,VEH_XNHGZBH_R,VEH_YH,VEH_YH_R," +
                        "   VEH_ZBZL,VEH_ZBZL_R,VEH_ZCHGZBH,VEH_ZCHGZBH_0,VEH_ZCHGZBH_0_R,VEH_ZCHGZBH_R,VEH_ZGCS,VEH_ZGCS_R,VEH_ZH,VEH_ZH_R,VEH_ZJ,VEH_ZJ_R,VEH_ZQYZZL,VEH_ZQYZZL_R,VEH_ZS,VEH_ZS_R,VEH_ZXXS,VEH_ZXXS_R," +
                        "   VEH_ZZBH,VEH_ZZBH_R,VEH_ZZL,VEH_ZZL_R,VEH_ZZLLYXS,VEH_ZZLLYXS_R,VEH_CLZZQYXX,VEH_CLZZQYXX_R,VEH_COC_STATUS,VEH_CREATE_TIME,VEH_CREATER_ID,VEH_ISUPLOAD,VEH_MANAGESTATUS,VEH_PZXLH,VEH_PZXLH_R,VEH_USERTYPE,VEH_ZDJGL,VEH_ZDJGL_R,VERCODE,VERCODE_R,WEB_IDENTIFY,WEB_IDENTIFY_R,WEB_VERIFYINFO,WEB_VERIFYINFO_R,ZCINFO_ID" +
                        "   )" +
                        "  SELECT sys_guid(),'"+loginUser.id+"','合格证日期变更',TO_CHAR(SYSDATE, 'yyyy-mm-dd'),TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'veh_Fzrq',TO_CHAR(SYSDATE, 'yyyy-mm-dd'),'"+loginUser.id+"','申请备注',NULL,NULL,UPLOAD,UPLOAD,USED,USED2,USED2,USED,VEH_BGCAZZDYXZZL," +
                        "         VEH_BGCAZZDYXZZL,VEH_BZ,VEH_BZ,VEH_CJH,VEH_CJH,VEH_CLFL,VEH_CLFL,VEH_CLMC,VEH_CLMC,VEH_CLPP,VEH_CLPP,VEH_CLSBDH,VEH_CLSBDH,VEH_CLXH,VEH_CLXH,VEH_CLZTXX,VEH_CLZTXX,VEH_CLZZQYMC," +
                        "         VEH_CLZZQYMC,VEH_CLZZRQ,'"+fz_date+"',VEH_CPGGH,VEH_CPGGH,VEH_CPSCDZ,VEH_CPSCDZ,VEH_CSYS,VEH_CSYS,VEH_DPHGZBH,VEH_DPHGZBH,VEH_DPID,VEH_DPID,VEH_DPXH,VEH_DPXH,VEH_DYWYM,VEH_DYWYM," +
                        "         VEH_EDZK,VEH_EDZK,VEH_EDZZL,VEH_EDZZL,VEH_FDJH,VEH_FDJH,VEH_FDJXH,VEH_FDJXH,VEH_FZRQ,'"+fz_date+"',VEH_GBTHPS,VEH_GBTHPS,VEH_GGPC,VEH_GGSXRQ,VEH_GGSXRQ,VEH_GL,VEH_GL,VEH_HLJ,VEH_HLJ," +
                        "         VEH_HXNBC,VEH_HXNBC,VEH_HXNBG,VEH_HXNBG,VEH_HXNBK,VEH_HXNBK,VEH_HZDFS,VEH_HZDFS,VEH_JSS,VEH_JSS,VEH_JSSLX,VEH_JSSLX,VEH_JSSZCRS,VEH_JSSZCRS,VEH_LTGG,VEH_LTGG,VEH_LTS,VEH_LTS," +
                        "         0,VEH_PFBZ,VEH_PFBZ,VEH_PL,VEH_PL,VEH_QLJ,VEH_QLJ,VEH_QYBZ,VEH_QYBZ,VEH_QYID,VEH_QYID,VEH_QYQTXX,VEH_QYQTXX,VEH_QZDCZFS,VEH_QZDCZFS,VEH_QZDFS,VEH_QZDFS,VEH_RLZL,VEH_RLZL," +
                        "         VEH_TYPE,VEH_TYPE,VEH_VIN_FOUR_BIT,VEH_VIN_FOUR_BIT,VEH_WKC,VEH_WKC,VEH_WKG,VEH_WKG,VEH_WKK,VEH_WKK,WEB_VIRTUALCODE,WEB_VIRTUALCODE,VEH_YH,VEH_YH,VEH_ZBZL,VEH_ZBZL,VEH_ZCHGZBH,VEH_ZCHGZBH_0," +
                        "         VEH_ZCHGZBH_0,VEH_ZCHGZBH,VEH_ZGCS,VEH_ZGCS,VEH_ZH,VEH_ZH,VEH_ZJ,VEH_ZJ,VEH_ZQYZZL,VEH_ZQYZZL,VEH_ZS,VEH_ZS,VEH_ZXXS,VEH_ZXXS,VEH_ZZBH,VEH_ZZBH,VEH_ZZL,VEH_ZZL,VEH_ZZLLYXS,VEH_ZZLLYXS," +
                        "         veh_clzzqyxx,veh_clzzqyxx,1,CREATE_TIME,CREATER_ID,WEB_STATUS,0,VEH_PZXLH,VEH_PZXLH,0,VEH_ZDJGL,VEH_ZDJGL,VERCODE,VERCODE,WEB_IDENTIFY,WEB_IDENTIFY,WEB_VERIFYINFO,WEB_VERIFYINFO,ID" +
                        "    FROM WZH_ZCINFO" +
                        "   WHERE ID = '"+zcinf_ID+"'"
        return  sqlService.executeUpdate(sql);
    }

    /**
     * @Description 调用后台自动打印PDF文件
     * @param params
     * @param grailsApplication
     * @return isSuccess=0表示打印失败；isSuccess=1表示打印成功  ；msg返回打印结果信息
     * veh_wzhgzbh 完整合格证编号
     *veh_Dywym 打印唯一码
     *vercode  打印二维码
     * @Update 2013-09-05 huxx
     */
    def printWCF(params,grailsApplication){
        def result=[:]
        try{
            SysInfoService client = new SysInfoServiceLocator();
            //根据 veh_Type 为1 时 调用农用车service 为0 时 调用汽车service
            Certificate  ver=new Certificate();
            ZCInfo printTmp
            if (params.pdf_veh_Zchgzbh){
                printTmp=ZCInfo.findByVeh_Zchgzbh(params.pdf_veh_Zchgzbh);
            }else{
                printTmp=new ZCInfo(params)
            }
            if (printTmp){
                ver.setVeh_Clztxx(printTmp.veh_Clztxx==null?'':printTmp.veh_Clztxx);//车辆状态信息
                ver.setVeh_Zchgzbh(printTmp.veh_Zchgzbh==null?'':printTmp.veh_Zchgzbh);
                ver.setVeh_Dphgzbh(printTmp.veh_Dphgzbh==null?'':printTmp.veh_Dphgzbh);
                ver.setVeh_Fzrq(printTmp.veh_Fzrq==null?'':printTmp.veh_Fzrq);
                ver.setVeh_Clzzqymc(printTmp.veh_Clzzqymc==null?'':printTmp.veh_Clzzqymc);
                ver.setVeh_Qyid(printTmp.veh_Qyid==null?'':printTmp.veh_Qyid);
                ver.setVeh_Clfl(printTmp.veh_Clfl==null?'':printTmp.veh_Clfl);
                ver.setVeh_Clmc(printTmp.veh_Clmc==null?'':printTmp.veh_Clmc);
                ver.setVeh_Clpp(printTmp.veh_Clpp==null?'':printTmp.veh_Clpp);
                ver.setVeh_Rlzl(printTmp.veh_Rlzl==null?'':printTmp.veh_Rlzl);
                ver.setVeh_Csys(printTmp.veh_Csys==null?'':printTmp.veh_Csys);
                ver.setVeh_Clxh(printTmp.veh_Clxh==null?'':printTmp.veh_Clxh);
                ver.setVeh_Dpxh(printTmp.veh_Dpxh==null?'':printTmp.veh_Dpxh);
                ver.setVeh_Dpid(printTmp.veh_Dpid==null?'':printTmp.veh_Dpid);
                ver.setVeh_Clsbdh(printTmp.veh_Clsbdh==null?'':printTmp.veh_Clsbdh);
                ver.setVeh_Fdjh(printTmp.veh_Fdjh==null?'':printTmp.veh_Fdjh);
                ver.setVeh_Fdjxh(printTmp.veh_Fdjxh==null?'':printTmp.veh_Fdjxh);
                ver.setVeh_Pfbz(printTmp.veh_Pfbz==null?'':printTmp.veh_Pfbz);
                ver.setVeh_Pl(printTmp.veh_Pl==null?'':printTmp.veh_Pl);
                ver.setVeh_Gl(printTmp.veh_Gl==null?'':printTmp.veh_Gl);
                ver.setVeh_Zxxs(printTmp.veh_Zxxs==null?'':printTmp.veh_Zxxs);
                ver.setVeh_Qlj(printTmp.veh_Qlj==null?'':printTmp.veh_Qlj);
                ver.setVeh_Hlj(printTmp.veh_Hlj==null?'':printTmp.veh_Hlj);
                ver.setVeh_Lts(printTmp.veh_Lts==null?'':printTmp.veh_Lts);
                ver.setVeh_Ltgg(printTmp.veh_Ltgg==null?'':printTmp.veh_Ltgg);
                ver.setVeh_Gbthps(printTmp.veh_Gbthps==null?'':printTmp.veh_Gbthps);
                ver.setVeh_Zj(printTmp.veh_Zj==null?'':printTmp.veh_Zj);
                ver.setVeh_Zh(printTmp.veh_Zh==null?'':printTmp.veh_Zh);
                ver.setVeh_Zs(printTmp.veh_Zs==null?'':printTmp.veh_Zs);
                ver.setVeh_Wkc(printTmp.veh_Wkc==null?'':printTmp.veh_Wkc);
                ver.setVeh_Wkg(printTmp.veh_Wkg==null?'':printTmp.veh_Wkg);
                ver.setVeh_Wkk(printTmp.veh_Wkk==null?'':printTmp.veh_Wkk);
                ver.setVeh_Hxnbc(printTmp.veh_Hxnbc==null?'':printTmp.veh_Hxnbc);
                ver.setVeh_Hxnbg(printTmp.veh_Hxnbg==null?'':printTmp.veh_Hxnbg);
                ver.setVeh_Hxnbk(printTmp.veh_Hxnbk==null?'':printTmp.veh_Hxnbk);
                ver.setVeh_Zzl(printTmp.veh_Zzl==null?'':printTmp.veh_Zzl);
                ver.setVeh_Qybz(printTmp.veh_Qybz==null?'':printTmp.veh_Qybz);
                ver.setVeh_Edzzl(printTmp.veh_Edzzl==null?'':printTmp.veh_Edzzl);
                ver.setVeh_Zbzl(printTmp.veh_Zbzl==null?'':printTmp.veh_Zbzl);
                ver.setVeh_Zzllyxs(printTmp.veh_Zzllyxs==null?'':printTmp.veh_Zzllyxs);
                ver.setVeh_Zqyzzl(printTmp.veh_Zqyzzl==null?'':printTmp.veh_Zqyzzl);
                ver.setVeh_Edzk(printTmp.veh_Edzk==null?'':printTmp.veh_Edzk);
                ver.setVeh_Bgcazzdyxzzl(printTmp.veh_Bgcazzdyxzzl==null?'':printTmp.veh_Bgcazzdyxzzl);
                ver.setVeh_Jsszcrs(printTmp.veh_Jsszcrs==null?'':printTmp.veh_Jsszcrs);
                ver.setVeh_Yh(printTmp.veh_Yh==null?'':printTmp.veh_Yh);
                ver.setVeh_Ggpc(printTmp.veh_Ggpc==null?'':printTmp.veh_Ggpc);
                ver.setVeh_Cpggh(printTmp.veh_Cpggh==null?'':printTmp.veh_Cpggh);
                ver.setVeh_Ggsxrq(printTmp.veh_Ggsxrq==null?'':printTmp.veh_Ggsxrq);
                ver.setVeh_Zzbh(printTmp.veh_Zzbh==null?'':printTmp.veh_Zzbh);
                ver.setVeh_Zgcs(printTmp.veh_Zgcs==null?'':printTmp.veh_Zgcs);
                ver.setVeh_Clzzrq(printTmp.veh_Clzzrq==null?'':printTmp.veh_Clzzrq);
                ver.setVeh_Bz(printTmp.veh_Bz==null?'':printTmp.veh_Bz);
                ver.setVeh_Qyqtxx(printTmp.veh_Qyqtxx==null?'':printTmp.veh_Qyqtxx);
                ver.setVeh_Clscdwmc(printTmp.veh_Clzzqymc==null?'':printTmp.veh_Clzzqymc);
                ver.setVeh_Cpscdz(printTmp.veh_Cpscdz==null?'':printTmp.veh_Cpscdz);

                if (ver.veh_Qlj &&ver.veh_Qlj.indexOf('/')>0){
                    def lst=ver.veh_Qlj.split('/')
                    ver.setVeh_Zxzs(lst.size().toString())
                }else{
                    ver.setVeh_Zxzs('1')
                }

                if (printTmp.veh_Type=='0'){  //汽车
                    String serverPath=grailsApplication.config.serverprint.carpath;
                    PrintResult printResult=client.getBasicHttpBinding_IPrintInfo(new URL(serverPath)).printVeritificate(ver);
                    result.isSuccess=printResult.isSuccess?'1':'0';
                    result.msg=printResult.msg;
                    result.veh_wzhgzbh=printResult.veh_wzhgzbh
                    result.veh_Dywym=printResult.veh_Dywym
                    result.vercode=printResult.vercode
                }else if(printTmp.veh_Type=='1'){//农用车
                    String serverPath=grailsApplication.config.serverprint.argpath;
                    PrintResult printResult=client.getBasicHttpBinding_IPrintInfo(new URL(serverPath)).printVeritificate(ver);
                    result.isSuccess=printResult.isSuccess?'1':'0';
                    result.msg=printResult.msg;
                    result.veh_wzhgzbh=printResult.veh_wzhgzbh
                    result.veh_Dywym=printResult.veh_Dywym
                    result.vercode=printResult.vercode

                }
            } else{
                result.isSuccess='0';
                result.msg="生成PDF出错，对应的合格证不存在";
            }
        } catch(Exception e){
            result.isSuccess='0';
            result.msg="调用打印方法出错";
        }

        return result
    }

    /**
     * @Description 根据条件选择合格证信息,导出合格证信息
     * @param params
     * @Create 2013-11-02 huxx
     * Update 2014-08-15 zhuwei 增加量按按创建时间查询条件导出合格证信息
     */
    def exportZcinfoByParams(params){
        def veh_workshopno=''
        if(params.veh_workshopno){
            veh_workshopno=params.veh_workshopno
        }
        def need=false
        def sql=""
        if(params.veh_Zchgzbh){
            sql+=" z.veh_Zchgzbh = '${params.veh_Zchgzbh}'"
            need=true
        }

        if(params.veh_Clsbdh){
            if (need){
                sql+=" and z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
            }else{
                sql+="  z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
                need=true
            }

        }
        if(params.veh_workshopno&&"${params.veh_workshopno}".trim().length()>0){
            if (need){
                sql+=" and z.veh_workshopno = '${veh_workshopno}'"
            }else{
                sql+=" z.veh_workshopno = '${veh_workshopno}'"
                need=true
            }
        }
        if(params.createTimeStart){
            if (need){
                sql+=" and z.create_Time >= '${params.createTimeStart} 00:00:00'"
            }else{
                sql+="  z.create_Time >= '${params.createTimeStart} 00:00:00'"
                need=true
            }
        }

        if(params.createTimeEnd){
            if (need){
                sql+=" and z.create_Time <= '${params.createTimeEnd} 23:59:59'"
            }else{
                sql+="  z.create_Time <= '${params.createTimeEnd} 23:59:59'"
                need=true
            }
        }
        if(params.veh_Clxh){
            if (need){
                sql+=" and z.veh_Clxh = '${params.veh_Clxh}'"
            }else{
                sql+="  z.veh_Clxh = '${params.veh_Clxh}'"
                need=true
            }

        }
        if(params.veh_Fdjxh){
            if (need){
                sql+=" and z.veh_Fdjxh = '${params.veh_Fdjxh}'"
            }else{
                sql+="  z.veh_Fdjxh = '${params.veh_Fdjxh}'"
                need=true
            }

        }
        if(params.veh_Fzrq){
            if (need){
                sql+=" and z.veh_Fzrq >='${params.veh_Fzrq}'"
            }else{
                sql+=" z.veh_Fzrq >='${params.veh_Fzrq}'"
                need=true
            }
        }
        if (params.veh_Fzrq1) {
            if (need){
                sql+=" and z.veh_Fzrq <='${params.veh_Fzrq1}'"
            }else{
                sql+=" z.veh_Fzrq <='${params.veh_Fzrq1}'"
                need=true
            }
        }

        if(params.uploader_Time){
            if (need){
                sql+=" and z.uploader_Time like '%${params.uploader_Time}%'"
            }else{
                sql+=" z.uploader_Time like '%${params.uploader_Time}%'"
                need=true
            }
        }

        if (params.isUpload){
            if (need){
                sql+=" and z.web_status = '${params.isUpload}'"
            }else{
                sql+=" z.web_status = '${params.isUpload}'"
                need=true
            }
        }
        if ("hand".equals(params.searchType)){
            if (need){
                sql+=" and z.create_Time <= '${params.curTime}'"
            }else{
                sql+=" z.create_Time <= '${params.curTime}'"
                need=true
            }
        }

        if (params.isPrint){
            if (need){
                sql+=" and z.web_isprint = '${params.isPrint}'"
            }else{
                sql+=" z.web_isprint = '${params.isPrint}'"
                need=true
            }

        }
        def sqls="""
              select *
              from (select row_.*, rownum rownum_
                      from (
                           select z.zcInfoId         as zcinfoId,
                                       z.create_time      as createTime,
                                       z.creater_id       as createrId,
                                       z.update_time      as updateTime,
                                       Z.UPDATER_ID        as updaterName,
                                       u.USER_NAME        as uploaderName,
                                       z.uploader_time    as uploaderTime,
                                       z.veh_bgcazzdyxzzl as veh_bgcazzdyxzzl,
                                       z.veh_bz           as veh_bz,
                                       z.veh_cjh          as veh_cjh,
                                       z.veh_clfl         as veh_clfl,
                                       z.veh_clmc         as veh_clmc,
                                       z.veh_clpp         as veh_clpp,
                                       z.veh_clsbdh       as veh_clsbdh,
                                       z.veh_clxh         as veh_clxh,
                                       z.veh_clztxx       as veh_clztxx,
                                       z.veh_clzzrq       as veh_clzzrq,
                                       z.veh_cpggh        as veh_cpggh,
                                       z.veh_cpscdz       as veh_cpscdz,
                                       z.veh_csys         as veh_csys,
                                       z.veh_dphgzbh      as veh_dphgzbh,
                                       z.veh_dpid         as veh_dpid,
                                       z.veh_dpxh         as veh_dpxh,
                                       z.veh_clzzqymc     as veh_clzzqymc,
                                       z.veh_edzk         as veh_edzk,
                                       z.veh_edzzl        as veh_edzzl,
                                       z.veh_fdjh         as veh_fdjh,
                                       z.veh_fdjxh        as veh_fdjxh,
                                       z.veh_fzrq         as veh_fzrq,
                                       z.veh_gbthps       as veh_gbthps,
                                       z.veh_ggpc         as veh_ggpc,
                                       z.veh_ggsxrq       as veh_ggsxrq,
                                       z.veh_gl           as veh_gl,
                                       z.veh_hlj          as veh_hlj,
                                       z.veh_hxnbc        as veh_hxnbc,
                                       z.veh_hxnbg        as veh_hxnbg,
                                       z.veh_hxnbk        as veh_hxnbk,
                                       z.veh_hzdczfs      as veh_hzdczfs,
                                       z.veh_hzdfs        as veh_hzdfs,
                                       z.veh_jss          as veh_jss,
                                       z.veh_jsslx        as veh_jsslx,
                                       z.veh_jsszcrs      as veh_jsszcrs,
                                       z.veh_ltgg         as veh_ltgg,
                                       z.veh_lts          as veh_lts,
                                       z.veh_pfbz         as veh_pfbz,
                                       z.veh_pl           as veh_pl,
                                       z.veh_qlj          as veh_qlj,
                                       z.veh_qybz         as veh_qybz,
                                       z.veh_qyid         as veh_qyid,
                                       z.veh_qyqtxx       as veh_qyqtxx,
                                       z.veh_qzdczfs      as veh_qzdczfs,
                                       z.veh_qzdfs        as veh_qzdfs,
                                       z.veh_rlzl         as veh_rlzl,
                                       z.veh_type         as veh_type,
                                       z.veh_vin_four_bit as veh_vin_four_bit,
                                       z.veh_wkc          as veh_wkc,
                                       z.veh_wkg          as veh_wkg,
                                       z.veh_wkk          as veh_wkk,
                                       z.veh_yh           as veh_yh,
                                       z.veh_zbzl         as veh_zbzl,
                                       z.veh_zchgzbh      as veh_zchgzbh,
                                       z.veh_zchgzbh_0    as veh_zchgzbh_0,
                                       z.veh_zgcs         as veh_zgcs,
                                       z.veh_zh           as veh_zh,
                                       z.veh_zj           as veh_zj,
                                       z.veh_zqyzzl       as veh_zqyzzl,
                                       z.veh_zs           as veh_zs,
                                       z.veh_zxxs         as veh_zxxs,
                                       z.veh_zzbh         as veh_zzbh,
                                       z.veh_zzl          as veh_zzl,
                                       z.veh_zzllyxs      as veh_zzllyxs,
                                       z.veh_clzzqyxx     as veh_clzzqyxx,
                                       z.veh_pzxlh        as veh_pzxlh,
                                       z.veh_workshopno   as veh_workshopno,
                                       z.veh_zdjgl        as veh_zdj,
                                       z.web_isprint      as web_isprint,
                                       z.web_status       as web_status,
                                       z.web_virtualcode  as web_virtualcode
                                  from WZH_ZCINFO     z left join sys_user u  on z.uploader_id = u.ID

                                  ${sql? "where"+sql:""}

                                    order by z.veh_zchgzbh   DESC

                      ) row_ )
        """
        def lst= sqlService.executeList(sqls)


        def results=[]
        lst?.each {l->
            def m=[:]
            m.id=l.zcinfoId
            m.web_virtualcode=l.web_virtualcode
            m.web_status=l.web_status
            m.veh_Clsbdh=l.veh_clsbdh
            m.veh_Zchgzbh=l.veh_zchgzbh
            m.veh_Fzrq=l.veh_fzrq
            m.veh_Clzzqymc=l.veh_clzzqymc
            m.veh_Clpp=l.veh_clpp
            m.veh_Clmc=l.veh_clmc
            m.veh_Clxh=l.veh_clxh
            m.veh_Csys=l.veh_csys
            m.veh_Cjh=l.veh_clsbdh
            m.veh_Dpxh=l.veh_dpxh

            m.veh_Dphgzbh=l.veh_dphgzbh
            m.veh_Fdjxh=l.veh_fdjxh
            m.veh_Fdjh=l.veh_fdjh
            m.veh_Rlzl=l.veh_rlzl
            m.veh_Pl=l.veh_pl
            m.veh_Gl=l.veh_gl
            m.veh_Lts=l.veh_lts
            m.veh_Pfbz=l.veh_pfbz

            m.veh_Yh=l.veh_yh
            m.veh_Wkc=l.veh_wkc
            m.veh_Wkk=l.veh_wkk
            m.veh_Wkg=l.veh_wkg
            m.veh_Hxnbc=l.veh_hxnbc
            m.veh_Hxnbk=l.veh_hxnbk
            m.veh_Hxnbg=l.veh_hxnbg
            m.veh_Ltgg=l.veh_ltgg
            m.veh_Gbthps=l.veh_gbthps

            m.veh_Qlj=l.veh_qlj
            m.veh_Hlj=l.veh_hlj
            m.veh_Zj=l.veh_zj
            m.veh_Zh=l.veh_zh
            m.veh_Zs=l.veh_zs
            m.veh_Zxxs=l.veh_zxxs
            m.veh_Zzl=l.veh_zzl
            m.veh_Zbzl=l.veh_zbzl
            m.veh_Edzzl=l.veh_edzzl

            m.veh_Zzllyxs=l.veh_zzllyxs
            m.veh_Zqyzzl=l.veh_zqyzzl
            m.veh_Bgcazzdyxzzl=l.veh_bgcazzdyxzzl
            m.veh_Jsszcrs=l.veh_jsszcrs
            m.veh_Edzk=l.veh_edzk
            m.veh_Zgcs=l.veh_zgcs
            m.veh_Clzzrq=l.veh_clzzrq
            m.createrId=l.createrId
            if(l.createTime){
                println(l.createTime)
                def time= l.createTime.substring(0,10)
                println(time)
                def parts=time.split('-')
                def returnTime=parts[0]+'年'+parts[1]+'月'+parts[2]+'日'
                println(returnTime)
                m.createTime=returnTime
            }
//            def userUp=User.get(l.uploader_Id)
            m.uploaderName=l.uploaderName
            m.uploader_Time=l.uploaderTime

            m.updaterName=l.updaterName
            m.updateTime=l.updateTime

            //导出excle中添加的字段信息
            m.veh_Type=l.veh_type
            m.veh_Qyid=l.veh_qyid
            m.veh_Clfl=l.veh_clfl
            m.veh_Dpid=l.veh_dpid
            m.veh_zdjgl=l.veh_zdj


            m.veh_Qzdfs=l.veh_qzdfs
            m.veh_Hzdfs=l.veh_hzdfs
            m.veh_Qzdczfs=l.veh_qzdczfs
            m.veh_Hzdczfs=l.veh_hzdczfs
            m.veh_Cpggh=l.veh_cpggh
            m.veh_Ggsxrq=l.veh_ggsxrq
            m.veh_Bz=l.veh_Bz
            m.veh_Qybz=l.veh_qybz
            m.veh_Cpscdz=l.veh_cpscdz
            m.veh_Qyqtxx=l.veh_qyqtxx
            m.veh_Clztxx=l.veh_clztxx
            m.veh_jss=l.veh_jss
            m.veh_jsslx=l.veh_jsslx
            m.veh_Zchgzbh_0=l.veh_zchgzbh_0
            m.veh_VinFourBit=l.veh_vin_four_bit
            m.veh_Ggpc=l.veh_ggpc
            m.veh_pzxlh=l.veh_pzxlh
            m.veh_clzzqyxx=l.veh_clzzqyxx
            m.veh_workshopno=l.veh_workshopno

            results.add(m)
        }
        def result = [rows: results]
    }

    /**
     * @Description 根据条件选择合格证信息,不是导出方法
     * @param params
     * @Create 2013-11-02 huxx
     * @Update 2014-02-13 huxx 添加按照合格证号段和创建时间时间段查询条件  、
     * Update 2014-08-15 zhuwei 添加修改按照合格证创建时间查询条件
     * Update 2018-04-18 QJ 上传显示区分农用车汽车
     */
    def selectZcinfoByParams(params){
        def veh_workshopno=''
        if(params.veh_workshopno){
            veh_workshopno=params.veh_workshopno
        }
        def need=false
        def sql=""
        if(params.isDistributor&&params.isDistributor=='1'){
            sql+=" z.veh_Zchgzbh = '${params.veh_Zchgzbh}'"
            need=true
        }else{
            if(params.veh_Zchgzbh){
                sql+=" z.veh_Zchgzbh = '${params.veh_Zchgzbh}'"
                need=true
            }
        }
        //##################合格证号段查询条件###############################
        if(params.hgzStart){
            if (need){
                sql+=" and z.veh_Zchgzbh >= '${params.hgzStart}'"
            }else{
                sql+="  z.veh_Zchgzbh >= '${params.hgzStart}'"
                need=true
            }
        }

        if(params.hgzEnd){
            if (need){
                sql+=" and z.veh_Zchgzbh <= '${params.hgzEnd}'"
            }else{
                sql+="  z.veh_Zchgzbh <= '${params.hgzEnd}'"
                need=true
            }
        }
        //##################合格证号段查询条件END###############################

        //###########创建时间时间段查询条件#################################
        if(params.createTimeStart){
            if (need){
                sql+=" and z.create_Time >= '${params.createTimeStart} 00:00:00'"
            }else{
                sql+="  z.create_Time >= '${params.createTimeStart} 00:00:00'"
                need=true
            }
        }

        if(params.createTimeEnd){
            if (need){
                sql+=" and z.create_Time <= '${params.createTimeEnd} 23:59:59'"
            }else{
                sql+="  z.create_Time <= '${params.createTimeEnd} 23:59:59'"
                need=true
            }
        }
        //###########创建时间时间段查询条件END#################################
        if(params.isDistributor&&params.isDistributor=='1'){
            if (need){
                sql+=" and z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
            }else{
                sql+="  z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
                need=true
            }
        }else{
            if(params.veh_Clsbdh){
                if (need){
                    sql+=" and z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
                }else{
                    sql+="  z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
                    need=true
                }

            }
        }

        if(params.veh_Clxh){
            if (need){
                sql+=" and z.veh_Clxh = '${params.veh_Clxh}'"
            }else{
                sql+="  z.veh_Clxh = '${params.veh_Clxh}'"
                need=true
            }

        }
        if(params.veh_Fdjxh){
            if (need){
                sql+=" and z.veh_Fdjxh = '${params.veh_Fdjxh}'"
            }else{
                sql+="  z.veh_Fdjxh = '${params.veh_Fdjxh}'"
                need=true
            }

        }
        if(params.veh_workshopno&&"${params.veh_workshopno}".trim().length()>0){
            if (need){
                sql+=" and z.veh_workshopno = '${veh_workshopno}'"
            }else{
                sql+=" z.veh_workshopno = '${veh_workshopno}'"
                need=true
            }
        }
        if(params.veh_Fzrq){
            if (need){
                sql+=" and z.veh_Fzrq >='${params.veh_Fzrq}'"
            }else{
                sql+=" z.veh_Fzrq >='${params.veh_Fzrq}'"
                need=true
            }
        }
        if (params.veh_Fzrq1) {
            if (need){
                sql+=" and z.veh_Fzrq <='${params.veh_Fzrq1}'"
            }else{
                sql+=" z.veh_Fzrq <='${params.veh_Fzrq1}'"
                need=true
            }
        }

        if(params.uploader_Time){
            if (need){
                sql+=" and z.uploader_Time like '%${params.uploader_Time}%'"
            }else{
                sql+=" z.uploader_Time like '%${params.uploader_Time}%'"
                need=true
            }
        }

        if (params.isUpload){
            if (need){
                sql+=" and z.web_status = '${params.isUpload}'"
            }else{
                sql+=" z.web_status = '${params.isUpload}'"
                need=true
            }
        }
        if ("hand".equals(params.searchType)){
            if (need){
                sql+=" and ((z.need_upload='1' and z.web_isprint=0) or z.web_isprint='1')"
            }else{
                sql+=" ((z.need_upload='1' and z.web_isprint=0) or z.web_isprint='1')"
                need=true
            }
        }

        if (params.isPrint){
            if (need){
                sql+=" and z.web_isprint = '${params.isPrint}'"
            }else{
                sql+=" z.web_isprint = '${params.isPrint}'"
                need=true
            }

        }
        List codeList =[]
        if(params.loginUser){
            def userId = params.loginUser.id
            User user  = User.findById(userId)
            def roles = user.roles
            roles.each{role->
                def roleCode = role.roleCode
                codeList.add(roleCode)
            }
        }
        def flag
        if(codeList!=null&&codeList.size()!=0){
            if(codeList.contains('carUpload')&&codeList.contains('farmVehicleUpload')){
                flag=0
            }else{
                if(codeList.contains('carUpload')){
                    flag=1
                }else if(codeList.contains('farmVehicleUpload')){
                    flag=2
                }else{
                    flag=0
                }
            }
        }else{
            flag=0
        }
        if(flag==1){
            if (need){
                sql+=" and z.veh_type = '0'"
            }else{
                sql+=" z.veh_type = '0'"
                need=true
            }
        }else if(flag==2){
            if (need){
                sql+=" and z.veh_type = '1'"
            }else{
                sql+=" z.veh_type = '1'"
                need=true
            }
        }
        def max=(params.offset?params.offset:0)+(params.max?params.max:0)
        def sqls="""
              select *
              from (select row_.*, rownum rownum_
                      from (
                           select z.zcInfoId         as zcinfoId,
                                       z.create_time      as createTime,
                                       z.creater_id       as createrId,
                                       z.update_time      as updateTime,
                                       Z.UPDATER_ID        as updaterName,
                                       u.USER_NAME        as uploaderName,
                                       z.uploader_time    as uploaderTime,
                                       z.veh_bgcazzdyxzzl as veh_bgcazzdyxzzl,
                                       z.veh_bz           as veh_bz,
                                       z.veh_cjh          as veh_cjh,
                                       z.veh_clfl         as veh_clfl,
                                       z.veh_clmc         as veh_clmc,
                                       z.veh_clpp         as veh_clpp,
                                       z.veh_clsbdh       as veh_clsbdh,
                                       z.veh_clxh         as veh_clxh,
                                       z.veh_clztxx       as veh_clztxx,
                                       z.veh_clzzrq       as veh_clzzrq,
                                       z.veh_cpggh        as veh_cpggh,
                                       z.veh_cpscdz       as veh_cpscdz,
                                       z.veh_csys         as veh_csys,
                                       z.veh_dphgzbh      as veh_dphgzbh,
                                       z.veh_dpid         as veh_dpid,
                                       z.veh_dpxh         as veh_dpxh,
                                       z.veh_clzzqymc     as veh_clzzqymc,
                                       z.veh_edzk         as veh_edzk,
                                       z.veh_edzzl        as veh_edzzl,
                                       z.veh_fdjh         as veh_fdjh,
                                       z.veh_fdjxh        as veh_fdjxh,
                                       z.veh_fzrq         as veh_fzrq,
                                       z.veh_gbthps       as veh_gbthps,
                                       z.veh_ggpc         as veh_ggpc,
                                       z.veh_ggsxrq       as veh_ggsxrq,
                                       z.veh_gl           as veh_gl,
                                       z.veh_hlj          as veh_hlj,
                                       z.veh_hxnbc        as veh_hxnbc,
                                       z.veh_hxnbg        as veh_hxnbg,
                                       z.veh_hxnbk        as veh_hxnbk,
                                       z.veh_hzdczfs      as veh_hzdczfs,
                                       z.veh_hzdfs        as veh_hzdfs,
                                       z.veh_jss          as veh_jss,
                                       z.veh_jsslx        as veh_jsslx,
                                       z.veh_jsszcrs      as veh_jsszcrs,
                                       z.veh_ltgg         as veh_ltgg,
                                       z.veh_lts          as veh_lts,
                                       z.veh_pfbz         as veh_pfbz,
                                       z.veh_pl           as veh_pl,
                                       z.veh_qlj          as veh_qlj,
                                       z.veh_qybz         as veh_qybz,
                                       z.veh_qyid         as veh_qyid,
                                       z.veh_qyqtxx       as veh_qyqtxx,
                                       z.veh_qzdczfs      as veh_qzdczfs,
                                       z.veh_qzdfs        as veh_qzdfs,
                                       z.veh_rlzl         as veh_rlzl,
                                       z.veh_type         as veh_type,
                                       z.veh_vin_four_bit as veh_vin_four_bit,
                                       z.veh_wkc          as veh_wkc,
                                       z.veh_wkg          as veh_wkg,
                                       z.veh_wkk          as veh_wkk,
                                       z.veh_yh           as veh_yh,
                                       z.veh_zbzl         as veh_zbzl,
                                       z.veh_zchgzbh      as veh_zchgzbh,
                                       z.veh_zchgzbh_0    as veh_zchgzbh_0,
                                       z.veh_zgcs         as veh_zgcs,
                                       z.veh_zh           as veh_zh,
                                       z.veh_zj           as veh_zj,
                                       z.veh_zqyzzl       as veh_zqyzzl,
                                       z.veh_zs           as veh_zs,
                                       z.veh_zxxs         as veh_zxxs,
                                       z.veh_zzbh         as veh_zzbh,
                                       z.veh_zzl          as veh_zzl,
                                       z.veh_zzllyxs      as veh_zzllyxs,
                                       z.veh_clzzqyxx     as veh_clzzqyxx,
                                       z.veh_pzxlh        as veh_pzxlh,
                                       z.veh_workshopno   as veh_workshopno,
                                       z.veh_zdjgl        as veh_zdj,
                                       z.web_isprint      as web_isprint,
                                       z.web_status       as web_status,
                                       z.web_virtualcode  as web_virtualcode
                                  from WZH_ZCINFO     z left join sys_user u  on z.uploader_id = u.ID

                                  ${sql? "where"+sql:""}

                                    order by z.veh_zchgzbh   DESC

                      ) row_
                     where rownum <= ${max})
             where rownum_ > ${params.offset}

        """
        def lst= sqlService.executeList(sqls)
//        def lst=ZCInfo.createCriteria().list (params,cel)
        def totalCount=0
        def sqlCount="select count(*) as totalCount from wzh_zcinfo z "
        if (sql){
            sqlCount+=" where ${sql}  "
        }
        sqlCount+=" order by z.veh_zchgzbh"
        def countList=sqlService.executeList(sqlCount)
        totalCount=countList.get(0).get("totalCount")

        def results=[]
        lst?.each {l->
            def m=[:]
            m.id=l.zcinfoId
            m.web_virtualcode=l.web_virtualcode
            m.web_status=l.web_status
            m.veh_Clsbdh=l.veh_clsbdh
            m.veh_Zchgzbh=l.veh_zchgzbh
            m.veh_Fzrq=l.veh_fzrq
            m.veh_Clzzqymc=l.veh_clzzqymc
            m.veh_Clpp=l.veh_clpp
            m.veh_Clmc=l.veh_clmc
            m.veh_Clxh=l.veh_clxh
            m.veh_Csys=l.veh_csys
            m.veh_Cjh=l.veh_clsbdh
            m.veh_Dpxh=l.veh_dpxh

            m.veh_Dphgzbh=l.veh_dphgzbh
            m.veh_Fdjxh=l.veh_fdjxh
            m.veh_Fdjh=l.veh_fdjh
            m.veh_Rlzl=l.veh_rlzl
            m.veh_Pl=l.veh_pl
            m.veh_Gl=l.veh_gl
            m.veh_Lts=l.veh_lts
            m.veh_Pfbz=l.veh_pfbz

            m.veh_Yh=l.veh_yh
            m.veh_Wkc=l.veh_wkc
            m.veh_Wkk=l.veh_wkk
            m.veh_Wkg=l.veh_wkg
            m.veh_Hxnbc=l.veh_hxnbc
            m.veh_Hxnbk=l.veh_hxnbk
            m.veh_Hxnbg=l.veh_hxnbg
            m.veh_Ltgg=l.veh_ltgg
            m.veh_Gbthps=l.veh_gbthps

            m.veh_Qlj=l.veh_qlj
            m.veh_Hlj=l.veh_hlj
            m.veh_Zj=l.veh_zj
            m.veh_Zh=l.veh_zh
            m.veh_Zs=l.veh_zs
            m.veh_Zxxs=l.veh_zxxs
            m.veh_Zzl=l.veh_zzl
            m.veh_Zbzl=l.veh_zbzl
            m.veh_Edzzl=l.veh_edzzl

            m.veh_Zzllyxs=l.veh_zzllyxs
            m.veh_Zqyzzl=l.veh_zqyzzl
            m.veh_Bgcazzdyxzzl=l.veh_bgcazzdyxzzl
            m.veh_Jsszcrs=l.veh_jsszcrs
            m.veh_Edzk=l.veh_edzk
            m.veh_Zgcs=l.veh_zgcs
            m.veh_Clzzrq=l.veh_clzzrq
            m.createrId=l.createrId
            m.createTime=l.createTime
//            def userUp=User.get(l.uploader_Id)
            m.uploaderName=l.uploaderName
            m.uploaderTime=l.uploaderTime

            m.updaterName=l.updaterName
            m.updateTime=l.updateTime
            //新加合格证信息显示的内容
            m.veh_Dphgzbh=l.veh_Dphgzbh
            m.veh_Qyid=l.veh_qyid
            m.veh_Clfl=l.veh_clfl
            m.veh_dpid=l.veh_dpid
            m.veh_zdjgl=l.veh_zdj
            m.veh_Qzdfs=l.veh_qzdfs
            m.veh_Hzdfs=l.veh_hzdfs
            m.veh_Qzdczfs=l.veh_qzdczfs
            m.veh_Hzdczfs=l.veh_hzdczfs
            m.veh_Cpggh=l.veh_cpggh
            m.veh_Ggsxrq=l.veh_ggsxrq
            m.veh_Qybz=l.veh_qybz
            m.veh_Cpscdz=l.veh_cpscdz
            m.veh_Qyqtxx=l.veh_qyqtxx
            m.veh_Clztxx=l.veh_clztxx
            m.veh_jss=l.veh_jss
            m.veh_jsslx=l.veh_jsslx
            m.veh_VinFourBit=l.veh_vin_four_bit
            m.veh_Bz=l.veh_Bz
            results.add(m)
        }
        def result = [rows: results,total: totalCount]
    }

    /**
     * @Description 验证公告信息与公告库中的信息是否匹配
     * @param carStorage
     * @Create 2013-11-12 huxx
     * @UpdateTime 2015-03-30 zhuwei
     * @Update 添加发动机型号、排量、功率、最大净功率的校验
     * @Update 上传单独校验，不用此方法
     */
    def String checkCarStorage(ZCInfo zcinfo){
        StringBuffer sb=new StringBuffer()
        //获取分解前的公告信息
        PreCarStorage preCarStorage=new PreCarStorage()
        if ("QX".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_Clxh(zcinfo.veh_Clxh)
        }else if ("DP".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(zcinfo.veh_Dpid,'DP')
        }else{
            sb.append("合格证车辆状态信息错误")
            return sb.toString()
        }

        //合格证编号与车辆类型的匹配验证
        if(zcinfo.veh_Clxh.startsWith("FD")){  //汽车的车辆型号以FD开头，合格证以WCB开头
            def hgzbh=zcinfo.veh_Zchgzbh
            if(!zcinfo.veh_Zchgzbh.startsWith("WCB")){
                sb.append("合格证编号与车辆类型不符")
            }
        }else{  //农用车的车辆型号
            if(!zcinfo.veh_Zchgzbh.startsWith("NB")){
                sb.append("合格证编号与车辆类型不符")
            }
        }

        //匹配条件验证
        if (!preCarStorage){
            sb.append("车辆型号与公告库不符！")
        }else{
            if(!preCarStorage.veh_Clmc?.equals(zcinfo.veh_Clmc)){
                sb.append("车辆名称与公告库不符！")
            }

            if (!preCarStorage.veh_Fdjxh){
                if (zcinfo.veh_Fdjxh){
                    sb.append("发动机型号与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Fdjxh.split(";")
                if (!f.collect().contains(zcinfo.veh_Fdjxh)){
                    sb.append("发动机型号与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Pl){
                if (zcinfo.veh_Pl){
                    sb.append("排量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Pl.split(";")
                if (!f.collect().contains(zcinfo.veh_Pl)){
                    sb.append("排量与公告库不符！")
                }
            }


            if (!preCarStorage.veh_Gl){
                if (zcinfo.veh_Gl){
                    sb.append("功率与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Gl.split(";")
                if (!f.collect().contains(zcinfo.veh_Gl)){
                    sb.append("功率与公告库不符！")
                }
            }
            //电动车校验去掉排量
            if(zcinfo.veh_Rlzl=='电'){
                //添加整车发动机型号、排量、功率、的对应关系的校验
                if("QX".equals(zcinfo.veh_Clztxx)){
                    def Fdjxh_Count=preCarStorage.veh_Fdjxh.split(";")
                    def Gl_count=preCarStorage.veh_Gl.split(";")
                    if (Fdjxh_Count.size()==Gl_count.size()){
                        def i=ArrayUtils.indexOf( Fdjxh_Count, zcinfo.veh_Fdjxh)  //查找合格证中的发动机型号在分解前发动机型号中的下标
                        println(Fdjxh_Count)
                        println(Gl_count)
                        if((Gl_count[i]).equals(zcinfo.veh_Gl)){

                        }else{
                            sb.append("分解前公告发动机型号、功率未一一对应！")
                        }

                    }else{
                        sb.append("分解前公告发动机型号、功率参数个数不一致！")
                    }
                }
            }else{
                //添加整车发动机型号、排量、功率、的对应关系的校验
                if("QX".equals(zcinfo.veh_Clztxx)){
                    def Fdjxh_Count=preCarStorage.veh_Fdjxh.split(";")
                    def Pl_Count=preCarStorage.veh_Pl.split(";")
                    def Gl_count=preCarStorage.veh_Gl.split(";")
                    if ((Fdjxh_Count.size()==Pl_Count.size())&&(Fdjxh_Count.size()==Gl_count.size())){
                        def i=ArrayUtils.indexOf( Fdjxh_Count, zcinfo.veh_Fdjxh)  //查找合格证中的发动机型号在分解前发动机型号中的下标
                        println(Fdjxh_Count)
                        println(Pl_Count)
                        println(Gl_count)
                        if((Pl_Count[i]).equals(zcinfo.veh_Pl)&&(Gl_count[i]).equals(zcinfo.veh_Gl)){

                        }else{
                            sb.append("分解前公告发动机型号、排量、功率未一一对应！")
                        }

                    }else{
                        sb.append("分解前公告发动机型号、排量、功率参数个数不一致！")
                    }
                }
            }

            if (!preCarStorage.veh_Rlzl?.equals(zcinfo.veh_Rlzl)){
                sb.append("燃料种类和公告库不符！")
            }

            if (!preCarStorage.veh_Qlj){
                if (zcinfo.veh_Qlj){
                    sb.append("前轮距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Qlj.split(",")
                if (!f.collect().contains(zcinfo.veh_Qlj)){
                    sb.append("前轮距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hlj){
                if (zcinfo.veh_Hlj){
                    sb.append("后轮距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hlj.split(",")
                if (!f.collect().contains(zcinfo.veh_Hlj)){
                    sb.append("后轮距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Lts){
                if (zcinfo.veh_Lts){
                    sb.append("轮胎数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Lts.split(",")
                if (!f.collect().contains(zcinfo.veh_Lts)){
                    sb.append("轮胎数与公告库不符！")
                }
            }
//            if (!preCarStorage.veh_Ltgg){
//                if (zcinfo.veh_Ltgg){
//                    sb.append("轮胎规格与公告库不符！")
//                }
//            }else{
//                def f=preCarStorage.veh_Ltgg.split(",")
//                if (!f.collect().contains(zcinfo.veh_Ltgg)){
//                    sb.append("轮胎规格与公告库不符！")
//                }
//            }
            if (!preCarStorage.veh_Gbthps){
                if (zcinfo.veh_Gbthps){
                    sb.append("钢板弹簧片数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Gbthps.split(",")
                if (!f.collect().contains(zcinfo.veh_Gbthps)){
                    sb.append("钢板弹簧片数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zj){
                if (zcinfo.veh_Zj){
                    sb.append("轴距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zj.split(",")
                if (!f.collect().contains(zcinfo.veh_Zj)){
                    sb.append("轴距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zs){
                if (zcinfo.veh_Zs){
                    sb.append("轴数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zs)){
                    sb.append("轴数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkc){
                if (zcinfo.veh_Wkc){
                    sb.append("外廓长与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkc.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkc)){
                    sb.append("外廓长与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkk){
                if (zcinfo.veh_Wkk){
                    sb.append("外廓宽与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkk.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkk)){
                    sb.append("外廓宽与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkg){
                if (zcinfo.veh_Wkg){
                    sb.append("外廓高与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkg.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkg)){
                    sb.append("外廓高与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbc){
                if (zcinfo.veh_Hxnbc){
                    sb.append("货箱内部长与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbc.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbc)){
                    sb.append("货箱内部长与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbk){
                if (zcinfo.veh_Hxnbk){
                    sb.append("货箱内部宽与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbk.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbk)){
                    sb.append("货箱内部宽与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbg){
                if (zcinfo.veh_Hxnbg){
                    sb.append("货箱内部高与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbg.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbg)){
                    sb.append("货箱内部高与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zzl){
                if (zcinfo.veh_Zzl){
                    sb.append("总质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Zzl)){
                    sb.append("总质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Edzzl){
                if (zcinfo.veh_Edzzl){
                    sb.append("额定载质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Edzzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Edzzl)){
                    sb.append("额定载质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zbzl){
                if (zcinfo.veh_Zbzl){
                    sb.append("整备质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zbzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Zbzl)){
                    sb.append("整备质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zzllyxs){
                if (zcinfo.veh_Zzllyxs){
                    sb.append("载质量利用系数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zzllyxs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zzllyxs)){
                    sb.append("载质量利用系数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Jsszcrs){
                if (zcinfo.veh_Jsszcrs){
                    sb.append("驾驶室准乘人数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Jsszcrs.split(",")
                if (!f.collect().contains(zcinfo.veh_Jsszcrs)){
                    sb.append("驾驶室准乘人数与公告库不符！")
                }
            }
            if (!preCarStorage.veh_Ggpc){
                if (zcinfo.veh_Ggpc){
                    sb.append("公告批次与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Ggpc.split(",")
                if (!f.collect().contains(zcinfo.veh_Ggpc)){
                    def historyGgpcList = HistoryGgpc.findAllByVeh_Clxh(zcinfo.veh_Clxh);
                    def ggpcList = []
                    historyGgpcList.each{historyGgpc->
                        ggpcList.add(historyGgpc.veh_Ggpc)
                    }
                    if(!ggpcList.contains(zcinfo.veh_Ggpc)){
                        sb.append("公告批次与公告库不符！")
                    }
                }
            }
            if (!preCarStorage.veh_Zgcs){
                if (zcinfo.veh_Zgcs){
                    sb.append("最高车速与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zgcs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zgcs)){
                    sb.append("最高车速与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Qybz){
                if (zcinfo.veh_Qybz){
                    sb.append("企业标准与公告库不符！")
                }
            }else{
                if(zcinfo.veh_Qybz){
                    if(!preCarStorage.veh_Qybz.trim()?.equals(zcinfo.veh_Qybz.trim())){
                        sb.append("企业标准与公告库不符！")
                    }
                }
            }
            if(zcinfo.veh_Rlzl=='电'){
                if(!preCarStorage.veh_Pfbz.equals(zcinfo.veh_Pfbz)){
                    sb.append("排放标准与公告库不符！")
                }

            }else{
                if(!preCarStorage.veh_Pfbz?.equals(zcinfo.veh_Pfbz)){
                    sb.append("排放标准与公告库不符！")
                }

            }

            if (zcinfo.veh_Clsbdh){
                if (!preCarStorage.veh_clsbdh){
                    if (zcinfo.veh_Clsbdh){
                        sb.append("车辆识别代号与公告库不符！")
                    }
                }else{
                    def f=preCarStorage.veh_clsbdh.split(",")
                    def clsbdh=zcinfo.veh_Clsbdh.substring(0,8)
                    def pipei=false
                    f.each{
                        def dh=it.substring(0,8)
                        if (clsbdh.equals(dh)){
                            pipei=true
                        }
                    }
                    if(!pipei){
                        sb.append("车辆识别代号与公告库不匹配！")
                    }
                }
            }else{
                sb.append("合格证的车辆识别代号不能为空！")
            }


        }

        return sb.toString()
    }
    /**
     * @Description 运输管理科打印PDF合格证验证公告信息与公告库中的信息是否匹配（不验证公告批次）
     * @param carStorage
     * @Create 2018-06-14
     */
    def String checkTransportCarStorage(ZCInfo zcinfo){
        StringBuffer sb=new StringBuffer()
        //获取分解前的公告信息
        PreCarStorage preCarStorage=new PreCarStorage()
        if ("QX".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_Clxh(zcinfo.veh_Clxh)
        }else if ("DP".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(zcinfo.veh_Dpid,'DP')
        }else{
            sb.append("合格证车辆状态信息错误")
            return sb.toString()
        }

        //合格证编号与车辆类型的匹配验证
        if(zcinfo.veh_Clxh.startsWith("FD")){  //汽车的车辆型号以FD开头，合格证以WCB开头
            def hgzbh=zcinfo.veh_Zchgzbh
            if(!zcinfo.veh_Zchgzbh.startsWith("WCB")){
                sb.append("合格证编号与车辆类型不符")
            }
        }else{  //农用车的车辆型号
            if(!zcinfo.veh_Zchgzbh.startsWith("NB")){
                sb.append("合格证编号与车辆类型不符")
            }
        }

        //匹配条件验证
        if (!preCarStorage){
            sb.append("车辆型号与公告库不符！")
        }else{
            if(!preCarStorage.veh_Clmc?.equals(zcinfo.veh_Clmc)){
                sb.append("车辆名称与公告库不符！")
            }

            if (!preCarStorage.veh_Fdjxh){
                if (zcinfo.veh_Fdjxh){
                    sb.append("发动机型号与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Fdjxh.split(";")
                if (!f.collect().contains(zcinfo.veh_Fdjxh)){
                    sb.append("发动机型号与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Pl){
                if (zcinfo.veh_Pl){
                    sb.append("排量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Pl.split(";")
                if (!f.collect().contains(zcinfo.veh_Pl)){
                    sb.append("排量与公告库不符！")
                }
            }


            if (!preCarStorage.veh_Gl){
                if (zcinfo.veh_Gl){
                    sb.append("功率与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Gl.split(";")
                if (!f.collect().contains(zcinfo.veh_Gl)){
                    sb.append("功率与公告库不符！")
                }
            }
            //电动车校验去掉排量
            if(zcinfo.veh_Rlzl=='电'){
                //添加整车发动机型号、排量、功率、的对应关系的校验
                if("QX".equals(zcinfo.veh_Clztxx)){
                    def Fdjxh_Count=preCarStorage.veh_Fdjxh.split(";")
                    def Gl_count=preCarStorage.veh_Gl.split(";")
                    if (Fdjxh_Count.size()==Gl_count.size()){
                        def i=ArrayUtils.indexOf( Fdjxh_Count, zcinfo.veh_Fdjxh)  //查找合格证中的发动机型号在分解前发动机型号中的下标
                        println(Fdjxh_Count)
                        println(Gl_count)
                        if((Gl_count[i]).equals(zcinfo.veh_Gl)){

                        }else{
                            sb.append("分解前公告发动机型号、功率未一一对应！")
                        }

                    }else{
                        sb.append("分解前公告发动机型号、功率参数个数不一致！")
                    }
                }
            }else{
                //添加整车发动机型号、排量、功率、的对应关系的校验
                if("QX".equals(zcinfo.veh_Clztxx)){
                    def Fdjxh_Count=preCarStorage.veh_Fdjxh.split(";")
                    def Pl_Count=preCarStorage.veh_Pl.split(";")
                    def Gl_count=preCarStorage.veh_Gl.split(";")
                    if ((Fdjxh_Count.size()==Pl_Count.size())&&(Fdjxh_Count.size()==Gl_count.size())){
                        def i=ArrayUtils.indexOf( Fdjxh_Count, zcinfo.veh_Fdjxh)  //查找合格证中的发动机型号在分解前发动机型号中的下标
                        println(Fdjxh_Count)
                        println(Pl_Count)
                        println(Gl_count)
                        if((Pl_Count[i]).equals(zcinfo.veh_Pl)&&(Gl_count[i]).equals(zcinfo.veh_Gl)){

                        }else{
                            sb.append("分解前公告发动机型号、排量、功率未一一对应！")
                        }

                    }else{
                        sb.append("分解前公告发动机型号、排量、功率参数个数不一致！")
                    }
                }
            }

            if (!preCarStorage.veh_Rlzl?.equals(zcinfo.veh_Rlzl)){
                sb.append("燃料种类和公告库不符！")
            }

            if (!preCarStorage.veh_Qlj){
                if (zcinfo.veh_Qlj){
                    sb.append("前轮距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Qlj.split(",")
                if (!f.collect().contains(zcinfo.veh_Qlj)){
                    sb.append("前轮距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hlj){
                if (zcinfo.veh_Hlj){
                    sb.append("后轮距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hlj.split(",")
                if (!f.collect().contains(zcinfo.veh_Hlj)){
                    sb.append("后轮距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Lts){
                if (zcinfo.veh_Lts){
                    sb.append("轮胎数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Lts.split(",")
                if (!f.collect().contains(zcinfo.veh_Lts)){
                    sb.append("轮胎数与公告库不符！")
                }
            }

//            if (!preCarStorage.veh_Ltgg){
//                if (zcinfo.veh_Ltgg){
//                    sb.append("轮胎规格与公告库不符！")
//                }
//            }else{
//                def f=preCarStorage.veh_Ltgg.split(",")
//                if (!f.collect().contains(zcinfo.veh_Ltgg)){
//                    sb.append("轮胎规格与公告库不符！")
//                }
//            }

            if (!preCarStorage.veh_Gbthps){
                if (zcinfo.veh_Gbthps){
                    sb.append("钢板弹簧片数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Gbthps.split(",")
                if (!f.collect().contains(zcinfo.veh_Gbthps)){
                    sb.append("钢板弹簧片数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zj){
                if (zcinfo.veh_Zj){
                    sb.append("轴距与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zj.split(",")
                if (!f.collect().contains(zcinfo.veh_Zj)){
                    sb.append("轴距与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zs){
                if (zcinfo.veh_Zs){
                    sb.append("轴数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zs)){
                    sb.append("轴数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkc){
                if (zcinfo.veh_Wkc){
                    sb.append("外廓长与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkc.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkc)){
                    sb.append("外廓长与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkk){
                if (zcinfo.veh_Wkk){
                    sb.append("外廓宽与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkk.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkk)){
                    sb.append("外廓宽与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Wkg){
                if (zcinfo.veh_Wkg){
                    sb.append("外廓高与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Wkg.split(",")
                if (!f.collect().contains(zcinfo.veh_Wkg)){
                    sb.append("外廓高与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbc){
                if (zcinfo.veh_Hxnbc){
                    sb.append("货箱内部长与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbc.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbc)){
                    sb.append("货箱内部长与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbk){
                if (zcinfo.veh_Hxnbk){
                    sb.append("货箱内部宽与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbk.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbk)){
                    sb.append("货箱内部宽与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Hxnbg){
                if (zcinfo.veh_Hxnbg){
                    sb.append("货箱内部高与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Hxnbg.split(",")
                if (!f.collect().contains(zcinfo.veh_Hxnbg)){
                    sb.append("货箱内部高与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zzl){
                if (zcinfo.veh_Zzl){
                    sb.append("总质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Zzl)){
                    sb.append("总质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Edzzl){
                if (zcinfo.veh_Edzzl){
                    sb.append("额定载质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Edzzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Edzzl)){
                    sb.append("额定载质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zbzl){
                if (zcinfo.veh_Zbzl){
                    sb.append("整备质量与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zbzl.split(",")
                if (!f.collect().contains(zcinfo.veh_Zbzl)){
                    sb.append("整备质量与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Zzllyxs){
                if (zcinfo.veh_Zzllyxs){
                    sb.append("载质量利用系数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zzllyxs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zzllyxs)){
                    sb.append("载质量利用系数与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Jsszcrs){
                if (zcinfo.veh_Jsszcrs){
                    sb.append("驾驶室准乘人数与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Jsszcrs.split(",")
                if (!f.collect().contains(zcinfo.veh_Jsszcrs)){
                    sb.append("驾驶室准乘人数与公告库不符！")
                }
            }
//            if (!preCarStorage.veh_Ggpc){
//                if (zcinfo.veh_Ggpc){
//                    sb.append("公告批次与公告库不符！")
//                }
//            }else{
//                def f=preCarStorage.veh_Ggpc.split(",")
//                if (!f.collect().contains(zcinfo.veh_Ggpc)){
//                    sb.append("公告批次与公告库不符！")
//                }
//            }
            if (!preCarStorage.veh_Zgcs){
                if (zcinfo.veh_Zgcs){
                    sb.append("最高车速与公告库不符！")
                }
            }else{
                def f=preCarStorage.veh_Zgcs.split(",")
                if (!f.collect().contains(zcinfo.veh_Zgcs)){
                    sb.append("最高车速与公告库不符！")
                }
            }

            if (!preCarStorage.veh_Qybz){
                if (zcinfo.veh_Qybz){
                    sb.append("企业标准与公告库不符！")
                }
            }else{
                if(zcinfo.veh_Qybz){
                    if(!preCarStorage.veh_Qybz.trim()?.equals(zcinfo.veh_Qybz.trim())){
                        sb.append("企业标准与公告库不符！")
                    }
                }
            }
            if(zcinfo.veh_Rlzl=='电'){
                if(!preCarStorage.veh_Pfbz.equals(zcinfo.veh_Pfbz)){
                    sb.append("排放标准与公告库不符！")
                }

            }else{
                if(!preCarStorage.veh_Pfbz?.equals(zcinfo.veh_Pfbz)){
                    sb.append("排放标准与公告库不符！")
                }

            }

            if (zcinfo.veh_Clsbdh){
                if (!preCarStorage.veh_clsbdh){
                    if (zcinfo.veh_Clsbdh){
                        sb.append("车辆识别代号与公告库不符！")
                    }
                }else{
                    def f=preCarStorage.veh_clsbdh.split(",")
                    def clsbdh=zcinfo.veh_Clsbdh.substring(0,8)
                    def pipei=false
                    f.each{
                        def dh=it.substring(0,8)
                        if (clsbdh.equals(dh)){
                            pipei=true
                        }
                    }
                    if(!pipei){
                        sb.append("车辆识别代号与公告库不匹配！")
                    }
                }
            }else{
                sb.append("合格证的车辆识别代号不能为空！")
            }


        }

        return sb.toString()
    }
    /**
     * @Description 审核验证轮胎规格公告信息与公告库中的信息是否匹配
     * @param carStorage
     * @Create 2017-8-24huxx
     */
    def  checkLtgg(ZCInfo zcinfo){
        StringBuffer sb=new StringBuffer()
        PreCarStorage preCarStorage=new PreCarStorage()

        //获取分解前的公告信息
        if ("QX".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_Clxh(zcinfo.veh_Clxh)
        }else if ("DP".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(zcinfo.veh_Dpid,'DP')
        }else{
            sb.append("合格证车辆状态信息错误")
            return sb.toString()
        }
        if (!preCarStorage){
            sb.append("车辆型号与公告库不符！")
        }else{
            if (!preCarStorage.veh_Ltgg){
                if (zcinfo.veh_Ltgg){
                    sb.append("轮胎规格与公告库不符!是否继续生成合格证？")
                }
            }else{
                def f=preCarStorage.veh_Ltgg.split(",")
                if (!f.collect().contains(zcinfo.veh_Ltgg)){
                    sb.append("轮胎规格与公告库不符!是否继续生成合格证？")
                }
            }
        }
        return sb.toString()
    }
    /**
     * @Description 上传验证轮胎规格公告信息与公告库中的信息是否匹配
     * @param carStorage
     * @Create 2017-08-24 huxx
     */
    def  checkLtgg2(ZCInfo zcinfo){
        def fileId=""

        PreCarStorage preCarStorage=new PreCarStorage()

        //获取分解前的公告信息
        if ("QX".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_Clxh(zcinfo.veh_Clxh)
        }else if ("DP".equals(zcinfo.veh_Clztxx)){
            preCarStorage=PreCarStorage.findByVeh_DpidAndVeh_Clztxx(zcinfo.veh_Dpid,'DP')
        }
        if (!preCarStorage.veh_Ltgg){
            if (zcinfo.veh_Ltgg){
                fileId= zcinfo.id
            }
        }else{
            def f=preCarStorage.veh_Ltgg.split(",")
            if (!f.collect().contains(zcinfo.veh_Ltgg)){
                fileId= zcinfo.id
            }
        }
        return fileId
    }
    /**
     * @Description 根据条件选择合格证信息
     * @param params
     * @Create 2013-11-27 liuly
     */
    def selectZcinfoByShop(params){
        def need=false
        def sql=""
        if(params.veh_Zchgzbh){
            sql+=" veh_Zchgzbh = '${params.veh_Zchgzbh}'"
            need=true
        }

        if(params.veh_Clsbdh){
            if (need){
                sql+=" and z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
            }else{
                sql+="  z.veh_Clsbdh like '%${params.veh_Clsbdh}%'"
                need=true
            }

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
//                sql+=" and z.veh_workshopno = '${params.organCode}'"
            }else{
                sql+=" z.veh_workshopno in ${params.organsCodeStr.toString()} "
//                sql+=" z.veh_workshopno = '${params.organCode}'"
                need=true
            }

        }
        def sqls="select id,zcinfoid from WZH_ZCINFO z"
        if (sql){
            sqls+=" where  ${sql}  "
        }
        def lstId= sqlService.executeList(sqls)
        def cel={
            if(params.startTime){
                ge('createTime',startTime)
            }
            if(params.endTime){
                le('createTime',endTime)
            }
            if (params.veh_Zchgzbh) {
                like("veh_Zchgzbh","${params.veh_Zchgzbh}")
            }
            if (params.veh_Clsbdh) {
                like("veh_Clsbdh","${params.veh_Clsbdh}")
            }
            if(params.web_status){
                eq("web_status","${params.web_status}")
            }
            if (params.organCodeList?.size()>0) {
//                eq('veh_workshopno',params.organCode)
                inList("veh_workshopno", params.organCodeList)
            }
            order ("veh_Zchgzbh","asc")
            order ("web_status", "asc")
        }
        def lst=ZCInfo.createCriteria().list(params,cel)
        def results=[]
        lst?.each {l->
            def m=[:]
            l.properties.each {
                m."${it.key}"=it.value
            }
            m.id=l.id
            def id=''
            lstId.each { i->
                if (i.zcinfoid==l.id){
                    id = i.id
                }
            }
            m.idReal=id
            results.add(m)
        }
        def result = [rows: results,total: lst.totalCount]
    }
}
