package com.example.android.books.interfaces;
import android.view.View;

public interface LivrosClickListener {
    void onClick(View view, int indice);
    void onLongClick(View view, int indice);
}
