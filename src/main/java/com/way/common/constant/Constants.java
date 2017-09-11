package com.way.common.constant;

public class Constants {
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
    public static final String NO = "0";
    /**
     * 是/否
     */
    public static final int NO_INT = 0;
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

    public static final int NOT_MANAGE = 0;// 非管理岗

    public static final int IS_MANAGE = 1;// 管理岗
    
    /**
	 * 手机服务密码密钥
	 */
	public static final Integer PRIVATEENCRYPT_MOBILE = 10;
	
	/**
	 * 淘宝密码密钥
	 */
	public static final Integer PRIVATEENCRYPT_TAOBAO = 20;
	
	/**
	 * 京东密码密钥
	 */
	public static final Integer PRIVATEENCRYPT_JINGDONG = 30;
	
	/**
	 * 排序:升序
	 */
	public static final String ORDER_ASC="asc";
	
	/**
	 * 排序：降序
	 */
	public static final String ORDER_DESC="desc";
	
	/**
	 * 代办
	 */
	public static final String TASK_TODO="todo";

	/**
	 * 已办
	 */
	public static final String TASK_DONE="done";


	/**
	 * 借款单号头
	 */
	public static final String APPLY_NO_HEADER="APP_";
	
	/**
	 * 请求来源
	 */
	public static final String MOBILE_SERVER="mobile-server";

    /**
     * 资料步骤默认值
     */
    public static final String DATA_COMPLET_VALUES = "0000000000";
    /**
     * 资料步骤第一步默认值
     */
    public static final String DATA_COMPLET_VALUES_FIRST = "1000000000";
    /**
     * 资料步骤第二步默认值
     */
    public static final String DATA_COMPLET_VALUES_SECOND  = "1100000000";
    /**
     * 资料步骤第三步默认值
     */
    public static final String DATA_COMPLET_VALUES_THIRD = "1110000000";

    /**
     * 资料明细完成步骤默认值
     */
    public static final String DATA_DETAIL_COMPLET_VALUES = "000000000000000000000000000000000000000000000000000000000000";

    /**
     * 资料明细初始化第一步默认值
     */
    public static final String DATA_DETAIL_COMPLET_VALUES_FIRST = "000111110000000000000000000000000000000000000000000000000000";
    /**
     * 资料明细初始化第二步默认值
     */
    public static final String DATA_DETAIL_COMPLET_VALUES_SECOND = "000111111100000000000000000000000000000000000000000000000000";
    /**
     * 资料明细初始化第三步默认值
     */
    public static final String DATA_DETAIL_COMPLET_VALUES_THIRD = "000111111100100000000000000000000000000000000000000000000000";
}
