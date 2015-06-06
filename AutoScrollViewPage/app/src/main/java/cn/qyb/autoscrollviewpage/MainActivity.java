package cn.qyb.autoscrollviewpage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.qyb.library.AutoScrollViewPage;


public class MainActivity extends Activity {

    private AutoScrollViewPage viewPage;
    private int[] imgRes = new int[]{R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4};
    private List<ImageView> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        viewPage = (AutoScrollViewPage) findViewById(R.id.vp);
        views = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(imgRes[i]);
            views.add(iv);
        }

        MyPageAdapter adapter = new MyPageAdapter(views);
        viewPage.setAdapter(adapter);
        viewPage.startScroll();
    }

    public class MyPageAdapter extends PagerAdapter {

        private List<ImageView> mViews;

        public MyPageAdapter(List<ImageView> views) {
            mViews = views;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((AutoScrollViewPage)container).addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((AutoScrollViewPage)container).removeView(mViews.get(position));
        }
    }

}
