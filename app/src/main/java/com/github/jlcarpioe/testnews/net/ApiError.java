package com.github.jlcarpioe.testnews.net;

import com.android.volley.VolleyError;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * ApiError.
 * Handle error info when call API services.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class ApiError {

    private VolleyError networkError;

    @Expose
    @SerializedName("code")
    private int errorCode;

	@Expose
    @SerializedName("msg")
    private String[] errorMessage;

    @Expose
    private String status;


    public ApiError(VolleyError networkError) {
        this.networkError = networkError;
    }

    public VolleyError getNetworkError() {
        return networkError;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String[] getErrorMessage() {
        return errorMessage;
    }

	public String getStatus() {
		return status;
	}

}
