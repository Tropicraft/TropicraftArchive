/**
 * 
 */
package tropicraft.client.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tropicraft.entities.passive.water.StarfishType;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StarfishItemRenderer implements IItemRenderer {
	private Minecraft minecraft = FMLClientHandler.instance().getClient();

	public StarfishItemRenderer() {
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch(helper) {
		case ENTITY_ROTATION:
			return false;
		case ENTITY_BOBBING:
			return true;
		case EQUIPPED_BLOCK:
			return false;
		case BLOCK_3D:
			return false;
		case INVENTORY_BLOCK:
			return false;
		default:
			return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		int dmg = item.getItemDamage();
		
		if (dmg > StarfishType.values().length) {
			dmg = 0;
		}
		
		Tessellator tessellator = Tessellator.instance;
		StarfishType starfishType = StarfishType.values()[dmg];

		switch (type) {
		case INVENTORY:
			renderStarfishInInventory(starfishType);
			break;
		case EQUIPPED:
			renderEquippedStarfish(starfishType);
			break;
		case ENTITY:
			renderStarfishEntity(starfishType);
			break;
		default:
			// this should never happen
		}
	}
	
    private void renderStarfishEntity(StarfishType starfishType) {
		float f = 0f;
		float f1 = 1f;
		float f2 = 0f;
		float f3 = 1f;
		float f1shifted = 1;
		float f3shifted = 1;
		
		Tessellator tessellator = Tessellator.instance;

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();

		if (RenderItem.renderInFrame) {
			GL11.glTranslatef(0.5f, -0.25f, 0.0f);
            GL11.glRotatef(-180.0F, 0.0F, 1.0F, 0.0F);
		} else {
			GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		}

		for (int i = 0; i < starfishType.getLayerCount(); i++) {
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(starfishType.getTexturePaths().get(i)));
			popper(tessellator, f1, f2, f, f3, f1shifted, f3shifted, starfishType.getLayerHeights()[i]);
			GL11.glTranslatef(0f, 0f, -starfishType.getLayerHeights()[i]);
		}

		GL11.glPopMatrix();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
	}

	private void renderEquippedStarfish(StarfishType starfishType) {
		float f = 0f;
		float f1 = 1f;
		float f2 = 0f;
		float f3 = 1f;
		float f1shifted = 1;
		float f3shifted = 1;
		
		Tessellator tessellator = Tessellator.instance;

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		for (int i = 0; i < starfishType.getLayerCount(); i++) {
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(starfishType.getTexturePaths().get(i)));
			popper(tessellator, f1, f2, f, f3, f1shifted, f3shifted, starfishType.getLayerHeights()[i]);
			GL11.glTranslatef(0f, 0f, -starfishType.getLayerHeights()[i]);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}

	private void renderStarfishInInventory(StarfishType starfishType) {
		GL11.glDisable(GL11.GL_LIGHTING);
		
		Tessellator tessellator = Tessellator.instance;
		
		for (int i = 0; i < starfishType.getLayerCount(); i++) {
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(starfishType.getTexturePaths().get(i)));

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0, 16, 0.0, 0.0, 1.0);
			tessellator.addVertexWithUV(16.0, 16.0, 0.0, 1.0, 1.0);
			tessellator.addVertexWithUV(16.0, 0.0, 0.0, 1.0, 0.0);
			tessellator.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
			tessellator.draw();
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		
	}

	private void popper(Tessellator tessellator, float f, float f1, float f2, float f3, float f1shifted, float f3shifted, float layerHeight) {
        float f4 = 1.0F;
        float f5 = layerHeight;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);

        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f, f3shifted);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0D, f2, f3shifted);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0D, f2, f1shifted);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f, f1shifted);

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - f5, f, f1);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0F - f5, f2, f1);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0F - f5, f2, f3);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - f5, f, f3);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        for (int i = 0; i < 32; i++) {
            float f6 = (float) i / 32F;
            float f10 = (f + (f2 - f) * f6) - 0.001953125F;
            float f14 = f4 * f6;
            tessellator.addVertexWithUV(f14, 0.0D, 0.0F - f5, f10, f3);
            tessellator.addVertexWithUV(f14, 0.0D, 0.0D, f10, f3);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0D, f10, f1);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0F - f5, f10, f1);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        for (int j = 0; j < 32; j++) {
            float f7 = (float) j / 32F;
            float f11 = (f + (f2 - f) * f7) - 0.001953125F;
            float f15 = f4 * f7 + 0.03125F;
            tessellator.addVertexWithUV(f15, 1.0D, 0.0F - f5, f11, f1);
            tessellator.addVertexWithUV(f15, 1.0D, 0.0D, f11, f1);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0D, f11, f3);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0F - f5, f11, f3);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        for (int k = 0; k < 32; k++) {
            float f8 = (float) k / 32F;
            float f12 = (f3 + (f1 - f3) * f8) - 0.001953125F;
            float f16 = f4 * f8 + 0.03125F;
            tessellator.addVertexWithUV(0.0D, f16, 0.0D, f, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0D, f2, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0F - f5, f2, f12);
            tessellator.addVertexWithUV(0.0D, f16, 0.0F - f5, f, f12);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        for (int l = 0; l < 32; l++) {
            float f9 = (float) l / 32F;
            float f13 = (f3 + (f1 - f3) * f9) - 0.001953125F;
            float f17 = f4 * f9;
            tessellator.addVertexWithUV(f4, f17, 0.0D, f2, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0D, f, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0F - f5, f, f13);
            tessellator.addVertexWithUV(f4, f17, 0.0F - f5, f2, f13);
        }

        tessellator.draw();
    }

}
