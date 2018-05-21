package test.com.selfdefineview.wifi;

import java.util.Collection;

/**
 * 集合操作工具类
 */
public class CollectionUtils {
    /**
     * 判断集合是否为null或者0个元素
     */
    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }
}
