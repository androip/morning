package morning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"morining","morning"})
@Configuration
@EnableFeignClients
@EnableCaching
public class MetaApplication {

	private static Logger logger = LoggerFactory.getLogger(MetaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MetaApplication.class, args);
		logger.info("MetaApplication start");
	}
}
