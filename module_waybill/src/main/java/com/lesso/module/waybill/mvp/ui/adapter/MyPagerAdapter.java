package com.lesso.module.waybill.mvp.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.BaseLazyLoadFragment;

import java.util.ArrayList;

/**
 * @Author :hexingbo
 * @Date :2019/11/16
 * @FileName： MyPagerAdapter
 * @Describe :
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public String[] mTitles;
    public ArrayList<BaseLazyLoadFragment> mFragments;

    public MyPagerAdapter(FragmentManager fm, String[] mTitles, ArrayList<BaseLazyLoadFragment> mFragments) {
        super(fm);
        this.mTitles = mTitles;
        this.mFragments = mFragments;
    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragments.get(position);
    }
}
