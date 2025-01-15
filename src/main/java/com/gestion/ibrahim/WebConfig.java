package com.gestion.ibrahim;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
	public class WebConfig implements WebMvcConfigurer {

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    	 // Accès aux images via http://localhost:8082/images/
	        registry.addResourceHandler("/images/**")
	                .addResourceLocations("classpath:/static/images/"); 
	        }
	} 


