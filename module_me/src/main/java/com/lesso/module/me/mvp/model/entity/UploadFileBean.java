package com.lesso.module.me.mvp.model.entity;

/**
 * @Author :hexingbo
 * @Date :2019/11/21
 * @FileName： UploadFileBean
 * @Describe :
 */
public class UploadFileBean {

    private String headPath;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    @Override
    public String toString() {
        return "UploadFileBean{" +
                "headPath='" + headPath + '\'' +
                '}';
    }
}
