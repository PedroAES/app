package com.example.android.livros.model;

import java.io.Serializable;

public class Livro implements Serializable {

    private String codigo;
    private String titulo;
    private String autor;
    private String codigo_sessao;

    public Livro(String codigo, String titulo, String autor, String codigo_sessao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.codigo_sessao = codigo_sessao;
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

    public String getCodigo_sessao() {
        return codigo_sessao;
    }

    public void setCodigo_sessao(String codigoSessao) {
        this.codigo_sessao = codigoSessao;
    }

}
