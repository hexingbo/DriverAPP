package com.lesso.module.me.mvp.contract;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.PermissionUtil;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.lesso.module.me.mvp.model.entity.UploadCardFileResultBean;
import com.lesso.module.me.mvp.model.entity.UploadHeadFileResultBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.enums.UploadFileUserCardType;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * user：贺兴波
 * 2019/11/20 15:31
 * Description:
 * ================================================
 */
public interface UserAuthenticationContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Activity getActivity();

        RxPermissions getRxPermissions();

        PermissionUtil.RequestPermission getRequestPermission();

        void setImageViewPicture(String filePath, UploadFileUserCardType fileTypes);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 请求权限
         *
         * @param rxPermissions
         * @param requestPermission
         * @param mErrorHandler
         */
        void checkPermission( RxPermissions rxPermissions, PermissionUtil.RequestPermission requestPermission, RxErrorHandler mErrorHandler);

        /**
         * 上传文件
         *
         * @param fileTypes 文件类型字符串，多个用“，”隔开（IdCard：身份证正面照，IdCardBack：身份证反面照，
         *                  DriverCard：驾驶证正面照 DriverCardBack：驾驶证反面照 LifePhoto：手持身份证照）
         * @param fileArr   多文件上传时，文件流数组和文件类型得一一对应
         * @return
         */
        Observable<HttpResult<UploadCardFileResultBean>> postUploadDriverInfoFile(@Nullable String currentUserId, @Nullable UploadFileUserCardType fileTypes, @Nullable List<File> fileArr);

        /**
         * 实际认证提交参数
         *
         * @param currentUserId      当前用户id
         * @param name               司机姓名
         * @param idno               身份证号
         * @param driverno           驾驶证号
         * @param idCardPath         身份证正面附件地址
         * @param idCardBackPath     身份证反面附件地址
         * @param driverCardPath     驾驶证主页附件地址
         * @param driverCardBackPath 驾驶证副页附件地址
         * @param lifePhotoPath      手持身份证附件地址
         */
        Observable<HttpResult<CompanyJoinedBean>> postDriverVerify(@NonNull String currentUserId, @NonNull String name, @NonNull String idno,
                                                                   @NonNull String driverno, @NonNull String idCardPath, @NonNull String idCardBackPath,
                                                                   @NonNull String driverCardPath, @NonNull String driverCardBackPath, String lifePhotoPath);
    }
}
