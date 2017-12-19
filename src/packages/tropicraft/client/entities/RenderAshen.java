package tropicraft.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import tropicraft.TropicraftUtils;
import tropicraft.client.entities.models.ModelAshen;
import tropicraft.entities.hostile.land.tribes.ashen.Ashen;
import tropicraft.entities.hostile.land.tribes.ashen.AshenHunter;
import tropicraft.items.TropicraftItems;

public class RenderAshen extends RenderLiving {

    private MaskRenderer mask;
    private ModelAshen modelAshen;

    public RenderAshen(ModelBase modelbase, float f) {
        super(modelbase, f);
        modelAshen = (ModelAshen) modelbase;
        mask = new MaskRenderer();
    }

    public void renderAshen(Ashen entityAshen, double d, double d1, double d2,
            float f, float f1) {

        /*if (entityAshen.getEntityToAttack() != null && entityAshen.getDistanceToEntity(entityAshen.getEntityToAttack()) < 2.0F) {
            modelAshen.swinging = true;
        } else {*/
            modelAshen.swinging = false;
        //}
        modelAshen.actionState = entityAshen.getActionState();
        super.doRenderLiving(entityAshen, d, d1, d2, f, f1);

    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderAshen((Ashen) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderAshen((Ashen) entity, d, d1, d2, f, f1);
    }

    private void renderMask(EntityLivingBase entityliving) {
        GL11.glPushMatrix();
        modelAshen.head.postRender(.045F);
        TropicraftUtils.bindTextureEntity("mask");
        GL11.glTranslatef(-0.03125F, 0.40625F + MathHelper.sin(((Ashen) entityliving).bobber), .18F);
        mask.renderMask(((Ashen) entityliving).getMaskType());
        GL11.glPopMatrix();
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
        if (entityliving instanceof AshenHunter) {
            if (((Ashen) entityliving).getActionState() == 2) {
                GL11.glPushMatrix();
                modelAshen.leftArm.postRender(0.0625F);
                GL11.glTranslatef(-0.35F, -0.45F, -0.225F);
                //GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                float scale = 0.3F;
                GL11.glScalef(scale, scale, scale);
                RenderManager.instance.itemRenderer.renderItem(entityliving, new ItemStack(TropicraftItems.spearBamboo), 0);
                
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                modelAshen.rightArm.postRender(0.0625F);
                
                /*GL11.glTranslatef(0.4F, -0.20F, -0.245F);
                GL11.glRotatef(05F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);*/
                
                //GL11.glTranslatef(0.4F, -0.50F, -0.245F);
                //GL11.glRotatef(12F, 0.0F, 0.0F, 1.0F);
                
                GL11.glTranslatef(0.4F, -0.30F, -0.245F);
                GL11.glRotatef(130F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(5F, 0.0F, 0.0F, 1.0F);
                
                /*GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(130F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(90F, 0.0F, 0.0F, 1.0F);
                
                GL11.glRotatef(160F, 0.0F, 1.0F, 0.0F);*/
                
                GL11.glScalef(scale, scale, scale);
                RenderManager.instance.itemRenderer.renderItem(entityliving, new ItemStack(TropicraftItems.blowGun), 0);
                GL11.glPopMatrix();
            }
        }
        if (((Ashen) entityliving).hasMask()) {        	
            renderMask(entityliving);
        }
    }

	@Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TropicraftUtils.bindTextureEntity("nativetext");
	}
}
