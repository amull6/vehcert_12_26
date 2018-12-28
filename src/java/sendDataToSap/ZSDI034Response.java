/**
 * ZSDI034Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package sendDataToSap;

public class ZSDI034Response  implements java.io.Serializable {
    private String e_TEXT;

    private String e_TYPE;

    public ZSDI034Response() {
    }

    public ZSDI034Response(
           String e_TEXT,
           String e_TYPE) {
           this.e_TEXT = e_TEXT;
           this.e_TYPE = e_TYPE;
    }


    /**
     * Gets the e_TEXT value for this ZSDI034Response.
     * 
     * @return e_TEXT
     */
    public String getE_TEXT() {
        return e_TEXT;
    }


    /**
     * Sets the e_TEXT value for this ZSDI034Response.
     * 
     * @param e_TEXT
     */
    public void setE_TEXT(String e_TEXT) {
        this.e_TEXT = e_TEXT;
    }


    /**
     * Gets the e_TYPE value for this ZSDI034Response.
     * 
     * @return e_TYPE
     */
    public String getE_TYPE() {
        return e_TYPE;
    }


    /**
     * Sets the e_TYPE value for this ZSDI034Response.
     * 
     * @param e_TYPE
     */
    public void setE_TYPE(String e_TYPE) {
        this.e_TYPE = e_TYPE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ZSDI034Response)) return false;
        ZSDI034Response other = (ZSDI034Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.e_TEXT==null && other.getE_TEXT()==null) || 
             (this.e_TEXT!=null &&
              this.e_TEXT.equals(other.getE_TEXT()))) &&
            ((this.e_TYPE==null && other.getE_TYPE()==null) || 
             (this.e_TYPE!=null &&
              this.e_TYPE.equals(other.getE_TYPE())));
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
        if (getE_TEXT() != null) {
            _hashCode += getE_TEXT().hashCode();
        }
        if (getE_TYPE() != null) {
            _hashCode += getE_TYPE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZSDI034Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", ">ZSDI034.Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("e_TEXT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "E_TEXT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("e_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "E_TYPE"));
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
