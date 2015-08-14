package lain.mods.skinport.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ASMTransformer implements IClassTransformer
{

    class transformer001 extends ClassVisitor
    {

        class method001 extends MethodVisitor
        {

            public method001(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.RETURN)
                {
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/asm/Hooks", "RenderManager_postRenderManagerInit", "(Lnet/minecraft/client/renderer/entity/RenderManager;)V", false);
                }
                super.visitInsn(opcode);
            }

        }

        class method002 extends MethodVisitor
        {

            public method002(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.ARETURN)
                {
                    this.visitVarInsn(Opcodes.ASTORE, 2);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ALOAD, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 2);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/asm/Hooks", "RenderManager_getEntityRenderObject", "(Lnet/minecraft/client/renderer/entity/RenderManager;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/entity/Render;)Lnet/minecraft/client/renderer/entity/Render;", false);
                }
                super.visitInsn(opcode);
            }

        }

        ObfHelper m001 = ObfHelper.newMethod("<init>", "net/minecraft/client/renderer/entity/RenderManager", "()V");
        ObfHelper m002 = ObfHelper.newMethod("func_78713_a", "net/minecraft/client/renderer/entity/RenderManager", "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/client/renderer/entity/Render;").setDevName("getEntityRenderObject");

        public transformer001(ClassVisitor cv)
        {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (m001.match(name, desc))
                return new method001(super.visitMethod(access, name, desc, signature, exceptions));
            if (m002.match(name, desc))
                return new method002(super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    class transformer002 extends ClassVisitor
    {

        class method001 extends MethodVisitor
        {

            public method001(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode)
            {
                if (opcode == Opcodes.RETURN)
                {
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitFieldInsn(Opcodes.GETFIELD, f001.getData(0), f001.getData(1), f001.getData(2));
                    this.visitVarInsn(Opcodes.ASTORE, 1);
                    this.visitVarInsn(Opcodes.ALOAD, 0);
                    this.visitVarInsn(Opcodes.ALOAD, 1);
                    this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/asm/Hooks", "GuiOptions_postInitGui", "(Lnet/minecraft/client/gui/GuiOptions;Ljava/util/List;)V", false);
                }
                super.visitInsn(opcode);
            }

        }

        class method002 extends MethodVisitor
        {

            public method002(MethodVisitor mv)
            {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitCode()
            {
                super.visitCode();

                this.visitVarInsn(Opcodes.ALOAD, 0);
                this.visitVarInsn(Opcodes.ALOAD, 1);
                this.visitMethodInsn(Opcodes.INVOKESTATIC, "lain/mods/skinport/asm/Hooks", "GuiOptions_preActionPerformed", "(Lnet/minecraft/client/gui/GuiOptions;Lnet/minecraft/client/gui/GuiButton;)V", false);
            }

        }

        ObfHelper m001 = ObfHelper.newMethod("func_73866_w_", "net/minecraft/client/gui/GuiOptions", "()V").setDevName("initGui");
        ObfHelper m002 = ObfHelper.newMethod("func_146284_a", "net/minecraft/client/gui/GuiOptions", "(Lnet/minecraft/client/gui/GuiButton;)V").setDevName("actionPerformed");

        ObfHelper f001 = ObfHelper.newField("field_146292_n", "net/minecraft/client/gui/GuiScreen", "Ljava/util/List;").setDevName("buttonList");

        public transformer002(ClassVisitor cv)
        {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            if (m001.match(name, desc))
                return new method001(super.visitMethod(access, name, desc, signature, exceptions));
            if (m002.match(name, desc))
                return new method002(super.visitMethod(access, name, desc, signature, exceptions));
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        if ("net.minecraft.client.renderer.entity.RenderManager".equals(transformedName))
            return transform001(bytes);
        if ("net.minecraft.client.gui.GuiOptions".equals(transformedName))
            return transform002(bytes);
        return bytes;
    }

    private byte[] transform001(byte[] bytes)
    {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(new transformer001(classWriter), ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private byte[] transform002(byte[] bytes)
    {
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classReader.accept(new transformer002(classWriter), ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

}
