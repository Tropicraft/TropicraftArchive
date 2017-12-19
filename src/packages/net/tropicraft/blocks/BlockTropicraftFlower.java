package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftFlower extends TropicraftBlock //hai 8>
{
    public int[] textures; //:D
    public String[] names;

    enum sexy {COROSUS, COJO, RUBE, FISHTACO};	//8D

    public BlockTropicraftFlower(int i, int[] j, String[] s, Material material) {
        super(i, material);
        textures = j;
        names = s;
        float f = 0.2F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 4.939F, 0.5F + f);
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        return textures[j];
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
    }

    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.tilledField.blockID;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        super.onNeighborBlockChange(world, i, j, k, l);
        checkFlowerChange(world, i, j, k);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        checkFlowerChange(world, i, j, k);
    }

    protected final void checkFlowerChange(World world, int i, int j, int k) {
        if (!canBlockStay(world, i, j, k)) {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return (world.getFullBlockLightValue(i, j, k) >= 8 || world.canBlockSeeTheSky(i, j, k)) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < textures.length; var4++)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
