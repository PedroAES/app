package com.example.android.books.model;

public class TokenAuthentication {
    private String token;

    public TokenAuthentication(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
