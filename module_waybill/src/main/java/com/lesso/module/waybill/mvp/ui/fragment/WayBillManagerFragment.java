package com.lesso.module.waybill.mvp.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.MessageEvent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.AppUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.lesso.module.waybill.R;
import com.lesso.module.waybill.R2;
import com.lesso.module.waybill.di.component.DaggerWayBillManagerComponent;
import com.lesso.module.waybill.mvp.contract.WayBillManagerContract;
import com.lesso.module.waybill.mvp.model.entity.UpdateDetailBean;
import com.lesso.module.waybill.mvp.presenter.WayBillManagerPresenter;
import com.lesso.module.waybill.mvp.ui.adapter.MyPagerAdapter;
import com.ycbjie.ycupdatelib.PermissionUtils;
import com.ycbjie.ycupdatelib.UpdateFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.armscomponent.commonsdk.core.Constants;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/13 22:22
 * Description:
 * ================================================
 */
public class WayBillManagerFragment extends BaseFragment<WayBillManagerPresenter>
        implements WayBillManagerContract.View {

    @BindView(R2.id.tab_menu)
    SlidingTabLayout tabMenu;
    @BindView(R2.id.vp_content)
    ViewPager vpContent;

    @Inject
    MyPagerAdapter mAdapter;

    private int versionCode;
    private String versionName;
    private UpdateFragment updateFragment;

    private static final String[] mPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static WayBillManagerFragment newInstance() {
        WayBillManagerFragment fragment = new WayBillManagerFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWayBillManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_way_bill_manager, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        vpContent.setAdapter(mAdapter);
        vpContent.setOffscreenPageLimit(mPresenter.getFragments().size());
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPresenter.getFragments().get(i).tryLoadData();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vpContent.setCurrentItem(0);
        tabMenu.setViewPager(vpContent);

        getVersionInfo();
        mPresenter.checkVersionDetail(versionCode);
    }

    private void getVersionInfo() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getContext().getPackageManager()
                    .getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionCode = packageInfo.versionCode;
        versionName = packageInfo.versionName;
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    protected void getEventBusHub_Fragment(MessageEvent message) {

    }


    @Override
    public void onCheckVersion(boolean isNewVersion, UpdateDetailBean.AndroidBean android) {
        DataHelper.setStringSF(getContext(), Constants.SP_Update_APK_URL, android.getUpdateUrl());
        if (isNewVersion) {
            /*
             * @param isForceUpdate             是否强制更新
             * @param desc                      更新文案
             * @param url                       下载链接
             * @param apkFileName               apk下载文件路径名称
             * @param packName                  包名
             */
            //测试链接
            updateFragment = UpdateFragment.showFragment(getActivity(),
                    true, android.getUpdateUrl(), "lessodriver_apk", android.getUpdateContent(),
                    AppUtils.getPackageName(getActivity()));
        }
    }

}
