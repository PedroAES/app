package com.example.android.livros.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.livros.DAO.TokenDAO;
import com.example.android.livros.R;
import com.example.android.livros.adapter.LivroAdapter;
import com.example.android.livros.adapter.LivrosTouchListener;
import com.example.android.livros.interfaces.ILivrosClickListener;
import com.example.android.livros.model.Emprestimo;
import com.example.android.livros.model.Livro;
import com.example.android.livros.model.TokenAuthentication;
import com.example.android.livros.retrofit.RetrofitConfig;

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
        tokenDAO = new TokenDAO( this);
        rv = (RecyclerView)findViewById( R.id.rv);
        logado= tokenDAO.getUsuarioLogado();
        titulo = getIntent().getStringExtra( "titulo" );
        livrosTotal = (List<Livro>) getIntent().getSerializableExtra( "lista" );
        emprestimos= (List<Emprestimo>) getIntent().getSerializableExtra( "emprestimos" );
        for(Emprestimo emprestimo: emprestimos)
            if(logado.getMatricula().equalsIgnoreCase( emprestimo.getMatricula_usuario()))
                emprestimosUsuario.add( emprestimo );

        if(titulo != ""){
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

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date();
                        String dataFormatada= dateFormat.format(date);
                        String data_emprestimo= formatoData( dataFormatada, 0 );
                        String data_devolucao= formatoData( dataFormatada, 1 );
                        //emprestimosUsuario(logado);

                        int result =verificaDados(dataFormatada);

                        if(result==1){
                            atualizaEmprestimo(logado);
                        }else if(result==0){
                            //VERIFICAR QUANDO N TIVER NENHUM CADASTRO
                            String cod_cadastro= "";
                            String codigo= "";
                            if(emprestimos.size()>0)
                                codigo = emprestimos.get( emprestimos.size()-1 ).getCodigo();
                            else
                                codigo="0000";

                            for(int i=0; i< codigo.length(); i++)
                                if(codigo.charAt(i) == '0')
                                    cod_cadastro+="0";
                            int cod = Integer.parseInt(codigo) + 1;
                            cod_cadastro+= String.valueOf( cod );
                            emprestimo = new Emprestimo(cod_cadastro,logado.getMatricula(),data_emprestimo,data_devolucao );
                            if(titulo.equals( "" ))
                                titulo = livrosEscolhido.get( indice ).getTitulo();
                            emprestimo.getEmprestimos().add( titulo );
                            novoEmprestimo(logado);
                        }

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
                        if(logado.getMatricula().equalsIgnoreCase( emprestimo.getMatricula_usuario()))
                            emprestimosUsuario.add( emprestimo );
                }
            }
            @Override
            public void onFailure(Call<List<Emprestimo>> call, Throwable t) {
                Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
            }
        } );
    }

    public int verificaDados(String data){
        for(Emprestimo e : emprestimosUsuario){
            String data_emp = e.getData_emprestimo().split("T")[0];
            String data_sist = data.split(" ")[0];
            if(data_emp.equalsIgnoreCase(data_sist)){
                //mesmo dia, livro só é acrescentado e faz o put em vez do post
                emprestimo = e;
                emprestimo.getEmprestimos().add( titulo );
                return 1;
            }
        }
        return 0;
    }

    //PUT
    public void atualizaEmprestimo(TokenAuthentication t){
        Call<Emprestimo> call = new RetrofitConfig().getLivroService().updateEmprestimo( "Token "+t.getToken(),emprestimo.getCodigo(),emprestimo );
        call.enqueue( new Callback<Emprestimo>() {
            @Override
            public void onResponse(Call<Emprestimo> call, Response<Emprestimo> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(),
                            "Empréstimo do livro "+ titulo + " realizado com sucesso!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Emprestimo> call, Throwable t) {
                Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
            }
        } );
    }

    //POST
    public void novoEmprestimo(TokenAuthentication t){
        Call<Emprestimo> call= new RetrofitConfig().getLivroService().addEmprestimo( "Token "+ t.getToken(), emprestimo );
        call.enqueue( new Callback<Emprestimo>() {
            @Override
            public void onResponse(Call<Emprestimo> call, Response<Emprestimo> response) {
                if(response.isSuccessful())
                    Toast.makeText(getApplicationContext(),
                            "Empréstimo do livro "+ titulo + " realizado com sucesso!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Emprestimo> call, Throwable t) {
                Log.e( "Token   ", "Erro ao buscar o token:" + t.getMessage() );
            }
        } );
    }

    private String titulo;
    private TokenDAO tokenDAO;
    private List<Livro> livrosTotal;
    private List<Livro> livrosEscolhido = new ArrayList<>(  );
    private RecyclerView rv;
    private List<Emprestimo> emprestimos;
    private List<Emprestimo> emprestimosUsuario= new ArrayList<>(  );
    private Emprestimo emprestimo;
    TokenAuthentication logado;
}