/**
 * AfficheFromCRMSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package newDms;

public interface AfficheFromCRMSoap_PortType extends java.rmi.Remote {

    /**
     * 同步公告信息
     */
    public Result syncAffiche(AfficheModel[] ams) throws java.rmi.RemoteException;

    /**
     * 删除公告信息
     */
    public Result delete(String[] ids) throws java.rmi.RemoteException;

    /**
     * 合格证更换信息回传
     */
    public Result updateVehicle(String wz_unique, String wz_certificate_code, String wz_vin, String fdjxh, String wz_engineno, String wym, String sapcode) throws java.rmi.RemoteException;
}
