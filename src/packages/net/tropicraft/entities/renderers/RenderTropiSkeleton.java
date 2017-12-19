package net.tropicraft.entities.renderers;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.tropicraft.entities.models.ModelTropiSkeleton;

import org.lwjgl.opengl.GL11;

public class RenderTropiSkeleton extends RenderBiped {

    public RenderTropiSkeleton(float par1) {
        super(new ModelTropiSkeleton(), par1);
    }

    @Override
    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2) {
        ItemStack var3 = par1EntityLiving.getHeldItem();

        if (var3 != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            float var4;

            net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(var3, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, var3, BLOCK_3D));

            GL11.glPushMatrix();
            if (var3.itemID < 256 && (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType()))) {
                var4 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                var4 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var4, -var4, var4);
            } else if (var3.itemID == Item.bow.shiftedIndex) {
                var4 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var4, -var4, var4);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (Item.itemsList[var3.itemID].isFull3D()) {
                var4 = 0.625F;
                //GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glTranslatef(0.1F, -0.2F, 0.2F);
                GL11.glScalef(var4, -var4, var4);

                // GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F); //swapped with next line
                GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);

                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

            } else {
                var4 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(var4, var4, var4);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 0);

            if (var3.getItem().requiresMultipleRenderPasses()) {
                for (int x = 1; x < var3.getItem().getRenderPasses(var3.getItemDamage()); x++) {
                    this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, x);
                }
            }

            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
}
