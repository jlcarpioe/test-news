package com.github.jlcarpioe.testnews.enums;

import com.android.volley.Request;


/**
 * API_ROUTE
 * {@link Enum} List of operations for api services.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>
 *
 */
public enum API_ROUTE {

    LIST   (Request.Method.GET, "search_by_date");


    private String path;
    private int method;

    API_ROUTE(int method, String path) {
        this.method = method;
        this.path = path;
    }

    public int getMethod() {
	    return method;
    }

    public String getPath() {
        return path;
    }

}
