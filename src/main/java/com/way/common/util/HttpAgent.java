package com.way.common.util;

import com.way.common.log.WayLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author xinpei.xu
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HttpAgent extends HttpPost {
	// 默认编码
	private String charset = "UTF-8";
	private static final int DEFAULT_TIMEOUT = 60000;// 默认超时时间,毫秒

	public int getStatusLine() {
		return statusLine;
	}

	public static int getDefaultTimeout() {
		return DEFAULT_TIMEOUT;
	}

	private int statusLine;// 返回状态码
	private int timeout = 0;

	public HttpAgent(String url) {
		super(url);
	}

	public HttpAgent() {
		super();
	}

	public void setDefaultHeader() {
		this.addHeader("Content-type", "application/json; charset=UTF-8");
	}

	public void setContentHeader() {
		this.setHeader("contentEncoding", "charset=UTF-8");
	}

	/**
	 * 
	 * @param jsonStr
	 * @return 接口返回信息
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doPost(String jsonStr) throws ClientProtocolException,
			IOException {
		HttpClient httpclient = new DefaultHttpClient();
		// 默认JSON传输
		this.setDefaultHeader();
		this.setContentHeader();
		// 接口返回
		String respJson = "";
		try {
			// 设置超时间
			if (timeout > 0) {
				httpclient.getParams().setIntParameter("http.socket.timeout",
						timeout);
			} else {
				httpclient.getParams().setIntParameter("http.socket.timeout",
						DEFAULT_TIMEOUT);
			}
			// 设置请求体,以及编码方式
			if (StringUtils.isNotBlank(jsonStr)) {
				StringEntity reqEntity = new StringEntity(jsonStr, charset);
				this.setEntity(reqEntity);
			}
			HttpResponse response = httpclient.execute(this);
			statusLine = response.getStatusLine().getStatusCode();
			WayLogger.debug("HttpAgent statusline==>" + statusLine);
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					respJson = EntityUtils.toString(resEntity, "UTF-8");
				}
			}
		} catch (Exception e) {
			WayLogger.error(e, "执行HttpPost请求异常");
		}
		return respJson;
	}

	// 返回HTTP相应状态码
	public int getstatusLine() {
		return statusLine;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setUrl(String url) throws URISyntaxException {
		this.setURI(new URI(url));
	}

	/**
	 * 设置连接超时
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
