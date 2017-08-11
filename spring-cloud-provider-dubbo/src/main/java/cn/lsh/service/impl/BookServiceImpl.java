package cn.lsh.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import cn.lsh.dao.BookMapper;
import cn.lsh.entity.Book;
import cn.lsh.entity.BookCriteria;
import cn.lsh.entity.BookCriteria.Criteria;
import cn.lsh.service.BookService;

@Service
public class BookServiceImpl implements BookService,ApplicationContextAware{
	private static final Logger LOGGER=LoggerFactory.getLogger(BookServiceImpl.class);
	private static ApplicationContext applicationContext=null;
	@Autowired
	private BookMapper bookMapper;

	@Override
	public int saveBook(Book book) {
		// TODO Auto-generated method stub
		LOGGER.info("插入一条数据：{}",book.toString());
		return bookMapper.insert(book);
	}

	@Override
	public int deleteBook(int bookId) {
		// TODO Auto-generated method stub
		LOGGER.info("删除bookId为{}的数据",bookId);
		return bookMapper.deleteByPrimaryKey(bookId);
	}

	@Override
	public Book getBook(int bookId) {
		// TODO Auto-generated method stub
		Book book=bookMapper.selectByPrimaryKey(bookId);
		LOGGER.info("查询到一条数据：{}",book.toString());
		return book;
	}

	@Override
	public List<Book> getBooks() {
		// TODO Auto-generated method stub
		LOGGER.info("查询所有数据");
		BookCriteria bookCriteria=new BookCriteria();
		return bookMapper.selectByExample(bookCriteria);
	}

	@Override
	public int updateBook(int bookId, Book book) {
		// TODO Auto-generated method stub
		LOGGER.info("更新一条数据：{}",book.toString());
		BookCriteria bookCriteria=new BookCriteria();
		Criteria criteria=bookCriteria.createCriteria();
		criteria.andBookIdEqualTo(bookId);
		return bookMapper.updateByExample(book, bookCriteria);
	}

	public static Object getBean(String name){
		return getApplicationContext().getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz){
		return getApplicationContext().getBean(clazz);
	}
	
	public static <T> T getBean(String name,Class<T> clazz){
		return getApplicationContext().getBean(name, clazz);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		// TODO Auto-generated method stub
		if(BookServiceImpl.applicationContext==null){
			BookServiceImpl.applicationContext=ctx;
		}
	}
    
	//获取applicationContext
	private static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
}
