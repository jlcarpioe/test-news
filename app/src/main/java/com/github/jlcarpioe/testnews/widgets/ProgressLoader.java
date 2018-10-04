package com.github.jlcarpioe.testnews.widgets;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.github.jlcarpioe.testnews.R;


/**
 * ProgressLoader
 * Custom loader while make a request.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class ProgressLoader extends ProgressDialog {

	/**
	 * Instance progress loader
	 *
	 * @param context Context application
	 * @return ProgressLoader
	 */
	public static ProgressLoader setup (Context context) {
		ProgressLoader dialog = new ProgressLoader(context);
		dialog.setCancelable(false);
		return dialog;
	}

	/**
	 * Constructor
	 *
	 * @param context Context application.
	 *
	 */
	private ProgressLoader(Context context) {
		super(context);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_progress_loader);

		// Background transparent
		try {
			getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
