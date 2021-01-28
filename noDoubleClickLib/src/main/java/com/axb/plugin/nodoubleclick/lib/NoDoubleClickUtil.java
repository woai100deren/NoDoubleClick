package com.axb.plugin.nodoubleclick.lib;

/**
 * 防双击工具类
 */
public class NoDoubleClickUtil {
    //防止双击多次调用方案2
    private static long time;
    private static long doubleClickTime = 500;

    /**
     * 按照设置的时间间隔，判断是否是双击。如果是双击，返回true
     * @return
     */
    public static boolean isOnDoubleClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - time < doubleClickTime) {
            return true;
        }
        time = currentTime;
        return false;
    }

    /**
     * 设置双击之间的时间间隔，单位ms，可以不设置。不设置时，默认为500ms
     * @param doubleClickTime
     */
    public static void init(long doubleClickTime){
        NoDoubleClickUtil.doubleClickTime = doubleClickTime;
    }
}