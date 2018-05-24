package test.com.selfdefineview.updateapk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import test.com.selfdefineview.MyApplication;
import test.com.selfdefineview.R;
import test.com.selfdefineview.util.AppContext;
import test.com.selfdefineview.util.AppInfo;
import test.com.selfdefineview.util.LogHelper;
import test.com.selfdefineview.util.PopWindowHelper;
import test.com.selfdefineview.util.UI;

public class UpdateAppView extends FrameLayout implements OnClickListener {

    private UpdateVersionSM model;

    RelativeLayout fenLayout;
    LinearLayout fenLayout1;
    LinearLayout progressLayout;

    private TextView update;
    private TextView cancel;
    private TextView content;
    private TextView progressTextView;
    private TextView install;
    private String fileURL;
    private ProgressBar pb;

    public boolean needUpdate;

    private String fileName = "huarun.apk";
    private String code;

    public UpdateAppView(Context context, UpdateVersionSM data,
                         boolean needUpdate) {
        super(context);
        this.needUpdate = needUpdate;
        initView();
        initData(data);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_updatedialog_oa, this);
        fenLayout = (RelativeLayout) findViewById(R.id.fenLayout);
        fenLayout1 = (LinearLayout) findViewById(R.id.fenLayout1);
        progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        pb = (ProgressBar) findViewById(R.id.view_updatedialog_pb);
        update = (TextView) findViewById(R.id.view_updatedialog_okTextView);
        cancel = (TextView) findViewById(R.id.view_updatedialog_cancelTextView);
        progressTextView = (TextView) findViewById(R.id.progressTextView);
        install = (TextView) findViewById(R.id.view_updatedialog_installTextView);
        content = (TextView) findViewById(R.id.view_updatedialog_contentTV);
        update.setOnClickListener(this);
        cancel.setOnClickListener(this);
        install.setOnClickListener(this);
    }

    private void initData(UpdateVersionSM data) {
        model = data;
        //fileURL = model.data.downloadUrl;
        fileURL = "www.baidu.com/";
        LogHelper.Log("dandelion", "updateUrl=" + fileURL);
        //content.setText("客户端将从" + AppInfo.VERSION_NAME + "升级至" + model.data.versionNo);
        content.setText("客户端将从" + AppInfo.VERSION_NAME + "升级至最新版本");
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.view_updatedialog_okTextView:
//                update.setVisibility(View.GONE);
//                cancel.setVisibility(View.GONE);
//                content.setVisibility(View.GONE);
                fenLayout.setVisibility(GONE);
                fenLayout1.setVisibility(VISIBLE);
                progressLayout.setVisibility(View.VISIBLE);
                new DownloadFileAsync().execute(fileURL);
                break;
            case R.id.view_updatedialog_cancelTextView:
                PopWindowHelper.closePopWindow();
                if (needUpdate) {
                    UI.pop();
                }
                break;
            case R.id.view_updatedialog_installTextView:
                install(AppContext.getCurrentActivity(), MyApplication.ROOT_PATH + fileName);
                break;
            default:
                break;
        }
    }

    public static void install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (Build.VERSION.SDK_INT >= 24) {
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", file);
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(i);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            checkAndCreateDirectory("/my_downloads");
        }

        @Override
        protected String doInBackground(String... aurl) {
            try {
                URL u = new URL(fileURL);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Accept-Encoding", "identity");
                c.connect();
                LogHelper.Log("test", "openConnection=" + c.getURL() + "/" + c.getRequestMethod());
                int lenghtOfFile = c.getContentLength();
                FileOutputStream f = new FileOutputStream(new File(MyApplication.ROOT_PATH, fileName));
                InputStream in = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    total += len1;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    f.write(buffer, 0, len1);
                }
                f.close();
            } catch (Exception e) {
                Log.e("catch exception", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            pb.setProgress(Integer.parseInt(progress[0]));
            progressTextView.setText(progress[0] + "%");
        }

        @Override
        protected void onPostExecute(String unused) {
            install(AppContext.getCurrentActivity(), MyApplication.ROOT_PATH
                    + fileName);
            install.setVisibility(View.VISIBLE);
        }
    }

    public void checkAndCreateDirectory(String dirName) {
        File new_dir = new File(Environment.getExternalStorageDirectory()
                + dirName);
        if (!new_dir.exists()) {
            new_dir.mkdirs();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && needUpdate) {
            UI.pop();
        }
        return super.onKeyDown(keyCode, event);
    }

}
