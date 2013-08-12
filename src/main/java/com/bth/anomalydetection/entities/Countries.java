package com.bth.anomalydetection.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class Countries {
   
    @Id
    @Column(name="Id")
    private long id;
    
    @Column(name="Name")
    private String name;
    
    @Column(name="Abbr")
    private String abbr;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
    
}
    
    