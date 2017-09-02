package com.way.common.util;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
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
