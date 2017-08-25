package com.way.common.util;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author LG
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ListConverter<Z, T> {

    /**
     * 功能描述: 转化接口<br>
     * 〈功能详细描述〉
     *
     * @param t
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Z convert(T t) throws Exception;
}
