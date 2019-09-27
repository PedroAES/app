package com.example.android.books.livroutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.android.books.R;
import com.example.android.books.interfaces.LivrosClickListener;

public class LivrosActivity extends AppCompatActivity {
    private List<Livro> livros = new ArrayList<>();
    private LivroAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livros_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new LivrosTouchListener(getApplicationContext(), recyclerView, new LivrosClickListener() {
            @Override
            public void onClick(View view, int indice) {
                Toast.makeText(getApplicationContext(),
                        livros.get(indice).getTitulo() + " de "  + livros.get(indice).getAutor()
                        + " foi clicado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int indice) {
                Toast.makeText(getApplicationContext(),
                        livros.get(indice).getTitulo() + " de "  + livros.get(indice).getAutor()
                                + " foi pressionado!", Toast.LENGTH_SHORT).show();
            }
        }));
        mAdapter = new LivroAdapter(livros);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        initBookData();
    }

    private void initBookData() {
        Livro livro = new Livro("Arte do Clular", "Pauvo Litor", "12", "AC");
        livros.add(livro);

        livro = new Livro("Tecnicas Para Apavorar Menino Amarelo", "Hashtag", "12", "AC");
        livros.add(livro);

        livro = new Livro("Arquitetura de Software", "Igão", "12", "AC");
        livros.add(livro);

        mAdapter.notifyDataSetChanged();
    }
}
