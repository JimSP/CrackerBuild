package com.github.jimsp.crackerbuild.model;

import java.util.List;

public class ImportModel {

	public static final String IMPORT = "import";

	public static String listJavaCode(final List<ImportModel> imports) {
		return imports //
				.stream() //
				.map(mapper -> mapper.javaCode()) //
				.reduce((a, b) -> a //
						.concat(b)) //
				.get();
	}

	private final PackageModel packageModel;
	private final String className;

	public ImportModel(final PackageModel packageModel, final String className) {
		this.packageModel = packageModel;
		this.className = className;
	}

	public PackageModel getPackageModel() {
		return packageModel;
	}

	public String getClassName() {
		return className;
	}

	public String javaCode() {
		return IMPORT //
				.concat(Constants.SPACE) //
				.concat(packageModel.getPackageName()) //
				.concat(Constants.DOT //
						.concat(className)) //
				.concat(Constants.SEMI_COMMA) //
				.concat(Constants.NEW_LINE);
	}
}
