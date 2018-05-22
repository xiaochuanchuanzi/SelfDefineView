package test.com.selfdefineview.coordinate;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.com.selfdefineview.R;

/**
 * Created by 立涛 on 2018/5/21.
 */

public class CoordinatorActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);
    }
}
