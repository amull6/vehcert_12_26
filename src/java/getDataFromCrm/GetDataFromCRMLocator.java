/**
 * GetDataFromCRMLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class GetDataFromCRMLocator extends org.apache.axis.client.Service implements GetDataFromCRM {

    public GetDataFromCRMLocator() {
    }


    public GetDataFromCRMLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GetDataFromCRMLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GetDataFromCRMSoap12
    private String GetDataFromCRMSoap12_address = "http://192.168.0.34:8089/GetDataFromCRM.asmx";

    public String getGetDataFromCRMSoap12Address() {
        return GetDataFromCRMSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String GetDataFromCRMSoap12WSDDServiceName = "GetDataFromCRMSoap12";

    public String getGetDataFromCRMSoap12WSDDServiceName() {
        return GetDataFromCRMSoap12WSDDServiceName;
    }

    public void setGetDataFromCRMSoap12WSDDServiceName(String name) {
        GetDataFromCRMSoap12WSDDServiceName = name;
    }

    public getDataFromCrm.GetDataFromCRMSoap_PortType getGetDataFromCRMSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GetDataFromCRMSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGetDataFromCRMSoap12(endpoint);
    }

    public getDataFromCrm.GetDataFromCRMSoap_PortType getGetDataFromCRMSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            getDataFromCrm.GetDataFromCRMSoap12Stub _stub = new getDataFromCrm.GetDataFromCRMSoap12Stub(portAddress, this);
            _stub.setPortName(getGetDataFromCRMSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGetDataFromCRMSoap12EndpointAddress(String address) {
        GetDataFromCRMSoap12_address = address;
    }


    // Use to get a proxy class for GetDataFromCRMSoap
    private String GetDataFromCRMSoap_address = "http://192.168.0.34:8089/GetDataFromCRM.asmx";

    public String getGetDataFromCRMSoapAddress() {
        return GetDataFromCRMSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String GetDataFromCRMSoapWSDDServiceName = "GetDataFromCRMSoap";

    public String getGetDataFromCRMSoapWSDDServiceName() {
        return GetDataFromCRMSoapWSDDServiceName;
    }

    public void setGetDataFromCRMSoapWSDDServiceName(String name) {
        GetDataFromCRMSoapWSDDServiceName = name;
    }

    public getDataFromCrm.GetDataFromCRMSoap_PortType getGetDataFromCRMSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GetDataFromCRMSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGetDataFromCRMSoap(endpoint);
    }

    public getDataFromCrm.GetDataFromCRMSoap_PortType getGetDataFromCRMSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            getDataFromCrm.GetDataFromCRMSoap_BindingStub _stub = new getDataFromCrm.GetDataFromCRMSoap_BindingStub(portAddress, this);
            _stub.setPortName(getGetDataFromCRMSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGetDataFromCRMSoapEndpointAddress(String address) {
        GetDataFromCRMSoap_address = address;
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
            if (getDataFromCrm.GetDataFromCRMSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                getDataFromCrm.GetDataFromCRMSoap12Stub _stub = new getDataFromCrm.GetDataFromCRMSoap12Stub(new java.net.URL(GetDataFromCRMSoap12_address), this);
                _stub.setPortName(getGetDataFromCRMSoap12WSDDServiceName());
                return _stub;
            }
            if (getDataFromCrm.GetDataFromCRMSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                getDataFromCrm.GetDataFromCRMSoap_BindingStub _stub = new getDataFromCrm.GetDataFromCRMSoap_BindingStub(new java.net.URL(GetDataFromCRMSoap_address), this);
                _stub.setPortName(getGetDataFromCRMSoapWSDDServiceName());
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
        if ("GetDataFromCRMSoap12".equals(inputPortName)) {
            return getGetDataFromCRMSoap12();
        }
        else if ("GetDataFromCRMSoap".equals(inputPortName)) {
            return getGetDataFromCRMSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "GetDataFromCRM");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "GetDataFromCRMSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "GetDataFromCRMSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("GetDataFromCRMSoap12".equals(portName)) {
            setGetDataFromCRMSoap12EndpointAddress(address);
        }
        else 
if ("GetDataFromCRMSoap".equals(portName)) {
            setGetDataFromCRMSoapEndpointAddress(address);
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
