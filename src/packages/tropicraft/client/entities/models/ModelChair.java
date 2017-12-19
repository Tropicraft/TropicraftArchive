package tropicraft.client.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelChair extends ModelBase {

    public ModelRenderer New_Shape1;
    public ModelRenderer New_Shape2;
    public ModelRenderer New_Shape3;
    public ModelRenderer New_Shape31;
    public ModelRenderer New_Shape32;
    public ModelRenderer New_Shape321;
    public ModelRenderer New_Shape4;
    public ModelRenderer New_Shape41;

    public ModelChair() {
        New_Shape1 = new ModelRenderer(this, 0, 0);
        New_Shape1.addBox(-7F, 0F, -8F, 16, 1, 16, 0F);
        New_Shape1.setRotationPoint(-1F, 0F, 0F);

        New_Shape1.rotateAngleX = 0F;
        New_Shape1.rotateAngleY = 0F;
        New_Shape1.rotateAngleZ = 0F;
        New_Shape1.mirror = false;

        New_Shape2 = new ModelRenderer(this, 0, 0);
        New_Shape2.addBox(-7F, 0F, 0F, 16, 1, 16, 0F);
        New_Shape2.setRotationPoint(-1F, 0F, 8F);

        New_Shape2.rotateAngleX = 1.169371F;
        New_Shape2.rotateAngleY = 0F;
        New_Shape2.rotateAngleZ = 0F;
        New_Shape2.mirror = false;

        New_Shape3 = new ModelRenderer(this, 0, 0);
        New_Shape3.addBox(-1F, -1F, 0F, 1, 10, 1, 0F);
        New_Shape3.setRotationPoint(-8F, -3F, 6F);

        New_Shape3.rotateAngleX = 0.4537856F;
        New_Shape3.rotateAngleY = 0F;
        New_Shape3.rotateAngleZ = 0F;
        New_Shape3.mirror = false;

        New_Shape31 = new ModelRenderer(this, 0, 0);
        New_Shape31.addBox(0F, 0F, 0F, 1, 10, 1, 0F);
        New_Shape31.setRotationPoint(8F, -4F, 5F);

        New_Shape31.rotateAngleX = 0.4537856F;
        New_Shape31.rotateAngleY = 0F;
        New_Shape31.rotateAngleZ = 0F;
        New_Shape31.mirror = false;

        New_Shape32 = new ModelRenderer(this, 0, 0);
        New_Shape32.addBox(0F, 0F, -1F, 1, 10, 1, 0F);
        New_Shape32.setRotationPoint(8F, -4F, 0F);

        New_Shape32.rotateAngleX = -0.4537856F;
        New_Shape32.rotateAngleY = 0F;
        New_Shape32.rotateAngleZ = 0F;
        New_Shape32.mirror = false;

        New_Shape321 = new ModelRenderer(this, 0, 0);
        New_Shape321.addBox(-1F, 0F, -1F, 1, 10, 1, 0F);
        New_Shape321.setRotationPoint(-8F, -4F, 0F);

        New_Shape321.rotateAngleX = -0.4537856F;
        New_Shape321.rotateAngleY = 0F;
        New_Shape321.rotateAngleZ = 0F;
        New_Shape321.mirror = false;

        New_Shape4 = new ModelRenderer(this, 0, 29);
        New_Shape4.addBox(0F, -1F, 0F, 14, 1, 2, 0F);
        New_Shape4.setRotationPoint(-10F, -4F, 11F);

        New_Shape4.rotateAngleX = 0F;
        New_Shape4.rotateAngleY = 1.570796F;
        New_Shape4.rotateAngleZ = 0F;
        New_Shape4.mirror = false;

        New_Shape41 = new ModelRenderer(this, 0, 29);
        New_Shape41.addBox(0F, 0F, 0F, 14, 1, 2, 0F);
        New_Shape41.setRotationPoint(8F, -5F, 11F);

        New_Shape41.rotateAngleX = 0F;
        New_Shape41.rotateAngleY = 1.570796F;
        New_Shape41.rotateAngleZ = 0F;
        New_Shape41.mirror = false;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        New_Shape1.render(f5);
        New_Shape2.render(f5);
        New_Shape3.render(f5);
        New_Shape31.render(f5);
        New_Shape32.render(f5);
        New_Shape321.render(f5);
        New_Shape4.render(f5);
        New_Shape41.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
    }
}
