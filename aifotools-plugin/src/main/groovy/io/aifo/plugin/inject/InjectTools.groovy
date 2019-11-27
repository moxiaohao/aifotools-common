package io.aifo.plugin.inject

import io.aifo.plugin.inject.util.InjectHelper
import io.aifo.plugin.inject.util.InjectUtil
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtField
import javassist.CtMethod
import javassist.CtNewConstructor
import org.gradle.api.Project
import java.lang.annotation.Annotation
import java.lang.reflect.Constructor

class InjectTools {

    private final static ClassPool pool = ClassPool.getDefault()


    static void injectDir(String path, String packageName, Project project) {
//      project.logger.error("transform application path --------" + path)
        pool.appendClassPath(path)
        //project.android.bootClasspath 加入android.jar，否则找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString());

        pool.importPackage(InjectHelper.InjectAnnotation)
        InjectUtil.importBaseClass(pool);
        pool.insertClassPath(new ClassClassPath(this.getClass()));

        File dir = new File(path)
        if (dir.isDirectory()) {

            dir.eachFileRecurse { File file ->

                String filePath = file.absolutePath//确保当前文件是class文件，并且不是系统自动生成的class文件
                if (filePath.endsWith(".class")
                        && !filePath.contains('R$')
                        && !filePath.contains('$')//代理类
                        && !filePath.contains('R.class')
                        && !filePath.contains("BuildConfig.class")) {

                    project.logger.error("transform begin dir path" + filePath)

                    // 判断当前目录是否是在我们的应用包里面
                    int index = filePath.indexOf(packageName);
                    boolean isMyPackage = index != -1;

                    if (isMyPackage) {

                        String className = InjectUtil.getClassName(index, filePath);
                        CtClass c = pool.get(className)

                        if (c.isFrozen()) c.defrost()

                        InjectInfo injectInfo = new InjectInfo()
                        injectInfo.setProject(project)
                        injectInfo.setClazz(c)

                        //检查类中的方法 有没有OnCreate 和 构造函数 有的话先存起来
                        for (CtMethod ctmethod : c.getDeclaredMethods()) {
                            String methodName = InjectUtil.getSimpleName(ctmethod);
                            project.logger.error("transform application methodName --------" + methodName)
                            if (InjectHelper.ON_CREATE.contains(methodName)) {
                                injectInfo.setOnCreateMethod(ctmethod)
                            }
                        }

//                      injectInfo.setConstructMethods(ctConstructorList);
                        injectContent(project, c, injectInfo, path)
                        c.detach()//用完一定记得要卸载，否则pool里的永远是旧的代码
                    }
                }
            }
        }
    }

    private static void injectContent(Project project, CtClass c, InjectInfo injectInfo, String path) {

        for (CtField ctField : c.getDeclaredFields()) {
            for (Annotation mAnnotation : ctField.getAnnotations()) {
                if (mAnnotation.annotationType().canonicalName.equals(InjectHelper.InjectAnnotation)) {

                    if (null != injectInfo.OnCreateMethod) {
                        injectInfo.OnCreateMethod.insertAfter(ctField.name + " = new " + ctField.type.name + "();");
                    } else {

                        boolean hasEmptyContructor = false;
                        for (CtConstructor constructor : c.getDeclaredConstructors()) {
                            constructor.insertAfter(ctField.name + " = new " + ctField.type.name + "();");
                            if (constructor.isEmpty()) {
                                hasEmptyContructor = true;
                            }
                        }
                        if (!hasEmptyContructor && !c.isInterface()) {
                            CtConstructor defaultConstructor = CtNewConstructor.make("public " + c.getSimpleName() + "() {}", c);
                            defaultConstructor.insertAfter(ctField.name + " = new " + ctField.type.name + "();")
                            c.addConstructor(defaultConstructor);
                        }
                    }
                    c.writeFile(path)
                }
            }
        }
    }
}
