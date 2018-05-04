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

	private Boolean isDefaltInstance() {
		return extend == null && implementsList == null;
	}

	public String javaCode() {
		if (isDefaltInstance()) {
			return Constants.BLANK;
		}

		return EXTENDS.concat(Constants.SPACE) //
				.concat(extend) //
				.concat(Constants.SPACE) //
				.concat(IMPLEMENTS) //
				.concat( //
						implementsList //
								.stream() //
								.reduce((a, b) -> a.concat(Constants.COMMA) //
										.concat(Constants.SPACE) //
										.concat(b)) //
								.orElse(Constants.BLANK));

	}
}
