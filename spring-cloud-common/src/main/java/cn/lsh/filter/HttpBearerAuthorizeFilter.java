package cn.lsh.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import cn.lsh.exception.ResponseHandler;
import cn.lsh.exception.ResponseStatus;
import cn.lsh.util.Constants;
import cn.lsh.util.JwtUtils;
import cn.lsh.vo.security.Audience;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;


public class HttpBearerAuthorizeFilter extends ZuulFilter{
	private static final Logger LOGGER=LoggerFactory.getLogger(HttpBearerAuthorizeFilter.class);
	@Autowired
	private Audience audience;
	@Autowired
	private ResponseHandler responseHandler;

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() {
		// TODO Auto-generated method stub
		RequestContext ctx=RequestContext.getCurrentContext();
		HttpServletRequest request=ctx.getRequest();
		try {
			//无Authorization时返回处理逻辑
			if(!isExistedAuthorization(request)){
				responseHandler(ctx, ResponseStatus.NO_AUTHORIZATION);
				return null;
			}
			//JWT无效时返回处理逻辑
			if(!isValidJwt(request)){
				responseHandler(ctx, ResponseStatus.INVALID_TOKEN);
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("exception message : {}",ExceptionUtils.getStackTrace(e));
			return null;
		}
		LOGGER.info("request url : {}",request.getRequestURI());
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * 返回逻辑统一处理
	 */
	private void responseHandler(RequestContext ctx,ResponseStatus responseStatus) throws JsonProcessingException, IOException{
		ObjectMapper mapper=new ObjectMapper();
		//zuul过滤请求，false为不路由，true为路由
		ctx.setSendZuulResponse(false);
		HttpServletResponse response=ctx.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(mapper.writeValueAsString(this.responseHandler.getBaseResponse(responseStatus)));
		
		ctx.setResponse(response);
	}
	
	/*
	 * 判断JWT是否有效
	 */
	private boolean isValidJwt(HttpServletRequest request){
		LOGGER.info("{} request to {}",request.getMethod(),request.getRequestURL());
		String authorization=request.getHeader("Authorization");
		LOGGER.info("authorization is: {}",authorization);
		String headString=authorization.substring(0, 6).toLowerCase();
		//compareTo逐个比较两个字符串中相对字符的ascll值，所有字符都相同返回0，发现小于时返回正整数，大于时返回负整数，即ascll值的差值。
		if(headString.compareTo(Constants.BEARER)==0){
			authorization=authorization.substring(7, authorization.length());
			if(JwtUtils.parseJWT(authorization, audience.getBase64Secret())!=null){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 判断Authorization是否存在
	 */
	private boolean isExistedAuthorization(HttpServletRequest request){
		LOGGER.info("{} request to {}",request.getMethod(),request.getRequestURL());
		String authorization=request.getHeader("Authorization");
		LOGGER.info("authorization is: {}",authorization);
		return !(authorization==null||authorization.length()<=7);
	}
}
