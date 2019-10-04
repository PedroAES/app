package com.example.android.books.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.books.DAO.TokenDAO;
import com.example.android.books.adapter.EmprestimosAdapter;
import com.example.android.books.EmprestimosRecyclerView;
import com.example.android.books.R;
import com.example.android.books.interfaces.ILivrosClickListener;
import com.example.android.books.model.Emprestimo;
import com.example.android.books.model.TokenAuthentication;

import java.util.ArrayList;
import java.util.List;

public class ListarEmprestimosActivity extends AppCompatActivity {
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private List<Emprestimo> emprestimosUsuarios = new ArrayList<>();
    private EmprestimosAdapter mAdapter;
    private TokenDAO tokenDAO;
    private TokenAuthentication logado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.listar_emprestimos_activity);
        tokenDAO = new TokenDAO( this);
        logado = tokenDAO.getUsuarioLogado();
        emprestimos = (List<Emprestimo>)getIntent().getSerializableExtra( "emprestimos" );
        for(Emprestimo e: emprestimos)
            if(e.getMatricula_usuario().equalsIgnoreCase( logado.getMatricula() ))
                emprestimosUsuarios.add( e );

        RecyclerView recyclerView = findViewById(R.id.recycler_view_emprestimos);
        mAdapter = new EmprestimosAdapter(this,emprestimosUsuarios);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayout.VERTICAL ) );
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new EmprestimosRecyclerView(getApplicationContext(), recyclerView, new ILivrosClickListener() {
            @Override
            public void onClick(View view, int indice) {
                Toast.makeText(getApplicationContext(),
                        emprestimosUsuarios.get(indice).getCodigo() + " foi clicado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int indice) {
                Toast.makeText(getApplicationContext(),
                        emprestimosUsuarios.get(indice).getCodigo() +" foi pressionado!", Toast.LENGTH_SHORT).show();
            }
        }));


        //initEmprestimoData();
    }

    private void initEmprestimoData() {
        Emprestimo emprestimo = new Emprestimo("12", "54", "12/01/1993", "31/01/1999");
//        emprestimo.setEmprestimos("livro1");
//        emprestimo.setEmprestimos("livro2");
//        emprestimo.setEmprestimos("livro3");
        emprestimos.add(emprestimo);

        emprestimo = new Emprestimo("11", "Hashtag", "12/02/2000", "21/11/2001");
        emprestimos.add(emprestimo);

        emprestimo = new Emprestimo("42e", "Igão", "424", "AC");
        emprestimos.add(emprestimo);

        mAdapter.notifyDataSetChanged();
    }

}
