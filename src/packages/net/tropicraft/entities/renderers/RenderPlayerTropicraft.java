package net.tropicraft.entities.renderers;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.tropicraft.entities.EntityBeachFloat;
import net.tropicraft.mods.TropicraftMod;

import org.lwjgl.opengl.GL11;

public class RenderPlayerTropicraft {

	private MaskRenderer mask;
	public ModelBiped playerMod;
	/** boolean used to determine whether to use Tropicraft renders or not */
	private boolean isTropicraftActive = false;
	private boolean hasModelBeenSet = false;
	private RenderPlayer renderPlayer;
	private RenderManager renderManager;

	public RenderPlayerTropicraft(/*RenderPlayerAPI var1*/) {
		//super(var1);
		mask = new MaskRenderer();
		System.out.println("Tropicraft Player Renders Initialized");
		renderManager = renderManager.instance;
		renderPlayer = (RenderPlayer) renderManager.entityRenderMap.get(EntityPlayer.class);
	}
	
	public void rotatePlayer(EntityPlayer player)
	{
		if(player.ridingEntity instanceof EntityBeachFloat){
			  GL11.glRotatef(-player.ridingEntity.rotationYaw, 0.0F, 1.0F, 0.0F);
	            GL11.glRotatef(90, 0.0F, 0.0F, 1.0F);
	            GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
	            GL11.glTranslatef(0F, -.85F, -.5F);
			//System.out.println(player.ridingEntity.rotationYaw);
			//GL11.glRotatef(0, 0, 1, 0);
			//GL11.glRotatef(player.ridingEntity.rotationYaw, 0, 1, 0);
			//GL11.glRotatef(90, 0, 0, 1);
			
			//GL11.glRotatef(player.ridingEntity.rotationYaw - player.rotationYaw, 0, 1, 0);
	            player.renderYawOffset = 180;
			//player.rotationYaw = 0;
			//player.rotationYaw = player.ridingEntity.rotationYaw;
			//GL11.glTranslatef(0, -2, 0);
		
//			GL11.glRotatef(player.ridingEntity.rotationYaw - 90, 0, 1, 0);
			//player.renderYawOffset = player.ridingEntity.rotationYaw - 90;
			
			
		}
		if (TropicraftMod.proxy.getUpsideDownUsernames().contains(player.username)) {
			GL11.glTranslatef(0f, 1.8f, 0f);
			GL11.glRotatef(180f, 1f, 0f, 1f);
		}
	}

	private void getSetModel() {
		playerMod = (ModelBiped) TropicraftMod.getPrivateValueBoth(RenderPlayer.class, RenderManager.instance.entityRenderMap.get(EntityPlayer.class), "a", "modelBipedMain");

	//	System.out.println((playerMod == null));
	}

	//@Override
	public boolean renderSpecials(EntityPlayer player, float var2) {
		isTropicraftActive = false;

		findAndSetIsTropicraftSpecialsActive(player, var2);

		//System.out.println("being called bro");

		if(isTropicraftActive) {
			if(!hasModelBeenSet) {
				getSetModel();
				System.out.println(playerMod);
				this.hasModelBeenSet = true;
			}
			renderTropSpecials(player, var2);
			hackiness(player, var2);
			return true;
		}else
			return false;
		
		
	}

	/**
	 * If the player should render a tropicraft item, render it, then return true. Otherwise, do not render anything, return false.
	 * @param entityplayer Player
	 * @param var2 partial tick maybe? idk
	 * @return whether should render tropicraft item or not
	 */
	public void renderTropSpecials(EntityPlayer entityplayer, float var2) {
		ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3);
		ItemStack itemstack1 = entityplayer.inventory.armorItemInSlot(0);

		if (itemstack != null) {
			if (itemstack.getItem().shiftedIndex == TropicraftMod.ashenMask.shiftedIndex) {

				renderMask(entityplayer, itemstack.getItemDamage());
			}

			if (itemstack.itemID == TropicraftMod.snorkel.shiftedIndex) {
				renderSnorkel(entityplayer);
			}
		}

