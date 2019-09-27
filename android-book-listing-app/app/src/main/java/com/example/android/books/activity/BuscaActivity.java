package com.example.android.books.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.books.DAO.LivroDAO;
import com.example.android.books.DAO.TokenDAO;
import com.example.android.books.R;
import com.example.android.books.adapter.LivroAdapter;
import com.example.android.books.adapter.RecyclerItemClickListener;
import com.example.android.books.model.Livro;
import com.example.android.books.retrofit.RetrofitConfig;

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
		tokenDAO = new TokenDAO( this);
		livroDAO = new LivroDAO( this );
		userBusca = findViewById(R.id.entrada);
		rv = (RecyclerView)findViewById( R.id.rv);
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

		livros = livroDAO.getAllLivros();
		if(livros.size() == 0)
			buscarLivros();
	}

	public void buscar(View view) {
		EditText entrada = findViewById(R.id.entrada);
		String input = entrada.getText().toString();

		if (!input.isEmpty()) {
			String titulo = userBusca.getText().toString();
			Intent i = new Intent( this, ListarLivrosActivity.class );
			i.putExtra( "titulo", titulo );
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
					for(Livro livro: livros_api){
						livros.add( livro );
						livroDAO.inserirLivro(livro);
					}
				}
			}

			@Override
			public void onFailure(Call<List<Livro>> call, Throwable t) {
				Log.e("Livros ", "Erro na conexão:" + t.getMessage());
			}
		});
	}

	private EditText userBusca;
	private LivroDAO livroDAO;
	private TokenDAO tokenDAO;
	private Livro livro;
	private ArrayList<Livro> livros;
	private RecyclerView rv;
}
