/**
 * SysInfoService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package cn.com.wz.vehcert;

public interface SysInfoService extends javax.xml.rpc.Service {
    public String getBasicHttpBinding_IPrintInfoAddress();

    public IPrintInfo getBasicHttpBinding_IPrintInfo() throws javax.xml.rpc.ServiceException;

    public IPrintInfo getBasicHttpBinding_IPrintInfo(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
