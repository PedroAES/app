package com.example.android.books;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cadastro_activity);

        etNome = findViewById(R.id.nome);
        etEmail = findViewById(R.id.email);
        etEndereco = findViewById(R.id.endereco);
        etMatricula = findViewById(R.id.matricula);
        etSenha = findViewById(R.id.senha);
        etTelefone = findViewById(R.id.telefone);
    }

    public void sair(View view){
        finish();
    }

    public void registrar(View view){
        nome = etNome.getText().toString().toLowerCase().trim();
        email = etEmail.getText().toString().trim();
        matricula = etMatricula.getText().toString().trim();
        endereco = etEndereco.getText().toString().trim();
        telefone = etTelefone.getText().toString().trim();
        senha = etSenha.getText().toString().trim();

        if (validarCampos()) {
            efetuarRegistro();
        }
    }

    private void efetuarRegistro(){
        telaDeCarregamento();
    }

    private boolean validarCampos() {
        String CAMPO_VAZIO="";

        if (CAMPO_VAZIO.equals(nome)) {
            etNome.setError("Informe o seu nome");
            etNome.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(email)) {
            etEmail.setError("Informe o seu E-mail");
            etEmail.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(matricula)) {
            etMatricula.setError("Informe a sua matrícula");
            etMatricula.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(endereco)) {
            etEndereco.setError("Informe o seu endereço");
            etEndereco.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(telefone)) {
            etTelefone.setError("Informe seu telefone");
            etTelefone.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(senha)) {
            etSenha.setError("Informe uma senha");
            etSenha.requestFocus();
            return false;
        }

        return true;

    }

    private void telaDeCarregamento() {
        pDialog = new ProgressDialog(CadastroActivity.this);
        pDialog.setMessage("Efetuando Cadastro...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private EditText etNome;
    private EditText etEmail;
    private EditText etMatricula;
    private EditText etEndereco;
    private EditText etTelefone;
    private EditText etSenha;
    private String nome;
    private String email;
    private String matricula;
    private String endereco;
    private String telefone;
    private String senha;
    private ProgressDialog pDialog;
}
