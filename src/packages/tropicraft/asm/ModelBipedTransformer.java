package tropicraft.asm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class ModelBipedTransformer implements IClassTransformer {
	
	private final String CLASS_NAME = "net.minecraft.client.model.ModelBiped";
	private final String OBF_CLASS_NAME = "bbj";
	private final String POINT_NAME = "setRotationAngles";
	private final String POINT_NAME_M = "func_78087_a";
	private final String POINT_DESC = "(FFFFFFLnet/minecraft/entity/Entity;)V";
	private final String TROPI_DESC = "(FFFFFFLnet/minecraft/entity/Entity;Lnet/minecraft/client/model/ModelBiped;)Z";
	
	public ModelBipedTransformer(){
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
        if(FMLLaunchHandler.side() == Side.CLIENT && name.equals(CLASS_NAME) || name.equals(OBF_CLASS_NAME))
        {
            return transformModelBiped(bytes);
        }
		return bytes;
	}
	
	private byte[] transformModelBiped(byte[] bytes) {
		System.out.println("Found class, starting sexy TropiTransform on ModelBiped!");
		
		ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        Iterator<MethodNode> methods = classNode.methods.iterator();
        while (methods.hasNext())
        {
        	MethodNode m = methods.next();
        	if((m.name.equals(this.POINT_NAME) || m.name.equals(this.POINT_NAME_M)) && m.desc.equals(this.POINT_DESC))
            {
        		FMLLog.log(Level.FINEST, "Attempting to set preform magic on " + m.name);
        		LabelNode lmm1Node = new LabelNode(new Label());
        		
        		LabelNode jumpLabel = new LabelNode(new Label());
				
                // make new instruction list
                InsnList toInject = new InsnList();
               
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 1));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 2));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 3));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 4));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 5));
                toInject.add(new VarInsnNode(Opcodes.FLOAD, 6));
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 7));
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
               // toInject.add(new FieldInsnNode(Opcodes.PUTFIELD, "net/minecraft/client/model/ModelBiped", "herp", "net/minecraft/client/model/ModelBiped"));
                
                toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "tropicraft/CoreModMethods", "setRotationAngles", this.TROPI_DESC));
               
                toInject.add(new JumpInsnNode(Opcodes.IFEQ, lmm1Node));
                toInject.add(jumpLabel);
                toInject.add(new InsnNode(Opcodes.RETURN));
                toInject.add(lmm1Node);
                
                m.instructions.insert(m.instructions.get(0), toInject);
        		System.out.println("TropiTransform completed.");
                break;
            }
        }
        
        
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);		
        classNode.accept(cw);
        
        return cw.toByteArray();
	}
}
