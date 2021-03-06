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
import morining.api.IProcessMetaService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MetaTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MetaProcessTemplateTests {

	private static Logger logger = LoggerFactory.getLogger(MetaProcessTemplateTests.class);
	
	@Autowired 
	IProcessMetaService processMetaService;

	@Test
	public void getOneTest() {
		logger.info("getOneTest:"+JSON.toJSONString(processMetaService.getProcessTemplate("1")));
	}
	
	@Test
	public void getListTest() {
		logger.info(JSON.toJSONString(processMetaService.getProcessTemplateList(0,1)));
	}
}
