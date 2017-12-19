package net.tropicraft.items;

//import net.tropicraft.entities.EntityCoconutGrenade;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityCoconutGrenade;
import net.tropicraft.mods.TropicraftMod;

public class ItemCoconut extends ItemTropicraft {

    String type;
    private int spawnID;

    public ItemCoconut(int i, Block block, String type) {
        super(i);
        spawnID = block.blockID;
        this.type = type;
        if (this.type.equals("bomb")) {
        	this.setCreativeTab(TropicraftMod.tabCombat);
        } else {
        	this.setCreativeTab(TropicraftMod.tabFood);
        }
    }

   @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (type.equals("bomb")) {
            itemstack.stackSize--;
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityCoconutGrenade(world, entityplayer));
            }
        }

        return itemstack;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
        
        if (type.equals("bomb")) {
            return true;
        }
        
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
            //    Block.blocksList[spawnID].onBlockPlaced(world, i, j, k, l);
            	Block.blocksList[this.spawnID].onBlockPlaced(world, i, j, k, l, par8, par9, par10, 0);
                Block.blocksList[spawnID].onBlockPlacedBy(world, i, j, k, entityplayer);
                world.playSoundEffect((float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F, block.stepSound.stepSoundName, (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                itemstack.stackSize--;
            }
        }
        return true;
    }

}
