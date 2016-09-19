package com.nucome.app.crm;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by david on 4/29/2016.
 */
public class ServiceInfo implements Serializable {
    private String serviceId;
    private String serviceName;

    private String description;
    private String category;
    private String language;
    private String address;

    private Double latitude;
    private Double longitude;

    private String providerName;
    private long providerPhone;
    public String getServiceId() {
        return serviceId;
    }
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public long getProviderPhone() {
        return providerPhone;
    }
    public void setProviderPhone(long providerPhone) {
        this.providerPhone = providerPhone;
    }
}
