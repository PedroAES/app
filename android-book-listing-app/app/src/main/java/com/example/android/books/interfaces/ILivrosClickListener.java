package com.example.android.books.interfaces;

import android.view.View;

public interface ILivrosClickListener {
    void onClick(View view, int indice);
    void onLongClick(View view, int indice);
}
