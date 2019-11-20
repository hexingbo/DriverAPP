package me.jessyan.armscomponent.commonsdk.base.bean;

import android.graphics.Rect;
import android.os.Parcel;
import android.support.annotation.Nullable;

import com.previewlibrary.enitity.IThumbViewInfo;

/**
 * @Author :hexingbo
 * @Date :2019/5/14
 * @FileName： ImageLookBean
 * @Describe :
 */
public class ImageLookBean implements IThumbViewInfo {

    private String url;  //图片地址
    private Rect mBounds; // 记录坐标
    private String videoUrl;

    public ImageLookBean(String url) {
        this.url = url;
    }

    public ImageLookBean(String videoUrl, String url) {
        this.url = url;
        this.videoUrl = videoUrl;
    }

    @Override
    public String getUrl() {//将你的图片地址字段返回
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Rect getBounds() {//将你的图片显示坐标字段返回
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.mBounds, flags);
        dest.writeString(this.videoUrl);
    }

    protected ImageLookBean(Parcel in) {
        this.url = in.readString();
        this.mBounds = in.readParcelable(Rect.class.getClassLoader());
        this.videoUrl = in.readString();
    }

    public static final Creator<ImageLookBean> CREATOR = new Creator<ImageLookBean>() {
        @Override
        public ImageLookBean createFromParcel(Parcel source) {
            return new ImageLookBean(source);
        }

        @Override
        public ImageLookBean[] newArray(int size) {
            return new ImageLookBean[size];
        }
    };
}