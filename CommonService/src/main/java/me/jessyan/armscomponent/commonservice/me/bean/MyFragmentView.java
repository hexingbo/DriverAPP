package me.jessyan.armscomponent.commonservice.me.bean;

import android.support.v4.app.Fragment;

/**
 * @Author :hexingbo
 * @Date :2019/9/27
 * @FileName： MyFragmentView
 * @Describe :
 */
public class MyFragmentView {

    private Fragment myFragment;

    public MyFragmentView(Fragment myFragment) {
        this.myFragment = myFragment;
    }

    public Fragment getMyFragment() {
        return myFragment;
    }

    public void setMyFragment(Fragment myFragment) {
        this.myFragment = myFragment;
    }
}
