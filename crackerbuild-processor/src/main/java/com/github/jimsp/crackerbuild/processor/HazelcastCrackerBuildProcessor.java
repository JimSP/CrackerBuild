package com.github.jimsp.crackerbuild.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.github.jimsp.crackerbuild.model.ClassModel;
import com.github.jimsp.crackerbuild.model.Constants;
import com.github.jimsp.crackerbuild.model.ConstructorModel;
import com.github.jimsp.crackerbuild.model.ImportModel;
import com.github.jimsp.crackerbuild.model.InheritanceModel;
import com.github.jimsp.crackerbuild.model.MethodModel;
import com.github.jimsp.crackerbuild.model.ModifierModel;
import com.github.jimsp.crackerbuild.model.PackageModel;
import com.github.jimsp.crackerbuild.model.ThrowsModel;
import com.github.jimsp.crackerbuild.model.VariableModel;
import com.github.jimsp.crackerbuild.utils.CrackerBuildUtils;
import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("com.jimsp.crackerbuild.annotation.Hazelcast")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HazelcastCrackerBuildProcessor extends AbstractProcessor {

	private static final String DEZ = "10";
	private static final int DOIS = 2;
	private static final int UM = 1;

	private static final String COM_HAZELCAST_CORE = "com.hazelcast.core";
	private static final String COM_HAZELCAST_CONFIG = "com.hazelcast.config";
	private static final String JAVA_UTIL_CONCURRENT = "java.util.concurrent";
	private static final String JAVA_UTIL_CONCURRENT_FUTURE = "java.util.concurrent.Future";

	private static final String EXECUTION_POLL = "EXECUTION_POLL";
	private static final String EXECUTOR_SERVICE_NAME = "EXECUTOR_SERVICE_NAME";
	private static final String INSTANCE_NAME = "INSTANCE_NAME";

	private static final String HAZELCAST = "Hazelcast";
	private static final String EXECUTOR_CONFIG = "ExecutorConfig";
	private static final String CONFIG = "Config";
	private static final String INTERRUPTED_EXCEPTION = "InterruptedException";
	private static final String EXECUTION_EXCEPTION = "ExecutionException";

	private static final String CONFIG_METHOD = "config";
	private static final String EXECUTOR_CONFIG_METHOD = "executorConfig";
	private static final String ADD_EXECUTOR_CONFIG = "addExecutorConfig";
	private static final String GET_EXECUTOR_SERVICE = "getExecutorService";

	private static final String PROXY = "proxy";
	private static final String GET = "get()";
	private static final String SUBMIT = "submit";

	private static final String PARAMETER = "parameter";
	private static final String TARGET = "target";

	private static final String DELIMITER_METHOD = "[()]";

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		annotations //
				.stream() //
				.filter(predicate -> predicate != null) //
				.map(annotation -> {
					final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);

					final Element elementCLass = CrackerBuildUtils.filterByElementKindClass(elements);

					final Element elementConstructor = CrackerBuildUtils
							.filterByElementKindConstructor(elementCLass.getEnclosedElements());

					final Element elementMethod = CrackerBuildUtils
							.filterByElementKindMethod(elementCLass.getEnclosedElements());

					return getTargetClassModel(elementCLass, elementConstructor, elementMethod);
				}).forEach(classModel -> { //
					writeJavaCode(classModel);
				});

		return true;
	}

	private void writeJavaCode(final ClassModel classModel) {
		try {
			final String javaCode = classModel.javaCode();
			System.out.println(javaCode);
			final JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(classModel.getClassName());

			try (final PrintWriter printWriter = new PrintWriter(javaFileObject.openWriter())) {
				printWriter.print(javaCode);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ClassModel getTargetClassModel(final Element elementCLass, final Element elementConstructor,
			final Element elementMethod) {

		final String targetClassName = elementCLass.getSimpleName().toString();
		final String targetPackageName = elementCLass.getEnclosingElement().asType().toString();
		final String targetConstructorParameterType = getParameterType(elementConstructor);

		final String targetMethodName = elementMethod.getSimpleName().toString();
		final String targetMethodReturnType = getReturnType(elementMethod);
		final String targetAsyncMethodReturnType = getAsyncReturnType(elementMethod);

		final PackageModel proxyPackage = createPackage(targetPackageName //
				.concat(Constants.DOT) //
				.concat(PROXY));

		final PackageModel targetPackage = createPackage(targetPackageName);

		final List<ImportModel> imports = createImports(targetPackage, targetClassName);
		final List<ModifierModel> modifierClass = getModifiers(ModifierModel.PUBLIC);
		final String className = getProxyClassName(targetClassName);
		final InheritanceModel inheritances = new InheritanceModel(null, Arrays.asList("Callable<String>, Serializable"));

		final List<VariableModel> attributes = new ArrayList<>();
		attributes.add(createPublicStaticFinal(Constants.STRING, EXECUTOR_SERVICE_NAME, "\"" //
				.concat(className) ///
				.concat("\"")));
		attributes.add(createPublicStaticFinal(Constants.STRING, INSTANCE_NAME, "\"" //
				.concat("CrackerBuild") //
				.concat("\"")));
		attributes.add(createPublicStaticFinal(Constants.INTEGER, EXECUTION_POLL, DEZ));
		attributes.add(createPrivateFinalAttribute(targetClassName, TARGET));

		final List<VariableModel> parameters = Arrays
				.asList(createFinalParemeter(targetConstructorParameterType, PARAMETER));

		final ConstructorModel constructor = createConstructors(className, parameters, targetClassName);

		final MethodModel methodCall = createCallMethod(targetMethodName, null, targetMethodReturnType);
		final MethodModel methodCallAsync = createCallAsyncMethod(getAsyncMethodName(targetMethodName), targetMethodName, null,
				targetAsyncMethodReturnType);
		final MethodModel methodConfig = createConfigMethod();
		final MethodModel methodExecutorConfig = createExecutorConfigMethod();

		final ClassModel classModel = getClassModel(proxyPackage, imports, modifierClass, className, inheritances,
				attributes, Arrays.asList(constructor),
				Arrays.asList(methodCall, methodCallAsync, methodConfig, methodExecutorConfig));
		return classModel;
	}

	private String getAsyncMethodName(final String targetMethodName) {
		return targetMethodName.concat(Constants.ASYNC_SULFIX);
	}

	private String getParameterType(final Element element) {
		return splitElementType(element)[UM];
	}

	private String getReturnType(final Element element) {
		return splitElementType(element)[DOIS];
	}

	private String getAsyncReturnType(final Element element) {
		return JAVA_UTIL_CONCURRENT_FUTURE //
				.concat(Constants.LESS_THAN_SIGN) //
				.concat(splitElementType(element)[DOIS]) //
				.concat(Constants.GREATER_THAN_SIGN);
	}

	private String[] splitElementType(final Element element) {
		return element //
				.asType() //
				.toString() //
				.split(DELIMITER_METHOD);
	}

	private List<ImportModel> createImports(final PackageModel targetPackage, final String targetClassName) {
		final PackageModel javaUtilConcurrencePackage = createPackage(JAVA_UTIL_CONCURRENT);
		final String executionExceptionClass = EXECUTION_EXCEPTION;
		final ImportModel executionExceptionImport = createImport(javaUtilConcurrencePackage, executionExceptionClass);
		
		final ImportModel importTargetClass = createImport(targetPackage, targetClassName);

		final PackageModel comHazelcastConfigPackage = createPackage(COM_HAZELCAST_CONFIG);
		final PackageModel comHazelcastCorePackage = createPackage(COM_HAZELCAST_CORE);

		final ImportModel importHazelcastConfig = createImport(comHazelcastConfigPackage, CONFIG);
		final ImportModel importExecutorConfig = createImport(comHazelcastConfigPackage, EXECUTOR_CONFIG);
		final ImportModel importHazelcast = createImport(comHazelcastCorePackage, HAZELCAST);

		return Arrays.asList(importTargetClass, executionExceptionImport, importHazelcastConfig, importExecutorConfig,
				importHazelcast);
	}

	private ClassModel getClassModel(final PackageModel packageModel, final List<ImportModel> imports,
			final List<ModifierModel> modifierClass, final String className, final InheritanceModel inheritances,
			final List<VariableModel> attributes, final List<ConstructorModel> constructors,
			final List<MethodModel> methods) {

		return new ClassModel(packageModel, imports, modifierClass, className, inheritances, attributes, constructors,
				methods);
	}

	private PackageModel createPackage(final String packageName) {
		return new PackageModel(packageName);
	}

	private ImportModel createImport(final PackageModel packageModel, final String className) {
		return new ImportModel(packageModel, className);
	}

	private List<ModifierModel> getModifiers(final ModifierModel... modifier) {
		return Arrays.asList(modifier);
	}

	private String getProxyClassName(final String targetClassName) {
		return targetClassName //
				.concat(Constants.SULFIX_CLASS_NAME);
	}

	private VariableModel createPublicStaticFinal(final String type, final String name, final String value) {
		return new VariableModel(Arrays.asList(ModifierModel.PUBLIC, ModifierModel.STATIC, ModifierModel.FINAL), type,
				name, value);
	}

	private VariableModel createPrivateFinalAttribute(final String type, final String name) {
		return new VariableModel(Arrays.asList(ModifierModel.PRIVATE, ModifierModel.FINAL), type, name);
	}

	private VariableModel createFinalParemeter(final String type, final String name) {
		return new VariableModel(Arrays.asList(ModifierModel.FINAL), type, name);
	}

	private ConstructorModel createConstructors(final String constructorName, final List<VariableModel> parameters,
			final String injectAttributeType) {

		final String constructorBody = parameters //
				.stream() //
				.map(mapper -> Constants.THIS //
						.concat(Constants.DOT) //
						.concat(TARGET) //
						.concat(Constants.SPACE) //
						.concat(Constants.ATTRINUITION) //
						.concat(Constants.SPACE) //
						.concat(Constants.NEW) //
						.concat(Constants.SPACE) //
						.concat(injectAttributeType) //
						.concat(Constants.LEFT_PARENTHESIS) //
						.concat(mapper.getName()) //
						.concat(Constants.RIGTH_PARENTHESIS) //
						.concat(Constants.SEMI_COMMA) //
						.concat(Constants.NEW_LINE)) //
				.reduce((a, b) -> a //
						.concat(b)) //
				.get();

		return new ConstructorModel(Arrays.asList(ModifierModel.PUBLIC), constructorName, parameters, constructorBody);
	}

	private MethodModel createCallMethod(final String methodName, final List<VariableModel> parameters,
			final String returnType) {

		final String methodBody = Constants.RETURN //
				.concat(Constants.SPACE) //
				.concat(methodName) //
				.concat(Constants.ASYNC_SULFIX) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.DOT) //
				.concat(GET) //
				.concat(Constants.SEMI_COMMA);

		final List<String> throwsList = Arrays.asList(INTERRUPTED_EXCEPTION, EXECUTION_EXCEPTION);
		return new MethodModel(Arrays.asList(ModifierModel.PUBLIC), methodName, parameters, methodBody, returnType, new ThrowsModel(throwsList));
	}

	private MethodModel createCallAsyncMethod(final String methodName, final String targetMethodName,
			final List<VariableModel> parameters, final String returnType) {

		final String methodBody = Constants.RETURN //
				.concat(Constants.SPACE) //
				.concat(Constants.GET_OR_CREATE_HAZELCAST_INSTANCE) //
				.concat(Constants.DOT) //
				.concat(GET_EXECUTOR_SERVICE) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(EXECUTOR_SERVICE_NAME) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.DOT) //
				.concat(SUBMIT) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(TARGET) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.SEMI_COMMA) //
				.concat(Constants.NEW_LINE);

		return new MethodModel(Arrays.asList(ModifierModel.PUBLIC), methodName, parameters, methodBody, returnType);
	}

	private MethodModel createConfigMethod() {
		final String methodBody = Constants.RETURN //
				.concat(Constants.SPACE) //
				.concat(Constants.NEW) //
				.concat(Constants.SPACE) //
				.concat(CONFIG) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(INSTANCE_NAME) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.DOT) //
				.concat(ADD_EXECUTOR_CONFIG) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(EXECUTOR_CONFIG_METHOD) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.SEMI_COMMA) //
				.concat(Constants.NEW_LINE);

		return new MethodModel(Arrays.asList(ModifierModel.PUBLIC), CONFIG_METHOD, null, methodBody, CONFIG);
	}

	private MethodModel createExecutorConfigMethod() {
		final String methodBody = Constants.RETURN //
				.concat(Constants.SPACE) //
				.concat(Constants.NEW) //
				.concat(Constants.SPACE) //
				.concat(EXECUTOR_CONFIG) //
				.concat(Constants.LEFT_PARENTHESIS) //
				.concat(EXECUTOR_SERVICE_NAME) //
				.concat(Constants.COMMA) //
				.concat(Constants.SPACE) //
				.concat(EXECUTION_POLL) //
				.concat(Constants.RIGTH_PARENTHESIS) //
				.concat(Constants.SEMI_COMMA) //
				.concat(Constants.NEW_LINE);

		return new MethodModel(Arrays.asList(ModifierModel.PUBLIC), EXECUTOR_CONFIG_METHOD, null, methodBody,
				EXECUTOR_CONFIG);
	}
}
