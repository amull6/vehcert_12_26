/**
 * InvoiceFeedBack.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package invoice;

public class InvoiceFeedBack  extends invoice.Result  implements java.io.Serializable {
    private invoice.InvoiceResult[] resultList;

    public InvoiceFeedBack() {
    }

    public InvoiceFeedBack(
           int resultCode,
           String message,
           invoice.InvoiceResult[] resultList) {
        super(
            resultCode,
            message);
        this.resultList = resultList;
    }


    /**
     * Gets the resultList value for this InvoiceFeedBack.
     * 
     * @return resultList
     */
    public invoice.InvoiceResult[] getResultList() {
        return resultList;
    }


    /**
     * Sets the resultList value for this InvoiceFeedBack.
     * 
     * @param resultList
     */
    public void setResultList(invoice.InvoiceResult[] resultList) {
        this.resultList = resultList;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof InvoiceFeedBack)) return false;
        InvoiceFeedBack other = (InvoiceFeedBack) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.resultList==null && other.getResultList()==null) || 
             (this.resultList!=null &&
              java.util.Arrays.equals(this.resultList, other.getResultList())));
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
        if (getResultList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResultList());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getResultList(), i);
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
        new org.apache.axis.description.TypeDesc(InvoiceFeedBack.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceFeedBack"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ResultList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceResult"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceResult"));
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
