/**
 * GetDataFromCRMSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public interface GetDataFromCRMSoap_PortType extends java.rmi.Remote {

    /**
     * 业务员信息
     */
    public getDataFromCrm.SystemUserModel[] getClerk(int day) throws java.rmi.RemoteException;

    /**
     * 联系人信息
     */
    public ContactModel[] getContact(int hours) throws java.rmi.RemoteException;

    /**
     * 客户信息
     */
    public AccountModel[] getAccount(int hours) throws java.rmi.RemoteException;

    /**
     * 车辆信息
     */
    public getDataFromCrm.VehicleInfoModel[] getVehicleInfo(int hours) throws java.rmi.RemoteException;

    /**
     * 车辆关键件信息
     */
    public VehicleMainPartModel[] vehicleMainPart(int hours) throws java.rmi.RemoteException;

    /**
     * 同步线索规则
     */
    public getDataFromCrm.LeadsRuleModel[] leadsRule(int hours) throws java.rmi.RemoteException;

    /**
     * 经销商库存查询
     */
    public AfficeStockModel[] getDealerVehicleInfo(String sapcode) throws java.rmi.RemoteException;
}
