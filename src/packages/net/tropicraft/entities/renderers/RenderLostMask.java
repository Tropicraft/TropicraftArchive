package net.tropicraft.entities.renderers;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.tropicraft.entities.EntityLostMask;

import org.lwjgl.opengl.GL11;

public class RenderLostMask extends Render {

    protected MaskRenderer mask;

    public RenderLostMask() {

        shadowSize = 0.5F;
        mask = new MaskRenderer();
    }

    public void renderLostMask(EntityLostMask entity, double d, double d1, double d2,
            float f, float f1) {

        loadTexture("/tropicalmod/mask.png");
        GL11.glPushMatrix();

        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);

        if (entity.getMode() == 0 && entity.isAirBorne && !entity.isInWater()) {
            GL11.glRotatef(entity.rotatorX, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(entity.rotatorY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(entity.rotatorZ, 0.0F, 0.0F, 1.0F);
        }
        if (entity.getMode() == 1) {

            GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
            lightingHelper(entity, 2.0F, 2.0F);
        }

        mask.renderMask(entity.getColor());

        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderLostMask((EntityLostMask) entity, d, d1, d2, f, f1);
    }

    private void lightingHelper(EntityLostMask entitypainting, float f, float f1) {
        int i = MathHelper.floor_double(entitypainting.posX);
        int j = MathHelper.floor_double(entitypainting.posY + (double) (f1 / 16F));
        int k = MathHelper.floor_double(entitypainting.posZ);
        int direction = entitypainting.getDirection();
        //System.out.println(direction);
        if (direction == 0) {
            i = MathHelper.floor_double(entitypainting.posX + (double) (f / 16F));
           
        }
        if (direction == 1) {
        	 //System.out.println("X = " + i + " Z = " + k);
            k = MathHelper.floor_double(entitypainting.posZ - (double) (f/ 16F));
        }
        if (direction == 2) {
            i = MathHelper.floor_double(entitypainting.posX - (double) (f / 16F));
        }
        if (direction == 3) {
            k = MathHelper.floor_double(entitypainting.posZ + (double) (f / 16F));
        }
     
        int l = renderManager.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int i1 = l % 0x10000;
        int j1 = l / 0x10000;
        int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int var8 = var7 % 65536;
        int var9 = var7 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var8, (float) var9);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
}
