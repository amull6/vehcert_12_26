/**
 * CertificateWithSystemInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package vehcert;

public class CertificateWithSystemInfo  extends CertificateWithPrintingSystem  implements java.io.Serializable {
    private String h_ID;

    private String VERCODE;

    private String RESPONSE_CODE;

    private java.util.Calendar CREATETIME;

    private String HD_HOST;

    private String HD_USER;

    private java.util.Calendar UPDATETIME;

    private String VERSION;

    private String CLIENT_HARDWARE_INFO;

    private String UPSEND_TAG;

    private java.util.Calendar FEEDBACK_TIME;

    private String UKEY;

    public CertificateWithSystemInfo() {
    }

    public CertificateWithSystemInfo(
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
           String UKEY) {
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
            DYWYM);
        this.h_ID = h_ID;
        this.VERCODE = VERCODE;
        this.RESPONSE_CODE = RESPONSE_CODE;
        this.CREATETIME = CREATETIME;
        this.HD_HOST = HD_HOST;
        this.HD_USER = HD_USER;
        this.UPDATETIME = UPDATETIME;
        this.VERSION = VERSION;
        this.CLIENT_HARDWARE_INFO = CLIENT_HARDWARE_INFO;
        this.UPSEND_TAG = UPSEND_TAG;
        this.FEEDBACK_TIME = FEEDBACK_TIME;
        this.UKEY = UKEY;
    }


    /**
     * Gets the h_ID value for this CertificateWithSystemInfo.
     * 
     * @return h_ID
     */
    public String getH_ID() {
        return h_ID;
    }


    /**
     * Sets the h_ID value for this CertificateWithSystemInfo.
     * 
     * @param h_ID
     */
    public void setH_ID(String h_ID) {
        this.h_ID = h_ID;
    }


    /**
     * Gets the VERCODE value for this CertificateWithSystemInfo.
     * 
     * @return VERCODE
     */
    public String getVERCODE() {
        return VERCODE;
    }


    /**
     * Sets the VERCODE value for this CertificateWithSystemInfo.
     * 
     * @param VERCODE
     */
    public void setVERCODE(String VERCODE) {
        this.VERCODE = VERCODE;
    }


    /**
     * Gets the RESPONSE_CODE value for this CertificateWithSystemInfo.
     * 
     * @return RESPONSE_CODE
     */
    public String getRESPONSE_CODE() {
        return RESPONSE_CODE;
    }


    /**
     * Sets the RESPONSE_CODE value for this CertificateWithSystemInfo.
     * 
     * @param RESPONSE_CODE
     */
    public void setRESPONSE_CODE(String RESPONSE_CODE) {
        this.RESPONSE_CODE = RESPONSE_CODE;
    }


    /**
     * Gets the CREATETIME value for this CertificateWithSystemInfo.
     * 
     * @return CREATETIME
     */
    public java.util.Calendar getCREATETIME() {
        return CREATETIME;
    }


    /**
     * Sets the CREATETIME value for this CertificateWithSystemInfo.
     * 
     * @param CREATETIME
     */
    public void setCREATETIME(java.util.Calendar CREATETIME) {
        this.CREATETIME = CREATETIME;
    }


    /**
     * Gets the HD_HOST value for this CertificateWithSystemInfo.
     * 
     * @return HD_HOST
     */
    public String getHD_HOST() {
        return HD_HOST;
    }


    /**
     * Sets the HD_HOST value for this CertificateWithSystemInfo.
     * 
     * @param HD_HOST
     */
    public void setHD_HOST(String HD_HOST) {
        this.HD_HOST = HD_HOST;
    }


    /**
     * Gets the HD_USER value for this CertificateWithSystemInfo.
     * 
     * @return HD_USER
     */
    public String getHD_USER() {
        return HD_USER;
    }


    /**
     * Sets the HD_USER value for this CertificateWithSystemInfo.
     * 
     * @param HD_USER
     */
    public void setHD_USER(String HD_USER) {
        this.HD_USER = HD_USER;
    }


    /**
     * Gets the UPDATETIME value for this CertificateWithSystemInfo.
     * 
     * @return UPDATETIME
     */
    public java.util.Calendar getUPDATETIME() {
        return UPDATETIME;
    }


    /**
     * Sets the UPDATETIME value for this CertificateWithSystemInfo.
     * 
     * @param UPDATETIME
     */
    public void setUPDATETIME(java.util.Calendar UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }


    /**
     * Gets the VERSION value for this CertificateWithSystemInfo.
     * 
     * @return VERSION
     */
    public String getVERSION() {
        return VERSION;
    }


    /**
     * Sets the VERSION value for this CertificateWithSystemInfo.
     * 
     * @param VERSION
     */
    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }


    /**
     * Gets the CLIENT_HARDWARE_INFO value for this CertificateWithSystemInfo.
     * 
     * @return CLIENT_HARDWARE_INFO
     */
    public String getCLIENT_HARDWARE_INFO() {
        return CLIENT_HARDWARE_INFO;
    }


    /**
     * Sets the CLIENT_HARDWARE_INFO value for this CertificateWithSystemInfo.
     * 
     * @param CLIENT_HARDWARE_INFO
     */
    public void setCLIENT_HARDWARE_INFO(String CLIENT_HARDWARE_INFO) {
        this.CLIENT_HARDWARE_INFO = CLIENT_HARDWARE_INFO;
    }


    /**
     * Gets the UPSEND_TAG value for this CertificateWithSystemInfo.
     * 
     * @return UPSEND_TAG
     */
    public String getUPSEND_TAG() {
        return UPSEND_TAG;
    }


    /**
     * Sets the UPSEND_TAG value for this CertificateWithSystemInfo.
     * 
     * @param UPSEND_TAG
     */
    public void setUPSEND_TAG(String UPSEND_TAG) {
        this.UPSEND_TAG = UPSEND_TAG;
    }


    /**
     * Gets the FEEDBACK_TIME value for this CertificateWithSystemInfo.
     * 
     * @return FEEDBACK_TIME
     */
    public java.util.Calendar getFEEDBACK_TIME() {
        return FEEDBACK_TIME;
    }


    /**
     * Sets the FEEDBACK_TIME value for this CertificateWithSystemInfo.
     * 
     * @param FEEDBACK_TIME
     */
    public void setFEEDBACK_TIME(java.util.Calendar FEEDBACK_TIME) {
        this.FEEDBACK_TIME = FEEDBACK_TIME;
    }


    /**
     * Gets the UKEY value for this CertificateWithSystemInfo.
     * 
     * @return UKEY
     */
    public String getUKEY() {
        return UKEY;
    }


    /**
     * Sets the UKEY value for this CertificateWithSystemInfo.
     * 
     * @param UKEY
     */
    public void setUKEY(String UKEY) {
        this.UKEY = UKEY;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CertificateWithSystemInfo)) return false;
        CertificateWithSystemInfo other = (CertificateWithSystemInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.h_ID==null && other.getH_ID()==null) || 
             (this.h_ID!=null &&
              this.h_ID.equals(other.getH_ID()))) &&
            ((this.VERCODE==null && other.getVERCODE()==null) || 
             (this.VERCODE!=null &&
              this.VERCODE.equals(other.getVERCODE()))) &&
            ((this.RESPONSE_CODE==null && other.getRESPONSE_CODE()==null) || 
             (this.RESPONSE_CODE!=null &&
              this.RESPONSE_CODE.equals(other.getRESPONSE_CODE()))) &&
            ((this.CREATETIME==null && other.getCREATETIME()==null) || 
             (this.CREATETIME!=null &&
              this.CREATETIME.equals(other.getCREATETIME()))) &&
            ((this.HD_HOST==null && other.getHD_HOST()==null) || 
             (this.HD_HOST!=null &&
              this.HD_HOST.equals(other.getHD_HOST()))) &&
            ((this.HD_USER==null && other.getHD_USER()==null) || 
             (this.HD_USER!=null &&
              this.HD_USER.equals(other.getHD_USER()))) &&
            ((this.UPDATETIME==null && other.getUPDATETIME()==null) || 
             (this.UPDATETIME!=null &&
              this.UPDATETIME.equals(other.getUPDATETIME()))) &&
            ((this.VERSION==null && other.getVERSION()==null) || 
             (this.VERSION!=null &&
              this.VERSION.equals(other.getVERSION()))) &&
            ((this.CLIENT_HARDWARE_INFO==null && other.getCLIENT_HARDWARE_INFO()==null) || 
             (this.CLIENT_HARDWARE_INFO!=null &&
              this.CLIENT_HARDWARE_INFO.equals(other.getCLIENT_HARDWARE_INFO()))) &&
            ((this.UPSEND_TAG==null && other.getUPSEND_TAG()==null) || 
             (this.UPSEND_TAG!=null &&
              this.UPSEND_TAG.equals(other.getUPSEND_TAG()))) &&
            ((this.FEEDBACK_TIME==null && other.getFEEDBACK_TIME()==null) || 
             (this.FEEDBACK_TIME!=null &&
              this.FEEDBACK_TIME.equals(other.getFEEDBACK_TIME()))) &&
            ((this.UKEY==null && other.getUKEY()==null) || 
             (this.UKEY!=null &&
              this.UKEY.equals(other.getUKEY())));
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
        if (getH_ID() != null) {
            _hashCode += getH_ID().hashCode();
        }
        if (getVERCODE() != null) {
            _hashCode += getVERCODE().hashCode();
        }
        if (getRESPONSE_CODE() != null) {
            _hashCode += getRESPONSE_CODE().hashCode();
        }
        if (getCREATETIME() != null) {
            _hashCode += getCREATETIME().hashCode();
        }
        if (getHD_HOST() != null) {
            _hashCode += getHD_HOST().hashCode();
        }
        if (getHD_USER() != null) {
            _hashCode += getHD_USER().hashCode();
        }
        if (getUPDATETIME() != null) {
            _hashCode += getUPDATETIME().hashCode();
        }
        if (getVERSION() != null) {
            _hashCode += getVERSION().hashCode();
        }
        if (getCLIENT_HARDWARE_INFO() != null) {
            _hashCode += getCLIENT_HARDWARE_INFO().hashCode();
        }
        if (getUPSEND_TAG() != null) {
            _hashCode += getUPSEND_TAG().hashCode();
        }
        if (getFEEDBACK_TIME() != null) {
            _hashCode += getFEEDBACK_TIME().hashCode();
        }
        if (getUKEY() != null) {
            _hashCode += getUKEY().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CertificateWithSystemInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "CertificateWithSystemInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("h_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "H_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VERCODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "VERCODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RESPONSE_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "RESPONSE_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CREATETIME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "CREATETIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HD_HOST");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "HD_HOST"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HD_USER");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "HD_USER"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UPDATETIME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "UPDATETIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VERSION");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "VERSION"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CLIENT_HARDWARE_INFO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "CLIENT_HARDWARE_INFO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UPSEND_TAG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "UPSEND_TAG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FEEDBACK_TIME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "FEEDBACK_TIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UKEY");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.vidc.info/certificate/operation/", "UKEY"));
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
