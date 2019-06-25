package com;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import morning.entity.User;
import morning.repo.mapper.UserMapper;





@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EngineTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MybatisTests {

    @Autowired
    private UserMapper userMapper;
	
	  @Test
	    public void save() {
	        User user = new User();
	        user.setUsername("jp");
	        user.setPassword("123");
	        user.setSex(1);
	        user.setAge(18);
	        // 返回插入的记录数 ，期望是1条 如果实际不是一条则抛出异常
	        Assert.assertEquals(1,userMapper.insert(user));
	    }
}
