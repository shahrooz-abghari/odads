package com.bth.anomalydetection.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "norrkoping_port")
public class NorrkopingPort  implements java.io.Serializable {
    
    @Id
    @Column(name="Id")
    private long id;
    
    @Column(name="VesselName")
    private String vesselName;
    
    @Column(name="Origin")
    private String origin;
    
    @Column(name="Destination")
    private String destination;
    
    @Column(name="ArrivalDate")
    private String arrivalDate;
    
    @Column(name="CompanyName")
    private String companyName;
   
    @Column(name="Berth")
    private String berth;
    
    @Column(name="DeadWeight")
    private Float deadWeight;
       
    @Column(name="\"Length\"")
    private Float length;
    
    @Column(name="Width")
    private Float width;
        
    @Column(name="Goods")
    private String goods;
    
    @Column(name="ArrivalDeparture")
    private int arrivalDeparture;
    
    @Column(name="CurrentDate")
    private String currentDate;

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getArrivalDeparture() {
        return arrivalDeparture;
    }

    public void setArrivalDeparture(int arrivalDeparture) {
        this.arrivalDeparture = arrivalDeparture;
    }

    public String getBerth() {
        return berth;
    }

    public void setBerth(String berth) {
        this.berth = berth;
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

    public float getDeadWeight() {
        return deadWeight;
    }

    public void setDeadWeight(float deadWeight) {
        this.deadWeight = deadWeight;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

}
