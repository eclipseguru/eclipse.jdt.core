package org.eclipse.jdt.apt.tests.external.annotations.batch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("org.eclipse.jdt.apt.tests.external.annotations.batch.BatchGen")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class BatchGenProcessor extends AbstractProcessor {

	private static int ROUND = 0;

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		try {
			if( ROUND == 0 ){
				ROUND ++;
				JavaFileObject builderFile = processingEnv.getFiler().createSourceFile("p1.Class0");
				try (PrintWriter writer = new PrintWriter(builderFile.openWriter())){
					writer.print("package p1;\n");
					writer.print("public class Class0{ X x; }");
				}
				builderFile = processingEnv.getFiler().createSourceFile("p1.gen.Class1");
				try (PrintWriter writer = new PrintWriter(builderFile.openWriter())){
					writer.print("package p1.gen;\n");
					writer.print("public class Class1{}");
				}
				builderFile = processingEnv.getFiler().createSourceFile("p1.gen.Class2");
				try (PrintWriter writer = new PrintWriter(builderFile.openWriter())){
					writer.print("package p1.gen;\n");
					writer.print("public class Class2{ Class1 class1; }");
				}
				return true;
			}
			else { // NO-OP
				return false;
			}
		} catch (IOException e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
		}

		return false;
	}

}
