package cn.lsh.filter;
/*
 * 拦截器，拦截消费者请求，先从redis缓存中读取token验证用户信息，若没有在请求api网关
 */

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.lsh.util.JwtUtils;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;

import cn.lsh.exception.ResponseStatus;
import cn.lsh.service.BookConsumerService;
import cn.lsh.util.Constants;
import cn.lsh.vo.response.BaseResponse;
import cn.lsh.vo.security.Audience;
import cn.lsh.vo.security.LoginParamter;
import lombok.extern.slf4j.Slf4j;

@Order(1)
@WebFilter(filterName = "redisFilter", urlPatterns = { "/consumer/*" })
@Slf4j
public class RedisFilter implements Filter {
	@Autowired
	private Audience audience;

	@Autowired
	private BookConsumerService bookConsumerService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.info("============= RedisFilter init =================");
	}

	//若API网关成功返回token，则放置在redis缓存中,然后继续执行controller方法
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("============= start to execute RedisFilter =================");
		Map<String, String[]> test = request.getParameterMap();

		String accessToken = JwtUtils.getAuthorization((HttpServletRequest) request);
		if (StringUtils.isNotBlank(accessToken)) {
			Claims claims = JwtUtils.parseJWT(accessToken.substring(7), audience.getBase64Secret());
			if (claims == null) {
				BaseResponse baseResponse = new BaseResponse();
				baseResponse.setCode(ResponseStatus.INVALID_TOKEN.getCode());
				baseResponse.setMessage(ResponseStatus.INVALID_TOKEN.getMessage());
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(JSON.toJSONString(baseResponse));
			} else {
				//token合法
				chain.doFilter(request, response);
			}
		} else {
			String jwtToken = stringRedisTemplate.opsForValue().get(Constants.BEARER);
			LoginParamter loginParamter = (LoginParamter) redisTemplate.opsForValue().get(Constants.LOGIN_PARAMETER);
			if (!ObjectUtils.isEmpty(jwtToken)) {//若token不为空，则执行controller方法
				log.info("redis存在jwtToken: {}", jwtToken);
				chain.doFilter(request, response);
			} else {
				log.info("redis不存在token，请求api网关");
				//token为空，则向API网关申请新的token
				BaseResponse baseRespone = bookConsumerService.getToken(loginParamter);
				if (baseRespone.getCode() == ResponseStatus.OK.getCode()) {
					Map<String, Object> tokenMap = (HashMap<String, Object>) baseRespone.getData();
					log.info(Constants.TOKEN_TPYE + ": {}", tokenMap.get(Constants.TOKEN_TPYE));
					log.info(Constants.ACCESS_TOKEN + ": {}", tokenMap.get(Constants.ACCESS_TOKEN));
					log.info(Constants.EXPIRES_IN + ": {}", tokenMap.get(Constants.EXPIRES_IN));

					stringRedisTemplate.opsForValue().set(Constants.BEARER, (String) tokenMap.get(Constants.ACCESS_TOKEN), Long.valueOf(tokenMap.get(Constants.EXPIRES_IN).toString()), TimeUnit.SECONDS);

					chain.doFilter(request, response);
				}
			}
		}

		log.info("============= end to execute RedisFilter =================");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("============= RedisFilter destroy =================");
	}

}
