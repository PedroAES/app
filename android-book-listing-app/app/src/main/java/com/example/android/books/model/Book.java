package com.example.android.books.model;

public class Book{

	public Book(String titulo, String autor, float rating, float preco) {
		this.titulo = titulo;
		this.autor = autor;
		this.mRating = rating;
		this.preco = preco;
	}

	public String getTitulo() {
		return titulo;
	}
	public String getAutor() {
		return autor;
	}
	public float getRating() {
		return mRating;
	}
	public float getPreco() {
		return preco;
	}

	private String titulo;
	private String autor;
	private float mRating;
	private float preco;
}
