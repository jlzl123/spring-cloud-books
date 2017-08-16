package cn.lsh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import cn.lsh.PrivoderBookApplication;
import cn.lsh.entity.User;
import cn.lsh.vo.security.LoginParamter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PrivoderBookApplication.class)
public class RedisServiceTest {
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Test
	public void test(){
//		addUSer();
//		User user=(User) redisTemplate.opsForValue().get("lsh");
//		System.out.println(user.getName());
		addLoginParamter();
	}

	@SuppressWarnings("unused")
	private void addUSer(){
		User user=new User();
		user.setId(1);
		user.setName("liushihua");
		user.setPassword("87157158201208195214135170169181371724084228");
		user.setRole("管理员");
		user.setSalt("123");
		redisTemplate.opsForValue().set("lsh",user);
	}
	
	private void addLoginParamter(){
		LoginParamter loginParamter=new LoginParamter("098f6bcd4621d373cade4e832627b4f6", "liushihua", 
				"123456");
		redisTemplate.opsForValue().set("login", loginParamter);
	}
}
