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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.books.livroutils.CadastroLivroActivity;
import com.example.android.books.livroutils.LivrosActivity;

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
	}

	public void buscar(View view) {
		EditText entrada = findViewById(R.id.entrada);
		String input = entrada.getText().toString();

		if (!input.isEmpty()) {
//			Intent results = new Intent(BuscaActivity.this, LivrosActivity.class);
//			results.putExtra("topic", userBusca.getText().toString().toLowerCase());
//			startActivity(results);
			startActivity(new Intent(this, LivrosActivity.class));

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



	private EditText userBusca;
}
