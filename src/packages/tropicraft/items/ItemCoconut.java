package tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.ModIds;
import tropicraft.Tropicraft;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.config.TropicraftConfig;
import tropicraft.entities.projectiles.EntityCoconutGrenade;

public class ItemCoconut extends ItemTropicraft {

	/**
	 * Flag that determines whether this is a regular coconut or a coconut bomb. <br>
	 * 0 for regular coconut <br>
	 * 1 for coconut bomb
	 */
	private int mode;

	/**
	 * @param id itemID
	 * @param tab creative tab to display on
	 * @param mode 0 for regular coconut, 1 for coconut bomb
	 */
	public ItemCoconut(int id, CreativeTabs tab, int mode) {
		super(id, tab);
		this.mode = mode;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (mode == 1) { //coconut bomb
			itemstack.stackSize--;
			world.playSoundAtEntity(player, "random.bow", 0.5f, 0.4F/ (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!world.isRemote && Tropicraft.coconutBombWhitelistedUsers.contains(player.username)) {
				world.spawnEntityInWorld(new EntityCoconutGrenade(world, player));
			} else {
				if (world.isRemote && !Tropicraft.coconutBombWhitelistedUsers.contains(player.username))
					player.addChatMessage(TropicraftConfig.cocoBombWarning);
			}
		}

		return itemstack;
	}
	
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int i, int j, int k, int meta, float par8, float par9, float par10)
    {
    	if (world.isRemote)
    		return false;
    	
    	if (mode == 1)
    		return true;
    	
        if (world.getBlockId(i, j, k) == Block.snow.blockID) {
            meta = 0;
        } else {
            if (meta == 0) {
                j--;
            }
            if (meta == 1) {
                j++;
            }
            if (meta == 2) {
                k--;
            }
            if (meta == 3) {
                k++;
            }
            if (meta == 4) {
                i--;
            }
            if (meta == 5) {
                i++;
            }
        }
        
        if (itemstack.stackSize == 0) {
            return false;
        }
        
        int placedBlockID = TropicraftBlocks.coconut.blockID;
        
        if (world.canPlaceEntityOnSide(placedBlockID, i, j, k, false, meta, (Entity)null, itemstack))
		{
			Block block = Block.blocksList[placedBlockID];
			int j1 = block.onBlockPlaced(world, i, j, k, meta, par8, par9, par10, 0);

			if (world.setBlock(i, j, k, placedBlockID, j1, 3))
			{
				if (world.getBlockId(i, j, k) == placedBlockID)
				{
					Block.blocksList[placedBlockID].onBlockPlacedBy(world, i, j, k, player, itemstack);
					Block.blocksList[placedBlockID].onPostBlockPlaced(world, i, j, k, j1);
				}

				world.playSoundEffect((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--itemstack.stackSize;
			}
		}
    	
        return true;
    }

	@Override
	public String getImageName() {
		return mode == 0 ? "coconut" : "coconutbomb";
	}
}
