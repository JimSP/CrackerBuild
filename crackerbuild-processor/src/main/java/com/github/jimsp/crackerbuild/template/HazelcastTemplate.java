package com.github.jimsp.crackerbuild.template;

public class HazelcastTemplate {

	public static String getTemplate(final String packageName, final String className) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("package " + packageName + ";\n");
		stringBuilder.append("\n");
		stringBuilder.append("public class " + className + "{\n");
		stringBuilder.append("   public String test(final String message){\n");
		stringBuilder.append("      System.out.println(\"execution generated method:\" + message);\n");
		stringBuilder.append("      return message;\n");
		stringBuilder.append("   }\n");
		stringBuilder.append("}\n");
		return stringBuilder.toString();
	}

}
