package com.github.jlcarpioe.testnews.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jlcarpioe.testnews.R;
import com.github.jlcarpioe.testnews.interfaces.OnItemListListener;
import com.github.jlcarpioe.testnews.models.Story;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * StoriesAdapter
 * Adapter to manage stories information list.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>
 *
 */
public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.CustomViewHolder> {

	private List<Story> items;
	private OnItemListListener mListener;
	private SimpleDateFormat sdf;
	private Calendar calendar;


	public StoriesAdapter(List<Story> items, OnItemListListener listener) {
		this.items = items;
		this.mListener = listener;
		this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
		this.calendar = Calendar.getInstance();
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@NonNull
	@Override
	public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_story, parent, false);
		return new CustomViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
		final Story x = items.get(position);
		calendar.setTimeInMillis(x.getCreatedAt()*1000);

		// set info
		holder.title.setText(x.getTitle());
		holder.info.setText(String.format("@%s - %s", x.getAuthor(), sdf.format(calendar.getTime())));

		// set click listener
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if ( mListener != null ) {
					mListener.onSelected(x);
				}
			}
		});
	}

	public void setItems(List<Story> storyList) {
		items = storyList;
		notifyDataSetChanged();
	}

	public void removeItem(int position) {
		items.remove(position);
		notifyItemRemoved(position);
	}

	public void addItems(List<Story> storyList) {
		items.addAll(storyList);
		notifyDataSetChanged();
	}



	/**
	 * ViewHolder class for layout.
	 *
	 */
	static class CustomViewHolder extends RecyclerView.ViewHolder {

		View itemView;
		TextView title;
		TextView info;

		CustomViewHolder(View itemView) {
			super(itemView);

			this.itemView = itemView;
			this.title = itemView.findViewById(R.id.story_title);
			this.info = itemView.findViewById(R.id.story_info);
		}
	}
}
