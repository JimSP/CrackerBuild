package com.github.jimsp.crackerbuild.component;

import com.jimsp.crackerbuild.annotation.Hazelcast;

public class HelloCrackerBuild {

	@Hazelcast
	public String helloCrackerBuilder(final String value) {
		com.github.jimsp.crackerbuild.hazelcast.Teste teste = new com.github.jimsp.crackerbuild.hazelcast.Teste();
		return teste.test(value);
	}
}
