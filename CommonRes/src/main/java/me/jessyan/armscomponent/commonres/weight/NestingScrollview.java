package me.jessyan.armscomponent.commonres.weight;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * @Author :hexingbo
 * @Date :2019/10/16
 * @FileName： NestingScrollview
 * @Describe :出现新的滑动冲突的问题,导致滑动失去惯性,丝滑的感觉就再也没有了,进一步了为了牛奶般的丝滑,把ScrollView 替换为下面的自定义ScrollView 就可以了:
 */
public class NestingScrollview  extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private boolean autoScroll = true;

    /**
     * 多层嵌套时的自动滚动
     * @param autoScroll
     */
    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    /**
     * 防止多层嵌套时候的自动滚动
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return autoScroll?super.computeScrollDeltaToGetChildRectOnScreen(rect):0;
    }


    public NestingScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NestingScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NestingScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

}