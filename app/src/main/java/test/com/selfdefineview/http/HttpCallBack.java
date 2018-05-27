package test.com.selfdefineview.http;

/**
 * Created by code5 on 2016/4/23.
 */
public interface HttpCallBack {
    /**
     * 访问网络成功
     * @param e    返回的javabean
     */
    <E> void onSuccess(int requestCode, String json, E e);
    /**
     * 访问网络失败
     */
    void onfail(int requestCode, String errorType);
}
