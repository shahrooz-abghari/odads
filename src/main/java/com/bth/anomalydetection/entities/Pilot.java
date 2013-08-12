package com.bth.anomalydetection.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pilot")
public class Pilot {

    @Id
    @Column(name="Id")
    private long id;
   
    @Column(name="VesselName")
    private String vesselName;
   
    @Column(name="Origin")
    private String origin;
    
    @Column(name="Destination")
    private String destination;
    
    @Column(name="OrderTime")
    private String orderTime;
    
    @Column(name="StartTime")
    private String startTime;
    
    @Column(name="OStartTime")
    private String opStartTime;
    
    @Column(name="OEndTime")
    private String opEndTime;
   
    @Column(name="CurrentDate")
    private String currentDate;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOpEndTime() {
        return opEndTime;
    }

    public void setOpEndTime(String opEndTime) {
        this.opEndTime = opEndTime;
    }

    public String getOpStartTime() {
        return opStartTime;
    }

    public void setOpStartTime(String opStartTime) {
        this.opStartTime = opStartTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }
    
    
}
