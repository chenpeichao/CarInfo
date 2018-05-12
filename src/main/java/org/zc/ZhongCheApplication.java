package org.zc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@SpringBootApplication
@PropertySource(value = {"classpath:config/constant/constant.properties"},encoding="utf-8")
public class ZhongCheApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ZhongCheApplication.class);
		springApplication.run(args);
	}
}
