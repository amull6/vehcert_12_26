/**
 * AfficheFromCRMLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package newDms;

public class AfficheFromCRMLocator extends org.apache.axis.client.Service implements AfficheFromCRM {

    public AfficheFromCRMLocator() {
    }


    public AfficheFromCRMLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AfficheFromCRMLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AfficheFromCRMSoap12
    private String AfficheFromCRMSoap12_address = "http://crmwebservice.wuzheng.com.cn:8089/AfficheFromCRM.asmx";

    public String getAfficheFromCRMSoap12Address() {
        return AfficheFromCRMSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String AfficheFromCRMSoap12WSDDServiceName = "AfficheFromCRMSoap12";

    public String getAfficheFromCRMSoap12WSDDServiceName() {
        return AfficheFromCRMSoap12WSDDServiceName;
    }

    public void setAfficheFromCRMSoap12WSDDServiceName(String name) {
        AfficheFromCRMSoap12WSDDServiceName = name;
    }

    public AfficheFromCRMSoap_PortType getAfficheFromCRMSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AfficheFromCRMSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAfficheFromCRMSoap12(endpoint);
    }

    public AfficheFromCRMSoap_PortType getAfficheFromCRMSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AfficheFromCRMSoap12Stub _stub = new AfficheFromCRMSoap12Stub(portAddress, this);
            _stub.setPortName(getAfficheFromCRMSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAfficheFromCRMSoap12EndpointAddress(String address) {
        AfficheFromCRMSoap12_address = address;
    }


    // Use to get a proxy class for AfficheFromCRMSoap
    private String AfficheFromCRMSoap_address = "http://crmwebservice.wuzheng.com.cn:8089/AfficheFromCRM.asmx";

    public String getAfficheFromCRMSoapAddress() {
        return AfficheFromCRMSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String AfficheFromCRMSoapWSDDServiceName = "AfficheFromCRMSoap";

    public String getAfficheFromCRMSoapWSDDServiceName() {
        return AfficheFromCRMSoapWSDDServiceName;
    }

    public void setAfficheFromCRMSoapWSDDServiceName(String name) {
        AfficheFromCRMSoapWSDDServiceName = name;
    }

    public AfficheFromCRMSoap_PortType getAfficheFromCRMSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AfficheFromCRMSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAfficheFromCRMSoap(endpoint);
    }

    public AfficheFromCRMSoap_PortType getAfficheFromCRMSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            AfficheFromCRMSoap_BindingStub _stub = new AfficheFromCRMSoap_BindingStub(portAddress, this);
            _stub.setPortName(getAfficheFromCRMSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAfficheFromCRMSoapEndpointAddress(String address) {
        AfficheFromCRMSoap_address = address;
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
            if (AfficheFromCRMSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                AfficheFromCRMSoap12Stub _stub = new AfficheFromCRMSoap12Stub(new java.net.URL(AfficheFromCRMSoap12_address), this);
                _stub.setPortName(getAfficheFromCRMSoap12WSDDServiceName());
                return _stub;
            }
            if (AfficheFromCRMSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                AfficheFromCRMSoap_BindingStub _stub = new AfficheFromCRMSoap_BindingStub(new java.net.URL(AfficheFromCRMSoap_address), this);
                _stub.setPortName(getAfficheFromCRMSoapWSDDServiceName());
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
        if ("AfficheFromCRMSoap12".equals(inputPortName)) {
            return getAfficheFromCRMSoap12();
        }
        else if ("AfficheFromCRMSoap".equals(inputPortName)) {
            return getAfficheFromCRMSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "AfficheFromCRM");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "AfficheFromCRMSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "AfficheFromCRMSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("AfficheFromCRMSoap12".equals(portName)) {
            setAfficheFromCRMSoap12EndpointAddress(address);
        }
        else 
if ("AfficheFromCRMSoap".equals(portName)) {
            setAfficheFromCRMSoapEndpointAddress(address);
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
