package test.com.selfdefineview.selfseekbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import test.com.selfdefineview.R;

/**
 * Created by zhangsixia on 18/6/26.
 * 带有进度条的对话框
 */

public class SeekBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekbar_layout);
        findViewById(R.id.progressText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出报告进度对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(SeekBarActivity.this);
                final Dialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                View dialogLayout = LayoutInflater.from(SeekBarActivity.this).inflate(R.layout.dialoglayout_layout,null);
                final SeekBar seekBar = dialogLayout.findViewById(R.id.seekbar_id);
                final TextView percentText = dialogLayout.findViewById(R.id.progress);
                TextView cancleText = dialogLayout.findViewById(R.id.cancel_btn);
                TextView confirmText = dialogLayout.findViewById(R.id.confirm_btn);
                seekBar.setMax(100);
                seekBar.setProgress(0);
                percentText.setText("0%");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //setSeekBarClickable(seekBar,progress,50);
                        percentText.setText(progress + "%");
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
                cancleText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int progress = seekBar.getProgress();
                        alertDialog.cancel();
                        //当前滑动的最新进度,如果小于之前已保存的进度,就给出提示

                    }
                });
                confirmText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
                alertDialog.getWindow().setContentView(dialogLayout);//自定义布局应该在这里添加，要在dialog.show()的后面
                alertDialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
            }
        });
    }
}
