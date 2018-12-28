/**
 * SystemUserModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class SystemUserModel  extends CommonModel  implements java.io.Serializable {
    private String username;

    private String usercode;

    private String orgName;

    private int orgcode;

    private int deleFlag;

    public SystemUserModel() {
    }

    public SystemUserModel(
           String field1,
           String field2,
           String field3,
           String field4,
           String field5,
           String username,
           String usercode,
           String orgName,
           int orgcode,
           int deleFlag) {
        super(
            field1,
            field2,
            field3,
            field4,
            field5);
        this.username = username;
        this.usercode = usercode;
        this.orgName = orgName;
        this.orgcode = orgcode;
        this.deleFlag = deleFlag;
    }


    /**
     * Gets the username value for this SystemUserModel.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this SystemUserModel.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the usercode value for this SystemUserModel.
     * 
     * @return usercode
     */
    public String getUsercode() {
        return usercode;
    }


    /**
     * Sets the usercode value for this SystemUserModel.
     * 
     * @param usercode
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }


    /**
     * Gets the orgName value for this SystemUserModel.
     * 
     * @return orgName
     */
    public String getOrgName() {
        return orgName;
    }


    /**
     * Sets the orgName value for this SystemUserModel.
     * 
     * @param orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    /**
     * Gets the orgcode value for this SystemUserModel.
     * 
     * @return orgcode
     */
    public int getOrgcode() {
        return orgcode;
    }


    /**
     * Sets the orgcode value for this SystemUserModel.
     * 
     * @param orgcode
     */
    public void setOrgcode(int orgcode) {
        this.orgcode = orgcode;
    }


    /**
     * Gets the deleFlag value for this SystemUserModel.
     * 
     * @return deleFlag
     */
    public int getDeleFlag() {
        return deleFlag;
    }


    /**
     * Sets the deleFlag value for this SystemUserModel.
     * 
     * @param deleFlag
     */
    public void setDeleFlag(int deleFlag) {
        this.deleFlag = deleFlag;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SystemUserModel)) return false;
        SystemUserModel other = (SystemUserModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.usercode==null && other.getUsercode()==null) || 
             (this.usercode!=null &&
              this.usercode.equals(other.getUsercode()))) &&
            ((this.orgName==null && other.getOrgName()==null) || 
             (this.orgName!=null &&
              this.orgName.equals(other.getOrgName()))) &&
            this.orgcode == other.getOrgcode() &&
            this.deleFlag == other.getDeleFlag();
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getUsercode() != null) {
            _hashCode += getUsercode().hashCode();
        }
        if (getOrgName() != null) {
            _hashCode += getOrgName().hashCode();
        }
        _hashCode += getOrgcode();
        _hashCode += getDeleFlag();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SystemUserModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "SystemUserModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usercode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Usercode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "orgName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Orgcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DeleFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
