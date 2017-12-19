package tropicraft.asm;

import java.util.Iterator;
import java.util.logging.Level;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
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
public class RenderPlayerTransformer implements IClassTransformer {
	
	private final String CLASS_NAME = "net.minecraft.client.renderer.entity.RenderPlayer";
	private final String OBF_CLASS_NAME = "bhj";
	private final String RENSP_NAME = "renderSpecials";
	private final String RENSP_NAME_M = "func_77100_a";
	private final String RENSP_NOTCH = "a";
	private final String RENSP_DESC = "(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V";
	private final String OBF_RENSP_DESC = "(Lbeu;F)V";
	private final String RSPOI_NAME = "renderArrowsStuckInEntity";
	private final String RSPOI_NAME_M = "func_85093_e";	
	private final String RSPOI_NOTCH = "e";
	private final String RSTRO_DESC = "(Lnet/minecraft/client/entity/AbstractClientPlayer;F)Z";
	
	private final String ROTPL_NAME = "rotatePlayer";
	private final String ROTPL_NAME_M = "func_77102_a";
	private final String ROTPL_DESC = "(Lnet/minecraft/client/entity/AbstractClientPlayer;FFF)V";
	private final String OBF_ROTPL_DESC = "(Lbeu;FFF)V";
	private final String RPTRO_DESC = "(Lnet/minecraft/client/entity/AbstractClientPlayer;)V";
	
	private final String MBMAI_NAME = "modelBipedMain";
	private final String MBCHE_NAME = "modelArmorChestplate";
	private final String MBARM_NAME = "modelArmor";
	private final String MBMAI_NAME_M = "field_77109_a";
	private final String MBCHE_NAME_M = "field_77108_b";
	private final String MBARM_NAME_M = "field_77111_i";
	
	public RenderPlayerTransformer()
	{
		System.out.println("Welcome to RenderPlayerTransformer.");
	}
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		if(FMLLaunchHandler.side() == Side.CLIENT && name.equals(this.CLASS_NAME) || name.equals(OBF_CLASS_NAME)) {			
			return this.transformRenderPlayer(name, bytes);
		}
		return bytes;
	}
	
	private byte[] transformRenderPlayer(String name, byte[] bytes)
	{
		System.out.println("Found class, starting sexy TropiTransform on RenderPlayer!");
		
		ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        
        Iterator<MethodNode> methods = classNode.methods.iterator();
        this.transformFields(bytes, classNode);
        while (methods.hasNext())
        {
            MethodNode m = methods.next();
        //	System.out.printf("HERP HELLO %s %s %s %s or %s\n", m.name, RENSP_NOTCH, m.desc, OBF_RENSP_DESC, RENSP_DESC);
            if((m.name.equals(this.RENSP_NAME) || m.name.equals(this.RENSP_NAME_M) || m.name.equals(this.RENSP_NOTCH)) 
            		&& (m.desc.equals(this.RENSP_DESC) || m.desc.equals(OBF_RENSP_DESC)))
            {
            //    System.out.printf("SHAZBUTTTTTTTTTTT: %s\n", m.name);
            	for (int index = 0; index < m.instructions.size(); index++)
                {
            		if(m.instructions.get(index).getType() == AbstractInsnNode.METHOD_INSN)
            		{
            			MethodInsnNode mdNode = (MethodInsnNode) m.instructions.get(index);
            			if(mdNode.name.equals(this.RSPOI_NAME) || mdNode.name.equals(this.RSPOI_NAME_M) || mdNode.name.equals(this.RSPOI_NOTCH))
            			{            				
            				LabelNode lmm1Node = new LabelNode(new Label());
                            
            				LabelNode jumpLabel = new LabelNode(new Label());
            				
                            // make new instruction list
                            InsnList toInject = new InsnList();
                            
                            toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            toInject.add(new VarInsnNode(Opcodes.FLOAD, 2));
                            
                            toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "tropicraft/CoreModMethods", "renderSpecials", this.RSTRO_DESC));
                            
                            toInject.add(new JumpInsnNode(Opcodes.IFNE, lmm1Node));
                            toInject.add(jumpLabel);
                            toInject.add(new InsnNode(Opcodes.RETURN));
                            toInject.add(lmm1Node);
                            
                            m.instructions.insert(m.instructions.get(index), toInject);
                    		System.out.println("TropiTransform completed 2.");
                            break;
            			}
            		}
                }
            }
            
            if((m.name.equals(this.ROTPL_NAME) || m.name.equals(this.ROTPL_NAME_M)) && (m.desc.equals(this.ROTPL_DESC) || m.desc.equals(OBF_ROTPL_DESC)))
            {
				LabelNode lmm1Node = new LabelNode(new Label());
				
                // make new instruction list
                InsnList toInject = new InsnList();
                
                toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "tropicraft/CoreModMethods", "rotatePlayer", this.RPTRO_DESC));
                toInject.add(lmm1Node);
                
                m.instructions.insert(m.instructions.get(0), toInject);
        		System.out.println("TropiTransform completed 3.");
                break;
            }
        }
        
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);		
        classNode.accept(cw);
        
        return cw.toByteArray();
	}
	
	private void transformFields(byte[] bytes, ClassNode classNode) {
		
//		TropicraftMod.debugOut(getClass(), "Found class, starting sexy TropiTransform!");
////		
////		ClassNode classNode = new ClassNode();
////        ClassReader classReader = new ClassReader(bytes);
////        classReader.accept(classNode, 0);
        
        Iterator<FieldNode> fields = classNode.fields.iterator();
        while (fields.hasNext())
        {
        	 FieldNode f = fields.next();
        
        	 if(f.name.equals(this.MBMAI_NAME) || f.name.equals(this.MBCHE_NAME) || f.name.equals(this.MBARM_NAME) || f.name.equals(this.MBMAI_NAME_M) || f.name.equals(this.MBCHE_NAME_M) || f.name.equals(this.MBARM_NAME_M)) {
        		 FMLLog.log(Level.FINEST, "Attempting to set " + f.name + " to public");
        		 f.access = Opcodes.ACC_PUBLIC;
         		System.out.println("TropiTransform completed 1.");
        	 }
        	
        }     
	}
	
}
