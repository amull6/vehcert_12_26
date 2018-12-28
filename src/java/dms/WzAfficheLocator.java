/**
 * WzAfficheLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package dms;

public class WzAfficheLocator extends org.apache.axis.client.Service implements WzAffiche {

    public WzAfficheLocator() {
    }


    public WzAfficheLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WzAfficheLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WzAfficheSoap12
    private String WzAfficheSoap12_address = "http://192.168.1.38/CRMWebAffire/WzAffiche.asmx";

    public String getWzAfficheSoap12Address() {
        return WzAfficheSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String WzAfficheSoap12WSDDServiceName = "WzAfficheSoap12";

    public String getWzAfficheSoap12WSDDServiceName() {
        return WzAfficheSoap12WSDDServiceName;
    }

    public void setWzAfficheSoap12WSDDServiceName(String name) {
        WzAfficheSoap12WSDDServiceName = name;
    }

    public dms.WzAfficheSoap_PortType getWzAfficheSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WzAfficheSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWzAfficheSoap12(endpoint);
    }

    public dms.WzAfficheSoap_PortType getWzAfficheSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            dms.WzAfficheSoap12Stub _stub = new dms.WzAfficheSoap12Stub(portAddress, this);
            _stub.setPortName(getWzAfficheSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWzAfficheSoap12EndpointAddress(String address) {
        WzAfficheSoap12_address = address;
    }


    // Use to get a proxy class for WzAfficheSoap
    private String WzAfficheSoap_address = "http://192.168.1.38/CRMWebAffire/WzAffiche.asmx";

    public String getWzAfficheSoapAddress() {
        return WzAfficheSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String WzAfficheSoapWSDDServiceName = "WzAfficheSoap";

    public String getWzAfficheSoapWSDDServiceName() {
        return WzAfficheSoapWSDDServiceName;
    }

    public void setWzAfficheSoapWSDDServiceName(String name) {
        WzAfficheSoapWSDDServiceName = name;
    }

    public dms.WzAfficheSoap_PortType getWzAfficheSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WzAfficheSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWzAfficheSoap(endpoint);
    }

    public dms.WzAfficheSoap_PortType getWzAfficheSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            dms.WzAfficheSoap_BindingStub _stub = new dms.WzAfficheSoap_BindingStub(portAddress, this);
            _stub.setPortName(getWzAfficheSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWzAfficheSoapEndpointAddress(String address) {
        WzAfficheSoap_address = address;
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
            if (dms.WzAfficheSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                dms.WzAfficheSoap12Stub _stub = new dms.WzAfficheSoap12Stub(new java.net.URL(WzAfficheSoap12_address), this);
                _stub.setPortName(getWzAfficheSoap12WSDDServiceName());
                return _stub;
            }
            if (dms.WzAfficheSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                dms.WzAfficheSoap_BindingStub _stub = new dms.WzAfficheSoap_BindingStub(new java.net.URL(WzAfficheSoap_address), this);
                _stub.setPortName(getWzAfficheSoapWSDDServiceName());
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
        if ("WzAfficheSoap12".equals(inputPortName)) {
            return getWzAfficheSoap12();
        }
        else if ("WzAfficheSoap".equals(inputPortName)) {
            return getWzAfficheSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "WzAffiche");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "WzAfficheSoap12"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "WzAfficheSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("WzAfficheSoap12".equals(portName)) {
            setWzAfficheSoap12EndpointAddress(address);
        }
        else 
if ("WzAfficheSoap".equals(portName)) {
            setWzAfficheSoapEndpointAddress(address);
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
