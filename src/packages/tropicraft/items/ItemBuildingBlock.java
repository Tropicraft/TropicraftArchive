package tropicraft.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBuildingBlock extends ItemBlock {

	private static final String[] blockNames = new String[]{"Thatch Bundle", "Palm Planks"};
	
	public ItemBuildingBlock(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	public int getMetadata(int par1)
	{
		return par1;
	}
	
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "buildingBlock" + "." + blockNames[par1ItemStack.getItemDamage()];
    }
	
    /**
     * Returns the unlocalized name of this item.
     */
	@Override
    public String getUnlocalizedName()
    {
        return "buildingBlock";
    }
}
