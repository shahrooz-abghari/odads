package com.bth.anomalydetection.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trip_history")
public class VesselTripHistory implements Serializable {
    
	@Id
    @Column(name = "id")
    private Long id;
    
    @Column(name = "VesselName")
    private String vesselName;
    
    @Column(name = "Trip")
    private String trip;
    
    @Column(name = "Count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }
    
    
}
