package net.tropicraft.blocks.tileentities;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.tropicraft.blocks.BlockLemonSqueezer;

@SideOnly(Side.CLIENT)
public class TileEntityLemonSqueezerRenderer extends TileEntitySpecialRenderer {
	private ModelLemonSqueezer modelSqueezer = new ModelLemonSqueezer();

	@Override
	public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float partialTicks) {
		TileEntityLemonSqueezer squeezer = (TileEntityLemonSqueezer)var1;
        this.bindTextureByName("/tropicalmod/lemonsqueezer.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x+0.5f,(float)y+1.5f,(float)z+0.5f);
		GL11.glRotatef(180f, 1f, 0f, 1f);
		modelSqueezer.fruitType = squeezer.fruitType;
		modelSqueezer.renderFruit = squeezer.fruitType != null && squeezer.ticks < TileEntityLemonSqueezer.ticksToSqueeze;
		modelSqueezer.renderJuice = squeezer.fruitType != null && squeezer.ticks == TileEntityLemonSqueezer.ticksToSqueeze;
		modelSqueezer.ticks = squeezer.ticks;
		modelSqueezer.renderLemonSqueezer();
		GL11.glPopMatrix();
	}
}
