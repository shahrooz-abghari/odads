package com.bth.anomalydetection.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tallinn_port")
public class TallinnPort implements java.io.Serializable {
    
    @Id
    @Column(name="Id")
    private long id;
    
    @Column(name="VesselName")
    private String vesselName;
    
    @Column(name="VesselType")
    private String vesselType;
    
    @Column(name="Harbour")
    private String harbour;
   
    @Column(name="VesselLine")
    private String vesselLine;
    
    @Column(name="Terminal")
    private String terminal;
    
    @Column(name="CompanyName")
    private String companyName;
      
    @Column(name="GrossWeight")
    private Float grossWeight;
       
    @Column(name="\"Length\"")
    private Float length;
    
    @Column(name="Width")
    private Float width;
        
    @Column(name="Pier")
    private String berth;
   
    @Column(name="Time")
    private String time;
  
    @Column(name="ArrivalDeparture")
    private int arrivalDeparture;
    
    @Column(name="CurrentDate")
    private String currentDate;

    public int getArrivalDeparture() {
        return arrivalDeparture;
    }

    public void setArrivalDeparture(int arrivalDeparture) {
        this.arrivalDeparture = arrivalDeparture;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public float  getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(float grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getHarbour() {
        return harbour;
    }

    public void setHarbour(String harbour) {
        this.harbour = harbour;
    }

    public String getBerth() {
        return berth;
    }

    public void setBerth(String berth) {
        this.berth = berth;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getVesselLine() {
        return vesselLine;
    }

    public void setVesselLine(String vesselLine) {
        this.vesselLine = vesselLine;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String vesselType) {
        this.vesselType = vesselType;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    
    
}
