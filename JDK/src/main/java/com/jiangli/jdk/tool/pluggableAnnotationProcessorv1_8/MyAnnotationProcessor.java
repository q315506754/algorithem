package com.jiangli.jdk.tool.pluggableAnnotationProcessorv1_8;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * -XprintRounds  -processor com.jiangli.jdk.tool.pluggableAnnotationProcessorv1_8.MyAnnotationProcessor
 */
@SupportedAnnotationTypes("com.jiangli.jdk.tool.aptv1_6.StaticCount")//可以用"*"表示支持所有Annotations
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class MyAnnotationProcessor extends AbstractProcessor {
    private void note(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //annotations的值是通过@SupportedAnnotationTypes声明的且目标源代码拥有的所有Annotations
        for (TypeElement te : annotations) {
            note("annotation:" + te.toString());
        }
        Set<? extends Element> elements = roundEnv.getRootElements();//获取源代码的映射对象
        for (Element e : elements) {
            //获取源代码对象的成员
            List<? extends Element> enclosedElems = e.getEnclosedElements();
            //留下方法成员,过滤掉其他成员
            List<? extends ExecutableElement> ees = ElementFilter.methodsIn(enclosedElems);
            for (ExecutableElement ee : ees) {
                note("--ExecutableElement name is " + ee.getSimpleName());
                List<? extends AnnotationMirror> as = ee.getAnnotationMirrors();//获取方法的Annotations
                note("--as=" + as);
                for (AnnotationMirror am : as) {
                    //获取Annotation的值
                    Map<? extends ExecutableElement, ? extends AnnotationValue> map = am.getElementValues();
                    Set<? extends ExecutableElement> ks = map.keySet();
                    for (ExecutableElement k : ks) {//打印Annotation的每个值
                        AnnotationValue av = map.get(k);
                        note("----" + ee.getSimpleName() + "." + k.getSimpleName() + "=" + av.getValue());
                    }
                }
            }
        }
        return false;
    }
}