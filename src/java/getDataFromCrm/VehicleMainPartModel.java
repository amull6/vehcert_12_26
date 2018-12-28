/**
 * VehicleMainPartModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class VehicleMainPartModel  extends getDataFromCrm.CommonModel  implements java.io.Serializable {
    private String vehicleCode;

    private String partName;

    private String providerName;

    private String providerCode;

    private String partDescription;

    public VehicleMainPartModel() {
    }

    public VehicleMainPartModel(
           String field1,
           String field2,
           String field3,
           String field4,
           String field5,
           String vehicleCode,
           String partName,
           String providerName,
           String providerCode,
           String partDescription) {
        super(
            field1,
            field2,
            field3,
            field4,
            field5);
        this.vehicleCode = vehicleCode;
        this.partName = partName;
        this.providerName = providerName;
        this.providerCode = providerCode;
        this.partDescription = partDescription;
    }


    /**
     * Gets the vehicleCode value for this VehicleMainPartModel.
     * 
     * @return vehicleCode
     */
    public String getVehicleCode() {
        return vehicleCode;
    }


    /**
     * Sets the vehicleCode value for this VehicleMainPartModel.
     * 
     * @param vehicleCode
     */
    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }


    /**
     * Gets the partName value for this VehicleMainPartModel.
     * 
     * @return partName
     */
    public String getPartName() {
        return partName;
    }


    /**
     * Sets the partName value for this VehicleMainPartModel.
     * 
     * @param partName
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }


    /**
     * Gets the providerName value for this VehicleMainPartModel.
     * 
     * @return providerName
     */
    public String getProviderName() {
        return providerName;
    }


    /**
     * Sets the providerName value for this VehicleMainPartModel.
     * 
     * @param providerName
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }


    /**
     * Gets the providerCode value for this VehicleMainPartModel.
     * 
     * @return providerCode
     */
    public String getProviderCode() {
        return providerCode;
    }


    /**
     * Sets the providerCode value for this VehicleMainPartModel.
     * 
     * @param providerCode
     */
    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }


    /**
     * Gets the partDescription value for this VehicleMainPartModel.
     * 
     * @return partDescription
     */
    public String getPartDescription() {
        return partDescription;
    }


    /**
     * Sets the partDescription value for this VehicleMainPartModel.
     * 
     * @param partDescription
     */
    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof VehicleMainPartModel)) return false;
        VehicleMainPartModel other = (VehicleMainPartModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.vehicleCode==null && other.getVehicleCode()==null) || 
             (this.vehicleCode!=null &&
              this.vehicleCode.equals(other.getVehicleCode()))) &&
            ((this.partName==null && other.getPartName()==null) || 
             (this.partName!=null &&
              this.partName.equals(other.getPartName()))) &&
            ((this.providerName==null && other.getProviderName()==null) || 
             (this.providerName!=null &&
              this.providerName.equals(other.getProviderName()))) &&
            ((this.providerCode==null && other.getProviderCode()==null) || 
             (this.providerCode!=null &&
              this.providerCode.equals(other.getProviderCode()))) &&
            ((this.partDescription==null && other.getPartDescription()==null) || 
             (this.partDescription!=null &&
              this.partDescription.equals(other.getPartDescription())));
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
        if (getVehicleCode() != null) {
            _hashCode += getVehicleCode().hashCode();
        }
        if (getPartName() != null) {
            _hashCode += getPartName().hashCode();
        }
        if (getProviderName() != null) {
            _hashCode += getProviderName().hashCode();
        }
        if (getProviderCode() != null) {
            _hashCode += getProviderCode().hashCode();
        }
        if (getPartDescription() != null) {
            _hashCode += getPartDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VehicleMainPartModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "VehicleMainPartModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicleCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "vehicleCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "partName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providerName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "providerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("providerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "providerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "partDescription"));
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
