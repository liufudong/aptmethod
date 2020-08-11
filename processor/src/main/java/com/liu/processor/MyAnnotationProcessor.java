package com.liu.processor;

import com.google.auto.service.AutoService;
import com.liu.annotation.MyAnnotation;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class MyAnnotationProcessor extends AbstractProcessor {
    public static final String METHOD_NAME = "getTitles";
    public static final String PACKAGE_NAME = "com.liu.test";
    public static final String CLASS_NAME = "$BindsActivity";
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(MyAnnotation.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MyAnnotation.class);
        for (Element mTypeElement : elements) {
            try {
                generateActivityFile(mTypeElement, PACKAGE_NAME).writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public JavaFile generateActivityFile(Element mTypeElement, String packgeName) {

        StringBuffer mStringBuffer = new StringBuffer();
        MyAnnotation mMyAnnotation = mTypeElement.getAnnotation(MyAnnotation.class);
        if (mMyAnnotation != null) {
            for (String text:mMyAnnotation.sString()){
                mStringBuffer.append("\""+text+"\"").append(",");
            }
            MethodSpec.Builder injectMethod = MethodSpec.methodBuilder(METHOD_NAME)
                    .addModifiers(Modifier.PUBLIC).returns(String[].class)
                    .addStatement("return new String[]{"+mStringBuffer.deleteCharAt(mStringBuffer.length()-1).toString()+"}");
//            .addStatement("return "+mMyAnnotation.sString());
//                    .returns(String[].class);
//                    .addParameter(TypeName.get(mTypeElement.asType()), "activity", Modifier.FINAL);
//            injectMethod.addStatement("return",
//                    "new String[]{"+mStringBuffer.deleteCharAt(mStringBuffer.length()-1).toString()+"}");
            //new String[]{"text1", "text2", "text3"}
            //generaClass
            TypeSpec injectClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + CLASS_NAME)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(injectMethod.build())
                    .build();
            return JavaFile.builder(packgeName, injectClass).build();
        }
        return null;
    }
}
