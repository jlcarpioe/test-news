package com.github.jlcarpioe.testnews.interfaces;

/**
 * OnApiErrorListener.
 * Actions listener for error on API.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public interface OnApiErrorListener<ApiError> {

    void onApiError(ApiError apiError);

}
