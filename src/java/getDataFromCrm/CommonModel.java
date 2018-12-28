/**
 * CommonModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class CommonModel  implements java.io.Serializable {
    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    public CommonModel() {
    }

    public CommonModel(
           String field1,
           String field2,
           String field3,
           String field4,
           String field5) {
           this.field1 = field1;
           this.field2 = field2;
           this.field3 = field3;
           this.field4 = field4;
           this.field5 = field5;
    }


    /**
     * Gets the field1 value for this CommonModel.
     * 
     * @return field1
     */
    public String getField1() {
        return field1;
    }


    /**
     * Sets the field1 value for this CommonModel.
     * 
     * @param field1
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }


    /**
     * Gets the field2 value for this CommonModel.
     * 
     * @return field2
     */
    public String getField2() {
        return field2;
    }


    /**
     * Sets the field2 value for this CommonModel.
     * 
     * @param field2
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }


    /**
     * Gets the field3 value for this CommonModel.
     * 
     * @return field3
     */
    public String getField3() {
        return field3;
    }


    /**
     * Sets the field3 value for this CommonModel.
     * 
     * @param field3
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }


    /**
     * Gets the field4 value for this CommonModel.
     * 
     * @return field4
     */
    public String getField4() {
        return field4;
    }


    /**
     * Sets the field4 value for this CommonModel.
     * 
     * @param field4
     */
    public void setField4(String field4) {
        this.field4 = field4;
    }


    /**
     * Gets the field5 value for this CommonModel.
     * 
     * @return field5
     */
    public String getField5() {
        return field5;
    }


    /**
     * Sets the field5 value for this CommonModel.
     * 
     * @param field5
     */
    public void setField5(String field5) {
        this.field5 = field5;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CommonModel)) return false;
        CommonModel other = (CommonModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.field1==null && other.getField1()==null) || 
             (this.field1!=null &&
              this.field1.equals(other.getField1()))) &&
            ((this.field2==null && other.getField2()==null) || 
             (this.field2!=null &&
              this.field2.equals(other.getField2()))) &&
            ((this.field3==null && other.getField3()==null) || 
             (this.field3!=null &&
              this.field3.equals(other.getField3()))) &&
            ((this.field4==null && other.getField4()==null) || 
             (this.field4!=null &&
              this.field4.equals(other.getField4()))) &&
            ((this.field5==null && other.getField5()==null) || 
             (this.field5!=null &&
              this.field5.equals(other.getField5())));
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
        if (getField1() != null) {
            _hashCode += getField1().hashCode();
        }
        if (getField2() != null) {
            _hashCode += getField2().hashCode();
        }
        if (getField3() != null) {
            _hashCode += getField3().hashCode();
        }
        if (getField4() != null) {
            _hashCode += getField4().hashCode();
        }
        if (getField5() != null) {
            _hashCode += getField5().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommonModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CommonModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Field1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Field2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Field3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Field4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("field5");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Field5"));
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
