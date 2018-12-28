/**
 * WzAfficheSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package dms;

public interface WzAfficheSoap_PortType extends java.rmi.Remote {

    /**
     * 同步公告信息
     */
    public void syncAffiche(AfficheModel[] ams, javax.xml.rpc.holders.BooleanHolder syncAfficheResult, javax.xml.rpc.holders.StringHolder message) throws java.rmi.RemoteException;

    /**
     * 删除公告信息
     */
    public void delete(String[] ids, javax.xml.rpc.holders.BooleanHolder deleteResult, javax.xml.rpc.holders.StringHolder message) throws java.rmi.RemoteException;

    /**
     * 传递合格证信息信息
     */
    public String receiveChange(String a) throws java.rmi.RemoteException;

    /**
     * 创建订单日历
     */
    public String addOrderCalender(String name, String password, int year, int month, int day) throws java.rmi.RemoteException;

    /**
     * 合格证更换信息回传
     */
    public void updateVehicle(String wz_unique, String wz_certificate_code, String wz_vin, String fdjxh, String wz_engineno, String wym, javax.xml.rpc.holders.BooleanHolder updateVehicleResult, javax.xml.rpc.holders.StringHolder message) throws java.rmi.RemoteException;
}
