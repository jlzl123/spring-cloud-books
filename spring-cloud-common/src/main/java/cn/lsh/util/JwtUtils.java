package cn.lsh.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import cn.lsh.entity.User;
import cn.lsh.vo.security.Audience;
import cn.lsh.vo.security.LoginParamter;

public class JwtUtils {
	private static final Logger LOGGER=LoggerFactory.getLogger(JwtUtils.class);
	
	private JwtUtils(){
		
	}

	/*
	 * 验证jwt是否合法
	 */
	public static Claims parseJWT(String jsonWebToken,String base64Security){
		try {
			return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();		
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("exception message is: {}", ExceptionUtils.getStackTrace(e));
			return null;
		}
	}
	
	/*
	 * 创建jwt
	 */
	private static String createJWT(LoginParamter loginParamter,User user,Audience audience){
		long ttlmillis=audience.getExpiresSecond()*1000;
		String base64Security=audience.getBase64Secret();
		Date now=new Date(System.currentTimeMillis());
        
		//生成签名密钥
		byte[] apiKeySecretBytes=DatatypeConverter.parseBase64Binary(base64Security);
		Key signingKey=new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
		
		//添加构成JWT的参数		
		JwtBuilder builder=Jwts.builder().setHeaderParam("typ", "JWT")
				.claim("role", user.getRole())
				.claim("unique_name", loginParamter.getUserName())
				.claim("userid", user.getName())
				.setIssuer(audience.getName())
				.setAudience(audience.getClientId())
				.signWith(SignatureAlgorithm.HS256, signingKey);
		
        //添加Token过期时间
		if(ttlmillis>=0){
			Date exp=new Date(System.currentTimeMillis()+ttlmillis);
			builder.setExpiration(exp).setNotBefore(now);
		}
		
		//生成JWT
		return builder.compact();
	}
}
