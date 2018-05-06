package com.github.jimsp.crackerbuild.model;

import java.util.List;

public class InheritanceModel {

	protected static InheritanceModel defaultInstance() {
		return new InheritanceModel(null, null);
	}

	private static final String IMPLEMENTS = "implements";
	private static final String EXTENDS = "extends";

	private final String extend;
	private final List<String> implementsList;

	public InheritanceModel(final String extend, final List<String> implementsList) {
		this.extend = extend;
		this.implementsList = implementsList;
	}

	public String getExtend() {
		return extend;
	}

	public List<String> getImplementsList() {
		return implementsList;
	}

	private Boolean hasExtend() {
		return extend != null;
	}

	private Boolean hasImplements() {
		return implementsList != null;
	}

	private Boolean isDefaltInstance() {
		return hasExtend() || hasImplements();
	}

	public String javaCode() {
		if (isDefaltInstance()) {
			return Constants.BLANK;
		}
		
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(hasExtend()) {
			stringBuilder //
			.append(EXTENDS //
					.concat(Constants.SPACE) //
					.concat(extend));
		}
		
		if(hasImplements()) {
			stringBuilder.append(implementsList //
									.stream() //
									.reduce((a, b) -> a.concat(Constants.COMMA) //
											.concat(Constants.SPACE) //
											.concat(b)).get());
		}
		
		return stringBuilder.toString();
	}
}
