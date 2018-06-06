package test.com.selfdefineview.spannable;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * Created by zhangsixia on 18/5/3.
 */

public class StringFormatUtil {
    private SpannableStringBuilder spBuilder;
    private String wholeStr, highlightStr;
    private Context mContext;
    private int color;
    private int start = 0, end = 0;
    private CountType countType = null;
    /**
     * @param context
     * @param wholeStr 全部文字
     * @param highlightStr 改变颜色的文字
     * @param color 颜色
     */
    public StringFormatUtil(){

    }
    public StringFormatUtil(Context context, String wholeStr, String highlightStr, int color){
        this.mContext=context;
        this.wholeStr=wholeStr;
        this.highlightStr=highlightStr;
        this.color=color;
        findCount(wholeStr);
    }
    public StringFormatUtil fillColor(){
        if(!TextUtils.isEmpty(wholeStr)&&!TextUtils.isEmpty(highlightStr)){
            if(wholeStr.contains(highlightStr)){
                /*
                 * 返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                 */
                start=wholeStr.indexOf(highlightStr);
                end=start+highlightStr.length();
            }else{
                start = 0;
                end = 0;
            }
        }else{
            start = 0;
            end = 0;
        }
        spBuilder=new SpannableStringBuilder(wholeStr);
        color=mContext.getResources().getColor(color);
        //CharacterStyle charaStyle=new ForegroundColorSpan(color);//每次创建可解决一个字符串中多次调用setSpan的问题
        if(countType != null){
            spBuilder.setSpan(new ForegroundColorSpan(color), countType.START1, countType.END1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spBuilder.setSpan(new ForegroundColorSpan(color), countType.START2, countType.END2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spBuilder.setSpan(new ForegroundColorSpan(color), countType.START3, countType.END3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spBuilder.setSpan(new ForegroundColorSpan(color), countType.START4, countType.END4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }
    public SpannableStringBuilder getResult(){
        if (spBuilder != null) {
            return spBuilder;
        }
        return null;
    }

    public boolean matchType(String str){
        return str.matches("[0-9]+");
    }

    public void findCount(String textStr){
        int strlength = textStr.length();
        countType = new CountType();
        countType.START1 = strlength-3;
        countType.END1 = strlength-1;

        countType.START2 = strlength-6;
        countType.END2 = strlength-4;

        countType.START3 = strlength-10;
        countType.END3 = strlength-8;

        countType.START4 = strlength-13;
        countType.END4 = strlength-11;

    }

    class CountType{
        int START1;
        int END1;

        int START2;
        int END2;

        int START3;
        int END3;

        int START4;
        int END4;
    }
}
