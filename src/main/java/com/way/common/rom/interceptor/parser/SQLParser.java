/**
 * SQLParser.java
 */
package com.way.common.rom.interceptor.parser;

/**
 * 功能描述：弱化的ＳＱＬ分析工具
 *
 * @ClassName SQLParser
 * @Author：xinpei.xu
 * @Date：2017/08/14 21:34
 */
public class SQLParser {

    public static class Location {
        int s = -1;
        int e = -1;

        /**
         * @return the s
         */
        public final int getS() {
            return s;
        }

        /**
         * @return the e
         */
        public final int getE() {
            return e;
        }

    }

    private SQLParser(String t) {
    }

    public static Location parser(String sql, String token) {
        int pos = -1;
        int make = -1;
        boolean balance = false;

        Location l = new Location();
        for (; ; ) {
            char c = Character.toLowerCase(charAt(sql, ++pos));
            if ((c == token.charAt(0)) && pos - 1 == make) {
                l.s = pos;
            }
            if (c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f'
                    || c == '\b') {
                make = pos;
                if (!balance && l.s > -1
                        && sql.substring(l.s, pos).matches("(?i)" + token)) {
                    l.e = pos;
                    return l;
                }
            }
            if (c == '(') {
                balance = true;
            }
            if (c == ')') {
                balance = false;
            }
        }
    }

    protected static char charAt(String sql, int index) {
        if (index >= sql.length()) {
            return 0x1A;
        }

        return sql.charAt(index);
    }
}
