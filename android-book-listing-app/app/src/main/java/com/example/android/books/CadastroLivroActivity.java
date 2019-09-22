package com.example.android.books;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class CadastroLivroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.livro_cadastro_activity);

        etCodigo = findViewById(R.id.codigo);
        etTitulo = findViewById(R.id.titulo);
        etAutor = findViewById(R.id.autor);
        etCodigoSessao = findViewById(R.id.codigo_sessao);
    }

    public void cadastrarLivro(View view){
        codigo = etCodigo.getText().toString().toLowerCase().trim();
        titulo = etTitulo.getText().toString().trim();
        autor = etAutor.getText().toString().trim();
        codigoSessao = etCodigoSessao.getText().toString().trim();

        if(validarCampos()){
            efetuarCadastro();
        }
    }

    private void efetuarCadastro(){
        telaDeCarregamento();
    }

    private void telaDeCarregamento() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Efetuando Cadastro...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private boolean validarCampos(){
        String CAMPO_VAZIO="";

        if (CAMPO_VAZIO.equals(codigo)) {
            etCodigo.setError("Informe o código");
            etCodigo.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(titulo)) {
            etTitulo.setError("Informe o título");
            etTitulo.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(autor)) {
            etAutor.setError("Informe o autor");
            etAutor.requestFocus();
            return false;
        }

        if (CAMPO_VAZIO.equals(codigoSessao)) {
            etCodigoSessao.setError("Informe o código da sessão");
            etCodigoSessao.requestFocus();
            return false;
        }

        return true;
    }

    private EditText etCodigo;
    private EditText etTitulo;
    private EditText etAutor;
    private EditText etCodigoSessao;
    private String codigo;
    private String titulo;
    private String autor;
    private String codigoSessao;
    private ProgressDialog pDialog;
}
