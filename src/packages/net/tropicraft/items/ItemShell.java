package net.tropicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntityWallShell;
import net.tropicraft.mods.TropicraftMod;

public class ItemShell extends ItemTropicraft {

    public ItemShell(int i) {
        super(i);
        this.setCreativeTab(TropicraftMod.tabDecorations);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
        if (l == 0) {
            return false;
        }
        if (l == 1) {
            return false;
        }
        
        if (l == 2)
        {
            --k;
        }

        if (l == 3)
        {
            ++k;
        }

        if (l == 4)
        {
            --i;
        }

        if (l == 5)
        {
            ++i;
        }if(world.getBlockMaterial(i, j, k).isSolid())
    	{
    		return false;
    	}
        if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack)) {
            return false;
        }
        if(!TropicraftMod.wallShell.canPlaceBlockAt(world, i, j, k))
        {
        	return false;
        }
        switch(l)
        {
        	case 2:
        		l = 1;
        		break;
        	case 3:
        		l = 3;
        		break;
        	case 4:
        		l = 0;
        		break;
        	case 5:
        		l = 2;
        		break;
        }
        world.setBlockAndMetadataWithNotify(i, j, k, TropicraftMod.wallShell.blockID, l);
        --itemstack.stackSize;
        TileEntityWallShell west = (TileEntityWallShell)world.getBlockTileEntity(i, j, k);
        if(west != null)
        {
        	west.setType(shiftedIndex);
        }
        return true;
    }
}
