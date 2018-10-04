package com.github.jlcarpioe.testnews.models;

import com.google.gson.annotations.Expose;

import java.util.List;


public class ResponseHits {

	@Expose
	private List<Story> hits;

	@Expose
	private int page;

	@Expose int hitsPerPage;


	public ResponseHits() {

	}

	public List<Story> getHits() {
		return hits;
	}

	public int getPage() {
		return page;
	}

	public int getHitsPerPage() {
		return hitsPerPage;
	}

}
