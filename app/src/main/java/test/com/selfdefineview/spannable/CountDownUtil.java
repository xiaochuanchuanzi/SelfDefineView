package test.com.selfdefineview.spannable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import test.com.selfdefineview.R;

/**
 * Created by zhangsixia on 18/5/3.
 */

public class CountDownUtil extends CountDownTimer {
    private TextView tv;//
    private Context mContext;
    int Type;//0:距离活动开始还剩     1:抢购中      2:活动已结束
    String headStr = "";
    String noneStart = "距离开始还有";
    String activeing = "抢购中本场还剩";
    public static final String activeend = "活动已结束,敬请期待下次活动";

    /**
     * @param millisInFuture    倒计时时间
     * @param countDownInterval 间隔
     * @param tv                控件
     */
    public CountDownUtil(Context context, long millisInFuture, long countDownInterval, TextView tv, int mType) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
        this.mContext = context;
        this.Type = mType;
        switch(mType){
            case 1:
                headStr = noneStart;
                break;
            case 2:
                headStr = activeing;
                break;
            case 3:
                headStr = activeend;
                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        StringFormatUtil spanStr = null;
        if(headStr.equals(activeing)){
            spanStr = new StringFormatUtil(mContext, getHours(millisUntilFinished),"抢购中", R.color.brown_touming_qb).fillColor();
        }else if(headStr.equals(noneStart)){
            spanStr = new StringFormatUtil(mContext, getHours(millisUntilFinished),"", R.color.brown_touming_qb).fillColor();
        }
        tv.setText(spanStr.getResult());//设置时间
        //tv.setText(getHours(millisUntilFinished));
    }

    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        if(Type == 1){
            //活动开始,更新状态,刷新接口
            mCountDownInterface.refreshDatas((Activity) mContext);
        }else{
            tv.setText("活动已结束,敬请期待下次活动");
        }
    }



    /**
     * @paramsssss毫秒值
     * @return时:分:秒 1510876800, 1511395200
     */
    public String getHours(long time) {
        long second = time / 1000;
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        second = second % 60;                //剩余秒数

        String dayss = "";
        String hourss = "";
        String minutess = "";
        String seconds = "";
        if(days < 10){
            dayss = "0"+days+"天";
        }else{
            dayss = days+"天";
        }
        if(hours < 10){
            hourss = "0"+hours+"小时";
        }else{
            hourss = hours+"小时";
        }
        if(minutes < 10){
            minutess = "0"+minutes+"分";
        }else{
            minutess = minutes+"分";
        }
        if(second < 10){
            seconds = "0"+second+"秒";
        }else{
            seconds = second+"秒";
        }
        return headStr + dayss + hourss + minutess + seconds;
    }

    public interface CountDownInterface{
        void refreshDatas(Activity activity);
    }
    public static CountDownInterface mCountDownInterface;
    public static void setListener(CountDownInterface mCountDownInterface){
        CountDownUtil.mCountDownInterface = mCountDownInterface;
    }

}
