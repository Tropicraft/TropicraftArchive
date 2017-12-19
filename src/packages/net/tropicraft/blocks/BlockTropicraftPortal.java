package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntitySplashFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftPortal extends BlockFluid {

    public BlockTropicraftPortal(int i, boolean flag) {
        super(i, Material.water);
        if (flag) {
            setTickRandomly(true);
        }
        this.setBlockUnbreakable();
        this.setResistance(6000000.0F);
    }
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {

    	System.out.println(world.getBlockMetadata(i, j, k) == 8);
    	if (!world.isRemote && entity instanceof EntityPlayerMP && world.getBlockMetadata(i, j, k) == 8) {
            entity.setAir(300);
            ((EntityPlayerMP)entity).timeUntilPortal++;

   //         System.out.println(((EntityPlayerMP)entity).timeUntilPortal);
            
            if (((EntityPlayerMP)entity).timeUntilPortal > 400 && ((EntityPlayerMP)entity).isPotionActive(Potion.confusion.id))
            {
                ((EntityPlayerMP)entity).timeUntilPortal = 0;
                ((EntityPlayerMP)entity).removePotionEffect(Potion.confusion.id);
                TropicraftMod.teleportPlayerToTropics((EntityPlayerMP)entity);
            }
    	}
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
        if (iblockaccess.getBlockId(i, j, k) == 0) {
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        Material material = iblockaccess.getBlockMaterial(i, j, k);
        if (material == blockMaterial) {
            return false;
        }
        if (l == 1) {
            return true;
        }
        return false;

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
    public int tickRate() {
        return 10;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if (world.isRemote)
        {
            sparkle(world, i, j, k);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        if (l == Block.lavaStill.blockID || l == Block.lavaMoving.blockID) {
            if (world.getBlockId(i, j - 1, k) == l) {
                world.setBlock(i, j - 1, k, 0);
            }
            if (world.getBlockId(i, j + 1, k) == l) {
                world.setBlock(i, j + 1, k, 0);
            }
            if (world.getBlockId(i - 1, j, k) == l) {
                world.setBlock(i - 1, j, k, 0);
            }
            if (world.getBlockId(i + 1, j, k) == l) {
                world.setBlock(i + 1, j, k, 0);
            }
            if (world.getBlockId(i, j, k - 1) == l) {
                world.setBlock(i, j, k - 1, 0);
            }
            if (world.getBlockId(i, j, k + 1) == l) {
                world.setBlock(i, j, k + 1, 0);
            }
        }
    }

    @Override
    public int getRenderType() {
        return 4;
    }
    
    @SideOnly(Side.CLIENT)
    private void sparkle(World world, int i, int j, int k)
    {
        Random random = world.rand;
        
        int maxCount = 2;

        if (world.getBlockMetadata(i, j, k) == 0 && world.isRemote)
        {
            for (int count = 0; count < maxCount; count++)
            {
            	world.spawnParticle("bubble", i + random.nextDouble(), j + random.nextDouble(), k + random.nextDouble(), 0D, 0D, 0D);
            }
        }

        if (world.getBlockId(i, j + 1, k) == 0 && world.isRemote)
        {
            for (int count = 0; count < maxCount; count++)
            {
            	world.spawnParticle("splash", i + random.nextDouble(), j + 0.9, k + random.nextDouble(), 0D, 0D, 0D); 
            }
        }
    }

}
