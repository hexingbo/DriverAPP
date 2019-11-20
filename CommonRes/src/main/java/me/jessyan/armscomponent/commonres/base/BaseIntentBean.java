package me.jessyan.armscomponent.commonres.base;

import java.io.Serializable;
import java.util.List;

/**
 * =============================================
 * 作    者：贺兴波
 * 时    间：2019/11/18
 * 描    述：BaseIntentBean
 * =============================================
 */
public class BaseIntentBean<T> implements Serializable {

    private int valueInt;
    private String valueString;
    private boolean valueBoolean;
    private List<T> list;

    public BaseIntentBean() {
    }

    public BaseIntentBean(int valueInt) {
        this.valueInt = valueInt;
    }

    public BaseIntentBean(String valueString) {
        this.valueString = valueString;
    }

    public BaseIntentBean(boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public BaseIntentBean(List<T> list) {
        this.list = list;
    }

    public BaseIntentBean(int valueInt, String valueString) {
        this.valueInt = valueInt;
        this.valueString = valueString;
    }

    public BaseIntentBean(int valueInt, boolean valueBoolean) {
        this.valueInt = valueInt;
        this.valueBoolean = valueBoolean;
    }


    public BaseIntentBean(int valueInt, List<T> list) {
        this.valueInt = valueInt;
        this.list = list;
    }

    public BaseIntentBean(String valueString, boolean valueBoolean) {
        this.valueString = valueString;
        this.valueBoolean = valueBoolean;
    }

    public BaseIntentBean(String valueString, List<T> list) {
        this.valueString = valueString;
        this.list = list;
    }

    public BaseIntentBean(boolean valueBoolean, List<T> list) {
        this.valueBoolean = valueBoolean;
        this.list = list;
    }

    public BaseIntentBean(int valueInt, String valueString, List<T> list) {
        this.valueInt = valueInt;
        this.valueString = valueString;
        this.list = list;
    }

    public BaseIntentBean(int valueInt, String valueString, boolean valueBoolean) {
        this.valueInt = valueInt;
        this.valueString = valueString;
        this.valueBoolean = valueBoolean;
    }

    public BaseIntentBean(int valueInt, String valueString, boolean valueBoolean, List<T> list) {
        this.valueInt = valueInt;
        this.valueString = valueString;
        this.valueBoolean = valueBoolean;
        this.list = list;
    }

    public boolean isValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "IntentBean{" +
                "valueInt=" + valueInt +
                ", valueString='" + valueString + '\'' +
                ", list=" + list +
                '}';
    }
}
