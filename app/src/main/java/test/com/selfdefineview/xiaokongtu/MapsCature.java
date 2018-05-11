package test.com.selfdefineview.xiaokongtu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsixia on 18/4/27.
 */

public class MapsCature extends View implements SelfGroup.BodyInterface2, SelfGroup.BodyInterface3 {

    public MapsCature(Context context) {
        super(context);
        init();
    }

    public MapsCature(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapsCature(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        redBorderPaint = new Paint();
        redBorderPaint.setAntiAlias(true);
        redBorderPaint.setColor(Color.RED);
        redBorderPaint.setStyle(Paint.Style.STROKE);
        redBorderPaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        overviewBitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_4444);
    }

    private float dip2Px(float value) {
        return getResources().getDisplayMetrics().density * value;
    }

    private int bigUnitCount = 0;
    private int column = 0;
    private int row = 0;
    //标识是否需要绘制概览图
    boolean isDrawOverview = false;
    //标识是否需要更新概览图
    boolean isDrawOverviewBitmap = true;
    //缩略图Bitmap
    private Bitmap overviewBitmap;
    //用于绘制概览图的画笔
    Paint overviewPaint = new Paint();
    //概览图白色方块高度
    float rectHeight;
    //概览图白色方块的宽度
    float rectWidth;
    private Paint redBorderPaint;

    /*设置数据*/
    List<List<String>> strListTypes = new ArrayList<>();

    public void setData(int bigUnitCount, int row, List<List<String>> strListType) {
        this.bigUnitCount = bigUnitCount;
        this.row = row;
        this.strListTypes.clear();
        this.strListTypes.addAll(strListType);
        invalidate();
    }

    /**
     * 获取悬浮的缩略图
     */
    Bitmap drawOverview() {
        //默认设置为--不去更新缩略图的联动效果
        isDrawOverviewBitmap = false;
        int bac = Color.parseColor("#7e000000");
        overviewPaint.setColor(bac);
        overviewPaint.setAntiAlias(true);
        overviewPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        overviewBitmap.eraseColor(Color.TRANSPARENT);
        //创建一个画布--及悬浮框的Bitmap
        Canvas canvas = new Canvas(overviewBitmap);
        //绘制透明灰色背景
        canvas.drawRect(0, 0, getWidth(), getHeight(), overviewPaint);
        //再绘制里面的座位小圆白点---以及座位是否被选中的状态
        float width = 0;
        for (int k = 0; k < bigUnitCount; k++) {
            column = strListTypes.get(k).size();
            //循环完成第一次之后进行第二列的绘制,需要改变X周方向上的参数
            for (int i = 0; i < row; i++) {//绘制每一行
                float top = i * rectHeight;
                float bottom = top + rectHeight;
                for (int j = 0; j < column; j++) {//绘制每一列
                    float left = j * rectWidth + width;
                    float right = left + rectWidth;
                    overviewPaint.setColor(Color.WHITE);
                    overviewPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect((int) left, (int) top, (int) right, (int) bottom, overviewPaint);
                }
            }
            width += rectWidth * column + 5;
        }
        return overviewBitmap;
    }

    /**
     * 在绘制好的概览图上面进行动态的绘制动图--就像地图一样
     */
    void drawOverview(Canvas canvas) {
        int left = (int) transX;
        int right = left + (int) redPaintWidth;
        int top = (int) transY;
        int bottom = top + (int) redPaintHeight;
        if (left <= 0) {
            left = 0;
            right = (int) redPaintWidth;
        }
        if (top <= 0) {
            top = 0;
            bottom = (int) redPaintHeight;
        }
        if (right >= dip2Px(150)) {
            left = (int) (dip2Px(150) - redPaintWidth);
            right = (int) dip2Px(150);
        }
        if (bottom >= dip2Px(100)) {
            top = (int) (dip2Px(100) - redPaintHeight);
            bottom = (int) dip2Px(100);
        }
        canvas.drawRect(left, top, right, bottom, redBorderPaint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isDrawOverview) {
            //获取概览图---悬浮的缩略图
            drawOverview();
            //将Bitmap悬浮的缩略图---绘制到左上角
            canvas.drawBitmap(overviewBitmap, 0, 0, null);
            drawOverview(canvas);
        }
    }

    public void setListener(SelfGroup mSelfGroup) {
        mSelfGroup.setInterface2(this);
        mSelfGroup.setInterface3(this);
    }

    float scaleSize_X = 1.0f;
    float scaleSize_Y = 1.0f;
    float redPaintWidth = 0;
    float redPaintHeight = 0;
    float transX, transY;
    @Override
    public void changeParams(int totalWith, int totalHeight, int row, List<List<String>> strListType, int parentWidth, int patentHeight) {
        scaleSize_X = dip2Px(150.0f) / totalWith;
        scaleSize_Y = dip2Px(100.0f) / totalHeight;
        int totalcount = 0;
        for (int j = 0; j < bigUnitCount; j++) {
            int columCount = strListType.get(j).size();
            totalcount += columCount;
        }
        rectWidth = ((totalWith - 100) / totalcount) * scaleSize_X;
        rectHeight = (totalHeight / row) * scaleSize_Y;
        redPaintWidth = dip2Px(150) * parentWidth / totalWith;
        redPaintHeight = dip2Px(100) * patentHeight / totalHeight;
        postInvalidate();
    }

    @Override
    public void changeTranselate(float transX, float transY) {
        this.transX = transX * scaleSize_X;
        this.transY = transY * scaleSize_Y;
        postInvalidate();
    }

    @Override
    public void mapStatus(boolean isVisiMap) {
        isDrawOverview = isVisiMap;
        postInvalidate();
    }
}
