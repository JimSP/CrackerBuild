package com.github.jimsp.crackerbuild.utils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

public class CrackerBuildUtils {

	public static Element filterByElementKindClass(final Set<? extends Element> elements) {
		return elements //
				.stream() //
				.filter(predicate -> ElementKind.CLASS.equals(predicate.getKind())) //
				.findFirst() //
				.get();
	}

	public static Element filterByElementKindMethod(final List<? extends Element> elements) {
		return elements //
				.stream() //
				.filter(predicate -> ElementKind.METHOD.equals(predicate.getKind())) //
				.findFirst() //
				.get();
	}
	
	public static Element filterByElementKindConstructor(final List<? extends Element> elements) {
		return elements //
				.stream() //
				.filter(predicate -> ElementKind.CONSTRUCTOR.equals(predicate.getKind())) //
				.findFirst() //
				.get();
	}

	public static void dumb(final TypeElement annotation) {
		System.out.println();
		System.out.println("dump TypeElement");
		System.out.println("kind=" + annotation.getKind());
		System.out.println("annotationMirrors=" + annotation.getAnnotationMirrors());
		dumpCollection(annotation.getEnclosedElements());
		System.out.println("type=" + annotation.asType());
		System.out.println("interfaces=" + annotation.getInterfaces());
		System.out.println("modifiers=" + annotation.getModifiers());
		System.out.println("nestingKind=" + annotation.getNestingKind());
		System.out.println("qualifiedName=" + annotation.getQualifiedName());
		System.out.println("simpleName=" + annotation.getSimpleName());
		System.out.println("TypeParameters=" + annotation.getTypeParameters());
		System.out.println("-------------");
	}
	
	public static void dumpCollection(final Collection<? extends Element> elements) {
		System.out.println();
		System.out.println("dump Elements");
		elements.stream().forEach(element->dumb(element));
	}

	public static void dumb(final Element element) {
		System.out.println();
		System.out.println("dump Element");
		System.out.println("kind=" + element.getKind());
		System.out.println("annotationMirrors=" + element.getAnnotationMirrors());
		dumpCollection(element.getEnclosedElements());
		System.out.println("modifiers=" + element.getModifiers());
		System.out.println("simpleName=" + element.getSimpleName());
		System.out.println("type=" + element.asType());
		System.out.println("enclosingElement=" + Optional.ofNullable(element.getEnclosingElement()).orElse(null));
		System.out.println("-------------");

	}
}
