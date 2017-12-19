package net.tropicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.encyclopedia.TropicalBook;
import net.tropicraft.gui.GuiTropicalBook;
import cpw.mods.fml.common.FMLCommonHandler;

public class ItemNigelJournal extends ItemTropicraft {

    public TropicalBook tropbook;

    public ItemNigelJournal(int i, TropicalBook book) {
        super(i);
        maxStackSize = 1;
        tropbook = book;
    }

    public ItemNigelJournal(int i, int k) {
        super(i);
        maxStackSize = 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {

        if (world.isRemote && tropbook != null) {
        	tropbook.updatePagesFromInventory(entityplayer.inventory);
        	FMLCommonHandler.instance().showGuiScreen(new GuiTropicalBook(tropbook));     
        }	

        return itemstack;
    }
}
