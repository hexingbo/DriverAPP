package com.jess.arms.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者 ：贺兴波
 * 时间 ：2018/6/8
 * 描述 ：AppManagerUtil  需要在application里初始化，提供一些api用于app状态判断和activity栈管理等
 */
public class AppManagerUtil {

    private static Application sContext;

    private static final int STATUS_FORCE_KILLED = -1;
    private static final int STATUS_NORMAL = 0;

    /**
     * 当前应用的状态，默认为被回收状态；
     * ps：当最小化应用后，如果被强杀或者回收了，此值会重置为默认；
     * 我们在应用的第一个界面（WelcomeActivity）修改此值为normal状态，在BaseActivity中判断此值，
     * 如果处于回收状态，则可以进行重启等炒作，否则才进行初始化等操作，以此规避回收后恢复界面时某些对象为空引起crash问题；
     */
    private int appStatus = STATUS_FORCE_KILLED;
    private int activeCount = 0;// 当前活动的activity数
    private boolean isForeground = false;// 应用是否在前台


    private AppManagerUtil() {
    }

    public static AppManagerUtil getInstance() {
        return InstanceHolder.sInstance;
    }

    private static class InstanceHolder {
        private static final AppManagerUtil sInstance = new AppManagerUtil();
    }

    public void init(Application application) {
        sContext = application;
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityStack.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                activeCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                isForeground = true;
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                activeCount--;
                if (activeCount <= 0) {
                    isForeground = false;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStack.remove(activity);
            }
        });
    }

    /**
     * @return 全局context
     */
    public static Context appContext() {
        if (sContext == null)
            throw new IllegalStateException("AppManagerUtil must be init in application!");
        return sContext;
    }

    /**
     * 设置应用为正常状态
     */
    public void setStatusNormal() {
        this.appStatus = STATUS_NORMAL;
    }

    /**
     * @return 当前应用是否在前台
     */
    public boolean isForeground() {
        return isForeground;
    }

    /**
     * @return 当前应用是否被回收
     */
    public boolean isForceKilled() {
        return appStatus == STATUS_FORCE_KILLED;
    }

    // **********************************************

    /**
     * 跳转
     *
     * @param aty
     */
    public static void jump(Class<? extends Activity> aty) {
        Activity context = ActivityStack.getCurrentActivity();
        context.startActivity(new Intent(context, aty));
    }

    /**
     * 跳转（一个）并传值
     *
     * @param aty
     * @param key
     * @param value
     */
    public static void jump(Class<? extends Activity> aty, String key, Serializable value) {
        Activity context = ActivityStack.getCurrentActivity();
        context.startActivity(new Intent(context, aty).putExtra(key, value));
    }

    /**
     * 跳转下一个，关闭当前
     *
     * @param aty
     */
    public static void jumpAndFinish(Class<? extends Activity> aty) {
        Activity context = ActivityStack.getCurrentActivity();
        context.startActivity(new Intent(context, aty));
        context.finish();
    }

    /**
     * 跳转传值关闭（一个）
     *
     * @param aty
     * @param key
     * @param value
     */
    public static void jumpAndFinish(Class<? extends Activity> aty, String key, Serializable value) {
        Activity context = ActivityStack.getCurrentActivity();
        context.startActivity(new Intent(context, aty).putExtra(key, value));
        context.finish();
    }

    /**
     * 跳转传值
     *
     * @param aty
     * @param map
     */
    public static void jump(Class<? extends Activity> aty, Map<String, Serializable> map) {
        Activity context = ActivityStack.getCurrentActivity();
        Intent intent = new Intent(context, aty);
        if (map != null) {
            for (String key : map.keySet()) {
                intent.putExtra(key, map.get(key));
            }
        }
        context.startActivity(intent);
    }

    /**
     * 跳转传值关闭
     *
     * @param aty
     * @param map
     */
    public static void jumpAndFinish(Class<? extends Activity> aty, Map<String, Serializable> map) {
        Activity context = ActivityStack.getCurrentActivity();
        Intent intent = new Intent(context, aty);
        if (map != null) {
            for (String key : map.keySet()) {
                intent.putExtra(key, map.get(key));
            }
        }
        context.startActivity(intent);
        context.finish();
    }

    /**
     * 获取当前的activity
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        return ActivityStack.getCurrentActivity();
    }

    /**
     * 退出一个activity
     *
     * @param clazz
     * @return
     */
    public static boolean isExists(Class<? extends Activity> clazz) {
        return ActivityStack.isExists(clazz);
    }

    /**
     * 关闭一个activity
     *
     * @param clazz
     */
    public static void finishExcept(Class<? extends Activity> clazz) {
        ActivityStack.finishExcept(clazz);
    }

    /**
     * 关闭一个打标记的activity
     *
     * @param clazz
     */
    public static void finishTargetActivity(Class<? extends Activity> clazz) {
        ActivityStack.finishTargetActivity(clazz);
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        ActivityStack.exitApp();
    }

    /**
     * 模拟activity栈，对activity进行管理
     */
    private static class ActivityStack {
        private static final LinkedList<Activity> STACK = new LinkedList<>();

        // 入栈
        public static void add(Activity aty) {
            synchronized (ActivityStack.class) {
                STACK.addLast(aty);
            }
        }

        // 出栈
        public static void remove(Activity aty) {
            synchronized (ActivityStack.class) {
                if (STACK.contains(aty))
                    STACK.remove(aty);
            }
        }

        //关闭指定activity
        public static void finishTargetActivity(Class<? extends Activity> clazz) {
            for (Activity activity : STACK) {
                if (activity.getClass().equals(clazz)) {
                    remove(activity);
                }
            }
        }

        public static Activity getCurrentActivity() {
            return STACK.getLast();
        }

        public static boolean isExists(Class<? extends Activity> clazz) {
            for (Activity aty : STACK) {
                if (aty.getClass().getSimpleName().equals(clazz.getSimpleName()))
                    return true;
            }
            return false;
        }

        public static void exitApp() {
            synchronized (ActivityStack.class) {
                List<Activity> copy = new LinkedList<>(STACK);
                for (Activity aty : copy) {
                    aty.finish();
                }
                copy.clear();

//                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }

        public static void finishExcept(Class<? extends Activity> clazz) {
            synchronized (ActivityStack.class) {
                List<Activity> copy = new LinkedList<>(STACK);
                for (Activity aty : copy) {
                    if (!aty.getClass().equals(clazz)) {
                        aty.finish();
                    }
                }
                copy.clear();
            }
        }
    }
}
