package com.github.jimsp.crackerbuild.component;

import com.github.jimsp.crackerbuild.hazelcast.Teste;
import com.jimsp.crackerbuild.annotation.Hazelcast;

public class HelloCrackerBuild {

	@Hazelcast
	public String helloCrackerBuilder(final String value) {
		final Teste teste = new Teste();
		return teste.test(value);
	}
}
