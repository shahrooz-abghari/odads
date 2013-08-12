package com.bth.anomalydetection.beans;

public class CompositeKey {
    String mmsi;
    String origin;
    String destination;
    AnomalyType anomalyType;
    
   
    public CompositeKey(String mmsi, String origin, String destination, AnomalyType anomalyType) {
        this.mmsi = mmsi;
        this.origin = origin;
        this.destination = destination;
        this.anomalyType = anomalyType;
    }
    
    public AnomalyType getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(AnomalyType anomalyType) {
        this.anomalyType = anomalyType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompositeKey other = (CompositeKey) obj;
        if ((this.mmsi == null) ? (other.mmsi != null) : !this.mmsi.equals(other.mmsi)) {
            return false;
        }
        if ((this.origin == null) ? (other.origin != null) : !this.origin.equals(other.origin)) {
            return false;
        }
        if ((this.destination == null) ? (other.destination != null) : !this.destination.equals(other.destination)) {
            return false;
        }
        if (this.anomalyType != other.anomalyType) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.mmsi != null ? this.mmsi.hashCode() : 0);
        hash = 97 * hash + (this.origin != null ? this.origin.hashCode() : 0);
        hash = 97 * hash + (this.destination != null ? this.destination.hashCode() : 0);
        hash = 97 * hash + (this.anomalyType != null ? this.anomalyType.hashCode() : 0);
        return hash;
    }

    
}
