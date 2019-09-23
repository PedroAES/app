package com.example.android.books.model;

import java.util.ArrayList;
import java.util.List;

public class Emprestimo {

    private String codigo;
    private String matriculaUsuario;
    private String dataEmprestimo;
    private String dataDevolucao;
    private List<Livro> emprestimos;

    public Emprestimo(String codigo, String matriculaUsuario, String dataEmprestimo, String dataDevolucao) {
        this.codigo = codigo;
        this.matriculaUsuario = matriculaUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.emprestimos = new ArrayList<>(  );
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMatriculaUsuario() {
        return matriculaUsuario;
    }

    public void setMatriculaUsuario(String matriculaUsuario) {
        this.matriculaUsuario = matriculaUsuario;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public List<Livro> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(Livro livro) {
        this.emprestimos.add( livro );
    }
}
