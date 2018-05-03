package com.github.jimsp.crackerbuild.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jimsp.crackerbuild.component.HelloCrackerBuild;

@Configuration
public class ExampleConfiguration {

	@Bean
	public HelloCrackerBuild helloCrackerBuild() {
		return new HelloCrackerBuild();
	}
}
