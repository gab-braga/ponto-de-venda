package com.github.fgabrielbraga.model;

import java.util.Date;

public class Report {

    private Date timestamp;
    private Double valueSale = 0.0;
    private Double valueAcquisition = 0.0;

    public Report() {
    }

    public Report(Date timestamp, Double valueSale, Double valueAcquisition) {
        setTimestamp(timestamp);
        setValueSale(valueSale);
        setValueAcquisition(valueAcquisition);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValueSale() {
        return valueSale;
    }

    public void setValueSale(Double valueSale) {
        if(valueSale != null) {
            this.valueSale = valueSale;
        }
        else {
            this.valueSale = 0.0;
        }
    }

    public Double getValueAcquisition() {
        return valueAcquisition;
    }

    public void setValueAcquisition(Double valueAcquisition) {
        if(valueAcquisition != null) {
            this.valueAcquisition = valueAcquisition;
        }
        else {
            this.valueAcquisition = 0.0;
        }
    }

    public Double getValueTotal() {
        return getValueSale() - getValueAcquisition();
    }
}
