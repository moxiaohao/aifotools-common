package io.aifo.apt.processor;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import io.aifo.api.apt.InstanceFactory;
import io.aifo.api.javassist.Inject;
import io.aifo.apt.AnnotationProcessor;
import io.aifo.apt.inter.IProcessor;

public class AppProxyProcessor implements IProcessor {

    @Override
    public void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor) {

        mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.WARNING, "正在处理前");
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Inject.class);
        for (Element element : elementsAnnotatedWith) {
            mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.WARNING, "正在处理: " + element.toString());
        }
        mAbstractProcessor.mMessager.printMessage(Diagnostic.Kind.WARNING, "正在处理后");
    }
}
