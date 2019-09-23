package com.example.android.books.model;

public class Sessao {
    private String codigo;
    private String descricao;
    private String localizacao;

    public Sessao(String codigo, String descricao, String localizacao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.localizacao = localizacao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
