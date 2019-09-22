package com.example.android.books;

public class Book{

	Book(String titulo, String autor, float rating, float preco) {
		this.titulo = titulo;
		this.autor = autor;
		this.mRating = rating;
		this.preco = preco;
	}

	String getTitulo() {
		return titulo;
	}
	String getAutor() {
		return autor;
	}
	float getRating() {
		return mRating;
	}
	float getPreco() {
		return preco;
	}

	private String titulo;
	private String autor;
	private float mRating;
	private float preco;
}
