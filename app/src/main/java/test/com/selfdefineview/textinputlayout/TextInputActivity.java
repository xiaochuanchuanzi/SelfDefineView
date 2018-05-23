package test.com.selfdefineview.textinputlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.com.selfdefineview.R;
import test.com.selfdefineview.util.ToastHelper;

/**
 * Created by 立涛 on 2018/5/23.
 */

public class TextInputActivity extends AppCompatActivity {

    private TextInputLayout mMobileTil;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        mMobileTil = (TextInputLayout) findViewById(R.id.til_mobile);
    }
    public void onClickLogin(View v){
        if (verifyMobile()){
            mMobileTil.setErrorEnabled(false);
            ToastHelper.showToastCenterNow("Success");
        }else{
            mMobileTil.setErrorEnabled(true);
            mMobileTil.setError("手机号格式错误");
        }
    }
    public boolean verifyMobile(){
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(((EditText) findViewById(R.id.et_mobile)).getText().toString());
        return  matcher.matches();
    }
}
