package com.example.android.books.activity;

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

        for(Livro livro: livrosTotal)
            if(titulo.equalsIgnoreCase( livro.getTitulo() ))
                livrosEscolhido.add( livro );

        if(livrosEscolhido.size() == 0)
            Toast.makeText(ListarLivrosActivity.this, "Não há livros com esse título.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),
                        livrosEscolhido.get(indice).getTitulo() + " de "  + livrosEscolhido.get(indice).getAutor()
                        + " foi clicado!", Toast.LENGTH_SHORT).show();
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

    private TokenDAO tokenDAO;
    private List<Livro> livrosTotal;
    private List<Livro> livrosEscolhido = new ArrayList<>(  );
    private RecyclerView rv;
    private LivroDAO livroDAO;
}
