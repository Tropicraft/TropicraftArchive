package net.tropicraft.asm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import net.minecraft.client.model.ModelBiped;
import net.tropicraft.mods.TropicraftMod;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IClassTransformer;

public class ModelBipedTransformer implements IClassTransformer {
	private HashMap<String, String> obfStrings = new HashMap<String, String>();
	private HashMap<String, String> mcpStrings = new HashMap<String, String>();
	
	private HashMap<String, String> strings = new HashMap<String, String>();
	
	public ModelBipedTransformer(){
		putMappings("className", "aww", "net.minecraft.client.model.ModelBiped");
		putMappings("setRotationAnglesName", "a", "setRotationAngles");
		putMappings("setRotationAnglesDesc", "(FFFFFFLlq;)V", "(FFFFFFLnet/minecraft/entity/Entity;)V");
		
		putMappings("methodDescTropiSet", "(FFFFFFLlq;Laww;)Z", "(FFFFFFLnet/minecraft/entity/Entity;Lnet/minecraft/client/model/ModelBiped;)Z");
	}
	@Override
	public byte[] transform(String name, byte[] bytes) {
		String mcp = mcpStrings.get("className");
        if(name.equals(mcp))
        {
        	strings = mcpStrings;
            return transformModelBiped(bytes, mcpStrings);
        }
		String obf = obfStrings.get("className");
		if(name.equals(obf))
        {
			strings = obfStrings;
            return transformModelBiped(bytes, obfStrings);
        }
		return bytes;
	}
	private byte[] transformModelBiped(byte[] bytes,
			HashMap<String, String> map) {
		TropicraftMod.debugOut(getClass(), "Found class, starting sexy TropiTransform!");
		
		ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        Iterator<MethodNode> methods = classNode.methods.iterator();
        while (methods.hasNext())
        {
        	MethodNode m = methods.next();
        	if(m.name.equals(map.get("setRotationAnglesName")) && m.desc.equals(map.get("setRotationAnglesDesc")))
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
                
               
                toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/tropicraft/mods/CoreModMethods", "setRotationAngles", map.get("methodDescTropiSet")));
               
                toInject.add(new JumpInsnNode(Opcodes.IFEQ, lmm1Node));
                toInject.add(jumpLabel);
                toInject.add(new InsnNode(Opcodes.RETURN));
                toInject.add(lmm1Node);
                
              
              
                
                m.instructions.insert(m.instructions.get(0), toInject);
                break;
            }
        }
        
        
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);		
        classNode.accept(cw);
        
        return cw.toByteArray();
	}
	private void putMappings(String givenName, String obfName, String mcpName)
	{
		obfStrings.put(givenName, obfName);
		mcpStrings.put(givenName, mcpName);
	}
	
	

}
