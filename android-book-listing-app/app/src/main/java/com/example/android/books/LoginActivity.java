package com.example.android.books;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.books.DAO.TokenDAO;
import com.example.android.books.model.TokenAuthentication;
import com.example.android.books.model.Usuario;
import com.example.android.books.retrofit.RetrofitConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize activity on main thread.
        // Bundle holds previous state when re-initialized
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Inflate the activity's UI
        tokenDAO = new TokenDAO( this);
        setContentView(R.layout.login_activity);
        etUsername = findViewById(R.id.etLoginUserName);
        etSenha = findViewById(R.id.etLoginSenha);
        verificarTokenBanco();
    }

    public void cadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void login(View view){
        username = etUsername.getText().toString().toLowerCase().trim();
        senha = etSenha.getText().toString().trim();
		usuario = new Usuario(username, senha);
		if(validarCampos())
		    efetuarLogin();
    }

    public void verificarTokenBanco(){
        tokens= tokenDAO.getTokens();
        if(tokens!= null){
            for(TokenAuthentication t : tokens){
                if(t.getStatus()==1){
                    tokenAuthentication = new TokenAuthentication( t.getToken(),t.getUsername(),t.getStatus() );
                    break;
                }
            }
            telaBusca();
        }
    }

    private void telaDeCarregamento() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Entrando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private boolean validarCampos() {
        String CAMPO_VAZIO = "";
        if(CAMPO_VAZIO.equals(username)){
            etUsername.setError("Informe o seu username");
            etUsername.requestFocus();
            return false;
        }

        if(CAMPO_VAZIO.equals(senha)){
            etSenha.setError("Informe a sua senha");
            etSenha.requestFocus();
            return false;
        }

        return true;
    }

    public void telaBusca(){
        Intent i = new Intent(getBaseContext(), BuscaActivity.class);
        startActivity(i);
        finish();
    }

	public void efetuarLogin(){
        Call<TokenAuthentication> call = new RetrofitConfig().getLivroService().loginUsuario( usuario);
        call.enqueue( new Callback<TokenAuthentication>() {
            @Override
            public void onResponse(Call<TokenAuthentication> call, Response<TokenAuthentication> response) {
                if(response.isSuccessful()){
                    tokenAuthentication = response.body();
                    TokenAuthentication banco = tokenDAO.atualizarStatus( tokenAuthentication.getToken() );
                    if(banco == null)
                        tokenDAO.inserir( tokenAuthentication,username );
                    else
                        tokenAuthentication = banco;
                    telaDeCarregamento();
                    telaBusca();
                }else{
                    Toast.makeText(getBaseContext(), "Erro na conexão", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TokenAuthentication> call, Throwable t) {
                Log.e("Token   ", "Erro ao buscar o token:" + t.getMessage());
            }
        } );
	}

    public TokenAuthentication getTokenAuthentication() {
        return tokenAuthentication;
    }

    private EditText etUsername;
    private EditText etSenha;
    private String username;
    private String senha;
    private ProgressDialog pDialog;
	private Usuario usuario;
	private TokenDAO tokenDAO;
	private TokenAuthentication tokenAuthentication;
	private List<TokenAuthentication> tokens;
}
