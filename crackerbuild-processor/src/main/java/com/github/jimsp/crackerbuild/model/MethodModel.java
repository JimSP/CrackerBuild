package com.github.jimsp.crackerbuild.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MethodModel {

	public static String listJavaCode(final List<MethodModel> methods) {
		return methods //
				.stream() //
				.map(mapper -> mapper.javaCode()) //
				.reduce((a, b) -> a //
						.concat(b)) //
				.get();
	}

	private final List<ModifierModel> modifiers;
	private final String methodName;
	private final List<VariableModel> parameters;
	private final String methodBody;
	private final String returnType;
	private final ThrowsModel throwsModel;

	public MethodModel(final List<ModifierModel> modifiers, final String methodName,
			final List<VariableModel> parameters, final String methodBody, final String returnType) {
		this.modifiers = modifiers;
		this.methodName = methodName;
		this.parameters = parameters;
		this.returnType = returnType;
		this.methodBody = methodBody;
		this.throwsModel = new ThrowsModel(Collections.emptyList());
	}
	
	public MethodModel(final List<ModifierModel> modifiers, final String methodName,
			final List<VariableModel> parameters, final String methodBody, final String returnType, final ThrowsModel throwsModel) {
		this.modifiers = modifiers;
		this.methodName = methodName;
		this.parameters = parameters;
		this.returnType = returnType;
		this.methodBody = methodBody;
		this.throwsModel = throwsModel;
	}

	public List<ModifierModel> getModifiers() {
		return modifiers;
	}

	public String getMethodName() {
		return methodName;
	}

	public List<VariableModel> getParameters() {
		return parameters;
	}

	public String getMethodBody() {
		return methodBody;
	}

	public String getReturnType() {
		return returnType;
	}

	public String javaCode() {
		return ModifierModel.listJavaCode(modifiers) //
				.concat(Constants.SPACE) //
				.concat(returnType) //
				.concat(Constants.SPACE) //
				.concat(methodName) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(VariableModel.listJavaCodeParameters(parameters)) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.SPACE) //
				.concat(throwsModel.javaCode()) //
				.concat(Constants.SPACE) //
				.concat(Constants.LEFT_CURLY_BRACKET) //
				.concat(Constants.NEW_LINE) //
				.concat(methodBody) //
				.concat(Constants.RIGTH_CURLY_BRACKET) //
				.concat(Constants.NEW_LINE)
				.concat(Constants.NEW_LINE);
	}

}
