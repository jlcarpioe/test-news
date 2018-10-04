package com.github.jlcarpioe.testnews.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.jlcarpioe.testnews.utils.Util;
import com.github.jlcarpioe.testnews.enums.API_ROUTE;
import com.github.jlcarpioe.testnews.enums.PARAM_TYPE;
import com.github.jlcarpioe.testnews.interfaces.OnApiErrorListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * RequestManager.
 * Handle request to API Services.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class RequestManager {

	private static final String TAG = RequestManager.class.getSimpleName();

	private static String BASE_URL;
	private static String PREFIX_URL;


	/**
	 * Load Server info connection
	 *
	 * @param context Context application.
	 */
	private static void loadInfo(Context context) {
		BASE_URL = getBaseURL(context);
		PREFIX_URL = Util.getMetaData(context, "prefix_url");
	}


	/**
	 * Build request to API.
	 *
	 * @param context {@link Context}
	 * @param params ArrayList of {@link ApiParam}
	 * @param infoRoute {@link API_ROUTE}
	 * @param successClass Class where will save data when it is success.
	 * @param errorClass Class where will save data of error.
	 * @param responseListener Listener for success response.
	 * @param errorListener Listener for error response.
	 * @param apiErrorListener Listener for custom API error response.
	 *
	 */
	public static <T,S> void request(Context context, ArrayList<ApiParam> params, API_ROUTE infoRoute,
	                                 Class<T> successClass, Class<S> errorClass,
	                                 Response.Listener<T> responseListener,
	                                 Response.ErrorListener errorListener,
	                                 OnApiErrorListener<S> apiErrorListener) {
		try {

			// load server info
			loadInfo(context);

			// add request to volley
			addRequestQueue(context, new GsonRequest<>(
					context,
					infoRoute.getMethod(),
					buildRouteString(params, BASE_URL + PREFIX_URL + infoRoute.getPath()),
					successClass,
					errorClass,
					getHeadersParams(params),
					getBodyParams(params),
					responseListener,
					errorListener,
					apiErrorListener));

		} catch (Exception e) {
			Log.e(TAG, "Exception " + e.getMessage());

			if ( errorListener != null ) {
				errorListener.onErrorResponse(new VolleyError("Exception " + e.getMessage(), e));
			}
		}

	}


	/**
	 * Add request queue to volley.
	 *
	 * @param context Context application.
	 * @param gsonRequest {@link GsonRequest}
	 *
	 */
	private static void addRequestQueue(Context context, GsonRequest gsonRequest) {
		VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(gsonRequest);
	}


	/**
	 * Define URL to communicate with API webservices.
	 *
	 * @param context {@link Context}
	 *
	 * @return String of URL
	 *
	 */
	private static String getBaseURL(Context context) {
		String apiURL;
		String stringURL = Util.getMetaData(context, "base_url");

		// check if ends with letter or digit
		if ( Character.isLetterOrDigit(stringURL.charAt(stringURL.length()-1)) ) {
			apiURL = stringURL.toLowerCase() + "/";
		} else {
			apiURL = stringURL.toLowerCase();
		}

		apiURL = apiURL.startsWith("http") ? apiURL : "http://" + apiURL;

		return apiURL;
	}


	/**
	 * Build complete URL for API request.
	 *
	 * @param params ArrayList of {@link ApiParam}
	 * @param partialRoute Route of API service.
	 *
	 * @return Complete route for the request of API.
	 *
	 */
	private static String buildRouteString(ArrayList<ApiParam> params, String partialRoute) {

		// check if it is empty
		if ( params == null || params.isEmpty() ) {
			return partialRoute;
		}

		// build url with params if it is required
		String endOfURL = "";
		for (ApiParam param : params) {
			switch (param.getParamType()) {
				case URL_PARAM:
					// check if has to replace value in the URL
					if ( partialRoute.contains(":" + param.getName()) ) {
						partialRoute = partialRoute.replace(":" + param.getName(), param.getValue().toString());
					}
					break;

				case QUERY_STRING:
					if (endOfURL.length() > 0) {
//						endOfURL += "&";
						endOfURL = endOfURL.concat("&");
					} else {
						endOfURL = "?";
					}
					endOfURL += param.getName() + "=" + param.getValue();
					break;

				default:
					break;
			}
		}

		return partialRoute + endOfURL;
	}


	/**
	 * Get custom headers include for the request to API.
	 *
	 * @param params ArrayList of {@link ApiParam}
	 * @return HashMap of key/value headers
	 */
	private static Map<String, String> getHeadersParams(ArrayList<ApiParam> params) {
		Map<String, String> result = new HashMap<>();

		if ( params != null && !params.isEmpty() ) {
			for (ApiParam param : params) {
				if (param.getParamType() == PARAM_TYPE.HEADER) {
					result.put(param.getName(), param.getValue().toString());
				}
			}
		}

		return result;
	}


	/**
	 * Get values for body request to API.
	 *
	 * @param params ArrayList of {@link ApiParam}
	 * @return HashMap of key/value body
	 */
	private static Map<String, String> getBodyParams(ArrayList<ApiParam> params) {
		Map<String, String> result = new HashMap<>();

		if ( params != null && !params.isEmpty() ) {
			for (ApiParam param : params) {
				if (param.getParamType() == PARAM_TYPE.BODY) {
					result.put(param.getName(), param.getValue().toString());
				}
			}
		}

		return result;
	}

}
