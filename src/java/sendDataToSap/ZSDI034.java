/**
 * ZSDI034.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package sendDataToSap;

public class ZSDI034  implements java.io.Serializable {
    private String i_ZCLXH;

    private String i_ZEQUNR;

    private String i_ZFDJH;

    private String i_ZFDJXH;

    private String i_ZHGZBH;

    private String i_ZTIME;

    private String i_ZVIN;

    private String i_ZVIN_OLD;

    public ZSDI034() {
    }

    public ZSDI034(
           String i_ZCLXH,
           String i_ZEQUNR,
           String i_ZFDJH,
           String i_ZFDJXH,
           String i_ZHGZBH,
           String i_ZTIME,
           String i_ZVIN,
           String i_ZVIN_OLD) {
           this.i_ZCLXH = i_ZCLXH;
           this.i_ZEQUNR = i_ZEQUNR;
           this.i_ZFDJH = i_ZFDJH;
           this.i_ZFDJXH = i_ZFDJXH;
           this.i_ZHGZBH = i_ZHGZBH;
           this.i_ZTIME = i_ZTIME;
           this.i_ZVIN = i_ZVIN;
           this.i_ZVIN_OLD = i_ZVIN_OLD;
    }


    /**
     * Gets the i_ZCLXH value for this ZSDI034.
     * 
     * @return i_ZCLXH
     */
    public String getI_ZCLXH() {
        return i_ZCLXH;
    }


    /**
     * Sets the i_ZCLXH value for this ZSDI034.
     * 
     * @param i_ZCLXH
     */
    public void setI_ZCLXH(String i_ZCLXH) {
        this.i_ZCLXH = i_ZCLXH;
    }


    /**
     * Gets the i_ZEQUNR value for this ZSDI034.
     * 
     * @return i_ZEQUNR
     */
    public String getI_ZEQUNR() {
        return i_ZEQUNR;
    }


    /**
     * Sets the i_ZEQUNR value for this ZSDI034.
     * 
     * @param i_ZEQUNR
     */
    public void setI_ZEQUNR(String i_ZEQUNR) {
        this.i_ZEQUNR = i_ZEQUNR;
    }


    /**
     * Gets the i_ZFDJH value for this ZSDI034.
     * 
     * @return i_ZFDJH
     */
    public String getI_ZFDJH() {
        return i_ZFDJH;
    }


    /**
     * Sets the i_ZFDJH value for this ZSDI034.
     * 
     * @param i_ZFDJH
     */
    public void setI_ZFDJH(String i_ZFDJH) {
        this.i_ZFDJH = i_ZFDJH;
    }


    /**
     * Gets the i_ZFDJXH value for this ZSDI034.
     * 
     * @return i_ZFDJXH
     */
    public String getI_ZFDJXH() {
        return i_ZFDJXH;
    }


    /**
     * Sets the i_ZFDJXH value for this ZSDI034.
     * 
     * @param i_ZFDJXH
     */
    public void setI_ZFDJXH(String i_ZFDJXH) {
        this.i_ZFDJXH = i_ZFDJXH;
    }


    /**
     * Gets the i_ZHGZBH value for this ZSDI034.
     * 
     * @return i_ZHGZBH
     */
    public String getI_ZHGZBH() {
        return i_ZHGZBH;
    }


    /**
     * Sets the i_ZHGZBH value for this ZSDI034.
     * 
     * @param i_ZHGZBH
     */
    public void setI_ZHGZBH(String i_ZHGZBH) {
        this.i_ZHGZBH = i_ZHGZBH;
    }


    /**
     * Gets the i_ZTIME value for this ZSDI034.
     * 
     * @return i_ZTIME
     */
    public String getI_ZTIME() {
        return i_ZTIME;
    }


    /**
     * Sets the i_ZTIME value for this ZSDI034.
     * 
     * @param i_ZTIME
     */
    public void setI_ZTIME(String i_ZTIME) {
        this.i_ZTIME = i_ZTIME;
    }


    /**
     * Gets the i_ZVIN value for this ZSDI034.
     * 
     * @return i_ZVIN
     */
    public String getI_ZVIN() {
        return i_ZVIN;
    }


    /**
     * Sets the i_ZVIN value for this ZSDI034.
     * 
     * @param i_ZVIN
     */
    public void setI_ZVIN(String i_ZVIN) {
        this.i_ZVIN = i_ZVIN;
    }


    /**
     * Gets the i_ZVIN_OLD value for this ZSDI034.
     * 
     * @return i_ZVIN_OLD
     */
    public String getI_ZVIN_OLD() {
        return i_ZVIN_OLD;
    }


    /**
     * Sets the i_ZVIN_OLD value for this ZSDI034.
     * 
     * @param i_ZVIN_OLD
     */
    public void setI_ZVIN_OLD(String i_ZVIN_OLD) {
        this.i_ZVIN_OLD = i_ZVIN_OLD;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ZSDI034)) return false;
        ZSDI034 other = (ZSDI034) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.i_ZCLXH==null && other.getI_ZCLXH()==null) || 
             (this.i_ZCLXH!=null &&
              this.i_ZCLXH.equals(other.getI_ZCLXH()))) &&
            ((this.i_ZEQUNR==null && other.getI_ZEQUNR()==null) || 
             (this.i_ZEQUNR!=null &&
              this.i_ZEQUNR.equals(other.getI_ZEQUNR()))) &&
            ((this.i_ZFDJH==null && other.getI_ZFDJH()==null) || 
             (this.i_ZFDJH!=null &&
              this.i_ZFDJH.equals(other.getI_ZFDJH()))) &&
            ((this.i_ZFDJXH==null && other.getI_ZFDJXH()==null) || 
             (this.i_ZFDJXH!=null &&
              this.i_ZFDJXH.equals(other.getI_ZFDJXH()))) &&
            ((this.i_ZHGZBH==null && other.getI_ZHGZBH()==null) || 
             (this.i_ZHGZBH!=null &&
              this.i_ZHGZBH.equals(other.getI_ZHGZBH()))) &&
            ((this.i_ZTIME==null && other.getI_ZTIME()==null) || 
             (this.i_ZTIME!=null &&
              this.i_ZTIME.equals(other.getI_ZTIME()))) &&
            ((this.i_ZVIN==null && other.getI_ZVIN()==null) || 
             (this.i_ZVIN!=null &&
              this.i_ZVIN.equals(other.getI_ZVIN()))) &&
            ((this.i_ZVIN_OLD==null && other.getI_ZVIN_OLD()==null) || 
             (this.i_ZVIN_OLD!=null &&
              this.i_ZVIN_OLD.equals(other.getI_ZVIN_OLD())));
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
        if (getI_ZCLXH() != null) {
            _hashCode += getI_ZCLXH().hashCode();
        }
        if (getI_ZEQUNR() != null) {
            _hashCode += getI_ZEQUNR().hashCode();
        }
        if (getI_ZFDJH() != null) {
            _hashCode += getI_ZFDJH().hashCode();
        }
        if (getI_ZFDJXH() != null) {
            _hashCode += getI_ZFDJXH().hashCode();
        }
        if (getI_ZHGZBH() != null) {
            _hashCode += getI_ZHGZBH().hashCode();
        }
        if (getI_ZTIME() != null) {
            _hashCode += getI_ZTIME().hashCode();
        }
        if (getI_ZVIN() != null) {
            _hashCode += getI_ZVIN().hashCode();
        }
        if (getI_ZVIN_OLD() != null) {
            _hashCode += getI_ZVIN_OLD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZSDI034.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:sap-com:document:sap:rfc:functions", ">ZSDI034"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZCLXH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZCLXH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZEQUNR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZEQUNR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZFDJH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZFDJH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZFDJXH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZFDJXH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZHGZBH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZHGZBH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZTIME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZTIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZVIN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZVIN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("i_ZVIN_OLD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "I_ZVIN_OLD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
