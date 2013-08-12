package com.bth.anomalydetection.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "helsinki_port")
public class HelsinkiPort {
    
	@Id
    @Column(name="Id")
    private long id;
    
    @Column(name="VesselName")
    private String vesselName;
    
    @Column(name="Harbour")
    private String harbour;
    
    @Column(name="Terminal")
    private String terminal;
    
    @Column(name="Origin")
    private String origin;
    
    @Column(name="Destination")
    private String destination;
    
    @Column(name="ArrivalTime")
    private String arrivalTime;
   
    @Column(name="DepartureTime")
    private String departureTime;
    
    @Column(name="CompanyName")
    private String companyName;
        
    @Column(name="Berth")
    private String berth;
    
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

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHarbour() {
        return harbour;
    }

    public void setHarbour(String harbour) {
        this.harbour = harbour;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }
    
}
