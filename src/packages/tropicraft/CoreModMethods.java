package tropicraft;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import tropicraft.client.entities.MaskRenderer;
import tropicraft.items.TropicraftItems;
import CoroUtil.OldUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CoreModMethods {
	private static MaskRenderer mask = new MaskRenderer();
	public static ModelBiped playerMod;
	/** boolean used to determine whether to use Tropicraft renders or not */
	private boolean isTropicraftActive = false;
	private boolean hasModelBeenSet = false;
	private RenderPlayer renderPlayer;
	private RenderManager renderManager;

	public CoreModMethods() {
		mask = new MaskRenderer();
		System.out.println("Tropicraft Player Renders Initialized");
		renderManager = renderManager.instance;
		renderPlayer = (RenderPlayer) renderManager.entityRenderMap
				.get(EntityPlayer.class);

	}

	private static void getSetModel() {		
		playerMod = (ModelBiped) OldUtil.getPrivateValueSRGMCP(
				RenderPlayer.class,
				RenderManager.instance.entityRenderMap.get(EntityPlayer.class),
				"field_77109_a", "modelBipedMain");
	}

	public static boolean setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity entity, ModelBiped model) {
		return false;
	}
	
	public static boolean renderSpecials(AbstractClientPlayer player, float partialTicks) {
		renderTropSpecials(player, partialTicks);
		return true;
	}

	public static void rotatePlayer(AbstractClientPlayer player) {

	}

	public static void renderTropSpecials(EntityPlayer entityplayer, float var2) {
	//	System.out.println("RENDER TROPI SPECIALS");
		ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3);
		ItemStack itemstack1 = entityplayer.inventory.armorItemInSlot(0);
		getSetModel();
		if (itemstack != null) {
			if (itemstack.getItem().itemID == TropicraftItems.ashenMasks.itemID) {

				renderMask(entityplayer, itemstack.getItemDamage());
			}

			if (itemstack.itemID == TropicraftItems.snorkel.itemID) {
				renderSnorkel(entityplayer);
			}
		}

		if (itemstack1 != null
				&& itemstack1.itemID == TropicraftItems.flippers.itemID) {
			renderFlippers(entityplayer);
		}
	}

	private void findAndSetIsTropicraftSpecialsActive(
			EntityPlayer entityplayer, float f) {
		ItemStack itemstack = entityplayer.inventory.armorItemInSlot(3);
		ItemStack itemstack1 = entityplayer.inventory.armorItemInSlot(0);

		if (itemstack != null) {
			if (itemstack.getItem().itemID == TropicraftItems.ashenMasks.itemID) {
				isTropicraftActive = true;
			}
			if (itemstack.itemID == TropicraftItems.snorkel.itemID) {
				isTropicraftActive = true;
			}
		}

		if (itemstack1 != null
				&& itemstack1.itemID == TropicraftItems.flippers.itemID) {
			isTropicraftActive = true;
		}
	}

	private static void renderMask(EntityPlayer entityliving, int i) {

		float f2 = -0.3F;
		// if (entityliving.isSneaking()) {
		// f2 = -0.50F;
		// }
		GL11.glPushMatrix();
		playerMod.bipedHead.postRender(0.0625F);
		TropicraftUtils.bindTextureEntity("mask");
		//loadTexture(ModInfo.TEXTURE_ENTITY_LOC + "mask.png");
	//	System.out.println(i);
		GL11.glTranslatef(0.025F, 0.1125F, f2);
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		mask.renderMask(i);
		GL11.glPopMatrix();
	}

	private static void renderSnorkel(EntityPlayer entityliving) {
		float f2 = -.250F;
		
		GL11.glPushMatrix();
		playerMod.bipedHead.postRender(0.0625F);
		TropicraftUtils.bindTextureItem("snorkel");
		GL11.glScalef(0.78F, 0.78F, 0.9F);
		GL11.glTranslatef(0.468F, 0.30F, f2);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		
		Icon icon = TropicraftItems.snorkel.getIconFromDamage(0);
		float minU = icon.getMinU();
		float minV = icon.getMinV();
		float maxU = icon.getMaxU();
		float maxV = icon.getMaxV();
		ItemRenderer.renderItemIn2D(Tessellator.instance, 1.0F, 0.0F, 0.0F, 1.0F, 16, 16, 0.0625F);
		GL11.glPopMatrix();
	}

	private static void renderFlippers(EntityPlayer player) {
		float f = -0.5425F;
		if (player.onGround) {
			f = -0.5325F;
		}
		GL11.glPushMatrix();
		playerMod.bipedRightLeg.postRender(0.0625F);
		TropicraftUtils.bindTextureModGui("tropiitems");
		GL11.glScalef(1.25F, 1.5F, 1.9F);
		GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.1995F, 0.40F, f);
		mask.renderItem(37);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		playerMod.bipedLeftLeg.postRender(0.0625F);
		TropicraftUtils.bindTextureModGui("tropiitems");
		GL11.glScalef(1.25F, 1.5F, 1.9F);
		GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.25F, 0.40F, f);
		mask.renderItem(37);
		GL11.glPopMatrix();
	}

	public static void loadTexture(String par1Str) {
		TropicraftUtils.bindTextureMod(par1Str);
	}
}
