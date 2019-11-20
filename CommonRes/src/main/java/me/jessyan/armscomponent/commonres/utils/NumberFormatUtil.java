package me.jessyan.armscomponent.commonres.utils;

import android.content.Context;

import com.jess.arms.utils.ArmsUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import me.jessyan.armscomponent.commonres.R;

/**
 * @Author :hexingbo
 * @Date :2019/4/22
 * @FileName： NumberF
 * @Describe :
 */
public class NumberFormatUtil {

    public static double numberDouble(String s) {
        if (ArmsUtils.isEmpty(s)) {
            s = "0";
        } else {
            if (s.contains(",")) {
                s = s.replaceAll(",", "");
            }
        }
        return Double.valueOf(s);
    }


    /**
     * @param d #.00
     * @return
     */
    public static String numberFormat(double d) {
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return df.format(d);
    }

    public static String numberFormat(String d) {
        if (ArmsUtils.isEmpty(d)) {
            d = "0.00";
        } else {
            d = d.replace(",", "");
        }
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return df.format(Double.valueOf(d));
    }

    /**
     * @param d #¥.00
     * @return
     */
    public static String moneyFormat(Context context, double d) {
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return context.getResources().getString(R.string.str_money_lab) + df.format(d);
    }

    /**
     * @param d #¥.00
     * @return
     */
    public static String moneyFormat(Context context, String d) {
        if (ArmsUtils.isEmpty(d)) {
            d = "0.00";
        } else {
            d = d.replace(",", "");
        }
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return context.getResources().getString(R.string.str_money_lab) + df.format(Double.valueOf(d));
    }

    /**
     * 把科学计数法显示出全部数字
     *
     * @param d
     */
    public static String object2Str(Object d) {
        if (d == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(d);
    }

    public static String getNumberFormat(int numberDigits, String number) {
        DecimalFormat df;
        switch (numberDigits) {
            case 0:
//	                value = Integer.valueOf(value) + "";
                df = new DecimalFormat("#0");
                break;
            case 1:
                df = new DecimalFormat("#0.0");
                break;
            case 2:
                df = new DecimalFormat("#0.00");
                break;
            case 3:
                df = new DecimalFormat("#0.000");
                break;
            case 4:
                df = new DecimalFormat("#0.0000");
                break;
            default:
                df = new DecimalFormat("#0.00");
                break;
        }
        String value = number==null||number.equals("")||number.length()==0 ? "0" : number;
        return df.format(new BigDecimal(value));
    }
}
