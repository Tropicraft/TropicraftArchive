package tropicraft.items;

import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemReed;

public class ItemFlowerPot extends ItemReed {

	public ItemFlowerPot(int par1, Block par2Block) {
		super(par1, par2Block);
		this.setCreativeTab(TropiCreativeTabs.tabDecorations);
	}

	@Override
	public void registerIcons(IconRegister registry) {
		this.itemIcon = registry.registerIcon(ModInfo.ICONLOCATION + "flowerpot");
	}

}
