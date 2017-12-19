package tropicraft.client.entities;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import tropicraft.ModInfo;
import tropicraft.entities.items.WallMask;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWallMask extends Render {

	private MaskRenderer renderer;

	public RenderWallMask() {
		renderer = new MaskRenderer();
	}

	@Override
	public void updateIcons(IconRegister par1IconRegister) {
		
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1) {
		//System.out.println("hurr");
		renderWallShell((WallMask)entity, d0, d1, d2, f, f1);
	}

	private void loadTypeTexture(int type) {
		loadTexture(ModInfo.ICONLOCATION + TropicraftItems.getShellImageNames()[type]);
	}

	private void renderWallShell(WallMask entity, double x, double y, double z, float f, float f1) {
		int type = entity.getShellType();
		//	loadTypeTexture(type);
		loadTexture("/mods/TropicraftMod/textures/entities/mask.png");

		GL11.glPushMatrix();

		GL11.glTranslatef((float) x -.03125F, (float) y, (float) z);
		GL11.glTranslatef(0, -.3125F, 0);
		GL11.glTranslatef(0, 0, -.03125F );
        GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);

        GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
        
		lightingHelper(entity, 1.0F, 1.0F);
		//GL11.glColor3f(1.0F, 1.0F, 1.0F);

		//   this.renderBlocksInstance.setOverrideBlockTexture(shells[type]);
		// this.renderBlocksInstance.renderBlockAsItem(, 0, 1.0F);
		renderer.renderMask(entity.getShellType());
		//    this.renderBlocksInstance.clearOverrideBlockTexture();


		GL11.glPopMatrix();		
	}

	private void lightingHelper(WallMask wallShell, float f, float f1) {
		int i = MathHelper.floor_double(wallShell.posX);
		int j = MathHelper.floor_double(wallShell.posY + (double) (f1 / 16F));
		int k = MathHelper.floor_double(wallShell.posZ);
		if (wallShell.hangingDirection == 0) {
			i = MathHelper.floor_double(wallShell.posX - (double) (f / 16F));
			
		}
		if (wallShell.hangingDirection == 1) {
			k = MathHelper.floor_double(wallShell.posZ - (double) (f / 16F));
			//System.out.println("1");
		}
		if (wallShell.hangingDirection == 2) {
			i = MathHelper.floor_double(wallShell.posX + (double) (f / 16F));
			//System.out.println("2");
		}
		if (wallShell.hangingDirection == 3) {
			k = MathHelper.floor_double(wallShell.posZ + (double) (f / 16F));
			//System.out.println("3");
		}
		int l = renderManager.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int i1 = l % 0x10000;
		int j1 = l / 0x10000;
		int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int var8 = var7 % 65536;
		int var9 = var7 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var8, (float) var9);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}
}
