/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: EncodeUtils.java,v 1.1 2012/05/30 12:04:24 xinsheng Exp $
 */
package com.wh.tms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 各种格式的编码加码工具类.
 * 
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * 
 * @author calvin
 */
public class EncodeUtils extends StringEscapeUtils {
	
	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	
	/**
	 * Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input).toUpperCase();
	}
	
	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}
	
	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}
	
	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}
	
	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}
	
	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String input) {
		return urlEncode(input, DEFAULT_URL_ENCODING);
	}
	
	/**
	 * URL 编码.
	 */
	public static String urlEncode(String input, String encoding) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}
	
	/**
	 * 只将中文进行URLEncoder编码为utf-8
	 * 
	 * @param input
	 * @return
	 */
	public static String urlEncodeZh(String input) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			String s = input.substring(i, i + 1);
			if (s.matches("[\\u4E00-\\u9FA5]+")) {
				sb.append(EncodeUtils.urlEncode(s));
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 只将中文进行URLEncoder编码为encoding
	 * 
	 * @param input
	 * @param encoding
	 * @return
	 */
	public static String urlEncodeZh(String input, String encoding) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			String s = input.substring(i, i + 1);
			if (s.matches("[\\u4E00-\\u9FA5]+")) {
				sb.append(EncodeUtils.urlEncode(s, encoding));
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}
	
	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(String input) {
		return urlDecode(input, DEFAULT_URL_ENCODING);
	}
	
	/**
	 * URL 解码.
	 */
	public static String urlDecode(String input, String encoding) {
		try {
			return URLDecoder.decode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}
	
	/**
	 * ISO-8859-1字符集转UTF-8
	 * 
	 * @param s
	 * @return
	 */
	public static String iso2utf8(String s) {
		if (s == null) {
			return "";
		}
		try {
			return new String(s.getBytes("ISO-8859-1"), "UTF-8").trim();
		} catch (Exception e) {
			return s;
		}
	}
	
	/**
	 * ISO-8859-1字符集转GBK
	 * 
	 * @param s
	 * @return
	 */
	public static String iso2gbk(String s) {
		if (s == null) {
			return "";
		}
		try {
			return new String(s.getBytes("ISO-8859-1"), "GBK").trim();
		} catch (Exception e) {
			return s;
		}
	}
	
	/**
	 * 此方法与javascript中的escape实现一致
	 * 
	 * @param src
	 * @return
	 */
	public static String escapeJS(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
				tmp.append(j);
			} else if (j < 256) {
				tmp.append("%");
				if (j < 16) {
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 此方法与javascript中的unescape实现一致
	 * 
	 * @param src
	 * @return
	 */
	public static String unescapeJS(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
}
