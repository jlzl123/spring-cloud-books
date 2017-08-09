package cn.lsh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {
	private static final Logger LOGGER=LoggerFactory.getLogger(ServiceDiscoveryApplication.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LOGGER.info("start execute ServiceDiscoveryApplication....\n");
		SpringApplication.run(ServiceDiscoveryApplication.class, args);
		LOGGER.info("end execute ServiceDiscoveryApplication....\n");
	}

}
