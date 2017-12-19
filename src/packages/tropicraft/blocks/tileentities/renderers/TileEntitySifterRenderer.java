package tropicraft.blocks.tileentities.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import tropicraft.blocks.tileentities.TileEntitySifter;

public class TileEntitySifterRenderer extends TileEntitySpecialRenderer
{
	/**
	 * Entity to be rendered, usually EntityItem (sand)
	 */
	private Entity entity;

	public TileEntitySifterRenderer()
	{
	}

	public void renderTileEntitySifter(TileEntitySifter tileentitysifter, double d, double d1, double d2, float f)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);

		if(entity == null && tileentitysifter.getIsSifting())
		{
			entity = (EntityItem)(new EntityItem(tileentitysifter.getWorldObj()));
			((EntityItem)entity).setEntityItemStack(new ItemStack(Block.sand, 1));
		}

		if(entity != null) 
		{
			entity.setWorld(tileentitysifter.getWorldObj()); 	//allows entity to be placed into world
			//f1=size of object inside spawner
			float f1 = 0.4375F;						
			GL11.glTranslatef(0.0F, 0.7F, 0.0F);		//height of thing inside spawner
			GL11.glScalef(f1*3, f1*3, f1*3);			//size of thing inside spawner, scaled    
			GL11.glRotatef((float)(tileentitysifter.yaw2 + (tileentitysifter.yaw - tileentitysifter.yaw2) * (double)f) * 10.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.4F, 0.0F);		//other height of thing inside spawner

			entity.setLocationAndAngles(d, d1, d2, 0.0F, 0.0F);
			if(tileentitysifter.getIsSifting())
				RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0.0F, f);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, 
			float f)
	{
		renderTileEntitySifter((TileEntitySifter)tileentity, d, d1, d2, f);
	}

}