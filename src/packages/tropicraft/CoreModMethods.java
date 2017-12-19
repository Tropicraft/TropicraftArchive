package tropicraft;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import CoroAI.c_CoroAIUtil;
import tropicraft.client.entities.MaskRenderer;
import tropicraft.items.TropicraftItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class CoreModMethods {
	private static MaskRenderer mask = new MaskRenderer();
	public static ModelBiped playerMod;
	/** boolean used to determine whether to use Tropicraft renders or not */
	private boolean isTropicraftActive = false;
	private boolean hasModelBeenSet = false;
	private RenderPlayer renderPlayer;
	private RenderManager renderManager;

	public CoreModMethods(/* RenderPlayerAPI var1 */) {
		// super(var1);
		mask = new MaskRenderer();
		System.out.println("Tropicraft Player Renders Initialized");
		renderManager = renderManager.instance;
		renderPlayer = (RenderPlayer) renderManager.entityRenderMap
				.get(EntityPlayer.class);

	}

	private static void getSetModel() {		
		playerMod = (ModelBiped) c_CoroAIUtil.getPrivateValueBoth(
				RenderPlayer.class,
				RenderManager.instance.entityRenderMap.get(EntityPlayer.class),
				"a", "modelBipedMain");

		// System.out.println((playerMod == null));
	}

	public static void renderGameOverlay(float partialTicks, boolean hasScreen,
			int mouseX, int mouseZ) {
		System.out.println("Huebawt");
		
		Minecraft mc = Minecraft.getMinecraft();
		
		ScaledResolution var5 = new ScaledResolution(mc.gameSettings,
				mc.displayWidth, mc.displayHeight);
		int var6 = var5.getScaledWidth();
		int var7 = var5.getScaledHeight();
		ItemStack itemstack = mc.thePlayer.inventory.armorItemInSlot(0);

		if (mc.gameSettings.thirdPersonView == 0 && itemstack != null
				&& itemstack.itemID == TropicraftItems.ashenMasks.itemID) {
			renderMaskBlur(var6, var7);
		}
		if (mc.gameSettings.thirdPersonView == 0 && itemstack != null
				&& itemstack.itemID == TropicraftItems.snorkel.itemID) {
			renderSnorkelBlur(var6, var7);
		}
	}

	public static boolean setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity entity, ModelBiped model) {
		return false;
	}

	public static boolean renderSpecials(EntityPlayer player, float partialTicks) {
		renderTropSpecials(player, partialTicks);
		return true;
	}

	public static void rotatePlayer(EntityPlayer player) {

	}

	public static void renderTropSpecials(EntityPlayer entityplayer, float var2) {
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

		float f2 = -0.25F;
		// if (entityliving.isSneaking()) {
		// f2 = -0.50F;
		// }
		GL11.glPushMatrix();
		playerMod.bipedHead.postRender(0.0625F);
		loadTexture("/mods/TropicraftMod/textures/entities/mask.png");
		System.out.println(i);
		GL11.glTranslatef(0.025F, 0.1125F, f2);
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		mask.renderMask(i);
		GL11.glPopMatrix();
	}

	private static void renderSnorkel(EntityPlayer entityliving) {
		float f2 = -0.250F;
		// if (entityliving.isSneaking()) {
		// f2 = -0.40F;
		// }

		GL11.glPushMatrix();
		playerMod.bipedHead.postRender(0.0625F);
		loadTexture("/gui/items.png");
		GL11.glScalef(1.2F, 1.2F, 1.2F);
		GL11.glTranslatef(-0.238F, 0.30F, f2);
		GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
		mask.renderItem(TropicraftItems.snorkel.getIconFromDamage(0));
		GL11.glPopMatrix();
	}

	private static void renderFlippers(EntityPlayer player) {
		float f = -0.5425F;
		if (player.onGround) {
			f = -0.5325F;
		}
		GL11.glPushMatrix();
		playerMod.bipedRightLeg.postRender(0.0625F);
		loadTexture("/mods/TropicraftMod/gui/tropiitems.png");
		GL11.glScalef(1.25F, 1.5F, 1.9F);
		GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.1995F, 0.40F, f);
		mask.renderItem(37);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		playerMod.bipedLeftLeg.postRender(0.0625F);
		loadTexture("/mods/TropicraftMod/gui/tropiitems.png");
		GL11.glScalef(1.25F, 1.5F, 1.9F);
		GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.25F, 0.40F, f);
		mask.renderItem(37);
		GL11.glPopMatrix();
	}

	public static void loadTexture(String par1Str) {
		RenderEngine var2 = RenderManager.instance.renderEngine;
		var2.bindTexture(par1Str);
	}

	private static void renderMaskBlur(int i, int j) {
		GL11.glDisable(2929 /*
							 * GL_DEPTH_TEST
							 */);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3008 /*
							 * GL_ALPHA_TEST
							 */);
		GL11.glBindTexture(3553 /*
								 * GL_TEXTURE_2D
								 */, Minecraft.getMinecraft().renderEngine
				.getTexture("%blur%/tropicalmod/gui/maskblur.png"));
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(2929 /*
							 * GL_DEPTH_TEST
							 */);
		GL11.glEnable(3008 /*
							 * GL_ALPHA_TEST
							 */);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private static void renderSnorkelBlur(int i, int j) {
		GL11.glDisable(2929 /*
							 * GL_DEPTH_TEST
							 */);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3008 /*
							 * GL_ALPHA_TEST
							 */);
		GL11.glBindTexture(3553 /*
								 * GL_TEXTURE_2D
								 */, Minecraft.getMinecraft().renderEngine
				.getTexture("%blur%/tropicalmod/gui/snorkelGrad.png"));
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(2929 /*
							 * GL_DEPTH_TEST
							 */);
		GL11.glEnable(3008 /*
							 * GL_ALPHA_TEST
							 */);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
