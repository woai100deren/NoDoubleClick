package com.axb.plugin.nodoubleclick;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class NoDoubleClickMethodVisitor extends MethodVisitor {
    private String methodName;
    private List<String> lambdaMethods;
    public NoDoubleClickMethodVisitor(MethodVisitor mv,String methodName,List<String> lambdaMethods) {
        super(Opcodes.ASM7,mv);
        this.methodName = methodName;
        this.lambdaMethods = lambdaMethods;
    }

    /**
     * 开始访问方法代码，此处可以添加方法运行前拦截器;
     */
    @Override
    public void visitCode() {
        super.visitCode();
        //if("onClick".equals(methodName) || methodName.startsWith("lambda$"))
        //放弃对Lambda语法的支持，因为无法判断Lambda是用在什么方法中，不能判断出来是在onClick事件中
        if("onClick".equals(methodName) || ("onItemClick".equals(methodName))){
            addCode();
        }

        if(lambdaMethods.contains(methodName)){
            addCode();
        }
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
        for (Object object : bootstrapMethodArguments) {
            //以下是对lambda写法的支持
            //对view的click事件
            if("onClick".equals(name)
                    && "()Landroid/view/View$OnClickListener;".equals(descriptor)
                    && object instanceof Handle) {
                Handle handle = (Handle)object;
                lambdaMethods.add(handle.getName());
            }
            //对listView的item点击事件
            if("onItemClick".equals(name)
                    && "()Landroid/widget/AdapterView$OnItemClickListener;".equals(descriptor)
                    && object instanceof Handle) {
                Handle handle = (Handle)object;
                lambdaMethods.add(handle.getName());
            }
        }
    }

    private void addCode(){
        //以下代码相当于写成：
        /*
        if(!NoDoubleClickUtil.isOnDoubleClick()){
            //之前onClick代码里面的逻辑搬进来。
        }
        如果mv.visitJumpInsn(Opcodes.IFEQ, label);改成mv.visitJumpInsn(Opcodes.IFNE, label);
        则变为：
        if(NoDoubleClickUtil.isOnDoubleClick()){
            //之前onClick代码里面的逻辑搬进来。
        }
        */
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,"com/axb/plugin/nodoubleclick/lib/NoDoubleClickUtil","isOnDoubleClick","()Z",false);
        Label label = new Label();
        mv.visitJumpInsn(Opcodes.IFEQ, label);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitLabel(label);
    }
}
