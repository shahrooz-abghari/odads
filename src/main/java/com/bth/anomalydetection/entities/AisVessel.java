package com.bth.anomalydetection.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ships")
public class AisVessel implements java.io.Serializable {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "VesselName")
    private String vesselName;
    @Column(name = "IMO")
    private String imo;
    @Column(name = "MMSI")
    private String mmsi;
    @Column(name = "CallSign")
    private String callSign;
    @Column(name = "VesselType")
    private String vesselType;
    @Column(name = "Flag")
    private String flag;
    @Column(name = "Year")
    private Integer year;
    @Column(name = "\"Length\"")
    private Float length;
    @Column(name = "Width")
    private Float width;
    @Column(name = "Draught")
    private Float draught;
    @Column(name = "DeadWeight")
    private Float deadWeight;
    @Column(name = "SpeedMax")
    private Float speedMax;
    @Column(name = "SpeedAverage")
    private Float speedAverage;
    @Column(name = "Longitude")
    private Double longitude;
    @Column(name = "Latitude")
    private Double latitude;
    @Column(name = "Heading")
    private Integer heading;
    @Column(name = "CurrentPort")
    private String currentPort;
    @Column(name = "LastKnownPort")
    private String origin;
    @Column(name = "Destination")
    private String destination;
    @Column(name = "ArrivalTime")
    private String arrivalTime;
    @Column(name = "InfoReceivedTime")
    private String infoReceivedTime;
    @Column(name = "CurrentDate")
    private String currentDate;
    @Column(name = "VesselTypeInfo")
    private String vesselTypeInfo;
    
   // @OneToMany (mappedBy="vessel", fetch= FetchType.LAZY)
    transient private List<Track> trackData;


    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(String currentPort) {
        this.currentPort = currentPort;
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

    public float getDraught() {
        return draught;
    }

    public void setDraught(float draught) {
        this.draught = draught;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    public String getInfoReceivedTime() {
        return infoReceivedTime;
    }

    public void setInfoReceivedTime(String infoReceivedTime) {
        this.infoReceivedTime = infoReceivedTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String name) {
        this.vesselName = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public float getSpeedAverage() {
        return speedAverage;
    }

    public void setSpeedAverage(float speedAverage) {
        this.speedAverage = speedAverage;
    }

    public float getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(float speedMax) {
        this.speedMax = speedMax;
    }

    public List<Track> getTrackData() {
        return trackData;
    }

    public void setTrackData(List<Track> trackData) {
        this.trackData = trackData;
    }

    public String getVesselType() {
        return vesselType;
    }

    public void setVesselType(String type) {
        this.vesselType = type;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getVesselTypeInfo() {
        return vesselTypeInfo;
    }

    public void setVesselTypeInfo(String vesselTypeInfo) {
        this.vesselTypeInfo = vesselTypeInfo;
    }
}
