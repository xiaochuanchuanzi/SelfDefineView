package test.com.selfdefineview.util;

import android.app.Activity;
import android.app.Application;


public class AppContext {


	public static Application _application;

	public static Application getApplication() {
		return _application;
	}

	private static Activity currentActivity;

	public static void enter(Activity activity) {
		currentActivity = activity;
	}

	public static Activity getCurrentActivity() {
		return currentActivity;
	}

	public static Activity getContext() {
		return currentActivity;
	}


}
