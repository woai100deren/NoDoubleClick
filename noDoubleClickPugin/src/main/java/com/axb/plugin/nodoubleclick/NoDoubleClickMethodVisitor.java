package com.axb.plugin.nodoubleclick;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import java.util.List;

public class NoDoubleClickMethodVisitor extends MethodVisitor {
    private String methodName;
    private List<String> lambdaMethods;
    private boolean needAddCode = false;
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
//        Log.e("NoDoubleClickMethodVisitor","11111111111111111111="+methodName+",needAddCode = "+needAddCode);
        //if("onClick".equals(methodName) || methodName.startsWith("lambda$"))
        //放弃对Lambda语法的支持，因为无法判断Lambda是用在什么方法中，不能判断出来是在onClick事件中
        if(("onClick".equals(methodName) || ("onItemClick".equals(methodName))) && needAddCode){
            addCode();
        }

        if(lambdaMethods.contains(methodName) && needAddCode){
            addCode();
        }

        needAddCode = false;
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
//        Log.e("NoDoubleClickMethodVisitor","222222222222222="+name);
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

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
//        Log.e("NoDoubleClickMethodVisitor","333333333333="+descriptor);
        //匹配了注解的方法才加上防双击
        if("Lcom/axb/plugin/nodoubleclick/lib/annotation/NoDoubleClick;".equals(descriptor)){
            needAddCode = true;
        }
        return super.visitAnnotation(descriptor, visible);
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
