package com.example.android.books;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.CardViewHolder> {

	private final String LOG_TAG = BookAdapter.class.getSimpleName();
	private List<Book> listaLivros;

	BookAdapter(List<Book> listaLivros) {
	    this.listaLivros = listaLivros;
	}

	@Override
	public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.livro, parent, false);

		return new CardViewHolder(view);
	}


	@Override
	public void onBindViewHolder(CardViewHolder holder, int position) {
		Book currentBook = listaLivros.get(position);
		holder.bookTitle.setText(currentBook.getTitulo());

		try {
			String authors = currentBook.getAutor();

			if (!authors.isEmpty()) {
				holder.bookAuthor.setText(authors);
			}

		} catch (NullPointerException e) {
			Log.v(LOG_TAG, "Sem autores");
			holder.bookAuthor.setVisibility(View.INVISIBLE);
		}
		holder.bookRating.setRating(currentBook.getRating());
		String price = "";
		if (currentBook.getPreco() > 0) {
			price = "$" + currentBook.getPreco();
			holder.bookPrice.setText(price);
		}
	}

	@Override
	public int getItemCount() {
		return listaLivros.size();
	}

	void clear() {
		listaLivros = new ArrayList<>();
	}
	void addAll(List<Book> data) {

		for (int i = 0; i < data.size(); i++) {
			Book book = data.get(i);
			listaLivros.add(book);
			notifyDataSetChanged();
		}
	}

	static class CardViewHolder extends RecyclerView.ViewHolder {
		TextView bookTitle;
		TextView bookAuthor;
		RatingBar bookRating;
		TextView bookPrice;


		CardViewHolder(View itemView) {
			super(itemView);
			bookTitle = itemView.findViewById(R.id.book_title_text_view);
			bookAuthor = itemView.findViewById(R.id.author_text_view);
			bookRating = itemView.findViewById(R.id.rating_bar);
			bookRating.setMax(5);
			bookRating.setNumStars(5);
			Drawable progress = bookRating.getProgressDrawable();
			DrawableCompat.setTint(progress, Color.YELLOW);
			bookPrice = itemView.findViewById(R.id.retail_price_text_view);
		}
	}
}
