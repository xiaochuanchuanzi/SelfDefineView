package test.com.selfdefineview.constraint;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import test.com.selfdefineview.R;

/**
 * Created by 立涛 on 2018/5/13.
 */

public class ConstraintActivity extends AppCompatActivity {
    private TextView tab1;
    private TextView tab2;
    private TextView tab3;
    private ConstraintLayout activity_main;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        activity_main = findViewById(R.id.activity_main);
    }

    public void floatBtton(View view){
        view.setBackgroundColor(getResources().getColor(R.color.red_oa));
    }
}
