package org.parseplatform;

import com.google.auto.service.AutoService;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public class JSONObjectProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet();
        annotations.add(JSONMapper.class.getCanonicalName());
        return annotations;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(JSONMapper.class).stream().filter(e -> e.getKind().equals(ElementKind.CLASS))
                .forEach(this::generateMapper);
        return true;
    }

    private void generateMapper(Element e) {
        try {
            final String sourceFileName = e.getSimpleName() + "_mapper";
            final String sourcePackage = elementUtils.getPackageOf(e).getQualifiedName().toString();

            final MethodSpec.Builder mapMethodBuilder = MethodSpec.methodBuilder("map")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.get(e.asType()))
                    .addParameter(JSONObject.class, "jsonObject")
                    .addStatement("$1T bean = new $1T()", e.asType());

            e.getEnclosedElements().stream().filter(f -> f.getKind().equals(ElementKind.FIELD))
                    .forEach(f -> {
                        mapMethodBuilder
                                .addCode(valueReader(f));
                    });

            final MethodSpec mapMethod = mapMethodBuilder.addStatement("return bean")
                    .build();

            final TypeSpec mapper = TypeSpec.classBuilder(sourceFileName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(mapMethod).build();

            JavaFile javaFile = JavaFile.builder(sourcePackage, mapper).build();
            javaFile.writeTo(filer);
        } catch (IOException ex) {
            messager.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), e);
        }
    }

    private CodeBlock valueReader(Element field) {
        CodeBlock.Builder builder = CodeBlock.builder();
        final String type = field.asType().toString();
        if (int.class.getCanonicalName().equals(type))
            return builder.addStatement(
                    "bean.set" + fieldName(field.getSimpleName()) + "(new Double((($T)jsonObject.get(\"" +
                            field.getSimpleName().toString() + "\")).doubleValue()).intValue())",
                    JSONNumber.class).build();
        if (double.class.getCanonicalName().equals(type))
            return builder
                    .addStatement("bean.set" + fieldName(field.getSimpleName()) + "((($T)jsonObject.get(\""
                            + fieldName(field.getSimpleName()) + "\")).doubleValue())", JSONNumber.class)
                    .build();

        return builder.addStatement("bean.set" + fieldName(field.getSimpleName()) + "((($T)jsonObject.get(\"" +
                        field.getSimpleName().toString() + "\")).stringValue())",
                JSONString.class).build();
    }

    private String fieldName(Name simpleName) {
        String name = simpleName.toString();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
