package cn.lzh.base.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;

/**
 * Created by LZH On 2019/3/23
 */
public class ViewUtils {

    /**
     * 前景色
     *
     * @param text  文本
     * @param color 颜色
     * @param start 起始位置
     * @param end   结束位置
     * @return
     */
    public static SpannableString foregroundColorSpan(String text, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 背景色
     *
     * @param text  文本
     * @param color 颜色
     * @param start 起始位置
     * @param end   结束位置
     * @return
     */
    public static SpannableString backgroundColorSpan(String text, int color, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(color);
        spannableString.setSpan(backgroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 相对于默认字体大小的倍数
     *
     * @param text       文本
     * @param proportion 相对于默认字体大小的倍数
     * @param start      起始位置
     * @param end        结束位置
     * @return
     */
    public static SpannableString relativeSizeSpan(String text, float proportion, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(proportion);
        spannableString.setSpan(relativeSizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 删除线
     *
     * @param text  文本
     * @param start 起始位置
     * @param end   结束位置
     * @return
     */
    public static SpannableString strikethroughSpan(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 下划线
     *
     * @param text  文本
     * @param start 起始位置
     * @param end   结束位置
     * @return
     */
    public static SpannableString underlineSpan(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
