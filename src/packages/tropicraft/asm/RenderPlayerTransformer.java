package tropicraft.asm;

import java.util.HashMap;

import net.minecraft.client.model.ModelBiped;

import java.util.Iterator;
import java.util.logging.Level;

import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.Label;
import org.objectweb.asm.ClassReader;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IClassTransformer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.objectweb.asm.Opcodes;
@SideOnly(Side.CLIENT)
public class RenderPlayerTransformer implements IClassTransformer {
	
	private HashMap<String, String> obfStrings = new HashMap<String, String>();
	private HashMap<String, String> mcpStrings = new HashMap<String, String>();
	
	private HashMap<String, String> strings = new HashMap<String, String>();
	
	public RenderPlayerTransformer()
	{
		putMappings("className", "bht", "net.minecraft.client.renderer.entity.RenderPlayer");
		putMappings("renderSpecialsName", "func_77100_a", "renderSpecials");
		putMappings("renderSpecialsDesc", "(Lqx;F)V", "(Lnet/minecraft/entity/player/EntityPlayer;F)V");
		putMappings("renderSpecialsPointName", "func_85093_e", "renderArrowsStuckInEntity");
		putMappings("renderSpecialsPointDesc", "(Lmd;F)V", "(Lnet/minecraft/entity/EntityLiving;F)V");
		putMappings("methodDescTropiRenderSpecials", "(Lqx;F)Z", "(Lnet/minecraft/entity/player/EntityPlayer;F)Z");
		
		putMappings("rotatePlayerName", "func_77102_a", "rotatePlayer");
		putMappings("rotatePlayerDesc", "(Lqx;FFF)V", "(Lnet/minecraft/entity/player/EntityPlayer;FFF)V");
		putMappings("methodDescTropiRotatePlayer", "(Lqx;)V", "(Lnet/minecraft/entity/player/EntityPlayer;)V");
		
				
		putMappings("modelBipedMainName", "field_77071_a", "modelBipedMain");
		putMappings("modelArmorChestplateName", "field_77108_b", "modelArmorChestplate");
		putMappings("modelArmorName", "field_77111_i", "modelArmor");
		
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		String mcp = mcpStrings.get("className");
        if(name.equals(mcp))
        {
        	strings = mcpStrings;
            return transformRenderPlayer(bytes, mcpStrings);
        }
		String obf = obfStrings.get("className");
		if(name.equals(obf))
        {
			strings = obfStrings;
            return transformRenderPlayer(bytes, obfStrings);
        }
		return bytes;
	}
	
	private byte[] transformRenderPlayer(byte[] bytes, HashMap<String, String> map)
	{
		System.out.println("Found class, starting sexy TropiTransform!");
		
		ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        Iterator<MethodNode> methods = classNode.methods.iterator();
        this.transformFeilds(bytes, map, classNode);
        while (methods.hasNext())
        {
            MethodNode m = methods.next();
            if(isName(m.name, "renderSpecialsName") && isName(m.name, "renderSpecialsDesc"))
            {
            	for (int index = 0; index < m.instructions.size(); index++)
                {
            		if(m.instructions.get(index).getType() == AbstractInsnNode.METHOD_INSN)
            		{
            			MethodInsnNode mdNode = (MethodInsnNode) m.instructions.get(index);
            			if(isName(mdNode.name, "renderSpecialsPointName") && isName(mdNode.name, "renderSpecialsPointDesc"))
            			{            				
            				LabelNode lmm1Node = new LabelNode(new Label());
                            
            				LabelNode jumpLabel = new LabelNode(new Label());
            				
                            // make new instruction list
                            InsnList toInject = new InsnList();
                            
                            toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            toInject.add(new VarInsnNode(Opcodes.FLOAD, 2));
                            toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "tropicraft/CoreModMethods", "renderSpecials", map.get("methodDescTropiRenderSpecials")));
                            toInject.add(new JumpInsnNode(Opcodes.IFNE, lmm1Node));
                            toInject.add(jumpLabel);
                            toInject.add(new InsnNode(Opcodes.RETURN));
                            toInject.add(lmm1Node);
                            
                            m.instructions.insert(m.instructions.get(index), toInject);
                            break;
            			}
            		}
                }
            }
            
            if(isName(m.name, "rotatePlayerName") && isName(m.desc, "rotatePlayerDesc"))
            {
				LabelNode lmm1Node = new LabelNode(new Label());
				
                // make new instruction list
                InsnList toInject = new InsnList();
                
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "tropicraft/CoreModMethods", "rotatePlayer", map.get("methodDescTropiRotatePlayer")));
                toInject.add(lmm1Node);
                
                m.instructions.insert(m.instructions.get(0), toInject);
                break;
            }
        }
        
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);		
        classNode.accept(cw);
        
        return cw.toByteArray();
	}
	
	private void transformFeilds(byte[] bytes,
			HashMap<String, String> map, ClassNode classNode) {
		
//		TropicraftMod.debugOut(getClass(), "Found class, starting sexy TropiTransform!");
////		
////		ClassNode classNode = new ClassNode();
////        ClassReader classReader = new ClassReader(bytes);
////        classReader.accept(classNode, 0);
        
        Iterator<FieldNode> fields = classNode.fields.iterator();
        while (fields.hasNext())
        {
        	 FieldNode f = fields.next();
        	
        	 if(isName(f.name, "modelBipedMainName") || isName(f.name, "modelArmorChestplateName") || isName(f.name, "modelArmorName")) {
        		 FMLLog.log(Level.FINEST, "Attempting to set " + f.name + " to public");
        		 f.access = Opcodes.ACC_PUBLIC;
        	 }
        	
        }     
	}
	
	private boolean isName(String toCheck, String key) {
		return this.mcpStrings.get(key).equals(toCheck) || this.obfStrings.get(key).equals(toCheck);
	}
	
	private void putMappings(String givenName, String obfName, String mcpName)
	{
		obfStrings.put(givenName, obfName);
		mcpStrings.put(givenName, mcpName);
	}
	
}
