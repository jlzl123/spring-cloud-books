package cn.lsh.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lsh.dao.BookMapper;
import cn.lsh.entity.Book;
import cn.lsh.entity.BookCriteria;
import cn.lsh.entity.BookCriteria.Criteria;

@Service
public class BookService {
	private static final Logger LOGGER=LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookMapper bookMapper;
	
	public int saveBook(Book book){
		return bookMapper.insert(book);
	}

	public Book getBook(Integer bookId){
		return bookMapper.selectByPrimaryKey(bookId);
	}
	
	public List<Book> getBooks(){
		BookCriteria criteria=new BookCriteria();
		return bookMapper.selectByExample(criteria);
	}
	
	public int updateBook(Book book){
		return bookMapper.updateByPrimaryKey(book);
	}
	
	public int updateBook(Integer bookId,Book book){
		book.setBookId(bookId);
		BookCriteria bookCriteria=new BookCriteria();
		Criteria criteria=bookCriteria.createCriteria();
		criteria.andBookIdEqualTo(bookId);
		return bookMapper.updateByExample(book, bookCriteria);
	}
	
	public int deleteBook(Integer bookId){
		return bookMapper.deleteByPrimaryKey(bookId);
	}

}
