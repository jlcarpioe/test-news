package com.github.jlcarpioe.testnews.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;


/**
 * Util
 * General operations utilities.
 *
 * This code is under the MIT License (MIT). See LICENSE file.
 *
 * @author José Luis Carpio E. <jlcarpioe@gmail.com>.
 *
 */
public class Util {

	private static final String TAG = Util.class.getSimpleName();


	/**
	 * Get value from manifest
	 *
	 * @param context Context application
	 * @param keyName meta data key
	 * @return String
	 */
	public static String getMetaData(Context context, String keyName) {
		String meta = "unknown";
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			meta = (bundle.get(keyName) instanceof String) ? bundle.getString(keyName) : String.valueOf(bundle.getInt(keyName));
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
		} catch (NullPointerException e) {
			Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
		}
		return meta;
	}

}
