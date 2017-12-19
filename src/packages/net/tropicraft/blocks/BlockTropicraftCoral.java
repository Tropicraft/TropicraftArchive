package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftCoral extends TropicraftBlock {    //hai 8>

    public int collectionID;
    public int textures[]; //:D
    private float dayBrightness = 0.3F;
    private float nightBrightness = 0.6F;

    public BlockTropicraftCoral(int i, int[] j, Material material) {
        super(i, material);
        textures = j;
        setTickRandomly(true);
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        return textures[j & 7];
    }

    @Override
    public int damageDropped(int i) {
        return i & 7;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return super.canPlaceBlockAt(world, i, j, k) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k)) && world.getBlockMaterial(i, j, k) == Material.water
                && world.getBlockMaterial(i, j + 1, k) == Material.water;
    }

    protected boolean canThisPlantGrowOnThisBlockID(int i) {
        return i == Block.grass.blockID || i == Block.dirt.blockID || i == Block.sand.blockID || i == TropicraftMod.purifiedSand.blockID;
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
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k) & 7, 0);
            world.setBlockWithNotify(i, j, k, 0);
        } else {
            // Checks if world day/night does not match coral day/night type
            int meta = world.getBlockMetadata(i, j, k);
            if (world.isDaytime() != isDayCoral(meta)) {
                int newMetadata = (meta & 7) | (isDayCoral(meta) ? 8 : 0);
                world.setBlockAndMetadata(i, j, k, this.blockID, newMetadata);
            }
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        if (isDayCoral(world.getBlockMetadata(x, y, z))) {
            return (int) (15.0F * dayBrightness);
        } else {
            return (int) (15.0F * nightBrightness);
        }
    }

    private boolean isDayCoral(int metadata) {
        return (metadata & 8) == 0;
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
        return (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMaterial(i, j + 1, k) == Material.water) && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
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
        for (int var4 = 0; var4 < 6; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
