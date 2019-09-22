package com.example.android.books;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize activity on main thread.
        // Bundle holds previous state when re-initialized
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Inflate the activity's UI
        setContentView(R.layout.login_activity);
        etEmail = findViewById(R.id.etLoginEmail);
        etSenha = findViewById(R.id.etLoginSenha);
    }

    public void cadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void login(View view){
        email = etEmail.getText().toString().toLowerCase().trim();
        senha = etSenha.getText().toString().trim();

        if(validarCampos())
            efetuarLogin();
    }

    private void telaDeCarregamento() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Entrando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void efetuarLogin(){
        telaDeCarregamento();
        Intent i = new Intent(this, BuscaActivity.class);
        startActivity(i);
        finish();
    }

    private boolean validarCampos() {
        String CAMPO_VAZIO = "";
        if(CAMPO_VAZIO.equals(email)){
            etEmail.setError("Informe o seu E-mail");
            etEmail.requestFocus();
            return false;
        }

        if(CAMPO_VAZIO.equals(senha)){
            etSenha.setError("Informe a sua senha");
            etSenha.requestFocus();
            return false;
        }

        return true;
    }

    private EditText etEmail;
    private EditText etSenha;
    private String email;
    private String senha;
    private ProgressDialog pDialog;
}
