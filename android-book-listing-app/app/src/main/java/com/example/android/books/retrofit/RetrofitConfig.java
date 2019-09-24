package com.example.android.books.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private static Retrofit retrofit = null;
    private static String baseUrl= "http://127.0.0.1:8080";

    public RetrofitConfig() {
        retrofit = new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory( JacksonConverterFactory.create() )
                .build();
    }
	
	public LivroService getLivroService() {
        return retrofit.create( LivroService.class );
    }
}

