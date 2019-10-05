package com.example.android.livros.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.livros.DAO.TokenDAO;
import com.example.android.livros.R;
import com.example.android.livros.model.Emprestimo;
import com.example.android.livros.model.Livro;
import com.example.android.livros.model.Sessao;
import com.example.android.livros.model.TokenAuthentication;
import com.example.android.livros.model.Usuario;
import com.example.android.livros.retrofit.RetrofitConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busca_activity);
		userBusca = findViewById(R.id.entrada);
        tokenDAO = new TokenDAO( this);
		logado = tokenDAO.getUsuarioLogado();
		final ImageButton buscar = findViewById(R.id.btn_buscar);
		userBusca.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					buscar.performClick();

					return true;
				}
				return false;
			}
		});
		final ImageButton listarEmprestimos = findViewById( R.id.btn_emprestimos );

		sessoes();
		getUsuario();
	}

    @Override
    protected void onStart() {
        super.onStart();
        buscarLivros();
        emprestimosUsuario();
    }

    public void buscar(View view) {
//		EditText entrada = findViewById(R.id.entrada);
//		String input = entrada.getText().toString();
		int id = view.getId();

		if (id == R.id.btn_buscar) {
			String titulo = userBusca.getText().toString();
			Intent i = new Intent( this, ListarLivrosActivity.class );
			i.putExtra( "titulo", titulo );
			i.putExtra( "lista", (Serializable) livros );
			i.putExtra("emprestimos", (Serializable) emprestimos);
			startActivity( i );

		} else {
		    Intent i = new Intent( this,ListarEmprestimosActivity.class );
		    i.putExtra( "emprestimos", (Serializable) emprestimos );
		    startActivity( i );
		}
	}

	public void setupUI(View view) {
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					v.performClick();
					hideSoftKeyboard(BuscaActivity.this);
					return false;
				}
			});
		}

		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);
				setupUI(innerView);
			}
		}
	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager =
				(InputMethodManager) activity.getSystemService(
						Activity.INPUT_METHOD_SERVICE);

		inputMethodManager.hideSoftInputFromWindow(
				activity.getCurrentFocus().getWindowToken(), 0);
	}

	public void cadastroLivro(View view){
		Intent i = new Intent( this,CadastroLivroActivity.class );
		i.putExtra( "sessoes", (Serializable) sessoes );
		startActivity( i );
	}

	public void buscarLivros(){
		String token = tokenDAO.getTokenPorStatus();
		Call<List<Livro>> call= new RetrofitConfig().getLivroService().getLivros( "Token " + token);
		call.enqueue(new Callback<List<Livro>>() {
			@Override
			public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
				if(response.isSuccessful())
                    livros = response.body();
			}

			@Override
			public void onFailure(Call<List<Livro>> call, Throwable t) {
				Log.e("Livros ", "Erro na conexão:" + t.getMessage());
			}
		});
	}

	public void emprestimosUsuario(){
		TokenAuthentication logado= tokenDAO.getUsuarioLogado();
		matricula = logado.getMatricula();
		Call<List<Emprestimo>> call = new RetrofitConfig().getLivroService().getEmprestimos( "Token "+ logado.getToken());
		call.enqueue( new Callback<List<Emprestimo>>() {
			@Override
			public void onResponse(Call<List<Emprestimo>> call, Response<List<Emprestimo>> response) {
				if(response.isSuccessful())
					emprestimos = response.body();
			}
			@Override
			public void onFailure(Call<List<Emprestimo>> call, Throwable t) {
				Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
			}
		} );
	}

	public void sessoes(){
		Call<List<Sessao>> call = new RetrofitConfig().getLivroService().getSessoes( "Token " + logado.getToken() );
		call.enqueue( new Callback<List<Sessao>>() {
			@Override
			public void onResponse(Call<List<Sessao>> call, Response<List<Sessao>> response) {
				if(response.isSuccessful())
					sessoes = response.body();
			}
			@Override
			public void onFailure(Call<List<Sessao>> call, Throwable t) {
				Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
			}
		} );
	}
	
	public void sair(View view){
		int id = view.getId();
		if(id == R.id.sair){
			Toast.makeText( getBaseContext(),"Logout realizado com sucesso!!", Toast.LENGTH_SHORT ).show();
			tokenDAO.altera( logado, 0 );
			startActivity(new Intent( getBaseContext(), LoginActivity.class )  );
		}
		//sai e remove credenciais
	}

	public void getUsuario(){
		Call<Usuario> call = new RetrofitConfig().getLivroService().getUsuario( "Token "+ logado.getToken(),logado.getMatricula() );
		call.enqueue( new Callback<Usuario>() {
			@Override
			public void onResponse(Call<Usuario> call, Response<Usuario> response) {
				if(response.isSuccessful())
					usuario = response.body();
			}

			@Override
			public void onFailure(Call<Usuario> call, Throwable t) {
				Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
			}
		} );
	}

	private EditText userBusca;
    private TokenDAO tokenDAO;
    private List<Livro> livros;
    private Emprestimo emprestimo;
	private List<Emprestimo> emprestimos;
    private List<Emprestimo> emprestimosUsuario= new ArrayList<>(  );
	private String matricula;
	private TokenAuthentication logado;
	private List<Sessao> sessoes = new ArrayList<>(  );
	private Usuario usuario;
}
