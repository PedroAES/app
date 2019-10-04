package com.example.android.books.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.books.DAO.TokenDAO;
import com.example.android.books.R;
import com.example.android.books.model.Emprestimo;
import com.example.android.books.model.Livro;
import com.example.android.books.model.Sessao;
import com.example.android.books.model.TokenAuthentication;
import com.example.android.books.retrofit.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroLivroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.livro_cadastro_activity);
        tokenDAO = new TokenDAO( this);
        logado = tokenDAO.getUsuarioLogado();
        etCodigo = findViewById(R.id.codigo);
        etTitulo = findViewById(R.id.titulo);
        etAutor = findViewById(R.id.autor);
        sessao = findViewById( R.id.spinner_sessoes );
//        etCodigoSessao = findViewById(R.id.codigo_sessao);
        sessoes= (List<Sessao>) getIntent().getSerializableExtra( "sessoes" );
        final List<String> codigos_sessoes = new ArrayList<>(  );
        codigos_sessoes.add( "Código da sessão" );
        for(Sessao s: sessoes)
            codigos_sessoes.add( s.getCodigo() );

        dataAdapter = new ArrayAdapter<>( this,R.layout.support_simple_spinner_dropdown_item, codigos_sessoes);
        sessao.setAdapter( dataAdapter );
        sessao.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position >0)
                    codigoSessao = codigos_sessoes.get( position );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }

    public void cadastrarLivro(View view){
        codigo = etCodigo.getText().toString().toLowerCase().trim();
        titulo = etTitulo.getText().toString().trim();
        autor = etAutor.getText().toString().trim();
//        codigoSessao = etCodigoSessao.getText().toString().trim();

        if(validarCampos()){
            livro = new Livro( codigo,titulo,autor,codigoSessao );
            efetuarCadastro();
        }
    }

    private void efetuarCadastro(){
        //telaDeCarregamento();
        cadastrarLivroPost();
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

    public void cadastrarLivroPost(){
        Call<Livro> call = new RetrofitConfig().getLivroService().addLivros( "Token "+ logado.getToken(), livro );
        call.enqueue( new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if(response.isSuccessful())
                    Toast.makeText( getBaseContext(),"Cadastro de livro realizado com sucesso!", Toast.LENGTH_SHORT ).show();
                else
                    Toast.makeText( getBaseContext(),"Erro no cadastro!", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
            }
        } );
    }


    private EditText etCodigo;
    private EditText etTitulo;
    private EditText etAutor;
    private EditText etCodigoSessao;
    private Spinner sessao;
    private String codigo;
    private String titulo;
    private String autor;
    private String codigoSessao;
    private ProgressDialog pDialog;
    private Livro livro;
    private TokenDAO tokenDAO;
    private TokenAuthentication logado;
    private List<Sessao> sessoes = new ArrayList<>(  );
    private ArrayAdapter<String> dataAdapter;
}
