package com.github.jlcarpioe.testnews.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Story {

	@Expose
	private String title;

	@Expose
	private String url;

	@Expose
	private String author;

	@Expose
	@SerializedName("story_title")
	private String storyTitle;

	@Expose
	@SerializedName("story_url")
	private String storyUrl;

	@Expose
	@SerializedName("created_at_i")
	private long createdAt;

	@Expose
	private String objectID;


	public Story() {

	}

	public String getTitle() {
		return (title != null) ? title : storyTitle;
	}

	public String getUrl() {
		return (url != null) ? url : storyUrl;
	}

	public String getAuthor() {
		return author;
	}

	public String getObjectID() {
		return objectID;
	}

	public long getCreatedAt() {
		return createdAt;
	}


	public static ArrayList<Story> getDataSample(int page) {
		ArrayList<Story> list = new ArrayList<>();
		int end = page * 20;
		int start = end - 20;

		for (int i = start; i < end; i++) {
			Story x = new Story();
			x.title = "Test " + i;
			x.author = "jazxwe" + i;
			x.createdAt = 1538288556;
			list.add(x);
		}

		return list;
	}

}
