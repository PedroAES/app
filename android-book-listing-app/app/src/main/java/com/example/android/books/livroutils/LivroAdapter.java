package com.example.android.books.livroutils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.books.R;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {

    List<Livro> livros;

    public LivroAdapter(List livros) {
        this.livros = livros;
    }

    @Override
    public LivroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.livro, parent, false);

        return new LivroViewHolder(itemView);
    }

    @Override public int getItemCount() {
        return livros.size();
    }

    @Override
    public void onBindViewHolder(LivroViewHolder holder , int position) {
        holder.titulo.setText(livros.get(position).getTitulo());
        holder.autor.setText(livros.get(position).getAutor());
        holder.cod_livro.setText("Cód: "+livros.get(position).getCodLivro());
        holder.cod_sessao.setText("Sessão: "+livros.get(position).getCodSessao());
    }

    public class LivroViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo;
        public TextView autor;
        public TextView cod_livro;
        public TextView cod_sessao;

        public LivroViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.titulo);
            autor = view.findViewById(R.id.autor);
            cod_livro = view.findViewById(R.id.livro_cod);
            cod_sessao = view.findViewById(R.id.sessao_cod);
        }
    }
}
