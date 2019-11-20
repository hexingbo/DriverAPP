package me.jessyan.armscomponent.commonservice.me.bean;

import android.support.v4.app.Fragment;

/**
 * @Author :hexingbo
 * @Date :2019/9/27
 * @FileName： MyFragmentView
 * @Describe :
 */
public class WayBillManagerFragmentView {

    private Fragment wayBillManagert;

    public WayBillManagerFragmentView(Fragment wayBillManagert) {
        this.wayBillManagert = wayBillManagert;
    }

    public Fragment getWayBillManagert() {
        return wayBillManagert;
    }

    public void setWayBillManagert(Fragment wayBillManagert) {
        this.wayBillManagert = wayBillManagert;
    }
}
