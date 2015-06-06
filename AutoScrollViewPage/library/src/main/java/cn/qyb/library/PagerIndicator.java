package cn.qyb.library;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by qiaoyubo on 2015/6/6.
 */
public class PagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener{

    private static final int DEFAULT_SIZE = 100;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

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
