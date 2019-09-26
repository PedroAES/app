package com.example.android.books.interfaces;

import com.example.android.books.model.Livro;

import java.util.List;

public interface ILivro {
    void inserirLivro(Livro livro);
    List<Livro> getAllLivros();
}
