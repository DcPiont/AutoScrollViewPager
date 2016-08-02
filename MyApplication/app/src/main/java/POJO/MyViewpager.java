package POJO;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by DcPiont on 2016/7/28.
 */

public class MyViewpager extends ViewPager {
    private boolean isScrollable;

    public MyViewpager(Context context){
        super(context);
    }

    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    public boolean getScrollable(){
        return isScrollable;
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isScrollable){
            return false;
        }
        else
            return super.onTouchEvent(ev);
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!isScrollable)
            return false;
        else
            return super.onInterceptTouchEvent(ev);
    }
}
