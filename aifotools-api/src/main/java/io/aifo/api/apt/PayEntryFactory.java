package io.aifo.api.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by moxiaohao on 2017/4/22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface PayEntryFactory {

    String packageName();

    Class<?> payEntryTemplate();
}
