package com.bth.anomalydetection.entities;

import com.bth.anomalydetection.beans.AnomalyType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "anomaly_history")
public class AnomalyHistory implements Serializable {
    
	@Id
    @GeneratedValue    
    @Column(name = "Id")
    private Long id;
    
//    @OneToMany(cascade= CascadeType.ALL)
//    private List<Anomalies> anomalies;
    
    @OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
    private List<Anomalies> anomalies;
    
    @Column(name = "AnomalyType")
    private AnomalyType anomalyType;
   
    @Column(name = "VesselId")
    private Long vesselId;

    @Column(name = "Date")
    private String date;

    public AnomalyHistory() {
    }

    public AnomalyHistory(Long vesselId, String date) {
        this.vesselId = vesselId;
        this.date = date;
    }

    public List<Anomalies> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomalies> anomalies) {
        this.anomalies = anomalies;
    }
    
    
    public Long getVesselId() {
        return vesselId;
    }

    public void setVesselId(Long vesselId) {
        this.vesselId = vesselId;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnomalyType getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(AnomalyType anomalyType) {
        this.anomalyType = anomalyType;
    }

       
}
