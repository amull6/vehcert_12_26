/**
 * PrintResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package cn.com.wz.vehcert;

public class PrintResult  implements java.io.Serializable {
    private Boolean isSuccess;

    private String msg;

    private String veh_Dywym;

    private String veh_wzhgzbh;

    private String vercode;

    public PrintResult() {
    }

    public PrintResult(
           Boolean isSuccess,
           String msg,
           String veh_Dywym,
           String veh_wzhgzbh,
           String vercode) {
           this.isSuccess = isSuccess;
           this.msg = msg;
           this.veh_Dywym = veh_Dywym;
           this.veh_wzhgzbh = veh_wzhgzbh;
           this.vercode = vercode;
    }


    /**
     * Gets the isSuccess value for this PrintResult.
     * 
     * @return isSuccess
     */
    public Boolean getIsSuccess() {
        return isSuccess;
    }


    /**
     * Sets the isSuccess value for this PrintResult.
     * 
     * @param isSuccess
     */
    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    /**
     * Gets the msg value for this PrintResult.
     * 
     * @return msg
     */
    public String getMsg() {
        return msg;
    }


    /**
     * Sets the msg value for this PrintResult.
     * 
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }


    /**
     * Gets the veh_Dywym value for this PrintResult.
     * 
     * @return veh_Dywym
     */
    public String getVeh_Dywym() {
        return veh_Dywym;
    }


    /**
     * Sets the veh_Dywym value for this PrintResult.
     * 
     * @param veh_Dywym
     */
    public void setVeh_Dywym(String veh_Dywym) {
        this.veh_Dywym = veh_Dywym;
    }


    /**
     * Gets the veh_wzhgzbh value for this PrintResult.
     * 
     * @return veh_wzhgzbh
     */
    public String getVeh_wzhgzbh() {
        return veh_wzhgzbh;
    }


    /**
     * Sets the veh_wzhgzbh value for this PrintResult.
     * 
     * @param veh_wzhgzbh
     */
    public void setVeh_wzhgzbh(String veh_wzhgzbh) {
        this.veh_wzhgzbh = veh_wzhgzbh;
    }


    /**
     * Gets the vercode value for this PrintResult.
     * 
     * @return vercode
     */
    public String getVercode() {
        return vercode;
    }


    /**
     * Sets the vercode value for this PrintResult.
     * 
     * @param vercode
     */
    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof PrintResult)) return false;
        PrintResult other = (PrintResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.isSuccess==null && other.getIsSuccess()==null) || 
             (this.isSuccess!=null &&
              this.isSuccess.equals(other.getIsSuccess()))) &&
            ((this.msg==null && other.getMsg()==null) || 
             (this.msg!=null &&
              this.msg.equals(other.getMsg()))) &&
            ((this.veh_Dywym==null && other.getVeh_Dywym()==null) || 
             (this.veh_Dywym!=null &&
              this.veh_Dywym.equals(other.getVeh_Dywym()))) &&
            ((this.veh_wzhgzbh==null && other.getVeh_wzhgzbh()==null) || 
             (this.veh_wzhgzbh!=null &&
              this.veh_wzhgzbh.equals(other.getVeh_wzhgzbh()))) &&
            ((this.vercode==null && other.getVercode()==null) || 
             (this.vercode!=null &&
              this.vercode.equals(other.getVercode())));
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
        if (getIsSuccess() != null) {
            _hashCode += getIsSuccess().hashCode();
        }
        if (getMsg() != null) {
            _hashCode += getMsg().hashCode();
        }
        if (getVeh_Dywym() != null) {
            _hashCode += getVeh_Dywym().hashCode();
        }
        if (getVeh_wzhgzbh() != null) {
            _hashCode += getVeh_wzhgzbh().hashCode();
        }
        if (getVercode() != null) {
            _hashCode += getVercode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PrintResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/PrintContract", "PrintResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isSuccess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/PrintContract", "isSuccess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/PrintContract", "msg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("veh_Dywym");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/PrintContract", "veh_Dywym"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("veh_wzhgzbh");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/PrintContract", "veh_wzhgzbh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vercode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/PrintContract", "vercode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
