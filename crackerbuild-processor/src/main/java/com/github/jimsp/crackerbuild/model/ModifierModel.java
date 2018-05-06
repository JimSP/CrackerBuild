package com.github.jimsp.crackerbuild.model;

import java.util.List;

public enum ModifierModel {

	PUBLIC("public"), //
	PRIVATE("private"), //
	PROTECTED("protected"), //
	VOLATILE("volatile"), //
	STATIC("static"), //
	FINAL("final"), //
	SYNCRONIZED("sycronized");

	public static String listJavaCode(final List<ModifierModel> modifiers) {
		return modifiers //
				.stream() //
				.map(mapper->mapper.value.concat(Constants.SPACE)) //
				.reduce((a,b)->a.concat(b)) //
				.get();
	}

	private final String value;

	private ModifierModel(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
