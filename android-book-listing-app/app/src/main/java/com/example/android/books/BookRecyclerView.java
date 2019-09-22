package com.example.android.books;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class BookRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener {

	private View mEmptyView;
	private AdapterView.OnItemClickListener mListener;
	private GestureDetector mGestureDetector;

	private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
			updateEmptyStatus();
		}
	};

	public BookRecyclerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public BookRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setEmptyView(View emptyView) {
		this.mEmptyView = emptyView;
	}

	@Override
	public void setAdapter(RecyclerView.Adapter adapter) {
		if (getAdapter() != null) {
			getAdapter().unregisterAdapterDataObserver(mDataObserver);
		}

		if (adapter != null) {
			adapter.registerAdapterDataObserver(mDataObserver);
		}

		super.setAdapter(adapter);
		updateEmptyStatus();
	}

	private void updateEmptyStatus() {
		if (mEmptyView != null && getAdapter() != null) {

			final boolean showEmptyView = getAdapter().getItemCount() == 0;
			mEmptyView.setVisibility(showEmptyView ? VISIBLE : GONE);
			setVisibility(showEmptyView ? GONE : VISIBLE);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
		View childView = rv.findChildViewUnder(e.getX(), e.getY());

		if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {

			return true;
		}

		return false;
	}

	public BookRecyclerView(Context context, RecyclerView recyclerView, AdapterView.OnItemClickListener listener) {
		super(context);
		mListener = listener;
		mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {


				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				System.out.println("wsdasd");
			}

		});
	}

	@Override
	public void onTouchEvent(RecyclerView rv, MotionEvent e) {

	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

	}
}
