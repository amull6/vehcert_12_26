/**
 * OperateResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package vehcert;

public class OperateResult  implements java.io.Serializable {
    private int resultCode;

    private NameValuePair[] resultDetail;

    public OperateResult() {
    }

    public OperateResult(
           int resultCode,
           NameValuePair[] resultDetail) {
           this.resultCode = resultCode;
           this.resultDetail = resultDetail;
    }


    /**
     * Gets the resultCode value for this OperateResult.
     * 
     * @return resultCode
     */
    public int getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this OperateResult.
     * 
     * @param resultCode
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the resultDetail value for this OperateResult.
     * 
     * @return resultDetail
     */
    public NameValuePair[] getResultDetail() {
        return resultDetail;
    }


    /**
     * Sets the resultDetail value for this OperateResult.
     * 
     * @param resultDetail
     */
    public void setResultDetail(NameValuePair[] resultDetail) {
        this.resultDetail = resultDetail;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof OperateResult)) return false;
        OperateResult other = (OperateResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.resultCode == other.getResultCode() &&
            ((this.resultDetail==null && other.getResultDetail()==null) || 
             (this.resultDetail!=null &&
              java.util.Arrays.equals(this.resultDetail, other.getResultDetail())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getResultCode();
        if (getResultDetail() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResultDetail());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getResultDetail(), i);
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
        new org.apache.axis.description.TypeDesc(OperateResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "OperateResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "ResultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "ResultDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "NameValuePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "NameValuePair"));
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
