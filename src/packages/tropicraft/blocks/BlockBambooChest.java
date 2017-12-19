package tropicraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockChest;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.ModRenderIds;
import tropicraft.blocks.tileentities.TileEntityBambooChest;
import tropicraft.creative.TropiCreativeTabs;

public class BlockBambooChest extends BlockChest implements ITileEntityProvider {

    public BlockBambooChest(int blockID) {
        super(blockID, 0);
        this.disableStats();
        setCreativeTab(TropiCreativeTabs.tabBlock);
    }
    
    @Override
    public int getRenderType() {
    	return ModRenderIds.bambooChestModelId;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityBambooChest();
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    @Override
    public Icon getIcon(int i, int j) {
        return this.blockIcon;
    }
  
    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer.
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int i, int j, int k)
    {
        TileEntityBambooChest tile = (TileEntityBambooChest) world.getBlockTileEntity(i, j, k);
        if (tile != null && tile.isUnbreakable()) {
            return 0.0F;
        }
        return super.getPlayerRelativeBlockHardness(player, world, i, j, k);
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + "bamboochest");
    }
}
