package net.tropicraft.entities.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.tropicraft.entities.EntityAshen;
import net.tropicraft.entities.EntityAshenHunter;
import net.tropicraft.entities.models.ModelAshen;
import net.tropicraft.mods.TropicraftMod;

import org.lwjgl.opengl.GL11;

public class RenderAshen extends RenderLiving {

    private MaskRenderer mask;
    private ModelAshen modelAshen;

    public RenderAshen(ModelBase modelbase, float f) {
        super(modelbase, f);
        modelAshen = (ModelAshen) modelbase;
        mask = new MaskRenderer();
    }

    public void renderAshen(EntityAshen entityAshen, double d, double d1, double d2,
            float f, float f1) {

        if (entityAshen.getEntityToAttack() != null && entityAshen.getDistanceToEntity(entityAshen.getEntityToAttack()) < 2.0F) {
            modelAshen.swinging = true;
        } else {
            modelAshen.swinging = false;
        }
        modelAshen.actionState = entityAshen.getActionState();
        super.doRenderLiving(entityAshen, d, d1, d2, f, f1);

    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderAshen((EntityAshen) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderAshen((EntityAshen) entity, d, d1, d2, f, f1);
    }

    private void renderMask(EntityLiving entityliving) {
        GL11.glPushMatrix();
        modelAshen.head.postRender(.045F);
        loadTexture("/tropicalmod/mask.png");
        GL11.glTranslatef(-0.03125F, 0.40625F + MathHelper.sin(((EntityAshen) entityliving).bobber), .18F);
        mask.renderMask(((EntityAshen) entityliving).getMaskType());
        GL11.glPopMatrix();
    }

    @Override
    protected void renderEquippedItems(EntityLiving entityliving, float f) {
        if (entityliving instanceof EntityAshenHunter) {
            if (((EntityAshenHunter) entityliving).getActionState() == 2) {
                GL11.glPushMatrix();
                modelAshen.leftArm.postRender(0.0625F);
                loadTexture("/tropicalmod/tropiitems.png");
                GL11.glTranslatef(-0.35F, .15F, 0.015F);
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 0.0F, 1.0F);
                mask.renderItem(TropicraftMod.bambooSpear.getIconFromDamage(0));
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                modelAshen.rightArm.postRender(0.0625F);
                loadTexture("/tropicalmod/tropiitems.png");
                GL11.glTranslatef(0.48F, 0.15F, -0.045F);
                GL11.glRotatef(12F, 0.0F, 0.0F, 1.0F);
                mask.renderItem(TropicraftMod.blowGun.getIconFromDamage(0));
                GL11.glPopMatrix();
            }
        } else if (entityliving instanceof EntityAshen) {
            if (((EntityAshen) entityliving).getActionState() == 2) {
                GL11.glPushMatrix();
                modelAshen.leftArm.postRender(0.0625F);
                loadTexture("/tropicalmod/bambooSpear.png");
                GL11.glTranslatef(-0.35F, .15F, 0.015F);
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 0.0F, 1.0F);
                mask.renderItem(0);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                modelAshen.rightArm.postRender(0.0625F);
                loadTexture("/tropicalmod/blowgunitem.png");
                GL11.glTranslatef(0.48F, 0.15F, -0.045F);
                GL11.glRotatef(12F, 0.0F, 0.0F, 1.0F);
                mask.renderItem(0);
                GL11.glPopMatrix();
            }
        }
        if (((EntityAshen) entityliving).hasMask()) {        	
            renderMask(entityliving);
        }
    }
}
