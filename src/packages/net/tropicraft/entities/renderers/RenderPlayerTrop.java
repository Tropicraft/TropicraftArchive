package net.tropicraft.entities.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class RenderPlayerTrop extends RenderPlayer {

    private MaskRenderer mask;
    public ModelBiped playerMod;
    public Minecraft mc;

    public RenderPlayerTrop(ModelBiped player) {
        mask = new MaskRenderer();
        try {
            getModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderMask(EntityPlayer entityliving, int i) {

        float f2 = -0.30F;
        if (entityliving.isSneaking()) {
            f2 = -0.50F;
        }
        GL11.glPushMatrix();
        playerMod.bipedHead.postRender(0.0625F);
        loadTexture("/tropicalmod/mask.png");
        GL11.glTranslatef(0.025F, 0.1125F, f2);
        GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
        mask.renderMask(i);
        GL11.glPopMatrix();
    }

    private void renderSnorkel(EntityPlayer entityliving) {
        float f2 = -0.250F;
        if (entityliving.isSneaking()) {
            f2 = -0.40F;
        }
        GL11.glPushMatrix();
        playerMod.bipedHead.postRender(0.0625F);
        loadTexture("/tropicalmod/tropiitems.png");
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        GL11.glTranslatef(-0.238F, 0.30F, f2);
        GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
      //  mask.renderItem(mod_tropicraft.snorkel.getIconFromDamage(0));
        GL11.glPopMatrix();
    }

    private void renderFlippers(EntityPlayer player) {
        float f = -0.5425F;
        if (player.onGround) {
            f = -0.5325F;
        }
        GL11.glPushMatrix();
        playerMod.bipedRightLeg.postRender(0.0625F);
        loadTexture("/tropicalmod/tropiitems.png");
        GL11.glScalef(1.25F, 1.5F, 1.9F);
        GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.1995F, 0.40F, f);
   //     mask.renderItem(mod_tropicraft.snorkel.getIconFromDamage(0) - 1);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        playerMod.bipedLeftLeg.postRender(0.0625F);
        loadTexture("/tropicalmod/tropiitems.png");
        GL11.glScalef(1.25F, 1.5F, 1.9F);
        GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.25F, 0.40F, f);
     //   mask.renderItem(mod_tropicraft.snorkel.getIconFromDamage(0) - 1);
        GL11.glPopMatrix();
    }

    @Override
    protected void renderSpecials(EntityPlayer entityplayer, float f) {

     /*   ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3);
        ItemStack itemstack1 = entityplayer.inventory.armorItemInSlot(0);

        if (itemstack != null) {
            if (itemstack.getItem().shiftedIndex == mod_tropicraft.AshenMask.shiftedIndex) {

                renderMask(entityplayer, itemstack.getItemDamage());
            }
            if (itemstack.itemID == mod_tropicraft.snorkel.shiftedIndex) {
                renderSnorkel(entityplayer);
            }

        }
        if (itemstack1 != null && itemstack1.itemID == mod_tropicraft.flippers.shiftedIndex) {
            renderFlippers(entityplayer);
        }
        super.renderSpecials(entityplayer, f);
*/
    }

    @Override
    protected void rotatePlayer(EntityPlayer entityplayer, float f, float f1, float f2) {

       /* if (mod_BeachFloatHandler.playerFloatMap.get(entityplayer) != null) {
            EntityBeachFloat beachfloat = (EntityBeachFloat) (mod_BeachFloatHandler.playerFloatMap.get(entityplayer));

            GL11.glRotatef(-beachfloat.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(getDeathMaxRotation(entityplayer), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0F, -.85F, -1.05F);
        } else {
            super.rotatePlayer(entityplayer, f, f1, f2);
        }*/
    }

    private void getModel() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
     //   playerMod = (ModelBiped) mod_EntMover.getPrivateValueBoth(RenderPlayer.class, this, "c", "modelBipedMain");
    }
}