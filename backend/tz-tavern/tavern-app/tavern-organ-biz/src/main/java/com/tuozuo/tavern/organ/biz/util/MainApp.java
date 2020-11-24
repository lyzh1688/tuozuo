/**
 * 版权申明: 苏州朗动科技有限公司<br>
 * 项目描述: 企查查-接口平台<br>
 * 该接口调用demo仅供学习参考
 */
package com.tuozuo.tavern.organ.biz.util;

import static java.lang.System.out;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpHead;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * 
 * 描述 http请求处理模块<br>
 * 
 * @author szld<br>
 * @version 1.0 <br>
 *          日期：2019年7月31日 下午2:23:41
 */
public class MainApp {
	/*// 请登录http://yjapi.com/DataCenter/MyData
	// 查看我的秘钥 我的Key
	private static final String appkey = "我的接口:我的Key";
	private static final String seckey = "我的接口:我的秘钥";

	public static void main(String[] args) {
		String reqInterNme = "http://api.qichacha.com/ECIV4/Search";
		String paramStr = "keyword=新疆庆华能源集团有限公司";
		String status = "";
		try {
			// auth header setting
			HttpHead reqHeader = new HttpHead();
			String[] autherHeader = RandomAuthentHeader();
			reqHeader.setHeader("Token", autherHeader[0]);
			reqHeader.setHeader("Timespan", autherHeader[1]);
			final String reqUri = reqInterNme.concat("?key=").concat(appkey).concat("&").concat(paramStr);
			String tokenJson = HttpHelper.httpGet(reqUri, reqHeader.getAllHeaders());
			out.println(String.format("==========================>this is response:{%s}", tokenJson));

			// parse status from json
			status = FormartJson(tokenJson, "Status");
			out.println(String.format("==========================>Status:{%s}", status));
			if (!HttpCodeRegex.isAbnornalRequest(status)) {
				PrettyPrintJson(tokenJson);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// 获取返回码 Res Code
	static class HttpCodeRegex {
		private static final String ABNORMAL_REGIX = "(101)|(102)";
		private static final Pattern pattern = Pattern.compile(ABNORMAL_REGIX);
		protected static boolean isAbnornalRequest(final String status) {
			return pattern.matcher(status).matches();
		}
	}

	// 获取Auth Code
	protected static final String[] RandomAuthentHeader() {
		String timeSpan = String.valueOf(System.currentTimeMillis() / 1000);
		String[] authentHeaders = new String[] { DigestUtils.md5Hex(appkey.concat(timeSpan).concat(seckey)).toUpperCase(), timeSpan };
		return authentHeaders;
	}

	// 解析JSON
	protected static String FormartJson(String jsonString, String key) throws JSONException {
		JSONObject jObject = new JSONObject(jsonString);
		return (String) jObject.get(key);
	}

	// pretty print 返回值
	protected static void PrettyPrintJson(String jsonString) throws JSONException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object obj = mapper.readValue(jsonString, Object.class);
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			out.println(indented);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}