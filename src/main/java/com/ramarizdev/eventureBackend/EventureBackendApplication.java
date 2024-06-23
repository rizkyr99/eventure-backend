package com.ramarizdev.eventureBackend;

import com.ramarizdev.eventureBackend.config.RsaKeyConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
@Log
public class EventureBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventureBackendApplication.class, args);
	}

}
