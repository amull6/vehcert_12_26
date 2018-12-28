/**
 * LeadsRuleModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package getDataFromCrm;

public class LeadsRuleModel  implements java.io.Serializable {
    private String salebu;

    private String dealername;

    private String rulecode;

    private String brand;

    private String car;

    private String vehicle;

    private String province;

    private String city;

    private String county;

    private String owneridname;

    private String dealercode;

    public LeadsRuleModel() {
    }

    public LeadsRuleModel(
           String salebu,
           String dealername,
           String rulecode,
           String brand,
           String car,
           String vehicle,
           String province,
           String city,
           String county,
           String owneridname,
           String dealercode) {
           this.salebu = salebu;
           this.dealername = dealername;
           this.rulecode = rulecode;
           this.brand = brand;
           this.car = car;
           this.vehicle = vehicle;
           this.province = province;
           this.city = city;
           this.county = county;
           this.owneridname = owneridname;
           this.dealercode = dealercode;
    }


    /**
     * Gets the salebu value for this LeadsRuleModel.
     * 
     * @return salebu
     */
    public String getSalebu() {
        return salebu;
    }


    /**
     * Sets the salebu value for this LeadsRuleModel.
     * 
     * @param salebu
     */
    public void setSalebu(String salebu) {
        this.salebu = salebu;
    }


    /**
     * Gets the dealername value for this LeadsRuleModel.
     * 
     * @return dealername
     */
    public String getDealername() {
        return dealername;
    }


    /**
     * Sets the dealername value for this LeadsRuleModel.
     * 
     * @param dealername
     */
    public void setDealername(String dealername) {
        this.dealername = dealername;
    }


    /**
     * Gets the rulecode value for this LeadsRuleModel.
     * 
     * @return rulecode
     */
    public String getRulecode() {
        return rulecode;
    }


    /**
     * Sets the rulecode value for this LeadsRuleModel.
     * 
     * @param rulecode
     */
    public void setRulecode(String rulecode) {
        this.rulecode = rulecode;
    }


    /**
     * Gets the brand value for this LeadsRuleModel.
     * 
     * @return brand
     */
    public String getBrand() {
        return brand;
    }


    /**
     * Sets the brand value for this LeadsRuleModel.
     * 
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }


    /**
     * Gets the car value for this LeadsRuleModel.
     * 
     * @return car
     */
    public String getCar() {
        return car;
    }


    /**
     * Sets the car value for this LeadsRuleModel.
     * 
     * @param car
     */
    public void setCar(String car) {
        this.car = car;
    }


    /**
     * Gets the vehicle value for this LeadsRuleModel.
     * 
     * @return vehicle
     */
    public String getVehicle() {
        return vehicle;
    }


    /**
     * Sets the vehicle value for this LeadsRuleModel.
     * 
     * @param vehicle
     */
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }


    /**
     * Gets the province value for this LeadsRuleModel.
     * 
     * @return province
     */
    public String getProvince() {
        return province;
    }


    /**
     * Sets the province value for this LeadsRuleModel.
     * 
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }


    /**
     * Gets the city value for this LeadsRuleModel.
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }


    /**
     * Sets the city value for this LeadsRuleModel.
     * 
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Gets the county value for this LeadsRuleModel.
     * 
     * @return county
     */
    public String getCounty() {
        return county;
    }


    /**
     * Sets the county value for this LeadsRuleModel.
     * 
     * @param county
     */
    public void setCounty(String county) {
        this.county = county;
    }


    /**
     * Gets the owneridname value for this LeadsRuleModel.
     * 
     * @return owneridname
     */
    public String getOwneridname() {
        return owneridname;
    }


    /**
     * Sets the owneridname value for this LeadsRuleModel.
     * 
     * @param owneridname
     */
    public void setOwneridname(String owneridname) {
        this.owneridname = owneridname;
    }


    /**
     * Gets the dealercode value for this LeadsRuleModel.
     * 
     * @return dealercode
     */
    public String getDealercode() {
        return dealercode;
    }


    /**
     * Sets the dealercode value for this LeadsRuleModel.
     * 
     * @param dealercode
     */
    public void setDealercode(String dealercode) {
        this.dealercode = dealercode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof LeadsRuleModel)) return false;
        LeadsRuleModel other = (LeadsRuleModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.salebu==null && other.getSalebu()==null) || 
             (this.salebu!=null &&
              this.salebu.equals(other.getSalebu()))) &&
            ((this.dealername==null && other.getDealername()==null) || 
             (this.dealername!=null &&
              this.dealername.equals(other.getDealername()))) &&
            ((this.rulecode==null && other.getRulecode()==null) || 
             (this.rulecode!=null &&
              this.rulecode.equals(other.getRulecode()))) &&
            ((this.brand==null && other.getBrand()==null) || 
             (this.brand!=null &&
              this.brand.equals(other.getBrand()))) &&
            ((this.car==null && other.getCar()==null) || 
             (this.car!=null &&
              this.car.equals(other.getCar()))) &&
            ((this.vehicle==null && other.getVehicle()==null) || 
             (this.vehicle!=null &&
              this.vehicle.equals(other.getVehicle()))) &&
            ((this.province==null && other.getProvince()==null) || 
             (this.province!=null &&
              this.province.equals(other.getProvince()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.county==null && other.getCounty()==null) || 
             (this.county!=null &&
              this.county.equals(other.getCounty()))) &&
            ((this.owneridname==null && other.getOwneridname()==null) || 
             (this.owneridname!=null &&
              this.owneridname.equals(other.getOwneridname()))) &&
            ((this.dealercode==null && other.getDealercode()==null) || 
             (this.dealercode!=null &&
              this.dealercode.equals(other.getDealercode())));
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
        if (getSalebu() != null) {
            _hashCode += getSalebu().hashCode();
        }
        if (getDealername() != null) {
            _hashCode += getDealername().hashCode();
        }
        if (getRulecode() != null) {
            _hashCode += getRulecode().hashCode();
        }
        if (getBrand() != null) {
            _hashCode += getBrand().hashCode();
        }
        if (getCar() != null) {
            _hashCode += getCar().hashCode();
        }
        if (getVehicle() != null) {
            _hashCode += getVehicle().hashCode();
        }
        if (getProvince() != null) {
            _hashCode += getProvince().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getCounty() != null) {
            _hashCode += getCounty().hashCode();
        }
        if (getOwneridname() != null) {
            _hashCode += getOwneridname().hashCode();
        }
        if (getDealercode() != null) {
            _hashCode += getDealercode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LeadsRuleModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "LeadsRuleModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salebu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "salebu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealername");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "dealername"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rulecode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "rulecode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "brand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("car");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "car"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vehicle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "vehicle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("province");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "province"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "city"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("county");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "county"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("owneridname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "owneridname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealercode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "dealercode"));
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
