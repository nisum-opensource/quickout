/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/

package com.nisum.quickout;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Umair Iqbal Spring Boot Main class to start the application. This
 *         will bring up a built in Tomcat server to serve requests.
 * 
 */
@ImportResource("application-context.xml")
@SpringBootApplication
@EnableJpaRepositories("com.nisum.quickout.persistence.repository")
@EntityScan("com.nisum.quickout.persistence.domain")
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan(basePackages = "com.nisum.quickout")
//developer can add their own properties for debugging purposes. Must be ignored by the source control system to
//avoid conflict with other dev environments
@PropertySource(value="classpath:local_quickout.properties", ignoreResourceNotFound=true)
public class QuickoutApplication {
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(QuickoutApplication.class, args);
	}
}
