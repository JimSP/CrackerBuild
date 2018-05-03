package com.github.jimsp.crackerbuild.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.github.jimsp.crackerbuild.template.HazelcastTemplate;
import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("com.jimsp.crackerbuild.annotation.Hazelcast")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HazelcastCrackerBuildProcessor extends AbstractProcessor {

	private static final String DOT = ".";

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		System.out.println("c=HazelcastCrackerBuildProcessor, m=process, annotations=" + annotations + ",roundEnv=" + roundEnv);
		
		annotations.stream().forEach(annotation->{
			try {
				final String packageName = "com.github.jimsp.crackerbuild.hazelcast";
				final String className = "Teste";
				final JavaFileObject javaFileObject = super.processingEnv.getFiler().createSourceFile(packageName + DOT + className);
				writeCode(javaFileObject, annotation, packageName, className);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		return true;
	}

	private void writeCode(final JavaFileObject javaFileObject, final TypeElement annotation, final String packageName, final String className) throws IOException {
		final String code = HazelcastTemplate.getTemplate(packageName, className);
		try(final PrintWriter printWriter = new PrintWriter(javaFileObject.openWriter())){
			printWriter.println(code);
		}
		System.out.println(code);
	}
}
