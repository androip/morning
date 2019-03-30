package org.meta_test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import morining.MetaTestApplication;
import morining.api.ITestEntityService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MetaTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeignTests {

	private static Logger logger = LoggerFactory.getLogger(FeignTests.class);
	
	@Autowired 
	ITestEntityService testEntityService;

	@Test
	public void contextLoads() {
		
		logger.info("*******************"+JSON.toJSONString(testEntityService.sayHiFromClientOne("1")));
		
		
	}

}
