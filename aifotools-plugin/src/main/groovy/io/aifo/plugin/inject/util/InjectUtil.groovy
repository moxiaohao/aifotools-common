package io.aifo.plugin.inject.util


import javassist.ClassPool
import javassist.CtMethod

public class InjectUtil {

    /**
     * 事先载入相关类
     * @param pool
     */
    static void importBaseClass(ClassPool pool) {
        pool.importPackage(io.aifo.plugin.inject.util.InjectHelper.InjectAnnotation);
    }

    static String getSimpleName(CtMethod ctmethod) {
        def methodName = ctmethod.getName();
        return methodName.substring(
                methodName.lastIndexOf('.') + 1, methodName.length());
    }

    static String getClassName(int index, String filePath) {
        int end = filePath.length() - 6 // .class = 6
        return filePath.substring(index, end).replace('\\', '.').replace('/', '.')
    }



}
