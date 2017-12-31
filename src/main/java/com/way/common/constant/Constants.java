package com.way.common.constant;

public class Constants {

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 失败
     */
    public static final int INVALID = 1;

    /**
     * Token失效--重新登录账号已经在其他设备登录
     */
    public static final int  OKEN_EXPIRED_OTHERLOGIN = 2;

    /**
     * token失效--登录超时
     */
    public static final int TOKEN_EXPIRED_OVERTIME = 3;

    /**
     * 版本升级--不需要强制升级
     */
    public static final int VERSION_UPGRADE = 4;

    /**
     * 版本升级--需要强制升级
     */
    public static final int VERSION_MANDATORY_UPGRADE = 5;

    /**
     * 您还不是会员，请先购买会员
     */
    public static final int MEMBERSHIP_EXPIRES = 6;

    /**
     * 是/否
     */
    public static final String YES = "1";
    /**
     * 是/否
     */
    public static final String YES_STR = "01";
    /**
     * 是/否
     */
    public static final int YES_INT = 1;
    /**
     * 是/否
     */
    public static final String NULL_INT = "2";
    /**
     * 是/否
     */
    public static final String NO = "2";
    /**
     * 是/否
     */
    public static final int NO_INT = 2;
    /**
     * 短信验证码有效时间
     */
    public static final int EXPIRE = 900;
    /**
     * 短信验证码发送间隔时间
     */
    public static final int COUNT_DOWN_EXPIRE = 60;
    /**
     * 短信验证码有效期限内错误次数限制
     */
    public static final int SMSERRORCOUNT = 10;

    /**
     * 24小时内号码提交短信失败次数
     */
    public static final int PHONEERRORCOUNT = 10;
    /**
     * 一天的秒数
     */
    public static final int DAY_OF_SECONDS = 86400;
    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * @Fields SYS_MENU_LEVEL_0 系统顶级菜单
     */
    public static final String SYS_MENU_LEVEL_0 = "0";
    /**
     * @Fields SYS_MENU_LEVEL_1 系统二级菜单
     */
    public static final String SYS_MENU_LEVEL_1 = "1";

    public static final int SEX_WOMAN = 0;// 性别女
    public static final int SEX_MAN = 1;// 性别男

    public static final int DELETE = 0;// 删除
    /**
     * <p>罗荣加 </p>
     * <p>未选择</p>
     */
    public static final Integer UNSELECT = 0;
    public static final String UNSELECT_STR = "0";
    public static final Long UNSELECT_LONG = 0l;

    // 定义一些地方定义为永久的时间
    public static final String neverDate = "9999-12-31 23:59:59";

	/**
	 * 排序:升序
	 */
	public static final String ORDER_ASC="asc";
	
	/**
	 * 排序：降序
	 */
	public static final String ORDER_DESC="desc";
	
	/**
	 * 借款单号头
	 */
	public static final String APPLY_NO_HEADER="APP_";
	
	/**
	 * 请求来源
	 */
	public static final String MOBILE_SERVER="mobile-server";

    /**
     * 默认授权开始时间
     */
    public static final String ACCREDIT_STARTTIME="09:00";

    /**
     * 默认授权结束时间
     */
    public static final String ACCREDIT_ENDTIME="17:00";

    /**
     * 默认授权日期
     */
    public static final String WEEKS="1,2,3,4,5";

}
