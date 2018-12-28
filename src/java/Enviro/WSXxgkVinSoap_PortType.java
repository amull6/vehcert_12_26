/**
 * WSXxgkVinSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package Enviro;

public interface WSXxgkVinSoap_PortType extends java.rmi.Remote {

    /**
     * <h3>登录中心服务接口，成功返回临时认证码32位KEY码，超时则需重新登录</h3><p>输入参数：manufid
     * = 企业编号(机构代码)，不能为空；password =  报送密码；返回数据：一个标准的XML字符串<br /><br><br><br><br><p>
     */
    public String login(String manufid, String password) throws java.rmi.RemoteException;

    /**
     * <h3>查询已报送VIN数</h3><p>输入参数： key = 临时认证码，不能为空；xshzh =  型式核准号；返回数据：一个标准的XML字符串<br
     * /><br><br><br><br><p>
     */
    public String getVinCountByXxgkh(String key, String xxgkh) throws java.rmi.RemoteException;

    /**
     * <h3>查询已报送VIN总数通过报送日期</h3><p>输入参数： key = 临时认证码，不能为空；dtFrom =
     * 报送起始时间；dtTo =  报送截至时间；时间格式 YYYY-MM-DD HH24:MI:SS；返回数据：一个标准的XML字符串<br
     * /><br><br><br><br><p>
     */
    public String getVinCountByDate(String key, String dtFrom, String dtTo) throws java.rmi.RemoteException;

    /**
     * <h3>查询已报送VIN所对应的环保激活码</h3><p>输入参数： key = 临时认证码，不能为空；vin = 
     * vin号；返回数据：一个标准的XML字符串<br /><br><br><br><br><p>
     */
    public String getHbcodeByVin(String key, String vin) throws java.rmi.RemoteException;

    /**
     * <h3>删除已上报VIN信息</h3><p>输入参数： key = 临时认证码，不能为空；vin =  vin号；返回数据：一个标准的XML字符串<br
     * /><br><br><br><br><p>
     */
    public String delData(String key, String vin) throws java.rmi.RemoteException;

    /**
     * <h3>退出本次连接</h3><p>输入参数；Key = 临时认证码，不能为空；返回数据：一个标准的XML字符串<br
     * /><br><br><br><br><p>
     */
    public String logout(String key) throws java.rmi.RemoteException;

    /**
     * <h3>上报Vin数据；注意，报送成功后1小时以后才会算正式报送完成</h3><p>输入参数： key = 临时认证码，不能为空；strVinData:
     * 信息主体<br><br><br><br><p>
     */
    public String sendVinData(String key, String strVinData) throws java.rmi.RemoteException;
}
