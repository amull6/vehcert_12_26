/**
 * AfficeStockModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class AfficeStockModel  implements java.io.Serializable {
    private String new_vin;

    private int new_loanislock;

    public AfficeStockModel() {
    }

    public AfficeStockModel(
           String new_vin,
           int new_loanislock) {
           this.new_vin = new_vin;
           this.new_loanislock = new_loanislock;
    }


    /**
     * Gets the new_vin value for this AfficeStockModel.
     * 
     * @return new_vin
     */
    public String getNew_vin() {
        return new_vin;
    }


    /**
     * Sets the new_vin value for this AfficeStockModel.
     * 
     * @param new_vin
     */
    public void setNew_vin(String new_vin) {
        this.new_vin = new_vin;
    }


    /**
     * Gets the new_loanislock value for this AfficeStockModel.
     * 
     * @return new_loanislock
     */
    public int getNew_loanislock() {
        return new_loanislock;
    }


    /**
     * Sets the new_loanislock value for this AfficeStockModel.
     * 
     * @param new_loanislock
     */
    public void setNew_loanislock(int new_loanislock) {
        this.new_loanislock = new_loanislock;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AfficeStockModel)) return false;
        AfficeStockModel other = (AfficeStockModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.new_vin==null && other.getNew_vin()==null) || 
             (this.new_vin!=null &&
              this.new_vin.equals(other.getNew_vin()))) &&
            this.new_loanislock == other.getNew_loanislock();
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
        if (getNew_vin() != null) {
            _hashCode += getNew_vin().hashCode();
        }
        _hashCode += getNew_loanislock();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AfficeStockModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "AfficeStockModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("new_vin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "new_vin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("new_loanislock");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "new_loanislock"));
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
