package com.bth.anomalydetection.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "track")
public class Track {
    
	@Id
    @Column(name = "Id")
    private int id;
    
    @Column(name = "Latitude")
    private Double latitude;
    
    @Column(name = "Longitude")
    private Double longitude;
    
    @Column(name = "Speed")
    private Double speed;
    
    @Column(name = "Course")
    private Integer course;
    
    @Column(name = "Time")
    private String time;
    
    @JsonIgnore
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn(name = "VesselId")
    private AisVessel vessel;

    @JsonIgnore
    public AisVessel getVessel() {
        return vessel;
    }

    @JsonIgnore
    public void setVessel(AisVessel vessel) {
        this.vessel = vessel;
    }
    
    
    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    
}
