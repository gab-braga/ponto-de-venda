package com.github.fgabrielbraga.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "acquisitions")
@DiscriminatorValue(value = "A")
public class Acquisition extends Operation {

    @Column(name = "REASON", length = 100, nullable = true)
    private String reason;

    public Acquisition() {
    }

    public Acquisition(Double valor, User operador, Date dataHora, String reason) {
        setValue(valor);
        setOperator(operador);
        setDate(dataHora);
        this.reason = reason;
    }

    public Acquisition(Long codigo, Double valor, User operador, Date dataHora, String reason) {
        this(valor, operador, dataHora, reason);
        setCode(codigo);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
