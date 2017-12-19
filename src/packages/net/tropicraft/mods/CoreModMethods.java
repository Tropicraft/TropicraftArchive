package net.tropicraft.mods;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.tropicraft.entities.EntityBeachFloat;

public class CoreModMethods {

	public static void checkFeilds() {
		RenderPlayer p = (RenderPlayer) (RenderManager.instance.entityRenderMap
				.get(EntityPlayer.class));
		try {
		//	System.out.println(p.modelBipedMain);
		} catch (Exception e) {

		}
	}

	public static boolean setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity entity, ModelBiped p) {

		if (entity.ridingEntity instanceof EntityBeachFloat) {
			p.bipedHead.rotateAngleY = par4 / (180F / (float)Math.PI);
			if(p.bipedHead.rotateAngleY >= (float)Math.PI/2){
				p.bipedHead.rotateAngleY = (float)Math.PI/2;
			}
			if(p.bipedHead.rotateAngleY <= -(float)Math.PI/2){
				p.bipedHead.rotateAngleY = -(float)Math.PI/2;
			}
			//System.out.println(p.bipedHead.rotateAngleY);
	        p.bipedHead.rotateAngleX = par5 / (180F / (float)Math.PI);
	        p.bipedHeadwear.rotateAngleY = p.bipedHead.rotateAngleY;
	        p.bipedHeadwear.rotateAngleX = p.bipedHead.rotateAngleX;
	        p.bipedRightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
	        p.bipedLeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
	        p.bipedRightArm.rotateAngleZ = 0.0F;
	        p.bipedLeftArm.rotateAngleZ = 0.0F;
	        p.bipedRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
	        p.bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
	        p.bipedRightLeg.rotateAngleY = 0.0F;
	        p.bipedLeftLeg.rotateAngleY = 0.0F;

	        

	        if (p.heldItemLeft != 0)
	        {
	            p.bipedLeftArm.rotateAngleX = p.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)p.heldItemLeft;
	        }

	        if (p.heldItemRight != 0)
	        {
	            p.bipedRightArm.rotateAngleX = p.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)p.heldItemRight;
	        }

	        p.bipedRightArm.rotateAngleY = 0.0F;
	        p.bipedLeftArm.rotateAngleY = 0.0F;
	        float var8;
	        float var9;

	        if (p.onGround > -9990.0F)
	        {
	            var8 = p.onGround;
	            p.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(var8) * (float)Math.PI * 2.0F) * 0.2F;
	            p.bipedRightArm.rotationPointZ = MathHelper.sin(p.bipedBody.rotateAngleY) * 5.0F;
	            p.bipedRightArm.rotationPointX = -MathHelper.cos(p.bipedBody.rotateAngleY) * 5.0F;
	            p.bipedLeftArm.rotationPointZ = -MathHelper.sin(p.bipedBody.rotateAngleY) * 5.0F;
	            p.bipedLeftArm.rotationPointX = MathHelper.cos(p.bipedBody.rotateAngleY) * 5.0F;
	            p.bipedRightArm.rotateAngleY += p.bipedBody.rotateAngleY;
	            p.bipedLeftArm.rotateAngleY += p.bipedBody.rotateAngleY;
	            p.bipedLeftArm.rotateAngleX += p.bipedBody.rotateAngleY;
	            var8 = 1.0F - p.onGround;
	            var8 *= var8;
	            var8 *= var8;
	            var8 = 1.0F - var8;
	            var9 = MathHelper.sin(var8 * (float)Math.PI);
	            float var10 = MathHelper.sin(p.onGround * (float)Math.PI) * -(p.bipedHead.rotateAngleX - 0.7F) * 0.75F;
	            p.bipedRightArm.rotateAngleX = (float)((double)p.bipedRightArm.rotateAngleX - ((double)var9 * 1.2D + (double)var10));
	            p.bipedRightArm.rotateAngleY += p.bipedBody.rotateAngleY * 2.0F;
	            p.bipedRightArm.rotateAngleZ = MathHelper.sin(p.onGround * (float)Math.PI) * -0.4F;
	        }

	        if (p.isSneak)
	        {
	            p.bipedBody.rotateAngleX = 0.5F;
	            p.bipedRightArm.rotateAngleX += 0.4F;
	            p.bipedLeftArm.rotateAngleX += 0.4F;
	            p.bipedRightLeg.rotationPointZ = 4.0F;
	            p.bipedLeftLeg.rotationPointZ = 4.0F;
	            p.bipedRightLeg.rotationPointY = 9.0F;
	            p.bipedLeftLeg.rotationPointY = 9.0F;
	            p.bipedHead.rotationPointY = 1.0F;
	            p.bipedHeadwear.rotationPointY = 1.0F;
	        }
	        else
	        {
	            p.bipedBody.rotateAngleX = 0.0F;
	            p.bipedRightLeg.rotationPointZ = 0.1F;
	            p.bipedLeftLeg.rotationPointZ = 0.1F;
	            p.bipedRightLeg.rotationPointY = 12.0F;
	            p.bipedLeftLeg.rotationPointY = 12.0F;
	            p.bipedHead.rotationPointY = 0.0F;
	            p.bipedHeadwear.rotationPointY = 0.0F;
	        }

	        p.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
	        p.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
	        p.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
	        p.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;

	        if (p.aimedBow)
	        {
	            var8 = 0.0F;
	            var9 = 0.0F;
	            p.bipedRightArm.rotateAngleZ = 0.0F;
	            p.bipedLeftArm.rotateAngleZ = 0.0F;
	            p.bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F) + p.bipedHead.rotateAngleY;
	            p.bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F + p.bipedHead.rotateAngleY + 0.4F;
	            p.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + p.bipedHead.rotateAngleX;
	            p.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + p.bipedHead.rotateAngleX;
	            p.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
	            p.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
	            p.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
	            p.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
	            p.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
	            p.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
	        }
	        return true;
		}
		return false;
	}

}
