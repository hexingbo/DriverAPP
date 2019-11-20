package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.Nullable;

import java.io.File;

/**
 * @Author :hexingbo
 * @Date :2019/11/20
 * @FileName： SubmitUploadDriverHeadFileBean
 * @Describe :
 */
public class SubmitUploadDriverHeadFileBean {

    private String currentUserId;
    private File personFile;

    public SubmitUploadDriverHeadFileBean(@Nullable String currentUserId, @Nullable File personFile) {
        this.currentUserId = currentUserId;
        this.personFile = personFile;
    }
}
