/**
 * CertificateRequestService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package vehcert;

public interface CertificateRequestService extends javax.xml.rpc.Service {
    public String getCertificateRequestServiceSoap12Address();

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap12() throws javax.xml.rpc.ServiceException;

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public String getCertificateRequestServiceSoapAddress();

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap() throws javax.xml.rpc.ServiceException;

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
