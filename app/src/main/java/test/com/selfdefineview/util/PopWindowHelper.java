package test.com.selfdefineview.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import test.com.selfdefineview.R;

/**
 * Created by zhangsixia on 17/7/27.
 */
public class PopWindowHelper {

    //存储开启的popupwindow
    public static ArrayList<PopupWindow> popupWindows = new ArrayList<>();
    //存储要加载的关闭动画 关闭时取0号位置开启
    public static ArrayList<AnimatorSet> animators = new ArrayList<>();

    public static void showHomeAd(final Context context, final View view, final View parent) {

        final ImageView showImageView = (ImageView) view.findViewById(R.id.showImageView);
        final ImageView cancelImageView = (ImageView) view.findViewById(R.id.cancelImageView);

        ObjectAnimator animatorMoveY = ObjectAnimator.ofFloat(showImageView, "translationY", BitmapUtils.dp2px(context, -AppInfo.SCREEN_HEIGHT / 2), 0f);
        ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(showImageView, "rotation", 45f, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorMoveY).with(animatorRotate);
        animSet.setDuration(1000);
        animSet.start();
        animatorMoveY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int[] location = new int[2];
                cancelImageView.getLocationOnScreen(location);
                if (cancelImageView.getVisibility() == View.VISIBLE && location[1] == 0) {
                    RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) cancelImageView.getLayoutParams();
                    layout.topMargin = BitmapUtils.dp2px(context, 20);
                    cancelImageView.setLayoutParams(layout);
                }
                cancelImageView.setVisibility(View.VISIBLE);
                showImageView.setVisibility(View.VISIBLE);
            }
        });

        PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.btn_transparent_black));


        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindows.add(0, popupWindow);
    }

    /**
     * 弹出窗 上部下拉效果
     *
     * @param context
     * @param view
     * @param parent
     */
    public static void showTop(Context context, final View view, View parent) {

        MyPopupWindow popupWindow = new MyPopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.btn_transparent_black));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(parent);
        popupWindows.add(0, popupWindow);
    }

    /**
     * 弹出窗 上部下拉效果 内容包裹
     *
     * @param context
     * @param view
     * @param parent
     */
    public static void showTop2(Context context, final View view, View parent) {

        PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
//                R.drawable.btn_transparent_black));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(parent);
        popupWindows.add(0, popupWindow);
    }

    /**
     * 弹出窗 中心缩放+淡出效果
     *
     * @param context
     * @param view
     * @param parent
     */
    public static void showCenter(Context context, final View view, View parent) {

        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1.2f, 1.0f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1.2f, 1.0f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorScaleX).with(animatorScaleY).with(animatorAlpha);
        animSet.setDuration(300);
        animSet.start();
        animatorScaleX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });

        PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.btn_transparent_black));

        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindows.add(0, popupWindow);

        ObjectAnimator animatorScaleXclose = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0f);
        ObjectAnimator animatorScaleYclose = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0f);
        ObjectAnimator animatorAlphaclose = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0f);
        AnimatorSet animSetclose = new AnimatorSet();
        animSetclose.play(animatorScaleXclose).with(animatorScaleYclose).with(animatorAlphaclose);
        animSetclose.setDuration(300);
        animators.add(animSetclose);
    }

    /**
     * 弹出窗 中心缩放+淡出效果(缩放平缓)
     *
     * @param context
     * @param view
     * @param parent
     */
    public static void showCenter2(Context context, final View view, View parent) {

        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 0.4f, 0.8f, 1.1f, 1.08f, 1.06f, 1.04f, 1.02f, 1.0f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 0.4f, 0.8f, 1.1f, 1.08f, 1.06f, 1.04f, 1.02f, 1.0f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorScaleX).with(animatorScaleY).with(animatorAlpha);
        animSet.setDuration(800);
        animSet.start();
        animatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        animatorAlpha.start();

        PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.btn_transparent_black2));

        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popupWindows.add(0, popupWindow);

        ObjectAnimator animatorScaleXclose = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0f);
        ObjectAnimator animatorScaleYclose = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0f);
        ObjectAnimator animatorAlphaclose = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0f);
        AnimatorSet animSetclose = new AnimatorSet();
        animSetclose.play(animatorScaleXclose).with(animatorScaleYclose).with(animatorAlphaclose);
        animSetclose.setDuration(300);
        animators.add(animSetclose);
    }

    /**
     * 弹出窗 下部弹出效果
     *
     * @param context
     * @param view
     * @param parent
     */
    public static void showBottom(Context context, final View view, View parent) {

        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        view.setPivotY(AppInfo.SCREEN_HEIGHT);
        animSet.play(animatorScaleY);
        animSet.setDuration(300);
        animSet.start();

        PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.btn_transparent_black));

        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindows.add(0, popupWindow);


        ObjectAnimator animatorScaleYclose = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0f);
        AnimatorSet animSetclose = new AnimatorSet();
        animSetclose.play(animatorScaleYclose);
        animSetclose.setDuration(300);
        animators.add(animSetclose);

    }

    /**
     * 弹出窗 弹出无效果 关闭有效果
     *
     * @param context
     * @param view
     * @param parent
     */
    public static void showBottom2(Context context, final View view, View parent) {

        view.setPivotY(AppInfo.SCREEN_HEIGHT);

        PopupWindow popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.btn_transparent_black));

        // 设置好参数之后再show
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindows.add(0, popupWindow);

        ObjectAnimator animatorScaleYclose = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0f);
        AnimatorSet animSetclose = new AnimatorSet();
        animSetclose.play(animatorScaleYclose);
        animSetclose.setDuration(300);
        animators.add(animSetclose);

    }


    public static void closePopWindow() {
        if (popupWindows != null && !popupWindows.isEmpty()) {
            final PopupWindow popupWindow = popupWindows.get(0);
            if (popupWindow.isShowing()) {
                if (!animators.isEmpty()) {//有动画时先播放动画然后关闭
                    final AnimatorSet set = animators.get(0);
                    set.start();
                    set.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            popupWindow.dismiss();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animators.remove(set);
                } else {
                    popupWindow.dismiss();
                }
            }
            popupWindows.remove(popupWindow);
        }
    }

}
