package com.bth.anomalydetection.beans;

import java.util.Date;

public class PositionBean {

    private double longitude;
    private double latitiude;
    private Date infoReceivedTime;

    public double getLatitiude() {
        return latitiude;
    }

    public void setLatitiude(double latitiude) {
        this.latitiude = latitiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
     public Date getInfoReceivedTime() {
        return infoReceivedTime;
    }

    public void setInfoReceivedTime(Date infoReceivedTime) {
        this.infoReceivedTime = infoReceivedTime;
    }
}