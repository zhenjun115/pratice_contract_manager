package com.contract.manager;

//import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( exclude = {
//		org.activiti.spring.boot.SecurityAutoConfiguration.class,
//		SecurityAutoConfiguration.class
} )

//@MapperScan( "com.contract.manager.mapper" )
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}