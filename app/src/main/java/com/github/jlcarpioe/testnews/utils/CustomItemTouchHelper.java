package com.github.jlcarpioe.testnews.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.github.jlcarpioe.testnews.R;
import com.github.jlcarpioe.testnews.adapters.StoriesAdapter;


/**
 * CustomItemTouchHelper.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 *
 * https://code.i-harness.com/en/q/250378d
 * https://github.com/FanFataL/swipe-controller-demo/blob/master/app/src/main/java/pl/fanfatal/swipecontrollerdemo/SwipeController.java
 *
 */
public class CustomItemTouchHelper extends ItemTouchHelper.Callback {

	private final Context mContext;
	private final StoriesAdapter mAdapter;


	public CustomItemTouchHelper(Context context, StoriesAdapter storiesAdapter) {
		mContext = context;
		mAdapter = storiesAdapter;
	}


	@Override
	public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
		int swipeFlags = ItemTouchHelper.START;
		return makeMovementFlags(0, swipeFlags);
	}

	@Override
	public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
		return false;
	}

	@Override
	public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
		// TODO Call API to delete in background!?
		mAdapter.removeItem(viewHolder.getAdapterPosition());
	}

	@Override
	public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

		View itemView = viewHolder.itemView;
		Paint paint = new Paint();

		// background
		RectF deleteBackground = new RectF(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
		paint.setColor(Color.RED);
		c.drawRoundRect(deleteBackground, 12, 11, paint);

		// text
		String deleteText = mContext.getString(R.string.tag_delete);
		float textSize = mContext.getResources().getDimension(R.dimen.text_button);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);
		c.drawText(
				deleteText,
				deleteBackground.centerX() - (paint.measureText(deleteText)/2),
				deleteBackground.centerY() + (textSize/2), paint
		);

		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
	}

}
