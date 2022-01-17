package com.github.fgabrielbraga.model;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_CODE", nullable = false)
    private Long code;

    @Column(name = "NAME", nullable = false, length = 80)
    private String name;

    @Column(name = "CPF", nullable = false, length = 11)
    private String cpf;

    @Column(name = "PHONE", nullable = false, length = 15)
    private String phone;

    @Column(name = "EMAIL", nullable = false, length = 80)
    private String email;

    @Column(name = "ADDRESS", nullable = false, length = 100)
    private String address;

    @Column(name = "NUMBER", nullable = false, length = 5)
    private String number;

    @Column(name = "CITY", nullable = false, length = 80)
    private String city;

    @Column(name = "UF", nullable = false, length = 2)
    private String uf;

    public Client() {
    }

    public Client(String name, String cpf, String phone, String email, String address, String number, String city, String uf) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.number = number;
        this.city = city;
        this.uf = uf;
    }

    public Client(Long code, String name, String cpf, String phone, String email, String address, String number, String city, String uf) {
        this(name, cpf, phone, email, address, number, city, uf);
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return getName();
    }
}
