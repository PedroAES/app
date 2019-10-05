package com.example.android.livros.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.livros.DAO.TokenDAO;
import com.example.android.livros.R;
import com.example.android.livros.model.TokenAuthentication;
import com.example.android.livros.model.Usuario;
import com.example.android.livros.retrofit.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tokenDAO = new TokenDAO( this);
        tokens= tokenDAO.getTokens();
        setContentView(R.layout.login_activity);
        etUsername = findViewById(R.id.etLoginUserName);
        etSenha = findViewById(R.id.etLoginSenha);
        verificarTokenBanco();
        getUsuarios();
    }

    public void cadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void login(View view){
        username = etUsername.getText().toString().toLowerCase().trim();
        senha = etSenha.getText().toString().trim();
        String matricula = null;
        if(tokens.size() > 0){
            for(TokenAuthentication t: tokens)
                if(username.equalsIgnoreCase( t.getUsername() ) && t.getStatus() == 0){
                    tokenDAO.altera( t,1 );
                    break;
                }
            telaDeCarregamento();
            telaBusca();
        }else{
            usuario = new Usuario(username, senha);
            if(validarCampos()){
                for(Usuario us : usuarios){
                    if(username.equalsIgnoreCase( us.getUsername() ))
                        matricula = us.getMatricula();
                }
                efetuarLogin(matricula);
            }
        }
    }

    public void verificarTokenBanco(){
        if(tokens.size() > 0){
            for(TokenAuthentication t : tokens){
                if(t.getStatus()==1){
                    tokenAuthentication = new TokenAuthentication( t.getToken(),t.getUsername(),t.getStatus(), t.getMatricula() );
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

	public void efetuarLogin(final String matricula) {
        Call<TokenAuthentication> call = new RetrofitConfig().getLivroService().loginUsuario( usuario );
        call.enqueue( new Callback<TokenAuthentication>() {
            @Override
            public void onResponse(Call<TokenAuthentication> call, Response<TokenAuthentication> response) {
                if (response.isSuccessful()) {
                    tokenAuthentication = response.body();
                    boolean verifica = false;
                    for (TokenAuthentication t : tokens)
                        if (t.getToken().equalsIgnoreCase( tokenAuthentication.getToken() )) {
                            tokenAuthentication = t;
                            verifica = true;
                            break;
                        }
                    if (!verifica)
                        tokenDAO.inserir( tokenAuthentication, username, matricula );
                    telaDeCarregamento();
                    telaBusca();
                } else {
                    Toast.makeText( getBaseContext(), "Senha ou usuário inválido!!", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<TokenAuthentication> call, Throwable t) {
                Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
            }
        } );
    }

    public void getUsuarios() {
        Call<List<Usuario>> call = new RetrofitConfig().getLivroService().getUsuarios();
        call.enqueue( new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(response.isSuccessful()){
                    usuarios = response.body();
                }else
                    Toast.makeText(getBaseContext(), "Erro para buscar usuários", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("Token   ", "Erro ao buscar o token:" + t.getMessage());
            }
        } );
    }

    private EditText etUsername;
    private EditText etSenha;
    private String username;
    private String senha;
    private ProgressDialog pDialog;
	private Usuario usuario;
	private TokenDAO tokenDAO;
	private TokenAuthentication tokenAuthentication;
	private ArrayList<TokenAuthentication> tokens;
	private List<Usuario> usuarios;

}
