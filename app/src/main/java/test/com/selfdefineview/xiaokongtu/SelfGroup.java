package test.com.selfdefineview.xiaokongtu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import test.com.selfdefineview.R;
import test.com.selfdefineview.xiaokongtu.entity.PictureRooms;

/**
 * Created by zhangsixia on 18/4/27.
 */

public class SelfGroup extends RelativeLayout {

    private int textView_Height;
    private int textView_Width;
    private int totalRectHeight_X;
    private int totalRectHeight_Y;
    private Matrix mMetrix;
    private float[] array;
    private OverScroller mScroller;//用于辅助View拖动或滑行
    private VelocityTracker mVelocityTracker = null; //速度追踪器
    int horizontalDistance;
    private int totalColum = 0;

    public SelfGroup(Context context) {
        super(context);
        init();
    }

    public SelfGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelfGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new OverScroller(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        array = new float[9];
        mMetrix = new Matrix();
        horizontalDistance = 30;
    }

    int Unit_Count;//单元的数量
    int houseCount_In_Unit;//每个单元内户型的数量
    int floadCount;//楼层
    List<PictureRooms> strListRoom = new ArrayList<>();
    List<List<String>> strListType = new ArrayList<>();

    public void setData(int bigUnitCount, int row, List<PictureRooms> strListRoom, List<List<String>> strListType) {
        this.strListRoom.clear();
        this.strListRoom.addAll(strListRoom);
        this.strListType.clear();
        this.strListType.addAll(strListType);

        this.Unit_Count = bigUnitCount;
        this.floadCount = row;

        for(int i=0;i<strListType.size();i++){
            for(int j=0;j < strListType.get(i).size();j++){
                totalColum ++;
            }
        }
        //Status;//1:白色(空闲)   2灰色(无数据,预约)   3红色(已出售)
        for (int i = 0; i < this.strListRoom.size(); i++) {
            SelfTextView mTextView = new SelfTextView(getContext());
            mTextView.setTextSize(12);
            mTextView.setBackgroundColor(getResources().getColor(R.color.white));
            if(strListRoom.get(i).Id == 0){//无房源数据,不可点击
                mTextView.setColors(getResources().getColor(R.color.word_gray6));
                mTextView.setTextColor(Color.WHITE);
                //mTextView.setText("");
                mTextView.drawTextString("未参加活动");
            }else{//有房源数据
                if(strListRoom.get(i).Status == 1){//1:白色(空闲)
                    mTextView.setColors(getResources().getColor(R.color.white));
                    mTextView.setTextColor(Color.GRAY);
                    //mTextView.setText(strListRoom.get(i).Roomnum + "");
                    mTextView.drawTextString(strListRoom.get(i).Roomnum +"");
                    setListener(mTextView, i);//可点击选择
                }else if(strListRoom.get(i).Status == 2){//2灰色(预约)
                    mTextView.setColors(getResources().getColor(R.color.word_gray6));
                    mTextView.setTextColor(Color.WHITE);
                   // mTextView.setText(strListRoom.get(i).Roomnum + "");
                    mTextView.drawTextString(strListRoom.get(i).Roomnum +"");
                }else if(strListRoom.get(i).Status == 3){//3红色(已出售)
                    mTextView.setColors(getResources().getColor(R.color.viewfinder_laser));
                    mTextView.setTextColor(Color.WHITE);
                    //mTextView.setText(strListRoom.get(i).Roomnum + "");
                    mTextView.drawTextString(strListRoom.get(i).Roomnum +"");
                }
            }
            mTextView.setTag(i);
            addView(mTextView);
        }
    }

    @Override
    protected void onLayout(boolean f, int l, int t, int r, int b) {
        int horizontalSpace = 0;
        int textcount = 0;
        for(int i = 0 ;i < Unit_Count;i++){//一个单元一个单元的绘制
            houseCount_In_Unit = strListType.get(i).size();
            int horizontalTranse = 0;
            for (int j = 0 ;j < houseCount_In_Unit;j++){
                int verticalTranse = 0;
                for(int k = 0 ;k < floadCount;k++){
                    View childView = getChildAt(textcount);
                    int top    = verticalTranse;
                    int bottom = top + textView_Height;
                    int left   = horizontalSpace + horizontalTranse;
                    int right  = left + textView_Width;
                    childView.layout(left, top, right, bottom);
                    textcount ++;
                    verticalTranse += textView_Height;
                }
                horizontalTranse += textView_Width;
            }
            horizontalSpace += horizontalDistance + houseCount_In_Unit * textView_Width;//每更换一个单元都要在X轴方向上增加一条竖向分割线的宽度
            totalRectHeight_X += horizontalDistance + houseCount_In_Unit * textView_Width;
        }
        if (Unit_Count != 0) {
            totalRectHeight_Y = textView_Height * floadCount;
            mBodyInterface2.changeParams(totalRectHeight_X, totalRectHeight_Y, floadCount, strListType, getWidth(), getHeight());
        }

    }

    public void setListener(View mTextView, final int position) {
       /* mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemsClickListener.OnitemListener(view, position);
            }
        });*/
        mTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mOnItemsClickListener.OnitemListener(view, position);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        textView_Width = width * 1 / 3;
        textView_Height = height * 1 / 9;
        if(textView_Width * totalColum < width){
            if(totalColum > 0){
                textView_Width = (width - 60) / totalColum;
                requestLayout();
            }
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            SelfTextView mTexts = (SelfTextView) getChildAt(i);
            mTexts.setWidth(textView_Width);
            mTexts.setHeight(textView_Height);
            mTexts.setGravity(Gravity.CENTER);
            mTexts.measure(textView_Width, textView_Height);
        }
        setMeasuredDimension(width, height);
    }

