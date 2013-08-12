package com.bth.anomalydetection.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smuggling_black_list")
public class SmugglingBlackList implements Serializable {
    @Id
    @GeneratedValue    
    @Column(name = "Id")
    private Long id;
    
    @Column(name = "VesselName")
    private String vesselName;
    
    @Column(name = "CallSign")
    private String callSign;
   
    @Column(name = "IMO")
    private String imo;
    
    @Column(name = "Enabled")
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
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

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

}
