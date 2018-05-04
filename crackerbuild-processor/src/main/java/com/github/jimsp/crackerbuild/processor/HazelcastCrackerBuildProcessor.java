package com.github.jimsp.crackerbuild.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.github.jimsp.crackerbuild.template.HazelcastTemplate;
import com.google.auto.service.AutoService;
import com.google.testing.compile.JavaFileObjects;

@SupportedAnnotationTypes("com.jimsp.crackerbuild.annotation.Hazelcast")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HazelcastCrackerBuildProcessor extends AbstractProcessor {

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		System.out.println(
				"c=HazelcastCrackerBuildProcessor, m=process, annotations=" + annotations + ",roundEnv=" + roundEnv);
		
		annotations //
				.stream() //
				.filter(predicate -> predicate != null) //
				.forEach(annotation -> { //
					dumb(annotation);

					roundEnv.getElementsAnnotatedWith(annotation) //
							.stream() //
							.filter(predicate -> predicate != null) //
							.forEach(element -> {
								dumb(element);
								element.getEnclosedElements().stream().forEach(action->{
									if(ElementKind.CLASS == action.getKind()) {
										final String className = action.getSimpleName().toString();
									}
								});
								final Element enclosingElement = element.getEnclosingElement();
								Optional //
										.ofNullable(enclosingElement) //
										.ifPresent(consumer -> {
											final String packageName = consumer.getEnclosingElement().toString();
											final String clazzName = consumer.getSimpleName().toString();
											final String methodName = element.getSimpleName().toString();
											final String types[] = element.asType().toString().split("[()]");
											System.out.println(Arrays.toString(types));
											
											final String code = HazelcastTemplate.getTemplate(packageName,clazzName,
													methodName, types[1], types[2]);
											System.out.println(code);
											try {
												final JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(packageName + "." + clazzName + "Proxy");
												try(final PrintWriter printWriter = new PrintWriter(javaFileObject.openWriter())){
													printWriter.print(code);
												}
											} catch (IOException e) {
												throw new RuntimeException(e);
											}
										});

							});
				});

		return true;
	}

	private void dumb(final TypeElement annotation) {
		System.out.println("dump TypeElement");
		System.out.println("kind=" + annotation.getKind());
		System.out.println("annotationMirrors=" + annotation.getAnnotationMirrors());
		System.out.println("enclosedElements=" + annotation.getEnclosedElements());
		System.out.println("type=" + annotation.asType());
		System.out.println("interfaces=" + annotation.getInterfaces());
		System.out.println("modifiers=" + annotation.getModifiers());
		System.out.println("nestingKind=" + annotation.getNestingKind());
		System.out.println("qualifiedName=" + annotation.getQualifiedName());
		System.out.println("simpleName=" + annotation.getSimpleName());
		System.out.println("TypeParameters=" + annotation.getTypeParameters());
		System.out.println("-------------");
	}

	private void dumb(final Element element) {
		System.out.println("dump Element");
		System.out.println("kind=" + element.getKind());
		System.out.println("annotationMirrors=" + element.getAnnotationMirrors());
		System.out.println("enclosedElements=" + element.getEnclosedElements());
		System.out.println("modifiers=" + element.getModifiers());
		System.out.println("simpleName=" + element.getSimpleName());
		System.out.println("type=" + element.asType());
		System.out.println("enclosingElement=" + element.getEnclosingElement().getSimpleName());
		System.out.println("-------------");

	}
}
