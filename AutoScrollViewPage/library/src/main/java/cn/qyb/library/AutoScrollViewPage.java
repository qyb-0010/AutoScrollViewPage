package cn.qyb.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AutoScrollViewPage extends ViewPager implements NoLeakHandler.MsgHandler{

    public static final int MODE_SLIDE_NONE = 0;
    public static final int MODE_SLIDE_TO_PARENT = 1;

    private static final int MSG_SCROLL = 1;
    private static final int DEFAULT_INTERVAL = 2000;

    private boolean isAutoScroll;
    private boolean mStopScrollWhenTouch = true;

    private long mInterval;

    private Handler mHandler;
    private int mCurrentItem = 0;
    private int mSlideMode;
    private float mTouchX;
    private float mDownX;

    public AutoScrollViewPage(Context context) {
        this(context, null);
    }

    public AutoScrollViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHandler = new NoLeakHandler(this);
        mInterval = DEFAULT_INTERVAL;
        mSlideMode = MODE_SLIDE_NONE;
    }

    public void startScroll() {
        isAutoScroll = true;
        sendScrollMsg(mInterval);
    }

    public void startScroll(long timeDelay) {
        isAutoScroll = true;
        sendScrollMsg(timeDelay);
    }

    private void sendScrollMsg(long timeDelay) {
        mHandler.removeMessages(MSG_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_SCROLL, timeDelay);
    }

    private void scrollToNext() {
        PagerAdapter adapter = getAdapter();
        int totalCount = 0;
        if (adapter == null || adapter.getCount() <= 1) {
            return;
        }
        totalCount = adapter.getCount();
        int nextItem = ++ mCurrentItem;
        if (nextItem == totalCount) {
            mCurrentItem = 0;
            setCurrentItem(mCurrentItem, true);
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    public void stopScroll () {
        isAutoScroll = false;
        mHandler.removeMessages(MSG_SCROLL);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        if (mStopScrollWhenTouch) {
            if (action == MotionEvent.ACTION_DOWN) {
                stopScroll();
            } else if (action == MotionEvent.ACTION_UP) {
                startScroll();
            }
        }
        if (mSlideMode == MODE_SLIDE_TO_PARENT) {
            mTouchX = ev.getX();
            if (action == MotionEvent.ACTION_DOWN) {
                mDownX = mTouchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int totalCount = adapter == null ? 0 : adapter.getCount();

            if (currentItem == 0 && mDownX <= mTouchX ||
                    currentItem == totalCount - 1 || mDownX >= mTouchX) {
                getParent().requestDisallowInterceptTouchEvent(false);
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_SCROLL:
                scrollToNext();
                sendScrollMsg(mInterval);
                break;
        }
    }

    public void setInterval(long interval) {
        this.mInterval = interval;
    }

    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        mStopScrollWhenTouch = stopScrollWhenTouch;
    }

    public void setSlideMode(int mode) {
        mSlideMode = mode;
    }

    public boolean isScrolling() {
        return isAutoScroll;
    }
}
