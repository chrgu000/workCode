package com.whty.dmp.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring.xml",
		"classpath*:/spring-*.xml" ,
		"classpath*:/mybatis/mybatis-config.xml"})
public class JunitTest extends AbstractJUnit4SpringContextTests{

}
