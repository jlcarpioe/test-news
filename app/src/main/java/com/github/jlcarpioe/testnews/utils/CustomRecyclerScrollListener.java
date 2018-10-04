package com.github.jlcarpioe.testnews.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


/**
 * CustomRecyclerScrollListener.
 * Refresh action when it reaches the end of recycler.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class CustomRecyclerScrollListener extends RecyclerView.OnScrollListener {

	private boolean isLoading;
	private boolean hasMorePages;
	private boolean isRefreshing;
	private int mPageNumber;
	private OnRefreshList mRefreshList;


	public CustomRecyclerScrollListener(OnRefreshList onRefreshList, int pageNumber) {
		mRefreshList = onRefreshList;
		mPageNumber = pageNumber;
		hasMorePages = true;
		isLoading = false;
		isRefreshing = false;
	}


	@Override
	public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);

		LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
		if (layoutManager == null) {
			return;
		}

		int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
		int visibleItemCount = layoutManager.getChildCount();
		int totalItemCount = layoutManager.getItemCount();

		if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
			isLoading = true;
			if (hasMorePages && !isRefreshing) {
				isRefreshing = true;
				mRefreshList.OnRefresh(mPageNumber + 1);
			}
		}
		else {
			isLoading = false;
		}
	}

	public void reset() {
		mPageNumber = 1;
	}

	public void notifyNoMorePages() {
		hasMorePages = false;
	}

	public void notifyMorePages() {
		isRefreshing = false;
		mPageNumber = mPageNumber + 1;
	}

	public interface OnRefreshList {
		void OnRefresh(int pageNumber);
	}
}
