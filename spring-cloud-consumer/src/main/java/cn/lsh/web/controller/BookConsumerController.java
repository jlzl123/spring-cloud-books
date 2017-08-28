package cn.lsh.web.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.lsh.controller.BaseController;
import cn.lsh.entity.Book;
import cn.lsh.exception.ResponseStatus;
import cn.lsh.service.BookConsumerService;
import cn.lsh.util.Constants;
import cn.lsh.vo.response.BaseResponse;
import cn.lsh.vo.security.AccessToken;
import cn.lsh.vo.security.LoginParamter;

@RestController
@Api("/")
public class BookConsumerController extends BaseController{
	private static final Logger LOGGER=LoggerFactory.getLogger(BookConsumerController.class);

	@Autowired
	private BookConsumerService bookConsumerService;
	@Autowired 
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@PostMapping(value="/consumer/books",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse addBook(@Validated @RequestBody Book book,BindingResult bindingResult){
		BaseResponse response=super.getValidatedResult(bindingResult);
		if(response!=null){
			return response;
		}
		
//		//取出redis中的token
//		String jwtToken=stringRedisTemplate.opsForValue().get(Constants.BEARER);
//		//取出reids中保存的用户信息
//		LoginParamter loginParamter=(LoginParamter) redisTemplate.opsForValue().get(Constants.LOGIN_PARAMETER);
//		
//		//如果token不为空，且请求服务正确，则返回结果
//		if(!ObjectUtils.isEmpty(jwtToken)){
//			BaseResponse baseResponse=bookConsumerService.addBook(Constants.BEARER+" "+jwtToken, book);
//			//调用服务返回正确
//			if(baseResponse.getCode()==ResponseStatus.OK.getCode()){
//				return baseResponse;
//			}
//		}
//		
//		/*
//		 * 上面token为空，用户为登录
//		 * 则验证用户，并生成token
//		 */
//		BaseResponse baseRespone=bookConsumerService.getToken(loginParamter);
//		if(validAccess(baseRespone)){
//			Map<String, Object> token=(Map<String, Object>) baseRespone.getData();
//			return bookConsumerService.addBook(Constants.BEARER+" "+token.get(Constants.ACCESS_TOKEN), book);
//		}
		return bookConsumerService.addBook(Constants.BEARER+" "+stringRedisTemplate.opsForValue().get(Constants.BEARER), book);
	}
	
	@GetMapping(value="/consumer/books",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse getBooks(){
//		String jwtToken=stringRedisTemplate.opsForValue().get(Constants.BEARER);
//		LoginParamter loginParamter=(LoginParamter) redisTemplate.opsForValue().get(Constants.LOGIN_PARAMETER);
//		
//		if(!ObjectUtils.isEmpty(jwtToken)){
//			BaseResponse baseResponse=bookConsumerService.getBooks(Constants.BEARER+" "+jwtToken);
//			if(baseResponse.getCode()==ResponseStatus.OK.getCode()){
//				return baseResponse;
//			}
//		}
//		
//		BaseResponse baseResponse=bookConsumerService.getToken(loginParamter);
//		if(validAccess(baseResponse)){
//			Map<String, Object> token=(Map<String, Object>) baseResponse.getData();
//			return bookConsumerService.getBooks(Constants.BEARER+" "+token.get(Constants.ACCESS_TOKEN));
//		}
		return bookConsumerService.getBooks(Constants.BEARER+" "+stringRedisTemplate.opsForValue().get(Constants.BEARER));
	}
	
	@GetMapping(value="/consumer/books/{bookId:[0-9]*}")
	public BaseResponse getBook(@PathVariable("bookId")Integer bookId){
//		BaseResponse response=super.getValidatedResult(bindingResult);
//		if(response!=null){
//			return response;
//		}
		
//		String jwtToken=stringRedisTemplate.opsForValue().get(Constants.BEARER);
//		LoginParamter loginParamter=(LoginParamter) redisTemplate.opsForValue().get(Constants.LOGIN_PARAMETER);
//		
//		if(!ObjectUtils.isEmpty(jwtToken)){
//			BaseResponse baseResponse=bookConsumerService.getBook(Constants.BEARER+" "+jwtToken, bookId);
//			if(baseResponse.getCode()==ResponseStatus.OK.getCode()){
//				return baseResponse;
//			}
//		}
//		
//		BaseResponse baseResponse=bookConsumerService.getToken(loginParamter);
//		if(validAccess(baseResponse)){
//			Map<String, Object> token=(Map<String, Object>) baseResponse.getData();
//			return bookConsumerService.getBook(Constants.BEARER+" "+token.get(Constants.ACCESS_TOKEN),bookId);
//		}
		return bookConsumerService.getBook(Constants.BEARER+" "+stringRedisTemplate.opsForValue().get(Constants.BEARER),bookId);
	}
	
	@PutMapping(value="/consumer/books/{bookId:[0-9]*}")
	public BaseResponse updateBook(@PathVariable("bookId")Integer bookId,@Validated @RequestBody Book book,BindingResult bindingResult){
		BaseResponse response=super.getValidatedResult(bindingResult);
		if(response!=null){
			return response;
		}
		
//		String jwtToken=stringRedisTemplate.opsForValue().get(Constants.BEARER);
//		LoginParamter loginParamter=(LoginParamter) redisTemplate.opsForValue().get(Constants.LOGIN_PARAMETER);
//		
//		if(!ObjectUtils.isEmpty(jwtToken)){
//			BaseResponse baseResponse=bookConsumerService.updateBook(Constants.BEARER+" "+jwtToken, bookId, book);
//			if(baseResponse.getCode()==ResponseStatus.OK.getCode()){
//				return baseResponse;
//			}
//		}
//		
//		BaseResponse baseResponse=bookConsumerService.getToken(loginParamter);
//		if(validAccess(baseResponse)){
//			Map<String, Object> token=(Map<String, Object>) baseResponse.getData();
//			return bookConsumerService.updateBook(Constants.BEARER+" "+token.get(Constants.ACCESS_TOKEN),bookId,book);
//		}
		return bookConsumerService.updateBook(Constants.BEARER+" "+stringRedisTemplate.opsForValue().get(Constants.BEARER),bookId,book);
	}
	
	@DeleteMapping(value="/consumer/books/{bookId:[0-9]*}")
	public BaseResponse deleteBook(@PathVariable("bookId")Integer bookId){
//		BaseResponse response=super.getValidatedResult(bindingResult);
//		if(response!=null){
//			return response;
//		}
		
//		String jwtToken=stringRedisTemplate.opsForValue().get(Constants.BEARER);
//		LoginParamter loginParamter=(LoginParamter) redisTemplate.opsForValue().get(Constants.LOGIN_PARAMETER);
//		
//		if(!ObjectUtils.isEmpty(jwtToken)){
//			BaseResponse baseResponse=bookConsumerService.deleteBook(Constants.BEARER+" "+jwtToken, bookId);
//			if(baseResponse.getCode()==ResponseStatus.OK.getCode()){
//				return baseResponse;
//			}
//		}
//		
//		BaseResponse response=bookConsumerService.getToken(loginParamter);
//		if(validAccess(response)){
//			Map<String, Object> token=(Map<String, Object>) response.getData();
//			return bookConsumerService.deleteBook(Constants.BEARER+" "+token.get(Constants.ACCESS_TOKEN), bookId);
//		}
		return bookConsumerService.deleteBook(Constants.BEARER+" "+stringRedisTemplate.opsForValue().get(Constants.BEARER), bookId);
	}
	
	//验证用户合法，生成token并保存到redis
//	private boolean validAccess(BaseResponse baseResponse){
//		boolean flag=false;
//		if(baseResponse.getCode()==ResponseStatus.OK.getCode()){
//			//调用网关提供的服务接口返回的baseResponse的token实例会转换HashMap
//			Map<String, Object> token=(Map<String, Object>) baseResponse.getData();
//			LOGGER.info("token_type is: {}",token.get(Constants.TOKEN_TPYE));
//			LOGGER.info("access_token is: {}",token.get(Constants.ACCESS_TOKEN));
//			LOGGER.info("expires_in:",token.get(Constants.EXPIRES_IN));
//			stringRedisTemplate.opsForValue().set(Constants.BEARER, (String)token.get(Constants.ACCESS_TOKEN),
//					((Integer)token.get(Constants.EXPIRES_IN)).longValue(),TimeUnit.SECONDS);
//			flag=true;
//		}
//		return flag;
//	}
}
