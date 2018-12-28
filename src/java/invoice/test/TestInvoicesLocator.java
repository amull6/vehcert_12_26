/**
 * TestInvoicesLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package invoice.test;

public class TestInvoicesLocator extends org.apache.axis.client.Service implements invoice.test.TestInvoices {

    public TestInvoicesLocator() {
    }


    public TestInvoicesLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TestInvoicesLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_ITestInvoices
    private String BasicHttpBinding_ITestInvoices_address = "http://test.autoidc.cn/TestInvoices.svc";

    public String getBasicHttpBinding_ITestInvoicesAddress() {
        return BasicHttpBinding_ITestInvoices_address;
    }

    // The WSDD service name defaults to the port name.
    private String BasicHttpBinding_ITestInvoicesWSDDServiceName = "BasicHttpBinding_ITestInvoices";

    public String getBasicHttpBinding_ITestInvoicesWSDDServiceName() {
        return BasicHttpBinding_ITestInvoicesWSDDServiceName;
    }

    public void setBasicHttpBinding_ITestInvoicesWSDDServiceName(String name) {
        BasicHttpBinding_ITestInvoicesWSDDServiceName = name;
    }

    public invoice.test.ITestInvoices getBasicHttpBinding_ITestInvoices() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_ITestInvoices_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_ITestInvoices(endpoint);
    }

    public invoice.test.ITestInvoices getBasicHttpBinding_ITestInvoices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            invoice.test.BasicHttpBinding_ITestInvoicesStub _stub = new invoice.test.BasicHttpBinding_ITestInvoicesStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_ITestInvoicesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_ITestInvoicesEndpointAddress(String address) {
        BasicHttpBinding_ITestInvoices_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (invoice.test.ITestInvoices.class.isAssignableFrom(serviceEndpointInterface)) {
                invoice.test.BasicHttpBinding_ITestInvoicesStub _stub = new invoice.test.BasicHttpBinding_ITestInvoicesStub(new java.net.URL(BasicHttpBinding_ITestInvoices_address), this);
                _stub.setPortName(getBasicHttpBinding_ITestInvoicesWSDDServiceName());
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
        if ("BasicHttpBinding_ITestInvoices".equals(inputPortName)) {
            return getBasicHttpBinding_ITestInvoices();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "TestInvoices");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_ITestInvoices"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_ITestInvoices".equals(portName)) {
            setBasicHttpBinding_ITestInvoicesEndpointAddress(address);
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
