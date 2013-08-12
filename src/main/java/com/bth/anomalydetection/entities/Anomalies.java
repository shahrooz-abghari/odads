package com.bth.anomalydetection.entities;

import com.bth.anomalydetection.beans.AnomalyType;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "anomalies")
public class Anomalies implements Serializable {
    
	@Id
    @GeneratedValue
    @Column
    private long id;
    
    @Column
    private AnomalyType anomalyType;
    
    @ManyToOne
    @JoinColumn(name="history_id")
    private AnomalyHistory history;

    public AnomalyType getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(AnomalyType anomalyType) {
        this.anomalyType = anomalyType;
    }
    
    @JsonIgnore
    public AnomalyHistory getHistory() {
        return history;
    }

    public void setHistory(AnomalyHistory history) {
        this.history = history;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
}
