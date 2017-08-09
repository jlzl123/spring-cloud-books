package cn.lsh.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lsh.vo.response.BaseResponse;

@ControllerAdvice//如果返回的为json数据或其它对象，添加该注解
@ResponseBody
public class GlobalExceptionHandler {//添加全局异常处理流程，根据需要设置需要处理的异常

	public Object methodArgumentNotValidHandler(MethodArgumentNotValidException exception){
		//按需重新封装需要返回的错误信息
		List<ArgumentInvalidResult> invalidArguments=new ArrayList<ArgumentInvalidResult>();
		//解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
		for(FieldError error:exception.getBindingResult().getFieldErrors()){
			ArgumentInvalidResult invalidArgument=new ArgumentInvalidResult();
			invalidArgument.setField(error.getField());
			invalidArgument.setDefaultMessage(error.getDefaultMessage());
			invalidArgument.setRejectedValue(error.getRejectedValue());
			invalidArguments.add(invalidArgument);
		}
		
		BaseResponse baseResponse=new BaseResponse();
		baseResponse.setCode(ResponseStatus.PARAMETER_ERROR.getCode());
		baseResponse.setMessage(ResponseStatus.PARAMETER_ERROR.getMessage());
		baseResponse.setData(invalidArguments);
		return baseResponse;
	}
}
