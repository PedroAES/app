package com.example.android.books;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroViewHolder> {

    List<Livro> livros;

    public LivroAdapter(List livros) {
        this.livros = livros;
    }

    @Override
    public LivroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.livro_row, parent, false);

        return new LivroViewHolder(itemView);
    }

    @Override public int getItemCount() {
        return livros.size();
    }

    @Override
    public void onBindViewHolder(LivroViewHolder holder , int position) {
        holder.titulo.setText(livros.get(position).getTitulo());
        holder.autor.setText(livros.get(position).getAutor());
    }

    public class LivroViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo;
        public TextView autor;

        public LivroViewHolder(View view) {
            super(view);
            titulo = (TextView) view.findViewById(R.id.ltitulo);
            autor = (TextView) view.findViewById(R.id.lautor);
        }
    }
}
