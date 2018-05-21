package test.com.selfdefineview.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            if ("null".equals(str)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isEqual(String str1, String str2) {
        if (isEmpty(str1) || isEmpty(str2)) {
            return false;
        } else {
            if (str1.equals(str2)) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles.trim());
        return m.matches();
    }

    /**
     * 验证数量
     */
    public static boolean isNum(String num) {
        Pattern p = Pattern
                .compile("0-9");
        Matcher m = p.matcher(num.trim());
        return m.matches();
    }


    /**
     * 是否只是字母
     *
     * @param s
     * @return
     */
    public static boolean isPinyin(String s) {
        String pinyin = "^[A-Za-z]+$";/**此正则表达式判断单词字符是否为：[a-zA-Z_0-9]**/
        Pattern pattern = Pattern.compile(pinyin);
        return pattern.matcher(s).matches();
    }

    /**
     * 是否是汉字
     *
     * @param s
     * @return
     */
    public static boolean isChinese(String s) {
        String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";  /**这个正则表达式用来判断是否为中文**/
        Pattern pattern = Pattern.compile(chinese);
        return pattern.matcher(s).matches();

    }

    /**
     * 截取time
     *
     * @return
     */
    public static String subTimeString(String time) {
        try {
            return time.substring(0, time.lastIndexOf(":"));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 截取date
     *
     * @return
     */
    public static String subDateString(String time) {
        try {
            return time.substring(0, time.lastIndexOf(" "));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 把list转换成id串
     *
     * @param list
     * @return
     */
    public static String changeListToString(ArrayList<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String str : list) {
            builder.append(str).append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    public static String changeSizeToPersonnum(int size) {
        return size == 0 ? "" : size + "人";
    }

    public static String changeSizeToString(int size) {
        return size == 0 ? "" : size + "";
    }

}
