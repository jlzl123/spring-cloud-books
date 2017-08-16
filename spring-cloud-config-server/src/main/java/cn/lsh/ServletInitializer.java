package cn.lsh;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//继承SpringBootServletInitializer类并实现configure方法，使用application的sources方法可以通过 WAR 启动项目
public class ServletInitializer extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(ConfigServerApplication.class);
	}
}
