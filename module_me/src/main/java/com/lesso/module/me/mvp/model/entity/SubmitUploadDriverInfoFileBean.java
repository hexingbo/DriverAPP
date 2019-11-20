package com.lesso.module.me.mvp.model.entity;

import android.support.annotation.Nullable;

import java.io.File;
import java.util.List;

import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;

/**
 * @Author :hexingbo
 * @Date :2019/11/20
 * @FileName： SubmitUploadDriverInfoFileBean
 * @Describe :
 */
public class SubmitUploadDriverInfoFileBean {

    private String fileTypes;
    private List<File> fileArr;

    public SubmitUploadDriverInfoFileBean(@Nullable UploadFileUserCardType fileTypes,@Nullable List<File> fileArr) {
        this.fileTypes = fileTypes.name();
        this.fileArr = fileArr;
    }

}
