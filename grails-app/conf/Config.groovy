// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

environments {
    development {
        grails.config.locations = [ "file:${System.properties.getProperty("base.dir")}\\web-app\\data\\parent\\baseConfig.properties",
                "file:${System.properties.getProperty("base.dir")}\\web-app\\data\\subConfig.properties"
        ]
    }
    production {
        grails.config.locations = [ "classpath:\\..\\..\\data\\parent\\baseConfig.properties",
                "classpath:\\..\\..\\data\\subConfig.properties",
        ]
    }
}

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.gorm.failOnError=false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        pdf: 'application/pdf',
        rtf: 'application/rtf',
        excel: 'application/vnd.ms-excel',
        ods: 'application/vnd.oasis.opendocument.spreadsheet',
        all: '*/*',
        json: ['application/json','text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/data/*','/plugins/*']


//grails.config.locations = [ "classpath:docmgr-config.properties"]


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

///添加
grails.gsp.enable.reload = true
//grails.gsp.view.dir = "/cn/com/wz/elearing/"

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
//environments {
//    development {
//        grails.logging.jul.usebridge = true
//        grails.serverURL = "http://localhost:9999/ewp"
//    }
//    production {
//        grails.logging.jul.usebridge = false
//        // TODO: grails.serverURL = "http://www.changeme.com"
//        grails.serverURL = "http://localhost"
//    }
//}



// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'
    info  'grails.app.jobs'


}
//debug插件配置
//logger.grails.app.service.DebugService='info'
//grails.debug.system=true
//grails.debug.stats=true
//grails.debug.params=true
//grails.debug.headers=true
//grails.debug.controller=true
//grails.debug.session=true
//grails.debug.requestAttributes=true
//grails.debug.model=true
//grails.debug.enable=true

// ckeditor configuration
ckeditor {
    config  = "/js/myckconfig.js"
    skipAllowedItemsCheck = false
    defaultFileBrowser = "ofm"
    upload {
        basedir = "/uploads/"
        overwrite = false
        link {
            browser = true
            upload = false
            allowed = []
            denied = ['html', 'htm', 'php', 'php2', 'php3', 'php4', 'php5',
                    'phtml', 'pwml', 'inc', 'asp', 'aspx', 'ascx', 'jsp',
                    'cfm', 'cfc', 'pl', 'bat', 'exe', 'com', 'dll', 'vbs', 'js', 'reg',
                    'cgi', 'htaccess', 'asis', 'sh', 'shtml', 'shtm', 'phtm']
        }
        image {
            browser = true
            upload = true
            allowed = ['jpg', 'gif', 'jpeg', 'png']
            denied = []
        }
        flash {
            browser = true
            upload = true
            allowed = ['swf']
            denied = []
        }
    }
}

//// cas client configuration, required by CasClientPlugin
//cas {
//    urlPattern = '/*'
////    urlPattern = ['/oneurl/*', '/another', '/anotheranother/*']
//    disabled = false
//}
//environments {
//    development {
//        cas.loginUrl = 'https://cas.server:8443/cas/login'
////        cas.validateUrl = 'https://cas.server:8443/cas/serviceValidate'
//        cas.serverName = 'localhost:9999/ewp/login'
////        cas.serviceUrl = 'http://dev.casclient.demo.com/access'
//        cas.disabled = false
//        cas.mocking = true
//    }
//    test {
////        cas.loginUrl = 'https://test.casserver.demo.com/cas/login'
////        cas.validateUrl = 'https://test.cas.com/cas/serviceValidate'
////        cas.serverName = 'test.casclient.demo.com:80'
////        cas.serviceUrl = 'http://test.casclient.demo.com/access'
//    }
//    production {
////        cas.loginUrl = 'https://prod.casserver.demo.com/cas/login'
////        cas.validateUrl = 'https://prod.casserver.demo.com/cas/serviceValidate'
////        cas.serverName = 'prod.casclient.demo.com:80'
////        cas.serviceUrl = 'http://prod.casclient.demo.com/access'
//    }
//}


