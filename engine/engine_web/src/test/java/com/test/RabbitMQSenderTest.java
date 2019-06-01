package com.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import morning.EngineApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RabbitMQSenderTest {

	
	@Autowired
    private RabbitTemplate template;
	
	@Test
	public void SendTest() {
		String msg = "Fuck your sister";
		Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
		map.put("k1", new HashMap<String,Object>(){;
		{
			put("1", 1);
			put("2", 2.0);
		}});
		map.put("k2", new HashMap<String,Object>(){
		{
			put("3", 3);
			put("4", "4");
		}});
		
		template.convertAndSend("Test",JSON.toJSONString(map));
        System.out.println(" [x] Sent '" + map + "'");
	}
}
