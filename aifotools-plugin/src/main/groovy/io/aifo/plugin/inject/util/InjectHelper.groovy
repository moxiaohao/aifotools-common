package io.aifo.plugin.inject.util


import javassist.CtMethod
import javassist.CtNewMethod
import javassist.bytecode.AnnotationsAttribute
import javassist.bytecode.FieldInfo

import java.lang.annotation.Annotation

public class InjectHelper {

    final static String InjectAnnotation = "io.aifo.api.javassist.Inject"

    static def ON_CREATE = ['onCreate', "onActivityCreated"] as String[]
    static def ON_DESTROY = 'onDestroy'

    static def Activity_OnCreate = "\n" +
            "    protected void onCreate(Bundle savedInstanceState) {\n" +
            "        super.onCreate(savedInstanceState);\n";

    static def Fragment_OnCreate = "public void onActivityCreated(Bundle savedInstanceState) {\n" +
            "        super.onActivityCreated(savedInstanceState);"

    static def Pre_Switch_Str = "public void call(Message msg) {\n" +
            "switch (msg.what){\n"

    static def Pre_OnDestroy = "  \n" +
            "    protected void onDestroy() {\n" +
            "        super.onDestroy();\n";

    /**
     * 处理BusInfo
     * @param mBusInfo
     * @param path
     */
    static void initInject(io.aifo.plugin.inject.InjectInfo injectInfo, String path) {

        if (injectInfo.clazz.isFrozen()) injectInfo.clazz.defrost()     //解冻

        if (injectInfo.constructMethod != null) {//有被BusRegister注解的方法
            injectInfo.BusRegisterMethod.insertAfter(getInjectFieldStr(injectInfo));

        } else if (injectInfo.getOnCreateMethod() == null) {//没有OnCreateMethod，创建并加上新代码

            String m = getInjectFieldStr();

            injectInfo.project.logger.quiet m

            CtMethod mInitEventMethod = CtNewMethod.make(m, injectInfo.clazz);

            injectInfo.clazz.addMethod(mInitEventMethod)

        } else {//有OnCreateMethod，直接插入新代码

            injectInfo.project.logger.quiet "OnCreateMethod not null"

            injectInfo.constructMethod.insertAfter(getRegisterEventMethodStr(injectInfo));
        }
        injectInfo.clazz.writeFile(path)
    }

    /**
     * 获取初始化OkBus方法的代码
     * @param mBusInfo 事件信息
     * @return
     */
    static String getInjectFieldStr(io.aifo.plugin.inject.InjectInfo injectInfo) {

        String CreateStr = "";

//      injectInfo.clazz.addInterface(injectInfo.clazz.classPool.get("com.base.event.Event"));

        //为当前的类添加时间处理的接口
        for (int i = 0; i < injectInfo.getFields().size(); i++) {

            FieldInfo fieldInfo = injectInfo.getFields().get(i).getFieldInfo();

            Annotation mAnnotation = injectInfo.getAnnotations().get(i)

            AnnotationsAttribute attribute = fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
            //获取注解属性
            javassist.bytecode.annotation.Annotation annotation = attribute.getAnnotation(mAnnotation.annotationType().canonicalName);
            //获取注解
//          int id = ((IntegerMemberValue) annotation.getMemberValue("value")).getValue();//获取注解的值
            CreateStr += fieldInfo.name + " =  InstanceFactory.create(fieldInfo.getMetaClass().class);\n"
        }
        return CreateStr;

    }

}
