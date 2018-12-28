/**
 * InvoiceRowItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package invoice;

public class InvoiceRowItem  extends invoice.Result  implements java.io.Serializable {
    private String rowId;

    private String SPMC;

    private int SL;

    private String GGXH;

    private String[] hgzList;

    public InvoiceRowItem() {
    }

    public InvoiceRowItem(
           int resultCode,
           String message,
           String rowId,
           String SPMC,
           int SL,
           String GGXH,
           String[] hgzList) {
        super(
            resultCode,
            message);
        this.rowId = rowId;
        this.SPMC = SPMC;
        this.SL = SL;
        this.GGXH = GGXH;
        this.hgzList = hgzList;
    }


    /**
     * Gets the rowId value for this InvoiceRowItem.
     * 
     * @return rowId
     */
    public String getRowId() {
        return rowId;
    }


    /**
     * Sets the rowId value for this InvoiceRowItem.
     * 
     * @param rowId
     */
    public void setRowId(String rowId) {
        this.rowId = rowId;
    }


    /**
     * Gets the SPMC value for this InvoiceRowItem.
     * 
     * @return SPMC
     */
    public String getSPMC() {
        return SPMC;
    }


    /**
     * Sets the SPMC value for this InvoiceRowItem.
     * 
     * @param SPMC
     */
    public void setSPMC(String SPMC) {
        this.SPMC = SPMC;
    }


    /**
     * Gets the SL value for this InvoiceRowItem.
     * 
     * @return SL
     */
    public int getSL() {
        return SL;
    }


    /**
     * Sets the SL value for this InvoiceRowItem.
     * 
     * @param SL
     */
    public void setSL(int SL) {
        this.SL = SL;
    }


    /**
     * Gets the GGXH value for this InvoiceRowItem.
     * 
     * @return GGXH
     */
    public String getGGXH() {
        return GGXH;
    }


    /**
     * Sets the GGXH value for this InvoiceRowItem.
     * 
     * @param GGXH
     */
    public void setGGXH(String GGXH) {
        this.GGXH = GGXH;
    }


    /**
     * Gets the hgzList value for this InvoiceRowItem.
     * 
     * @return hgzList
     */
    public String[] getHgzList() {
        return hgzList;
    }


    /**
     * Sets the hgzList value for this InvoiceRowItem.
     * 
     * @param hgzList
     */
    public void setHgzList(String[] hgzList) {
        this.hgzList = hgzList;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof InvoiceRowItem)) return false;
        InvoiceRowItem other = (InvoiceRowItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.rowId==null && other.getRowId()==null) || 
             (this.rowId!=null &&
              this.rowId.equals(other.getRowId()))) &&
            ((this.SPMC==null && other.getSPMC()==null) || 
             (this.SPMC!=null &&
              this.SPMC.equals(other.getSPMC()))) &&
            this.SL == other.getSL() &&
            ((this.GGXH==null && other.getGGXH()==null) || 
             (this.GGXH!=null &&
              this.GGXH.equals(other.getGGXH()))) &&
            ((this.hgzList==null && other.getHgzList()==null) || 
             (this.hgzList!=null &&
              java.util.Arrays.equals(this.hgzList, other.getHgzList())));
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
        if (getRowId() != null) {
            _hashCode += getRowId().hashCode();
        }
        if (getSPMC() != null) {
            _hashCode += getSPMC().hashCode();
        }
        _hashCode += getSL();
        if (getGGXH() != null) {
            _hashCode += getGGXH().hashCode();
        }
        if (getHgzList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHgzList());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getHgzList(), i);
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
        new org.apache.axis.description.TypeDesc(InvoiceRowItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "InvoiceRowItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rowId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RowId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPMC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SPMC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GGXH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GGXH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hgzList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "HgzList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "string"));
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
