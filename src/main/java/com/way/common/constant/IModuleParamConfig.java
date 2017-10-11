package com.way.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈麦芽贷系统各模块业务参数配置编码表〉<br>
 * 〈功能详细描述〉
 *
 * 参数编码按如下规则进行增加<br>
 * 接口中心（WAY_MOBILE） ：以字母A开头，接4位编码，以0001开始，依次往上增加；<br>
 * 管理后台（MYLOAN_MANAGER） ：以字母B开头，接4位编码，以0001开始，依次往上增加；<br>
 * 用户服务（WAY_MEMBER）：以字母C开头，接4位编码，以0001开始，依次往上增加； <br>
 * 基础服务（WAY_BASE） ：以字母D开头，接4位编码，以0001开始，依次往上增加；<br>
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class IModuleParamConfig {
	
    /** mobile-server */
	public final static String WAY_MOBILE = "mobile";
    /** 管理后台 */
	public final static String WAY_MANAGER = "manage";
    /** member*/
	public final static String WAY_MEMBER = "member";
    /** base */
	public final static String WAY_BASE = "base";

	
    /********* mobile-server begin **********/

    /********* mobile-server end **********/

	/********* 管理后台begin **********/

	/********* 管理后台end **********/

	/********* 用户服务begin **********/

	/********* 用户服务end **********/

	/********* 基础服务begin **********/

	/********* 基础服务end **********/

	public final static Map<String, String> modelMap = new HashMap<String, String>();
	static {  
		modelMap.put(WAY_MOBILE, "接口中心");
		modelMap.put(WAY_MANAGER, "管理后台");
		modelMap.put(WAY_MEMBER, "用户服务");
		modelMap.put(WAY_BASE, "基础服务");
	}
	
}
