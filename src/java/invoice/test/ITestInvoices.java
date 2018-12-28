/**
 * ITestInvoices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package invoice.test;

import invoice.InvoiceFeedBack;

public interface ITestInvoices extends java.rmi.Remote {
    public String helloWorld() throws java.rmi.RemoteException;
    public InvoiceFeedBack uploadInvoices(String userName, String password, byte[] fileBytes, String key) throws java.rmi.RemoteException;
    public InvoiceFeedBack relateInvoices(String userName, String password, InvoiceFeedBack feedBack, String key) throws java.rmi.RemoteException;
}
