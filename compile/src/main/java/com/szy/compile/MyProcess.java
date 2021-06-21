package com.szy.compile;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.szy.annotations.BeginGenerate;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by devov on 2021/2/3.
 */

@AutoService(Processor.class)  // 需要添加
public class MyProcess extends AbstractProcessor {
    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;

    private static final String PACKAGE_NAME = "com.szy.compile";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BeginGenerate.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        System.out.println("size:"+annotations.size());

        for(Element e : roundEnv.getElementsAnnotatedWith(BeginGenerate.class)){
            System.out.println("-------------------->"+e);
            BeginGenerate  annotation = e.getAnnotation(BeginGenerate.class);
            generateClass(annotation.value1(),annotation.value2());
        }
        return true;
    }

    private void generateClass(int value1,int value2){
        FieldSpec fieldSpec = FieldSpec.builder(int.class,"offset")
                .addModifiers(Modifier.PRIVATE)
                .initializer("$L",5)
                .build();

        MethodSpec methodSpec = MethodSpec.methodBuilder("sum")
                .addStatement("int result = $L + $L + offset",value1,value2)
                .addStatement("return result")
                .returns(int.class)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("SimpleAdd")
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldSpec)
                .addMethod(methodSpec)
                .build();

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME,typeSpec).build();
        try{
           System.out.println("------------------------------------------------------>>>>"+mFiler.toString());
            javaFile.writeTo(mFiler);
        }catch (Exception e){

        }
    }
}
