package cn.lsh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public abstract class SwaggerConfig {
	
	
	public abstract Docket newsApi();
//	{
//		return new Docket(DocumentationType.SWAGGER_2)
////		        .groupName("consumer")
//				.apiInfo(apiInfo())
//				.select()
//				.paths(PathSelectors.regex("/consumer/.*"))
//				.build();
//	}

	public ApiInfo apiInfo(){
		return new ApiInfoBuilder().title("系统Api接口管理")
				.description("各个微服务")
				.contact(new Contact("liushihua", "www.abc.com", "123456789@qq.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.version("1.0")
				.build();
	}
}
