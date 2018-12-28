/**
 * ContactModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class ContactModel  extends CommonModel  implements java.io.Serializable {
    private String customerCode;

    private int linkmanType;

    private String linkmanName;

    private int linkmanGender;

    private String linkmanCellPhoneNumber;

    private String linkmanProvinceName;

    private String linkmanCityName;

    private String linkmanCountyName;

    private String linkmanEmail;

    private String linkmanAddress;

    private int deleFlag;

    public ContactModel() {
    }

    public ContactModel(
           String field1,
           String field2,
           String field3,
           String field4,
           String field5,
           String customerCode,
           int linkmanType,
           String linkmanName,
           int linkmanGender,
           String linkmanCellPhoneNumber,
           String linkmanProvinceName,
           String linkmanCityName,
           String linkmanCountyName,
           String linkmanEmail,
           String linkmanAddress,
           int deleFlag) {
        super(
            field1,
            field2,
            field3,
            field4,
            field5);
        this.customerCode = customerCode;
        this.linkmanType = linkmanType;
        this.linkmanName = linkmanName;
        this.linkmanGender = linkmanGender;
        this.linkmanCellPhoneNumber = linkmanCellPhoneNumber;
        this.linkmanProvinceName = linkmanProvinceName;
        this.linkmanCityName = linkmanCityName;
        this.linkmanCountyName = linkmanCountyName;
        this.linkmanEmail = linkmanEmail;
        this.linkmanAddress = linkmanAddress;
        this.deleFlag = deleFlag;
    }


    /**
     * Gets the customerCode value for this ContactModel.
     * 
     * @return customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }


    /**
     * Sets the customerCode value for this ContactModel.
     * 
     * @param customerCode
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }


    /**
     * Gets the linkmanType value for this ContactModel.
     * 
     * @return linkmanType
     */
    public int getLinkmanType() {
        return linkmanType;
    }


    /**
     * Sets the linkmanType value for this ContactModel.
     * 
     * @param linkmanType
     */
    public void setLinkmanType(int linkmanType) {
        this.linkmanType = linkmanType;
    }


    /**
     * Gets the linkmanName value for this ContactModel.
     * 
     * @return linkmanName
     */
    public String getLinkmanName() {
        return linkmanName;
    }


    /**
     * Sets the linkmanName value for this ContactModel.
     * 
     * @param linkmanName
     */
    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }


    /**
     * Gets the linkmanGender value for this ContactModel.
     * 
     * @return linkmanGender
     */
    public int getLinkmanGender() {
        return linkmanGender;
    }


    /**
     * Sets the linkmanGender value for this ContactModel.
     * 
     * @param linkmanGender
     */
    public void setLinkmanGender(int linkmanGender) {
        this.linkmanGender = linkmanGender;
    }


    /**
     * Gets the linkmanCellPhoneNumber value for this ContactModel.
     * 
     * @return linkmanCellPhoneNumber
     */
    public String getLinkmanCellPhoneNumber() {
        return linkmanCellPhoneNumber;
    }


    /**
     * Sets the linkmanCellPhoneNumber value for this ContactModel.
     * 
     * @param linkmanCellPhoneNumber
     */
    public void setLinkmanCellPhoneNumber(String linkmanCellPhoneNumber) {
        this.linkmanCellPhoneNumber = linkmanCellPhoneNumber;
    }


    /**
     * Gets the linkmanProvinceName value for this ContactModel.
     * 
     * @return linkmanProvinceName
     */
    public String getLinkmanProvinceName() {
        return linkmanProvinceName;
    }


    /**
     * Sets the linkmanProvinceName value for this ContactModel.
     * 
     * @param linkmanProvinceName
     */
    public void setLinkmanProvinceName(String linkmanProvinceName) {
        this.linkmanProvinceName = linkmanProvinceName;
    }


    /**
     * Gets the linkmanCityName value for this ContactModel.
     * 
     * @return linkmanCityName
     */
    public String getLinkmanCityName() {
        return linkmanCityName;
    }


    /**
     * Sets the linkmanCityName value for this ContactModel.
     * 
     * @param linkmanCityName
     */
    public void setLinkmanCityName(String linkmanCityName) {
        this.linkmanCityName = linkmanCityName;
    }


    /**
     * Gets the linkmanCountyName value for this ContactModel.
     * 
     * @return linkmanCountyName
     */
    public String getLinkmanCountyName() {
        return linkmanCountyName;
    }


    /**
     * Sets the linkmanCountyName value for this ContactModel.
     * 
     * @param linkmanCountyName
     */
    public void setLinkmanCountyName(String linkmanCountyName) {
        this.linkmanCountyName = linkmanCountyName;
    }


    /**
     * Gets the linkmanEmail value for this ContactModel.
     * 
     * @return linkmanEmail
     */
    public String getLinkmanEmail() {
        return linkmanEmail;
    }


    /**
     * Sets the linkmanEmail value for this ContactModel.
     * 
     * @param linkmanEmail
     */
    public void setLinkmanEmail(String linkmanEmail) {
        this.linkmanEmail = linkmanEmail;
    }


    /**
     * Gets the linkmanAddress value for this ContactModel.
     * 
     * @return linkmanAddress
     */
    public String getLinkmanAddress() {
        return linkmanAddress;
    }


    /**
     * Sets the linkmanAddress value for this ContactModel.
     * 
     * @param linkmanAddress
     */
    public void setLinkmanAddress(String linkmanAddress) {
        this.linkmanAddress = linkmanAddress;
    }


    /**
     * Gets the deleFlag value for this ContactModel.
     * 
     * @return deleFlag
     */
    public int getDeleFlag() {
        return deleFlag;
    }


    /**
     * Sets the deleFlag value for this ContactModel.
     * 
     * @param deleFlag
     */
    public void setDeleFlag(int deleFlag) {
        this.deleFlag = deleFlag;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ContactModel)) return false;
        ContactModel other = (ContactModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.customerCode==null && other.getCustomerCode()==null) || 
             (this.customerCode!=null &&
              this.customerCode.equals(other.getCustomerCode()))) &&
            this.linkmanType == other.getLinkmanType() &&
            ((this.linkmanName==null && other.getLinkmanName()==null) || 
             (this.linkmanName!=null &&
              this.linkmanName.equals(other.getLinkmanName()))) &&
            this.linkmanGender == other.getLinkmanGender() &&
            ((this.linkmanCellPhoneNumber==null && other.getLinkmanCellPhoneNumber()==null) || 
             (this.linkmanCellPhoneNumber!=null &&
              this.linkmanCellPhoneNumber.equals(other.getLinkmanCellPhoneNumber()))) &&
            ((this.linkmanProvinceName==null && other.getLinkmanProvinceName()==null) || 
             (this.linkmanProvinceName!=null &&
              this.linkmanProvinceName.equals(other.getLinkmanProvinceName()))) &&
            ((this.linkmanCityName==null && other.getLinkmanCityName()==null) || 
             (this.linkmanCityName!=null &&
              this.linkmanCityName.equals(other.getLinkmanCityName()))) &&
            ((this.linkmanCountyName==null && other.getLinkmanCountyName()==null) || 
             (this.linkmanCountyName!=null &&
              this.linkmanCountyName.equals(other.getLinkmanCountyName()))) &&
            ((this.linkmanEmail==null && other.getLinkmanEmail()==null) || 
             (this.linkmanEmail!=null &&
              this.linkmanEmail.equals(other.getLinkmanEmail()))) &&
            ((this.linkmanAddress==null && other.getLinkmanAddress()==null) || 
             (this.linkmanAddress!=null &&
              this.linkmanAddress.equals(other.getLinkmanAddress()))) &&
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
        if (getCustomerCode() != null) {
            _hashCode += getCustomerCode().hashCode();
        }
        _hashCode += getLinkmanType();
        if (getLinkmanName() != null) {
            _hashCode += getLinkmanName().hashCode();
        }
        _hashCode += getLinkmanGender();
        if (getLinkmanCellPhoneNumber() != null) {
            _hashCode += getLinkmanCellPhoneNumber().hashCode();
        }
        if (getLinkmanProvinceName() != null) {
            _hashCode += getLinkmanProvinceName().hashCode();
        }
        if (getLinkmanCityName() != null) {
            _hashCode += getLinkmanCityName().hashCode();
        }
        if (getLinkmanCountyName() != null) {
            _hashCode += getLinkmanCountyName().hashCode();
        }
        if (getLinkmanEmail() != null) {
            _hashCode += getLinkmanEmail().hashCode();
        }
        if (getLinkmanAddress() != null) {
            _hashCode += getLinkmanAddress().hashCode();
        }
        _hashCode += getDeleFlag();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContactModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ContactModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CustomerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "linkmanType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanGender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanGender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanCellPhoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanCellPhoneNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanProvinceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanProvinceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanCityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanCityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanCountyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanCountyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanEmail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanEmail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkmanAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LinkmanAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
