/**
 * VehicleInfoModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class VehicleInfoModel  extends CommonModel  implements java.io.Serializable {
    private String VIN;

    private String vehicleCode;

    private String oldVIN;

    private String salesDealerCode;

    private String salesDealerName;

    private String revertDealerCode;

    private String revertDealerName;

    private String productCode;

    private String productName;

    private String vehicleLicensePlate;

    private String annoucementNumber;

    private String engineManufacture;

    private String engineModel;

    private String gearModel;

    private String engineSerialNumber;

    private String outOfFactoryDate;

    private String salesDate;

    private String isWorkOff;

    private String workingHours;

    private String mileage;

    private String capacity;

    private String firstStoppageMileage;

    private String lastMaintenanceWorkingHours;

    private String lastMaintenanceCapacity;

    private String lastMaintenanceMileage;

    private String lastMaintenanceTime;

    private String preSaleMaxTime;

    private String preSaleTime;

    private String salesInvoiceNumber;

    private String invoiceDate;

    private String CRMID;

    private String deleFlag;

    private String salebuname;

    private String orgname;

    public VehicleInfoModel() {
    }

    public VehicleInfoModel(
           String field1,
           String field2,
           String field3,
           String field4,
           String field5,
           String VIN,
           String vehicleCode,
           String oldVIN,
           String salesDealerCode,
           String salesDealerName,
           String revertDealerCode,
           String revertDealerName,
           String productCode,
           String productName,
           String vehicleLicensePlate,
           String annoucementNumber,
           String engineManufacture,
           String engineModel,
           String gearModel,
           String engineSerialNumber,
           String outOfFactoryDate,
           String salesDate,
           String isWorkOff,
           String workingHours,
           String mileage,
           String capacity,
           String firstStoppageMileage,
           String lastMaintenanceWorkingHours,
           String lastMaintenanceCapacity,
           String lastMaintenanceMileage,
           String lastMaintenanceTime,
           String preSaleMaxTime,
           String preSaleTime,
           String salesInvoiceNumber,
           String invoiceDate,
           String CRMID,
           String deleFlag,
           String salebuname,
           String orgname) {
        super(
            field1,
            field2,
            field3,
            field4,
            field5);
        this.VIN = VIN;
        this.vehicleCode = vehicleCode;
        this.oldVIN = oldVIN;
        this.salesDealerCode = salesDealerCode;
        this.salesDealerName = salesDealerName;
        this.revertDealerCode = revertDealerCode;
        this.revertDealerName = revertDealerName;
        this.productCode = productCode;
        this.productName = productName;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.annoucementNumber = annoucementNumber;
        this.engineManufacture = engineManufacture;
        this.engineModel = engineModel;
        this.gearModel = gearModel;
        this.engineSerialNumber = engineSerialNumber;
        this.outOfFactoryDate = outOfFactoryDate;
        this.salesDate = salesDate;
        this.isWorkOff = isWorkOff;
        this.workingHours = workingHours;
        this.mileage = mileage;
        this.capacity = capacity;
        this.firstStoppageMileage = firstStoppageMileage;
        this.lastMaintenanceWorkingHours = lastMaintenanceWorkingHours;
        this.lastMaintenanceCapacity = lastMaintenanceCapacity;
        this.lastMaintenanceMileage = lastMaintenanceMileage;
        this.lastMaintenanceTime = lastMaintenanceTime;
        this.preSaleMaxTime = preSaleMaxTime;
        this.preSaleTime = preSaleTime;
        this.salesInvoiceNumber = salesInvoiceNumber;
        this.invoiceDate = invoiceDate;
        this.CRMID = CRMID;
        this.deleFlag = deleFlag;
        this.salebuname = salebuname;
        this.orgname = orgname;
    }


    /**
     * Gets the VIN value for this VehicleInfoModel.
     * 
     * @return VIN
     */
    public String getVIN() {
        return VIN;
    }


    /**
     * Sets the VIN value for this VehicleInfoModel.
     * 
     * @param VIN
     */
    public void setVIN(String VIN) {
        this.VIN = VIN;
    }


    /**
     * Gets the vehicleCode value for this VehicleInfoModel.
     * 
     * @return vehicleCode
     */
    public String getVehicleCode() {
        return vehicleCode;
    }


    /**
     * Sets the vehicleCode value for this VehicleInfoModel.
     * 
     * @param vehicleCode
     */
    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }


    /**
     * Gets the oldVIN value for this VehicleInfoModel.
     * 
     * @return oldVIN
     */
    public String getOldVIN() {
        return oldVIN;
    }


    /**
     * Sets the oldVIN value for this VehicleInfoModel.
     * 
     * @param oldVIN
     */
    public void setOldVIN(String oldVIN) {
        this.oldVIN = oldVIN;
    }


    /**
     * Gets the salesDealerCode value for this VehicleInfoModel.
     * 
     * @return salesDealerCode
     */
    public String getSalesDealerCode() {
        return salesDealerCode;
    }


    /**
     * Sets the salesDealerCode value for this VehicleInfoModel.
     * 
     * @param salesDealerCode
     */
    public void setSalesDealerCode(String salesDealerCode) {
        this.salesDealerCode = salesDealerCode;
    }


    /**
     * Gets the salesDealerName value for this VehicleInfoModel.
     * 
     * @return salesDealerName
     */
    public String getSalesDealerName() {
        return salesDealerName;
    }


    /**
     * Sets the salesDealerName value for this VehicleInfoModel.
     * 
     * @param salesDealerName
     */
    public void setSalesDealerName(String salesDealerName) {
        this.salesDealerName = salesDealerName;
    }


    /**
     * Gets the revertDealerCode value for this VehicleInfoModel.
     * 
     * @return revertDealerCode
     */
    public String getRevertDealerCode() {
        return revertDealerCode;
    }


    /**
     * Sets the revertDealerCode value for this VehicleInfoModel.
     * 
     * @param revertDealerCode
     */
    public void setRevertDealerCode(String revertDealerCode) {
        this.revertDealerCode = revertDealerCode;
    }


    /**
     * Gets the revertDealerName value for this VehicleInfoModel.
     * 
     * @return revertDealerName
     */
    public String getRevertDealerName() {
        return revertDealerName;
    }


    /**
     * Sets the revertDealerName value for this VehicleInfoModel.
     * 
     * @param revertDealerName
     */
    public void setRevertDealerName(String revertDealerName) {
        this.revertDealerName = revertDealerName;
    }


    /**
     * Gets the productCode value for this VehicleInfoModel.
     * 
     * @return productCode
     */
    public String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this VehicleInfoModel.
     * 
     * @param productCode
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the productName value for this VehicleInfoModel.
     * 
     * @return productName
     */
    public String getProductName() {
        return productName;
    }


    /**
     * Sets the productName value for this VehicleInfoModel.
     * 
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }


    /**
     * Gets the vehicleLicensePlate value for this VehicleInfoModel.
     * 
     * @return vehicleLicensePlate
     */
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }


    /**
     * Sets the vehicleLicensePlate value for this VehicleInfoModel.
     * 
     * @param vehicleLicensePlate
     */
    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }


    /**
     * Gets the annoucementNumber value for this VehicleInfoModel.
     * 
     * @return annoucementNumber
     */
    public String getAnnoucementNumber() {
        return annoucementNumber;
    }


    /**
     * Sets the annoucementNumber value for this VehicleInfoModel.
     * 
     * @param annoucementNumber
     */
    public void setAnnoucementNumber(String annoucementNumber) {
        this.annoucementNumber = annoucementNumber;
    }


    /**
     * Gets the engineManufacture value for this VehicleInfoModel.
     * 
     * @return engineManufacture
     */
    public String getEngineManufacture() {
        return engineManufacture;
    }


    /**
     * Sets the engineManufacture value for this VehicleInfoModel.
     * 
     * @param engineManufacture
     */
    public void setEngineManufacture(String engineManufacture) {
        this.engineManufacture = engineManufacture;
    }


    /**
     * Gets the engineModel value for this VehicleInfoModel.
     * 
     * @return engineModel
     */
    public String getEngineModel() {
        return engineModel;
    }


    /**
     * Sets the engineModel value for this VehicleInfoModel.
     * 
     * @param engineModel
     */
    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }


    /**
     * Gets the gearModel value for this VehicleInfoModel.
     * 
     * @return gearModel
     */
    public String getGearModel() {
        return gearModel;
    }


    /**
     * Sets the gearModel value for this VehicleInfoModel.
     * 
     * @param gearModel
     */
    public void setGearModel(String gearModel) {
        this.gearModel = gearModel;
    }


    /**
     * Gets the engineSerialNumber value for this VehicleInfoModel.
     * 
     * @return engineSerialNumber
     */
    public String getEngineSerialNumber() {
        return engineSerialNumber;
    }


    /**
     * Sets the engineSerialNumber value for this VehicleInfoModel.
     * 
     * @param engineSerialNumber
     */
    public void setEngineSerialNumber(String engineSerialNumber) {
        this.engineSerialNumber = engineSerialNumber;
    }


    /**
     * Gets the outOfFactoryDate value for this VehicleInfoModel.
     * 
     * @return outOfFactoryDate
     */
    public String getOutOfFactoryDate() {
        return outOfFactoryDate;
    }


    /**
     * Sets the outOfFactoryDate value for this VehicleInfoModel.
     * 
     * @param outOfFactoryDate
     */
    public void setOutOfFactoryDate(String outOfFactoryDate) {
        this.outOfFactoryDate = outOfFactoryDate;
    }


    /**
     * Gets the salesDate value for this VehicleInfoModel.
     * 
     * @return salesDate
     */
    public String getSalesDate() {
        return salesDate;
    }


    /**
     * Sets the salesDate value for this VehicleInfoModel.
     * 
     * @param salesDate
     */
    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }


    /**
     * Gets the isWorkOff value for this VehicleInfoModel.
     * 
     * @return isWorkOff
     */
    public String getIsWorkOff() {
        return isWorkOff;
    }


    /**
     * Sets the isWorkOff value for this VehicleInfoModel.
     * 
     * @param isWorkOff
     */
    public void setIsWorkOff(String isWorkOff) {
        this.isWorkOff = isWorkOff;
    }


    /**
     * Gets the workingHours value for this VehicleInfoModel.
     * 
     * @return workingHours
     */
    public String getWorkingHours() {
        return workingHours;
    }


    /**
     * Sets the workingHours value for this VehicleInfoModel.
     * 
     * @param workingHours
     */
    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }


    /**
     * Gets the mileage value for this VehicleInfoModel.
     * 
     * @return mileage
     */
    public String getMileage() {
        return mileage;
    }


    /**
     * Sets the mileage value for this VehicleInfoModel.
     * 
     * @param mileage
     */
    public void setMileage(String mileage) {
        this.mileage = mileage;
    }


    /**
     * Gets the capacity value for this VehicleInfoModel.
     * 
     * @return capacity
     */
    public String getCapacity() {
        return capacity;
    }


    /**
     * Sets the capacity value for this VehicleInfoModel.
     * 
     * @param capacity
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }


    /**
     * Gets the firstStoppageMileage value for this VehicleInfoModel.
     * 
     * @return firstStoppageMileage
     */
    public String getFirstStoppageMileage() {
        return firstStoppageMileage;
    }


    /**
     * Sets the firstStoppageMileage value for this VehicleInfoModel.
     * 
     * @param firstStoppageMileage
     */
    public void setFirstStoppageMileage(String firstStoppageMileage) {
        this.firstStoppageMileage = firstStoppageMileage;
    }


    /**
     * Gets the lastMaintenanceWorkingHours value for this VehicleInfoModel.
     * 
     * @return lastMaintenanceWorkingHours
     */
    public String getLastMaintenanceWorkingHours() {
        return lastMaintenanceWorkingHours;
    }


    /**
     * Sets the lastMaintenanceWorkingHours value for this VehicleInfoModel.
     * 
     * @param lastMaintenanceWorkingHours
     */
    public void setLastMaintenanceWorkingHours(String lastMaintenanceWorkingHours) {
        this.lastMaintenanceWorkingHours = lastMaintenanceWorkingHours;
    }


    /**
     * Gets the lastMaintenanceCapacity value for this VehicleInfoModel.
     * 
     * @return lastMaintenanceCapacity
     */
    public String getLastMaintenanceCapacity() {
        return lastMaintenanceCapacity;
    }


    /**
     * Sets the lastMaintenanceCapacity value for this VehicleInfoModel.
     * 
     * @param lastMaintenanceCapacity
     */
    public void setLastMaintenanceCapacity(String lastMaintenanceCapacity) {
        this.lastMaintenanceCapacity = lastMaintenanceCapacity;
    }


    /**
     * Gets the lastMaintenanceMileage value for this VehicleInfoModel.
     * 
     * @return lastMaintenanceMileage
     */
    public String getLastMaintenanceMileage() {
        return lastMaintenanceMileage;
    }


    /**
     * Sets the lastMaintenanceMileage value for this VehicleInfoModel.
     * 
     * @param lastMaintenanceMileage
     */
    public void setLastMaintenanceMileage(String lastMaintenanceMileage) {
        this.lastMaintenanceMileage = lastMaintenanceMileage;
    }


    /**
     * Gets the lastMaintenanceTime value for this VehicleInfoModel.
     * 
     * @return lastMaintenanceTime
     */
    public String getLastMaintenanceTime() {
        return lastMaintenanceTime;
    }


    /**
     * Sets the lastMaintenanceTime value for this VehicleInfoModel.
     * 
     * @param lastMaintenanceTime
     */
    public void setLastMaintenanceTime(String lastMaintenanceTime) {
        this.lastMaintenanceTime = lastMaintenanceTime;
    }


    /**
     * Gets the preSaleMaxTime value for this VehicleInfoModel.
     * 
     * @return preSaleMaxTime
     */
    public String getPreSaleMaxTime() {
        return preSaleMaxTime;
    }


    /**
     * Sets the preSaleMaxTime value for this VehicleInfoModel.
     * 
     * @param preSaleMaxTime
     */
    public void setPreSaleMaxTime(String preSaleMaxTime) {
        this.preSaleMaxTime = preSaleMaxTime;
    }


    /**
     * Gets the preSaleTime value for this VehicleInfoModel.
     * 
     * @return preSaleTime
     */
    public String getPreSaleTime() {
        return preSaleTime;
    }


    /**
     * Sets the preSaleTime value for this VehicleInfoModel.
     * 
     * @param preSaleTime
     */
    public void setPreSaleTime(String preSaleTime) {
        this.preSaleTime = preSaleTime;
    }


    /**
     * Gets the salesInvoiceNumber value for this VehicleInfoModel.
     * 
     * @return salesInvoiceNumber
     */
    public String getSalesInvoiceNumber() {
        return salesInvoiceNumber;
    }


    /**
     * Sets the salesInvoiceNumber value for this VehicleInfoModel.
     * 
     * @param salesInvoiceNumber
     */
    public void setSalesInvoiceNumber(String salesInvoiceNumber) {
        this.salesInvoiceNumber = salesInvoiceNumber;
    }


    /**
     * Gets the invoiceDate value for this VehicleInfoModel.
     * 
     * @return invoiceDate
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }


    /**
     * Sets the invoiceDate value for this VehicleInfoModel.
     * 
     * @param invoiceDate
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }


    /**
     * Gets the CRMID value for this VehicleInfoModel.
     * 
     * @return CRMID
     */
    public String getCRMID() {
        return CRMID;
    }


    /**
     * Sets the CRMID value for this VehicleInfoModel.
     * 
     * @param CRMID
     */
    public void setCRMID(String CRMID) {
        this.CRMID = CRMID;
    }


    /**
     * Gets the deleFlag value for this VehicleInfoModel.
     * 
     * @return deleFlag
     */
    public String getDeleFlag() {
        return deleFlag;
    }


    /**
     * Sets the deleFlag value for this VehicleInfoModel.
     * 
     * @param deleFlag
     */
    public void setDeleFlag(String deleFlag) {
        this.deleFlag = deleFlag;
    }


    /**
     * Gets the salebuname value for this VehicleInfoModel.
     * 
     * @return salebuname
     */
    public String getSalebuname() {
        return salebuname;
    }


    /**
     * Sets the salebuname value for this VehicleInfoModel.
     * 
     * @param salebuname
     */
    public void setSalebuname(String salebuname) {
        this.salebuname = salebuname;
    }


    /**
     * Gets the orgname value for this VehicleInfoModel.
     * 
     * @return orgname
     */
    public String getOrgname() {
        return orgname;
    }


    /**
     * Sets the orgname value for this VehicleInfoModel.
     * 
     * @param orgname
     */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof VehicleInfoModel)) return false;
        VehicleInfoModel other = (VehicleInfoModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.VIN==null && other.getVIN()==null) || 
             (this.VIN!=null &&
              this.VIN.equals(other.getVIN()))) &&
            ((this.vehicleCode==null && other.getVehicleCode()==null) || 
             (this.vehicleCode!=null &&
              this.vehicleCode.equals(other.getVehicleCode()))) &&
            ((this.oldVIN==null && other.getOldVIN()==null) || 
             (this.oldVIN!=null &&
              this.oldVIN.equals(other.getOldVIN()))) &&
            ((this.salesDealerCode==null && other.getSalesDealerCode()==null) || 
             (this.salesDealerCode!=null &&
              this.salesDealerCode.equals(other.getSalesDealerCode()))) &&
            ((this.salesDealerName==null && other.getSalesDealerName()==null) || 
             (this.salesDealerName!=null &&
              this.salesDealerName.equals(other.getSalesDealerName()))) &&
            ((this.revertDealerCode==null && other.getRevertDealerCode()==null) || 
             (this.revertDealerCode!=null &&
              this.revertDealerCode.equals(other.getRevertDealerCode()))) &&
            ((this.revertDealerName==null && other.getRevertDealerName()==null) || 
             (this.revertDealerName!=null &&
              this.revertDealerName.equals(other.getRevertDealerName()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.productName==null && other.getProductName()==null) || 
             (this.productName!=null &&
              this.productName.equals(other.getProductName()))) &&
            ((this.vehicleLicensePlate==null && other.getVehicleLicensePlate()==null) || 
             (this.vehicleLicensePlate!=null &&
              this.vehicleLicensePlate.equals(other.getVehicleLicensePlate()))) &&
            ((this.annoucementNumber==null && other.getAnnoucementNumber()==null) || 
             (this.annoucementNumber!=null &&
              this.annoucementNumber.equals(other.getAnnoucementNumber()))) &&
            ((this.engineManufacture==null && other.getEngineManufacture()==null) || 
             (this.engineManufacture!=null &&
              this.engineManufacture.equals(other.getEngineManufacture()))) &&
            ((this.engineModel==null && other.getEngineModel()==null) || 
             (this.engineModel!=null &&
              this.engineModel.equals(other.getEngineModel()))) &&
            ((this.gearModel==null && other.getGearModel()==null) || 
             (this.gearModel!=null &&
              this.gearModel.equals(other.getGearModel()))) &&
            ((this.engineSerialNumber==null && other.getEngineSerialNumber()==null) || 
             (this.engineSerialNumber!=null &&
              this.engineSerialNumber.equals(other.getEngineSerialNumber()))) &&
            ((this.outOfFactoryDate==null && other.getOutOfFactoryDate()==null) || 
             (this.outOfFactoryDate!=null &&
              this.outOfFactoryDate.equals(other.getOutOfFactoryDate()))) &&
            ((this.salesDate==null && other.getSalesDate()==null) || 
             (this.salesDate!=null &&
              this.salesDate.equals(other.getSalesDate()))) &&
            ((this.isWorkOff==null && other.getIsWorkOff()==null) || 
             (this.isWorkOff!=null &&
              this.isWorkOff.equals(other.getIsWorkOff()))) &&
            ((this.workingHours==null && other.getWorkingHours()==null) || 
             (this.workingHours!=null &&
              this.workingHours.equals(other.getWorkingHours()))) &&
            ((this.mileage==null && other.getMileage()==null) || 
             (this.mileage!=null &&
              this.mileage.equals(other.getMileage()))) &&
            ((this.capacity==null && other.getCapacity()==null) || 
             (this.capacity!=null &&
              this.capacity.equals(other.getCapacity()))) &&
            ((this.firstStoppageMileage==null && other.getFirstStoppageMileage()==null) || 
             (this.firstStoppageMileage!=null &&
              this.firstStoppageMileage.equals(other.getFirstStoppageMileage()))) &&
            ((this.lastMaintenanceWorkingHours==null && other.getLastMaintenanceWorkingHours()==null) || 
             (this.lastMaintenanceWorkingHours!=null &&
              this.lastMaintenanceWorkingHours.equals(other.getLastMaintenanceWorkingHours()))) &&
            ((this.lastMaintenanceCapacity==null && other.getLastMaintenanceCapacity()==null) || 
             (this.lastMaintenanceCapacity!=null &&
              this.lastMaintenanceCapacity.equals(other.getLastMaintenanceCapacity()))) &&
            ((this.lastMaintenanceMileage==null && other.getLastMaintenanceMileage()==null) || 
             (this.lastMaintenanceMileage!=null &&
              this.lastMaintenanceMileage.equals(other.getLastMaintenanceMileage()))) &&
            ((this.lastMaintenanceTime==null && other.getLastMaintenanceTime()==null) || 
             (this.lastMaintenanceTime!=null &&
              this.lastMaintenanceTime.equals(other.getLastMaintenanceTime()))) &&
            ((this.preSaleMaxTime==null && other.getPreSaleMaxTime()==null) || 
             (this.preSaleMaxTime!=null &&
              this.preSaleMaxTime.equals(other.getPreSaleMaxTime()))) &&
            ((this.preSaleTime==null && other.getPreSaleTime()==null) || 
             (this.preSaleTime!=null &&
              this.preSaleTime.equals(other.getPreSaleTime()))) &&
            ((this.salesInvoiceNumber==null && other.getSalesInvoiceNumber()==null) || 
             (this.salesInvoiceNumber!=null &&
              this.salesInvoiceNumber.equals(other.getSalesInvoiceNumber()))) &&
            ((this.invoiceDate==null && other.getInvoiceDate()==null) || 
             (this.invoiceDate!=null &&
              this.invoiceDate.equals(other.getInvoiceDate()))) &&
            ((this.CRMID==null && other.getCRMID()==null) || 
             (this.CRMID!=null &&
              this.CRMID.equals(other.getCRMID()))) &&
            ((this.deleFlag==null && other.getDeleFlag()==null) || 
             (this.deleFlag!=null &&
              this.deleFlag.equals(other.getDeleFlag()))) &&
            ((this.salebuname==null && other.getSalebuname()==null) || 
             (this.salebuname!=null &&
              this.salebuname.equals(other.getSalebuname()))) &&
            ((this.orgname==null && other.getOrgname()==null) || 
             (this.orgname!=null &&
              this.orgname.equals(other.getOrgname())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getVIN() != null) {
            _hashCode += getVIN().hashCode();
        }
        if (getVehicleCode() != null) {
            _hashCode += getVehicleCode().hashCode();
        }
        if (getOldVIN() != null) {
            _hashCode += getOldVIN().hashCode();
        }
        if (getSalesDealerCode() != null) {
            _hashCode += getSalesDealerCode().hashCode();
        }
        if (getSalesDealerName() != null) {
            _hashCode += getSalesDealerName().hashCode();
        }
        if (getRevertDealerCode() != null) {
            _hashCode += getRevertDealerCode().hashCode();
        }
        if (getRevertDealerName() != null) {
            _hashCode += getRevertDealerName().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getProductName() != null) {
            _hashCode += getProductName().hashCode();
        }
        if (getVehicleLicensePlate() != null) {
            _hashCode += getVehicleLicensePlate().hashCode();
        }
        if (getAnnoucementNumber() != null) {
            _hashCode += getAnnoucementNumber().hashCode();
        }
        if (getEngineManufacture() != null) {
            _hashCode += getEngineManufacture().hashCode();
        }
        if (getEngineModel() != null) {
            _hashCode += getEngineModel().hashCode();
        }
        if (getGearModel() != null) {
            _hashCode += getGearModel().hashCode();
        }
        if (getEngineSerialNumber() != null) {
            _hashCode += getEngineSerialNumber().hashCode();
        }
        if (getOutOfFactoryDate() != null) {
            _hashCode += getOutOfFactoryDate().hashCode();
        }
        if (getSalesDate() != null) {
            _hashCode += getSalesDate().hashCode();
        }
        if (getIsWorkOff() != null) {
            _hashCode += getIsWorkOff().hashCode();
        }
        if (getWorkingHours() != null) {
            _hashCode += getWorkingHours().hashCode();
        }
        if (getMileage() != null) {
            _hashCode += getMileage().hashCode();
        }
        if (getCapacity() != null) {
            _hashCode += getCapacity().hashCode();
        }
        if (getFirstStoppageMileage() != null) {
            _hashCode += getFirstStoppageMileage().hashCode();
        }
        if (getLastMaintenanceWorkingHours() != null) {
            _hashCode += getLastMaintenanceWorkingHours().hashCode();
        }
        if (getLastMaintenanceCapacity() != null) {
            _hashCode += getLastMaintenanceCapacity().hashCode();
        }
        if (getLastMaintenanceMileage() != null) {
            _hashCode += getLastMaintenanceMileage().hashCode();
        }
        if (getLastMaintenanceTime() != null) {
            _hashCode += getLastMaintenanceTime().hashCode();
        }
        if (getPreSaleMaxTime() != null) {
            _hashCode += getPreSaleMaxTime().hashCode();
        }
        if (getPreSaleTime() != null) {
            _hashCode += getPreSaleTime().hashCode();
        }
        if (getSalesInvoiceNumber() != null) {
            _hashCode += getSalesInvoiceNumber().hashCode();
        }
        if (getInvoiceDate() != null) {
            _hashCode += getInvoiceDate().hashCode();
        }
        if (getCRMID() != null) {
            _hashCode += getCRMID().hashCode();
        }
        if (getDeleFlag() != null) {
            _hashCode += getDeleFlag().hashCode();
        }
        if (getSalebuname() != null) {
            _hashCode += getSalebuname().hashCode();
        }
        if (getOrgname() != null) {
            _hashCode += getOrgname().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VehicleInfoModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "VehicleInfoModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VIN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "VIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "VehicleCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldVIN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "OldVIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesDealerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SalesDealerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesDealerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SalesDealerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revertDealerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RevertDealerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("revertDealerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RevertDealerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ProductName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleLicensePlate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "VehicleLicensePlate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoucementNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "AnnoucementNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("engineManufacture");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "EngineManufacture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("engineModel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "EngineModel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gearModel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GearModel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("engineSerialNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "EngineSerialNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outOfFactoryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "OutOfFactoryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SalesDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isWorkOff");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IsWorkOff"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workingHours");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "WorkingHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mileage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Mileage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("capacity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Capacity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstStoppageMileage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "FirstStoppageMileage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastMaintenanceWorkingHours");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LastMaintenanceWorkingHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastMaintenanceCapacity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LastMaintenanceCapacity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastMaintenanceMileage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LastMaintenanceMileage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastMaintenanceTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LastMaintenanceTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preSaleMaxTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PreSaleMaxTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preSaleTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PreSaleTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salesInvoiceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SalesInvoiceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invoiceDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CRMID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CRMID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DeleFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salebuname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "salebuname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "orgname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
