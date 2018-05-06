package com.github.jimsp.crackerbuild.model;

import java.util.List;

public class ConstructorModel {

	public static String listJavaCode(final List<ConstructorModel> constructors) {
		return constructors //
				.stream() //
				.map(mapper -> mapper.javaCode()) //
				.reduce((a, b) -> a //
						.concat(Constants.NEW_LINE) //
						.concat(b) //
						.concat(Constants.NEW_LINE)) //
				.get().concat(Constants.NEW_LINE);
	}

	private final List<ModifierModel> modifiers;
	private final String constructorName;
	private final List<VariableModel> variableModels;
	private final String constructorBody;

	public ConstructorModel(final List<ModifierModel> modifiers, final String constructorName,
			final List<VariableModel> variableModels, final String constructorBody) {
		this.modifiers = modifiers;
		this.constructorName = constructorName;
		this.variableModels = variableModels;
		this.constructorBody = constructorBody;
	}

	public List<ModifierModel> getModifiers() {
		return modifiers;
	}

	public String getConstructorName() {
		return constructorName;
	}

	public List<VariableModel> getVariableModels() {
		return variableModels;
	}

	public String getConstructorBody() {
		return constructorBody;
	}

	public String javaCode() {
		return ModifierModel.listJavaCode(modifiers) //
				.concat(Constants.SPACE) //
				.concat(constructorName) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(VariableModel.listJavaCodeParameters(variableModels)) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.LEFT_CURLY_BRACKET) //
				.concat(Constants.NEW_LINE) //
				.concat(constructorBody) //
				.concat(Constants.RIGTH_CURLY_BRACKET);
	}
}
