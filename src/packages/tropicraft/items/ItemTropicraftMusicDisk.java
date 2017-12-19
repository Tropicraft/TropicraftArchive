package tropicraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTropicraftMusicDisk extends ItemRecord {

	/**
	 * Second half of the record name. Eg if image name is "record_easternisles" then this would be "easternisles"
	 */
	private String imagePostfixName;
	
	/** Name of the artist of the track */
	private String artistName;
	
	public ItemTropicraftMusicDisk(int id, String recordName, String imagePostfixName, String artist) {
		super(id, recordName);
		this.imagePostfixName = imagePostfixName;
		this.artistName = artist;
		setCreativeTab(TropiCreativeTabs.tabMusic);
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
	                world.playAuxSFXAtEntity(null, 1005, i, j, k, this.itemID);
	                itemstack.stackSize--;
	                
	                return true;
	            }
	        }
	        else
	        {
	            return false;
	        }
	}

    /**
     * Return the title for this record.
     */
	@Override
    @SideOnly(Side.CLIENT)
    public String getRecordTitle() {
		return String.format("%s - %s", artistName, recordName);
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + "record_" + this.imagePostfixName);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer ent, List list, boolean wat) {
		list.add(getRecordTitle());
	}
}
