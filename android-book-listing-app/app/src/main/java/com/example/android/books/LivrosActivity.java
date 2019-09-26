package com.example.android.books;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LivrosActivity extends AppCompatActivity {
    private List<Livro> livros = new ArrayList<>();
    private RecyclerView recyclerView;
    private LivroAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livros_activity);
        recyclerView = (RecyclerView) findViewById(R.id.ecycler_view);

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

        livro = new Livro("Tecnicas Para Apavorar", "Hashtag", "12", "AC");
        livros.add(livro);

        livro = new Livro("Arquitetura de Software", "Igão", "12", "AC");
        livros.add(livro);

        mAdapter.notifyDataSetChanged();
    }
}
