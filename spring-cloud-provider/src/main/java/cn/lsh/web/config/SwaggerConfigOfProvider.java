package cn.lsh.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import cn.lsh.config.SwaggerConfig;

@Configuration
@EnableSwagger2
public class SwaggerConfigOfProvider extends SwaggerConfig{

	@Override
	@Bean
	public Docket newsApi() {
		// TODO Auto-generated method stub
		return new Docket(DocumentationType.SWAGGER_2)
		        .groupName("books")//分组
				.apiInfo(super.apiInfo())
				.select()
				.paths(PathSelectors.regex("/api/.*"))//配置要生成文档的api接口
//				.apis(RequestHandlerSelectors.basePackage("cn.lsh.web.controller"))
//				.paths(PathSelectors.any())
				.build();
	}
}
