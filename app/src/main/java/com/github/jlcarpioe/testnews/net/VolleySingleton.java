package com.github.jlcarpioe.testnews.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * VolleySingleton.
 * Implements a singleton class that encapsulates RequestQueue and other Volley functionality.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>
 *
 * @see RequestQueue Single Instance <https://developer.android.com/training/volley/requestqueue.html#singleton>
 *
 */
class VolleySingleton {

	private static VolleySingleton mInstance;
	private RequestQueue mRequestQueue;
	private Context mContext;


	/**
	 * Contructor for class.
	 *
	 * @param context Context application.
	 *
	 */
	private VolleySingleton(Context context) {
		mContext = context;
		mRequestQueue = getRequestQueue();
	}

	/**
	 * Instance of singleton
	 *
	 * @param context Context application.
	 * @return Instance of VolleySingleton.
	 *
	 */
	static synchronized VolleySingleton getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new VolleySingleton(context);
		}
		return mInstance;
	}

	/**
	 * Get request queue.
	 *
	 * @return RequestQueue
	 *
	 */
	private RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			// getApplicationContext() is key, it keeps you from leaking the
			// Activity or BroadcastReceiver if someone passes one in.
			mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
		}
		return mRequestQueue;
	}

	/**
	 * Add request to queue.
	 *
	 * @param request {@link GsonRequest}
	 * @param <T> Sucess Class for JsonObject
	 * @param <S> Error Class for JsonObject
	 */
	<T,S> void addToRequestQueue(GsonRequest<T,S> request) {
		getRequestQueue().add(request);
	}

}
