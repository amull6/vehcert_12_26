/**
 * CertificateRequestServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package vehcert;

public class CertificateRequestServiceLocator extends org.apache.axis.client.Service implements CertificateRequestService {

    public CertificateRequestServiceLocator() {
    }


    public CertificateRequestServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CertificateRequestServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CertificateRequestServiceSoap12
    private String CertificateRequestServiceSoap12_address = "http://localhost:9901/CertificateRequestService.asmx";

    public String getCertificateRequestServiceSoap12Address() {
        return CertificateRequestServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String CertificateRequestServiceSoap12WSDDServiceName = "CertificateRequestServiceSoap12";

    public String getCertificateRequestServiceSoap12WSDDServiceName() {
        return CertificateRequestServiceSoap12WSDDServiceName;
    }

    public void setCertificateRequestServiceSoap12WSDDServiceName(String name) {
        CertificateRequestServiceSoap12WSDDServiceName = name;
    }

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CertificateRequestServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCertificateRequestServiceSoap12(endpoint);
    }

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            vehcert.CertificateRequestServiceSoap12Stub _stub = new vehcert.CertificateRequestServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getCertificateRequestServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCertificateRequestServiceSoap12EndpointAddress(String address) {
        CertificateRequestServiceSoap12_address = address;
    }


    // Use to get a proxy class for CertificateRequestServiceSoap
    private String CertificateRequestServiceSoap_address = "http://localhost:9901/CertificateRequestService.asmx";

    public String getCertificateRequestServiceSoapAddress() {
        return CertificateRequestServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String CertificateRequestServiceSoapWSDDServiceName = "CertificateRequestServiceSoap";

    public String getCertificateRequestServiceSoapWSDDServiceName() {
        return CertificateRequestServiceSoapWSDDServiceName;
    }

    public void setCertificateRequestServiceSoapWSDDServiceName(String name) {
        CertificateRequestServiceSoapWSDDServiceName = name;
    }

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CertificateRequestServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCertificateRequestServiceSoap(endpoint);
    }

    public vehcert.CertificateRequestServiceSoap_PortType getCertificateRequestServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            vehcert.CertificateRequestServiceSoap_BindingStub _stub = new vehcert.CertificateRequestServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getCertificateRequestServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCertificateRequestServiceSoapEndpointAddress(String address) {
        CertificateRequestServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (vehcert.CertificateRequestServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                vehcert.CertificateRequestServiceSoap12Stub _stub = new vehcert.CertificateRequestServiceSoap12Stub(new java.net.URL(CertificateRequestServiceSoap12_address), this);
                _stub.setPortName(getCertificateRequestServiceSoap12WSDDServiceName());
                return _stub;
            }
            if (vehcert.CertificateRequestServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                vehcert.CertificateRequestServiceSoap_BindingStub _stub = new vehcert.CertificateRequestServiceSoap_BindingStub(new java.net.URL(CertificateRequestServiceSoap_address), this);
                _stub.setPortName(getCertificateRequestServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("CertificateRequestServiceSoap12".equals(inputPortName)) {
            return getCertificateRequestServiceSoap12();
        }
        else if ("CertificateRequestServiceSoap".equals(inputPortName)) {
            return getCertificateRequestServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.vidc.info/certificaterequest", "CertificateRequestService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.vidc.info/certificaterequest", "CertificateRequestServiceSoap12"));
            ports.add(new javax.xml.namespace.QName("http://service.vidc.info/certificaterequest", "CertificateRequestServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("CertificateRequestServiceSoap12".equals(portName)) {
            setCertificateRequestServiceSoap12EndpointAddress(address);
        }
        else 
if ("CertificateRequestServiceSoap".equals(portName)) {
            setCertificateRequestServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
