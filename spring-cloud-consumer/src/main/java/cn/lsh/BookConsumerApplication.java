package cn.lsh;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class BookConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookConsumerApplication.class, args);
	}
}
