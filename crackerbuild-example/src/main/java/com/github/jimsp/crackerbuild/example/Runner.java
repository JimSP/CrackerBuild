package com.github.jimsp.crackerbuild.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.jimsp.crackerbuild.component.HelloCrackerBuild;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	private HelloCrackerBuild helloCrackerBuild;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(helloCrackerBuild.helloCrackerBuilder("Teste"));
	}
}
