package com.example.android.books.activity;

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

import com.example.android.books.DAO.TokenDAO;
import com.example.android.books.R;
import com.example.android.books.model.Emprestimo;
import com.example.android.books.model.Livro;
import com.example.android.books.model.TokenAuthentication;
import com.example.android.books.retrofit.RetrofitConfig;

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
        buscarLivros();
        emprestimosUsuario();
	}

	public void buscar(View view) {
		EditText entrada = findViewById(R.id.entrada);
		String input = entrada.getText().toString();

		if (!input.isEmpty()) {
			String titulo = userBusca.getText().toString();
			Intent i = new Intent( this, ListarLivrosActivity.class );
			i.putExtra( "titulo", titulo );
			i.putExtra( "lista", (Serializable) teste );
			i.putExtra("emprestimos", (Serializable) emprestimos);
			startActivity( i );

		} else {
			Toast.makeText(
					BuscaActivity.this,
					getString(R.string.enter_text),
					Toast.LENGTH_SHORT)
					.show();
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
		startActivity(new Intent(this, CadastroLivroActivity.class));
	}

	public void buscarLivros(){
		String token = tokenDAO.getTokenPorStatus();
		Call<List<Livro>> call= new RetrofitConfig().getLivroService().getLivros( "Token " + token);
		call.enqueue(new Callback<List<Livro>>() {
			@Override
			public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
				if(response.isSuccessful()){
					List<Livro> livros_api = response.body();
					teste = livros_api;
				}
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
				if(response.isSuccessful()){
					emprestimos = response.body();
//					for(Emprestimo emprestimo: emprestimos)
//						if(matricula.equalsIgnoreCase( emprestimo.getMatricula_usuario()))
//							emprestimosUsuario.add( emprestimo );
				}
			}
			@Override
			public void onFailure(Call<List<Emprestimo>> call, Throwable t) {
				Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
			}
		} );
	}

	private EditText userBusca;
    private TokenDAO tokenDAO;
    private List<Livro> teste;
    private Emprestimo emprestimo;
	private List<Emprestimo> emprestimos;
    private List<Emprestimo> emprestimosUsuario= new ArrayList<>(  );
	private String matricula;
}