		if (itemstack1 != null && itemstack1.itemID == TropicraftMod.flippers.shiftedIndex) {
			renderFlippers(entityplayer);
		}
	}

	private void findAndSetIsTropicraftSpecialsActive(EntityPlayer entityplayer, float f) {
		ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3);
		ItemStack itemstack1 = entityplayer.inventory.armorItemInSlot(0);

		if (itemstack != null) {
			if (itemstack.getItem().shiftedIndex == TropicraftMod.ashenMask.shiftedIndex) {
				isTropicraftActive = true;
			}
			if (itemstack.itemID == TropicraftMod.snorkel.shiftedIndex) {
				isTropicraftActive = true;
			}
		}

		if (itemstack1 != null && itemstack1.itemID == TropicraftMod.flippers.shiftedIndex) {
			isTropicraftActive = true;
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
		mask.renderItem(TropicraftMod.snorkel.getIconFromDamage(0));
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
        mask.renderItem(TropicraftMod.snorkel.getIconFromDamage(0) - 1);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		playerMod.bipedLeftLeg.postRender(0.0625F);
		loadTexture("/tropicalmod/tropiitems.png");
		GL11.glScalef(1.25F, 1.5F, 1.9F);
		GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.25F, 0.40F, f);
		mask.renderItem(TropicraftMod.snorkel.getIconFromDamage(0) - 1);
		GL11.glPopMatrix();
	}
	
	public void loadTexture(String par1Str)
    {
        RenderEngine var2 = this.renderManager.renderEngine;
        var2.bindTexture(var2.getTexture(par1Str));
    }
	
	private void hackiness(EntityPlayer par1EntityPlayer, float par2)
	{
		float var7;
        float var8;

        if (par1EntityPlayer.username.equals("deadmau5") && this.loadDownloadableImageTexture(par1EntityPlayer.skinUrl, (String)null))
        {
            for (int var20 = 0; var20 < 2; ++var20)
            {
                float var25 = par1EntityPlayer.prevRotationYaw + (par1EntityPlayer.rotationYaw - par1EntityPlayer.prevRotationYaw) * par2 - (par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * par2);
                var7 = par1EntityPlayer.prevRotationPitch + (par1EntityPlayer.rotationPitch - par1EntityPlayer.prevRotationPitch) * par2;
                GL11.glPushMatrix();
                GL11.glRotatef(var25, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(var7, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.375F * (float)(var20 * 2 - 1), 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                GL11.glRotatef(-var7, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-var25, 0.0F, 1.0F, 0.0F);
                var8 = 1.3333334F;
                GL11.glScalef(var8, var8, var8);
                this.playerMod.renderEars(0.0625F);
                GL11.glPopMatrix();
            }
        }

        float var11;

        if (this.loadDownloadableImageTexture(par1EntityPlayer.playerCloakUrl, (String)null) && !par1EntityPlayer.getHasActivePotion() && !par1EntityPlayer.getHideCape())
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double var22 = par1EntityPlayer.field_71091_bM + (par1EntityPlayer.field_71094_bP - par1EntityPlayer.field_71091_bM) * (double)par2 - (par1EntityPlayer.prevPosX + (par1EntityPlayer.posX - par1EntityPlayer.prevPosX) * (double)par2);
            double var24 = par1EntityPlayer.field_71096_bN + (par1EntityPlayer.field_71095_bQ - par1EntityPlayer.field_71096_bN) * (double)par2 - (par1EntityPlayer.prevPosY + (par1EntityPlayer.posY - par1EntityPlayer.prevPosY) * (double)par2);
            double var9 = par1EntityPlayer.field_71097_bO + (par1EntityPlayer.field_71085_bR - par1EntityPlayer.field_71097_bO) * (double)par2 - (par1EntityPlayer.prevPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.prevPosZ) * (double)par2);
            var11 = par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * par2;
            double var12 = (double)MathHelper.sin(var11 * (float)Math.PI / 180.0F);
            double var14 = (double)(-MathHelper.cos(var11 * (float)Math.PI / 180.0F));
            float var16 = (float)var24 * 10.0F;

            if (var16 < -6.0F)
            {
                var16 = -6.0F;
            }

            if (var16 > 32.0F)
            {
                var16 = 32.0F;
            }

            float var17 = (float)(var22 * var12 + var9 * var14) * 100.0F;
            float var18 = (float)(var22 * var14 - var9 * var12) * 100.0F;

            if (var17 < 0.0F)
            {
                var17 = 0.0F;
            }

            float var19 = par1EntityPlayer.prevCameraYaw + (par1EntityPlayer.cameraYaw - par1EntityPlayer.prevCameraYaw) * par2;
            var16 += MathHelper.sin((par1EntityPlayer.prevDistanceWalkedModified + (par1EntityPlayer.distanceWalkedModified - par1EntityPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * var19;

            if (par1EntityPlayer.isSneaking())
            {
                var16 += 25.0F;
            }

            GL11.glRotatef(6.0F + var17 / 2.0F + var16, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var18 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var18 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.playerMod.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }

        ItemStack var21 = par1EntityPlayer.inventory.getCurrentItem();

        if (var21 != null)
        {
            GL11.glPushMatrix();
            this.playerMod.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            if (par1EntityPlayer.fishEntity != null)
            {
                var21 = new ItemStack(Item.stick);
            }

            EnumAction var23 = null;

            if (par1EntityPlayer.getItemInUseCount() > 0)
            {
                var23 = var21.getItemUseAction();
            }

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(var21, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, var21, BLOCK_3D));

            if (var21.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[var21.itemID].getRenderType())))
            {
                var7 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                var7 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-var7, -var7, var7);
            }
            else if (var21.itemID == Item.bow.shiftedIndex)
            {
                var7 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(var7, -var7, var7);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (Item.itemsList[var21.itemID].isFull3D())
            {
                var7 = 0.625F;

                if (Item.itemsList[var21.itemID].shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                if (par1EntityPlayer.getItemInUseCount() > 0 && var23 == EnumAction.block)
                {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(var7, -var7, var7);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                var7 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(var7, var7, var7);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float var10;
            int var27;
            float var28;

            if (var21.getItem().requiresMultipleRenderPasses())
            {
                for (var27 = 0; var27 < var21.getItem().getRenderPasses(var21.getItemDamage()); ++var27)
                {
                    int var26 = var21.getItem().getColorFromItemStack(var21, var27);
                    var28 = (float)(var26 >> 16 & 255) / 255.0F;
                    var10 = (float)(var26 >> 8 & 255) / 255.0F;
                    var11 = (float)(var26 & 255) / 255.0F;
                    GL11.glColor4f(var28, var10, var11, 1.0F);
                    this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var21, var27);
                }
            }
            else
            {
                var27 = var21.getItem().getColorFromItemStack(var21, 0);
                var8 = (float)(var27 >> 16 & 255) / 255.0F;
                var28 = (float)(var27 >> 8 & 255) / 255.0F;
                var10 = (float)(var27 & 255) / 255.0F;
                GL11.glColor4f(var8, var28, var10, 1.0F);
                this.renderManager.itemRenderer.renderItem(par1EntityPlayer, var21, 0);
            }
            GL11.glPopMatrix();
        }
	}

	private boolean loadDownloadableImageTexture(String skinUrl, String string) {
        RenderEngine var3 = this.renderManager.renderEngine;
        int var4 = var3.getTextureForDownloadableImage(skinUrl, string);

        if (var4 >= 0)
        {
            var3.bindTexture(var4);
            return true;
        }
        else
        {
            return false;
        }
	}
	
}
