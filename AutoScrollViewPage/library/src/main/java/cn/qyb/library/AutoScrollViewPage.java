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

    public static final int DIRECTION_LEFT = 10;
    public static final int DIRECTION_RIGHT = 11;

    private static final int MSG_SCROLL = 1;
    private static final int DEFAULT_INTERVAL = 2000;

    private boolean isAutoScroll;
    private boolean mStopScrollWhenTouch = true;

    private long mInterval;

    private Handler mHandler;
    private int mCurrentItem = 0;
    private int mSlideMode;
    private float mDownX;
    private int mDirection;

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
        mDirection = DIRECTION_RIGHT;
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
        int totalCount;
        if (adapter == null || adapter.getCount() <= 1) {
            return;
        }
        totalCount = adapter.getCount();
        mCurrentItem = getCurrentItem();
        int nextItem = mDirection == DIRECTION_RIGHT ? ++ mCurrentItem : --mCurrentItem;
        if (nextItem < 0) {
            setCurrentItem(totalCount -1, true);
        } else if (nextItem == totalCount) {
            setCurrentItem(0, true);
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
            float touchX = ev.getX();
            if (action == MotionEvent.ACTION_DOWN) {
                mDownX = touchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int totalCount = adapter == null ? 0 : adapter.getCount();

            if (currentItem == 0 && mDownX <= touchX ||
                    currentItem == totalCount - 1 && mDownX >= touchX) {
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

    public long getInterval() {
        return mInterval;
    }

    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        mStopScrollWhenTouch = stopScrollWhenTouch;
    }

    public void setSlideMode(int mode) {
        mSlideMode = mode;
    }

    public int getSlideMode() {
        return mSlideMode;
    }

    public boolean isScrolling() {
        return isAutoScroll;
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    public int getDirection() {
        return mDirection;
    }
}
