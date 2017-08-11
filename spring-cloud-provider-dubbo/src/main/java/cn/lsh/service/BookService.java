package cn.lsh.service;

import java.util.List;

import cn.lsh.entity.Book;

public interface BookService {
	
	public int saveBook(Book book);

	public int deleteBook(int bookId);
	
	public Book getBook(int bookId);
	
	public List<Book> getBooks();
	
	public int updateBook(int bookId,Book book);
}
