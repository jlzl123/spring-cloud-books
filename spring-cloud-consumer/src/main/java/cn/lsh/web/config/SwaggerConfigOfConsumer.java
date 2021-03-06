package cn.lsh.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import cn.lsh.config.SwaggerConfig;

@Configuration
@EnableSwagger2
public class SwaggerConfigOfConsumer extends SwaggerConfig{

	@Bean
	@Override
	public Docket newsApi() {
		// TODO Auto-generated method stub
		return new Docket(DocumentationType.SWAGGER_2).groupName("consumer")
				.apiInfo(apiInfo())
				.select()
				.paths(PathSelectors.regex("/consumer/.*"))
				.build();
	}

}
