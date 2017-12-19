package net.tropicraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntityBambooMug;
import net.tropicraft.drinks.Drink;
import net.tropicraft.mods.TropicraftMod;

public class ItemDrink extends ItemTropicraft {
	public ItemDrink(int i) {
		super(i);
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(TropicraftMod.tabFood);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int dmg = par1ItemStack.getItemDamage();

		if (dmg < Drink.drinkList.length && Drink.drinkList[dmg] != null) {
			par3List.add(Drink.drinkList[dmg].displayName);
		}
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (Drink drink : Drink.drinkList) {
			if (drink != null) {
				par3List.add(new ItemStack(this.shiftedIndex, 1, drink.drinkId));
			}
		}
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		int var11 = par3World.getBlockId(par4, par5, par6);

		if (var11 == Block.snow.blockID) {
			par7 = 1;
		} else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID && (Block.blocksList[var11] == null || !Block.blocksList[var11] .isBlockReplaceable(par3World, par4, par5, par6))) {
			if (par7 == 0) {
				--par5;
			} else if (par7 == 1) {
				++par5;
			} else if (par7 == 2) {
				--par6;
			} else if (par7 == 3) {
				++par6;
			} else if (par7 == 4) {
				--par4;
			} else if (par7 == 5) {
				++par4;
			}
		}
		
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
			return false;
		} else if (par3World.canPlaceEntityOnSide(TropicraftMod.bambooMug.blockID, par4, par5, par6, false, par7, par2EntityPlayer)) {
			Block var12 = TropicraftMod.bambooMug;
			int var13 = this.getMetadata(par1ItemStack.getItemDamage());
			int var14 = var12.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var13);

			if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, var14)) {
				par3World.playSoundEffect((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (!world.setBlockAndMetadataWithNotify(x, y, z, TropicraftMod.bambooMug.blockID, metadata)) {
			return false;
		}

		if (world.getBlockId(x, y, z) == TropicraftMod.bambooMug.blockID) {
			TropicraftMod.bambooMug.onBlockPlacedBy(world, x, y, z, player);
			TropicraftMod.bambooMug.onPostBlockPlaced(world, x, y, z, metadata);
			
			TileEntityBambooMug mug = (TileEntityBambooMug) world.getBlockTileEntity(x, y, z);
			mug.setCocktail(stack.copy());
			
	        int var6 = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        int meta = 2;
	        
	        if (var6 == 0) {
	        	meta = 2;
	        } else if (var6 == 1) {
	        	meta = 5;
	        } else if (var6 == 2) {
	        	meta = 3;
	        } else if (var6 == 3) {
	        	meta = 4;
	        }
	        world.setBlockMetadataWithNotify(x, y, z, meta);
		}

		return true;
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.canEat(Drink.drinkList[par1ItemStack.getItemDamage()].alwaysEdible)) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }
    
    @Override
    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        Drink.drinkList[par1ItemStack.getItemDamage()].onDrink(par3EntityPlayer);
        return new ItemStack(TropicraftMod.bambooMugEmpty);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getIconFromDamageForRenderPass(int par1, int par2) {
    	return par2 == 0 ? 3 : 164;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
    	return par2 == 0 ? 16777215 : Drink.drinkList[par1ItemStack.getItemDamage()].color;
    }
}
