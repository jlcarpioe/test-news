package com.github.jlcarpioe.testnews.net;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.github.jlcarpioe.testnews.interfaces.OnApiErrorListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.util.Map;


/**
 * GsonRequest.
 * Actions of {@link Request} to communicate with API.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class GsonRequest<T, S> extends Request<T> {

    private static final String TAG = GsonRequest.class.getSimpleName();
    private final Gson gson;
    private final Class<T> successClass;
    private final Class<S> errorClass;
    private final Response.Listener<T> responseListener;
    private final OnApiErrorListener<S> apiErrorListener;
    private final Map<String, String> headers;
    private final Map<String, String> params;


	/**
	 * Assign data to request.
	 *
	 * @param context {@link Context}
	 * @param method Request method (GET, POST, UPDATE, DELETE)
	 * @param url URL of webservice operation.
	 * @param successClass Class where will save data when it is success.
	 * @param errorClass Class where will save data of error.
	 * @param headers Headers of request
	 * @param params Params of request
	 * @param responseListener Listener for success response.
	 * @param errorListener Listener for error response.
	 * @param apiErrorListener Listener for custom API error response.
	 *
	 */
    public GsonRequest(Context context, int method, String url, Class<T> successClass,
                       Class<S> errorClass, Map<String, String> headers,
                       @Nullable Map<String,String> params, Response.Listener<T> responseListener,
                       Response.ErrorListener errorListener,
                       OnApiErrorListener<S> apiErrorListener) {

        super(method, url, errorListener);

        this.gson = new GsonBuilder()
		        .excludeFieldsWithoutExposeAnnotation()
		        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
		        .serializeNulls()
		        .create();

        this.successClass = successClass;
        this.responseListener = responseListener;
        this.headers = headers;
        this.params = params;
        this.errorClass = errorClass;
        this.apiErrorListener = apiErrorListener;

        // search and check if exists header token to authorize API request
        /*String authToken = SharedPreference.getString(context, "token_header");
        if ( authToken != null ) {
	        headers.put("Authorization", "Bearer " + authToken);
        }*/

	    Log.d(TAG, "URL -> " + url);

    }



    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.headers != null ? this.headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params != null ? this.params : super.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            if (this.successClass != null) {
	            String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//	            Log.d(TAG, "SUCCESS :: \n" + data );
                return Response.success(
                        this.gson.fromJson(data, this.successClass),
                        HttpHeaderParser.parseCacheHeaders(response));
            }

            throw new Exception("no deserializable class provided");

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(T response) {
	    if ( responseListener != null ) {
		    this.responseListener.onResponse(response);
	    }
    }


    @Override
    public void deliverError(VolleyError error) {
        try {
            if (this.errorClass != null && apiErrorListener != null ) {
//            	Log.d(TAG, "ERROR :: " + new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers)) );
                apiErrorListener.onApiError(
                		this.gson.fromJson(
                				new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers)),
				                this.errorClass
		                )
                );
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception - deliverError :: " + e.getMessage());
            super.deliverError(error);
        }
    }
}
