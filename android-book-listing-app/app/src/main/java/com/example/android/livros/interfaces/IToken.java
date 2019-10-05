package com.example.android.livros.interfaces;

import com.example.android.livros.model.TokenAuthentication;

import java.util.ArrayList;

public interface IToken {
    void inserir(TokenAuthentication token, String username, String matricula);
    ArrayList<TokenAuthentication> getTokens();
    String getTokenPorStatus();
    TokenAuthentication getUsuarioLogado();
    void altera(TokenAuthentication token, int status);
}
