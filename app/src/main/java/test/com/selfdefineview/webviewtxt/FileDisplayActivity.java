package test.com.selfdefineview.webviewtxt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.com.selfdefineview.R;

public class FileDisplayActivity extends AppCompatActivity {
    SuperFileView2 mSuperFileView;
    String filePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);
        init();
    }
    public void init() {
        mSuperFileView = (SuperFileView2) findViewById(R.id.mSuperFileView);
        mSuperFileView.setOnGetFilePathListener(new SuperFileView2.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView2 mSuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2);
            }
        });
        Intent intent = this.getIntent();
        String path = (String) intent.getSerializableExtra("path");
        if (!TextUtils.isEmpty(path)) {
            setFilePath(path);
        }
        //调用了show方法之后,会触发setOnGetFilePathListener监听
        mSuperFileView.show();
    }
    private void getFilePathAndShowFile(SuperFileView2 mSuperFileView2) {
        if (getFilePath().contains("http")) {//网络地址要先下载
            downLoadFromNet(getFilePath(),mSuperFileView2);
        } else {
            mSuperFileView2.displayFile(new File(getFilePath()));
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
    }
    public static void show(Context context, String url) {
        Intent intent = new Intent(context, FileDisplayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("path", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public void setFilePath(String fileUrl) {
        this.filePath = fileUrl;
    }
    private String getFilePath() {
        return filePath;
    }
    //从网络先下载
    private void downLoadFromNet(final String url,final SuperFileView2 mSuperFileView2) {
        //1.网络下载、存储路径、
        File cacheFile = getCacheFile(url);
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                cacheFile.delete();
                return;
            }
        }
        //从网络下载文件
        LoadFileModel.loadPdfFile(url, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    ResponseBody responseBody = response.body();
                    is = responseBody.byteStream();
                    File file1 = getCacheDir(url);
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
                    //fileN : /storage/emulated/0/pdf/kauibao20170821040512.pdf
                    File fileN = getCacheFile(url);//new File(getCacheDir(url), getFileName(url))
                    if (!fileN.exists()) {
                        fileN.createNewFile();
                    }
                    fos = new FileOutputStream(fileN);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //2.ACache记录文件的有效期
                    mSuperFileView2.displayFile(fileN);
                } catch (Exception e) {
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {}
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                File file = getCacheFile(url);
                if (!file.exists()) {
                    file.delete();
                }
            }
        });
    }
    /***
     * 获取缓存目录
     */
    private File getCacheDir(String url) {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/007/");
    }
    /***
     * 绝对路径获取缓存文件
     */
    private File getCacheFile(String url) {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/007/" + getFileName(url));
    }
    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     */
    private String getFileName(String url) {
        String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
        return fileName;
    }
    /***
     * 获取文件类型--扩展名类型
     */
    private String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = paramString.substring(i + 1);
        return str;
    }
}
