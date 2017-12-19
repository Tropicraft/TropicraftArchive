package tropicraft.items;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tropicraft.Tropicraft;
import tropicraft.ai.WorldDirector;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.encyclopedia.TropicalBook;
import tropicraft.gui.GuiTropicalBook;
import tropicraft.packets.TropicraftPacketHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemNigelJournal extends ItemTropicraft {

	public ItemNigelJournal(int id, String imgName) {
		super(id, imgName);
		maxStackSize = 1;
		this.setCreativeTab(TropiCreativeTabs.tabMisc);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		
		//if in hand and 1/2 a second
		if (!par2World.isRemote && par3Entity instanceof EntityPlayer && par2World.getWorldTime() % 10 == 0 && par5) {
			updatePagesFromInventory(((EntityPlayer)par3Entity).username, ((EntityPlayer)par3Entity).inventory);
		}
	}
	
	public void updatePagesFromInventory(String username, InventoryPlayer inv) {
		NBTTagCompound pNBT = WorldDirector.getPlayerNBT(username);
		NBTTagCompound nbt = pNBT.getCompoundTag("pagesData");
		if (nbt == null) nbt = new NBTTagCompound();
		boolean changeDetected = false;
		for (int i = 0; i < inv.mainInventory.length; i++) {
			ItemStack is = inv.mainInventory[i];
			if (is != null) {
            	if (is.getItem() instanceof ItemJournalPage) {
            		int pageID = is.getItemDamage();
            		if (!nbt.hasKey("p" + pageID)) {
            			nbt.setBoolean("p" + pageID, true);
            			inv.mainInventory[i] = null;
            			changeDetected = true;
            		}
            	}
            }
		}
		
		if (changeDetected) {
			pNBT.setCompoundTag("pagesData", nbt);
			TropicraftPacketHandler.syncPagesData(username);
		}
	}
	
/*	TODO @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (world.isRemote && getTropBook() != null) {
        	getTropBook().updatePagesFromInventory(entityplayer.inventory);
        	FMLCommonHandler.instance().showGuiScreen(new GuiTropicalBook(getTropBook()));     
        }	

        return itemstack;
    }*/
    
  //  @SideOnly(Side.CLIENT)
 //  private TropicalBook getTropBook() {
 //   	return Tropicraft.journal;
 //TODO   }

	@Override
	public String getImageName() {
		return this.imageName;
	}

}
