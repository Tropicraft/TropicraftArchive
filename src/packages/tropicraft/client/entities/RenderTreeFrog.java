package tropicraft.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import tropicraft.Tropicraft;
import tropicraft.TropicraftUtils;
import tropicraft.entities.hostile.land.TreeFrog;

public class RenderTreeFrog extends RenderLiving {

    public RenderTreeFrog(ModelBase modelbase, float f) {
        super(modelbase, f);
    }
    
    @Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
    	if (((TreeFrog)entity).type == 0) {
    		return TropicraftUtils.bindTextureEntity("treefroggreen");
		} else if (((TreeFrog)entity).type == 1) {
			return TropicraftUtils.bindTextureEntity("treefrogredlight");
		} else if (((TreeFrog)entity).type == 2) {
			return TropicraftUtils.bindTextureEntity("treefrogblue");
		} else /*if (((TreeFrog)entity).type == 3)*/ {
			return TropicraftUtils.bindTextureEntity("treefrogyellow");
		}
	}

    public void renderTreeFrog(TreeFrog entitytreefrog, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving(entitytreefrog, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderTreeFrog((TreeFrog) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderTreeFrog((TreeFrog) entity, d, d1, d2, f, f1);
    }
}
