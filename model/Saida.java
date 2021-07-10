package model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Saida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SAIDA_CODIGO")
    private int codigo;

    @Column(name = "SAIDA_VALOR")
    private double valor;

    @Column(name = "SAIDA_DATA_HORA")
    private Date dataHora;

    @Column(name = "SAIDA_MOTIVO")
    private String motivo;

    @Column(name = "SAIDA_OPERADOR")
    private String operador;

    public Saida() {
    }

    public Saida(double valor, Date dataHora, String motivo, String operador) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.motivo = motivo;
        this.operador = operador;
    }

    public Saida(int codigo, double valor, Date dataHora, String motivo, String operador) {
        this(valor, dataHora, motivo, operador);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }
}
