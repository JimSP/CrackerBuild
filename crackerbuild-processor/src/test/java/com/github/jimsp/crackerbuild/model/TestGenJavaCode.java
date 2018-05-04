package com.github.jimsp.crackerbuild.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TestGenJavaCode {

	private static final String NAME_CLASS_B = "classB";
	private static final String NAME_CLASS_A = "classA";
	private static final String TYPE_CLASS_B = "ClassB";
	private static final String TYPE_CLASS_A = "ClassA";
	private static final String COM_GITHUB_JIMSP_CRACKERBUILD_MODEL = "com.github.jimsp.crackerbuild.model";
	private static final String CLASS_NAME = "CrackerBuildClassTest";

	@Test
	public void test() {
		final PackageModel packageModel = new PackageModel(Constants.PACKAGE_CRACKER_BUILD);
		
		final PackageModel packageModelImportA = new PackageModel(COM_GITHUB_JIMSP_CRACKERBUILD_MODEL);
		final PackageModel packageModelImportB = new PackageModel(COM_GITHUB_JIMSP_CRACKERBUILD_MODEL);
		final ImportModel importA = new ImportModel(packageModelImportA, TYPE_CLASS_A);
		final ImportModel importB = new ImportModel(packageModelImportB, TYPE_CLASS_B);
		final List<ImportModel> imports = Arrays.asList(importA, importB);
		
		final List<ModifierModel> modifierClass = Arrays.asList(ModifierModel.PUBLIC, ModifierModel.FINAL);
		final String className = CLASS_NAME;
		final InheritanceModel inheritances = null;
		
		
		final VariableModel attributeA = new VariableModel(Arrays.asList(ModifierModel.PRIVATE, ModifierModel.FINAL),
				TYPE_CLASS_A, NAME_CLASS_A);
		final VariableModel attributeB = new VariableModel(Arrays.asList(ModifierModel.PRIVATE, ModifierModel.FINAL),
				TYPE_CLASS_B, NAME_CLASS_B);
		final List<VariableModel> attributes = Arrays.asList(attributeA, attributeB);
		
		
		final VariableModel constructorParameterA = new VariableModel(Arrays.asList(ModifierModel.FINAL),
				TYPE_CLASS_A, NAME_CLASS_A);
		final VariableModel constructorParameterB = new VariableModel(Arrays.asList(ModifierModel.FINAL),
				TYPE_CLASS_B, NAME_CLASS_B);
		final String constructorBody = "this." + NAME_CLASS_A + "=" + NAME_CLASS_A + ";\n" + "this." + NAME_CLASS_B
				+ "=" + NAME_CLASS_B + ";\n";
		final ConstructorModel constructor = new ConstructorModel(Arrays.asList(ModifierModel.PUBLIC), CLASS_NAME,
				Arrays.asList(constructorParameterA, constructorParameterB), constructorBody);
		final List<ConstructorModel> constructors = Arrays.asList(constructor);
		
		final MethodModel getClassA = new MethodModel(Arrays.asList(ModifierModel.PUBLIC), "getClassA",
				Collections.emptyList(), "return " + NAME_CLASS_A + ";\n", TYPE_CLASS_A);
		final MethodModel getClassB = new MethodModel(Arrays.asList(ModifierModel.PUBLIC), "getClassB",
				Collections.emptyList(), "return " + NAME_CLASS_B + ";\n", TYPE_CLASS_B);
		final List<MethodModel> methods = Arrays.asList(getClassA, getClassB);

		final ClassModel classModel = new ClassModel(packageModel, imports, modifierClass, className, inheritances,
				attributes, constructors, methods);
		
		System.out.println(classModel.javaCode());
	}
}
