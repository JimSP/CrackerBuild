package com.github.jimsp.crackerbuild.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.jimsp.crackerbuild.component.proxy.HelloCrackerBuildProxy;

@Component
public class Runner implements CommandLineRunner {
	
	@Override
	public void run(String... args) throws Exception {
		final HelloCrackerBuildProxy helloCrackerBuildProxy = new HelloCrackerBuildProxy();
		System.out.println(helloCrackerBuildProxy.call(1));
	}
}
