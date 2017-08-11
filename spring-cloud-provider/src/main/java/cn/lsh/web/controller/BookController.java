package cn.lsh.web.controller;


import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;














import cn.lsh.entity.Book;
import cn.lsh.exception.ResponseStatus;
import cn.lsh.service.BookService;
import cn.lsh.util.Constants;
import cn.lsh.vo.response.BaseResponse;

@RestController
@Api(value="/")
public class BookController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	@ApiOperation(value="添加某本书籍",httpMethod="POST",notes="添加成功返回bookId",response=BaseResponse.class)
	@ApiResponses(value={
			@ApiResponse(code=200,message="Success",response=BaseResponse.class),
			@ApiResponse(code=401,message="Unauthorized"),
			@ApiResponse(code=403,message="Forbidden"),
			@ApiResponse(code=404,message="Not Found"),
			@ApiResponse(code=500,message="Failure"),
	})
	@PostMapping(value="/api/books",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse addBook(@Validated @ApiParam(value="添加的某本书籍信息",required=true)@RequestBody Book book){
		LOGGER.info("添加书籍：{}",book.getBookName());
		BaseResponse baseResponse=new BaseResponse();
		int i=bookService.saveBook(book);
		if(i!=0){
			baseResponse.setCode(ResponseStatus.OK.getCode());
			baseResponse.setMessage(ResponseStatus.OK.getMessage());
			baseResponse.setData(i);
		}else{
			baseResponse.setCode(ResponseStatus.DATA_CREATE_ERROR.getCode());
			baseResponse.setMessage(ResponseStatus.DATA_CREATE_ERROR.getMessage());
//			baseResponse.setData(Constants.NULL_DATA);
		}
		return baseResponse;
	}

	@ApiOperation(value="查询所有书籍",httpMethod="GET",notes="查询所有书籍",response=BaseResponse.class)
	@ApiResponses(value={
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")
	})
	@GetMapping(value="/api/books",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse getBooks(){
		BaseResponse baseResponse=new BaseResponse();
		List<Book> books=bookService.getBooks();
		if(books!=null&&!books.isEmpty()){
			baseResponse.setCode(ResponseStatus.OK.getCode());
			baseResponse.setMessage(ResponseStatus.OK.getMessage());
			baseResponse.setData(books);
		}else{
			baseResponse.setCode(ResponseStatus.DATA_REQUERY_ERROR.getCode());
            baseResponse.setMessage(ResponseStatus.DATA_REQUERY_ERROR.getMessage());
            baseResponse.setData("Query books failed");

		}
		return baseResponse;
	}
	

    @ApiOperation(value = "查询某本书籍", httpMethod = "GET",
            notes = "根据bookId，查询到某本书籍",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
	@GetMapping(value="/api/books/{bookId:[0-9]*}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse getBook(@NotNull @ApiParam(value="书籍ID",required=true)@PathVariable("bookId")Integer bookId){
		BaseResponse baseResponse=new BaseResponse();
		Book book=bookService.getBook(bookId);
		if(book!=null){
			LOGGER.info("查询到书籍ID为{}的书籍", bookId);
			baseResponse.setCode(ResponseStatus.OK.getCode());
			baseResponse.setMessage(ResponseStatus.OK.getMessage());
			baseResponse.setData(book);
		}else{
			LOGGER.info("没有查询到书籍ID为{}的书籍", bookId);
			baseResponse.setCode(ResponseStatus.DATA_REQUERY_ERROR.getCode());
            baseResponse.setMessage(ResponseStatus.DATA_REQUERY_ERROR.getMessage());
            baseResponse.setData("Query book failed");

		}
		return baseResponse;
	}
	
	
    @ApiOperation(value = "更新某本书籍", httpMethod = "PUT",
            notes = "更新的某本书籍信息",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")
    })
	@PutMapping(value="/api/books/{bookId:[0-9]*}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse updateBook(@NotNull @ApiParam(value="要更新的某本书籍ID",required=true) @PathVariable("bookId")Integer bookId,
			@Validated @NotNull @ApiParam(value="要更新的某本书籍信息",required=true) @RequestBody Book book){
		BaseResponse baseResponse=new BaseResponse();
		int i=bookService.updateBook(bookId, book);
		if(i!=1){
			baseResponse.setCode(ResponseStatus.DATA_UPDATED_ERROR.getCode());
			baseResponse.setMessage(ResponseStatus.DATA_UPDATED_ERROR.getMessage());
			baseResponse.setData("Update book failed id=" + book.getBookId());
		}else{
			baseResponse.setCode(ResponseStatus.OK.getCode());
			baseResponse.setMessage(ResponseStatus.OK.getMessage());
			baseResponse.setData("Update book id=" + bookId);
		}
		return baseResponse;
	}
    
    @ApiOperation(value = "删除某本书籍信息", httpMethod = "DELETE",
            notes = "删除某本书籍信息",
            response = BaseResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BaseResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    @DeleteMapping(value="/api/books/{bookId:[0-9]*}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse deleteBook(@NotNull @ApiParam(value="要删除的某本书籍ID",required=true) @PathVariable("bookId")Integer bookId){
    	BaseResponse baseResponse=new BaseResponse();
    	int i=bookService.deleteBook(bookId);
    	if(i!=1){
			baseResponse.setCode(ResponseStatus.DATA_DELETED_ERROR.getCode());
			baseResponse.setMessage(ResponseStatus.DATA_DELETED_ERROR.getMessage());
			baseResponse.setData("Deleted book failed id=" + bookId);
		}else{
			baseResponse.setCode(ResponseStatus.OK.getCode());
			baseResponse.setMessage(ResponseStatus.OK.getMessage());
			baseResponse.setData("Deleted book id=" + bookId);
		}
    	return baseResponse;
    }
}
