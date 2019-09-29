package com.example.android.books;
import java.util.ArrayList;

import java.util.List;



public class Emprestimo {



    private String codigo;

    private String matricula_usuario;

    private String data_emprestimo;

    private String data_devolucao;

    private List<String> emprestimos;



    public Emprestimo(String codigo, String matriculaUsuario, String dataEmprestimo, String dataDevolucao) {

        this.codigo = codigo;

        this.matricula_usuario = matriculaUsuario;

        this.data_emprestimo = dataEmprestimo;

        this.data_devolucao = dataDevolucao;

        this.emprestimos = new ArrayList<>(  );

    }



    public String getCodigo() {

        return codigo;

    }



    public void setCodigo(String codigo) {

        this.codigo = codigo;

    }



    public String getMatricula_usuario() {

        return matricula_usuario;

    }



    public void setMatricula_usuario(String matricula_usuario) {

        this.matricula_usuario = matricula_usuario;

    }



    public String getData_emprestimo() {

        return data_emprestimo;

    }



    public void setData_emprestimo(String data_emprestimo) {

        this.data_emprestimo = data_emprestimo;

    }



    public String getDataDevolucao() {

        return data_devolucao;

    }



    public void setDataDevolucao(String dataDevolucao) {

        this.data_devolucao = dataDevolucao;

    }



    public List<String> getEmprestimos() {

        return emprestimos;

    }



    public void setEmprestimos(String titulo) {

        this.emprestimos.add( titulo );

    }

}