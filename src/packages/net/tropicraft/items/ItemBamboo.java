package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class ItemBamboo extends ItemTropicraft {
    
    private int spawnID;

    public ItemBamboo(int i, Block block) {
        super(i);
        spawnID = block.blockID;
        this.setCreativeTab(TropicraftMod.tabMaterials);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
        if (world.getBlockId(i, j, k) == Block.snow.blockID) {
            l = 0;
        } else {
            if (l == 0) {
                j--;
            }
            if (l == 1) {
                j++;
            }
            if (l == 2) {
                k--;
            }
            if (l == 3) {
                k++;
            }
            if (l == 4) {
                i--;
            }
            if (l == 5) {
                i++;
            }
        }
        if (itemstack.stackSize == 0) {
            return false;
        }
        if (world.canPlaceEntityOnSide(this.spawnID, i, j, k, false, l, (Entity)null)) {
            Block block = Block.blocksList[spawnID];
            if (world.setBlockWithNotify(i, j, k, spawnID)) {
            	Block.blocksList[this.spawnID].onBlockPlaced(world, i,  j, k, l, par8, par9, par10, 0);
                Block.blocksList[spawnID].onBlockPlacedBy(world, i, j, k, entityplayer);
                world.playSoundEffect((float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F, block.stepSound.stepSoundName, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                itemstack.stackSize--;
            }
        }
        return true;
    }
}
