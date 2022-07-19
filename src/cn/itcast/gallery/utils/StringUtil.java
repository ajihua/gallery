package cn.itcast.gallery.utils;

public class StringUtil {
    /**
     * 校验字符串是否为空
     * @param str 输入的字符串
     * @return true为空 false不为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim()))
            return true;
        return false;
    }

    
}
