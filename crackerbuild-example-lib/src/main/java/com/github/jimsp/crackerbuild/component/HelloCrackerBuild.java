package com.github.jimsp.crackerbuild.component;

import java.io.Serializable;
import java.util.concurrent.Callable;

import com.jimsp.crackerbuild.annotation.Hazelcast;

@Hazelcast
public class HelloCrackerBuild implements Callable<String>, Serializable{

	private static final long serialVersionUID = 5060150282922897311L;

	private final Integer value;
	
	public HelloCrackerBuild(final Integer value) {
		this.value = value;
	}

	@Override
	public String call() throws Exception {
		return value.toString();
	}
}
