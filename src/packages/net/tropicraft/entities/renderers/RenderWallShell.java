package net.tropicraft.entities.renderers;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.tropicraft.entities.EntityWallShell;
import net.tropicraft.mods.TropicraftMod;

import org.lwjgl.opengl.GL11;

public class RenderWallShell extends Render {

    MaskRenderer renderer;
    static String[] image = {"/tropicalmod/commonshell1.png", "/tropicalmod/shell2.png",
        "/tropicalmod/shell6.png", "/tropicalmod/shell3.png", "/tropicalmod/shell4.png"};

    public RenderWallShell() {
        renderer = new MaskRenderer();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderBoat((EntityWallShell) entity, d, d1, d2, f, f1);

    }

    public int getTexture(int i) {
        //System.out.println(i);

        if (i == TropicraftMod.shellCommon1.shiftedIndex) {
            return TropicraftMod.shellCommon1.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellCommon3.shiftedIndex) {
            return TropicraftMod.shellCommon3.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellCommon2.shiftedIndex) {
            return TropicraftMod.shellCommon2.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellRare1.shiftedIndex) {
            return TropicraftMod.shellRare1.getIconFromDamage(0);
        }
        if (i == TropicraftMod.turtleShell.shiftedIndex) {
            return TropicraftMod.turtleShell.getIconFromDamage(0);
        }
        if (i == TropicraftMod.shellStarfish.shiftedIndex) {
            return TropicraftMod.shellStarfish.getIconFromDamage(0);
        } else {
            return TropicraftMod.shellCommon1.getIconFromDamage(0);
        }
    }

    public void renderBoat(EntityWallShell entity, double d, double d1, double d2,
            float f, float f1) {

        loadTexture("/tropicalmod/tropiitems.png");
        GL11.glPushMatrix();

        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(.25F, .5F, 0.0F);
        lightingHelper(entity, 2.0F, 2.0F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        renderer.renderItem(getTexture(entity.getColor()));

        GL11.glPopMatrix();
    }

    private void lightingHelper(EntityWallShell entitypainting, float f, float f1) {
        int i = MathHelper.floor_double(entitypainting.posX);
        int j = MathHelper.floor_double(entitypainting.posY + (double) (f1 / 16F));
        int k = MathHelper.floor_double(entitypainting.posZ);
        if (entitypainting.direction == 0) {
            i = MathHelper.floor_double(entitypainting.posX + (double) (f / 16F));
        }
        if (entitypainting.direction == 1) {
            k = MathHelper.floor_double(entitypainting.posZ - (double) (f / 16F));
        }
        if (entitypainting.direction == 2) {
            i = MathHelper.floor_double(entitypainting.posX - (double) (f / 16F));
        }
        if (entitypainting.direction == 3) {
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
