package com.jiangli.bytecode.asm;

import org.objectweb.asm.*;

public class AddTimeClassAdapter extends ClassAdapter {
    private String owner;
    private boolean isInterface;

    public AddTimeClassAdapter(ClassVisitor cv) {
        super(cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (!name.equals("<init>") && !isInterface && mv != null) {
            //为方法添加计时功能
            mv = new AddTimeMethodAdapter(mv);
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        //添加字段
        if (!isInterface) {
            FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "timer", "J", null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }

    class AddTimeMethodAdapter extends MethodAdapter {
        public AddTimeMethodAdapter(MethodVisitor mv) {
            super(mv);
        }

        /**
         * visitCode方法，它会在ASM开始访问某一个方法的Code区时被调用，重写visitCode方法，将AOP中的前置逻辑就放在这里。
         */
        @Override
        public void visitCode() {
            mv.visitCode();
            mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
            mv.visitInsn(Opcodes.LSUB);
            mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
        }

        /**
         * 每当ASM访问到无参数指令时，都会调用MyMethodVisitor中的visitInsn方法。我们判断了当前指令是否为无参数的“return”指令，如果是就在它的前面添加一些指令，也就是将AOP的后置逻辑放在该方法中。
         * @param opcode
         */
        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
                mv.visitInsn(Opcodes.LADD);
                mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
            }
            mv.visitInsn(opcode);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocal) {
            mv.visitMaxs(maxStack + 4, maxLocal);
        }
    }

}