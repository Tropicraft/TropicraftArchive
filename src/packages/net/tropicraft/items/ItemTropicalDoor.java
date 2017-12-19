package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicalDoor extends ItemTropicraft {
	
	/**
	 * Material of the door. In this case, we use Material.wood for bamboo doors
	 */
	private Material doorMaterial;
	
	public ItemTropicalDoor(int i, Material material) {
		super(i);
		this.doorMaterial = material;
		this.maxStackSize = 1;
	     this.setCreativeTab(TropicraftMod.tabBlock);
	}

	/**
	 * Called when the player right clicks with door in hand
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
	{
		if (l != 1 || world.isRemote)
		{
			return false;
		}
		else
		{
			++j;
			Block var11;

			if (this.doorMaterial == Material.wood)
			{
				var11 = TropicraftMod.bambooDoor;
			}
			else
			{
				var11 = Block.doorSteel;
			}

			if (entityplayer.canPlayerEdit(i, j, k, l, itemstack) && entityplayer.canPlayerEdit(i, j + 1, k, l, itemstack))
			{
				if (!var11.canPlaceBlockAt(world, i, j, k))
				{
					return false;
				}
				else
				{
					int var12 = MathHelper.floor_double((double)((entityplayer.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
					placeDoorBlock(world, i, j, k, var12, var11);
					--itemstack.stackSize;
					return true;
				}
			}
			else
			{
				return false;
			}
		}
	}

	public static void placeDoorBlock(World par0World, int par1, int par2, int par3, int par4, Block par5Block)
	{
		byte var6 = 0;
		byte var7 = 0;

		if (par4 == 0)
		{
			var7 = 1;
		}

		if (par4 == 1)
		{
			var6 = -1;
		}

		if (par4 == 2)
		{
			var7 = -1;
		}

		if (par4 == 3)
		{
			var6 = 1;
		}

		int var8 = (par0World.isBlockNormalCube(par1 - var6, par2, par3 - var7) ? 1 : 0) + (par0World.isBlockNormalCube(par1 - var6, par2 + 1, par3 - var7) ? 1 : 0);
		int var9 = (par0World.isBlockNormalCube(par1 + var6, par2, par3 + var7) ? 1 : 0) + (par0World.isBlockNormalCube(par1 + var6, par2 + 1, par3 + var7) ? 1 : 0);
		boolean var10 = par0World.getBlockId(par1 - var6, par2, par3 - var7) == par5Block.blockID || par0World.getBlockId(par1 - var6, par2 + 1, par3 - var7) == par5Block.blockID;
		boolean var11 = par0World.getBlockId(par1 + var6, par2, par3 + var7) == par5Block.blockID || par0World.getBlockId(par1 + var6, par2 + 1, par3 + var7) == par5Block.blockID;
		boolean var12 = false;

		if (var10 && !var11)
		{
			var12 = true;
		}
		else if (var9 > var8)
		{
			var12 = true;
		}

		par0World.editingBlocks = true;
		par0World.setBlockAndMetadataWithNotify(par1, par2, par3, par5Block.blockID, par4);
		par0World.setBlockAndMetadataWithNotify(par1, par2 + 1, par3, par5Block.blockID, 8 | (var12 ? 1 : 0));
		par0World.editingBlocks = false;
		par0World.notifyBlocksOfNeighborChange(par1, par2, par3, par5Block.blockID);
		par0World.notifyBlocksOfNeighborChange(par1, par2 + 1, par3, par5Block.blockID);
	}

}
