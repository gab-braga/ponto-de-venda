package model;

import javax.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENTE_CODIGO")
    private int codigo;

    @Column(name = "CLIENTE_NOME")
    private String nome;

    @Column(name = "CLIENTE_CPF")
    private String cpf;

    @Column(name = "CLIENTE_TELEFONE")
    private String telefone;

    @Column(name = "CLIENTE_EMAIL")
    private String email;

    @Column(name = "CLIENTE_ENDERECO")
    private String endereco;

    @Column(name = "CLIENTE_NUMERO")
    private String numero;

    @Column(name = "CLIENTE_CIDADE")
    private String cidade;

    @Column(name = "CLIENTE_UF")
    private String uf;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, String telefone, String email, String endereco, String numero, String cidade, String uf) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Cliente(int codigo, String nome, String cpf, String telefone, String email, String endereco, String numero, String cidade, String uf) {
        this(nome, cpf, telefone, email, endereco, numero, cidade, uf);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
