package com.example.android.books.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.books.R;
import com.example.android.books.model.Livro;
import com.example.android.books.model.Usuario;
import com.example.android.books.retrofit.RetrofitConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            usuario = new Usuario( nome,senha,email,matricula,endereco,telefone );
            efetuarRegistro();
        }
    }

    private void efetuarRegistro(){
        cadastroUsuario();
        //telaDeCarregamento();
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

    public void cadastroUsuario(){
        Call<Usuario> call= new RetrofitConfig().getLivroService().addUsuarios( usuario );
        call.enqueue( new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful())
                    Toast.makeText( getBaseContext(),"Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT ).show();
                else
                    Toast.makeText( getBaseContext(),"Erro no cadastro!", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Livros ", "Erro na conexão:" + t.getMessage());
            }
        } );
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
    private Usuario usuario;
}
