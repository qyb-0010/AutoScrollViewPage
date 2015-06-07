package cn.qyb.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AutoScrollViewPage extends ViewPager implements NoLeakHandler.MsgHandler{

    private static final int MSG_SCROLL = 1;
    private static final int DEFAULT_INTERVAL = 2000;

    private boolean isAutoScroll;

    private long mInterval;

    private Handler mHandler;
    private int mCurrentItem = 0;

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
    }

    public void startScroll() {
        isAutoScroll = true;
        sendScrollMsg(mInterval);
    }

    public void startScroll(long timeDelay) {
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

    public boolean isScrolling() {
        return isAutoScroll;
    }
}
