package morning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

@Configuration
public class MetaConfig {

	public @Bean MongoClient mongoClient() {
	      return new MongoClient("localhost");
	  }

	public @Bean MongoTemplate mongoTemplate() {
      return new MongoTemplate(mongoClient(), "morning");
	}
}
