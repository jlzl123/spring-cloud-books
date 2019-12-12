package cn.lsh.controller;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import cn.lsh.entity.User;
import cn.lsh.exception.ResponseStatus;
import cn.lsh.util.Constants;
import cn.lsh.util.Md5Utils;
import cn.lsh.vo.response.BaseResponse;
import cn.lsh.vo.security.Audience;
import cn.lsh.vo.security.LoginParamter;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	private static final Logger LOGGER=LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected HttpServletRequest request;
	
	/*
	 * 验证参数是否正确
	 */
	public BaseResponse getValidatedResult(BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			BaseResponse baseResponse=new BaseResponse();
			
			List<FieldError> errors=bindingResult.getFieldErrors();
			StringBuilder sb=new StringBuilder();
			for(FieldError error:errors){
				sb.append(error.getField()).append(":").append(error.getDefaultMessage()).append(" ,");
			}
			sb.deleteCharAt(sb.length()-1);
			
			String formattedMessage=MessageFormat.format(ResponseStatus.PARAMETER_VALIDATION.getMessage(), sb);
			baseResponse.setCode(ResponseStatus.PARAMETER_VALIDATION.getCode());
			baseResponse.setMessage(formattedMessage);
			baseResponse.setData(Constants.NULL_DATA);
			return baseResponse;
		}
		return null;
	}
	
	public BaseResponse isValidClientId(LoginParamter loginParamter,Audience audience){
		if(loginParamter.getClientId().compareTo(audience.getClientId())!=0){
			BaseResponse baseResponse=new BaseResponse();
			baseResponse.setCode(ResponseStatus.INVALID_CLIENT_ID.getCode());
			baseResponse.setMessage(ResponseStatus.INVALID_CLIENT_ID.getMessage());
			baseResponse.setData(Constants.NULL_DATA);
			return baseResponse;
		}
		return null;
	}

	public BaseResponse isValidUserName(User user){
		if(user==null){
		   BaseResponse baseResponse=new BaseResponse();
		   baseResponse.setCode(ResponseStatus.INVALID_USER_NAME.getCode());
		   baseResponse.setMessage(ResponseStatus.INVALID_USER_NAME.getMessage());
		   baseResponse.setData(Constants.NULL_DATA);
		   return baseResponse;
		}
		return null;
	}
	
	public BaseResponse isValidPassWord(LoginParamter loginParamter,User user){
		String md5Password=Md5Utils.getMd5(loginParamter.getPassword()+user.getSalt());
		LOGGER.info("md5Password is : {}",md5Password);
		if(!md5Password.equals(user.getPassword())){
			BaseResponse baseResponse=new BaseResponse();
			baseResponse.setCode(ResponseStatus.INVALID_PASSWORD.getCode());
			baseResponse.setMessage(ResponseStatus.INVALID_PASSWORD.getMessage());
			baseResponse.setData(Constants.NULL_DATA);
			return baseResponse;
		}
		return null;
	}
}
