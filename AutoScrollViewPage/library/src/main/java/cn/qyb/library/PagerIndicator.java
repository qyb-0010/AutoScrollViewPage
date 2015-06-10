package cn.qyb.library;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by qiaoyubo on 2015/6/6.
 */
public class PagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener{

    private static final int DEFAULT_SIZE = 100;
    private static final int DEFAULT_INDICATOR_RADIUS = 20;
    private static final int DEFAULT_SPACE = 20;

    private ViewPager mViewPager;

    private int mIndicatorRadius;
    private int mSpace;

    private int mCurrentItem = 0;
    private int mTotalCount;

    private Animator mAnimationIn;
    private Animator mAnimationOut;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        configIndicator();
//        initAnim();
    }

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    private void initAnim() {
//    }

    private void configIndicator() {
        mIndicatorRadius = DEFAULT_INDICATOR_RADIUS;
        mSpace = DEFAULT_SPACE;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPageSelected(int position) {
//        if (mAnimationIn.isRunning()) {
//            mAnimationIn.end();
//        }
//        if (mAnimationOut.isRunning()) {
//            mAnimationOut.end();
//        }
        View preIndicator = getChildAt(mCurrentItem % mTotalCount);
        preIndicator.setBackgroundResource(R.drawable.indicator_unselected);
//        mAnimationOut.setTarget(preIndicator);
//        mAnimationOut.start();

        View selectedIndicator = getChildAt(position);
        selectedIndicator.setBackgroundResource(R.drawable.indicator_selected);
//        mAnimationIn.setTarget(selectedIndicator);
//        mAnimationIn.start();

        mCurrentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setViewPager (ViewPager viewPager) {
        if (viewPager.getAdapter() == null) {
            throw new IllegalArgumentException("adapter can't be null");
        }
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
        mCurrentItem = mViewPager.getCurrentItem();
        mTotalCount = mViewPager.getAdapter().getCount();
        createIndicator();
    }

    private void createIndicator() {
        removeAllViews();
        if (mTotalCount > 1) {
            addIndicator(R.drawable.indicator_selected);
            for (int i = 1; i < mTotalCount; i++) {
                addIndicator(R.drawable.indicator_unselected);
            }
        }
    }

    private void addIndicator(int backgroundRes) {
        View indicator = new View(getContext());
        indicator.setBackgroundResource(backgroundRes);
        addView(indicator, mIndicatorRadius, mIndicatorRadius);
        LayoutParams params = (LayoutParams) indicator.getLayoutParams();
        params.leftMargin = mSpace / 2;
        params.rightMargin = mSpace / 2;
        indicator.setLayoutParams(params);
    }

}
