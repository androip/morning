

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import morining.MetaApiApplication;
import morining.api.ITestEntityService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MetaApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignAPITests {

	private static Logger logger = LoggerFactory.getLogger(FeignAPITests.class);
	
	@Autowired 
	ITestEntityService testEntityService;

	@Test
	public void contextLoads() {
		
		logger.info("*********8hello test");
		logger.info("----"+JSON.toJSONString(testEntityService.sayHiFromClientOne("1")));
		
	}

}
