/**
 * SI_DMS_ZSDI034_OUTService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package sendDataToSap;

public interface SI_DMS_ZSDI034_OUTService extends javax.xml.rpc.Service {
    public String getHTTPS_PortAddress();

    public sendDataToSap.SI_DMS_ZSDI034_OUT getHTTPS_Port() throws javax.xml.rpc.ServiceException;

    public sendDataToSap.SI_DMS_ZSDI034_OUT getHTTPS_Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getHTTP_PortAddress();

    public sendDataToSap.SI_DMS_ZSDI034_OUT getHTTP_Port() throws javax.xml.rpc.ServiceException;

    public sendDataToSap.SI_DMS_ZSDI034_OUT getHTTP_Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
