package test.com.selfdefineview.constraintlayout;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.com.selfdefineview.R;

/**
 * Created by 立涛 on 2018/5/13.
 */

public class ConstraintActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.constraint_layout);
    }
}
