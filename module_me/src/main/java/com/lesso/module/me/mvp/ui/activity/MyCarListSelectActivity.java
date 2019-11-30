package com.lesso.module.me.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lesso.module.me.R;
import com.lesso.module.me.mvp.model.entity.CarJoinBean;
import com.lesso.module.me.mvp.model.entity.CarJoinReq;
import com.lesso.module.me.mvp.ui.adapter.CarJoinAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.core.Constants;

public class MyCarListSelectActivity extends MyCarsListManagerActivity {
    private String companyId;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitle(R.string.module_me_select_join_cars);
        btnSubmit.setText(R.string.str_value_confirm);
        pageSize = Integer.MAX_VALUE;
        companyId = (String) getIntent().getSerializableExtra(Constants.KEY_COMPANY_ID);

        mAdapter = new CarJoinAdapter(new ArrayList<>(), this, true);
//        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        btnSubmit.setOnClickListener(l -> onSubmit());
    }

    private void onSubmit() {
        List<CarJoinBean> list = mAdapter.getList();
        Observable.fromIterable(list)
                  .filter(carJoinBean -> carJoinBean.isSelected())
                  .toList()
                  .subscribe(l -> {
                      if(l.isEmpty()) {
                          showMessage(getString(R.string.module_me_please_select_join_car));
                      } else {
                          CarJoinReq carJoinReq = CarJoinReq.ofSubmit(l, companyId);
                          mPresenter.joinCar(carJoinReq);
                      }
                  });
    }

    @Override
    public void onJoinCarSucceed() {
        showMessage(getString(R.string.module_me_user_submit_succeed));
        setResult(RESULT_OK);
        finish();
    }
}
