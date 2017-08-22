package cn.lsh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import cn.lsh.entity.Book;
import cn.lsh.exception.ResponseStatus;
import cn.lsh.util.Constants;
import cn.lsh.vo.response.BaseResponse;
import cn.lsh.vo.security.LoginParamter;
import feign.hystrix.FallbackFactory;
//如果需要触发来进行熔断，则需要用 fallbackFactory
@FeignClient(name="api-gateway",fallbackFactory=BookConsumerService.FeignClientFallbackFactory.class)
public interface BookConsumerService {
	
	@PostMapping(value="/api-gateway/v1/api/books")
	public BaseResponse addBook(@RequestHeader(Constants.AUTHORIZATION)String authorizationToken,@RequestBody Book book);

	@GetMapping(value="/api-gateway/v1/api/books")
	public BaseResponse getBooks(@RequestHeader(Constants.AUTHORIZATION)String authorizationToken);
	
	@GetMapping(value="/api-gateway/v1/api/books/{bookId}")
	public BaseResponse getBook(@RequestHeader(Constants.AUTHORIZATION)String authorizationToken,
			@PathVariable("bookId")Integer bookId);
	
	@PutMapping(value="/api-gateway/v1/api/books/{bookId}")
	public BaseResponse updateBook(@RequestHeader(Constants.AUTHORIZATION)String authorizationToken,
			@PathVariable("bookId")Integer bookId,@RequestBody Book book);
	
	@DeleteMapping(value="/api-gateway/v1/api/books/{bookId}")
	public BaseResponse deleteBook(@RequestHeader(Constants.AUTHORIZATION)String authorizationToken,
			@PathVariable("bookId")Integer bookId);
	
	@PostMapping(value="/oauth/token")
	public BaseResponse getToken(@RequestBody LoginParamter loginParamter);
	
	@Component
	class FeignClientFallbackFactory implements FallbackFactory<BookConsumerService>{
		private static final Logger LOGGER=LoggerFactory.getLogger(FeignClientFallbackFactory.class);

		@Override
		public BookConsumerService create(Throwable arg0) {
			// TODO Auto-generated method stub
			return new BookConsumerService() {
				
				@Override
				public BaseResponse updateBook(String authorizationToken, Integer bookId,
						Book book) {
					// TODO Auto-generated method stub
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}",book.getBookId());
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookName = {}",book.getBookName());
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数： publisher= {}",book.getPublisher());
					return initFallbackRespone();
				}
				
				@Override
				public BaseResponse getToken(LoginParamter loginParamter) {
					// TODO Auto-generated method stub
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：clientId = {}", loginParamter.getClientId());
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：userName = {}", loginParamter.getUserName());
                    FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：password = {}", loginParamter.getPassword());
					return initFallbackRespone();
				}
				
				@Override
				public BaseResponse getBooks(String authorizationToken) {
					// TODO Auto-generated method stub
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法");
					return initFallbackRespone();
				}
				
				@Override
				public BaseResponse getBook(String authorizationToken, Integer bookId) {
					// TODO Auto-generated method stub
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}",bookId);
					return initFallbackRespone();
				}
				
				@Override
				public BaseResponse deleteBook(String authorizationToken, Integer bookId) {
					// TODO Auto-generated method stub
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}",bookId);
					return initFallbackRespone();
				}
				
				@Override
				public BaseResponse addBook(String authorizationToken, Book book) {
					// TODO Auto-generated method stub
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookId = {}",book.getBookId());
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数：bookName = {}",book.getBookName());
					FeignClientFallbackFactory.LOGGER.info("异常发生，进入fallback方法，接收的参数： publisher= {}",book.getPublisher());
					return initFallbackRespone();
				}
				
				private BaseResponse initFallbackRespone(){
					BaseResponse baseResponse=new BaseResponse();
					baseResponse.setCode(ResponseStatus.INVALID_SERVICE.getCode());
					baseResponse.setMessage(ResponseStatus.INVALID_SERVICE.getMessage());
					return baseResponse;
				}
			};
		}
		
	}
}
