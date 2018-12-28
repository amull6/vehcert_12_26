package cn.com.wz.parent.util
/**
 * @Description 执行sql的service
 * @Create huxx 2013-06-22
 */
class SqlService {
    def dataSource

    /**
     * 执行本地SQL 按照条件返回查询数据
     */
    def  executeList(sql){
        def db = new groovy.sql.Sql(dataSource)
        return db.rows(sql.toString())
    }

    /**
     * 执行本地SQL 操作 用于执行insert update delete以及SQL DDL语句
     * return 返回受影响行数
     */
    def  executeUpdate(sql){
        def db = new groovy.sql.Sql(dataSource)
        return db.executeUpdate(sql);
    }
}
