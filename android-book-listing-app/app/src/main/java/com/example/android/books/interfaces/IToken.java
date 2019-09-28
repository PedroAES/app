package com.example.android.books.interfaces;

import com.example.android.books.model.Sessao;
import com.example.android.books.model.TokenAuthentication;

import java.util.ArrayList;
import java.util.List;

public interface IToken {
    void inserir(TokenAuthentication token, String username, String matricula);
    ArrayList<TokenAuthentication> getTokens();
    String getTokenPorStatus();
}
