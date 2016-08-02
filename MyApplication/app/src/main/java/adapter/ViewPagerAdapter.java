package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import POJO.ImageViewShow;

/**
 * Created by DcPiont on 2016/7/25.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ImageViewShow> imageList;
    private ImageView imageView;
    private int clickPosition = 0;
    private int nowPosition = 0;

    public ViewPagerAdapter(Context context, List<ImageViewShow> list) {
        this.context = context;
        this.imageList = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= imageList.size();
        if (position < 0) {
            position = imageList.size() + position;
        }
        nowPosition = position;
        imageView = imageList.get(position).getImageView();
        clickPosition = position - 1;
        if (clickPosition < 0)
            clickPosition = imageList.size() +clickPosition;

        Log.i("info", "现在的position：" + nowPosition);
        ViewParent vp = null;
        if (imageView != null) {
            vp = imageView.getParent();
        }
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(imageView);
        }
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, imageList.get(clickPosition).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        container.addView(imageView);

        return imageView;
    }


}
