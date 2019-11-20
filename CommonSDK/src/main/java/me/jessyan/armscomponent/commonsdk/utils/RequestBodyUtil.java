package me.jessyan.armscomponent.commonsdk.utils;

import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import me.jessyan.armscomponent.commonsdk.base.bean.RequestBodyBean;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author :hexingbo
 * @Date :2019/4/18
 * @FileName： RequestBodyUtil
 * @Describe :
 */
public class RequestBodyUtil {

    public static RequestBody getRequestBodyValue(RequestBodyBean bodyBean) {
        //构建body
        //addFormDataPart()第一个参数为表单名字，这是和后台约定好的
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
        Map<String, List<String>> mapList = bodyBean.getMapList();
        if (!ArmsUtils.isEmpty(mapList)) {
            for (String key : mapList.keySet()) {
                List<String> list = mapList.get(key);
                if (!ArmsUtils.isEmpty(mapList)) {
                    for (String value : list) {
                        builder.addFormDataPart(key, value);
                    }
                }
            }
        }

        Map<String, Object> mapValue = bodyBean.getMapValue();
        if (!ArmsUtils.isEmpty(mapValue)) {
            for (String key : mapValue.keySet()) {
                builder.addFormDataPart(key, mapValue.get(key) + "");
            }
        }

        return builder.build();
    }

    public static RequestBody getRequestBodyFile(Map<String, File> mapFile) {
        //构建body
        //addFormDataPart()第一个参数为表单名字，这是和后台约定好的
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (!ArmsUtils.isEmpty(mapFile))
            //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
            for (String key : mapFile.keySet()) {
                File file = mapFile.get(key);
                if (FileSizeUtil.getFileOrFilesSize(file.getPath(), FileSizeUtil.SIZETYPE_KB) >= 1024) {
                    if (ImageUtil.qualityCompress(ImageUtil.fileToBitmap(file.getPath()), 500, file)) {
                        builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
                    }
                } else {
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
            }
        return builder.build();
    }

    public static RequestBody getRequestBodyValueAndFile(Map<String, File> mapFile, RequestBodyBean bodyBean) {
        //构建body
        //addFormDataPart()第一个参数为表单名字，这是和后台约定好的
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        Map<String, List<String>> mapList = bodyBean.getMapList();
        if (!ArmsUtils.isEmpty(mapList)) {
            for (String key : mapList.keySet()) {
                List<String> list = mapList.get(key);
                if (!ArmsUtils.isEmpty(mapList)) {
                    for (String value : list) {
                        builder.addFormDataPart(key, value);
                    }
                }
            }
        }

        Map<String, Object> mapValue = bodyBean.getMapValue();
        if (!ArmsUtils.isEmpty(mapValue)) {
            for (String key : mapValue.keySet()) {
                builder.addFormDataPart(key, mapValue.get(key) + "");
            }
        }

        if (!ArmsUtils.isEmpty(mapFile)) {
            //注意，file是后台约定的参数，如果是多图，file[]，如果是单张图片，file就行
            for (String key : mapFile.keySet()) {
                File file = mapFile.get(key);
                if (file.exists()) {
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
                }
            }
        }

        return builder.build();
    }
}
