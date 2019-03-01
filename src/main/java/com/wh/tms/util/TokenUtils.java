package com.wh.tms.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;

import com.wh.tms.constans.SystemCons;

/**
 * token工具类
* @ClassName: TokenUtils 
* @Description: TODO 
* @author Huqk
* @date 2018年3月5日 下午8:40:50
 */
public class TokenUtils {
	
	
	
	/**
	 * 生成token
	* @Title: generateToken 
	* @Description: TODO
	* @param @param params 自定义参数
	* @param @param ttlMillis token有效期
	* @param @return 
	* @return String
	* @throws
	 */
	public static String generateToken(Map<String,Object> params,long ttlMillis) {
		
		long nowMillis = System.currentTimeMillis();
		Date nowDate = new Date(nowMillis);
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SystemCons.sso_key);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder().setIssuedAt(nowDate)
		                                .setClaims(params)
		                                .signWith(signatureAlgorithm, signingKey);
		if (ttlMillis >= 0) {
			long expTime = nowMillis + ttlMillis;
		    builder.setExpiration(new Date(expTime));//设置过期时间
		}
		 
		return builder.compact();
	}
	
	/**
	 * 验证token
	* @Title: verifyToken 
	* @Description: TODO
	* @param @param token
	* @param @return
	* @param @throws Exception 
	* @return Claims
	* @throws
	 */
	public static Claims verifyToken(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SystemCons.sso_key)).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return claims;
	}
	
	/**
	 * 获取当前请求token
	* @Title: getToken 
	* @Description: TODO
	* @param @param request
	* @param @return 
	* @return String
	* @throws
	 */
	public static String getToken(HttpServletRequest request) {
		
		if (null != request) {
			return request.getHeader("x-access-token");
		}
		return null;
	}
	public static void main(String[] args) {
		Integer uid = 15516455;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		String token = generateToken(params,SystemCons.token_exp_time);
		System.out.println(token);
		try {
			Claims clims = verifyToken(token);
			System.out.println(clims.get("uid"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("登录已过期，请重新登录!");
		}
	}

}
