package com.ramarizdev.eventureBackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;


@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.url}")
    private String cloudinaryUrl;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary1 = new Cloudinary(cloudinaryUrl);
        cloudinary1.config.secure = true;

        return cloudinary1;
    }

}
