package net.tropicraft.entities.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.tropicraft.entities.EntityStarfish;

public class RenderStarfish extends RenderLiving {

    public RenderStarfish(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    public void renderStarfish(EntityStarfish entitystarfish, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving(entitystarfish, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderStarfish((EntityStarfish) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderStarfish((EntityStarfish) entity, d, d1, d2, f, f1);
    }
}
