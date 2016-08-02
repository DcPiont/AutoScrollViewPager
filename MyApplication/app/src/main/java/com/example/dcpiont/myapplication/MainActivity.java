package com.example.dcpiont.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import POJO.ImageViewShow;
import POJO.MyViewpager;
import adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private MyViewpager viewPager;
    private ViewGroup group;
    private Switch switch_mode;
    private Switch switch_mode2;
    private Button btnLeft;
    private Button btnCenter;
    private Button btnRight;
    private List<ImageViewShow> imageViewList;
    private List<ImageView> ivFocusList;
    private ViewPagerAdapter adapter;
    private int oldItem = 0;
    private int correntItem = 0;
    private Handler handler;
    private boolean isScroll = false;//判断是否允许自动滚动
    private boolean isFirstScroll = false;//判断是否已滚动一次
    private static final int DELAY = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                /**
                 * 使用isScroll判断是否为初始载入图片，若不进行判断，则不能自动滚动，需手动滑动图片一次
                 */
                if (handler.hasMessages(1) && isFirstScroll) {
                    handler.removeMessages(1);
                }
                switch (msg.what) {
                    case 1:
                        if (isScroll) {
                            //更新显示的View
                            viewPager.setCurrentItem(++correntItem);
                            Log.i("info", "准备下次播放");
                            isFirstScroll = true;
                            sendEmptyMessageDelayed(1, DELAY);
                        }
                        break;
                    case 2:
                        //暂停轮播
                        Log.i("info", "暂停轮播");
                        break;
                    case 3:
                        //恢复轮播
                        Log.i("info", "恢复轮播");
                        sendEmptyMessageDelayed(1, DELAY);
                        break;
                    case 4:
                        //记录最新的页号
                        //Log.i("info", "记录最新的页号 " + msg.arg1);
                        correntItem = msg.arg1;
                        //Log.i("info", "oldItem:  " + oldItem);
                        ivFocusList.get(oldItem).setBackgroundResource(R.drawable.normal);
                        oldItem = correntItem % imageViewList.size();
                        ivFocusList.get(oldItem).setBackgroundResource(R.drawable.focused);
                        break;
                }
            }

        };

        //使用按钮实现跳转图片和显示信息
        /*btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isScroll)
                    viewPager.setCurrentItem(--correntItem);
            }
        });

        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isScroll)
                    Toast.makeText(getApplicationContext(), imageViewList.get(correntItem%4).getMessage(), Toast.LENGTH_SHORT);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isScroll)
                    viewPager.setCurrentItem(++correntItem);
            }
        });*/

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

                handler.sendMessage(Message.obtain(handler, 4, position, 0));
                //Toast.makeText(getApplicationContext(), ""+position%4, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(2);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(1, DELAY);
                        break;
                }
            }
        });

        viewPager.setCurrentItem(imageViewList.size() * 1000);//默认在中间，使用户看不到边界，取第一张图为默认显示图
        handler.sendEmptyMessageDelayed(1, DELAY);
    }

    private void setView() {
        viewPager = (MyViewpager) findViewById(R.id.viewPager);
        switch_mode = (Switch) findViewById(R.id.switch_mode);
        switch_mode2 = (Switch) findViewById(R.id.switch_mode2);
        //设置手势控制开关选择击事件
        switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //若开关为关闭状态，则禁用手指触摸事件
                viewPager.setScrollable(b);
            }
        });
        switch_mode2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isScroll = b;
                if (b) {
                    handler.sendEmptyMessageDelayed(1, DELAY);
                }
                //Log.i("info", "isScroll:" + isScroll);
            }
        });
        group = (ViewGroup) findViewById(R.id.viewGroup);

        //初始化图片数组
        ImageViewShow imageViewShow1 = new ImageViewShow(getApplicationContext());
        imageViewShow1.getImageView().setImageResource(R.mipmap.idolmaster);
        imageViewShow1.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewShow1.setMessage("1");
        imageViewShow1.OnClickListener();

        ImageViewShow imageViewShow2 = new ImageViewShow(getApplicationContext());
        imageViewShow2.getImageView().setImageResource(R.mipmap.brave_shine);
        imageViewShow2.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewShow2.setMessage("2");
        imageViewShow2.OnClickListener();

        ImageViewShow imageViewShow3 = new ImageViewShow(getApplicationContext());
        imageViewShow3.getImageView().setImageResource(R.mipmap.charlotte_ab_twicon_2);
        imageViewShow3.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewShow3.setMessage("3");
        imageViewShow3.OnClickListener();

        ImageViewShow imageViewShow4 = new ImageViewShow(getApplicationContext());
        imageViewShow4.getImageView().setImageResource(R.mipmap.charlotte_ab_twicon_3);
        imageViewShow4.getImageView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewShow4.setMessage("4");
        imageViewShow4.OnClickListener();

        //初始化ImageViewShow数组
        imageViewList = new ArrayList<>();
        imageViewList.add(imageViewShow1);
        imageViewList.add(imageViewShow2);
        imageViewList.add(imageViewShow3);
        imageViewList.add(imageViewShow4);

        adapter = new ViewPagerAdapter(getApplicationContext(), imageViewList);
        viewPager.setAdapter(adapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        //设置焦点
        ivFocusList = new ArrayList<>();
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView ivFocus = new ImageView(this);
            ivFocus.setLayoutParams(new ViewGroup.LayoutParams(30, 30));
            ivFocus.setPadding(20, 10, 20, 0);
            ivFocusList.add(ivFocus);
            if (i == 0)
                ivFocusList.get(i).setBackgroundResource(R.drawable.focused);
            else
                ivFocusList.get(i).setBackgroundResource(R.drawable.normal);
            group.addView(ivFocusList.get(i));
            group.setBackgroundColor(Color.GREEN);
            group.setAlpha(0.6f);
        }

        //初始化按钮
        /*btnLeft = (Button) findViewById(R.id.btnLeft);
        btnCenter = (Button) findViewById(R.id.btnCenter);
        btnRight = (Button) findViewById(R.id.btnRight);*/
    }

    /*private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }*/
}