    private float dip2Px(float value) {
        return getResources().getDisplayMetrics().density * value;
    }

    @Override
    public void computeScroll() {
        //返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。这是一个很重要的方法，通常放在View.computeScroll()中，用来判断是否滚动是否结束
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
    int lastY, lastX;
    int currentY, currentX;
    int distanceY, distanceX;
    boolean isScrolling = false;
    float touchDownX;
    float touchDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = ev.getX();
                touchDownY = ev.getY();
                isScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float distance_X = touchDownX - ev.getX();
                float distance_Y = touchDownY - ev.getY();
                if (Math.abs(distance_X) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()
                        || Math.abs(distance_Y) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    isScrolling = true;
                } else {
                    isScrolling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isScrolling = false;
                break;
        }
        return isScrolling;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //gestureDetector.onTouchEvent(event);
        mVelocityTracker.addMovement(event);
        //根据触摸位置计算每像素的移动速率。
        mVelocityTracker.computeCurrentVelocity(1000);
        /*//计算速率
        int velocityX = (int) mVelocityTracker.getXVelocity() * (-1);
        int velocityY = (int) mVelocityTracker.getYVelocity() * (-1);*/
        //判断操作类别
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当滚动的过程中,按下手指的时候,就停止滚动的动画
                if (mScroller != null) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                }
                //获取当前的坐标值
                lastY = (int) event.getY();
                lastX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //计算出两次动作间的滑动距离
                currentY = (int) event.getY();
                currentX = (int) event.getX();
                distanceY = currentY - lastY;
                distanceX = currentX - lastX;
                //翻转滑动方向
                distanceY = distanceY * -1;
                distanceX = distanceX * -1;
                //拖动滑动
                smoothScrollBy(distanceX, distanceY);
                //设置左边和顶部联动
                if (mBodyInterface != null) {
                    mBodyInterface.getMoveParams_BODY(distanceX, distanceY);
                    mBodyInterface1.getMoveParams_BODY(distanceX, distanceY);
                }
                //拖动结束时将当前
                lastY = currentY;
                lastX = currentX;
                break;
            case MotionEvent.ACTION_UP:
                mBodyInterface3.mapStatus(false);
                break;
        }
        return true;
    }
    /**
     * 调用Scroller的startScroll后，Scroller会根据偏移量是时间计算当前的X坐标和Y坐标，执行invalidte会让View执行draw()方法，从而调用computeScroll()方法
     */
    public void smoothScrollBy(int dx, int dy) {
        if(totalRectHeight_X < getWidth()){
            totalRectHeight_X = getWidth();
        }
        if(totalRectHeight_Y < getHeight()){
            totalRectHeight_Y = getHeight();
        }
        int scrollY = mScroller.getFinalY() + dy;
        if (scrollY < 0) { //执行下滑操作,到达顶端时执行的操作
            dy = -1 * mScroller.getFinalY();
        }
        if (scrollY >= totalRectHeight_Y - getHeight()) {
            //执行上滑操作,到达底端时执行的操作
            dy = totalRectHeight_Y - getHeight() - mScroller.getFinalY();
        }
        int scrollX = mScroller.getFinalX() + dx;
        if (scrollX < 0) { //执行下滑操作,到达顶端时执行的操作
            dx = -1 * mScroller.getFinalX();
        }
        if (scrollX >= totalRectHeight_X - getWidth()) {
            //执行上滑操作,到达底端时执行的操作
            dx = totalRectHeight_X - getWidth() - mScroller.getFinalX();
        }
        Log.i("scroller", "      " + mScroller.getFinalX() + "   " + mScroller.getFinalY());
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
        mBodyInterface2.changeTranselate(mScroller.getFinalX(), mScroller.getFinalY());
        mBodyInterface3.mapStatus(true);
    }

    public SelfGroup.BodyInterface mBodyInterface;

    public interface BodyInterface {
        void getMoveParams_BODY(int distanceX, int distanceY);

        void getUpFling_Body(int velocityX, int velocityY);

        void getUpScroll_BODY(int distanceX, int distanceY);
    }

    public void setInterface(SelfGroup.BodyInterface mBodyInterface) {
        this.mBodyInterface = mBodyInterface;
    }

    public SelfGroup.BodyInterface1 mBodyInterface1;

    public interface BodyInterface1 {
        void getMoveParams_BODY(int distanceX, int distanceY);

        void getUpFling_Body(int velocityX, int velocityY);

        void getUpScroll_BODY(int distanceX, int distanceY);
    }

    public void setInterface1(SelfGroup.BodyInterface1 mBodyInterface1) {
        this.mBodyInterface1 = mBodyInterface1;
    }

    public SelfGroup.BodyInterface2 mBodyInterface2;

    public interface BodyInterface2 {
        void changeParams(int totalWith, int totalHeight, int row, List<List<String>> strListType, int parentWidth, int patentHeight);

        void changeTranselate(float transX, float transY);
    }

    public void setInterface2(SelfGroup.BodyInterface2 mBodyInterface2) {
        this.mBodyInterface2 = mBodyInterface2;
    }

    public SelfGroup.BodyInterface3 mBodyInterface3;

    public interface BodyInterface3 {
        void mapStatus(boolean isVisiMap);
    }

    public void setInterface3(SelfGroup.BodyInterface3 mBodyInterface3) {
        this.mBodyInterface3 = mBodyInterface3;
    }

    /**
     * 自定义点击事件
     */
    public OnItemsClickListener mOnItemsClickListener;

    public interface OnItemsClickListener {
        void OnitemListener(View view, int position);
    }

    public void setmOnItemsClickListener(OnItemsClickListener mOnItemsClickListener) {
        this.mOnItemsClickListener = mOnItemsClickListener;
    }
}
