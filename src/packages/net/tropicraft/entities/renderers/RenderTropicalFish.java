package net.tropicraft.entities.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.tropicraft.entities.EntityTropicalFish;
import net.tropicraft.entities.models.ModelFish;

import org.lwjgl.opengl.GL11;

public class RenderTropicalFish extends RenderWaterMob {

    public ModelFish fish;
    private MaskRenderer mask;

    public RenderTropicalFish(ModelBase modelbase, float f) {
        super(modelbase, f);
        fish = (ModelFish) modelbase;
        mask = new MaskRenderer();
    }

    public void renderTropicalFish(EntityTropicalFish fish, double d, double d1, double d2,
            float f, float f1) {
        super.renderWaterMob(fish, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderTropicalFish((EntityTropicalFish) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderTropicalFish((EntityTropicalFish) entity, d, d1, d2, f, f1);
    }

    @Override
    protected void renderEquippedItems(EntityLiving entityliving, float f) {
        GL11.glPushMatrix();
        fish.Body.postRender(.045F);
        loadTexture("/tropicalmod/tropicalFish.png");
        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(.85F, 0.0F, 0.0F);
        mask.renderFish(((EntityTropicalFish) entityliving).getColor() * 2);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        fish.Tail.postRender(.045F);
        loadTexture("/tropicalmod/tropicalFish.png");
        GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-.90F, 0.725F, 0.0F);
        mask.renderFish(((EntityTropicalFish) entityliving).getColor() * 2 + 1);
        GL11.glPopMatrix();

    }

    protected void preRenderScale(EntityTropicalFish entityTropicalFish, float f) {
        GL11.glScalef(.75F, .20F, .20F);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f) {
        preRenderScale((EntityTropicalFish) entityliving, f);
    }
}
