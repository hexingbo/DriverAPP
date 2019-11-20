package me.jessyan.armscomponent.commonsdk.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @Author :hexingbo
 * @Date :2019/4/10
 * @FileName： LayoutManagerUtil
 * @Describe :
 */
public class LayoutManagerUtil {
    public static LinearLayoutManager getLinearLayoutManager_VERTICAL(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    public static LinearLayoutManager getLinearLayoutManager_HORIZONTAL(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    public static GridLayoutManager getGridLayoutManager(Context context, int spanCount) {
        return new GridLayoutManager(context, spanCount);
    }

    public static StaggeredGridLayoutManager getStaggeredGridLayoutManager(int spanCount) {
        return new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
    }
}
