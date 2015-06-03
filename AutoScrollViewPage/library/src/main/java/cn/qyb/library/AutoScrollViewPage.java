package cn.qyb.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class AutoScrollViewPage extends ViewPager implements NoLeakHandler.MsgHandler{

    private static final int MSG_SCROLL = 1;

    private boolean isScrolling;

    private long mDelayTime;
    private long mDivideTime;

    private Handler mHandler;
    private int mCurrentItem = 0;

    public AutoScrollViewPage(Context context) {
        super(context);
        init();
    }

    public AutoScrollViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHandler = new NoLeakHandler(this);
    }

    public void startScroll() {
        mHandler.removeMessages(MSG_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_SCROLL, 3000);
    }

    private void scrollToNext() {
        PagerAdapter adapter = getAdapter();
        int totalCount = 0;
        if (adapter == null || adapter.getCount() <= 1) {
            return;
        }
        totalCount = adapter.getCount();
        int nextItem = ++mCurrentItem;
        if (nextItem == totalCount) {
            mCurrentItem = 0;
            setCurrentItem(mCurrentItem, true);
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    public void stopScroll () {

    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_SCROLL:
                scrollToNext();
                startScroll();
                break;
        }
    }
}
