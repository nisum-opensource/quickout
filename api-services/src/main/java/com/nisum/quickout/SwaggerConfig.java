/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.nisum.quickout.controller"))
//				.paths(PathSelectors.any())
				.paths(regex("/quickout.*"))
				.build()
				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
        ApiInfo apiInfo =new ApiInfo(
        		"QuickOut", 
        		"QuickOut Web Service", 
        		"1.0", 
        		"Terms of service", 
        		new Contact("Faraz", "https://www.nisum.com/about/", "mfaraz@nisum.com"),
    			"License", 
    			"https://www.nisum.com", 
    			new ArrayList<>()); 
        		
        		
        return apiInfo;
    }
	
}