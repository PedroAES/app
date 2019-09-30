package com.example.android.books.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.books.DAO.LivroDAO;
import com.example.android.books.DAO.TokenDAO;
import com.example.android.books.R;
import com.example.android.books.adapter.LivroAdapter;
import com.example.android.books.adapter.LivrosTouchListener;
import com.example.android.books.interfaces.ILivrosClickListener;
import com.example.android.books.model.Emprestimo;
import com.example.android.books.model.Livro;
import com.example.android.books.model.TokenAuthentication;
import com.example.android.books.retrofit.RetrofitConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListarLivrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_listar_livros );
        livroDAO = new LivroDAO( this );
        tokenDAO = new TokenDAO( this);
        rv = (RecyclerView)findViewById( R.id.rv);

        String titulo = getIntent().getStringExtra( "titulo" );
        livrosTotal = (List<Livro>) getIntent().getSerializableExtra( "lista" );

        if(titulo != null){
            for(Livro livro: livrosTotal)
                if(livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                    livrosEscolhido.add( livro );
        }else //n tendo nada no edite, mostra todos os livros
            livrosEscolhido = livrosTotal;

        if(livrosEscolhido.size() == 0)
            Toast.makeText(ListarLivrosActivity.this, "Não há livros no acervo.", Toast.LENGTH_SHORT).show();
        else{
            LivroAdapter adapter = new LivroAdapter( livrosEscolhido );
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
            rv.setLayoutManager( layoutManager );
            rv.setHasFixedSize( true );
            rv.addItemDecoration( new DividerItemDecoration( this, LinearLayout.VERTICAL ) );
            rv.setAdapter( adapter );
            rv.addOnItemTouchListener(new LivrosTouchListener(getApplicationContext(), rv, new ILivrosClickListener() {
            @Override
            public void onClick(View view, int indice) {
                confirmarAluguel(indice);
//                Toast.makeText(getApplicationContext(),
//                        livrosEscolhido.get(indice).getTitulo() + " de "  + livrosEscolhido.get(indice).getAutor()
//                        + " foi clicado!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int indice) {
                Toast.makeText(getApplicationContext(),
                        livrosEscolhido.get(indice).getTitulo() + " de "  + livrosEscolhido.get(indice).getAutor()
                                + " foi pressionado!", Toast.LENGTH_SHORT).show();
            }
        }));
        }
    }

    public void confirmarAluguel(final int indice){
        new AlertDialog.Builder(this)
                .setTitle( "Empréstimo" )
                .setMessage( "Deseja fazer o empréstimo do livro "+ livrosEscolhido.get( indice ).getTitulo()+ " ?" )
                .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Livro livro = livrosEscolhido.get( indice );
                        TokenAuthentication logado= tokenDAO.getUsuarioLogado();
                        matricula = logado.getMatricula();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        String dataFormatada= dateFormat.format(date);
                        String data_emprestimo= formatoData( dataFormatada, 0 );
                        String data_devolucao= formatoData( dataFormatada, 1 );
                        emprestimosUsuario(logado);

                        //ver se a data é a mesma
                    }
                } )
                .setNegativeButton( "Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } )
                .show();
    }

    @SuppressWarnings("deprecation")
    public String formatoData(String dataFormatada, int tipo_data){
        String data_ = "";
        //2019-09-10T12:00:00-03:00
        String data = dataFormatada.split( " " )[0];
        String hora = dataFormatada.split( " " )[1];
        if(tipo_data == 1){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date a = new Date();
            Calendar c = Calendar.getInstance();
            try {
                a = dateFormat.parse( data );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setTime( a );
            c.add(c.DATE, +10);
            a = c.getTime();
            data = dateFormat.format(a);
        }
        data_+=data + "T" + hora + "-03:00";
        return data_;
    }

    public void emprestimosUsuario(TokenAuthentication t){
        Call<List<Emprestimo>> call = new RetrofitConfig().getLivroService().getEmprestimos( "Token "+ t.getToken() );
        call.enqueue( new Callback<List<Emprestimo>>() {
            @Override
            public void onResponse(Call<List<Emprestimo>> call, Response<List<Emprestimo>> response) {
                if(response.isSuccessful()){
                    emprestimos = response.body();
                    for(Emprestimo emprestimo: emprestimos)
                        if(matricula.equalsIgnoreCase( emprestimo.getMatricula_usuario()))
                            emprestimosUsuario.add( emprestimo );
                }
            }
            @Override
            public void onFailure(Call<List<Emprestimo>> call, Throwable t) { }
        } );
    }

    public int verificaDados(String data){
        for(Emprestimo e : emprestimosUsuario){
            //verificar data

        }
        return 0;
    }

    private TokenDAO tokenDAO;
    private List<Livro> livrosTotal;
    private List<Livro> livrosEscolhido = new ArrayList<>(  );
    private RecyclerView rv;
    private LivroDAO livroDAO;
    private List<Emprestimo> emprestimos;
    private List<Emprestimo> emprestimosUsuario= new ArrayList<>(  );
    private String matricula;
}
