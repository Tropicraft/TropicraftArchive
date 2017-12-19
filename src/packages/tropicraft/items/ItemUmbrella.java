package tropicraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.placeable.EntityUmbrella;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUmbrella extends ItemTropicraft {

	private String[] imageNames, displayNames;
	
	private Icon[] images;
	
    public ItemUmbrella(int i, String[] imageNames, String[] displayNames) {
        super(i);
        this.displayNames = displayNames;
        this.imageNames = imageNames;
        maxStackSize = 1;
        setHasSubtypes(true);
        setMaxDamage(0);
        this.setCreativeTab(TropiCreativeTabs.tabDecorations);
    }
    
    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
    	return displayNames[itemstack.getItemDamage()];
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < imageNames.length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int i) {
        return images[i];
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        int color = itemstack.getItemDamage();
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double) f;
        double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double) f + 1.6200000000000001D) - (double) entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double) f;
        Vec3 vec3d = Vec3.createVectorHelper(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        Vec3 vec3d1 = vec3d.addVector((double) f7 * d3, (double) f8 * d3, (double) f9 * d3);
        MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return itemstack;
        }
        if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE && movingobjectposition.sideHit == 1) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if (!world.isRemote) {
                if (world.getBlockId(i, j, k) == Block.snow.blockID) {
                    j--;
                }
                world.spawnEntityInWorld(new EntityUmbrella(world, (float) i + 0.5F, (float) j + 1.0F, (float) k + 0.5F, color));
                //Entity ent = new EntityKoaManly(world);
                //ent.initJobAndStates(ent.getOccupation(ent.gender), true);
                //ent = new EntitySurfboard(world);
                //ent = new EntityVMonkey(world);
                //ent.setPosition((float) i + 0.5F, (float) j + 1.0F, (float) k + 0.5F);
                //world.spawnEntityInWorld(ent);
            }
            itemstack.stackSize--;
        }
        return itemstack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
    	images = new Icon[imageNames.length];
    	
    	for (int i = 0; i < imageNames.length; i++) {
    		images[i] = iconRegister.registerIcon(ModInfo.ICONLOCATION + getImageName() + "_" + imageNames[i]);
    	}
    }

	@Override
	public String getImageName() {
		return "umbrella";
	}
}
