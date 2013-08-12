package com.bth.anomalydetection.beans;

import com.bth.anomalydetection.ajax.json.CustomDateSerializer;
import com.bth.anomalydetection.entities.Track;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class VesselInfoBean implements java.io.Serializable {

    private Long id;
    private String name;
    private String cleanName;
    private String imo;
    private String mmsi;
    private String callSign;
    private VesselType type;
    private String flag;
    private int year;
    private float length;
    private float width;
    private float draught;
    private float deadWeight;
    private float speedMax;
    private float speedAverage;
    private double longitude;
    private double latitude;
    private int heading;
    private String currentPort;
    private String origin;
    private String destination;
    private Date arrivalTime;
    private Date infoReceivedTime;
    private Date currentDate;
    private List <Track> trackData;
    private EnumSet<AnomalyType> anomalyTypes;
    private VesselStatus status;
    private boolean alreadyChecked;
    

    public VesselInfoBean() {
    }

    public VesselInfoBean(Long id, String name, String cleanName, String imo, String mmsi, String callSign, VesselType type, String flag, int year, float length, float width, float draught, float deadWeight, float speedMax, float speedAverage, double longitude, double latitude, int heading, String currentPort, String origin, String destination, Date arrivalTime, Date infoReceivedTime, Date currentDate, List<Track> trackData, EnumSet<AnomalyType> anomalyTypes, VesselStatus status, boolean alreadyChecked) {
        this.id = id;
        this.name = name;
        this.cleanName = cleanName;
        this.imo = imo;
        this.mmsi = mmsi;
        this.callSign = callSign;
        this.type = type;
        this.flag = flag;
        this.year = year;
        this.length = length;
        this.width = width;
        this.draught = draught;
        this.deadWeight = deadWeight;
        this.speedMax = speedMax;
        this.speedAverage = speedAverage;
        this.longitude = longitude;
        this.latitude = latitude;
        this.heading = heading;
        this.currentPort = currentPort;
        this.origin = origin;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.infoReceivedTime = infoReceivedTime;
        this.currentDate = currentDate;
        this.trackData = trackData;
        this.anomalyTypes = anomalyTypes;
        this.status = status;
        this.alreadyChecked = alreadyChecked;
    }

    public boolean isAlreadyChecked() {
        return alreadyChecked;
    }

    public void setAlreadyChecked(boolean alreadyChecked) {
        this.alreadyChecked = alreadyChecked;
    }

    
    public EnumSet<AnomalyType> getAnomalyTypes() {
        return anomalyTypes;
    }

    public void setAnomalyTypes(EnumSet<AnomalyType> anomalyTypes) {
        this.anomalyTypes = anomalyTypes;
    }

    
    public VesselStatus getStatus() {
        return status;
    }

    public void setStatus(VesselStatus status) {
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getCleanName() {
        return cleanName;
    }

    public void setCleanName(String cleanName) {
        this.cleanName = cleanName;
    }

    public List<Track> getTrackData() {
        return trackData;
    }

    public void setTrackData(List<Track> trackData) {
        this.trackData = trackData;
    }
    
    @JsonSerialize(using=CustomDateSerializer.class)
    public Date getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    @JsonSerialize(using=CustomDateSerializer.class)
    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
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

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    @JsonSerialize(using=CustomDateSerializer.class)
    public Date getInfoReceivedTime() {
        return infoReceivedTime;
    }

    public void setInfoReceivedTime(Date infoReceivedTime) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public VesselType getType() {
        return type;
    }

    public void setType(VesselType type) {
        this.type = type;
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
    
    
}
