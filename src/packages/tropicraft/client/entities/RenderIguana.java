package tropicraft.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import tropicraft.entities.passive.land.Iguana;

public class RenderIguana extends RenderLiving {

    public RenderIguana(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    public void renderIguana(Iguana entityiguana, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving(entityiguana, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderIguana((Iguana) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderIguana((Iguana) entity, d, d1, d2, f, f1);
    }
}
