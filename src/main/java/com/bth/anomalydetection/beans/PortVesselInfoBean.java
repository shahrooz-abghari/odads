package com.bth.anomalydetection.beans;

import java.util.Date;

public class PortVesselInfoBean {
   
    private String vesselName;
    private VesselType vesselType;
    private String origin;
    private String destination;
    private String companyName;
    private Date time;
    private VesselStatus vesselStatus;
    private AnomalyType anomalyType;
    private DataSourceType dataSourceType;
    private Date currentDate;
    
    public PortVesselInfoBean(String vesselName, VesselType vesselType, String origin, String destination, String companyName, Date time, VesselStatus vesselStatus, DataSourceType dataSourceType, Date currentDate) {
        this.vesselName = vesselName;
        this.vesselType = vesselType;
        this.origin = origin;
        this.destination = destination;
        this.companyName = companyName;
        this.time = time;
        this.vesselStatus = vesselStatus;
        this.dataSourceType = dataSourceType;
        this.currentDate = currentDate;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    
    public AnomalyType getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(AnomalyType anomalyType) {
        this.anomalyType = anomalyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public VesselStatus getVesselStatus() {
        return vesselStatus;
    }

    public void setVesselStatus(VesselStatus vesselStatus) {
        this.vesselStatus = vesselStatus;
    }
    
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
  
    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public VesselType getVesselType() {
        return vesselType;
    }

    public void setVesselType(VesselType vesselType) {
        this.vesselType = vesselType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

   
    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }
     
}
