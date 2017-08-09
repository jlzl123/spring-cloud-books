package cn.lsh.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.lsh.util.Constants;
import cn.lsh.vo.response.BaseResponse;

@Component
public class ResponseHandler {
	private static final Logger LOGGER=LoggerFactory.getLogger(ResponseHandler.class);
	
	public BaseResponse getBaseResponse(Object obj,ResponseStatus responseStatus){
		BaseResponse baseResponse=getBaseResponse(responseStatus);
		baseResponse.setData(obj==null?Constants.NULL_DATA:obj);
		return baseResponse;
	}
	
    public BaseResponse getBaseResponse(ResponseStatus responseStatus){
		BaseResponse baseResponse=new BaseResponse();
		if(responseStatus!=null){
			baseResponse.setCode(responseStatus.getCode());
			baseResponse.setMessage(responseStatus.getMessage());
			baseResponse.setData(Constants.NULL_DATA);
		}
		return baseResponse;
	}
}
