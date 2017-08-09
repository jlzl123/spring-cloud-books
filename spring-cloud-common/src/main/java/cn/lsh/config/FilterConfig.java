package cn.lsh.config;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.lsh.filter.HttpBearerAuthorizeFilter;

@Configuration
@EnableZuulProxy
public class FilterConfig {

	@Bean
	public HttpBearerAuthorizeFilter accessFilter(){
		return new HttpBearerAuthorizeFilter();
	}
}
