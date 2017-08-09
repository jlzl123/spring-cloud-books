package cn.lsh;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.lsh.dao")
public class SpringCloudApplication {
	private static final Logger LOGGER=LoggerFactory.getLogger(SpringCloudApplication.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        LOGGER.info("start execute SpringCloudApplication....\n");
        SpringApplication.run(SpringCloudApplication.class, args);
        LOGGER.info("end execute SpringCloudApplication....\n");
	}

}
