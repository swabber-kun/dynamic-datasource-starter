package com.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// spring boot默认开启了DataSource的自动装配，因此需要排除自动装配
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DataSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataSourceApplication.class, args);
	}
}
