package com.example.android.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.books.DAO.LivroDAO;
import com.example.android.books.model.Livro;
import com.example.android.books.retrofit.RetrofitConfig;

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
	}

	public void buscar(View view) {
		EditText entrada = findViewById(R.id.entrada);
		String input = entrada.getText().toString();

		if (!input.isEmpty()) {


			Toast.makeText(BuscaActivity.this, userBusca.getText(), Toast.LENGTH_SHORT).show();
//			Intent results = new Intent(BuscaActivity.this, QueryResultsActivity.class);
//			results.putExtra("topic", userBusca.getText().toString().toLowerCase());
//			startActivity(results);

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
		//buscar o token no banco com status igual a 1
		//chamar login não pode
		Call<List<Livro>> call= new RetrofitConfig().getLivroService().getLivros(loginActivity.getTokenAuthentication().getToken());
		call.enqueue(new Callback<List<Livro>>() {
			@Override
			public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
				if(response.isSuccessful()){
					livros = response.body();
					for(Livro livro: livros)
						livroDAO.inserirLivro(livro);
				}
			}

			@Override
			public void onFailure(Call<List<Livro>> call, Throwable t) {

			}
		});
	}

	private EditText userBusca;
	private LivroDAO livroDAO;
	private Livro livro;
	private List<Livro> livros;
	private LoginActivity loginActivity;
}
