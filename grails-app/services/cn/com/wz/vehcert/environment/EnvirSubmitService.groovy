package cn.com.wz.vehcert.environment

import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.StringUtil
import cn.com.wz.parent.system.user.User
import cn.com.wz.vehcert.coc.CocCarStoragePrint
import cn.com.wz.vehcert.zcinfo.PrintSet
import cn.com.wz.vehcert.zcinfo.ZCInfo
import groovy.sql.Sql

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

/**
 *
 * @param
 * @return
 */
class EnvirSubmitService {
    /**
     * @Description 根据参数获取环保信息数据
     * @CreateTime 2017-2-8 by QJ
     * @param params
     * @return
     */
    def selectByParams(params){
        def cel={
            if(params.time1){
                def time1 = params.time1 +' 00:00:00'
                ge("create_time","${time1.toString()}")
            }
            if (params.time2) {
                def time2 = params.time2 +' 23:59:59'
                le("create_time","${time2.toString()}")
            }
            if (params.status) {
                eq("status","0")
            }
            if (params.type){
                eq("type","${params.type}")
            }

        }
        def lst=EnvirUpload.createCriteria().list([max: params.max,offset: params.offset],cel)
        def list=[]
        lst?.each {
            def m=[:]

            it.properties.each {
                if(it.value==null){
                    it.value=''
                }
                m."${it.key}"=it.value
            }
            m.id="${it.id}"
            list.add(m)
        }
        def result=[total:lst.totalCount,rows:list]
        return result
    }
    /**
     * @Description 根据条件将已打印的环保信息同步到服务器上
     * @param params
     * @return 返回同步失败的记录id集合
     * @Create 2017-02-09 Qj
     */
    def  uploadEnvironment(params){
        def result=[]
        def remoteDB=params.remoteDB
        def cel={
            eq('status',0)
            if(params.time1){
                def time1 = params.time1 +' 00:00:00'
                ge("create_time","${time1.toString()}")
            }
            if (params.time2) {
                def time2 = params.time2 +' 23:59:59'
                le("create_time","${time2.toString()}")
            }
        }
        def lst=EnvirUpload.createCriteria().list(cel)

        def sqlList=[]
        lst?.each{coc->
            //可以在这里先读取远程数据库判断记录是否已经存在 ,如果存在就将记录的id保存到result （暂时未做）
            StringBuffer sbf = new StringBuffer("insert into ");
            sbf.append("Wzh_En_Upload");  /**表名**/
            String col="(id,"
            String value="values('${coc.id}',"
            coc.properties?.each{
                switch(it.key){
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

        def uploadResult=  SynchronizationData(remoteDB,sqlList)
        //同步成功后，更新已提交的纪录
        if (uploadResult){
            if (lst?.size()>0){
                StringBuffer sb=new StringBuffer("update EnvirUpload set status=1 where id in (")
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
                        EnvirUpload.executeUpdate(sb.toString())
                        sb=new StringBuffer("update EnvirUpload set status=1 where id in ('${it.id}'")
                        count=count-999
                    }
                    count+=1
                }
                sb.append(")")
                EnvirUpload.executeUpdate(sb.toString())
            }
        } else{
            result.addAll(lst?.id?.collect())
        }
        return result
    }
    /**  同步数据
     * @update Qj 2017-02-9 添加了出现异常时数据回滚
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
}
