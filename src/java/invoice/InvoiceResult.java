/**
 * InvoiceResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package invoice;

public class InvoiceResult  extends invoice.Result  implements java.io.Serializable {
    private String typeCode;

    private String invNo;

    private invoice.InvoiceRowItem[] itemList;

    public InvoiceResult() {
    }

    public InvoiceResult(
           int resultCode,
           String message,
           String typeCode,
           String invNo,
           invoice.InvoiceRowItem[] itemList) {
        super(
            resultCode,
            message);
        this.typeCode = typeCode;
        this.invNo = invNo;
        this.itemList = itemList;
    }


    /**
     * Gets the typeCode value for this InvoiceResult.
     * 
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }


    /**
     * Sets the typeCode value for this InvoiceResult.
     * 
     * @param typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }


    /**
     * Gets the invNo value for this InvoiceResult.
     * 
     * @return invNo
     */
    public String getInvNo() {
        return invNo;
    }


    /**
     * Sets the invNo value for this InvoiceResult.
     * 
     * @param invNo
     */
    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }


    /**
     * Gets the itemList value for this InvoiceResult.
     * 
     * @return itemList
     */
    public invoice.InvoiceRowItem[] getItemList() {
        return itemList;
    }


    /**
     * Sets the itemList value for this InvoiceResult.
     * 
     * @param itemList
     */
    public void setItemList(invoice.InvoiceRowItem[] itemList) {
        this.itemList = itemList;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof InvoiceResult)) return false;
        InvoiceResult other = (InvoiceResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.typeCode==null && other.getTypeCode()==null) || 
             (this.typeCode!=null &&
              this.typeCode.equals(other.getTypeCode()))) &&
            ((this.invNo==null && other.getInvNo()==null) || 
             (this.invNo!=null &&
              this.invNo.equals(other.getInvNo()))) &&
            ((this.itemList==null && other.getItemList()==null) || 
             (this.itemList!=null &&
              java.util.Arrays.equals(this.itemList, other.getItemList())));
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
        if (getTypeCode() != null) {
            _hashCode += getTypeCode().hashCode();
        }
        if (getInvNo() != null) {
            _hashCode += getInvNo().hashCode();
        }
        if (getItemList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItemList());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getItemList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InvoiceResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TypeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "InvNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ItemList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceRowItem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceRowItem"));
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
