package com.example.android.books.interfaces;

import com.example.android.books.model.Sessao;
import com.example.android.books.model.TokenAuthentication;

import java.util.List;

public interface IToken {
    void inserir(TokenAuthentication token, String username);
    List<TokenAuthentication> getTokens();
    TokenAuthentication atualizarStatus(String token);
}
