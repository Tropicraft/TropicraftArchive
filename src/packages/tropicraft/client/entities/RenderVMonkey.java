package tropicraft.client.entities;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import tropicraft.TropicraftUtils;
import tropicraft.client.entities.models.ModelVMonkey;
import tropicraft.entities.passive.land.VMonkey;

public class RenderVMonkey extends RenderLiving {
    
    protected ModelVMonkey modelVMonkey;

    public RenderVMonkey(ModelVMonkey modelbase, float f) {
        super(modelbase, f);
        modelVMonkey = modelbase;
    }

    public void func_177_a(VMonkey entity, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving(entity, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving((VMonkey) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        doRenderLiving((VMonkey) entity, d, d1, d2, f, f1);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
        ItemStack itemstack = entityliving.getHeldItem();
        if (itemstack != null) {
            GL11.glPushMatrix();
            modelVMonkey.lLegLower.postRender(.045F);
            GL11.glTranslatef(-0.0625F, .4375F, .4625F);
            if (itemstack.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())) {
                float f1 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f1 *= 0.75F;
                GL11.glRotatef(50F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, f1);
            } else if (Item.itemsList[itemstack.itemID].isFull3D()) {
                float f2 = 0.625F;
                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f2, -f2, f2);
                GL11.glRotatef(-100F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 0.0F, 0.0F);
            } else {
                float f3 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1F, -0.20F);
                GL11.glScalef(f3, f3, f3);
                GL11.glRotatef(35F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-105F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(35F, 0.0F, 0.0F, 1.0F);
            }
            renderManager.itemRenderer.renderItem(entityliving, itemstack, 0);
            GL11.glPopMatrix();
        }
    }

	@Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TropicraftUtils.bindTextureEntity("monkeytext");
	}
}
