package com.example.android.livros.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.livros.R;
import com.example.android.livros.model.Livro;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.CardViewHolder>{

    private List<Livro> livros;

    public LivroAdapter(List<Livro> livros) {
        this.livros = livros;
    }

    @NonNull
    @Override
    public LivroAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.items_livros, viewGroup, false );

        return new CardViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull LivroAdapter.CardViewHolder cardViewHolder, int i) {

        Livro livro = livros.get( i );

        cardViewHolder.titulo.setText( livro.getTitulo() );
        cardViewHolder.autor.setText( livro.getAutor() );
        cardViewHolder.codigo.setText("Cód: "+ livro.getCodigo() );
        cardViewHolder.codigo_sessao.setText( "Sessão: "+ livro.getCodigo_sessao());
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
        TextView titulo;
        TextView autor;
        TextView codigo;
        TextView codigo_sessao;

        public CardViewHolder(@NonNull View itemView) {
            super( itemView );
            titulo = (TextView)itemView.findViewById( R.id.titulo );
            autor = (TextView)itemView.findViewById( R.id.autor);
            codigo = (TextView)itemView.findViewById( R.id.livro_cod );
            codigo_sessao = (TextView)itemView.findViewById( R.id.sessao_cod );

        }
    }
}
