package com.example.android.books;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.books.model.Book;

import java.util.List;

class CarregamentoLivro extends AsyncTaskLoader<List<Book>> {

	private String mSearchUrl;
	private List<Book> mData;

	public CarregamentoLivro(Context context, String url) {
		super(context);
		mSearchUrl = url;
	}

	@Override
	protected void onStartLoading() {
		if (mData != null) {
			deliverResult(mData); // Use cached data
		} else {
			forceLoad();
		}
	}


	@Override
	public List<Book> loadInBackground() {
		if (mSearchUrl == null) {
			return null;
		}

		return QueryUtils.fetchBooks(mSearchUrl);
	}

	@Override
	public void deliverResult(List<Book> data) {
		mData = data;
		super.deliverResult(data);
	}
}
