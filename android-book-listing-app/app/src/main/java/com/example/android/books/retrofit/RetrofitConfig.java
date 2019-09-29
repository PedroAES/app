package com.example.android.books.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static Retrofit retrofit = null;
    private static String baseUrl= "http://192.168.15.3:8080/";

    public RetrofitConfig() {
        retrofit = new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory( GsonConverterFactory.create()  )
                .build();
    }
	
	public LivroService getLivroService() {
        return retrofit.create( LivroService.class );
    }
}

