package net.tropicraft.blocks.tileentities;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.tropicraft.entities.renderers.MaskRenderer;
import net.tropicraft.mods.TropicraftMod;

import org.lwjgl.opengl.GL11;

public class TileEntityWallShellRender extends TileEntitySpecialRenderer {
	
	MaskRenderer renderer = new MaskRenderer();
    static String[] image = {"/tropicalmod/commonshell1.png", "/tropicalmod/shell2.png",
        "/tropicalmod/shell6.png", "/tropicalmod/shell3.png", "/tropicalmod/shell4.png"};
	
	public int getTexture(int i) {
        //System.out.println(i);

        if (i == TropicraftMod.shellCommon1.shiftedIndex) {
            return TropicraftMod.shellCommon1.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellCommon3.shiftedIndex) {
            return TropicraftMod.shellCommon3.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellCommon2.shiftedIndex) {
            return TropicraftMod.shellCommon2.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellRare1.shiftedIndex) {
            return TropicraftMod.shellRare1.getIconFromDamage(0);
        }
        if (i == TropicraftMod.turtleShell.shiftedIndex) {
            return TropicraftMod.turtleShell.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellStarfish.shiftedIndex) {
            return TropicraftMod.shellStarfish.getIconFromDamage(0);
        } else {
            return TropicraftMod.shellCommon1.getIconFromDamage(0);
        }
    }
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var4,
			double var6, float var8) {
		TileEntityWallShell west = (TileEntityWallShell)var1;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)var2, (float)var4, (float)var6);
        
        
        GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
        int meta = west.getBlockMetadata();
        
        if(meta == 0)
        {
            GL11.glTranslatef(1F, 0.0F, -0.25F); 
        	GL11.glRotatef(-90, 0.0F, 1.0F, 0.0F);
        }
        if(meta == 1)
        {
            GL11.glTranslatef(0.75F, 0.0F, -1F); 
        	GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);
        }
        if(meta == 2)
        {
            GL11.glTranslatef(0.0F, 0.0F, -.75F); 
        	GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
        }
        if(meta == 3)
        {
            GL11.glTranslatef(.25F, 0.0F, 0.0F); 
        	GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
        }
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.bindTextureByName("/tropicalmod/tropiitems.png");
        renderer.renderItem(getTexture(west.type));
        GL11.glPopMatrix();
		
	}

}
