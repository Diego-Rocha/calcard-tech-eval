package io.diego.tech.config;

import io.diego.lib.spring.data.service.generic.repository.GenericRepositoryImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "io.diego" })
@EntityScan({ "io.diego.tech.model" })
@EnableJpaRepositories(
	basePackages = { "io.diego.tech.repository" },
	repositoryBaseClass = GenericRepositoryImpl.class)
public class AppBoot {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(AppBoot.class).run(args);

	}
}