package cn.lsh.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.lsh.controller.BaseController;
import cn.lsh.entity.User;
import cn.lsh.exception.ResponseStatus;
import cn.lsh.service.UserService;
import cn.lsh.util.Constants;
import cn.lsh.util.JwtUtils;
import cn.lsh.vo.response.BaseResponse;
import cn.lsh.vo.security.AccessToken;
import cn.lsh.vo.security.Audience;
import cn.lsh.vo.security.LoginParamter;

@RestController
@Api(value="/")
public class JWTController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTController.class);
	@Autowired 
	private UserService userService;
    @Autowired
    private Audience audience;
	
    @ApiOperation(value="获取access_token",httpMethod="POST",notes="成功返回access_token",response=BaseResponse.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure")
    })
    @PostMapping(value="/oauth/token",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse getAccessToken(@ApiParam(value="登录信息",required=true) @RequestBody @Validated LoginParamter loginParamter,BindingResult result){
    	LOGGER.info("{} 获取access_token",loginParamter.getUserName());
    	BaseResponse baseResponse=null;
    	
    	//验证参数
    	baseResponse=getValidatedResult(result);
    	if(baseResponse!=null){
    		LOGGER.info("{} 获取access_token失败,{}",loginParamter.getUserName(),baseResponse.getMessage());
    		return baseResponse;
    	}
    	
    	//验证clientId是否有效
    	baseResponse=isValidClientId(loginParamter, audience);
    	if(baseResponse!=null){
    		LOGGER.info("{} 获取access_token失败,{}",loginParamter.getUserName(),baseResponse.getMessage());
    		return baseResponse;
    	}
    	
    	//验证用户名密码是否正确
    	User user=userService.findUserInfoByName(loginParamter.getUserName());
    	baseResponse=isValidUserName(user);
    	if(baseResponse!=null){
    		LOGGER.info("{} 获取access_token失败,{}",loginParamter.getUserName(),baseResponse.getMessage());
    		return baseResponse;
    	}
    	
    	baseResponse=isValidPassWord(loginParamter, user);
    	if(baseResponse!=null){
    		LOGGER.info("{} 获取access_token失败,{}",loginParamter.getUserName(),baseResponse.getMessage());
    		return baseResponse;
    	}
    	
    	//验证用户合法,根据用户信息生成jwt token,并返回给客户端
    	String accessToken=JwtUtils.createJWT(loginParamter, user, audience);
    	AccessToken token=new AccessToken();
    	token.setToken(accessToken);
    	token.setExpiresIn(audience.getExpiresSecond());
    	token.setTokenType(Constants.BEARER);
    	
    	baseResponse=new BaseResponse();
    	baseResponse.setCode(ResponseStatus.OK.getCode());
    	baseResponse.setMessage(ResponseStatus.OK.getMessage());
    	baseResponse.setData(token);
    	LOGGER.info("{} 获取access_token成功,{}",loginParamter.getUserName(),token);
    	return baseResponse;
    }
}
