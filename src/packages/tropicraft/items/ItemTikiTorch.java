package tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.creative.TropiCreativeTabs;

public class ItemTikiTorch extends ItemTropicraftImpl {

	public ItemTikiTorch(int id, String imgName) {
		super(id, imgName);
		setCreativeTab(TropiCreativeTabs.tabDecorations);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
		if (world.getBlockId(x, y, z) == Block.snow.blockID) {
			side = 0;
		} else {
			if (side == 0) {
				y--;
			}
			if (side == 1) {
				y++;
			}
			if (side == 2) {
				z--;
			}
			if (side == 3) {
				z++;
			}
			if (side == 4) {
				x--;
			}
			if (side == 5) {
				x++;
			}
		}
		if (itemstack.stackSize == 0) {
			return false;
		}
		if (!world.isRemote && world.canPlaceEntityOnSide(TropicraftBlocks.tikiTorch.blockID, x, y, z, false, side, (Entity)null, itemstack)) {
			int blockBelow = world.getBlockId(x, y - 1, z);
			if (Block.blocksList[blockBelow] instanceof BlockFence) {
				world.setBlock(x, y, z, TropicraftBlocks.tikiTorch.blockID, 0, 3);
				world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), TropicraftBlocks.tikiTorch.stepSound.getStepSound(), (TropicraftBlocks.tikiTorch.stepSound.getVolume() + 1.0F) / 2.0F, TropicraftBlocks.tikiTorch.stepSound.getPitch() * 0.8F);
				Block.blocksList[TropicraftBlocks.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				itemstack.stackSize--;
				return true;
			} else if (world.getBlockId(x, y + 1, z) == 0 && world.getBlockId(x, y + 2, z) == 0) {
				Block.blocksList[TropicraftBlocks.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				Block.blocksList[TropicraftBlocks.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				Block.blocksList[TropicraftBlocks.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				world.setBlock(x, y, z, TropicraftBlocks.tikiTorch.blockID, 1, 3);
				world.setBlock(x, y + 1, z, TropicraftBlocks.tikiTorch.blockID, 1, 3);
				world.setBlock(x, y + 2, z, TropicraftBlocks.tikiTorch.blockID, 0, 3);
				Block.blocksList[TropicraftBlocks.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				Block.blocksList[TropicraftBlocks.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				Block.blocksList[TropicraftBlocks.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
				world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), TropicraftBlocks.tikiTorch.stepSound.getStepSound(), (TropicraftBlocks.tikiTorch.stepSound.getVolume() + 1.0F) / 2.0F, TropicraftBlocks.tikiTorch.stepSound.getPitch() * 0.8F);
				itemstack.stackSize--;
				return true;
			}
		}
		return false;
	}
}
