package com.example.android.books.model;


public class Usuario {

    private String username;
    private String password;
    private String email;
    private String matricula;
    private String endereco;
    private String telefone;

    public Usuario(String username, String password, String email, String matricula, String endereco, String telefone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.matricula = matricula;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
