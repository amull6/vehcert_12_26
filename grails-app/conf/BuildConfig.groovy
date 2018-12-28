grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.offline.mode=false
//改变war生成路径和名称
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.war.file = "target/${appName}.war"
//改变应用程序的访问端口号
grails.server.port.http =8686
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
//        pom true
        inherits true // Whether to inherit repository definitions from plugins
        grailsHome()
        grailsPlugins()

        grailsCentral()
        mavenCentral()

        mavenRepo "http://192.168.1.50:8081/nexus/content/groups/wf/"
        mavenRepo "http://192.168.1.50:8081/nexus/content/groups/public/"
        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        runtime 'mysql:mysql-connector-java:5.1.10-bin'
        runtime 'poi:poi:3.9-20121203'
        runtime 'poi:poi-examples:3.9-20121203'
        runtime 'poi:poi-excelant:3.9-20121203'
        runtime 'poi:poi-ooxml:3.9-20121203'
        runtime 'poi:poi-ooxml-schemas:3.9-20121203'
        runtime 'poi:poi-scratchpad:3.9-20121203'
        runtime 'EWSAPI:EWSAPI:1.1.4'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"


        build ":tomcat:$grailsVersion"
        compile ":ckeditor:3.6.3.0"
        compile ":ajax-uploader:1.1"
        compile ":export:1.5"
        compile ":quartz:1.0-RC9"

//        compile ":cas-client:1.1"
    }
}
