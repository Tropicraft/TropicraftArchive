package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class ItemTikiItem extends ItemTropicraft {

    public ItemTikiItem(int i) {
        super(i);
        this.setCreativeTab(TropicraftMod.tabDecorations);
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
        if (!world.isRemote && world.canPlaceEntityOnSide(TropicraftMod.tikiTorch.blockID, x, y, z, false, side, (Entity)null)) {
            int blockBelow = world.getBlockId(x, y - 1, z);
            if (Block.blocksList[blockBelow] instanceof BlockFence) {
                world.setBlockAndMetadataWithNotify(x, y, z, TropicraftMod.tikiTorch.blockID, 0);
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), TropicraftMod.tikiTorch.stepSound.getStepSound(), (TropicraftMod.tikiTorch.stepSound.getVolume() + 1.0F) / 2.0F, TropicraftMod.tikiTorch.stepSound.getPitch() * 0.8F);
                Block.blocksList[TropicraftMod.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
                itemstack.stackSize--;
                return true;
            } else if (world.getBlockId(x, y + 1, z) == 0 && world.getBlockId(x, y + 2, z) == 0) {
            	Block.blocksList[TropicraftMod.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
                Block.blocksList[TropicraftMod.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
                Block.blocksList[TropicraftMod.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
                world.setBlockAndMetadataWithNotify(x, y, z, TropicraftMod.tikiTorch.blockID, 1);
                world.setBlockAndMetadataWithNotify(x, y + 1, z, TropicraftMod.tikiTorch.blockID, 1);
                world.setBlockAndMetadataWithNotify(x, y + 2, z, TropicraftMod.tikiTorch.blockID, 0);
                Block.blocksList[TropicraftMod.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
                Block.blocksList[TropicraftMod.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
                Block.blocksList[TropicraftMod.tikiTorch.blockID].onBlockPlaced(world, x, y, z, side, par8, par9, par10, 0);
                world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), TropicraftMod.tikiTorch.stepSound.getStepSound(), (TropicraftMod.tikiTorch.stepSound.getVolume() + 1.0F) / 2.0F, TropicraftMod.tikiTorch.stepSound.getPitch() * 0.8F);
                itemstack.stackSize--;
                return true;
            }
        }
        return false;
    }
}