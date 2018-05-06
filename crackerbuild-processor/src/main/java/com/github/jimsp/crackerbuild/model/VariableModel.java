package com.github.jimsp.crackerbuild.model;

import java.util.List;

public class VariableModel {

	public static String listJavaCodeParameters(final List<VariableModel> variableModels) {
		if(variableModels == null) {
			return Constants.BLANK;
		}
		
		return variableModels //
				.stream() //
				.map(mapper -> mapper.javaCode()) //
				.reduce((a, b) -> a //
						.concat(Constants.COMMA) //
						.concat(b)) //
				.orElse(Constants.BLANK);
	}

	public static String listJavaCodeAttributes(final List<VariableModel> variableModels) {
		if(variableModels == null) {
			return Constants.BLANK;
		}
		
		return variableModels //
				.stream() //
				.map(mapper -> mapper.javaCode()) //
				.reduce((a, b) -> a //
						.concat(Constants.SEMI_COMMA) //
						.concat(Constants.NEW_LINE) //
						.concat(b) //
						.concat(Constants.SEMI_COMMA)
						.concat(Constants.NEW_LINE)) //
				.orElse(Constants.BLANK)
				.concat(Constants.NEW_LINE);
	}

	private final List<ModifierModel> modifiers;
	private final String type;
	private final String name;
	private final String value;

	public VariableModel(final List<ModifierModel> modifiers, final String type, final String name) {
		super();
		this.modifiers = modifiers;
		this.type = type;
		this.name = name;
		this.value = null;
	}
	
	public VariableModel(final List<ModifierModel> modifiers, final String type, final String name, final String value) {
		super();
		this.modifiers = modifiers;
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public List<ModifierModel> getModifiers() {
		return modifiers;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

	public String javaCode() {
		
		if(value == null) {
			return listDeclaration();
		}
		
		return listDeclaration()
				.concat(Constants.SPACE) //
				.concat(Constants.ATTRINUITION) //
				.concat(Constants.SPACE) //
				.concat(value);
	}

	private String listDeclaration() {
		return ModifierModel.listJavaCode(modifiers)  //
				.concat(type) //
				.concat(Constants.SPACE) //
				.concat(name);
	}
}
