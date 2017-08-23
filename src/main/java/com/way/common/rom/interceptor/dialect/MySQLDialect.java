package com.way.common.rom.interceptor.dialect;

/**
 * 功能描述：mysql方言实现
 *
 * @ClassName MySQLDialect
 * @Author：xinpei.xu
 * @Date：2017/08/14 20:58
 */
public class MySQLDialect implements IDBDialect {

    /*
     * (non-Javadoc)
     *
     * @see
     * org.yate.basic.dao.interceptor.IDBDialect#getPaginationSQL(java.lang.
     * String, int, int)
     */
    public String getPaginationSQL(String originalSql, int page, int rows) {
        StringBuilder sb = new StringBuilder(originalSql.trim().length() + 16);
        sb.append(originalSql).append(" limit ")
                .append(Math.max((page - 1) * rows, 0)).append(",")
                .append(rows);
        return sb.toString();
    }

}
