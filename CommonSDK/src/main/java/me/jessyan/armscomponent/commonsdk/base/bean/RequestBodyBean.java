package me.jessyan.armscomponent.commonsdk.base.bean;

import java.util.List;
import java.util.Map;

/**
 * @Author :hexingbo
 * @Date :2019/4/18
 * @FileName： RequestBodyBean
 * @Describe :
 */
public class RequestBodyBean {

    private Map<String, List<String>> mapList;
    private Map<String, Object> mapValue;

    public RequestBodyBean() {

    }

    public RequestBodyBean(Map<String, List<String>> mapList, Map<String, Object> mapValue) {
        this.mapList = mapList;
        this.mapValue = mapValue;
    }

    public Map<String, List<String>> getMapList() {
        return mapList;
    }

    public void setMapList(Map<String, List<String>> mapList) {
        this.mapList = mapList;
    }

    public Map<String, Object> getMapValue() {
        return mapValue;
    }

    public void setMapValue(Map<String, Object> mapValue) {
        this.mapValue = mapValue;
    }
}
