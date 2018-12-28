/**
 * CertificateInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package vehcert;

public class CertificateInfo  extends vehcert.CertificateWithSystemInfo  implements java.io.Serializable {
    private String VEHICLE_STATUS;

    public CertificateInfo() {
    }

    public CertificateInfo(
           String WZHGZBH,
           String DPHGZBH,
           java.util.Calendar FZRQ,
           String CLZZQYMC,
           String CLPP,
           String CLMC,
           String CLXH,
           String CSYS,
           String DPXH,
           String DPID,
           String CLSBDH,
           String CJH,
           String FDJH,
           String FDJXH,
           String RLZL,
           String PFBZ,
           String PL,
           String GL,
           String ZXXS,
           String QLJ,
           String HLJ,
           String LTS,
           String LTGG,
           String GBTHPS,
           String ZJ,
           String ZH,
           String ZS,
           String WKC,
           String WKK,
           String WKG,
           String HXNBC,
           String HXNBK,
           String HXNBG,
           String ZZL,
           String EDZZL,
           String ZBZL,
           String ZZLLYXS,
           String ZQYZZL,
           String EDZK,
           String BGCAZZDYXZZL,
           String JSSZCRS,
           String QZDFS,
           String HZDFS,
           String QZDCZFS,
           String HZDCZFS,
           String ZGCS,
           java.util.Calendar CLZZRQ,
           String BZ,
           String QYBZ,
           String CPSCDZ,
           String QYQTXX,
           String CPH,
           String PC,
           java.util.Calendar GGSXRQ,
           String PZXLH,
           String CLZTXX,
           String ZCHGZBH,
           String CLLX,
           java.util.Calendar CZRQ,
           String CLSCDWMC,
           String QYID,
           String YH,
           String ZXZS,
           String CDDBJ,
           String ZZBH,
           String DYWYM,
           String h_ID,
           String VERCODE,
           String RESPONSE_CODE,
           java.util.Calendar CREATETIME,
           String HD_HOST,
           String HD_USER,
           java.util.Calendar UPDATETIME,
           String VERSION,
           String CLIENT_HARDWARE_INFO,
           String UPSEND_TAG,
           java.util.Calendar FEEDBACK_TIME,
           String UKEY,
           String VEHICLE_STATUS) {
        super(
            WZHGZBH,
            DPHGZBH,
            FZRQ,
            CLZZQYMC,
            CLPP,
            CLMC,
            CLXH,
            CSYS,
            DPXH,
            DPID,
            CLSBDH,
            CJH,
            FDJH,
            FDJXH,
            RLZL,
            PFBZ,
            PL,
            GL,
            ZXXS,
            QLJ,
            HLJ,
            LTS,
            LTGG,
            GBTHPS,
            ZJ,
            ZH,
            ZS,
            WKC,
            WKK,
            WKG,
            HXNBC,
            HXNBK,
            HXNBG,
            ZZL,
            EDZZL,
            ZBZL,
            ZZLLYXS,
            ZQYZZL,
            EDZK,
            BGCAZZDYXZZL,
            JSSZCRS,
            QZDFS,
            HZDFS,
            QZDCZFS,
            HZDCZFS,
            ZGCS,
            CLZZRQ,
            BZ,
            QYBZ,
            CPSCDZ,
            QYQTXX,
            CPH,
            PC,
            GGSXRQ,
            PZXLH,
            CLZTXX,
            ZCHGZBH,
            CLLX,
            CZRQ,
            CLSCDWMC,
            QYID,
            YH,
            ZXZS,
            CDDBJ,
            ZZBH,
            DYWYM,
            h_ID,
            VERCODE,
            RESPONSE_CODE,
            CREATETIME,
            HD_HOST,
            HD_USER,
            UPDATETIME,
            VERSION,
            CLIENT_HARDWARE_INFO,
            UPSEND_TAG,
            FEEDBACK_TIME,
            UKEY);
        this.VEHICLE_STATUS = VEHICLE_STATUS;
    }


    /**
     * Gets the VEHICLE_STATUS value for this CertificateInfo.
     * 
     * @return VEHICLE_STATUS
     */
    public String getVEHICLE_STATUS() {
        return VEHICLE_STATUS;
    }


    /**
     * Sets the VEHICLE_STATUS value for this CertificateInfo.
     * 
     * @param VEHICLE_STATUS
     */
    public void setVEHICLE_STATUS(String VEHICLE_STATUS) {
        this.VEHICLE_STATUS = VEHICLE_STATUS;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CertificateInfo)) return false;
        CertificateInfo other = (CertificateInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.VEHICLE_STATUS==null && other.getVEHICLE_STATUS()==null) || 
             (this.VEHICLE_STATUS!=null &&
              this.VEHICLE_STATUS.equals(other.getVEHICLE_STATUS())));
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
        if (getVEHICLE_STATUS() != null) {
            _hashCode += getVEHICLE_STATUS().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CertificateInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "CertificateInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VEHICLE_STATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "VEHICLE_STATUS"));
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
