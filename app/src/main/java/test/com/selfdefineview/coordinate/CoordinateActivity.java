package test.com.selfdefineview.coordinate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.com.selfdefineview.R;

/**
 * Created by zhangsixia on 18/5/14.
 * CoordinateLayout是用于协调子View之间的协作关系的
 */

public class CoordinateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinate_layout);
    }
}
