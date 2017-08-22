package cn.lsh;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringCloudApplication
@EnableFeignClients
@EnableHystrix
public class BookConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookConsumerApplication.class, args);
	}
}
