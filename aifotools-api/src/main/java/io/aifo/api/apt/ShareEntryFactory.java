package io.aifo.api.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author srwing
 * @fileName ShareEntryFactory
 * @time 2019-11-27 11:23
 * @Email surao@foryou56.com (有问题请邮件)
 * @desc
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ShareEntryFactory {

    String packageName();

    Class<?> payEntryTemplate();
}