package me.jessyan.armscomponent.commonres.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @Author :hexingbo
 * @Date :2019/10/28
 * @FileName： MyFragmentUtils
 * @Describe :
 */
public class MyFragmentUtils {

    /**
     * 初始化第一个默认的fragment
     */
    public static void addFragment(FragmentManager manager, Fragment addFragment, int addContentId) {
        //开启一个事务
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //replace：移除之前所有的碎片，替换新的碎片（remove和add的集合体）(很少用，不推荐，因为是重新加载，所以消耗流量)
        //参数：1.公共父容器的的id  2.fragment的碎片
        fragmentTransaction.add(addContentId, addFragment);
        fragmentTransaction.addToBackStack(null);

        //提交事务
        fragmentTransaction.commit();
    }
}
