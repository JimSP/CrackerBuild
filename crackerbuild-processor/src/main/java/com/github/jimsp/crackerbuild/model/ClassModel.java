package com.github.jimsp.crackerbuild.model;

import java.util.List;
import java.util.Optional;

public class ClassModel {
	
	public static final String CLASS = "class";

	private final PackageModel packageModel;
	private final List<ImportModel> imports;
	private final List<ModifierModel> modifierClass;
	private final String className;
	private final Optional<InheritanceModel> inheritances;
	private final List<VariableModel> attributes;
	private final List<ConstructorModel> constructors;
	private final List<MethodModel> methods;

	public ClassModel(final PackageModel packageModel, final List<ImportModel> imports, final List<ModifierModel> modifierClass,
			final String className, final InheritanceModel inheritances, final List<VariableModel> attributes,
			final List<ConstructorModel> constructors, final List<MethodModel> methods) {
		this.packageModel = packageModel;
		this.imports = imports;
		this.modifierClass = modifierClass;
		this.className = className;
		this.inheritances = Optional.ofNullable(inheritances);
		this.attributes = attributes;
		this.constructors = constructors;
		this.methods = methods;
	}

	public PackageModel getPackageModel() {
		return packageModel;
	}

	public List<ImportModel> getImports() {
		return imports;
	}

	public List<ModifierModel> getModifierClass() {
		return modifierClass;
	}

	public String getClassName() {
		return className;
	}

	public Optional<InheritanceModel> getInheritances() {
		return inheritances;
	}

	public List<VariableModel> getAttributes() {
		return attributes;
	}

	public List<ConstructorModel> getConstructors() {
		return constructors;
	}

	public List<MethodModel> getMethods() {
		return methods;
	}

	public String javaCode() {
		return packageModel //
				.javaCode() //
				.concat(ImportModel.listJavaCode(imports)) //
				.concat(Constants.NEW_LINE) //
				.concat(ModifierModel.listJavaCode(modifierClass)) //
				.concat(CLASS)
				.concat(Constants.SPACE)
				.concat(className) //
				.concat(Constants.SPACE) //
				.concat(inheritances.orElse(InheritanceModel.defaultInstance()) //
						.javaCode()) //
				.concat(Constants.LEFT_CURLY_BRACKET) //
				.concat(Constants.NEW_LINE) //
				.concat(Constants.NEW_LINE) //
				.concat(VariableModel.listJavaCodeAttributes(attributes)) //
				.concat(ConstructorModel.listJavaCode(constructors)) //
				.concat(Constants.NEW_LINE)
				.concat(MethodModel.listJavaCode(methods)) //
				.concat(Constants.RIGTH_CURLY_BRACKET) //
				.concat(Constants.NEW_LINE);
	}
}
