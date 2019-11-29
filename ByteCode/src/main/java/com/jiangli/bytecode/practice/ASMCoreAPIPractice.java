package com.jiangli.bytecode.practice;

import com.jiangli.bytecode.ByteDto;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jiangli
 * @date 2019/11/26 10:14
 */
public class ASMCoreAPIPractice {
    public static void main(String[] args) {
        try {
            ClassReader classReader = new ClassReader("com.jiangli.bytecode.ByteDto");
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            classReader.accept(new ClassAdapter(classWriter) {
                @Override
                public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
                    super.visit(i, i1, s, s1, s2, strings);
                }

                @Override
                public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
                    MethodVisitor ret = super.visitMethod(i, s, s1, s2, strings);

                    System.out.println("s:"+s);
                    if (s.equals("run")) {
                        //MethodVisitor xx = classWriter.visitMethod(i, s, s1, s2, strings);

                        return new MethodAdapter(ret) {


                            //，它会在ASM开始访问某一个方法的Code区时被调用，重写visitCode方法，将AOP中的前置逻辑就放在这里。
                            @Override
                            public void visitCode() {
                                //这里注释掉也没事
                                //super.visitCode();
                                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
                                mv.visitVarInsn(Opcodes.LSTORE, 1);

                                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                mv.visitLdcInsn("start2");
                                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
                            }

                            //。通过调用methodVisitor的visitXXXXInsn()方法就可以实现字节码的插入，XXXX对应相应的操作码助记符类型
                            @Override
                            public void visitInsn(int opcode) {
                                if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)
                                        || opcode == Opcodes.ATHROW) {

                                    //类名 字段名/方法名 描述符
                                    //方法在返回之前，打印"end"
                                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                    mv.visitLdcInsn("end2");//比如mv.visitLdcInsn(“end”)对应的操作码就是ldc “end”，即将字符串“end”压入栈。
                                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

                                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
                                    mv.visitVarInsn(Opcodes.LLOAD, 1);
                                    mv.visitInsn(Opcodes.LSUB);
                                    mv.visitVarInsn(Opcodes.LSTORE, 3);
                                    mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                                    mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
                                    mv.visitInsn(Opcodes.DUP);
                                    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V");
                                    mv.visitLdcInsn("cost:");
                                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
                                    mv.visitVarInsn(Opcodes.LLOAD, 3);
                                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;");
                                    mv.visitLdcInsn("ms");
                                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
                                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
                                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
                                }
                                mv.visitInsn(opcode);
                            }
                        };
                    }
                    return ret;
                }

                //
            }, ClassReader.SKIP_DEBUG);

            byte[] bytes = classWriter.toByteArray();
            File out = new File(ClassLoader.getSystemResource("com/jiangli/bytecode/ByteDto.class").toURI());
            System.out.println(out);
            FileOutputStream fout = new FileOutputStream(out);
            fout.write(bytes);
            fout.close();
            System.out.println("now generator cc success!!!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteDto byteDto = new ByteDto();
        byteDto.run();
    }

}
