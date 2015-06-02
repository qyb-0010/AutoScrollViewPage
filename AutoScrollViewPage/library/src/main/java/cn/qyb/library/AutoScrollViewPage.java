package cn.qyb.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class AutoScrollViewPage extends ViewPager implements NoLeakHandler.MsgHandler{

    private static final int MSG_SCROLL = 1;

    private boolean isScrolling;

    private long mDelayTime;
    private long mDivideTime;

    private Handler mHandler;

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

    }

    public void stopScroll () {

    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_SCROLL:
                break;
        }
    }
}
