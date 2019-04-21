package com.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import morning.EngineApp;
import morning.service.impl.EngineService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EngineTests {

	private static Logger logger = LoggerFactory.getLogger(EngineTests.class);
	
	@Autowired
	private EngineService engineService;
	
	
	@Test
	public void getOneTest() {
		logger.info(JSON.toJSONString(engineService.startProcess("p1", "jiangpeng")));
	}
}
