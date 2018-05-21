package test.com.selfdefineview.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import test.com.selfdefineview.MyApplication;

public class ToastHelper {

    private Context context;

    private Toast toast;

    public ToastHelper(Context context) {
        this.context = context;
    }

    public void showToast(String content) {
        if (!TextUtil.isEmpty(content)) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void showToastLong(String content) {
        if (!TextUtil.isEmpty(content)) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void showToast(int id) {
        toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showToastCenter(String content) {
//        toast = new Toast(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View layout = inflater.inflate(R.layout.view_toast, null);
//        TextView textView= (TextView) layout.findViewById(R.id.contentTextView);
//        textView.setText(content);
//        toast.setView(layout);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
        if (!TextUtil.isEmpty(content)) {
            Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void showToastCenterNow(String content) {
        Toast toast = Toast.makeText(MyApplication.getInstance(), content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastNow(String content) {
        Toast toast = Toast.makeText(MyApplication.getInstance(), content, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showToastCenter(int id) {
        toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastCenterNow(int id) {
        Toast toast = Toast.makeText(MyApplication.getInstance(), id, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
