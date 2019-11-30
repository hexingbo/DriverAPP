package com.lesso.module.waybill.mvp.model.entity;

/**
 * @author EDZ
 */
public class UpdateDetailBean {

    /**
     * Android : {"guid":"1dc5fd06-9400-447b-8b87-c5bd7d46d16d","appType":"Android","hotUpdate":1,"versionCode":1,"versionName":"1.0新版本","updateUrl":"/upload/app/20191120\\com.qihoo.appstore_300090006.apk","updateSize":19.09,"fileMd5":null,"force":1,"silent":1,"autoInstall":1,"ignorable":1,"effectDate":"2019-11-21 11:00:00","updateContent":"1.0新版本1.0新版本1.0新版本1.0新版本","createdDate":1574245559,"status":"A","remark":"1.0新版本1.0新版本1.0新版本1.0新版本","app_source":"D"}
     */

    private AndroidBean Android;

    public AndroidBean getAndroid() {
        return Android;
    }

    public void setAndroid(AndroidBean Android) {
        this.Android = Android;
    }

    public static class AndroidBean {
        /**
         * guid : 1dc5fd06-9400-447b-8b87-c5bd7d46d16d
         * appType : Android
         * hotUpdate : 1
         * versionCode : 1
         * versionName : 1.0新版本
         * updateUrl : /upload/app/20191120\com.qihoo.appstore_300090006.apk
         * updateSize : 19.09
         * fileMd5 : null
         * force : 1
         * silent : 1
         * autoInstall : 1
         * ignorable : 1
         * effectDate : 2019-11-21 11:00:00
         * updateContent : 1.0新版本1.0新版本1.0新版本1.0新版本
         * createdDate : 1574245559
         * status : A
         * remark : 1.0新版本1.0新版本1.0新版本1.0新版本
         * app_source : D
         */

        private String guid;
        private String appType;
        private int hotUpdate;
        private int versionCode;
        private String versionName;
        private String updateUrl;
        private double updateSize;
        private Object fileMd5;
//        是否强制更新(0：不强制；1：强制)
        private int force;
        private int silent;
        private int autoInstall;
        private int ignorable;
        private String effectDate;
        private String updateContent;
        private int createdDate;
        private String status;
        private String remark;
        private String app_source;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public int getHotUpdate() {
            return hotUpdate;
        }

        public void setHotUpdate(int hotUpdate) {
            this.hotUpdate = hotUpdate;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        public double getUpdateSize() {
            return updateSize;
        }

        public void setUpdateSize(double updateSize) {
            this.updateSize = updateSize;
        }

        public Object getFileMd5() {
            return fileMd5;
        }

        public void setFileMd5(Object fileMd5) {
            this.fileMd5 = fileMd5;
        }

        public int getForce() {
            return force;
        }

        public void setForce(int force) {
            this.force = force;
        }

        public int getSilent() {
            return silent;
        }

        public void setSilent(int silent) {
            this.silent = silent;
        }

        public int getAutoInstall() {
            return autoInstall;
        }

        public void setAutoInstall(int autoInstall) {
            this.autoInstall = autoInstall;
        }

        public int getIgnorable() {
            return ignorable;
        }

        public void setIgnorable(int ignorable) {
            this.ignorable = ignorable;
        }

        public String getEffectDate() {
            return effectDate;
        }

        public void setEffectDate(String effectDate) {
            this.effectDate = effectDate;
        }

        public String getUpdateContent() {
            return updateContent;
        }

        public void setUpdateContent(String updateContent) {
            this.updateContent = updateContent;
        }

        public int getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(int createdDate) {
            this.createdDate = createdDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getApp_source() {
            return app_source;
        }

        public void setApp_source(String app_source) {
            this.app_source = app_source;
        }
    }
}
