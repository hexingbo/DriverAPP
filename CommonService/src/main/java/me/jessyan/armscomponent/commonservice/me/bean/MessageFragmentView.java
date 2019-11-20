package me.jessyan.armscomponent.commonservice.me.bean;

import android.support.v4.app.Fragment;

/**
 * @Author :hexingbo
 * @Date :2019/9/27
 * @FileName： MyFragmentView
 * @Describe :
 */
public class MessageFragmentView {

    private Fragment messageFragment;

    public MessageFragmentView(Fragment messageFragment) {
        this.messageFragment = messageFragment;
    }

    public Fragment getMessageFragment() {
        return messageFragment;
    }

    public void setMessageFragment(Fragment messageFragment) {
        this.messageFragment = messageFragment;
    }
}
