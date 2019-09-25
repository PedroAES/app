package com.example.android.books.model;

public class Livro {

    private String codigo;
    private String titulo;
    private String autor;
    private String codigoSessao;

    public Livro(String codigo, String titulo, String autor, String codigoSessao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.codigoSessao = codigoSessao;
    }

    public Livro() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCodigoSessao() {
        return codigoSessao;
    }

    public void setCodigoSessao(String codigoSessao) {
        this.codigoSessao = codigoSessao;
    }

}
