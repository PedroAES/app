package com.example.android.books.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.books.model.Livro;

import java.util.ArrayList;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.CardViewHolder>{

    private ArrayList<Livro> livros;

    public LivroAdapter(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    @NonNull
    @Override
    public LivroAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LivroAdapter.CardViewHolder cardViewHolder, int i) {

        Livro livro = livros.get( i );

        cardViewHolder.titulo.setText( livro.getTitulo() );
        cardViewHolder.autor.setText( livro.getAutor() );
        cardViewHolder.codigo.setText( livro.getCodigo() );
        cardViewHolder.codigo_sessao.setText( livro.getCodigoSessao());
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView( recyclerView );
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView codigo;
        TextView titulo;
        TextView autor;
        TextView codigo_sessao;

        public CardViewHolder(@NonNull View itemView) {
            super( itemView );


        }
    }
}
