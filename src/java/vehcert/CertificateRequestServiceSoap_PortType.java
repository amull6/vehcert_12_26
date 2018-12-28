/**
 * CertificateRequestServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package vehcert;

public interface CertificateRequestServiceSoap_PortType extends java.rmi.Remote {
    public String helloWorld() throws java.rmi.RemoteException;
    public String helloWorld_Remote() throws java.rmi.RemoteException;
    public vehcert.QueryResult queryCertificateByWZHGZBH(String wzhgzbh) throws java.rmi.RemoteException;
    public vehcert.QueryResult queryCertificateByDate(int rqlx, String beginTime, String endTime, int pageSize, int pageSite) throws java.rmi.RemoteException;
    public vehcert.OperateResult uploadInsert_Ent(CertificateInfo[] data) throws java.rmi.RemoteException;
    public vehcert.OperateResult uploadOverTime_Ent(CertificateInfo[] data, String memo) throws java.rmi.RemoteException;
    public vehcert.OperateResult uploadUpdate_Ent(CertificateInfo[] data, String memo) throws java.rmi.RemoteException;
    public vehcert.OperateResult uploadDelete_Ent(String[] wzhgzbh, String memo) throws java.rmi.RemoteException;
    public int regHardwareInfo(String hardwareInfo) throws java.rmi.RemoteException;
    public String getHardwareInfoFromServer() throws java.rmi.RemoteException;
}
