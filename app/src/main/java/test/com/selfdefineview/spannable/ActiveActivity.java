package test.com.selfdefineview.spannable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import test.com.selfdefineview.R;

/**
 * Created by zhangsixia on 18/6/6.
 */

public class ActiveActivity extends AppCompatActivity {

    private TextView time_text;
    private Map<TextView, CountDownUtil> timeMap = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_layout);
        time_text = findViewById(R.id.time_text);
        long TimeDifference = 8724243;
        CountDownUtil cdu1 = new CountDownUtil(ActiveActivity.this, TimeDifference * 1000, 1000, time_text, 2);
        cdu1.start();
    }

    /**
     * 倒计时CountDown在列表页面中的应用方式
     */
   /* public void list(){
        CountDownUtil tc = timeMap.get(time_text);
        if (tc != null) {
            tc.cancel();
            tc = null;
        }
        switch (data.Class) {
            case 1://未开始
                CountDownUtil cdu = new CountDownUtil(mContext, time_activites * 1000, 1000, time_text, 1);
                cdu.start();
                timeMap.put(time_text, cdu);
                break;
            case 2://进行中
                CountDownUtil cdu1 = new CountDownUtil(mContext, time_activites * 1000, 1000, time_text, 2);
                cdu1.start();
                timeMap.put(time_text, cdu1);
                break;
            case 3://已结束
                time_text.setText(CountDownUtil.activeend);
                break;
        }
    }*/
}
