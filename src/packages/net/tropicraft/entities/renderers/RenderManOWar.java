package net.tropicraft.entities.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.tropicraft.entities.EntityManOWar;
import net.tropicraft.entities.models.ModelManOWar;

import org.lwjgl.opengl.GL11;

public class RenderManOWar extends RenderLiving {

    private ModelBase scaleAmount;

    public RenderManOWar(ModelBase modelbase, ModelBase modelbase1, float f) {
        super(modelbase, f);
        mainModel = (ModelManOWar) modelbase;
        scaleAmount = modelbase1;
    }

    protected int func_40287_a(EntityManOWar manowar, int i, float f) {
        if (i == 0) {
            setRenderPassModel(scaleAmount);
            GL11.glEnable(2977 /*
                     * GL_NORMALIZE
                     */);
            GL11.glEnable(3042 /*
                     * GL_BLEND
                     */);
            GL11.glBlendFunc(770, 771);
            return 1;
        }
        if (i == 1) {
            GL11.glDisable(3042 /*
                     * GL_BLEND
                     */);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
        return -1;
    }

    public void renderManOWar(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        super.doRender(entityliving, d, d1, d2, f, f1);
        ((ModelManOWar) mainModel).isOnGround = entityliving.onGround;

    }

    @Override
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f) {
        return func_40287_a((EntityManOWar) entityliving, i, f);
    }
}
