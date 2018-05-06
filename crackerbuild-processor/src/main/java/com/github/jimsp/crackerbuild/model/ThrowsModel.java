package com.github.jimsp.crackerbuild.model;

import java.util.List;

public class ThrowsModel {

	private final List<String> throwsList;

	public ThrowsModel(final List<String> throwsList) {
		this.throwsList = throwsList;
	}

	public List<String> getThrowsList() {
		return throwsList;
	}

	public String javaCode() {
		if (!throwsList.isEmpty()) {
			return Constants.THROWS //
					.concat(Constants.SPACE) //
					.concat(throwsList //
							.stream() //
							.reduce((a, b) -> a //
									.concat(Constants.COMMA) //
									.concat(Constants.SPACE)
									.concat(b)) //
							.get());
		}

		return Constants.BLANK;
	}
}
