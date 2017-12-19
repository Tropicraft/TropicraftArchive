package tropicraft.items.ashen;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import tropicraft.ModInfo;
import tropicraft.TropicraftUtils;
import tropicraft.entities.items.WallMask;
import tropicraft.items.ItemTropicraftArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAshenMask extends ItemTropicraftArmor {
	@SideOnly(Side.CLIENT)
	private Icon[] images;

	private final String[] displayNames;
	private final String[] imageNames;

	public ItemAshenMask(int id, String[] displayNames, String[] imageNames) {
		super(id, null, 0, 0);
		this.displayNames = displayNames;
		this.imageNames = imageNames;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);

	}

	// public void setMaskItemType(ItemStack stack, int par2){
	// NBTTagCompound nbt = stack.getTagCompound();
	// if(nbt == null){
	// nbt = new NBTTagCompound();
	// stack.setTagCompound(nbt);
	// }
	// NBTTagCompound nbt1 = nbt.getCompoundTag("display");
	// if(!nbt.hasKey("display")){
	// nbt.setCompoundTag("display", nbt1);
	// }
	// nbt.setInteger("masktype", par2);
	// }
	//
	// public static int getMaskType(ItemStack stack){
	// NBTTagCompound nbt = stack.getTagCompound();
	// if(nbt == null){
	// return 0;
	// }
	// else{
	// NBTTagCompound nbt1 = nbt.getCompoundTag("display");
	// if(nbt1 == null){
	// return 0;
	// }
	// else {
	// if( nbt1.hasKey("color")){
	// return nbt1.getInteger("color");
	// }
	// else{
	// return 0;
	// }
	// }
	// }
	// }
	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (par7 == 0) {
			return false;
		} else if (par7 == 1) {
			return false;
		} else {
			int i1 = Direction.facingToDirection[par7];

			WallMask entityhanging = new WallMask(par3World, par4, par5, par6,
					i1, par1ItemStack.getItemDamage());

			if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7,
					par1ItemStack)) {
				return false;
			} else {
				if (entityhanging != null && entityhanging.onValidSurface()) {
					if (!par3World.isRemote) {
						par3World.spawnEntityInWorld(entityhanging);
					}

					--par1ItemStack.stackSize;
				}

				return true;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage) {
		// System.out.println("Twats");
		int j = MathHelper.clamp_int(damage, 0, 15);
		return this.images[j];
	}

	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		for (int meta = 0; meta < imageNames.length; meta++) {
			list.add(new ItemStack(id, 1, meta));
		}
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + displayNames[i];
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return ModInfo.TEXTURE_ARMOR_LOC + "mask_band.png";
	}

	/**
	 * Registers all icons used in this item
	 * 
	 * @param iconRegistry
	 *            IconRegister instance used to register all icons for this item
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		images = new Icon[displayNames.length];
	//	this.itemIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
		
		for (int i = 0; i < displayNames.length; i++) {
			images[i] = iconRegistry.registerIcon(ModInfo.ICONLOCATION + imageNames[i]);
		}
	}

	@Override
	public void damageArmor(EntityLivingBase player, ItemStack stack,
			DamageSource source, int damage, int slot) {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player,
			ScaledResolution resolution, float partialTicks, boolean hasScreen,
			int mouseX, int mouseY) {
		int i = resolution.getScaledWidth();
		int j = resolution.getScaledHeight();
		GL11.glDisable(2929 /*
							 * GL_DEPTH_TEST
							 */);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3008 /*
							 * GL_ALPHA_TEST
							 */);
		TropicraftUtils.bindTextureModGui("maskblur");
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

	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	public String getImageName() {
		if (imageNames.length <= 0) {
			throw new IllegalArgumentException(
					"Length of names array must be > 0 in ItemAshenMask");
		}

		return imageNames[0];
	}
}
