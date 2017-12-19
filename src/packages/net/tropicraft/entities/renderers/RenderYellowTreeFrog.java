package net.tropicraft.entities.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.tropicraft.entities.EntityPoisonousTreeFrogYellow;

public class RenderYellowTreeFrog extends RenderLiving {

    public RenderYellowTreeFrog(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    public void renderTreeFrog(EntityPoisonousTreeFrogYellow entitypoisonoustreefrogyellow, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving(entitypoisonoustreefrogyellow, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderTreeFrog((EntityPoisonousTreeFrogYellow) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderTreeFrog((EntityPoisonousTreeFrogYellow) entity, d, d1, d2, f, f1);
    }
}
