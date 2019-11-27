package io.aifo.apt.inter;


import javax.annotation.processing.RoundEnvironment;

import io.aifo.apt.AnnotationProcessor;

/**
 * 注解处理器接口
 */
public interface IProcessor {
    void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor);
}
