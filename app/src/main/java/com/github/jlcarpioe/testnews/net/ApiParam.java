package com.github.jlcarpioe.testnews.net;

import com.github.jlcarpioe.testnews.enums.PARAM_TYPE;


/**
 * ApiParam.
 * Handle params' request.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class ApiParam {

    private String name;
	private PARAM_TYPE paramType;
	private Object value;


    public ApiParam(String name, PARAM_TYPE paramType, Object value) {
        this.name = name;
        this.paramType = paramType;
        this.value = value;
    }

	public String getName() {
		return name;
	}

	PARAM_TYPE getParamType() {
		return paramType;
	}

	public Object getValue() {
		return value;
	}

}
