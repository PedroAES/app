package com.example.android.books;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.books.interfaces.LivrosClickListener;

import java.util.ArrayList;
import java.util.List;

public class ListarEmprestimosActivity extends AppCompatActivity {
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private EmprestimosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_emprestimos_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_emprestimos);
        recyclerView.addOnItemTouchListener(new EmprestimosRecyclerView(getApplicationContext(), recyclerView, new LivrosClickListener() {
            @Override
            public void onClick(View view, int indice) {
                Toast.makeText(getApplicationContext(),
                        emprestimos.get(indice).getCodigo() + " foi clicado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int indice) {
                Toast.makeText(getApplicationContext(),
                        emprestimos.get(indice).getCodigo() +" foi pressionado!", Toast.LENGTH_SHORT).show();
            }
        }));
        mAdapter = new EmprestimosAdapter(emprestimos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        initEmprestimoData();
    }

    private void initEmprestimoData() {
        Emprestimo emprestimo = new Emprestimo("12", "54", "12/01/1993", "31/01/1999");
        emprestimo.setEmprestimos("livro1");
        emprestimo.setEmprestimos("livro2");
        emprestimo.setEmprestimos("livro3");
        emprestimos.add(emprestimo);

        emprestimo = new Emprestimo("11", "Hashtag", "12/02/2000", "21/11/2001");
        emprestimos.add(emprestimo);

        emprestimo = new Emprestimo("42e", "Igão", "424", "AC");
        emprestimos.add(emprestimo);

        mAdapter.notifyDataSetChanged();
    }

}
