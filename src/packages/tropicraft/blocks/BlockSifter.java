package tropicraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.blocks.tileentities.TileEntitySifter;
import tropicraft.creative.TropiCreativeTabs;

public class BlockSifter extends BlockContainer {

	public BlockSifter(int id) {
		super(id, Material.rock);
		disableStats();
		setCreativeTab(TropiCreativeTabs.tabBlock);
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySifter();
	}
	
	@Override
    public int idDropped(int i, Random random, int j) {
        return TropicraftBlocks.sifter.blockID;
    }
	
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int d, float f1, float f2, float f3) {
    	if (world.isRemote) {
            return true;
        }
    	
    	ItemStack stack = entityplayer.getCurrentEquippedItem();

        TileEntitySifter tileentitysifta = (TileEntitySifter) world.getBlockTileEntity(i, j, k);

        if (tileentitysifta != null && entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().itemID == (new ItemStack(Block.sand)).itemID && !tileentitysifta.getIsSifting()) {
            entityplayer.getCurrentEquippedItem().stackSize--;
            tileentitysifta.setSifting(true);
          //  System.out.println("setting te sifta" + tileentitysifta.sifting);
        }
        return true;
    } // /o/ \o\ /o\ \o\ /o\ \o/ /o/ /o/ \o\ \o\ /o/ /o/ \o/ /o\ \o/ \o/ /o\ /o\ \o/ \o/ /o/ \o\o\o\o\o\o\o\o\o\ :D
    
	/**
	 * Register all Icons used in this block
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "sifter");
	}

}
