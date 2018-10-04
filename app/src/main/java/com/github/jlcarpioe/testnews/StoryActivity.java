package com.github.jlcarpioe.testnews;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.jlcarpioe.testnews.widgets.ProgressLoader;


/**
 * StoryActivity.
 * Webview to show the complete story.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class StoryActivity extends AppCompatActivity {

	ProgressLoader mProgress;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);
		mProgress = ProgressLoader.setup(this);

		WebView webView = findViewById(R.id.app_webview);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				mProgress.show();
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				mProgress.dismiss();
				super.onPageFinished(view, url);
			}
		});
//		webView.getSettings().setJavaScriptEnabled(true);
		if (getIntent().getExtras() != null) {
			webView.loadUrl(getIntent().getExtras().getString("story_url", ""));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_back, menu);
		return true;
		//return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle arrow click here
		if (item.getItemId() == R.id.action_back) {
			finish(); // close this activity and return to preview activity (if there is any)
		}

		return super.onOptionsItemSelected(item);
	}

}
