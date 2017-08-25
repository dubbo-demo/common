package com.way.common.util;

/**
 * INFO: info
 * User: zhaokai
 * Date: 2016/9/6 - 14:39
 * Version: 1.0
 * History: <p>如果有修改过程，请记录</P>
 */

public class ResultCodeUtils {
    /**
     * 参数传递示范 ServiceResultCode ，EmplpyeeServiceCodeEnum，FlowStageServiceResultCode
     * 这三部分构成了完整的serviceresultcode值
     *
     * @param codes
     * @return
     */
    public static Integer getResultCode(String... codes) {
        StringBuffer str = new StringBuffer();
        for (int index = 0; index < codes.length; index++) {
            str.append(codes[index]);
        }
        return Integer.parseInt(str.toString());
    }


    public static String formatString(Integer value, Integer formatLength) {
        String newString = String.format("%0" + formatLength + "d", value);
        return newString;
    }

    public static String formatString(Long value, Integer formatLength) {
        String newString = String.format("%0" + formatLength + "d", value);
        return newString;
    }
}
