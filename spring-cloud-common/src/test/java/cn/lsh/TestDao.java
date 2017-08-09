package cn.lsh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.lsh.dao.BookMapper;
import cn.lsh.entity.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDao {
	@Autowired
	private BookMapper bookMapper;
	
	@Test
	public void test(){
		System.out.println("*****test*");
		Book book=new Book();
		book.setBookName("spring cloud 微服务");
		book.setPublisher("翟永超");
		bookMapper.insert(book);
	}

}
