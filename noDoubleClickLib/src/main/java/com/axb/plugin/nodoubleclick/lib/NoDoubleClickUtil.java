package com.axb.plugin.nodoubleclick.lib;

/**
 * 防双击工具类
 */
public class NoDoubleClickUtil {
    //防止双击多次调用方案2
    private static long time;
    private static long doubleClickTime = 500;

    public static boolean isOnDoubleClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - time < doubleClickTime) {
            return true;
        }
        time = currentTime;
        return false;
    }

    public static void init(long doubleClickTime){
        NoDoubleClickUtil.doubleClickTime = doubleClickTime;
    }
}