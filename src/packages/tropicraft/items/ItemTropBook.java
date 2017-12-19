package tropicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.Tropicraft;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.encyclopedia.TropicalBook;
import tropicraft.gui.GuiTropicalBook;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTropBook extends ItemTropicraft {

    private String bookName;

    public ItemTropBook(int i, TropicalBook book, String name) {
        super(i);
        this.bookName = name;
        maxStackSize = 1;
        this.setCreativeTab(TropiCreativeTabs.tabMisc);
    }

    public ItemTropBook(int i, String name) {
        this(i, null, name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (world.isRemote && getTropBook() != null) {
        	getTropBook().updatePagesFromInventory(entityplayer.inventory);
        	FMLCommonHandler.instance().showGuiScreen(new GuiTropicalBook(getTropBook()));     
        }	

        return itemstack;
    }
    
    @SideOnly(Side.CLIENT)
    private TropicalBook getTropBook() {
    	return Tropicraft.encyclopedia;
    }

	@Override
	public String getImageName() {
		return bookName;
	}
}
