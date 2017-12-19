package net.tropicraft.entities.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityKoaManly;
import net.tropicraft.entities.models.ModelKoaMan;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import CoroAI.PFQueue;

public class RenderKoaMan extends RenderLiving
{

	int counter;

	public RenderKoaMan(ModelKoaMan modelbase, float f)
	{
		super(modelbase, f);
		mainModel = (ModelKoaMan)modelbase;
		shadowSize = f;
		counter = 300;
	}

	public void setRenderPassModel(ModelKoaMan modelbase)
	{
		renderPassModel = modelbase;
	}

	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
			float f, float f1)
	{
		GL11.glPushMatrix();
		GL11.glDisable(2884 /*GL_CULL_FACE*/);
		mainModel.onGround = renderSwingProgress(entityliving, f1);
		if(renderPassModel != null)
		{
			renderPassModel.onGround = mainModel.onGround;
		}
		mainModel.isRiding = entityliving.isRiding();
		if(renderPassModel != null)
		{
			renderPassModel.isRiding = mainModel.isRiding;
		}
		try
		{
			float f2 = entityliving.prevRenderYawOffset + (entityliving.renderYawOffset - entityliving.prevRenderYawOffset) * f1;
			float f3 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f1;
			float f4 = entityliving.prevRotationPitch + (entityliving.rotationPitch - entityliving.prevRotationPitch) * f1;
			renderLivingAt(entityliving, d, d1, d2);
			float f5 = handleRotationFloat(entityliving, f1);
			rotateCorpse(entityliving, f5, f2, f1);
			float f6 = 0.0625F;
			GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
			GL11.glScalef(-1F, -1F, 1.0F);
			preRenderCallback(entityliving, f1);
			GL11.glTranslatef(0.0F, -24F * f6 - 0.0078125F, 0.0F);
			float f7 = entityliving.prevLegYaw + (entityliving.legYaw - entityliving.prevLegYaw) * f1;
			float f8 = entityliving.legSwing - entityliving.legYaw * (1.0F - f1);
			if(f7 > 1.0F)
			{
				f7 = 1.0F;
			}
			loadDownloadableImageTexture(entityliving.skinUrl, entityliving.getTexture());
			GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
			mainModel.setLivingAnimations(entityliving, f8, f7, f1);
			mainModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
			for(int i = 0; i < 4; i++)
			{
				if(shouldRenderPass(entityliving, i, f1) == 1)
				{
					renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
					GL11.glDisable(3042 /*GL_BLEND*/);
					GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
				}
			}

			renderEquippedItems(entityliving, f1);
			float f9 = entityliving.getBrightness(f1);
			int j = getColorMultiplier(entityliving, f9, f1);
			GL13.glClientActiveTexture(33985 /*GL_TEXTURE1_ARB*/);
			GL13.glActiveTexture(33985 /*GL_TEXTURE1_ARB*/);
			GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
			GL13.glClientActiveTexture(33984 /*GL_TEXTURE0_ARB*/);
			GL13.glActiveTexture(33984 /*GL_TEXTURE0_ARB*/);
			if((j >> 24 & 0xff) > 0 || entityliving.hurtTime > 0 || entityliving.deathTime > 0)
			{
				GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
				GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
				GL11.glEnable(3042 /*GL_BLEND*/);
				GL11.glBlendFunc(770, 771);
				GL11.glDepthFunc(514);
				if(entityliving.hurtTime > 0 || entityliving.deathTime > 0)
				{
					GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
					mainModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
					for(int k = 0; k < 4; k++)
					{
						if(inheritRenderPass(entityliving, k, f1) == 1)
						{
							GL11.glColor4f(f9, 0.0F, 0.0F, 0.4F);
							renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
						}
					}

				}
				if((j >> 24 & 0xff) > 0)
				{
					float f10 = (float)(j >> 16 & 0xff) / 255F;
					float f11 = (float)(j >> 8 & 0xff) / 255F;
					float f12 = (float)(j & 0xff) / 255F;
					float f13 = (float)(j >> 24 & 0xff) / 255F;
					GL11.glColor4f(f10, f11, f12, f13);
					mainModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
					for(int l = 0; l < 4; l++)
					{
						if(inheritRenderPass(entityliving, l, f1) == 1)
						{
							GL11.glColor4f(f10, f11, f12, f13);
							renderPassModel.render(entityliving, f8, f7, f5, f3 - f2, f4, f6);
						}
					}

				}
				GL11.glDepthFunc(515);
				GL11.glDisable(3042 /*GL_BLEND*/);
				GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
				GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
			}
			GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		GL13.glClientActiveTexture(33985 /*GL_TEXTURE1_ARB*/);
		GL13.glActiveTexture(33985 /*GL_TEXTURE1_ARB*/);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL13.glClientActiveTexture(33984 /*GL_TEXTURE0_ARB*/);
		GL13.glActiveTexture(33984 /*GL_TEXTURE0_ARB*/);
		GL11.glEnable(2884 /*GL_CULL_FACE*/);
		GL11.glPopMatrix();
		passSpecialRender(entityliving, d, d1, d2);

		//if trading
		if (true) {
			//renderImage(entityliving, d, d1, d2, f, f1, "/misc/shadow.png");		//dat code
			//renderShadow(entityliving, d, d1, d2, f, f1);
		}

		if (PFQueue.renderLine) {
			//c_CoroAIUtil.renderPFLines(entityliving, d, d1, d2, f, f1);
		}
	}

	protected void renderLivingAt(EntityLiving entityliving, double d, double d1, double d2)
	{
		GL11.glTranslatef((float)d, (float)d1, (float)d2);
	}

	protected void rotateCorpse(EntityLiving entityliving, float f, float f1, float f2)
	{
		GL11.glRotatef(180F - f1, 0.0F, 1.0F, 0.0F);
		if(entityliving.deathTime > 0)
		{
			float f3 = ((((float)entityliving.deathTime + f2) - 1.0F) / 20F) * 1.6F;
			f3 = MathHelper.sqrt_float(f3);
			if(f3 > 1.0F)
			{
				f3 = 1.0F;
			}
			GL11.glRotatef(f3 * getDeathMaxRotation(entityliving), 0.0F, 0.0F, 1.0F);
		}
	}

	protected float renderSwingProgress(EntityLiving entityliving, float f)
	{
		return entityliving.getSwingProgress(f);
	}

	protected float handleRotationFloat(EntityLiving entityliving, float f)
	{
		return (float)entityliving.ticksExisted + f;
	}

	protected void renderEquippedItems(EntityLiving entityliving, float f)
	{
		super.renderEquippedItems(entityliving, f);
		ItemStack itemstack = entityliving.getHeldItem();
		if(itemstack != null)
		{
			GL11.glPushMatrix();
			mainModel.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if(itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
			{
				float f1 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f1 *= 0.75F;
				GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f1, -f1, f1);
			} else
				if(itemstack.itemID == Item.bow.shiftedIndex)
				{
					float f2 = 0.625F;
					GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
					GL11.glRotatef(-20F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(f2, -f2, f2);
					GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
				} else
					if(Item.itemsList[itemstack.itemID].isFull3D())
					{
						float f3 = 0.625F;
						GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
						GL11.glScalef(f3, -f3, f3);
						GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
					} else
					{
						float f4 = 0.375F;
						GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
						GL11.glScalef(f4, f4, f4);
						GL11.glRotatef(60F, 0.0F, 0.0F, 1.0F);
						GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
					}
			renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
			GL11.glPopMatrix();
		}
	}

	protected int inheritRenderPass(EntityLiving entityliving, int i, float f)
	{
		return shouldRenderPass(entityliving, i, f);
	}

	protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
	{
		return -1;
	}

	protected float getDeathMaxRotation(EntityLiving entityliving)
	{
		return 90F;
	}

	protected int getColorMultiplier(EntityLiving entityliving, float f, float f1)
	{
		return 0;
	}

	protected void preRenderCallback(EntityLiving entityliving, float f)
	{
	}

	protected void renderLivingLabel(EntityLiving entityliving, String s, double d, double d1, double d2, int i)
	{
		float f = entityliving.getDistanceToEntity(renderManager.livingPlayer);
		if(f > (float)i)
		{
			return;
		}
		FontRenderer fontrenderer = getFontRendererFromRenderManager();
		float f1 = 1.6F;
		float f2 = 0.01666667F * f1;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.3F, (float)d2);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f2, -f2, f2);
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDepthMask(false);
		//GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		Tessellator tessellator = Tessellator.instance;
		byte byte0 = 0;
		if(s != null && s.equals("deadmau5"))
		{
			byte0 = -10;
		}
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		tessellator.startDrawingQuads();
		int j = fontrenderer.getStringWidth(s) / 2;
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tessellator.addVertex(-j - 1, -1 + byte0, 0.0D);
		tessellator.addVertex(-j - 1, 8 + byte0, 0.0D);
		tessellator.addVertex(j + 1, 8 + byte0, 0.0D);
		tessellator.addVertex(j + 1, -1 + byte0, 0.0D);
		tessellator.draw();
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
		//GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	protected void passSpecialRender(EntityLiving entityliving, double d, double d1, double d2)
	{
		if(!Minecraft.isDebugInfoEnabled());

		if (entityliving instanceof EntityKoaManly) {
			renderName((EntityKoaManly)entityliving, d, d1, d2);
		}
		//c_CoroAIUtil.renderJobs((c_EnhAI)entityliving);
	}


	protected void renderName(EntityKoaManly entitykoa, double d, double d1, double d2)
	{
		//	System.out.println("counter: " + counter);

		if(Minecraft.isGuiEnabled() && entitykoa != renderManager.livingPlayer)
		{
			float f = 1.6F;
			float f1 = 0.01666667F * f;
			float f2 = entitykoa.getDistanceToEntity(renderManager.livingPlayer);
			float f3 = entitykoa.isSneaking() ? 32F : 64F;
			String s = entitykoa.name;
			//s = entitykoa.debugInfo;
			
			if(f2 < f3)
			{


				if(!entitykoa.isSneaking())
				{
					if(entitykoa.isPlayerSleeping())
					{
						renderLivingLabel(entitykoa, s, d, d1 - 1.5D, d2, 24);
					} else
					{	
						//put all talk logic code in here

					}
					renderLivingLabel(entitykoa, s, d, d1, d2, 24);

				} else
				{
					FontRenderer fontrenderer = getFontRendererFromRenderManager();
					GL11.glPushMatrix();
					GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.3F, (float)d2);
					GL11.glNormal3f(0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
					GL11.glScalef(-f1, -f1, f1);
					GL11.glDisable(2896 /*GL_LIGHTING*/);
					GL11.glTranslatef(0.0F, 0.25F / f1, 0.0F);
					GL11.glDepthMask(false);
					GL11.glEnable(3042 /*GL_BLEND*/);
					GL11.glBlendFunc(770, 771);
					Tessellator tessellator = Tessellator.instance;
					GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
					tessellator.startDrawingQuads();
					int i = fontrenderer.getStringWidth(s) / 2;
					tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
					tessellator.addVertex(-i - 1, -1D, 0.0D);
					tessellator.addVertex(-i - 1, 8D, 0.0D);
					tessellator.addVertex(i + 1, 8D, 0.0D);
					tessellator.addVertex(i + 1, -1D, 0.0D);
					tessellator.draw();
					GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
					GL11.glDepthMask(true);
					fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 0x20ffffff);
					GL11.glEnable(2896 /*GL_LIGHTING*/);
					GL11.glDisable(3042 /*GL_BLEND*/);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glPopMatrix();
				}
			}
		}
	}

	public void doRender(Entity entity, double d, double d1, double d2, 
			float f, float f1)
	{
		doRenderLiving((EntityLiving)entity, d, d1, d2, f, f1);
	}

	private World getWorldFromRenderManager() {
		return this.renderManager.worldObj;
	}

	private void renderShadow(Entity entity, double d, double d1, double d2, 
			float f, float f1)
	{
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		RenderEngine renderengine = renderManager.renderEngine;
		renderengine.bindTexture(renderengine.getTexture("%clamp%/misc/hmm.png"));
		World world = getWorldFromRenderManager();
		GL11.glDepthMask(false);
		float f2 = shadowSize;
		if(entity instanceof EntityLiving)
		{
			f2 *= ((EntityLiving)entity).getRenderSizeModifier();
		}

		//old positioning code
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f1;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f1 + (double)entity.getShadowSize();
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f1;

		double dist = 1D;
		float look = 1F;

		//new positioning code
		//double d3 = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f1) + ((double)(-Math.sin((entity.rotationYaw+look) / 180.0F * 3.1415927F) * Math.cos(entity.rotationPitch / 180.0F * 3.1415927F)) * dist);
		//double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f1 + (double)entity.getShadowSize();
		//double d5 = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f1) + ((double)(Math.cos((entity.rotationYaw+look) / 180.0F * 3.1415927F) * Math.cos(entity.rotationPitch / 180.0F * 3.1415927F)) * dist);

		int i = MathHelper.floor_double(d3 - (double)f2);
		int j = MathHelper.floor_double(d3 + (double)f2);
		int k = MathHelper.floor_double(d4 - (double)f2);
		int l = MathHelper.floor_double(d4);
		int i1 = MathHelper.floor_double(d5 - (double)f2);
		int j1 = MathHelper.floor_double(d5 + (double)f2);
		double d6 = d - d3;
		double d7 = d1 - d4;
		double d8 = d2 - d5;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		for(int k1 = i; k1 <= j; k1++)
		{
			for(int l1 = k; l1 <= l; l1++)
			{
				for(int i2 = i1; i2 <= j1; i2++)
				{
					int j2 = world.getBlockId(k1, l1 - 1, i2);
					if(j2 > 0 && world.getBlockLightValue(k1, l1, i2) > 3)
					{
						renderShadowOnBlock(Block.blocksList[j2], d, d1 + (double)entity.getShadowSize(), d2, k1, l1, i2, f, f2, d6, d7 + (double)entity.getShadowSize(), d8);
					}
				}

			}

		}

		tessellator.draw();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glDepthMask(true);
	}

	private void renderShadowOnBlock(Block block, double d, double d1, double d2, 
			int i, int j, int k, float f, float f1, double d3, 
			double d4, double d5)
	{
		Tessellator tessellator = Tessellator.instance;
		if(!block.renderAsNormalBlock())
		{
			return;
		}
		double d6 = ((double)f - (d1 - ((double)j + d4)) / 2D) * 0.5D * (double)getWorldFromRenderManager().getLightBrightness(i, j, k);
		if(d6 < 0.0D)
		{
			return;
		}
		if(d6 > 1.0D)
		{
			d6 = 1.0D;
		}
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float)d6);
		double d7 = (double)i + block.getBlockBoundsMinX() + d3;
		double d8 = (double)i + block.getBlockBoundsMaxX() + d3;
		double d9 = (double)j + block.getBlockBoundsMinY() + d4 + 0.015625D;
		double d10 = (double)k + block.getBlockBoundsMinZ() + d5;
		double d11 = (double)k + block.getBlockBoundsMaxZ() + d5;
		float f2 = (float)((d - d7) / 2D / (double)f1 + 0.5D);
		float f3 = (float)((d - d8) / 2D / (double)f1 + 0.5D);
		float f4 = (float)((d2 - d10) / 2D / (double)f1 + 0.5D);
		float f5 = (float)((d2 - d11) / 2D / (double)f1 + 0.5D);
		tessellator.addVertexWithUV(d7, d9, d10, f2, f4);
		tessellator.addVertexWithUV(d7, d9, d11, f2, f5);
		tessellator.addVertexWithUV(d8, d9, d11, f3, f5);
		tessellator.addVertexWithUV(d8, d9, d10, f3, f4);
	}

	protected ModelKoaMan mainModel;
	protected ModelKoaMan renderPassModel;
}
