/**
 * SysInfoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package cn.com.wz.vehcert;

public class SysInfoServiceLocator extends org.apache.axis.client.Service implements SysInfoService {

    public SysInfoServiceLocator() {
    }


    public SysInfoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SysInfoServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IPrintInfo
    private String BasicHttpBinding_IPrintInfo_address = "http://localhost:10000/print";

    public String getBasicHttpBinding_IPrintInfoAddress() {
        return BasicHttpBinding_IPrintInfo_address;
    }

    // The WSDD service name defaults to the port name.
    private String BasicHttpBinding_IPrintInfoWSDDServiceName = "BasicHttpBinding_IPrintInfo";

    public String getBasicHttpBinding_IPrintInfoWSDDServiceName() {
        return BasicHttpBinding_IPrintInfoWSDDServiceName;
    }

    public void setBasicHttpBinding_IPrintInfoWSDDServiceName(String name) {
        BasicHttpBinding_IPrintInfoWSDDServiceName = name;
    }

    public IPrintInfo getBasicHttpBinding_IPrintInfo() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IPrintInfo_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IPrintInfo(endpoint);
    }

    public IPrintInfo getBasicHttpBinding_IPrintInfo(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            BasicHttpBinding_IPrintInfoStub _stub = new BasicHttpBinding_IPrintInfoStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IPrintInfoWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IPrintInfoEndpointAddress(String address) {
        BasicHttpBinding_IPrintInfo_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (IPrintInfo.class.isAssignableFrom(serviceEndpointInterface)) {
                BasicHttpBinding_IPrintInfoStub _stub = new BasicHttpBinding_IPrintInfoStub(new java.net.URL(BasicHttpBinding_IPrintInfo_address), this);
                _stub.setPortName(getBasicHttpBinding_IPrintInfoWSDDServiceName());
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
        if ("BasicHttpBinding_IPrintInfo".equals(inputPortName)) {
            return getBasicHttpBinding_IPrintInfo();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "SysInfoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IPrintInfo"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IPrintInfo".equals(portName)) {
            setBasicHttpBinding_IPrintInfoEndpointAddress(address);
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
