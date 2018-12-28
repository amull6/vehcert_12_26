/**
 * WSXxgkVinLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package Enviro;

public class WSXxgkVinLocator extends org.apache.axis.client.Service implements WSXxgkVin {

/**
 * <strong>Vin报送服务<a href="http://www.vecc-mep.org.cn/download/WSXxgkVin.PDF"
 * target="_blank"><font size="3" color="#FF0000">(详细使用说明点此下载)</font></a><br><br></strong>数据来源于<strong>中国环境保护部机动车排污监控中心</strong>
 * <a href="http://www.vecc-mep.org.cn" target="_blank">http://www.vecc-mep.org.cn/</a>
 */

    public WSXxgkVinLocator() {
    }


    public WSXxgkVinLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSXxgkVinLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSXxgkVinSoap12
    private String WSXxgkVinSoap12_address = "http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx";

    public String getWSXxgkVinSoap12Address() {
        return WSXxgkVinSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String WSXxgkVinSoap12WSDDServiceName = "WSXxgkVinSoap12";

    public String getWSXxgkVinSoap12WSDDServiceName() {
        return WSXxgkVinSoap12WSDDServiceName;
    }

    public void setWSXxgkVinSoap12WSDDServiceName(String name) {
        WSXxgkVinSoap12WSDDServiceName = name;
    }

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSXxgkVinSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSXxgkVinSoap12(endpoint);
    }

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            Enviro.WSXxgkVinSoap12Stub _stub = new Enviro.WSXxgkVinSoap12Stub(portAddress, this);
            _stub.setPortName(getWSXxgkVinSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSXxgkVinSoap12EndpointAddress(String address) {
        WSXxgkVinSoap12_address = address;
    }


    // Use to get a proxy class for WSXxgkVinSoap
    private String WSXxgkVinSoap_address = "http://web1.vecc-mep.org.cn/WSXxgkVin/WSXxgkVin.asmx";

    public String getWSXxgkVinSoapAddress() {
        return WSXxgkVinSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String WSXxgkVinSoapWSDDServiceName = "WSXxgkVinSoap";

    public String getWSXxgkVinSoapWSDDServiceName() {
        return WSXxgkVinSoapWSDDServiceName;
    }

    public void setWSXxgkVinSoapWSDDServiceName(String name) {
        WSXxgkVinSoapWSDDServiceName = name;
    }

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSXxgkVinSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSXxgkVinSoap(endpoint);
    }

    public Enviro.WSXxgkVinSoap_PortType getWSXxgkVinSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            Enviro.WSXxgkVinSoap_BindingStub _stub = new Enviro.WSXxgkVinSoap_BindingStub(portAddress, this);
            _stub.setPortName(getWSXxgkVinSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSXxgkVinSoapEndpointAddress(String address) {
        WSXxgkVinSoap_address = address;
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
            if (Enviro.WSXxgkVinSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                Enviro.WSXxgkVinSoap12Stub _stub = new Enviro.WSXxgkVinSoap12Stub(new java.net.URL(WSXxgkVinSoap12_address), this);
                _stub.setPortName(getWSXxgkVinSoap12WSDDServiceName());
                return _stub;
            }
            if (Enviro.WSXxgkVinSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                Enviro.WSXxgkVinSoap_BindingStub _stub = new Enviro.WSXxgkVinSoap_BindingStub(new java.net.URL(WSXxgkVinSoap_address), this);
                _stub.setPortName(getWSXxgkVinSoapWSDDServiceName());
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
        if ("WSXxgkVinSoap12".equals(inputPortName)) {
            return getWSXxgkVinSoap12();
        }
        else if ("WSXxgkVinSoap".equals(inputPortName)) {
            return getWSXxgkVinSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVin");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVinSoap12"));
            ports.add(new javax.xml.namespace.QName("http://web1.vecc-mep.org.cn/WSXxgkVin", "WSXxgkVinSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("WSXxgkVinSoap12".equals(portName)) {
            setWSXxgkVinSoap12EndpointAddress(address);
        }
        else 
if ("WSXxgkVinSoap".equals(portName)) {
            setWSXxgkVinSoapEndpointAddress(address);
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
