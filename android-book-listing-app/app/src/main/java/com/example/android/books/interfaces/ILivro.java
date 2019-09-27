package com.example.android.books.interfaces;

import com.example.android.books.model.Livro;

import java.util.ArrayList;
import java.util.List;

public interface ILivro {
    void inserirLivro(Livro livro);
    ArrayList<Livro> getAllLivros();
}
