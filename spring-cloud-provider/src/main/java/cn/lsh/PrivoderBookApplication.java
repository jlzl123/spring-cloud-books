package cn.lsh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PrivoderBookApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(PrivoderBookApplication.class, args);
	}

}
