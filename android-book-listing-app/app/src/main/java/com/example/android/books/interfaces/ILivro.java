package com.example.android.books.interfaces;

import com.example.android.books.model.Livro;

import java.util.List;

public interface ILivro {
    List<Livro> buscarLivro(String nomeLivro);
    void inserirLivro(Livro livro);
}
