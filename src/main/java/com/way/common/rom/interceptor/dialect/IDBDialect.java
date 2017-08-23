package com.way.common.rom.interceptor.dialect;

/**
 * 功能描述：弱化的ＳＱＬ分析工具
 *
 * @ClassName IDBDialect
 * @Author：xinpei.xu
 * @Date：2017/08/14 20:43
 */
public interface IDBDialect {
	public static enum Type {
		MYSQL
	}

    /**
     *
     * @param originalSql
     * @param page
     * @param size
     * @return
     */
	String getPaginationSQL(final String originalSql, final int page,
                            final int size);
}
