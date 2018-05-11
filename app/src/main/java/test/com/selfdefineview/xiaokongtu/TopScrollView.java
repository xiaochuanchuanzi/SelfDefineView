package test.com.selfdefineview.xiaokongtu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.OverScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsixia on 18/4/22.
 */

public class TopScrollView extends View implements SelfGroup.BodyInterface1 {

    private OverScroller mScroller;//用于辅助View拖动或滑行
    private Rect mRect;
    private Paint mPaint;
    private int color = Color.GRAY;
    private int paintWidth = 2;

    private int totalRectWidth;
    private int RectWidth;
    private int RectHeight;
    List<String> strList = new ArrayList<>();
    List<List<String>> strListArea = new ArrayList<>();
    List<List<String>> strListType = new ArrayList<>();
    int bigUnitCount = 0;
    int column = 0;
    int row = 0;
    int totalColum = 0;

    /**
     * @param row         行数
     * @param strList     单元的数据
     * @param strListArea 面积的数据
     * @param strListType 房屋类型数据
     */
    public void setData(int row, List<String> strList, List<List<String>> strListArea, List<List<String>> strListType) {
        this.strList.clear();
        this.strList.addAll(strList);
        this.strListArea.clear();
        this.strListArea.addAll(strListArea);
        this.strListType.clear();
        this.strListType.addAll(strListType);
        this.bigUnitCount = strList.size();
        this.row = row;
        for (int i = 0; i < strListArea.size(); i++) {
            for (int j = 0; j < strListArea.get(i).size(); j++) {
                totalColum++;
            }
        }
        invalidate();
    }

    public TopScrollView(Context context) {
        super(context);
        init();
    }

    public TopScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new OverScroller(getContext());
        mRect = new Rect();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintWidth);
    }

    public void addListener(SelfGroup mSelfGroup) {
        mSelfGroup.setInterface1(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        RectWidth = width * 1 / 3;
        RectHeight = height / 3;
        if (RectWidth * totalColum < width) {
            if (totalColum > 0) {
                RectWidth = (width - 60) / totalColum;
                requestLayout();
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = 0;
        totalRectWidth = 0;
        for (int k = 0; k < bigUnitCount; k++) {
            //得到当前单元内列的数量
            column = strListArea.get(k).size();
            //通过列的数量得到总宽度 Xtranselate
            totalRectWidth += RectWidth * column + 30;
            for (int i = 0; i < row; i++) {//绘制行Rect
                float top = i * RectHeight;
                float bottom = top + RectHeight;
                for (int j = 0; j < column; j++) {//绘制列Rect
                    if (i == 0) {
                        if (j == 0) {
                            float left = j * RectWidth * column + width;
                            float right = left + RectWidth * column;
                            mRect.set((int) left, (int) top, (int) right, (int) bottom);
                            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                            mPaint.setColor(Color.WHITE);
                            canvas.drawRect(mRect, mPaint);
                        }
                    } else {
                        float left = j * RectWidth + width;
                        float right = left + RectWidth;
                        mRect.set((int) left, (int) top, (int) right, (int) bottom);
                        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                        mPaint.setColor(Color.WHITE);
                        canvas.drawRect(mRect, mPaint);
                    }
                    int unitPosition = i + 1;
                    drawTextInRect(canvas, String.valueOf(unitPosition), k, j);
                }
                drawLines(mPaint, canvas, i, width);
            }
            //获取上一次,已经绘制完成的距离左边的距离
            width += RectWidth * column + 30;
        }
        /*for (int k = 0; k < bigUnitCount; k++) {
            column = strListArea.get(k).size();
            totalRectWidth += RectWidth * column + 30;
        }*/
    }

    private void drawTextInRect(Canvas canvas, String position, int k, int j) {
        String testString = "";
        if (position.equals("1")) {//当为第一行的时候,绘制单元
            testString = strList.get(k) + "单元";
        } else if (position.equals("2")) {//面积
            for (int i = 0; i < bigUnitCount; i++) {
                if (i == k) {
                    List<String> area = strListArea.get(i);
                    testString = area.get(j) + "㎡";
                }
            }
        } else if (position.equals("3")) {//户型
            for (int i = 0; i < bigUnitCount; i++) {
                if (k == i) {
                    testString = strListType.get(i).get(j);
                }
            }
        }
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.GRAY);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (mRect.bottom + mRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(testString, mRect.centerX(), baseline, mPaint);
    }

    //绘制底部的分割线
    public void drawLines(Paint mPaint, Canvas mCanvas, int i, float width) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        int leftLine = (int) (width);
        int topLine = (int) (i * RectHeight);
        int rightLine = (int) (RectWidth * column + leftLine);
        int belowLine = topLine;
        mCanvas.drawLine(leftLine, topLine, rightLine, belowLine, mPaint);
    }

    @Override
    public void computeScroll() {
        //返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 调用Scroller的startScroll后，Scroller会根据偏移量是时间计算当前的X坐标和Y坐标，执行invalidte会让View执行draw()方法，从而调用computeScroll()方法
     */
    public void smoothScrollBy(int dx) {
        if (totalRectWidth < getWidth()) {
            totalRectWidth = getWidth();
        }
        int scrollX = mScroller.getFinalX() + dx;

        if (scrollX < 0) { //执行下滑操作,到达顶端时执行的操作
            dx = -1 * mScroller.getFinalX();
        }
        if (scrollX >= totalRectWidth - getWidth()) {
            //执行上滑操作,到达底端时执行的操作
            dx = totalRectWidth - getWidth() - mScroller.getFinalX();
        }
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, 0);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    /**
     * 根据瞬时速度，让画布滑行
     */
    public void fling(int velocityX, int velocityY) {
        //最后两个是参数是允许的超过边界值的距离
        mScroller.fling(mScroller.getStartX(), mScroller.getStartY(), velocityX, velocityY, 0, totalRectWidth - getWidth(), 0, 0);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void getMoveParams_BODY(int distanceX, int distanceY) {
        smoothScrollBy(distanceX);
    }

    @Override
    public void getUpFling_Body(int velocityX, int velocityY) {
        fling(velocityX, -velocityY);
    }

    @Override
    public void getUpScroll_BODY(int distanceX, int distanceY) {
        smoothScrollBy(distanceX);
    }
}


