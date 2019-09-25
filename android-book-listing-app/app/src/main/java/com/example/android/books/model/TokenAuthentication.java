package com.example.android.books.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenAuthentication {
    @SerializedName( "token" )
    @Expose
    private String token;
    private String username;
    private int status;

    public TokenAuthentication() {
    }

    public TokenAuthentication(String token, String username, int status) {
        this.token = token;
        this.username = username;
        this.status = status;
    }

    public TokenAuthentication(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
