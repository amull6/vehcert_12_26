/**
 * InvoicesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package invoice;

public class InvoicesServiceLocator extends org.apache.axis.client.Service implements InvoicesService {

    public InvoicesServiceLocator() {
    }


    public InvoicesServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InvoicesServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IInvoicesService
    private String BasicHttpBinding_IInvoicesService_address = "http://www.autoidc.cn/service/InvoicesService.svc";

    public String getBasicHttpBinding_IInvoicesServiceAddress() {
        return BasicHttpBinding_IInvoicesService_address;
    }

    // The WSDD service name defaults to the port name.
    private String BasicHttpBinding_IInvoicesServiceWSDDServiceName = "BasicHttpBinding_IInvoicesService";

    public String getBasicHttpBinding_IInvoicesServiceWSDDServiceName() {
        return BasicHttpBinding_IInvoicesServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_IInvoicesServiceWSDDServiceName(String name) {
        BasicHttpBinding_IInvoicesServiceWSDDServiceName = name;
    }

    public IInvoicesService getBasicHttpBinding_IInvoicesService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IInvoicesService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IInvoicesService(endpoint);
    }

    public IInvoicesService getBasicHttpBinding_IInvoicesService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            BasicHttpBinding_IInvoicesServiceStub _stub = new BasicHttpBinding_IInvoicesServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IInvoicesServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IInvoicesServiceEndpointAddress(String address) {
        BasicHttpBinding_IInvoicesService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (IInvoicesService.class.isAssignableFrom(serviceEndpointInterface)) {
                BasicHttpBinding_IInvoicesServiceStub _stub = new BasicHttpBinding_IInvoicesServiceStub(new java.net.URL(BasicHttpBinding_IInvoicesService_address), this);
                _stub.setPortName(getBasicHttpBinding_IInvoicesServiceWSDDServiceName());
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
        if ("BasicHttpBinding_IInvoicesService".equals(inputPortName)) {
            return getBasicHttpBinding_IInvoicesService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "InvoicesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IInvoicesService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IInvoicesService".equals(portName)) {
            setBasicHttpBinding_IInvoicesServiceEndpointAddress(address);
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
