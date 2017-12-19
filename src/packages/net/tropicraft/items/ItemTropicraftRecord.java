package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.proxies.RenderGlobalProxy;

import java.util.List;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTropicraftRecord extends ItemRecord {

	/** Used for client-side stuff on servers, etc */
	@SidedProxy(clientSide = "net.tropicraft.proxies.RecordProxy", serverSide = "net.tropicraft.proxies.RenderGlobalProxy")
	public static RenderGlobalProxy proxy;
	
	public ItemTropicraftRecord(int i, String s) {
		super(i, s);
		setTextureFile("/tropicalmod/tropiitems.png");
		this.setCreativeTab(TropicraftMod.tabFrox);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {

		 if (world.getBlockId(i, j, k) == Block.jukebox.blockID && world.getBlockMetadata(i, j, k) == 0)
	        {
	            if (world.isRemote)
	            {
	                return true;
	            }
	            else
	            {
	                ((BlockJukeBox)Block.jukebox).insertRecord(world, i, j, k, itemstack);
	                world.playAuxSFXAtEntity(null, 1005, i, j, k, shiftedIndex);
	                itemstack.stackSize--;
	                
	                return true;
	            }
	        }
	        else
	        {
	            return false;
	        }
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer ent, List list, boolean wat) {
		list.add("Frox - " + recordName);
	}

    @SideOnly(Side.CLIENT)
    public String func_90043_g()
    {
        return "Frox - " + this.recordName;
    }
}