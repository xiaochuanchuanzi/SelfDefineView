package test.com.selfdefineview.xiaokongtu;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by zhangsixia on 18/4/28.
 */

@SuppressLint({"ViewConstructor", "AppCompatCustomView"})
public class SelfTextView extends TextView {

    private int mPosition = 0;
    public SelfTextView(Context context, int position) {
        super(context);
        this.mPosition = position;
    }

    public SelfTextView(Context context) {
        super(context);
    }

    public SelfTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }
    Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroud(canvas);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.TRANSPARENT);
        int leftLine = 0;
        int rightLine = getWidth();
        int topLine = getHeight()-1;
        int belowLine = topLine;
        canvas.drawLine(leftLine, topLine, rightLine, belowLine, linePaint);
    }

    int colorss = 0;
    public void setColors(int colorID){
        colorss = colorID;
        invalidate();
    }

    /**
     * 绘制每个房屋的中间背景
     */
    public void drawBackgroud(Canvas mCanvas){
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(colorss);
        Rect mRect = new Rect();
        int leftLine = 20;
        int rightLine = getWidth() - 20;
        int topLine = 1;
        int belowLine = getHeight();
        mRect.set(leftLine, topLine, rightLine, belowLine);
        mCanvas.drawRect(mRect, linePaint);
        drawTextInRect(mCanvas,mRect);
    }

    String testString = "";
    public void drawTextString(String mString){
        testString = mString;
        invalidate();
    }

    private void drawTextInRect(Canvas canvas, Rect mRect) {
        linePaint.setTextSize(30);
        linePaint.setColor(Color.GRAY);
        Paint.FontMetricsInt fontMetrics = linePaint.getFontMetricsInt();
        int baseline = (mRect.bottom + mRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        linePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(testString, mRect.centerX(), baseline, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
