package cn.qyb.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;

public class PagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener{

    private static final int DEFAULT_INDICATOR_RADIUS = 20;
    private static final int DEFAULT_SPACE = 20;

    private ViewPager mViewPager;

    private int mIndicatorRadius;
    private int mSpace;

    private int mCurrentItem = 0;
    private int mTotalCount;

    private int mAnimationInResId;
    private int mAnimationOutResId;

    private int mSelectedDrawableResId;
    private int mUnSelectedDrawableResId;

    private Animator mAnimationIn;
    private Animator mAnimationOut;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator);
            mIndicatorRadius = typedArray.getDimensionPixelOffset(R.styleable.PagerIndicator_radius, DEFAULT_INDICATOR_RADIUS);
            mSpace = typedArray.getDimensionPixelOffset(R.styleable.PagerIndicator_space, DEFAULT_SPACE);
            mAnimationInResId = typedArray.getResourceId(R.styleable.PagerIndicator_animatorIn, 0);
            mAnimationOutResId = typedArray.getResourceId(R.styleable.PagerIndicator_animatorOut, 0);
            mSelectedDrawableResId = typedArray.getResourceId(R.styleable.PagerIndicator_drawableSelected, R.drawable.indicator_selected);
            mUnSelectedDrawableResId = typedArray.getResourceId(R.styleable.PagerIndicator_drawableUnselected, R.drawable.indicator_unselected);
            if (mAnimationInResId != 0) {
                mAnimationIn = AnimatorInflater.loadAnimator(context, mAnimationInResId);
            }
            if (mAnimationOutResId != 0) {
                mAnimationOut = AnimatorInflater.loadAnimator(context, mAnimationOutResId);
            }
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.BOTTOM |Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (mAnimationIn.isRunning()) {
            mAnimationIn.end();
        }
        if (mAnimationOut.isRunning()) {
            mAnimationOut.end();
        }
        View preIndicator = getChildAt(mCurrentItem % mTotalCount);
        preIndicator.setBackgroundResource(mUnSelectedDrawableResId);
        mAnimationOut.setTarget(preIndicator);
        mAnimationOut.start();

        View selectedIndicator = getChildAt(position);
        selectedIndicator.setBackgroundResource(mSelectedDrawableResId);
        mAnimationIn.setTarget(selectedIndicator);
        mAnimationIn.start();

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
            addIndicator(mSelectedDrawableResId);
            for (int i = 1; i < mTotalCount; i++) {
                addIndicator(mUnSelectedDrawableResId);
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

    public void setRadius(int radius) {
        mIndicatorRadius = radius;
    }


}
