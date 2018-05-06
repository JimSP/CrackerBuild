package com.github.jimsp.crackerbuild.template;

public class HazelcastTemplate {

	public static String getTemplate(final String packageName, final String className, final String methodName,
			final String parameterType, final String returnType) {
		return new StringBuilder() //
				.append("package " + packageName + ".proxy;\n") //
				.append("\n") //
				.append("import java.util.concurrent.ExecutionException;\n") //
				.append("\n") //
				.append("import " + packageName + "." + className + "Build;\n") //
				.append("\n") //
				.append("import com.hazelcast.config.Config;\n") //
				.append("import com.hazelcast.config.ExecutorConfig;\n") //
				.append("import com.hazelcast.core.Hazelcast;\n") //
				.append("\n") //
				.append("public class " + className + "Proxy {\n") //
				.append("\n") //
				.append("	public static final String EXECUTOR_SERVICE_NAME = \"HelloCrackerBuildProxy\";\n") //
				.append("	public static final String INSTANCE_NAME = \"crackerBuild\";\n") //
				.append("	public static final Integer EXECUTION_POLL = 10;\n") //
				.append("\n") //
				.append("	private final " + className + " target;\n") //
				.append("	private final " + parameterType + " parameter;\n") //
				.append("\n") //
				.append("	public " + className + "Proxy(final " + className + " target, final " + parameterType
						+ " parameter) {\n") //
				.append("		this.target = target;\n") //
				.append("		this.parameter = parameter;\n") //
				.append("	}\n") //
				.append("\n") //
				.append("	public " + returnType + " " + methodName + "()\n") //
				.append("			throws InterruptedException, ExecutionException {\n") //
				.append("      return " + methodName + "Async(parameter).get();\n") //
				.append("   }\n") //
				.append("\n") //
				.append("	public java.util.concurrent.Future<" + returnType + "> \n" + methodName
						+ "Async() throws InterruptedException, ExecutionException {\n") //
				.append("		return Hazelcast.getOrCreateHazelcastInstance(config()).getExecutorService(EXECUTOR_SERVICE_NAME)\n") //
				.append("				.submit(() -> target." + methodName + "(parameter));\n") //
				.append("	}\n") //
				.append("\n") //
				.append("	private Config config() {\n") //
				.append("		return new Config(INSTANCE_NAME).addExecutorConfig(executorConfig());\n") //
				.append("	}\n") //
				.append("\n") //
				.append("	private ExecutorConfig executorConfig() {\n") //
				.append("		return new ExecutorConfig(EXECUTOR_SERVICE_NAME, EXECUTION_POLL);\n") //
				.append("	}\n") //
				.append("}\n") //
				.toString();
	}
}
