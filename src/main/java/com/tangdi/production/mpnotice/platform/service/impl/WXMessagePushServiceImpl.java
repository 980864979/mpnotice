package com.tangdi.production.mpnotice.platform.service.impl;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tangdi.production.mpnotice.http.client.HttpRequestClient;
import com.tangdi.production.mpnotice.http.client.HttpResp;
import com.tangdi.production.mpnotice.constants.NoticeCT;
import com.tangdi.production.mpnotice.constants.WeixinConfig;
import com.tangdi.production.mpnotice.platform.service.MessagePushService;
import com.tangdi.production.mpnotice.utils.ParamValidate;
import com.tangdi.production.mpnotice.utils.SHA1;
import com.tangdi.production.mpnotice.weixin.utils.WXXMLParse;
import com.tangdi.production.mpnotice.utils.TdExpBasicFunctions;

/***
 * 微信消息推送接口
 * 
 * @author sunhaining
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WXMessagePushServiceImpl implements MessagePushService {
	private static final Logger log = LoggerFactory.getLogger(WXMessagePushServiceImpl.class);
	@Autowired
	private HttpRequestClient httpRequestClient;

	@Override
	public Map<String, Object> signIn(Map param) throws Exception {
		Map responseMap = new HashMap();
		String signature = param.get("signature") == null ? "" : param.get("signature").toString();
		String timestamp = param.get("timestamp") == null ? "" : param.get("timestamp").toString();
		String nonce = param.get("nonce") == null ? "" : param.get("nonce").toString();
		String[] str = { WeixinConfig.WEIXIN_TOKEN, timestamp, nonce };
		Arrays.sort(str);
		String bigStr = str[0] + str[1] + str[2];
		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		log.info("原数据：{},加密后数据：{}", signature, digest);
		responseMap.put("digest", digest);
		return responseMap;

	}

	@Override
	public Map<String, Object> getAccessToken(Map param) throws Exception {
		Map<String, Object> atMap = new HashMap<String, Object>();
		try {
			HttpResp httpResp = httpRequestClient.sendGet(WeixinConfig.WEIXIN_ACCESSTOKENURI);
			if (httpResp != null) {
				log.info("HttpResp->" + httpResp.toString());
				String jsonString = httpResp.getContent();
				JsonNode jsonNode = new ObjectMapper().readValue(jsonString, JsonNode.class);
				log.info("第三方返回值:" + jsonString);
				atMap.put(NoticeCT.WEIXIN_ACCESSTOKEN, jsonNode.get("access_token").asText());
				atMap.put(NoticeCT.WEIXIN_ACCESSTOKEN_EXPIRESIN, jsonNode.get("expires_in").asText());
				log.info("accessToken值:{}", atMap);
			}
		} catch (Exception e) {
			log.info("获取accessToken失败：{}", e.getMessage());
		}
		return atMap;
	}

	@Override
	public Map<String, Object> getOAuthAccessToken(Map param) throws Exception {
		Map<String, Object> atMap = new HashMap<String, Object>();
		try {
			String code = param.get(NoticeCT.WEIXIN_CODE).toString();
			log.info("code值:{}", code);
			HttpResp httpResp = httpRequestClient.sendGet(String.format(WeixinConfig.WEIXIN_OAUTHACCESSTOKENURI, code));
			if (httpResp != null) {
				log.info("HttpResp->" + httpResp.toString());
				String jsonString = httpResp.getContent();
				JsonNode jsonNode = new ObjectMapper().readValue(jsonString, JsonNode.class);
				log.info("第三方返回值:" + jsonString);
				atMap.put(NoticeCT.WEIXIN_ACCESSTOKEN, jsonNode.get("access_token").asText());
				atMap.put(NoticeCT.WEIXIN_OPENID, jsonNode.get("openid").asText());
				log.info("accessToken和openId返回值:{}", atMap);
			}
		} catch (Exception e) {
			log.info("获取accessToken失败：{}", e.getMessage());
		}
		return atMap;
	}

	@Override
	public String pushNotice(String protocolType, Map param) throws Exception {
		String rstStr = "";
		if (NoticeCT.PROTOCOLTYPE_XML.equals(protocolType)) {
			log.info("当前数据类型为XML");
			ParamValidate.doing(param, NoticeCT.PUSHUSER, NoticeCT.PUSHMSGTYPE, NoticeCT.PUSHDESCRIPTION);
			Map<String, Object> paramSend = new TreeMap<String, Object>();

			log.info("获取用户 openId：{}", param.get(NoticeCT.PUSHUSER));
			paramSend.put("ToUserName", param.get(NoticeCT.PUSHUSER));

			paramSend.put("FromUserName", WeixinConfig.WEIXIN_FROMUSERNAME);
			paramSend.put("CreateTime", TdExpBasicFunctions.GETDATE());
			paramSend.put("MsgType", param.get(NoticeCT.PUSHMSGTYPE));

			if (NoticeCT.PUSHMSGTYPE_TEXT.equals(param.get(NoticeCT.PUSHMSGTYPE))) {
				paramSend.put("Content", NoticeCT.PUSHDESCRIPTION);
			} else if (NoticeCT.PUSHMSGTYPE_NEWS.equals(param.get(NoticeCT.PUSHMSGTYPE))) {
				paramSend.put("ArticleCount", "1");
				paramSend.put("Title", param.get(NoticeCT.PUSHTITLE));
				paramSend.put("Description", param.get(NoticeCT.PUSHDESCRIPTION));
				paramSend.put("PicUrl", WeixinConfig.WEIXIN_BINDPREVIEWPIC);
				paramSend.put("Url", String.format(WeixinConfig.WEIXIN_BINDURL, URLEncoder.encode(WeixinConfig.WEIXIN_BINDPREVIEWURL, "UTF-8")));
			}
			rstStr = WXXMLParse.generate(paramSend);
		} else if (NoticeCT.PROTOCOLTYPE_JSON.equals(protocolType)) {
			log.info("当前数据类型为JSON");
			ObjectMapper objectMapper = new ObjectMapper();
			rstStr = objectMapper.writeValueAsString(param);
		} else {
			throw new Exception("数据类型错误，目前只支持xml、json！");
		}
		return rstStr;
	}

	@Override
	public String encryp(String data) throws Exception {
		return null;
	}
}
