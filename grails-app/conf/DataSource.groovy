
//以下是oracle数据连接方式
import org.springframework.core.io.support.PropertiesLoaderUtils
def dsp=PropertiesLoaderUtils.loadAllProperties("db.properties")
def dbType=dsp.getProperty("dbType")?dsp.getProperty("dbType"):'oracle'
def validationQuery=dsp.getProperty("validationQuery")
if("oracle".equals(dbType)){

    dataSource {
        driverClassName =dsp.getProperty("driverClassName")
        url = dsp.getProperty("url")
        dialect = org.hibernate.dialect.Oracle10gDialect
        lobHandler="oracleLobHandler"
        logSql  = dsp.getProperty("logSql")=='true'?true:false
        formatSql = dsp.getProperty("formatSql")=='true'?true:false
        pooled = dsp.getProperty("pooled")=='true'?true:false
    }

    hibernate {
        cache.use_second_level_cache = true
        cache.use_query_cache = false
        cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
        //	connection.SetBigStringTryClob=true
        //	jdbc.batch_size=0
    }
    environments {
        development {
            dataSource {
                dbCreate =  dsp.getProperty("dbCreate")// one of 'create', 'create-drop', 'update', 'validate', ''
                username =  dsp.getProperty("userName")
                password =  dsp.getProperty("password")
                properties {
                    maxActive = -1
                    minEvictableIdleTimeMillis=1800000
                    timeBetweenEvictionRunsMillis=1800000
                    numTestsPerEvictionRun=3
                    testOnBorrow=true
                    testWhileIdle=true
                    testOnReturn=true
                    validationQuery=validationQuery
                }
            }
        }
        test {
            dataSource {
                dbCreate = dsp.getProperty("dbCreate")// one of 'create', 'create-drop', 'update', 'validate', ''
                username =  dsp.getProperty("userName")
                password =  dsp.getProperty("password")
            }
        }
        production {
            dataSource {
                dbCreate =  dsp.getProperty("dbCreate")// one of 'create', 'create-drop', 'update', 'validate', ''
                username =  dsp.getProperty("userName")
                password =  dsp.getProperty("password")
                properties {
                    maxActive = -1
                    minEvictableIdleTimeMillis=1800000
                    timeBetweenEvictionRunsMillis=1800000
                    numTestsPerEvictionRun=3
                    testOnBorrow=true
                    testWhileIdle=true
                    testOnReturn=true
                    validationQuery=validationQuery
                }
            }
        }
    }
}else if ('mysql'.equals(dbType)){
    dataSource {
        driverClassName = dsp.getProperty("driverClassName")
        username = dsp.getProperty("userName")
        password = dsp.getProperty("password")
        logSql  = dsp.getProperty("logSql")=='true'?true:false
        formatSql = dsp.getProperty("formatSql")=='true'?true:false
        pooled = dsp.getProperty("pooled")=='true'?true:false
    }
    hibernate {
        cache.use_second_level_cache = true
        cache.use_query_cache = false
        cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    }
// environment specific settings
    environments {
        development {
            dataSource {
                dbCreate = dsp.getProperty("dbCreate") // one of 'create', 'create-drop', 'update', 'validate', ''
                url = dsp.getProperty("devurl")
            }
        }
        test {
            dataSource {
                dbCreate = dsp.getProperty("dbCreate")
                url = dsp.getProperty("testurl")
            }
        }
        production {
            dataSource {
                dbCreate =dsp.getProperty("dbCreate")
                url = dsp.getProperty("produrl")
                properties {
                    maxActive = -1
                    minEvictableIdleTimeMillis=1800000
                    timeBetweenEvictionRunsMillis=1800000
                    numTestsPerEvictionRun=3
                    testOnBorrow=true
                    testWhileIdle=true
                    testOnReturn=true
                    validationQuery=validationQuery
                }
            }
        }
    }
}else if ('sqlserver'.equals(dbType)){
    dataSource {
        driverClassName = dsp.getProperty("driverClassName")
        username = dsp.getProperty("userName")
        password = dsp.getProperty("password")
        logSql  = dsp.getProperty("logSql")=='true'?true:false
        formatSql = dsp.getProperty("formatSql")=='true'?true:false
        pooled = dsp.getProperty("pooled")=='true'?true:false
        dialect = org.hibernate.dialect.SQLServerDialect
    }
    hibernate {

        cache.use_second_level_cache = true
        cache.use_query_cache = false
        cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    }
// environment specific settings
    environments {
        development {
            dataSource {
                dbCreate = dsp.getProperty("dbCreate") // one of 'create', 'create-drop', 'update', 'validate', ''
                url = dsp.getProperty("url")
            }
        }
        test {
            dataSource {
                dbCreate = dsp.getProperty("dbCreate")
                url = dsp.getProperty("url")
            }
        }
        production {
            dataSource {
                dbCreate =dsp.getProperty("dbCreate")
                url = dsp.getProperty("url")
                properties {
                    maxActive = -1
                    minEvictableIdleTimeMillis=1800000
                    timeBetweenEvictionRunsMillis=1800000
                    numTestsPerEvictionRun=3
                    testOnBorrow=true
                    testWhileIdle=true
                    testOnReturn=true
                    validationQuery=validationQuery
                }
            }
        }
    }
}
