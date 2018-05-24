package test.com.selfdefineview.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UI {

    public static void enter(Activity activity) {
        AppContext.enter(activity);
    }

    public static void push(Class<?> activityClass) {
        Intent intent = new Intent(AppContext.getCurrentActivity(), activityClass);
        AppContext.getCurrentActivity().startActivity(intent);
    }

    public static void pop() {
        AppContext.getCurrentActivity().finish();
    }

    public static void push(Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent(AppContext.getCurrentActivity(), activityClass);
        intent.putExtras(bundle);
        AppContext.getCurrentActivity().startActivity(intent);
    }

    public static void pushAndReturn(Class<?> activityClass, int requestCode) {
        Intent intent = new Intent(AppContext.getCurrentActivity(), activityClass);
        AppContext.getCurrentActivity().startActivityForResult(intent, requestCode);
    }

    public static void pushAndReturn(Class<?> activityClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(AppContext.getCurrentActivity(), activityClass);
        intent.putExtras(bundle);
        AppContext.getCurrentActivity().startActivityForResult(intent, requestCode);
    }

}
