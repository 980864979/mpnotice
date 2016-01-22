/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.tangdi.production.mpnotice.weixin.utils;

import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.tangdi.production.mpnotice.constants.NoticeCT;

/**
 * XMLParse class
 *
 * 提供提取消息格式中的密文及生成回复消息格式的接口.
 */
@SuppressWarnings("rawtypes")
public class WXXMLParse {

	/**
	 * 提取出xml数据包中的加密消息
	 * @param xmltext 待提取的xml字符串
	 * @return 提取出的加密消息字符串
	 * @throws AesException 
	 */
	public static Object[] extract(String xmltext) throws AesException     {
		Object[] result = new Object[3];
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmltext);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			NodeList nodelist2 = root.getElementsByTagName("ToUserName");
			result[0] = 0;
			result[1] = nodelist1.item(0).getNodeValue();
			result[2] = nodelist2.item(0).getNodeValue();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ParseXmlError);
		}
	}

	/**
	 * 生成xml消息
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	public static String generate(String encrypt, String signature, String timestamp, String nonce) {
		String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
		return String.format(format, encrypt, signature, timestamp, nonce);

	}
	
	/**
	 * 生成xml 单个 图文消息
	 * @return 生成的xml字符串
	 */
	public static String generate(Map param) {
		String contentFormat = "<xml>\n" 
				+ "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
				+ "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"
				+ "<CreateTime>%3$s</CreateTime>\n"
				+ "<MsgType><![CDATA[%4$s]]></MsgType>\n"
				+ "<Content><![CDATA[%5$s]]></Content>\n"
				+ "</xml>";
		
			String newsFormat = "<xml>\n" 
					+ "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
					+ "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"
					+ "<CreateTime>%3$s</CreateTime>\n"
					+ "<MsgType><![CDATA[%4$s]]></MsgType>\n"
					+ "<ArticleCount>%5$s</ArticleCount>\n"
					+ "<Articles>\n" 
					+ "<item>\n" 
					+ "<Title><![CDATA[%6$s]]></Title>\n"
					+ "<Description><![CDATA[%7$s]]></Description>\n"
					+ "<PicUrl><![CDATA[%8$s]]></PicUrl>\n"
					+ "<Url><![CDATA[%9$s]]></Url>\n"
					+ "</item>\n" 
					+ "</Articles>\n" 
					+ "</xml>";
			
			if(NoticeCT.PUSHMSGTYPE_TEXT.equals(param.get(NoticeCT.PUSHMSGTYPE))){
				return String.format(contentFormat, param.get("ToUserName"), param.get("FromUserName"), param.get("CreateTime")
						, param.get("MsgType"), param.get("Content"));
			}else{
				return String.format(newsFormat, param.get("ToUserName"), param.get("FromUserName"), param.get("CreateTime")
						, param.get("MsgType"), param.get("ArticleCount"), param.get("Title")
						, param.get("Description"), param.get("PicUrl"), param.get("Url"));
			}
			
		}
}