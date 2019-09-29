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
import com.example.android.books.model.Livro;

import java.util.ArrayList;
import java.util.List;

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
                        //pegar a matricula do usuário na tabela do token
                        //pegar a data do sistema do dia e da devolução
                    }
                } )
                .setNegativeButton( "Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } )
                .show();
    }

    private TokenDAO tokenDAO;
    private List<Livro> livrosTotal;
    private List<Livro> livrosEscolhido = new ArrayList<>(  );
    private RecyclerView rv;
    private LivroDAO livroDAO;
}
