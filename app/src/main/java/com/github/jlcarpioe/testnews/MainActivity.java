package com.github.jlcarpioe.testnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.jlcarpioe.testnews.utils.CustomItemTouchHelper;
import com.github.jlcarpioe.testnews.utils.CustomRecyclerScrollListener;
import com.github.jlcarpioe.testnews.adapters.StoriesAdapter;
import com.github.jlcarpioe.testnews.enums.API_ROUTE;
import com.github.jlcarpioe.testnews.enums.PARAM_TYPE;
import com.github.jlcarpioe.testnews.interfaces.OnApiErrorListener;
import com.github.jlcarpioe.testnews.interfaces.OnItemListListener;
import com.github.jlcarpioe.testnews.models.ResponseHits;
import com.github.jlcarpioe.testnews.models.Story;
import com.github.jlcarpioe.testnews.net.ApiError;
import com.github.jlcarpioe.testnews.net.ApiParam;
import com.github.jlcarpioe.testnews.net.RequestManager;

import java.util.ArrayList;


/**
 * MainActivity.
 * Show list of stories.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
		CustomRecyclerScrollListener.OnRefreshList, OnItemListListener {

	private static final int REQUEST_CODE = 123;

	private SwipeRefreshLayout mSwipeLayout;
	private CustomRecyclerScrollListener mRecyclerScrollListener;
	private StoriesAdapter mAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// adapter to fill views of items
		mAdapter = new StoriesAdapter(new ArrayList<Story>(), this);

		// swipe refresh
		mSwipeLayout = findViewById(R.id.swipe_view);
		mSwipeLayout.setOnRefreshListener(this);

		// refresh when scroll down
		mRecyclerScrollListener  = new CustomRecyclerScrollListener(this, 1);

		// recycler view
		RecyclerView recyclerView = findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(mAdapter);
		recyclerView.addOnScrollListener(mRecyclerScrollListener);

		// swipe item to delete action
		CustomItemTouchHelper helperCallback = new CustomItemTouchHelper(this, mAdapter);
		new ItemTouchHelper(helperCallback).attachToRecyclerView(recyclerView);

		// enable swipe refresh
		mSwipeLayout.setRefreshing(true);

		// call api
		getData(1);
	}

	@Override
	public void onRefresh() {

		// enable swipe refresh
		mSwipeLayout.setRefreshing(true);

		getData(1);
	}

	@Override
	public void OnRefresh(int pageNumber) {

		// enable swipe refresh
		mSwipeLayout.setRefreshing(true);

		getData(pageNumber);
	}

	@Override
	public void onSelected(Story story) {
		Intent intent = new Intent(this, StoryActivity.class);
		intent.putExtra("story_url", story.getUrl());
		startActivityForResult(intent, REQUEST_CODE);
	}


	/**
	 * Get data from server by page number.
	 *
	 * @param page {@link Integer} value of page to search data.
	 *
	 */
	private void getData(final int page) {

		// request params
		ArrayList<ApiParam> params = new ArrayList<>();
		params.add(new ApiParam("query", PARAM_TYPE.QUERY_STRING, "android"));
		params.add(new ApiParam("page", PARAM_TYPE.QUERY_STRING, page));

		// request to server
		RequestManager.request(
				this,
				params,
				API_ROUTE.LIST,
				ResponseHits.class,
				ApiError.class,
				new Response.Listener<ResponseHits>() {
					@Override
					public void onResponse(ResponseHits response) {

						if (response.getPage() == 1) {

							// clear data set
							mAdapter.setItems(response.getHits());

							// reset scroll listener data
							mRecyclerScrollListener.reset();
						}
						else {

							// add items to data set
							mAdapter.addItems(response.getHits());

							// update scroll state
							mRecyclerScrollListener.notifyMorePages();
						}

						// disable swipe refresh
						mSwipeLayout.setRefreshing(false);

					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// disable swipe refresh
						mSwipeLayout.setRefreshing(false);
					}
				},
				new OnApiErrorListener<ApiError>() {
					@Override
					public void onApiError(ApiError apiError) {
						// disable swipe refresh
						mSwipeLayout.setRefreshing(false);
					}
				}
		);
	}

}
