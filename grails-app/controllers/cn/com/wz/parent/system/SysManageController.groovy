package cn.com.wz.parent.system

import cn.com.wz.parent.ConstantsUtil
import cn.com.wz.parent.DateUtil
import cn.com.wz.parent.base.BaseController

import java.lang.management.ManagementFactory
import java.lang.management.OperatingSystemMXBean;
/**
 * @Description
 * @Author 　 h u x x
 * @CreateTime 　2013-06-06
 * */
class SysManageController extends BaseController {

    def sysManageService

    /**
     * @Description 获取服务器信息
     * @create huxx 2013-06-06
     */
    def osInfo(){
//        def osName=System.properties.getProperty("os.name")
//        params.osName=osName
//        def osVersion=System.properties.getProperty("os.version")
//        params.osVersion=osVersion
//        params.userName=System.properties.getProperty("user.name")
//        params.userCountry=System.properties.getProperty("user.country")
//
//        params.javaVersion=System.properties.getProperty("java.version")
//        params.vmInfo=System.properties.getProperty("java.vm.info")
//
//        params.grailsName=System.properties.getProperty("grails.name")
//
////        //内存信息
////        params.totalMemory=Runtime.getRuntime().totalMemory()
////        def leftMemory=Runtime.getRuntime().freeMemory()
////        def maxMemory=Runtime.getRuntime().maxMemory()
//
//        //CPU信息
//        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//
//        int g = 1024*1024*1024;
//        // 总的物理内存
//        float totalMemorySize = osmxb.getTotalPhysicalMemorySize() / g;
//        // 剩余的物理内存
//        float freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / g;
//        // 已使用的物理内存
//        float usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize())/ g;
//
//        params.totalMemorySize=totalMemorySize
//        params.freePhysicalMemorySize=freePhysicalMemorySize
//        params.usedMemory=usedMemory
//
//        // 获得线程总数
//        ThreadGroup parentThread;
//        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent()){
//
//        }
//
//        int totalThread = parentThread.activeCount()
//        params.totalThread=totalThread
//        double cpuRatio = 0;
//        if (osName.toLowerCase().startsWith("windows")) {
//            String procCmd = '''${System.getenv("windir")}
//             "\\system32\\wbem\\wmic.exeprocess get Caption,CommandLine,"
//            "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount
//            '''
//            cpuRatio = sysManageService.getCpuRatioForWindows(procCmd)
//        }
//        else {
//            cpuRatio = sysManageService.getCpuRateForLinux(osVersion);
//        }
//
//        params.cpuRatio=cpuRatio
        render (view: '/cn/com/wz/parent/system/sysmanage/index',model: params)
    }


}
