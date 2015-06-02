package cn.qyb.library;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by qiaoyubo on 2015/6/2.
 */
public class NoLeakHandler extends Handler {

    private WeakReference<MsgHandler> mWeakRef;

    public interface MsgHandler{
        void handleMessage(Message msg);
    }

    public NoLeakHandler (MsgHandler handler) {
        mWeakRef = new WeakReference<MsgHandler>(handler);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        MsgHandler handler = mWeakRef.get();
        if (handler != null) {
            handler.handleMessage(msg);
        }
    }
}
