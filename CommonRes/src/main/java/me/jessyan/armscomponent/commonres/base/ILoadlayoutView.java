package me.jessyan.armscomponent.commonres.base;


/**
 * @Author :hexingbo
 * @Date :2019/10/31
 * @FileName： ILoadlayoutView
 * @Describe :
 */
public interface ILoadlayoutView {

    void setLayoutState_LOADING();

    void setLayoutState_SUCCESS();

    void setLayoutState_FAILED();

    void setLayoutState_NO_DATA();

}
