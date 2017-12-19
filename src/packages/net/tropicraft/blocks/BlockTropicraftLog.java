package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicraftLog extends TropicraftBlock{

    public BlockTropicraftLog(int i, int j) {
        super(i, j, Material.wood);
        setTickRandomly(true);
        Block.setBurnProperties(i, 5, 5);
        this.setRequiresSelfNotify();
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public int idDropped(int i, Random random, int j) {
        return TropicraftMod.tropicalWood.blockID;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
        super.harvestBlock(world, entityplayer, i, j, k, l);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        if (world.getBlockMetadata(i, j, k) == 0) {
            spawnCoconuts(world, i, j, k, random, 20);
        }
    }

    public static void spawnCoconuts(World world, int i, int j, int k, Random random, int chance) {

        if ((world.getBlockId(i, j + 1, k) == TropicraftMod.tropicLeaves.blockID && world.getBlockMetadata(i, j + 1, k) == 0) || (world.getBlockId(i, j + 2, k) == TropicraftMod.tropicLeaves.blockID
                && world.getBlockMetadata(i, j + 2, k) == 0)) {
            if (world.isAirBlock(i + 1, j, k) && random.nextInt(chance) == 0) {
                world.setBlockWithNotify(i + 1, j, k, TropicraftMod.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0);

            }
            if (world.isAirBlock(i - 1, j, k) && random.nextInt(chance) == 0) {
                world.setBlockWithNotify(i - 1, j, k, TropicraftMod.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0);


            }
            if (world.isAirBlock(i, j, k - 1) && random.nextInt(chance) == 0) {
                world.setBlockWithNotify(i, j, k - 1, TropicraftMod.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0);


            }
            if (world.isAirBlock(i, j, k + 1) && random.nextInt(chance) == 0) {
                world.setBlockWithNotify(i, j, k + 1, TropicraftMod.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0);
            }

            if (world.isAirBlock(i, j - 1, k) && random.nextInt(chance) == 0) {
                world.setBlockWithNotify(i, j - 1, k, TropicraftMod.coconut.blockID);
                world.setBlockMetadataWithNotify(i, j, k, 0);
            }
        }

    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        if (j == 0) {
            if (i == 1) {
                return TropicraftMod.palmWoodBottom;
            }
            if (i == 0) {
                return TropicraftMod.palmWoodBottom;
            } else {
                return TropicraftMod.tropicalWood.blockIndexInTexture;
            }
        }
        if (j == 8){        	
       	 if (i == 3) {
                return TropicraftMod.palmWoodBottom;
            }
            if (i == 2) {
                return TropicraftMod.palmWoodBottom;
            } else {
                return TropicraftMod.tropicalWood.blockIndexInTexture;
            }
       }
       if(j == 4){
       	if(i == 4 || i == 5){
       		return TropicraftMod.palmWoodBottom;
       	}
       	else{
       		return TropicraftMod.tropicalWood.blockIndexInTexture;
       	}
       }
        if(j == 1){
            if (i == 1) {
                return 145;
            }
            if (i == 0) {
                return 145;
            } else {
                return 144;
            }
        }
        if(j == 5){
        	if(i == 4 || i == 5){
        		return 145;
        	}
        	 return 144;
        }
        if(j == 9){
        	if(i == 3 || i == 2){
        		return 145;
        	}
        	return 144;
        }
        
       
      
       return 1;
    }

    @Override
    public int damageDropped(int i) {
        return i & 1;
    }
    
    /**
     * returns a number between 0 and 3
     */
    public static int limitToValidMetadata(int par0)
    {
        return par0 & 1;
    }

    public float blockStrength(EntityPlayer entityplayer, int metadata) {
   // oldtc    super.blockStrength(entityplayer, metadata);
        if (blockHardness < 0.0F) {
            return 0.0F;
        }
        if (!entityplayer.canHarvestBlock(this)) {
            return 1.0F / blockHardness / 100F;
        } else {
            return entityplayer.getCurrentPlayerStrVsBlock(Block.wood, metadata) / blockHardness / 30F;
        }
    }
    
    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer.
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5)
    {
        return blockStrength(par1EntityPlayer, par2World.getBlockMetadata(par3, par4, par5));
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     * NOTE: This used to be onBlockRemoval - I have yet to find a suitable replacement
     */
    @Override
    public void breakBlock(World world, int i, int j, int k, int lol, int meh) {
        byte byte0 = 4;
        int l = byte0 + 1;
        if (world.checkChunksExist(i - l, j - l, k - l, i + l, j + l, k + l)) {
            for (int i1 = -byte0; i1 <= byte0; i1++) {
                for (int j1 = -byte0; j1 <= byte0; j1++) {
                    for (int k1 = -byte0; k1 <= byte0; k1++) {
                        int l1 = world.getBlockId(i + i1, j + j1, k + k1);
                        if (l1 != TropicraftMod.tropicLeaves.blockID) {
                            continue;
                        }
                        int i2 = world.getBlockMetadata(i + i1, j + j1, k + k1);
                        if ((i2 & 8) == 0) {
                            world.setBlockMetadata(i + i1, j + j1, k + k1, i2 | 8);
                        }
                    }

                }

            }

        }
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }

    @Override
    public boolean canSustainLeaves(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood(World world, int x, int y, int z)
    {
        return true;
    }
    /**
     * Called when the block is placed in the world.
     */
    @Override
    public int getRenderType()
    {
        return 31;
    }
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;
        int var7 = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
        byte var8 = 0;

        switch (var7)
        {
            case 0:
            case 1:
                var8 = 0;
                break;
            case 2:
            case 3:
                var8 = 8;
                break;
            case 4:
            case 5:
                var8 = 4;
        }

        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | var8);
    }
    
    @Override
    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(this.blockID, 1, limitToValidMetadata(par1));
    }

}
