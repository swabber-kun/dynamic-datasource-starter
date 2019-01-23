package com.dynamic;

import com.dynamic.controller.ConnectionSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

// spring boot默认开启了DataSource的自动装配，因此需要排除自动装配
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DataSourceApplication {

	@Bean
	@ConfigurationProperties(prefix = "connection")
	public ConnectionSettings connectionSettings(){
		return new ConnectionSettings();

	}

	public static void main(String[] args) {
		SpringApplication.run(DataSourceApplication.class, args);
	}

}

