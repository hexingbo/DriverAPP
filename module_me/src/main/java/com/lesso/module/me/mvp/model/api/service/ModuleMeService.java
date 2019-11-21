package com.lesso.module.me.mvp.model.api.service;

import com.lesso.module.me.mvp.model.api.Api;
import com.lesso.module.me.mvp.model.entity.CompanyJoinBean;
import com.lesso.module.me.mvp.model.entity.CompanyJoinedBean;
import com.lesso.module.me.mvp.model.entity.DriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.SubmitCompanyJoinedActionBean;
import com.lesso.module.me.mvp.model.entity.SubmitCompanyJoinedListBean;
import com.lesso.module.me.mvp.model.entity.SubmitCompanyJoiningBean;
import com.lesso.module.me.mvp.model.entity.SubmitDriverVerifyBean;
import com.lesso.module.me.mvp.model.entity.SubmitDriverVerifyDetailBean;
import com.lesso.module.me.mvp.model.entity.SubmitLoginOutBean;
import com.lesso.module.me.mvp.model.entity.UploadCardFileResultBean;
import com.lesso.module.me.mvp.model.entity.UploadHeadFileResultBean;
import com.lesso.module.me.mvp.model.entity.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonres.constant.CommonHttpUrl;
import me.jessyan.armscomponent.commonsdk.base.bean.HttpResult;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * @Author :hexingbo
 * @Date :2019/10/4
 * @FileName： ModuleMeService
 * @Describe :
 */
public interface ModuleMeService {

    /**
     * 5、退出登录
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postLoginOutUserApp)
    Observable<HttpResult> postLoginOutUserApp(@Body SubmitLoginOutBean bean);

    /**
     * 6、查询个人中心用户信息
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @GET(CommonHttpUrl.API_getUserInfo)
    Observable<HttpResult<UserInfoBean>> getUserInfo(@Query("driverId") String driverId);


    /**
     * 7、根据公司名称查询物流公司
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @GET(CommonHttpUrl.API_getSeachCompanyJoinList)
    Observable<HttpResult<List<CompanyJoinBean>>> getSeachCompanyJoinList(@Query("companyName") String companyName);

    /**
     * 8、查询推荐的物流公司
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @GET(CommonHttpUrl.API_getRecommendCompanyJoinList)
    Observable<HttpResult<List<CompanyJoinBean>>> getRecommendCompanyJoinList(@Query("records") int pageSize);

    /**
     * 9、查询物流公司详情
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @GET(CommonHttpUrl.API_getCompanyDetail)
    Observable<HttpResult<CompanyJoinBean>> getCompanyDetail(@Query("companyId") String companyId);

    /**
     * 10、司机申请加盟
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postCompanyJoin)
    Observable<HttpResult> postCompanyJoin(@Body SubmitCompanyJoiningBean bean);

    /**
     * 11、司机加盟列表
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postCompanyJoinedList)
    Observable<HttpResult<List<CompanyJoinedBean>>> postCompanyJoinedList(@Body SubmitCompanyJoinedListBean bean);

    /**
     * 12、司机加盟管理操作
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postCompanyJoinedAction)
    Observable<HttpResult> postCompanyJoinedAction(@Body SubmitCompanyJoinedActionBean bean);

    /**
     * 20、司机认证
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postDriverVerify)
    Observable<HttpResult<CompanyJoinedBean>> postDriverVerify(@Body SubmitDriverVerifyBean bean);

    /**
     * 21、司机认证个人信息查询
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_getDriverVerifyDetail)
    Observable<HttpResult<DriverVerifyDetailBean>> getDriverVerifyDetail(@Body SubmitDriverVerifyDetailBean bean);

    /**
     * 22、司机认证个人信息保存
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postSaveDriverVerifyInfo)
    Observable<HttpResult> postSaveDriverVerifyInfo(@Body DriverVerifyDetailBean bean);

    /**
     * 28、身份证、驾驶证附件上传和识别附件信息
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postUploadDriverInfoFile)
    Observable<HttpResult<UploadCardFileResultBean>> postUploadDriverInfoFile(@Body RequestBody body);

    /**
     * 29、个人头像上传
     */
    @Headers({DOMAIN_NAME_HEADER + Api.Module_Me_Doman_Name})
    @POST(CommonHttpUrl.API_postUploadDriverHeadFile)
    Observable<HttpResult<UploadHeadFileResultBean>> postUploadDriverHeadFile(@Body RequestBody body);


}
