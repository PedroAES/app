package com.example.android.books;

public class Livro {
    private String titulo;
    private String autor;
    private String codLivro;
    private String codSessao;

    public Livro (String titulo, String autor, String codLivro, String codSessao){
        this.titulo = titulo;
        this.autor = autor;
        this.codLivro = codLivro;
        this.codSessao = codSessao;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getAutor(){
        return autor;
    }

    public String getCodLivro(){
        return codLivro;
    }

    public String getCodSessao(){
        return codSessao;
    }
}
