package com.github.jimsp.crackerbuild.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.jimsp.crackerbuild.component.proxy.HelloCrackerBuildProxy;

@Component
public class Runner implements CommandLineRunner {
	
	@Override
	public void run(String... args) throws Exception {
		final Integer parameter = 1;
		final HelloCrackerBuildProxy helloCrackerBuildProxy = new HelloCrackerBuildProxy(parameter);

		final String result = helloCrackerBuildProxy.call();
		System.out.println(result);
	}

}
