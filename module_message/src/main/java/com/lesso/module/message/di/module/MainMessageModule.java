package com.lesso.module.message.di.module;

import dagger.Binds;
import dagger.Module;

import com.lesso.module.message.mvp.contract.MainMessageContract;
import com.lesso.module.message.mvp.model.MainMessageModel;



@Module
public abstract class MainMessageModule {

    @Binds
    abstract MainMessageContract.Model bindMainMessageModel(MainMessageModel model);
}