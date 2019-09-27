package com.example.android.books.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.books.DAO.LivroDAO;
import com.example.android.books.R;
import com.example.android.books.adapter.LivroAdapter;
import com.example.android.books.adapter.RecyclerItemClickListener;
import com.example.android.books.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class ListarLivrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_listar_livros );
        livroDAO = new LivroDAO( this );
        rv = (RecyclerView)findViewById( R.id.rv);
        String titulo = getIntent().getStringExtra( "titulo" );

        livros = livroDAO.getAllLivros();
        ArrayList<Livro> lista= new ArrayList<>();
        for(Livro livro: livros){
            if(titulo.equalsIgnoreCase( livro.getTitulo() ))
                lista.add(livro);
        }

        if(lista.size() == 0)
            Toast.makeText(ListarLivrosActivity.this, "Não há livros com esse título.", Toast.LENGTH_SHORT).show();
        else{
            LivroAdapter adapter = new LivroAdapter( lista );
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
            rv.setLayoutManager( layoutManager );
            rv.setHasFixedSize( true );
            rv.addItemDecoration( new DividerItemDecoration( this, LinearLayout.VERTICAL ) );
            rv.setAdapter( adapter );
            rv.addOnItemTouchListener(
                    new RecyclerItemClickListener( getBaseContext(), rv, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText( ListarLivrosActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    } )
            );
        }

    }

    private ArrayList<Livro> livros;
    private RecyclerView rv;
    private LivroDAO livroDAO;
}
