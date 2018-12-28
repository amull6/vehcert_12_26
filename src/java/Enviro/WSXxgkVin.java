/**
 * WSXxgkVin.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package Enviro;

public interface WSXxgkVin extends javax.xml.rpc.Service {

/**
 * <strong>Vin报送服务<a href="http://www.vecc-mep.org.cn/download/WSXxgkVin.PDF"
 * target="_blank"><font size="3" color="#FF0000">(详细使用说明点此下载)</font></a><br><br></strong>数据来源于<strong>中国环境保护部机动车排污监控中心</strong>
 * <a href="http://www.vecc-mep.org.cn" target="_blank">http://www.vecc-mep.org.cn/</a>
 */
    public String getWSXxgkVinSoap12Address();

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap12() throws javax.xml.rpc.ServiceException;

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getWSXxgkVinSoapAddress();

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap() throws javax.xml.rpc.ServiceException;

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
