package com.axb.plugin.nodoubleclick;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class NoDoubleClickClassVisitor extends ClassVisitor {
    private List<String> lambdaMethods = new ArrayList<>();
    public NoDoubleClickClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM7,cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        return new NoDoubleClickMethodVisitor(methodVisitor,name,lambdaMethods);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }
}
