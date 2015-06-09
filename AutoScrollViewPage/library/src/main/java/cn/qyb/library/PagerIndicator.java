package cn.qyb.library;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by qiaoyubo on 2015/6/6.
 */
public class PagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener{

    private static final int DEFAULT_SIZE = 100;
    private static final int DEFAULT_INDICATOR_RADIUS = 10;
    private static final int DEFAULT_SPACE = 10;

    private ViewPager mViewPager;

    private int mIndicatorRadius;
    private int mSpace;

    private int mCurrentItem = 0;
    private int mTotalCount;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        configIndicator();
    }

    private void configIndicator() {
        mIndicatorRadius = DEFAULT_INDICATOR_RADIUS;
        mSpace = DEFAULT_SPACE;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

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
            addIndicator();
            for (int i = 1; i < mTotalCount; i++) {
                addIndicator();
            }
        }
    }

    private void addIndicator() {
        View indicator = new View(getContext());
        indicator.setBackgroundColor(Color.BLUE);
        addView(indicator, mIndicatorRadius, mIndicatorRadius);
        LayoutParams params = (LayoutParams) indicator.getLayoutParams();
        params.leftMargin = mSpace / 2;
        params.rightMargin = mSpace / 2;
        indicator.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = measureSize(w, widthMode);
        height = measureSize(h, heightMode);

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureSize(int size, int mode) {
        int measuredSize;
        if (mode == MeasureSpec.EXACTLY) {
            measuredSize = size;
        } else {
            measuredSize = DEFAULT_SIZE;
            if (mode == MeasureSpec.AT_MOST) {
                measuredSize = Math.min(measuredSize, size);
            }
        }
        return measuredSize;
    }
}
