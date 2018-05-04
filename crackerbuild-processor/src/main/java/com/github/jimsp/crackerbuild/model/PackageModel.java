package com.github.jimsp.crackerbuild.model;

public class PackageModel {
	
	public static final String PACKAGE = "package"; 
	
	private final String packageName;

	public PackageModel(final String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}
	
	public String javaCode() {
		return PACKAGE //
				.concat(Constants.SPACE) //
				.concat(packageName) //
				.concat(Constants.SEMI_COMMA) //
				.concat(Constants.NEW_LINE)
				.concat(Constants.NEW_LINE);
	}
}
