package com.example.android.livros.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo implements Serializable {

    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("matricula_usuario")
    @Expose
    private String matriculaUsuario;
    @SerializedName("data_emprestimo")
    @Expose
    private String dataEmprestimo;
    @SerializedName("data_devolucao")
    @Expose
    private String dataDevolucao;
    @SerializedName("emprestimos")
    @Expose
    private List<String> emprestimos = null;

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

    public String getMatricula_usuario() {
        return matriculaUsuario;
    }

    public void setMatricula_usuario(String matricula_usuario) {
        this.matriculaUsuario = matricula_usuario;
    }

    public String getData_emprestimo() {
        return dataEmprestimo;
    }

    public void setData_emprestimo(String data_emprestimo) {
        this.dataEmprestimo = data_emprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public List<String> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<String> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
