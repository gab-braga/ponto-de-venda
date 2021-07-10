package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @Column(name = "USUARIO_NOME")
    private String nome;

    @Column(name = "USUARIO_SENHA")
    private String senha;

    @Column(name = "USUARIO_PERMISSAO")
    private String permissao;

    public Usuario() {
    }

    public Usuario(String nome, String senha, String permissao) {
        this.nome = nome;
        this.senha = senha;
        this.permissao = permissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }
}
