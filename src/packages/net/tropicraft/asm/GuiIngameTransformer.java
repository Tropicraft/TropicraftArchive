package net.tropicraft.asm;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IClassTransformer;

public class GuiIngameTransformer implements IClassTransformer {
	private String obfClassName = "atr";
	private String mcpClassName = "net.minecraft.client.gui.GuiIngame";
	
	private String obfMethodName = "a";
	private String mcpMethodName = "renderGameOverlay";
	
	private String obfMethodDesc = "(FZII)V";
	private String mcpMethodDesc = "(FZII)V";
	
	private String obfPointOwner = "ban";
	private String mcpPointOwner = "net/minecraft/client/renderer/EntityRenderer";
	
	private String obfPointName = "c";
	private String mcpPointName = "setupOverlayRendering";
	
	private String obfPointDesc = "()V";
	private String mcpPointDesc = "()V";
	
	@Override
	public byte[] transform(String name, byte[] bytes) {
		if (name.equals(mcpClassName)) {
			return transformClass(true, bytes);
		} else if (name.equals(obfClassName)) {
			return transformClass(false, bytes);
		}
		return bytes;
	}
	
	private byte[] transformClass(boolean mcp, byte[] bytes) {
		ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        Iterator<MethodNode> methods = classNode.methods.iterator();
        
        while (methods.hasNext())
        {
            MethodNode m = methods.next();
            
            if(m.name.equals(mcp?mcpMethodName:obfMethodName) &&
            		m.desc.equals(mcp?mcpMethodDesc:obfMethodDesc)) {
            	transformMethod(mcp, m);
            	break;
            }
        }
        
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);		
        classNode.accept(cw);
        
        return cw.toByteArray();
	}

	protected void transformMethod(boolean mcp, MethodNode method) {
		for (int index = 0; index < method.instructions.size(); index++) {
    		if(method.instructions.get(index).getType() == AbstractInsnNode.METHOD_INSN) {
    			MethodInsnNode mdNode = (MethodInsnNode) method.instructions.get(index);    			
    			
    			if (mdNode.owner.equals(mcp?mcpPointOwner:obfPointOwner) &&
    					mdNode.name.equals(mcp?mcpPointName:obfPointName) &&
    					mdNode.desc.equals(mcp?mcpPointDesc:obfPointDesc)) {
    				InsnList toInject = new InsnList();

    				toInject.add(new VarInsnNode(Opcodes.FLOAD, 1));
    				toInject.add(new VarInsnNode(Opcodes.ILOAD, 2));
    				toInject.add(new VarInsnNode(Opcodes.ILOAD, 3));
    				toInject.add(new VarInsnNode(Opcodes.ILOAD, 4));
    				toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/tropicraft/mods/TropicraftMod", "renderGameOverlay", "(FZII)V"));

    				method.instructions.insert(method.instructions.get(index), toInject);
    				break;
				}
    		}
		}
	}
}
