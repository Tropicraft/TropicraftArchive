package tropicraft.client.entities;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;

import org.lwjgl.opengl.GL11;

import tropicraft.ModInfo;
import tropicraft.blocks.TropicraftBlocks;

public class RenderTCItemFrame extends Render {

	private final RenderBlocks renderBlocksInstance = new RenderBlocks();

	public void func_82404_a(EntityItemFrame par1EntityItemFrame, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		float var10 = (float)(par1EntityItemFrame.posX - par2) - 0.5F;
		float var11 = (float)(par1EntityItemFrame.posY - par4) - 0.5F;
		float var12 = (float)(par1EntityItemFrame.posZ - par6) - 0.5F;
		int var13 = par1EntityItemFrame.xPosition + Direction.offsetX[par1EntityItemFrame.hangingDirection];
		int var14 = par1EntityItemFrame.yPosition;
		int var15 = par1EntityItemFrame.zPosition + Direction.offsetZ[par1EntityItemFrame.hangingDirection];
		GL11.glTranslatef((float)var13 - var10, (float)var14 - var11, (float)var15 - var12);
		this.renderFrameItemAsBlock(par1EntityItemFrame);
		this.func_82402_b(par1EntityItemFrame);
		GL11.glPopMatrix();
	}

	/**
	 * Render the item frame's item as a block.
	 */
	 private void renderFrameItemAsBlock(EntityItemFrame par1EntityItemFrame)
	 {
		 //RenderItemFrame
		 GL11.glPushMatrix();
		 //GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderManager.renderEngine.getTexture("/mods/TropicraftMod/textures/blocks/bamboobundle.png"));
		 GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderManager.renderEngine.getTexture("/terrain.png"));
		 GL11.glRotatef(par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
		 Block var2 = TropicraftBlocks.bambooBundle;
		 float var3 = 0.0625F;
		 float var4 = 0.75F;
		 float var5 = var4 / 2.0F;
		 GL11.glPushMatrix();
		 this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5 + 0.0625F), (double)(0.5F - var5 + 0.0625F), (double)(var3 * 0.5F), (double)(0.5F + var5 - 0.0625F), (double)(0.5F + var5 - 0.0625F));
		 this.renderBlocksInstance.setOverrideBlockTexture(renderBlocksInstance.getBlockIcon(TropicraftBlocks.bambooBundle));
		 this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		 this.renderBlocksInstance.clearOverrideBlockTexture();
		 this.renderBlocksInstance.unlockBlockBounds();
		 GL11.glPopMatrix();
		 this.renderBlocksInstance.setOverrideBlockTexture(renderBlocksInstance.getBlockIcon(TropicraftBlocks.bambooBundle));
		 GL11.glPushMatrix();
		 this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(var3 + 0.5F - var5), (double)(0.5F + var5));
		 this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		 GL11.glPopMatrix();
		 GL11.glPushMatrix();
		 this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F + var5 - var3), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(0.5F + var5), (double)(0.5F + var5));
		 this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		 GL11.glPopMatrix();
		 GL11.glPushMatrix();
		 this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)var3, (double)(0.5F + var5), (double)(var3 + 0.5F - var5));
		 this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		 GL11.glPopMatrix();
		 GL11.glPushMatrix();
		 this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F + var5 - var3), (double)var3, (double)(0.5F + var5), (double)(0.5F + var5));
		 this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
		 GL11.glPopMatrix();
		 this.renderBlocksInstance.unlockBlockBounds();
		 this.renderBlocksInstance.clearOverrideBlockTexture();
		 GL11.glPopMatrix();
	 }

	 private void func_82402_b(EntityItemFrame par1EntityItemFrame)
	    {
	        ItemStack itemstack = par1EntityItemFrame.getDisplayedItem();

	        if (itemstack != null)
	        {
	            EntityItem entityitem = new EntityItem(par1EntityItemFrame.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
	            entityitem.getEntityItem().stackSize = 1;
	            entityitem.hoverStart = 0.0F;
	            GL11.glPushMatrix();
	            GL11.glTranslatef(-0.453125F * (float)Direction.offsetX[par1EntityItemFrame.hangingDirection], -0.18F, -0.453125F * (float)Direction.offsetZ[par1EntityItemFrame.hangingDirection]);
	            GL11.glRotatef(180.0F + par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef((float)(-90 * par1EntityItemFrame.getRotation()), 0.0F, 0.0F, 1.0F);

	            switch (par1EntityItemFrame.getRotation())
	            {
	                case 1:
	                    GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
	                    break;
	                case 2:
	                    GL11.glTranslatef(0.0F, -0.32F, 0.0F);
	                    break;
	                case 3:
	                    GL11.glTranslatef(0.16F, -0.16F, 0.0F);
	            }

	            if (entityitem.getEntityItem().getItem() == Item.map)
	            {
	                this.renderManager.renderEngine.bindTexture("/misc/mapbg.png");
	                Tessellator tessellator = Tessellator.instance;
	                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
	                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
	                GL11.glScalef(0.00390625F, 0.00390625F, 0.00390625F);
	                GL11.glTranslatef(-65.0F, -107.0F, -3.0F);
	                GL11.glNormal3f(0.0F, 0.0F, -1.0F);
	                tessellator.startDrawingQuads();
	                byte b0 = 7;
	                tessellator.addVertexWithUV((double)(0 - b0), (double)(128 + b0), 0.0D, 0.0D, 1.0D);
	                tessellator.addVertexWithUV((double)(128 + b0), (double)(128 + b0), 0.0D, 1.0D, 1.0D);
	                tessellator.addVertexWithUV((double)(128 + b0), (double)(0 - b0), 0.0D, 1.0D, 0.0D);
	                tessellator.addVertexWithUV((double)(0 - b0), (double)(0 - b0), 0.0D, 0.0D, 0.0D);
	                tessellator.draw();
	                MapData mapdata = Item.map.getMapData(entityitem.getEntityItem(), par1EntityItemFrame.worldObj);
	                GL11.glTranslatef(0.0F, 0.0F, -1.0F);

	                if (mapdata != null)
	                {
	                    this.renderManager.itemRenderer.mapItemRenderer.renderMap((EntityPlayer)null, this.renderManager.renderEngine, mapdata);
	                }
	            }
	            else
	            {
	                TextureCompass texturecompass;

	                if (entityitem.getEntityItem().getItem() == Item.compass)
	                {
	                    texturecompass = TextureCompass.compassTexture;
	                    double d0 = texturecompass.currentAngle;
	                    double d1 = texturecompass.angleDelta;
	                    texturecompass.currentAngle = 0.0D;
	                    texturecompass.angleDelta = 0.0D;
	                    texturecompass.updateCompass(par1EntityItemFrame.worldObj, par1EntityItemFrame.posX, par1EntityItemFrame.posZ, (double)MathHelper.wrapAngleTo180_float((float)(180 + par1EntityItemFrame.hangingDirection * 90)), false, true);
	                    texturecompass.currentAngle = d0;
	                    texturecompass.angleDelta = d1;
	                }

	                RenderItem.renderInFrame = true;
	                RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
	                RenderItem.renderInFrame = false;

	                if (entityitem.getEntityItem().getItem() == Item.compass)
	                {
	                    texturecompass = TextureCompass.compassTexture;
	                    texturecompass.updateAnimation();
	                }
	            }

	            GL11.glPopMatrix();
	        }
	    }

	 /**
	  * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	  * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	  * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	  * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	  */
	 public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	 {
		 this.func_82404_a((EntityItemFrame)par1Entity, par2, par4, par6, par8, par9);
	 }
}
