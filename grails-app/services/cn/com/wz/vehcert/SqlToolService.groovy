package cn.com.wz.vehcert

import cn.com.wz.vehcert.carstorage.PreCarStorage
import cn.com.wz.vehcert.coc.CocCarStorage
import groovy.sql.Sql

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class SqlToolService{
    def dataSource
    def time1 = 0.0;
    def time2 = 0.0;
	/** JDBC sql2000 */
	def Sql SQLBYCON (){
		def db = new groovy.sql.Sql(dataSource)

		return db;
	}

    /** JDBC sql2000 */
    def Sql SQLBYCON (def _URL_IP,def _PORT,def _DATABASE,def _USERNAME,def _PASSWORD){
        def db = groovy.sql.Sql.newInstance(_URL_SQL+_URL_IP+":"+_PORT+";DatabaseName="+_DATABASE,_USERNAME,_PASSWORD,
                "com.microsoft.sqlserver.jdbc.SQLServerDriver")
        return db;
    }

	
	/** JDBC oracle */
	def Sql ORACLEBYCON (def _URL_IP,def PORT,def _USERNAME,def _PASSWORD){
		def db = groovy.sql.Sql.newInstance(_URL_ORACLE+_URL_IP+":"+PORT+":orcl",_USERNAME,_PASSWORD,
			"oracle.jdbc.driver.OracleDriver")
		
		return db;
	}
	
	/** JDBC oracle */
	def Sql ORACLEBYCON (def _URL,def _USERNAME,def _PASSWORD,def _ClassName,def object){
        def db = groovy.sql.Sql.newInstance(_URL,_USERNAME,_PASSWORD,_ClassName);
		
		return db;
	}

    /** JDBC */
    def Sql ORACLEBYCON (def datasource){
        def db =  new groovy.sql.Sql(datasource)

        return db;
    }
	
	/** JDBC oracle */
	def Sql ORACLEBYCON (def _URL,def _USERNAME,def _PASSWORD){
		def db = groovy.sql.Sql.newInstance(_URL,_USERNAME,_PASSWORD,
				"oracle.jdbc.driver.OracleDriver")

		return db;
	}
	
	def static final _URL_SQL = "jdbc:sqlserver://"
	
	def static final _URL_ORACLE = "jdbc:oracle:thin:@"
	

	/*** DELETE */
	def DeleteByTable(Sql sql_con) throws java.sql.SQLException{
        sql_con.execute '''
			delete from wzh_carStorage
		'''
        sql_con.execute '''
			delete from COC_CARSTORAGE
		'''
	}
    /**
     * @Description 同步重型柴油环保信息库
     * @param oracle_db
     * @param sql_db
     * @param
     * @Create 2017-1-13 Qj
     * @Update
     */
    def synHeavyDiesel(Sql oracle_db,Sql sql_db){
        def totalTime=0

        //获取服务器中的分解前的公告数据库，这是1.25端的数据
        def selectSql="select * from wzh_heavy_diesel"
        def list=oracle_db.rows(selectSql)

        //删除本地数据库
        def deletesql="delete from wzh_heavy_diesel"
        def result=sql_db.execute(deletesql)

        List<String> sql_options = new ArrayList<String>();
        def count=0
         def totalCount=list?.size()
        def allCount=0
        list?.each{ p->
            allCount+=1
            count+=1
            StringBuffer sb=new StringBuffer("insert into wzh_heavy_diesel ")
            StringBuffer title=new StringBuffer(" (id")
            StringBuffer values=new StringBuffer(" values('${p.ID}'")
            p.entrySet().each{v->
                if (!"ID".equals(v.key)){
                    title.append(",${v.key}")
                    values.append(",'${v.value?v.value:''}'")
                }
            }
            title.append(")")
            values.append(")")
            sb.append(title)
            sb.append(values)
            sql_options.add(sb)

            if (allCount==totalCount){
                def time =  this.SynchronizationData (sql_db,sql_options,500);
                count = 0
                sql_options.clear()
                totalTime = totalTime+time;
            }
        }
        return [totalCount:totalCount,totalTime:totalTime]
    }
    /**
     * @Description 同步双燃料环保信息库
     * @param oracle_db
     * @param sql_db
     * @param
     * @Create 2017-1-13 Qj
     * @Update
     */
    def synLightDual(Sql oracle_db,Sql sql_db){
        def totalTime=0

        //获取服务器中的分解前的公告数据库，这是1.25端的数据
        def selectSql="select * from wzh_light_dual"
        def list=oracle_db.rows(selectSql)

        //删除本地数据库
        def deletesql="delete from wzh_light_dual"
        def result=sql_db.execute(deletesql)

        List<String> sql_options = new ArrayList<String>();
        def count=0
        def totalCount=list?.size()
        def allCount=0
        list?.each{ p->
            allCount+=1
            count+=1
            StringBuffer sb=new StringBuffer("insert into wzh_light_dual")
            StringBuffer title=new StringBuffer(" (id")
            StringBuffer values=new StringBuffer(" values('${p.ID}'")
            p.entrySet().each{v->
                if (!"ID".equals(v.key)){
                    title.append(",${v.key}")
                    values.append(",'${v.value?v.value:''}'")
                }
            }
            title.append(")")
            values.append(")")
            sb.append(title)
            sb.append(values)
            sql_options.add(sb)

            if (allCount==totalCount){
                ///实现环保信息同步
                def time =  this.SynchronizationData (sql_db,sql_options,500);
                count = 0
                sql_options.clear()
                totalTime = totalTime+time;
            }
        }
        return [totalCount:totalCount,totalTime:totalTime]
    }
    /**
     * @Description 同步轻型柴油环保信息库
     * @param oracle_db
     * @param sql_db
     * @param
     * @Create 2017-1-13 Qj
     * @Update
     */
    def synLightDiesel(Sql oracle_db,Sql sql_db){
        def totalTime=0

        //获取服务器中的分解前的公告数据库，这是1.25端的数据
        def selectSql="select * from wzh_light_Diesel"
        def list=oracle_db.rows(selectSql)

        //删除本地数据库
        def deletesql="delete from wzh_light_Diesel"
        def result=sql_db.execute(deletesql)

        List<String> sql_options = new ArrayList<String>();
        def count=0
        def totalCount=list?.size()
        def allCount=0
        list?.each{ p->
            allCount+=1
            count+=1
            StringBuffer sb=new StringBuffer("insert into wzh_light_Diesel")
            StringBuffer title=new StringBuffer(" (id")
            StringBuffer values=new StringBuffer(" values('${p.ID}'")
            p.entrySet().each{v->
                if (!"ID".equals(v.key)){
                    title.append(",${v.key}")
                    values.append(",'${v.value?v.value:''}'")
                }
            }
            title.append(")")
            values.append(")")
            sb.append(title)
            sb.append(values)
            sql_options.add(sb)

            if (allCount==totalCount){
                ///实现环保信息同步
                def time =  this.SynchronizationData (sql_db,sql_options,500);
                count = 0
                sql_options.clear()
                totalTime = totalTime+time;
            }
        }
        return [totalCount:totalCount,totalTime:totalTime]
    }
    /**
     * @Description 同步重型燃气环保信息库
     * @param oracle_db
     * @param sql_db
     * @param
     * @Create 2017-2-24 Qj
     * @Update
     */
    def synHeavyGas(Sql oracle_db,Sql sql_db){
        def totalTime=0

        //获取服务器中的分解前的公告数据库，这是1.25端的数据
        def selectSql="select * from wzh_heavy_gas"
        def list=oracle_db.rows(selectSql)

        //删除本地数据库
        def deletesql="delete from wzh_heavy_gas"
        def result=sql_db.execute(deletesql)

        List<String> sql_options = new ArrayList<String>();
        def count=0
        def totalCount=list?.size()
        def allCount=0
        list?.each{ p->
            allCount+=1
            count+=1
            StringBuffer sb=new StringBuffer("insert into wzh_heavy_gas")
            StringBuffer title=new StringBuffer(" (id")
            StringBuffer values=new StringBuffer(" values('${p.ID}'")
            p.entrySet().each{v->
                if (!"ID".equals(v.key)){
                    title.append(",${v.key}")
                    values.append(",'${v.value?v.value:''}'")
                }
            }
            title.append(")")
            values.append(")")
            sb.append(title)
            sb.append(values)
            sql_options.add(sb)

            if (allCount==totalCount){
                def time =  this.SynchronizationData (sql_db,sql_options,500);
                count = 0
                sql_options.clear()
                totalTime = totalTime+time;
            }
        }
        return [totalCount:totalCount,totalTime:totalTime]
    }
    /**
     * @Description 同步生成前的公告库
     * @param oracle_db
     * @param sql_db
     * @param maxCount
     * @Create 2013-11-13 huxx
     * @Update 2013-11-22 huxx 先读取远程数据，然后再删除本地数据，防止远程服务不通，但本地数据删除的问题
     */
    def synPreCarStorage(Sql oracle_db,Sql sql_db,int maxCount){
        def totalTime=0

        //获取服务器中的分解前的公告数据库，这是1.25端的数据
        def selectSql="select * from tbl_preCarstorage"
        def list=oracle_db.rows(selectSql)

        //删除本地数据库
        def deletesql="delete from tbl_precarstorage"
        def result=sql_db.execute(deletesql)

        List<String> sql_options = new ArrayList<String>();
        def count=0
        def totalCount=list?.size()
        def allCount=0
        list?.each{ p->
            allCount+=1
            count+=1
            StringBuffer sb=new StringBuffer("insert into tbl_precarstorage ")
            StringBuffer title=new StringBuffer(" (id")
            StringBuffer values=new StringBuffer(" values('${p.ID}'")
            p.entrySet().each{v->
                if (!"ID".equals(v.key)){
                    title.append(",${v.key}")
                    values.append(",'${v.value?v.value:''}'")
                }

            }
            title.append(")")
            values.append(")")
            sb.append(title)
            sb.append(values)
            sql_options.add(sb)

            if (count ==maxCount || allCount==totalCount){
                ///实现公告信息同步
                def time =  this.SynchronizationData (sql_db,sql_options,maxCount);
                count = 0
                sql_options.clear()
                totalTime = totalTime+time;
            }
        }
        return [totalCount:totalCount,totalTime:totalTime]
    }
    /**
     * @Description     从服务器同步合格证信息岛本地
     * @param oracle_db
     * @param sql_db
     * @param maxCount
     * @Create 2013-12-18 liuly
     * @Update 2014-03-19 huxx       修改注释
     */
    def synZcinfo(Sql oracle_db,Sql sql_db,params,int maxCount){
        def totalTime=0
        //获取服务器中的合格证信息
        def selectSql="select * from WZH_ZCINFO where Veh_Zchgzbh= '${params.veh_Zchgzbh}'"
        def list=oracle_db.rows(selectSql)
        //删除本地合格证信息
        if(list.size()>0){
            def deletesql="delete  from WZH_ZCINFO where veh_Zchgzbh= '${params.veh_Zchgzbh}'"
            def result=sql_db.execute(deletesql)
        }
        List<String> sql_options = new ArrayList<String>();
        def count=0
        def totalCount=list?.size()
        def allCount=0
        list?.each{ p->
            allCount+=1
            count+=1
            StringBuffer sb=new StringBuffer("insert into WZH_ZCINFO ")
            StringBuffer title=new StringBuffer(" (VEH_ZCHGZBH")
            StringBuffer values=new StringBuffer(" values('${params.veh_Zchgzbh}'")
            p.entrySet().each{v->
                if (!"VEH_ZCHGZBH".equals(v.key)&&!"ID".equals(v.key)){
                    title.append(",${v.key}")
                    values.append(",'${v.value?v.value:''}'")
                }

            }
            title.append(")")
            values.append(")")
            sb.append(title)
            sb.append(values)
            sql_options.add(sb)
            if (count ==maxCount || allCount==totalCount){
                ///实现公告信息同步
                def time =  this.SynchronizationData (sql_db,sql_options,maxCount);
                count = 0
                sql_options.clear()
                totalTime = totalTime+time;
            }
        }
        return [totalCount:totalCount,totalTime:totalTime]
    }

    /**
     * @Description     从服务器同步合格证信息岛本地[适用于农装]
     * @param oracle_db
     * @param sql_db
     * @param maxCount
     * @Create 2014-07-16
     * @Author zhuwei
     */
    def synZcinfo_NZ(Sql oracle_db,Sql sql_db,params,int maxCount){
        def  result
        def totalTime=0
        //获取服务器中的合格证信息
        def selectSql="select * from WZH_NZ_ZCINFO where veh_Clbh= '${params.veh_Clbh}'"
        def list=oracle_db.rows(selectSql)
        //删除本地合格证信息
        if(list.size()>0){
            def deletesql="delete from WZH_NZ_ZCINFO where veh_Clbh='${params.veh_Clbh}'"
            result=sql_db.execute(deletesql)
        }
        List<String> sql_options = new ArrayList<String>();
        def count=0
        def totalCount=list?.size()
        def allCount=0
        list?.each{ p->
            allCount+=1
            count+=1
            StringBuffer sb=new StringBuffer("insert into WZH_NZ_ZCINFO ")
            StringBuffer title=new StringBuffer(" (VEH_CLBH")
            StringBuffer values=new StringBuffer(" values('${params.veh_Clbh}'")
            p.entrySet().each{v->
                if (!"VEH_CLBH".equals(v.key)&&!"ID".equals(v.key)){
                    title.append(",${v.key}")
                    values.append(",'${v.value?v.value:''}'")
                }

            }
            title.append(")")
            values.append(")")
            sb.append(title)
            sb.append(values)
            sql_options.add(sb)
            if (count ==maxCount || allCount==totalCount){
                ///实现公告信息同步
                def time =  this.SynchronizationData (sql_db,sql_options,maxCount);
                count = 0
                sql_options.clear()
                totalTime = totalTime+time;
            }
        }
        return [totalCount:totalCount,totalTime:totalTime]
    }
	/**  获得服务器数据进行封装操作  (公告信息)*/
    ///// zouQ desc:  2013年8月24日 修改了服务器（公告、一致性信息）数据再修改时,不能及时更新到车间数据库
    /**
     * @Description同步公告信息
     * @param oracle_db
     * @param sql_db
     * @param lastSynTime
     * @return
     * @Update 2013-09-04 huxx 1、将最后删除时间与最后下载时间分开保存 。2、同步的总数=下载数量+删除记录数-下载记录数和删除记录数共有的记录
     */
	def  GetServeData(Sql oracle_db,Sql sql_db,lastSynTime,lastDeleteTime,int maxCount){
        //删除本地服务器已删除的公告信息记录   同时用于计算总的同步记录数，
       def curDeleteTime= getNowTimeBySeriver(oracle_db) //获取服务器当前时间
       def deleteArr= interactive_Oracle_Sql(oracle_db,sql_db,1,lastDeleteTime); //删除本地数据库中服务端已删除的分解前公告记录

        def returnResult=""
        def totalTime = 0.0 ////总耗时
        def totalCount = 0 ////更新总条数

        int start=0
        int offset=0
        List<String> sql_options = new ArrayList<String>();
        int count=0
        //获取当前时间
        def serverTime=getNowTimeBySeriver(oracle_db)
        while(true){
            start=count*maxCount
            offset=start+maxCount
            count+=1
            StringBuffer sbf_e = new StringBuffer("select o.*  from(select t.* ,rownum as num from (SELECT * FROM WZH_CARSTORAGE ");
            if (lastSynTime){
                sbf_e.append(" where  (to_date(CREATE_TIME,'yyyy-mm-dd hh24:mi:ss') >=to_date('"+lastSynTime+"','yyyy-mm-dd hh24:mi:ss')")
                sbf_e.append(" or to_date(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') >=to_date('"+lastSynTime+"','yyyy-mm-dd hh24:mi:ss'))")
            }
            sbf_e.append(") t where rownum <=${offset}) o where o.num >${start}")

            def rows_e = oracle_db.rows(sbf_e.toString())

            if (!(rows_e && rows_e.size()>0)){
                break;
            }

            totalCount += rows_e.size();

            rows_e?.each{r->
                //从deleteArr中剔除已计数的记录
                deleteArr.remove(r.id)

                StringBuffer sbf = new StringBuffer("insert into wzh_carStorage ");
                StringBuffer title=new StringBuffer(" (id")
                StringBuffer values=new StringBuffer(" values('${r.ID}'")
                r.entrySet().each{v->
                    def noNeed=["ID","NUM"]
                    if (!noNeed.contains(v.key) ){
                        title.append(",${v.key}")
                        values.append(",'${v.value?v.value:''}'")
                    }

                }
                title.append(")")
                values.append(")")
                sbf.append(title)
                sbf.append(values)
                sql_options.add(sbf);
            }

        }

        //将读取的远程数据插入到本地数据库中
        if(sql_options.size()>0){
            def time =  this.SynchronizationData (sql_db,sql_options,maxCount);
            totalTime = totalTime+time
            sql_options.clear()

            //加上删除的记录数
            totalCount+=deleteArr?.size()

        }else{
            //没有下载数据时，判断是否删除数据了
            totalCount= deleteArr.size()
            totalTime="0.0"
        }

        //保存最后同步时间
        saveSynTime(sql_db,1,serverTime,curDeleteTime)

        returnResult = totalCount+","+totalTime
        return returnResult;

    }

    /**
     * @Description 按公告类型同步公告信息
     * @param oracle_db
     * @param sql_db
     * @param maxCount
     * @param type
     * @return
     * @Create 2013-11-25 huxx
     * @Update 农用车公告同步改为增量同步，增加的部分为创建时间或者更新时间晚于上次同步时间的记录 2015-04-14 zhuwei
     * @Update 农用车公告通过删除记录为停用时间超过25天，不超过40天的记录做删除操作  by zhuwei
     */
    def  GetServeDataByType(Sql oracle_db,Sql sql_db,lastSynTime,lastDeleteTime,int maxCount,String type){
        def curDeleteTime= getNowTimeBySeriver(oracle_db) //获取服务器当前时间
        //删除本地服务器已删除的公告信息记录   同时用于计算总的同步记录数，
//       def deleteSql="delete from wzh_carstorage where car_Storage_type='${type}'  and cast(turn_off_time as datetime) <(getdate()-25) and cast(turn_off_time as datetime) >(getdate()-40) "
//       def result=sql_db.execute(deleteSql)
        def deleteArr= interactive_Oracle_Sql(oracle_db,sql_db,1,lastDeleteTime); //删除本地数据库中服务端已删除的分解前公告记录

        def returnResult=""
        def totalTime = 0.0 ////总耗时
        def totalCount = 0 ////更新总条数

        int start=0
        int offset=0
        List<String> sql_options = new ArrayList<String>();
        int count=0
        //获取当前时间
        def serverTime=getNowTimeBySeriver(oracle_db)
        while(true){
            start=count*maxCount
            offset=start+maxCount
            count+=1
//            StringBuffer sbf_e = new StringBuffer("select o.*  from(select t.* ,rownum as num from (SELECT * FROM WZH_CARSTORAGE where  car_Storage_type='${type}' ");
//            sbf_e.append(") t where rownum <=${offset}) o where o.num >${start}")
            StringBuffer sbf_e = new StringBuffer("select o.*  from(select t.* ,rownum as num from (SELECT * FROM WZH_CARSTORAGE  where  car_Storage_type='${type}' ");
            if (lastSynTime){
                sbf_e.append(" and (  (to_date(CREATE_TIME,'yyyy-mm-dd hh24:mi:ss') >=to_date('"+lastSynTime+"','yyyy-mm-dd hh24:mi:ss')")
                sbf_e.append(" or to_date(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') >=to_date('"+lastSynTime+"','yyyy-mm-dd hh24:mi:ss')) )")
            }
            sbf_e.append(") t where rownum <=${offset}) o where o.num >${start}")

            def rows_e = oracle_db.rows(sbf_e.toString())

            if (!(rows_e && rows_e.size()>0)){
                break;
            }

            totalCount += rows_e.size();

            rows_e?.each{r->
                StringBuffer sbf = new StringBuffer("insert into wzh_carStorage ");
                StringBuffer title=new StringBuffer(" (id")
                StringBuffer values=new StringBuffer(" values('${r.ID}'")
                r.entrySet().each{v->
                    def noNeed=["ID","NUM"]
                    if (!noNeed.contains(v.key) ){
                        title.append(",${v.key}")
                        values.append(",'${v.value?v.value:''}'")
                    }

                }
                title.append(")")
                values.append(")")
                sbf.append(title)
                sbf.append(values)
                sql_options.add(sbf);
            }

        }

        //将读取的远程数据插入到本地数据库中
        if(sql_options.size()>0){
            def time =  this.SynchronizationData (sql_db,sql_options,maxCount);
            totalTime = totalTime+time
            sql_options.clear()

        }else{
            totalCount=0
            totalTime="0.0"
        }
        //保存最后同步时间
        saveSynTime(sql_db,1,serverTime,curDeleteTime)

        returnResult = totalCount+","+totalTime
        return returnResult;

    }

	/**  获得服务器数据进行封装操作  (一致性信息)*/
    ///// zouQ desc:  2013年8月24日 修改了服务器（公告、一致性信息）数据再修改时,不能及时更新到车间数据库
    //
    /**
     * @Description 车间同步一致性证书的方法
     * @param oracle_db
     * @param sql_db
     * @param lastSynTime
     * @param listDeleteTime
     * @param maxCount
     * @return
     *
     * @Update 2013-09-04 huxx 1、将最后删除时间与最后下载时间分开保存 。2、同步的总数=下载数量+删除记录数-下载记录数和删除记录数共有的记录
     * @Update 2013-11-25 huxx 修改当数据量比较大时，远程数据分批读取和插入本地数据库
     */
	def  GetServeDataByConsistency(Sql oracle_db,Sql sql_db,def lastSynTime,def listDeleteTime,int maxCount){
        //删除本地服务器已删除或修改的记录
        def curDeleteTime= getNowTimeBySeriver(oracle_db)
        def deleteArr=interactive_Oracle_Sql(oracle_db,sql_db,2,listDeleteTime);

        def serverTime=getNowTimeBySeriver(oracle_db)

        def returnResult=""
        def totalTime = 0.0 ////总耗时
        def totalCount = 0 ////更新总条数

        int start=0
        int offset=0
        List<String> sql_options = new ArrayList<String>();
        int count=0
        while(true){
            start=count*maxCount
            offset=start+maxCount
            count+=1
            StringBuffer sbf_e = new StringBuffer("select o.*  from(select t.* ,rownum as num from (SELECT * FROM COC_CARSTORAGE ");
            if (lastSynTime){
                sbf_e.append(" where （to_date(coc_create_time,'yyyy-mm-dd hh24:mi:ss') >=to_date('"+lastSynTime+"','yyyy-mm-dd hh24:mi:ss')")
                sbf_e.append(" or to_date(coc_update_time,'yyyy-mm-dd hh24:mi:ss') >=to_date('"+lastSynTime+"','yyyy-mm-dd hh24:mi:ss'))")
            }
            sbf_e.append(") t where rownum <=${offset}) o where o.num >${start}")

            def rows_e = oracle_db.rows(sbf_e.toString())

            if (!(rows_e && rows_e.size()>0)){
                break;
            }

            totalCount += rows_e.size();

            rows_e.each{coc->
                deleteArr.remove(coc.id)

                StringBuffer sbf = new StringBuffer("insert into COC_CARSTORAGE ");
                StringBuffer title=new StringBuffer(" (id")
                StringBuffer values=new StringBuffer(" values('${coc.ID}'")
                coc.entrySet().each{v->
                    def noNeed=["ID","NUM","COC_CREATER_ID","COC_UPDATER_ID"]
                    if (!noNeed.contains(v.key) ){
                        title.append(",${v.key}")
                        values.append(",'${v.value?v.value:''}'")
                    }

                }
                title.append(")")
                values.append(")")
                sbf.append(title)
                sbf.append(values)
                sql_options.add(sbf);

            }

        }

        //将读取的远程数据插入到本地数据库中
        if(sql_options.size()>0){
            def time =  this.SynchronizationData (sql_db,sql_options,maxCount);
            totalTime = totalTime+time
            sql_options.clear()

            //加上删除的记录数
            totalCount+=deleteArr?.size()

        }else{
            //没有下载数据时，判断是否删除数据了
            totalCount= deleteArr.size()
            totalTime="0.0"
        }

        //保存最后同步时间
        saveSynTime(sql_db,2,serverTime,curDeleteTime)

        returnResult = totalCount+","+totalTime
        return returnResult;
    }
	
	
	/**
     * 同步服务器数据到本地

     *  @Update 2013-08-29 huxx 将最后更新时间分开保存
     *  @Update 2013-11-25 huxx 修改插入，分批处理
     * */
	def SynchronizationData(Sql sql_con,List<String> sql_options,int maxCount) throws Exception{
        Connection con = sql_con.useConnection;
        con.setAutoCommit(false)
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY)
        long  statrTime = System.currentTimeMillis()
        statement.clearBatch()

        def all=sql_options.size()
        def count=0
        def allCount=0
        sql_options.each{
            count+=1
            allCount+=1
            statement.addBatch(it.toString())
            if (count==maxCount || allCount==all){
                statement.executeBatch()
                con.commit()
                count=0
                statement.clearBatch()
            }
        }
        return (System.currentTimeMillis()-statrTime)/1000
	}
    /**
     * @Description 保存更新时间
     * @param ora_con
     * @param sql_con
     * @param   flag=1保存公告信息最后更新时间；flag=2保存一致性证书最后更新时间
     */
    def saveSynTime(Sql sql_con,int flag,String synTime,String deleteTime){
        def updateCount=0
        def sql=""
        try{
            if (flag ==1){
                sql = "update  tbl_lastSynTime set CARSTORAGE_LASTSYNTIME = '"+synTime+"',CARSTORAGE_LASTDELETETIME='"+deleteTime+"';"
            } else if (flag ==2 ){
                sql = "update  tbl_lastSynTime set COC_LASTSYNTIME = '"+synTime+"',COC_LASTDELETETIME='"+deleteTime+"';"
            }
            updateCount=sql_con.executeUpdate(sql)
            sql_con.commit()
        }catch (Exception e){
            e.cause.printStackTrace();
        }
    }

     ////获取当前服务器日期
    def getNowTimeBySeriver(Sql sql)
    {
        try{
            def result =  sql.rows("SELECT to_char(Sysdate,'yyyy-mm-dd hh24:mi:ss') as times FROM dual")
        if(result.size()>0){
            return result[0].times
        }else{
            return null
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * @Description 获取上次同步时间
     * @param sql
     * @return
     * @Create 2013-08-29 huxx
     */
    def getLastSynTime(sql){
        try{
            def result =  sql.rows("select COC_LASTSYNTIME as cocLastSynTime,COC_LASTDELETETIME as cocLastDELETETime,CARSTORAGE_LASTSYNTIME as carStorageLastSynTime,CARSTORAGE_LASTDELETETIME as carStorageLASTDELETETIME from tbl_lastSynTime")
            if(result.size()>0){
                return result[0]
            }else{
                sql.execute("INSERT INTO tbl_lastSynTime (COC_LASTSYNTIME,COC_LASTDELETETIME,CARSTORAGE_LASTSYNTIME,CARSTORAGE_LASTDELETETIME) VALUES ('1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59')")
                return ['1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59']
            }
        }catch (Exception e){
            e.printStackTrace();
            sql.execute """
                    create table tbl_lastSynTime
					(
					  COC_LASTSYNTIME      VARCHAR(255) not null,
					  COC_LASTDELETETIME      VARCHAR(255) not null,
					  CARSTORAGE_LASTDELETETIME      VARCHAR(255) not null,
					  CARSTORAGE_LASTSYNTIME varchar(255)  not null
				    )
				 """
            sql.execute("INSERT INTO tbl_lastSynTime (COC_LASTSYNTIME,COC_LASTDELETETIME,CARSTORAGE_LASTSYNTIME,CARSTORAGE_LASTDELETETIME) VALUES ('1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59')")
            return ['1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59','1989-08-09 23:59:59']
        }
    }

   ////初始化信息/修复
    def initialize(Sql sql_con)
    {
        try{
        sql_con.execute """
            DELETE FROM wzh_carStorage
          """
        sql_con.execute """
                DELETE FROM COC_CARSTORAGE
              """
        sql_con.execute"""
             update tbl_lastSynTime set COC_LASTSYNTIME='1989-08-09 23:59:59',CARSTORAGE_LASTSYNTIME='1989-08-09 23:59:59',COC_LASTDELETETIME='1989-08-09 23:59:59',CARSTORAGE_LASTDELETETIME='1989-08-09 23:59:59'
            """
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     *   2013年8月24日 zouQ
     *   服务器跟车间数据库实现数据同步交互操作（仅限于 服务器数据更新、删除 操作）
     *   @Description 删除本地数据库中服务端已删除的记录
     *   params : type 类型 1 公告信息  2 一致性信息
     *   @Update 2013-08-29 huxx 1、删除组成的sql语句的最好一个逗号。2、解决in中最大1000的限制 3、添加至删除同步时间后的修改的记录
     *   @Updadte 2015-04-10 同步时删除车间服务器端停用超过25天不超过40天的公告 zhuwei
     */
    def interactive_Oracle_Sql(Sql ora_con,Sql sql_con,def type,def lastDeleteTime){
        def result=[]
        def rows
        ////获取服务器 Temp表中进行修改、删除操作的 公告信息、一致性信息
//        def rows =  ora_con.rows("SELECT WZH_CARSTORAGEID FROM wzh_temp where WZH_TYPE=${type}  and to_date(insert_Time,'yyyy-mm-dd hh24:mi:ss') >to_date('${lastDeleteTime}','yyyy-mm-dd hh24:mi:ss')")
        if(type.toString()=='1'){ //取TEMP表中的已经停用和编辑更新过的公告信息
            rows =  ora_con.rows(" SELECT WZH_CARSTORAGEID FROM wzh_temp where WZH_TYPE=${type}  and ( (to_date(insert_Time,'yyyy-mm-dd hh24:mi:ss') <( sysdate - 25) and to_date(insert_Time,'yyyy-mm-dd hh24:mi:ss') >( sysdate - 40)) or (CAR_STORAGE_NO is null)   ) ")
        }else if(type.toString()=='2'){
            rows =  ora_con.rows("SELECT WZH_CARSTORAGEID FROM wzh_temp where WZH_TYPE=${type}  and to_date(insert_Time,'yyyy-mm-dd hh24:mi:ss') <( sysdate - 25) and to_date(insert_Time,'yyyy-mm-dd hh24:mi:ss') >( sysdate - 40) ")
        }

        ////用来封装要进行删除，修改的 公告信息、一致性信息 （仅限于执行车间数据库条件）
        StringBuffer sbf_ora = new StringBuffer();
        rows?.each {c->
            result.add(c.WZH_CARSTORAGEID)
            sbf_ora.append("'"+c.WZH_CARSTORAGEID+"'")
            sbf_ora.append(",")
        }
        //去掉最后一个逗号
        def ins=sbf_ora.toString()
        if (ins){
            ins = ins.substring(0,ins.lastIndexOf(",")).split(",")
            def ids=cn.com.wz.parent.StringUtil.splitIn(ins)
            ids?.each{ it->
                def str_sql;
                if (type==1){   ///公告信息
                    str_sql = "DELETE FROM wzh_carStorage where ID in("+it.collect().join(",") +")";
                }
                if (type==2){   //// 一致性信息
                    str_sql = "DELETE FROM COC_CARSTORAGE where ID in("+it.collect().join(",")+")";
                }
                if (str_sql){
                    sql_con.execute(str_sql);
                }
            }
        }
        return result
    }

    ////连接关闭
    def closeCon(Sql sql_con,Sql ora_con)
    {
        Connection con = sql_con.useConnection;
        ora_con.useConnection.close();
        con.close();
    }

}

