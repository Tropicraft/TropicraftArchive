package tropicraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.blocks.tileentities.TileEntityFirePit;
import tropicraft.creative.TropiCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockParticleMaker extends BlockContainer implements ITileEntityProvider {

    public BlockParticleMaker(int blockID) {
        super(blockID, Material.circuits);
        this.disableStats();
        setCreativeTab(TropiCreativeTabs.tabBlock);
        setBlockBounds(0.05F, 0F, 0.05F, 0.95F, 0.1F, 0.95F);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityFirePit();
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    @Override
    public Icon getIcon(int i, int j) {
        return this.blockIcon;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
  
    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer.
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int i, int j, int k)
    {
        return super.getPlayerRelativeBlockHardness(player, world, i, j, k);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + "firepit");
    }
}
