package cn.lsh.util;

import cn.lsh.entity.User;
import cn.lsh.vo.security.Audience;
import cn.lsh.vo.security.LoginParamter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.stream.Stream;

public class JwtUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	private JwtUtils() {

	}

	/*
	 * 验证jwt是否合法，即解密
	 */
	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken)
					.getBody();
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("exception message is: {}", ExceptionUtils.getStackTrace(e));
			return null;
		}
	}

	/*
	 * 创建jwt,即加密
	 */
	public static String createJWT(LoginParamter loginParamter, User user, Audience audience) {
		long ttlmillis = audience.getExpiresSecond() * 1000;
		String base64Security = audience.getBase64Secret();
		Date now = new Date(System.currentTimeMillis());

		//生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

		//添加构成JWT的参数		
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")//typ表示token的类型
				.claim("role", user.getRole())// 自定义属性
				.claim("unique_name", loginParamter.getUserName())
				.claim("userid", user.getName())
				.setIssuer(audience.getName())// 签发者
				.setAudience(audience.getClientId())//接受者
				.signWith(SignatureAlgorithm.HS256, signingKey);// 签名算法以及密匙

		//添加Token过期时间
		if (ttlmillis >= 0) {
			Date exp = new Date(System.currentTimeMillis() + ttlmillis);
			builder.setExpiration(exp)// 过期时间
					.setNotBefore(now);// 失效时间
		}

		//生成JWT
		return builder.compact();
	}

	/**
	 * 从请求中获取token
	 * @param request
	 * @return
	 */
	public static String getAuthorization(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (StringUtils.isBlank(authorization)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				authorization = Stream.of(cookies).filter(cookie -> Constants.AUTHORIZATION.equals(cookie.getName()))
						.map(Cookie::getValue).findFirst().orElse(null);
			}
		}
		return authorization;
	}

	public static void main(String[] args) {
		Claims c = parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoi566h55CG5ZGYIiwidW5pcXVlX25hbWUiOiJsaXVzaGlodWEiLCJ1c2VyaWQiOiJsaXVzaGlodWEiLCJpc3MiOiJsaXVzaGlodWEiLCJhdWQiOiIwOThmNmJjZDQ2MjFkMzczY2FkZTRlODMyNjI3YjRmNiIsImV4cCI6MTUwMjM1OTQ1NSwibmJmIjoxNTAyMzUyMjU0fQ.OeDN4deNUlDiUmwROjxw5_xrurJt46b1RZ0K5yNKH88",
				"MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=");
		System.out.println(c);
	}
}
