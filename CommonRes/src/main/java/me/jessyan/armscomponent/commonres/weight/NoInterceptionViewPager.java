package me.jessyan.armscomponent.commonres.weight;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class NoInterceptionViewPager extends ViewPager {
    private static final String TAG = "NoInterceptionViewPager";
    private boolean isScroll = true;
    private int lastX;
    private int lastY;

    public NoInterceptionViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoInterceptionViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                Log.i(TAG, "dealtX:=" + dealtX);
                Log.i(TAG, "dealtY:=" + dealtY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }


//    /**
//     * 1.dispatchTouchEvent一般情况不做处理
//     * ,如果修改了默认的返回值,子孩子都无法收到事件
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);   // return true;不行
//    }
//
//    /**
//     * 是否拦截
//     * 拦截:会走到自己的onTouchEvent方法里面来
//     * 不拦截:事件传递给子孩子
//     */
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        // return false;//可行,不拦截事件,
//        // return true;//不行,孩子无法处理事件
//        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
//        if (isScroll) {
//            return super.onInterceptTouchEvent(ev);
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 是否消费事件
//     * 消费:事件就结束
//     * 不消费:往父控件传
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        //return false;// 可行,不消费,传给父控件
//        //return true;// 可行,消费,拦截事件
//        //super.onTouchEvent(ev); //不行,
//        //虽然onInterceptTouchEvent中拦截了,
//        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
//        if (isScroll) {
//            return super.onTouchEvent(ev);
//        } else {
//            return true;// 可行,消费,拦截事件
//        }
//    }
//
//    public void setScroll(boolean scroll) {
//        isScroll = scroll;
//    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}